package ca.mcgill.ecse223.kingdomino.view;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

public class KingUI_Stats extends JFrame {
	
	private int WIDTH;
	private int HEIGHT;
	private int textW;
	private int textH;
	private JLabel stats;
	private JLabel statsTwo;

	
	public KingUI_Stats(){
		
		userInterface();
	}
	public void userInterface() {
		WIDTH = 950;
		HEIGHT = 800;
		textW = textH = 75;
		
		// Layout Manager
		Container c =  getContentPane();
		setLayout(new GroupLayout(c));
		
		// Swing components
		this.setSize(WIDTH, HEIGHT);
		this.setTitle("Check your statistics");
		this.getContentPane().setBackground(Color.LIGHT_GRAY);
		
		// back button
		JButton back = new JButton("BACK");
		back.setFont(new Font("Times", Font.BOLD, 15));
		back.setBounds(0, 0, textW*2, textH);
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// return to main page
			}
		});
		// box with users selection
		Border border = BorderFactory.createLineBorder(Color.BLACK, 3);
		stats = new JLabel("Insert Username Below", SwingConstants.CENTER);
		stats.setText("Stats");
		stats.setBorder(border);
		stats.setFont(new Font("Times", Font.BOLD, 20));
		stats.setBounds(WIDTH/2-150, textH/2, 300, 300);
		
		ArrayList users = new ArrayList<String>();
		String name;
		try {
			File newFile = new File("usernames.txt");
			FileReader get = new FileReader(newFile.getAbsolutePath());
			BufferedReader buff = new BufferedReader(get);
			while((name = buff.readLine()) != null) {
				users.add(name);
			} get.close();
		} catch(IOException exception) {
			exception.printStackTrace();
		}
		JComboBox combo = new JComboBox(users.toArray());
		combo.setEditable(true);
		combo.setBounds(WIDTH/2-150, HEIGHT/2-textH-textH/2, 300, 50);
		
		Border borderTwo = BorderFactory.createLineBorder(Color.BLACK, 3);
		statsTwo = new JLabel("User info", SwingConstants.CENTER);
		statsTwo.setText("<html> No. Games Won: <br/><br/><br/>No. Games Lost: "
				+ "<br/><br/><br/>No. Games Played: <br/><br/><br/>No. Games Tied: <br/><br/><br/>Win Ratio: <html>");
		statsTwo.setBorder(borderTwo);
		statsTwo.setFont(new Font("Times", Font.BOLD, 20));
		statsTwo.setBounds(WIDTH/2-200, 300+textH/2+textH/3, 400, 400);
		
	
		// Add
		c.add(back);
		c.add(stats);
		c.add(statsTwo);
		c.add(combo);
	}
	public static void main(String[] args) {
		
		SwingUtilities.invokeLater(new Runnable() {
		
			public void run() {
				JFrame frame = new KingUI_Stats();
				frame.setVisible(true);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			}
		});
	}
}