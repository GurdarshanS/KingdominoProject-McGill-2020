//package ca.mcgill.ecse223.kingdomino.controller;
//import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
//import ca.mcgill.ecse223.kingdomino.model.*;
//
//import java.util.*;
//
//public class NeigborAdjacencyTestDriver {
//	
//	public static void main (String[] args) {
//		
//		KDController.initiateEmptyGame();
//				
////		#################################################
////		#				neighbor adj test				#
////		#################################################
//		
////		populate kingdom with pre-existing dominos
//		int[] id= {27,38,1,22,46};
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
////		preplace a new domino
//		
//		int newId=42;
//		int newPosX=2;
//		int newPosY=2;
//		String newDir="up";
//		Domino newDominoToPlace = KDController.getdominoByID(newId); 
//		DominoInKingdom prePlacedDomino=KDController.prePlaceDomino(player, newDominoToPlace, newPosX,newPosY,newDir);
//	
//		KDController.verifyNeighborAdjacencyLastTerritory(player);
//		
//		System.out.println(newDominoToPlace.getStatus());
//		
////		Neighborhood leftNeighborhood = KDController.getDominoLeftNeighbors(t,prePlacedDomino);
////		System.out.println(leftNeighborhood);	
////
////		System.out.println("---------------------------------------------------------------------------------------------");
////		
////		Neighborhood rightNeighborhood = KDController.getDominoRightNeighbors(t,prePlacedDomino);
////		System.out.println(rightNeighborhood);
////		
////		List<TerrainType> leftTileNeighborTerrains = leftNeighborhood.getNeighborTerrainType();
////		List<TerrainType> rightTileNeighborTerrains = rightNeighborhood.getNeighborTerrainType();
////		
////		if (leftTileNeighborTerrains.isEmpty() && rightTileNeighborTerrains.isEmpty()) {
////			System.out.println("no neighbors at all");
////		}
////		
////		if (!leftTileNeighborTerrains.isEmpty()) {
////			for (TerrainType testTerrain:leftTileNeighborTerrains) {
////				if (testTerrain.name().equalsIgnoreCase(newDominoToPlace.getLeftTile().name())){
////					System.out.println("found left match!");
////				}
////			}
////		}
////		
////		if (!rightTileNeighborTerrains.isEmpty()) {
////			for (TerrainType testTerrain:rightTileNeighborTerrains) {
////				if (testTerrain.name().equalsIgnoreCase(newDominoToPlace.getRightTile().name())){
////					System.out.println("found right match!");
////				}
////			}
////		}		
//		
//	}
//
//}
//
