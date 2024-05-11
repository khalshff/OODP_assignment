package entities;

import java.io.Serializable;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

// TODO: Auto-generated Javadoc
/**
 * The Class Reservation.
 */
public class Reservation implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The reservation id. */
	private int reservationId;

	/** The customer name. */
	private String customerName;

	/** The customer contact. */
	private int customerContact;

	/** The number of pax. */
	private int numOfPax;

	/** The reserve date time. */
	private Date reserveDateTime;

	/** The table reserved. */
	private Table tableReserved;

	/** The acceptance status. */
	private boolean acceptanceStatus;

	/**
	 * Instantiates a new reservation.
	 *
	 * @param customerName    the customer name
	 * @param customerContact the customer contact
	 * @param numOfPax        the num of pax
	 * @param reserveDateTime the reserve date time
	 * @param tableReserved   the table reserved
	 */
	public Reservation(String customerName, int customerContact, int numOfPax, Date reserveDateTime,
			Table tableReserved) {
		this.customerName = customerName;
		this.customerContact = customerContact;
		this.numOfPax = numOfPax;
		this.reserveDateTime = reserveDateTime;
		this.tableReserved = tableReserved;
		this.acceptanceStatus = false;
	}

	/**
	 * Gets the reservation id.
	 *
	 * @return the reservation id
	 */
	public int getReservationId() {
		return reservationId;
	}

	/**
	 * Gets the num of pax.
	 *
	 * @return the num of pax
	 */
	public int getNumOfPax() {
		return numOfPax;
	}

	/**
	 * Sets the num of pax.
	 *
	 * @param numOfPax the new num of pax
	 */
	public void setNumOfPax(int numOfPax) {
		this.numOfPax = numOfPax;
	}

	/**
	 * Gets the customer name.
	 *
	 * @return the customer name
	 */
	public String getCustomerName() {
		return customerName;
	}

	/**
	 * Sets the customer name.
	 *
	 * @param customerName the new customer name
	 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	/**
	 * Gets the customer contact.
	 *
	 * @return the customer contact
	 */
	public long getCustomerContact() {
		return customerContact;
	}

	/**
	 * Sets the customer contact.
	 *
	 * @param customerContact the new customer contact
	 */
	public void setCustomerContact(int customerContact) {
		this.customerContact = customerContact;
	}

	/**
	 * Gets the reserve date time.
	 *
	 * @return the reserve date time
	 */
	public Date getReserveDateTime() {
		return reserveDateTime;
	}

	/**
	 * Sets the reserve date time.
	 *
	 * @param reserveDateTime the new reserve date time
	 */
	public void setReserveDateTime(Date reserveDateTime) {
		this.reserveDateTime = reserveDateTime;
	}

	/**
	 * Gets the table reserved.
	 *
	 * @return the table reserved
	 */
	public Table getTableReserved() {
		return tableReserved;
	}

	/**
	 * Gets the acceptance status.
	 *
	 * @return the acceptance status
	 */
	public String getAcceptanceStatus() {
		if (acceptanceStatus == true)
			return "Accepted";
		else
			return "Pending";
	}

	/**
	 * Sets the acceptance status.
	 */
	public void setAcceptanceStatus() {
		this.acceptanceStatus = true;
		this.tableReserved.setStatus(Table.TableStatus.OCCUPIED);
	}
}
