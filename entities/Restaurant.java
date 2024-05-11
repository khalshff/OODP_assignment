package entities;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

// TODO: Auto-generated Javadoc
/**
 * The Class Restaurant.
 */
public class Restaurant implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The restaurant name. */
	private String restaurantName;

	/** The address. */
	private String address;

	/** The tel. */
	private String tel;

	/** The am session start. */
	private Date amSessionStart;

	/** The am session end. */
	private Date amSessionEnd;

	/** The pm session start. */
	private Date pmSessionStart;

	/** The pm session end. */
	private Date pmSessionEnd;

	/** The staffs. */
	private ArrayList<Staff> staffs;

	/** The menus. */
	private ArrayList<MenuItem> menus;

	/** The promo menus. */
	private ArrayList<PromoMenu> promoMenus;

	/** The tables. */
	private ArrayList<Table> tables;

	/** The orders. */
	private ArrayList<Order> orders;

	/** The settled orders. */
	private ArrayList<Order> settledOrders;

	/** The reservations. */
	private ArrayList<Reservation> reservations;

	/** The settled reservations. */
	private ArrayList<Reservation> settledReservations;

	/** The invoices. */
	private ArrayList<OrderInvoice> invoices;

	/** The str date format. */
	private String strDateFormat = "hh:mm:ss a";

	/** The date format. */
	private DateFormat dateFormat = new SimpleDateFormat(strDateFormat);

	/**
	 * Instantiates a new restaurant.
	 */
	public Restaurant() {
		this.staffs = new ArrayList<Staff>();
		this.menus = new ArrayList<MenuItem>();
		this.promoMenus = new ArrayList<PromoMenu>();
		this.tables = new ArrayList<Table>();
		this.orders = new ArrayList<Order>();
		this.settledOrders = new ArrayList<Order>();
		this.reservations = new ArrayList<Reservation>();
		this.settledReservations = new ArrayList<Reservation>();
		this.invoices = new ArrayList<OrderInvoice>();
	}

	/**
	 * Instantiates a new restaurant.
	 *
	 * @param restaurantName the restaurant name
	 * @param address        the address
	 * @param tel            the tel
	 * @param amSessionStart the am session start
	 * @param amSessionEnd   the am session end
	 * @param pmSessionStart the pm session start
	 * @param pmSessionEnd   the pm session end
	 */
	public Restaurant(String restaurantName, String address, String tel, Date amSessionStart, Date amSessionEnd,
			Date pmSessionStart, Date pmSessionEnd) {
		// Setting of simple variables
		this.restaurantName = restaurantName;
		this.address = address;
		this.tel = tel;
		this.amSessionStart = amSessionStart;
		this.amSessionEnd = amSessionEnd;
		this.pmSessionStart = pmSessionStart;
		this.pmSessionEnd = pmSessionEnd;
		this.staffs = new ArrayList<Staff>();
		this.menus = new ArrayList<MenuItem>();
		this.promoMenus = new ArrayList<PromoMenu>();
		this.tables = new ArrayList<Table>();
		this.orders = new ArrayList<Order>();
		this.settledOrders = new ArrayList<Order>();
		this.reservations = new ArrayList<Reservation>();
		this.settledReservations = new ArrayList<Reservation>();
		this.invoices = new ArrayList<OrderInvoice>();
	}

	/**
	 * Gets the restaurant name.
	 *
	 * @return the restaurant name
	 */
	public String getRestaurantName() {
		return this.restaurantName;
	}

	/**
	 * Sets the restaurant name.
	 *
	 * @param restaurantName the new restaurant name
	 */
	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
	}

	/**
	 * Gets the am session start.
	 *
	 * @return the am session start
	 */
	public Date getAmSessionStart() {
		return amSessionStart;
	}

	/**
	 * Gets the am session start string.
	 *
	 * @return the am session start string
	 */
	public String getAmSessionStartString() {
		return dateFormat.format(getAmSessionStart());
	}

	/**
	 * Sets the am session start.
	 *
	 * @param amSessionStart the new am session start
	 */
	public void setAmSessionStart(Date amSessionStart) {
		this.amSessionStart = amSessionStart;
	}

	/**
	 * Gets the am session end.
	 *
	 * @return the am session end
	 */
	public Date getAmSessionEnd() {
		return amSessionEnd;
	}

	/**
	 * Gets the am session end string.
	 *
	 * @return the am session end string
	 */
	public String getAmSessionEndString() {
		return dateFormat.format(getAmSessionEnd());
	}

	/**
	 * Sets the am session end.
	 *
	 * @param amSessionEnd the new am session end
	 */
	public void setAmSessionEnd(Date amSessionEnd) {
		this.amSessionEnd = amSessionEnd;
	}

	/**
	 * Gets the pm session start.
	 *
	 * @return the pm session start
	 */
	public Date getPmSessionStart() {
		return pmSessionStart;
	}

	/**
	 * Gets the pm session start string.
	 *
	 * @return the pm session start string
	 */
	public String getPmSessionStartString() {
		return dateFormat.format(getPmSessionStart());
	}

	/**
	 * Sets the pm session start.
	 *
	 * @param pmSessionStart the new pm session start
	 */
	public void setPmSessionStart(Date pmSessionStart) {
		this.pmSessionStart = pmSessionStart;
	}

	/**
	 * Gets the pm session end.
	 *
	 * @return the pm session end
	 */
	public Date getPmSessionEnd() {
		return pmSessionEnd;
	}

	/**
	 * Gets the pm session end string.
	 *
	 * @return the pm session end string
	 */
	public String getPmSessionEndString() {
		return dateFormat.format(getPmSessionEnd());
	}

	/**
	 * Sets the pm session end.
	 *
	 * @param pmSessionEnd the new pm session end
	 */
	public void setPmSessionEnd(Date pmSessionEnd) {
		this.pmSessionEnd = pmSessionEnd;
	}

	/**
	 * Gets the address.
	 *
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * Sets the address.
	 *
	 * @param address the new address
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * Gets the tel.
	 *
	 * @return the tel
	 */
	public String getTel() {
		return tel;
	}

	/**
	 * Sets the tel.
	 *
	 * @param tel the new tel
	 */
	public void setTel(String tel) {
		this.tel = tel;
	}

	/**
	 * Gets the staffs.
	 *
	 * @return the staffs
	 */
	public ArrayList<Staff> getStaffs() {
		return staffs;
	}

	/**
	 * Sets the staffs.
	 *
	 * @param staffs the new staffs
	 */
	public void setStaffs(ArrayList<Staff> staffs) {
		this.staffs = staffs;
	}

	/**
	 * Gets the menus.
	 *
	 * @return the menus
	 */
	public ArrayList<MenuItem> getMenus() {
		return this.menus;
	}

	/**
	 * Sets the menus.
	 *
	 * @param menus the new menus
	 */
	public void setMenus(ArrayList<MenuItem> menus) {
		this.menus = menus;
	}

	/**
	 * Gets the promo menu.
	 *
	 * @return the promo menu
	 */
	public ArrayList<PromoMenu> getPromoMenu() {
		return this.promoMenus;
	}

	/**
	 * Sets the promo menu.
	 *
	 * @param promoMenus the new promo menu
	 */
	public void setPromoMenu(ArrayList<PromoMenu> promoMenus) {
		this.promoMenus = promoMenus;
	}

	/**
	 * Gets the tables.
	 *
	 * @return the tables
	 */
	public ArrayList<Table> getTables() {
		return this.tables;
	}

	/**
	 * Sets the tables.
	 *
	 * @param tables the new tables
	 */
	public void setTables(ArrayList<Table> tables) {
		this.tables = tables;
	}

	/**
	 * Gets the orders.
	 *
	 * @return the orders
	 */
	public ArrayList<Order> getOrders() {
		return orders;
	}

	/**
	 * Sets the orders.
	 *
	 * @param order the new orders
	 */
	public void setOrders(ArrayList<Order> order) {
		this.orders = order;
	}

	/**
	 * Gets the settled orders.
	 *
	 * @return the settled orders
	 */
	public ArrayList<Order> getSettledOrders() {
		return this.settledOrders;
	}

	/**
	 * Sets the settled orders.
	 *
	 * @param settledOrder the new settled orders
	 */
	public void setSettledOrders(ArrayList<Order> settledOrder) {
		this.settledOrders = settledOrder;
	}

	/**
	 * Gets the reservations.
	 *
	 * @return the reservations
	 */
	public ArrayList<Reservation> getReservations() {
		return this.reservations;
	}

	/**
	 * Sets the reservations.
	 *
	 * @param reservation the new reservations
	 */
	public void setReservations(ArrayList<Reservation> reservation) {
		this.reservations = reservation;
	}

	/**
	 * Gets the settled reservations.
	 *
	 * @return the settled reservations
	 */
	public ArrayList<Reservation> getSettledReservations() {
		return this.settledReservations;
	}

	/**
	 * Sets the settled reservations.
	 *
	 * @param settledReservations the new settled reservations
	 */
	public void setSettledReservations(ArrayList<Reservation> settledReservations) {
		this.settledReservations = settledReservations;
	}

	/**
	 * Gets the invoices.
	 *
	 * @return the invoices
	 */
	public ArrayList<OrderInvoice> getInvoices() {
		return this.invoices;
	}

	/**
	 * Sets the invoices.
	 *
	 * @param invoices the new invoices
	 */
	public void setInvoices(ArrayList<OrderInvoice> invoices) {
		this.invoices = invoices;
	}

}
