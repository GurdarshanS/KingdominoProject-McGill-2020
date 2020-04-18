package ca.mcgill.ecse223.kingdomino.view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.List;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.Border;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.KDController;
import ca.mcgill.ecse223.kingdomino.controller.KDQuery;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom;
import ca.mcgill.ecse223.kingdomino.model.Gameplay.Gamestatus;
import ca.mcgill.ecse223.kingdomino.model.KingdomTerritory;
import ca.mcgill.ecse223.kingdomino.model.Kingdomino;
import ca.mcgill.ecse223.kingdomino.model.Player;

public class KingUI_Main {
	
	public static String SAVE = null;
	
	public static JFrame frame = new JFrame("Kingdomino");
	public static JPanel contPanel = new JPanel();
	public static CardLayout c1 = new CardLayout();
	public static int playerNum = 4;
	public static ArrayList<String> bonusOptions = new ArrayList<String>();

	public KingUI_Main() {
		initComponents();
	}

	/**
	 * launches the Main UI of the game
	 * 
	 * @author All team
	 * @param 
	 */
	private void initComponents() {
			
		frame.setSize(1350, 850);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		JLabel title = new JLabel("Kingdomino");
		title.setFont(new Font("Times", Font.BOLD, 60));
		Border blackline = BorderFactory.createLineBorder(Color.black);
		title.setBorder(blackline);
		JButton start = new JButton("Start New Game");
		JButton load = new JButton("Load Game");
		JButton search = new JButton("User Statistics");
		
		JPanel p1 = new JPanel();
		JPanel p2 = new JPanel(new GridBagLayout());
		JPanel p3 = new JPanel(new BorderLayout());
		
		
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.insets = new Insets(10, 10, 10, 10);
		
		p1.add(title);
		
		constraints.gridx = 0;
		constraints.gridy = 0;
		p2.add(start, constraints);
		constraints.gridx = 0;
		constraints.gridy = 1;
		p2.add(load, constraints);
		constraints.gridx = 0;
		constraints.gridy = 2;
		p2.add(search, constraints);
		
		p3.add(p1, BorderLayout.NORTH);
		p3.add(p2);

		contPanel.setLayout(c1);
		contPanel.add(p3, "1");
		c1.show(contPanel, "1");
		frame.add(contPanel);
		
		
		start.addActionListener(new java.awt.event.ActionListener() {
			/**
			 * button that indicates want to start a new game
			 * 
			 * @author All team
			 * @param evt
			 */
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				
				String ans = JOptionPane.showInputDialog(null, "Provide file name (.data) or leave empty for default save!");
				if (ans!=null) {
					if(!ans.isEmpty()) {
						
						SAVE = ans;
						KDController.saveGame(ans, true);
						Kingdomino kd = KDController.loadGame(ans);
						
						KingUI_Settings.initSettings();
						c1.show(contPanel, "2");
						
						KingdominoApplication.setKingdomino(kd);
					}
					else {
						KDController.saveGame(null, true);
						Kingdomino kd = KDController.loadGame(null);
						
						KingUI_Settings.initSettings();
						c1.show(contPanel, "2");
						
						KingdominoApplication.setKingdomino(kd);
					}
				}
				
			}
		});
		
		load.addActionListener(new java.awt.event.ActionListener() {
			/**
			 * button that indicates want to load an existing game
			 * 
			 * @author All team
			 * @param evt
			 */
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				
				String s = System.getProperty("user.dir");
				ArrayList<String> fileName = KDController.fileSearch(s);
				String files = "Choose file name (.data) from below to load game in progress\n\n";
				for(String str : fileName) {
					files = files+"    			        "+str+"\n";
				}
				files+=" \n";
				
				String providedFile = JOptionPane.showInputDialog(null, files);
				SAVE = providedFile;
				
				if(providedFile != null) {
					if(providedFile.isEmpty()) {
						JOptionPane.showMessageDialog(null, "Input is empty!");
					}
			
					else {
						if(!fileName.contains(providedFile)) {
							JOptionPane.showMessageDialog(null, "File not found ");			
						}else {
							Kingdomino kingdomino = KDController.loadGame(providedFile);
							
							if(kingdomino.hasStateMachine()) {
								
								if(!kingdomino.getStateMachine().getGamestatus().equals(Gamestatus.Initializing)) {
									
									frame.dispose();
									Kingdomino kd = KingdominoApplication.getKingdomino();
									Player player = kd.getCurrentGame().getNextPlayer();
									DominoInKingdom dnk = (DominoInKingdom) player.getKingdom().getTerritory(player.getKingdom().getTerritories().size()-1);
									new PlayingUI(player, dnk.getX(), dnk.getY(), dnk);
									
								}
								
								else {
									
									JOptionPane.showMessageDialog(null, "NO Game in Progress, START a new game");
									
								}
								
							}
							
							else {
								
								JOptionPane.showMessageDialog(null, "NO Game in Progress, START a new game");
								
							}
							
						}
					} 
				}
			}
		});
		
		search.addActionListener(new java.awt.event.ActionListener() {
			/**
			 * button that indicates want to view user statistics across 
			 * different existing game
			 * 
			 * @author All team
			 * @param evt
			 */
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				
				String s = System.getProperty("user.dir");
				ArrayList<String> fileName = KDController.fileSearch(s);
				String files = "Choose file name (.data) from below to load stats\n\n";
				for(String str : fileName) {
					files = files+"    			        "+str+"\n";
				}
				
				files+=" \n";
				String providedFile = JOptionPane.showInputDialog(null, files);
			
				if(providedFile != null) {
					if(providedFile.isEmpty()) {
						JOptionPane.showMessageDialog(null, "Input is empty!");
					}
			
					else {
						if(!fileName.contains(providedFile)) {
							JOptionPane.showMessageDialog(null, "File not found ");			
						}else {
							Kingdomino kd = KDController.loadGame(providedFile);
							KingUI_Stats.initComponents();
							c1.show(contPanel, "6");
							
							KingdominoApplication.setKingdomino(kd);
							
						}
					}
				}
				KingdominoApplication.setKingdomino(null);
				
			}
		});
	}
	
	
}
