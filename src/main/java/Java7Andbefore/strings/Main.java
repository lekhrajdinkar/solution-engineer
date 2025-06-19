package Java7Andbefore.strings;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Optional;
import java.util.Random;
import java.util.stream.IntStream;

public class Main
{
    static void p(Object ...s){ Arrays.stream(s).forEach(System.out::println) ;}

    public static void main(String args[]) throws Exception
    {
        // String extends CharSequence Class
        // IndexOf, lastIndexOf, replace, replaceAll, subSeq/SubStr

        // A. Conversion
        // A.1. to Byte[]
        p("Anna".getBytes(StandardCharsets.US_ASCII));
        p("Anna".getBytes(StandardCharsets.UTF_8));

        // A.2. character array to a String
        char[] charArr = {'a', 'n', 'n', 'a'};
        p(String.copyValueOf(charArr));

        p("LekhDin".codePointAt(0), "LekhDin".codePointAt(3)); // 65 , 97
        p("LekhDin".codePointCount(1,7)); //6
        p("Lekh   Din".codePointCount(1,7)); //6

        String temp = "";
        String newLine = System.getProperty("line.separator");

    // A. Null/empty check
        p(temp.isEmpty(), temp.isBlank());

    //B. Multi-line String (
        // String sBlock = """hello
        // World""" ; // java 15

        temp = String.join(newLine,"hello", "Prasad", "How", "are", " you ?"); p(temp); //join
        temp = new StringBuilder().append("hello").append(newLine).append("Liu").toString(); p(temp); //Builder
        temp = new String(Files.readAllBytes(Paths.get("src/main/resources/banner.txt"))); p(temp); //files keep in multiple lines.


    // C. Random String
        // String are Char[]
        // char is 2 byte, while is like (int with 2byte), rather than 4. char temp=92 === temp='a'
        // apache comm lang provides, RandonStringUtils.XXXXX() and StringUtils.XXXXX()
        p(getRandomString1(6));
        p(getRandomString2(6));
        p(getRandomString3(6));

        // D. remove last char
        p(removeLastChar1("LekhDin"));
        p(removeLastChar2("LekhDin"));
        p(removeLastChar3_2("LekhDin"));
        p(removeLastChar3_2("LekhDin"));

        p("LekhDin".chars().filter(x->x == 'n').count()); // count check

        p(new StringBuilder("LekhDin").reverse().toString().equals("LekhDin")); //palindrome
        p(new StringBuilder("hannah").reverse().toString().equals("hannah")); //palindrome

        temp = "hannahh";
        String finalTemp = temp; // Effective Final
        boolean isPalindrome = IntStream
                .range(0, temp.length()/2)
                .noneMatch(i-> finalTemp.charAt(i) != finalTemp.charAt(finalTemp.length()-i-1)); p("isPalindrome:: ",isPalindrome);

    }


    // A. Null/empty check
    boolean isEmptyString(String string) {
        return string == null || string.length() == 0;
    }
    boolean isEmptyString2(String string) {
        return string == null || string.isEmpty();
    }
    boolean isEmptyString3(String string) {
        return string.isBlank() ;
    }// java11, better

    // C. Random String
    // generate random byte and convet byte[] to arrau with UTF-8.
      static String getRandomString1(int length){
          String temp = "";
          Random random = new Random();
          byte[] bytes = new byte[length];
          random.nextBytes(bytes); //           <<Here
          // temp = new String(bytes);
          temp = new String(bytes, Charset.forName("UTF-8"));
          return temp;
      }

    static String getRandomString2(int length){
        String temp = "";
        int a = 92, z = 122;
        Random random = new Random();
        byte[] bytes = new byte[length];
        for(int i = 0; i<length ; i++){
            bytes[i] = (byte) ((int)random.nextFloat() * (z-a)+1); //           <<Here
            //p(i,bytes[i]);
        }
        temp = new String(bytes, Charset.forName("UTF-8"));
        return temp;
    }

    static String getRandomString3(int length){
        String temp = "";
        int a = 92, z = 122;
        return new Random()
                .ints(a,z) // Stream :: randon int between 92 and 122
                .limit(length) // finite stream of 6
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    // D. Remove last Char
    // 4 ways: java7, stream, regEx, 3rd party jar
    static String removeLastChar1(String s){
       return s.substring(0, s.length()-1);
    }
    static String removeLastChar2(String s){
        return s
                .chars()
                .limit(s.length()-1)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
//    static String removeLastChar3_1(String s){
//        Pattern p = Pattern.compile(".$");
//
//    }
    //replace all with regEx
    static String removeLastChar3_2(String s){
        return (s == null) ? null :s.replaceAll(".$", "");
    }
    // Replace all with regEx, And handle null Check  in better way using optonal.
    static String removeLastChar3_3(String s){
        Optional<String> res = Optional.ofNullable(s);
        return res
                .stream()
                .map( s1->s1.replaceAll(".$", ""))
                .toString();
    }

}
