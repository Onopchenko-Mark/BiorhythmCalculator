package biorhythmcalc;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class DateInput {
    private JTextField dayField, yearField;
    private JComboBox<String> monthSelector;

    DateInput() {
	this.dayField = createTextField();
	this.monthSelector = new JComboBox<>(AppConstants.MONTHS);
	this.monthSelector.setFont(AppConstants.SMALL_FONT);
	this.yearField = createTextField();
    }

    private JTextField createTextField() {
	JTextField textField = new JTextField(5);
	textField.setFont(AppConstants.SMALL_FONT);
	return textField;
    }

    private JLabel createLabel(String text) {
	JLabel label = new JLabel(text);
	label.setFont(AppConstants.SMALL_FONT);
	return label;
    }

    private void addComponentToPanel(JPanel panel, GridBagConstraints gbc, Object component, int gridx, int gridy) {
	gbc.gridx = gridx;
	gbc.gridy = gridy;
	panel.add((java.awt.Component) component, gbc);
    }

    public JPanel getInputPanel() {
	JPanel selectorPanel = new JPanel(new GridBagLayout());
	selectorPanel.setBackground(new Color(230, 230, 230)); // Light gray background
	GridBagConstraints gbc = new GridBagConstraints();

	gbc.insets = new Insets(0, 0, 0, 0); // Avoiding unnecessary padding
	// Adding Labels
	addComponentToPanel(selectorPanel, gbc, createLabel("Day"), 0, 0);
	addComponentToPanel(selectorPanel, gbc, createLabel("Month"), 1, 0);
	addComponentToPanel(selectorPanel, gbc, createLabel("Year"), 2, 0);

	// Adding Fields
	addComponentToPanel(selectorPanel, gbc, dayField, 0, 1);
	addComponentToPanel(selectorPanel, gbc, monthSelector, 1, 1);
	addComponentToPanel(selectorPanel, gbc, yearField, 2, 1);

	return selectorPanel;
    }

    public void setDay(int day) {
	dayField.setText(Integer.toString(day));
    }

    public int getDay() {
	return Integer.parseInt(dayField.getText());
    }

    public void setMonthIndex(int index) {
	if (index >= monthSelector.getItemCount()) {
	    throw new Error(
		    "The month selector index is out of bounds (must be greater or equal to 0 and smaller than 12)");
	}
	monthSelector.setSelectedIndex(index);
    }

    public int getMonth() {
	return monthSelector.getSelectedIndex();
    }

    public void setYear(int year) {
	yearField.setText(Integer.toString(year));
    }

    public int getYear() {
	return Integer.parseInt(yearField.getText());
    }
}