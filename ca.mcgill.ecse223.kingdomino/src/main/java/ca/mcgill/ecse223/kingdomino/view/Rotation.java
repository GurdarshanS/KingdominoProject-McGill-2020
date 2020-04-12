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
import java.util.Iterator;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.Border;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.KDController;
import ca.mcgill.ecse223.kingdomino.model.Castle;
import ca.mcgill.ecse223.kingdomino.model.Domino;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom.DirectionKind;
import ca.mcgill.ecse223.kingdomino.model.Kingdom;
import ca.mcgill.ecse223.kingdomino.model.KingdomTerritory;
import ca.mcgill.ecse223.kingdomino.model.Kingdomino;
import ca.mcgill.ecse223.kingdomino.model.Player;
import ca.mcgill.ecse223.kingdomino.model.Property;
import ca.mcgill.ecse223.kingdomino.model.TerrainType;

public class Rotation extends JFrame{

	public  JFrame frameR = new JFrame("Kingdomino");
	private HashMap<Integer, JPanel> coordinates = new HashMap<Integer, JPanel>();
	private JPanel currentLeft = new JPanel();
	private JPanel currentRight = new JPanel();
	private int LeftId = 0;
	private int RightId = 0;
	private JPanel crowns1;
	private JPanel crowns2;
	private JPanel crowns3;
	JButton rotateR = new JButton("Rotate Clockwise");
	JButton rotateL = new JButton("Rotate CounterClockwise");
	JButton moveR = new JButton("Move Right");
	JButton moveL = new JButton("Move Left");
	JButton moveU = new JButton("Move Up");
	JButton moveD = new JButton("Move Down");
	JButton place = new JButton("Place Domino");
	JButton discard = new JButton("Discard Domino");
	Color panelPrevColorLeft;
	Color panelPrevColorRight;
	

	
	public Rotation(Player p1, int x, int y, DominoInKingdom dom) {
		
		frameR.setSize(1350, 850);
		frameR.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		drawInitialKingdom(p1);
		drawNewDomino(p1,x,y,dom);
	}

	private void drawInitialKingdom(Player p1) {

		
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
		buttons.add(discard);


		display.add(board);
		display.add(buttons, BorderLayout.SOUTH);
		frameR.add(display);
		
			for(KingdomTerritory dInK : p1.getKingdom().getTerritories()) {
			
			if(dInK instanceof Castle) continue;
			
			DominoInKingdom domino = (DominoInKingdom) dInK;
			
			if(domino.equals((DominoInKingdom)(p1.getKingdom().getTerritory(p1.getKingdom().getTerritories().size()-1)))) break;
			
			draw(domino, domino.getDirection());
			
		}
		
		
		
		this.coordinates.get(41).setBackground(Color.MAGENTA);
		
	}	
		
	private int translate(int x, int y) {
			
			return x-(9*y)+41;
			
		}
	
	private void draw(DominoInKingdom domino, DirectionKind dir) {
			
			int squareToDraw = translate(domino.getX(), domino.getY());
			
			if(dir.equals(DirectionKind.Right)) {
				LeftId = squareToDraw;
				RightId = squareToDraw+1;
			} else if(dir.equals(DirectionKind.Left)) {
				LeftId = squareToDraw;
				RightId = squareToDraw-1;
			} else if(dir.equals(DirectionKind.Up)) {
				LeftId = squareToDraw;
				RightId = squareToDraw-9;
			} else if(dir.equals(DirectionKind.Down)) {
				LeftId = squareToDraw;
				RightId = squareToDraw+9;
			}
			
			this.currentLeft = this.coordinates.get(LeftId);
			this.currentRight = this.coordinates.get(RightId);
			
			panelPrevColorLeft = this.currentLeft.getBackground();
			panelPrevColorRight = this.currentRight.getBackground();
		
			this.currentLeft.setBackground(getColor(domino.getDomino().getLeftTile()));
			this.currentRight.setBackground(getColor(domino.getDomino().getRightTile()));
			addCrowns(this.currentLeft,domino.getDomino().getLeftCrown());
			addCrowns(this.currentRight,domino.getDomino().getRightCrown());
			
		}
	
		
	private void undraw(DominoInKingdom domino, DirectionKind dir) {
			
			int squareToDraw = translate(domino.getX(), domino.getY());
			
			if(dir.equals(DirectionKind.Right)) {
				LeftId = squareToDraw;
				RightId = squareToDraw+1;
			} else if(dir.equals(DirectionKind.Left)) {
				LeftId = squareToDraw;
				RightId = squareToDraw-1;
			} else if(dir.equals(DirectionKind.Up)) {
				LeftId = squareToDraw;
				RightId = squareToDraw-9;
			} else if(dir.equals(DirectionKind.Down)) {
				LeftId = squareToDraw;
				RightId = squareToDraw+9;
			}
			
			this.currentLeft = this.coordinates.get(LeftId);
			this.currentRight = this.coordinates.get(RightId);
			this.currentLeft.setBackground(panelPrevColorLeft);
			this.currentRight.setBackground(panelPrevColorRight);
			
		}
		
	private void drawNewDomino(Player p1, int x, int y, DominoInKingdom domino) {

		draw(domino, domino.getDirection());
		
		moveR.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				
				undraw(domino,  domino.getDirection());
				KDController.moveSM("right");
				System.out.println(KingdominoApplication.getKingdomino().getStateMachine().getGamestatusFullName());
				//KDController.moveLatestDomino(p1, "right");
				draw(domino,  domino.getDirection());
				
			}
		});
		
		moveL.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				
				undraw(domino,  domino.getDirection());
				KDController.moveSM("left");
				System.out.println(KingdominoApplication.getKingdomino().getStateMachine().getGamestatusFullName());
				//KDController.moveLatestDomino(p1, "left");
				draw(domino,  domino.getDirection());
				
			}
		});
		
		moveU.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				
				undraw(domino,  domino.getDirection());
				KDController.moveSM("up");
				System.out.println(KingdominoApplication.getKingdomino().getStateMachine().getGamestatusFullName());
				//KDController.moveLatestDomino(p1, "up");
				draw(domino,  domino.getDirection());
				
			}
		});
		
		moveD.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				
				undraw(domino,  domino.getDirection());
				KDController.moveSM("down");
				System.out.println(KingdominoApplication.getKingdomino().getStateMachine().getGamestatusFullName());
				//KDController.moveLatestDomino(p1, "down");
				draw(domino,  domino.getDirection());
				
			}
		});
		
		rotateR.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
			
				undraw(domino,  domino.getDirection());
				KDController.rotateSM("clockwise");
				System.out.println(KingdominoApplication.getKingdomino().getStateMachine().getGamestatusFullName());
				//KDController.rotateLatestDomino(p1, "Clockwise");
				draw(domino,  domino.getDirection());
				
			}
		});
		
		rotateL.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				
				undraw(domino,  domino.getDirection());
				KDController.rotateSM("counterclockwise");
				System.out.println(KingdominoApplication.getKingdomino().getStateMachine().getGamestatusFullName());
				//KDController.rotateLatestDomino(p1, "CounterClockwise");
				draw(domino,  domino.getDirection());
				
			}
		});
		
		
		place.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				boolean placed = KDController.placeSM();
				System.out.println(placed);
			}
		});
		
		discard.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				boolean discarded = KDController.discardSM();
				System.out.println(discarded);
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
			crowns3 = new JPanel();
			crowns3.setBackground(new Color(255,255,0));
			if(n1==1) {
				p1.add(crowns1);
			} else if(n1==2) {
				p1.add(crowns1);
				p1.add(crowns2);
			} else if(n1==3) {
				p1.add(crowns1);
				p1.add(crowns2);
				p1.add(crowns3);
			}
		}
		
	

				
			
		
}
		
