package com.ivan.utils.math.geom;

import com.ivan.utils.math.geometry.Circle;
import com.ivan.utils.math.geometry.Line2D;
import com.ivan.utils.math.geometry.Line3D;
import com.ivan.utils.math.geometry.Point2D;
import com.ivan.utils.math.geometry.Rectangle;
import com.ivan.utils.math.geometry.Vector2D;

public class GeometryTest {
    public static void main(final String[] args) {
        final Point2D p = new Point2D(1, 2);
        final Line2D l = new Line2D(1, 3, 4, 3);
        final Vector2D v = new Vector2D(2, 3);
        final Vector2D t = new Vector2D(3, 1);

        final Rectangle rect = new Rectangle(3, 1, 4, 5);
        final Circle circ = new Circle(2, 1, 5);

        // basic info
        System.out.println("Basic Information");
        System.out.println("-----------------");
        printPoint(p);

        printLine(l);

        printVector("v", v);
        printVector("v", t);

        printRectangle(rect);

        printCircle(circ);

        // basic operations
        System.out.println("Basic Operations");
        System.out.println("----------------");
        System.out.println("p+v = " + p.add(v));
        System.out.println("p-v = " + p.subtract(v));
        System.out.println();

        System.out.println("v+5 = " + v.add(5));
        System.out.println("v-5 = " + v.subtract(5));
        System.out.println("v×5 = " + v.multiply(5));
        System.out.println("v÷5 = " + v.divide(5));
        System.out.println();

        System.out.println("v∙t = " + v.dot(t));
        System.out.println();

        // projections
        System.out.println("Projections");
        System.out.println("-----------");
        final Vector2D u = new Vector2D(0, 2);
        final Vector2D a = new Vector2D(1, 1);
        System.out.println("Projection of " + a + " into " + u + " = " + a.projection(u));

        final Point2D r1 = new Point2D(3, 3);
        final Point2D r2 = new Point2D(3, 5);
        final Line2D r = new Line2D(r1, r2);
        final Point2D pt = new Point2D(4, 4);
        System.out.println("Projection of " + pt + " into " + r + " = " + pt.projection(r));

        final Line2D l1 = new Line2D(3, 3, 4, 4);
        final Line2D l2 = new Line2D(3, 3, 3, 5);
        System.out.println("Projection of " + l1 + " into " + l2 + " = " + l1.projection(l2));
        System.out.println();

        // perpendicular and parallel
        System.out.println("Perpendicular and Parallel Lines");
        System.out.println("--------------------------------");
        final Line2D lp0 = new Line2D(0, 0, 0, 1);
        final Line2D lp1 = new Line2D(0, 0, 1, 0);
        final Line2D lp2 = new Line2D(1, 0, 1, 1);
        final Line2D lp3 = new Line2D(0, 0, 1, 1);
        printPerpParaTest(lp0, lp1);
        printPerpParaTest(lp0, lp2);
        printPerpParaTest(lp0, lp3);
        System.out.println();

        // line-line intersection
        System.out.println("2D Intersections");
        System.out.println("----------------");
        final Line2D[] lines2d = new Line2D[] {
                new Line2D(0, 0, 1, 1),
                new Line2D(0, 1, 1, 0),
                new Line2D(0, 1, 1, 2),
                new Line2D(1, 0, 2, 0),
        };
        for (int i = 0; i < lines2d.length; i++) {
            final Line2D li = lines2d[i];
            for (int j = i + 1; j < lines2d.length; j++) {
                final Line2D lj = lines2d[j];
                System.out.println(li + " and " + lj + ": " + li.intersectionPoint(lj));
            }
        }
        System.out.println();

        // TODO Line3D still not fully implemented, results are incorrect
        System.out.println("3D Intersections");
        System.out.println("----------------");
        final Line3D[] lines3d = new Line3D[] {
                new Line3D(0, 0, 0, 1, 1, 1),
                new Line3D(1, 0, 1, 0, 1, 0),
                new Line3D(2, 0, 1, 0, 1, 0),
                new Line3D(1, 0, 2, 2, 1, 2),
        };

        for (int i = 0; i < lines3d.length; i++) {
            final Line3D li = lines3d[i];
            for (int j = i + 1; j < lines3d.length; j++) {
                final Line3D lj = lines3d[j];
                System.out.println(li + " and " + lj + ": " + li.intersectionPoint(lj));
            }
        }
        System.out.println();

        // TODO line, rectangle and circle intersections
        // TODO ellipses, curves
        // TODO ellipse and curve intersections (including lines, rects and circles)
        // TODO simple shapes, convex polygons
        // TODO complex shapes, concave polygons, paths
        // TODO collision tests
        // TODO shape intersections
        // TODO boolean operations with shapes: union, exclusion, AND, OR, XOR, ...
        // TODO 3D geometry
    }

    private static void printPoint(final Point2D p) {
        System.out.println("p = " + p);
        System.out.println();
    }

    private static void printLine(final Line2D l) {
        System.out.println("l = " + l);
        System.out.println("  Length: " + l.length() + "; squared: " + l.lengthSq());
        System.out.println("  As vector: " + l.asVector());
        System.out.println();
    }

    private static void printVector(final String name, final Vector2D v) {
        System.out.println(name + " = " + v);
        System.out.println("  Magnitude: " + v.magnitude() + "; squared: " + v.magnitudeSq() + "");
        System.out.println("  Unit vector: " + v.normalize());
        System.out.println("  Rotated 30 degrees: " + v.rotate(Math.toRadians(30.0)));
        System.out.println("  Rotated 60 degrees: " + v.rotate(Math.toRadians(60.0)));
        System.out.println("  Rotated 90 degrees: " + v.rotate(Math.toRadians(90.0)));
        System.out.println("  Normals:");
        System.out.println("    Left-handed : " + v.normalLH());
        System.out.println("    Right-handed: " + v.normalRH());
        System.out.println();
    }

    private static void printRectangle(final Rectangle r) {
        System.out.println("rect = " + r);
        System.out.println("  Area: " + r.getArea());
        System.out.println("  Center: " + r.getCenter());
        System.out.println("  Boundaries:");
        System.out.println("    Points:");
        final Point2D[] ptBounds = r.getBoundaryPoints();
        for (final Point2D point : ptBounds) {
            System.out.println("      " + point);
        }
        System.out.println("    Lines:");
        final Line2D[] lnBounds = r.getBoundaryLines();
        for (final Line2D line : lnBounds) {
            System.out.println("      " + line);
        }
        System.out.println();
    }

    private static void printCircle(final Circle c) {
        System.out.println("circ = " + c);
        System.out.println("  Area: " + c.getArea());
        System.out.println();
    }

    private static void printPerpParaTest(final Line2D l1, final Line2D l2) {
        System.out.println(l1 + " and " + l2 + " are" + (l1.isPerpendicular(l2) ? "" : " not") + " perpendicular," + (l1.isParallel(l2) ? "" : " not") + " parallel.");
    }
}
