package controls;

import java.util.ArrayList;

import boundaries.StaffBoundary;
import entities.Staff;

// TODO: Auto-generated Javadoc
/**
 * The Class StaffController.
 */
public class StaffController {

	/**
	 * Ask staff.
	 *
	 * @param staffs the staffs
	 * @return the staff
	 */
	public static Staff askStaff(ArrayList<Staff> staffs) {
		return StaffBoundary.displayAskStaff(staffs);
	}
}
