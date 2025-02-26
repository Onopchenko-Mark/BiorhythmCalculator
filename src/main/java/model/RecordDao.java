package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class RecordDao implements RecordDaoInterface {

    @Override
    public void createTable() {
	String query = "CREATE TABLE IF NOT EXISTS results ( id INTEGER PRIMARY KEY AUTOINCREMENT, "
		+ "birthDate TEXT, targetDate TEXT, physical REAL, emotional REAL, intellectual REAL);";
	try (Connection c = DatabaseManager.connect(); Statement s = c.createStatement();) {
	    s.execute(query);
	} catch (Exception e) {
	    System.out.println("Error creating the biorhythm results table " + e.getMessage());
	}
    }

    @Override
    public void insertResults(RecordModel record) {
	String query = "INSERT INTO results (birthDate, targetDate, physical, emotional, intellectual) VALUES (?,?,?,?,?)";

	try (Connection c = DatabaseManager.connect(); PreparedStatement pst = c.prepareStatement(query);) {
	    pst.setString(1, record.getBirthDate().toString());
	    pst.setString(2, record.getTargetDate().toString());
	    pst.setDouble(3, record.getPhysical());
	    pst.setDouble(4, record.getEmotional());
	    pst.setDouble(5, record.getIntellectual());
	    pst.executeUpdate();
	} catch (Exception e) {
	    System.out.println("Error creating the biorhythm results table " + e.getMessage());
	}
    }

    @Override
    public List<String[]> getResults() {
	List<String[]> results = new ArrayList<>();
	String query = "SELECT * FROM results";

	try (Connection c = DatabaseManager.connect();
		Statement s = c.createStatement();
		ResultSet rs = s.executeQuery(query);) {
	    while (rs.next()) {
		String[] row = { rs.getString("birthDate"), rs.getString("targetDate"),
			String.valueOf(rs.getDouble("physical")), String.valueOf(rs.getDouble("emotional")),
			String.valueOf(rs.getDouble("intellectual")) };
		results.add(row);
	    }
	} catch (Exception e) {
	    System.out.println("Error getting results " + e.getMessage());
	}
	return results;
    }

    @Override
    public void deleteResults() {
	String query = "DELETE FROM results";

	try (Connection c = DatabaseManager.connect(); Statement s = c.createStatement();) {
	    s.execute(query);
	} catch (Exception e) {
	    System.out.println("Error deleting results " + e.getMessage());
	}
    }
}
