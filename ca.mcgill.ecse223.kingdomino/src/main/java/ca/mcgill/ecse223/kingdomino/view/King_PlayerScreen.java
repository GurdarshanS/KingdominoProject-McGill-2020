package ca.mcgill.ecse223.kingdomino.view;

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
import javax.swing.GroupLayout;


public class King_PlayerScreen extends JFrame {

	public static void main(String[] args) {
		
		new King_PlayerScreen().setVisible(true);

	}
	
	public King_PlayerScreen() {
		initComponents();
	
		}
	
	public void initComponents() {
		
		this.setSize(500,400);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setTitle("Kingdomino");
		
		

		JLabel header = new JLabel("Players");
		header.setFont(new Font("Times", Font.BOLD, 60));
		Border headerBorder = BorderFactory.createLineBorder(Color.black);
		header.setBorder(headerBorder);
		
		JLabel player1 = new JLabel("Player 1");
		JLabel player2 = new JLabel("Player 2");
		JLabel player3 = new JLabel("Player 3");
		JLabel player4 = new JLabel("Player 4");
		
		
		JButton createNew1 = new JButton("Create New");
		JButton browse1 = new JButton("Browse Profiles");
		
		JButton createNew2 = new JButton("Create New");
		JButton browse2 = new JButton("Browse Profiles");
		
		JButton createNew3 = new JButton("Create New");
		JButton browse3 = new JButton("Browse Profiles");
		
		JButton createNew4 = new JButton("Create New");
		JButton browse4 = new JButton("Browse Profiles");
		
//		ButtonGroup player1Group = new ButtonGroup();
//		player1Group.add(createNew1);
//		player1Group.add(browse1); necessary???
		
		
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		layout.setHorizontalGroup(
				layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
						.addComponent(header)
						.addComponent(player1)
						.addGroup(layout.createSequentialGroup()
								.addComponent(createNew1)
								.addComponent(browse1)
								
						)
						.addComponent(player2)
						.addGroup(layout.createSequentialGroup()
								.addComponent(createNew2)
								.addComponent(browse2)
					)
						.addComponent(player3)
						.addGroup(layout.createSequentialGroup()
								.addComponent(createNew3)
								.addComponent(browse3)
					)
						.addComponent(player4)
						.addGroup(layout.createSequentialGroup()
								.addComponent(createNew4)
								.addComponent(browse4)
					)
					)
				);
		
		
		layout.setVerticalGroup(
				layout.createParallelGroup(GroupLayout.Alignment.CENTER)
				.addGroup(layout.createSequentialGroup()
					.addComponent(header)
					.addComponent(player1)
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
							.addComponent(createNew1)
							.addComponent(browse1)
							.addComponent(player1)
							
					)
					.addComponent(player2)
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
							.addComponent(createNew2)
							.addComponent(browse2)
					)
					.addComponent(player3)
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
							.addComponent(createNew3)
							.addComponent(browse3)
					)
					.addComponent(player4)
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
							.addComponent(createNew4)
							.addComponent(browse4)
					)
					)
				);
		
		createNew1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				System.out.println("create new user1 initiated");
			}
		});
			
			browse1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					System.out.println("browse profiles1 initiated");
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
	}
}
