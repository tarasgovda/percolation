/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
    private Node first = null;
    private Node last = null;
    private int size = 0;

    @Override
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    public boolean isEmpty() {
        return first == null;
    }

    public void addFirst(Item item)    {
        if (item == null) {
            throw new IllegalArgumentException();
        }

    }

    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
    }

    private class DequeIterator implements Iterator<Item> {

        private Node current = first;

        @Override
        public boolean hasNext() {
            return current.next == null;
        }

        @Override
        public Item next() {
            return null;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }



    private class Node {
        private Item data;
        private Node prev;
        private Node next;
    }

    public static void main(String... args) {

    }
}
