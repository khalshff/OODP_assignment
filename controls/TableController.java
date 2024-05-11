package controls;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import entities.Reservation;
import entities.Restaurant;
import entities.Table;

// TODO: Auto-generated Javadoc
/**
 * The Class TableController.
 */
public class TableController {

	/**
	 * Find all tables that satisfy the requirement of number of people and date
	 * time.
	 *
	 * @param numOfPax     Number of people dining
	 * @param tableList    All of the tables in the restaurant
	 * @param dateTime     the date time
	 * @param reservations the reservations
	 * @param restaurant   the restaurant
	 * @return List of available tables
	 */
	public static ArrayList<Table> checkForAvailability(int numOfPax, ArrayList<Table> tableList, Date dateTime,
			ArrayList<Reservation> reservations, Restaurant restaurant) {
		ArrayList<Table> availableTables = new ArrayList<Table>();
		ArrayList<Integer> res = new ArrayList<Integer>();
		int dateSession = ReservationController.getRestaurantSession(dateTime, restaurant);
		int reserveSession = 0;
		for (Reservation r : reservations) {
			reserveSession = ReservationController.getRestaurantSession(r.getReserveDateTime(), restaurant);
			Date reserveDate = setTimeToMidnight(r.getReserveDateTime());
			Date newDate = setTimeToMidnight(dateTime);

			if (reserveDate.compareTo(newDate) == 0 && dateSession == reserveSession)
				res.add(r.getTableReserved().getTableNumber());

		}
		for (Table t : tableList) {
			if (res != null) {
				for (Integer i : res) {
					if (t.getCapacity() >= numOfPax && t.getTableNumber() == i)
						t.setStatus(Table.TableStatus.RESERVED);
				}
			}
			if (t.getCapacity() >= numOfPax && t.getStatus() == Table.TableStatus.VACATED) {
				availableTables.add(t);

			}
		}
		return availableTables;
	}

	/**
	 * Sets the time of a date to midnight.
	 *
	 * @param date the date
	 * @return the date
	 */
	public static Date setTimeToMidnight(Date date) {
		Calendar calendar = Calendar.getInstance();

		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		return calendar.getTime();
	}

	/**
	 * Find available table.
	 *
	 * @param numOfPax the num of pax
	 * @param tables the tables
	 * @param dateTime the date time
	 * @param reservations the reservations
	 * @param restaurant the restaurant
	 * @return the table
	 */
	public static Table findAvailableTable(int numOfPax, ArrayList<Table> tables, Date dateTime,
			ArrayList<Reservation> reservations, Restaurant restaurant) {
		ArrayList<Table> availableTables = checkForAvailability(numOfPax, tables, dateTime, reservations, restaurant);
		if (availableTables.size() != 0 && availableTables != null) {
			Table res = availableTables.get(0); // get the first in array
			for (Table t : availableTables) {
				if (t.getCapacity() < res.getCapacity())
					res = t; // find the smallest table capacity in the array

			}
			return res;
		} else {
			System.out.println("Sorry, there are no tables available " + "that can accomodate " + numOfPax
					+ " person(s) on that date and time.");
			return null;
		}
	}

	/**
	 * Gets the table by status type.
	 *
	 * @param tableList the table list
	 * @param status    the status
	 * @return the table by type
	 */
	public static ArrayList<Table> getTableByType(ArrayList<Table> tableList, Table.TableStatus status) {
		ArrayList<Table> tablesByType = new ArrayList<Table>();
		for (Table t : tableList)
			if (t.getStatus() == status)
				tablesByType.add(t);
		return tablesByType;
	}

	/**
	 * Gets the table.
	 *
	 * @param tableNum  the table num
	 * @param tableList the table list
	 * @return the table
	 */
	public static Table getTable(int tableNum, ArrayList<Table> tableList) {
		Table table = null;
		for (Table t : tableList)
			if (t.getTableNumber() == tableNum)
				table = t;
		return table;
	}

}
