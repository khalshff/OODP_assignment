package entities;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;

// TODO: Auto-generated Javadoc
/**
 * The Class MenuItem.
 */
public class MenuItem implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * The Enum ItemType.
	 */
	public static enum ItemType {

		/** The Main course. */
		MAINCOURSE("Main Course"),
		/** The Drink. */
		DRINK("Drink"),
		/** The Dessert. */
		DESSERT("Dessert");

		/** The value. */
		private final String value;

		/**
		 * Instantiates a new item type.
		 *
		 * @param value the value
		 */
		private ItemType(String value) {
			this.value = value;
		}

		/**
		 * To str value.
		 *
		 * @return the string
		 */
		public String toStrValue() {
			return value;
		}

		/**
		 * Gets the item type list.
		 *
		 * @return the item type list
		 */
		public static ArrayList<ItemType> getItemTypeList() {
			ArrayList<ItemType> typeList = new ArrayList<ItemType>();
			typeList.add(MAINCOURSE);
			typeList.add(DRINK);
			typeList.add(DESSERT);
			return typeList;
		}
	}

	/** The item type. */
	private ItemType itemType;

	/** The item name. */
	private String itemName;

	/** The item desc. */
	private String itemDesc;

	/** The item price. */
	private double itemPrice;

	/**
	 * Instantiates a new menu item.
	 */
	public MenuItem() {

	}

	/**
	 * Instantiates a new menu item.
	 *
	 * @param itemType  the item type
	 * @param itemName  the item name
	 * @param itemDesc  the item desc
	 * @param itemPrice the item price
	 */
	public MenuItem(ItemType itemType, String itemName, String itemDesc, double itemPrice) {
		this.itemType = itemType;
		this.itemName = itemName;
		this.itemDesc = itemDesc;
		this.itemPrice = itemPrice;
	}

	/**
	 * Gets the item type.
	 *
	 * @return the item type
	 */
	public ItemType getItemType() {
		return itemType;
	}

	/**
	 * Sets the item type.
	 *
	 * @param itemType the new item type
	 */
	public void setItemType(ItemType itemType) {
		this.itemType = itemType;
	}

	/**
	 * Gets the item name.
	 *
	 * @return the item name
	 */
	public String getItemName() {
		return itemName;
	}

	/**
	 * Sets the item name.
	 *
	 * @param itemName the new item name
	 */
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	/**
	 * Gets the item desc.
	 *
	 * @return the item desc
	 */
	public String getItemDesc() {
		return itemDesc;
	}

	/**
	 * Sets the item desc.
	 *
	 * @param itemDesc the new item desc
	 */
	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}

	/**
	 * Gets the item price.
	 *
	 * @return the item price
	 */
	public double getItemPrice() {
		return itemPrice;
	}

	/**
	 * Sets the item price.
	 *
	 * @param itemPrice the new item price
	 */
	public void setItemPrice(double itemPrice) {
		this.itemPrice = itemPrice;
	}

	/**
	 * Display item details.
	 */
	public void displayItemDetails() {
		System.out.println();
		System.out.printf("%-30s", getItemName());
		System.out.printf("%60s%n", new DecimalFormat("$###,##0.00").format(getItemPrice()));
		System.out.println("\"" + getItemDesc() + "\"");
	}

	/**
	 * Display item summary.
	 */
	public void displayItemSummary() {
		System.out.println();
		System.out.printf("%-30s", getItemName());
		System.out.println();
		System.out.println("\"" + getItemDesc() + "\"");
	}

	/**
	 * Gets the menu item.
	 *
	 * @param itemName the item name
	 * @param menu     the menu
	 * @return the menu item
	 */
	public static MenuItem getMenuItem(String itemName, ArrayList<MenuItem> menu) {
		for (MenuItem mi : menu) {
			if (mi.getItemName().equalsIgnoreCase(itemName)) {
				return mi;
			}
		}
		return null;
	}
}
