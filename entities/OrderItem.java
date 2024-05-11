package entities;

import java.io.Serializable;
import java.util.Calendar;

// TODO: Auto-generated Javadoc
/**
 * The Class OrderItem.
 */
public class OrderItem implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** MenuItem of the Ordered Item. */
	private MenuItem menuItem;

	/** The promo item. */
	private PromoMenu promoItem;

	/** The quantity. */
	private int quantity;

	/** The order id. */
	private int orderId;

	/**
	 * The Enum OrderItemCategory.
	 */
	public static enum OrderItemCategory {

		/** The ala carte. */
		ALA_CARTE,
		/** The promo. */
		PROMO
	}

	/** The category. */
	private OrderItemCategory category;

	/**
	 * Creates a new OrderItem from the MenuItem.
	 *
	 * @param menuItem the menu item
	 */
	public OrderItem(MenuItem menuItem) {
		this.orderId = Calendar.getInstance().hashCode();
		this.menuItem = menuItem;
		this.quantity = 1;
		this.category = OrderItemCategory.ALA_CARTE;
	}

	/**
	 * Instantiates a new order item.
	 *
	 * @param menuItem the menu item
	 * @param quantity the quantity
	 */
	public OrderItem(MenuItem menuItem, int quantity) {
		this.orderId = Calendar.getInstance().hashCode();
		this.menuItem = menuItem;
		this.quantity = quantity;
		this.category = OrderItemCategory.ALA_CARTE;
	}

	/**
	 * Instantiates a new order item.
	 *
	 * @param promoItem the promo item
	 */
	public OrderItem(PromoMenu promoItem) {
		this.orderId = Calendar.getInstance().hashCode();
		this.promoItem = promoItem;
		this.quantity = 1;
		this.category = OrderItemCategory.PROMO;
	}

	/**
	 * Instantiates a new order item.
	 *
	 * @param promoItem the promo item
	 * @param quantity  the quantity
	 */
	public OrderItem(PromoMenu promoItem, int quantity) {
		this.orderId = Calendar.getInstance().hashCode();
		this.promoItem = promoItem;
		this.quantity = quantity;
		this.category = OrderItemCategory.PROMO;
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
	 * Gets the quantity.
	 *
	 * @return the quantity
	 */
	public int getQuantity() {
		return quantity;
	}

	/**
	 * Sets the quantity.
	 *
	 * @param quantity the new quantity
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	/**
	 * Gets the category.
	 *
	 * @return the category
	 */
	public OrderItemCategory getCategory() {
		return category;
	}

	/**
	 * Gets the MenuItem.
	 *
	 * @return this OrderItem's menu item.
	 */
	public MenuItem getMenuItem() {
		return menuItem;
	}

	/**
	 * Gets the promo item.
	 *
	 * @return the promo item
	 */
	public PromoMenu getPromoItem() {
		return promoItem;
	}

	/**
	 * Get the Price of the order item.
	 *
	 * @return the order item price
	 */
	public double getPrice() {
		if (this.category == OrderItemCategory.ALA_CARTE)
			return menuItem.getItemPrice();
		if (this.category == OrderItemCategory.PROMO)
			return promoItem.getPromoPrice();
		return 0;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		if (this.category == OrderItemCategory.ALA_CARTE)
			return menuItem.getItemName();
		if (this.category == OrderItemCategory.PROMO)
			return promoItem.getPromoName();
		return "";
	}

	/**
	 * Gets the total cost.
	 *
	 * @return the total cost
	 */
	public double getTotalCost() {
		if (this.category == OrderItemCategory.ALA_CARTE)
			return menuItem.getItemPrice() * quantity;
		if (this.category == OrderItemCategory.PROMO)
			return promoItem.getPromoPrice() * quantity;
		return 0;
	}

}
