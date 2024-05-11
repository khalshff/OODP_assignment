/*
 * 
 */
package entities;

import java.io.Serializable;

// TODO: Auto-generated Javadoc
/**
 * The Class Person.
 */
public class Person implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The name. */
	private String name;

	/** The gender. */
	private boolean gender;

	/**
	 * Instantiates a new person.
	 *
	 * @param name   the name
	 * @param gender the gender
	 */
	public Person(String name, boolean gender) {
		this.name = name;
		this.gender = gender;
	}

	/**
	 * Instantiates a new person.
	 */
	public Person() {

	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the gender.
	 *
	 * @return the gender
	 */
	public String getGender() {
		if (this.gender == false)
			return "Male";
		else
			return "Female";
	}

	/**
	 * Checks if is gender.
	 *
	 * @return true, if is gender
	 */
	public boolean isGender() {
		return this.gender;
	}

	/**
	 * Sets the gender.
	 *
	 * @param gender the new gender
	 */
	public void setGender(boolean gender) {
		this.gender = gender;
	}

}
