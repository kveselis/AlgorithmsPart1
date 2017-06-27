import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private WeightedQuickUnionUF weightedQuickUnionUF;
    private boolean[] openSites;
    private int n;

    public Percolation(int n) {                // create n-by-n grid, with all sites blocked

        if (n <= 0) throw new IllegalArgumentException();

        this.n = n;
        openSites = new boolean[n * n + 2];

        for (int i = 1; i < openSites.length - 1; i++) openSites[i] = false;

        weightedQuickUnionUF = new WeightedQuickUnionUF(n * n + 2);

    }

    private int[] arrayOfNeighbours(int row, int col) {
        if (!inPlace(row, col)) {
            throw new IndexOutOfBoundsException();
        }

        int[] neighbours = new int[4];
        int cnt = 0;

        if (col > 1) {
            neighbours[cnt] = gridToIndex(row, col - 1);
            cnt++;
        }
        if (col < n) {
            neighbours[cnt] = gridToIndex(row, col + 1);
            cnt++;
        }
        if (row > 1) {
            neighbours[cnt] = gridToIndex(row - 1, col);
            cnt++;
        }
        if (row < n) neighbours[cnt] = gridToIndex(row + 1, col);

        return neighbours;
    }

    private int gridToIndex(int row, int col) {
        if (!inPlace(row, col)) {
            throw new IndexOutOfBoundsException();
        }
        return n * (row - 1) + col;
    }

    private boolean inPlace(int row, int col) {
        return ((row > 0) && (col > 0) && (row <= n) && (col <= n));
    }

    public void open(int row, int col)    // open site (row, col) if it is not open already
    {

        if (!inPlace(row, col)) {
            throw new IndexOutOfBoundsException();
        }

        int idx = gridToIndex(row, col);

        if (!openSites[idx]) {
            openSites[idx] = true;

            int[] neighbours = arrayOfNeighbours(row, col);

            for (int neighbour : neighbours) {
                if (neighbour > 0 && openSites[neighbour]) {
                    weightedQuickUnionUF.union(idx, neighbour);
                }
            }

            if (row == 1) {
                weightedQuickUnionUF.union(0, idx);
            }
            if (row == n) {
                weightedQuickUnionUF.union(idx, (n * n)  + 1);
            }

        }

    }

    public boolean isOpen(int row, int col)  // is site (row, col) open?
    {
        if (!inPlace(row, col)) {
            throw new IndexOutOfBoundsException();
        }
        return openSites[gridToIndex(row, col)];
    }

    public boolean isFull(int row, int col)  // is site (row, col) full?
    {
        if (!inPlace(row, col)) {
            throw new IndexOutOfBoundsException();
        }

        return (isOpen(row, col) && weightedQuickUnionUF.connected(gridToIndex(row, col), 0));
    }

    public int numberOfOpenSites()       // number of open sites
    {
        int cnt = 0;

        for (int i = 1; i < openSites.length - 1; i++) {
            if (openSites[i]) cnt++;
        }

        return cnt;
    }

    public boolean percolates()              // does the system percolate?
    {
        return (weightedQuickUnionUF.connected(0, (n * n) + 1));
    }

    public static void main(String[] args)   // test client (optional)
    {

        boolean testsPassed = true;

        for (int cnt = 0; cnt < TestData.INPUTS.length; cnt++) {

            In in = new In(TestData.INPUTS[cnt]);      // input file
            if (in.exists()) {
                StdOut.println(TestData.INPUTS[cnt]);
                int n = in.readInt();         // n-by-n percolation system

                Percolation g = new Percolation(n);
                while (!in.isEmpty()) {
                    int i = in.readInt();
                    int j = in.readInt();
                    g.open(i, j);
                }
                StdOut.println("Percolates? " + g.percolates());
                if (g.percolates() != TestData.PERCULATES[cnt] ||
                        g.numberOfOpenSites() != TestData.OPENED_SITES[cnt]) {
                    StdOut.printf("Test data: file='%s', opened=%d, percolates=%b\n",
                            TestData.INPUTS[cnt], TestData.OPENED_SITES[cnt], TestData.PERCULATES[cnt]);
                    StdOut.printf("Program result: opened=%d, percolates=%b\n\n",
                            g.numberOfOpenSites(), g.percolates());
                    testsPassed = false;
                }

                in.close();
            }
        }

        if (testsPassed) {
            StdOut.println("All tests PASSED.");
        }
        else {
            StdOut.println("Some tests FAILED!");
        }
    }


    private static final class TestData {

        private static final String[] INPUTS = {"greeting57.txt", "heart25.txt", "input10-no.txt", "input10.txt",
                                                "input1-no.txt", "input1.txt", "input20.txt", "input2-no.txt",
                                                "input2.txt", "input3.txt", "input4.txt", "input50.txt", "input5.txt",
                                                "input6.txt", "input7.txt", "input8-no.txt", "input8.txt",
                                                "jerry47.txt", "sedgewick60.txt", "wayne98.txt" };
        private static final int[] OPENED_SITES = {2522, 352, 55, 56, 0, 1, 250, 2, 3, 6, 8, 1412,
                                                   25, 18, 16, 33, 34, 1476, 2408, 5079};
        private static final boolean[] PERCULATES = {false, false, false, true, false, true, true,
                                                     false, true, true, true, true, true, true,
                                                     true, false, true, true, true, true};

    }
}



