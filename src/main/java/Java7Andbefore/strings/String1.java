package Java7Andbefore.strings;

import java.util.Arrays;

public class String1
{
    static void p(Object ...s){ Arrays.stream(s).forEach(System.out::println) ;}
    public  static void main(String a[]){

        String temp = "";

        // A. Formatting
        // IllegalFormatException
        temp = String.format("Welcome %s %s!", "Anna", "Liu"); p(temp);

        // Diff between parseInt abd valueOf : return Type.
        int numberFromString = Integer.parseInt("20");
        Integer numberFromString2 = Integer.valueOf("20"); // also uses internal cache for -128 to 128, better
        //Integer numberFromString3 = new Integer("20"); // deprecated
        Integer numberFromString4 =  Integer.decode("2#"); // deprecated
        p(numberFromString, numberFromString2,numberFromString4);
    }
}
