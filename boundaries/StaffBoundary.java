package boundaries;

import java.util.ArrayList;
import java.util.Scanner;
import entities.Staff;

// TODO: Auto-generated Javadoc
/**
 * The Class StaffBoundary.
 */
public class StaffBoundary {
	
	/** The sc. */
	static Scanner sc = new Scanner(System.in);

	/**
	 * Display ask staff.
	 *
	 * @param staffs the staffs
	 * @return the staff
	 */
	public static Staff displayAskStaff(ArrayList<Staff> staffs) {
		Staff staff = null;
		do {
			System.out.print("Please enter your StaffID for use: ");
			int staffId = sc.nextInt();
			for (int i = 0; i < staffs.size(); i++)
				if (staffId == staffs.get(i).getStaffId()) {
					staff = staffs.get(i);
					break;
				}
			if (staff == null)
				System.out.println("You've entered an invalid StaffID");
		} while (staff == null);
		return staff;
	}
}
