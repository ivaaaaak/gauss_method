package input;

import matrix.Matrix;

import java.util.ArrayList;
import java.util.List;

public class MatrixGenerator {

    public double[][] generate(int rowsNum, int columnsNum) {
        double[][] matrix = new double[rowsNum][columnsNum];
        List<Double> unknowns = new ArrayList<>();
        for (int i = 0; i < columnsNum - 1; i++) {
            unknowns.add(Math.random() * 20 - 10);
        }
        for (int i = 0; i < rowsNum; i++) {
            double freeTerm = 0;
            for (int j = 0; j < columnsNum - 1; j++) {
                matrix[i][j] = Math.random() * 20 - 10;
                freeTerm += matrix[i][j] * unknowns.get(j);
            }
            matrix[i][columnsNum - 1] = freeTerm;
        }
        Matrix.print(matrix);
        return matrix;
    }
}
