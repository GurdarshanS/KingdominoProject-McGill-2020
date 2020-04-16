package ca.mcgill.ecse223.kingdomino.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.Border;

@SuppressWarnings("serial")
public class KingUI_Pile extends JFrame {

	public static void main(String[] args) {
		
		initPile();
	}
	public KingUI_Pile() {
		
		initPile();
	}
	
	public static void initPile() {

		Boolean tileHasCrown = true;
		Boolean dominoInPile = true;
		Border border = BorderFactory.createLineBorder(Color.DARK_GRAY);
		
		JFrame frame = new JFrame();
		frame = KingUI_Main.frame;
		frame.setSize(1350, 850);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		JLabel pile = new JLabel("Domino Pile");
		pile.setFont(new Font("Times", Font.BOLD, 50));
		JPanel p = new JPanel();
		
		JPanel dominos = new JPanel();
		JPanel domino[] = new JPanel[48];
		JPanel tile[] = new JPanel[96];
		
		for (int i = 0; i < 48; i++) {
			
			if (dominoInPile) {
				
				tile[i] = new JPanel();
				tile[i].setBackground(Color.LIGHT_GRAY);
				tile[i].setBorder(border);
				if (tileHasCrown) {
					JPanel crown = new JPanel();
					crown.setBackground(Color.yellow);
					tile[i].add(crown);
				}
				tile[i+48] = new JPanel();
				tile[i+48].setBackground(Color.GRAY);
				tile[i+48].setBorder(border);
				if (tileHasCrown) {
					JPanel crown = new JPanel();
					crown.setBackground(Color.yellow);
					tile[i+48].add(crown);
				}
				domino[i] = new JPanel(new GridLayout(1,2));
				domino[i].add(tile[i]);
				domino[i].add(tile[i+48]);
				dominos.add(domino[i]);
			}
		}
		
		
		
		p.add(pile);
		frame.add(p, BorderLayout.NORTH);
		frame.add(dominos, BorderLayout.CENTER);
		frame.setVisible(true);
	}
}
