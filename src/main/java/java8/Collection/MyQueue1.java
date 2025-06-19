package java8.Collection;

import java.util.*;


public class MyQueue1 {

    static void p(Object... s){Arrays.stream(s).forEach(System.out::println);}
    public static void main(String... a)
    {
        // 1. LinkedList is imple of both ***
        Queue<Integer> queue = new LinkedList<>(); // insertion order
        Queue<Student> queue2 = new PriorityQueue<>(); // sorted

        Deque<Integer> deque = new LinkedList<>();
        Deque<Integer> deque2 = new ArrayDeque<>(); // not thread safe, 0(1), resizable array

        deque.add(1);deque.add(2);deque.add(3);

        deque.addFirst(100);p("addfirst : ",deque);
        deque.addLast(200);p("addLast: ",deque);
        deque.removeFirst();p("removeFirst : ",deque);
        deque.removeLast();p("removeLast: ",deque);

        // while (!deque.isEmpty()) { p("poll ",queue.poll()); }

        // 2. PriorityQueue - sorted
        // Comparable of Student decide priority/sort
        // or, below Comparator of Student decide priority/sort
        PriorityQueue<Student> pq = new PriorityQueue<>( (x,y)->x.age == y.age ? 0 : (x.age > y.age ? 1 : -1));
        Student s1 = Student.builder().firstname("lekhraj1").lastname("Dinkar1").age((byte) 28).build();
        Student s2 = Student.builder().firstname("lekhraj2").lastname("Dinkar2").age((byte) 35).build();
        Student s3 = Student.builder().firstname("lekhraj3").lastname("Dinkar3").age((byte) 25).build();

        pq.add(s1); p(pq);
        pq.add(s2); p(pq);
        pq.add(s3); p(pq);

        Student youngest = Collections.min(pq, Comparator.comparingInt(Student::getAge)); p("youngest", youngest);
        p("min : ",Collections.min(deque));
        // 3. Thread safe
        // ConcurrentLinkedQueue
        // PriorityBlockingQueue:



    }
}

/*
Queue:
===========
Represents a collection designed for holding elements prior to processing.
Typically follows the FIFO (First-In-First-Out) order, meaning that the elements are inserted at the end of the queue and are removed from the front.
Extends the Collection interface and adds insertion, extraction, and inspection operations.
Some common implementations are LinkedList and PriorityQueue.

Deque (Double Ended Queue):
============================
Represents a queue where you can insert and remove elements from both ends.
Provides more operations than Queue, such as adding/removing elements at both ends, peeking at the first/last element, etc.
Extends the Queue interface and adds additional methods for insertion and removal at both ends.
Some common implementations are ArrayDeque and LinkedList.
 */
