package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.time.LocalDate;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import utils.ButtonUtils;
import utils.ImageUtils;

public class LeftSide extends JPanel implements AppLayout {
    private static final long serialVersionUID = -8762225151377979999L;
    private static final String HEADER_IMAGE_URI = "https://sun9-55.userapi.com/impf/mw5y60DKKbsWUa_IVT6JM4Yd3L7lTR46K-xzkQ/TotfrMQj4X8.jpg?size=600x335&quality=96&sign=905b294e882c2307e9f6bdd8a2a02935&type=album";

    private DateInput birthDateInput, targetDateInput;
    private JButton calculateButton = ButtonUtils.createLargeButton("Calculate");

    LeftSide() {
	setLayout(new BorderLayout());

	birthDateInput = new DateInput(LocalDate.of(1999, 1, 1), "Birth Date");
	targetDateInput = new DateInput(LocalDate.now(), "Target Date");
	resetUI();

	add(ImageUtils.createImage("Header Image", HEADER_IMAGE_URI, 300, 150), BorderLayout.NORTH);
	add(createInputPanel(), BorderLayout.CENTER);
	add(calculateButton, BorderLayout.SOUTH);
    }

    private JPanel createInputPanel() {
	JPanel inputsPanel = new JPanel();
	inputsPanel.setLayout(new BoxLayout(inputsPanel, BoxLayout.Y_AXIS));

	inputsPanel.add(birthDateInput.getInputPanel());

	inputsPanel.add(Box.createVerticalStrut(20));

	inputsPanel.add(targetDateInput.getInputPanel());

	JPanel containerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
	containerPanel.add(inputsPanel);

	return containerPanel;
    }

    @Override
    public void resetUI() {
	birthDateInput.setDate(LocalDate.of(1999, 1, 1));
	targetDateInput.setDate(LocalDate.now());
    }

    public LocalDate getBirthDate() {
	return birthDateInput.getDate();
    }

    public LocalDate getTargetDate() {
	return targetDateInput.getDate();
    }

    public JButton getCalculateButton() {
	return calculateButton;
    }
}
