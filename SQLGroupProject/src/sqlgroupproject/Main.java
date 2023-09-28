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
		try(Connection connection = DriverManager.getConnection(databaseURL);
			Statement statement = connection.createStatement()){
				//statement.execute(Store.createTable);
				//statement.execute(Store.insertData);
				ResultSet resultset = statement.executeQuery(Employee.selectAll);
				printTableData(resultset);
				resultset = statement.executeQuery(Store.selectAll);
				System.out.println();
				printTableData(resultset);
			}
				
		catch(SQLException e)
		{
			System.out.println("Something went wrong accessing SQL.");
			e.printStackTrace();
		}
	}
	
	public static void addEmployee(String fName, String LName, String title, String dob, int storeID) {
		
		Connection connection;
		try {
			connection = DriverManager.getConnection(databaseURL);
			PreparedStatement prep = connection.prepareStatement(Employee.insertData);
			prep.setString(1, fName);
			prep.setString(2, LName);
			prep.setString(3, title);
			prep.setString(4, dob);
			prep.setInt(5, storeID);
			
		} catch (SQLException e) {
			System.out.println("There was a problem adding a new employee to the Employee Database.");
			e.printStackTrace();
		}
        
		
		
		
		
		
	}

private static void printTableData(ResultSet resultSet) throws SQLException {
	ResultSetMetaData metaData = resultSet.getMetaData();
	
	//print header
	int dashCount = 0;
	for(int i = 1; i <= metaData.getColumnCount(); i++) {
		System.out.print(metaData.getColumnLabel(i) + " ");
		dashCount += metaData.getColumnLabel(i).length() + 1;
	}
	System.out.println();
	System.out.println("-".repeat(--dashCount));
	
	//print data
	while(resultSet.next()) {
		for(int i = 1; i<= metaData.getColumnCount(); i++ ) {
			System.out.printf("%-" + (metaData.getColumnLabel(i).toString().length()+1) +
					"s", resultSet.getObject(i) + " ");
		}
		System.out.println();
	}
	
}
}