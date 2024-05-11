package boundaries;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

import controls.MenuController;
import controls.OrderController;
import controls.ReservationController;
import controls.TableController;
import entities.OrderInvoice;
import entities.Restaurant;

// TODO: Auto-generated Javadoc
/**
 * The Class RestaurantBoundary.
 */
public class RestaurantBoundary {

	/** The sc. */
	static Scanner sc = new Scanner(System.in);

	/**
	 * Restaurant options.
	 *
	 * @param restaurant the restaurant
	 */
	public static void restaurantOptions(Restaurant restaurant) {
		int option = 0;

		do {
			generateHeader(restaurant.getRestaurantName());
			System.out.println("1. Manage Menu");
			System.out.println("2. Manage Orders");
			System.out.println("3. Manage Reservations");
			System.out.println("4. Generate Sales Revenue Report");
			System.out.println("5. View All Tables Availability");
			System.out.println("0. Exit");
			System.out.println("");

			try {
				System.out.print("Please enter your choice (0-5): ");

				option = sc.nextInt();
				switch (option) {
				case 0:
					return;
				case 1:
					MenuController.displayMenuOptions(restaurant);
					break;
				case 2:
					OrderController.displayOrderOptions(restaurant);
					break;
				case 3:
					ReservationController.displayReservationOptions(restaurant);
					break;
				case 4:
					generateRevenueReport(restaurant);
					break;
				case 5:
					ReservationController.displayAllTableStatues(restaurant);
					break;
				default:
					System.out.println("Invalid input!");
				}
			} catch (InputMismatchException ex) {
				System.out.println("Invalid input! Please enter choice (1-5).");
				sc.nextLine(); // Clear the garbage input
				continue;
			}
		} while (option != 0);
		return;
	}

	/**
	 * Generate revenue report.
	 *
	 * @param restaurant the restaurant
	 */
	public static void generateRevenueReport(Restaurant restaurant) {

		int option = 0, input;
		do {
			generateHeader("Sales Revenue Report");
			System.out.println("1. Daily");
			System.out.println("2. Monthly");
			System.out.println("3. Yearly");
			System.out.println("0. Back");
			System.out.print("How would you like to see the report in: ");
			option = sc.nextInt();
			ArrayList<OrderInvoice> report = new ArrayList<OrderInvoice>();

			try {
				switch (option) {
				case 0:
					return;
				case 1:
					System.out.print("Enter number of days you would like to see the report for: ");
					input = sc.nextInt();
					report = reportByDay(restaurant, input);
					generateHeader("Sales Revenue Report");
					printReport(report);
					break;
				case 2:
					System.out.print("Enter the Year which you would like to view (E.g. 2019): ");
					input = sc.nextInt();
					System.out.print("Enter the Month which you would like to view (E.g. 03 for march): ");
					int month = sc.nextInt();
					report = reportByMonth(restaurant, month, input);
					generateHeader("Sales Revenue Report");
					printReport(report);
					break;
				case 3:
					System.out.print("Enter the Year which you would like to view (E.g. 2019): ");
					input = sc.nextInt();
					report = reportByYear(restaurant, input);
					generateHeader("Sales Revenue Report");
					printReport(report);
					break;
				default:
					System.out.println("! Please enter only choice (0-3).");
					break;
				}

			} catch (InputMismatchException ex) {
				System.out.println("Invalid input! Please enter choice (0-2).");
				sc.nextLine(); // Clear the garbage input
				continue;
			}
		} while (option != 0);
	}

	/**
	 * Report by day.
	 *
	 * @param restaurant the restaurant
	 * @param numOfDays  the num of days
	 * @return the array list
	 */
	private static ArrayList<OrderInvoice> reportByDay(Restaurant restaurant, int numOfDays) {
		ArrayList<OrderInvoice> invoices = restaurant.getInvoices();
		ArrayList<OrderInvoice> forReport = new ArrayList<OrderInvoice>();
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DATE, -numOfDays);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		Date yesterday = cal.getTime();
		Format dateFormat = new SimpleDateFormat("dd MMM yyyy h:mm a");
		System.out.println("Report from: " + dateFormat.format(yesterday));
		for (int i = 0; i < invoices.size(); i++) {
			if (invoices.get(i).getGeneratedDateTime().after(yesterday)) {
				System.out.println("Invoice No. " + invoices.get(i).getOrderInvoiceNo());
				forReport.add(invoices.get(i));
			}
		}
		return forReport;
	}

	/**
	 * Report by month.
	 *
	 * @param restaurant the restaurant
	 * @param month      the month
	 * @param year       the year
	 * @return the array list
	 */
	private static ArrayList<OrderInvoice> reportByMonth(Restaurant restaurant, int month, int year) {
		ArrayList<OrderInvoice> invoices = restaurant.getInvoices();
		ArrayList<OrderInvoice> forReport = new ArrayList<OrderInvoice>();
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.MONTH, month - 1);
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		Date yesterday = cal.getTime();
		cal.set(Calendar.MONTH, month);
		Date nextMonth = cal.getTime();
		Format dateFormat = new SimpleDateFormat("dd MMM yyyy h:mm a");
		System.out.print("\nReport from: " + dateFormat.format(yesterday) + " to ");
		if (Calendar.getInstance().getTime().before(nextMonth)) {
			System.out.println(dateFormat.format(Calendar.getInstance().getTime()));
		} else {
			System.out.println(dateFormat.format(nextMonth));
		}
		for (int i = 0; i < invoices.size(); i++) {
			if (invoices.get(i).getGeneratedDateTime().after(yesterday)
					&& invoices.get(i).getGeneratedDateTime().before(nextMonth)) {
				System.out.println("Invoice No. " + invoices.get(i).getOrderInvoiceNo());
				forReport.add(invoices.get(i));
			}
		}
		return forReport;
	}

	/**
	 * Report by year.
	 *
	 * @param restaurant the restaurant
	 * @param year       the year
	 * @return the array list
	 */
	private static ArrayList<OrderInvoice> reportByYear(Restaurant restaurant, int year) {
		ArrayList<OrderInvoice> invoices = restaurant.getInvoices();
		ArrayList<OrderInvoice> forReport = new ArrayList<OrderInvoice>();
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.MONTH, 0);
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		Date yesterday = cal.getTime();
		cal.set(Calendar.YEAR, year + 1);
		Date nextYear = cal.getTime();
		Format dateFormat = new SimpleDateFormat("dd MMM yyyy h:mm a");
		System.out.print("\nReport from: " + dateFormat.format(yesterday) + " to ");
		if (Calendar.getInstance().getTime().before(nextYear)) {
			System.out.println(dateFormat.format(Calendar.getInstance().getTime()));
		} else {
			System.out.println(dateFormat.format(nextYear));
		}

		for (int i = 0; i < invoices.size(); i++) {
			if (invoices.get(i).getGeneratedDateTime().after(yesterday)
					&& invoices.get(i).getGeneratedDateTime().before(nextYear)) {
				System.out.println("Invoice No. " + invoices.get(i).getOrderInvoiceNo());
				forReport.add(invoices.get(i));
			}
		}

		return forReport;
	}

	/**
	 * Prints the report.
	 *
	 * @param report the report
	 */
	private static void printReport(ArrayList<OrderInvoice> report) {
		double total = 0;
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		System.out.printf("%-18s%-23s%6s\n\n", "Invoice Number", "Bill Date", "Total Bill");
		for (int i = 0; i < report.size(); i++) {
			System.out.printf("%-18s%-26s$%5.2f\n", report.get(i).getOrderInvoiceNo(),
					formatter.format(report.get(i).getGeneratedDateTime()), report.get(i).getTotalPrice());
			total += report.get(i).getTotalPrice();
		}
		System.out.println(new String(new char[50]).replace("\0", "-"));
		System.out.printf("%-44s$%5.2f\n", "Total Sales", total);
	}

	/**
	 * Generate header.
	 *
	 * @param header the header
	 */
	public static void generateHeader(String header) {
		int padding = (94 - header.length()) / 2;
		System.out.println(new String(new char[94]).replace("\0", "="));
		System.out.print(new String(new char[padding]).replace("\0", " "));
		System.out.print(header);
		System.out.println(new String(new char[padding]).replace("\0", " "));
		System.out.println(new String(new char[94]).replace("\0", "="));
	}

}
