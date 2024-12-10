package me.vlink102.vlib.generic.math;

public class MatrixTransformation {
    public class Matrix2D {
        public static double[][] multiply(double[][] matrix, int scalar) {
            final double[][] temp = new double[matrix.length][matrix[0].length];
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix[i].length; j++) {
                    temp[i][j] = matrix[i][j] * scalar;
                }
            }
            return (temp);
        }

        public static double[][] add(double[][] matrix, double[][] other) {
            final double[][] temp = new double[matrix.length][matrix[0].length];
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix[i].length; j++) {
                    temp[i][j] = matrix[i][j] + other[i][j];
                }
            }
            return (temp);
        }

        public static double[][] subtract(double[][] matrix, double[][] other) {
            final double[][] temp = new double[matrix.length][matrix[0].length];
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix[i].length; j++) {
                    temp[i][j] = matrix[i][j] - other[i][j];
                }
            }
            return (temp);
        }

        public static double[][] multiply(double[][] matrix, double[][] other) {
            if (matrix[0].length != other.length) return null;
            int n = matrix.length;
            int m = matrix[0].length;
            int p = other[0].length;
            double[][] result = new double[n][p];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < p; j++) {
                    double sum = 0;
                    for (int k = 0; k < m; k++) {
                        sum += matrix[i][k] * other[k][j];
                    }
                    result[i][j] = sum;
                }
            }
            return result;
        }

        public static double[][] winogradForm(double[][] matrix, double[][] other) {
            double a = matrix[0][0];
            double b = matrix[0][1];
            double c = matrix[1][0];
            double d = matrix[1][1];
            double A = other[0][0];
            double B = other[0][1];
            double C = other[1][0];
            double D = other[1][1];

            double t = a * A;
            double u = (c - a) * (C - D);
            double v = (c + d) * (C - A);
            double w = t + (c + d - a) * (A + D - C);

            double[][] result = new double[2][2];
            result[0][0] = t + b * B;
            result[0][1] = w + v + (a + b - c - d) * D;
            result[1][0] = w + u + d * (B + C - A - D);
            result[1][1] = w + u + v;
            return result;
        }

        public static double[][] strassenAlgorithm(double[][] matrix, double[][] other) {
            double A_11 = matrix[0][0];
            double A_12 = matrix[0][1];
            double A_21 = matrix[1][0];
            double A_22 = matrix[1][1];
            double B_11 = other[0][0];
            double B_12 = other[0][1];
            double B_21 = other[1][0];
            double B_22 = other[1][1];

            double M_1 = (A_11 + A_22) * (B_11 + B_22);
            double M_2 = (A_21 + A_22) * B_11;
            double M_3 = A_11 * (B_12 - B_22);
            double M_4 = A_22 * (B_21 - B_11);
            double M_5 = (A_11 + A_12) * B_22;
            double M_6 = (A_21 + A_11) * (B_11 + B_12);
            double M_7 = (A_12 - A_22) * (B_21 + B_22);

            double[][] result = new double[2][2];
            result[0][0] = M_1 + M_4 - M_5 + M_7;
            result[0][1] = M_3 + M_5;
            result[1][0] = M_2 + M_4;
            result[1][1] = M_1 - M_2 + M_3 + M_6;
            return result;
        }
        public static Vector3D xRotate(Vector3D initial, double theta) {
            double[][] rotationMatrix = {
                    {1, 0, 0},
                    {0, Math.cos(theta), -Math.sin(theta)},
                    {0, Math.sin(theta), Math.cos(theta)}
            };
            double[][] rotated = Matrix2D.multiply(initial.toMatrix(), rotationMatrix);

            return Vector3D.of(rotated);
        }

        public static Vector3D yRotate(Vector3D initial, double theta) {
            double[][] rotationMatrix = {
                    {Math.cos(theta), 0, Math.sin(theta)},
                    {0, 1, 0},
                    {-Math.sin(theta), 0, Math.cos(theta)}
            };
            double[][] rotated = Matrix2D.multiply(initial.toMatrix(), rotationMatrix);
            return Vector3D.of(rotated);
        }

        public static Vector3D zRotate(Vector3D initial, double theta) {
            double[][] rotationMatrix = {
                    {Math.cos(theta), -Math.sin(theta), 0},
                    {Math.sin(theta), Math.cos(theta), 0},
                    {0, 0, 1}
            };
            double[][] rotated = Matrix2D.multiply(initial.toMatrix(), rotationMatrix);
            return Vector3D.of(rotated);
        }

        public static double[][] rotate(double[][] matrix, double yaw, double pitch, double roll) {
            double[][] rotationMatrix = {
                    {Math.cos(yaw) * Math.cos(pitch), Math.cos(yaw) * Math.sin(pitch) * Math.sin(roll) - Math.sin(yaw) * Math.cos(roll), Math.cos(yaw) * Math.sin(pitch) * Math.cos(roll) + Math.sin(yaw) * Math.sin(roll)},
                    {Math.sin(yaw) * Math.cos(pitch), Math.sin(yaw) * Math.sin(pitch) * Math.sin(roll) + Math.cos(yaw) * Math.cos(roll), Math.sin(yaw) * Math.sin(pitch) * Math.cos(roll) - Math.cos(yaw) * Math.sin(roll)},
                    {-Math.sin(pitch), Math.cos(pitch) * Math.sin(roll), Math.cos(pitch) * Math.cos(roll)}
            };
            return Matrix2D.multiply(matrix, rotationMatrix);
        }

        public static double tR(double theta) {
            return 1 + (2 * Math.cos(theta));
        }

        public static double[][] rotate(double[][] matrix, Vector3D axis, double theta) {
            double uX = axis.uX();
            double uY = axis.uY();
            double uZ = axis.uZ();

            double uX_2 = uX * uX;
            double uY_2 = uY * uY;
            double uZ_2 = uZ * uZ;

            double cos = Math.cos(theta);
            double sin = Math.sin(theta);

            double[][] rotationMatrix = {
                    {
                        (uX_2 * (1 - cos)) + cos,
                            (uX * uY * (1 - cos)) - (uZ * sin),
                            (uX * uZ * (1 - cos)) + (uY * sin)
                    },
                    {
                        (uX * uY * (1 - cos)) + (uZ * sin),
                            (uY_2 * (1 - cos)) + cos,
                            (uY * uZ * (1 - cos)) - (uX * sin)
                    },
                    {
                        ((uX * uZ * (1 - cos)) - (uY * sin)),
                            (uY * uZ * (1 - cos)) + (uX * sin),
                            (uZ_2 * (1 - cos)) + cos
                    }
            };

            return Matrix2D.multiply(matrix, rotationMatrix);
        }
    }
}
