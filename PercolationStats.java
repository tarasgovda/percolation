/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private double[] thresholds;

    public PercolationStats(int n, int trials) {
        this.thresholds = new double[trials];


        int randRow;
        int randCol;

        for (int i = 0; i < trials; i++) {
            Percolation percolation = new Percolation(n);
            while (!percolation.percolates()) {
                randRow = StdRandom.uniform(n) + 1;
                randCol = StdRandom.uniform(n) + 1;

                if (percolation.isOpen(randRow, randCol)) continue;

                percolation.open(randRow, randCol);
            }
            double openSites = (double) percolation.numberOfOpenSites();
            double threshlod = openSites / (n*n);
            thresholds[i] = threshlod;
        }
    }
    public double mean() {
        return StdStats.mean(thresholds);
    }
    public double stddev() {
        return StdStats.stddev(thresholds);
    }
    public double confidenceLo() {
        return mean() - confidenceDev();
    }
    public double confidenceHi() {
        return mean() + confidenceDev();
    }

    private double confidenceDev() {
        return (1.96*stddev()) / Math.sqrt(thresholds.length);
    }
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);

        PercolationStats percolationStats = new PercolationStats(n, t);

        System.out.println("mean\t\t\t\t\t= " + percolationStats.mean());
        System.out.println("stddev\t\t\t\t\t= " + percolationStats.stddev());
        System.out.println("95% confidence interval = [" +
                                   percolationStats.confidenceLo() +
                                    ", " +
                                    percolationStats.confidenceHi() +
                                    "]");


    }
}
