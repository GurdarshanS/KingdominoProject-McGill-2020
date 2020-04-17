package ca.mcgill.ecse223.kingdomino.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;
import javax.swing.border.Border;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.KDController;
import ca.mcgill.ecse223.kingdomino.controller.KDQuery;
import ca.mcgill.ecse223.kingdomino.model.Castle;
import ca.mcgill.ecse223.kingdomino.model.Domino;
import ca.mcgill.ecse223.kingdomino.model.Domino.DominoStatus;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom.DirectionKind;
import ca.mcgill.ecse223.kingdomino.model.Draft;
import ca.mcgill.ecse223.kingdomino.model.KingdomTerritory;
import ca.mcgill.ecse223.kingdomino.model.Player;
import ca.mcgill.ecse223.kingdomino.model.Player.PlayerColor;
import ca.mcgill.ecse223.kingdomino.model.TerrainType;
import ca.mcgill.ecse223.kingdomino.persistence.KDPersistence;

public class PlayingUI extends JFrame{

	public  JFrame frameR = new JFrame("Kingdomino");
	private HashMap<Integer, JPanel> coordinates = new HashMap<Integer, JPanel>();
	private JPanel currentLeft = new JPanel();
	private JPanel currentRight = new JPanel();
	private int LeftId = 0;
	private int RightId = 0;

	Border line = BorderFactory.createLineBorder(Color.black);
	JPanel display = new JPanel(new BorderLayout());
	
	JButton rotateR = new JButton("Rotate Clockwise");
	JButton rotateL = new JButton("Rotate CounterClockwise");
	JButton moveR = new JButton("Move Right");
	JButton moveL = new JButton("Move Left");
	JButton moveU = new JButton("Move Up");
	JButton moveD = new JButton("Move Down");
	JButton place = new JButton("Place Domino");
	JButton discard = new JButton("Discard Domino");
	
	JButton choose = new JButton("Choose #1");
	JButton choose2 = new JButton("Choose #2");
	JButton choose3 = new JButton("Choose #3");
	JButton choose4 = new JButton("Choose #4");
	
	JButton saveStats = new JButton("Save Stats");
	JButton exit = new JButton("Exit");
	JButton playAgain = new JButton("Play Again");
	
	JButton nextRound = new JButton("Next Round");
	
	Color panelPrevColorLeft;
	Color panelPrevColorRight;
	
	JLabel green = new JLabel("Player GREEN");
	JLabel blue = new JLabel("Player BLUE");
	JLabel yellow = new JLabel("Player YELLOW");
	JLabel pink = new JLabel("Player PINK");
	
	JLabel order = new JLabel("Player Order:  ");
	
	Draft currentDraft = KingdominoApplication.getKingdomino().getCurrentGame().getCurrentDraft();
	
	JPanel score = new JPanel();
	JLabel scoreOfPlayer;
	
	JLabel turnNum;
	JLabel greenP;
	JLabel blueP;
	JLabel yellowP;
	JLabel pinkP;
	
	JButton lookKingdom1 = new JButton("Look at Blue's Kingdom");
	JButton lookKingdom2 = new JButton("Look at Pink's Kingdom");
	JButton lookKingdom3 = new JButton("Look at Green's Kingdom");
	JButton lookKingdom4 = new JButton("Look at Yellow's Kingdom");
	
	int prevNumCrownsLeft;
	int prevNumCrownsRight;
	
	/**
	 * Displays the panel/frame for the playing screen
	 * 
	 * @author All team
	 * @param p1
	 * @param x
	 * @param y
	 * @param dom
	 */
	public PlayingUI(Player p1, int x, int y, DominoInKingdom dom) {

		frameR.setSize(1350, 850);
		frameR.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frameR.setLocationRelativeTo(null);
		
		drawInitialKingdom(p1);
		drawNewDomino(p1,x,y,dom);
	}
	/**
	 * Provides user interface for the kingdom playing screens
	 * 
	 * @author All team
	 * @param p1
	 */
	private void drawInitialKingdom(Player p1) {

		Border blackline = BorderFactory.createLineBorder(Color.DARK_GRAY);

		JPanel board = new JPanel(new GridLayout(9,9));
		JPanel buttons = new JPanel();
		JPanel draft = new JPanel();		
		
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

		buttons.add(nextRound);
		buttons.add(rotateR);
		buttons.add(rotateL);
		buttons.add(moveL);
		buttons.add(moveR);
		buttons.add(moveU);
		buttons.add(moveD);
		buttons.add(place);
		buttons.add(discard);

		GroupLayout layout = new GroupLayout(draft);
		draft.setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		JPanel dom1 = new JPanel(new GridLayout(1,2));
		JPanel dom2 = new JPanel(new GridLayout(1,2));
		JPanel dom3 = new JPanel(new GridLayout(1,2));
		JPanel dom4 = new JPanel(new GridLayout(1,2));
		JButton browse = new JButton("Browse All Dominoes");
		JButton save = new JButton("Save Current Game");

		dom1.setPreferredSize(new Dimension(200, 100));
		dom2.setPreferredSize(new Dimension(200, 100));
		dom3.setPreferredSize(new Dimension(200, 100));
		dom4.setPreferredSize(new Dimension(200, 100));
		
		draft.add(dom1);
		draft.add(dom2);
		draft.add(dom3);
		draft.add(dom4);
		draft.add(browse);
		draft.add(save);
		
		JPanel[] firstDomino = new JPanel[2];
		for(int i=0; i<2; i++) {
			firstDomino[i] = new JPanel();
			
			Domino d1 = currentDraft.getIdSortedDomino(0);
			
			if(i == 0) {
				
				firstDomino[i].setBackground(getColor(d1.getLeftTile()));
				addCrowns(firstDomino[i], d1.getLeftCrown());
				
			}
			
			if(i == 1) {
				
				firstDomino[i].setBackground(getColor(d1.getRightTile()));
				addCrowns(firstDomino[i], d1.getRightCrown());
			}
			
			firstDomino[i].setBorder(line);
			dom1.add(firstDomino[i]);
			
		}
		
		JPanel[] second = new JPanel[2];
		for(int i=0; i<2; i++) {
			
			second[i] = new JPanel();
			
			Domino d2 = currentDraft.getIdSortedDomino(1);
			
			if(i == 0) {
				
				second[i].setBackground(getColor(d2.getLeftTile()));
				addCrowns(second[i], d2.getLeftCrown());
			}
			
			if(i == 1) {
				
				second[i].setBackground(getColor(d2.getRightTile()));
				addCrowns(second[i], d2.getRightCrown());
			}
			
			second[i].setBorder(line);
			dom2.add(second[i]);
		}
		
		JPanel[] third = new JPanel[2];
		for(int i=0; i<2; i++) {
			third[i] = new JPanel();
			
			Domino d3 = currentDraft.getIdSortedDomino(2);
			
			if(i == 0) {
				
				third[i].setBackground(getColor(d3.getLeftTile()));
				addCrowns(third[i], d3.getLeftCrown());
			}
			
			if(i == 1) {
				
				third[i].setBackground(getColor(d3.getRightTile()));
				addCrowns(third[i], d3.getRightCrown());
				
			}
			
			third[i].setBorder(line);
			dom3.add(third[i]);
		}
		
		JPanel[] fourth = new JPanel[2];
		for(int i=0; i<2; i++) {
			fourth[i] = new JPanel();
			
			Domino d4 = currentDraft.getIdSortedDomino(3);
			
			if(i == 0) {
				
				fourth[i].setBackground(getColor(d4.getLeftTile()));
				addCrowns(fourth[i], d4.getLeftCrown());
				
			}
			
			if(i == 1) {
				
				fourth[i].setBackground(getColor(d4.getRightTile()));
				addCrowns(fourth[i], d4.getRightCrown());
				
			}
			
			fourth[i].setBorder(line);
			dom4.add(fourth[i]);
		}
		
		choose.setEnabled(false);
		choose2.setEnabled(false);
		choose3.setEnabled(false);
		choose4.setEnabled(false);
		nextRound.setEnabled(false);
		discard.setEnabled(false);

		if(p1.getColor().equals(PlayerColor.Green)) {
			green.setFont(new Font("Times", Font.BOLD, 20));
			green.setForeground(Color.GREEN);
			score.add(green);
		}
		if(p1.getColor().equals(PlayerColor.Blue)) {
			blue.setFont(new Font("Times", Font.BOLD, 20));
			blue.setForeground(Color.BLUE);
			score.add(blue);
		}
		if(p1.getColor().equals(PlayerColor.Yellow)) {
			yellow.setFont(new Font("Times", Font.BOLD, 20));
			yellow.setForeground(new Color(255,215,15));
			score.add(yellow); 
		}
		if(p1.getColor().equals(PlayerColor.Pink)) {
			pink.setFont(new Font("Times", Font.BOLD, 20));
			pink.setForeground(Color.PINK);
			score.add(pink); 
		}
		
		int playerScore = p1.getTotalScore();
		String s = String.format("%1$-5s", playerScore); 
		
		scoreOfPlayer = new JLabel("                 Score: " + s + "            ");
		scoreOfPlayer.setFont(new Font("Times", Font.BOLD, 20));
		
		score.add(scoreOfPlayer);
		
		order.setFont(new Font("Times", Font.BOLD, 20));
		score.add(order);
		displayPlayerOrder(score);
		displayTurnNumber(score);
		
		display.add(board);
		display.add(buttons, BorderLayout.SOUTH);
		display.add(draft, BorderLayout.WEST);
		display.add(score, BorderLayout.NORTH);
		frameR.add(display);
		
		layout.setHorizontalGroup(
				layout.createSequentialGroup()
				.addGap(10)
				.addGroup(layout.createSequentialGroup())
				.addGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
						.addGroup(layout.createSequentialGroup()
								.addComponent(dom1, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
								.addGap(10))
						.addGroup(layout.createSequentialGroup()
								.addComponent(choose))
						.addGroup(layout.createSequentialGroup()
								.addComponent(dom2, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
								.addGap(10))
						.addGroup(layout.createSequentialGroup()
								.addComponent(choose2))
						.addGroup(layout.createSequentialGroup()
								.addComponent(dom3, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
								.addGap(10))
						.addGroup(layout.createSequentialGroup()
								.addComponent(choose3))
						.addGroup(layout.createSequentialGroup()
								.addComponent(dom4, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
								.addGap(10))
						.addGroup(layout.createSequentialGroup()
								.addComponent(choose4))
						.addGroup(layout.createSequentialGroup()
								.addComponent(browse)
						)
						.addGroup(layout.createSequentialGroup()
								.addComponent(save)
						)
						.addGroup(layout.createSequentialGroup()
						)
						.addGroup(layout.createSequentialGroup())
						.addGroup(layout.createSequentialGroup())
						.addGroup(layout.createSequentialGroup())
						.addGroup(layout.createSequentialGroup()
								)
						)
				)
		);
		
		layout.setVerticalGroup(
				layout.createParallelGroup()
				.addGroup(layout.createParallelGroup())
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
				.addGroup(layout.createSequentialGroup()
					.addGroup(layout.createParallelGroup()
							.addComponent(dom1, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(75))
					.addGroup(layout.createParallelGroup()
							.addComponent(choose))
					.addGroup(layout.createParallelGroup()
							.addComponent(dom2, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(75))
					.addGroup(layout.createParallelGroup()
							.addComponent(choose2))
					.addGroup(layout.createParallelGroup()
							.addComponent(dom3, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(75))
					.addGroup(layout.createParallelGroup()
							.addComponent(choose3))
					.addGroup(layout.createParallelGroup()
							.addComponent(dom4, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(75))
					.addGroup(layout.createParallelGroup()
							.addComponent(choose4)
							.addGap(75))
					.addGroup(layout.createParallelGroup()
							.addComponent(browse)
							.addGap(20)
					)
					.addGroup(layout.createParallelGroup()
							.addComponent(save)
					)
					.addGroup(layout.createParallelGroup()
					)
					.addGroup(layout.createParallelGroup())
					.addGroup(layout.createParallelGroup())
					.addGroup(layout.createParallelGroup())
					.addGroup(layout.createParallelGroup()
							)
					)
					
				)
		);
		save.addActionListener(new ActionListener() {
			/**
			 * Saves the game at the current point in the game
			 * @author All team
			 * @param e
			 */
			public void actionPerformed(ActionEvent e) {
				
				KDController.saveGame(KingUI_Main.SAVE, true);
				
		}});
		browse.addActionListener(new ActionListener() {
			/**
			 * Displays the browse dominoe page 
			 * @author All team
			 * @param e
			 */
			public void actionPerformed(ActionEvent e) {
				
				new BrowsingDominoes();
				
			}
		});
		choose.addActionListener(new ActionListener() {
			/**
			 * Chooses the first domino
			 * @author All team
			 * @param e
			 */
			public void actionPerformed(ActionEvent e) {
				
				boolean chosen = KDController.chooseSM(currentDraft.getIdSortedDomino(0));
			
				if(chosen) {
					
					choose.setEnabled(false);
			
					if(KDQuery.lastPlayerInTurn(KingdominoApplication.getKingdomino().getCurrentGame().getNextPlayer())) {
						
						nextRound.setEnabled(true);
						nextRound.setBackground(Color.GREEN);
						
					}
					
					else {
						
						frameR.dispose();
						
						KDController.manipulateNextSM(-3, 3, "right");

						Player currentPlayer = KingdominoApplication.getKingdomino().getCurrentGame().getNextPlayer();
						DominoInKingdom dInK = (DominoInKingdom) currentPlayer.getKingdom().getTerritory(currentPlayer.getKingdom().getTerritories().size()-1);
						PlayingUI newGame = new PlayingUI(currentPlayer, -3, 3, dInK);
						newGame.frameR.setVisible(true);
						
					}
					
				}
				
			}
		});
		
		choose2.addActionListener(new ActionListener() {
			/**
			 * Chooses the second domino
			 * @author All team
			 * @param e
			 */
			public void actionPerformed(ActionEvent e) {

				boolean chosen = KDController.chooseSM(currentDraft.getIdSortedDomino(1));
			
				if(chosen) {
					
					choose2.setEnabled(false);
					
					if(KDQuery.lastPlayerInTurn(KingdominoApplication.getKingdomino().getCurrentGame().getNextPlayer())) {
						
						nextRound.setEnabled(true);
						nextRound.setBackground(Color.GREEN);
						
					}
					
					else {
						
						frameR.dispose();
						
						KDController.manipulateNextSM(-3, 3, "right");
						Player currentPlayer = KingdominoApplication.getKingdomino().getCurrentGame().getNextPlayer();
						DominoInKingdom dInK = (DominoInKingdom) currentPlayer.getKingdom().getTerritory(currentPlayer.getKingdom().getTerritories().size()-1);
					
						PlayingUI newGame = new PlayingUI(currentPlayer, -3, 3, dInK);
						newGame.frameR.setVisible(true);
					}
					
				}
				
			}
		});
		choose3.addActionListener(new ActionListener() {
			/**
			 * Chooses the third domino
			 * @author All team
			 * @param e
			 */
			public void actionPerformed(ActionEvent e) {

				boolean chosen = KDController.chooseSM(currentDraft.getIdSortedDomino(2));
	
				if(chosen) {
					
					choose3.setEnabled(false);
					
					if(KDQuery.lastPlayerInTurn(KingdominoApplication.getKingdomino().getCurrentGame().getNextPlayer())) {
						
						nextRound.setEnabled(true);
						nextRound.setBackground(Color.GREEN);
						
					}
					
					else {
						
						frameR.dispose();
						
						KDController.manipulateNextSM(-3, 3, "right");
						Player currentPlayer = KingdominoApplication.getKingdomino().getCurrentGame().getNextPlayer();
						DominoInKingdom dInK = (DominoInKingdom) currentPlayer.getKingdom().getTerritory(currentPlayer.getKingdom().getTerritories().size()-1);
					
						PlayingUI newGame = new PlayingUI(currentPlayer, -3, 3, dInK);
						newGame.frameR.setVisible(true);
						
					}
					
				}
				
			}
		});
		
		choose4.addActionListener(new ActionListener() {
			/**
			 * Chooses the fourth domino
			 * @author All team
			 * @param e
			 */
			public void actionPerformed(ActionEvent e) {
			
				boolean chosen = KDController.chooseSM(currentDraft.getIdSortedDomino(3));
			
				if(chosen) {
					
					choose4.setEnabled(false);
					
					if(KDQuery.lastPlayerInTurn(KingdominoApplication.getKingdomino().getCurrentGame().getNextPlayer())) {
						
						nextRound.setEnabled(true);
						nextRound.setBackground(Color.GREEN);
						
					}
					
					else {
						
						frameR.dispose();
						
						KDController.manipulateNextSM(-3, 3, "right");
						Player currentPlayer = KingdominoApplication.getKingdomino().getCurrentGame().getNextPlayer();
						DominoInKingdom dInK = (DominoInKingdom) currentPlayer.getKingdom().getTerritory(currentPlayer.getKingdom().getTerritories().size()-1);
					
						PlayingUI newGame = new PlayingUI(currentPlayer, -3, 3, dInK);
						newGame.frameR.setVisible(true);
						
					}
						
				}
				
			}
		});
		
		nextRound.addActionListener(new ActionListener() {
			/**
			 * Advances to the next round
			 * @author All team
			 * @param e
			 */
			public void actionPerformed(ActionEvent e) {
			
				boolean ready = KDController.nextSelectionReadySM();
				boolean ready2 = KDController.draftReadySM();
				
				boolean isEmpty = KDQuery.isDominoPileEmpty();
				
				if(ready && ready2) {
				
					frameR.dispose();
					
					KDController.manipulateFirstSM(-3, 3, "right");
					
					Player currentPlayer = KingdominoApplication.getKingdomino().getCurrentGame().getNextPlayer();
					DominoInKingdom dInK = (DominoInKingdom) currentPlayer.getKingdom().getTerritory(currentPlayer.getKingdom().getTerritories().size()-1);
				
					PlayingUI newGame = new PlayingUI(currentPlayer, -3, 3, dInK);
					newGame.frameR.setVisible(true);
					
				}
				
				if(isEmpty) {
					
					frameR.dispose();
					
					KDController.lastSelectionReadySM(-3, 3, "right");
					
					Player currentPlayer = KingdominoApplication.getKingdomino().getCurrentGame().getNextPlayer();
					DominoInKingdom dInK = (DominoInKingdom) currentPlayer.getKingdom().getTerritory(currentPlayer.getKingdom().getTerritories().size()-1);
				
					PlayingUI newGame = new PlayingUI(currentPlayer, -3, 3, dInK);
					newGame.frameR.setVisible(true);
					
				}
			}
		});
		
		
		for(KingdomTerritory dInK : p1.getKingdom().getTerritories()) {
			
			if(dInK instanceof Castle) continue;
			
			DominoInKingdom domino = (DominoInKingdom) dInK;
			
			if(domino.equals((DominoInKingdom)(p1.getKingdom().getTerritory(p1.getKingdom().getTerritories().size()-1)))) break;
			if(!domino.getDomino().getStatus().equals(DominoStatus.PlacedInKingdom)) continue;
			
			draw(domino, domino.getDirection(), false);
			addCrowns(this.currentLeft,domino.getDomino().getLeftCrown());
			addCrowns(this.currentRight,domino.getDomino().getRightCrown());
			
		}
		
		this.coordinates.get(41).setBackground(Color.BLACK);
		
	}	
		
	private int translate(int x, int y) {
			
			return x-(9*y)+41;
			
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
			
			panelPrevColorLeft = this.currentLeft.getBackground();
			panelPrevColorRight = this.currentRight.getBackground();
		
			this.currentLeft.setBackground(getColor(domino.getDomino().getLeftTile()));
			this.currentRight.setBackground(getColor(domino.getDomino().getRightTile()));
			
			if(lastDomino) {
				
				this.currentLeft.setBorder(BorderFactory.createLineBorder(Color.CYAN)); 
				this.currentRight.setBorder(BorderFactory.createLineBorder(Color.CYAN)); 
			
			}
			
		}
	/**
	 * removes an individual domino
	 * 
	 * @author All team
	 * @param domino
	 * @param dir
	 * @param lastDomino
	 */
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
			
			this.currentLeft.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY)); 
			this.currentRight.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY)); 
		
		}
	/**
	 * Draws the domino that has yet to be placed and allows it to be moved
	 * 
	 * @author All team
	 * @param domino
	 * @param dir
	 * @param lastDomino
	 */
	private void drawNewDomino(Player p1, int x, int y, DominoInKingdom domino) {
		
		if(!KDQuery.isThereAvailablePlacement(p1, domino)) discard.setEnabled(true);
		
		draw(domino, domino.getDirection(), true);
		
		
		manageAfterMove(domino);
		changeIfUnderneath(domino);
		
		if(KingdominoApplication.getKingdomino().getStateMachine().getGamestatusFullName().equals("EndingGame")) {
			
			frameR.dispose();
			
			KDController.scoringSM();
			
			 JFrame frame = new JFrame("Rankings");

			 frame.setSize(1000, 500);
		     frame.setLocationRelativeTo(null);
		     frame.setResizable(false);
		     frame.setBackground(Color.LIGHT_GRAY);
		     frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		     JTextArea textArea = new JTextArea();
			 JScrollPane pan = new JScrollPane(textArea);
			 pan.setBounds(5, 5, 1300, 800);
			 textArea.setFont(new Font("Serif", Font.ITALIC, 16));
			 
			 ArrayList<String> winners = new ArrayList<String>();
				
			 String empty = "";
			 
		     for (Player p:KingdominoApplication.getKingdomino().getCurrentGame().getPlayers()) {
		    	 
					int[] sizeCrown=KDQuery.playerMaxPropSizeAndNumCrown(p);
					String row=String.format("%1$-20s  rank: %2$-10d score: %3$-10d size: %4$-10d crown: %5$-10d \n", 
							  				 p.getColor(),p.getCurrentRanking(),p.getTotalScore(),sizeCrown[0],sizeCrown[1]);
					
					textArea.append(row);
					
					if(p.getCurrentRanking() == 1) empty += (p.getColor().toString() + "\n");
						
		     }
		     
		      textArea.append("WINNER: ");
		      textArea.append(empty);
			  frame.add(pan, BorderLayout.CENTER);
			  
			  JPanel buttonsKingdom = new JPanel();
			  buttonsKingdom.add(lookKingdom1);
			  buttonsKingdom.add(lookKingdom2);
			  buttonsKingdom.add(lookKingdom3);
			  buttonsKingdom.add(lookKingdom4);
			  
			  JPanel manage = new JPanel();
			  manage.add(saveStats);
			  manage.add(exit);
			  manage.add(playAgain);
			
			  frame.add(buttonsKingdom, BorderLayout.SOUTH);
			  frame.add(manage, BorderLayout.NORTH);

			  frame.setVisible(true);
			  
			  lookKingdom1.addActionListener(new ActionListener() {
				  /**
					 * calls constructor to draw first players kingdom
					 * 
					 * @author All team
					 * @param arg0
					 */
				public void actionPerformed(ActionEvent arg0) {
					
					Player blue = KDQuery.getPlayerByColor("blue");
					
					LookAtKingdom blueKingdom = new LookAtKingdom(blue);
					blueKingdom.frameR.setVisible(true);
					
				}

			  });
			  
			  lookKingdom2.addActionListener(new ActionListener() {
				  /**
					 * calls constructor to draw second players kingdom
					 * 
					 * @author All team
					 * @param arg0
					 */
					public void actionPerformed(ActionEvent arg0) {
						
						Player pink = KDQuery.getPlayerByColor("pink");
						
						LookAtKingdom pinkKingdom = new LookAtKingdom(pink);
						pinkKingdom.frameR.setVisible(true);
						
					}
	
			});
			  
			  lookKingdom3.addActionListener(new ActionListener() {
				  /**
					 * calls constructor to draw third players kingdom
					 * 
					 * @author All team
					 * @param arg0
					 */
					public void actionPerformed(ActionEvent arg0) {
						
						Player green = KDQuery.getPlayerByColor("green");
						
						LookAtKingdom greenKingdom = new LookAtKingdom(green);
						greenKingdom.frameR.setVisible(true);
						
					}
	
			});
			  
			  lookKingdom4.addActionListener(new ActionListener() {
				  /**
					 * calls constructor to draw fourth players kingdom
					 * 
					 * @author All team
					 * @param arg0
					 */
					public void actionPerformed(ActionEvent arg0) {
						
						Player yellow = KDQuery.getPlayerByColor("yellow");
						
						LookAtKingdom yellowKingdom = new LookAtKingdom(yellow);
						yellowKingdom.frameR.setVisible(true);
						
					}
	
			 });
			  
			  saveStats.addActionListener(new ActionListener() {
				  /**
					 * saves the current game's statistics
					 * 
					 * @author All team
					 * @param arg0
					 */
				public void actionPerformed(ActionEvent arg0) {
					
					KDController.saveGame(KingUI_Main.SAVE, true);
					
				}
				
			  });
			  
			  exit.addActionListener(new ActionListener() {
				  /**
					 * exits application
					 * 
					 * @author All team
					 * @param arg0
					 */
				public void actionPerformed(ActionEvent e) {
					
					System.exit(0);
					
				}
			
			  });
			  
			  playAgain.addActionListener(new ActionListener() {
				  /**
					 * returns users to the main page to start a new game
					 * 
					 * @author All team
					 * @param arg0
					 */
				public void actionPerformed(ActionEvent e) {
					
					frame.dispose();
					
					new KingUI_Main().frame.setVisible(true);
					
				}
			
			  });
  
		}
		
		if(KingdominoApplication.getKingdomino().getStateMachine().getGamestatusFullName().equals("Playing.SelectingStandardDomino") && KDQuery.lastPlayerInTurn(p1)) {
			
			nextRound.setEnabled(true);
			nextRound.setBackground(Color.GREEN);
			
		}
		
		if(KingdominoApplication.getKingdomino().getStateMachine().getGamestatusFullName().equals("Playing.ConfirmingChoice")) {
			
			choose.setEnabled(true);
			choose2.setEnabled(true);
			choose3.setEnabled(true);
			choose4.setEnabled(true);
			
			moveL.setEnabled(false);
			moveU.setEnabled(false);
			moveD.setEnabled(false);
			moveR.setEnabled(false);
			rotateR.setEnabled(false);
			rotateL.setEnabled(false);
			place.setEnabled(false);
			discard.setEnabled(false);
			
			currentLeft.setBorder(BorderFactory.createLineBorder(Color.GREEN)); 
			currentRight.setBorder(BorderFactory.createLineBorder(Color.GREEN)); 
			
			if(currentDraft.getIdSortedDomino(0).hasDominoSelection() || currentDraft.getIdSortedDomino(0).getStatus().equals(DominoStatus.Discarded)) choose.setEnabled(false);
			if(currentDraft.getIdSortedDomino(1).hasDominoSelection() || currentDraft.getIdSortedDomino(1).getStatus().equals(DominoStatus.Discarded)) choose2.setEnabled(false);
			if(currentDraft.getIdSortedDomino(2).hasDominoSelection() || currentDraft.getIdSortedDomino(2).getStatus().equals(DominoStatus.Discarded)) choose3.setEnabled(false);
			if(currentDraft.getIdSortedDomino(3).hasDominoSelection() || currentDraft.getIdSortedDomino(3).getStatus().equals(DominoStatus.Discarded)) choose4.setEnabled(false);
			
			boolean inLastState = KingdominoApplication.getKingdomino().getStateMachine().getGamestatusFullName().equalsIgnoreCase("Finishing.ConfirmingLastChoice");
			
			if(inLastState && !KDQuery.hasAllPlayersPlayed()) {
				
				frameR.dispose();
				
				KDController.manipulateLastSM(-3, 3, "right");
				
				Player currentPlayer = KingdominoApplication.getKingdomino().getCurrentGame().getNextPlayer();
				DominoInKingdom dInK = (DominoInKingdom) currentPlayer.getKingdom().getTerritory(currentPlayer.getKingdom().getTerritories().size()-1);
				PlayingUI newGame = new PlayingUI(currentPlayer, -3, 3, dInK);
				newGame.frameR.setVisible(true);
				
			}
			
			else if(inLastState && KDQuery.hasAllPlayersPlayed()) {
				
				frameR.dispose();
				
				KDController.scoringSM();
				
				 JFrame frame = new JFrame("Rankings");

				 frame.setSize(1000, 500);
			     frame.setLocationRelativeTo(null);
			     frame.setResizable(false);
			     frame.setBackground(Color.LIGHT_GRAY);
			     frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			
			     JTextArea textArea = new JTextArea();
				 JScrollPane pan = new JScrollPane(textArea);
				 pan.setBounds(5, 5, 1300, 800);
				 textArea.setFont(new Font("Serif", Font.ITALIC, 16));
				 
				 ArrayList<String> winners = new ArrayList<String>();
					
				 String empty = "";
				 
			     for (Player p:KingdominoApplication.getKingdomino().getCurrentGame().getPlayers()) {
			    	 
						int[] sizeCrown=KDQuery.playerMaxPropSizeAndNumCrown(p);
						String row=String.format("%1$-20s  rank: %2$-10d score: %3$-10d size: %4$-10d crown: %5$-10d \n", 
								  				 p.getColor(),p.getCurrentRanking(),p.getTotalScore(),sizeCrown[0],sizeCrown[1]);
						
						textArea.append(row);
						
						if(p.getCurrentRanking() == 1) empty += (p.getColor().toString() + "\n");
							
			     }
			     
			      textArea.append("WINNER: ");
			      textArea.append(empty);
				  frame.add(pan, BorderLayout.CENTER);
				  
				  JPanel buttonsKingdom = new JPanel();
				  buttonsKingdom.add(lookKingdom1);
				  buttonsKingdom.add(lookKingdom2);
				  buttonsKingdom.add(lookKingdom3);
				  buttonsKingdom.add(lookKingdom4);
				  
				  JPanel manage = new JPanel();
				  manage.add(saveStats);
				  manage.add(exit);
				  manage.add(playAgain);
				
				  frame.add(buttonsKingdom, BorderLayout.SOUTH);
				  frame.add(manage, BorderLayout.NORTH);

				  frame.setVisible(true);
				  
				  lookKingdom1.addActionListener(new ActionListener() {
					  /**
						 * calls constructor to draw first players kingdom
						 * 
						 * @author All team
						 * @param arg0
						 */
					public void actionPerformed(ActionEvent arg0) {
						
						Player blue = KDQuery.getPlayerByColor("blue");
						
						LookAtKingdom blueKingdom = new LookAtKingdom(blue);
						blueKingdom.frameR.setVisible(true);
						
					}
	
				  });
				  
				  lookKingdom2.addActionListener(new ActionListener() {
					  /**
						 * calls constructor to draw second players kingdom
						 * 
						 * @author All team
						 * @param arg0
						 */
						public void actionPerformed(ActionEvent arg0) {
							
							Player pink = KDQuery.getPlayerByColor("pink");
							
							LookAtKingdom pinkKingdom = new LookAtKingdom(pink);
							pinkKingdom.frameR.setVisible(true);
							
						}
		
				});
				  
				  lookKingdom3.addActionListener(new ActionListener() {
					  /**
						 * calls constructor to draw third players kingdom
						 * 
						 * @author All team
						 * @param arg0
						 */
						public void actionPerformed(ActionEvent arg0) {
							
							Player green = KDQuery.getPlayerByColor("green");
							
							LookAtKingdom greenKingdom = new LookAtKingdom(green);
							greenKingdom.frameR.setVisible(true);
							
						}
		
				});
				  
				  lookKingdom4.addActionListener(new ActionListener() {
					  /**
						 * calls constructor to draw fourth players kingdom
						 * 
						 * @author All team
						 * @param arg0
						 */
						public void actionPerformed(ActionEvent arg0) {
							
							Player yellow = KDQuery.getPlayerByColor("yellow");
							
							LookAtKingdom yellowKingdom = new LookAtKingdom(yellow);
							yellowKingdom.frameR.setVisible(true);
							
						}
		
				 });
				  
				  saveStats.addActionListener(new ActionListener() {
					  /**
						 * saves the current games' players statistics
						 * 
						 * @author All team
						 * @param arg0
						 */
					public void actionPerformed(ActionEvent arg0) {
						
						KDController.saveGame(KingUI_Main.SAVE, true);
						
					}
					
				  });
				  /**
					 * exits application
					 * 
					 * @author All team
					 * @param arg0
					 */
				  exit.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent e) {
						
						System.exit(0);
						
					}
				
				  });
				  /**
					 * returns users to the main page to start a new game
					 * 
					 * @author All team
					 * @param arg0
					 */
				  playAgain.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent e) {
						
						frame.dispose();
						
						new KingUI_Main().frame.setVisible(true);
						
					}
				
				  });
	  
			}
			
		}
		
		moveR.addActionListener(new java.awt.event.ActionListener() {
			/**
			 * move domino right
			 * 
			 * @author All team
			 * @param evt
			 */
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				
				
				manageBeforeMove(domino);
				undraw(domino,  domino.getDirection());
				
				
				KDController.moveSM("right");
			
				
				draw(domino,  domino.getDirection(), true);
				
				manageAfterMove(domino);
				changeIfUnderneath(domino);
				
			}
		});
		
		moveL.addActionListener(new java.awt.event.ActionListener() {
			/**
			 * move domino left
			 * 
			 * @author All team
			 * @param evt
			 */
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				
				
				manageBeforeMove(domino);
				undraw(domino,  domino.getDirection());
				
				
				KDController.moveSM("left");
			
				draw(domino,  domino.getDirection(), true);
				
				manageAfterMove(domino);
				changeIfUnderneath(domino);
				
			}
		});
		
		moveU.addActionListener(new java.awt.event.ActionListener() {
			/**
			 * move domino up
			 * 
			 * @author All team
			 * @param evt
			 */
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				
				manageBeforeMove(domino);
				undraw(domino,  domino.getDirection());
				
				
				KDController.moveSM("up");
				
			
			
				draw(domino,  domino.getDirection(), true);
				
				manageAfterMove(domino);
				changeIfUnderneath(domino);
				
			}
		});
		
		moveD.addActionListener(new java.awt.event.ActionListener() {
			/**
			 * move domino down
			 * 
			 * @author All team
			 * @param evt
			 */
			public void actionPerformed(java.awt.event.ActionEvent evt) {
			
				manageBeforeMove(domino);
				undraw(domino,  domino.getDirection());
				
				KDController.moveSM("down");
			
				draw(domino,  domino.getDirection(), true);
				
				manageAfterMove(domino);
				changeIfUnderneath(domino);
				
				
			}
		});
		
		rotateR.addActionListener(new java.awt.event.ActionListener() {
			/**
			 * rotate domino right
			 * 
			 * @author All team
			 * @param evt
			 */
			public void actionPerformed(java.awt.event.ActionEvent evt) {
			
				manageBeforeMove(domino);
				undraw(domino,  domino.getDirection());
				
				KDController.rotateSM("clockwise");
			
				draw(domino,  domino.getDirection(), true);
			
				manageAfterMove(domino);
				changeIfUnderneath(domino);
				
				
			}
		});
		
		rotateL.addActionListener(new java.awt.event.ActionListener() {
			/**
			 * move domino left
			 * 
			 * @author All team
			 * @param evt
			 */
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				
				manageBeforeMove(domino);
				undraw(domino,  domino.getDirection());
				
				KDController.rotateSM("counterclockwise");
			
				draw(domino,  domino.getDirection(), true);
				
				manageAfterMove(domino);
				changeIfUnderneath(domino);
				
			}
		});
		
		place.addActionListener(new java.awt.event.ActionListener() {
			/**
			 * places domino at current location 
			 * Checks the state and other specificities
			 * @author All team
			 * @param evt
			 */
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				boolean placed = KDController.placeSM();
			
				if(placed) {
					
					choose.setEnabled(true);
					choose2.setEnabled(true);
					choose3.setEnabled(true);
					choose4.setEnabled(true);
					
					moveL.setEnabled(false);
					moveU.setEnabled(false);
					moveD.setEnabled(false);
					moveR.setEnabled(false);
					rotateR.setEnabled(false);
					rotateL.setEnabled(false);
					place.setEnabled(false);
					discard.setEnabled(false);
					
					currentLeft.setBorder(BorderFactory.createLineBorder(Color.GREEN)); 
					currentRight.setBorder(BorderFactory.createLineBorder(Color.GREEN)); 
					
					int playerScore = p1.getTotalScore();
					String s = String.format("%1$-5s", playerScore); 
					score.remove(scoreOfPlayer);
					score.remove(blueP);
					score.remove(pinkP);
					score.remove(yellowP);
					score.remove(greenP);
					score.remove(turnNum);
					score.remove(order);
					scoreOfPlayer = new JLabel("                 Score: " + s + "            ");
					scoreOfPlayer.setFont(new Font("Times", Font.BOLD, 20));
					score.add(scoreOfPlayer);
					order.setFont(new Font("Times", Font.BOLD, 20));
					score.add(order);
					displayPlayerOrder(score);
					displayTurnNumber(score);
					frameR.setVisible(true);
					/////////////////
					if(currentDraft.getIdSortedDomino(0).hasDominoSelection() || currentDraft.getIdSortedDomino(0).getStatus().equals(DominoStatus.Discarded)) choose.setEnabled(false);
					if(currentDraft.getIdSortedDomino(1).hasDominoSelection() || currentDraft.getIdSortedDomino(1).getStatus().equals(DominoStatus.Discarded)) choose2.setEnabled(false);
					if(currentDraft.getIdSortedDomino(2).hasDominoSelection() || currentDraft.getIdSortedDomino(2).getStatus().equals(DominoStatus.Discarded)) choose3.setEnabled(false);
					if(currentDraft.getIdSortedDomino(3).hasDominoSelection() || currentDraft.getIdSortedDomino(3).getStatus().equals(DominoStatus.Discarded)) choose4.setEnabled(false);
					
					boolean inLastState = KingdominoApplication.getKingdomino().getStateMachine().getGamestatusFullName().equalsIgnoreCase("Finishing.ConfirmingLastChoice");
					
					if(inLastState && !KDQuery.hasAllPlayersPlayed()) {
						
						frameR.dispose();
						
						KDController.manipulateLastSM(-3, 3, "right");
						
						Player currentPlayer = KingdominoApplication.getKingdomino().getCurrentGame().getNextPlayer();
						DominoInKingdom dInK = (DominoInKingdom) currentPlayer.getKingdom().getTerritory(currentPlayer.getKingdom().getTerritories().size()-1);
						PlayingUI newGame = new PlayingUI(currentPlayer, -3, 3, dInK);
						newGame.frameR.setVisible(true);
						
					}
					
					else if(inLastState && KDQuery.hasAllPlayersPlayed()) {
						
						frameR.dispose();
						
						KDController.scoringSM();
						
						 JFrame frame = new JFrame("Rankings");

						 frame.setSize(1000, 500);
					     frame.setLocationRelativeTo(null);
					     frame.setResizable(false);
					     frame.setBackground(Color.LIGHT_GRAY);
					     frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
					
					     JTextArea textArea = new JTextArea();
						 JScrollPane pan = new JScrollPane(textArea);
						 pan.setBounds(5, 5, 1300, 800);
						 textArea.setFont(new Font("Serif", Font.ITALIC, 16));
						 
						 ArrayList<String> winners = new ArrayList<String>();
							
						 String empty = "";
						 
					     for (Player p:KingdominoApplication.getKingdomino().getCurrentGame().getPlayers()) {
					    	 
								int[] sizeCrown=KDQuery.playerMaxPropSizeAndNumCrown(p);
								String row=String.format("%1$-20s  rank: %2$-10d score: %3$-10d size: %4$-10d crown: %5$-10d \n", 
										  				 p.getColor(),p.getCurrentRanking(),p.getTotalScore(),sizeCrown[0],sizeCrown[1]);
								
								textArea.append(row);
								
								if(p.getCurrentRanking() == 1) empty += (p.getColor().toString() + "\n");
									
					     }
					     
					      textArea.append("WINNER: ");
					      textArea.append(empty);
						  frame.add(pan, BorderLayout.CENTER);
						  
						  JPanel buttonsKingdom = new JPanel();
						  buttonsKingdom.add(lookKingdom1);
						  buttonsKingdom.add(lookKingdom2);
						  buttonsKingdom.add(lookKingdom3);
						  buttonsKingdom.add(lookKingdom4);
						  
						  JPanel manage = new JPanel();
						  manage.add(saveStats);
						  manage.add(exit);
						  manage.add(playAgain);
						
						  frame.add(buttonsKingdom, BorderLayout.SOUTH);
						  frame.add(manage, BorderLayout.NORTH);
	
						  frame.setVisible(true);
						  
						  lookKingdom1.addActionListener(new ActionListener() {

							public void actionPerformed(ActionEvent arg0) {
								
								Player blue = KDQuery.getPlayerByColor("blue");
								
								LookAtKingdom blueKingdom = new LookAtKingdom(blue);
								blueKingdom.frameR.setVisible(true);
								
							}
			
						  });
						  
						  lookKingdom2.addActionListener(new ActionListener() {

								public void actionPerformed(ActionEvent arg0) {
									
									Player pink = KDQuery.getPlayerByColor("pink");
									
									LookAtKingdom pinkKingdom = new LookAtKingdom(pink);
									pinkKingdom.frameR.setVisible(true);
									
								}
				
						});
						  
						  lookKingdom3.addActionListener(new ActionListener() {

								public void actionPerformed(ActionEvent arg0) {
									
									Player green = KDQuery.getPlayerByColor("green");
									
									LookAtKingdom greenKingdom = new LookAtKingdom(green);
									greenKingdom.frameR.setVisible(true);
									
								}
				
						});
						  
						  lookKingdom4.addActionListener(new ActionListener() {

								public void actionPerformed(ActionEvent arg0) {
									
									Player yellow = KDQuery.getPlayerByColor("yellow");
									
									LookAtKingdom yellowKingdom = new LookAtKingdom(yellow);
									yellowKingdom.frameR.setVisible(true);
									
								}
				
						 });
						  
						  saveStats.addActionListener(new ActionListener() {

							public void actionPerformed(ActionEvent arg0) {
								
								KDController.saveGame(KingUI_Main.SAVE, true);
								
							}
							
						  });
						  
						  exit.addActionListener(new ActionListener() {

							public void actionPerformed(ActionEvent e) {
								
								System.exit(0);
								
							}
						
						  });
						  
						  playAgain.addActionListener(new ActionListener() {

							public void actionPerformed(ActionEvent e) {
								
								frame.dispose();
								
								new KingUI_Main().frame.setVisible(true);
								
								
							}
						
						  });
			  
					}
				}
				
			}
		});

		discard.addActionListener(new java.awt.event.ActionListener() {
			/**
			 * discards domino if unable to place on board
			 * Checks the state and other specificities
			 * 
			 * @author All team
			 * @param evt
			 */
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				boolean discarded = KDController.discardSM();
				
				if(discarded) {
					
					choose.setEnabled(true);
					choose2.setEnabled(true);
					choose3.setEnabled(true);
					choose4.setEnabled(true);
					
					moveL.setEnabled(false);
					moveU.setEnabled(false);
					moveD.setEnabled(false);
					moveR.setEnabled(false);
					rotateR.setEnabled(false);
					rotateL.setEnabled(false);
					place.setEnabled(false);
					discard.setEnabled(false);
					
					if(currentDraft.getIdSortedDomino(0).hasDominoSelection() || currentDraft.getIdSortedDomino(0).getStatus().equals(DominoStatus.Discarded)) choose.setEnabled(false);
					if(currentDraft.getIdSortedDomino(1).hasDominoSelection() || currentDraft.getIdSortedDomino(1).getStatus().equals(DominoStatus.Discarded)) choose2.setEnabled(false);
					if(currentDraft.getIdSortedDomino(2).hasDominoSelection() || currentDraft.getIdSortedDomino(2).getStatus().equals(DominoStatus.Discarded)) choose3.setEnabled(false);
					if(currentDraft.getIdSortedDomino(3).hasDominoSelection() || currentDraft.getIdSortedDomino(3).getStatus().equals(DominoStatus.Discarded)) choose4.setEnabled(false);
					
					boolean inLastState = KingdominoApplication.getKingdomino().getStateMachine().getGamestatusFullName().equalsIgnoreCase("Finishing.ConfirmingLastChoice");
				
					if(inLastState && !KDQuery.hasAllPlayersPlayed()) {
						
						frameR.dispose();
						
						KDController.manipulateLastSM(-3, 3, "right");
						
						Player currentPlayer = KingdominoApplication.getKingdomino().getCurrentGame().getNextPlayer();
						DominoInKingdom dInK = (DominoInKingdom) currentPlayer.getKingdom().getTerritory(currentPlayer.getKingdom().getTerritories().size()-1);
						PlayingUI newGame = new PlayingUI(currentPlayer, -3, 3, dInK);
						newGame.frameR.setVisible(true);
						
					}
					
					else if(inLastState && KDQuery.hasAllPlayersPlayed()) {
						
						frameR.dispose();
						
						KDController.scoringSM();
						
						 JFrame frame = new JFrame("Rankings");

						 frame.setSize(1000, 500);
						 frame.setLocationRelativeTo(null);
						 frame.setResizable(false);
						 frame.setBackground(Color.LIGHT_GRAY);
						 frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

					     JTextArea textArea = new JTextArea();
						 JScrollPane pan = new JScrollPane(textArea);
						 pan.setBounds(5, 5, 1300, 800);
						 textArea.setFont(new Font("Serif", Font.ITALIC, 16));
						 
						 ArrayList<String> winners = new ArrayList<String>();
						
						 String empty = "";
					     for (Player p:KingdominoApplication.getKingdomino().getCurrentGame().getPlayers()) {
					    	 
								int[] sizeCrown=KDQuery.playerMaxPropSizeAndNumCrown(p);
								String row=String.format("%1$-20s  rank: %2$-10d score: %3$-10d size: %4$-10d crown: %5$-10d \n", 
										  				 p.getColor(),p.getCurrentRanking(),p.getTotalScore(),sizeCrown[0],sizeCrown[1]);
								
								textArea.append(row);
								
								if(p.getCurrentRanking() == 1) empty += (p.getColor().toString() + "\n");
									
					     }
					     
					      textArea.append("WINNER: ");
					      textArea.append(empty);
						  frame.add(pan, BorderLayout.CENTER);
						  
						  JPanel buttonsKingdom = new JPanel();
						  buttonsKingdom.add(lookKingdom1);
						  buttonsKingdom.add(lookKingdom2);
						  buttonsKingdom.add(lookKingdom3);
						  buttonsKingdom.add(lookKingdom4);
						  
						  JPanel manage = new JPanel();
						  manage.add(saveStats);
						  manage.add(exit);
						  manage.add(playAgain);
						
						  frame.add(buttonsKingdom, BorderLayout.SOUTH);
						  frame.add(manage, BorderLayout.NORTH);
	
						  frame.setVisible(true);
						  
						  lookKingdom1.addActionListener(new ActionListener() {

							public void actionPerformed(ActionEvent arg0) {
								
								Player blue = KDQuery.getPlayerByColor("blue");
								
								LookAtKingdom blueKingdom = new LookAtKingdom(blue);
								blueKingdom.frameR.setVisible(true);
								
							}
			
						  });
						  
						  lookKingdom2.addActionListener(new ActionListener() {

								public void actionPerformed(ActionEvent arg0) {
									
									Player pink = KDQuery.getPlayerByColor("pink");
									
									LookAtKingdom pinkKingdom = new LookAtKingdom(pink);
									pinkKingdom.frameR.setVisible(true);
									
								}
				
						});
						  
						  lookKingdom3.addActionListener(new ActionListener() {

								public void actionPerformed(ActionEvent arg0) {
									
									Player green = KDQuery.getPlayerByColor("green");
									
									LookAtKingdom greenKingdom = new LookAtKingdom(green);
									greenKingdom.frameR.setVisible(true);
									
								}
				
						});
						  
						  lookKingdom4.addActionListener(new ActionListener() {

								public void actionPerformed(ActionEvent arg0) {
									
									Player yellow = KDQuery.getPlayerByColor("yellow");
									
									LookAtKingdom yellowKingdom = new LookAtKingdom(yellow);
									yellowKingdom.frameR.setVisible(true);
									
								}
				
						 });
						  
						  saveStats.addActionListener(new ActionListener() {

							public void actionPerformed(ActionEvent arg0) {
								
								KDController.saveGame(KingUI_Main.SAVE, true);
								
							}
							
						  });
						  
						  exit.addActionListener(new ActionListener() {

							public void actionPerformed(ActionEvent e) {
								
								System.exit(0);
								
							}
						
						  });
						  
						  playAgain.addActionListener(new ActionListener() {

							public void actionPerformed(ActionEvent e) {
								
								frame.dispose();
								
								new KingUI_Main().frame.setVisible(true);
								
							}
						
						  });
			
					}
				}
				
			}
		});
		
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
		
			frameR.setVisible(true);
			
	}
	/**
	 * remove a domino's crowns on top of the 
	 * tile color
	 * 
	 * @author All team
	 * @param p1
	 * @param n1
	 */
	private void removeCrowns(JPanel p1, int n1) {
			
			if(n1==1) {
				p1.getComponent(0).setVisible(false);
				p1.remove(p1.getComponent(0));
			} else if(n1==2) {
				p1.getComponent(0).setVisible(false);
				p1.remove(p1.getComponent(0));
				p1.getComponent(0).setVisible(false);
				p1.remove(p1.getComponent(0));
			} else if(n1==3) {
				p1.getComponent(0).setVisible(false);
				p1.remove(p1.getComponent(0));
				p1.getComponent(0).setVisible(false);
				p1.remove(p1.getComponent(0));
				p1.getComponent(0).setVisible(false);
				p1.remove(p1.getComponent(0));
			}
	
		}
	/**
	 * Indicates that there is a domino underneath another domino (red)
	 * 
	 * @author All team
	 * @param domino
	
	 */
	private void changeIfUnderneath(DominoInKingdom domino) {
			
			int[] otherPosMine = KDQuery.calculateRightPos(domino);
			
			for(KingdomTerritory kTerritory : domino.getKingdom().getTerritories()) {
				
				if(kTerritory instanceof Castle) {
					
					if(domino.getX() == 0 && domino.getY() == 0) this.currentLeft.setBackground(Color.RED);
					else if(otherPosMine[0] == 0 && otherPosMine[1] == 0) this.currentRight.setBackground(Color.RED);
					
				}
				
				else {
						
					DominoInKingdom dInK = (DominoInKingdom) kTerritory;
					if(dInK.equals(domino) || !dInK.getDomino().getStatus().equals(DominoStatus.PlacedInKingdom)) continue;
				
					int[] otherPos = KDQuery.calculateRightPos(kTerritory);
					
					if(dInK.getX() == domino.getX() && dInK.getY() == domino.getY()) this.currentLeft.setBackground(Color.RED);
					if(otherPos[0] == domino.getX() && otherPos[1] == domino.getY()) this.currentLeft.setBackground(Color.RED);
					
					if(dInK.getX() == otherPosMine[0] && dInK.getY() == otherPosMine[1]) this.currentRight.setBackground(Color.RED);
					if(otherPos[0] == otherPosMine[0] && otherPos[1] == otherPosMine[1]) this.currentRight.setBackground(Color.RED);
					
				}
				
			}
			
		}
	/**
	 * Removes the domino's crowns, and adds the dominos underneath
	 * 
	 * @author All team
	 * @param domino
	
	 */
	private void manageBeforeMove(DominoInKingdom domino) {
			
			removeCrowns(currentLeft, domino.getDomino().getLeftCrown());
			removeCrowns(currentRight, domino.getDomino().getRightCrown());
	
			addCrowns(currentLeft, prevNumCrownsLeft);
			addCrowns(currentRight, prevNumCrownsRight);
			
		}
	/**
	 * Removes the previous domino's crowns, and adds the current dominos
	 * 
	 * @author All team
	 * @param domino
	
	 */
	private void manageAfterMove(DominoInKingdom domino) {
						
			prevNumCrownsLeft = currentLeft.getComponentCount();
			prevNumCrownsRight = currentRight.getComponentCount();

			removeCrowns(currentLeft, prevNumCrownsLeft);
			removeCrowns(currentRight, prevNumCrownsRight);
			addCrowns(currentLeft, domino.getDomino().getLeftCrown());
			addCrowns(currentRight, domino.getDomino().getRightCrown());
			
		}
	/**
	 * Displays the order in which players select and play
	 * 
	 * @author All team
	 * @param panel
	
	 */
	private void displayPlayerOrder(JPanel panel) {
		
		List<Player> playerOrder = KingdominoApplication.getKingdomino().getCurrentGame().getPlayers();
		
		greenP = new JLabel("GREEN  ");
		greenP.setFont(new Font("Times", Font.BOLD, 20));
		greenP.setForeground(Color.GREEN);
		
		pinkP = new JLabel("PINK  ");
		pinkP.setFont(new Font("Times", Font.BOLD, 20));
		pinkP.setForeground(Color.PINK);
		
		blueP = new JLabel("BLUE  ");
		blueP.setFont(new Font("Times", Font.BOLD, 20));
		blueP.setForeground(Color.BLUE);
		
		yellowP = new JLabel("YELLOW  ");
		yellowP.setFont(new Font("Times", Font.BOLD, 20));
		yellowP.setForeground(new Color(255,215,15));
		
		for(Player player : playerOrder) {
			
			if(player.getColor().equals(PlayerColor.Blue)) panel.add(blueP);
			else if(player.getColor().equals(PlayerColor.Green)) panel.add(greenP);
			else if(player.getColor().equals(PlayerColor.Pink)) panel.add(pinkP);
			else panel.add(yellowP);
			
		}
		
	}
	/**
	 * Displays the current turn's number
	 * 
	 * @author All team
	 * @param panel
	
	 */
	private void displayTurnNumber(JPanel panel) {
		
		int turn = KingdominoApplication.getKingdomino().getCurrentGame().getNextPlayer().getKingdom().getTerritories().size()-1;
		
		turnNum = new JLabel("         Turn: " + turn +"/12");
		turnNum.setFont(new Font("Times", Font.BOLD, 20));
		panel.add(turnNum);
		
	}
}