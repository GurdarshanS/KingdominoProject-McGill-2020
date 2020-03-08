package ca.mcgill.ecse223.kingdomino.controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.model.BonusOption;
import ca.mcgill.ecse223.kingdomino.model.Castle;
import ca.mcgill.ecse223.kingdomino.model.Domino;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom.DirectionKind;
import ca.mcgill.ecse223.kingdomino.model.Game;
import ca.mcgill.ecse223.kingdomino.model.Kingdom;
import ca.mcgill.ecse223.kingdomino.model.KingdomTerritory;
import ca.mcgill.ecse223.kingdomino.model.Kingdomino;
import ca.mcgill.ecse223.kingdomino.model.Player;
import ca.mcgill.ecse223.kingdomino.model.Property;
import ca.mcgill.ecse223.kingdomino.model.TerrainType;
import ca.mcgill.ecse223.kingdomino.model.Domino.DominoStatus;
import ca.mcgill.ecse223.kingdomino.model.Player.PlayerColor;

public class KDController {

	public static void initializeGame() {
		// Initialize empty game
		Kingdomino kingdomino = new Kingdomino();
		Game game = new Game(48, kingdomino);
		game.setNumberOfPlayers(4);
		kingdomino.setCurrentGame(game);
		// Populate game
		addDefaultUsersAndPlayers(game);
		createAllDominoes(game);
		game.setNextPlayer(game.getPlayer(0));
		KingdominoApplication.setKingdomino(kingdomino);
	}
	
	/**
	 * 
	 * This method identifies all the properties of a player's kingdom
	 * and creates the corresponding Property objects
	 * 
	 * @see IdentifyProperties.feature
	 * @author Jing Han 260528152
	 * @param player
	 * @return void
	 */
	
	public static void identifyAllProperty(Player player) {
		List<List<int[]>> terrainGroups = sortTerrain(player);
		List<int[]> wheatGroup=terrainGroups.get(0);
		List<int[]> swampGroup=terrainGroups.get(1);
		List<int[]> grassGroup=terrainGroups.get(2);
		List<int[]> mountainGroup=terrainGroups.get(3);
		List<int[]> lakeGroup=terrainGroups.get(4);
		List<int[]> forestGroup=terrainGroups.get(5);
		
		identifyOneProperty(wheatGroup,TerrainType.WheatField,player);
		identifyOneProperty(swampGroup,TerrainType.Swamp,player);
		identifyOneProperty(grassGroup,TerrainType.Grass,player);
		identifyOneProperty(mountainGroup,TerrainType.Mountain,player);
		identifyOneProperty(lakeGroup,TerrainType.Lake,player);
		identifyOneProperty(forestGroup,TerrainType.Forest,player);
	}
	
	/**
	 * 
	 * This method retrieves all the properties of a player's kingdom
	 * 
	 * @see  - no features explicitly
	 * @author Jing Han 260528152
	 * @param player
	 * @return List<Property>
	 */
	
	public static List<Property> getAllProperty(Player player){
		return player.getKingdom().getProperties();
	}
	
	/**
	 * 
	 * This method retrieves all Properties of a particular 
	 * TerrainType of a player's kingdom
	 * 
	 * @see  - no features explicitly
	 * @author Jing Han 260528152
	 * @param player
	 * @param t
	 * @return filteredProp
	 */
	
	public static List<Property> getPropertyByTerrain(Player player, TerrainType t) {
		
		List<Property> allProp=getAllProperty(player);
		List<Property> filteredProp = new ArrayList<Property>();
		
		for (Property testProp:allProp) {
			if (testProp.getPropertyType().equals(t)) {
				filteredProp.add(testProp);
			}
		}
		return filteredProp;
	}
	
	public static boolean checkPropertyMatch(TerrainType testTerrain, int[] testIds, Property property) {
		
		boolean match=true;
		
		if (!(testTerrain.equals(property.getPropertyType()))) {
			match=false;
		}
		else {
			List<Domino> dInP=property.getIncludedDominos();
			List<Integer> ids = new ArrayList<Integer>();
			for (Domino d: dInP) {
				ids.add(d.getId());
			}
			
			if (ids.size()!=testIds.length) {
				match=false;
			}
			else {
				for (int i:testIds) {
					if (!(ids.contains(i))) {
						match=false;
						break;
					}
				}
			}
		}
		return match;
	}
	
	/**
	 * 
	 * This helper method checks of two KingdomTerritories overlap
	 * 
	 * @see verifyNoOverlapAllTerritories, verifyNoOverlapLastTerritory
	 * @author Jing Han 260528152
	 * @param a
	 * @param b
	 * @return boolean
	 */

	private static boolean checkOverlap(KingdomTerritory a, KingdomTerritory b) {
		
		int[] otherA=calculateOtherPos(a);
		int[] otherB=calculateOtherPos(b);
		
		int ax1=a.getX();
		int ay1=a.getY();
		int ax2=otherA[0];
		int ay2=otherA[1];
		
//		System.out.println("("+ax1+","+ay1+")");
//		System.out.println("("+ax2+","+ay2+")");
		
		int bx1=b.getX();
		int by1=b.getY();
		int bx2=otherB[0];
		int by2=otherB[1];
		
//		System.out.println("("+bx1+","+by1+")");
//		System.out.println("("+bx2+","+by2+")");
		
		if (ax1==bx1 && ay1==by1) {
			return true;
		}
		else if (ax1==bx2 && ay1==by2) {
			return true;
		}
		else if (ax2==bx1 && ay2==by1) {
			return true;
		}
		else if (ax2==bx2 && ay2==by2) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * 
	 * This helper method calculates the square of the L2 dist btwn two coordinates
	 * 
	 * @see all verification methods
	 * @author Jing Han 260528152
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2 
	 * @return norm
	 */
	
	public static int L2NormSquared(int x1, int y1, int x2, int y2) {
		int deltaX=x2-x1;
		int deltaY=y2-y1;
		
		int norm = deltaX*deltaX+deltaY*deltaY;
		
		return norm;
	}

	/**
	 * 
	 * This helper method calculates the coordinate of a Domino's rightTile
	 * based on the Domin's leftTile and orientation
	 * 
	 * @see all verification methods
	 * @author Jing Han 260528152
	 * @param d
	 * @return coord2
	 */
	

	public static int[] calculateOtherPos(KingdomTerritory d) {
		
		int [] coord2 = new int[2];
		if (d instanceof Castle) {
			coord2[0]=0;
			coord2[1]=0;
		}
		
		else {
			int x2;
			int y2;
			
			if (((DominoInKingdom) d).getDirection().equals(DirectionKind.Right)) {
				x2=d.getX()+1;
				y2=d.getY();
			}
			else if (((DominoInKingdom) d).getDirection().equals(DirectionKind.Up)) {
				x2=d.getX();
				y2=d.getY()+1;
			}
			else if (((DominoInKingdom) d).getDirection().equals(DirectionKind.Left)) {
				x2=d.getX()-1;
				y2=d.getY();
			}
			else {
				x2=d.getX();
				y2=d.getY()-1;
			}
			
			coord2[0]=x2;
			coord2[1]=y2;
		}
		return coord2;
	}
	
	/**
	 * 
	 * This method identifies one type of property by TerrainType
	 *  of a player's kingdom and creates the corresponding Property object
	 * 
	 * @see IdentifyProperties.feature
	 * @author Jing Han 260528152
	 * @param terrainGroup
	 * @param t
	 * @param player
	 * @return void
	 */
	
		
	private static void identifyOneProperty(List<int[]> terrainGroup,TerrainType t,Player player) {
		
		List<List<int[]>> terrainClusters = clusterTerrain(terrainGroup); 
		
		List<List<Integer>> propertyIds = new ArrayList<List<Integer>>();
		List<Integer> propertyCrowns = new ArrayList<Integer>();
		List<Integer> propertySize = new ArrayList<Integer>();
		
		for (List<int[]> cluster:terrainClusters) {
			int size=cluster.size();
			List<Integer> ids = new ArrayList<Integer>();
			int numCrown=0;
			for (int[] info:cluster) {
				int tempId=info[2];
				if (!(ids.contains(tempId))) {
					ids.add(tempId);
				}
				numCrown=numCrown+info[3];
			}
			
			propertyIds.add(ids);
			propertySize.add(size);
			propertyCrowns.add(numCrown);
		}
		
//		System.out.println(propertyCrowns);
//		System.out.println(propertySize);
//		System.out.println(propertyIds);
		
		for (int i=0;i<propertyCrowns.size();i++) {
			Property newProp = new Property(player.getKingdom());
			newProp.setCrowns(propertyCrowns.get(i));
			newProp.setSize(propertySize.get(i));
			newProp.setPropertyType(t);
			List<Integer> ids = propertyIds.get(i);
			for (int j:ids) {
				Domino aDomino = getdominoByID(j);
				newProp.addIncludedDomino(aDomino);
			}
		}
		
	}
	
	/**
	 * 
	 * This method clusters the all the of a player's kingdom by
	 * their terrain group into the largest possible clusters
	 * 
	 * @see IdentifyProperties.feature
	 * @author Jing Han 260528152
	 * @param terrainGroup
	 * @return firstPass
	 */
	
	private static List<List<int[]>> clusterTerrain(List<int[]> terrainGroup) {
		int delta=1;
		List<List<int[]>> firstPass = mergeTile(terrainGroup);
		while (delta>0) {
			
			List<List<int[]>> secondPass=mergeCluster(firstPass);
			delta = firstPass.size()-secondPass.size();
			firstPass=secondPass;
		}
		
//		for (List<int[]> cluster:firstPass) {
//			for (int [] info:cluster) {
//				System.out.println(Arrays.toString(info));
//			}
//			System.out.println("---------------------------");
//		}
		
		return firstPass;
	}
	
	/**
	 * 
	 * This method merges two clusters of the same type if they 
	 * share an adjacent edge
	 * 
	 * @see IdentifyProperties.feature
	 * @author Jing Han 260528152
	 * @param initialClusters
	 * @return mergedClusters
	 */
	
	private static List<List<int[]>> mergeCluster(List<List<int[]>> initialClusters){
		List<Integer> ignoreIndex = new ArrayList<Integer>();
		List<List<int[]>> mergedClusters = new ArrayList<List<int[]>>();
		
		for (int i=0;i<initialClusters.size()-1;i++) {
			if (!(ignoreIndex.contains(i))) {
				List<int[]> clusterA=initialClusters.get(i);
				
				for (int j=i+1;j<initialClusters.size();j++) {
					if (!(ignoreIndex.contains(j))) {
						List<int[]> clusterB=initialClusters.get(j);
						
						if (clusterAdjacent(clusterA,clusterB)) {
							clusterA.addAll(clusterB);
							mergedClusters.add(clusterA);
							ignoreIndex.add(i);
							ignoreIndex.add(j);
							break;
						}
						
					}
				}
			}
		}
		
		for (int k=0;k<initialClusters.size();k++) {
			if (!(ignoreIndex.contains(k))) {
				mergedClusters.add(initialClusters.get(k));
			}
		}
		
		return mergedClusters;
	}
	
	/**
	 * 
	 * This method merges two tiles of the same type if they 
	 * share an adjacent edge
	 * 
	 * @see IdentifyProperties.feature
	 * @author Jing Han 260528152
	 * @param initialTiles
	 * @return mergedList
	 */
	
	private static List<List<int[]>> mergeTile(List<int[]> initialTiles){
		List<Integer> ignoreIndex = new ArrayList<Integer>();
		List<List<int[]>> mergedList=new ArrayList<List<int[]>>();
		
		for (int i=0;i<initialTiles.size()-1;i++) {
			if (!(ignoreIndex.contains(i))) {
				int[] tileA = initialTiles.get(i);
				List<int[]> tempCluster = new ArrayList<int[]>();
						
				for (int j=i+1;j<initialTiles.size();j++) {
					if (!(ignoreIndex.contains(j))) {
						int[] tileB=initialTiles.get(j);
						if (tileAdjacent(tileA,tileB)) {
							tempCluster.add(tileA);
							tempCluster.add(tileB);
							mergedList.add(tempCluster);
							ignoreIndex.add(i);
							ignoreIndex.add(j);
							break;
						}
					}
				}
			}
		}
		for (int k=0;k<initialTiles.size();k++) {
			List<int[]> tempCluster = new ArrayList<int[]>();
			if (!(ignoreIndex.contains(k))) {
				tempCluster.add(initialTiles.get(k));
				mergedList.add(tempCluster);
			}
		}
		
		return mergedList;
	}
	
	/**
	 * 
	 * This method checks whether two tiles
	 * share an adjacent edge
	 * 
	 * @see IdentifyProperties.feature
	 * @author Jing Han 260528152
	 * @param tileA
	 * @param tileB
	 * @return boolean
	 */
	
	private static boolean tileAdjacent(int[] tileA,int []tileB) {
		int x1=tileA[0];
		int y1=tileA[1];
		int x2=tileB[0];
		int y2=tileB[1];
		
		int norm = L2NormSquared(x1, y1, x2, y2);
		if (norm==1) {
			return true;
		}
		else {
			return false;
		}
		
	}
	
	/**
	 * 
	 * This method checks whether two clusters of adjacent tiles
	 * share an adjacent edge
	 * 
	 * @see IdentifyProperties.feature
	 * @author Jing Han 260528152
	 * @param clusterA
	 * @param clusterB
	 * @return boolean
	 */
	
	private static boolean clusterAdjacent(List<int[]> clusterA, List<int[]> clusterB) {
		
		for (int i=0;i<clusterA.size();i++) {
			int [] tileA=clusterA.get(i);
			for (int j=0;j<clusterB.size();j++) {
				int[] tileB=clusterB.get(j);
				if (tileAdjacent(tileA,tileB)) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	/**
	 * 
	 * This method sorts the tiles of a player's kingdom
	 * by their TerrainTypes
	 * 
	 * @see IdentifyProperties.feature
	 * @author Jing Han 260528152
	 * @param player
	 * @return allProperty
	 */
	
	private static List<List<int[]>> sortTerrain(Player player){
		List <int[]> wheatGroup = new ArrayList<int[]>();
		List <int[]> swampGroup = new ArrayList<int[]>();
		List <int[]> grassGroup = new ArrayList<int[]>();
		List <int[]> mountainGroup = new ArrayList<int[]>();
		List <int[]> lakeGroup = new ArrayList<int[]>();
		List <int[]> forestGroup = new ArrayList<int[]>();
		
		List<KingdomTerritory> t = player.getKingdom().getTerritories();	
		
		for (KingdomTerritory each:t) {
			if (each instanceof DominoInKingdom) {
				
				int[][] tileValues = extractTileValues((DominoInKingdom)each);
				int [] leftValues = tileValues[0];
				int [] rightValues=tileValues[1];
				
				TerrainType leftTerrain = ((DominoInKingdom) each).getDomino().getLeftTile();
				TerrainType rightTerrain = ((DominoInKingdom) each).getDomino().getRightTile();		
				
				if (leftTerrain.equals(TerrainType.WheatField)) wheatGroup.add(leftValues);
				if (leftTerrain.equals(TerrainType.Swamp)) swampGroup.add(leftValues);
				if (leftTerrain.equals(TerrainType.Grass)) grassGroup.add(leftValues);
				if (leftTerrain.equals(TerrainType.Mountain)) mountainGroup.add(leftValues);
				if (leftTerrain.equals(TerrainType.Lake)) lakeGroup.add(leftValues);
				if (leftTerrain.equals(TerrainType.Forest)) forestGroup.add(leftValues);
				
				if (rightTerrain.equals(TerrainType.WheatField)) wheatGroup.add(rightValues);
				if (rightTerrain.equals(TerrainType.Swamp)) swampGroup.add(rightValues);
				if (rightTerrain.equals(TerrainType.Grass)) grassGroup.add(rightValues);
				if (rightTerrain.equals(TerrainType.Mountain)) mountainGroup.add(rightValues);
				if (rightTerrain.equals(TerrainType.Lake)) lakeGroup.add(rightValues);
				if (rightTerrain.equals(TerrainType.Forest)) forestGroup.add(rightValues);
			}
		}
		
		List<List<int[]>> allProperty = new ArrayList<List<int[]>>();
		allProperty.add(wheatGroup);
		allProperty.add(swampGroup);
		allProperty.add(grassGroup);
		allProperty.add(mountainGroup);
		allProperty.add(lakeGroup);
		allProperty.add(forestGroup);
		
		return allProperty;
	}
	
	/**
	 * 
	 * This method preplaces a domino into a playe's kingdom
	 * Useful for bring games up to a testable state in cucumber files
	 * 
	 * @see - no features associated
	 * @author Jing Han 260528152
	 * @param player
	 * @param dominoToPlace
	 * @param posx
	 * @param posy
	 * @param dir
	 * @return dInK
	 */

	public static DominoInKingdom prePlaceDomino(Player player, Domino dominoToPlace, int posx, int posy, String dir) {
		dominoToPlace.setStatus(DominoStatus.CorrectlyPreplaced);
		Kingdom kingdom=player.getKingdom();
		DominoInKingdom dInK = new DominoInKingdom(posx,posy,kingdom,dominoToPlace);
		dInK.setDirection(getDirection(dir));
		return dInK;
	}
	
	/**
	 * 
	 * This method extracts the coordinates, id, and crown number
	 * of the tiles of a DominoInKingdom dInK of the player's kingdom
	 * 
	 * @see IdentifyProperties.feature
	 * @author Jing Han 260528152
	 * @param dInK
	 * @return tileInfo
	 */
	
	private static int[][] extractTileValues(DominoInKingdom dInK){
		
		int ID = dInK.getDomino().getId();

		int leftX=dInK.getX();
		int leftY=dInK.getY();
		int leftCrown = dInK.getDomino().getLeftCrown();
		int[] leftInfo= {leftX,leftY,ID,leftCrown};
		
		int [] otherPos=calculateOtherPos(dInK);
		
		int rightX=otherPos[0];
		int rightY=otherPos[1];
		int rightCrown=dInK.getDomino().getRightCrown();
		int [] rightInfo= {rightX,rightY,ID,rightCrown};
		
		int[][] tileInfo= {leftInfo,rightInfo};
		
		return tileInfo;
		
	}
	
	/**
	 * 
	 * This method returns the size of a property at a certain index of a certain player
	 * 
	 * @see CalculatePropertyAttributes.feature
	 * @author Eric Guan 260930210
	 * @param player, index
	 * @return propertySize 
	 */
	
	public static void propertySize(Player player, Property property) {
		
		if(player == null) {
			
			throw new java.lang.IllegalArgumentException("This player does not exist");
		}
		
		int propertySize = 0;
		TerrainType type = property.getPropertyType();
		List<Domino> allDominos = property.getIncludedDominos();
		
		for (Domino domino: allDominos) {
			
			if (domino.getLeftTile().equals(type)) {
				propertySize++;
			}
			
			if (domino.getRightTile().equals(type)) {
				propertySize++;
				}
		}
		property.setSize(propertySize);
	}
	
	/**
	 * 
	 * This method returns the number of crowns of a property at a certain index of a certain player
	 * 
	 * @see CalculatePropertyAttributes.feature
	 * @author Eric Guan 260930210
	 * @param player, index
	 * @return propertyCrown 
	 */
	
	public static void getPropertyCrown(Player player, Property property) {
		
		if(player == null) {
			
			throw new java.lang.IllegalArgumentException("This player does not exist");
		}
		
		int propertyCrown = 0;
		TerrainType type = property.getPropertyType();
		List<Domino> allDominos = property.getIncludedDominos();
		
		for (Domino domino: allDominos) {
			
			if (domino.getLeftTile().equals(type)) {
				propertyCrown += domino.getLeftCrown();
			}
			
			if (domino.getRightTile().equals(type)) {
				propertyCrown += domino.getRightCrown();
			}
		}
		property.setCrowns(propertyCrown);
	}
	
	public static int getNumberOfProperties(Player player) {
		
		int numberOfProperties = 0;
		
		if(player == null) {
			
			throw new java.lang.IllegalArgumentException("This player does not exist");
		}

		List<Property> properties = player.getKingdom().getProperties();
		System.out.println(properties.size());
		numberOfProperties = properties.size();
		
		return numberOfProperties;
	}
	public static List<Property> getPropertyAttributes(Player player) {
		
		List<Property> properties = new ArrayList<Property>();
		int numberOfProp = getNumberOfProperties(player);
		System.out.println(numberOfProp);
		
		for (int i = 0; i < numberOfProp; i++) {
			
			properties.get(i).setCrowns(player.getKingdom().getProperty(i).getCrowns());
			properties.get(i).setSize(player.getKingdom().getProperty(i).getSize());
		}
		
		return properties;
	}
	
	/**
	 * 
	 * This method calculates and updates the property score of a player
	 * 
	 * @see CalculatePropertyAttributes.feature
	 * @author Eric Guan 260930210
	 * @param player
	 * @return void 
	 */
	
	public static void propertyScore(Player player) {
		
		if(player == null) {
			
			throw new java.lang.IllegalArgumentException("This player does not exist");
		}
		
		player.setPropertyScore(0);
		List<Property> properties = player.getKingdom().getProperties();
		int totalPropertyScore = 0;
		
		for(int index = 0; index < properties.size(); index++) {
			Property property = properties.get(index);
			totalPropertyScore += property.getCrowns()*property.getSize();
		}
		player.setPropertyScore(totalPropertyScore);
	}
	
	/**
	 * 
	 * This method check if the castle is in the middle of the Kingdom
	 * 
	 * @see CalculateBonusScore.feature
	 * @author Eric Guan 260930210
	 * @param player
	 * @return boolean
	 */
	
	public static int isCastleInMiddle(Player player) {
		
		if(player == null) {
			
			throw new java.lang.IllegalArgumentException("This player does not exist");
		}
		
		boolean right= false, left = false, top = false, bottom = false;
		int score = 0;

		if (true) {//isBonusOption("Middle Castle")) { //this doesnt work
		
			List<KingdomTerritory> kingdomTerritories = player.getKingdom().getTerritories(); 
			System.out.println(kingdomTerritories.size());
			
			for (int index = 1; index < kingdomTerritories.size(); index++) {
				
				KingdomTerritory domino = kingdomTerritories.get(index);
				
				if (domino.getX()==2) {
			
					right = true;
				}
				
				if (domino.getX()==-2) {
					
					left = true;
				}
				
				if (domino.getY()==2) {
					
					top = true;
				}
				
				if (domino.getY()==-2) {
					
					bottom = true;
				}
				
				if (((DominoInKingdom) domino).getDirection().equals(DirectionKind.Right) && domino.getX()==1) {
					
					right = true;
				}
				
				if (((DominoInKingdom) domino).getDirection().equals(DirectionKind.Left) && domino.getX()==-1) {
					
					left = true;
				}
				if (((DominoInKingdom) domino).getDirection().equals(DirectionKind.Up) && domino.getY()==1) {
	
					top = true;
				}
				if (((DominoInKingdom) domino).getDirection().equals(DirectionKind.Down) && domino.getY()==-1) {
	
					bottom = true;
				}
			}
		}
		if (top && right && left && bottom) {
			
			score = 10;
		}
		return score;
	}
	
	/**
	 * 
	 * This method check if the Kingdom respects the rules for Harmony
	 * 
	 * @see CalculateBonusScore.feature
	 * @author Eric Guan 260930210
	 * @param player
	 * @return boolean
	 */
	
	public static int isHarmony(Player player) {
		
		if(player == null) {
			
			throw new java.lang.IllegalArgumentException("This player does not exist");
		}
		int score = 0;
		
		if (true) {//isBonusOption("Harmony")) { //this doesnt work
			
			boolean harmony = false;
			List<KingdomTerritory> kingdomTerritories = player.getKingdom().getTerritories(); 
			System.out.println(kingdomTerritories.size());
			
			if(kingdomTerritories.size() > 12) {
			
				harmony = true;
			}
			
			if (harmony) {
			
				score = 5;
			}
		}
		return score;
	}
	
	/**
	 * 
	 * This method return the bonus options selected
	 * 
	 * @see CalculateBonusScore.feature
	 * @author Eric Guan 260930210
	 * @param game
	 * @return List
	 */
	
	public static boolean isBonusOption(String string) {
		
		boolean selected = false;
		
		List<BonusOption> bonusOptions = KingdominoApplication.getKingdomino().getCurrentGame().getSelectedBonusOptions();
		
		for (int i = 0; i < bonusOptions.size(); i++) {
			
			if (bonusOptions.get(i).getOptionName().equalsIgnoreCase(string)) {
				
				selected = true;
			}
		}
		return selected;
	}
	
	/**
	 * 
	 * This method calculates and updates the bonus score of a player
	 * 
	 * @see CalculateBonusScore.feature
	 * @author Eric Guan 260930210
	 * @param player
	 * @return void 
	 */
	
	public static void bonusScore(Player player) {
		
		if(player == null) {
			
			throw new java.lang.IllegalArgumentException("This player does not exist");
		}
		
		int bonus = 0;
		
		bonus = isHarmony(player) + isCastleInMiddle(player);
		
		player.setBonusScore(bonus);
	}
	
	/**
	 * 
	 * This method calculates and updates the total score of a player
	 * 
	 * @see CalculatePlayerScore.feature
	 * @author Eric Guan 260930210
	 * @param player
	 * @return void 
	 */

	public static void playerScore(Player player) {
		
		if(player == null) {
			
			throw new java.lang.IllegalArgumentException("This player does not exist");
		}
		
		propertyScore(player);
		bonusScore(player);
	}	
	
	///////////////////////////////////////
	/// ----Given TA Helper Methods---- ///
	///////////////////////////////////////
	
	private static void addDefaultUsersAndPlayers(Game game) {
		String[] users = { "User1", "User2", "User3", "User4" };
		for (int i = 0; i < users.length; i++) {
			game.getKingdomino().addUser(users[i]);
			Player player = new Player(game);
			player.setColor(PlayerColor.values()[i]);
			Kingdom kingdom = new Kingdom(player);
			new Castle(0, 0, kingdom, player);
		}
	}

	private static void createAllDominoes(Game game) {
		try {
			BufferedReader br = new BufferedReader(new FileReader("src/main/resources/alldominoes.dat"));
			String line = "";
			String delimiters = "[:\\+()]";
			while ((line = br.readLine()) != null) {
				String[] dominoString = line.split(delimiters); // {id, leftTerrain, rightTerrain, crowns}
				int dominoId = Integer.decode(dominoString[0]);
				TerrainType leftTerrain = getTerrainType(dominoString[1]);
				TerrainType rightTerrain = getTerrainType(dominoString[2]);
				int numCrown = 0;
				if (dominoString.length > 3) {
					numCrown = Integer.decode(dominoString[3]);
				}
				new Domino(dominoId, leftTerrain, rightTerrain, numCrown, game);
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw new java.lang.IllegalArgumentException(
					"Error occured while trying to read alldominoes.dat: " + e.getMessage());
		}
	}

	public static Domino getdominoByID(int id) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		for (Domino domino : game.getAllDominos()) {
			if (domino.getId() == id) {
				return domino;
			}
		}
		throw new java.lang.IllegalArgumentException("Domino with ID " + id + " not found.");
	}

	private static TerrainType getTerrainType(String terrain) {
		switch (terrain) {
		case "W":
			return TerrainType.WheatField;
		case "F":
			return TerrainType.Forest;
		case "M":
			return TerrainType.Mountain;
		case "G":
			return TerrainType.Grass;
		case "S":
			return TerrainType.Swamp;
		case "L":
			return TerrainType.Lake;
		default:
			throw new java.lang.IllegalArgumentException("Invalid terrain type: " + terrain);
		}
	}

	private static DirectionKind getDirection(String dir) {
		switch (dir) {
		case "up":
			return DirectionKind.Up;
		case "down":
			return DirectionKind.Down;
		case "left":
			return DirectionKind.Left;
		case "right":
			return DirectionKind.Right;
		default:
			throw new java.lang.IllegalArgumentException("Invalid direction: " + dir);
		}
	}

	private static DominoStatus getDominoStatus(String status) {
		switch (status) {
		case "inPile":
			return DominoStatus.InPile;
		case "excluded":
			return DominoStatus.Excluded;
		case "inCurrentDraft":
			return DominoStatus.InCurrentDraft;
		case "inNextDraft":
			return DominoStatus.InNextDraft;
		case "erroneouslyPreplaced":
			return DominoStatus.ErroneouslyPreplaced;
		case "correctlyPreplaced":
			return DominoStatus.CorrectlyPreplaced;
		case "placedInKingdom":
			return DominoStatus.PlacedInKingdom;
		case "discarded":
			return DominoStatus.Discarded;
		default:
			throw new java.lang.IllegalArgumentException("Invalid domino status: " + status);
		}
	}
}
