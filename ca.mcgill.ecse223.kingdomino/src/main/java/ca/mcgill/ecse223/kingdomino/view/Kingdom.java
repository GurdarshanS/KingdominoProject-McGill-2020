package ca.mcgill.ecse223.kingdomino.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.Border;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.model.Player;

@SuppressWarnings("serial")
public class Kingdom extends JFrame {

	public static void main(String[] args) {
		
		initKingdom();
	}
	public Kingdom() {
		
		initKingdom();
	}
	
	public static void initKingdom() {
		
		Border border = BorderFactory.createLineBorder(Color.DARK_GRAY);
		int gap = 40;
		//Player player = KingdominoApplication.getKingdomino().getCurrentGame().getNextPlayer();
		String player = "player_name";
		
		JFrame frame = new JFrame();
		frame = KingUI_Main.frame;
		frame.setSize(1350, 850);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		JLabel territory = new JLabel("Territory Score");
		territory.setFont(new Font("Times", Font.BOLD, 50));
		JLabel wheatScore = new JLabel("4");
		JPanel wheat = new JPanel(new GridLayout(1,1));
		wheat.setBackground(Color.YELLOW);
		wheat.setBorder(border);
		wheatScore.setFont(new Font("Times", Font.BOLD, 50));
		JLabel forestScore = new JLabel("0");
		JPanel forest = new JPanel(new GridLayout(1,1));
		forest.setBackground(Color.DARK_GRAY);
		forest.setBorder(border);
		forestScore.setFont(new Font("Times", Font.BOLD, 50));
		JLabel grassScore = new JLabel("15");
		JPanel grass = new JPanel(new GridLayout(1,1));
		grass.setBackground(Color.GREEN);
		grass.setBorder(border);
		grassScore.setFont(new Font("Times", Font.BOLD, 50));
		JLabel swampScore = new JLabel("7");
		JPanel swamp = new JPanel(new GridLayout(1,1));
		swamp.setBackground(Color.black);
		swamp.setBorder(border);
		swampScore.setFont(new Font("Times", Font.BOLD, 50));
		JLabel lakeScore = new JLabel("3");
		JPanel lake = new JPanel(new GridLayout(1,1));
		lake.setBackground(Color.BLUE);
		lake.setBorder(border);
		lakeScore.setFont(new Font("Times", Font.BOLD, 50));
		
		JLabel kingdom = new JLabel("Kingdom Score");
		kingdom.setFont(new Font("Times", Font.BOLD, 50));
		JLabel kingdomScore = new JLabel("29");
		kingdomScore.setFont(new Font("Times", Font.BOLD, 50));
		
		JLabel playerName = new JLabel(player+"'s Kingdom");
		playerName.setFont(new Font("Times", Font.BOLD, 50));
		
		JButton back = new JButton("Back");
		back.setFont(new Font("Times", Font.BOLD, 16));
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("back");
				//back to endgame?
			}
		});
		
		JButton next = new JButton("View Next Player's Kingdom");
		next.setFont(new Font("Times", Font.BOLD, 16));
		next.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Next Player");
				//Kingdom.initKingdom();
			}
		});
		JPanel p2 = new JPanel(new GridBagLayout());
		
		JPanel p = new JPanel();
		GroupLayout group = new GroupLayout(p);
		group.setAutoCreateContainerGaps(true);
		group.setAutoCreateGaps(true);
		
		group.setHorizontalGroup(
				group.createSequentialGroup()
				.addGroup(group.createSequentialGroup())
				.addGroup(group.createSequentialGroup()
				.addGroup(group.createParallelGroup(GroupLayout.Alignment.CENTER)						
						.addGroup(group.createSequentialGroup()
								.addComponent(back)
								.addGap(275)
								.addComponent(playerName)
								.addGap(100)
								.addComponent(next)
								)
						)
				)
		);
		
		group.setVerticalGroup(
				group.createParallelGroup()
				.addGroup(group.createParallelGroup())
				.addGroup(group.createParallelGroup(GroupLayout.Alignment.CENTER)
				.addGroup(group.createSequentialGroup()						
						.addGroup(group.createParallelGroup()
								.addComponent(back)
								.addComponent(playerName)
								.addGap(100)
								.addComponent(next)								
								)
						)
				)
		);
		
		p.setLayout(group);		
		
		JPanel p1 = new JPanel();
		GroupLayout group1 = new GroupLayout(p1);
		group1.setAutoCreateContainerGaps(true);
		group1.setAutoCreateGaps(true);
		
		group1.setHorizontalGroup(
				group1.createSequentialGroup()
				.addGroup(group1.createSequentialGroup())
				.addGroup(group1.createSequentialGroup()
				.addGroup(group1.createParallelGroup(GroupLayout.Alignment.CENTER)
						.addComponent(territory)
						.addGroup(group1.createSequentialGroup()
								.addComponent(wheat)
								.addComponent(forest)
								.addComponent(grass)
								.addComponent(swamp)
								.addComponent(lake)
						)
						.addGroup(group1.createSequentialGroup()
								.addComponent(wheatScore)
								.addGap(gap)
								.addComponent(forestScore)
								.addGap(gap)
								.addComponent(grassScore)
								.addGap(gap)
								.addComponent(swampScore)
								.addGap(gap)
								.addComponent(lakeScore)
								)
						.addComponent(p2)
						.addComponent(kingdom)
						.addComponent(kingdomScore)
						.addComponent(p2)
						)
				)
		);
		
		group1.setVerticalGroup(
				group1.createParallelGroup()
				.addGroup(group1.createParallelGroup())
				.addGroup(group1.createParallelGroup(GroupLayout.Alignment.CENTER)
				.addGroup(group1.createSequentialGroup()
						.addGap(100)
						.addComponent(territory)
						.addGroup(group1.createParallelGroup()
								.addComponent(wheat)
								.addComponent(forest)
								.addComponent(grass)
								.addComponent(swamp)
								.addComponent(lake)
						)
						.addGroup(group1.createParallelGroup()
								.addComponent(wheatScore)
								.addGap(gap)
								.addComponent(forestScore)
								.addGap(gap)
								.addComponent(grassScore)
								.addGap(gap)
								.addComponent(swampScore)
								.addGap(gap)
								.addComponent(lakeScore)
								)
						.addGap(100)
						.addComponent(p2)
						.addComponent(kingdom)
						.addComponent(kingdomScore)
						.addComponent(p2)
						)
				)
		);
		
		p1.setLayout(group1);
		
		JPanel p3 = new JPanel(new GridLayout(9,9));
		JPanel[] panels = new JPanel[81];
		for(int i=0; i<81; i++) {
			
			panels[i] = new JPanel();
			panels[i].setBackground(Color.WHITE);
			panels[i].setBorder(border);
			p3.add(panels[i]);
		}
		
		frame.add(p3, BorderLayout.CENTER);
		
		frame.add(p, BorderLayout.NORTH);
		frame.add(p1, BorderLayout.WEST);
		//frame.add(p2, BorderLayout.EAST);
		frame.setVisible(true);
	} 
}
