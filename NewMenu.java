package database;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.List;

import javax.swing.*;

//The Menu for addition of new items
public class NewMenu extends JFrame {

	public NewMenu(Connection conn) {
		super();
		setTitle("Add a New Item");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		JPanel newPane = new JPanel(new GridBagLayout());
		JPanel idPane, namePane, quantityPane, locationPane;
		
		JLabel idLabel = new JLabel("Enter the item's barcode number: ");
		JLabel nameLabel = new JLabel("Enter the item's name: ");
		JLabel quantityLabel = new JLabel("How many of this item are you adding?: ");
		JLabel locLabel = new JLabel("Where is this item located: ");
		JTextField idField = new JTextField("", 30);
		JTextField nameField = new JTextField("", 50);
		JTextField quantityField = new JTextField("", 30);
		JTextField locField = new JTextField("", 30);
		
		DefaultListModel<String> model = new DefaultListModel<String>();
		String[] tags = {"Beverage", "Bread", "Canned", "Dairy", "Frozen", "Meat", "Produce", "Toiletries", "Other"};
		for (String element: tags) {
			model.addElement(element);
		}
		JList<String> tagList = new JList<String>(model);
		tagList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		JButton addButton = new JButton("Add Item");
		addButton.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Item item = new Item(Integer.parseInt(idField.getText()), nameField.getText(), Integer.parseInt(quantityField.getText()), locField.getText());
						List selected = tagList.getSelectedValuesList();
						for (int i=0; i < selected.size(); i++) {
						    item.setTags(i, (String) selected.get(i));
						}
						if (item.checkValid(item)) {
							//If item does not exist, add it to table
							ResultSet rs = Sql.selectWhereId(conn, Integer.parseInt(idField.getText()));
							try {
								if (!rs.next()) {
									Sql.insert(conn, item);
									NewMenu.this.dispose();
									HomePage.table.setModel(HomePage.getModel(conn));
								}
								//If ID already in use by another item, ask to add this quantity to it
								else {
									JFrame frame = new JFrame("Error");
									JPanel panel = new JPanel();
									JLabel label  = new JLabel("Item ID already exists, add quantity to existing item?");
									JButton yesButton = new JButton("Yes");
									yesButton.addActionListener(
											new ActionListener() {
												public void actionPerformed(ActionEvent e) {
													Item newItem = new Item(1, "", 1, "");
													try {
														ResultSet rs = Sql.selectWhereId(conn, item.getId());
														rs.next();
														newItem.setId(rs.getInt(1));
														newItem.setName(rs.getString(2));
														newItem.setQuantity(rs.getInt(3));
														String tag = "";
														String tagResult = rs.getString(4);
														int tagIndex = 0;
														for (int i=0; i<tagResult.length(); i++) {
															if (tagResult.charAt(i) == ' ' || i==tagResult.length()-1) {
																//Do Nothing
															}
															else if (tagResult.charAt(i) == ',') {
																newItem.setTags(tagIndex, tag);
																tagIndex++;
																tag = "";
															}
															else {
																tag = tag + (tagResult.charAt(i));
															}
														}
													}
													catch (SQLException e1) {
														// TODO Auto-generated catch block
														e1.printStackTrace();
													}
													int newQuant = item.getQuantity() + newItem.getQuantity();
													newItem.setQuantity(newQuant);
													Sql.update(conn, newItem, newItem.getId());
													frame.dispose();
													NewMenu.this.dispose();
												}
											}
									);
									JButton noButton = new JButton("No");
									noButton.addActionListener(
											new ActionListener() {
												public void actionPerformed(ActionEvent e) {
													frame.dispose();
												}
											}
									);
									panel.add(label);
									panel.add(yesButton);
									panel.add(noButton);
									frame.add(panel);
									frame.pack();
									frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
									frame.setLocationRelativeTo(null);
									frame.setVisible(true);
								}
							} catch (HeadlessException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
						//Invalid Item parameters
						else {
							new Popup("Warning", "Error: ID and Quantity cannot be negative");
						}
					}
				}
		);
		
		idPane = new JPanel();
		idPane.add(idLabel);
		idPane.add(idField);
		
		namePane = new JPanel();
		namePane.add(nameLabel);
		namePane.add(nameField);
		
		quantityPane = new JPanel();
		quantityPane.add(quantityLabel);
		quantityPane.add(quantityField);
		
		locationPane = new JPanel();
		locationPane.add(locLabel);
		locationPane.add(locField);
		
		GridBagConstraints c = new GridBagConstraints();
		
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 0;
		newPane.add(idPane, c);
		c.gridy = 1;
		newPane.add(namePane, c);
		c.gridy = 2;
		newPane.add(quantityPane, c);
		c.gridy = 3;
		newPane.add(locationPane, c);
		c.gridy = 4;
		newPane.add(tagList, c);
		c.gridy = 5;
		c.anchor = GridBagConstraints.LAST_LINE_END;
		newPane.add(addButton, c);
		
		
		add(newPane);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

}
