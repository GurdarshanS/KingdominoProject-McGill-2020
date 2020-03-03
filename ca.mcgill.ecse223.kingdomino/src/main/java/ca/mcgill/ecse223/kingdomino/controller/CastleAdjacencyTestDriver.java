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
		System.out.println(player.getKingdom().getTerritories());
				
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
		KDController.prePlaceDomino(player, dominoToPlace, 1,1,"right");
		System.out.println("added domino");
		System.out.println(player.getKingdom().getTerritories());


		
		KDController.verifyCastleAdjacency(player);
		
		String validity=KDController.getKingdomVerificationResult(player);
		System.out.println(validity);
		
	}

}

