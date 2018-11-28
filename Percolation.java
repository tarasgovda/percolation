/* *****************************************************************************
 *  Name: Percolation
 *  Date: 26-11-2018
 *  Description: Class for Monte Carlo simulation
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private final int gridSize;
    private final boolean[][] grid;
    private int numberOfOpenSites;
    private final WeightedQuickUnionUF weightedQuickUnionUF;
    private final WeightedQuickUnionUF gridOnly;


    public Percolation(int n)  {
        if (n <= 0) throw new IllegalArgumentException("grid size should be a positive number");
        this.grid = new boolean[n + 1][n + 1];
        this.gridSize = n;
        this.numberOfOpenSites = 0;
        this.weightedQuickUnionUF = new WeightedQuickUnionUF(n*n + 2);
        this.gridOnly = new WeightedQuickUnionUF(n*n + 1);
    }

    public void open(int row, int col) {
        validateIndices(row, col);
        if (isOpen(row, col)) {
            return;
        }
        grid[row][col] = true;

        // left
        if (col != 1 && isOpen(row, col - 1)) {
            weightedQuickUnionUF.union(xyTo1D(row, col - 1), xyTo1D(row, col));
            gridOnly.union(xyTo1D(row, col - 1), xyTo1D(row, col));
        }

        // right
        if (col != gridSize && isOpen(row, col + 1)) {
            weightedQuickUnionUF.union(xyTo1D(row, col + 1), xyTo1D(row, col));
            gridOnly.union(xyTo1D(row, col + 1), xyTo1D(row, col));
        }

        // top
        if (row != 1 && isOpen(row - 1, col)) {
            weightedQuickUnionUF.union(xyTo1D(row - 1, col), xyTo1D(row, col));
            gridOnly.union(xyTo1D(row - 1, col), xyTo1D(row, col));
        } else if (row == 1) {
            gridOnly.union(0, xyTo1D(row, col));
            weightedQuickUnionUF.union(0, xyTo1D(row, col));
        }

        // bottom
        if (row != gridSize && isOpen(row + 1, col)) {
            weightedQuickUnionUF.union(xyTo1D(row + 1, col), xyTo1D(row, col));
            gridOnly.union(xyTo1D(row + 1, col), xyTo1D(row, col));
        } else if (row == gridSize) {
            weightedQuickUnionUF.union(xyTo1D(row, col), gridSize*gridSize + 1);
        }

        numberOfOpenSites++;
    }
    public boolean isOpen(int row, int col) {
        validateIndices(row, col);

        return grid[row][col];
    }
    public boolean isFull(int row, int col) {
        validateIndices(row, col);

        return gridOnly.connected(0, xyTo1D(row, col));
    }
    public int numberOfOpenSites() {
        return numberOfOpenSites;
    }
    public boolean percolates() {
        return weightedQuickUnionUF.connected(0,
                                              gridSize*gridSize + 1);
    }

    public static void main(String[] args) {
        // do your tests here
    }

    private int xyTo1D(int x, int y) {
        validateIndices(x, y);

        return (gridSize * (x - 1)) + y;
    }

    private void validateIndices(int row, int col) {
        if (row <= 0 || row > gridSize) throw new IllegalArgumentException("row index out of bounds");

        if (col <= 0 || col > gridSize) throw new IllegalArgumentException("column index out of bounds");
    }
}
