import gaussmethod.GaussianElimination;
import input.MatrixGenerator;
import input.MatrixReader;
import matrix.Matrix;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class Main {

    private final static Scanner scanner = new Scanner(System.in);
    private final static PrintStream consoleOutStream = System.out;

    public static void main(String[] args) {
        int mode = 0;
        while (mode != 1 && mode != 2 && mode != 3) {
            try {
                System.out.println("Please enter:");
                System.out.println("1 to read matrix from console");
                System.out.println("2 to read matrix from file");
                System.out.println("3 to generate matrix");
                mode = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException ignored) {}
        }

        MatrixReader reader = getReader(mode);
        if (reader != null) {
            Matrix matrix;

            if (mode == 3) {
                MatrixGenerator generator = new MatrixGenerator();
                matrix = generator.generate(reader.readRowsNum(), reader.readColumnsNum());
            } else {
                matrix = reader.read();
            }

            if (matrix != null) {
                System.setOut(consoleOutStream);

                GaussianElimination g = new GaussianElimination();
                double[] answer = g.computeAnswer(matrix);

                if (answer != null) {
                    double[] residualError = g.computeResidualError(matrix, answer);

                    for (int i = 0; i < answer.length; i++) {
                        System.out.printf("X" + (i + 1) + " = %.2f \n", answer[i]);
                    }
                    System.out.println();
                    for (int i = 0; i < residualError.length; i++) {
                        System.out.println("Discrepancies " + (i + 1) + " = " + residualError[i]);
                    }
                } else {
                    System.out.println("The system has no single solution");
                }
            }
        }
    }

    private static MatrixReader getReader(int mode) {
        switch (mode) {
            case 1:
            case 3:
                return new MatrixReader(scanner);
            case 2:
                while (true) {
                    System.out.println("Enter file name:");
                    String filePath = scanner.nextLine();
                    try {
                        File file = new File(filePath);
                        FileInputStream fileStream = new FileInputStream(file.getAbsoluteFile());
                        System.setOut(new PrintStream(OutputStream.nullOutputStream()));
                        return new MatrixReader(new Scanner(fileStream));
                    } catch (FileNotFoundException e) {
                        System.err.println("File not found");
                    }
                }
            default: return null;
        }
    }

}
