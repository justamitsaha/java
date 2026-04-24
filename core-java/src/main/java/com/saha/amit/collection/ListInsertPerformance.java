package com.saha.amit.collection;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;


/*
    This class is to compare the performance of add operation in ArrayList and LinkedList
    We will be adding to middle, first and last and see the performance difference
    While we add to middle or beginning, LinkedList should outperform ArrayList as it doesn't require shifting of elements, but when we add to end, ArrayList should outperform LinkedList as it has better cache locality and less overhead.
    But the performance difference may not be linear as it also depends on the size of the list and the number of operations we perform, as well as the JVM optimizations and garbage collection.
 */
public class ListInsertPerformance {

    public static ArrayList<String> arrayList = new ArrayList<>();
    public static LinkedList<String> linkedList = new LinkedList<>();
    public static int counter = 900_000;
    public static int mode;

    static {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the counter value");
        mode = scanner.nextInt();
        for (int i = 0; i < 1000; i++) {
            switch (mode) {
                case 1:
                    arrayList.add(i, "Hello");
                    linkedList.add(i, "Hello");
                    break;
                case 2:
                    arrayList.addFirst("Hello");
                    linkedList.addFirst("Hello");
                    break;
                case 3:
                    arrayList.addLast("Hello");
                    linkedList.addLast("Hello");
                    break;
                default:
                    arrayList.add(i, "Hello");
                    linkedList.add(i, "Hello");
            }
        }
    }


    public static void main(String[] args) {
        updateListWithCounter(counter);
    }

    /*
        When we add to middle Linked list outperforms Linked list, But be care full of how you do it
        Doing it like linkedList.add(Math.round(list2.size()/2),"Hello"); doesn't help as we're still using index
        to calculate where I am adding
     */
    public static void updateListWithCounter(int counter) {

        arrayList.add("Amit");
        arrayList.add("Amit");
        arrayList.add("Amit");
        int insertLocation = Math.round((float) arrayList.size() / 2);
        long start1 = System.currentTimeMillis();
        for (int i = 0; i < counter; i++) {
            switch (mode) {
                case 1 -> arrayList.add(insertLocation, "Hello");
                case 2 -> arrayList.addFirst("Hello");
                case 3 -> arrayList.addLast("Hello");
                default -> arrayList.add(i, "Hello");
            }
        }
        long end1 = System.currentTimeMillis();
        System.out.println("AL With counter " + counter + " Time taken " + (end1 - start1));


        linkedList.add("Amit");
        linkedList.add("Amit");
        linkedList.add("Amit");
        insertLocation = Math.round((float) linkedList.size() / 2);
        long start2 = System.currentTimeMillis();
        for (int i = 0; i < counter; i++) {
            switch (mode) {
                case 1 -> linkedList.add(insertLocation, "Hello");
                case 2 -> linkedList.addFirst("Hello");
                case 3 -> linkedList.addLast("Hello");
                default -> linkedList.add(i, "Hello");
            }
        }
        long end2 = System.currentTimeMillis();
        System.out.println("LL With counter " + counter + " Time taken " + (end2 - start2));
    }



    /*
    Result for add(insertLocation,"Hello")
        Array List time approx 51k, 54k, 42k, 42k
        Linked List time approx 800, 650, 650, 657
        conclusion Linked list faster as expected but increase in time is not linear how ?
    Result for addFirst("Hello");
        Array List time approx 42k, 41k, 41k,42k
        Linked List time approx 12, 9 ,10, 12
        conclusion--> since counter is increasing so AL will have to less shift so performance is better but still LL wins, but when counter is increased AL becomes better how ?
    Result for addLast("Hello")
        Array List time approx 25, 17, 20, 17
        Linked List time approx 15, 12, 10, 10
        conclusion--> Linked list faster as expected but increase in time is not linear how ?
    Result for add(i, "Hello");
        Array List time approx 77, 75, 85, 80
        Linked List time approx 13k, 13k, 14k , 14k
        conclusion--> For less count difference is negligible for higher counter value AL is better, since wr are adding to last element, so AL won't have to do many shit
     */
}
