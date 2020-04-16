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
		 Border borderTwo;
	
		List<User> users = KingdominoApplication.getKingdomino().getUsers();
		List<String> sList = new ArrayList<String>();
		for(User u: users) {
			wins = u.getWonGames();
			played = u.getPlayedGames();
			loss = played-wins;
			//ratio = wins/played;
			String row = String.format("%1$-20s Games Won: %2$-5d Games Lost: %3$-5d Games Played: %4$-5d Win Ratio: %5$-5f",
										u.getName(), wins, loss, played, ratio);
			sList.add(row);
		}
		
		JList list = new JList(sList.toArray());
		list.setFont(new Font("Times", Font.BOLD, 16));
		
		// back button
		back = new JButton("Back");
		back.setFont(new Font("Times", Font.BOLD, 16));
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				KingUI_Main.c1.show(KingUI_Main.contPanel, "1");
			}
		});
		// box that says stats
		border = BorderFactory.createLineBorder(Color.BLACK);
		stats = new JLabel("Insert Username Below", SwingConstants.CENTER);
		stats.setText("Player Statistics");
		stats.setBorder(border);
		stats.setFont(new Font("Times", Font.BOLD, 55));
		
		
		borderTwo = BorderFactory.createLineBorder(Color.BLACK);
		JPanel p3 = new JPanel();
		GroupLayout layout = new GroupLayout(p3);
		p3.setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		KingUI_Main.contPanel.add(p3, "6");
		
		layout.setHorizontalGroup(
				layout.createSequentialGroup()
				.addComponent(back)
				.addGap(300)
				.addGroup(layout.createSequentialGroup())
				.addGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
						.addComponent(stats)
						.addComponent(list)
						//.addComponent(input, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE )
						.addGap(50)
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
						.addGap(100)
						.addComponent(stats)
						.addComponent(list)
						//.addComponent(input,GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE  )
						.addGap(50)
						.addGroup(layout.createParallelGroup()
								.addComponent(statsTwo)
						)
						)
				)
		);
	}
}