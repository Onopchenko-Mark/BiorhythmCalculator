package view;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import utils.ButtonUtils;
import utils.TextUtils;

public class ResultsScreen extends JFrame {
    private static final long serialVersionUID = 78783845135283862L;
    private static String[] columns = { "Birth Date", "Target Date", "Physical", "Emotional", "Intellectual" };
    private DefaultTableModel model = new DefaultTableModel(columns, 0);
    private JButton deleteButton = ButtonUtils.createLargeButton("Delete All");

    public ResultsScreen() {
	setTitle("Past Calculations");
	setLayout(new BorderLayout());

	JLabel titleLabel = TextUtils.createTitleLabel("Past Results");

	add(titleLabel, BorderLayout.NORTH);
	add(new JScrollPane(new JTable(model)), BorderLayout.CENTER);
	add(deleteButton, BorderLayout.SOUTH);

	setAlwaysOnTop(true);
	setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	pack();
	setLocationRelativeTo(null);
	setVisible(false);
    }

    public void setTable(List<String[]> records) {
	model.setRowCount(0); // Clears the table before adding new data
	for (var row : records)
	    model.addRow(row);
    }

    public void addDeleteRecordsListener(ActionListener listener) {
	deleteButton.addActionListener(listener);
    }

//    GETTERS + SETTERS
    public JButton getDeleteButton() {
	return deleteButton;
    }
}
