package database;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

public class HomePage extends JFrame {
	Color frameColor = new Color(188, 225, 251);
	GridBagConstraints c = new GridBagConstraints();

	public HomePage(Connection conn) {
		super();
		setTitle("Inventory Manager");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel homePane = new JPanel(new GridBagLayout());
		JPanel buttonPane;
		JTextField searchBar = new JTextField("Search");
		String[] sortByStrings = { "ID", "Name", "Quantity", "Date Added"};
		JComboBox sortByMenu = new JComboBox(sortByStrings);
		JScrollPane scrollPane = new JScrollPane();
		JTable table = Main.getTable(conn);
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
						HomePage.this.dispose();
						HomePage updatedHomePage = new HomePage(conn);
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
	
	public ImageIcon addIcon(String name) {
		ImageIcon icon = new ImageIcon(this.getClass().getResource(name));
		return icon;
	}

}
