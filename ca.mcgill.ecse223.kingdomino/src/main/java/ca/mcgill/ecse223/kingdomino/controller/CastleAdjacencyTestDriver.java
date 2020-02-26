package ca.mcgill.ecse223.kingdomino.controller;
import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.model.*;

import ca.mcgill.ecse223.kingdomino.model.Domino.DominoStatus;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom.DirectionKind;
import ca.mcgill.ecse223.kingdomino.model.Player.PlayerColor;

import java.util.*;

public class CastleAdjacencyTestDriver {
	
	public static void main (String[] args) {
		
		KDController.initiateEmptyGame();
		Kingdomino kd = KingdominoApplication.getKingdomino();
		Player player = kd.getCurrentGame().getNextPlayer();
				
//		#################################################
//		#		       Castle Adjacency Test			#
//		#################################################
		
//	      |  0 |  0 | up        | invalid |
//	      |  1 |  0 | right     | valid   |
//	      |  0 |  1 | left      | valid   |
//	      | -1 |  0 | down      | valid   |
//	      |  0 | -1 | right     | valid   |
//	      | -2 |  0 | right     | valid   |
//	      | -1 | -1 | up        | valid   |
//	      |  1 |  1 | right     | invalid |
//	      |  1 |  1 | down      | valid   |
//	      |  1 | -1 | left      | valid   |
		
		Domino dominoToPlace = KDController.getdominoByID(1); 
		KDController.prePlaceDomino(player, dominoToPlace, 0,0,"up");
		
//		view status of kingdom territories before castle adjacency verification
		List<KingdomTerritory> t = player.getKingdom().getTerritories();
		for (int i=1;i<t.size();i++) {
			DominoInKingdom dInK=(DominoInKingdom) t.get(i);
			System.out.println(dInK.getDomino().getStatus());
		}
		
		KDController.verifyCastleAdjacency(player);
		
//		view status of kingdom territores after castle adjacencyu verification
		for (int i=1;i<t.size();i++) {
			DominoInKingdom dInK=(DominoInKingdom) t.get(i);
			System.out.println(dInK.getDomino().getStatus());
		}
		
	}

}

