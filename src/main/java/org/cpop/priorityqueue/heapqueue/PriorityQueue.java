package org.cpop.priorityqueue.heapqueue;

import org.cpop.priorityqueue.interfaces.IPriorityQueue;

import java.util.List;
import java.util.Vector;

/**
 * The priority heapqueue implementation is based on a binary heap
 * The space complexity is O(n) for all of the methods
 * All the public methods are syncronized so this ensures the thread-safe condition
 */
public class PriorityQueue<T extends Comparable> implements IPriorityQueue<T> {
    private List<T> items;
    private boolean maxPriorityQueue;

    public PriorityQueue(boolean isMaxPriorityQueue) {
        items = new Vector<T>(40,20);
        maxPriorityQueue = isMaxPriorityQueue;
    }

    public PriorityQueue() {
        items = new Vector<>();
        maxPriorityQueue = true;
    }

    /**
     * Get the list of items contained by the heapqueue
     * Complexty: T(1)
     * @return List<T> A list of items (not sorted)
     */
    @Override
    public synchronized List<T> getItems() {
        return items;
    }

    /**
     * Add a new item to the heap list
     * Complexity: shiftUp has a complexity of O(log2(n))
     *             but the overall complexity of add is higher because of the
     *             vector add method.
     *             The vector add method increses it's size when it get's full
     *             Initial behavior is to double it's size, which is a tradeoff
     *             between size and time complexity. With this behavior the
     *             complexity is O(n). In this implementation the capacity of
     *             the vector is incresed with 20 elements when it gets full
     *             to avoid unused space. This behavior makes the method to
     *             have a complexity of O(k^2).
     *
     * Returns: -
     */
    @Override
    public synchronized void add(T item) {
        items.add(item);//add item on the last place
        shiftUp(items.size() - 1);//shift it up to keep it in order
    }

    /**
     * Get the item with the most priority
     * Complexity: O(log2(n))because of the shiftDown method from the binary heap
     * @return T The item with the most priority or null if heapqueue is empty
     */
    @Override
    public synchronized T pop() {
        if (!items.isEmpty()) {
            T item = items.get(0);//get the first item;
            items.set(0, items.get(items.size() - 1));//get the last item and put it on the first place
            items.remove(items.size() - 1);
            shiftDown(0);//shift it down to its position
            return item;//return the item with the most priority
        }
        return null;
    }

    /**
     * Update an item from the heapqueue
     * Complexity: O(n) mainly because the heap doesn't keep the items ordered
     * so we need to do a sequencial search. This could be optimised if we find a way to keep
     * a list of indexes ordered, so we can do a binary search.
     *
     * @param item The item to be updated
     */
    @Override
    public synchronized void update(T item) {
        //find position of the item
        int index = -1;
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).compareTo(item) == 0) {//use the compare method to find the item index
                index = i;
                break;//end the loop when found the item;
            }
        }

        if (index != -1) {//if the item is found remove it and then add it again so the order is maintained
            items.set(index, items.get(items.size() - 1));//move the last node to the index
            items.remove(items.size() - 1);//remove it from the end
            shiftDown(index);//shift it down to its position;
            add(item);//add the item back;
        }
    }

    /**
     * Shift up the item from give position to maintain the heap form
     *
     * @param position position of item to be shifted up
     */
    private void shiftUp(Integer position) {
        int parentPos = (position - 1) / 2;
        int rez = items.get(position).compareTo(items.get(parentPos));
        if ((rez != 0) && ((rez < 0) == this.maxPriorityQueue)) {//if current node is bigger than its parent
            T temp = items.get(parentPos);
            items.set(parentPos, items.get(position));
            items.set(position, temp);
            shiftUp(parentPos);//try to move it further up
        }
    }

    /**
     * Shift down the item from position "position" to maintain the heap form
     *
     * @param position position of item to be shifted up
     */
    private void shiftDown(Integer position) {
        int leftChildIndex = 2 * position + 1;
        int rightChildIndex = 2 * position + 2;
        int switchIndex;
        if (leftChildIndex >= items.size()) {
            return; //it has no childs
        } else {
            if (rightChildIndex >= items.size()) {//it has only one child
                switchIndex = leftChildIndex;
            } else {//it has two childs
                int rez = items.get(leftChildIndex).compareTo(items.get(rightChildIndex));
                if ((rez != 0) && ((rez < 0) == this.maxPriorityQueue)) {//find the biggest child
                    switchIndex = leftChildIndex;
                } else {
                    switchIndex = rightChildIndex;
                }
            }
        }

        int rez = items.get(position).compareTo(items.get(switchIndex));
        if (!((rez != 0) && ((rez < 0) == this.maxPriorityQueue))) {//if not in the correct order
            T temp = items.get(switchIndex);
            items.set(switchIndex, items.get(position));
            items.set(position, temp);
        }

        shiftDown(switchIndex);//try to move it further down;
    }
}
