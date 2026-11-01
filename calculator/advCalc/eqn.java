package advCalc;

import java.util.Scanner;

/**
 * Equation Solver Module (eqn) for Advanced Calculator.
 * 
 * Supports:
 * 1. Linear Equations (ax + b = 0)
 * 2. System of 2 Linear Equations (2 unknowns: x, y)
 * 3. System of 3 Linear Equations (3 unknowns: x, y, z)
 * 4. Quadratic Equations (ax^2 + bx + c = 0) with real & complex roots
 * 5. Cubic Equations (ax^3 + bx^2 + cx + d = 0) using Cardano's & trigonometric methods
 * 6. Polynomial Evaluation & Newton-Raphson Numerical Root Finding
 * 7. Interactive CLI menu for user-friendly calculator interactions
 */
public class eqn {

    private static final double EPSILON = 1e-11;

    // ==========================================
    // 1. LINEAR EQUATION: ax + b = 0
    // ==========================================

    /**
     * Solves a linear equation ax + b = 0.
     * 
     * @param a Coefficient of x
     * @param b Constant term
     * @return Formatted solution description
     */
    public static String solveLinear(double a, double b) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Equation: %sx + %s = 0\n", formatNum(a), formatNum(b)));

        if (Math.abs(a) < EPSILON) {
            if (Math.abs(b) < EPSILON) {
                sb.append("Result: Infinite solutions (Identity: 0 = 0).");
            } else {
                sb.append("Result: No solution (Inconsistent equation).");
            }
        } else {
            double x = -b / a;
            if (Math.abs(x) < EPSILON) x = 0.0;
            sb.append(String.format("Result: x = %s", formatNum(x)));
        }
        return sb.toString();
    }

    /**
     * Programmatically returns the root of ax + b = 0, or null if no unique solution exists.
     */
    public static Double getLinearRoot(double a, double b) {
        if (Math.abs(a) < EPSILON) return null;
        double x = -b / a;
        return Math.abs(x) < EPSILON ? 0.0 : x;
    }

    // ==========================================
    // 2. SYSTEM OF 2 LINEAR EQUATIONS (2 Unknowns)
    //    a1*x + b1*y = c1
    //    a2*x + b2*y = c2
    // ==========================================

    /**
     * Solves system of 2 linear equations using Cramer's Rule.
     */
    public static String solveLinearSystem2(double a1, double b1, double c1,
                                            double a2, double b2, double c2) {
        StringBuilder sb = new StringBuilder();
        sb.append("System of Equations:\n");
        sb.append(String.format("  (1) %sx + %sy = %s\n", formatNum(a1), formatNum(b1), formatNum(c1)));
        sb.append(String.format("  (2) %sx + %sy = %s\n", formatNum(a2), formatNum(b2), formatNum(c2)));

        double d = a1 * b2 - a2 * b1;
        double dx = c1 * b2 - c2 * b1;
        double dy = a1 * c2 - a2 * c1;

        if (Math.abs(d) > EPSILON) {
            double x = dx / d;
            double y = dy / d;
            if (Math.abs(x) < EPSILON) x = 0.0;
            if (Math.abs(y) < EPSILON) y = 0.0;
            sb.append(String.format("Result: Unique solution\n  x = %s\n  y = %s", formatNum(x), formatNum(y)));
        } else {
            if (Math.abs(dx) < EPSILON && Math.abs(dy) < EPSILON) {
                sb.append("Result: Infinitely many solutions (Coincident/Dependent equations).");
            } else {
                sb.append("Result: No solution (Inconsistent/Parallel lines).");
            }
        }
        return sb.toString();
    }

    /**
     * Returns [x, y] for system of 2 linear equations, or null if no unique solution exists.
     */
    public static double[] getSystem2Roots(double a1, double b1, double c1,
                                           double a2, double b2, double c2) {
        double d = a1 * b2 - a2 * b1;
        if (Math.abs(d) <= EPSILON) return null;
        double x = (c1 * b2 - c2 * b1) / d;
        double y = (a1 * c2 - a2 * c1) / d;
        return new double[]{Math.abs(x) < EPSILON ? 0.0 : x, Math.abs(y) < EPSILON ? 0.0 : y};
    }

    // ==========================================
    // 3. SYSTEM OF 3 LINEAR EQUATIONS (3 Unknowns)
    //    a1*x + b1*y + c1*z = d1
    //    a2*x + b2*y + c2*z = d2
    //    a3*x + b3*y + c3*z = d3
    // ==========================================

    private static double determinant3x3(double m00, double m01, double m02,
                                         double m10, double m11, double m12,
                                         double m20, double m21, double m22) {
        return m00 * (m11 * m22 - m12 * m21)
             - m01 * (m10 * m22 - m12 * m20)
             + m02 * (m10 * m21 - m11 * m20);
    }

    /**
     * Solves system of 3 linear equations using Cramer's Rule.
     */
    public static String solveLinearSystem3(double a1, double b1, double c1, double d1,
                                            double a2, double b2, double c2, double d2,
                                            double a3, double b3, double c3, double d3) {
        StringBuilder sb = new StringBuilder();
        sb.append("System of Equations:\n");
        sb.append(String.format("  (1) %sx + %sy + %sz = %s\n", formatNum(a1), formatNum(b1), formatNum(c1), formatNum(d1)));
        sb.append(String.format("  (2) %sx + %sy + %sz = %s\n", formatNum(a2), formatNum(b2), formatNum(c2), formatNum(d2)));
        sb.append(String.format("  (3) %sx + %sy + %sz = %s\n", formatNum(a3), formatNum(b3), formatNum(c3), formatNum(d3)));

        double detD = determinant3x3(a1, b1, c1, a2, b2, c2, a3, b3, c3);
        double detDx = determinant3x3(d1, b1, c1, d2, b2, c2, d3, b3, c3);
        double detDy = determinant3x3(a1, d1, c1, a2, d2, c2, a3, d3, c3);
        double detDz = determinant3x3(a1, b1, d1, a2, b2, d2, a3, b3, d3);

        if (Math.abs(detD) > EPSILON) {
            double x = detDx / detD;
            double y = detDy / detD;
            double z = detDz / detD;
            if (Math.abs(x) < EPSILON) x = 0.0;
            if (Math.abs(y) < EPSILON) y = 0.0;
            if (Math.abs(z) < EPSILON) z = 0.0;
            sb.append(String.format("Result: Unique solution\n  x = %s\n  y = %s\n  z = %s",
                    formatNum(x), formatNum(y), formatNum(z)));
        } else {
            if (Math.abs(detDx) < EPSILON && Math.abs(detDy) < EPSILON && Math.abs(detDz) < EPSILON) {
                sb.append("Result: Infinitely many solutions or dependent system.");
            } else {
                sb.append("Result: No solution (Inconsistent system).");
            }
        }
        return sb.toString();
    }

    /**
     * Returns [x, y, z] for system of 3 linear equations, or null if no unique solution exists.
     */
    public static double[] getSystem3Roots(double a1, double b1, double c1, double d1,
                                           double a2, double b2, double c2, double d2,
                                           double a3, double b3, double c3, double d3) {
        double detD = determinant3x3(a1, b1, c1, a2, b2, c2, a3, b3, c3);
        if (Math.abs(detD) <= EPSILON) return null;
        double detDx = determinant3x3(d1, b1, c1, d2, b2, c2, d3, b3, c3);
        double detDy = determinant3x3(a1, d1, c1, a2, d2, c2, a3, d3, c3);
        double detDz = determinant3x3(a1, b1, d1, a2, b2, d2, a3, b3, d3);
        double x = detDx / detD;
        double y = detDy / detD;
        double z = detDz / detD;
        return new double[]{
            Math.abs(x) < EPSILON ? 0.0 : x,
            Math.abs(y) < EPSILON ? 0.0 : y,
            Math.abs(z) < EPSILON ? 0.0 : z
        };
    }

    // ==========================================
    // 4. QUADRATIC EQUATION: ax^2 + bx + c = 0
    // ==========================================

    /**
     * Solves quadratic equation ax^2 + bx + c = 0.
     * Computes real or complex conjugate roots and parabola vertex.
     */
    public static String solveQuadratic(double a, double b, double c) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Equation: %sx^2 + %sx + %s = 0\n", formatNum(a), formatNum(b), formatNum(c)));

        if (Math.abs(a) < EPSILON) {
            sb.append("Note: a = 0; reducing to a linear equation.\n");
            sb.append(solveLinear(b, c));
            return sb.toString();
        }

        double disc = b * b - 4 * a * c;
        sb.append(String.format("Discriminant (D = b^2 - 4ac): %s\n", formatNum(disc)));

        if (disc > EPSILON) {
            double sqrtD = Math.sqrt(disc);
            double x1 = (-b + sqrtD) / (2 * a);
            double x2 = (-b - sqrtD) / (2 * a);
            if (Math.abs(x1) < EPSILON) x1 = 0.0;
            if (Math.abs(x2) < EPSILON) x2 = 0.0;
            sb.append("Roots: Two distinct real roots\n");
            sb.append(String.format("  x1 = %s\n  x2 = %s", formatNum(x1), formatNum(x2)));
        } else if (Math.abs(disc) <= EPSILON) {
            double x = -b / (2 * a);
            if (Math.abs(x) < EPSILON) x = 0.0;
            sb.append("Roots: Real and equal root (multiplicity 2)\n");
            sb.append(String.format("  x1 = x2 = %s", formatNum(x)));
        } else {
            double realPart = -b / (2 * a);
            double imagPart = Math.sqrt(-disc) / (2 * Math.abs(a));
            if (Math.abs(realPart) < EPSILON) realPart = 0.0;
            sb.append("Roots: Complex conjugate roots\n");
            sb.append(String.format("  x1 = %s + %si\n  x2 = %s - %si",
                    formatNum(realPart), formatNum(imagPart),
                    formatNum(realPart), formatNum(imagPart)));
        }

        // Vertex information
        double vx = -b / (2 * a);
        double vy = -disc / (4 * a);
        if (Math.abs(vx) < EPSILON) vx = 0.0;
        if (Math.abs(vy) < EPSILON) vy = 0.0;
        sb.append(String.format("\nParabola Vertex: (%s, %s)", formatNum(vx), formatNum(vy)));

        return sb.toString();
    }

    /**
     * Returns array of real roots. Empty array if roots are complex.
     */
    public static double[] getQuadraticRealRoots(double a, double b, double c) {
        if (Math.abs(a) < EPSILON) {
            Double lr = getLinearRoot(b, c);
            return lr == null ? new double[0] : new double[]{lr};
        }
        double disc = b * b - 4 * a * c;
        if (disc > EPSILON) {
            double sqrtD = Math.sqrt(disc);
            return new double[]{(-b + sqrtD) / (2 * a), (-b - sqrtD) / (2 * a)};
        } else if (Math.abs(disc) <= EPSILON) {
            return new double[]{-b / (2 * a)};
        } else {
            return new double[0]; // No real roots
        }
    }

    // ==========================================
    // 5. CUBIC EQUATION: ax^3 + bx^2 + cx + d = 0
    // ==========================================

    /**
     * Solves cubic equation ax^3 + bx^2 + cx + d = 0 using Cardano's
     * and trigonometric methods.
     */
    public static String solveCubic(double a, double b, double c, double d) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Equation: %sx^3 + %sx^2 + %sx + %s = 0\n",
                formatNum(a), formatNum(b), formatNum(c), formatNum(d)));

        if (Math.abs(a) < EPSILON) {
            sb.append("Note: a = 0; reducing to a quadratic equation.\n");
            sb.append(solveQuadratic(b, c, d));
            return sb.toString();
        }

        // Normalize to monic: x^3 + Bx^2 + Cx + D = 0
        double B = b / a;
        double C = c / a;
        double D = d / a;

        // Substitute x = t - B/3 to get depressed cubic: t^3 + pt + q = 0
        double p = C - (B * B) / 3.0;
        double q = (2.0 * B * B * B) / 27.0 - (B * C) / 3.0 + D;

        // Discriminant delta = (q/2)^2 + (p/3)^3
        double delta = (q / 2.0) * (q / 2.0) + (p / 3.0) * (p / 3.0) * (p / 3.0);
        double shift = B / 3.0;

        if (delta < -EPSILON) {
            // Three distinct real roots (trigonometric method)
            double r = Math.sqrt(-(p * p * p) / 27.0);
            double cosVal = -q / (2.0 * r);
            cosVal = Math.max(-1.0, Math.min(1.0, cosVal)); // Clamp
            double phi = Math.acos(cosVal);
            double factor = 2.0 * Math.sqrt(-p / 3.0);

            double x1 = factor * Math.cos(phi / 3.0) - shift;
            double x2 = factor * Math.cos((phi + 2.0 * Math.PI) / 3.0) - shift;
            double x3 = factor * Math.cos((phi + 4.0 * Math.PI) / 3.0) - shift;

            if (Math.abs(x1) < EPSILON) x1 = 0.0;
            if (Math.abs(x2) < EPSILON) x2 = 0.0;
            if (Math.abs(x3) < EPSILON) x3 = 0.0;

            sb.append("Roots: Three distinct real roots\n");
            sb.append(String.format("  x1 = %s\n  x2 = %s\n  x3 = %s",
                    formatNum(x1), formatNum(x2), formatNum(x3)));
        } else if (Math.abs(delta) <= EPSILON) {
            // Real roots, at least two are equal
            if (Math.abs(p) <= EPSILON && Math.abs(q) <= EPSILON) {
                double x = -shift;
                if (Math.abs(x) < EPSILON) x = 0.0;
                sb.append("Roots: Triple real root (multiplicity 3)\n");
                sb.append(String.format("  x1 = x2 = x3 = %s", formatNum(x)));
            } else {
                double u = Math.cbrt(-q / 2.0);
                double x1 = 2.0 * u - shift;
                double x2 = -u - shift;
                if (Math.abs(x1) < EPSILON) x1 = 0.0;
                if (Math.abs(x2) < EPSILON) x2 = 0.0;
                sb.append("Roots: Real roots (one single, one double root)\n");
                sb.append(String.format("  x1 = %s\n  x2 = x3 = %s", formatNum(x1), formatNum(x2)));
            }
        } else {
            // One real root and two complex conjugate roots (Cardano's method)
            double sqrtDelta = Math.sqrt(delta);
            double u = Math.cbrt(-q / 2.0 + sqrtDelta);
            double v = Math.cbrt(-q / 2.0 - sqrtDelta);

            double x1 = (u + v) - shift;
            if (Math.abs(x1) < EPSILON) x1 = 0.0;

            double realPart = -(u + v) / 2.0 - shift;
            double imagPart = (Math.sqrt(3.0) / 2.0) * Math.abs(u - v);
            if (Math.abs(realPart) < EPSILON) realPart = 0.0;

            sb.append("Roots: One real root and two complex conjugate roots\n");
            sb.append(String.format("  x1 = %s\n", formatNum(x1)));
            sb.append(String.format("  x2 = %s + %si\n  x3 = %s - %si",
                    formatNum(realPart), formatNum(imagPart),
                    formatNum(realPart), formatNum(imagPart)));
        }

        return sb.toString();
    }

    // ==========================================
    // 6. POLYNOMIAL UTILITIES
    // ==========================================

    /**
     * Evaluates a polynomial P(x) = c[0]*x^n + c[1]*x^(n-1) + ... + c[n]
     * using Horner's method.
     */
    public static double evaluatePolynomial(double[] coeffs, double x) {
        if (coeffs == null || coeffs.length == 0) return 0.0;
        double result = coeffs[0];
        for (int i = 1; i < coeffs.length; i++) {
            result = result * x + coeffs[i];
        }
        return result;
    }

    /**
     * Finds a real root of polynomial using Newton-Raphson method.
     */
    public static Double findPolynomialRoot(double[] coeffs, double initialGuess, int maxIterations, double tol) {
        if (coeffs == null || coeffs.length < 2) return null;

        // Derivative coefficients
        int n = coeffs.length - 1;
        double[] deriv = new double[n];
        for (int i = 0; i < n; i++) {
            deriv[i] = coeffs[i] * (n - i);
        }

        double x = initialGuess;
        for (int iter = 0; iter < maxIterations; iter++) {
            double fx = evaluatePolynomial(coeffs, x);
            if (Math.abs(fx) < tol) return x;

            double fpx = evaluatePolynomial(deriv, x);
            if (Math.abs(fpx) < EPSILON) {
                // Derivative near zero; perturb guess slightly
                x += 0.1;
                continue;
            }

            double nextX = x - fx / fpx;
            if (Math.abs(nextX - x) < tol) return nextX;
            x = nextX;
        }
        return Math.abs(evaluatePolynomial(coeffs, x)) < 1e-3 ? x : null;
    }

    // ==========================================
    // 7. FORMATTING & HELPER FUNCTIONS
    // ==========================================

    public static String formatNum(double val) {
        if (Math.abs(val) < EPSILON) return "0";
        if (val == (long) val) {
            return String.format("%d", (long) val);
        }
        // Round to 5 decimal places cleanly
        String formatted = String.format("%.5f", val);
        // Strip trailing zeros
        while (formatted.contains(".") && (formatted.endsWith("0") || formatted.endsWith("."))) {
            if (formatted.endsWith(".")) {
                formatted = formatted.substring(0, formatted.length() - 1);
                break;
            }
            formatted = formatted.substring(0, formatted.length() - 1);
        }
        return formatted;
    }

    // ==========================================
    // 8. INTERACTIVE MENU
    // ==========================================

    public static void menu() {
        Scanner sc = new Scanner(System.in);
        menu(sc);
    }

    public static void menu(Scanner sc) {
        while (true) {
            System.out.println("\n==========================================");
            System.out.println("       ADVANCED CALCULATOR - EQUATION     ");
            System.out.println("==========================================");
            System.out.println("1. Linear Equation (ax + b = 0)");
            System.out.println("2. System of 2 Linear Equations (2 Unknowns: x, y)");
            System.out.println("3. System of 3 Linear Equations (3 Unknowns: x, y, z)");
            System.out.println("4. Quadratic Equation (ax^2 + bx + c = 0)");
            System.out.println("5. Cubic Equation (ax^3 + bx^2 + cx + d = 0)");
            System.out.println("6. Polynomial Evaluation & Root Finding");
            System.out.println("0. Return / Exit");
            System.out.println("==========================================");
            System.out.print("Enter choice (0-6): ");

            if (!sc.hasNextInt()) {
                System.out.println("Invalid input. Please enter an integer.");
                sc.nextLine();
                continue;
            }

            int choice = sc.nextInt();
            sc.nextLine(); // consume newline

            if (choice == 0) {
                System.out.println("Exiting equation solver.");
                break;
            }

            switch (choice) {
                case 1: {
                    System.out.println("\n[Linear Equation: ax + b = 0]");
                    System.out.print("Enter coefficient a: ");
                    double a = sc.nextDouble();
                    System.out.print("Enter constant b: ");
                    double b = sc.nextDouble();
                    System.out.println("\n" + solveLinear(a, b));
                    break;
                }
                case 2: {
                    System.out.println("\n[System of 2 Equations: a1*x + b1*y = c1, a2*x + b2*y = c2]");
                    System.out.print("Enter a1, b1, c1 (separated by space): ");
                    double a1 = sc.nextDouble();
                    double b1 = sc.nextDouble();
                    double c1 = sc.nextDouble();
                    System.out.print("Enter a2, b2, c2 (separated by space): ");
                    double a2 = sc.nextDouble();
                    double b2 = sc.nextDouble();
                    double c2 = sc.nextDouble();
                    System.out.println("\n" + solveLinearSystem2(a1, b1, c1, a2, b2, c2));
                    break;
                }
                case 3: {
                    System.out.println("\n[System of 3 Equations: a*x + b*y + c*z = d]");
                    System.out.print("Enter row 1 (a1 b1 c1 d1): ");
                    double a1 = sc.nextDouble();
                    double b1 = sc.nextDouble();
                    double c1 = sc.nextDouble();
                    double d1 = sc.nextDouble();
                    System.out.print("Enter row 2 (a2 b2 c2 d2): ");
                    double a2 = sc.nextDouble();
                    double b2 = sc.nextDouble();
                    double c2 = sc.nextDouble();
                    double d2 = sc.nextDouble();
                    System.out.print("Enter row 3 (a3 b3 c3 d3): ");
                    double a3 = sc.nextDouble();
                    double b3 = sc.nextDouble();
                    double c3 = sc.nextDouble();
                    double d3 = sc.nextDouble();
                    System.out.println("\n" + solveLinearSystem3(a1, b1, c1, d1, a2, b2, c2, d2, a3, b3, c3, d3));
                    break;
                }
                case 4: {
                    System.out.println("\n[Quadratic Equation: ax^2 + bx + c = 0]");
                    System.out.print("Enter coefficient a: ");
                    double a = sc.nextDouble();
                    System.out.print("Enter coefficient b: ");
                    double b = sc.nextDouble();
                    System.out.print("Enter constant c: ");
                    double c = sc.nextDouble();
                    System.out.println("\n" + solveQuadratic(a, b, c));
                    break;
                }
                case 5: {
                    System.out.println("\n[Cubic Equation: ax^3 + bx^2 + cx + d = 0]");
                    System.out.print("Enter coefficient a: ");
                    double a = sc.nextDouble();
                    System.out.print("Enter coefficient b: ");
                    double b = sc.nextDouble();
                    System.out.print("Enter coefficient c: ");
                    double c = sc.nextDouble();
                    System.out.print("Enter constant d: ");
                    double d = sc.nextDouble();
                    System.out.println("\n" + solveCubic(a, b, c, d));
                    break;
                }
                case 6: {
                    System.out.println("\n[Polynomial Evaluation & Root Finding]");
                    System.out.print("Enter degree of polynomial (n >= 1): ");
                    int deg = sc.nextInt();
                    if (deg < 1) {
                        System.out.println("Degree must be at least 1.");
                        break;
                    }
                    double[] coeffs = new double[deg + 1];
                    System.out.println("Enter coefficients from highest degree (x^" + deg + ") to constant term:");
                    for (int i = 0; i <= deg; i++) {
                        System.out.print("c[" + (deg - i) + "]: ");
                        coeffs[i] = sc.nextDouble();
                    }
                    System.out.print("Enter x value to evaluate P(x): ");
                    double xVal = sc.nextDouble();
                    double pVal = evaluatePolynomial(coeffs, xVal);
                    System.out.println("P(" + formatNum(xVal) + ") = " + formatNum(pVal));

                    System.out.print("Find a real root using Newton-Raphson? (y/n): ");
                    String ans = sc.next();
                    if (ans.equalsIgnoreCase("y")) {
                        System.out.print("Enter initial guess x0: ");
                        double guess = sc.nextDouble();
                        Double root = findPolynomialRoot(coeffs, guess, 100, 1e-7);
                        if (root != null) {
                            System.out.println("Found root near: x ≈ " + formatNum(root));
                            System.out.println("Verification P(" + formatNum(root) + ") = " + formatNum(evaluatePolynomial(coeffs, root)));
                        } else {
                            System.out.println("Could not converge to a root with initial guess " + guess + ".");
                        }
                    }
                    break;
                }
                default:
                    System.out.println("Invalid selection. Please choose an option from 0 to 6.");
            }
        }
    }

    public static void main(String[] args) {
        menu();
    }
}
