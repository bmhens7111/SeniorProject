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
		JPanel hostPane, userPane, passPane;
		JLabel welcomeText = new JLabel("<html>Welcome to Inventory Management,<br>please enter your login credentials</html>");
		JLabel userLabel = new JLabel("Username: ");
		JLabel passLabel = new JLabel("Password: ");
		JTextField userInput = new JTextField("", 30);
		JTextField passInput = new JTextField("", 30);
		JLabel hostLabel = new JLabel("Hostname: ");
		JTextField hostInput = new JTextField("", 30);
		JButton loginButton = new JButton(addIcon("/loginButton.png"));
		loginButton.setBorder(null);
		loginButton.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						String connUrl = "jdbc:mysql://" + hostInput.getText() + "/";
						String username = userInput.getText();
						String password = passInput.getText();
						try {
							//Database connection is attempted with username and password entered
							Connection conn = DriverManager.getConnection(connUrl, username, password);
							System.out.println("LOGIN: Successful login as User: " + username);
							Statement stmt = conn.createStatement();	      
							String sql = "CREATE DATABASE IF NOT EXISTS Manager";
							stmt.executeUpdate(sql);
							sql = "USE Manager";
							stmt.executeUpdate(sql);
							sql = "CREATE TABLE IF NOT EXISTS Items(ID int NOT NULL,"
									+ " Name varchar(30), "
									+ "Quantity int, "
									+ "Tags varchar(255), "
									+ "Location varchar(30), "
									+ "DateAdded varchar(10), "
									+ "primary key(ID))";
							stmt.executeUpdate(sql);
							LoginScreen.this.dispose();
							Main.createHomePage(conn);
						}
						catch (Exception exc){
							exc.printStackTrace();
							System.out.println("LOGIN: Login attempt failed");
							new Popup("Warning", "Invalid Username or Password");
						}
					}
				}
		);
		
		hostPane = new JPanel();
		userPane = new JPanel();
		passPane = new JPanel();
		
		hostPane.add(hostLabel);
		hostPane.add(hostInput);
		
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
		loginPane.add(hostPane, c);
		c.gridy = 2;
		loginPane.add(userPane, c);
		c.gridy = 3;
		loginPane.add(passPane, c);
		c.gridy = 4;
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
