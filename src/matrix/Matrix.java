package matrix;

import static java.lang.Math.abs;

public class Matrix {

    public static MatrixElementIndex findMaxAbsElementIndex(double[][] matrix) {
        double max = 0;
        int p = -1;
        int k = -1;
        for (int i = 0; i < matrix.length; i++) {
            int j = findMaxAbsRowElementIndex(matrix[i]);
            if (j != -1) {
                double result = matrix[i][j];
                if (abs(result) > abs(max)) {
                    max = result;
                    p = i;
                    k = j;
                }
            }
        }
        return new MatrixElementIndex(p, k);
    }

    private static int findMaxAbsRowElementIndex(double[] row) {
        double max = 1e-18;
        int k = -1;
        for (int j = 0; j < row.length - 1; j++) {
            if (abs(row[j]) > abs(max)) {
                max = row[j];
                k = j;
            }
        }
        return k;
    }

    public static void print(double[][] matrix) {
        for (double[] row : matrix) {
            for (double elem : row) {
                System.out.printf("%6.2f  ", elem);
            }
            System.out.println();
        }
        System.out.println();
    }

    public static double[][] copy(double[][] src) {
        double[][] dst = new double[src.length][];
        for (int i = 0; i < src.length; i++) {
            dst[i] = src[i].clone();
        }
        return dst;
    }
}
