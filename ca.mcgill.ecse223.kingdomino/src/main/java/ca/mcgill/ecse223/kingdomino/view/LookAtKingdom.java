package ca.mcgill.ecse223.kingdomino.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.Border;

import ca.mcgill.ecse223.kingdomino.model.Castle;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom;
import ca.mcgill.ecse223.kingdomino.model.KingdomTerritory;
import ca.mcgill.ecse223.kingdomino.model.Player;
import ca.mcgill.ecse223.kingdomino.model.TerrainType;
import ca.mcgill.ecse223.kingdomino.model.Domino.DominoStatus;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom.DirectionKind;
import ca.mcgill.ecse223.kingdomino.model.Player.PlayerColor;

public class LookAtKingdom extends JFrame{
	
	public  JFrame frameR = new JFrame("Kingdomino");
	private HashMap<Integer, JPanel> coordinates = new HashMap<Integer, JPanel>();
	private JPanel currentLeft = new JPanel();
	private JPanel currentRight = new JPanel();
	private int LeftId = 0;
	private int RightId = 0;
	
	JPanel score = new JPanel();
	
	JLabel green = new JLabel("Player GREEN");
	JLabel blue = new JLabel("Player BLUE");
	JLabel yellow = new JLabel("Player YELLOW");
	JLabel pink = new JLabel("Player PINK");
	
	/**
	 * launches UI to show player's kingdom after game over
	 * 
	 * @author All team
	 * @param player
	 */
	
	public LookAtKingdom(Player player) {
		
		frameR.setSize(1350, 850);
		
		drawKingdom(player);
		
	}
	
	/**
	 * draws a player's Kingdom based on his/her dominoes
	 * 
	 * @author All team
	 * @param player
	 */
	
	private void drawKingdom(Player player) {
		
		Border blackline = BorderFactory.createLineBorder(Color.DARK_GRAY);

		JPanel board = new JPanel(new GridLayout(9,9));	
		JPanel display = new JPanel(new BorderLayout());
		
		JPanel[] panels = new JPanel[81];
		for(int i=0; i<81; i++) {
			
			panels[i] = new JPanel();
			panels[i].setBackground(Color.WHITE);
			panels[i].setBorder(blackline);
			board.add(panels[i]);
			
		}
		
		for(int i=1; i<=81; i++) {
			coordinates.put(i, panels[i-1]);
		}
		
		for(KingdomTerritory dInK : player.getKingdom().getTerritories()) {
			
			if(dInK instanceof Castle) continue;
			
			DominoInKingdom domino = (DominoInKingdom) dInK;
			
			if(domino.getDomino().getStatus().equals(DominoStatus.Discarded)) continue;
			
			draw(domino, domino.getDirection(), false);
			addCrowns(currentLeft,domino.getDomino().getLeftCrown());
			addCrowns(currentRight,domino.getDomino().getRightCrown());
			
		}
		
		this.coordinates.get(41).setBackground(Color.BLACK);
		
		if(player.getColor().equals(PlayerColor.Green)) {
			green.setFont(new Font("Times", Font.BOLD, 20));
			green.setForeground(Color.GREEN);
			score.add(green);
		}
		if(player.getColor().equals(PlayerColor.Blue)) {
			blue.setFont(new Font("Times", Font.BOLD, 20));
			blue.setForeground(Color.BLUE);
			score.add(blue);
		}
		if(player.getColor().equals(PlayerColor.Yellow)) {
			yellow.setFont(new Font("Times", Font.BOLD, 20));
			yellow.setForeground(new Color(255,215,15));
			score.add(yellow); 
		}
		if(player.getColor().equals(PlayerColor.Pink)) {
			pink.setFont(new Font("Times", Font.BOLD, 20));
			pink.setForeground(Color.PINK);
			score.add(pink); 
		}
		
		display.add(board);
		display.add(score, BorderLayout.NORTH);
		frameR.add(display);
	
	}
	
	/**
	 * draws an individual domino
	 * 
	 * @author All team
	 * @param domino
	 * @param dir
	 * @param lastDomino
	 */
	
	
	private void draw(DominoInKingdom domino, DirectionKind dir, boolean lastDomino) {
		
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
		
		this.currentLeft.setBackground(getColor(domino.getDomino().getLeftTile()));
		this.currentRight.setBackground(getColor(domino.getDomino().getRightTile()));
		
		if(lastDomino) {
			
			this.currentLeft.setBorder(BorderFactory.createLineBorder(Color.CYAN)); 
			this.currentRight.setBorder(BorderFactory.createLineBorder(Color.CYAN)); 
			
		}
		
	}
	
	/**
	 * converts the (x,y) coordinate of a domino into a single index [1,81] that 
	 * identifies a single block on the game board grid
	 * 
	 * @author All team
	 * @param x
	 * @param y
	 */
	
	private int translate(int x, int y) {
		
		return x-(9*y)+41;
		
	}
	
	/**
	 * draws a domino's crowns on top of the 
	 * tile color
	 * 
	 * @author All team
	 * @param p1
	 * @param n1
	 */
	
	private void addCrowns(JPanel p1, int n1) {
		
		JPanel crowns1 = new JPanel();
		crowns1.setBackground(new Color(255,255,0));
		JPanel crowns2 = new JPanel();
		crowns2.setBackground(new Color(255,255,0));
		JPanel crowns3 = new JPanel();
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
	
	/**
	 * gets the Color of a Tile
	 * 
	 * @author All team
	 * @param t1
	 */
	
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
	
}
