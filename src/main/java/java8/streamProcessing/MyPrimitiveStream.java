package java8.streamProcessing;

import java.util.*;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class MyPrimitiveStream {
    static void p(Object... objArr){
        Arrays.stream(objArr).forEach(System.out::println);
        System.out.println("--------------------------");
    }
    static int[] numbers = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    public static  void main(String a[])
    {
        // =======================
        // Int Streams
        // =======================
        IntStream streamOfChars = "abc".chars();
        Stream<Integer> streamBoxed = IntStream.of(numbers).boxed();

        IntStream intStream1 = IntStream.of(numbers)
                .limit(8) //.peek(x->p("limit",x))
                .skip(1)//.peek(x->p("Skip",x))
                .filter(i->i>4)//.peek(x->p("filter",x))
                .map(i->i+100);//.peek(x->p("map",x));

        long count = intStream1.count(); p("1. Count", count);

        OptionalInt o = IntStream.of(numbers).reduce((acc,e)->acc+e);
        o.ifPresent(MyPrimitiveStream::p);

        p("2. summaryStatistics().getMax()", IntStream.of(numbers).summaryStatistics().getMax());
        p("3. summaryStatistics().getCount()", IntStream.of(numbers).summaryStatistics().getCount());

        boolean noneMatch = IntStream.of(numbers).noneMatch(num -> num < 0);
        p("4, No negative numbers?" , noneMatch);

        IntStream.of(1, 2, 3, 4, 5)
                .mapToObj(String::valueOf)
                .forEach(System.out::println);

        //IntStream.sum() --> Collector

        // =======================
        // Double Streams
        // =======================
        DoubleStream doubleStream = new Random().doubles(3);

    }

}
