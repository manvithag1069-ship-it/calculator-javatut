package advCalc;

import java.util.Scanner;

/**
 * Matrix operations module for Advanced Calculator with interactive user input.
 * 
 * Supports:
 * - Addition
 * - Subtraction
 * - Multiplication
 * - Scalar Multiplication
 * - Transpose
 * - Trace
 * - Determinant
 * - Adjoint
 * - Inverse
 */
public class Matrix {

    private static final double EPSILON = 1e-11;

    // ==========================================
    // MATRIX OPERATIONS
    // ==========================================

    // 1. Matrix Addition: A + B
    public static double[][] add(double[][] a, double[][] b) {
        checkSameDimensions(a, b, "addition");
        int r = a.length, c = a[0].length;
        double[][] res = new double[r][c];
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                res[i][j] = a[i][j] + b[i][j];
            }
        }
        return res;
    }

    // 2. Matrix Subtraction: A - B
    public static double[][] subtract(double[][] a, double[][] b) {
        checkSameDimensions(a, b, "subtraction");
        int r = a.length, c = a[0].length;
        double[][] res = new double[r][c];
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                res[i][j] = a[i][j] - b[i][j];
            }
        }
        return res;
    }

    // 3. Matrix Multiplication: A * B
    public static double[][] multiply(double[][] a, double[][] b) {
        if (a[0].length != b.length) {
            throw new IllegalArgumentException("Matrix multiplication error: Columns of A ("
                    + a[0].length + ") must equal rows of B (" + b.length + ").");
        }
        int r = a.length, c = b[0].length, kLen = b.length;
        double[][] res = new double[r][c];
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                double sum = 0;
                for (int k = 0; k < kLen; k++) {
                    sum += a[i][k] * b[k][j];
                }
                res[i][j] = Math.abs(sum) < EPSILON ? 0.0 : sum;
            }
        }
        return res;
    }

    // 4. Scalar Multiplication: k * A
    public static double[][] scalarMultiply(double[][] a, double scalar) {
        int r = a.length, c = a[0].length;
        double[][] res = new double[r][c];
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                res[i][j] = a[i][j] * scalar;
            }
        }
        return res;
    }

    // 5. Transpose: A^T
    public static double[][] transpose(double[][] a) {
        int r = a.length, c = a[0].length;
        double[][] res = new double[c][r];
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                res[j][i] = a[i][j];
            }
        }
        return res;
    }

    // 6. Trace: sum of main diagonal elements
    public static double trace(double[][] a) {
        int n = Math.min(a.length, a[0].length);
        double sum = 0;
        for (int i = 0; i < n; i++) {
            sum += a[i][i];
        }
        return sum;
    }

    // 7. Determinant (Recursive cofactor expansion)
    public static double determinant(double[][] a) {
        int n = a.length;
        if (n != a[0].length) {
            throw new IllegalArgumentException("Matrix must be square to compute determinant.");
        }
        if (n == 1)
            return a[0][0];
        if (n == 2)
            return a[0][0] * a[1][1] - a[0][1] * a[1][0];

        double det = 0;
        for (int j = 0; j < n; j++) {
            double sign = (j % 2 == 0) ? 1.0 : -1.0;
            det += sign * a[0][j] * determinant(getSubmatrix(a, 0, j));
        }
        return det;
    }

    // 8. Cofactor Matrix
    public static double[][] cofactor(double[][] a) {
        int n = a.length;
        if (n != a[0].length) {
            throw new IllegalArgumentException("Matrix must be square to compute cofactors.");
        }
        double[][] cof = new double[n][n];
        if (n == 1) {
            cof[0][0] = 1.0;
            return cof;
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                double sign = ((i + j) % 2 == 0) ? 1.0 : -1.0;
                cof[i][j] = sign * determinant(getSubmatrix(a, i, j));
            }
        }
        return cof;
    }

    // 9. Adjoint Matrix: Transpose of Cofactor Matrix
    public static double[][] adjoint(double[][] a) {
        return transpose(cofactor(a));
    }

    // 10. Inverse Matrix: (1 / det) * adj(A)
    public static double[][] inverse(double[][] a) {
        double det = determinant(a);
        if (Math.abs(det) < EPSILON) {
            throw new ArithmeticException("Matrix is singular (det = 0); inverse does not exist.");
        }
        double[][] adj = adjoint(a);
        int n = a.length;
        double[][] inv = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                double val = adj[i][j] / det;
                inv[i][j] = Math.abs(val) < EPSILON ? 0.0 : val;
            }
        }
        return inv;
    }

    // Helper: Get submatrix excluding given row and column
    public static double[][] getSubmatrix(double[][] a, int excludeRow, int excludeCol) {
        int n = a.length;
        double[][] sub = new double[n - 1][n - 1];
        int r = 0;
        for (int i = 0; i < n; i++) {
            if (i == excludeRow) continue;
            int c = 0;
            for (int j = 0; j < n; j++) {
                if (j == excludeCol) continue;
                sub[r][c++] = a[i][j];
            }
            r++;
        }
        return sub;
    }

    // Helper: Check identical dimensions for + and -
    private static void checkSameDimensions(double[][] a, double[][] b, String op) {
        if (a.length != b.length || a[0].length != b[0].length) {
            throw new IllegalArgumentException("Matrix " + op + " error: Dimensions must match. "
                    + "(" + a.length + "x" + a[0].length + ") vs (" + b.length + "x" + b[0].length + ")");
        }
    }

    // ==========================================
    // USER INPUT & DISPLAY HELPERS
    // ==========================================

    /**
     * Reads a general r x c matrix from user input.
     */
    public static double[][] readMatrix(Scanner sc, String name) {
        System.out.print("Enter number of rows for " + name + ": ");
        int r = sc.nextInt();
        System.out.print("Enter number of columns for " + name + ": ");
        int c = sc.nextInt();
        double[][] mat = new double[r][c];
        System.out.println("Enter elements for " + name + " row-wise (" + r + "x" + c + "):");
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                mat[i][j] = sc.nextDouble();
            }
        }
        return mat;
    }

    /**
     * Reads a square n x n matrix from user input.
     */
    public static double[][] readSquareMatrix(Scanner sc, String name) {
        System.out.print("Enter dimension n for square " + name + " (n x n): ");
        int n = sc.nextInt();
        double[][] mat = new double[n][n];
        System.out.println("Enter elements for " + name + " row-wise (" + n + "x" + n + "):");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < c_len(mat); j++) {
                mat[i][j] = sc.nextDouble();
            }
        }
        return mat;
    }

    private static int c_len(double[][] mat) {
        return mat.length;
    }

    /**
     * Formatted Matrix Display
     */
    public static void display(double[][] a, String label) {
        System.out.println(label + ":");
        for (double[] row : a) {
            System.out.print("  [ ");
            for (double val : row) {
                if (Math.abs(val - Math.round(val)) < EPSILON) {
                    System.out.printf("%6d ", (long) Math.round(val));
                } else {
                    System.out.printf("%6.2f ", val);
                }
            }
            System.out.println("]");
        }
        System.out.println();
    }

    // ==========================================
    // INTERACTIVE MENU
    // ==========================================

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n==========================================");
            System.out.println("       ADVANCED CALCULATOR - MATRIX       ");
            System.out.println("==========================================");
            System.out.println("1. Matrix Addition (A + B)");
            System.out.println("2. Matrix Subtraction (A - B)");
            System.out.println("3. Matrix Multiplication (A * B)");
            System.out.println("4. Scalar Multiplication (k * A)");
            System.out.println("5. Transpose (A^T)");
            System.out.println("6. Trace of Matrix");
            System.out.println("7. Determinant of Matrix");
            System.out.println("8. Adjoint Matrix");
            System.out.println("9. Inverse Matrix (A^-1)");
            System.out.println("0. Exit");
            System.out.println("==========================================");
            System.out.print("Enter choice (0-9): ");

            if (!sc.hasNextInt()) {
                System.out.println("Invalid input. Please enter a valid number.");
                sc.nextLine();
                continue;
            }

            int choice = sc.nextInt();
            sc.nextLine(); // consume newline

            if (choice == 0) {
                System.out.println("Exiting Matrix Calculator. Goodbye!");
                break;
            }

            try {
                switch (choice) {
                    case 1: {
                        System.out.println("\n--- Matrix Addition ---");
                        double[][] A = readMatrix(sc, "Matrix A");
                        double[][] B = readMatrix(sc, "Matrix B");
                        display(A, "\nMatrix A");
                        display(B, "Matrix B");
                        display(add(A, B), "Result (A + B)");
                        break;
                    }
                    case 2: {
                        System.out.println("\n--- Matrix Subtraction ---");
                        double[][] A = readMatrix(sc, "Matrix A");
                        double[][] B = readMatrix(sc, "Matrix B");
                        display(A, "\nMatrix A");
                        display(B, "Matrix B");
                        display(subtract(A, B), "Result (A - B)");
                        break;
                    }
                    case 3: {
                        System.out.println("\n--- Matrix Multiplication ---");
                        double[][] A = readMatrix(sc, "Matrix A");
                        double[][] B = readMatrix(sc, "Matrix B");
                        display(A, "\nMatrix A");
                        display(B, "Matrix B");
                        display(multiply(A, B), "Result (A * B)");
                        break;
                    }
                    case 4: {
                        System.out.println("\n--- Scalar Multiplication ---");
                        double[][] A = readMatrix(sc, "Matrix A");
                        System.out.print("Enter scalar value k: ");
                        double k = sc.nextDouble();
                        display(A, "\nMatrix A");
                        display(scalarMultiply(A, k), "Result (" + k + " * A)");
                        break;
                    }
                    case 5: {
                        System.out.println("\n--- Matrix Transpose ---");
                        double[][] A = readMatrix(sc, "Matrix A");
                        display(A, "\nMatrix A");
                        display(transpose(A), "Result Transpose (A^T)");
                        break;
                    }
                    case 6: {
                        System.out.println("\n--- Matrix Trace ---");
                        double[][] A = readMatrix(sc, "Matrix A");
                        display(A, "\nMatrix A");
                        System.out.printf("Trace: %.2f%n", trace(A));
                        break;
                    }
                    case 7: {
                        System.out.println("\n--- Matrix Determinant ---");
                        double[][] A = readSquareMatrix(sc, "Matrix A");
                        display(A, "\nMatrix A");
                        System.out.printf("Determinant: %.2f%n", determinant(A));
                        break;
                    }
                    case 8: {
                        System.out.println("\n--- Matrix Adjoint ---");
                        double[][] A = readSquareMatrix(sc, "Matrix A");
                        display(A, "\nMatrix A");
                        display(adjoint(A), "Adjoint of Matrix A");
                        break;
                    }
                    case 9: {
                        System.out.println("\n--- Matrix Inverse ---");
                        double[][] A = readSquareMatrix(sc, "Matrix A");
                        display(A, "\nMatrix A");
                        display(inverse(A), "Inverse of Matrix A (A^-1)");
                        break;
                    }
                    default:
                        System.out.println("Invalid selection. Please choose an option between 0 and 9.");
                }
            } catch (IllegalArgumentException | ArithmeticException ex) {
                System.out.println("\n[Error] " + ex.getMessage());
            }
        }
    }
}
