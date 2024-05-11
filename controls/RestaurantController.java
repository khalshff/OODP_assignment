package controls;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import boundaries.RestaurantBoundary;
import entities.MenuItem;
import entities.Order;
import entities.OrderInvoice;
import entities.OrderItem;
import entities.Restaurant;
import entities.PromoMenu;
import entities.Staff;
import entities.Table;

// TODO: Auto-generated Javadoc
/**
 * The Class RestaurantController.
 */
public class RestaurantController {

	/** The Constant DATAPATH. */
	private static final Path DATAPATH = Paths.get(System.getProperty("user.dir"), "data");

	/** The Constant RESTAURANT_FILE_NAME. */
	private static final String RESTAURANT_FILE_NAME = "restaurant.txt";

	/**
	 * Display restaurant.
	 *
	 * @param restaurant the restaurant
	 */
	public static void displayRestaurant(Restaurant restaurant) {
		RestaurantBoundary.restaurantOptions(restaurant);
	}

	/**
	 * Inits the restaurant.
	 *
	 * @return the restaurant
	 */
	public static Restaurant initRestaurant() {
		Restaurant restaurant = new Restaurant();
		// HashMap<String, Object> saved = new HashMap<String, Object>();
		System.out.print("Initalizing system.. Loading data files..\n");
		File f = new File(Paths.get(DATAPATH.toString(), RESTAURANT_FILE_NAME).toString());
		if (!f.exists()) {
			restaurant = createRestaurant();

			// Save Data
			try {
				FileOutputStream fileout = new FileOutputStream(
						Paths.get(DATAPATH.toString(), RESTAURANT_FILE_NAME).toString());
				ObjectOutputStream out = new ObjectOutputStream(fileout);
				out.writeObject(restaurant);
				out.close();
				fileout.close();
			} catch (IOException i) {
				i.printStackTrace();
			}
		} else {
			// Retrieve Data
			FileInputStream fileIn;
			ObjectInputStream objectIn;
			try {
				fileIn = new FileInputStream(Paths.get(DATAPATH.toString(), RESTAURANT_FILE_NAME).toString());
				objectIn = new ObjectInputStream(fileIn);
				restaurant = (Restaurant) objectIn.readObject();
				objectIn.close();
				fileIn.close();
			} catch (IOException i) {
				i.printStackTrace();
			} catch (ClassNotFoundException c) {
				System.out.println("Restaurant class not found");
				c.printStackTrace();
			}
		}
		System.out.println("\nSystem initialization completed!");
		System.out.println("\nWelcome to the Restaurant Reservation & " + "Point of Sales Application!");
		return restaurant;
	}

	/**
	 * Save restaurant.
	 *
	 * @param restaurant the restaurant
	 */
	public static void saveRestaurant(Restaurant restaurant) {
		// Save Data
		FileOutputStream fileOut;
		ObjectOutputStream objectOut;
		try {
			fileOut = new FileOutputStream(Paths.get(DATAPATH.toString(), RESTAURANT_FILE_NAME).toString());
			objectOut = new ObjectOutputStream(fileOut);
			objectOut.writeObject(restaurant);
			objectOut.close();
			fileOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Creates the restaurant.
	 *
	 * @return the restaurant
	 */
	// Initialization
	public static Restaurant createRestaurant() {
		Date amStart = null, amEnd = null, pmStart = null, pmEnd = null;
		try {
			amStart = new SimpleDateFormat("hh:mm:ss").parse("11:00:00");
			amEnd = new SimpleDateFormat("hh:mm:ss").parse("15:00:00");
			pmStart = new SimpleDateFormat("hh:mm:ss").parse("18:00:00");
			pmEnd = new SimpleDateFormat("hh:mm:ss").parse("22:00:00");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Restaurant restaurant = new Restaurant("Group 2's Restaurant", "50 Nanyang Ave, 639798", "67911744", amStart,
				amEnd, pmStart, pmEnd);
		restaurant.setStaffs(createNewStaff());
		restaurant.setMenus(createNewMenu());
		restaurant.setPromoMenu(createNewPromoMenu());
		restaurant.setTables(createTables());
		createNewOrders(restaurant); // create history of orders for displaying generate sales report
		return restaurant;
	}

	/**
	 * Creates the new staff.
	 *
	 * @return the array list
	 */
	public static ArrayList<Staff> createNewStaff() {
		ArrayList<Staff> staff = new ArrayList<Staff>();
		Staff s1 = new Staff("Bob", false, 001, "Waiter");
		Staff s2 = new Staff("Racheal", true, 002, "Chef");
		Staff s3 = new Staff("Watson", false, 003, "Manager");
		Staff s4 = new Staff("Mary", false, 001, "Waiter");
		staff.add(s1);
		staff.add(s2);
		staff.add(s3);
		staff.add(s4);
		return staff;
	}

	/**
	 * Creates the new menu.
	 *
	 * @return the array list
	 */
	public static ArrayList<MenuItem> createNewMenu() {
		ArrayList<MenuItem> menu = new ArrayList<MenuItem>();
		MenuItem mi1 = new MenuItem(MenuItem.ItemType.MAINCOURSE, "Chicken Rice", "Delicious", 3.50);
		MenuItem mi2 = new MenuItem(MenuItem.ItemType.DRINK, "Coke", "Soft Drink", 1.50);
		MenuItem mi3 = new MenuItem(MenuItem.ItemType.DRINK, "Sprite", "Soft Drink", 1.50);
		MenuItem mi4 = new MenuItem(MenuItem.ItemType.DESSERT, "Ice Cream", "Homemade Ice Cream", 2.50);
		menu.add(mi1);
		menu.add(mi2);
		menu.add(mi3);
		menu.add(mi4);
		return menu;
	}

	/**
	 * Creates the new promo menu.
	 *
	 * @return the array list
	 */
	public static ArrayList<PromoMenu> createNewPromoMenu() {
		ArrayList<PromoMenu> promoMenu = new ArrayList<PromoMenu>();
		ArrayList<MenuItem> menu = createNewMenu();

		ArrayList<MenuItem> setItem1 = new ArrayList<MenuItem>();
		setItem1.add(menu.get(0));
		setItem1.add(menu.get(1));
		PromoMenu sp1 = new PromoMenu("Student Meal", "Valid from 2pm - 5pm everyday", 4.90, setItem1);
		promoMenu.add(sp1);

		ArrayList<MenuItem> setItem2 = new ArrayList<MenuItem>();
		setItem2.add(menu.get(0));
		setItem2.add(menu.get(2));
		setItem2.add(menu.get(3));
		PromoMenu sp2 = new PromoMenu("Lunch Special", "Valid from 2pm - 5pm everyday", 5.90, setItem2);
		promoMenu.add(sp2);
		return promoMenu;
	}

	/**
	 * Creates the tables.
	 *
	 * @return the array list
	 */
	public static ArrayList<Table> createTables() {
		ArrayList<Table> tables = new ArrayList<Table>();
		// create a total of 30 tables with even no. of seats
		for (int i = 0; i < 10; i++)
			tables.add(new Table(i + 1, 2)); // 10x 2-seater
		for (int i = 10; i < 20; i++)
			tables.add(new Table(i + 1, 4)); // 10x 4-seater
		for (int i = 20; i < 25; i++)
			tables.add(new Table(i + 1, 8)); // 5x 8-seater
		for (int i = 25; i < 30; i++)
			tables.add(new Table(i + 1, 10)); // 5x 10-seater
		return tables;
	}

	/**
	 * Creates the new orders.
	 *
	 * @param restaurant the restaurant
	 */
	public static void createNewOrders(Restaurant restaurant) {
		try {
			ArrayList<Order> orders = new ArrayList<Order>();
			ArrayList<OrderInvoice> orderInvoices = new ArrayList<OrderInvoice>();
			ArrayList<MenuItem> menu = restaurant.getMenus();
			ArrayList<PromoMenu> promoMenu = restaurant.getPromoMenu();
			ArrayList<Staff> staffs = restaurant.getStaffs();
			ArrayList<Table> tables = restaurant.getTables();

			OrderItem cr = new OrderItem(menu.get(0), 2);
			OrderItem coke = new OrderItem(menu.get(1), 2);
			OrderItem sprite = new OrderItem(menu.get(2), 2);
			OrderItem icecream = new OrderItem(menu.get(3), 2);
			OrderItem sm = new OrderItem(promoMenu.get(0), 2);
			OrderItem lm = new OrderItem(promoMenu.get(1), 2);

			Order order1 = new Order(staffs.get(0), tables.get(0));
			order1.setDateTimeCreated(new SimpleDateFormat("dd/MM/yyyy HH:mm").parse("20/12/2018 12:30"));
			order1.addOrderItems(cr);
			order1.addOrderItems(sprite);
			order1.generateInvoice();
			order1.getOrderInvoice()
					.setGeneratedDateTime(new SimpleDateFormat("dd/MM/yyyy HH:mm").parse("20/12/2018 13:30"));

			Order order2 = new Order(staffs.get(0), tables.get(1));
			order2.setDateTimeCreated(new SimpleDateFormat("dd/MM/yyyy HH:mm").parse("31/12/2018 13:00"));
			order2.addOrderItems(sm);
			order2.addOrderItems(coke);
			order2.addOrderItems(icecream);
			order2.generateInvoice();
			order2.getOrderInvoice()
					.setGeneratedDateTime(new SimpleDateFormat("dd/MM/yyyy HH:mm").parse("31/12/2018 14:00"));

			Order order3 = new Order(staffs.get(0), tables.get(2));
			order3.setDateTimeCreated(new SimpleDateFormat("dd/MM/yyyy HH:mm").parse("07/01/2019 12:00"));
			order3.addOrderItems(sm);
			order3.addOrderItems(coke);
			order3.addOrderItems(icecream);
			order3.generateInvoice();
			order3.getOrderInvoice()
					.setGeneratedDateTime(new SimpleDateFormat("dd/MM/yyyy HH:mm").parse("07/01/2019 13:30"));

			Order order4 = new Order(staffs.get(0), tables.get(2));
			order4.setDateTimeCreated(new SimpleDateFormat("dd/MM/yyyy HH:mm").parse("07/01/2019 12:00"));
			order4.addOrderItems(sm);
			order4.addOrderItems(coke);
			order4.addOrderItems(icecream);
			order4.generateInvoice();
			order4.getOrderInvoice()
					.setGeneratedDateTime(new SimpleDateFormat("dd/MM/yyyy HH:mm").parse("08/01/2019 12:30"));

			Order order5 = new Order(staffs.get(0), tables.get(2));
			order5.setDateTimeCreated(new SimpleDateFormat("dd/MM/yyyy HH:mm").parse("08/01/2019 14:00"));
			order5.addOrderItems(lm);
			order5.addOrderItems(icecream);
			order5.generateInvoice();
			order5.getOrderInvoice()
					.setGeneratedDateTime(new SimpleDateFormat("dd/MM/yyyy HH:mm").parse("07/01/2019 13:30"));

			orders.add(order1);
			orders.add(order2);
			orders.add(order3);
			orders.add(order4);
			orders.add(order5);
			orderInvoices.add(order1.getOrderInvoice());
			orderInvoices.add(order2.getOrderInvoice());
			orderInvoices.add(order3.getOrderInvoice());
			orderInvoices.add(order4.getOrderInvoice());
			orderInvoices.add(order5.getOrderInvoice());

			restaurant.setSettledOrders(orders);
			restaurant.setInvoices(orderInvoices);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	// validation

	/**
	 * Validate string input.
	 *
	 * @param input the input
	 */
	public static void validateStringInput(String input) {
		if (input.isEmpty())
			System.out.println("Input cannot be left blank");
	}

	/**
	 * Generate header.
	 *
	 * @param header the header
	 */
	public static void generateHeader(String header) {
		RestaurantBoundary.generateHeader(header);
	}

}
