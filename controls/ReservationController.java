package controls;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import boundaries.ReservationBoundary;
import entities.Reservation;
import entities.Restaurant;
import entities.Table;

// TODO: Auto-generated Javadoc
/**
 * The Controller for Reservation Entity.
 */
public class ReservationController {

	/**
	 * Add new reservation to the list.
	 *
	 * @param customerName    the customer name
	 * @param customerContact the customer contact number
	 * @param numOfPax        the number of pax
	 * @param date            the reservation date
	 * @param reservations    the reservation list
	 * @param tables          the table list
	 * @param restaurant      the restaurant
	 */
	public static void addNewReservation(String customerName, int customerContact, int numOfPax, Date date,
			ArrayList<Reservation> reservations, ArrayList<Table> tables, Restaurant restaurant) {
		Table res = TableController.findAvailableTable(numOfPax, tables, date, reservations, restaurant);
		// find suitable Table and create reservation
		if (res != null) {
			Reservation r = new Reservation(customerName, customerContact, numOfPax, date, res);
			// res.setStatus(TableStatus.RESERVED);

			reservations.add(r);

			System.out.println("Reservation created!");
		} else {
			Format dateFormat = new SimpleDateFormat("dd MMM yyyy h:mm a");
			String resDate;
			resDate = dateFormat.format(date);

			System.out.println(
					"Could not find available tables on " + resDate + " to accomodate " + numOfPax + " person(s).");
		}
		RestaurantController.saveRestaurant(restaurant);
	}

	/**
	 * Search reservation using customer number.
	 *
	 * @param reservations the reservation list
	 * @param customerNo   the customer number
	 * @param restaurant   the restaurant
	 * @return the reservation details
	 */
	public static Reservation searchReservation(ArrayList<Reservation> reservations, long customerNo,
			Restaurant restaurant) {
		Reservation reservation = null;
		for (Reservation r : reservations)
			if (r.getCustomerContact() == customerNo) {
				SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
				Date now = new Date(); // get current DateTime
				Date date = r.getReserveDateTime();
				if ((sdf.format(now).compareTo(sdf.format(date)) == 0) && 
						getRestaurantSession(date, restaurant) == getRestaurantSession(now, restaurant)) // check current operating session
					reservation = r;
			}
		return reservation;
	}

	/**
	 * Gets the list of reservations reserved under a contact number.
	 *
	 * @param reservations the reservations
	 * @param customerNo   the customer number
	 * @return the reservations reserved under the contact number
	 */
	public static ArrayList<Reservation> getReservationWithContact(ArrayList<Reservation> reservations,
			long customerNo) {
		ArrayList<Reservation> res = new ArrayList<Reservation>();
		for (Reservation r : reservations) {
			if (r.getCustomerContact() == customerNo)
				res.add(r);
		}
		return res;
	}

	/**
	 * Check reservation exist.
	 *
	 * @param reservations the reservations
	 * @param customerNo   the customer no
	 * @return true, if successful
	 */
	public static boolean checkReservationExist(ArrayList<Reservation> reservations, long customerNo) {
		boolean exist = false;
		for (Reservation r : reservations)
			if (r.getCustomerContact() == customerNo)
				exist = true;
		return exist;
	}

	/**
	 * Gets the restaurant session (AM/PM).
	 *
	 * @param date       the date
	 * @param restaurant the restaurant
	 * @return the restaurant session
	 */
	public static int getRestaurantSession(Date date, Restaurant restaurant) {
		Date AmSessionStart = restaurant.getAmSessionStart();
		Date AmSessionEnd = restaurant.getAmSessionEnd();

		Date PmSessionStart = restaurant.getPmSessionStart();
		Date PmSessionEnd = restaurant.getPmSessionEnd();

		if (inBetweenTime(date, AmSessionStart, AmSessionEnd)) {
			return 1;
		} else if (inBetweenTime(date, PmSessionStart, PmSessionEnd)) {
			return 2;
		} else {
			return 0;
		}

	}

	/**
	 * Compare Time from 2 Date variables (check if time is in between startTime &
	 * endTime).
	 *
	 * @param compareTime the compare time
	 * @param startTime   the start time
	 * @param endTime     the end time
	 * @return true, if successful
	 */
	private static boolean inBetweenTime(Date compareTime, Date startTime, Date endTime) {

		// calendar for currentTime
		Calendar compareCal = Calendar.getInstance();
		compareCal.setTime(compareTime);
		// calendar for startTime
		Calendar startCal = Calendar.getInstance();
		startCal.setTime(startTime);
		// calendar for endTime
		Calendar endCal = Calendar.getInstance();
		endCal.setTime(endTime);
		// set corresponding date fields of startTime and endTime to be equal t
		// ocurrentTime, so you compare only time fields
		startCal.set(compareCal.get(Calendar.YEAR), compareCal.get(Calendar.MONTH),
				compareCal.get(Calendar.DAY_OF_MONTH));
		endCal.set(compareCal.get(Calendar.YEAR), compareCal.get(Calendar.MONTH),
				compareCal.get(Calendar.DAY_OF_MONTH));
		// return true if between, false otherwise
		return (!compareTime.before(startCal.getTime())) && (!compareTime.after(endCal.getTime()));
	}

	/**
	 * Checks if is before.
	 *
	 * @param compareTime         the time that is being compared
	 * @param timeComparedAgainst the time that is compared against
	 * @return true, if compareTime is before timeComparedAgainst
	 */
	private static boolean isBefore(Date compareTime, Date timeComparedAgainst) {
		// calendar for currentTime
		Calendar compareCal = Calendar.getInstance();
		compareCal.setTime(compareTime);
		// calendar for startTime
		Calendar timeComparedCal = Calendar.getInstance();
		timeComparedCal.setTime(timeComparedAgainst);
		return (compareCal.before(timeComparedCal));
	}

	/**
	 * Check if the dateTime entered for reservation booking is valid and within
	 * restaurant operating hours.
	 *
	 * @param date       DateTime that need to be checked
	 * @param restaurant the restaurant
	 * @return true if valid, false if invalid dateTime
	 */
	public static boolean checkValidReservationDate(Date date, Restaurant restaurant) {
		boolean validDate = false;

		Date now = new Date(); // get current DateTime
		Calendar maxAdvanceDateCal = Calendar.getInstance();
		maxAdvanceDateCal.add(Calendar.DAY_OF_MONTH, 30);
		Date maxAdvanceDate = maxAdvanceDateCal.getTime();

		if (date.before(now)) // date have passed
			System.out.println("Reservation DateTime should not be in the past!");
		else if (date.after(maxAdvanceDate))
			System.out.println("Reservation booking can only be made at most 1 month in advance!");

		else if (getRestaurantSession(date, restaurant) == 0) { // outside restaurant operating hours
			System.out.println("Reservation booking can only be done within restaurant operating hours!");
		} else
			validDate = true;

		return validDate;
	}

	/**
	 * Assumption: if reservation date time have passed more than 30mins, remove.
	 *
	 * @param reservations the reservations
	 */
	private static void expiredReservation(ArrayList<Reservation> reservations) {
		Calendar expiredTime = Calendar.getInstance();
		expiredTime.add(Calendar.MINUTE, -30);
		Date expiredTimeToRemove = expiredTime.getTime();
		for (int i = reservations.size() - 1; i >= 0; i--) {
			if (isBefore(reservations.get(i).getReserveDateTime(), expiredTimeToRemove)) {
				Table resTable = reservations.get(i).getTableReserved();
				resTable.freeTable(resTable.getTableNumber());
				reservations.remove(i);

			}
		}

	}

	/**
	 * Gets the reservation that have not expired.
	 *
	 * @param reservations the reservations
	 * @return the reservations that have not expired
	 */
	public static ArrayList<Reservation> getUnexpiredReservation(ArrayList<Reservation> reservations) {
		expiredReservation(reservations);
		return reservations;
	}

	/**
	 * Display reservation options.
	 *
	 * @param restaurant the restaurant
	 */
	public static void displayReservationOptions(Restaurant restaurant) {
		ReservationBoundary.reservationOptions(restaurant);
	}

	/**
	 * Display all table statues.
	 *
	 * @param restaurant the restaurant
	 */
	public static void displayAllTableStatues(Restaurant restaurant) {
		ReservationBoundary.displayAllTablesStatus(restaurant.getTables(), restaurant);
	}

}
