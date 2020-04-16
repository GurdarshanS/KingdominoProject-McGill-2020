package ca.mcgill.ecse223.kingdomino.view;
import ca.mcgill.ecse223.kingdomino.model.*;
import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.*;
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
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.Border;

import ca.mcgill.ecse223.kingdomino.controller.KDController;
import ca.mcgill.ecse223.kingdomino.controller.KDQuery;
import ca.mcgill.ecse223.kingdomino.model.Kingdomino;

import javax.swing.GroupLayout;


public class King_PlayerScreen extends JFrame {

	static JButton createNew1 = new JButton("Assign New User");
	static JButton browse1 = new JButton("Assign Existing User");
	static JButton nouser1 = new JButton("No User");
	
	static JButton createNew2 = new JButton("Assign New User");
	static JButton browse2 = new JButton("Assign Existing User");
	static JButton nouser2 = new JButton("No User");
	
	static JButton createNew3 = new JButton("Assign New User");
	static JButton browse3 = new JButton("Assign Existing User");
	static JButton nouser3 = new JButton("No User");
	
	static JButton createNew4 = new JButton("Assign New User");
	static JButton browse4 = new JButton("Assign Existing User");
	static JButton nouser4 = new JButton("No User");

	public King_PlayerScreen() {
		
		initComponents();
		this.setSize(1350,850);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setTitle("Kingdomino");
		}
	
	public static void initComponents() {
		
		//Kingdomino kd = KDController.loadGame(null);

		JLabel header = new JLabel("Players");
		header.setFont(new Font("Times", Font.BOLD, 60));
		Border headerBorder = BorderFactory.createLineBorder(Color.black);
		header.setBorder(headerBorder);
		
		JLabel player1 = new JLabel("Player Blue");
		JLabel player2 = new JLabel("Player Pink");
		JLabel player3 = new JLabel("Player Yellow");
		JLabel player4 = new JLabel("Player Green");
		
		JButton done = new JButton("Done");
		done.setEnabled(false);
//		ButtonGroup player1Group = new ButtonGroup();
//		player1Group.add(createNew1);
//		player1Group.add(browse1); necessary???
		
		JPanel p3 = new JPanel();
		GroupLayout layout = new GroupLayout(p3);
		p3.setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		KingUI_Main.contPanel.add(p3, "3");
		
		layout.setHorizontalGroup(
				layout.createSequentialGroup()
				.addGap(450)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
						.addComponent(header)
						.addGap(30)
						.addComponent(player1)
						.addGroup(layout.createSequentialGroup()
								.addComponent(createNew1)
								.addComponent(browse1)
								.addComponent(nouser1)
								
						)
						
						.addComponent(player2)
						.addGroup(layout.createSequentialGroup()
								.addComponent(createNew2)
								.addComponent(browse2)
								.addComponent(nouser2)
					)
						.addComponent(player3)
						.addGroup(layout.createSequentialGroup()
								.addComponent(createNew3)
								.addComponent(browse3)
								.addComponent(nouser3)
					)
						.addComponent(player4)
						.addGroup(layout.createSequentialGroup()
								.addComponent(createNew4)
								.addComponent(browse4)
								.addComponent(nouser4)
					)
						.addComponent(done)
					)
				);
		
		
		layout.setVerticalGroup(
				layout.createParallelGroup(GroupLayout.Alignment.CENTER)
				.addGroup(layout.createSequentialGroup()
				.addGap(100)
					.addComponent(header)
					.addGap(30)
					.addComponent(player1)
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
							.addComponent(createNew1)
							.addComponent(browse1)
							.addComponent(player1)
							.addComponent(nouser1)
							
					)
					.addComponent(player2)
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
							.addComponent(createNew2)
							.addComponent(browse2)
							.addComponent(nouser2)
					)
					.addComponent(player3)
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
							.addComponent(createNew3)
							.addComponent(browse3)
							.addComponent(nouser3)
					)
					.addComponent(player4)
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
							.addComponent(createNew4)
							.addComponent(browse4)
							.addComponent(nouser4)
					)
					.addComponent(done)
					)
				);

		
		createNew1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				KingUI_PlayerCreate.initComponents("blue");
				KingUI_Main.c1.show(KingUI_Main.contPanel, "4");
				System.out.println("create new user1 initiated");
				if(!createNew1.isEnabled() && !createNew2.isEnabled() && !createNew3.isEnabled() && !createNew4.isEnabled()) {
					done.setEnabled(true);
				}
			}
		});
			
			browse1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					
					new  AssignPlayer_Screen("yellow");
					KingUI_Main.c1.show(KingUI_Main.contPanel, "5");
					System.out.println("create new user1 initiated");
					if(!createNew1.isEnabled() && !createNew2.isEnabled() && !createNew3.isEnabled() && !createNew4.isEnabled()) {
						done.setEnabled(true);
					}
				}
	
			});
			nouser1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					
					King_PlayerScreen.browse1.setEnabled(false);
					King_PlayerScreen.createNew1.setEnabled(false);
					King_PlayerScreen.nouser1.setEnabled(false);
					if(!createNew1.isEnabled() && !createNew2.isEnabled() && !createNew3.isEnabled() && !createNew4.isEnabled()) {
						done.setEnabled(true);
					}
					
					
				}
	
			});
			
			createNew2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					KingUI_PlayerCreate.initComponents("pink");
					KingUI_Main.c1.show(KingUI_Main.contPanel, "4");
					System.out.println("create new user1 initiated");
					if(!createNew1.isEnabled() && !createNew2.isEnabled() && !createNew3.isEnabled() && !createNew4.isEnabled()) {
						done.setEnabled(true);
					}
				}
			});
				
				browse2.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						new  AssignPlayer_Screen("pink");
						KingUI_Main.c1.show(KingUI_Main.contPanel, "5");
						System.out.println("create new user1 initiated");
						if(!createNew1.isEnabled() && !createNew2.isEnabled() && !createNew3.isEnabled() && !createNew4.isEnabled()) {
							done.setEnabled(true);
						}
					}
		
				});
				nouser2.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						
						King_PlayerScreen.browse2.setEnabled(false);
						King_PlayerScreen.createNew2.setEnabled(false);
						King_PlayerScreen.nouser2.setEnabled(false);
						if(!createNew1.isEnabled() && !createNew2.isEnabled() && !createNew3.isEnabled() && !createNew4.isEnabled()) {
							done.setEnabled(true);
						}
						
						
					}
		
				});
				
				createNew3.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						KingUI_PlayerCreate.initComponents("yellow");
						KingUI_Main.c1.show(KingUI_Main.contPanel, "4");
						System.out.println("create new user1 initiated");
						if(!createNew1.isEnabled() && !createNew2.isEnabled() && !createNew3.isEnabled() && !createNew4.isEnabled()) {
							done.setEnabled(true);
						}
					}
				});
					
					browse3.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent evt) {
							new  AssignPlayer_Screen("yellow");
							KingUI_Main.c1.show(KingUI_Main.contPanel, "5");
							System.out.println("create new user1 initiated");
							if(!createNew1.isEnabled() && !createNew2.isEnabled() && !createNew3.isEnabled() && !createNew4.isEnabled()) {
								done.setEnabled(true);
							}
						}
			
					});
					nouser3.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent evt) {
							
							King_PlayerScreen.browse3.setEnabled(false);
							King_PlayerScreen.createNew3.setEnabled(false);
							King_PlayerScreen.nouser3.setEnabled(false);
							if(!createNew1.isEnabled() && !createNew2.isEnabled() && !createNew3.isEnabled() && !createNew4.isEnabled()) {
								done.setEnabled(true);
							}
							
							
						}
			
					});
					
				createNew4.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						KingUI_PlayerCreate.initComponents("green");
						KingUI_Main.c1.show(KingUI_Main.contPanel, "4");
						System.out.println("create new user1 initiated");
						if(!createNew1.isEnabled() && !createNew2.isEnabled() && !createNew3.isEnabled() && !createNew4.isEnabled()) {
							done.setEnabled(true);
						}
					}
				});
						
						browse4.addActionListener(new java.awt.event.ActionListener() {
							public void actionPerformed(java.awt.event.ActionEvent evt) {
								new  AssignPlayer_Screen("green");
								KingUI_Main.c1.show(KingUI_Main.contPanel, "5");
								System.out.println("create new user1 initiated");
								if(!createNew1.isEnabled() && !createNew2.isEnabled() && !createNew3.isEnabled() && !createNew4.isEnabled()) {
									done.setEnabled(true);
								}
							}
				
						});
						nouser4.addActionListener(new java.awt.event.ActionListener() {
							public void actionPerformed(java.awt.event.ActionEvent evt) {
								
								King_PlayerScreen.browse4.setEnabled(false);
								King_PlayerScreen.createNew4.setEnabled(false);
								King_PlayerScreen.nouser4.setEnabled(false);
								if(!createNew1.isEnabled() && !createNew2.isEnabled() && !createNew3.isEnabled() && !createNew4.isEnabled()) {
									done.setEnabled(true);
								}
								
							}
				
						});
				
						done.addActionListener(new java.awt.event.ActionListener() {
							public void actionPerformed(java.awt.event.ActionEvent evt) {
							
								KDController.saveGame(null, true);
								KingdominoApplication.getStateMachine();
								KDController.draftReadySM();
								
								new StartPlayingUI();
								KingUI_Main.frame.dispose();
								System.out.println("Back");
						}
				});
		
	}
}