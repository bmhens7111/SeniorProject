package database;

import java.sql.*;

public class Sql {
	static PreparedStatement preparedStatement;
	
	//Adds a new item to the table
	public static void insert(Connection conn, Item item) {
		try {
			String sql = "insert into items values (?, ?, ?, ?, ?, ?)";
			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, item.getId());
			preparedStatement.setString(2, item.getName());
			preparedStatement.setInt(3, item.getQuantity());
			preparedStatement.setString(4, item.getTags());
			preparedStatement.setString(5, item.getLocation());
			preparedStatement.setString(6, item.getDateAdded());
			preparedStatement.executeUpdate();
			System.out.println(preparedStatement);
			System.out.println("INSERT MENU: " + item.getName() + " created");
		}
		catch(SQLException e) {
			e.printStackTrace();
			new Popup("Something went Wrong", "A mySQL error has occured, please try again");
		}
		HomePage.table.setModel(HomePage.getModel(conn));
	}
	
	//Returns list of items with a given id, used for sorting and deletion
	public static ResultSet selectWhereId(Connection conn, int id) {
		ResultSet result = null;
		try {
			String sql = "select * from items where id=?";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, id);
			result = preparedStatement.executeQuery();
			System.out.println(preparedStatement);
			System.out.println("RESULT: retrieving items with ID: " + id);
		} 
		catch (SQLException e) {
			e.printStackTrace();
			new Popup("Something went Wrong", "A mySQL error has occured, please try again");
		}
		return result;
	}
	
	//Updates the parameters of an item
	public static void update(Connection conn, Item item, int newId) {
		try {
			String sql = "Update items "
						+ "set id=?, name=?, quantity=?, tags=?, location=?"
						+ "where id=?";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, newId);
			preparedStatement.setString(2, item.getName());
			preparedStatement.setInt(3, item.getQuantity());
			preparedStatement.setString(4, item.getTags());
			preparedStatement.setString(5, item.getLocation());
			preparedStatement.setInt(6, item.getId());
			preparedStatement.executeUpdate();
			System.out.println(preparedStatement);
			System.out.println("UPDATE MENU: " + item.getName() + " updated");
		}
		catch (SQLException e) {
			e.printStackTrace();
			new Popup("Something went Wrong", "A mySQL error has occured, please try again");
		}
		HomePage.table.setModel(HomePage.getModel(conn));
	}

	//Removes an item with a given ID from the table
	public static void deleteFrom(Connection conn, int id) {
		try {
			String sql = "delete from items where id=?";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, id);
			preparedStatement.executeUpdate();
			System.out.println(preparedStatement);
			System.out.println("DELETE MENU: Item " + id + " deleted");
		}
		catch (SQLException e) {
			e.printStackTrace();
			new Popup("Something went Wrong", "A mySQL error has occured, please try again");
		}
		HomePage.table.setModel(HomePage.getModel(conn));
	}
	
	
	//Sorts the table in order by a given parameter
	public static ResultSet sortBy(Connection conn, String param, String direction) {
		ResultSet rs = null;
		String sql;
		try {
			sql = "select * from Items order by " + param + " " + direction;
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			rs = preparedStatement.executeQuery();
			System.out.println(preparedStatement);
			System.out.println("RESULT: Sorting Table by " + param);
		}
		catch (SQLException e) {
			e.printStackTrace();
			new Popup("Something went Wrong", "A mySQL error has occured, please try again");
		}
		return rs;
	}
	
}
