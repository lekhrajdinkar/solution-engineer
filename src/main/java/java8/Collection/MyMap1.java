package java8.Collection;

import static util.Print.p;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MyMap1 {
    public static void main(String... arr){
        initMap();
        mapOperation();
    }

    public static void initMap() {
        Map<Integer,Integer> treemap = new TreeMap(( x,y)->Integer.valueOf((String) x)- (int)y);// pass compartator

        //Thread safe
        Collections.synchronizedMap(Map.of("key1","value1", "key2", "value2"));

        //Immutable map
        Map m1 = Collections.singletonMap("username1", "password1"); //contain exactly one element.
        Map m2 = Collections.unmodifiableMap(Map.of("key1","value1", "key2", "value2")); //impure
        Map m3 = Map.of("key1","value1", "key2", "value2"); // J9
        Map m4 = Map.copyOf(m3); //J9

        // An Unmodifiable Map is just a wrapper over a modifiable map and it doesn’t allow modifications to it "directly"
        // But the underlying mutable map can still be changed and the modifications are reflected.
        // use 3rd part lib - guava --> ImmutableMap class

        Map<String, String> emptyMap = Map.of();
        Map<String, String> map = Map.of("key1","value1", "key2", "value2"); //max-10 pair

        map = Stream.of(new String[][] {
                { "Hello", "World" },
                { "John", "Doe" },
        }).collect(Collectors.toMap(data -> data[0], data -> data[1]));

        map = Stream.of(
                        new AbstractMap.SimpleEntry<String,String>("idea", "1"),
                        new AbstractMap.SimpleEntry<String,String>("mobile", "2"))
                .collect(Collectors.toMap(d->d.getKey(), d->d.getValue()));

        map = Map.ofEntries(
                new AbstractMap.SimpleEntry<String,String>("idea", "1"),
                new AbstractMap.SimpleEntry<String,String>("mobile", "2")   );

        map = new HashMap<String, String>(Map.of("key1","value1", "key2", "value2"));
    }

     static void mapOperation(){
        Map map = new HashMap();
        List<String> l = new ArrayList();

        map.put("1","lekhraj"); map.put("2","Manisha");

        map.compute("1", (k,v)->k+"-"+v);
        map.compute("10", (k,v)->k+"-"+v);
        p(map);

        map.computeIfAbsent("11", (k)->{
            l.add("accessing outter List");
            // map.clear(); // ConcurrentModificationException
            System.out.println(l);
            return k+"-Absent"; });

        map.computeIfAbsent("1", (k)->k+"-Absent"); //skip
        p(map);

        map.computeIfPresent("1", (k,v)->k+"-Present"+v);
        map.computeIfPresent("12", (k,v)->k+"-Present"+v); //skip
        p(map);

         map.merge("1","Prasad", (oldValue,newValue)->"old:"+oldValue+" >>MERGE<< new:"+newValue);
         // key,value-new,remappingFunction -
         // BiFunction :: (old-value/null , new value) --> {}
         p(map);
    }

}
