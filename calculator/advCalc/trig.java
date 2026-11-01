package advCalc;

import java.util.Scanner;

/**
 * Trigonometric operations module for Advanced Calculator with user input.
 * 
 * Supports:
 * - Sine (sin)
 * - Cosine (cos)
 * - Tangent (tan)
 * - Cosecant (csc / cosec)
 * - Secant (sec)
 * - Cotangent (cot)
 * - Inverse functions: arcsin, arccos, arctan
 */
public class trig {

    private static final double EPSILON = 1e-11;

    // ==========================================
    // BASIC TRIGONOMETRIC FUNCTIONS (Degrees)
    // ==========================================

    public static double sin(double deg) {
        double r = Math.sin(Math.toRadians(deg));
        return Math.abs(r) < EPSILON ? 0.0 : r;
    }

    public static double cos(double deg) {
        double r = Math.cos(Math.toRadians(deg));
        return Math.abs(r) < EPSILON ? 0.0 : r;
    }

    public static double tan(double deg) {
        double c = cos(deg);
        if (Math.abs(c) < EPSILON) {
            throw new ArithmeticException("tan(" + deg + "°) is undefined (division by zero).");
        }
        double r = Math.tan(Math.toRadians(deg));
        return Math.abs(r) < EPSILON ? 0.0 : r;
    }

    public static double csc(double deg) {
        double s = sin(deg);
        if (Math.abs(s) < EPSILON) {
            throw new ArithmeticException("csc(" + deg + "°) is undefined (division by zero).");
        }
        return 1.0 / s;
    }

    public static double sec(double deg) {
        double c = cos(deg);
        if (Math.abs(c) < EPSILON) {
            throw new ArithmeticException("sec(" + deg + "°) is undefined (division by zero).");
        }
        return 1.0 / c;
    }

    public static double cot(double deg) {
        double s = sin(deg);
        if (Math.abs(s) < EPSILON) {
            throw new ArithmeticException("cot(" + deg + "°) is undefined (division by zero).");
        }
        double r = cos(deg) / s;
        return Math.abs(r) < EPSILON ? 0.0 : r;
    }

    // ==========================================
    // INVERSE TRIGONOMETRIC FUNCTIONS (Returns Degrees)
    // ==========================================

    public static double asin(double val) {
        if (val < -1.0 || val > 1.0) {
            throw new IllegalArgumentException("asin input must be in range [-1, 1].");
        }
        return Math.toDegrees(Math.asin(val));
    }

    public static double acos(double val) {
        if (val < -1.0 || val > 1.0) {
            throw new IllegalArgumentException("acos input must be in range [-1, 1].");
        }
        return Math.toDegrees(Math.acos(val));
    }

    public static double atan(double val) {
        return Math.toDegrees(Math.atan(val));
    }

    // ==========================================
    // INTERACTIVE USER MENU
    // ==========================================

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n==========================================");
            System.out.println("    ADVANCED CALCULATOR - TRIGONOMETRY    ");
            System.out.println("==========================================");
            System.out.println("1. Sine (sin)");
            System.out.println("2. Cosine (cos)");
            System.out.println("3. Tangent (tan)");
            System.out.println("4. Cosecant (csc)");
            System.out.println("5. Secant (sec)");
            System.out.println("6. Cotangent (cot)");
            System.out.println("7. Inverse Sine (arcsin)");
            System.out.println("8. Inverse Cosine (arccos)");
            System.out.println("9. Inverse Tangent (arctan)");
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
                System.out.println("Exiting Trigonometry Calculator. Goodbye!");
                break;
            }

            try {
                switch (choice) {
                    case 1: {
                        System.out.print("Enter angle in degrees: ");
                        double deg = sc.nextDouble();
                        System.out.printf("sin(%.2f°) = %.4f%n", deg, sin(deg));
                        break;
                    }
                    case 2: {
                        System.out.print("Enter angle in degrees: ");
                        double deg = sc.nextDouble();
                        System.out.printf("cos(%.2f°) = %.4f%n", deg, cos(deg));
                        break;
                    }
                    case 3: {
                        System.out.print("Enter angle in degrees: ");
                        double deg = sc.nextDouble();
                        System.out.printf("tan(%.2f°) = %.4f%n", deg, tan(deg));
                        break;
                    }
                    case 4: {
                        System.out.print("Enter angle in degrees: ");
                        double deg = sc.nextDouble();
                        System.out.printf("csc(%.2f°) = %.4f%n", deg, csc(deg));
                        break;
                    }
                    case 5: {
                        System.out.print("Enter angle in degrees: ");
                        double deg = sc.nextDouble();
                        System.out.printf("sec(%.2f°) = %.4f%n", deg, sec(deg));
                        break;
                    }
                    case 6: {
                        System.out.print("Enter angle in degrees: ");
                        double deg = sc.nextDouble();
                        System.out.printf("cot(%.2f°) = %.4f%n", deg, cot(deg));
                        break;
                    }
                    case 7: {
                        System.out.print("Enter value between -1 and 1: ");
                        double val = sc.nextDouble();
                        System.out.printf("arcsin(%.4f) = %.2f°%n", val, asin(val));
                        break;
                    }
                    case 8: {
                        System.out.print("Enter value between -1 and 1: ");
                        double val = sc.nextDouble();
                        System.out.printf("arccos(%.4f) = %.2f°%n", val, acos(val));
                        break;
                    }
                    case 9: {
                        System.out.print("Enter value: ");
                        double val = sc.nextDouble();
                        System.out.printf("arctan(%.4f) = %.2f°%n", val, atan(val));
                        break;
                    }
                    default:
                        System.out.println("Invalid selection. Please choose an option between 0 and 9.");
                }
            } catch (ArithmeticException | IllegalArgumentException ex) {
                System.out.println("\n[Error] " + ex.getMessage());
            }
        }
    }
}
