import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import java.util.ArrayList;

public class BruteCollinearPoints {

    private ArrayList<LineSegment> allSegments;

    public BruteCollinearPoints(Point[] points)    // finds all line segments containing 4 points
    {
        if (points == null) throw new NullPointerException();
        checkDuplicates(points);

        allSegments = new ArrayList<>();

        for (int i1 = 0; i1 < points.length; i1++) {
            for (int i2 = i1 + 1; i2 < points.length; i2++) {
                for (int i3 = i2 + 1; i3 < points.length; i3++) {
                    for (int i4 = i3 + 1; i4 < points.length; i4++) {
                        if (points[i1] == null || points[i2] == null || points[i3] == null || points[i4] == null)
                            throw new NullPointerException();
                        if (Double.compare(points[i1].slopeTo(points[i2]), points[i2].slopeTo(points[i3])) == 0 &&
                                Double.compare(points[i2].slopeTo(points[i3]), points[i3].slopeTo(points[i4])) == 0) {
                            ArrayList<Point> sortP = new ArrayList<>();
                            sortP.add(points[i1]);
                            sortP.add(points[i2]);
                            sortP.add(points[i3]);
                            sortP.add(points[i4]);
                            sortP.sort(Point::compareTo);
                            allSegments.add(new LineSegment(sortP.get(0), sortP.get(sortP.size()-1)));
                        }
                    }
                }
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
        return allSegments.size();
    }

    public LineSegment[] segments()                // the line segments
    {
        LineSegment[] ret = new LineSegment[allSegments.size()];
        int cnt = 0;
        for (LineSegment l: allSegments) {
            ret[cnt++] = l;
        }
        return ret;
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
