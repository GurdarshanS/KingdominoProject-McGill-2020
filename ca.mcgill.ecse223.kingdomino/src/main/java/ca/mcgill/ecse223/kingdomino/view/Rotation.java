package ca.mcgill.ecse223.kingdomino.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.Border;

public class Rotation extends JFrame{

	public  JFrame frameR = new JFrame("Kingdomino");
	private HashMap<Integer, JPanel> coordinates = new HashMap<Integer, JPanel>();
	public JPanel currentLeft = new JPanel();
	public JPanel currentRight = new JPanel();
	public int LeftId = 0;
	public int RightId = 0;
	
public static void main(String[] args) {
		
		new Rotation().frameR.setVisible(true);
		
	}
	
	public Rotation() {
		
		frameR.setSize(1350, 850);
		frameR.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		initComponents();
	}

	private void initComponents() {
		

		JButton rotateR = new JButton("Rotate Clockwise");
		JButton rotateL = new JButton("Rotate CounterClockwise");
		JButton moveR = new JButton("Move Right");
		JButton moveL = new JButton("Move Left");
		JButton moveU = new JButton("Move Up");
		JButton moveD = new JButton("Move Down");
		
		Border blackline = BorderFactory.createLineBorder(Color.black);

		JPanel board = new JPanel(new GridLayout(5,5));
		JPanel display = new JPanel(new BorderLayout());
		JPanel buttons = new JPanel();
		
		JPanel[] panels = new JPanel[25];
		for(int i=0; i<25; i++) {
			panels[i] = new JPanel();
			panels[i].setBackground(Color.WHITE);
			panels[i].setBorder(blackline);
			board.add(panels[i]);
		}
		

		this.coordinates.put(1, panels[0]);
		this.coordinates.put(2, panels[1]);
		this.coordinates.put(3, panels[2]);
		this.coordinates.put(4, panels[3]);
		this.coordinates.put(5, panels[4]);             //////
		this.coordinates.put(6, panels[5]);
		this.coordinates.put(7, panels[6]);
		this.coordinates.put(8, panels[7]);
		this.coordinates.put(9, panels[8]);
		this.coordinates.put(10, panels[9]);			////
		this.coordinates.put(11, panels[10]);
		this.coordinates.put(12, panels[11]);
		this.coordinates.put(13, panels[12]);
		this.coordinates.put(14, panels[13]);
		this.coordinates.put(15, panels[14]);			//////
		this.coordinates.put(16, panels[15]);
		this.coordinates.put(17, panels[16]);
		this.coordinates.put(18, panels[17]);
		this.coordinates.put(19, panels[18]);
		this.coordinates.put(20, panels[19]);		/////
		this.coordinates.put(21, panels[20]);
		this.coordinates.put(22, panels[21]);
		this.coordinates.put(23, panels[22]);
		this.coordinates.put(24, panels[23]);
		this.coordinates.put(25, panels[24]);

		
		
		buttons.add(rotateR);
		buttons.add(rotateL);
		buttons.add(moveL);
		buttons.add(moveR);
		buttons.add(moveU);
		buttons.add(moveD);


		display.add(board);
		display.add(buttons, BorderLayout.SOUTH);

		this.currentLeft = this.coordinates.get(1);
		this.currentRight = this.coordinates.get(2);
		this.currentLeft.setBackground(Color.BLUE);
		this.currentRight.setBackground(Color.GREEN);
		LeftId = 1;
		RightId = 2;
		
		
		frameR.add(display);
		
		moveR.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				Color templeft = currentLeft.getBackground(); 
				Color tempright = currentRight.getBackground();
				currentLeft.setBackground(Color.WHITE);	
				LeftId = LeftId + 1;
				RightId = RightId + 1;
				currentLeft = coordinates.get(LeftId);
				currentLeft.setBackground(templeft);
				currentRight = coordinates.get(RightId);
				currentRight.setBackground(tempright);
				
				
			}
		});
		
		moveL.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				Color templeft = currentLeft.getBackground(); 
				Color tempright = currentRight.getBackground();
				currentRight.setBackground(Color.WHITE);	
				LeftId = LeftId - 1;
				RightId = RightId - 1;
				currentLeft = coordinates.get(LeftId);
				currentLeft.setBackground(templeft);
				currentRight = coordinates.get(RightId);
				currentRight.setBackground(tempright);
			}
		});
		
		moveU.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				Color templeft = currentLeft.getBackground(); 
				Color tempright = currentRight.getBackground();
				currentLeft.setBackground(Color.WHITE);	
				currentRight.setBackground(Color.WHITE);	
				LeftId = LeftId - 5;
				RightId = RightId - 5;
				currentLeft = coordinates.get(LeftId);
				currentLeft.setBackground(templeft);
				currentRight = coordinates.get(RightId);
				currentRight.setBackground(tempright);
				
				
			}
		});
		
		moveD.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				Color templeft = currentLeft.getBackground(); 
				Color tempright = currentRight.getBackground();
				currentRight.setBackground(Color.WHITE);
				currentLeft.setBackground(Color.WHITE);	
				LeftId = LeftId + 5;
				RightId = RightId + 5;
				currentLeft = coordinates.get(LeftId);
				currentLeft.setBackground(templeft);
				currentRight = coordinates.get(RightId);
				currentRight.setBackground(tempright);
			}
		});
		
		rotateR.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				
					
				
			}
		});
		
		rotateL.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				System.out.println("sup");
			}
		});
		
		
		
	}
	
	
		
}
