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

import ca.mcgill.ecse223.kingdomino.model.Domino;
import ca.mcgill.ecse223.kingdomino.model.Player;
import ca.mcgill.ecse223.kingdomino.model.TerrainType;

public class Rotation extends JFrame{

	public  JFrame frameR = new JFrame("Kingdomino");
	private HashMap<Integer, JPanel> coordinates = new HashMap<Integer, JPanel>();
	public JPanel currentLeft = new JPanel();
	public JPanel currentRight = new JPanel();
	public int LeftId = 0;
	public int RightId = 0;
	public String direction = "";
	public JPanel crowns1;
	public JPanel crowns2;
	public Domino domino;
	

	
	public Rotation(Player p1, int x, int y, Domino dom, String dir) {
		
		frameR.setSize(1350, 850);
		frameR.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		initComponents(p1,x,y,dom,dir);
	}

	private void initComponents(Player p1, int x, int y, Domino dom, String dir) {
		

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
		
		int z = x-(9*y)+41;
		domino = dom;
		direction = dir;
		
		if(dir.equals("right")) {
			LeftId = z;
			RightId = z+1;
		} else if(dir.equals("left")) {
			LeftId = z;
			RightId = z-1;
		} else if(dir.equals("up")) {
			LeftId = z;
			RightId = z-9;
		} else if(dir.equals("down")) {
			LeftId = z;
			RightId = z+9;
		}
		this.currentLeft = this.coordinates.get(LeftId);
		this.currentRight = this.coordinates.get(RightId);
		this.currentLeft.setBackground(getColor(domino.getLeftTile()));
		this.currentRight.setBackground(getColor(domino.getRightTile()));
		System.out.println(domino.getLeftCrown());
		System.out.println(domino.getRightCrown());
		addCrowns(this.currentLeft,domino.getLeftCrown());
		addCrowns(this.currentRight,domino.getRightCrown());
		
		this.coordinates.get(41).setBackground(Color.MAGENTA);
		
		frameR.add(display);
		
		moveR.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {

				if(LeftId==9 || RightId==9 || LeftId==18 || RightId==18 || LeftId==27 || RightId==27 || LeftId==36 || RightId==36 || LeftId==45 || RightId==45 || LeftId==54 || RightId==54 || LeftId==63 || RightId==63 || LeftId==72 || RightId==72 || LeftId==81 || RightId==81 || (RightId+1) ==41 || (LeftId+1) ==41) {

					currentLeft = coordinates.get(LeftId);
					currentRight = coordinates.get(RightId);
				}
				else {
					LeftId = LeftId + 1;
					RightId = RightId + 1;
					currentLeft.remove(crowns1);
					currentLeft.remove(crowns2);
					currentRight.remove(crowns1);
					currentRight.remove(crowns2);
					
					
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
						currentLeft.add(crowns1);
						currentLeft.add(crowns2);
						currentRight.add(crowns1);
						currentRight.add(crowns2);
				}
			}
		});
		
		moveL.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				
				if(LeftId==1 || RightId==1 || LeftId==10 || RightId==10 || LeftId==19 || RightId==19 || LeftId==28 || RightId==28 || LeftId==37 || RightId==37 || LeftId==46 || RightId==46 || LeftId==55 || RightId==55 || LeftId==64 || RightId==64 || LeftId==73 || RightId==73 || (LeftId-1)==41 || (RightId-1)==41) {
					currentLeft = coordinates.get(LeftId);
					currentRight = coordinates.get(RightId);
				}
				else {
					LeftId = LeftId - 1;
					RightId = RightId - 1;
					currentLeft.remove(crowns1);
					currentLeft.remove(crowns2);
					currentRight.remove(crowns1);
					currentRight.remove(crowns2);
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
					currentLeft.add(crowns1);
					currentLeft.add(crowns2);
					currentRight.add(crowns1);
					currentRight.add(crowns2);
				}
			}
		});
		
		moveU.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				
				if(LeftId==1 || RightId==1 || LeftId==2 || RightId==2 || LeftId==3 || RightId==3 || LeftId==4 || RightId==4 || LeftId==5 || RightId==5 || LeftId==6 || RightId==6 || LeftId==7 || RightId==7 || LeftId==8 || RightId==8 || LeftId==9 || RightId==9 ||(RightId-9)==41 || (LeftId-9)==41) {
					currentLeft = coordinates.get(LeftId);
					currentRight = coordinates.get(RightId);
				}
				else {
					LeftId = LeftId - 9;
					RightId = RightId - 9;
					currentLeft.remove(crowns1);
					currentLeft.remove(crowns2);
					currentRight.remove(crowns1);
					currentRight.remove(crowns2);
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
					currentLeft.add(crowns1);
					currentLeft.add(crowns2);
					currentRight.add(crowns1);
					currentRight.add(crowns2);
				}
			}
		});
		
		moveD.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				
				if(LeftId==73 || RightId==73 || LeftId==74 || RightId==74 || LeftId==75 || RightId==75 || LeftId==76 || RightId==76 || LeftId==77 || RightId==77 || LeftId==78 || RightId==78 || LeftId==79 || RightId==79 || LeftId==80 || RightId==80 || LeftId==81 || RightId==81 ||(RightId+9)==41 ||(LeftId+9)==41) {
					currentLeft = coordinates.get(LeftId);
					currentRight = coordinates.get(RightId);
				}
				else {
					LeftId = LeftId + 9;
					RightId = RightId + 9;
					currentLeft.remove(crowns1);
					currentLeft.remove(crowns2);
					currentRight.remove(crowns1);
					currentRight.remove(crowns2);
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
					currentLeft.add(crowns1);
					currentLeft.add(crowns2);
					currentRight.add(crowns1);
					currentRight.add(crowns2);
				}
			}
		});
		
		rotateR.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				
				currentLeft.remove(crowns1);
				currentLeft.remove(crowns2);
				currentRight.remove(crowns1);
				currentRight.remove(crowns2);
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
								currentLeft.add(crowns1);
								currentLeft.add(crowns2);
								currentRight.add(crowns1);
								currentRight.add(crowns2);
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
								 currentLeft.add(crowns1);
								 currentLeft.add(crowns2);
								 currentRight.add(crowns1);
								 currentRight.add(crowns2);
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
								currentLeft.add(crowns1);
								currentLeft.add(crowns2);
								currentRight.add(crowns1);
								currentRight.add(crowns2);
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
								currentLeft.add(crowns1);
								currentLeft.add(crowns2);
								currentRight.add(crowns1);
								currentRight.add(crowns2);
							}
				
			}
		});
		
		rotateL.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				currentLeft.remove(crowns1);
				currentLeft.remove(crowns2);
				currentRight.remove(crowns1);
				currentRight.remove(crowns2);
				
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
					currentLeft.add(crowns1);
					currentLeft.add(crowns2);
					currentRight.add(crowns1);
					currentRight.add(crowns2);
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
					currentLeft.add(crowns1);
					currentLeft.add(crowns2);
					currentRight.add(crowns1);
					currentRight.add(crowns2);
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
					currentLeft.add(crowns1);
					currentLeft.add(crowns2);
					currentRight.add(crowns1);
					currentRight.add(crowns2);
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
					currentLeft.add(crowns1);
					currentLeft.add(crowns2);
					currentRight.add(crowns1);
					currentRight.add(crowns2);
				}
			}
		});
		
		
		place.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				currentLeft = coordinates.get(LeftId);
				currentRight = coordinates.get(RightId);
				//initComponents();
			}
		});
		
		
			
		
		
	}
	
		private Color getColor(TerrainType t1) {
			Color c1 = null;
			if(t1.equals(TerrainType.Forest)) {
				c1 = new Color(0,102,0);
			} else if(t1.equals(TerrainType.Grass)) {
				c1 = Color.GREEN;
			} else if(t1.equals(TerrainType.Lake)) {
				c1 = Color.BLUE;
			} else if(t1.equals(TerrainType.Mountain)) {
				c1 = new Color(51,0,0);
			} else if(t1.equals(TerrainType.Swamp)) {
				c1 = new Color(153,102,0);
			} else if (t1.equals(TerrainType.WheatField)) {
				c1 = new Color(255,204,0);
			}
			return c1;
		}
		
		private void addCrowns(JPanel p1, int n1) {
			crowns1 = new JPanel();
			crowns1.setBackground(new Color(255,255,0));
			crowns2 = new JPanel();
			crowns2.setBackground(new Color(255,255,0));
			if(n1==1) {
				p1.add(crowns1);
			} else if(n1==2) {
				p1.add(crowns1);
				p1.add(crowns2);
			}
		}
}
