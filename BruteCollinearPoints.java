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
import java.util.List;

public class BruteCollinearPoints {

    private int numOfSegments;
    private List<LineSegment> lineSegments;

    public BruteCollinearPoints(Point[] points) {

        validateInput(points);

        numOfSegments = 0;
        lineSegments = new ArrayList<>();

        Arrays.sort(points);

        for (int i = 0; i < points.length- 3; i++) {
            for (int j = i + 1; j < points.length - 2; j++) {
                for (int k = j + 1; k < points.length - 1; k++) {
                    if (points[i].slopeTo(points[j]) != points[j].slopeTo(points[k])) continue;
                    for (int m = k + 1; m < points.length; m++) {
                        if ((points[i].slopeTo(points[j]) == points[j].slopeTo(points[k])) &&
                                (points[j].slopeTo(points[k]) == points[k].slopeTo(points[m]))) {
                            numOfSegments++;
                            lineSegments.add(new LineSegment(points[i], points[m]));
                        }
                    }
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
