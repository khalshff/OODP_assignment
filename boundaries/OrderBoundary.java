package boundaries;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

import controls.OrderController;
import controls.ReservationController;
import controls.RestaurantController;
import controls.StaffController;
import controls.TableController;
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
 * The Class OrderBoundary.
 */
public class OrderBoundary {

	/** The sc. */
	static Scanner sc = new Scanner(System.in);

	/**
	 * Order options.
	 *
	 * @param restaurant the restaurant
	 */
	public static void orderOptions(Restaurant restaurant) {

		int choice = 0;
		do {
			RestaurantController.generateHeader("Manage Order");
			System.out.println("1. Create Order");
			System.out.println("2. View Order");
			System.out.println("3. Add OrderItem");
			System.out.println("4. Remove OrderItem");
			System.out.println("5. Generate invoice");
			System.out.println("0. Back");
			try {
				System.out.print("\nPlease enter your choice (1-5): ");
				choice = sc.nextInt();
				sc.nextLine();
				switch (choice) {
				case 0:
					return;
				case 1: // Create Order
					displayCreateOrder(restaurant);
					break;
				case 2: // View Order
					displayViewOrder(restaurant);
					break;
				case 3: // Add Order Item
					displayAddOrderItem(restaurant);
					break;
				case 4: // Remove Order Item
					displayRemoveOrderItem(restaurant);
					break;
				case 5: // Generate Invoice
					displayPrintInvoice(restaurant);
					break;
				default:
					System.out.println("Invalid input!");
					break;
				}
			} catch (InputMismatchException ex) {
				System.out.println("Invalid input! Please enter choice (1-5).");
				sc.nextLine(); // Clear the garbage input
				continue;
			}
		} while (choice != 0);
		return;
	}

	/**
	 * Display create order.
	 *
	 * @param restaurant the restaurant
	 */
	private static void displayCreateOrder(Restaurant restaurant) {
		int choice = 0;
		do {
			RestaurantController.generateHeader("Create Order:");
			System.out.println("1. Walk-in");
			System.out.println("2. From Reservation");
			System.out.println("0. Back");

			try {
				System.out.print("\nPlease enter your choice (0-2): ");
				choice = sc.nextInt();
				sc.nextLine();
				switch (choice) {
				case 0:
					return;
				case 1: // Walk-in
					ArrayList<Table> vacatedTables = TableController.getTableByType(restaurant.getTables(),
							Table.TableStatus.VACATED);
					System.out.println("Available Tables: ");
					for (Table t : vacatedTables)
						System.out.println("Table " + t.getTableNumber() + " (" + t.getCapacity() + " seats)");
					System.out.println("0. Cancel");
					int selection = 0;
					do {
						System.out.print("Select Table No.: ");
						selection = sc.nextInt();
						if (selection == 0) // Back
							return;
						Table selectedTable = TableController.getTable(selection, vacatedTables);
						if (selectedTable != null) {
							System.out.println(new String(new char[50]).replace("\0", "-"));
							Staff staffPresent = StaffController.askStaff(restaurant.getStaffs()); // ask which staff
							OrderController.createOrderForWalkIn(staffPresent, selectedTable, restaurant); // create
																											// order
							System.out.println("Successfully Created Order for walk-in.");
							selection = 0;
						} else
							System.out.println("Selection invalid.");
					} while (selection != 0);
					choice = 0;
					break;
				case 2: // From Reservation
					int customerNo = 0;
					do {
						try {
							System.out.print("\nEnter Customer Contact (0 to cancel): ");
							customerNo = sc.nextInt();
							if (customerNo == 0) // Back
								return;
							if (String.valueOf(customerNo).length() != 8)
								System.out.println("Customer Number must contain 8 digits.");
							else {
								Reservation reservation = ReservationController
										.searchReservation(restaurant.getReservations(), customerNo, restaurant);
								if (reservation != null) {
									Staff staffPresent = StaffController.askStaff(restaurant.getStaffs()); // ask staff
									OrderController.createOrderFromReservation(staffPresent, reservation, restaurant);
									System.out.println("Successfully Created Order for Reservation by "
											+ reservation.getCustomerName());
								} else
									System.out.println("No Reservations found under that customer number.");
								customerNo = 0;
							}
						} catch (InputMismatchException ex) {
							System.out.println("Invalid input! Please enter numerical numbers only.");
							sc.nextLine(); // Clear the garbage input
							continue;
						}
					} while (String.valueOf(customerNo).length() != 8 && customerNo != 0);
					choice = 0;
					break;
				default:
					System.out.println("! Please enter only choice (0-2).");
					break;
				}
			} catch (InputMismatchException ex) {
				System.out.println("Invalid input! Please enter choice (0-2).");
				sc.nextLine(); // Clear the garbage input
				continue;
			}
		} while (choice != 0);
	}

	/**
	 * Display view order.
	 *
	 * @param restaurant the restaurant
	 */
	private static void displayViewOrder(Restaurant restaurant) {
		int selection = 0;
		ArrayList<Table> occupiedTables = TableController.getTableByType(restaurant.getTables(),
				Table.TableStatus.OCCUPIED);
		RestaurantController.generateHeader("View Orders:");
		if (occupiedTables.size() == 0) {
			System.out.println("There are currently no orders to view.");
			return;
		}
		for (Table t : occupiedTables)
			System.out.println("Table " + t.getTableNumber() + " (" + t.getCapacity() + " seats)");
		do {
			System.out.print("Select Table No. (0 to cancel): ");
			selection = sc.nextInt();
			if (selection == 0) // Back
				return;
			Table selectedTable = TableController.getTable(selection, occupiedTables);
			if (selectedTable != null) {
				Order selectedOrder = OrderController.getTableOrder(restaurant.getOrders(), selection);
				printLine(50, "-");
				System.out.printf("%-15s %s \n", "Order Id: ", selectedOrder.getOrderId());
				System.out.printf("%-15s %s \n", "Created Time: ",
						dateFormat("dd/MM/yyyy HH:mm", selectedOrder.getDateTimeCreated()));
				System.out.printf("%-15s %s \n", "Created By: ", selectedOrder.getStaff().getName());
				if (selectedOrder.getReservation() != null) // print reservation details if any
					System.out.printf("%-15s Reservation: ", selectedOrder.getReservation().getCustomerName() + " ( "
							+ selectedOrder.getReservation().getCustomerContact() + ")");
				System.out.printf("%-15s $ %1.2f\n\n", "Total Cost: ", selectedOrder.getSubTotalPrice());
				System.out.println("Current Items: ");
				if (selectedOrder.getOrderItems().size() == 0)
					System.out.println("No order items yet.");
				else {
					System.out.printf("%-25s%-15s%-10s\n", "Item Name", "Quantity", "SubTotal");
					for (OrderItem oi : selectedOrder.getOrderItems())
						System.out.printf("%-25s%-15d$%-7.2f\n\n", oi.getName(), oi.getQuantity(), oi.getTotalCost());
				}
				selection = 0;
			} else
				System.out.println("Selection invalid.");
		} while (selection != 0);
		return;
	}

	/**
	 * Display add order item.
	 *
	 * @param restaurant the restaurant
	 */
	public static void displayAddOrderItem(Restaurant restaurant) {
		int selection = 0;
		ArrayList<Table> occupiedTables = TableController.getTableByType(restaurant.getTables(),
				Table.TableStatus.OCCUPIED);
		RestaurantController.generateHeader("Add OrderItem:");
		if (occupiedTables.size() == 0) {
			System.out.println("There are currently no orders.");
			return;
		}
		for (Table t : occupiedTables)
			System.out.println("Table " + t.getTableNumber() + " (" + t.getCapacity() + " seats)");
		do {
			System.out.print("\nSelect Table No. (0 to cancel): ");
			selection = sc.nextInt();
			if (selection == 0) // Back
				return;
			Table selectedTable = TableController.getTable(selection, occupiedTables);
			if (selectedTable != null) {
				int itemType = 0;
				do {
					System.out.println("1. Ala carte Items");
					System.out.println("2. Promotional Items");
					System.out.println("0. Back");
					try {
						System.out.print("\nPlease enter your choice (0-2): ");
						itemType = sc.nextInt();
						sc.nextLine();
						switch (itemType) {
						case 0:
							return;
						case 1: // Ala carte Items
							ArrayList<MenuItem> menuItems = restaurant.getMenus();
							int addIndex = 0;
							for (int i = 0; i < menuItems.size(); i++) {
								MenuItem mi = menuItems.get(i);
								System.out.printf("%-4s %-30s %1.2f\n%-4s %-28s\n\n", (i + 1) + ".", mi.getItemName(),
										mi.getItemPrice(), " ", mi.getItemDesc());
							}
							do {
								try {
									System.out.print("Select Menu Item to add (0 to cancel): ");
									addIndex = sc.nextInt();
									if (addIndex == 0)
										return;
									if (addIndex >= 1 && addIndex <= menuItems.size()) {
										int quantity = 0;
										MenuItem selectedMenuItem = menuItems.get(addIndex - 1);
										try {
											System.out.print("\nEnter Quantity to add: ");
											quantity = sc.nextInt();
											OrderController.addOrderItem(quantity, selection, selectedMenuItem, null,
													restaurant);
											System.out.println("Successfully added Menu Item to Order.");
											addIndex = 0;
										} catch (InputMismatchException ex) {
											System.out.println("Invalid input! Please try again.");
											sc.nextLine(); // Clear the garbage input
											continue;
										}
									} else
										System.out.println("Selection doesn't not exist.");
								} catch (InputMismatchException ex) {
									System.out.println("Invalid input! Please try again.");
									sc.nextLine(); // Clear the garbage input
									continue;
								}
							} while (addIndex != 0);
							break;
						case 2: // Promotional Items
							ArrayList<PromoMenu> promoMenu = restaurant.getPromoMenu();
							int addPromoIndex = 0;
							for (int i = 0; i < promoMenu.size(); i++) {
								PromoMenu pm = promoMenu.get(i);
								System.out.printf("%-4s %-30s $ %1.2f\n%-4s %-28s\n\n", (i + 1) + ".",
										pm.getPromoName(), pm.getPromoPrice(), " ", pm.getPromoDesc());
							}
							do {
								try {
									System.out.print("Select Promo Item to add (0 to cancel): ");
									addPromoIndex = sc.nextInt();
									if (addPromoIndex == 0)
										return;
									if (addPromoIndex >= 1 && addPromoIndex <= promoMenu.size()) {
										int quantity = 0;
										PromoMenu selectedPromoItem = promoMenu.get(addPromoIndex - 1);
										try {
											System.out.print("\nEnter Quantity to add: ");
											quantity = sc.nextInt();
											OrderController.addOrderItem(quantity, selection, null, selectedPromoItem,
													restaurant);
											System.out.println("Successfully added Promo Item to Order.");
											addPromoIndex = 0;
										} catch (InputMismatchException ex) {
											System.out.println("Invalid input! Please try again.");
											sc.nextLine(); // Clear the garbage input
											continue;
										}
									} else
										System.out.println("Selection doesn't not exist.");
								} catch (InputMismatchException ex) {
									System.out.println("Invalid input! Please try again.");
									sc.nextLine(); // Clear the garbage input
									continue;
								}
							} while (addPromoIndex != 0);
							break;
						default:
							System.out.println("! Please enter only choice (0-2).");
							break;
						}
					} catch (InputMismatchException ex) {
						System.out.println("Invalid input! Please enter choice (0-2).");
						sc.nextLine(); // Clear the garbage input
						continue;
					}
					itemType = 0;
				} while (itemType != 0);
				selection = 0;
			} else
				System.out.println("Selection invalid.");
		} while (selection != 0);
	}

	/**
	 * Display remove order item.
	 *
	 * @param restaurant the restaurant
	 */
	private static void displayRemoveOrderItem(Restaurant restaurant) {
		int selection = 0;
		ArrayList<Table> occupiedTables = TableController.getTableByType(restaurant.getTables(),
				Table.TableStatus.OCCUPIED);
		RestaurantController.generateHeader("Remove OrderItem:");
		if (occupiedTables.size() == 0) {
			System.out.println("There are currently no orders.");
			return;
		}
		for (Table t : occupiedTables)
			System.out.println("Table " + t.getTableNumber() + " (" + t.getCapacity() + " seats)");
		do {
			System.out.print("\nSelect Table No. (0 to cancel): ");
			selection = sc.nextInt();
			if (selection == 0) // Back
				return;
			Table selectedTable = TableController.getTable(selection, occupiedTables);
			if (selectedTable != null) {
				int removeIndex = 0;
				Order tableOrder = OrderController.getTableOrder(restaurant.getOrders(), selection);
				ArrayList<OrderItem> tableOrderItems = tableOrder.getOrderItems();
				System.out.printf("\n%-4s %-28s %-7s \n", "No.", "Item Name", "Quantity");
				for (int i = 0; i < tableOrderItems.size(); i++) {
					OrderItem oi = tableOrderItems.get(i);
					System.out.printf("%-4s %-28s %-7d\n", (i + 1) + ".", oi.getName(), oi.getQuantity());
				}
				do {
					try {
						System.out.print("\nSelect Order Item to remove (0 to cancel): ");
						removeIndex = sc.nextInt();
						if (removeIndex == 0)
							return;
						if (removeIndex >= 1 && removeIndex <= tableOrderItems.size()) {
							int quantity = 0;
							OrderItem selectedOrderItem = tableOrderItems.get(removeIndex - 1);
							boolean canRemove = true;
							do {
								try {
									System.out.print("\nEnter Quantity to remove: ");
									quantity = sc.nextInt();
									canRemove = OrderController.checkRemoveQuantity(quantity, selectedOrderItem);
									if (canRemove) {
										OrderController.removeOrderItem(tableOrder, selectedOrderItem, quantity,
												restaurant);
										System.out.println("Successfully Remove Order Item.");
										canRemove = true;
									} else
										System.out.println("Error: Quantity enter exceeds quantity in order.");
								} catch (InputMismatchException ex) {
									System.out.println("Invalid input! Please try again.");
									sc.nextLine(); // Clear the garbage input
									continue;
								}
							} while (!canRemove);
							removeIndex = 0;
						} else
							System.out.println("Selection doesn't not exist.");
					} catch (InputMismatchException ex) {
						System.out.println("Invalid input! Please try again.");
						sc.nextLine(); // Clear the garbage input
						continue;
					}
				} while (removeIndex != 0);
				selection = 0;
			} else
				System.out.println("Selection invalid.");
		} while (selection != 0);
	}

	/**
	 * Display print invoice.
	 *
	 * @param restaurant the restaurant
	 */
	private static void displayPrintInvoice(Restaurant restaurant) {
		int selection = 0;
		ArrayList<Table> occupiedTables = TableController.getTableByType(restaurant.getTables(),
				Table.TableStatus.OCCUPIED);
		RestaurantController.generateHeader("Print Invoice:");
		if (occupiedTables.size() == 0) {
			System.out.println("There are currently no orders.");
			return;
		}
		for (Table t : occupiedTables)
			System.out.println("Table " + t.getTableNumber() + " (" + t.getCapacity() + " seats)");
		do {
			System.out.print("\nSelect Table No. (0 to cancel): ");
			selection = sc.nextInt();
			if (selection == 0) // Back
				return;
			Table selectedTable = TableController.getTable(selection, occupiedTables);
			if (selectedTable != null) {
				Order invoiceOrder = OrderController.getTableOrder(restaurant.getOrders(), selection);
				// generate invoice here
				if (invoiceOrder.getOrderInvoice() == null) { // invoice not generated
					int payment = 0;
					OrderController.generateInvoice(selection, restaurant); // create invoice
					OrderInvoice invoice = invoiceOrder.getOrderInvoice(); // get invoice
					do {
						try {
							printLine(50, "-");
							System.out.printf("%s $%.2f\n", "TOTAL AMOUNT:", invoice.getTotalPrice());
							printLine(50, "-");
							System.out.println("1. Credit Card");
							System.out.println("2. Cash");
							System.out.print("\nSelect the Payment Method: ");
							payment = sc.nextInt();
							switch (payment) {
							case 1:
								invoice.setPaidBy(OrderInvoice.PaymentType.CREDITCARD);
								invoice.setReceivedAmount(invoice.getTotalPrice());
								break;
							case 2:
								invoice.setPaidBy(OrderInvoice.PaymentType.CASH);
								System.out.print("Enter Amount Received: ");
								invoice.setReceivedAmount(sc.nextDouble());
								break;
							default:
								System.out.println("Invalid Options entered.");
								break;
							}
						} catch (InputMismatchException ex) {
							System.out.println("Invalid input! Please try again.");
							sc.nextLine(); // Clear the garbage input
							continue;
						}
					} while (payment < 1 || payment > 2);

					printLine(50, "-");
					printMiddle(restaurant.getRestaurantName());
					printMiddle(restaurant.getAddress());
					printMiddle("Tel: " + restaurant.getTel());
					printMiddle("Order #: " + invoice.getOrderInvoiceNo());
					System.out.println();
					System.out.printf("%-25s", "Server: " + invoice.getOrder().getStaff().getName());
					System.out.printf("%25s\n", "Date: " + dateFormat("dd/MM/yyyy", invoice.getGeneratedDateTime()));
					System.out.printf("%-25s", "Table: " + invoice.getOrder().getTable().getTableNumber());
					System.out.printf("%25s\n", "Time: " + dateFormat("HH:mm", invoice.getGeneratedDateTime()));
					printLine(50, "-");
					for (OrderItem oi : invoiceOrder.getOrderItems())
						System.out.printf("%1s%-5s%-39s%2.2f\n", "", oi.getQuantity(), oi.getName(), oi.getTotalCost());
					printLine(50, "-");
					System.out.printf("%40s%10.2f\n", "SubTotal: ", invoice.getSubTotalPrice());
					System.out.printf("%40s%10.2f\n", "GST: ", invoice.getGst());
					System.out.printf("%40s%10.2f\n", "Service Charge: ", invoice.getServiceCharge());
					printLine(50, "-");
					System.out.printf("%35s$%14.2f\n", "TOTAL: ", invoice.getTotalPrice());
					printLine(50, "-");
					System.out.printf("%40s%10s\n", "Payment By: ", invoice.getPaidBy());
					System.out.printf("%40s%10.2f\n", "Amount Received: ", invoice.getReceivedAmount());
					System.out.printf("%40s%10.2f\n", "Change: ", invoice.change());
					printLine(50, "=");
					String signOff = "Thank you for dining with us!";
					System.out.print(new String(new char[(50 - signOff.length()) / 2]).replace("\0", " "));
					System.out.print(signOff);
					System.out.println(new String(new char[(50 - signOff.length()) / 2]).replace("\0", " "));
					printLine(50, "=");
					System.out.println("\n");
					printLine(50, "-");
					selection = 0;
				} else
					System.out.println("Error: Order Invoice already generated for this order.");
			} else
				System.out.println("Selection invalid.");
		} while (selection != 0);
	}

	/**
	 * Prints the middle.
	 *
	 * @param word the word
	 */
	public static void printMiddle(String word) {
		int padding = (50 - word.length()) / 2;
		System.out.print(new String(new char[padding]).replace("\0", " "));
		System.out.print(word);
		System.out.println(new String(new char[padding]).replace("\0", " "));
	}

	/**
	 * Date format.
	 *
	 * @param format   the format
	 * @param dateTime the date time
	 * @return the string
	 */
	public static String dateFormat(String format, Date dateTime) {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		return formatter.format(dateTime);
	}

	/**
	 * Prints the line.
	 *
	 * @param size the size
	 * @param sym  the sym
	 */
	public static void printLine(int size, String sym) {
		System.out.println(new String(new char[size]).replace("\0", sym));
	}
}
