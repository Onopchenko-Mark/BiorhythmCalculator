package biorhythmcalc;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class Main {
    private static DefaultTableModel model;

    private static DateInput birthDateInput, targetDateInput;

    private static JPanel centerPanel;
    private static JFreeChart chart;
    private static BiorhythmRecord currentBiorhythm;

    public static void main(String[] args) {
	try {
	    // Adjusts the program's components to be of the same design
	    // language as the operating system that the program runs on
	    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	    JFrame.setDefaultLookAndFeelDecorated(true);
	} catch (Exception e) {
	    JOptionPane.showMessageDialog(null, "UIManager failed to load the setLookAndFeel method!", "Error!",
		    JOptionPane.ERROR_MESSAGE);
	}

	showTutorialScreen();
    }

    static void showTutorialScreen() {
	// Initializes the tutorial window
	JFrame frame = new JFrame("Welcome!");

	frame.setLayout(new BorderLayout());
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	// Initializes the title
	JLabel title = new JLabel("Welcome!", SwingConstants.CENTER);
	title.setFont(new Font("Verdana", Font.BOLD, 30));

	// Initializes the Panel which will contain the image and the text
	JPanel centerPanel = new JPanel();

	JLabel text = new JLabel(
		"<html>Biorhythms, as pseudoscience, were developed by Wilhelm Fliess in the late 19th century,<br/>"
			+ "and were popularized in the United States in the late 1970s.<br/><br/>"
			+ "The biorhythm theory suggests that humans have 3 biological cycles:<br/><br/>"
			+ "<font color='red'>Physical - coordination, strength, well-being</font><br/>"
			+ "<font color='green'>Emotional - creativity, sensitivity, mood</font><br/>"
			+ "<font color='blue'>Intellectual - alertness, memory, analytical functioning</font><br/><br/>"
			+ "All the cycles begin at birth, and have a sinusoidal pattern<br/>"
			+ "All the cycles have values ranging from 1 to -1<br/>"
			+ "The higher the value, the better you will do in that particular area<br/>"
			+ "The lower the cycle, the more difficult your life will be<br/><br/>"
			+ "Want to know your biorhythm?<br/>" + "The knowledge is one 'continue' press away!<html>");

	text.setFont(AppConstants.NORMAL_FONT);

	// Initializes the image
	JLabel tutorialImg = new JLabel();

	try {
	    URI tutorialImgURI = new URI("https://miro.medium.com/max/512/1*XGk-4-N5QFRC15NVvFHkzQ.jpeg");
	    ImageIcon tutorialImgLabel = new ImageIcon(tutorialImgURI.toURL());
	    tutorialImg.setIcon(
		    new ImageIcon(tutorialImgLabel.getImage().getScaledInstance(500, 350, Image.SCALE_SMOOTH)));
	} catch (MalformedURLException e) {
	    JOptionPane.showMessageDialog(null, "Error!", "Tutorial image URL could not be found!",
		    JOptionPane.ERROR_MESSAGE);
	} catch (URISyntaxException u) {
	    JOptionPane.showMessageDialog(null, "Error!", "Tutorial image URI is malformed!",
		    JOptionPane.ERROR_MESSAGE);
	}

	centerPanel.add(tutorialImg);
	centerPanel.add(text);

	JButton continueBtn = new JButton("Continue");
	continueBtn.setBackground(Color.GREEN);
	continueBtn.setFont(AppConstants.TITLE_FONT);

	// Launches the program after the 'continue' button is pressed
	continueBtn.addActionListener(e -> {
	    frame.dispose();
	    initializeMainScreen();
	});

	frame.add(title, BorderLayout.NORTH);
	frame.add(centerPanel, BorderLayout.CENTER);
	frame.add(continueBtn, BorderLayout.SOUTH);

	frame.pack();
	frame.setLocationRelativeTo(null);
	frame.setResizable(false);
	frame.setVisible(true);
    }

    static void initializeMainScreen() {
	JFrame f = new JFrame("Biorhythm Calculator");
	f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	// splits the window in half vertically
	JSplitPane mainPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, initializeLeftSide(), initializeRightSide());
	// makes the middle divider impossible to move around
	mainPanel.setEnabled(false);

	// adds all the components of the window (buttons, labels, text fields)
	f.add(mainPanel);

	f.pack();
	f.setLocationRelativeTo(null);
	f.setResizable(false);
	f.setVisible(true);

	resetComponents();
    }

    // Resets all the text fields and the JTable when the 'clear' button is pressed
    static void resetComponents() {
	// Set the birthday text fields equal to the first of January, 2000
	birthDateInput.setDay(1);
	birthDateInput.setMonthIndex(0);
	birthDateInput.setYear(1999);

	// Set the target text fields equal to the current date
	LocalDate currentDate = LocalDate.now();
	targetDateInput.setDay(currentDate.getDayOfMonth());
	targetDateInput.setMonthIndex(currentDate.getMonthValue() - 1);
	targetDateInput.setYear(currentDate.getYear());

	// Clears the JTable
	for (int i = 0; i < 4; i++)
	    model.setValueAt(null, 0, i);

	// Resets the current biorhythm
	currentBiorhythm = null;

	// RESETS THE GRAPH //
	createGraph();
    }

    // Initializes the right side of the JFrame
    @SuppressWarnings("serial")
    static JComponent initializeRightSide() {
	JPanel mainPanel = new JPanel(new BorderLayout());
	mainPanel.setBackground(Color.BLACK);

	centerPanel = new JPanel(new BorderLayout());

	// "Results" Label //
	{
	    JLabel resultsLabel = new JLabel("Results", SwingConstants.CENTER);
	    resultsLabel.setForeground(Color.WHITE);
	    resultsLabel.setFont(AppConstants.TITLE_FONT);
	    mainPanel.add(resultsLabel, BorderLayout.NORTH);
	}

	// TABLE //
	{
	    JPanel tablePanel = new JPanel(new BorderLayout());

	    // Initialize the model of the Table (header + one row)
	    model = new DefaultTableModel(new String[] { "Date", "<html><font color='red'>Physical</font><html>",
		    "<html><font color='blue'>Emotional</font><html>",
		    "<html><font color='green'>Intellectual</font><html>" }, 1) {
		// Overrides the 'isCellEditable' method of the JTable, which by default, allows
		// the user to edit the contents of a cell in a JTable
		// Instead, this overridden method restricts the user from editing the JTable
		@Override
		public boolean isCellEditable(int row, int column) {
		    // all cells false
		    return false;
		}
	    };

	    JTable table = new JTable(model) {
		// Overrides the 'prepareRenderer' method of the JTable, which by default,
		// renders every cell of the JTable a white color
		// Instead, this overridden method changes the cell color depending on the value
		// that it contains
		@Override
		public Component prepareRenderer(TableCellRenderer renderer, int rowIndex, int columnIndex) {
		    JComponent component = (JComponent) super.prepareRenderer(renderer, rowIndex, columnIndex);
		    component.setBackground(Color.WHITE);

		    Object cellValue = getValueAt(rowIndex, columnIndex);
		    // paint an empty cell or a cell which contains the date white
		    if (cellValue == null || columnIndex == 0)
			return component;

		    try {
			double value = Double.parseDouble(getValueAt(rowIndex, columnIndex).toString());
			component.setBackground(getColorForValue(value));
		    } catch (NullPointerException e) {
			// No need to print out an error, as this exception will only occur when the
			// JTable is empty
		    }

		    return component;
		}

		private Color getColorForValue(double value) {
		    return value > 0.3 ? Color.GREEN : (value < -0.3 ? Color.RED : Color.YELLOW);
		}
	    };

	    table.setRowSelectionAllowed(false);
	    table.setShowGrid(false);
	    table.setFont(AppConstants.SMALL_FONT);
	    table.getTableHeader().setReorderingAllowed(false);
	    table.getTableHeader().setFont(AppConstants.SMALL_FONT);

	    tablePanel.add(table.getTableHeader(), BorderLayout.NORTH);
	    tablePanel.add(table, BorderLayout.CENTER);

	    centerPanel.add(tablePanel, BorderLayout.NORTH);
	}

	// GRAPH //
	createGraph();
	// the graph is added to the centerPanel is the createGraph() function

	mainPanel.add(centerPanel, BorderLayout.CENTER);

	// BUTTONS //

	{
	    JPanel buttonPanel = new JPanel();
	    buttonPanel.setBackground(Color.BLACK);

	    JButton clearBtn = new JButton("Clear");

	    clearBtn.setFont(AppConstants.NORMAL_FONT);

	    // Calls the 'reset()' function when pressed
	    clearBtn.addActionListener(e -> resetComponents());

	    buttonPanel.add(clearBtn);

	    mainPanel.add(buttonPanel, BorderLayout.SOUTH);
	}

	return mainPanel;
    }

    static void createGraph() {
	// creates three functions
	XYSeries phys = new XYSeries("Physical");
	XYSeries emo = new XYSeries("Emotional");
	XYSeries intel = new XYSeries("Intellectual");
	List<XYSeries> seriesList = List.of(phys, emo, intel);
	// stores all functions in a shared group
	XYSeriesCollection dataset = new XYSeriesCollection();

	if (currentBiorhythm != null) {
	    long dayDifference = ChronoUnit.DAYS.between(currentBiorhythm.getBirthDate(),
		    currentBiorhythm.getTargetDate());
	    // adds the data points to the function
	    for (double x = 0.0; x < 10; x += 0.1) {
		phys.add(x, Math.sin((2 * Math.PI * (x + dayDifference)) / 23));
		emo.add(x, Math.sin((2 * Math.PI * (x + dayDifference)) / 28));
		intel.add(x, Math.sin((2 * Math.PI * (x + dayDifference)) / 33));
	    }

	    for (XYSeries series : seriesList) {
		dataset.addSeries(series);
	    }
	}

	// Chart title, X axis, Y axis, dataset used, plot orientation, legend,
	// tooltips, urls
	chart = ChartFactory.createXYLineChart(null, "Date (From the Target Date)", null, dataset,
		PlotOrientation.VERTICAL, true, true, false);

	chart.getXYPlot().setDataset(chart.getXYPlot().getDataset());

	ChartPanel chartPanel = new ChartPanel(chart);

	centerPanel.removeAll();
	centerPanel.revalidate();
	centerPanel.add(chartPanel, BorderLayout.CENTER);
	centerPanel.repaint();
    }

    // Initializes the left-side-panel of the JFrame
    static JComponent initializeLeftSide() {
	JPanel leftSidePanel = new JPanel(new GridBagLayout());
	leftSidePanel.setBackground(new Color(230, 230, 230)); // Light gray background

	// Image displayed at the top of the screen
	JLabel headerImg = new JLabel();
	try {
	    URI headerImgURI = new URI(
		    "https://sun9-55.userapi.com/impf/mw5y60DKKbsWUa_IVT6JM4Yd3L7lTR46K-xzkQ/TotfrMQj4X8.jpg?size=600x335&quality=96&sign=905b294e882c2307e9f6bdd8a2a02935&type=album");
	    ImageIcon headerImgIcon = new ImageIcon(headerImgURI.toURL());
	    headerImg.setIcon(new ImageIcon(headerImgIcon.getImage().getScaledInstance(300, 150, Image.SCALE_SMOOTH)));
	} catch (Exception e) {
	    JOptionPane.showMessageDialog(null, "Error!", "Image could not be loaded!", JOptionPane.ERROR_MESSAGE);
	}

	// Birthday Input Section
	JLabel bDayLabel = new JLabel("Birthdate");
	bDayLabel.setFont(AppConstants.TITLE_FONT);

	// Target Date Input Section
	JLabel targetDayLabel = new JLabel("Target Date");
	targetDayLabel.setFont(AppConstants.TITLE_FONT);

	birthDateInput = new DateInput();
	targetDateInput = new DateInput();

	JPanel birthDateInputPanel = birthDateInput.getInputPanel();
	JPanel targetDateInputPanel = targetDateInput.getInputPanel();

	// The 'Calculate' button
	JButton calcButton = new JButton("Calculate");
	calcButton.setFont(AppConstants.TITLE_FONT);

	// BUTTON PRESS //
	calcButton.addActionListener(e -> {
	    try {
		LocalDate birthDate = LocalDate.of(birthDateInput.getYear(), birthDateInput.getMonth() + 1,
			birthDateInput.getDay());
		LocalDate targetDate = LocalDate.of(targetDateInput.getYear(), targetDateInput.getMonth() + 1,
			targetDateInput.getDay());

		// Biorhythm is calculated
		currentBiorhythm = new BiorhythmRecord(birthDate, targetDate);

		// Updates the JTable
		model.setValueAt(currentBiorhythm.getTargetDate().toString(), 0, 0);
		model.setValueAt(currentBiorhythm.getPhysical(), 0, 1);
		model.setValueAt(currentBiorhythm.getEmotional(), 0, 2);
		model.setValueAt(currentBiorhythm.getIntellectual(), 0, 3);

		// Updates the graph
		createGraph();
	    } catch (NumberFormatException exception) {
		JOptionPane.showMessageDialog(null, "Invalid Input Entered!", "Error!", JOptionPane.ERROR_MESSAGE);
	    }
	});

	// COMPONENT PLACEMENT //
	int row = 0;

	// Header Image
	leftSidePanel.add(headerImg, createGbc(0, row++, 3, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.CENTER,
		new Insets(10, 0, 10, 0), 0.1));

	// Birthdate Section
	leftSidePanel.add(bDayLabel, createGbc(0, row++, 3, 1, GridBagConstraints.NONE, GridBagConstraints.CENTER,
		new Insets(15, 0, 5, 0), 0.3));
	leftSidePanel.add(birthDateInputPanel, createGbc(0, row++, 3, 1, GridBagConstraints.NONE,
		GridBagConstraints.CENTER, new Insets(5, 0, 10, 0), 0.0));

	// Target Date Section
	leftSidePanel.add(targetDayLabel, createGbc(0, row++, 3, 1, GridBagConstraints.NONE, GridBagConstraints.CENTER,
		new Insets(15, 0, 5, 0), 0.3));
	leftSidePanel.add(targetDateInputPanel, createGbc(0, row++, 3, 1, GridBagConstraints.NONE,
		GridBagConstraints.CENTER, new Insets(5, 0, 10, 0), 0.0));

	leftSidePanel.add(calcButton, createGbc(1, row, 1, 1, GridBagConstraints.NONE, GridBagConstraints.CENTER,
		new Insets(50, 0, 10, 0), 1.0));

	return leftSidePanel;
    }

    // Updated Helper Method for GridBagConstraints
    private static GridBagConstraints createGbc(int x, int y, int width, int height, int fill, int anchor,
	    Insets insets, double weighty) {
	GridBagConstraints gbc = new GridBagConstraints();
	gbc.gridx = x;
	gbc.gridy = y;
	gbc.gridwidth = width;
	gbc.gridheight = height;
	gbc.fill = fill;
	gbc.anchor = anchor;
	gbc.insets = insets;
	gbc.weightx = 1;
	gbc.weighty = weighty;
	return gbc;
    }

}