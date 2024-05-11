package boundaries;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Scanner;

import controls.MenuController;
import controls.RestaurantController;
import entities.MenuItem;
import entities.PromoMenu;
import entities.Restaurant;

// TODO: Auto-generated Javadoc
/**
 * The Class MenuUI.
 */
public class MenuBoundary {
	/** The sc. */
	private static Scanner sc = new Scanner(System.in);

	/**
	 * Menu options.
	 *
	 * @param restaurant the restaurant
	 */
	public static void menuOptions(Restaurant restaurant) {
		int option = 0;
		do {
			String s = "Menu Options";
			int padding = (94 - s.length()) / 2;
			System.out.println(new String(new char[94]).replace("\0", "="));
			System.out.print(new String(new char[padding]).replace("\0", " "));
			System.out.print(s);
			System.out.println(new String(new char[padding]).replace("\0", " "));
			System.out.println(new String(new char[94]).replace("\0", "="));

			System.out.println("1. View menu");
			System.out.println("2. Create a new menu item");
			System.out.println("3. Update an existing menu item");
			System.out.println("4. Remove an existing menu item");
			System.out.println("5. Create a new Set Package");
			System.out.println("6. Update an existing Set Package");
			System.out.println("7. Remove an existing Set Package");
			System.out.println("0. Back");

			try {
				System.out.print("\nPlease enter your choice (1-7): ");
				option = sc.nextInt();
				sc.nextLine();

				switch (option) {
				case 0:
					return;
				case 1:
					MenuController.displayMenu(restaurant);
					break;
				case 2:
					createMenuItemUI(restaurant);
					break;
				case 3:
					updateMenuItemUI(restaurant);
					break;
				case 4:
					removeMenuItemUI(restaurant);
					break;
				case 5:
					createPromoMenuUI(restaurant);
					break;
				case 6:
					updatePromoMenuUI(restaurant);
					break;
				case 7:
					removePromoMenuUI(restaurant);
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
		} while (option < 0 || option > 7);
	}

	/**
	 * Creates the menu item UI.
	 *
	 * @param restaurant the restaurant
	 */
	public static void createMenuItemUI(Restaurant restaurant) {
		String itemName = "", itemDesc = "";
		double itemPrice = 0;
		int itemTypeChoice = 0;
		MenuItem.ItemType itemType = null;
		RestaurantController.generateHeader("Creating New Menu Item");
		System.out.println("\"Enter '0' at any point of time to exit\"");
		do {
			try {
				System.out.print("\nEnter the item type" + " (1. Main Course 2. Drink 3. Dessert): ");
				itemTypeChoice = sc.nextInt();
				sc.nextLine();
			} catch (InputMismatchException ex) {
				System.out.println("Invalid input! Please try again.");
				sc.nextLine(); // Clear the garbage input
				continue;
			}
			if (itemTypeChoice < 0 || itemTypeChoice > 3) {
				System.out.println("Invalid input! Please try again.");
			} else if (itemTypeChoice == 0) {
				MenuBoundary.menuOptions(restaurant);
			}
		} while (itemTypeChoice < 0 || itemTypeChoice > 3);

		switch (itemTypeChoice) {
		case 0:
			MenuBoundary.menuOptions(restaurant);
			break;
		case 1:
			itemType = MenuItem.ItemType.MAINCOURSE;
			break;
		case 2:
			itemType = MenuItem.ItemType.DRINK;
			break;
		case 3:
			itemType = MenuItem.ItemType.DESSERT;
			break;
		default:
			System.out.println("Invalid input!");
			break;
		}

		do {
			System.out.print("Enter a name for the item: ");
			itemName = sc.nextLine();
			validateStringInput(itemName, restaurant);
			if (MenuController.menuItemExist(itemName, restaurant.getMenus())) {
				System.out.println("\"" + itemName + "\" already exist!");
				MenuBoundary.menuOptions(restaurant);
				return;
			}
			if (itemName.equalsIgnoreCase("0")) {
				MenuBoundary.menuOptions(restaurant);
			}
		} while (itemName.isEmpty());

		do {
			System.out.print("Enter a description for the item: ");
			itemDesc = sc.nextLine();
			validateStringInput(itemDesc, restaurant);
		} while (itemDesc.isEmpty());

		do {
			try {
				System.out.print("Enter a price for the item: ");
				itemPrice = sc.nextDouble();
				sc.nextLine();
			} catch (InputMismatchException ex) {
				System.out.println("Invalid input! Please try again.");
				sc.nextLine(); // Clear the garbage input
				continue;
			}
			if (itemPrice < 0) {
				System.out.println("\nItem price cannot be negative!");
				continue;
			} else if (itemPrice == 0) {
				System.out.println("\nItem price cannot be 0!");
				MenuBoundary.menuOptions(restaurant);
			}
		} while (!(itemPrice >= 0));
		MenuController.createMenuItem(restaurant, itemName, itemDesc, itemPrice, itemType);
		System.out.println("Item has been created!");
		MenuBoundary.menuOptions(restaurant);
	}

	/**
	 * Update menu item UI.
	 *
	 * @param restaurant the restaurant
	 */
	public static void updateMenuItemUI(Restaurant restaurant) {
		String itemName = null, newItemName = "", newItemDesc = "";
		Double newItemPrice = null;
		RestaurantController.generateHeader("Updating Menu Item");
		System.out.println("\"Enter '0' at any point of time to exit\"");
		do {
			System.out.print("Enter the name of the item you want to update: ");
			itemName = sc.nextLine();
			validateStringInput(itemName, restaurant);
		} while (itemName.isEmpty());
		int option = 0;
		if (MenuController.menuItemExist(itemName, restaurant.getMenus())) {
			System.out.println();
			System.out.println("What do you want to update?");
			System.out.println("1. Update item name");
			System.out.println("2. Update item description");
			System.out.println("3. Update item price");
			do {
				try {
					System.out.print("\nPlease enter your choice (1-3): ");
					option = sc.nextInt();
					sc.nextLine();
				} catch (InputMismatchException ex) {
					System.out.println("Invalid input! Please try again.");
					sc.nextLine(); // Clear the garbage input
					continue;
				}
			} while (option < 0 || option > 3);
			switch (option) {
			case 0:
				MenuBoundary.menuOptions(restaurant);
				break;
			case 1:
				do {
					System.out.print("Enter new item name: ");
					newItemName = sc.nextLine();
					validateStringInput(newItemName, restaurant);
				} while ((newItemName).isEmpty());
				MenuController.updateMenuItem(restaurant, itemName, newItemName, null, 0);
				System.out.println("Item updated!");
				break;
			case 2:
				do {
					System.out.print("Enter new item description: ");
					newItemDesc = sc.nextLine();
					validateStringInput(newItemDesc, restaurant);
				} while ((newItemDesc).isEmpty());
				// menuItem.setItemDesc(newItemDesc);
				MenuController.updateMenuItem(restaurant, itemName, null, newItemDesc, 0);
				System.out.println("Item updated!");
				break;
			case 3:
				do {
					try {
						System.out.print("Enter new item price: ");
						newItemPrice = sc.nextDouble();
						sc.nextLine();
					} catch (InputMismatchException ex) {
						System.out.println("Invalid input! Please try again.");
						sc.nextLine(); // Clear the garbage input
						continue;
					}
					if (newItemPrice < 0) {
						System.out.println("\nItem price cannot be negative!");
						continue;
					} else if (newItemPrice == 0) {
						System.out.println("\nItem price cannot be 0!");
						MenuBoundary.menuOptions(restaurant);
					}
				} while (!(newItemPrice > 0));
				// menuItem.setItemPrice(newItemPrice);
				MenuController.updateMenuItem(restaurant, itemName, null, null, newItemPrice);
				System.out.println("Item updated!");
				break;
			default:
				System.out.println("Invalid input!");
				break;
			}
		} else {
			System.out.println("\"" + itemName + "\" does not exist in Menu!");
		}
		MenuBoundary.menuOptions(restaurant);
	}

	/**
	 * Removes the menu item UI.
	 *
	 * @param restaurant the restaurant
	 */
	public static void removeMenuItemUI(Restaurant restaurant) {
		ArrayList<MenuItem> menu = restaurant.getMenus();
		ArrayList<PromoMenu> promoMenu = restaurant.getPromoMenu();
		PromoMenu promo = new PromoMenu();
		String itemName = null;
		RestaurantController.generateHeader("Removing Menu Item");
		System.out.println("\"Enter '0' at any point of time to exit\"");
		do {
			System.out.println("Enter the name of the item you want to remove: ");
			itemName = sc.nextLine();
			validateStringInput(itemName, restaurant);
		} while (itemName.isEmpty());
		Iterator<MenuItem> iter = menu.iterator();
		if (MenuController.menuItemExist(itemName, menu)) {
			if (MenuController.findInPromo(itemName, promoMenu)) {
				System.out.println("\"" + itemName + "\" is tied to a Set Promo. Cannot be removed!");
			} else {
				MenuController.removeMenuItem(restaurant, itemName);
				System.out.println("\"" + itemName + "\" removed from Menu!");
			}
		} else {
			System.out.println("\"" + itemName + "\" does not exist in Menu!");
		}
		MenuBoundary.menuOptions(restaurant);
	}

	/**
	 * Creates the promo menu UI.
	 *
	 * @param restaurant the restaurant
	 */
	public static void createPromoMenuUI(Restaurant restaurant) {
		ArrayList<MenuItem> menu = restaurant.getMenus();
		ArrayList<PromoMenu> promoMenu = restaurant.getPromoMenu();
		ArrayList<MenuItem> mi = new ArrayList<MenuItem>();
		String promoDesc = null, itemName = null, promoName = null;
		double promoPrice = 0;
		RestaurantController.generateHeader("Creating New Set Promo");
		System.out.println("\"Enter '0' at any point of time to exit\"");
		do {
			System.out.print("Enter a name for the promo: ");
			promoName = sc.nextLine();
			validateStringInput(promoName, restaurant);
		} while (promoName.isEmpty());
		if (MenuController.promoExist(promoName, promoMenu)) {
			System.out.println("\"" + promoName + "\"already exist in Menu!");
		} else {
			do {
				System.out.println("Enter a description for the promo: ");
				promoDesc = sc.nextLine();
				validateStringInput(promoDesc, restaurant);
			} while (promoDesc.isEmpty());

			do {
				try {
					System.out.println("Enter a price for the promo: ");
					promoPrice = sc.nextDouble();
					sc.nextLine();
				} catch (InputMismatchException ex) {
					System.out.println("Invalid input! Please try again.");
					sc.nextLine(); // Clear the garbage input
					continue;
				}
				if (promoPrice < 0) {
					System.out.println("\nPromo price cannot be negative!");
					continue;
				} else if (promoPrice == 0) {
					System.out.println("\nItem price cannot be 0!");
					MenuBoundary.menuOptions(restaurant);
				}
			} while (!(promoPrice > 0));

			do {
				do {
					System.out.println("Enter the name of the item to add into promo\n(Enter '#'to stop adding)\n");
					itemName = sc.nextLine();
				} while (itemName.isEmpty());
				if (!itemName.equals("#")) {
					if (MenuController.menuItemExist(itemName, menu)) {
						mi = MenuController.createMenuList(restaurant, itemName, mi);
						System.out.println(("\"" + itemName + "\" added into Promo!"));
					} else {
						System.out.println("\"" + itemName + "\" does not exist in Menu");
					}
				}
			} while (!itemName.equals("#"));
			MenuController.createPromoMenu(restaurant, promoName, promoDesc, promoPrice, mi);
			System.out.println("\"" + promoName + "\" added to Menu!");
		}
		MenuBoundary.menuOptions(restaurant);
	}

	/**
	 * Update promo menu UI.
	 *
	 * @param restaurant the restaurant
	 */
	public static void updatePromoMenuUI(Restaurant restaurant) {
		ArrayList<PromoMenu> promoMenu = restaurant.getPromoMenu();
		PromoMenu promo = new PromoMenu();
		String promoName = null, newPromoName, newPromoDesc;
		double newPromoPrice = 0;
		int option = 0;
		RestaurantController.generateHeader("Updating Set Promo");
		System.out.println("\"Enter '0' at any point of time to exit\"");
		do {
			System.out.print("Enter the the name of the promo to update: ");
			promoName = sc.nextLine();
		} while (promoName.isEmpty());
		if (MenuController.promoExist(promoName, promoMenu)) {
			promo = promo.getPromo(promoName, promoMenu);
			System.out.println("What do you want to update?");
			System.out.println("1. Update promo name");
			System.out.println("2. Update promo description");
			System.out.println("3. Update promo price");
			System.out.println("4. Add menu item to promo");
			System.out.println("5. Remove menu item from promo");
			do {
				try {
					System.out.print("\nPlease enter your choice (1-5): ");
					option = sc.nextInt();
					sc.nextLine();
				} catch (InputMismatchException ex) {
					System.out.println("Invalid input! Please try again.");
					sc.nextLine(); // Clear the garbage input
					continue;
				}
			} while (option < 0 || option > 5);
			switch (option) {
			case 0:
				MenuBoundary.menuOptions(restaurant);
				break;
			case 1:
				do {
					System.out.println("Enter new promo name: ");
					newPromoName = sc.nextLine();
					validateStringInput(newPromoName, restaurant);
				} while ((newPromoName).isEmpty());
				MenuController.updatePromoMenu(restaurant, promoName, newPromoName, null, 0);
				System.out.println("Promo updated!");
				break;
			case 2:
				do {
					System.out.println("Enter new promo description: ");
					newPromoDesc = sc.nextLine();
					validateStringInput(newPromoDesc, restaurant);
				} while ((newPromoDesc).isEmpty());
				MenuController.updatePromoMenu(restaurant, promoName, null, newPromoDesc, 0);
				System.out.println("Promo updated!");
				break;
			case 3:
				do {
					try {
						System.out.println("Enter new promo price: ");
						newPromoPrice = sc.nextDouble();
						sc.nextLine();
					} catch (InputMismatchException ex) {
						System.out.println("Invalid input! Please try again.");
						sc.nextLine(); // Clear the garbage input
						continue;
					}
					if (newPromoPrice < 0) {
						System.out.println("\nPromo price cannot be negative!");
						continue;
					} else if (newPromoPrice == 0) {
						System.out.println("\nItem price cannot be 0!");
						MenuBoundary.menuOptions(restaurant);
					}
				} while (!(newPromoPrice > 0));
				MenuController.updatePromoMenu(restaurant, promoName, null, null, newPromoPrice);
				System.out.println("Promo updated!");
				break;
			case 4:
				RestaurantController.generateHeader("Add Menu Item To Set Promo");
				MenuController.addItemToPromo(promoName, restaurant);
				break;
			case 5:
				RestaurantController.generateHeader("Remove Menu Item From Set Promo");
				MenuController.removeItemFromPromo(promoName, restaurant);
				break;
			default:
				System.out.println("Invalid input!");
				break;
			}
		} else {
			System.out.println("\"" + promoName + "\" does not exist in Menu!");
		}
		MenuBoundary.menuOptions(restaurant);
	}

	/**
	 * Removes the promo menu UI.
	 *
	 * @param restaurant the restaurant
	 */
	public static void removePromoMenuUI(Restaurant restaurant) {

		ArrayList<PromoMenu> promoMenu = restaurant.getPromoMenu();
		String promoName = null;
		RestaurantController.generateHeader("Removing Set Promo");
		System.out.println("\"Enter '0' at any point of time to exit\"");
		do {
			System.out.print("Enter the the name of the promo to remove: ");
			promoName = sc.nextLine();
		} while (promoName.isEmpty());
		if (MenuController.promoExist(promoName, promoMenu)) {
			MenuController.removeSetPromo(restaurant, promoName);
			System.out.println("\"" + promoName + "\" removed from Menu!");
		} else {
			System.out.println("\"" + promoName + "\" does not exist in Menu!");
		}
		MenuBoundary.menuOptions(restaurant);
	}

	/**
	 * Validate string input.
	 *
	 * @param input      the input
	 * @param restaurant the restaurant
	 */
	public static void validateStringInput(String input, Restaurant restaurant) {
		if (input.isEmpty()) {
			System.out.println("Input cannot be left blank");
		} else if (input.equalsIgnoreCase("0")) {
			MenuBoundary.menuOptions(restaurant);
		}
	}
}
