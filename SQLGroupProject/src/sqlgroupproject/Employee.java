package sqlgroupproject;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class Employee {
	public static final String createTable =
			"CREATE TABLE Employee ("
			+ "Id  int not null primary key "
			+ "GENERATED ALWAYS AS IDENTITY "
			+ "(START WITH 100, INCREMENT BY 1),"
			+ "FirstName varchar(255),"
			+ "LastName varchar(255),"
			+ "JobTitle varchar(255),"
			+ "DOB varchar(255),"
			+ "StoreID int"
			+ ")";
	
	public static final String insertData =
			"INSERT INTO Employee (FirstName, LastName, JobTitle, DOB, StoreID) VALUES "
			+ "('Tom','Ballinger', 'Clerk', '03/16/03', 3),"
			+ "('Jessie','Romero', 'Manager', '07/21/97', 1),"
			+ "('Barry','Binkerhoff', 'Stocker', '01/05/02', 1)";
	
	
	public static final String selectAll =
			"SELECT * FROM Employee";
	
	
	public static final String dropTable =
			"DROP TABLE Employee";
	
	/**
	 * Prints the table out as a string (mostly for testing right now)
	 * @param resultSet
	 * @throws SQLException
	 */
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
