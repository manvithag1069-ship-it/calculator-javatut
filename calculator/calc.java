import java.util.Scanner;
import advCalc.eqn;
import advCalc.trig;
import advCalc.Matrix;

/**
 * Main Calculator Application (calc).
 * 
 * Includes:
 * 1. Basic Calculator Operations:
 * - Addition (+)
 * - Subtraction (-)
 * - Multiplication (*)
 * - Division (/)
 * - Modulo / Remainder (%)
 * - Power / Exponentiation (x^y)
 * - Square Root & Cube Root
 * - Factorial (n!)
 * - Logarithms (log10, natural ln)
 * - Percentage
 * - Absolute value
 * 2. Seamless integration with Advanced Calculator (advCalc) modules:
 * - Trigonometry (advCalc.trig)
 * - Matrix Operations (advCalc.Matrix)
 * - Equation Solver (advCalc.eqn)
 */
public class calc {

    // ==========================================
    // BASIC CALCULATOR OPERATIONS
    // ==========================================

    public static double add(double a, double b) {
        return a + b;
    }

    public static double subtract(double a, double b) {
        return a - b;
    }

    public static double multiply(double a, double b) {
        return a * b;
    }

    public static double divide(double a, double b) {
        if (b == 0) {
            throw new ArithmeticException("Division by zero is undefined.");
        }
        return a / b;
    }

    public static double modulo(double a, double b) {
        if (b == 0) {
            throw new ArithmeticException("Modulo by zero is undefined.");
        }
        return a % b;
    }

    public static double power(double base, double exponent) {
        return Math.pow(base, exponent);
    }

    public static double squareRoot(double a) {
        if (a < 0) {
            throw new ArithmeticException("Square root of a negative number is undefined in real numbers.");
        }
        return Math.sqrt(a);
    }

    public static double cubeRoot(double a) {
        return Math.cbrt(a);
    }

    public static long factorial(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Factorial is not defined for negative numbers.");
        }
        if (n > 20) {
            throw new ArithmeticException("Factorial overflow for n > 20 (exceeds 64-bit integer limit).");
        }
        long fact = 1;
        for (int i = 2; i <= n; i++) {
            fact *= i;
        }
        return fact;
    }

    public static double log10(double a) {
        if (a <= 0) {
            throw new ArithmeticException("Logarithm (base 10) is undefined for non-positive numbers.");
        }
        return Math.log10(a);
    }

    public static double ln(double a) {
        if (a <= 0) {
            throw new ArithmeticException("Natural logarithm (ln) is undefined for non-positive numbers.");
        }
        return Math.log(a);
    }

    public static double percentage(double part, double total) {
        if (total == 0) {
            throw new ArithmeticException("Total cannot be zero when calculating percentage.");
        }
        return (part / total) * 100.0;
    }

    public static double absolute(double a) {
        return Math.abs(a);
    }

    // ==========================================
    // INTERACTIVE BASIC CALCULATOR SUB-MENU
    // ==========================================

    public static void runBasicCalculator(Scanner sc) {
        while (true) {
            System.out.println("\n------------------------------------------");
            System.out.println("            BASIC CALCULATIONS            ");
            System.out.println("------------------------------------------");
            System.out.println("1. Addition (a + b)");
            System.out.println("2. Subtraction (a - b)");
            System.out.println("3. Multiplication (a * b)");
            System.out.println("4. Division (a / b)");
            System.out.println("5. Modulo (a % b)");
            System.out.println("6. Power (a^b)");
            System.out.println("7. Square Root (√a)");
            System.out.println("8. Cube Root (∛a)");
            System.out.println("9. Factorial (n!)");
            System.out.println("10. Logarithm (log10 & ln)");
            System.out.println("11. Percentage ((part / total) * 100)");
            System.out.println("12. Absolute Value (|a|)");
            System.out.println("0. Back to Main Menu");
            System.out.println("------------------------------------------");
            System.out.print("Enter choice (0-12): ");

            if (!sc.hasNextInt()) {
                System.out.println("Invalid input. Please enter a valid number.");
                sc.nextLine();
                continue;
            }

            int choice = sc.nextInt();
            sc.nextLine();

            if (choice == 0) {
                break;
            }

            try {
                switch (choice) {
                    case 1: {
                        System.out.print("Enter first number (a): ");
                        double a = sc.nextDouble();
                        System.out.print("Enter second number (b): ");
                        double b = sc.nextDouble();
                        System.out.printf("Result: %s + %s = %s%n", fmt(a), fmt(b), fmt(add(a, b)));
                        break;
                    }
                    case 2: {
                        System.out.print("Enter first number (a): ");
                        double a = sc.nextDouble();
                        System.out.print("Enter second number (b): ");
                        double b = sc.nextDouble();
                        System.out.printf("Result: %s - %s = %s%n", fmt(a), fmt(b), fmt(subtract(a, b)));
                        break;
                    }
                    case 3: {
                        System.out.print("Enter first number (a): ");
                        double a = sc.nextDouble();
                        System.out.print("Enter second number (b): ");
                        double b = sc.nextDouble();
                        System.out.printf("Result: %s * %s = %s%n", fmt(a), fmt(b), fmt(multiply(a, b)));
                        break;
                    }
                    case 4: {
                        System.out.print("Enter numerator (a): ");
                        double a = sc.nextDouble();
                        System.out.print("Enter denominator (b): ");
                        double b = sc.nextDouble();
                        System.out.printf("Result: %s / %s = %s%n", fmt(a), fmt(b), fmt(divide(a, b)));
                        break;
                    }
                    case 5: {
                        System.out.print("Enter first number (a): ");
                        double a = sc.nextDouble();
                        System.out.print("Enter second number (b): ");
                        double b = sc.nextDouble();
                        System.out.printf("Result: %s %% %s = %s%n", fmt(a), fmt(b), fmt(modulo(a, b)));
                        break;
                    }
                    case 6: {
                        System.out.print("Enter base (a): ");
                        double a = sc.nextDouble();
                        System.out.print("Enter exponent (b): ");
                        double b = sc.nextDouble();
                        System.out.printf("Result: %s ^ %s = %s%n", fmt(a), fmt(b), fmt(power(a, b)));
                        break;
                    }
                    case 7: {
                        System.out.print("Enter number (a >= 0): ");
                        double a = sc.nextDouble();
                        System.out.printf("Result: √%s = %s%n", fmt(a), fmt(squareRoot(a)));
                        break;
                    }
                    case 8: {
                        System.out.print("Enter number: ");
                        double a = sc.nextDouble();
                        System.out.printf("Result: ∛%s = %s%n", fmt(a), fmt(cubeRoot(a)));
                        break;
                    }
                    case 9: {
                        System.out.print("Enter integer (0 <= n <= 20): ");
                        int n = sc.nextInt();
                        System.out.printf("Result: %d! = %d%n", n, factorial(n));
                        break;
                    }
                    case 10: {
                        System.out.print("Enter positive number (a > 0): ");
                        double a = sc.nextDouble();
                        System.out.printf("Result: log10(%s) = %.5f, ln(%s) = %.5f%n",
                                fmt(a), log10(a), fmt(a), ln(a));
                        break;
                    }
                    case 11: {
                        System.out.print("Enter part: ");
                        double part = sc.nextDouble();
                        System.out.print("Enter total: ");
                        double total = sc.nextDouble();
                        System.out.printf("Result: %s is %.2f%% of %s%n", fmt(part), percentage(part, total),
                                fmt(total));
                        break;
                    }
                    case 12: {
                        System.out.print("Enter number: ");
                        double a = sc.nextDouble();
                        System.out.printf("Result: |%s| = %s%n", fmt(a), fmt(absolute(a)));
                        break;
                    }
                    default:
                        System.out.println("Invalid selection. Choose between 0 and 12.");
                }
            } catch (ArithmeticException | IllegalArgumentException ex) {
                System.out.println("\n[Error] " + ex.getMessage());
            }
        }
    }

    private static String fmt(double val) {
        if (Math.abs(val) < 1e-11)
            return "0";
        if (val == (long) val) {
            return String.format("%d", (long) val);
        }
        return String.format("%.4f", val).replaceAll("\\.?0+$", "");
    }

    // ==========================================
    // MAIN ENTRY POINT
    // ==========================================

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n==========================================");
            System.out.println("            CALCULATOR SUITE              ");
            System.out.println("==========================================");
            System.out.println("1. Basic Calculator Operations (+, -, *, /, ^, √, !)");
            System.out.println("2. Trigonometry Module (advCalc.trig)");
            System.out.println("3. Matrix Calculator (advCalc.Matrix)");
            System.out.println("4. Equation Solver (advCalc.eqn)");
            System.out.println("0. Exit Application");
            System.out.println("==========================================");
            System.out.print("Enter choice (0-4): ");

            if (!sc.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number.");
                sc.nextLine();
                continue;
            }

            int choice = sc.nextInt();
            sc.nextLine();

            if (choice == 0) {
                System.out.println("Exiting Calculator Suite. Thank you!");
                break;
            }

            switch (choice) {
                case 1:
                    runBasicCalculator(sc);
                    break;
                case 2:
                    trig.main(new String[] {});
                    break;
                case 3:
                    Matrix.main(new String[] {});
                    break;
                case 4:
                    eqn.menu(sc);
                    break;
                default:
                    System.out.println("Invalid selection. Choose between 0 and 4.");
            }
        }
    }
}
