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
import ca.mcgill.ecse223.kingdomino.model.KingdomTerritory;
import ca.mcgill.ecse223.kingdomino.model.Kingdomino;
import ca.mcgill.ecse223.kingdomino.model.Player;

public class KingUI_Main {
	
	public static JFrame frame = new JFrame("Kingdomino");
	public static JPanel contPanel = new JPanel();
	public static CardLayout c1 = new CardLayout();
	public static int playerNum = 4;
	public static ArrayList<String> bonusOptions = new ArrayList<String>();
	
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
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				KDController.loadGame(null);
				KingUI_Settings.initSettings();
				c1.show(contPanel, "2");
				
			}
		});
		
		load.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				
				String s = System.getProperty("user.dir");
				System.out.println(s);
				ArrayList<String> fileName = KDController.fileSearch(s);
				System.out.println(fileName);
				String files = "Provide file name (.data) to load!\n\n";
				for(String str : fileName) {
					files = files+"    			        "+str+"\n";
				}
				String providedFile = JOptionPane.showInputDialog(null, files);

				if(providedFile != null) {
					if(providedFile.isEmpty()) {
						JOptionPane.showMessageDialog(null, "Input is empty!");
					}
					else {
						if(!fileName.contains(providedFile)) {
							JOptionPane.showMessageDialog(null, "File not found ");			
						}else {
							KDController.loadGame(providedFile);
							frame.dispose();
							Kingdomino kd = KingdominoApplication.getKingdomino();
							Player player = kd.getCurrentGame().getNextPlayer();
							DominoInKingdom dnk = (DominoInKingdom) player.getKingdom().getTerritory(player.getKingdom().getTerritories().size()-1);
							new PlayingUI(player, -3, 3, dnk);
						}
					} 
				}
			}
		});
		
		search.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				KDController.loadGame(null);
				KingUI_Stats.initComponents();
				c1.show(contPanel, "6");
			}
		});
	}
	
	
}
