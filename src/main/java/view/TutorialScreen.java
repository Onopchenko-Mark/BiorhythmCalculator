package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import utils.AppConstants;
import utils.ButtonUtils;
import utils.ImageUtils;
import utils.TextUtils;

public class TutorialScreen extends JFrame {
    private final static String TUT_TEXT = "<html>Biorhythms, as pseudoscience, were developed by Wilhelm Fliess in the late 19th century,<br/>"
	    + "and were popularized in the United States in the late 1970s.<br/>"
	    + "The biorhythm theory suggests that humans have 3 biological cycles:<br/>"
	    + "<b>Physical</b> - coordination, strength, well-being<br/>"
	    + "<b>Emotional</b> - creativity, sensitivity, mood<br/>"
	    + "<b>Intellectual</b> - alertness, memory, analytical functioning<br/>"
	    + "All the cycles begin at birth, and have a sinusoidal pattern.<br/>"
	    + "All the cycles have values ranging from 1 to -1.<br/>"
	    + "The higher the value, the better you will do in that particular area.<br/>"
	    + "The lower the cycle, the more difficult your life will be.<br/>" + "Want to know your biorhythm?<br/>"
	    + "The knowledge is one 'Continue' press away!</html>";

    JLabel textLabel = TextUtils.createLabel(TUT_TEXT);
    private final static String IMAGE_URI = "https://miro.medium.com/max/512/1*XGk-4-N5QFRC15NVvFHkzQ.jpeg";
    private JButton continueButton = ButtonUtils.createLargeButton("Continue");

    public TutorialScreen() {
	setTitle("Welcome!");
	setLayout(new BorderLayout());
	setDefaultCloseOperation(DISPOSE_ON_CLOSE);

	JLabel title = TextUtils.createTitleLabel("Welcome!");

	JPanel centerPanel = new JPanel();
	JLabel textLabel = TextUtils.createLabel(TUT_TEXT);
	JLabel tutorialImg = ImageUtils.createImage("Tutorial Image", IMAGE_URI, 500, 350);

	centerPanel.add(tutorialImg);
	centerPanel.add(textLabel);

	continueButton.setBackground(Color.GREEN);
	continueButton.setFont(AppConstants.TITLE_FONT);
	continueButton.addActionListener(e -> {
	    this.dispose();
	});

	add(title, BorderLayout.NORTH);
	add(centerPanel, BorderLayout.CENTER);
	add(continueButton, BorderLayout.SOUTH);

	pack();
	setLocationRelativeTo(null);
	setResizable(false);
	setVisible(true);
    }

    public void addContinueListener(ActionListener listener) {
	continueButton.addActionListener(listener);
    }
}
