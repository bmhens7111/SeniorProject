package test;
import java.sql.*;

public class Driver {

	public static void main(String[] args) {
		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/new_schema", "root", "Baked1alaska");
			Statement stmt = conn.createStatement();
			stmt.executeUpdate("create table Items(id integer, name varchar(16), quantity integer)");
			System.out.println("Table created.");
		}
		catch (Exception exc){
			exc.printStackTrace();
		}

	}

}
