package ca.mcgill.ecse223.kingdomino.playerui;


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

import javax.swing.GroupLayout;
public class AssignPlayer_Screen extends JFrame {
	
//public static void main(String[] args) {
//		
//		new AssignPlayer_Screen(Color).setVisible(true);
//
//	}
	
	public AssignPlayer_Screen(String ColorIn) {
		
		initComponents(ColorIn);
		this.setVisible(true);
	
		}
	
	public void initComponents(String ColorIn) {
		
		//Kingdomino kd = KDController.loadGame(null);
		
		this.setSize(500,400);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setTitle("Kingdomino");
		
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
		
		
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);


			
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
					
					String input = insertName.getText();
					User user = KDController.getUserByName(input);
					Player player = KDQuery.getPlayerByColor(ColorIn);
					KDController.assignPlayerToUser(player, user);
					
				
				}
		
			});
	
	
	}
}
	
