package database;

import java.awt.Font;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Popup extends JFrame {
	private final int FONTSIZE = 24;

	public Popup(String title, String content) {
		setTitle(title);
		JLabel label = new JLabel(content);
		Font font = new Font("TimesRoman", Font.BOLD, FONTSIZE);
		label.setFont(font);
		add(label);
		pack();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}

}
