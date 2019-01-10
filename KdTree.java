/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

public class KdTree {


    private static class Node {
        private Point2D p;
        private RectHV rect;
        private Node lb;
        private Node rt;
    }


    private Node root;
    private int size;

    public KdTree() {

    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return this.size;
    }




    public static void main(String[] args) {

    }

}
