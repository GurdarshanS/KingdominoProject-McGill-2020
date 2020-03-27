//package ca.mcgill.ecse223.kingdomino.controller;
//
//import java.util.Arrays;
//
//import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
//import ca.mcgill.ecse223.kingdomino.model.Domino;
//import ca.mcgill.ecse223.kingdomino.model.Player;
//
//import java.util.*;
//public class bonusScoreTestDriver {
//	
//	public static void main (String [] args) {
//		
//		KDController.initiateEmptyGame();
//		
//		int[] id= {7,6,14,23,21,48,1,16,22,46,41,12};
//		String[] dir= {"right","down","right","up","left","down","right","down","left","left","right"};
//		int[] x= {0,2,0,-2,2,-1,-2,0,-1,-1,1,1};
//		int[] y= {1,2,2,0,0,1,2,4,3,4,4,3};
//		
//		Player player=KingdominoApplication.getKingdomino().getCurrentGame().getNextPlayer();
//	
//		for (int i=0;i<id.length;i++) {
//			Domino dominoToPlace = KDController.getdominoByID(id[i]); 
//			KDController.prePlaceDomino(player, dominoToPlace, x[i], y[i], dir[i]);
//		}
//		
//		int newId=16;
//		int newX=0;
//		int newY=-1;
//		String direction="down";
//		Domino newDominoToPlace=KDController.getdominoByID(newId);
////		KDController.prePlaceDomino(player, newDominoToPlace, newX, newY, direction);
//		
//		boolean middle = KDController.isMiddleKingdom(player);
//		System.out.println(middle);
//		
//		boolean harmony=KDController.isHarmony(player);
//		System.out.println(harmony);
//		
//		
//		
//		
//	}
//
//}
