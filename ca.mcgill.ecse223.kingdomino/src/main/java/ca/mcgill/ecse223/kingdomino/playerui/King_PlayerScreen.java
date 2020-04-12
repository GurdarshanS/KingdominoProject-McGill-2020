package ca.mcgill.ecse223.kingdomino.playerui;
import ca.mcgill.ecse223.kingdomino.model.*;
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
	
	
	public static void main(String[] args) {
		
		new King_PlayerScreen().setVisible(true);

	}



	public King_PlayerScreen() {
		
		initComponents();
	
		}
	
	public void initComponents() {
		
		Kingdomino kd = KDController.loadGame(null);
		
		this.setSize(500,400);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setTitle("Kingdomino");
		
		

		JLabel header = new JLabel("Players");
		header.setFont(new Font("Times", Font.BOLD, 60));
		Border headerBorder = BorderFactory.createLineBorder(Color.black);
		header.setBorder(headerBorder);
		
		JLabel player1 = new JLabel("Player Blue");
		JLabel player2 = new JLabel("Player Pink");
		JLabel player3 = new JLabel("Player Yellow");
		JLabel player4 = new JLabel("Player Green");
		
		
		JButton createNew1 = new JButton("Assign New User");
		JButton browse1 = new JButton("Assign Existing User");
		JButton nouser1 = new JButton("No User");
		
		JButton createNew2 = new JButton("Assign New User");
		JButton browse2 = new JButton("Assign Existing User");
		JButton nouser2 = new JButton("No User");
		
		JButton createNew3 = new JButton("Assign New User");
		JButton browse3 = new JButton("Assign Existing User");
		JButton nouser3 = new JButton("No User");
		
		JButton createNew4 = new JButton("Assign New User");
		JButton browse4 = new JButton("Assign Existing User");
		JButton nouser4 = new JButton("No User");
		
		JButton back = new JButton("Back");
		JButton done = new JButton("Done");
		
//		ButtonGroup player1Group = new ButtonGroup();
//		player1Group.add(createNew1);
//		player1Group.add(browse1); necessary???
		
		
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
		layout.setHorizontalGroup(
				layout.createSequentialGroup()
				.addComponent(back)
				.addGap(400)
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
				.addComponent(back)
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
				System.out.println("create new user1 initiated");
			}
		});
			
			browse1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					
					//pop up 
					Player playerBlue = KDQuery.getPlayerByColor("blue");
				}
	
			});
			
			createNew2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					System.out.println("create new user2 initiated");
				}
			});
				
				browse2.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						System.out.println("browse profiles2 initiated");
					}
		
				});
				
				createNew3.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						System.out.println("create new user3 initiated");
					}
				});
					
					browse3.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent evt) {
							System.out.println("browse profiles3 initiated");
						}
			
					});
					
					createNew4.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent evt) {
							System.out.println("create new user4 initiated");
						}
					});
						
						browse4.addActionListener(new java.awt.event.ActionListener() {
							public void actionPerformed(java.awt.event.ActionEvent evt) {
								System.out.println("browse profiles4 initiated");
							}
				
						});
						back.addActionListener(new java.awt.event.ActionListener() {
							public void actionPerformed(java.awt.event.ActionEvent evt) {
								System.out.println("Done");
							}
						});
						done.addActionListener(new java.awt.event.ActionListener() {
							public void actionPerformed(java.awt.event.ActionEvent evt) {
								System.out.println("Back");
							}
						});
		
	}
}
