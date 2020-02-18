package model;

import java.util.Random;

public class Yatzy {
	// Face values of the 5 dice.
	// 1 <= values[i] <= 6.
	private int[] values = new int[5];

	// Number of times the 5 dice have been thrown.
	// 0 <= throwCount <= 3.
	private int throwCount = 0;

	// Random number generator.
	private Random random = new Random();

	public Yatzy() {
		//
	}

	/**
	 * Returns the 5 face values of the dice.
	 */
	public int[] getValues() {
		return values;
	}

	/**
	 * Sets the 5 face values of the dice. Req: values contains 5 face values in
	 * [1..6]. Note: This method is only meant to be used for test, and
	 * therefore has package visibility.
	 */
	void setValues(int[] values) {
		
		this.values = values;
		// 
	}

	/**
	 * Returns the number of times the 5 dice has been thrown.
	 */
	public int getThrowCount() {
		// 
		return throwCount;
	}

	/**
	 * Resets the throw count.
	 */
	public void resetThrowCount() {
		throwCount = 0;
	}

	/**
	 * Rolls the 5 dice. Only roll dice that are not hold. Req: holds contain 5
	 * boolean values.
	 */
	public void throwDice(boolean[] holds) {
		for(int i =0; i < values.length; i++) {
			if(!holds[i]) {
				values[i] = random.nextInt(6) +1;
			} 
		}
		throwCount++;
	}

	// -------------------------------------------------------------------------

	/**
	 * Returns all results possible with the current face values. The order of
	 * the results is the same as on the score board. Note: This is an optional
	 * method. Comment this method out, if you don't want use it.
	 */
	public int[] getResults() {
		int[] results = new int[15];
		for (int i = 0; i <= 5; i++) {
			results[i] = this.sameValuePoints(i + 1);
		}
		results[6] = this.onePairPoints();
		results[7] = this.twoPairPoints();
		results[8] = this.threeSamePoints();
		results[9] = this.fourSamePoints();
		results[10] = this.fullHousePoints();
		results[11] = this.smallStraightPoints();
		results[12] = this.largeStraightPoints();
		results[13] = this.chancePoints();
		results[14] = this.yatzyPoints();

		return results;
	}

	// -------------------------------------------------------------------------

	// Returns an int[7] containing the frequency of face values.
	// Frequency at index v is the number of dice with the face value v, 1 <= v
	// <= 6.
	// Index 0 is not used.
	private int[] calcCounts() {
		int [] eyeCount = new int[7];
		for(int i = 0; i < values.length; i++) {
			eyeCount[values[i]]++;
		}
		return eyeCount;
	}

	/**
	 * Returns same-value points for the given face value. Returns 0, if no dice
	 * has the given face value. Requires: 1 <= value <= 6;
	 */
	public int sameValuePoints(int value) {
		int [] eyeCount = calcCounts();
		int result;
		result = eyeCount[value]* value;
		return result;
	}

	/**
	 * Returns points for one pair (for the face value giving highest points).
	 * Returns 0, if there aren't 2 dice with the same face value.
	 */
	public int onePairPoints() {
		int[] eyeCount = calcCounts();
		
		boolean pairFound = false;
		int i = 6;
		int sum = 0;
		
		while(!pairFound && i > 0) {
			if(eyeCount[i] >= 2) {
				sum = i * 2;
				pairFound = true;
				
			}
			i--;
			
		}
		return sum;
	}

	/**
	 * Returns points for two pairs (for the 2 face values giving highest
	 * points). Returns 0, if there aren't 2 dice with one face value and 2 dice
	 * with a different face value.
	 */
	public int twoPairPoints() {
		int[] eyeCount = calcCounts();
		
		int pairs = 0;
		int i = 6;
		int sum = 0;
		
		while(pairs < 2 && i > 0) {
			 if(eyeCount[i] >= 2 ) {
				sum += 2*i;
				pairs++;
			}
			i--;
			
		}
		if(pairs != 2) {
			return 0;
		} else {
			return sum;
		}
		
	}

	/**
	 * Returns points for 3 of a kind. Returns 0, if there aren't 3 dice with
	 * the same face value.
	 */
	public int threeSamePoints() {
	int[] eyeCount = calcCounts();
		
		boolean threeOfKind = false;
		int i = 6;
		int sum = 0;
		
		while(!threeOfKind && i > 0) {
			 if(eyeCount[i] >= 3 ) {
				sum += 3*i;
				threeOfKind = true;
			}
			i--;
			
		}
		return sum;
		
	}

	/**
	 * Returns points for 4 of a kind. Returns 0, if there aren't 4 dice with
	 * the same face value.
	 */
	public int fourSamePoints() {
	int[] eyeCount = calcCounts();
		
		boolean fourOfKind = false;
		int i = 6;
		int sum = 0;
		
		while(!fourOfKind && i > 0) {
			 if(eyeCount[i] >= 4 ) {
				sum += 4*i;
				fourOfKind = true;
			}
			i--;
			
		}
		return sum;
	
	}

	/**
	 * Returns points for full house. Returns 0, if there aren't 3 dice with one
	 * face value and 2 dice a different face value.
	 */
	public int fullHousePoints() {
		int[] eyeCount = calcCounts();
		boolean twoOfKind = false;
		boolean threeOfKind = false;
		int sum = 0;
		
		
		for(int i = 0; i < eyeCount.length; i++ ) {
			if(eyeCount[i] == 2) {
				twoOfKind = true;
				sum += i*2;
			} if(eyeCount[i] == 3) {
				threeOfKind = true;
				sum += i*3;
			}
			
		}
		if(twoOfKind && threeOfKind) {
			return sum;
		} else {
			return 0;
		}
		
	}

	/**
	 * Returns points for small straight. Returns 0, if the dice are not showing
	 * 1,2,3,4,5.
	 */
	public int smallStraightPoints() {
		int[] eyeCount = calcCounts();
		
		int result = 0;
		int i = 1;
		boolean isFound = false;
		
		while(!isFound && i < 6) {
			if(eyeCount[i] == 1) {
				result += i;
			} else {
				isFound = true;
				result = 0;
				
			}
			i++;
		}
		
		return result;
	}

	/**
	 * Returns points for large straight. Returns 0, if the dice is not showing
	 * 2,3,4,5,6.
	 */
	public int largeStraightPoints() {
	int[] eyeCount = calcCounts();
		
		int result = 0;
		int i = 2;
		boolean isFound = false;
		
		while(!isFound && i < 7) {
			if(eyeCount[i] == 1) {
				result += i;
			} else {
				isFound = true;
				result = 0;
				
			}
			i++;
		}
		
		return result;
	}

	/**
	 * Returns points for chance.
	 */
	public int chancePoints() {
		int result = 0;
		
		for(int i = 0; i < values.length; i++) {
			result += values[i];
		}
		if(result > 30) {
			result = 30;
		}
		return result;
	}

	/**
	 * Returns points for yatzy. Returns 0, if there aren't 5 dice with the same
	 * face value.
	 */
	public int yatzyPoints() {
	int[] eyeCount = calcCounts();
		
		boolean fourOfKind = false;
		int i = 6;
		int sum = 0;
		
		while(!fourOfKind && i > 0) {
			 if(eyeCount[i] >= 5 ) {
				sum = 50;
				fourOfKind = true;
			}
			i--;
			
		}
		return sum;
	}

}