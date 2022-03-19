package database;

import java.sql.*;

public class Sql {

	public static void insert(Connection conn, String id, String name, String quantity) {
		try {
			String sql = "insert into items values (?, ?, ?)";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setString(1, id);
			preparedStatement.setString(2, name);
			preparedStatement.setString(3, quantity);
			System.out.print(preparedStatement);
			preparedStatement.executeUpdate();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static ResultSet selectWhereId(Connection conn, String id) {
		ResultSet result = null;
		try {
			String sql = "select * from items where id=?";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setString(1, id);
			result = preparedStatement.executeQuery();
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	public static void update(Connection conn, String id, String newId, String name, String quantity) {
		try {
			String sql = "Update items "
						+ "set id=?, name=?, quantity=? "
						+ "where id=?";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setString(1, newId);
			preparedStatement.setString(2, name);
			preparedStatement.setString(3, quantity);
			preparedStatement.setString(4, id);
			System.out.print(preparedStatement);
			preparedStatement.executeUpdate();
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
