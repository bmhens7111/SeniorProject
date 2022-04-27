package database;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

//Menu for updating an item's properties
public class UpdateMenu extends JFrame {

	public UpdateMenu(Connection conn, int id) {
		super();
		setTitle("Update Item Properties");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		JPanel updatePane = new JPanel(new GridBagLayout());
		JPanel idPane, namePane, quantityPane, locPane;
		JLabel idLabel = new JLabel("Update ID Number: ");
		JLabel nameLabel = new JLabel("Update Name: ");
		JLabel quantityLabel = new JLabel("Update Quantity: ");
		JLabel locLabel = new JLabel("Update Location: ");
		//Text Fields are populated with information of old item
		final int oldID = id;
		Item oldItem = new Item(1, "", 1, "");
		String name = "";
		int quantity = 0;
		String location = "";
		try {
			ResultSet result = Sql.selectWhereId(conn, id);
			result.next();
			id = result.getInt(1);
			name = result.getString(2);
			quantity = result.getInt(3);
			oldItem.setId(oldID);
			oldItem.setName(name);
			oldItem.setQuantity(quantity);
			String tag = "";
			String tagResult = result.getString(4);
			int tagIndex = 0;
			for (int i=0; i<tagResult.length(); i++) {
				if (tagResult.charAt(i) == ' ' || i==tagResult.length()-1) {
					//Do Nothing
				}
				else if (tagResult.charAt(i) == ',') {
					oldItem.setTags(tagIndex, tag);
					tagIndex++;
					tag = "";
				}
				else {
					tag = tag + (tagResult.charAt(i));
				}
			}
			location  = result.getString(5);
			oldItem.setLocation(location);
		}
		catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		JTextField idField = new JTextField(String.valueOf(oldID), 50);	
		JTextField nameField = new JTextField(name, 50);
		JTextField quantityField = new JTextField(String.valueOf(quantity), 50);
		
		String[] locations = readFile("locations.txt");
		JComboBox<String> locationDropDown = new JComboBox<String>(locations);
		
		for (int i=0; i<locations.length; i++) {
			if (locations[i].equals(location)) {
				locationDropDown.setSelectedIndex(i);
			}
		}
		
		DefaultListModel<String> model = new DefaultListModel<String>();
		String[] tags = readFile("tags.txt");
		for (String element: tags) {
			model.addElement(element);
		}
		
		JList<String> tagList = new JList<String>(model);
		tagList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		
		JButton updateButton = new JButton("Save Changes");
		updateButton.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						HomePage.createLastAction(oldItem, LastAction.Type.UPDATE);
						HomePage.alreadyDone = false;
						int newId = Integer.parseInt(idField.getText());
						String name = nameField.getText();
						int quantity  = Integer.parseInt(quantityField.getText());
						String location = (String)locationDropDown.getSelectedItem();
						Item item = new Item(oldID, name, quantity, location);
						List<String> selected = tagList.getSelectedValuesList();
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
		idPane.add(idLabel);
		idPane.add(idField);
		
		namePane = new JPanel();
		namePane.add(nameLabel);
		namePane.add(nameField);
		
		quantityPane = new JPanel();
		quantityPane.add(quantityLabel);
		quantityPane.add(quantityField);
		
		locPane = new JPanel();
		locPane.add(locLabel);
		locPane.add(locationDropDown);
		
		GridBagConstraints c = new GridBagConstraints();
		
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 0;
		updatePane.add(idPane, c);
		c.gridy = 1;
		updatePane.add(namePane, c);
		c.gridy = 2;
		updatePane.add(quantityPane, c);
		c.gridy = 3;
		updatePane.add(locPane, c);
		c.gridy = 4;
		updatePane.add(tagList, c);
		c.anchor = GridBagConstraints.LAST_LINE_END;
		c.gridy = 5;
		updatePane.add(updateButton, c);
		
		
		add(updatePane);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	private String[] readFile(String fileName) {
		List<String> contents = new ArrayList<String>();
		String path = System.getProperty("user.home");
		File file = new File(path+"\\documents\\"+ fileName);
		if (file.exists()) {
			BufferedReader br = null;
			try {
				br = new BufferedReader(new FileReader(file));
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		    String line;
		    try {
				while ((line = br.readLine()) != null) {
					contents.add(line);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return contents.toArray(new String[0]);
	}

}
