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
	public String direction = "";
	
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
		JButton place = new JButton("Place Domino");
		
		Border blackline = BorderFactory.createLineBorder(Color.black);

		JPanel board = new JPanel(new GridLayout(9,9));
		JPanel display = new JPanel(new BorderLayout());
		JPanel buttons = new JPanel();
		
		JPanel[] panels = new JPanel[81];
		for(int i=0; i<81; i++) {
			panels[i] = new JPanel();
			panels[i].setBackground(Color.WHITE);
			panels[i].setBorder(blackline);
			board.add(panels[i]);
		}
		
		for(int i=1; i<=81; i++) {
			this.coordinates.put(i, panels[i-1]);
		}
		

		
		
		buttons.add(rotateR);
		buttons.add(rotateL);
		buttons.add(moveL);
		buttons.add(moveR);
		buttons.add(moveU);
		buttons.add(moveD);
		buttons.add(place);


		display.add(board);
		display.add(buttons, BorderLayout.SOUTH);

		this.currentLeft = this.coordinates.get(1);
		this.currentRight = this.coordinates.get(2);
		this.currentLeft.setBackground(Color.BLUE);
		this.currentRight.setBackground(Color.GREEN);
		LeftId = 1;
		RightId = 2;
		direction = "right";
		this.coordinates.get(41).setBackground(Color.MAGENTA);
		
		frameR.add(display);
		
		moveR.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {

				if(LeftId==9 || RightId==9 || LeftId==18 || RightId==18 || LeftId==27 || RightId==27 || LeftId==36 || RightId==36 || LeftId==45 || RightId==45 || LeftId==54 || RightId==54 || LeftId==63 || RightId==63 || LeftId==72 || RightId==72 || LeftId==81 || RightId==81 || (RightId+1) ==41) {

					currentLeft = coordinates.get(LeftId);
					currentRight = coordinates.get(RightId);
				}
				else {
					LeftId = LeftId + 1;
					RightId = RightId + 1;
					
					if(direction.equals("right")){
						Color templeft = currentLeft.getBackground(); 
						Color tempright = currentRight.getBackground();
						currentLeft.setBackground(Color.WHITE);	
						currentLeft = coordinates.get(LeftId);
						currentLeft.setBackground(templeft);
						currentRight = coordinates.get(RightId);
						currentRight.setBackground(tempright);
					}
					else if(direction.equals("left")) {
						Color templeft = currentLeft.getBackground(); 
						Color tempright = currentRight.getBackground();
						currentRight.setBackground(Color.WHITE);
						currentLeft = coordinates.get(LeftId);
						currentLeft.setBackground(templeft);
						currentRight = coordinates.get(RightId);
						currentRight.setBackground(tempright);
						
					}
					else if (direction.equals("up") || direction.equals("down")){
						Color templeft = currentLeft.getBackground(); 
						Color tempright = currentRight.getBackground();
						currentRight.setBackground(Color.WHITE);
						currentLeft.setBackground(Color.WHITE);
						currentLeft = coordinates.get(LeftId);
						currentLeft.setBackground(templeft);
						currentRight = coordinates.get(RightId);
						currentRight.setBackground(tempright);
						
					} 
				}
			}
		});
		
		moveL.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				
				if(LeftId==1 || RightId==1 || LeftId==10 || RightId==10 || LeftId==19 || RightId==19 || LeftId==28 || RightId==28 || LeftId==37 || RightId==37 || LeftId==46 || RightId==46 || LeftId==55 || RightId==55 || LeftId==64 || RightId==64 || LeftId==73 || RightId==73 ||(RightId-1)==41) {
					currentLeft = coordinates.get(LeftId);
					currentRight = coordinates.get(RightId);
				}
				else {
					LeftId = LeftId - 1;
					RightId = RightId - 1;
					if(direction.equals("right")) {
						Color templeft = currentLeft.getBackground(); 
						Color tempright = currentRight.getBackground();
						currentRight.setBackground(Color.WHITE);	
						currentLeft = coordinates.get(LeftId);
						currentLeft.setBackground(templeft);
						currentRight = coordinates.get(RightId);
						currentRight.setBackground(tempright);
					}
					else if(direction.equals("left")) {
						Color templeft = currentLeft.getBackground(); 
						Color tempright = currentRight.getBackground();
						currentLeft.setBackground(Color.WHITE);	
						currentLeft = coordinates.get(LeftId);
						currentLeft.setBackground(templeft);
						currentRight = coordinates.get(RightId);
						currentRight.setBackground(tempright);
					}
					else if(direction.equals("up") || direction.equals("down")) {
						Color templeft = currentLeft.getBackground(); 
						Color tempright = currentRight.getBackground();
						currentRight.setBackground(Color.WHITE);
						currentLeft.setBackground(Color.WHITE);
						currentLeft = coordinates.get(LeftId);
						currentLeft.setBackground(templeft);
						currentRight = coordinates.get(RightId);
						currentRight.setBackground(tempright);
					}
				}
			}
		});
		
		moveU.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				
				if(LeftId==1 || RightId==1 || LeftId==2 || RightId==2 || LeftId==3 || RightId==3 || LeftId==4 || RightId==4 || LeftId==5 || RightId==5 || LeftId==6 || RightId==6 || LeftId==7 || RightId==7 || LeftId==8 || RightId==8 || LeftId==9 || RightId==9 ||(RightId-9)==41) {
					currentLeft = coordinates.get(LeftId);
					currentRight = coordinates.get(RightId);
				}
				else {
					LeftId = LeftId - 9;
					RightId = RightId - 9;
					if(direction.equals("right") || direction.equals("left")) {
						Color templeft = currentLeft.getBackground(); 
						Color tempright = currentRight.getBackground();
						currentLeft.setBackground(Color.WHITE);	
						currentRight.setBackground(Color.WHITE);	
						currentLeft = coordinates.get(LeftId);
						currentLeft.setBackground(templeft);
						currentRight = coordinates.get(RightId);
						currentRight.setBackground(tempright);
					
					}
					else if(direction.equals("up")) {
						Color templeft = currentLeft.getBackground(); 
						Color tempright = currentRight.getBackground();	
						currentLeft.setBackground(Color.WHITE);	
						currentLeft = coordinates.get(LeftId);
						currentLeft.setBackground(templeft);
						currentRight = coordinates.get(RightId);
						currentRight.setBackground(tempright);
					}
					else if(direction.equals("down")) {
						Color templeft = currentLeft.getBackground(); 
						Color tempright = currentRight.getBackground();
						currentRight.setBackground(Color.WHITE);	
						currentLeft = coordinates.get(LeftId);
						currentLeft.setBackground(templeft);
						currentRight = coordinates.get(RightId);
						currentRight.setBackground(tempright);
					}
				}
			}
		});
		
		moveD.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				
				if(LeftId==73 || RightId==73 || LeftId==74 || RightId==74 || LeftId==75 || RightId==75 || LeftId==76 || RightId==76 || LeftId==77 || RightId==77 || LeftId==78 || RightId==78 || LeftId==79 || RightId==79 || LeftId==80 || RightId==80 || LeftId==81 || RightId==81 ||(RightId+9)==41) {
					currentLeft = coordinates.get(LeftId);
					currentRight = coordinates.get(RightId);
				}
				else {
					LeftId = LeftId + 9;
					RightId = RightId + 9;
					if(direction.equals("right") || direction.equals("left")) {
					Color templeft = currentLeft.getBackground(); 
					Color tempright = currentRight.getBackground();
					currentLeft.setBackground(Color.WHITE);	
					currentRight.setBackground(Color.WHITE);	
					currentLeft = coordinates.get(LeftId);
					currentLeft.setBackground(templeft);
					currentRight = coordinates.get(RightId);
					currentRight.setBackground(tempright);
					
					}
					else if(direction.equals("up")) {
						Color templeft = currentLeft.getBackground(); 
						Color tempright = currentRight.getBackground();	
						currentRight.setBackground(Color.WHITE);	
						currentLeft = coordinates.get(LeftId);
						currentLeft.setBackground(templeft);
						currentRight = coordinates.get(RightId);
						currentRight.setBackground(tempright);
					}
					else if(direction.equals("down")) {
						Color templeft = currentLeft.getBackground(); 
						Color tempright = currentRight.getBackground();
						currentLeft.setBackground(Color.WHITE);	
						currentLeft = coordinates.get(LeftId);
						currentLeft.setBackground(templeft);
						currentRight = coordinates.get(RightId);
						currentRight.setBackground(tempright);
					}
				}
			}
		});
		
		rotateR.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				
				
							if(direction.equals("right")) {
								
								if(RightId ==74 || RightId==75 || RightId==76 || RightId==77 || RightId ==78 || RightId==79 || RightId==80 || RightId==81 || (RightId+8)==41 ){
									currentLeft = coordinates.get(LeftId);
									currentRight = coordinates.get(RightId);
								} else {
										Color tempright = currentRight.getBackground();
										currentRight.setBackground(Color.WHITE);
										RightId = RightId+8;
										currentRight = coordinates.get(RightId);
										currentRight.setBackground(tempright);
										direction = "down";
										}
							}
						
				
							else if(direction.equals("down")) {
								 
								 if(RightId ==10 || RightId==19 || RightId==28 || RightId==37 || RightId ==46 || RightId==55 || RightId==64 || RightId==73 || (RightId-10)==41 ){
										currentLeft = coordinates.get(LeftId);
										currentRight = coordinates.get(RightId);
								 } else {
											Color tempright = currentRight.getBackground();
											currentRight.setBackground(Color.WHITE);
											RightId = RightId-10;
											currentRight = coordinates.get(RightId);
											currentRight.setBackground(tempright);
											direction = "left";
											
										}
							 }
						
							else if(direction.equals("left")){
								
								if(RightId ==1 || RightId==2 || RightId==3 || RightId==4 || RightId ==5 || RightId==6 || RightId==7 || RightId==8 || (RightId-8)==41 ) {
									currentLeft = coordinates.get(LeftId);
									currentRight = coordinates.get(RightId);
								} else {
										Color tempright = currentRight.getBackground();
										currentRight.setBackground(Color.WHITE);
										RightId = RightId-8;
										currentRight = coordinates.get(RightId);
										currentRight.setBackground(tempright);
										direction = "up";
										}
							}
				
							else if(direction.equals("up")) {
								
								if(RightId ==9 || RightId==18 || RightId==27 || RightId==36 || RightId ==45 || RightId==54 || RightId==63 || RightId==72 || (RightId+10)==41 ) {
									currentLeft = coordinates.get(LeftId);
									currentRight = coordinates.get(RightId);
								} else {	
										Color tempright = currentRight.getBackground();
										currentRight.setBackground(Color.WHITE);
										RightId = RightId+10;
										currentRight = coordinates.get(RightId);
										currentRight.setBackground(tempright);
										direction = "right";
										}
							}
				
			}
		});
		
		rotateL.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				
				if(direction.equals("right")) {
					
					if(RightId ==2 || RightId==3 || RightId==4 || RightId==5 || RightId ==6 || RightId==7 || RightId==8 || RightId==9 || (RightId-10)==41 ) {
						currentLeft = coordinates.get(LeftId);
						currentRight = coordinates.get(RightId);
					} 		else {
									Color tempright = currentRight.getBackground();
									currentRight.setBackground(Color.WHITE);
									RightId = RightId-10;
									currentRight = coordinates.get(RightId);
									currentRight.setBackground(tempright);
									direction = "up";
								}
				}
				else if(direction.equals("up")) {
					
					if(RightId ==1 || RightId==10 || RightId==19 || RightId==28 || RightId ==37 || RightId==46 || RightId==55 || RightId==64 || (RightId+8)==41 ) {
						currentLeft = coordinates.get(LeftId);
						currentRight = coordinates.get(RightId);
					} 		else {
									Color tempright = currentRight.getBackground();
									currentRight.setBackground(Color.WHITE);
									RightId = RightId+8;
									currentRight = coordinates.get(RightId);
									currentRight.setBackground(tempright);
									direction = "left";
								}
				}
				else if(direction.equals("left")){
					
					if(RightId ==73 || RightId==74 || RightId==75 || RightId==76 || RightId ==77 || RightId==78 || RightId==79 || RightId==80 || (RightId+10)==41 ) {
						currentLeft = coordinates.get(LeftId);
						currentRight = coordinates.get(RightId);
					} 		else {
									Color tempright = currentRight.getBackground();
									currentRight.setBackground(Color.WHITE);
									RightId = RightId+10;
									currentRight = coordinates.get(RightId);
									currentRight.setBackground(tempright);
									direction = "down";
								}
				}
				else if(direction.equals("down")) {
					
					if(RightId ==18 || RightId==27 || RightId==36 || RightId==45 || RightId ==54 || RightId==63 || RightId==72 || RightId==81 || (RightId-8)==41 ) {
						currentLeft = coordinates.get(LeftId);
						currentRight = coordinates.get(RightId);
					} 		else {
									Color tempright = currentRight.getBackground();
									currentRight.setBackground(Color.WHITE);
									RightId = RightId-8;
									currentRight = coordinates.get(RightId);
									currentRight.setBackground(tempright);
									direction = "right";
								}
				}
			}
		});
		
		
		place.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				currentLeft = coordinates.get(LeftId);
				currentRight = coordinates.get(RightId);
				initComponents();
			}
		});
		
		
		
	}
	
	
		
}
