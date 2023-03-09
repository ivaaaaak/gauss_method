package gaussmethod;

import matrix.Matrix;

import java.util.LinkedList;

public record TriangularMatrix(LinkedList<Integer> mainRowsIndexes,
                               LinkedList<Integer> mainColumnsIndexes,
                               Matrix matrix) {

    public double calculateDeterminant() {
        double determinant = 1;
        for (int k = 0; k < matrix.coefficients().length; k++) {
            int i = mainRowsIndexes.get(k);
            int j = mainColumnsIndexes.get(k);
            determinant *= matrix.coefficients()[i][j];
        }
        return determinant;
    }

    public Matrix getRealTriangularMatrix() {
        int m = matrix.coefficients().length;
        int n = matrix.coefficients()[0].length;
        double[][] realTriangleMatrix = new double[m][n];

        for (int i = 0; i < m; i++) {
            int mainElemRowIndex = mainRowsIndexes.get(i);

            for (int j = 0; j < n; j++) {
                if (j == n - 1) {
                    realTriangleMatrix[i][j] = matrix.coefficients()[mainElemRowIndex][j];
                    continue;
                }
                int index = mainColumnsIndexes.get(j);
                realTriangleMatrix[i][j] = matrix.coefficients()[mainElemRowIndex][index];
            }
        }
        return new Matrix(realTriangleMatrix);
    }
}
