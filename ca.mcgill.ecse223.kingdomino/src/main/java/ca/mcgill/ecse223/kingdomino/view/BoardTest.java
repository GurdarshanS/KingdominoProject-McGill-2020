package ca.mcgill.ecse223.kingdomino.view;

import java.util.List;

import javax.swing.JFrame;

import ca.mcgill.ecse223.kingdomino.controller.KDController;
import ca.mcgill.ecse223.kingdomino.controller.View;
import ca.mcgill.ecse223.kingdomino.model.Domino;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom;
import ca.mcgill.ecse223.kingdomino.model.Game;
import ca.mcgill.ecse223.kingdomino.model.Kingdomino;
import ca.mcgill.ecse223.kingdomino.model.Player;
import ca.mcgill.ecse223.kingdomino.model.TerrainType;
import ca.mcgill.ecse223.kingdomino.model.Kingdom;
import ca.mcgill.ecse223.kingdomino.model.KingdomTerritory;

public class BoardTest extends JFrame {
		
	public static void main(String[] args) {
		
		Kingdomino kd = KDController.loadGame("tryingBoard.data");
		boolean used = KDController.manipulateFirstSM(-3, 3, "up");
		List<KingdomTerritory> t1 = kd.getCurrentGame().getNextPlayer().getKingdom().getTerritories();
		Player player = kd.getCurrentGame().getNextPlayer();
		DominoInKingdom dom = (DominoInKingdom) t1.get(1);
		int x = dom.getX();
		int y = dom.getY();
		Rotation r1 = new Rotation(player, x, y, dom);
		r1.frameR.setVisible(true);
		View.printPlayers(kd);
		
	}
}
