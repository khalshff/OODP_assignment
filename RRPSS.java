
import controls.RestaurantController;
import entities.Restaurant;

// TODO: Auto-generated Javadoc
/**
 * The Class RRPSS.
 */
public class RRPSS {

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		Restaurant restaurant = new Restaurant();
		restaurant = RestaurantController.initRestaurant();
		RestaurantController.displayRestaurant(restaurant);
		System.out.println("Saving system state...");
		RestaurantController.saveRestaurant(restaurant);
		System.out.println("Bye Bye");
		System.exit(0);
	}

}
