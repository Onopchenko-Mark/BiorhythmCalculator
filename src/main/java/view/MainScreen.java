package view;

import java.awt.event.ActionListener;
import java.time.LocalDate;

import javax.swing.JFrame;
import javax.swing.JSplitPane;

import model.RecordModel;

public class MainScreen extends JFrame {
    private static final long serialVersionUID = 1233274223353217126L;
    private LeftSide leftSide = new LeftSide();
    private RightSide rightSide = new RightSide();

    public MainScreen() {
	setTitle("Biorhythm Calculator");

	rightSide.addResetListener(e -> {
	    leftSide.resetUI();
	    rightSide.resetUI();
	});

	JSplitPane mainPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftSide, rightSide);
	mainPanel.setOneTouchExpandable(false);
	mainPanel.setEnabled(false);
	add(mainPanel);

	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	pack();
	setLocationRelativeTo(null);
	setVisible(true);
    }

    public void reset() {
	rightSide.resetUI();
	leftSide.resetUI();
    }

    public void render(RecordModel record) {
	rightSide.render(record);
    }

//  LISTENERS
    public void addCalculateListener(ActionListener listener) {
	leftSide.getCalculateButton().addActionListener(listener);
    }

    public void addResultsListener(ActionListener listener) {
	rightSide.getResultsButton().addActionListener(listener);
    }

    public void addResetListener(ActionListener listener) {
	rightSide.getResetButton().addActionListener(listener);
    }

//  GETTERS + SETTERS
    public LocalDate getBirthDate() {
	return leftSide.getBirthDate();
    }

    public LocalDate getTargetDate() {
	return leftSide.getTargetDate();
    }
}