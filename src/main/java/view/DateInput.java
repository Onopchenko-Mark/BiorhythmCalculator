package view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.time.LocalDate;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import utils.AppConstants;
import utils.TextUtils;

public class DateInput {
    private JTextField dayField, yearField;
    private JComboBox<String> monthSelector;
    private String label;
    private JPanel panel;

    public DateInput(LocalDate date, String label) {
	this.label = label;
	this.dayField = createTextField();
	this.monthSelector = createSelector(AppConstants.MONTHS);
	this.yearField = createTextField();
	setDate(date);
	panel = createPanel();
    }

    public JPanel getInputPanel() {
	return panel;
    }

    public void setDate(LocalDate date) {
	dayField.setText(Integer.toString(date.getDayOfMonth()));
	monthSelector.setSelectedIndex(date.getMonthValue() - 1);
	yearField.setText(Integer.toString(date.getYear()));
    }

    public LocalDate getDate() {
	return LocalDate.of(Integer.parseInt(yearField.getText()), monthSelector.getSelectedIndex() + 1,
		Integer.parseInt(dayField.getText()));
    }

    private JPanel createPanel() {
	JPanel mainPanel = new JPanel();
	mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

	JLabel titleLabel = TextUtils.createTitleLabel(label);
	titleLabel.setFont(AppConstants.TITLE_FONT);
	mainPanel.add(titleLabel);

	// Add small gap between label and input
	mainPanel.add(Box.createVerticalStrut(5));

	JPanel inputsGrid = new JPanel(new GridBagLayout());
	GridBagConstraints gbc = new GridBagConstraints();

	// Reset Insets to avoid extra space
	gbc.insets = new Insets(0, 0, 0, 0);

	// Adding Labels
	addComponentToPanel(inputsGrid, gbc, TextUtils.createSmallLabel("Day"), 0, 0);
	addComponentToPanel(inputsGrid, gbc, TextUtils.createSmallLabel("Month"), 1, 0);
	addComponentToPanel(inputsGrid, gbc, TextUtils.createSmallLabel("Year"), 2, 0);

	// Adding Fields
	addComponentToPanel(inputsGrid, gbc, dayField, 0, 1);
	addComponentToPanel(inputsGrid, gbc, monthSelector, 1, 1);
	addComponentToPanel(inputsGrid, gbc, yearField, 2, 1);

	// Add the inputs panel to the main panel
	mainPanel.add(inputsGrid);

	return mainPanel;
    }

    private JTextField createTextField() {
	JTextField textField = new JTextField(5);
	textField.setFont(AppConstants.SMALL_FONT);
	return textField;
    }

    private JComboBox<String> createSelector(String[] options) {
	JComboBox<String> selector = new JComboBox<>(options);
	selector.setFont(AppConstants.SMALL_FONT);
	return selector;
    }

    private void addComponentToPanel(JPanel panel, GridBagConstraints gbc, Object component, int gridx, int gridy) {
	gbc.gridx = gridx;
	gbc.gridy = gridy;
	panel.add((java.awt.Component) component, gbc);
    }
}