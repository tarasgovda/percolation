/* *****************************************************************************
 *  Name: PercolationStats
 *  Date: 27-11-2018
 *  Description: Class for gathering basic stats for Monte Carlo simulation
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private final double[] thresholds;
    private double mean;
    private double stddev;

    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) throw new IllegalArgumentException("Gird size for Monte Carlo "
                                                                          + "simulation and number "
                                                                          + "of trials should be"
                                                                        + " only positive numbers");
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
        this.mean = StdStats.mean(thresholds);
        this.stddev = StdStats.stddev(thresholds);
    }
    public double mean() {
        return mean;
    }
    public double stddev() {
        return this.stddev;
    }
    public double confidenceLo() {
        return this.mean - confidenceDev();
    }
    public double confidenceHi() {
        return this.mean + confidenceDev();
    }

    private double confidenceDev() {
        return (1.96*this.stddev) / Math.sqrt(thresholds.length);
    }
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);

        PercolationStats percolationStats = new PercolationStats(n, trials);

        System.out.println("mean\t\t\t\t\t= " + percolationStats.mean());
        System.out.println("stddev\t\t\t\t\t= " + percolationStats.stddev());
        System.out.println("95% confidence interval = [" +
                                   percolationStats.confidenceLo() +
                                    ", " +
                                    percolationStats.confidenceHi() +
                                    "]");


    }
}
