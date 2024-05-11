package entities;

import java.io.Serializable;

// TODO: Auto-generated Javadoc
/**
 * The Class Table.
 */
public class Table implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * The Enum for TableStatus.
	 */
	public enum TableStatus {

		/** Table is occupied. */
		OCCUPIED,
		/** Table is reserved. */
		RESERVED,
		/** Table is vacated. */
		VACATED
	}

	/** The table number. */
	private int tableNumber;

	/** The capacity of the Table. */
	private int capacity; // even sizes, with minimum of 2 and maximum of 10 pax

	/** The status of the Table. */
	private TableStatus status;

	/**
	 * Instantiates a new table.
	 *
	 * @param tableNumber the table number
	 * @param capacity    the capacity
	 */
	public Table(int tableNumber, int capacity) {
		this.tableNumber = tableNumber;
		this.capacity = capacity;
		this.status = TableStatus.VACATED;
	}

	/**
	 * Gets the table number.
	 *
	 * @return the table number
	 */
	public int getTableNumber() {
		return tableNumber;
	}

	/**
	 * Gets the capacity.
	 *
	 * @return the capacity
	 */
	public int getCapacity() {
		return capacity;
	}

	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public TableStatus getStatus() {
		return status;
	}

	/**
	 * Sets the status.
	 *
	 * @param status the new status
	 */
	public void setStatus(TableStatus status) {
		this.status = status;
	}

	/**
	 * Free table and set status as vacated.
	 *
	 * @param tableNum the table id
	 */
	public void freeTable(int tableNum) {
		tableNumber = tableNum;
		this.status = TableStatus.VACATED;

	}

}
