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
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

public class EndGame extends JFrame{
	
	 private JPanel jContentPane = null;

	
	public static void main(String[] args) {
		
		new EndGame().setVisible(true);

	}
	
	public EndGame() {
		
		initComponents();
	}
	
	public void initComponents() {
		
		this.setSize(500,400);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setTitle("Kingdomino");
		this.setContentPane(getJContentPane());
	        
		
	
		
//		GroupLayout layout = new GroupLayout(getContentPane());
//		getContentPane().setLayout(layout);
//		layout.setAutoCreateGaps(true);
//		layout.setAutoCreateContainerGaps(true);
//		layout.setHorizontalGroup(
//				layout.createSequentialGroup()
//				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
//						.addComponent(player)
//						.addComponent(winner)
//						)
//				);
//		
//		layout.setVerticalGroup(
//				layout.createParallelGroup(GroupLayout.Alignment.CENTER)
//				.addGroup(layout.createSequentialGroup()
//					.addComponent(player)
//					.addComponent(winner)
//					)
//				);
//						
//	}

	}	
	
	 private JPanel getJContentPane() {
	        if (jContentPane == null) {
	            jContentPane = new JPanel();
	            jContentPane.setLayout(null);
	            JPanel panel = new JPanel();
	            panel.setBounds((this.getWidth()/2)-60, (this.getHeight()/2)-30, 120, 35);
	            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
	            jContentPane.add(panel);
	            panel.setBackground(Color.WHITE);

	            JLabel winner = new JLabel("WINNER!");
	    		JLabel player = new JLabel("player goes here");
	    		
	    		panel.add(winner);
	    		panel.add(player);
	        }
	        return jContentPane;
	    }
}
