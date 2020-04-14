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
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.KDController;
import ca.mcgill.ecse223.kingdomino.controller.KDQuery;
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
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				KingUI_Main.c1.show(KingUI_Main.contPanel, "3");
			}
		});
		// box that says stats
		border = BorderFactory.createLineBorder(Color.BLACK);
		stats = new JLabel("Insert Username Below", SwingConstants.CENTER);
		stats.setText("Player Statistics");
		stats.setBorder(border);
		stats.setFont(new Font("Times", Font.BOLD, 55));
		
		// User names
		List<User> users = KDQuery.getUsers();
		JList list = new JList(users.toArray());
		list.setFont(new Font("Times", Font.BOLD, 16));
		JTextField input = new JTextField();
		search = new JButton("Search");
		search.setFont(new Font("Times", Font.BOLD, 16));
		search.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = input.getText();
				User user = KDController.getUserByName(name);
				wins = user.getWonGames();
				played = user.getPlayedGames();
				loss = played-wins;
				ratio = wins/played;
			}
		});
		
		borderTwo = BorderFactory.createLineBorder(Color.BLACK);
		statsTwo = new JLabel("User info", SwingConstants.CENTER);
		statsTwo.setText("<html> No. Games Won: "+wins
						+ "<br/><br/><br/>No. Games Lost: "+loss
						+ "<br/><br/><br/>No. Games Played: "+played
						+ "<br/><br/><br/>Win Ratio: "+ratio +"<html>");
		statsTwo.setBorder(borderTwo);
		statsTwo.setFont(new Font("Times", Font.BOLD, 25));
	
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
						.addComponent(input, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE )
						.addGap(50)
						.addComponent(search)
						.addGroup(layout.createSequentialGroup()
								.addComponent(statsTwo)
						)
						)
				)
		);
		
		layout.setVerticalGroup(
				layout.createParallelGroup()
				.addComponent(back)
				.addGroup(layout.createParallelGroup())
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
				.addGroup(layout.createSequentialGroup()
						.addGap(50)
						.addComponent(stats)
						.addComponent(input,GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE  )
						.addGap(50)
						.addComponent(search)
						.addGap(50)
						.addGroup(layout.createParallelGroup()
								.addComponent(statsTwo)
						)
						)
				)
		);
	}
}