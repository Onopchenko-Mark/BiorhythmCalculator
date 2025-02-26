package main;

import model.RecordDao;
import presenter.Presenter;
import view.TutorialScreen;
import view.View;

// LAUNCH TUT SCREEN + PASS action listener
public class Main {
    public static void main(String[] args) {
	var tutorialScreen = new TutorialScreen();
	tutorialScreen.addContinueListener(e -> startApplication());
    }

    private static void startApplication() {
	View view = new View();
	RecordDao model = new RecordDao();
	new Presenter(view, model);
    }
}
