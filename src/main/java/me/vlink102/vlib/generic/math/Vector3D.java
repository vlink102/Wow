package me.vlink102.vlib.generic.math;

public record Vector3D(double uX, double uY, double uZ) {
    public double[][] toMatrix() {
        return new double[][] {{uX, uY, uZ}};
    }

    public static Vector3D of(double[][] matrix) {
        return new Vector3D(matrix[0][0], matrix[0][1], matrix[0][2]);
    }

    public double getDirection() {
        return Math.atan2(uY, uX);
    }
}
