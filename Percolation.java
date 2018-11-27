/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private int gridSize;
    private int[][] grid;
    private int numberOfOpenSites;
    private WeightedQuickUnionUF weightedQuickUnionUF;


    public Percolation(int n)  {
        this.grid = new int[n + 1][n + 1];
        this.gridSize = n;
        this.numberOfOpenSites = 0;
        this.weightedQuickUnionUF = new WeightedQuickUnionUF(n*n + 2);
    }

    public void open(int row, int col) {
        validateIndices(row, col);
        grid[row][col] = 1;

        // left
        if (col != 1 && isOpen(row, col - 1)) {
            weightedQuickUnionUF.union(xyTo1D(row, col - 1), xyTo1D(row, col));
        }

        // right
        if (col != gridSize && isOpen(row, col + 1)) {
            weightedQuickUnionUF.union(xyTo1D(row, col + 1), xyTo1D(row, col));
        }

        // top
        if (row != 1 && isOpen(row - 1, col)) {
            weightedQuickUnionUF.union(xyTo1D(row - 1, col), xyTo1D(row, col));
        } else if (row == 1) {
            weightedQuickUnionUF.union(0, xyTo1D(row, col));
        }

        // bottom
        if (row != gridSize && isOpen(row + 1, col)) {
            weightedQuickUnionUF.union(xyTo1D(row + 1, col), xyTo1D(row, col));
        } else if (row == gridSize) {
            weightedQuickUnionUF.union(xyTo1D(row, col), gridSize + 1);
        }

        numberOfOpenSites++;
    }
    public boolean isOpen(int row, int col) {
        validateIndices(row, col);

        return grid[row][col] == 1;
    }
    public boolean isFull(int row, int col) {
        validateIndices(row, col);

        return weightedQuickUnionUF.connected(0, xyTo1D(row, col));
    }
    public int numberOfOpenSites() {
        return numberOfOpenSites;
    }
    public boolean percolates() {
        return weightedQuickUnionUF.connected(0, gridSize + 1);
    }

    public static void main(String[] args) {

        int size = 20;
        Percolation percolation = new Percolation(size);
        int randRow;
        int randCol;
        while (true) {
           randRow = StdRandom.uniform(20) + 1;
           randCol = StdRandom.uniform(20) + 1;

           if (percolation.isOpen(randRow, randCol)) continue;

           percolation.open(randRow, randCol);
           if (!percolation.percolates()) continue;

           double openSites = (double) percolation.numberOfOpenSites();
           double threshlod = openSites / (size*size);
           System.out.println(threshlod);
           System.out.println((int) openSites);
           break;
       }
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
