package java8.streamProcessing;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import static java.util.stream.Collectors.toCollection;
import static util.Print.p;

public class MyCollecters2 {

    static List<Integer> l = List.of(1,2,3,4,5,1,2,3,4,5,1,2,3,4,5); //Immutable List.
    public static <HasSet> void main(String a[])
    {
        // collect(Collectors.* )
        // 1. ============= groupingBy =============

        // 1.1 Collectors.groupingBy(Classify Function)

        Map<String,List<Integer>> r = l.stream()
                .skip(5)
                .limit(5)
                .sorted() // pass comparator, else Comparable of Integer
                //.peek(MyCollecters2::p)
                .peek(System.out::println)
                // group by String - Even/Odd, could also be complex object.
                .collect(Collectors.groupingBy(n-> n%2==0 ? "EVEN": "ODD"));
                //.collect(Collectors.groupingBy(n-> n%2==0 ? "EVEN": "ODD", maxBy(Comparator.naturalOrder()) ));
        p("1.1. Collectors.groupingBy(Function) - even/odd",r);

        // 1.2 Collectors.groupingBy(Function)
        Map<Integer,List<Integer>> r11 = l.stream().collect(Collectors.groupingBy(n->n));
        p("1.2. Collectors.groupingBy(Function) - group by same number",r11);

        // 1.3 Collectors.groupingBy(Function, downstream-Collectors)
        Map<Integer,Long> r12 = l.stream().collect(Collectors.groupingBy(n->n, Collectors.counting()) );
        p("1.3. Collectors.groupingBy(Function, downstream-Collector) - group by count",r12);
        r12.keySet().forEach(x-> { p("count of "+x+" is "+r12.get(x));});

        //2  ============ partitioningBy =============

        // 2.1 Collectors.partitioningBy(Function)
        Map<Boolean,List<Integer>> r2 = l.stream().collect(Collectors.partitioningBy(n->n%2==0) );
        p("2.1. Collectors.partitioningBy(Function) - even/odd",r2);

        // 2.2 Collectors.partitioningBy(Function, Collectors)
        Map<Boolean,Long> r21 = l.stream().collect(Collectors.partitioningBy(n->n%2==0, Collectors.counting()) );
        p("2.2. Collectors.partitioningBy(Function, Collectors) - even/old with count",r21);
        //r21.keySet().forEach(x-> { System.out.println("count of "+x+" is "+r21.get(x));});

        // 3.  Collectors.collectingAndThen
        Map<Integer,Boolean>  r31 = l.stream().distinct()
                .collect(Collectors.collectingAndThen(
                        Collectors.toMap(x->x, x->x%2==0),
                        m-> Collections.unmodifiableMap(m)
                ) );
        p("3.1. Collectors.collectingAndThen(Collectors, Function) ",r31);
        //r31.put(6,true); // >>>>> UnsupportedOperationException

        // 4.
        // Collectors.counting()
        // Collectors.maxBy()/minBy() -> iggest/smallest element of a Stream according to a provided Comparator instance.
        // eg : Comparator.naturalOrder(), Integer::compareTo, etc

        // 5. Collectors.joining
        Stream<Integer> stream = Arrays.stream(new Integer[] {1,2,3,4,5});
        String joinedvalue = stream.map(x->String.valueOf(x))
                .collect(Collectors.joining("--", "prefix", "suffix"));

        p("Collectors.joining :: joinedvalue "+joinedvalue, Stream.empty());

        // 6. teeing() / java12
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        double sum = numbers.stream().collect(
                Collectors.teeing(
                        Collectors.summingDouble(i -> i),
                        Collectors.counting(),
                        (total, count) -> total / count
                )
        );
        System.out.println("teeing: " + sum); // Output: 3.0

        // 7 Collector.Filtering / J9
        // like Filter().collect(Collector.*)
        p( "7. Collector.Filtering" , numbers.stream().collect(Collectors.filtering(
                i->i>3, Collectors.toList()
        )) );

        // 8 Collector.FlatMapping / J9
        // like flatmap().collect(Collector.*)
        List list = List.of(List.of(1,2,3), List.of(4,5,6));
        p( "8. Collector.FlatMapping" , list.stream().collect(Collectors.flatMapping(
                (List i)->i.stream(), Collectors.toList()
        )) );

        // 9. collector.toCollection - implemnetation
        // when using the toSet and toList collectors, we can’t make any assumptions of their implementations.
        List<String> result = Stream.of("lekhraj", "Dinkar").collect(toCollection(LinkedList::new));
        p("collector.toCollection ", result, result instanceof LinkedList<String>);

        // Collectors::toUnmodifiableList, toUnmodifiableSet, toUnmodifiableMap
        List<Integer> unmodifiableList = Arrays.asList(1, 2, 3, 4, 5).stream()
                .filter(n -> n % 2 == 0)
                .collect(Collectors.toUnmodifiableList());

        //  ================toMap==============================

        // 50 Collectors.toMap(Function, Function)
        // notice distinct, to resolve duplicates.
        Map<Integer,Boolean>  r50 = l.stream().distinct().collect(Collectors.toMap(x->x, x->x%2==0) );
        r50.put(6,true);
        p("50.1. Collectors.toMap(Function, Function) - k:num, v-Is even/odd",r50);

        // 50.1 Resolve duplicate key
        // Collectors.toMap(Function, Function, (old,new)->{})
        r50 = l.stream().collect(Collectors.toMap(x->x, x->x%2==0, (existing,replacmnet)->existing) );
        r50.put(6,true);
        p("50.1. Collectors.toMap(Function, Function, resolver) - k:num, v-Is even/odd",r50);

        // 50.2 Sorted/ConcurrentHashMap Map
        // Collectors.toMap(Function, Function, (old,new)->{}, Supplier)
        // >>> 4th arg Supplier
        TreeMap<Integer,Boolean> tmap = l.stream().collect(Collectors.toMap(x->x,
                x->x%2==0,
                (existing,replacmnet)->existing,
                TreeMap::new // ConcurrentHashMap::new
        ) );
        r50.put(6,true);
        p("50.1. Collectors.toMap(Function, Function, resolver, Supplier) - k:num, v-Is even/odd",tmap);

        //  ================toList==============================
        // java 16. Streams.toList() :: shortcut for collect(collectors.toList())

        list = Stream.of("a", "b", "c", "d").collect(Collectors.toList());

        // 51. Immutable
        // Collectors.toUnmodifiableList() /Set() / Map
        // java 19 - handy method -  StreamtoList
        List<String> immutableList = Stream.of("a", "b", "c", "d").toList(); // without collect()

        // 53.
        // Collectors.summarizingDouble/Long/Int()
        // Collectors.averagingDouble/Long/Int()
        // Collectors.summingDouble/Long/Int()
    }
}

/*
// Todo
1. custom collector
https://www.baeldung.com/java-stream-immutable-collection

2. parallel collector
 */

/*
Reference:
https://www.baeldung.com/java-collectors-tomap
https://www.baeldung.com/java-stream-immutable-collection
 */
