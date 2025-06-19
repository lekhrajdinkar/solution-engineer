package Java7Andbefore.strings;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class String2 {

    //java 8 :: StringJoiner
    static Logger log = LoggerFactory.getLogger(String2.class);
    static  String str = """
                dsbcjhbcjbajkbmz
                xhgvcsdlaskoapscjbj 
                """; //str.isLatin1(); compact String :: char-16 vs bytes-8
    static String[] strArray = str.split("");
    static Supplier<Stream<String>> strStreamSupplier = ()->Arrays.stream(strArray);
    public static void main(String... a)
    {
        // 1. remove all whitespace characters (spaces, tabs, line breaks, etc.) from the string
        str = str.replaceAll("\\s+", "");

        // 2. Count Char
        Map<String,Long> r = strStreamSupplier.get().collect(Collectors.groupingBy(x->x, Collectors.counting()));
        log.info("\n1. char count in string :: {}", r.toString());
    }

}

/*

https://www.baeldung.com/java-string

util methods::
1. Substring, charAt, reverse, replaceAll, equal/compareTo,
2. StringTokenizer is discouraged- use split,  join
3. toUpperCase, toLowerCase
 */