package ca.mcgill.ecse223.kingdomino.view;

import javax.swing.JLabel;

import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.KDController;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class KingUI_PlayerCreate extends JFrame {
	
	public KingUI_PlayerCreate(){
		
		initComponents();
		
	}
	public static void initComponents() {
		
		// Layout Manager
		//Container c =  getContentPane();
		//setLayout(new GroupLayout(c));
		
		// Swing components
		JLabel name;
		JTextField insertName;
		JLabel label;
		JButton done;
		JButton back;
		Border border;
		
		// back button
		back = new JButton("Back");
		back.setFont(new Font("Times", Font.BOLD, 16));
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				KingUI_Main.c1.show(KingUI_Main.contPanel, "2");
			}
		});
		// title
		border = BorderFactory.createLineBorder(Color.BLACK);
		name = new JLabel("Insert Username Below", SwingConstants.CENTER);
		name.setText("Create User Profile");
		name.setBorder(border);
		name.setFont(new Font("Times", Font.BOLD, 55));
		
		// instruction
		label = new JLabel("label", SwingConstants.CENTER);
		label.setText("Insert Player Usernames");
		label.setFont(new Font("Times", Font.BOLD, 20));
		
		// text box
		insertName = new JTextField();
		JTextField insertName2 = new JTextField();
		JTextField insertName3 = new JTextField();
		JTextField insertName4 = new JTextField();

		insertName.setColumns(10);
		insertName2.setColumns(10);
		insertName3.setColumns(10);
		insertName4.setColumns(10);

		// create name
		done = new JButton("Create");
		done.setFont(new Font("Times", Font.BOLD, 16));
		done.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				KingUI_Stats.initComponents();
				KingUI_Main.c1.show(KingUI_Main.contPanel, "4");
				if(KingdominoApplication.getKingdomino().getCurrentGame().getNumberOfPlayers() == 4) {
					String input = insertName.getText();
					KDController.provideUserProfile(input);
					String input2 = insertName2.getText();
					KDController.provideUserProfile(input2);
					String input3 = insertName3.getText();
					KDController.provideUserProfile(input3);
					String input4 = insertName4.getText();
					KDController.provideUserProfile(input4);
				}
				if(KingdominoApplication.getKingdomino().getCurrentGame().getNumberOfPlayers() == 3) {
					String input = insertName.getText();
					KDController.provideUserProfile(input);
					String input2 = insertName2.getText();
					KDController.provideUserProfile(input2);
					String input3 = insertName3.getText();
					KDController.provideUserProfile(input3);
				}
				if(KingdominoApplication.getKingdomino().getCurrentGame().getNumberOfPlayers() == 2) {
					String input = insertName.getText();
					KDController.provideUserProfile(input);
					String input2 = insertName2.getText();
					KDController.provideUserProfile(input2);
				}
			}
		});
		
		// Add
		//c.add(back);
		//c.add(done);
		//c.add(insertName);
		//c.add(name);
		//c.add(label);
		
		JPanel p2 = new JPanel();
		GroupLayout layout = new GroupLayout(p2);
		p2.setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		// I'm only doing this possibility since for now we only need 4 player mode
		if(KingdominoApplication.getKingdomino().getCurrentGame().getNumberOfPlayers() == 4) {
			
			layout.setHorizontalGroup(
					layout.createSequentialGroup()
					.addComponent(back)
					.addGap(400)
					.addGroup(layout.createSequentialGroup())
					.addGroup(layout.createSequentialGroup()
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
							.addComponent(name)
							.addComponent(label)
							.addGroup(layout.createSequentialGroup()
									.addComponent(insertName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE )
									.addGap(10)
									.addComponent(insertName2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE )
									.addGap(10)
									.addComponent(insertName3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE )
									.addGap(10)
									.addComponent(insertName4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE )
								)
							.addComponent(done)
							)
					)
			);
			
			layout.setVerticalGroup(
					layout.createParallelGroup()
					.addComponent(back)
					.addGroup(layout.createParallelGroup())
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
					.addGroup(layout.createSequentialGroup()
							.addGap(300)
							.addComponent(name)
							.addComponent(label)
							.addGroup(layout.createParallelGroup()
									.addComponent(insertName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE )
									.addGap(10)
									.addComponent(insertName2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE )
									.addGap(10)
									.addComponent(insertName3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE )
									.addGap(10)
									.addComponent(insertName4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE )
							)
							.addComponent(done)
							)
					)
			);
		
		}
		
		KingUI_Main.contPanel.add(p2, "3");
	}
}


