package gaussmethod;

import matrix.Matrix;
import matrix.MatrixElementIndex;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class GaussianElimination {

    public double[] computeAnswer(Matrix matrix) {
        TriangularMatrix transformedMatrix = bringToTriangularShape(matrix);
        if (transformedMatrix != null) {
            System.out.printf("Determinant = %.2f \n\n", transformedMatrix.calculateDeterminant());
            transformedMatrix.getRealTriangularMatrix().print();
            return doReverse(transformedMatrix);
        } else {
            return null;
        }
    }

    public double[] computeResidualError (Matrix matrix, double[] answer) {
        double[][] coefficients = matrix.coefficients();
        double[] residualError = new double[coefficients.length];
        int freeTermIndex = coefficients[0].length - 1;

        for (int i = 0; i < residualError.length; i++) {
            double calculatedFreeTerm = 0;
            for (int j = 0; j < freeTermIndex; j++) {
                calculatedFreeTerm += coefficients[i][j] * answer[j];
            }
            residualError[i] = coefficients[i][freeTermIndex] - calculatedFreeTerm;
        }
        return residualError;
    }

    private TriangularMatrix bringToTriangularShape(Matrix matrix) {
        LinkedList<Integer> mainRowsIndexes = new LinkedList<>();
        LinkedList<Integer> mainColumnsIndexes = new LinkedList<>();

        while (true) {
            Matrix curMatrix = new Matrix(matrix.copy());
            for (Integer mainRowIndex: mainRowsIndexes) {
                Arrays.fill(curMatrix.coefficients()[mainRowIndex], 0);
            }

            MatrixElementIndex index = curMatrix.findMaxAbsElementIndex();
            int p = index.i();
            int k = index.j();
            if (p != -1 && k != -1) {
                mainRowsIndexes.add(p);
                mainColumnsIndexes.add(k);
                double maxElem = matrix.coefficients()[p][k];

                for (int i = 0; i < matrix.coefficients().length; i++) {
                    if (i != p) {
                        if (!mainRowsIndexes.contains(i)) {
                            double q = - matrix.coefficients()[i][k] / maxElem;
                            for (int j = 0; j < matrix.coefficients()[i].length; j++) {
                                matrix.coefficients()[i][j] += q * matrix.coefficients()[p][j];
                            }
                        }
                    }
                }
            } else {
                break;
            }
        }
        if (mainRowsIndexes.size() < matrix.coefficients().length) {
            return null;
        }
        return new TriangularMatrix(mainRowsIndexes, mainColumnsIndexes, matrix);
    }

    private double[] doReverse(TriangularMatrix triangularMatrix) {

        Matrix matrix = triangularMatrix.matrix();
        List<Integer> mainRowsIndexes = triangularMatrix.mainRowsIndexes();
        List<Integer> mainColumnsIndexes = triangularMatrix.mainColumnsIndexes();

        Collections.reverse(mainRowsIndexes);
        Collections.reverse(mainColumnsIndexes);

        int n = matrix.coefficients()[0].length - 1;
        double[] answer = new double[n];

        for (int i = 0; i < n; i++) {
            int m = mainRowsIndexes.get(i);
            int x_index = mainColumnsIndexes.get(i);
            double sum = 0;

            for (int j = 0; j < n; j++) {
                if (j != x_index && !mainColumnsIndexes.subList(i + 1, mainColumnsIndexes.size()).contains(j)) {
                    sum += matrix.coefficients()[m][j] * answer[j];
                }
            }
            answer[x_index] = (matrix.coefficients()[m][n] - sum) / matrix.coefficients()[m][x_index];
        }
        return answer;
    }

}
