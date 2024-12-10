package me.vlink102.generic.math;

import java.util.Arrays;

public class AlphaTensor {

    public static double[][][] blockSplit(double[][] matrix, int n_rows, int n_cols) {
        double[][][] result = new double[(matrix.length / n_cols) * (matrix[0].length / n_rows)][n_rows][n_cols];
        int rd = matrix.length / n_rows;
        int cd = matrix[0].length / n_cols;
        for (int i = 0; i < rd; i++) {
            int i_0 = i * n_rows;
            for (int j = 0; j < cd; j++) {
                int j_0 = j * n_cols;

                int index = j + (i * rd);
                for (int k = 0; k < n_rows; k++) {
                    System.arraycopy(matrix[i_0 + k], j_0, result[index][k], 0, n_cols);
                    /*for (int l = 0; l < n_cols; l++) {
                        result[index][k][l] = matrix[i_0 + k][j_0 + l];
                    }*/
                }
            }
        }
        
        return result;
    }

    public static double[][][] multiplicationTensor(int n) {
        double[][][] result = new double[n^2][n^2][n^2];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < n; k++) {
                    result[i * n + j][j * n + k][k * n + i] = 1;
                }
            }
        }
        return result;
    }

    @Deprecated
    public static double[][] split(double[] array, int n) {
        double[][] result = new double[array.length / n][n];
        for (int i = 0; i < result.length; i++) {
            System.arraycopy(array, i * n, result[i], 0, n);

            /*for (int j = 0; j < n; j++) {
                result[i][j] = array[i * n + j];
            }*/
        }
        return result;
    }
}
