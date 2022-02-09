import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class databaser extends Frame implements ActionListener {
	
	public static void main(String[] args) {
		String connectUrl = "jdbc:mysql://localhost:3306/";
		try {
			Class.forName("com.mysql.jdbc,Driver");
			Connection conn = DriverManager.getConnection(connectUrl, "root", "Baked1alaska");
			Statement st = conn.createStatement();
			st.execute("create table item (item_id integer, name varchar(20), quantity integer)");
			st.execute("insert into item values(001, 'Bread', 20)");
		}
		catch (Exception e) {
			System.err.println("Error");
		}
		createFrame();
		
	}

	private static void createFrame() {
		JFrame frame = new JFrame("Inventory Manager");
        JButton view = new JButton("View");
        JButton add = new JButton("Add");
        frame.setLayout(new FlowLayout());
        frame.setSize(1300,650);
        frame.add(view);
        frame.add(add);
        view.setSize(50,50);
        add.setSize(50,50);
        view.setVisible(true);
        add.setVisible(true);
        frame.setVisible(true);
        
        view.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		
        	}
        });
        
        add.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		
        	}
        });
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
