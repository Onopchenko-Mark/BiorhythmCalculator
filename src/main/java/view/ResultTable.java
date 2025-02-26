package view;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

import model.RecordModel;
import utils.AppConstants;

public class ResultTable extends JTable {
    private static final long serialVersionUID = 907910458865846065L;
    private static final String[] TABLE_COLS = { "Date", "Physical", "Emotional", "Intellectual" };
    private static final Color[] HEADER_COLORS = { Color.BLACK, Color.RED, Color.BLUE, Color.GREEN };
    private static final TableModel model = new DefaultTableModel(TABLE_COLS, 1) {
	private static final long serialVersionUID = 1L;

	@Override
	public boolean isCellEditable(int row, int column) {
	    // all cells false
	    return false;
	}
    };

    ResultTable() {
	super(model);
	setRowSelectionAllowed(false);
	setShowGrid(false);
	setFont(AppConstants.SMALL_FONT);
	setShowGrid(true);
	customizeTableHeader();
    }

    public TableModel getModel() {
	return model;
    }

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
	} catch (NumberFormatException e) {
	    // Ignore non-numeric values (e.g., column headers)
	}

	return component;
    }

    private Color getColorForValue(double value) {
	return value > 0.3 ? Color.GREEN : (value < -0.3 ? Color.RED : Color.YELLOW);
    }

    private void customizeTableHeader() {
	JTableHeader header = getTableHeader();
	header.setReorderingAllowed(false);
	header.setFont(AppConstants.SMALL_FONT);
	header.setDefaultRenderer(new DefaultTableCellRenderer() {
	    @Override
	    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
		    boolean hasFocus, int row, int column) {
		JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
			column);
		label.setForeground(HEADER_COLORS[column]); // Set the header text color
		label.setHorizontalAlignment(SwingConstants.CENTER);
		return label;
	    }
	});
    }

    public void reset() {
	for (int i = 0; i < TABLE_COLS.length; i++)
	    getModel().setValueAt(null, 0, i);
    }

    public void updateTable(RecordModel record) {
	if (record == null) {
	    reset();
	    return;
	}

	getModel().setValueAt(record.getTargetDate().toString(), 0, 0);
	getModel().setValueAt(record.getPhysical(), 0, 1);
	getModel().setValueAt(record.getEmotional(), 0, 2);
	getModel().setValueAt(record.getIntellectual(), 0, 3);
    }
}
