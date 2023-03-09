package matrix;

import static java.lang.Math.abs;

public record Matrix(double[][] coefficients) {

    public MatrixElementIndex findMaxAbsElementIndex() {
        double max = 0;
        int p = -1;
        int k = -1;
        for (int i = 0; i < coefficients.length; i++) {
            int j = findMaxAbsRowElementIndex(coefficients[i]);
            if (j != -1) {
                double result = coefficients[i][j];
                if (abs(result) > abs(max)) {
                    max = result;
                    p = i;
                    k = j;
                }
            }
        }
        return new MatrixElementIndex(p, k);
    }

    private int findMaxAbsRowElementIndex(double[] row) {
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

    public void print() {
        for (double[] row : coefficients) {
            for (double elem : row) {
                System.out.printf("%6.2f  ", elem);
            }
            System.out.println();
        }
        System.out.println();
    }

    public double[][] copy() {
        double[][] dst = new double[coefficients.length][];
        for (int i = 0; i < coefficients.length; i++) {
            dst[i] = coefficients[i].clone();
        }
        return dst;
    }
}
