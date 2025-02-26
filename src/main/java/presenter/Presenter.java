package presenter;

import java.time.LocalDate;

import model.RecordDao;
import model.RecordModel;
import view.View;

public class Presenter {
    View view;
    RecordDao model;

    public Presenter(View view, RecordDao model) {
	this.view = view;
	this.model = model;

	view.addCalculateListener(e -> registerRecord());
	view.addResultsListener(e -> showResults());
	view.addDeleteRecordsListener(e -> deleteRecords());
    }

    // ACTION LISTENERS
    private void registerRecord() {
	try {
	    LocalDate birthDate = view.getBirthDate();
	    LocalDate targetDate = view.getTargetDate();

	    var record = new RecordModel(birthDate, targetDate);
	    model.insertResults(record);

	    view.updateMainScreen(record);
	    view.updateResultsScreen(model.getResults());
	} catch (NumberFormatException exception) {
	    View.showErrorMessage("Invalid Input Entered!");
	}
    }

    private void showResults() {
	view.renderResultsScreen(model.getResults());
    }

    private void deleteRecords() {
	model.deleteResults();
	view.updateResultsScreen(model.getResults());
    }
}
