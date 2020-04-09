package ca.mcgill.ecse223.kingdomino.view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.Border;

public class KingUI_Main {
	
	public static JFrame frame = new JFrame("Kingdomino");
	public static JPanel contPanel = new JPanel();
	public static CardLayout c1 = new CardLayout();
	
	public static void main(String[] args) {
		
		new KingUI_Main().frame.setVisible(true);
		
	}
	
	public KingUI_Main() {
		initComponents();
	}

	private void initComponents() {
			
		frame.setSize(1350, 850);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		JLabel title = new JLabel("Kingdomino");
		title.setFont(new Font("Times", Font.BOLD, 60));
		Border blackline = BorderFactory.createLineBorder(Color.black);
		title.setBorder(blackline);
		JButton start = new JButton("Start New Game");
		JButton load = new JButton("Load Game");

		
		JPanel p1 = new JPanel();
		JPanel p2 = new JPanel(new GridBagLayout());
		JPanel p3 = new JPanel(new BorderLayout());
		
		
		p1.setBackground(Color.LIGHT_GRAY);
		p2.setBackground(Color.LIGHT_GRAY);
		
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.insets = new Insets(10, 10, 10, 10);
		
		p1.add(title);
		
		constraints.gridx = 0;
		constraints.gridy = 0;
		p2.add(start, constraints);
		constraints.gridx = 0;
		constraints.gridy = 1;
		p2.add(load, constraints);
		
		p3.add(p1, BorderLayout.NORTH);
		p3.add(p2);

		contPanel.setLayout(c1);
		contPanel.add(p3, "1");
		c1.show(contPanel, "1");
		frame.add(contPanel);
		
		
		start.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				KingUI_Settings.initSettings();
				c1.show(contPanel, "2");
				
			}
		});
		
		load.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				System.out.println("sup");
			}
		});
	}
	
	
}

