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
						try {
							ResultSet rs = Sql.selectWhereId(conn, id);
							rs.next();
							Item item = new Item();
							item.setId(rs.getInt(1));
							item.setName(rs.getString(2));
							item.setQuantity(rs.getInt(3));
							String tag = "";
							String tagResult = rs.getString(4);
							int tagIndex = 0;
							for (int i=0; i<tagResult.length(); i++) {
								if (tagResult.charAt(i) == ' ' || i==tagResult.length()-1) {
									//Do Nothing
								}
								else if (tagResult.charAt(i) == ',') {
									item.setTags(tagIndex, tag);
									tagIndex++;
									tag = "";
								}
								else {
									tag = tag + (tagResult.charAt(i));
								}
							}
							HomePage.createLastAction(item, LastAction.Type.DELETE);
							Sql.deleteFrom(conn, id);
							DeleteMenu.this.dispose();
						}
						catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
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