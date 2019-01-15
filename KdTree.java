/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;
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
        root = insert(point2D, root, true, new RectHV(0, 0, 1, 1));
    }

    private Node insert(Point2D point2D, Node node, boolean verticalSplit, RectHV rect) {
        if (node == null) {
            size++;
            node = new Node(point2D);
            node.rect = rect;

            return node;
        }

        int cmp;
        int fullCompare;
        if (verticalSplit) cmp = Objects.compare(point2D, node.p, Point2D.X_ORDER);
        else cmp = Objects.compare(point2D, node.p, Point2D.Y_ORDER);

        fullCompare = point2D.compareTo(node.p);

        if (cmp < 0) {
            if (verticalSplit) {
                rect = new RectHV(rect.xmin(), rect.ymin(), node.p.x(), rect.ymax());
            } else {
                rect = new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), node.p.y());
            }
            node.lb = insert(point2D, node.lb, !verticalSplit, rect);
        }
        else if (cmp > 0 || fullCompare != 0) {
            if (verticalSplit) {
                rect = new RectHV(node.p.x(), rect.ymin(), rect.xmax(), rect.ymax());
            } else {
                rect = new RectHV(rect.xmin(), node.p.y(), rect.xmax(), rect.ymax());
            }
            node.rt = insert(point2D, node.rt, !verticalSplit, rect);
        }
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

    public void draw() {
      draw(root, true);
    }

    private void draw(Node node, boolean verticalSplit) {

        if (node == null) return;



        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        node.p.draw();

        StdDraw.setPenRadius();
        Point2D point2D;
        Point2D point2D1;

        if (verticalSplit) {
            StdDraw.setPenColor(StdDraw.RED);

            point2D = new Point2D(node.p.x(), node.rect.ymin());
            point2D1 = new Point2D(node.p.x(), node.rect.ymax());

        }
        else {
            StdDraw.setPenColor(StdDraw.BLUE);

            point2D = new Point2D(node.rect.xmin(), node.p.y());
            point2D1 = new Point2D(node.rect.xmax(), node.p.y());

        }

        point2D.drawTo(point2D1);

        verticalSplit = !verticalSplit;

        draw(node.lb, verticalSplit);
        draw(node.rt, verticalSplit);
    }


    public Iterable<Point2D> range(RectHV rectHV) {
        ArrayList<Point2D> result = new ArrayList<>();

        range(root, rectHV, result);

        return result;
    }


    private void range(Node node, RectHV rectHV, ArrayList<Point2D> result) {
        if (node == null || !node.rect.intersects(rectHV)) return;

        if (rectHV.contains(node.p)) result.add(node.p);

        range(node.lb, rectHV, result);
        range(node.rt, rectHV, result);
    }


    public Point2D nearest(Point2D point2D) {
        return nearest(root, point2D, null);
    }

    private Point2D nearest(Node node, Point2D point2D, Point2D closest) {
        if (node == null || (closest != null && point2D.distanceSquaredTo(closest) < node.rect.distanceSquaredTo(point2D))) return closest;

        if (point2D.distanceSquaredTo(node.p) < point2D.distanceSquaredTo(closest)) closest = node.p;

        closest = nearest(node.lb, point2D, closest);
        closest = nearest(node.rt, point2D, closest);

        return closest;

     }

    public static void main(String[] args) {

        // KdTree tree = new KdTree();
        // System.out.println(tree.isEmpty());
        //
        // tree.insert(new Point2D(0.1, 0.5));
        // tree.insert(new Point2D(0.4, 0.7));
        // tree.insert(new Point2D(0.4, 0.7));
        // tree.insert(new Point2D(0.4, 0.7));
        // tree.insert(new Point2D(0.4, 0.7));
        // System.out.println(tree.size);
        // System.out.println(tree.contains(new Point2D(0.1, 0.5)));
        // System.out.println(tree.contains(new Point2D(0.1, 0.4)));

    }

}
