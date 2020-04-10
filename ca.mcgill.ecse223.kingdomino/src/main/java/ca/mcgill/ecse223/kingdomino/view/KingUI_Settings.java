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
		group1.add(two);
		group1.add(three);
		group1.add(four);
		
		JLabel bonus = new JLabel("Bonus Points");
		bonus.setFont(new Font("Times", Font.BOLD, 20));
		JRadioButton yes = new JRadioButton("Yes");
		JRadioButton no = new JRadioButton("No");
		
		JLabel mode = new JLabel("Game Mode");
		mode.setFont(new Font("Times", Font.BOLD, 20));
		JRadioButton reg = new JRadioButton("Regular");
		JRadioButton dyn = new JRadioButton("Dynasty");
		JRadioButton mighty = new JRadioButton("Mighty Duel");
		
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
				System.out.println("2");
			}
		});
		
		three.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				two.setSelected(false);
				three.setSelected(true);
				four.setSelected(false);
				System.out.println("3");
			}
		});
		
		four.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				two.setSelected(false);
				three.setSelected(false);
				four.setSelected(true);
				System.out.println("4");
			}
		});
		
		yes.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				yes.setSelected(true);
				no.setSelected(false);
				System.out.println("yes");
			}
		});
		no.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				no.setSelected(true);
				yes.setSelected(false);
				System.out.println("no");
			}
		});
		
		reg.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				reg.setSelected(true);
				dyn.setSelected(false);
				mighty.setSelected(false);
				System.out.println("reg");
			}
		});
		
		dyn.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				dyn.setSelected(true);
				reg.setSelected(false);
				mighty.setSelected(false);
				System.out.println("dyn");
			}
		});
		mighty.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				mighty.setSelected(true);
				reg.setSelected(false);
				dyn.setSelected(false);
				System.out.println("mighty");
			}
		});
		
		
		cont.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				if(two.isSelected()) {
					System.out.println("two selected");
					KingdominoApplication.getKingdomino().getCurrentGame().setNumberOfPlayers(2);
				} else if(three.isSelected()) {
					System.out.println("three selected");
					KingdominoApplication.getKingdomino().getCurrentGame().setNumberOfPlayers(3);
				} else if(four.isSelected()) {
					System.out.println("four selected");
					KingdominoApplication.getKingdomino().getCurrentGame().setNumberOfPlayers(4);
				}
				
				if(yes.isSelected()) {
					System.out.println("yes selected");
				} else if(no.isSelected()) {
					System.out.println("no selected");
				}
				
				if(reg.isSelected()) {
					KingUI_Main.c1.show(KingUI_Main.contPanel, "3");
					System.out.println("regular selected");
				} else if(dyn.isSelected()) {
					System.out.println("dynasty selected");
				} else if(mighty.isSelected()) {
					System.out.println("mighty selected");
				}
				KingUI_PlayerCreate.initComponents();
				KingUI_Main.c1.show(KingUI_Main.contPanel, "3");
			}
		});
		
	}
}