package view;

import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import model.RecordModel;

public class BiorhythmChart {
    private static final List<Integer> CYCLE_LENGTHS = Arrays.asList(23, 28, 33);
    private static final String[] SERIES_NAMES = { "Physical", "Emotional", "Intellectual" };

    private XYSeriesCollection dataset;
    private JFreeChart chart;
    private ChartPanel chartPanel;

    BiorhythmChart() {
	this(null);
    }

    BiorhythmChart(RecordModel record) {
	this.dataset = createDataset(record);
	this.chart = createChart();
	this.chartPanel = new ChartPanel(chart);
    }

    private XYSeriesCollection createDataset(RecordModel record) {
	var dataset = new XYSeriesCollection();

	if (record == null)
	    return dataset;

	long dayDifference = ChronoUnit.DAYS.between(record.getBirthDate(), record.getTargetDate());

	for (int i = 0; i < CYCLE_LENGTHS.size(); i++) {
	    XYSeries series = new XYSeries(SERIES_NAMES[i]);
	    for (double x = 0.0; x < 10; x += 0.1) {
		series.add(x, Math.sin((2 * Math.PI * (x + dayDifference)) / CYCLE_LENGTHS.get(i)));
	    }
	    dataset.addSeries(series);
	}

	return dataset;
    }

    private JFreeChart createChart() {
	return ChartFactory.createXYLineChart(null, "Date (From the Target Date)", null, dataset,
		PlotOrientation.VERTICAL, true, true, false);
    }

    public JPanel getChartPanel() {
	return chartPanel;
    }

}
