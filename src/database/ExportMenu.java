package database;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

//Menu for exporting files in a .csv format
public class ExportMenu extends JFrame {

	public ExportMenu() {
		super();
		setTitle("Export the Database");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		JPanel exportPane = new JPanel(new GridBagLayout());
		JPanel filePane;
		
		JLabel fileLabel = new JLabel("Save File: ");
		JTextField fileField = new JTextField("", 50);
		JLabel locLabel = new JLabel(".csv to ");
		String[] sortByStrings = { "Desktop", "Downloads", "Documents"};
		JComboBox<String> saveLocation = new JComboBox<String>(sortByStrings);
		JButton exportButton = new JButton("Export to File");
		exportButton.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						String fileName = fileField.getText();
						String location  = (String) saveLocation.getSelectedItem();
						try {
							HomePage.exportToCSVFile(fileName, location);
						}
						catch (IOException e1) {
							e1.printStackTrace();
							new Popup("Something went Wrong", "A mySQL error has occured, please try again");
						}
						ExportMenu.this.dispose();
					}
				}
		);
		
		filePane = new JPanel();
		filePane.add(fileLabel);
		filePane.add(fileField);
		filePane.add(locLabel);
		filePane.add(saveLocation);
		
		GridBagConstraints c = new GridBagConstraints();
		
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 0;
		exportPane.add(filePane, c);
		c.anchor = GridBagConstraints.LAST_LINE_END;
		c.gridy = 1;
		exportPane.add(exportButton, c);
		
		
		add(exportPane);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
}
