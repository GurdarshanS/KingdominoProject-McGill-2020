package ca.mcgill.ecse223.kingdomino.view;

import java.awt.CardLayout;
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

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.KDController;
import ca.mcgill.ecse223.kingdomino.model.User;

public class KingUI_Stats extends JFrame {
	static int wins = 0, played=0, loss=0;
	 static double ratio= 0.0;
	
	 public KingUI_Stats(){
		
		initComponents();
	}
	public static void initComponents() {
		 JLabel stats;
		 JLabel statsTwo;
		 Border border;
		 JButton back, search;
		 JComboBox combo;
		 Border borderTwo;
	
		// back button
		back = new JButton("Back");
		back.setFont(new Font("Times", Font.BOLD, 16));
		//back.setBounds(0, 0, textW*2, textH);
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// return to main page
			}
		});
		// box that says stats
		border = BorderFactory.createLineBorder(Color.BLACK, 3);
		stats = new JLabel("Insert Username Below", SwingConstants.CENTER);
		stats.setText("Stats");
		stats.setBorder(border);
		stats.setFont(new Font("Times", Font.BOLD, 45));
		//stats.setBounds(WIDTH/2-150, textH/2, 300, 300);
		
		// User names
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
		combo = new JComboBox(users.toArray());
		combo.setEditable(true);
		//combo.setBounds(WIDTH/2-150, HEIGHT/2-textH-textH/2, 300, 50);
		
		// search button
		search = new JButton("Search Stats");
		search.setFont(new Font("Times", Font.BOLD, 16));
		//search.setBounds(WIDTH/2+WIDTH/4, HEIGHT/2-textH, textW*2, textH);
		search.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String input = combo.getSelectedItem().toString();
				User user = User.getWithName(input);
				wins = user.getWonGames();
				played = user.getPlayedGames();
				loss = played - wins;
				ratio = wins/played;
			}
		});
		
		borderTwo = BorderFactory.createLineBorder(Color.BLACK, 3);
		statsTwo = new JLabel("User info", SwingConstants.CENTER);
		statsTwo.setText("<html> No. Games Won: "+wins
						+ "<br/><br/><br/>No. Games Lost: "+loss
						+ "<br/><br/><br/>No. Games Played: "+played
						+ "<br/><br/><br/>No. Games Tied: "
						+ "<br/><br/><br/>Win Ratio: "+ratio +"<html>");
		statsTwo.setBorder(borderTwo);
		statsTwo.setFont(new Font("Times", Font.BOLD, 25));
		//statsTwo.setBounds(WIDTH/2-200, 300+textH/2+textH/3, 400, 400);
	
		// Add
		//c.add(back);
		//c.add(stats);
		//c.add(statsTwo);
		//c.add(combo);
		//c.add(search);
		JPanel p3 = new JPanel();
		GroupLayout layout = new GroupLayout(p3);
		p3.setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		KingUI_Main.contPanel.add(p3, "4");
		
		layout.setHorizontalGroup(
				layout.createSequentialGroup()
				.addComponent(back)
				.addGroup(layout.createSequentialGroup())
				.addGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
						.addComponent(stats)
						.addComponent(combo)
						.addGroup(layout.createSequentialGroup()
								.addComponent(statsTwo)
						)
						.addComponent(search)
						)
				)
		);
		
		layout.setVerticalGroup(
				layout.createParallelGroup()
				.addComponent(back)
				.addGroup(layout.createParallelGroup())
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
				.addGroup(layout.createSequentialGroup()
						.addComponent(stats)
						.addComponent(combo)
						.addGroup(layout.createParallelGroup()
								.addComponent(statsTwo)
						)
						.addComponent(search)
						)
				)
		);
	}
}