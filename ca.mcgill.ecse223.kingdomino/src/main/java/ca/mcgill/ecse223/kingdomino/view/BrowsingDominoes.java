package ca.mcgill.ecse223.kingdomino.view;

import java.awt.Font;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

import ca.mcgill.ecse223.kingdomino.controller.KDController;
import ca.mcgill.ecse223.kingdomino.model.Domino;

public class BrowsingDominoes extends JFrame{
	
	public JFrame frame = new JFrame("Kingdomino");

	
	public BrowsingDominoes() {
		frame.setVisible(true);
		frame.setSize(1300,800);
		plotDoms();
	}
	
	/**
	 * constructs JPanel for displaying  the list of all
	 * dominos in the game
	 * 
	 * @author All team
	 * @param none
	 */
	
	private void plotDoms() {
			JTextArea textArea = new JTextArea();
		  JScrollPane pan = new JScrollPane(textArea);
		  pan.setBounds(5, 5, 1300, 800);
		  List<Domino> doms = KDController.browseDominos();
		  textArea.setFont(new Font("Times", Font.PLAIN, 16));
		
		  for(int i=0; i<doms.size(); i++) { 
			  Domino dom = doms.get(i); 
			  
			  String s = String.format("Domino ID: %1$-10d \t Right Tile: %2$-40s \t Right Crown: %3$-10d \t Left Tile: %4$-40s \t Left Crown: %5$-10d \t\n\n", 
					  					dom.getId(),dom.getRightTile(), dom.getRightCrown(), dom.getLeftTile(), dom.getLeftCrown());
			  textArea.append(s);
		  }
		  
		  frame.add(pan);
		 
	}
}