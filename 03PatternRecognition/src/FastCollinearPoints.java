import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {

    private ArrayList<LineSegment> allLineSegments;

    public FastCollinearPoints(Point[] points)     // finds all line segments containing 4 or more points
    {
        if (points == null) throw new NullPointerException();
        checkDuplicates(points);

        allLineSegments = new ArrayList<>();

        Point[] pointsCopy = new Point[points.length];
        System.arraycopy(points, 0, pointsCopy, 0, points.length);

        double slope;
        int i, first, last;;
        int length = points.length;

        for (Point point: points) {

            Arrays.sort(pointsCopy);
            Arrays.sort(pointsCopy, point.slopeOrder());

            i = 1;
            while (i < length) {

                first = i;
                slope = point.slopeTo(pointsCopy[i]);
                i++;
                while ((i < length) && Double.compare(point.slopeTo(pointsCopy[i]), slope) == 0) {
                    i++;
                }
                last = i;

                if (last - first >= 3 && point.compareTo(pointsCopy[first]) < 0) {
                    allLineSegments.add(new LineSegment(point, pointsCopy[last - 1]));
                }
                i = last;
            }
       }

    }

    private void checkDuplicates(Point[] points) {
        for (int i = 0; i < points.length - 1; i++) {
            for (int j = i + 1; j < points.length; j++) {
                if (points[i].compareTo(points[j]) == 0) throw  new IllegalArgumentException();
            }
        }
    }

    public int numberOfSegments()        // the number of line segments
    {
        return allLineSegments.size();
    }
    public LineSegment[] segments()                // the line segments
    {
        return allLineSegments.toArray(new LineSegment[allLineSegments.size()]);
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