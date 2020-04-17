package ca.mcgill.ecse223.kingdomino.view;


import ca.mcgill.ecse223.kingdomino.model.*;
import ca.mcgill.ecse223.kingdomino.controller.*;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.util.*;
import javax.swing.border.Border;

import ca.mcgill.ecse223.kingdomino.controller.KDController;
import ca.mcgill.ecse223.kingdomino.controller.KDQuery;
import ca.mcgill.ecse223.kingdomino.model.Kingdomino;
public class AssignPlayer_Screen extends JFrame {
	
//public static void main(String[] args) {
//		
//		new AssignPlayer_Screen(Color).setVisible(true);
//
//	}
	
	public AssignPlayer_Screen(String ColorIn) {
		
		initComponents(ColorIn);	
	}
	
	public static void initComponents(String ColorIn) {
		
		//Kingdomino kd = KDController.loadGame(null);
		
		List<String> users = KDQuery.getAvailableUserNamesThisGame();
		JList list = new JList(users.toArray());
		list.setFont(new Font("Times", Font.BOLD, 16));
		
		JLabel name;
		JTextField insertName;
		JLabel label;
		JButton done;
		JButton back;
		Border border;
		
		back = new JButton("Back");
		border = BorderFactory.createLineBorder(Color.BLACK);
		name = new JLabel("Insert Username Below", SwingConstants.CENTER);
		name.setText("Select "+ ColorIn +" Player");
		name.setBorder(border);
		name.setFont(new Font("Times", Font.BOLD, 55));
		
		// text box
		insertName = new JTextField();
		insertName.setColumns(10);

		// create name
		done = new JButton("Select");
		done.setFont(new Font("Times", Font.BOLD, 16));
		
		JPanel p5 = new JPanel();
		GroupLayout layout = new GroupLayout(p5);
		p5.setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		KingUI_Main.contPanel.add(p5, "5");
			
			layout.setHorizontalGroup(
					layout.createSequentialGroup()
					.addComponent(back)
					.addGap(340)
					.addGroup(layout.createSequentialGroup())
					.addGroup(layout.createSequentialGroup()
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
							.addComponent(name)
							.addComponent(list)
							.addGroup(layout.createSequentialGroup()
									.addComponent(insertName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE )
									.addGap(10)
				
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
							.addGap(200)
							.addComponent(name)
							.addComponent(list)
							.addGroup(layout.createParallelGroup()
									.addComponent(insertName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE )
									.addGap(10)
									
							)
							.addComponent(done)
							)
					)
			);
			
			done.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
				try {	
					String input = insertName.getText();
					User user = KDController.getUserByName(input);
					Player player = KDQuery.getPlayerByColor(ColorIn);
					KDController.assignPlayerToUser(player, user);
					
					if(ColorIn.equalsIgnoreCase("blue")) {
						King_PlayerScreen.browse1.setEnabled(false);
						King_PlayerScreen.createNew1.setEnabled(false);
						King_PlayerScreen.nouser1.setEnabled(false);
						King_PlayerScreen.p1Complete = true;
						
					}
					if(ColorIn.equalsIgnoreCase("green")) {
						King_PlayerScreen.browse4.setEnabled(false);
						King_PlayerScreen.createNew4.setEnabled(false);
						King_PlayerScreen.nouser4.setEnabled(false);
						King_PlayerScreen.p4Complete = true;

					}
					if(ColorIn.equalsIgnoreCase("pink")) {
						King_PlayerScreen.browse2.setEnabled(false);
						King_PlayerScreen.createNew2.setEnabled(false);
						King_PlayerScreen.nouser2.setEnabled(false);
						King_PlayerScreen.p2Complete = true;
						
					}
					if(ColorIn.equalsIgnoreCase("yellow")) {
						King_PlayerScreen.browse3.setEnabled(false);
						King_PlayerScreen.createNew3.setEnabled(false);
						King_PlayerScreen.nouser3.setEnabled(false);
						King_PlayerScreen.p3Complete = true;
					}
					KingUI_Main.c1.show(KingUI_Main.contPanel, "3");
				}
				catch(Exception xx) {
					JOptionPane.showMessageDialog(null,xx.getMessage());
				}System.out.println(King_PlayerScreen.p1Complete + " "+ King_PlayerScreen.p2Complete +" "+King_PlayerScreen.p3Complete + " "+King_PlayerScreen.p4Complete);
				if(King_PlayerScreen.p1Complete && King_PlayerScreen.p2Complete && King_PlayerScreen.p3Complete && King_PlayerScreen.p4Complete) {
					King_PlayerScreen.done.setEnabled(true);
				
				}
				}
			});
				back.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						KingUI_Main.c1.show(KingUI_Main.contPanel, "3");
					
					}
		
			});
	
	
	}
}