package ca.mcgill.ecse223.kingdomino.view;

import javax.swing.JFrame;

import ca.mcgill.ecse223.kingdomino.model.Domino;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom;
import ca.mcgill.ecse223.kingdomino.model.Game;
import ca.mcgill.ecse223.kingdomino.model.Kingdomino;
import ca.mcgill.ecse223.kingdomino.model.Player;
import ca.mcgill.ecse223.kingdomino.model.TerrainType;
import ca.mcgill.ecse223.kingdomino.model.Kingdom;

public class BoardTest extends JFrame {
		
	public static void main(String[] args) {
		
		Rotation r1 = new Rotation(new Player(new Game(48, new Kingdomino())), 1,1, new DominoInKingdom(1,0, new Kingdom(new Player(new Game(48, new Kingdomino()))),new Domino(4, TerrainType.Grass, TerrainType.Mountain, 2, new Game(48, new Kingdomino()))));
		r1.frameR.setVisible(true);
		
	}
}
