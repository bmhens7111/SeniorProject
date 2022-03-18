package database;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

public class NewMenu extends JFrame {
	Color frameColor = new Color(188, 225, 251);
	GridBagConstraints c = new GridBagConstraints();

	public NewMenu(Connection conn) {
		super();
		setTitle("Add a New Item");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel newPane = new JPanel(new GridBagLayout());
		JPanel idPane, namePane, quantityPane;
		
		JLabel idLabel = new JLabel("Enter the item's barcode number: ");
		JLabel nameLabel = new JLabel("Enter the item's name: ");
		JLabel quantityLabel = new JLabel("How many of this item are you adding?: ");
		JTextField idField = new JTextField("", 30);
		JTextField nameField = new JTextField("", 30);
		JTextField quantityField = new JTextField("", 30);
		JButton addButton = new JButton("Add Item");
		addButton.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						String id = idField.getText();
						String name = nameField.getText();
						String quantity  = quantityField.getText();
						Sql.insert(conn, id, name, quantity);
						NewMenu.this.dispose();
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
		c.anchor = GridBagConstraints.LAST_LINE_END;
		c.gridy = 3;
		newPane.add(addButton, c);
		
		
		add(newPane);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

}
