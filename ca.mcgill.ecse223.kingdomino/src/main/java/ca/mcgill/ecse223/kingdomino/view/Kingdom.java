package ca.mcgill.ecse223.kingdomino.view;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

@SuppressWarnings("serial")
public class Kingdom extends JFrame {

	public Kingdom() {
		
		initKingdom();
	}
	
	public void initKingdom() {
		
		JFrame frame = KingUI_Main.frame;
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
		
		JButton back = new JButton("Back");
		back.setFont(new Font("Times", Font.BOLD, 16));
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//back to endgame?
			}
		});
		
		JLabel player = new JLabel("player_name");
		player.setFont(new Font("Times", Font.BOLD, 50));
		
		JButton next = new JButton("View next player's kingdom.");
		
		JPanel p1 = new JPanel();
		p1.add(territory);
	}
}
