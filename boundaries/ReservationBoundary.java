package boundaries;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

import controls.ReservationController;
import controls.RestaurantController;
import controls.TableController;
import entities.Reservation;
import entities.Restaurant;
import entities.Table;

// TODO: Auto-generated Javadoc
/**
 * User interface for Reservation.
 */

public class ReservationBoundary {

	/** Scanner to read user input. */
	private static Scanner sc = new Scanner(System.in);

	/**
	 * Reservation options.
	 *
	 * @param restaurant the restaurant
	 */
	public static void reservationOptions(Restaurant restaurant) {

		ArrayList<Reservation> reservations = restaurant.getReservations();
		int option = 0;
		do {
			RestaurantController.generateHeader("Reservation Options");

			System.out.println("1. Display all reservations");
			System.out.println("2. Create reservation booking ");
			System.out.println("3. Remove reservation booking");
			System.out.println("4. Display table availability");
			System.out.println("0. Back");

			try {
				System.out.print("\nPlease enter your choice (1-4): ");
				option = sc.nextInt();
				sc.nextLine();

				switch (option) {
				case 0:
					return;
				case 1:
					displayReservation(reservations);
					break;
				case 2:
					createReservation(restaurant);
					break;
				case 3:
					removeReservation(reservations, restaurant);
					break;
				case 4:
					displayTableAvailability(restaurant.getTables(), restaurant, reservations);
					break;
				default:
					System.out.println("Invalid input!");
					break;
				}
			} catch (InputMismatchException ex) {
				System.out.println("Invalid input! Please try again.");
				sc.nextLine(); // Clear the garbage input
				continue;
			}
		} while (option > 0 || option < 4);
	}

	/**
	 * Display reservation.
	 *
	 * @param reservations the reservations
	 */
	private static void displayReservation(ArrayList<Reservation> reservations) {
		RestaurantController.generateHeader("Reservations");
		ArrayList<Reservation> unexpiredReservations = ReservationController.getUnexpiredReservation(reservations);
		String format = "%-20s %-25s %-5s %-10s %-15s %s %n";

		Format dateFormat = new SimpleDateFormat("dd MMM yyyy h:mm a");
		String res;

		if (unexpiredReservations == null || unexpiredReservations.size() <= 0) {
			System.out.println("There are currently no reservations.");
			return;
		}

		System.out.printf(format, "Name", "Date/Time", "Pax", "Table No.", "Contact", "Status");

		for (Reservation r : unexpiredReservations) {
			res = dateFormat.format(r.getReserveDateTime());
			System.out.printf(format, r.getCustomerName(), res, r.getNumOfPax(), r.getTableReserved().getTableNumber(),
					r.getCustomerContact(), r.getAcceptanceStatus());
		}
	}

	/**
	 * Creates the reservation.
	 *
	 * @param r the r
	 */
	private static void createReservation(Restaurant r) {
		String customerName = "";
		int customerContact = 0;
		int numOfPax = 0;
		String reserveDateTime = "";
		Date date = null;
		boolean valid = false;
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");

		RestaurantController.generateHeader("Creating a New Reservation Booking");
		System.out.println("\"Enter '0' at any point of time to exit\"");

		do {
			System.out.print("Enter customer name: ");
			customerName = sc.nextLine();
			RestaurantController.validateStringInput(customerName);
			if (customerName.equalsIgnoreCase("0")) {
				reservationOptions(r);
				System.exit(0);
			}
		} while (customerName.isEmpty());

		do {
			try {
				System.out.print("Enter customer contact number: ");
				customerContact = sc.nextInt();
				sc.nextLine();
			} catch (InputMismatchException ex) {
				System.out.println("Invalid input! Please try again.");
				sc.nextLine(); // Clear the garbage input
			}
			if (customerContact < 0) {
				System.out.println("\nContact number cannot be negative!");
			} else if (customerContact == 0) {
				System.out.println("\nContact number cannot be 0!");
				reservationOptions(r);
				System.exit(0);
			} else if (String.valueOf(customerContact).length() != 8) {
				System.out.println("Customer Number must contain 8 digits.");
				customerContact = -1;
			}
		} while (!(customerContact >= 0));

		do {
			try {
				System.out.print("Enter number of pax: ");
				numOfPax = sc.nextInt();
				sc.nextLine();
			} catch (InputMismatchException ex) {
				System.out.println("Invalid input! Please try again.");
				sc.nextLine(); // Clear the garbage input
			}
			if (numOfPax < 0 || numOfPax > 10) {
				if (numOfPax < 0)
					System.out.println("\nNumber of pax cannot be negative!");
				else
					System.out.println("\nRestaurant can only cater for 10 pax per reservation booking!");
			} else if (numOfPax == 0) {
				System.out.println("\nNumber of pax cannot be 0!");
				reservationOptions(r);
				System.exit(0);
			}
		} while (!(numOfPax >= 0 && numOfPax <= 10));
		do {

			System.out.print("Enter reservation date & time in 24-hour format (dd/MM/yyyy HH:mm): ");
			reserveDateTime = sc.nextLine(); // convert to Date format
			if (reserveDateTime.equalsIgnoreCase("0")) {
				reservationOptions(r);
				System.exit(0);
			}
			try {
				date = formatter.parse(reserveDateTime);
				valid = ReservationController.checkValidReservationDate(date, r);

			} catch (ParseException e) {
				System.out.println("Invalid input! Please try again.");
			}
		} while (reserveDateTime.length() != formatter.toPattern().length() || reserveDateTime.isEmpty()
				|| valid == false);

		ReservationController.addNewReservation(customerName, customerContact, numOfPax, date, r.getReservations(),
				r.getTables(), r);

	}

	/**
	 * Removes the reservation.
	 *
	 * @param reservations the reservations
	 * @param r            the r
	 */
	private static void removeReservation(ArrayList<Reservation> reservations, Restaurant r) {
		int customerContact = 0, choice = 0;
		displayReservation(reservations);
		if (reservations == null || reservations.size() <= 0) {
			return;
		}
		System.out.println("");
		RestaurantController.generateHeader("Removing Reservation Booking");

		ArrayList<Reservation> res = null;
		Format dateFormat = new SimpleDateFormat("dd MMM yyyy h:mm a");
		String dateRes;
		String format = "%-5s %-20s %-25s %-5s %-10s %-15s %s %n";
		Reservation removedReservation = null;

		do {
			try {
				System.out.print("Enter customer contact to remove reservation ('0' to cancel): ");
				customerContact = sc.nextInt();
				System.out.println();
				if (customerContact == 0) {
					reservationOptions(r);
					System.exit(0);
				}
				if (String.valueOf(customerContact).length() != 8) {
					System.out.println("Customer Number must contain 8 digits.");
					customerContact = -1;
				} else {
					res = ReservationController.getReservationWithContact(r.getReservations(), customerContact);
					System.out.printf(format, "ID", "Name", "Date/Time", "Pax", "Table No.", "Contact", "Status");

					for (int i = 0; i < res.size(); i++) {
						Reservation reservation = res.get(i);
						dateRes = dateFormat.format(reservation.getReserveDateTime());
						System.out.printf(format, (i + 1), reservation.getCustomerName(), dateRes,
								reservation.getNumOfPax(), reservation.getTableReserved().getTableNumber(),
								reservation.getCustomerContact(), reservation.getAcceptanceStatus());
					}
					System.out.println("");
					do {
						try {
							System.out.print("Enter the reservation ID that you want to remove ('0' to cancel): ");
							choice = sc.nextInt();
							if (choice == 0)
								return;
							if (choice >= 1 && choice <= res.size()) {
								for (Reservation list : reservations) {
									if (list.getReserveDateTime() == res.get(choice - 1).getReserveDateTime())
										removedReservation = list;
								}
								if (removedReservation != null) {
									Table resTable = removedReservation.getTableReserved();
									resTable.freeTable(resTable.getTableNumber());
									reservations.remove(removedReservation);
									System.out.println("Reservation removed!");

								}
							} else {
								System.out.println("Failed to remove reservation as index provided is invalid!");
								choice = -1;
							}
						} catch (InputMismatchException ex) {
							System.out.println("Failed to remove reservation as index provided is invalid!");
							choice = -1;
							sc.nextLine(); // Clear the garbage input
							continue;
						}
					} while (!(choice >= 0));
				}
			} catch (InputMismatchException ex) {
				System.out.println("Invalid input! Please try again.");
				sc.nextLine(); // Clear the garbage input
			}

		} while (!(customerContact >= 0));
	}

	/**
	 * Display table availability.
	 *
	 * @param tables       the tables
	 * @param r            the r
	 * @param reservations the reservations
	 */
	private static void displayTableAvailability(ArrayList<Table> tables, Restaurant r,
			ArrayList<Reservation> reservations) {
		RestaurantController.generateHeader("Display Available Tables");
		int numOfPax = 0;
		do {
			try {
				System.out.print("Enter number of pax: ");
				numOfPax = sc.nextInt();
				sc.nextLine();
			} catch (InputMismatchException ex) {
				System.out.println("Invalid input! Please try again.");
				sc.nextLine(); // Clear the garbage input
				continue;
			}
			if (numOfPax < 0 || numOfPax > 10) {
				if (numOfPax < 0)
					System.out.println("\nNumber of pax cannot be negative!");
				else
					System.out.println("\nRestaurant can only cater for 10 pax per reservation booking!");
			} else if (numOfPax == 0) {
				System.out.println("\nNumber of pax cannot be 0!");
				reservationOptions(r);
				System.exit(0);
			}
		} while (!(numOfPax >= 0 && numOfPax < 10));

		Date date = new Date(); // get current DateTime
		ArrayList<Table> availableTables = TableController.checkForAvailability(numOfPax, tables, date, reservations,
				r);

		if (availableTables == null || availableTables.size() <= 0) {
			System.out.println(
					"Sorry, there are currently no available tables that can accomodate " + numOfPax + " person(s).");
			return;
		}

		String format = "%-15s %s %n";

		System.out.println("The following tables are available:");
		System.out.printf(format, "Table Number", "Capacity");
		int index = 0;
		for (Table t : availableTables) {
			System.out.printf(format, t.getTableNumber(), t.getCapacity());
			index++;
			if (index % 5 == 0)
				return;
		}
	}

	/**
	 * Display all tables status.
	 *
	 * @param tables     the tables
	 * @param restaurant the restaurant
	 */
	public static void displayAllTablesStatus(ArrayList<Table> tables, Restaurant restaurant) {

		ArrayList<Table> vacatedTables = TableController.getTableByType(restaurant.getTables(),
				Table.TableStatus.VACATED);
		ArrayList<Table> reservedTables = TableController.getTableByType(restaurant.getTables(),
				Table.TableStatus.RESERVED);
		ArrayList<Table> occupiedTables = TableController.getTableByType(restaurant.getTables(),
				Table.TableStatus.OCCUPIED);
		int index = 0;
		String ss = null;

		if (vacatedTables == null || vacatedTables.size() <= 0) {
			System.out.println("There are no available tables now.");
			return;
		} else {
			System.out.println("Vacant Tables");
			for (Table t : vacatedTables) {
				ss = t.getTableNumber() + "(" + t.getCapacity() + ")";

				System.out.print(ss + "\t");
				index++;
				if (index % 3 == 0)
					System.out.println();
			}
		}
		System.out.println("\n");

		if (reservedTables == null || reservedTables.size() <= 0)
			return;
		else {
			System.out.println("Reserved Tables");
			for (Table t : reservedTables) {
				ss = t.getTableNumber() + "(" + t.getCapacity() + ")";

				System.out.print(ss + "\t");
				index++;
				if (index % 3 == 0)
					System.out.println();
			}
		}
		System.out.println("\n");

		if (occupiedTables == null || occupiedTables.size() <= 0)
			return;
		else {
			System.out.println("Occupied Tables");
			for (Table t : occupiedTables) {
				ss = t.getTableNumber() + "(" + t.getCapacity() + ")";

				System.out.print(ss + "\t");
				index++;
				if (index % 3 == 0)
					System.out.println();
			}
		}

	}

}
