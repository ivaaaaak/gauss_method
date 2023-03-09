package input;

import matrix.Matrix;

import java.util.ArrayList;
import java.util.List;

public class MatrixGenerator {

    public Matrix generate(int rowsNum, int columnsNum) {
        double[][] coefficients = new double[rowsNum][columnsNum];
        List<Double> unknowns = new ArrayList<>();
        for (int i = 0; i < columnsNum - 1; i++) {
            unknowns.add(Math.random() * 20 - 10);
        }
        for (int i = 0; i < rowsNum; i++) {
            double freeTerm = 0;
            for (int j = 0; j < columnsNum - 1; j++) {
                coefficients[i][j] = Math.random() * 20 - 10;
                freeTerm += coefficients[i][j] * unknowns.get(j);
            }
            coefficients[i][columnsNum - 1] = freeTerm;
        }
        return new Matrix(coefficients);
    }
}
