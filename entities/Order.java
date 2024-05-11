
package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

// TODO: Auto-generated Javadoc
/**
 * The Class Order.
 */
public class Order implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The order id. */
	private int orderId;

	/** The date time created. */
	private Date dateTimeCreated;

	/** The staff. */
	private Staff staff;

	/** The table. */
	private Table table;

	/** The reservation. */
	private Reservation reservation;

	/** The order invoice. */
	private OrderInvoice orderInvoice;

	/** The order items. */
	private ArrayList<OrderItem> orderItems;

	/**
	 * Instantiates a new order.
	 *
	 * @param staff       the staff
	 * @param reservation the reservation
	 */
	public Order(Staff staff, Reservation reservation) {
		this.orderId = Calendar.getInstance().hashCode();
		this.dateTimeCreated = Calendar.getInstance().getTime();
		this.staff = staff;
		this.table = reservation.getTableReserved();
		this.reservation = reservation;
		this.orderInvoice = null;
		this.orderItems = new ArrayList<OrderItem>();
		this.table.setStatus(Table.TableStatus.OCCUPIED);
	}

	/**
	 * Instantiates a new order.
	 *
	 * @param staff the staff
	 * @param table the table
	 */
	public Order(Staff staff, Table table) {
		this.orderId = Calendar.getInstance().hashCode();
		this.dateTimeCreated = Calendar.getInstance().getTime();
		this.staff = staff;
		this.table = table;
		this.reservation = null;
		this.orderInvoice = null;
		this.orderItems = new ArrayList<OrderItem>();
		this.table.setStatus(Table.TableStatus.OCCUPIED);
	}

	/**
	 * Gets the order id.
	 *
	 * @return the order id
	 */
	public int getOrderId() {
		return orderId;
	}

	/**
	 * Gets the date time created.
	 *
	 * @return the date time created
	 */
	public Date getDateTimeCreated() {
		return dateTimeCreated;
	}

	/**
	 * Sets the date time created.
	 *
	 * @param dateTime the new date time created
	 */
	public void setDateTimeCreated(Date dateTime) {
		this.dateTimeCreated = dateTime;
	}

	/**
	 * Gets the staff.
	 *
	 * @return the staff
	 */
	public Staff getStaff() {
		return staff;
	}

	/**
	 * Gets the table.
	 *
	 * @return the table
	 */
	public Table getTable() {
		return table;
	}

	/**
	 * Gets the reservation.
	 *
	 * @return the reservation
	 */
	public Reservation getReservation() {
		return reservation;
	}

	/**
	 * Gets the order invoice.
	 *
	 * @return the order invoice
	 */
	public OrderInvoice getOrderInvoice() {
		return orderInvoice;
	}

	/**
	 * Sets the order invoice.
	 *
	 * @param orderInvoice the new order invoice
	 */
	public void setOrderInvoice(OrderInvoice orderInvoice) { // call this after creating order invoice
		this.orderInvoice = orderInvoice;
	}

	/**
	 * Gets the sub total price.
	 *
	 * @return the sub total price
	 */
	public double getSubTotalPrice() {
		double subtotal = 0.0;
		for (OrderItem orderItem : orderItems)
			subtotal += orderItem.getTotalCost();
		return subtotal;
	}

	/**
	 * Gets the order items.
	 *
	 * @return the order items
	 */
	public ArrayList<OrderItem> getOrderItems() {
		return orderItems;
	}

	/**
	 * Adds the order items.
	 *
	 * @param orderItem the order item
	 */
	public void addOrderItems(OrderItem orderItem) {
		this.orderItems.add(orderItem);
	}

	/**
	 * Removes the order item.
	 *
	 * @param quantity     the quantity
	 * @param toBeRemoveOI the to be remove OI
	 */
	public void removeOrderItem(int quantity, OrderItem toBeRemoveOI) {
		int finalQuantity = toBeRemoveOI.getQuantity() - quantity; // quantity after remove
		if (finalQuantity >= 1)
			toBeRemoveOI.setQuantity(finalQuantity);
		else if (finalQuantity == 0) // quantity 0 remove item from list
			this.orderItems.remove(toBeRemoveOI);
	}

	/**
	 * Generate invoice.
	 */
	public void generateInvoice() {
		if (this.orderInvoice != null)
			return;
		this.orderInvoice = new OrderInvoice(this);
		this.table.setStatus(Table.TableStatus.VACATED);
	}

}
