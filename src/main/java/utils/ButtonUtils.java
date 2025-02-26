package utils;

import java.awt.Font;

import javax.swing.JButton;

public class ButtonUtils {
    private static JButton createJButton(String text, Font font) {
	JButton btn = new JButton(text);
	btn.setFont(font);
	return btn;
    }

    public static JButton createSmallButton(String text) {
	return createJButton(text, AppConstants.SMALL_FONT);
    }

    public static JButton createButton(String text) {
	return createJButton(text, AppConstants.NORMAL_FONT);
    }

    public static JButton createLargeButton(String text) {
	return createJButton(text, AppConstants.TITLE_FONT);
    }
}
