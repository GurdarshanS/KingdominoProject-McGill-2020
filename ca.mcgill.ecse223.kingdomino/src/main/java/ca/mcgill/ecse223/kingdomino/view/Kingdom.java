package ca.mcgill.ecse223.kingdomino.view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

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
		
		//Player player = KingdominoApplication.getKingdomino().getCurrentGame().getNextPlayer();
		String player = "player_name";
		
		JFrame frame = new JFrame();
		frame = KingUI_Main.frame;
		frame.setSize(1350, 850);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		JLabel territory = new JLabel("Territory Score");
		territory.setFont(new Font("Times", Font.BOLD, 50));
		JLabel wheatScore = new JLabel("W");
		wheatScore.setFont(new Font("Times", Font.BOLD, 50));
		JLabel forestScore = new JLabel("F");
		forestScore.setFont(new Font("Times", Font.BOLD, 50));
		JLabel grassScore = new JLabel("G");
		grassScore.setFont(new Font("Times", Font.BOLD, 50));
		JLabel swampScore = new JLabel("S");
		swampScore.setFont(new Font("Times", Font.BOLD, 50));
		JLabel lakeScore = new JLabel("L");
		lakeScore.setFont(new Font("Times", Font.BOLD, 50));
		
		JLabel kingdom = new JLabel("Kingdom Score");
		kingdom.setFont(new Font("Times", Font.BOLD, 50));
		JLabel kingdomScore = new JLabel("K");
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
		
		JButton next = new JButton("View next player's kingdom.");
		next.setFont(new Font("Times", Font.BOLD, 16));
		next.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Next Player");
				//Kingdom.initKingdom();
			}
		});
		
		JPanel p = new JPanel();
		
		p.add(back);
		p.add(playerName);
		p.add(next);
		
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
						
								
						)
						.addGroup(group1.createSequentialGroup()
								.addComponent(wheatScore)
								.addComponent(forestScore)
								.addComponent(grassScore)
								.addComponent(swampScore)
								.addComponent(lakeScore)
						)
						.addComponent(kingdom)
						.addComponent(kingdomScore)
						)
				)

		);
		
		group1.setVerticalGroup(
				group1.createParallelGroup()
				.addGroup(group1.createParallelGroup())
				.addGroup(group1.createParallelGroup(GroupLayout.Alignment.CENTER)
				.addGroup(group1.createSequentialGroup()
						.addComponent(territory)
						.addGroup(group1.createParallelGroup()
						
								
						)
						.addGroup(group1.createParallelGroup()
								.addComponent(wheatScore)
								.addComponent(forestScore)
								.addComponent(grassScore)
								.addComponent(swampScore)
								.addComponent(lakeScore)
						)
						.addComponent(kingdom)
						.addComponent(kingdomScore)
						)
				)

		);
		
		p1.setLayout(group1);
		frame.add(p, BorderLayout.NORTH);
		frame.add(p1, BorderLayout.WEST);
		frame.setVisible(true);
	}
}
