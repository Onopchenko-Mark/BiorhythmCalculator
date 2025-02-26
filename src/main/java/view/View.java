package view;

import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import model.RecordModel;

public class View {
    private ResultsScreen resultsScreen;
    private MainScreen mainScreen;
    private ActionListener deleteRecordsListener;

    public View() {
	mainScreen = new MainScreen();
	mainScreen.addResetListener(e -> mainScreen.reset());
    }

    public void updateMainScreen(RecordModel record) {
	mainScreen.render(record);
    }

    public void updateResultsScreen(List<String[]> records) {
	if (isResultsScreenVisible())
	    resultsScreen.setTable(records);
    }

    public void renderResultsScreen(List<String[]> records) {
	if (resultsScreen == null || !resultsScreen.isDisplayable()) {
	    resultsScreen = new ResultsScreen();

	    if (deleteRecordsListener != null)
		resultsScreen.getDeleteButton().addActionListener(deleteRecordsListener);
	}

	resultsScreen.setTable(records);
	resultsScreen.setVisible(true);
    }

    // LISTENERS
    public void addCalculateListener(ActionListener listener) {
	mainScreen.addCalculateListener(listener);
    }

    public void addResultsListener(ActionListener listener) {
	mainScreen.addResultsListener(listener);
    }

    public void addDeleteRecordsListener(ActionListener listener) {
	deleteRecordsListener = listener; // Store the listener

	if (resultsScreen != null) {
	    resultsScreen.getDeleteButton().addActionListener(listener);
	}
    }

    // UTILS
    public static void showErrorMessage(String message) {
	JOptionPane.showMessageDialog(null, "Error!", message, JOptionPane.ERROR_MESSAGE);
    }

    // GETTERS + SETTERS
    public LocalDate getBirthDate() {
	return mainScreen.getBirthDate();
    }

    public LocalDate getTargetDate() {
	return mainScreen.getTargetDate();
    }

    public boolean isResultsScreenVisible() {
	return resultsScreen != null && resultsScreen.isDisplayable() && resultsScreen.isShowing()
		&& resultsScreen.getExtendedState() != JFrame.ICONIFIED;
    }
}
