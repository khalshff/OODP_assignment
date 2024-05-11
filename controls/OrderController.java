package controls;

import java.util.ArrayList;

import boundaries.OrderBoundary;
import entities.MenuItem;
import entities.Order;
import entities.OrderInvoice;
import entities.OrderItem;
import entities.PromoMenu;
import entities.Reservation;
import entities.Restaurant;
import entities.Staff;
import entities.Table;

// TODO: Auto-generated Javadoc
/**
 * The Class OrderController.
 */
public class OrderController {

	/**
	 * Creates the order for walk in.
	 *
	 * @param staffPresent the staff present
	 * @param table        the table
	 * @param restaurant   the restaurant
	 */
	public static void createOrderForWalkIn(Staff staffPresent, Table table, Restaurant restaurant) {
		ArrayList<Order> orders = restaurant.getOrders(); // get list of restaurant orders
		Order order = new Order(staffPresent, table); // create order
		orders.add(order); // add order to order list
		RestaurantController.saveRestaurant(restaurant);
	}

	/**
	 * Creates the order from reservation.
	 *
	 * @param staff       the staff
	 * @param reservation the reservation
	 * @param restaurant  the restaurant
	 */
	public static void createOrderFromReservation(Staff staff, Reservation reservation, Restaurant restaurant) {
		ArrayList<Order> orders = restaurant.getOrders();
		ArrayList<Reservation> reservations = restaurant.getReservations();
		ArrayList<Reservation> settledReservations = restaurant.getSettledReservations();
		Order order = new Order(staff, reservation);
		reservation.setAcceptanceStatus(); // update table & reservation status
		orders.add(order); // add order to order list
		reservations.remove(reservation); // remove reservation from reservation list
		settledReservations.add(reservation); // add reservation to settledReservation list
		RestaurantController.saveRestaurant(restaurant);
	}

	/**
	 * Adds the order item.
	 *
	 * @param quantity   the quantity
	 * @param tableNo    the table no
	 * @param menuItem   the menu item
	 * @param promoItem  the promo item
	 * @param restaurant the restaurant
	 */
	public static void addOrderItem(int quantity, int tableNo, MenuItem menuItem, PromoMenu promoItem,
			Restaurant restaurant) {
		Order addToOrder = getTableOrder(restaurant.getOrders(), tableNo); // get order of table
		OrderItem orderItemToAdd = getOrderItemFromList(addToOrder.getOrderItems(), menuItem, promoItem);
		if (orderItemToAdd == null) { // orderItem not added previously
			if (menuItem == null)
				orderItemToAdd = new OrderItem(promoItem, quantity);
			else
				orderItemToAdd = new OrderItem(menuItem, quantity);
			addToOrder.addOrderItems(orderItemToAdd);
		} else
			orderItemToAdd.setQuantity(orderItemToAdd.getQuantity() + quantity);
		RestaurantController.saveRestaurant(restaurant);
	}

	/**
	 * Removes the order item.
	 *
	 * @param order      the order
	 * @param orderItem  the order item
	 * @param quantity   the quantity
	 * @param restaurant the restaurant
	 */
	public static void removeOrderItem(Order order, OrderItem orderItem, int quantity, Restaurant restaurant) {
		order.removeOrderItem(quantity, orderItem);
		RestaurantController.saveRestaurant(restaurant);
	}

	/**
	 * Generate invoice.
	 *
	 * @param tableNo    the table no
	 * @param restaurant the restaurant
	 */
	public static void generateInvoice(int tableNo, Restaurant restaurant) {
		ArrayList<Order> orders = restaurant.getOrders();
		ArrayList<Order> settledOrders = restaurant.getSettledOrders();
		ArrayList<OrderInvoice> invoices = restaurant.getInvoices();
		Order order = getTableOrder(restaurant.getOrders(), tableNo);
		order.generateInvoice(); // create order invoice
		invoices.add(order.getOrderInvoice());
		orders.remove(order);
		settledOrders.add(order);
		RestaurantController.saveRestaurant(restaurant);
	}

	/**
	 * Check remove quantity.
	 *
	 * @param quantity     the quantity
	 * @param toBeRemoveOI the to be remove OI
	 * @return true, if successful
	 */
	public static boolean checkRemoveQuantity(int quantity, OrderItem toBeRemoveOI) {
		int finalQuantity = toBeRemoveOI.getQuantity() - quantity; // quantity after remove
		return (finalQuantity >= 1 || finalQuantity == 0) ? true : false;
	}

	/**
	 * Gets the table order.
	 *
	 * @param orders  the orders
	 * @param tableNo the table no
	 * @return the table order
	 */
	public static Order getTableOrder(ArrayList<Order> orders, int tableNo) {
		Order order = null;
		for (Order o : orders)
			if (o.getTable().getTableNumber() == tableNo)
				order = o;
		return order;
	}

	/**
	 * Gets the order item from list.
	 *
	 * @param orderItems the order items
	 * @param mi         the mi
	 * @param pi         the pi
	 * @return the order item from list
	 */
	public static OrderItem getOrderItemFromList(ArrayList<OrderItem> orderItems, MenuItem mi, PromoMenu pi) {
		OrderItem orderItem = null;
		String itemName = (mi == null) ? pi.getPromoName() : mi.getItemName();
		for (OrderItem oi : orderItems)
			if (oi.getName() == itemName)
				orderItem = oi;
		return orderItem;
	}

	/**
	 * Check order exist.
	 *
	 * @param orders the orders
	 * @param table  the table
	 * @return true, if successful
	 */
	public static boolean checkOrderExist(ArrayList<Order> orders, Table table) {
		boolean exist = false;
		for (Order o : orders)
			exist = (o.getTable().getTableNumber() == table.getTableNumber()) ? true : exist;
		return exist;
	}

	/**
	 * Display order options.
	 *
	 * @param restaurant the restaurant
	 */
	public static void displayOrderOptions(Restaurant restaurant) {
		OrderBoundary.orderOptions(restaurant);
	}
}
