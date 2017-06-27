import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdOut;
import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private class Node
    {
        private final Item item;
        private Node next;
        private Node prev;

        private Node(Item i)
        {
            if (i == null) { throw new java.lang.NullPointerException(); }
            this.item = i;
        }
    }

    private Node first = null;
    private int size = 0;

    public RandomizedQueue()                 // construct an empty randomized queue
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

    public void enqueue(Item item)           // add the item
    {
        if (item == null) { throw new java.lang.NullPointerException(); }
        Node oldFirst = first;
        first = new Node(item);
        if (oldFirst != null) oldFirst.prev = first;
        first.next = oldFirst;
        size++;
    }

    private Node nextNth() {

        int rand = StdRandom.uniform(0, size());

        Node nth = first;
        for (int i = 0; i < rand; i++)
        {
            nth = nth.next;
        }

        return nth;
    }

    public Item dequeue()                    // remove and return a random item
    {
        if (isEmpty()) { throw new java.util.NoSuchElementException(); }

        Node nth = nextNth();

        Node prevNode = nth.prev;
        Node nextNode = nth.next;

        if (size() > 1) {
            if (prevNode != null) {
                prevNode.next = nextNode;
            } else {
                first = nextNode;
            }
            if (nextNode != null) {
                nextNode.prev = prevNode;
            } else {
                // first = prevNode;
            }
        } else first = null;

        size--;
        return nth.item;

    }
    public Item sample()                     // return (but do not remove) a random item
    {
        if (isEmpty()) { throw new java.util.NoSuchElementException(); }
        return nextNth().item;
    }

    public Iterator<Item> iterator()         // return an iterator over items in order from front to end
    {
        return new Iterator<Item>() {
            private RandomizedQueue<Item> rq = new RandomizedQueue<>();
            {
                int icnt = size();
                for (int i = 0; i < icnt; i++) rq.enqueue(dequeue());
                first = rq.first;
            }

            public boolean hasNext() { return rq.first != null; }

            public Item next() {
                if (!hasNext()) { throw new java.util.NoSuchElementException(); }
                Item item = rq.first.item;
                rq.first = rq.first.next;
                return item;
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }



    public static void main(String[] args)   // unit testing (optional)
    {

        RandomizedQueue<Integer> d = new RandomizedQueue<>();

        d.enqueue(1);
        d.dequeue();
        d.dequeue();

        int n = 3;
        for (int i = 0; i < n; i++) {
            d.enqueue(n-i);
        }

        StdOut.println("size: " + d.size());

        for (Integer i: d) {
            StdOut.print(i);
        }

        StdOut.println();

        for (Integer i: d) {
            StdOut.print(i);
        }

    }
}