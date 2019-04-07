package BOE.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class db_import {
	private Connection conn;
	private ResultSet result;
	
	public boolean db_open() {
		try {
			// Step 1: "Load" the JDBC driver
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Step 2: Establish the connection to the database 
			String url = "jdbc:mysql://localhost:3306/mdeto"; 
			conn = DriverManager.getConnection(url,"test","password");
			
			return true;
		} catch (Exception e) {
			System.out.println("Could not open DB");
			System.out.println(e.getMessage());
			return false;
		}
	}
	
	public boolean db_close() {
		try {
			//Close the database connection
			conn.close();
			
			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage()); 
			return false;
		}
	}
	
	/**
	 * @param input assumes input is a valid sql query for the db
	 * @return the ResultSet object
	 */
	public ResultSet query(String input) {
		try {
			Statement stmt = conn.createStatement();  
			result = stmt.executeQuery(input);  
		} catch (Exception e) {
			System.out.println(e.getMessage()); 
		}
		return result;
	}

}
