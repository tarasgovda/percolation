/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.Objects;

public class KdTree {


    private static class Node {
        private Point2D p;
        private RectHV rect;
        private Node lb;
        private Node rt;

        public Node (Point2D p) {
            this.p = p;
        }
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


    public void insert(Point2D point2D) {
        root = insert(point2D, root, true);
    }

    private Node insert(Point2D point2D, Node node, boolean verticalSplit) {
        if (node == null) {
            size++;
            node = new Node(point2D);


            return node;
        }

        int cmp;
        int fullCompare;
        if (verticalSplit) cmp = Objects.compare(point2D, node.p, Point2D.X_ORDER);
        else cmp = Objects.compare(point2D, node.p, Point2D.Y_ORDER);

        fullCompare = point2D.compareTo(node.p);

        if (cmp < 0) node.lb = insert(point2D, node.lb, !verticalSplit);
        else if (cmp > 0 || fullCompare != 0) node.rt = insert(point2D, node.rt, !verticalSplit);
        else node.p = point2D;

        return node;
    }

    public boolean contains(Point2D point2D) {
        Node node = this.root;
        boolean verticalSplit = true;
        int cmp;
        int fullCompare;
        while (node != null) {
            if (verticalSplit) cmp = Objects.compare(point2D, node.p, Point2D.X_ORDER);
            else cmp = Objects.compare(point2D, node.p, Point2D.Y_ORDER);
            fullCompare = point2D.compareTo(node.p);
            verticalSplit = !verticalSplit;
            if (cmp < 0) node = node.lb;
            else if (cmp > 0 || fullCompare != 0) node = node.rt;
            else return true;
        }

        return false;
    }

    public static void main(String[] args) {

        KdTree tree = new KdTree();
        System.out.println(tree.isEmpty());

        tree.insert(new Point2D(0.1, 0.5));
        tree.insert(new Point2D(0.4, 0.7));
        tree.insert(new Point2D(0.4, 0.7));
        tree.insert(new Point2D(0.4, 0.7));
        tree.insert(new Point2D(0.4, 0.7));
        System.out.println(tree.size);
        System.out.println(tree.contains(new Point2D(0.1, 0.5)));
        System.out.println(tree.contains(new Point2D(0.1, 0.4)));
    }

}
