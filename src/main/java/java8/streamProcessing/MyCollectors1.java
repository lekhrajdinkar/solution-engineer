package java8.streamProcessing;

import web.model.dto.CourseDTO;

import java.util.*;
import java.util.stream.Collectors;
import static util.Print.p;

public class MyCollectors1
{
    public static void main(String a[])
    {
        String[] strings = {"1", "2", "3", "3"};

        // A. Collectors Interface
        p("\n-----------A. Collectors---------");
        // List
        ArrayList l = (ArrayList) Arrays.stream(strings).collect(Collectors.toList());
        p(l, l.getClass().getName()); // or toUnmodifiableList()

        // Set
        HashSet s = (HashSet)  Arrays.stream(strings).collect(Collectors.toSet());
        p(s, s.getClass().getName()); // or toUnmodifiableSet()

        // Map
        HashMap m1 = (HashMap)  s.stream().map(e->e).collect(Collectors.toMap(k->k,k->k));
        p(m1, m1.getClass().getName()); // no duplicate no collision

        // collision : IllegalStateException due to duplkicate 3
        // HashMap m2 = (HashMap)  l.stream().map(e->e).collect(Collectors.toMap(k->k,k->k)); p(m2);
        // Function.identity() === k->k a function that returns its input.

        // Fix collision : 3rd arg, another fi
        HashMap m2 = (HashMap)  l.stream().collect(Collectors.toMap(k->k,k->k, (k,dup)->dup+"__dup"));
        p(m2, m2.getClass().getName());
        m2.put("4","4"); p(m2); //works...

        //toUnmodifiableMap
        //HashMap m2_final = (HashMap) l.stream().collect(Collectors.toUnmodifiableMap(k->k, k->k, (k, dup)->dup+"__dup")); p(m2);
        // class cast exception, could be concurenthashmap
        Map m2_final = (Map) l.stream().collect(Collectors.toUnmodifiableMap(k->k, k->k, (k, dup)->dup+"__dup"));
        p(m2_final, m2_final.getClass().getName()); // java.util.ImmutableCollections$MapN
        //m2_final.put("4","4");  //UnsupportedOperationException


        p("\n-----------B. Collectors :: CollectingAndThen---------");
        // if want to perform some operation on result after collecting
        HashSet c1 = (HashSet) s.stream()
                .collect(Collectors.collectingAndThen(Collectors.toList(), list->new HashSet(list) ));
        p(c1, c1.getClass().getName());

        int size = (int) s.stream()
                .collect(Collectors.collectingAndThen(Collectors.toList(), list->list.size() ));
        p(size);
        p(s.stream().collect(Collectors.counting())); //better way

        List c2 = (List) s.stream()
                .collect(Collectors.collectingAndThen(Collectors.toList(), list->list.stream().map(x->x+"---modified---").collect(Collectors.toList())));
        p(c2);

        // List.copyOf(list) --> returns Immutable list, ImmutableCollections$ListN
        c2 = (List) s.stream()
                .collect(Collectors.collectingAndThen(Collectors.toList(), list -> List.copyOf(list)));
        p(c2, c2.getClass().getName());
        // c2.add("5"); //UnsupportedOperationException


        p("\n-----------    C . Collectors :: Joining   ---------");
        //String[][] string2d = new String[][]{ {"1", "2"}, {"3", "3"}};

        //  Join String collection
        p( Arrays.stream(strings).collect(Collectors.joining(" ")),
           Arrays.stream(strings).collect(Collectors.joining(" ", "PRE-- { ", " } --POST")));

        //p( Arrays.stream(string2d).flatMap(x->x).collect(Collectors.joining(" ")); //String

        //  Join Object collection
        List <CourseDTO> courses = List.of(
                CourseDTO.builder().title("c1").desc("c1---desc").build(),
                CourseDTO.builder().title("c2").desc("c2---desc").build(),
                CourseDTO.builder().title("c3").desc("c3---desc").build()
                );
        p( courses.stream()
                .map(x->x.toString()) //extra step to make charSeq.... <<                           Here
                .collect(Collectors.joining("\n--delimiter--\n"))); //joining works if instance/item is charSequence

        p("\n-----------    D. Collectors :: SummarizingDouble, averaging, summing    ---------");

        // SummarizingDouble/Long/Int is a collector that returns a special class
        // containing statistical information about numerical data in a Stream of extracted elements.

        p( Arrays.stream(strings).collect( Collectors.summarizingDouble(x->1)));
        // Output: DoubleSummaryStatistics{count=4, sum=4.000000, min=1.000000, average=1.000000, max=1.000000}

        p( Arrays.stream(strings).collect( Collectors.summarizingDouble(x->1L)));
        p( Arrays.stream(strings).collect( Collectors.summarizingDouble(x-> Double.parseDouble(x))));
        //p( Arrays.stream(strings).collect( Collectors.summarizingDouble(x->x))); //error

// averagingDouble
        p( Arrays.stream(strings).collect( Collectors.averagingDouble(x->1L))); //1.0L

// summingDouble
        p( Arrays.stream(strings).collect( Collectors.summingDouble(x->1L))); //4.0L

// min/max
        p( Arrays.stream(strings).collect( Collectors.maxBy( (e1,e2) -> (int) (Double.parseDouble(e1) - Double.parseDouble(e2))))); // comparator Fi :: compare(n,n+1)
        p( Arrays.stream(strings).map(x->Double.parseDouble(x)).collect( Collectors.maxBy( (e1,e2) -> (int) (e1-e2) ))); //Integer::compareTo better
        p(List.of(1,2,3).stream().collect(Collectors.minBy( Comparator.naturalOrder()))); // returns Optional[1]

// Collectors.groupingBy(Classification Function)
        p( Arrays.stream(strings).collect( Collectors.groupingBy(x->x+"-GROUP_NAME")));
        // {3-GROUP_NAME=[3, 3], 2-GROUP_NAME=[2], 1-GROUP_NAME=[1]}

// Collectors.partitioningBy(Classification Function)
        p( Arrays.stream(strings).collect( Collectors.partitioningBy(x-> Integer.parseInt(x)>2)));


//        p( Arrays.stream(strings).collect(
//                Collectors.teeing(
//                        Collectors.joining(" - ")),  // colllector-1
//                        Collectors.joining(" -- ")),  // colllector-2
//                            (c1_res, c2_res) -> { }   // do somethinf with results
//        ));
    }



}
