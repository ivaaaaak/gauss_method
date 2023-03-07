package input;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class MatrixReader {

    private final Scanner mainScanner;
    private final Scanner errorScanner;

    public MatrixReader(Scanner scanner) {
        this.mainScanner = scanner;
        this.errorScanner = new Scanner(System.in);
    }

    public double[][] read() {
        int rowsNum = readRowsNum();
        int columnsNum = readColumnsNum();

        double[][] inputMatrix = new double[rowsNum][columnsNum];

        for (int i = 0; i < rowsNum; i++) {
            System.out.println("Enter row №" + (i + 1) + " of the extended matrix with coefficients separated by a space. Double separator is \".\"");
            inputMatrix[i] = readRow(columnsNum, i);
            if (inputMatrix[i] == null) {
                return null;
            }
        }
        return inputMatrix;
    }

    private double[] readRow(int columnsNum, int rowNum) {
        double[] row = new double[columnsNum];
        List<Integer> errors = new ArrayList<>();

        for (int j = 0; j < columnsNum; j++) {
            try {
                row[j] = Double.parseDouble(mainScanner.next());
            } catch (NumberFormatException e) {
                errors.add(j);
            } catch (NoSuchElementException e) {
                System.err.println("Check if numbers of rows and columns are correct");
                return null;
            }
        }

        while (!errors.isEmpty()) {
            Iterator<Integer> it = errors.listIterator();
            while (it.hasNext()) {
                Integer errorIndex = it.next();
                System.err.println("In row №" + (rowNum + 1) + " coefficient №" + (errorIndex + 1) + " must be a double number with dot separator");
                System.err.println("Please enter again");
                try {
                    row[errorIndex] = Double.parseDouble(errorScanner.nextLine());
                    it.remove();
                } catch (NumberFormatException ignored) {}
            }
        }
        return row;
    }

    public int readRowsNum() {
        int rowsNum = 0;
        while (rowsNum <= 0) {
            try {
                System.out.println("Number of matrix's rows: ");
                rowsNum = Integer.parseInt(mainScanner.nextLine());
                if (rowsNum <= 0) {
                    System.err.println("Number should be greater than zero");
                }
                if (rowsNum > 20) {
                    System.err.println("Maximum number of rows is 20");
                    rowsNum = 0;
                }
            } catch (NumberFormatException e) {
                System.err.println("Please enter integer number");
                rowsNum = 0;
            }
        }
        return rowsNum;
    }

    public int readColumnsNum() {
        int columnsNum = 0;
        while (columnsNum <= 0) {
            try {
                System.out.println("Number of matrix's columns: ");
                columnsNum = Integer.parseInt(mainScanner.nextLine());
                if (columnsNum <= 0) {
                    System.err.println("Number should be greater than zero");
                }
                if (columnsNum > 21) {
                    System.err.println("Maximum number of columns is 21");
                    columnsNum = 0;
                }
            } catch (NumberFormatException e) {
                System.err.println("Please enter integer number");
                columnsNum = 0;
            }
        }
        return columnsNum;
    }
}
