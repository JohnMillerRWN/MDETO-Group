package BOE;

//Establish a connection to a mSQL database using JDBC
import java.sql.*; 

class JdbcTest { 

	public static void main (String[] args) { 
		try
		{
			// Step 1: "Load" the JDBC driver
			Class.forName("com.mysql.cj.jdbc.Driver"); 

			// Step 2: Establish the connection to the database 
			String url = "jdbc:mysql://localhost:3306/sakila"; 
			Connection conn = DriverManager.getConnection(url,"test","password");
			
			// Step 3: Query the database
			Statement stmt = conn.createStatement();  
			ResultSet result = stmt.executeQuery("SELECT * FROM actor LIMIT 10");  
			
			//Step 4: Print out result of query
			while( result.next() ) {
				System.out.println( result.getInt(1) + "\t| " + result.getString(2) + "  " + result.getString(3) );
			}
			
			//Step 5: Close the database connection
			conn.close();
		}
		catch (Exception e)
		{
			System.err.println("D'oh! Got an exception!"); 
			System.err.println(e.getMessage()); 
		} 
	} 
}