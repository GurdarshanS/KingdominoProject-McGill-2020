package ca.mcgill.ecse223.kingdomino.controller;

import java.util.List; 
import java.util.*;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.model.Domino;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom;
import ca.mcgill.ecse223.kingdomino.model.KingdomTerritory;
import ca.mcgill.ecse223.kingdomino.model.Player;
import ca.mcgill.ecse223.kingdomino.model.Property;
import ca.mcgill.ecse223.kingdomino.model.TerrainType;

public class HowToUsePropertyIDMethods {
	public static void main (String [] args) {
		KDController.initiateEmptyGame();

//		Populate board with random dominos, beyond the 5x5 just to test all possibilities
		int[] id= {31,20,40,30,24,22,38,23,43,13,1,25,19,36};
		String[] dir= {"right","up","left","up","left","up","right","right","right","down","down","right","right","left"};
		int[] x= {-1,-1,2,3,2,3,1,0,2,-1,-2,-2,-4,-3};
		int[] y= {1,-1,0,0,-1,-2,-2,-3,-3,-2,-2,-4,-4,-2};
		
		Player player=KingdominoApplication.getKingdomino().getCurrentGame().getNextPlayer();

		for (int i=0;i<id.length;i++) {
			Domino dominoToPlace = KDController.getdominoByID(id[i]); 
			KDController.prePlaceDomino(player, dominoToPlace, x[i], y[i], dir[i]);
		}
		
//		call the controller method to identify the properties of this player
		KDController.identifyAllProperty(player);
		
//		call the controller to see all the properties of this player
		
		List<Property> allProp = KDController.getAllProperty(player);
		System.out.println("========= all properties of this player ==========\n");
		for (Property p:allProp) {
			System.out.println(p.getPropertyType());
			List<Domino> dominoInProp = p.getIncludedDominos();
			for (Domino d:dominoInProp) {
				System.out.println(d.getId());
			}
			System.out.println("----------------------------");
		}
		System.out.println("================================================\n");
		
//		call the controller method to sort player properties by terrain type
		List<Property> properties = KDController.getPropertyByTerrain(player, TerrainType.WheatField);
		

		System.out.println("========= wheat properties of this player ==========\n");
		for (Property p:properties) {
			System.out.println(p.getPropertyType());
			List<Domino> dominoInProp = p.getIncludedDominos();
			for (Domino d:dominoInProp) {
				System.out.println(d.getId());
			}
			System.out.println("----------------------------");
		}
		System.out.println("================================================\n");
		
//		call the controller method to compare a property against a TerrainType and expected domino IDs in property
		
		Property testP = properties.get(0);
		
		System.out.println("========= characteristics of this test property ==========\n");
		System.out.println(testP.getPropertyType());
		List<Domino> dominoInProp = testP.getIncludedDominos();
		for (Domino d:dominoInProp) {
			System.out.println(d.getId());
			}
		System.out.println("================= test against ===============================\n");
		
		TerrainType testTerrain=TerrainType.WheatField;
		int [] testIds= {43,38,22};
		boolean match = KDController.checkPropertyMatch(testTerrain,testIds,testP);
		System.out.println(testTerrain.name());
		System.out.println(Arrays.toString(testIds));
		
		System.out.println("================ test resetult ===========================");
		System.out.println(match);

		
	}
	
//	public static void identifyAllProperty(Player player) {
//		List<List<int[]>> terrainGroups = sortTerrain(player);
//		List<int[]> wheatGroup=terrainGroups.get(0);
//		List<int[]> swampGroup=terrainGroups.get(1);
//		List<int[]> grassGroup=terrainGroups.get(2);
//		List<int[]> mountainGroup=terrainGroups.get(3);
//		List<int[]> lakeGroup=terrainGroups.get(4);
//		List<int[]> forestGroup=terrainGroups.get(5);
//		
//		identifyOneProperty(wheatGroup,TerrainType.WheatField,player);
//		identifyOneProperty(swampGroup,TerrainType.Swamp,player);
//		identifyOneProperty(grassGroup,TerrainType.Grass,player);
//		identifyOneProperty(mountainGroup,TerrainType.Mountain,player);
//		identifyOneProperty(lakeGroup,TerrainType.Lake,player);
//		identifyOneProperty(forestGroup,TerrainType.Forest,player);
//	}
//		
//	public static void identifyOneProperty(List<int[]> terrainGroup,TerrainType t,Player player) {
//		
//		
//		List<List<int[]>> terrainClusters = clusterTerrain(terrainGroup); 
//		
//		List<List<Integer>> propertyIds = new ArrayList<List<Integer>>();
//		List<Integer> propertyCrowns = new ArrayList<Integer>();
//		List<Integer> propertySize = new ArrayList<Integer>();
//		
//		for (List<int[]> cluster:terrainClusters) {
//			int size=cluster.size();
//			List<Integer> ids = new ArrayList<Integer>();
//			int numCrown=0;
//			for (int[] info:cluster) {
//				int tempId=info[2];
//				if (!(ids.contains(tempId))) {
//					ids.add(tempId);
//				}
//				numCrown=numCrown+info[3];
//			}
//			
//			propertyIds.add(ids);
//			propertySize.add(size);
//			propertyCrowns.add(numCrown);
//		}
//		
////		System.out.println(propertyCrowns);
////		System.out.println(propertySize);
////		System.out.println(propertyIds);
//		
//		for (int i=0;i<propertyCrowns.size();i++) {
//			Property newProp = new Property(player.getKingdom());
//			newProp.setCrowns(propertyCrowns.get(i));
//			newProp.setSize(propertySize.get(i));
//			newProp.setPropertyType(t);
//			List<Integer> ids = propertyIds.get(i);
//			for (int j:ids) {
//				Domino aDomino = KDController.getdominoByID(j);
//				newProp.addIncludedDomino(aDomino);
//			}
//		}
//		
//	}
//	
//	public static List<List<int[]>> clusterTerrain(List<int[]> terrainGroup) {
//		int delta=1;
//		List<List<int[]>> firstPass = mergeTile(terrainGroup);
//		while (delta>0) {
//			
//			List<List<int[]>> secondPass=mergeCluster(firstPass);
//			delta = firstPass.size()-secondPass.size();
//			firstPass=secondPass;
//		}
//		
////		for (List<int[]> cluster:firstPass) {
////			for (int [] info:cluster) {
////				System.out.println(Arrays.toString(info));
////			}
////			System.out.println("---------------------------");
////		}
//		
//		return firstPass;
//	}
//	
//	public static List<List<int[]>> mergeCluster(List<List<int[]>> initialClusters){
//		List<Integer> ignoreIndex = new ArrayList<Integer>();
//		List<List<int[]>> mergedClusters = new ArrayList<List<int[]>>();
//		
//		for (int i=0;i<initialClusters.size()-1;i++) {
//			if (!(ignoreIndex.contains(i))) {
//				List<int[]> clusterA=initialClusters.get(i);
//				
//				for (int j=i+1;j<initialClusters.size();j++) {
//					if (!(ignoreIndex.contains(j))) {
//						List<int[]> clusterB=initialClusters.get(j);
//						
//						if (clusterAdjacent(clusterA,clusterB)) {
//							clusterA.addAll(clusterB);
//							mergedClusters.add(clusterA);
//							ignoreIndex.add(i);
//							ignoreIndex.add(j);
//							break;
//						}
//						
//					}
//				}
//			}
//		}
//		
//		for (int k=0;k<initialClusters.size();k++) {
//			if (!(ignoreIndex.contains(k))) {
//				mergedClusters.add(initialClusters.get(k));
//			}
//		}
//		
//		return mergedClusters;
//	}
//	
//	public static List<List<int[]>> mergeTile(List<int[]> initialTiles){
//		List<Integer> ignoreIndex = new ArrayList<Integer>();
//		List<List<int[]>> mergedList=new ArrayList<List<int[]>>();
//		
//		for (int i=0;i<initialTiles.size()-1;i++) {
//			if (!(ignoreIndex.contains(i))) {
//				int[] tileA = initialTiles.get(i);
//				List<int[]> tempCluster = new ArrayList<int[]>();
//						
//				for (int j=i+1;j<initialTiles.size();j++) {
//					if (!(ignoreIndex.contains(j))) {
//						int[] tileB=initialTiles.get(j);
//						if (tileAdjacent(tileA,tileB)) {
//							tempCluster.add(tileA);
//							tempCluster.add(tileB);
//							mergedList.add(tempCluster);
//							ignoreIndex.add(i);
//							ignoreIndex.add(j);
//							break;
//						}
//					}
//				}
//			}
//		}
//		for (int k=0;k<initialTiles.size();k++) {
//			List<int[]> tempCluster = new ArrayList<int[]>();
//			if (!(ignoreIndex.contains(k))) {
//				tempCluster.add(initialTiles.get(k));
//				mergedList.add(tempCluster);
//			}
//		}
//		
//		return mergedList;
//	}
//	
//	public static boolean tileAdjacent(int[] tileA,int []tileB) {
//		int x1=tileA[0];
//		int y1=tileA[1];
//		int x2=tileB[0];
//		int y2=tileB[1];
//		
//		int norm = KDController.L2NormSquared(x1, y1, x2, y2);
//		if (norm==1) {
//			return true;
//		}
//		else {
//			return false;
//		}
//		
//	}
//	
//	public static boolean clusterAdjacent(List<int[]> clusterA, List<int[]> clusterB) {
//		
//		for (int i=0;i<clusterA.size();i++) {
//			int [] tileA=clusterA.get(i);
//			for (int j=0;j<clusterB.size();j++) {
//				int[] tileB=clusterB.get(j);
//				if (tileAdjacent(tileA,tileB)) {
//					return true;
//				}
//			}
//		}
//		
//		return false;
//	}
//	
//	public static List<List<int[]>> sortTerrain(Player player){
//		List <int[]> wheatGroup = new ArrayList<int[]>();
//		List <int[]> swampGroup = new ArrayList<int[]>();
//		List <int[]> grassGroup = new ArrayList<int[]>();
//		List <int[]> mountainGroup = new ArrayList<int[]>();
//		List <int[]> lakeGroup = new ArrayList<int[]>();
//		List <int[]> forestGroup = new ArrayList<int[]>();
//		
//		List<KingdomTerritory> t = player.getKingdom().getTerritories();	
//		
//		for (KingdomTerritory each:t) {
//			if (each instanceof DominoInKingdom) {
//				
//				int[][] tileValues = extractTileValues((DominoInKingdom)each);
//				int [] leftValues = tileValues[0];
//				int [] rightValues=tileValues[1];
//				
//				TerrainType leftTerrain = ((DominoInKingdom) each).getDomino().getLeftTile();
//				TerrainType rightTerrain = ((DominoInKingdom) each).getDomino().getRightTile();		
//				
//				if (leftTerrain.equals(TerrainType.WheatField)) wheatGroup.add(leftValues);
//				if (leftTerrain.equals(TerrainType.Swamp)) swampGroup.add(leftValues);
//				if (leftTerrain.equals(TerrainType.Grass)) grassGroup.add(leftValues);
//				if (leftTerrain.equals(TerrainType.Mountain)) mountainGroup.add(leftValues);
//				if (leftTerrain.equals(TerrainType.Lake)) lakeGroup.add(leftValues);
//				if (leftTerrain.equals(TerrainType.Forest)) forestGroup.add(leftValues);
//				
//				if (rightTerrain.equals(TerrainType.WheatField)) wheatGroup.add(rightValues);
//				if (rightTerrain.equals(TerrainType.Swamp)) swampGroup.add(rightValues);
//				if (rightTerrain.equals(TerrainType.Grass)) grassGroup.add(rightValues);
//				if (rightTerrain.equals(TerrainType.Mountain)) mountainGroup.add(rightValues);
//				if (rightTerrain.equals(TerrainType.Lake)) lakeGroup.add(rightValues);
//				if (rightTerrain.equals(TerrainType.Forest)) forestGroup.add(rightValues);
//			}
//		}
//		
//		List<List<int[]>> allProperty = new ArrayList<List<int[]>>();
//		allProperty.add(wheatGroup);
//		allProperty.add(swampGroup);
//		allProperty.add(grassGroup);
//		allProperty.add(mountainGroup);
//		allProperty.add(lakeGroup);
//		allProperty.add(forestGroup);
//		
//		return allProperty;
//	}
//	
//	public static int[][] extractTileValues(DominoInKingdom dInK){
//		
//		int ID = dInK.getDomino().getId();
//
//		int leftX=dInK.getX();
//		int leftY=dInK.getY();
//		int leftCrown = dInK.getDomino().getLeftCrown();
//		int[] leftInfo= {leftX,leftY,ID,leftCrown};
//		
//		int [] otherPos=KDController.calculateOtherPos(dInK);
//		
//		int rightX=otherPos[0];
//		int rightY=otherPos[1];
//		int rightCrown=dInK.getDomino().getRightCrown();
//		int [] rightInfo= {rightX,rightY,ID,rightCrown};
//		
//		int[][] tileInfo= {leftInfo,rightInfo};
//		
//		return tileInfo;
//		
//	}
}
