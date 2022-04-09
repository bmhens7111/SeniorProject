package database;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.List;

import javax.swing.*;

public class UpdateMenu extends JFrame {
	Color frameColor = new Color(188, 225, 251);
	GridBagConstraints c = new GridBagConstraints();

	public UpdateMenu(Connection conn, int id) {
		super();
		setTitle("Update Item Properties");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		JPanel updatePane = new JPanel(new GridBagLayout());
		JPanel idPane, namePane, quantityPane;
		JLabel idLabel = new JLabel("Update ID Number: ");
		JLabel nameLabel = new JLabel("Update Name: ");
		JLabel quantityLabel = new JLabel("Update Quantity: ");
		final int oldID = id;
		int newID = 0;
		String name = "";
		int quantity = 0;
		try {
			ResultSet result = Sql.selectWhereId(conn, id);
			result.next();
			id = result.getInt(1);
			name = result.getString(2);
			quantity = result.getInt(3);
		}
		catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		JTextField idField = new JTextField(String.valueOf(oldID), 50);	
		JTextField nameField = new JTextField(name, 50);
		JTextField quantityField = new JTextField(String.valueOf(quantity), 50);
		
		String[] tags = {"Beverage", "Bread", "Canned", "Dairy", "Frozen",
				"Meat", "Produce", "Toiletries", "Other"};
		DefaultListModel<String> model = new DefaultListModel<String>();
		for (String element: tags) {
			model.addElement(element);
		}
		JList tagList = new JList(model);
		tagList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		JButton updateButton = new JButton("Save Changes");
		updateButton.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						int newId = Integer.parseInt(idField.getText());
						String name = nameField.getText();
						int quantity  = Integer.parseInt(quantityField.getText());
						Item item = new Item();
						item.setId(oldID);
						item.setName(name);
						item.setQuantity(quantity);
						List selected = tagList.getSelectedValuesList();
						for (int i=0; i < selected.size(); i++) {
						    item.setTags(i, (String) selected.get(i));
						}
						if (item.checkValid(item)) {
							Sql.update(conn, item, newId);
							UpdateMenu.this.dispose();
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
		updatePane.add(idPane, c);
		c.gridy = 1;
		updatePane.add(namePane, c);
		c.gridy = 2;
		updatePane.add(quantityPane, c);
		c.gridy = 3;
		updatePane.add(tagList, c);
		c.anchor = GridBagConstraints.LAST_LINE_END;
		c.gridy = 4;
		updatePane.add(updateButton, c);
		
		
		add(updatePane);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

}
