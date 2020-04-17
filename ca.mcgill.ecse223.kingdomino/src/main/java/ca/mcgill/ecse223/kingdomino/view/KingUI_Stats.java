package ca.mcgill.ecse223.kingdomino.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.model.User;

public class KingUI_Stats extends JFrame {
	
static int wins = 0, played=0, loss=0;
	 static double ratio= 0.0;
	
	 public KingUI_Stats(){
		
		initComponents();
	}
	 
	/**
	 * launches UI to show statistics of users in this Kingdomino
	 * 
	 * @author All team
	 * @param none
	 */
	public static void initComponents() {
		 JLabel stats;
		 Border border;
		 JButton back;
	
		List<User> users = KingdominoApplication.getKingdomino().getUsers();
		List<String> sList = new ArrayList<String>();
		for(User u: users) {
			wins = u.getWonGames();
			played = u.getPlayedGames();
			loss = played-wins;
			if(played==0) {
				ratio = 0;
			}
			else {
				ratio = wins/played;
			}
			DecimalFormat df = new DecimalFormat("#.##");
			String row = String.format("User: %1$-20s \t\t Won: %2$-15d \t\t Lost: %3$-15d \t\t Played: %4$-15d \t\t Ratio: %5$-5s \n",
										u.getName(), wins, loss, played, df.format(ratio));
			
			sList.add(row);
		}
		
		JList list = new JList(sList.toArray());
		list.setFont(new Font("Times", Font.PLAIN, 16));
		
		// back button
		back = new JButton("Back");
		back.addActionListener(new ActionListener() {
			/**
			 * button to return to previous UI
			 * 
			 * @author All team
			 * @param evt
			 */
			public void actionPerformed(ActionEvent e) {
				
				KingUI_Main.c1.show(KingUI_Main.contPanel, "1");
			}
		});
		// box that says stats
		border = BorderFactory.createLineBorder(Color.BLACK);
		stats = new JLabel("Insert Username Below", SwingConstants.CENTER);
		stats.setText("User Statistics");
		stats.setBorder(border);
		stats.setFont(new Font("Times", Font.BOLD, 50));
		
		
		JPanel p3 = new JPanel();
		GroupLayout layout = new GroupLayout(p3);
		p3.setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		KingUI_Main.contPanel.add(p3, "6");
		
		layout.setHorizontalGroup(
				layout.createSequentialGroup()
				.addComponent(back)
				.addGap(300)
				.addGroup(layout.createSequentialGroup())
				.addGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
						.addComponent(stats)
						.addComponent(list)
						.addGap(50)
						
						)
				)
		);
		
		layout.setVerticalGroup(
				layout.createParallelGroup()
				.addComponent(back)
				.addGroup(layout.createParallelGroup())
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
				.addGroup(layout.createSequentialGroup()
						.addGap(150)
						.addComponent(stats)
						.addGap(50)
						.addComponent(list)
						.addGap(50)
						)
				)
		);
	}
}