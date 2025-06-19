package java8.streamProcessing;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static util.Print.p;

public class MyStream2 {

    static List<Integer> list = new ArrayList();
    static Collection collection = list;
    static Stream<Integer> stream = Stream.empty();
    static Stream<String> streamStr = Stream.empty();

    //Reuse above stream (Using Supplier)
    static Supplier<Stream<Integer>> streamSupplier;
    static Supplier<Stream<String>> streamStrSupplier;
    static{
       streamSupplier = ()-> Arrays.stream(new Integer[] {1,2,3,4,5,6,6,6,6});
       streamStrSupplier = ()->streamStr = Arrays.stream(new String[] {"zen", "ben", "ten"});
    }

    public static void main (String... arr) throws IOException {
        System.out.println("========== A. Create Streams =============");
        //initStreams();

        System.out.println("========== B. Intermediate =============");
        //intermediateOperation();

        System.out.println("========== C. terminal operations =============");
        terminalOperation();
    }


    static int accumulatorFunction(int n1, int n2){return n1+n2;}



    static void initStreams() throws IOException {
        // >>> Executing a terminal operation makes a stream inaccessible.
        // >>> se one terminal operation per stream.
        // >>> Intermediate operations are lazy.

        // A.1. From Collections and array
        stream = collection.stream();
        stream = list == null || list.size()==0 ? Stream.empty() : list.stream(); p("Stream.empty()",stream);

        // A.2 from Stream util
        stream = Stream.<Integer>builder().add(1).add(200).add(300).build();
        stream = Stream.generate(()->1).limit(2); p("Stream.generate",stream);//Supplier
        stream = Stream.iterate(40, n->n+2).limit(3);  p("Stream.iterate",stream);
        stream = Stream.iterate(40, n-> n<=46, n->n+2);  p("Stream.iterate",stream); // java 9, 2nd Agr - Predicate,  to terminate it.

        stream = Stream.of(1,2,3);  p("Stream.of",stream); //fixed lenght Stream

        //List locatList = new ArrayList<Integer>();
        //Stream.from( locatList); // java 17, dynamic lenght , Arg - Iterable

        // A.3 patten/regex + String
        streamStr = Pattern.compile(" ").splitAsStream("a b c"); p("Pattern.compile",streamStr);

        //A.4 Files
        Path path = Paths.get("C:\\Lekhraj\\Github\\02-Backend-API-Java17\\src\\main\\java\\java8\\streamProcessing\\stream.txt");
        Stream<String> streamOfStrings = Files.lines(path);
        Stream<String> streamWithCharset = Files.lines(path, Charset.forName("UTF-8"));
        p("read files streamWithCharset", streamWithCharset);
    }

    static  void intermediateOperation() {
        // java8 - filter, map, flatmap, peek, distinct

        // Java 9- takewhile + dropwhile

        // jav 16 - multiMap
        // https://chat.openai.com/c/a9bd942f-f423-49ef-bc91-85b4cb6af15d
        streamStr = streamStrSupplier.get().map(name->"prefix-"+name+"-suffix");  //takes consumer
        p(" map() : ",streamStr ); // 3 elements

        //re-use stream
        streamStr = streamStrSupplier.get().mapMulti((name,consumer)-> { //takes BiConsumer -> element + Consumer
            consumer.accept("prefix-1" + name + "suffix-1"); // Emit.
            consumer.accept("prefix-2" + name + "suffix-2");
            // ...
            // emit as many values using Consumer.accept (2nd arg)
        });
        p(" mapMulti() : ",streamStr ); //6 elements

    }

    static void terminalOperation(){

        // **** Also Check Collection1/2/3 ****

        //1. reduce()
        //reduce has 3 variant. 3rd one for parallel reduction
        stream = Arrays.stream(new Integer[] {1,2,3});
        int count = stream.reduce(100, (acc,x)-> x+acc);
        p("reduce1 :: count "+count, Stream.empty());

        stream = Arrays.stream(new Integer[] {1,2,3});
        Optional<Integer> optionalCount = stream.reduce((acc,x)-> x+acc);
        p("reduce2 :: count "+optionalCount.get(), Stream.empty());

        List<String> words = Arrays.asList("apple", "banana", "cherry");
        String resultReduce3 = words.parallelStream()
                .reduce("",
                        (partialResult, word) -> partialResult + "[" + word + "]",
                        (partialResult1, partialResult2) -> partialResult1 + partialResult2);
        p("reduce3 :: ", resultReduce3);

        // Collectors.summingInt()
        List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5);
        Integer sum = integers.stream().collect(Collectors.summingInt(Integer::intValue));
        stream = Arrays.stream(new Integer[] {1,2,3});
        count = stream.collect(Collectors.summingInt(x->x));
        p("Collectors.summingInt :: count "+count, Stream.empty());

        // anyMatch
        stream = Arrays.stream(new Integer[] {1,2,3});
        boolean result = stream.anyMatch(x->x==2); // allMatch, noneMatch
        p("anyMatch / allMatch / noneMatch :: "+result, Stream.empty());

        // findAny
        stream = Arrays.stream(new Integer[] {1,2,3});
        Optional<Integer> opt = stream.findAny(); // findFirst()
        p("findAny / findFirst :: "+result, Stream.empty());

        // Collectors.teeing //fix error
        /*Long average = streamStrSupplier.get()
                .collect(
                 Collectors.teeing(
                    Collectors.summingInt(i -> Integer.parseInt(i)),
                    Collectors.counting(),
                    (total, countt) -> total / countt //3rd arg - BiFunction <<<<
        ));*/

        p("mapMulti");
        stream = Arrays.stream(new Integer[] {1,2,3});
        stream.mapMulti( (item, consumer ) -> {
                consumer.accept(item);
                consumer.accept(item);
                consumer.accept(item);
        }).forEach(System.out::print);

    }

}
