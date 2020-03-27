//package ca.mcgill.ecse223.kingdomino.controller;
//import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
//import ca.mcgill.ecse223.kingdomino.model.*;
//
//import ca.mcgill.ecse223.kingdomino.model.Domino.DominoStatus;
//import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom.DirectionKind;
//import ca.mcgill.ecse223.kingdomino.model.Player.PlayerColor;
//
//import java.util.*;
//
//public class OverLapTestDriver {
//	
//	public static void main (String[] args) {
//		
//		KDController.initiateEmptyGame();
//				
////		#################################################
////		#				Overlap  Testing				#
////		#################################################
//		
//		
//		int[] id= {27,17,1,22,46};
//		String[] dir= {"up","down","right","left","right"};
//		int[] x = {0,3,0,3,-1};
//		int[] y = {2,3,1,1,-1};
//		
//		Player player=KingdominoApplication.getKingdomino().getCurrentGame().getNextPlayer();
//		
//		for (int i=0;i<id.length;i++) {
//			Domino dominoToPlace = KDController.getdominoByID(id[i]); 
//			KDController.prePlaceDomino(player, dominoToPlace, x[i], y[i], dir[i]);
//		}
//		
//		List<KingdomTerritory> t =player.getKingdom().getTerritories();
//	
////		see status of dominos in kingdom before verification
//		System.out.println("Domino In Kingdom status before overlap verification: ");
//		for (int i=1;i<t.size();i++) {
//			DominoInKingdom dInK=(DominoInKingdom) t.get(i);
//			System.out.println(dInK.getDomino().getStatus());
//		}
//		
////		verify dominos in kingdom for grid size, changing status to either CorrectlyPreplaced or ErroneousPrePlaced
////		KDController.verifyNoOverlapAllTerritories(player);
//		KDController.verifyNoOverlapAllTerritories(player);
//		
////		see status of dominos in kingdom after verification
//		System.out.println("---------------------------------------------------");
//		System.out.println("Domino In Kingdom status after overlap verification: ");
//		for (int i=1;i<t.size();i++) {
//			DominoInKingdom dInK=(DominoInKingdom) t.get(i);
//			System.out.println(dInK.getDomino().getStatus());
//		}
//		
//	}
//
//}
//
