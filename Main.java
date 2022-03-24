package database;

import java.sql.*;

public class Main {

	public static void main(String[] args) {
		new LoginScreen();
	}

	public static void createHomePage(Connection conn) {
		new HomePage(conn);
	}
	
	public static void createNewMenu(Connection conn) {
		new NewMenu(conn);
	}
	
	public static void createDeleteMenu(Connection conn) {
		new DeleteMenu(conn);
	}

	public static void createEditMenu(Connection conn) {
		new EditMenu(conn);
	}

}
