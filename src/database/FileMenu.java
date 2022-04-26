package database;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class FileMenu extends JFrame{

	public FileMenu(String fileName) {
		super();
		setTitle("Edit Item Locations/Tags Here");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		JPanel filePane = new JPanel(new GridBagLayout());
		
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		JLabel label = new JLabel("<html>Use this field to alter<br>tag and location names</html>");
		
		String[] content = readFile(fileName);
		JTextArea area = new JTextArea(content.length, 20);
		for (String val: content) {
			area.append(val + "\n");
		}
		
		JTextField field = new JTextField("", 20);
		field.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						area.append(field.getText() + "\n");
						field.setText("");
					}
				}
		);
		
		JScrollPane scroll = new JScrollPane(area);
		JButton saveButton = new JButton("Save Changes");
		saveButton.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						writeFile(area, fileName);
						FileMenu.this.dispose();
					}
				}
		);
		
		panel.add(label);
		panel.add(field);
		panel.add(scroll);
		
		GridBagConstraints c = new GridBagConstraints();
		
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 0;
		filePane.add(panel, c);
		c.anchor = GridBagConstraints.LAST_LINE_END;
		c.gridy = 1;
		filePane.add(saveButton, c);
		
		
		add(filePane);
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
	
	public void writeFile(JTextArea area, String fileName) {
		String path = System.getProperty("user.home");
	    File file = new File(path+"\\documents\\"+ fileName);
	    FileOutputStream outputStream = null;
		try {
			outputStream = new FileOutputStream(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    PrintWriter writer = new PrintWriter(outputStream);
	    for (String line : area.getText().split("\\n")) {
	    	writer.println(line);
	    }
	    writer.close();
	}

}
