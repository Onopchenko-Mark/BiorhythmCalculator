package biorhythmcalc;

import java.awt.Font;

public class AppConstants {

    // Array of months
    public static final String[] MONTHS = { "Jan", "Feb", "Mar", "Apr", "May", "June", "July", "Aug", "Sept", "Oct",
	    "Nov", "Dec" };

    // Array of days per month (non-leap year)
    public static final int[] DAYS_PER_MONTH = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

    // Font constants
    public static final Font TITLE_FONT = new Font("Verdana", Font.BOLD, 22);
    public static final Font NORMAL_FONT = new Font("Verdana", Font.PLAIN, 16);
    public static final Font SMALL_FONT = new Font("Verdana", Font.PLAIN, 14);

}
