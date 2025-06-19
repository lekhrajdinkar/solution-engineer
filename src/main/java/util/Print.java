package util;

import java8.streamProcessing.FnIStreamGen;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Stream;

public class Print {
    public static void p(Object... o){
        System.out.println("\n==============");
        Arrays.stream(o).forEach(System.out::println);
    }

    public static void pc(String desc, Collection c){
        System.out.println("\n======= "+ desc +" ========");
        c.stream().forEach(System.out::println);
    }

    public static void printStream(FnIStreamGen streamGen){
        streamGen.generate().forEach(System.out::println);
    }

    public static void p(String msg, Stream s){
        System.out.println(msg);
        s.forEach(System.out::println);
        System.out.println("-------------------");}


}
