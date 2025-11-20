// Implements algebraic operations and the square root function without using 
// the Java operations a + b, a - b, a * b, a / b, a % b, and without calling 
// Math.sqrt. All the functions in this class operate on int values and
// return int values.

/*  IMPLEMENTATION TIPS 
(1) To compute a + b, we can add 1 to a, b times. Implementing minus is a similar idea.
(2) When writing a function, always try to do it using other functions that you’ve already
implemented. For example, times can be implemented using plus.
(3) Implement the functions in the order in which they appear in the class.
(4) In this exercise we don’t worry about efficiency. We only care about elegance. */

public class Algebra {
	public static void main(String args[]) {
	    // Tests some of the operations
	    System.out.println(plus(2,3));   // 2 + 3
	    System.out.println(minus(7,2));  // 7 - 2
   		System.out.println(minus(2,7));  // 2 - 7
 		System.out.println(times(3,4));  // 3 * 4
   		System.out.println(plus(2,times(4,2)));  // 2 + 4 * 2
   		System.out.println(pow(5,3));      // 5^3
   		System.out.println(pow(3,5));      // 3^5
   		System.out.println(div(12,3));   // 12 / 3    
   		System.out.println(div(5,5));    // 5 / 5  
   		System.out.println(div(25,7));   // 25 / 7
   		System.out.println(mod(25,7));   // 25 % 7
   		System.out.println(mod(120,6));  // 120 % 6    
   		System.out.println(sqrt(36));
		System.out.println(sqrt(263169));
   		System.out.println(sqrt(76123));
	}  

	// Returns x1 + x2
	public static int plus(int x1, int x2) {
		int result = x1;
        if (x2 >= 0) {
            for (int i = 0; i < x2; i++) result++;
        } else {
            for (int i = 0; i < -x2; i++) result--;
        }
        return result;
	}

	// Returns x1 - x2
	public static int minus(int x1, int x2) {
		return plus(x1, -x2);
	}

	// Returns x1 * x2
	public static int times(int x1, int x2) {
		 int result = 0;
        boolean negative = false;
        if (x2 < 0) { x2 = -x2; negative = true; }
        for (int i = 0; i < x2; i++) result = plus(result, x1);
        if (negative) result = -result;
		return result;
	}

	// Returns x^n (for n >= 0)
	public static int pow(int x, int n) {
		    if (n == 0) return 1;
        int result = 1;
        for (int i = 0; i < n; i++) result = times(result, x);
        return result;
		
	}

	// Returns the integer part of x1 / x2 
	public static int div(int x1, int x2) {
	if (x2 == 0) throw new ArithmeticException("Division by zero");
        boolean negative = (x1 < 0) ^ (x2 < 0); 
        x1 = Math.abs(x1);
        x2 = Math.abs(x2);

        int count = 0;
        while (x1 >= x2) {
            x1 = minus(x1, x2);
            count++;
        }
        return negative ? -count : count;
	}

	// Returns x1 % x2
	public static int mod(int x1, int x2) {
		  if (x2 == 0) throw new ArithmeticException("Modulo by zero");
        int remainder = minus(x1, times(div(x1, x2), x2));
        if (x1 < 0 && remainder != 0) remainder = plus(remainder, x2);
        return remainder;
	}	

	// Returns the integer part of sqrt(x) 
	public static int sqrt(int x) {
        int result = 0;
        while (times(result + 1, result + 1) <= x) result++;
        return result;
	}	  	  
}