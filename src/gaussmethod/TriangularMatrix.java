package gaussmethod;

import java.util.List;

public record TriangularMatrix(List<Integer> mainRowsIndexes,
                               List<Integer> mainColumnsIndexes,
                               double[][] matrix) {

    public double calculateDeterminant() {
        double determinant = 1;
        for (int k = 0; k < matrix.length; k++) {
            int i = mainRowsIndexes.get(k);
            int j = mainColumnsIndexes.get(k);
            determinant *= matrix[i][j];
        }
        return determinant;
    }

    public double[][] getRealTriangularMatrix() {
        double[][] realTriangleMatrix = new double[matrix.length][matrix[0].length];

        for (int i = 0; i < matrix.length; i++) {
            int mainElemRowIndex = mainRowsIndexes.get(i);
            int mainElemColumnIndex = mainColumnsIndexes.get(i);

            for (int j = 0; j < matrix[i].length; j++) {
                if (j == i) {
                    realTriangleMatrix[i][j] = matrix[mainElemRowIndex][mainElemColumnIndex];
                    continue;
                }
                if (j == matrix[i].length - 1) {
                    realTriangleMatrix[i][j] = matrix[mainElemRowIndex][matrix[i].length - 1];
                    continue;
                }
                int ind = mainColumnsIndexes.get(j);
                realTriangleMatrix[i][j] = matrix[mainElemRowIndex][ind];
            }
        }
        return realTriangleMatrix;
    }
}
