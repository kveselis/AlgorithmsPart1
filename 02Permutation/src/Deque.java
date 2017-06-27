import edu.princeton.cs.algs4.StdOut;
import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {

    private class Node
    {
        private final Item item;
        private Node prev, next;

        private Node(Item i)
        {
            if (i == null) { throw new java.lang.NullPointerException(); }
            this.item = i;
        }
    }

    private Node first = null;
    private Node last = null;
    private int size = 0;

    public Deque()                           // construct an empty deque
    {

    }
    public boolean isEmpty()                 // is the deque empty?
    {
        return (size() == 0);
    }
    public int size()                        // return the number of items on the deque
    {
        return size;
    }
    public void addFirst(Item item)          // add the item to the front
    {
        if (item == null) { throw new java.lang.NullPointerException(); }
        Node oldFirst = first;
        first = new Node(item);
        first.next = oldFirst;
        if (oldFirst != null) {
            oldFirst.prev = first;
        }
        if (last == null) {
            last = first;
        }
        size++;
    }
    public void addLast(Item item)           // add the item to the end
    {
        if (item == null) { throw new java.lang.NullPointerException(); }
        Node oldLast = last;
        Node newLast = new Node(item);
        if (oldLast != null) {
            oldLast.next = newLast;
            newLast.prev = oldLast;
        }
        last = newLast;
        if (first == null) {
            first = last;
        }
        size++;
    }
    public Item removeFirst()                // remove and return the item from the front
    {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        if (first == null) { throw new java.lang.NullPointerException(); }

        Node remFirst = first;

        if (first != null) {
            first = first.next;
            if (first != null) {
                first.prev = null;
            }
            else
            {
                last = null;
            }
        }
        else
        {
            last = null;
        }
        size--;
        return remFirst.item;
    }
    public Item removeLast()                 // remove and return the item from the end
    {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        if (last == null) { throw new java.lang.NullPointerException(); }

        Node remLast = last;

        if (last != null)
        {
            last = last.prev;
            if (last != null) {
                last.next = null;
            }
            else {
                first = null;
            }
        }
        else
        {
            first = null;
        }
        size--;
        return remLast.item;
    }
    public Iterator<Item> iterator()         // return an iterator over items in order from front to end
    {
        return new Iterator<Item>() {

            private Node current = first;

            public boolean hasNext() {
                return current != null;
            }

            public Item next() {
                if (!hasNext()) { throw new java.util.NoSuchElementException(); }
                Item item = current.item;
                current = current.next;
                return item;
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }
    public static void main(String[] args)
    {

        Deque<Integer> d = new Deque<>();
        d.addLast(0);
        d.addLast(0);
        d.addFirst(1);
        d.addLast(1);
        d.addFirst(2);
        d.addLast(2);
        d.addFirst(3);
        d.addLast(3);

        StdOut.println("Size: " + d.size());

        d.forEach(i -> StdOut.print(i + " "));

        StdOut.println();
        while (!d.isEmpty()) {
            StdOut.println("s:" + d.size() + " - " + d.removeFirst());
            StdOut.println("s:" + d.size() + " - " + d.removeLast());
        }

    }
}