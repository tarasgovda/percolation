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

    private int numOfSegments;
    private List<LineSegment> lineSegments;

    public FastCollinearPoints(Point[] points) {
        validateInput(points);

        numOfSegments = 0;
        lineSegments = new ArrayList<>();
        Arrays.sort(points);
        int counter;
        Comparator<Point> order;
        List<Point> max = new ArrayList<>();
        Point[] temp = new Point[points.length];
        for (int i = 0; i < temp.length; i++) {
            temp[i] = points[i];
        }
        for (int i = 0; i < points.length; i++) {
            if (checkIfContainspoint(max, points[i])) continue;
            order = points[i].slopeOrder();
            Arrays.sort(temp, order);
            counter = 2;
            for (int j = 2; j < temp.length; j++) {

                if (order.compare(temp[j-1], temp[j]) == 0) {
                    counter++;
                    if (j == (temp.length - 1)) {
                        if (counter > 3) {
                            Arrays.sort(temp, (j - (counter - 1)), j); //doesn't work???
                            if (!checkIfContainspoint(max, temp[j-1])) {
                                max.add(temp[j-1]);
                                lineSegments.add(new LineSegment(temp[0], temp[j - 1]));
                                numOfSegments++;
                            }
                        }
                    }
                } else {
                    if (counter > 3) {
                        Arrays.sort(temp, (j - (counter - 1)), j); //doesn't work???
                        if (!checkIfContainspoint(max, temp[j-1])) {
                            max.add(temp[j-1]);
                            lineSegments.add(new LineSegment(temp[0], temp[j - 1]));
                            numOfSegments++;
                        }
                    }
                    counter = 2;
                }
            }
        }

    }

    private void validateInput(Point[] points) {
        if (points == null) throw new IllegalArgumentException();

        for (int i = 0; i < points.length - 1; i++) {
            for (int j = i + 1; j < points.length; j++) {
                if (points[j] == null) throw new IllegalArgumentException();
                if (points[i].compareTo(points[j]) == 0) throw new IllegalArgumentException();

            }
        }
    }

    private boolean checkIfContainspoint(List<Point> points, Point pointToCheck) {
        for (Point point: points) {
            if (pointToCheck.compareTo(point) == 0) return true;
        }

        return false;
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
