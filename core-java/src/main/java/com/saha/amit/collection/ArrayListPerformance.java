package com.saha.amit.collection;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class ArrayListPerformance {
    public static void main(String[] args) {
        int counter = 900000;

        updateListWithCounter(counter);
    }

    /*
        When we add to middle Linked list outperforms Linked list, But be care full of how you do it
        Doing it like linkedList.add(Math.round(list2.size()/2),"Hello"); doesn't help as we're still using index
        to calculate where I am adding
     */
    public static void updateListWithCounter(int counter) {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Amit");
        arrayList.add("Amit");
        arrayList.add("Amit");
        int insertLocation = Math.round((float) arrayList.size() / 2);
        long start1 = System.currentTimeMillis();
        for (int i = 0; i < counter; i++) {
            //arrayList.add(insertLocation,"Hello");
            //arrayList.add(i,"hello");
            //arrayList.addFirst("hello");
            arrayList.addLast("Hello");
        }
        long end1 = System.currentTimeMillis();
        System.out.println("AL With counter " + counter + " Time taken " + (end1 - start1));


        LinkedList<String> linkedList = new LinkedList<>();
        linkedList.add("Amit");
        linkedList.add("Amit");
        linkedList.add("Amit");
        insertLocation = Math.round((float) linkedList.size() / 2);
        long start2 = System.currentTimeMillis();
        for (int i = 0; i < counter; i++) {
            //linkedList.add(insertLocation,"Hello");
            //linkedList.add(i,"hello");
            //linkedList.addFirst("hello");
            linkedList.addLast("Hello");
        }
        long end2 = System.currentTimeMillis();
        System.out.println("LL With counter " + counter + " Time taken " + (end2 - start2));
    }

    /*
    Result for add(insertLocation,"Hello")
        AL With counter 300000 Time taken 2887
        LL With counter 300000 Time taken 7
        AL With counter 300000 Time taken 2888
        LL With counter 300000 Time taken 7
        AL With counter 300000 Time taken 2963
        LL With counter 300000 Time taken 7
        AL With counter 900000 Time taken 36979
        LL With counter 900000 Time taken 125
        AL With counter 900000 Time taken 36971
        LL With counter 900000 Time taken 126
        AL With counter 900000 Time taken 40142
        LL With counter 900000 Time taken 124
        conclusion Linked list faster as expected but increase in time is not linear how ?
    Result for add(i,"hello")
        AL With counter 300000 Time taken 10
        LL With counter 300000 Time taken 7
        AL With counter 300000 Time taken 10
        LL With counter 300000 Time taken 5
        AL With counter 300000 Time taken 11
        LL With counter 300000 Time taken 4
        AL With counter 900000 Time taken 27
        LL With counter 900000 Time taken 108
        AL With counter 900000 Time taken 26
        LL With counter 900000 Time taken 115
        AL With counter 900000 Time taken 26
        LL With counter 900000 Time taken 128
        conclusion--> since counter is increasing so AL will have to less shift so performance is better but still LL wins, but when counter is increased AL becomes better how ?
    Result for addFirst("hello")
        AL With counter 300000 Time taken 2896
        LL With counter 300000 Time taken 0
        AL With counter 300000 Time taken 3065
        LL With counter 300000 Time taken 8
        AL With counter 300000 Time taken 2910
        LL With counter 300000 Time taken 0
        AL With counter 900000 Time taken 39330
        LL With counter 900000 Time taken 117
        AL With counter 900000 Time taken 36272
        LL With counter 900000 Time taken 139
        AL With counter 900000 Time taken 38587
        LL With counter 900000 Time taken 107
        conclusion--> Linked list faster as expected but increase in time is not linear how ?
    Result for addLast("Hello")
        AL With counter 300000 Time taken 5
        LL With counter 300000 Time taken 4
        AL With counter 300000 Time taken 0
        LL With counter 300000 Time taken 0
        AL With counter 300000 Time taken 4
        LL With counter 300000 Time taken 5
        AL With counter 900000 Time taken 15
        LL With counter 900000 Time taken 108
        AL With counter 900000 Time taken 16
        LL With counter 900000 Time taken 105
        AL With counter 900000 Time taken 13
        LL With counter 900000 Time taken 105
        conclusion--> For less count difference is negligible for higher counter value AL is better, since wr are adding to last element, so AL won't have to do many shit
     */
}
