/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] arr;
    private int n;


    public RandomizedQueue() {
        this.arr = (Item[]) new Object[1];
        this.n = 0;
    }

    public boolean isEmpty()  {
        return n == 0;
    }

    public int size() {
        return n;
    }

    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        if (n == arr.length) { resize(2 * n); }
        this.arr[n++] = item;
    }
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        int rand = StdRandom.uniform(n);
        swapWithLast(rand);

        Item item = arr[--n];
        arr[n] = null;
        if (n > 0 && n == arr.length / 4) { resize(arr.length / 2); }
        return item;
    }

    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        int rand = StdRandom.uniform(n);
        swapWithLast(rand);
        return arr[n - 1];
    }

    @Override
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private void resize(int newSize) {
        Item[] copy = (Item[]) new Object[newSize];
        for (int i = 0; i < n; i++) {
            copy[i] = arr[i];
        }
        arr = copy;
    }

    private void swapWithLast(int index) {
        Item temp = arr[index];
        arr[index] = arr[n - 1];
        arr[n - 1] = temp;
    }

    private class RandomizedQueueIterator implements Iterator<Item> {

        private Item[] workCopy;
        private int index;

        RandomizedQueueIterator() {
            this.index = n;
            workCopy = (Item[]) new Object[n];
            for (int i = 0; i < n; i++) {
                workCopy[i] = arr[i];
            }
            StdRandom.shuffle(workCopy);
        }

        @Override
        public boolean hasNext() {
            return index != 0;
        }

        @Override
        public Item next() {
            if (hasNext()) {
                Item item = workCopy[--index];
                workCopy[index] = null;
                return item;
            } else throw new NoSuchElementException();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args) {
        RandomizedQueue<String> randomizedQueue = new RandomizedQueue<>();
        System.out.println(randomizedQueue.isEmpty());
        randomizedQueue.enqueue("Test");
        System.out.println(randomizedQueue.dequeue());
        System.out.println(randomizedQueue.isEmpty());
        randomizedQueue.enqueue("Again");
        System.out.println(randomizedQueue.sample());
        randomizedQueue.enqueue("lalala");
        System.out.println(randomizedQueue.dequeue());
        System.out.println(randomizedQueue.size());
        System.out.println(randomizedQueue.sample());
    }
}
