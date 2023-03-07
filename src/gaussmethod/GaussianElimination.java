package gaussmethod;

import matrix.Matrix;
import matrix.MatrixElementIndex;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class GaussianElimination {

    public double[] computeAnswer(double[][] matrix) {
        TriangularMatrix transformedMatrix = bringToTriangularShape(matrix);
        if (transformedMatrix != null) {
            System.out.printf("Determinant = %.2f \n\n", transformedMatrix.calculateDeterminant());
            Matrix.print(transformedMatrix.getRealTriangularMatrix());
            return doReverse(transformedMatrix);
        } else {
            return null;
        }
    }

    public double[] computeDiscrepancies (double[][] matrix, double[] answer) {
        double[] discrepancies = new double[answer.length];
        int freeTermIndex = matrix[0].length - 1;

        for (int i = 0; i < discrepancies.length; i++) {
            double calculatedFreeTerm = 0;
            for (int j = 0; j < freeTermIndex; j++) {
                calculatedFreeTerm += matrix[i][j] * answer[j];
            }
            discrepancies[i] = matrix[i][freeTermIndex] - calculatedFreeTerm;
        }
        return discrepancies;
    }

    private TriangularMatrix bringToTriangularShape (double[][] matrix) {
        List<Integer> mainRowsIndexes = new LinkedList<>();
        List<Integer> mainColumnsIndexes = new LinkedList<>();

        while (true) {
            double[][] curMatrix = Matrix.copy(matrix);
            for (Integer mainRowIndex: mainRowsIndexes) {
                Arrays.fill(curMatrix[mainRowIndex], 0);
            }

            MatrixElementIndex index = Matrix.findMaxAbsElementIndex(curMatrix);
            int p = index.i();
            int k = index.j();
            if (p != -1 && k != -1) {
                mainRowsIndexes.add(p);
                mainColumnsIndexes.add(k);
                double maxElem = matrix[p][k];

                for (int i = 0; i < matrix.length; i++) {
                    if (i != p) {
                        if (!mainRowsIndexes.contains(i)) {
                            double q = - matrix[i][k] / maxElem;
                            for (int j = 0; j < matrix[i].length; j++) {
                                matrix[i][j] = matrix[i][j] + q * matrix[p][j];
                            }
                        }
                    }
                }
            } else {
                break;
            }
        }
        if (mainRowsIndexes.size() < matrix.length) {
            return null;
        }
        return new TriangularMatrix(mainRowsIndexes, mainColumnsIndexes, matrix);
    }

    private double[] doReverse(TriangularMatrix triangularMatrix) {

        double[][] matrix = triangularMatrix.matrix();
        List<Integer> mainRowsIndexes = triangularMatrix.mainRowsIndexes();
        List<Integer> mainColumnsIndexes = triangularMatrix.mainColumnsIndexes();

        Collections.reverse(mainRowsIndexes);
        Collections.reverse(mainColumnsIndexes);

        int n = matrix[0].length - 1;
        double[] answer = new double[n];

        for (int i = 0; i < n; i++) {
            int m = mainRowsIndexes.get(i);
            int x_index = mainColumnsIndexes.get(i);
            double sum = 0;

            for (int j = 0; j < n; j++) {
                if (j != x_index && !mainColumnsIndexes.subList(i + 1, mainColumnsIndexes.size()).contains(j)) {
                    sum += matrix[m][j] * answer[j];
                }
            }
            answer[x_index] = (matrix[m][n] - sum) / matrix[m][x_index];
        }
        return answer;
    }

}
