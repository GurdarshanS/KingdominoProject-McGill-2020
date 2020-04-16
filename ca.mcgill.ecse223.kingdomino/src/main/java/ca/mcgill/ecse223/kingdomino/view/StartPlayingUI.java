package ca.mcgill.ecse223.kingdomino.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.Border;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.KDController;
import ca.mcgill.ecse223.kingdomino.controller.KDQuery;
import ca.mcgill.ecse223.kingdomino.controller.View;
import ca.mcgill.ecse223.kingdomino.model.Domino;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom;
import ca.mcgill.ecse223.kingdomino.model.Draft;
import ca.mcgill.ecse223.kingdomino.model.Kingdomino;
import ca.mcgill.ecse223.kingdomino.model.Player;
import ca.mcgill.ecse223.kingdomino.model.TerrainType;
import ca.mcgill.ecse223.kingdomino.model.Player.PlayerColor;

public class StartPlayingUI {

	public  JFrame frameR = new JFrame("Kingdomino");
	Border line = BorderFactory.createLineBorder(Color.black);
	
	Draft currentDraft = KingdominoApplication.getKingdomino().getCurrentGame().getCurrentDraft();
	
	JPanel display = new JPanel(new BorderLayout());
	JPanel start = new JPanel(new BorderLayout());
	JPanel score = new JPanel();
	
	JButton playing = new JButton("READY");
	
	JButton choose = new JButton("Choose #1");
	JButton choose2 = new JButton("Choose #2");
	JButton choose3 = new JButton("Choose #3");
	JButton choose4 = new JButton("Choose #4");
	
	JLabel green = new JLabel("Player GREEN");
	JLabel blue = new JLabel("Player BLUE");
	JLabel yellow = new JLabel("Player YELLOW");
	JLabel pink = new JLabel("Player PINK");
	
	JLabel order = new JLabel("Player Order:  ");
	
	JLabel turnNum;
	JLabel greenP;
	JLabel blueP;
	JLabel yellowP;
	JLabel pinkP;
	

	public StartPlayingUI() {
		
		frameR.setSize(1350, 850);
		frameR.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frameR.setLocationRelativeTo(null);
		frameR.setVisible(true);
		
		View.printNextRoundPlayerOrder(KingdominoApplication.getKingdomino());
		
		drawFirstDraft();
		
	}
	
	public static void main(String[] args) {
		
		Kingdomino kd = KDController.loadGame("firstDraft.data");
		StartPlayingUI ui = new StartPlayingUI();
		
	}

	public void drawFirstDraft() {

		playing.setEnabled(false);
		
		JPanel draft = new JPanel();		
		
		GroupLayout layout = new GroupLayout(draft);
		draft.setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		JPanel dom1 = new JPanel(new GridLayout(1,2));
		JPanel dom2 = new JPanel(new GridLayout(1,2));
		JPanel dom3 = new JPanel(new GridLayout(1,2));
		JPanel dom4 = new JPanel(new GridLayout(1,2));
		JButton browse = new JButton("Browse All Dominoes");
		//JButton save = new JButton("Save Current Game");
		dom1.setPreferredSize(new Dimension(200, 100));
		dom2.setPreferredSize(new Dimension(200, 100));
		dom3.setPreferredSize(new Dimension(200, 100));
		dom4.setPreferredSize(new Dimension(200, 100));
		
		draft.add(dom1);
		draft.add(dom2);
		draft.add(dom3);
		draft.add(dom4);
		draft.add(browse);
		//draft.add(save);
		
		playing.setPreferredSize(new Dimension(400, 200));
		playing.setFont(new Font("Times", Font.BOLD, 100));
		playing.setForeground(Color.BLACK);
		playing.setBackground(Color.PINK);
		
		start.add(playing, BorderLayout.CENTER);
		
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
		
		order.setFont(new Font("Times", Font.BOLD, 20));
		score.add(order);
		displayPlayerOrder(score);
		displayTurnNumber(score);
		
		display.add(draft);
		frameR.add(display, BorderLayout.WEST);
		frameR.add(start, BorderLayout.CENTER);
		frameR.add(score, BorderLayout.NORTH);
		
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
//						.addGroup(layout.createSequentialGroup()
//								.addComponent(save)
//						)
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
					)
//					.addGroup(layout.createParallelGroup()
//							.addComponent(save)
//					)
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
		browse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				new BrowsingDominoes();
				
			}
		});
		choose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				boolean chosen = KDController.chooseSM(currentDraft.getIdSortedDomino(0));
			
				if(chosen) {
					
					choose.setEnabled(false);
			
				}
				
				if(KDQuery.hasAllPlayersChosen()) playing.setEnabled(true);
				
			}
		});
		
		choose2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				boolean chosen = KDController.chooseSM(currentDraft.getIdSortedDomino(1));
			
				if(chosen) {
					
					choose2.setEnabled(false);
	
				}
				
				if(KDQuery.hasAllPlayersChosen()) playing.setEnabled(true);
				
			}
		});
		choose3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				boolean chosen = KDController.chooseSM(currentDraft.getIdSortedDomino(2));
	
				if(chosen) {
					
					choose3.setEnabled(false);
					
				}
				
				if(KDQuery.hasAllPlayersChosen()) playing.setEnabled(true);
				
			}
		});
		
		choose4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				boolean chosen = KDController.chooseSM(currentDraft.getIdSortedDomino(3));
			
				if(chosen) {
					
					choose4.setEnabled(false);
			
				}
				
				if(KDQuery.hasAllPlayersChosen()) playing.setEnabled(true);
				
			}
		});
		
		playing.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				
				frameR.dispose();
				
				KDController.selectionReadySM();
				KDController.draftReadySM();
				KDController.manipulateFirstSM(-3, 3, "right");
				
				Player currentPlayer = KingdominoApplication.getKingdomino().getCurrentGame().getNextPlayer();
				
				DominoInKingdom dInK = (DominoInKingdom) currentPlayer.getKingdom().getTerritory(currentPlayer.getKingdom().getTerritories().size()-1);
				
				PlayingUI start = new PlayingUI(currentPlayer, -3, 3, dInK);
				
			}
	
		});
		
	}
	
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

	private void displayTurnNumber(JPanel panel) {
		
		int turn = KingdominoApplication.getKingdomino().getCurrentGame().getNextPlayer().getKingdom().getTerritories().size()-1;
		
		turnNum = new JLabel("         Turn: " + turn +"/12");
		turnNum.setFont(new Font("Times", Font.BOLD, 20));
		panel.add(turnNum);
		
	}
}