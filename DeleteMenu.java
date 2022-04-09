package database;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

public class DeleteMenu extends JFrame {
	Color frameColor = new Color(188, 225, 251);
	GridBagConstraints c = new GridBagConstraints();

	public DeleteMenu(Connection conn) {
		super();
		setTitle("Enter an Item to Delete");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		JPanel deletePane = new JPanel(new GridBagLayout());
		JPanel idPane;
		
		JLabel idLabel = new JLabel("Enter the item's barcode number: ");
		JTextField idField = new JTextField("", 30);
		JButton deleteButton = new JButton("Delete Item");
		deleteButton.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						int id = Integer.parseInt(idField.getText());
						Sql.deleteFrom(conn, id);
						DeleteMenu.this.dispose();
					}
				}
		);
		
		idPane = new JPanel();
		idPane.setBackground(frameColor);
		idPane.add(idLabel);
		idPane.add(idField);
		
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 0;
		deletePane.add(idPane, c);
		c.anchor = GridBagConstraints.LAST_LINE_END;
		c.gridy = 1;
		deletePane.add(deleteButton, c);
		
		
		add(deletePane);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

}