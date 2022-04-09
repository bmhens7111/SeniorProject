package database;

import java.sql.*;

public class Sql {

	public static void insert(Connection conn, Item item) {
		try {
			String sql = "insert into items values (?, ?, ?, ?, ?)";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, item.getId());
			preparedStatement.setString(2, item.getName());
			preparedStatement.setInt(3, item.getQuantity());
			preparedStatement.setString(4, item.getTags());
			preparedStatement.setString(5, item.getDateAdded());
			System.out.print(preparedStatement);
			preparedStatement.executeUpdate();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static ResultSet selectWhereId(Connection conn, int id) {
		ResultSet result = null;
		try {
			String sql = "select * from items where id=?";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, id);
			result = preparedStatement.executeQuery();
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	public static void update(Connection conn, Item item, int newId) {
		try {
			String sql = "Update items "
						+ "set id=?, name=?, quantity=?, tags=?"
						+ "where id=?";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, newId);
			preparedStatement.setString(2, item.getName());
			preparedStatement.setInt(3, item.getQuantity());
			preparedStatement.setString(4, item.getTags());
			preparedStatement.setInt(5, item.getId());
			System.out.print(preparedStatement);
			preparedStatement.executeUpdate();
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void deleteFrom(Connection conn, int id) {
		try {
			String sql = "delete from items where id=?";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, id);
			preparedStatement.executeUpdate();
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static ResultSet sortBy(Connection conn, String param) {
		ResultSet rs = null;
		String sql;
		try {
			sql = "select * from Items order by " + param + " desc";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			rs = preparedStatement.executeQuery();
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}
	
}
