package java8.streamProcessing;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.*;
import static util.Print.p;
import static util.Print.printStream;

public class MyStream1 {
    public  static void main(String args[]) throws Exception {
        generate();
        operationOnStream();
        //primitiveStreams();
    }

    // A. generate Stream
    static void generate() throws IOException
    {
        printStream(() -> Stream.of("aa", "bb", "cc","aa", "bb", "cc"));
        printStream(() -> Stream.builder().add("a").add("b").add("c").build());
        printStream(() -> { List l = new ArrayList<String>(); l.add("a1"); l.add("b1"); return l.parallelStream(); });
        printStream(() -> {String[] sa = new String[] {"a2","b2"}; return Arrays.stream(sa); });

        printStream(() -> Stream.generate(() -> "element").limit(5));
        printStream(() -> Stream.iterate(40, n -> n + 2).limit(5)); //Note: use IntStream is want to get int[], rather that Integer[]
        printStream(() -> Stream.iterate("lekhraj", n -> n + "--d").limit(5));
        printStream(() -> {
            Stream ret = null;
            try {
                ret = Files.lines(Path.of("C:\\Users\\Manisha\\Documents\\GitHub\\02-Backend-API-Java17\\src\\main\\resources\\banner.txt"));
            } catch (IOException e) {
                ret = Stream.empty();
                p(e.getLocalizedMessage());
            }
            return ret;
        });
    }

    static void primitiveStreams(){
        IntStream iStream = IntStream.range(1,3);
        LongStream lStream = LongStream.range(1,3);
        DoubleStream dStream = DoubleStream.builder().add(2).build();
    }

    // B. Operations
    // B.1. Immediate --> allows chaining, map,  filter,
    // B.2. Terminal --> no chaining, end with definite value. eg: count, isParallel, forEach(), any/all/noneMatch, reduce etc
    // More : limit(2), skip(2),
    static void operationOnStream (){
        operationOnStream_distinct(Stream.of("aa1", "bb1", "cc1","aa1", "bb1", "cc1"));
        operationOnStream_map(Stream.of("aa1", "bb1", "cc1","aa1", "bb1", "cc1"));
        operationOnStream_anyMatch(Stream.of("aa1", "bb1", "cc1")); // T/F
        operationOnStream_filter(Stream.of("aa1", "bb1", "cc1"));
        operationOnStream_flatMap(Stream.of("aa1", "bb1", "cc1"));
        operationOnStream_reduce(Stream.of("aa1", "bb1", "cc1")); //binary-Operator

        // Collectors
        // IntSummaryStatistics : Collectors.summarizingInt(i->i)
        Stream<Integer> stream = Stream.of(1,2,3,4,5);
        IntSummaryStatistics r = Stream.of(1,2,3,4,5)
                .collect(Collectors.summarizingInt(i->i)); // i->i , Integer::intValue
        p(r.getAverage(), r.getCount(), r.getMax(), r.getMin());

        stream = Stream.of(1,2,3,4,5); p( stream.collect(Collectors.averagingInt(i->i)) );
        stream = Stream.of(1,2,3,4,5); p( stream.collect(Collectors.summingInt(i->i)));
        stream = Stream.of(1,2,3,4,5); p( stream.collect(Collectors.partitioningBy(i-> i>3)));
        stream = Stream.of(1,2,3,4,5); p( stream.collect(Collectors.groupingBy(i-> i>3)));

    }




    // A. Intermediate
    static void operationOnStream_distinct (Stream stream){
        //p(stream.distinct().count(), "xxxxxx");
        stream.distinct().forEach(System.out::println);
    }
    static void operationOnStream_map(Stream stream){
        stream.map(x->x.hashCode()).forEach(System.out::println);
    }
    static void operationOnStream_anyMatch(Stream<String> stream){
        p(stream.anyMatch(x->x.contains("a")));
    }
    static void operationOnStream_filter(Stream<String> stream){
        stream.filter(x->x.contains("a")).forEach(System.out::println);
    }
    static void operationOnStream_flatMap(Stream<String> stream){
        System.out.println("operationOnStream_flatMap");
        stream
                .flatMap(str->str.chars().mapToObj(c->(char)c)) // str.chars() - IntStream
                .forEach(System.out::println);
    }
    static void operationOnStream_reduce(Stream<String> stream){
        p(stream.reduce("", (agg, cur)-> agg + "_"+ cur));
    }

}



/*
ParallelStream --> runs cb/Lmbda in II.

Collection/Array - parallelStream
other- stream().parallel
Note:: uses ExecutorService and ForKJoin framework to achieve it.

*/