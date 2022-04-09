package database;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.List;

import javax.swing.*;

public class NewMenu extends JFrame {
	Color frameColor = new Color(188, 225, 251);
	GridBagConstraints c = new GridBagConstraints();
	String[] tags = {"Beverage", "Bread", "Canned", "Dairy", "Frozen", "Meat", "Produce", "Toiletries", "Other"};

	public NewMenu(Connection conn) {
		super();
		setTitle("Add a New Item");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		JPanel newPane = new JPanel(new GridBagLayout());
		JPanel idPane, namePane, quantityPane;
		
		JLabel idLabel = new JLabel("Enter the item's barcode number: ");
		JLabel nameLabel = new JLabel("Enter the item's name: ");
		JLabel quantityLabel = new JLabel("How many of this item are you adding?: ");
		JTextField idField = new JTextField("", 30);
		JTextField nameField = new JTextField("", 50);
		JTextField quantityField = new JTextField("", 30);
		String[] tags = {"Beverage", "Bread", "Canned", "Dairy", "Frozen",
						"Meat", "Produce", "Toiletries", "Other"};
		DefaultListModel<String> model = new DefaultListModel<String>();
		for (String element: tags) {
			model.addElement(element);
		}
		JList tagList = new JList(model);
		tagList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		JButton addButton = new JButton("Add Item");
		JLabel warningLabel = new JLabel("");
		addButton.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Item item = new Item();
						item.setId(Integer.parseInt(idField.getText()));
						item.setName(nameField.getText());
						item.setQuantity(Integer.parseInt(quantityField.getText()));
						List selected = tagList.getSelectedValuesList();
						for (int i=0; i < selected.size(); i++) {
						    item.setTags(i, (String) selected.get(i));
						}
						if (item.checkValid(item)) {
							Sql.insert(conn, item);
							NewMenu.this.dispose();
						}
						else {
							warningLabel.setText("Error: ID and Quantity cannot be negative");
							pack();
						}
					}
				}
		);
		
		idPane = new JPanel();
		idPane.setBackground(frameColor);
		idPane.add(idLabel);
		idPane.add(idField);
		
		namePane = new JPanel();
		namePane.setBackground(frameColor);
		namePane.add(nameLabel);
		namePane.add(nameField);
		
		quantityPane = new JPanel();
		quantityPane.setBackground(frameColor);
		quantityPane.add(quantityLabel);
		quantityPane.add(quantityField);
		
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 0;
		newPane.add(idPane, c);
		c.gridy = 1;
		newPane.add(namePane, c);
		c.gridy = 2;
		newPane.add(quantityPane, c);
		c.gridy = 3;
		newPane.add(tagList, c);
		c.gridy = 4;
		newPane.add(warningLabel, c);
		c.anchor = GridBagConstraints.LAST_LINE_END;
		c.gridy = 5;
		newPane.add(addButton, c);
		
		
		add(newPane);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

}
