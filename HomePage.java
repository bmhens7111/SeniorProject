package database;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.*;

public class HomePage extends JFrame {
	Color frameColor = new Color(188, 225, 251);
	GridBagConstraints c = new GridBagConstraints();
	JTable table = new JTable();
	String[] columnNames = { "ID", "Name", "Quantity"};
	DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
		@Override
		public boolean isCellEditable(int row, int column) {
			return false;
		}
	};

	public HomePage(Connection conn) {
		super();
		setTitle("Inventory Manager");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel homePane = new JPanel(new GridBagLayout());
		JPanel buttonPane;
		
		JTextField searchBar = new JTextField("Search");
		searchBar.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						table.setModel(searchModel(conn, searchBar.getText()));
					}
				}
		);
		searchBar.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				if (searchBar.isFocusOwner()) {
					searchBar.setText("");
				}
			}
		});
		
		String[] sortByStrings = { "ID", "Name", "Quantity"};
		JComboBox<String> sortByMenu = new JComboBox<String>(sortByStrings);
		sortByMenu.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						String param = (String)sortByMenu.getSelectedItem();
						table.setModel(sortModel(conn, param));
					}
				}
		);
		
		
		JScrollPane scrollPane = new JScrollPane();
		table.setModel(getModel(conn));
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				if (me.getClickCount() == 2) {
					JTable clicked = (JTable)me.getSource();
					int row = clicked.getSelectedRow();
					int column = 0;
					new UpdateMenu(conn, (String) table.getValueAt(row, column));
				}
			}
		});
		scrollPane.setViewportView(table);
		
		JButton newButton = new JButton(addIcon("/newButton.png"));
		newButton.setBorder(null);
		newButton.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Main.createNewMenu(conn);
					}
				}
		);
		
		JButton deleteButton = new JButton(addIcon("/deleteButton.png"));
		deleteButton.setBorder(null);
		deleteButton.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Main.createDeleteMenu(conn);
					}
				}
		);
		
		JButton editButton = new JButton(addIcon("/editButton.png"));
		editButton.setBorder(null);
		editButton.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Main.createEditMenu(conn);
					}
				}
		);
		
		JButton refreshButton = new JButton(addIcon("/refreshButton.png"));
		refreshButton.setBorder(null);
		refreshButton.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						table.setModel(getModel(conn));
					}
				}
		);
		
		buttonPane = new JPanel();
		buttonPane.add(newButton);
		buttonPane.add(deleteButton);
		buttonPane.add(editButton);
		buttonPane.add(refreshButton);
		buttonPane.setBackground(frameColor);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 3;
		c.gridx = 0;
		c.gridy = 0;
		homePane.add(searchBar, c);

		c.weightx = 0.5;
		c.gridx = 4;
		homePane.add(sortByMenu, c);

		c.gridx = 0;
		c.gridy = 1;
		homePane.add(scrollPane, c);

		c.gridy = 2;
		homePane.add(buttonPane, c);
		
		homePane.setBackground(frameColor);
		
		add(homePane);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	protected TableModel sortModel(Connection conn, String param) {
		model.setRowCount(0);
		try {
			ResultSet result = Sql.sortBy(conn, param);
			String id, name, quantity;
			while (result.next()) {
				id = result.getString(1);
				name = result.getString(2);
				quantity = result.getString(3);
				String[] row = {id, name, quantity};
				model.addRow(row);
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		return model;
	}
	
	private TableModel searchModel(Connection conn, String param) {
		model.setRowCount(0);
		String sql = "select * from items where id like ? or name like ? or quantity like ?";
		try {
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setString(1, "%" + param + "%");
			preparedStatement.setString(2, "%" + param + "%");
			preparedStatement.setString(3, "%" + param + "%");
			ResultSet result = preparedStatement.executeQuery();
			String id, name, quantity;
			while (result.next()) {
				id = result.getString(1);
				name = result.getString(2);
				quantity = result.getString(3);
				String[] row = {id, name, quantity};
				model.addRow(row);
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		return model;
	}
	
	private TableModel getModel(Connection conn) {
		model.setRowCount(0);
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
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		return model;
	}
	
	public ImageIcon addIcon(String name) {
		ImageIcon icon = new ImageIcon(this.getClass().getResource(name));
		return icon;
	}

}
