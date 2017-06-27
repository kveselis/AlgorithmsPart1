import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private int n;
    private int trials;
    private double[] threshold;

    public PercolationStats(int n, int trials) {    // perform trials independent experiments on an n-by-n grid

        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }

        this.n = n;
        this.trials = trials;
        this.threshold = new double[trials];

        for (int i = 0; i < trials; i++) {
            threshold[i] = doPercolate();
        }

    }

    private double doPercolate() {
        Percolation perc = new Percolation(n);
        int r;
        int c;
        do {
            r = StdRandom.uniform(1, n + 1);
            c = StdRandom.uniform(1, n + 1);
            perc.open(r, c);
        } while (!perc.percolates());

        return ((double) perc.numberOfOpenSites()) / (n * n);
    }

    public double mean()                          // sample mean of percolation threshold
    {
        return StdStats.mean(threshold);
    }

    public double stddev()                        // sample standard deviation of percolation threshold
    {
        return StdStats.stddev(threshold);
    }

    public double confidenceLo()                  // low  endpoint of 95% confidence interval
    {
        return (mean() - ((1.96 * stddev()) / Math.sqrt(trials)));
    }

    public double confidenceHi()                  // high endpoint of 95% confidence interval
    {
        return (mean() + ((1.96 * stddev()) / Math.sqrt(trials)));
    }

    public static void main(String[] args) {

        int nArg = Integer.parseInt(args[0]);
        int tArg = Integer.parseInt(args[1]);

        PercolationStats ps = new PercolationStats(nArg, tArg);

        StdOut.printf("mean                    = %.7f\n", ps.mean());
        StdOut.printf("stddev                  = %.17f\n", ps.stddev());
        StdOut.printf("95%% confidence interval = %.15f, %.15f\n", ps.confidenceHi(), ps.confidenceHi());

    }

/*    private void testTimes() {
        StdOut.printf("Running with 'WeightedQuickUnionUF'\n");
        StdOut.printf("T, time'\n");
        int n = 100;
        int tMax = 3000;
        for (int t = 1; t <= tMax; t *= 2) {
            Stopwatch sw = new Stopwatch();
            PercolationStats ps = new PercolationStats(n, t);
            StdOut.printf("%d, %f\n", t, sw.elapsedTime());
        }
        int t = 1;
        int nMax = 7000;
        StdOut.printf("n, time'\n");
        for (int n = 1; n <= nMax; n *= 2) {
            Stopwatch sw = new Stopwatch();
            PercolationStats ps = new PercolationStats(n, t);
            StdOut.printf("%d, %f\n", n, sw.elapsedTime());
        }

    } */

}
