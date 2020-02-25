package ca.mcgill.ecse223.kingdomino.controller;
import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.model.*;

import ca.mcgill.ecse223.kingdomino.model.Domino.DominoStatus;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom.DirectionKind;
import ca.mcgill.ecse223.kingdomino.model.Player.PlayerColor;

import java.util.*;

public class OverLapTestDriver {
	
	public static void main (String[] args) {
		
		KDController.initiateEmptyGame();
				
//		#################################################
//		#				Overlap  Testing				#
//		#################################################
		int[] id= {27,17,1,22,46};
		String[] dir= {"up","down","right","left","right"};
		int[] x = {0,3,0,3,-1};
		int[] y = {2,3,1,1,-1};
		
		Player player=KingdominoApplication.getKingdomino().getCurrentGame().getNextPlayer();
//		
		for (int i=0;i<id.length;i++) {
			Domino dominoToPlace = KDController.getdominoByID(id[i]); 
			KDController.prePlaceDomino(player, dominoToPlace, x[i], y[i], dir[i]);
		}
		
		List<KingdomTerritory> t =player.getKingdom().getTerritories();
		
	
//		see status of dominos in kingdom before verification
		System.out.println("Domino In Kingdom status before overlap verification: ");
		for (int i=1;i<t.size();i++) {
			DominoInKingdom dInK=(DominoInKingdom) t.get(i);
			System.out.println(dInK.getDomino().getStatus());
		}
//		verify dominos in kingdom for grid size, changing status to either CorrectlyPreplaced or ErroneousPrePlaced
		KDController.verifyNoOverlap(player);
////		
//		see status of dominos in kingdom after verification
		System.out.println("---------------------------------------------------");
		System.out.println("Domino In Kingdom status after overlap verification: ");
		for (int i=1;i<t.size();i++) {
			DominoInKingdom dInK=(DominoInKingdom) t.get(i);
			System.out.println(dInK.getDomino().getStatus());
		}
//		
////		get output of the verification result, either "valid" or "invalid"
//		String validity = KDController.getKingdomVerificationResult(player);
//		System.out.println("---------------------------------------------------");
//		System.out.println("size verification conclusion: kingdom size is "+validity);
		
		
		

		
//		List<KingdomTerritory> t =player.getKingdom().getTerritories();
//		
//		for (int i=1;i<t.size();i++) {
//			DominoInKingdom dInK=(DominoInKingdom) t.get(i);
//			System.out.println(dInK.getDomino().getStatus());
//		}
//		
//		KDController.verifyGridSize(player);
//		
//		System.out.println("-------------------------");
//		
//		for (int i=1;i<t.size();i++) {
//			DominoInKingdom dInK=(DominoInKingdom) t.get(i);
//			System.out.println(dInK.getDomino().getStatus());
//		}

		
		
//		  | id | x  | y  | direction | result  |
//	      | 15 |  1 |  2 | right     | valid   |
//	      | 15 |  2 |  2 | right     | invalid |
//	      | 20 |  2 |  2 | down      | invalid |
//	      | 12 | -1 |  0 | up        | valid   |
//	      | 12 | -1 |  0 | down      | invalid |
//	      | 44 |  2 |  0 | down      | valid   |
//	      | 42 |  2 |  2 | up        | valid   |
//	      | 29 |  1 |  2 | left      | invalid |
//	      | 48 | -1 |  3 | down      | valid   |
//	      | 43 |  1 | -1 | left      | invalid |
		
//		int [] id2= {15,15,20,12,12,44,42,29,48,43};
//		int [] x2= {1,2,2,-1,-1,2,2,1,-1,1};
//		int [] y2= {2,2,2,0,0,0,2,2,3,-1};
//		String [] dir2= {"right","right","down","up","down","down","up","left","down","left"};
//		
//		int k=0;
//
//		Domino dominoToPlace = KDController.getdominoByID(id2[k]);
//		KDController.prePlaceDomino(player, dominoToPlace, x2[k], y2[k], dir2[k]);
//		System.out.println(dominoToPlace.getStatus());
		
//		List<KingdomTerritory> t =kd.getCurrentGame().getNextPlayer().getKingdom().getTerritories();
//		System.out.println(t);
//		
//		System.out.println("--------------------------------");
//		DominoInKingdom last = (DominoInKingdom) t.get(t.size()-1);
//		System.out.println(last.getDomino().getStatus());
		
//		KDController.verifyNoOverlap(player);
//		
//		System.out.println("--------------------------------");
//		System.out.println(last.getDomino().getStatus());

		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
////		Kingdomino kd=KingdominoApplication.getKingdomino();
//		
//		int[] id= {7,8,20,22,23,24,30,31,32,38,40,43};
//		String[] direction= {"left","up","up","up","right","left","up","right","down","right","left","right"};
//		int[] posx= {2,-1,-1,3,0,2,3,-1,0,1,2,2};
//		int[] posy= {1,-3,-1,-2,-3,-1,0,1,-1,-2,0,-3};
		
	}

}

