package entities;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;

// TODO: Auto-generated Javadoc
/**
 * The Class SetPromo.
 */
public class PromoMenu implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The set promoitems. */
	public ArrayList<MenuItem> promoItems;

	/** The promo name. */
	private String promoName;

	/** The promo desc. */
	private String promoDesc;

	/** The promo price. */
	private double promoPrice;

	/**
	 * Instantiates a new promo menu.
	 */
	public PromoMenu() {

	}

	/**
	 * Instantiates a new promo menu.
	 *
	 * @param promoName  the promo name
	 * @param promoPrice the promo price
	 */
	public PromoMenu(String promoName, double promoPrice) {
		this.promoName = promoName;
		this.promoPrice = promoPrice;
	}

	/**
	 * Instantiates a new promo menu.
	 *
	 * @param promoName  the promo name
	 * @param promoDesc  the promo desc
	 * @param promoPrice the promo price
	 * @param promoItems the promo items
	 */
	public PromoMenu(String promoName, String promoDesc, double promoPrice, ArrayList<MenuItem> promoItems) {
		this.promoName = promoName;
		this.promoDesc = promoDesc;
		this.promoPrice = promoPrice;
		this.promoItems = promoItems;
	}

	/**
	 * Gets the promo name.
	 *
	 * @return the promo name
	 */
	public String getPromoName() {
		return promoName;
	}

	/**
	 * Sets the promo name.
	 *
	 * @param promoName the new promo name
	 */
	public void setPromoName(String promoName) {
		this.promoName = promoName;
	}

	/**
	 * Gets the promo desc.
	 *
	 * @return the promo desc
	 */
	public String getPromoDesc() {
		return promoDesc;
	}

	/**
	 * Sets the promo desc.
	 *
	 * @param promoDesc the new promo desc
	 */
	public void setPromoDesc(String promoDesc) {
		this.promoDesc = promoDesc;
	}

	/**
	 * Gets the promo price.
	 *
	 * @return the promo price
	 */
	public double getPromoPrice() {
		return promoPrice;
	}

	/**
	 * Sets the promo price.
	 *
	 * @param promoPrice the new promo price
	 */
	public void setPromoPrice(double promoPrice) {
		this.promoPrice = promoPrice;
	}

	/**
	 * Adds the to promo.
	 *
	 * @param newMenuItem the new menu item
	 */
	public void addToPromo(MenuItem newMenuItem) {
		promoItems.add(newMenuItem);
	}

	/**
	 * Gets the promo items.
	 *
	 * @return the promo items
	 */
	public ArrayList<MenuItem> getPromoItems() {
		return promoItems;
	}

	/**
	 * Display promo details.
	 */
	public void displayPromoDetails() {
		String price = new DecimalFormat("$###,##0.00").format(getPromoPrice());
		String promoName = " \"" + getPromoName() + "\" " + price + " Set Promo ";
		int paddingLength = (50 - promoName.length()) / 2;
		System.out.println();
		System.out.print(new String(new char[paddingLength]).replace("\0", "-"));
		System.out.print(promoName);
		System.out.println(new String(new char[paddingLength]).replace("\0", "-"));
		System.out.printf("%-50s%n", "\"" + getPromoDesc() + "\"");

		for (MenuItem mi : getPromoItems()) {
			mi.displayItemSummary();
		}
	}

	/**
	 * Gets the promo.
	 *
	 * @param promoName the promo name
	 * @param promoMenu the promo menu
	 * @return the promo
	 */
	public static PromoMenu getPromo(String promoName, ArrayList<PromoMenu> promoMenu) {
		for (PromoMenu pm : promoMenu) {
			if (pm.getPromoName().equalsIgnoreCase(promoName)) {
				return pm;
			}
		}
		return null;
	}

}
