/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Solver {

    private boolean solvable;
    private int moves;
    private SearchNode solution;

    public Solver(Board initial) {

        if (initial == null) throw new IllegalArgumentException();

        MinPQ<SearchNode> gameTree = new MinPQ<>((o1, o2) -> {
            if (o1.priority() > o2.priority()) {
                return 1;
            } else if (o1.priority() < o2.priority()) {
                return -1;
            }

            return 0;
        });

        MinPQ<SearchNode> alternateGameTree = new MinPQ<>((o1, o2) -> {
            if (o1.priority() > o2.priority()) {
                return 1;
            } else if (o1.priority() < o2.priority()) {
                return -1;
            }

            return 0;
        });

        SearchNode first = new SearchNode();
        first.node = initial;
        first.moves = 0;
        first.predecessor = null;
        gameTree.insert(first);

        SearchNode firsstTwin = new SearchNode();
        firsstTwin.node = initial.twin();
        firsstTwin.moves = 0;
        firsstTwin.predecessor = null;
        alternateGameTree.insert(firsstTwin);

        SearchNode current;
        SearchNode alternateCurrent;
        SearchNode neighbour;
        while (true) {

            current = gameTree.delMin();
            if (current.node.isGoal()) {
               solvable = true;
               moves = current.moves;
               solution = current;
               break;
            } else {
                for (Board board: current.neighbors()) {
                    neighbour = new SearchNode();
                    neighbour.node = board;
                    neighbour.predecessor = current;
                    neighbour.moves = current.moves + 1;
                    gameTree.insert(neighbour);
                }
            }


            alternateCurrent = alternateGameTree.delMin();
            if (alternateCurrent.node.isGoal()) {
                solvable = false;
                solution = null;
                moves = -1;
                break;
            } else {
                for (Board board: alternateCurrent.neighbors()) {
                    neighbour = new SearchNode();
                    neighbour.node = board;
                    neighbour.predecessor = alternateCurrent;
                    neighbour.moves = alternateCurrent.moves + 1;
                    alternateGameTree.insert(neighbour);
                }
            }
        }

    }

    public boolean isSolvable() {
        return solvable;
    }
    public int moves() {
        return moves;
    }

    public Iterable<Board> solution() {
        if (solution == null) return null;
        List<Board> solutionSequence = new ArrayList<>();
        solutionSequence.add(solution.node);
        SearchNode cur = solution;
        while (cur.predecessor != null) {
            solutionSequence.add(cur.predecessor.node);
            cur = cur.predecessor;
        }

        Collections.reverse(solutionSequence);

        return solutionSequence;
    }

    public static void main(String[] args) {

        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

    private class SearchNode {
        Board node;
        SearchNode predecessor;
        int moves;
        int priority = -1;

        public int priority() {
            if (priority == -1) {
                priority = moves + node.manhattan();
            }
            return priority;
        }

        public Iterable<Board> neighbors() {
            Iterable<Board> neighbors = node.neighbors();
            Iterator<Board> i = neighbors.iterator();
            List<Board> result = new ArrayList<>();
            Board board;
            while (i.hasNext()) {
                board = i.next();
                if (predecessor != null && predecessor.node.equals(board)) {
                    continue;
                }
                result.add(board);
            }

            return result;
        }

    }
}
