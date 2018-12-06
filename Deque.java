/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node first;
    private Node last;
    private int size;

    public Deque() {
        this.first = null;
        this.last = null;
        this.size = 0;
    }

    @Override
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    public boolean isEmpty() {
        return first == null;
    }

    public int size() {
        return this.size;
    }

    public void addFirst(Item item)    {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        Node oldFirst = this.first;
        this.first = new Node(item);
        first.next = oldFirst;
        if (oldFirst != null) {
            oldFirst.prev = first;
        } else {
            this.last = this.first;
        }

        size++;
    }

    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        Node oldLast = this.last;
        this.last = new Node(item);
        last.prev = oldLast;
        if (oldLast != null) {
            oldLast.next = this.last;
        } else {
            this.first = this.last;
        }

        size++;
    }
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        Node oldFirst = this.first;
        this.first = first.next;
        if (this.first == null) {
            this.last = null;
        }

        size--;
        return oldFirst.data;
    }

    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        Node oldLast = this.last;
        this.last = last.prev;
        if (this.last == null) {
            this.first = null;
        }

        size--;
        return oldLast.data;
    }

    private class DequeIterator implements Iterator<Item> {

        private Node current = first;

        @Override
        public boolean hasNext() {
            return current.next != null;
        }

        @Override
        public Item next() {
            if (hasNext()) {
                Item item = current.data;
                current = current.next;
                return item;
            } else throw new NoSuchElementException();
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

        Node(Item item) {
            this.data = item;
        }

    }

    public static void main(String... args) {
        Deque<String> deque = new Deque<>();

        deque.addFirst("Test");
        deque.removeLast();
        System.out.println(deque.isEmpty());
        deque.addLast("Again");
        deque.addFirst("lalal");
        System.out.println(deque.removeLast());
        System.out.println(deque.isEmpty());
        System.out.println(deque.size());
    }
}
