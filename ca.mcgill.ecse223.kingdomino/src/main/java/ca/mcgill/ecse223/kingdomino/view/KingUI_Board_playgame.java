package ca.mcgill.ecse223.kingdomino.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagLayout;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class KingUI_Board_playgame extends JFrame {

	
public static void main(String[] args) {
		
		new KingUI_Board_playgame().setVisible(true);
		
	}
	
	public KingUI_Board_playgame() {
		this.setSize(1300,800);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setTitle("Kingdomino");
		initComponents();
	}

	private void initComponents() {
		
		JLabel territory = new JLabel("Territory Score");
		territory.setFont(new Font("Sherif", Font.BOLD, 40));
		
		JButton discard = new JButton("Discard Domino");
		JButton place = new JButton("Place Domino");
		JButton back = new JButton("Back");
		JButton move = new JButton("Move Domino");
		JButton rotate = new JButton("Rotate Domino");
		
		JPanel p1 = new JPanel(new GridBagLayout());
		p1.setBackground(Color.BLACK);
		JPanel p2 = new JPanel(new GridBagLayout());
		p2.setBackground(Color.GREEN);

		
		
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
		layout.setHorizontalGroup(
				layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup()
						.addComponent(territory)
						.addGroup(layout.createSequentialGroup()
								.addComponent(p1)
								.addComponent(p2)
						)
						
						.addGroup(layout.createSequentialGroup()
								.addComponent(rotate)
								.addComponent(move)
								.addComponent(back)
						)
						.addGroup(layout.createSequentialGroup()
								.addComponent(discard)
								.addComponent(place)
								)
						)


		);
		
		layout.setVerticalGroup(
				layout.createParallelGroup()
				.addGroup(layout.createSequentialGroup()
					.addComponent(territory)
					.addGroup(layout.createParallelGroup()
							.addComponent(p1)
							.addComponent(p2)
					)
					.addGroup(layout.createParallelGroup()
							.addComponent(rotate)
							.addComponent(move)
							.addComponent(back)
							)
					.addGroup(layout.createParallelGroup()
							.addComponent(discard)
							.addComponent(place)
							)
						)

				
				
		);
		
		rotate.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				System.out.println("rotating");
			}
		});
		
		move.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				System.out.println("moving");
			}
		});
		
		back.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				System.out.println("backing");
			}
		});
		
		discard.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				System.out.println("discarding");
			}
		});
		place.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				System.out.println("placing");
			}
		});
		
		
		
	}
}
