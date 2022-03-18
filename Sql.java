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
	
}
