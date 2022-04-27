package database;

import java.awt.*;
import java.awt.event.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.*;

//The Application's Home Page
public class HomePage extends JFrame {
	GridBagConstraints c = new GridBagConstraints();
	static JTable table = new JTable();
	static String[] columnNames = { "ID", "Name", "Quantity", "Tags", "Location", "Date Added"};
	static DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
		@Override
		public boolean isCellEditable(int row, int column) {
			return false;
		}
	};
	static boolean alreadyDone = true;
	static Connection writeConn;
	static LastAction last;

	public HomePage(Connection conn) {
		super();
		setConnection(conn);
		setTitle("Inventory Manager");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel homePane = new JPanel(new GridBagLayout());
		JPanel buttonPane, buttonPane2;
		
		JTextField searchBar = new JTextField("Search");
		searchBar.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if (searchBar.getText() == "") {
							table.setModel(getModel(conn));
						}
						else {
							table.setModel(searchModel(conn, searchBar.getText()));
						}
					}
				}
		);
		
		String[] orderStrings = {"ASC", "DESC"};
		JComboBox<String> orderByMenu = new JComboBox<String>(orderStrings);
		
		String[] sortByStrings = { "ID", "Name", "Quantity", "DateAdded"};
		JComboBox<String> sortByMenu = new JComboBox<String>(sortByStrings);
		sortByMenu.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						String param = (String)sortByMenu.getSelectedItem();
						String order = (String)orderByMenu.getSelectedItem();
						table.setModel(sortModel(conn, param, order));
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
					new UpdateMenu(conn, Integer.parseInt((String) table.getValueAt(row, column)));
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
		
		JButton undoButton = new JButton(addIcon("/undoButton.png"));
		undoButton.setBorder(null);
		undoButton.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						undoLast(last);
					}
				}
		);
		
		JButton exportButton = new JButton(addIcon("/exportButton.png"));
		exportButton.setBorder(null);
		exportButton.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Main.createExportMenu();
					}
				}
		);
		
		JButton tagsButton = new JButton(addIcon("/tagButton.png"));
		tagsButton.setBorder(null);
		tagsButton.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Main.createFileMenu("tags.txt");
					}
				}
		);
		
		JButton locationsButton = new JButton(addIcon("/locButton.png"));
		locationsButton.setBorder(null);
		locationsButton.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Main.createFileMenu("locations.txt");
					}
				}
		);
		
		buttonPane = new JPanel();
		buttonPane.add(newButton);
		buttonPane.add(deleteButton);
		buttonPane.add(editButton);
		
		buttonPane2 = new JPanel();
		buttonPane2.add(locationsButton);
		buttonPane2.add(tagsButton);
		buttonPane2.add(undoButton);
		buttonPane2.add(exportButton);
		
		c.fill = GridBagConstraints.BOTH;
		c.gridwidth = 2;
		c.gridx = 0;
		c.gridy = 0;
		homePane.add(searchBar, c);
		
		c.gridwidth = 1;
		c.weightx = 0.5;
		c.gridx = 3;
		homePane.add(sortByMenu, c);
		
		c.gridx = 4;
		homePane.add(orderByMenu, c);

		c.gridwidth = 5;
		c.gridx = 0;
		c.gridy = 1;
		homePane.add(scrollPane, c);
		
		c.gridwidth = 1;
		c.gridy = 2;
		homePane.add(buttonPane, c);
		
		c.gridy = 3;
		homePane.add(buttonPane2, c);
		
		add(homePane);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
		checkLowStock();
	}
	
	public void checkLowStock() {
		ResultSet result = null;
		try {
			String sql = "select Name from items where quantity < 1";
			PreparedStatement preparedStatement = getConnection().prepareStatement(sql);
			result = preparedStatement.executeQuery();
			System.out.println(preparedStatement);
			System.out.println("RESULT: retrieving items with Low Quantity");
			String lowAlertContent = "<html> The following items are out of stock: <br>";
			while (result.next()) {
				lowAlertContent = lowAlertContent.concat(result.getString(1) + "<br>");
				System.out.println(lowAlertContent);
			}
			lowAlertContent = lowAlertContent.concat("</html>");
			new Popup("Out of Stock Warning", lowAlertContent);
		} 
		catch (SQLException e) {
			e.printStackTrace();
			new Popup("Something went Wrong", "A mySQL error has occured, please try again");
		}
	}
	
	//Returns a version of the table that is sorted by a given parameter in asc/desc order (direction)
	private TableModel sortModel(Connection conn, String param, String direction) {
		model.setRowCount(0);
		try {
			ResultSet result = Sql.sortBy(conn, param, direction);
			String id, name, quantity, tags, location, dateAdded;
			while (result.next()) {
				id = result.getString(1);
				name = result.getString(2);
				quantity = result.getString(3);
				tags = result.getString(4);
				location = result.getString(5);
				dateAdded = result.getString(6);
				String[] row = {id, name, quantity, tags, location, dateAdded};
				model.addRow(row);
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		return model;
	}
	
	//Returns a version of the table that displays items with certain parameters
	private TableModel searchModel(Connection conn, String param) {
		model.setRowCount(0);
		String sql = "select * from items where id like ?"
				+ " or name like ?"
				+ " or quantity like ?"
				+ " or tags like ?"
				+ " or location like ?"
				+ " or DateAdded like ?";
		try {
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setString(1, "%" + param + "%");
			preparedStatement.setString(2, "%" + param + "%");
			preparedStatement.setString(3, "%" + param + "%");
			preparedStatement.setString(4, "%" + param + "%");
			preparedStatement.setString(5, "%" + param + "%");
			preparedStatement.setString(6, "%" + param + "%");
			ResultSet result = preparedStatement.executeQuery();
			String id, name, quantity, tags, location, dateAdded;
			while (result.next()) {
				id = result.getString(1);
				name = result.getString(2);
				quantity = result.getString(3);
				tags = result.getString(4);
				location = result.getString(5);
				dateAdded = result.getString(6);
				String[] row = {id, name, quantity, tags, location, dateAdded};
				model.addRow(row);
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		return model;
	}
	
	//Returns the data used in the JTable
	static TableModel getModel(Connection conn) {
		model.setRowCount(0);
		String sql = "select * from items";
		try {
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			ResultSet result = preparedStatement.executeQuery();
			String id, name, quantity, tags, location, dateAdded;
			while (result.next()) {
				id = result.getString(1);
				name = result.getString(2);
				quantity = result.getString(3);
				tags = result.getString(4);
				location = result.getString(5);
				dateAdded = result.getString(6);
				String[] row = {id, name, quantity, tags, location, dateAdded};
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
	
	//Prevents Undo from re-adding deleted items multiple items
	public void undoLast(LastAction last) {
		if (!alreadyDone) {
			if (last.getType() == LastAction.Type.DELETE) {
				Sql.insert(writeConn, last.getItem());
			}
			else { //Last Action was update action
				Sql.update(writeConn, last.getItem(), last.getItem().getId());
				HomePage.table.setModel(HomePage.getModel(getConnection()));
			}
		}
		else {
			System.out.println("HOMEPAGE UNDO: Nothing to Undo");
		}
		alreadyDone = true;
	}
	
	//Another connection object for the file writer
	public void setConnection(Connection conn) {
		writeConn = conn;
	}
	
	public static Connection getConnection() {
		return writeConn;
	}
	
	//Retrieves data from tableModel, writes to .csv file with given name and location
	public static void exportToCSVFile(String fileName, String location) throws IOException {
		String path = System.getProperty("user.home");
	    FileWriter fileWriter = new FileWriter(path+"\\" + location +"\\"+ fileName + ".csv");
	    PrintWriter printWriter = new PrintWriter(fileWriter);
	    TableModel writeModel = getModel(getConnection());
	    String text = "";
	    for (String value: columnNames) {
	    	printWriter.print(value + ",");
	    }
	    printWriter.println();
	    
	    for (int row=0; row<writeModel.getRowCount(); row++) {
	    	for (int col=0; col<writeModel.getColumnCount(); col++) {
	    		text = (String) writeModel.getValueAt(row, col);
	    		if (col == writeModel.getColumnCount()-1) {
	    			printWriter.print(text);
	    		}
	    		else if (col == writeModel.getColumnCount()-3) {
	    			printWriter.print("\"" + text + "\"" + ",");
	    		}
	    		else {
	    			printWriter.print(text + ",");
	    		}
	    	}
	    	printWriter.println();
	    }
	    printWriter.close();
	    System.out.println("File " + fileName + " created");
	}

	//Delete and Update Menus create actions that can be undone
	public static void createLastAction(Item item, LastAction.Type type) {
		// TODO Auto-generated method stub
		last = new LastAction(item, type);
	}

}
