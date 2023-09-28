package sqlgroupproject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
	private static final String databaseURL = "jdbc:derby:FirstDatabase;create=true";

	public static void main(String[] args) {
		try (Connection connection = DriverManager.getConnection(databaseURL);
				Statement statement = connection.createStatement()) {
			// statement.execute(Store.createTable);
			// statement.execute(Store.insertData);
			ResultSet resultset = statement.executeQuery(Employee.selectAll);
			printTableData(resultset);
			resultset = statement.executeQuery(Store.selectAll);
			System.out.println();
			printTableData(resultset);
		}

		catch (SQLException e) {
			System.out.println("Something went wrong accessing SQL.");
			e.printStackTrace();
		}
	}

	private static void printTableData(ResultSet resultSet) throws SQLException {
		ResultSetMetaData metaData = resultSet.getMetaData();

		// print header
		int dashCount = 0;
		for (int i = 1; i <= metaData.getColumnCount(); i++) {
			System.out.print(metaData.getColumnLabel(i) + " ");
			dashCount += metaData.getColumnLabel(i).length() + 1;
		}
		System.out.println();
		System.out.println("-".repeat(--dashCount));

		// print data
		while (resultSet.next()) {
			for (int i = 1; i <= metaData.getColumnCount(); i++) {
				System.out.printf("%-" + (metaData.getColumnLabel(i).toString().length() + 1) + "s",
						resultSet.getObject(i) + " ");
			}
			System.out.println();
		}

	}

	/**
	 * Adds a new employee to the Employee Database
	 * 
	 * @param fName   - First Name
	 * @param LName   - Last Name
	 * @param title   - Job title/Position
	 * @param dob     - Date of Birth
	 * @param storeID - Store ID of the new employee
	 * 
	 * @author Edwin Casady
	 */
	public static void addEmployee(String fName, String LName, String title, String dob, int storeID) {

		try {
			Connection connection = DriverManager.getConnection(databaseURL);
			PreparedStatement prep = connection.prepareStatement(Employee.insertData);
			prep.setString(1, fName);
			prep.setString(2, LName);
			prep.setString(3, title);
			prep.setString(4, dob);
			prep.setInt(5, storeID);

			int success = prep.executeUpdate();

			resultsTest(success, "Employee");

			prep.close();
			connection.close();

		} catch (SQLException e) {
			System.out.println("There was a problem adding a new employee to the Employee Database.");
			e.printStackTrace();
		}
	}

	

	public static void newStore(int storeID, String city, int pharmacy, int zip) {
		try {
			Connection connection = DriverManager.getConnection(databaseURL);
			PreparedStatement prep = connection.prepareStatement(Store.insertData);
			prep.setInt(1, storeID);
			prep.setString(2, city);
			prep.setInt(3, pharmacy);
			prep.setInt(4, zip);

			int success = prep.executeUpdate();

			resultsTest(success, "Store");

			prep.close();
			connection.close();

		} catch (SQLException e) {
			System.out.println("There was a problem adding a new store to the Store Database.");
		}
	}
	
	private static void resultsTest(int i, String str) {
		if (i > 0)
			System.out.printf("New %s added.\n", str);
		else
			System.out.printf("Bad or incomplete data. Please retry adding new %s.\n", str);
	}

}