package ca.mcgill.ecse223.kingdomino.controller;

import java.util.List;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.model.Domino;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom;
import ca.mcgill.ecse223.kingdomino.model.KingdomTerritory;
import ca.mcgill.ecse223.kingdomino.model.Player;
import ca.mcgill.ecse223.kingdomino.model.Domino.DominoStatus;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom.DirectionKind;

public class DiscardTestDriver2 {
	
	public static void main (String [] args) {
//		#################################################
//		#				Discard  Testing				#
//		#################################################
			
			boolean shouldDiscard=true;
			KDController.initiateEmptyGame();
			Player player=KingdominoApplication.getKingdomino().getCurrentGame().getNextPlayer();
			
			int testId=48;
			System.out.println("============================= Discard Test Domino "+testId+" ==================================");
			Domino dominoToPlace = KDController.getdominoByID(41);	//change id here according to table below
			

			int[] id= {7,20,22,23,24,30,31,32,38,40,43};
			String[] dir= {"left","up","up","right","left","up","right","down","right","left","right"};
			int[] x = {2,-1,3,0,2,3,-1,0,1,2,2};
			int[] y = {1,-1,-2,-3,-1,0,1,-1,-2,0,-3};			
			
			for (int i=0;i<id.length;i++) {
				Domino existingDominoToPlace = KDController.getdominoByID(id[i]); 
				KDController.prePlaceDomino(player, existingDominoToPlace, x[i], y[i], dir[i]);
			}
			
			boolean castleAvailable=KDQuery.CastleNeighborhoodAvailable(player);
			List<KingdomTerritory> t =player.getKingdom().getTerritories();

//			placing new test domino and start iterating through all possible positions and orientations
			KDController.prePlaceDomino(player, dominoToPlace, 0, 1, "up");
//			KingdomTerritory territoryToChange=t.get(t.size()-1);
			System.out.println(t);
		
			KDController.verifyNoOverlapLastTerritory(player);
			String validity=KDController.getKingdomVerificationResult(player);
			System.out.println(validity);
		}

}
