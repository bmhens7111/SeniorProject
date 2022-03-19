package database;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

public class UpdateMenu extends JFrame {
	Color frameColor = new Color(188, 225, 251);
	GridBagConstraints c = new GridBagConstraints();

	public UpdateMenu(Connection conn, String id) {
		super();
		setTitle("Update Item Properties");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		JPanel updatePane = new JPanel(new GridBagLayout());
		JPanel idPane, namePane, quantityPane;
		final String ID = new String(id);
		String newId = "";
		String name = "";
		String quantity = "";
		
		JLabel idLabel = new JLabel("Update ID Number: ");
		JLabel nameLabel = new JLabel("Update Name: ");
		JLabel quantityLabel = new JLabel("Update Quantity: ");
		ResultSet result = Sql.selectWhereId(conn, id);
		System.out.println(result);
		try {
			while (result.next()) {
				id = result.getString(1);
				name = result.getString(2);
				quantity = result.getString(3);
			}
		}
		catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		JTextField idField = new JTextField(id, 30);
		JTextField nameField = new JTextField(name, 50);
		JTextField quantityField = new JTextField(quantity, 30);
		JButton updateButton = new JButton("Save Changes");
		updateButton.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						String newId = idField.getText();
						String name = nameField.getText();
						String quantity  = quantityField.getText();
						Sql.update(conn, ID, newId, name, quantity);
						UpdateMenu.this.dispose();
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
		c.anchor = GridBagConstraints.LAST_LINE_END;
		c.gridy = 3;
		updatePane.add(updateButton, c);
		
		
		add(updatePane);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

}
