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
//public class ControllerDriver {
//	
//	public static void main (String[] args) {
//		KDController.initiateEmptyGame();
////		Kingdomino kd=KingdominoApplication.getKingdomino();
//		
//		int[] id= {7,8,20,22,23,24,30,31,32,38,40,43};
//		String[] direction= {"left","up","up","up","right","left","up","right","down","right","left","right"};
//		int[] posx= {2,-1,-1,3,0,2,3,-1,0,1,2,2};
//		int[] posy= {1,-3,-1,-2,-3,-1,0,1,-1,-2,0,-3};
//		
//		Kingdomino kd = KingdominoApplication.getKingdomino();
//		Player player = kd.getCurrentGame().getNextPlayer();
//		
//		for (int i=0;i<id.length;i++) {
//			int pid=id[i];
//			String pdir=direction[i];
//			int x=posx[i];
//			int y=posy[i];
//			
//			Domino dominoToPlace = KDController.getdominoByID(pid);
//			KDController.prePlaceDomino(player, dominoToPlace, x, y, pdir);		
//			}
//		
//		String validity=KDController.verifyKingdom(player);
//		System.out.println(validity);
//	}
//
//}
//
