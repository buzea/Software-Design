package dataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class Mapper {
	
	// JDBC driver name and database URL
		 public static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
		 public static final String DB_URL = "jdbc:mysql://localhost/bank";

		   //  Database credentials
		 protected static final String USER = "root";
		 protected static final String PASS = "";
		 protected  Connection conn = null;
		 protected  Statement stmt = null;
	
	public Mapper() {
		try{
		      //STEP 2: Register JDBC driver
		      Class.forName("com.mysql.jdbc.Driver");

		      conn = DriverManager.getConnection(DB_URL,USER,PASS);

		      stmt = conn.createStatement();
		}catch(SQLException e){
			
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		}
	}
	
	@Override
	public void finalize() throws Throwable{
		super.finalize();
		conn.close();
		
	}

}
