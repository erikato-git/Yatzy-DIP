package model;

/**
 * This class models one Die that can be rolled.
 */
public class Die {
	private int sides;
	private int faceValue;

	/**
	 * Constructs a die with six sides.
	 */
	public Die() {
		this(6);
	}

	/**
	 * Constructs a die with n sides.
	 * @param sides the number of sides.
	 */
	public Die(int sides) {
		this.sides = sides;
		this.faceValue = 1;
	}

	/**
	 * Rolls the die.
	 */
	public void roll() {
		faceValue = (int) ((Math.random() * sides) + 1);
	}

	/**
	 * Turn the die with a specific face value facing up.
	 * @param value the value to face up on the die.
	 */
	public void setFaceValue(int value) {
		if (value > 0 && value <= sides) {
			faceValue = value;
		}
	}

	/**
	 * Read the die value facing up right now.
	 * @return the value of the face
	 */
	public int getFaceValue() {
		return faceValue;
	}

}