package me.vlink102.render;

import me.vlink102.vlib.generic.math.MatrixTransformation;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RenderUtil {
    public static List<Triangle> tetrahedron() {
        List<Triangle> triangles = new ArrayList<>();
        triangles.add(new Triangle(new Vertex(100, 100, 100),
                new Vertex(-100, -100, 100),
                new Vertex(-100, 100, -100),
                Color.WHITE));
        triangles.add(new Triangle(new Vertex(100, 100, 100),
                new Vertex(-100, -100, 100),
                new Vertex(100, -100, -100),
                Color.RED));
        triangles.add(new Triangle(new Vertex(-100, 100, -100),
                new Vertex(100, -100, -100),
                new Vertex(100, 100, 100),
                Color.GREEN));
        triangles.add(new Triangle(new Vertex(-100, 100, -100),
                new Vertex(100, -100, -100),
                new Vertex(-100, -100, 100),
                Color.BLUE));
        return triangles;
    }

    public static Vertex transform(double[][] matrix, Vertex in) {
        return new Vertex(
                in.x() * matrix[0][0] + in.y() * matrix[1][0] + in.z() * matrix[2][0],
                in.x() * matrix[0][1] + in.y() * matrix[1][1] + in.z() * matrix[2][1],
                in.x() * matrix[0][2] + in.y() * matrix[1][2] + in.z() * matrix[2][2]

        );
    }

    private static boolean similarSide(Vertex A, Vertex B, Vertex C, Vertex p) {
        Vertex V1V2 = new Vertex(B.x() - A.x(),B.y() - A.y(),B.z() - A.z());
        Vertex V1V3 = new Vertex(C.x() - A.x(),C.y() - A.y(),C.z() - A.z());
        Vertex V1P = new Vertex(p.x() - A.x(),p.y() - A.y(),p.z() - A.z());

        double V1V2CrossV1V3 = V1V2.x() * V1V3.y() - V1V3.x() * V1V2.y();
        double V1V2CrossP = V1V2.x() * V1P.y() - V1P.x() * V1V2.y();

        return V1V2CrossV1V3 * V1V2CrossP >= 0;
    }

    private static List<Triangle> curved(List<Triangle> initial) {
        List<Triangle> triangles = new ArrayList<>();
        for (Triangle t : initial) {
            Vertex m1 =
                    new Vertex((t.v1.x + t.v2.x) / 2, (t.v1.y + t.v2.y) / 2, (t.v1.z + t.v2.z) / 2);
            Vertex m2 =
                    new Vertex((t.v2.x + t.v3.x) / 2, (t.v2.y + t.v3.y) / 2, (t.v2.z + t.v3.z) / 2);
            Vertex m3 =
                    new Vertex((t.v1.x + t.v3.x) / 2, (t.v1.y + t.v3.y) / 2, (t.v1.z + t.v3.z) / 2);
            triangles.add(new Triangle(t.v1, m1, m3, t.color));
            triangles.add(new Triangle(t.v2, m1, m2, t.color));
            triangles.add(new Triangle(t.v3, m2, m3, t.color));
            triangles.add(new Triangle(m1, m2, m3, t.color));
        }
        return triangles;
    }




    public static void renderOld(List<Triangle> triangles, JPanel pane, Graphics2D graphics, int dx, int dy) {
        //graphics.translate(pane.getWidth() / 2, pane.getHeight() / 2);
        graphics.setColor(Color.WHITE);

        double heading = Math.toRadians(dx);
        double[][] headingTransform = {
                {Math.cos(heading), 0, -Math.sin(heading)},
                {0, 1, 0},
                {Math.sin(heading), 0, Math.cos(heading)}
        };
        double pitch = Math.toRadians(dy);
        double[][] pitchTransform = {
                {1, 0, 0},
                {0, Math.cos(pitch), Math.sin(pitch)},
                {0, -Math.sin(pitch), Math.cos(pitch)}
        };
        double[][] transform = MatrixTransformation.Matrix2D.multiply(headingTransform, pitchTransform);

        BufferedImage image = new BufferedImage(600, 600, BufferedImage.TYPE_INT_ARGB);
        double[] zBuffer = new double[image.getWidth() * image.getHeight()];
        Arrays.fill(zBuffer, Double.NEGATIVE_INFINITY);

        for (Triangle triangle : triangles) {
            Vertex v1 = transform(transform, triangle.v1());
            Vertex v2 = transform(transform, triangle.v2());
            Vertex v3 = transform(transform, triangle.v3());
            Vertex ab = new Vertex(v2.x - v1.x, v2.y - v1.y, v2.z - v1.z);
            Vertex ac = new Vertex(v3.x - v1.x, v3.y - v1.y, v3.z - v1.z);
            Vertex normal = new Vertex(
                    ab.y * ac.z - ab.z * ac.y,
                    ab.z * ac.x - ab.x * ac.z,
                    ab.x * ac.y - ab.y * ac.x
            );
            double normalMagnitude = Math.sqrt(normal.x * normal.x + normal.y * normal.y + normal.z * normal.z);
            normal.x /= normalMagnitude;
            normal.y /= normalMagnitude;
            normal.z /= normalMagnitude;
            double angle = Math.abs(normal.z);
            v1.addX(pane.getWidth() / 2.0);
            v1.addY(pane.getHeight() / 2.0);
            v2.addX(pane.getWidth() / 2.0);
            v2.addY(pane.getHeight() / 2.0);
            v3.addX(pane.getWidth() / 2.0);
            v3.addY(pane.getHeight() / 2.0);
            int minX = (int) Math.max(0, Math.ceil(Math.min(v1.x, Math.min(v2.x, v3.x))));
            int maxX = (int) Math.min(image.getWidth() - 1,
                    Math.floor(Math.max(v1.x, Math.max(v2.x, v3.x))));
            int minY = (int) Math.max(0, Math.ceil(Math.min(v1.y, Math.min(v2.y, v3.y))));
            int maxY = (int) Math.min(image.getHeight() - 1,
                    Math.floor(Math.max(v1.y, Math.max(v2.y, v3.y))));

            for (int y = minY; y <= maxY; y++) {
                for (int x = minX; x <= maxX; x++) {
                    Vertex p = new Vertex(x, y, 0);
                    boolean V1 = similarSide(v1, v2, v3, p);
                    boolean V2 = similarSide(v2, v3, v1, p);
                    boolean V3 = similarSide(v3, v1, v2, p);
                    if (V3 && V2 && V1) {
                        double depth = v1.z + v2.z + v3.z;
                        int zI = y * image.getWidth() + x;
                        if (zBuffer[zI] < depth) {
                            image.setRGB(x, y, getShade(triangle.color(), angle).getRGB());
                            zBuffer[zI] = depth;
                        }
                    }
                }
            }
            graphics.drawImage(image, 0, 0, null);
        }


    }
    public static Color getShade(Color color, double shade) {

        double redLinear = Math.pow(color.getRed(), 2.2) * shade;
        double greenLinear = Math.pow(color.getGreen(), 2.2) * shade;
        double blueLinear = Math.pow(color.getBlue(), 2.2) * shade;

        int red = (int) Math.pow(redLinear, 1 / 2.2);
        int green = (int) Math.pow(greenLinear, 1 / 2.2);
        int blue = (int) Math.pow(blueLinear, 1 / 2.2);

        return new Color(red, green, blue);
    }
}
