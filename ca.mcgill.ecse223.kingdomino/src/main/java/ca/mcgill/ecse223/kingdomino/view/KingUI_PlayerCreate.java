package ca.mcgill.ecse223.kingdomino.view;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.KDController;
import ca.mcgill.ecse223.kingdomino.controller.KDQuery;
import ca.mcgill.ecse223.kingdomino.model.Player;
import ca.mcgill.ecse223.kingdomino.model.Player.PlayerColor;
import ca.mcgill.ecse223.kingdomino.model.User;

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
	
	public KingUI_PlayerCreate(String color){
		
		initComponents(color);
		
	}
	public static void initComponents(String color) {
	
		// Swing components
		JLabel name;
		JTextField insertName;
		JLabel label;
		JButton done;
		JButton back;
		Border border;
		
		// back button
		back = new JButton("Back");
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				KingUI_Main.c1.show(KingUI_Main.contPanel, "3");
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
		label.setText("Insert Player Username");
		label.setFont(new Font("Times", Font.BOLD, 20));
		
		// text box
		insertName = new JTextField();
		insertName.setColumns(10);

		// create name
		done = new JButton("Create");
		done.setFont(new Font("Times", Font.BOLD, 16));
		done.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					String input = insertName.getText();
					try {
						KDController.provideUserProfile(input);
						User u = KDController.getUserByName(input);
						Player p = KDQuery.getPlayerByColor(color);
						KDController.assignPlayerToUser(p, u);
				
						if(color.equalsIgnoreCase("blue")) {
							King_PlayerScreen.browse1.setEnabled(false);
							King_PlayerScreen.createNew1.setEnabled(false);
							King_PlayerScreen.nouser1.setEnabled(false);
							King_PlayerScreen.p1Complete = true;


						}
						if(color.equalsIgnoreCase("green")) {
							King_PlayerScreen.browse4.setEnabled(false);
							King_PlayerScreen.createNew4.setEnabled(false);
							King_PlayerScreen.nouser4.setEnabled(false);
							King_PlayerScreen.p4Complete = true;


						}
						if(color.equalsIgnoreCase("pink")) {
							King_PlayerScreen.browse2.setEnabled(false);
							King_PlayerScreen.createNew2.setEnabled(false);
							King_PlayerScreen.nouser2.setEnabled(false);
							King_PlayerScreen.p2Complete = true;


						}
						if(color.equalsIgnoreCase("yellow")) {
							King_PlayerScreen.browse3.setEnabled(false);
							King_PlayerScreen.createNew3.setEnabled(false);
							King_PlayerScreen.nouser3.setEnabled(false);
							King_PlayerScreen.p3Complete = true;

						}
						KingUI_Main.c1.show(KingUI_Main.contPanel, "3");
					}catch(Exception ex) {
						JOptionPane.showMessageDialog(null,ex.getMessage());
					}if(King_PlayerScreen.p1Complete && King_PlayerScreen.p2Complete && King_PlayerScreen.p3Complete && King_PlayerScreen.p4Complete) {
						King_PlayerScreen.done.setEnabled(true);
						
					}
				}
		});
		
		JPanel p2 = new JPanel();
		GroupLayout layout = new GroupLayout(p2);
		p2.setLayout(layout);
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
							.addComponent(label)
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
							.addGap(300)
							.addComponent(name)
							.addComponent(label)
							.addGroup(layout.createParallelGroup()
									.addComponent(insertName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE )
									.addGap(10)
							)
							.addComponent(done)
							)
					)
			);
		KingUI_Main.contPanel.add(p2, "4");
	}
}