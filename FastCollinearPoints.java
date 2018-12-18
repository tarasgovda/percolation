/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class FastCollinearPoints {

    private final int numOfSegments;
    private  final List<LineSegment> lineSegments;

    public FastCollinearPoints(Point[] points) {
        validateInput(points);

        int num = 0;
        lineSegments = new ArrayList<>();
        Point[] copy = new Point[points.length];
        int counter;
        Comparator<Point> order;
        Point[] temp = new Point[copy.length];
        for (int i = 0; i < temp.length; i++) {
            copy[i] = points[i];
        }
        Arrays.sort(copy);
        for (int i = 0; i < temp.length; i++) {
            temp[i] = copy[i];
        }
        for (int i = 0; i < copy.length; i++) {
            order = copy[i].slopeOrder();
            Arrays.sort(temp, order);
            counter = 2;
            for (int j = 2; j < temp.length; j++) {

                if (order.compare(temp[j-1], temp[j]) == 0) {
                    counter++;
                    if (j == (temp.length - 1)) {
                        if (counter > 3) {
                            Arrays.sort(temp, (j - (counter - 2)), j + 1);
                            if (temp[0].compareTo(temp[j - (counter - 2)]) < 0) {
                                lineSegments.add(new LineSegment(temp[0], temp[j]));
                                num++;
                            }
                        }
                    }
                } else {
                    if (counter > 3) {
                        Arrays.sort(temp, (j - (counter - 1)), j);
                        if (temp[0].compareTo(temp[j - (counter - 1)]) < 0) {
                            lineSegments.add(new LineSegment(temp[0], temp[j - 1]));
                            num++;
                        }
                    }
                    counter = 2;
                }
            }
        }

        numOfSegments = num;
    }

    private void validateInput(Point[] points) {
        if (points == null) throw new IllegalArgumentException();

        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) throw new IllegalArgumentException();
            for (int j = i + 1; j < points.length; j++) {
                if (points[j] == null) throw new IllegalArgumentException();
                if (points[i].compareTo(points[j]) == 0) throw new IllegalArgumentException();

            }
        }
    }

    public int numberOfSegments() {
        return numOfSegments;
    }

    public LineSegment[] segments() {
        return lineSegments.toArray(new LineSegment[0]);
    }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
