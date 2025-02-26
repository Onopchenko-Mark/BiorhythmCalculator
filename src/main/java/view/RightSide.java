package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.RecordModel;
import utils.ButtonUtils;
import utils.TextUtils;

public class RightSide extends JPanel implements AppLayout {
    private static final long serialVersionUID = -4996207391210298138L;

    private final JPanel chartContainer;
    private ResultTable resultTable = new ResultTable();
    private BiorhythmChart currentChart;
    private JButton resetButton = ButtonUtils.createButton("Reset");
    private JButton resultsButton = ButtonUtils.createButton("Past Results");

    RightSide() {
	setLayout(new BorderLayout());

	chartContainer = new JPanel();

	JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
	buttonPanel.add(resetButton);
	buttonPanel.add(resultsButton);

	add(chartContainer, BorderLayout.CENTER);
	add(createHeaderSection(), BorderLayout.NORTH);
	add(buttonPanel, BorderLayout.SOUTH);

	resetUI();
    }

    private JPanel createHeaderSection() {
	JPanel headerPanel = new JPanel();
	headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));

	JLabel resultsLabel = TextUtils.createTitleLabel("Results");

	JPanel labelPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
	labelPanel.add(resultsLabel);

	headerPanel.add(labelPanel);

	JPanel tablePanel = new JPanel(new BorderLayout());
	tablePanel.add(resultTable.getTableHeader(), BorderLayout.NORTH);
	tablePanel.add(resultTable, BorderLayout.CENTER);
	headerPanel.add(tablePanel);

	return headerPanel;
    }

    public void addResetListener(ActionListener listener) {
	resetButton.addActionListener(listener);
    }

    public void addResultsListener(ActionListener listener) {
	resultsButton.addActionListener(listener);
    }

    @Override
    public void resetUI() {
	resultTable.reset();
	updateGraph(null);
    }

    public void render(RecordModel record) {
	if (record == null)
	    resetUI();

	resultTable.updateTable(record);
	updateGraph(record);
    }

    private void updateGraph(RecordModel record) {
	chartContainer.removeAll();

	currentChart = new BiorhythmChart(record);
	chartContainer.add(currentChart.getChartPanel());

	chartContainer.revalidate();
	chartContainer.repaint();
    }

    public JButton getResultsButton() {
	return resultsButton;
    }

    public JButton getResetButton() {
	return resetButton;
    }
}
