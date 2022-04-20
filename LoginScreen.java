package database;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

public class LoginScreen extends JFrame {
	GridBagConstraints c = new GridBagConstraints();

	//Creates the application's login screen
	public LoginScreen() {
		super();
		setTitle("Inventory Manager Login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel loginPane = new JPanel(new GridBagLayout());
		JPanel userPane, passPane;
		JLabel welcomeText = new JLabel("<html>Welcome to Inventory Management,<br>please enter your login credentials</html>");
		JLabel userLabel = new JLabel("Username: ");
		JLabel passLabel = new JLabel("Password: ");
		JTextField userInput = new JTextField("", 30);
		JTextField passInput = new JTextField("", 30);
		JButton loginButton = new JButton("login");
		loginButton.setBorder(null);
		loginButton.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						String connUrl = "jdbc:mysql://localhost:3306/new_schema";
						String username = userInput.getText();
						String password = passInput.getText();
						try {
							//Database connection is attempted with username and password entered
							Connection conn = DriverManager.getConnection(connUrl, username, password);
							System.out.println("LOGIN: Successful login as User: " + username);
							LoginScreen.this.dispose();
							Main.createHomePage(conn);
						}
						catch (Exception exc){
							System.out.println("LOGIN: Login attempt failed");
							new Popup("Warning", "Invalid Username or Password");
						}
					}
				}
		);
		
		userPane = new JPanel();
		passPane = new JPanel();
		
		userPane.add(userLabel);
		userPane.add(userInput);
		
		passPane.add(passLabel);
		passPane.add(passInput);
		
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
		loginPane.add(loginButton, c);
		
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
