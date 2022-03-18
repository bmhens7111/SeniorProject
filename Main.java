package database;

import java.sql.*;
import javax.swing.*;
import javax.swing.table.*;

public class Main {

	public static void main(String[] args) {
		LoginScreen login = new LoginScreen();
	}

	public static void createHomePage(Connection conn) {
		HomePage home = new HomePage(conn);
		
	}

	public static JTable getTable(Connection conn) {
		JTable table = new JTable();
		String[] columnNames = { "ID", "Name", "Quantity"};
		DefaultTableModel model = new DefaultTableModel(columnNames, 0);
		String sql = "select * from items";
		try {
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			ResultSet result = preparedStatement.executeQuery();
			String id, name, quantity;
			while (result.next()) {
				id = result.getString(1);
				name = result.getString(2);
				quantity = result.getString(3);
				String[] row = {id, name, quantity};
				model.addRow(row);
			}
			table.setModel(model);
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return table;
	}
	
	public static void createNewMenu(Connection conn) {
		// TODO Auto-generated method stub
		NewMenu newMenu = new NewMenu(conn);
	}
	
	public static void createDeleteMenu(Connection conn) {
		// TODO Auto-generated method stub
		DeleteMenu delete = new DeleteMenu(conn);
	}

	public static void createEditMenu(Connection conn) {
		// TODO Auto-generated method stub
		EditMenu edit = new EditMenu(conn);
	}

}
