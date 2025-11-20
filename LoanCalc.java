// Computes the periodical payment necessary to pay a given loan.
public class LoanCalc {

    static double epsilon = 0.001;   // Approximation accuracy
    static int iterationCounter;     // Number of iterations

    // Gets the loan data and computes the periodical payment.
    public static void main(String[] args) {

        // Expects: loan amount (double), interest rate (double as percent), periods (int)
        double loan = Double.parseDouble(args[0]);
        double rate = Double.parseDouble(args[1]);
        int n = Integer.parseInt(args[2]);

        System.out.println("Loan = " + loan + ", interest rate = " + rate + "%, periods = " + n);

        // Computes the periodical payment using brute-force search
        System.out.print("\nPeriodical payment, using brute force: ");
        System.out.println((int) bruteForceSolver(loan, rate, n, epsilon));
        System.out.println("number of iterations: " + iterationCounter);

        // Computes the periodical payment using bi-section search
        System.out.print("\nPeriodical payment, using bi-section search: ");
        System.out.println((int) bisectionSolver(loan, rate, n, epsilon));
        System.out.println("number of iterations: " + iterationCounter);
    }

    // Computes the ending balance of a loan
    private static double endBalance(double loan, double rate, int n, double payment) {
        double balance = loan;
        for (int i = 0; i < n; i++) {
            balance = (balance - payment) * (1 + (rate / 100));
        }
        return balance;
    }

    // Sequential brute-force approximation
    public static double bruteForceSolver(double loan, double rate, int n, double epsilon) {
        double guess = loan / n;        // Initial guess
        iterationCounter = 0;

        while (endBalance(loan, rate, n, guess) > 0) {
            guess = guess + epsilon;
            iterationCounter++;
        }

        return guess;
    }

    // Bisection search solver
    public static double bisectionSolver(double loan, double rate, int n, double epsilon) {

        iterationCounter = 0;

        double lo = loan / n;
        double hi = loan;
        double mid = (lo + hi) / 2;

        while (hi - lo > epsilon) {
            if (endBalance(loan, rate, n, mid) * endBalance(loan, rate, n, lo) > 0) {
                lo = mid;
            } else {
                hi = mid;
            }
            mid = (lo + hi) / 2;
            iterationCounter++;
        }

        return mid;
    }
}

