package entities;

import java.io.Serializable;

// TODO: Auto-generated Javadoc
/**
 * The Class Staff.
 */
public class Staff extends Person implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The staff id. */
	private int staffId;

	/** The job title. */
	private String jobTitle;

	/**
	 * Instantiates a new staff.
	 *
	 * @param name     the name
	 * @param gender   the gender
	 * @param staffId  the staff id
	 * @param jobTitle the job title
	 */

	public Staff(String name, boolean gender, int staffId, String jobTitle) {
		super(name, gender);
		this.staffId = staffId;
		this.jobTitle = jobTitle;
	}

	/**
	 * Gets the staff id.
	 *
	 * @return the staff id
	 */
	public int getStaffId() {
		return staffId;
	}

	/**
	 * Sets the staff id.
	 *
	 * @param staffId the new staff id
	 */
	public void setStaffId(int staffId) {
		this.staffId = staffId;
	}

	/**
	 * Gets the job title.
	 *
	 * @return the job title
	 */
	public String getJobTitle() {
		return jobTitle;
	}

	/**
	 * Sets the job title.
	 *
	 * @param jobTitle the new job title
	 */
	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Staff [name=" + super.getName() + ", gender=" + super.getGender() + ", staffId=" + staffId
				+ ", jobTitle=" + jobTitle + "]";
	}

}
