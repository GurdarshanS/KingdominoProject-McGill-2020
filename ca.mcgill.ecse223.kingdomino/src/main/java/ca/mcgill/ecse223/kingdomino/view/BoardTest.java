package ca.mcgill.ecse223.kingdomino.view;

import javax.swing.JFrame;

import ca.mcgill.ecse223.kingdomino.model.Domino;
import ca.mcgill.ecse223.kingdomino.model.Game;
import ca.mcgill.ecse223.kingdomino.model.Kingdomino;
import ca.mcgill.ecse223.kingdomino.model.TerrainType;

public class BoardTest extends JFrame {
		
	public static void main(String[] args) {
		
		Rotation r1 = new Rotation(1,1, new Domino(4, TerrainType.Grass, TerrainType.Mountain, 2, new Game(48, new Kingdomino())), "down");
		r1.frameR.setVisible(true);
		
	}
}
