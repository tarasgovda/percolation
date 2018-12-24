/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdRandom;

import java.util.ArrayList;
import java.util.List;

public class Board {

    private int[] blocks;

    public Board(int[][] blocks) {
        this.blocks = new int[(int) Math.pow(blocks.length, 2)];
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[i].length; j++) {
                this.blocks[blocks.length * i + j] = blocks[i][j];
            }
        }
    }

    public int dimension() {
        return (int) Math.sqrt(this.blocks.length);
    }

    public int hamming() {
        int counter = 0;

        for (int i = 0; i < blocks.length; i++) {
            if (blocks[i] != 0 && blocks[i] != i+1) counter++;
        }

        return counter;
    }

    public int manhattan() {
        int counter = 0;
        int goalPosition;
        for (int i = 0; i < blocks.length; i++) {
            if (blocks[i] == 0) continue;

            goalPosition = blocks[i] - 1;
            if (i != goalPosition) {
                counter += getHorizontalManhattanDistanse(goalPosition, i) +
                        getVerticalManhattanDistanse(goalPosition, i);
            }
        }

        return counter;
    }

    private int getHorizontalManhattanDistanse(int goal, int current) {
        return Math.abs((goal / dimension()) - (current / dimension()));
    }

    private int getVerticalManhattanDistanse(int goal, int current) {
        return Math.abs((goal % dimension()) - (current % dimension()));
    }

    public boolean isGoal() {

        if (blocks[blocks.length - 1] != 0) return false;

        for (int i = 0; i < blocks.length - 1; i++) {
            if (blocks[i] != i+1) return false;
        }

        return true;
    }

    public Board twin() {
        int index;
        do {
            index = StdRandom.uniform(dimension());
        } while (blocks[index] == 0 || blocks[index + dimension()] == 0);

        return new Board(toTwoDimesnions(index, index+dimension()));
    }

    public boolean equals(Object y) {
        if (y == null) return false;
        if (!(y.getClass() == this.getClass())) return false;

        if (this.dimension() != ((Board) y).dimension()) return false;

        return this.toString().equals(y.toString());
    }

    public Iterable<Board> neighbors() {
        int zeroIndex = -1;
        List<Board> neighbors = new ArrayList<>();

        for (int i = 0; i < blocks.length; i++) {
            if (blocks[i] == 0) {
                zeroIndex = i;
                break;
            }
        }

        if (zeroIndex % dimension() != 0) {
            neighbors.add(new Board(toTwoDimesnions(zeroIndex, zeroIndex - 1)));
        }

        if (zeroIndex + 1 % dimension() != 0) {
            neighbors.add(new Board(toTwoDimesnions(zeroIndex, zeroIndex + 1)));
        }

        if (zeroIndex / dimension() != dimension() - 1) {
            neighbors.add(new Board(toTwoDimesnions(zeroIndex, zeroIndex + dimension())));
        }

        if (zeroIndex / dimension() != 0) {
            neighbors.add(new Board(toTwoDimesnions(zeroIndex, zeroIndex - dimension())));
        }

        return neighbors;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(dimension());
        for (int i = 0; i < this.blocks.length; i++) {
            if (i % dimension() == 0) {
                builder.append("\n");
            }

            builder.append(this.blocks[i]);
            builder.append("\t");
        }

        return builder.toString();
    }

    private int[][] toTwoDimesnions(int firstIndexToSwap, int secondIndexToSwap) {
        int[][] arr = new int[dimension()][dimension()];

        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {

                if (firstIndexToSwap == i * dimension() + j) {
                    arr[i][j] = blocks[secondIndexToSwap];
                } else if (secondIndexToSwap == i * dimension() + j) {
                    arr[i][j] = blocks[firstIndexToSwap];
                } else {
                    arr[i][j] = blocks[i * dimension() + j];
                }
            }
        }

        return arr;
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);
        System.out.println(initial.toString());
        System.out.println(initial.hamming());
        System.out.println(initial.manhattan());
        System.out.println(initial.twin().toString());
        System.out.println("---------");
        for (Board board: initial.neighbors()) {
            System.out.println(board.toString());
            System.out.println("---------");
        }


    }
}
