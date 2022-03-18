package database;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

public class LoginScreen extends JFrame {
	Color frameColor = new Color(188, 225, 251);
	GridBagConstraints c = new GridBagConstraints();
	JLabel warningLabel = new JLabel();
	Connection conn;

	public LoginScreen() {
		super();
		setTitle("Inventory Manager Login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel loginPane = new JPanel(new GridBagLayout());
		JPanel userPane, passPane;
		JLabel welcomeText = new JLabel("<html>Welcome to Inventory Management,<br>please enter your login credentials</html>");
		JLabel userLabel = new JLabel("Username: ");
		JLabel passLabel = new JLabel("Password: ");
		JLabel warningLabel = new JLabel("");
		JTextField userInput = new JTextField("", 30);
		JTextField passInput = new JTextField("", 30);
		JButton loginButton = new JButton(addIcon("/loginButton.png"));
		loginButton.setBorder(null);
		loginButton.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						String connUrl = "jdbc:mysql://localhost:3306/new_schema";
						String username = userInput.getText();
						String password = passInput.getText();
						try {
							conn = DriverManager.getConnection(connUrl, username, password);
							LoginScreen.this.dispose();
							Main.createHomePage(conn);
						}
						catch (Exception exc){
							warningLabel.setText("Invalid Username or Password");
							LoginScreen.this.pack();
							exc.printStackTrace();
						}
					}
				}
		);
		
		userPane = new JPanel();
		passPane = new JPanel();
		
		userPane.add(userLabel);
		userPane.add(userInput);
		userPane.setBackground(frameColor);
		
		passPane.add(passLabel);
		passPane.add(passInput);
		passPane.setBackground(frameColor);
		
		c.weightx = 1;
		c.weighty = 1;
		c.gridx = 1;
		c.gridy = 0;
		loginPane.add(welcomeText, c);
		c.gridy = 1;
		loginPane.add(userPane, c);
		c.gridy = 2;
		loginPane.add(passPane, c);
		c.gridy = 3;
		loginPane.add(warningLabel, c);
		c.gridy = 4;
		loginPane.add(loginButton, c);
		loginPane.setBackground(frameColor);
		
		add(loginPane);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);

	}
	
	public ImageIcon addIcon(String name) {
		ImageIcon icon = new ImageIcon(this.getClass().getResource(name));
		return icon;
	}
	
}
