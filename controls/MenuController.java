package controls;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import boundaries.MenuBoundary;
import boundaries.OrderBoundary;
import entities.MenuItem;
import entities.Restaurant;
import entities.PromoMenu;

// TODO: Auto-generated Javadoc
/**
 * The Class Menu.
 */
public class MenuController {

	/** The sc. */
	private static Scanner sc = new Scanner(System.in);

	/**
	 * Display menu.
	 *
	 * @param restaurant the restaurant
	 */
	public static void displayMenu(Restaurant restaurant) {
		ArrayList<MenuItem> menu = restaurant.getMenus();
		ArrayList<PromoMenu> promoMenu = restaurant.getPromoMenu();
		int menuSize = menu.size();

		// Display menu items
		ArrayList<MenuItem.ItemType> typeList = new ArrayList<MenuItem.ItemType>();
		typeList = MenuItem.ItemType.getItemTypeList();

		for (MenuItem.ItemType type : typeList) {
			RestaurantController.generateHeader(type.toStrValue());
			for (int currItem = 0; currItem < menuSize; currItem++) {
				if (menu.get(currItem).getItemType() == type) {
					menu.get(currItem).displayItemDetails();
				}
			}
		}

		if (promoMenu.isEmpty()) {
			System.out.print("\nThere are no Promotions right now");
		} else {
			RestaurantController.generateHeader("Promotional Menu");
			for (PromoMenu pm : promoMenu) {
				pm.displayPromoDetails();
			}
		}

		MenuBoundary.menuOptions(restaurant);
	}

	/**
	 * Creates the menu item.
	 *
	 * @param restaurant the restaurant
	 * @param itemName   the item name
	 * @param itemDesc   the item desc
	 * @param itemPrice  the item price
	 * @param itemType   the item type
	 */
	public static void createMenuItem(Restaurant restaurant, String itemName, String itemDesc, double itemPrice,
			MenuItem.ItemType itemType) {
		ArrayList<MenuItem> menu = restaurant.getMenus();
		MenuItem mi = new MenuItem(itemType, itemName, itemDesc, itemPrice);
		menu.add(mi);
	}

	/**
	 * Update menu item.
	 *
	 * @param restaurant   the restaurant
	 * @param itemName     the item name
	 * @param newItemName  the new item name
	 * @param newItemDesc  the new item desc
	 * @param newItemPrice the new item price
	 */
	public static void updateMenuItem(Restaurant restaurant, String itemName, String newItemName, String newItemDesc,
			double newItemPrice) {
		ArrayList<MenuItem> menu = restaurant.getMenus();
		MenuItem menuItem = MenuItem.getMenuItem(itemName, menu);
		if (newItemName != null) {
			menuItem.setItemName(newItemName);
		}

		if (newItemDesc != null) {
			menuItem.setItemDesc(newItemDesc);
		}

		if (newItemPrice != 0) {
			menuItem.setItemPrice(newItemPrice);
		}

		RestaurantController.saveRestaurant(restaurant);
	}

	/**
	 * Removes the menu item.
	 *
	 * @param restaurant the restaurant
	 * @param itemName   the item name
	 */
	public static void removeMenuItem(Restaurant restaurant, String itemName) {
		Iterator<MenuItem> iter = restaurant.getMenus().iterator();
		while (iter.hasNext()) {
			MenuItem mi = iter.next();
			if (mi.getItemName().equalsIgnoreCase(itemName)) {
				iter.remove();
			}
		}
		RestaurantController.saveRestaurant(restaurant);
	}

	/**
	 * Adds the item to promo.
	 *
	 * @param promoName  the promo name
	 * @param restaurant the restaurant
	 */
	public static void addItemToPromo(String promoName, Restaurant restaurant) {
		ArrayList<MenuItem> menu = restaurant.getMenus();
		ArrayList<PromoMenu> promoMenu = restaurant.getPromoMenu();
		String itemName = null;
		System.out.println("\"Enter '0' at any point of time to exit\"");

		PromoMenu promo = PromoMenu.getPromo(promoName, promoMenu);
		do {
			System.out.println("Enter the MenuItem to add to: \"" + promoName + "\"");
			itemName = sc.nextLine();
		} while (itemName.isEmpty());
		if (menuItemExist(itemName, menu)) {
			MenuItem menuItem = MenuItem.getMenuItem(itemName, menu);
			promo.addToPromo(menuItem);
			System.out.println("\"" + itemName + "\" has been added into \"" + promoName + "\"");
		} else {
			System.out.println("\"" + itemName + "\" does not exist in Menu!");
		}
		RestaurantController.saveRestaurant(restaurant);
	}

	/**
	 * Removes the item from promo.
	 *
	 * @param promoName  the promo name
	 * @param restaurant the restaurant
	 */
	public static void removeItemFromPromo(String promoName, Restaurant restaurant) {
		ArrayList<MenuItem> menu = restaurant.getMenus();
		ArrayList<PromoMenu> promoMenu = restaurant.getPromoMenu();
		PromoMenu promo = new PromoMenu();
		String itemName = null;
		System.out.println("\"Enter '0' at any point of time to exit\"");

		promo = PromoMenu.getPromo(promoName, promoMenu);
		do {
			System.out.println("Enter the Menu Item to remove from: \"" + promoName + "\"");
			itemName = sc.nextLine();
		} while (itemName.isEmpty());
		if (menuItemExist(itemName, menu)) {
			Iterator<MenuItem> iter = (promo.getPromoItems()).iterator();
			while (iter.hasNext()) {
				MenuItem m = iter.next();
				if (m.getItemName().equalsIgnoreCase(itemName)) {
					iter.remove();
					System.out.println("\"" + itemName + "\" has been removed from \"" + promoName + "\"");
				}
			}
		} else {
			System.out.println("\"" + itemName + "\" does not exist in \"" + promoName + "\"");
		}
		RestaurantController.saveRestaurant(restaurant);
	}

	/**
	 * Creates the promo menu.
	 *
	 * @param restaurant the restaurant
	 * @param promoName  the promo name
	 * @param promoDesc  the promo desc
	 * @param promoPrice the promo price
	 * @param mi         the mi
	 */
	public static void createPromoMenu(Restaurant restaurant, String promoName, String promoDesc, double promoPrice,
			ArrayList<MenuItem> mi) {
		ArrayList<PromoMenu> promoMenu = restaurant.getPromoMenu();
		PromoMenu pm = new PromoMenu(promoName, promoDesc, promoPrice, mi);
		promoMenu.add(pm);
		RestaurantController.saveRestaurant(restaurant);
	}

	/**
	 * Update promo menu.
	 *
	 * @param restaurant    the restaurant
	 * @param promoName     the promo name
	 * @param newPromoName  the new promo name
	 * @param newPromoDesc  the new promo desc
	 * @param newPromoPrice the new promo price
	 */
	public static void updatePromoMenu(Restaurant restaurant, String promoName, String newPromoName,
			String newPromoDesc, double newPromoPrice) {
		ArrayList<PromoMenu> promoMenu = restaurant.getPromoMenu();
		PromoMenu promo = PromoMenu.getPromo(promoName, promoMenu);
		if (newPromoName != null) {
			promo.setPromoName(newPromoName);
		}

		if (newPromoDesc != null) {
			promo.setPromoDesc(newPromoDesc);
		}

		if (newPromoPrice != 0) {
			promo.setPromoPrice(newPromoPrice);
		}
		RestaurantController.saveRestaurant(restaurant);
	}

	/**
	 * Removes the set promo.
	 *
	 * @param restaurant the restaurant
	 * @param promoName  the promo name
	 */
	public static void removeSetPromo(Restaurant restaurant, String promoName) {
		Iterator<PromoMenu> iter = restaurant.getPromoMenu().iterator();
		while (iter.hasNext()) {
			PromoMenu sp = iter.next();
			if (sp.getPromoName().equalsIgnoreCase(promoName)) {
				iter.remove();
			}
		}
		RestaurantController.saveRestaurant(restaurant);
	}

	/**
	 * Menu item exist.
	 *
	 * @param itemName the item name
	 * @param menu     the menu
	 * @return true, if successful
	 */
	public static boolean menuItemExist(String itemName, ArrayList<MenuItem> menu) {
		boolean exist = false;
		for (MenuItem mi : menu) {
			if (mi.getItemName().equalsIgnoreCase(itemName)) {
				exist = true;
				break;
			}
		}
		return exist;
	}

	/**
	 * Creates the menu list.
	 *
	 * @param restaurant the restaurant
	 * @param itemName   the item name
	 * @param mi         the mi
	 * @return the array list
	 */
	public static ArrayList<MenuItem> createMenuList(Restaurant restaurant, String itemName, ArrayList<MenuItem> mi) {
		MenuItem menuItem = MenuItem.getMenuItem(itemName, restaurant.getMenus());
		mi.add(menuItem);
		return mi;
	}

	/**
	 * Promo exist.
	 *
	 * @param promoName the promo name
	 * @param promoMenu the promo menu
	 * @return true, if successful
	 */
	public static boolean promoExist(String promoName, ArrayList<PromoMenu> promoMenu) {
		boolean exist = false;
		for (PromoMenu pm : promoMenu) {
			if (pm.getPromoName().equalsIgnoreCase(promoName)) {
				exist = true;
				break;
			}
		}
		return exist;
	}

	/**
	 * Find in promo.
	 *
	 * @param itemName  the item name
	 * @param promoMenu the promo menu
	 * @return true, if successful
	 */
	public static boolean findInPromo(String itemName, ArrayList<PromoMenu> promoMenu) {
		for (PromoMenu pm : promoMenu) {
			for (MenuItem mi : pm.promoItems) {
				if (mi.getItemName().equalsIgnoreCase(itemName)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Display menu options.
	 *
	 * @param restaurant the restaurant
	 */
	public static void displayMenuOptions(Restaurant restaurant) {
		MenuBoundary.menuOptions(restaurant);
	}
}
