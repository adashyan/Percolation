import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

/**
 * Created by ar on 9/18/16.
 */
public class PercolationStats {


    private double[] percentage;
    private double mean;
    private double stdev;
    private int number;

    public PercolationStats(int n, int trials) {

        number = n;
        percentage = new double[trials];

        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }

        for (int i = 0; i < trials; i++) {

            Percolation percolation = new Percolation(n);
            boolean isPercolates = false;
            int count = 0;

            while (!isPercolates) {

                int row    = StdRandom.uniform(1, n + 1);
                int column = StdRandom.uniform(1, n + 1);

                if (!percolation.isOpen(row, column)) {

                    percolation.open(row, column);
                    isPercolates = percolation.percolates();
                    count++;
                }
            }

            percentage[i] = (double) count / (double) (n * n);
//            StdOut.println("number " + i + " trail done");
        }

        mean  = StdStats.mean(percentage);
        stdev = StdStats.stddev(percentage);
    }

    public double mean() {
        return mean;
    }
    public double stddev() {
        return stdev;
    }

    public double confidenceLo() {
        return mean() - 1.96 * stddev() / Math.sqrt((double) number);
    }

    public double confidenceHi() {
        return mean() + 1.96 * stddev() / Math.sqrt((double) number);
    }

    public static void main(String[] args) {

        if (args.length < 2) {
            throw new IllegalArgumentException();
        }

        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);

//        int n = 200;
//        int trials = 200;

        PercolationStats ps = new PercolationStats(n, trials);

        StdOut.println("mean                    = " + Double.toString(ps.mean()));
        StdOut.println("stddev                  = " + Double.toString(ps.stddev()));
        StdOut.println("95% confidence interval = " + Double.toString(ps.confidenceLo()) +
                ", " + Double.toString(ps.confidenceHi()));
    }
}
