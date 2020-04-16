package ca.mcgill.ecse223.kingdomino.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.Border;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.KDController;
import ca.mcgill.ecse223.kingdomino.model.BonusOption;
import ca.mcgill.ecse223.kingdomino.model.Kingdomino;
import ca.mcgill.ecse223.kingdomino.model.Player;
import ca.mcgill.ecse223.kingdomino.model.User;

public class KingUI_Settings extends JFrame{
	
	public static void initSettings() {
		
		JLabel title = new JLabel("Game Settings");
		title.setFont(new Font("Times", Font.BOLD, 60));
		Border blackline = BorderFactory.createLineBorder(Color.black);
		title.setBorder(blackline);
		
		JLabel players = new JLabel("No. of Players");
		players.setFont(new Font("Times", Font.BOLD, 20));
		JRadioButton two = new JRadioButton("2");
		JRadioButton three = new JRadioButton("3");
		JRadioButton four = new JRadioButton("4");
		ButtonGroup group1 = new ButtonGroup();
		two.setEnabled(false);
		three.setEnabled(false);
		
		group1.add(two);
		group1.add(three);
		group1.add(four);
		
		JLabel bonus = new JLabel("Bonus Points");
		bonus.setFont(new Font("Times", Font.BOLD, 20));
		JRadioButton yes = new JRadioButton("Harmony");
		JRadioButton no = new JRadioButton("Middle Kingdom");
		
		JLabel mode = new JLabel("Game Mode");
		mode.setFont(new Font("Times", Font.BOLD, 20));
		JRadioButton reg = new JRadioButton("Regular");
		JRadioButton dyn = new JRadioButton("Dynasty");
		JRadioButton mighty = new JRadioButton("Mighty Duel");
		dyn.setEnabled(false);
		mighty.setEnabled(false);
		
		JButton cont = new JButton("Continue");
		JButton back = new JButton("Back");
		
		JPanel p1 = new JPanel();
	

		GroupLayout layout = new GroupLayout(p1);
		p1.setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
		
		layout.setHorizontalGroup(
				layout.createSequentialGroup()
				.addComponent(back)
				.addGap(400)
				.addGroup(layout.createSequentialGroup())
				.addGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
						.addComponent(title)
						.addComponent(players)
						.addGroup(layout.createSequentialGroup()
								.addComponent(two)
								.addComponent(three)
								.addComponent(four)
						)
						.addComponent(bonus)
						.addGroup(layout.createSequentialGroup()
								.addComponent(yes)
								.addComponent(no)
						)
						.addComponent(mode)
						.addGroup(layout.createSequentialGroup()
								.addComponent(reg)
								.addComponent(dyn)
								.addComponent(mighty)
								)
						.addComponent(cont)
						)
				)

		);
		
		layout.setVerticalGroup(
				layout.createParallelGroup()
				.addComponent(back)
				.addGroup(layout.createParallelGroup())
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
				.addGroup(layout.createSequentialGroup()
					.addGap(250)
					.addComponent(title)
					.addComponent(players)
					.addGroup(layout.createParallelGroup()
							.addComponent(two)
							.addComponent(three)
							.addComponent(four)
					)
					.addComponent(bonus)
					.addGroup(layout.createParallelGroup()
							.addComponent(yes)
							.addComponent(no)
							)
					.addComponent(mode)
					.addGroup(layout.createParallelGroup()
							.addComponent(reg)
							.addComponent(dyn)
							.addComponent(mighty)
							)
					.addComponent(cont)
						)
				)
		);
		KingUI_Main.contPanel.add(p1, "2");
		
		back.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				KingUI_Main.c1.show(KingUI_Main.contPanel, "1");
			}
		});
		
		two.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				two.setSelected(true);
				three.setSelected(false);
				four.setSelected(false);
				KingUI_Main.playerNum = 2;
				System.out.println("2");
			}
		});
		
		three.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				two.setSelected(false);
				three.setSelected(true);
				four.setSelected(false);
				KingUI_Main.playerNum = 3;
				System.out.println("3");
			}
		});
		
		four.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				two.setSelected(false);
				three.setSelected(false);
				four.setSelected(true);
				KingUI_Main.playerNum = 4;
				System.out.println("4");
			}
		});
		
		yes.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				System.out.println("Harmony");
				KingUI_Main.bonusOptions.add("harmony");
			}
		});
		no.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				System.out.println("Middle Kingdom");
				KingUI_Main.bonusOptions.add("middlekingdom");

			}
		});
		
		reg.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				// regular game mode
			}
		});
		
		dyn.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				// to-do bonus feature
			}
		});
		mighty.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				// to-do bonus feature
			}
		});
		
		
		cont.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				Kingdomino kd = KingdominoApplication.getKingdomino();
				if(!four.isSelected() || !reg.isSelected()) {
					System.out.println("Select stuff plz");
				}else {
					try {
						KDController.setGameOptions(KingUI_Main.playerNum, KingUI_Main.bonusOptions);
						KDController.startANewGame();
						King_PlayerScreen.initComponents();
						KingUI_Main.c1.show(KingUI_Main.contPanel,"3");
						KDController.saveGame(null, true);
						for(Player p : kd.getCurrentGame().getPlayers()) {
							System.out.println(p.getColor()+" "+p.hasUser());
						}
						for(User u : kd.getUsers()) {
							System.out.println(u.getName()+ " "+u.hasPlayerInGames());
							
							for(Player p : u.getPlayerInGames()) {
								System.out.println(p.getGame().equals(kd.getCurrentGame()));
							}
						}
					} catch(Exception e) {
						System.out.println(e.getMessage());
					}
				}
			}
		});
		
	}
}
