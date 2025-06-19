package java8.Collection;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import static util.Print.pc;
import static util.Print.p;

public class MyList1
{
    public static void main(String a[])
    {
        Collections.unmodifiableList(new ArrayList<>()); // create immutable list
        arrayList_Create();
        arrayList_Search("Anna");
        arrayList_MultiThreadEnv();
        //arrayList_Copy(); //error
        LinkedList_test();
        diff_list();
    }

    static void arrayList_Copy(){
        List<Integer> source  = List.of(1,2,3); // java.util.ImmutableCollections$ListN

        List<Integer>  target  = new ArrayList(source); // Way-1
        target = source.stream().collect(Collectors.toList()); // way-2.1 (Preferred)
        source.stream().forEachOrdered(target::add); // way-2.2
        Collections.copy(source,target); // way-3
        target.addAll(source); //way-4
    }

    static void arrayList_Create()
    {
        List<Integer> list = null;

        // 1. create, add, remove, set, reverse, etc
        list = List.of(1,2,3);
        list = new ArrayList();
            list.add(0);
            list.add(1) ;
            list.remove(1) ;
        Collections.reverse(list);
        pc("1.1 CREATE : ",list);

        Stream<Integer> intStream = IntStream.range(0,10).boxed();
        list = intStream.toList();
        pc("1.2. CREATE from IntStream : ",list);

        list = Stream.of(1,2,3,4,5).toList();
        pc("1.2. CREATE from Stream.of() : ",list);

        // 2. Iterate
        System.out.print("---2.1 Iterator---");
        Iterator<Integer> itr = list.iterator();
        while(itr.hasNext()){
            Integer item = itr.next();
            // list.add(22); // FAIL-FAST :: java.util.ConcurrentModificationException
            System.out.print("---"+item);
        }

        // 3. ListIterator
        System.out.print("---2.2 ListIterator---");
        ListIterator<Integer> litr = new ArrayList(list).listIterator();
        while(litr.hasPrevious()){
            Integer item = litr.previous();
            System.out.print("---"+item);
        }
    }

    static void arrayList_Search(String text)
    {
        List<String> list = IntStream
                .range(0,5)
                .boxed()
                .map(i->Integer.toHexString(i))//           here
                .collect(Collectors.toCollection(ArrayList::new));
                //.collect(Collectors.toList());
        list.add("Anna");
        pc("4.1. Search List : ",list);

        //Search : 1
        List<String> res = list.stream()
                .filter(s->s.contains(text))
                //.collect(Collectors.toList());
                .toList();
        pc("4.2. Search result : ",res);

        //Search : 2
        Collections.sort(list);
        int indexFound = Collections.binarySearch(list, text);
        p("4.3. search index : ",indexFound); //5
    }


    static void arrayList_MultiThreadEnv(){
        // thread safe + fail-safe iterator
        CopyOnWriteArrayList list = new CopyOnWriteArrayList(new Integer[]{0,1,2});

        list.add(100);
        Iterator<Integer> itr = list.iterator();
        list.add(200);

        //itr.forEachRemaining(i->p(i));
        while(itr.hasNext()){
            list.add(300);
            Integer item = itr.next(); System.out.print("---"+item+"---"); // 0,1,2,100
        }
        itr = list.iterator();
        itr.forEachRemaining(System.out::println);
    }

    static void LinkedList_test()
    {
        LinkedList<String> list = new LinkedList();
        list.addFirst("item1");
        list.add("item2"); list.add("item2");
        list.add("item3");list.add("item3");list.add("item3"); // thrice
        list.addLast("item4");
        p(list.getFirst(),list.getLast(),list.get(2));
        list.stream().forEach(System.out::println);

// 1. Get Random index
        int randomIndex = (int) (new Random().nextFloat() * list.size())+1;
        randomIndex = new Random().nextInt(list.size());
        p("RANDOM Element from List : ", list.get(randomIndex));

// 2. Collectors - groupingBy, partitioningBy
        System.out.print("\nCollectors.groupingBy(Function) : ");
        Map<String,List<String>> result = list.stream().collect(Collectors.groupingBy(s->"<"+s+"-KEY>"));
        p(result);


        System.out.print("\nCollectors.groupingBy(Function,Collector) : ");
        p(list.stream().collect(Collectors.groupingBy(s->s, Collectors.counting())));


        System.out.print("\nCollectors.Partitioning : ");
        Map<Boolean,List<String>> result2 = list.stream().collect(Collectors.partitioningBy(s-> s.contains("4")?false:true));
        p(result2);

// 3. Search an Item with stream
        Optional<String> finditem2 = list.stream()
                        .filter(x -> "item2".equals(x))
                        .distinct() // remove duplicates
                        .findAny();
        p(finditem2.orElse("not-found:take-Sample-item-XXXX"));
    }

// 4. Compare 2 list and find differences
    static void diff_list(){
        List<Integer> listOne = List.of(1,2,3,4);
        List <Integer>listTwo = List.of(1,2);
        List<Integer> differences = listOne.stream()
                .filter(element -> !listTwo.contains(element))
                .collect(Collectors.toList());
        p(differences, listOne.getClass().getName());
    }
}

/*
- Intermediate
    - .distinct() --> remove duplicates
    - .filter(Objects::nonNull) --> remove Nulls

- terminal
    - collect(toCollection(ArrayList::new)) || collect(Collectors.toList()) || stream.toList()
    - forEachOrdered() vs foreach() :: when order of processing is imp while parallel processing, but slow.
    - findAny() : it returns Optional<T>
    - toCollect(Collectors.*)
        - partitioningBy(-)
        - groupingBy (-), groupingBy (-,-)

- more
    - of, copyof :: java.util.ImmutableCollections$ListN
    - Integer.toHexString(i)
    - itr.forEachRemaining(x->{})
 */

