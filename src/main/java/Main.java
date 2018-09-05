package org.cpop.priorityqueue.heapqueue;

import org.cpop.priorityqueue.domain.TestDummy;

import java.util.List;

public class Main {
    static <T> void displayItems(List<T> items){
        for(T each : items){
            System.out.println(each.toString());
        }
    }

    public static void main(String[] args){
        PriorityQueue<TestDummy> queue = new PriorityQueue<>();
        queue.add(new TestDummy(1,1));
        queue.add(new TestDummy(2,2));
        queue.add(new TestDummy(5,3));

        List<TestDummy> list = queue.getItems();
        System.out.println("Initial: ");
        displayItems(list);

        TestDummy td = list.get(1);

        td.setPriority(7);
        queue.update(td);
        list = queue.getItems();
        System.out.println("After update high:");
        displayItems(list);


        td = list.get(0);
        td.setPriority(1);
        queue.update(td);
        System.out.println("After update low");
        displayItems(list);

    }

}
