package ca.mcgill.ecse223.kingdomino.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.Border;

public class KingUI_Main extends JFrame{

	
	public static void main(String[] args) {
		
		new KingUI_Main().setVisible(true);
		
	}
	
	public KingUI_Main() {
		this.setSize(500,400);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setTitle("Kingdomino");
		initComponents();
	}

	private void initComponents() {
			
		JLabel title = new JLabel("Kingdomino");
		title.setFont(new Font("Times", Font.BOLD, 60));
		Border blackline = BorderFactory.createLineBorder(Color.black);
		title.setBorder(blackline);
		JButton start = new JButton("Start New Game");
		JButton load = new JButton("Load Game");
		
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.insets = new Insets(10, 10, 10, 10);
		
		JPanel p1 = new JPanel(new GridBagLayout());
		JPanel p2 = new JPanel(new GridBagLayout());
		
		p1.setBackground(Color.LIGHT_GRAY);
		p2.setBackground(Color.LIGHT_GRAY);
		
		
		p1.add(title);
		
		constraints.gridx = 0;
		constraints.gridy = 0;
		p2.add(start, constraints);
		constraints.gridx = 0;
		constraints.gridy = 1;
		p2.add(load, constraints);
		
		add(p1, BorderLayout.NORTH);
		add(p2);
		
		
		
		
		
		start.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				System.out.println("waddup");
			}
		});
		
		load.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				System.out.println("sup");
			}
		});
	}
	
	
}
