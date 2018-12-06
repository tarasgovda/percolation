/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] arr;
    private int N;
    private int size;


    public RandomizedQueue() {
        this.arr = (Item[]) new Object[1];
        this.N = 0;
        this.size = 0;
    }

    public boolean isEmpty()  {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

    }
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

    }

    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

    }

    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {

        private int current;

        RandomizedQueueIterator() {

        }

        @Override
        public boolean hasNext() {
            return current == 0;
        }

        @Override
        public Item next() {
            if (hasNext()) {

            } else throw new NoSuchElementException();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args) {

    }
}
