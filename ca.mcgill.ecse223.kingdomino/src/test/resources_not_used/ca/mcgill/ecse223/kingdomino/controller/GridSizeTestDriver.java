package ca.mcgill.ecse223.kingdomino.controller;
import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.model.*;

import java.util.*;

public class GridSizeTestDriver {
	
	public static void main (String[] args) {
		
		KDController.initiateEmptyGame();
				
//		#################################################
//		#				Grid Size Testing				#
//		#################################################
		int[] id= {7,8,20,22,23,24,30,31,32,38,40,43};
		String[] dir= {"left","up","up","up","right","left","up","down","down","right","left","right"};
		int[] x= {2,-1,-1,3,0,2,3,-1,0,1,2,2};
		int[] y= {1,-3,-1,-2,-3,-1,0,1,-1,-2,0,-3};
		
		Player player=KingdominoApplication.getKingdomino().getCurrentGame().getNextPlayer();
//		
		for (int i=0;i<id.length;i++) {
			Domino dominoToPlace = KDController.getdominoByID(id[i]); 
			KDController.prePlaceDomino(player, dominoToPlace, x[i], y[i], dir[i]);
		}
		
		List<KingdomTerritory> t =player.getKingdom().getTerritories();
	
//		see status of dominos in kingdom before verification
		System.out.println("Domino In Kingdom status before size verification: ");
		for (int i=1;i<t.size();i++) {
			DominoInKingdom dInK=(DominoInKingdom) t.get(i);
			System.out.println(dInK.getDomino().getStatus());
		}
//		verify dominos in kingdom for grid size, changing status to either CorrectlyPreplaced or ErroneousPrePlaced
		KDController.verifyGridSizeAllKingdom(player);
		
//		see status of dominos in kingdom after verification
		System.out.println("---------------------------------------------------");
		System.out.println("Domino In Kingdom status after size verification: ");
		for (int i=1;i<t.size();i++) {
			DominoInKingdom dInK=(DominoInKingdom) t.get(i);
			System.out.println(dInK.getDomino().getStatus());
		}
		
//		get output of the verification result, either "valid" or "invalid"
		String validity = KDController.getKingdomVerificationResult(player);
		System.out.println("---------------------------------------------------");
		System.out.println("size verification conclusion: kingdom size is "+validity);
		
	}

}

