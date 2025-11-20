// Computes the periodical payment necessary to pay a given loan.
public class LoanCalc {

	//static so all functions can use it
	static int iterationCounter=0;

    public static void main(String[] args) {

        double loan = Double.parseDouble(args[0]);
		double rate = Double.parseDouble(args[1]);
		int n = Integer.parseInt(args[2]);
		double epsilon = 0.001;

		// First try the Brute Force Algorithm
        double payment = sequentialApprox(loan, rate, n, epsilon);
        //System.out.printf("About to call endBalance, loan=%.2f%n, rate=%.2f%n, n=%d%n, payment=%.4f%n", loan, rate, n, payment);
        double finalBalance = endBalance(loan, rate, n, payment);
        System.out.printf("BRUTE FORCE: Best payment found: %.0f%n", payment);
        System.out.printf("Final balance after %d payments: %.4f%n", n, finalBalance);
        System.out.printf("iteration Counter: %d%n", iterationCounter);

		  //Then try bi-section search
        double newPayment = bisectionSearch(loan, rate, n, epsilon);
        //System.out.printf("About to call endBalance, loan=%.2f%n, rate=%.2f%n, n=%d%n, payment=%.4f%n", loan, rate, n, newPayment);
        finalBalance  = endBalance(loan, rate, n, newPayment);
        System.out.printf("BI-SECTION: Best payment found: %.0f%n", newPayment);
        System.out.printf("Final balance after %d payments: %.4f%n", n, finalBalance);
        System.out.printf("iteration Counter: %d%n", iterationCounter);
        
    }

    // Given an initial balance, interest rate, number of payments and payment amount
     // return the balance at the end of the payment period
     //
    static double endBalance(double loan, 
                       		 double rate,
                    		 int n, 
                    	     double payment) {
        
        //System.out.printf("In endBalance, loan=%.2f%n, rate=%.2f%n, n=%d%n, payment=%.2f%n", loan, rate, n, payment);
        
       // int i=0;
       double balance = loan;

        // Loop through each period to find balance
       for (int i = 0 ; i < n; i++) {
    balance = (balance - payment) * (1 + rate/100);  
}
        return balance;    
       
    }


    /*1. BRUTE FORCE APPROACH - Simulate the loan balance given a candidate payment
      2. Scan possible payments from a low value upward in small steps (epsilon).
      3. Keep the payment that leaves a final balance closest to zero*/
    static double sequentialApprox (double loan,
                                    double ratePerPeriod,
                                    int    n,
                                    double epsilon) {
    
       // set the initial guess of the brute force to g = loan/n.
        double payment = loan / n;

        // A reasonable upper bound 
        double maxPayment = loan * 2.0;

        double bestPayment = payment;
        double bestAbsBalance = Double.MAX_VALUE;

        // Reset iteration counter
        iterationCounter=0;

        while (payment <= maxPayment) {

            double balance = endBalance(loan, ratePerPeriod, n, payment);
            double absBalance = Math.abs(balance);

            // If this candidate gives a better (closer to zero) balance, remember it
			// we need this because we may never acutallly be within epsilon of 0, and if we
			// don't remember which value brought us closest, we will just end up with the l
			// the last one we checked (which may be the MAX_VALYE)
           if (absBalance < bestAbsBalance) {
                bestAbsBalance = absBalance;
                bestPayment = payment;
            }

            // If we are already within epsilon of zero balance, we can stop early
            if (absBalance <= epsilon) {
                break;
            }

            // Move to the next candidate payment
            payment += epsilon;
			iterationCounter++;
        }
		
         return Math.round(bestPayment);

    }    


	static double bisectionSearch (double loan,
								   double rate,
								   int    n,
								   double epsilon) {

		// Reset the iteration counter
		iterationCounter = 0;

		// ----- 1. Choose initial lo and hi -----
		// Choose initial lo and hi values, using similar considerations 
		// to what we did in the brute force search.
		double lo = loan / n;
        double hi = loan * 2.0;

		double fLo = endBalance(loan, rate, n, lo);
        double fHi = endBalance(loan, rate, n, hi);

		// We need f(lo) and f(hi) to have opposite signs
		 if (fLo * fHi > 0) {
            throw new IllegalStateException(
                "Bisection requires endBalance(lo) and endBalance(hi) to have opposite signs");
        }

		// ----- 2. Bisection loop -----
		while ((hi - lo) > epsilon) {
            iterationCounter++;

            double mid = (lo + hi) / 2.0;
            double fMid = endBalance(loan, rate, n, mid);

			// If we hit the root exactly, we can stop
		  if (fMid == 0.0) {
                return Math.round(mid);
            }

			// Decide which half contains the root:
			// If f(mid) has the same sign as f(lo), root is between mid and hi
			  if (fMid * fLo > 0) {
                lo = mid;
                fLo = fMid;     // update f(lo) to avoid recomputing
            } else {
                hi = mid;
                fHi = fMid;     // update f(hi)
            }
        }

		// Final approximation of the root (payment) is the midpoint
	    System.out.printf("About to return payment of  %.2f%n",(lo + hi) / 2.0 );
        return Math.round((lo + hi) / 2.0);
	}


}