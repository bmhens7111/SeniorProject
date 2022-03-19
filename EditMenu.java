package database;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

public class EditMenu extends JFrame {
	Color frameColor = new Color(188, 225, 251);
	GridBagConstraints c = new GridBagConstraints();

	public EditMenu(Connection conn) {
		super();
		setTitle("Enter an Item to Edit");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel editPane = new JPanel(new GridBagLayout());
		JPanel idPane;
		
		JLabel idLabel = new JLabel("Enter the item's barcode number: ");
		JTextField idField = new JTextField("", 30);
		JButton editButton = new JButton("Edit Item");
		editButton.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						String id = idField.getText();
						UpdateMenu upMenu = new UpdateMenu(conn, id);
						EditMenu.this.dispose();
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
		editPane.add(idPane, c);
		c.anchor = GridBagConstraints.LAST_LINE_END;
		c.gridy = 1;
		editPane.add(editButton, c);
		
		
		add(editPane);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

}
