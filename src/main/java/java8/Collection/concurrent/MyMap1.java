package java8.Collection.concurrent;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MyMap1 {
    Map<String,String> map = new HashMap<>();
    Map cmap1 = Collections.synchronizedMap(map); //option-1
    ConcurrentHashMap cmap2 = new ConcurrentHashMap<>(map); //option-2 better
    public static void main(String... arr){

    }

}
