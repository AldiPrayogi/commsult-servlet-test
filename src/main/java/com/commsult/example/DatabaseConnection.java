package com.commsult.example;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;

public class DatabaseConnection {
	
	Connection con;
	Statement st;
	
	protected static Connection initialize()
	        throws SQLException, ClassNotFoundException
	    {
	        // Initialize all the information regarding
	        // Database Connection
	        String dbDriver = "com.mysql.jdbc.Driver";
	        String dbURL = "jdbc:mysql:// localhost:3306/";
	        // Database name to access
	        String dbName = "commsult-test";
	        String dbUsername = "root";
	        String dbPassword = "root";
	  
	        Class.forName(dbDriver);
	        Connection con = DriverManager.getConnection(dbURL + dbName,
	                                                     dbUsername, 
	                                                     dbPassword);
	        return con;
	    }
	
	public void initializeDatabase()
	throws SQLException, ClassNotFoundException {
		String dbDriver = "com.mysql.jdbc.Driver";
		String dbURL = "jdbc:mysql://localhost:3306/";
		
		String dbName = "commsult-test";
		String dbUsername = "root";
		String dbPassword = "root";
		
		Class.forName(dbDriver);
		con = DriverManager.getConnection(dbURL + dbName, dbUsername, dbPassword);
		
		st = con.createStatement();
	}
	
	public ResultSet executeQuery(String query) throws Exception{
		return st.executeQuery(query);
	}
	
	public void closeCon() throws Exception{
		con.close();
	}
	
	//insert update delete
	public void executeUpdate(String query) throws Exception{
		st.executeUpdate(query);
	}
}
