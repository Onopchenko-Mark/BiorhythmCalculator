package utils;

import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class TextUtils {
    private static JLabel createJLabel(String text, Font font) {
	JLabel label = new JLabel(text);
	label.setFont(font);
	return label;
    }

    public static JLabel createSmallLabel(String text) {
	return createJLabel(text, AppConstants.SMALL_FONT);
    }

    public static JLabel createLabel(String text) {
	return createJLabel(text, AppConstants.NORMAL_FONT);
    }

    public static JLabel createTitleLabel(String text) {
	JLabel label = createJLabel(text, AppConstants.TITLE_FONT);
	label.setHorizontalTextPosition(SwingConstants.CENTER);
	label.setHorizontalAlignment(SwingConstants.CENTER); 
	return label;
    }
}
