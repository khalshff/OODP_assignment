package entities;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

// TODO: Auto-generated Javadoc
/**
 * The Class OrderInvoice.
 */
public class OrderInvoice implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The Constant GOODS_SERVICES_TAX. */
	private static final double GOODS_SERVICES_TAX = 0.07;

	/** The Constant SERVICE_CHARGE. */
	private static final double SERVICE_CHARGE = 0.10;

	/** The order invoice no. */
	private int orderInvoiceNo;

	/** The generated date time. */
	private Date generatedDateTime;

	/** The order. */
	private Order order;

	/** The sub total price. */
	private double subTotalPrice;

	/** The gst. */
	private double gst;

	/** The service charge. */
	private double serviceCharge;

	/** The total price. */
	private double totalPrice;

	/**
	 * The Enum PaymentType.
	 */
	public static enum PaymentType {

		/** The Credit card. */
		CREDITCARD,
		/** The Cash. */
		CASH
	}

	/** The paid by. */
	private PaymentType paidBy;

	/** The received amount. */
	private double receivedAmount;

	/**
	 * Instantiates a new order invoice.
	 *
	 * @param order the order
	 */
	public OrderInvoice(Order order) {
		this.orderInvoiceNo = Calendar.getInstance().hashCode();
		this.generatedDateTime = Calendar.getInstance().getTime();
		this.order = order;
		this.subTotalPrice = order.getSubTotalPrice();
		this.gst = this.subTotalPrice * GOODS_SERVICES_TAX;
		this.serviceCharge = this.subTotalPrice * SERVICE_CHARGE;
		this.totalPrice = this.subTotalPrice + this.gst + this.serviceCharge;
	}

	/**
	 * Gets the order invoice no.
	 *
	 * @return the order invoice no
	 */
	public int getOrderInvoiceNo() {
		return orderInvoiceNo;
	}

	/**
	 * Gets the generated date time.
	 *
	 * @return the generated date time
	 */
	public Date getGeneratedDateTime() {
		return generatedDateTime;
	}

	/**
	 * Sets the generated date time.
	 *
	 * @param generatedDateTime the new generated date time
	 */
	public void setGeneratedDateTime(Date generatedDateTime) {
		this.generatedDateTime = generatedDateTime;
	}

	/**
	 * Gets the order.
	 *
	 * @return the order
	 */
	public Order getOrder() {
		return order;
	}

	/**
	 * Gets the sub total price.
	 *
	 * @return the sub total price
	 */
	public double getSubTotalPrice() {
		return subTotalPrice;
	}

	/**
	 * Gets the gst.
	 *
	 * @return the gst
	 */
	public double getGst() {
		return gst;
	}

	/**
	 * Gets the service charge.
	 *
	 * @return the service charge
	 */
	public double getServiceCharge() {
		return serviceCharge;
	}

	/**
	 * Gets the total price.
	 *
	 * @return the total price
	 */
	public double getTotalPrice() {
		return totalPrice;
	}

	/**
	 * Gets the paid by.
	 *
	 * @return the paid by
	 */
	public PaymentType getPaidBy() {
		return paidBy;
	}

	/**
	 * Sets the paid by.
	 *
	 * @param paidBy the new paid by
	 */
	public void setPaidBy(PaymentType paidBy) {
		this.paidBy = paidBy;
	}

	/**
	 * Gets the received amount.
	 *
	 * @return the received amount
	 */
	public double getReceivedAmount() {
		return receivedAmount;
	}

	/**
	 * Sets the received amount.
	 *
	 * @param receivedAmount the new received amount
	 */
	public void setReceivedAmount(double receivedAmount) {
		this.receivedAmount = receivedAmount;
	}

	/**
	 * Change.
	 *
	 * @return the double
	 */
	public double change() {
		return receivedAmount - totalPrice;
	}

}
