package ca.mcgill.ecse223.kingdomino.view;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

@SuppressWarnings("serial")
public class KingUI_Pile extends JFrame {

	public KingUI_Pile() {
		
		initPile();
	}
	
	public void initPile() {

		String player = "player_name";
		
		JFrame frame = new JFrame();
		frame = KingUI_Main.frame;
		frame.setSize(1350, 850);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
}
