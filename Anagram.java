import java.util.Arrays;
import java.util.Random;

/** Functions for checking if a given string is an anagram. */
public class Anagram {
	public static void main(String args[]) {
		// Tests the isAnagram function.
		System.out.println(isAnagram("silent","listen"));  // true
		System.out.println(isAnagram("William Shakespeare","I am a weakish speller")); // true
		System.out.println(isAnagram("Madam Curie","Radium came")); // true
		System.out.println(isAnagram("Tom Marvolo Riddle","I am Lord Voldemort")); // true

		// Tests the preProcess function.
		System.out.println(preProcess("WhAt? No way!!!"));
		
		// Tests the randomAnagram function.
		System.out.println("silent and " + randomAnagram("silent") + " are anagrams.");
		
		// Performs a stress test of randomAnagram 
		String str = "1234567";
		Boolean pass = true;
		//// 10 can be changed to much larger values, like 1000
		for (int i = 0; i < 10; i++) {
			String randomAnagram = randomAnagram(str);
			System.out.println(randomAnagram);
			pass = pass && isAnagram(str, randomAnagram);
			if (!pass) break;
		}
		System.out.println(pass ? "test passed" : "test Failed");
	}  
	
	// Returns true if the two given strings are anagrams, false otherwise.
	public static boolean isAnagram(String str1, String str2) {
		// Replace the following statement with your code
		// First I need to preProcess the strings (make lower, no spaces, no puncs)
		// Then I need to compare same letters. Maybe arrange in alphabetical order 
		// and then go one by one on both strings
		
		String newStr1=preProcess(str1);
		String newStr2=preProcess(str2);
		//System.out.println("New str1: " + newStr1);
		//System.out.println("New str2: " + newStr2);

		//If strings are different lengths then not anagram 
		// (check this first so we can later loop and not get out of bounds)
		if (newStr1.length() != newStr2.length()) {
			return false;
		}

		//If I made it here - means strings are same length
		//Now I can alphabetize them
		newStr1 = sortStrAlphabetically(newStr1);
		newStr2 = sortStrAlphabetically(newStr2);
		//System.out.println("Sorted str1: " + newStr1);
		//System.out.println("Sorted str2: " + newStr2);

		//Now I can loop over each letter and see if it's the same:

		for (int i = 0; i < newStr1.length(); i++) {
			char c1 = newStr1.charAt(i);
			char c2 = newStr2.charAt(i);
			if (c1 != c2) {
				return false;
			}			
		}
		// If we made it here, then the length is the same and all chars are the same

		return true;
	}
	   
	
	public static String preProcess(String str) {
		// Replace the following statement with your code
		// I need to make all lower and remove all special characters
		String originalStr = str;
		String processedString;

		//System.out.println("Original string: " + originalStr);
		processedString = originalStr.toLowerCase();	
		//System.out.println("New string after lower: " + processedString);

		processedString = processedString.replaceAll("[^a-zA-Z0-9]", "");
		//System.out.println("New string after removing special chars: " + processedString);

		return processedString;
	} 
	   
	// Returns a random anagram of the given string. The random anagram consists of the same
	// characters as the given string, re-arranged in a random order. 
	public static String randomAnagram(String origStr) {
		// Replace the following statement with your code
		// Tip: One way to implement this function is to 
		// use a loop that draws a random character from the
		// string and then deletes the selected character 
		// from the string (so that we will not select it again).
		//System.out.println("My input string is: " + origStr);

		 // Create a Random object
        Random random = new Random();
		String newStr ="";

		while (origStr.length() > 0) {
        	// Generate a random index number between 0 (inclusive) and the string length (exclusive)
        	int randomIndex = random.nextInt(origStr.length());

       	 	// Get the character at the generated random index
       	 	char randomChar = origStr.charAt(randomIndex);

        	// Print the result
        	//System.out.println("Original String: " + origStr);
        	//System.out.println("Random Character: " + randomChar);

			//Make the random charcater the first charcter in the newStr
			newStr = newStr + randomChar;

			//Remove the randomChar so we don't call it agian:
			origStr =  (origStr.substring(0, randomIndex) + origStr.substring(randomIndex+1));
		}
		return newStr;
	}

	public static String sortStrAlphabetically(String inputStr) {
        //Convert the input String to a char array
        char[] charArray = inputStr.toCharArray();

        //Sort the char array alphabetically 
        Arrays.sort(charArray);

        //Convert the sorted char array back to a String
        String sortedStr = new String(charArray);
	

        return sortedStr;
    }

}
