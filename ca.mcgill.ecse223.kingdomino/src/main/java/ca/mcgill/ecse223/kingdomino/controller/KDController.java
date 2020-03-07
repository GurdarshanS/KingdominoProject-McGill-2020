package ca.mcgill.ecse223.kingdomino.controller;
import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.model.*;
import ca.mcgill.ecse223.kingdomino.model.Domino.DominoStatus;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom.DirectionKind;
import ca.mcgill.ecse223.kingdomino.model.Player.PlayerColor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;



	
public class KDController {

	private static int DraftAmount;
	private static int DomAmount;

	
	public static void initiateEmptyGame() {
		// Intialize empty game
		Kingdomino kingdomino = KingdominoApplication.getKingdomino();
		Game game = new Game(48, kingdomino);
		game.setNumberOfPlayers(4);
		kingdomino.setCurrentGame(game);
		// Populate game
		addDefaultUsersAndPlayers(game);
		createAllDominoes(game);
		game.setNextPlayer(game.getPlayer(0));
	}
	
	public static Domino[] listDominos() {
		Domino[] myDominos = new Domino[48];
		for(int i = 1; i < 49 ; i++) {
			Domino dom = KDController.getdominoByID(i);
			myDominos[i-1] = dom;
			
		}
		return myDominos;
	}

	public static ArrayList filterbyTerrain (String s1) {
		
		ArrayList myDominos = new ArrayList();
		TerrainType t1 = KDController.retrieveTerrainType(s1);
		for (int i = 1; i < 49 ; i++) {
			Domino dom = KDController.getdominoByID(i);
			
			if ( dom.getLeftTile().toString().equalsIgnoreCase(t1.toString()) || dom.getRightTile().toString().equalsIgnoreCase(t1.toString()) ) {
				
				myDominos.add(dom.getId());
			}			
		}	
		

		return myDominos;
	}
	
	
	
	
	
	public static void numOfPlayers(int number) {
		
		Kingdomino kingdomino = KingdominoApplication.getKingdomino();
		DomAmount = 0;
		if(number == 4) {
			DomAmount = 48;
		} else if (number == 3) {
			DomAmount = 36;
		} else if (number == 2) {
			DomAmount = 24;
		}
		Game game = new Game(DomAmount, kingdomino);
		game.setNumberOfPlayers(number);
		kingdomino.setCurrentGame(game);
		// Populate game
		addDefaultUsersAndPlayers(game);
		createAllDominoes(game);
		game.setNextPlayer(game.getPlayer(0));
		System.out.println(game.getNumberOfPlayers());
	}
	
	public static Domino[] shuffleDominos() {

			int count = 0;
			Domino[] DominoArray = new Domino[48];
			
			for (int i = 1; i < DominoArray.length+1 ; i++) {
				Domino dom = KDController.getdominoByID(i);
				DominoArray[count] = dom;
				count++;
			}
			
			ArrayList Dominos = new ArrayList();
			for (int i = 0; i < DominoArray.length; i++) {
				Dominos.add(DominoArray[i]);
			}		
			Collections.shuffle(Dominos);
			
			for(int i=0; i<DominoArray.length; i++) {
				DominoArray[i] = (Domino) Dominos.get(i);
			}
			return DominoArray;
	}
	
	public static Integer[] arrangeDominos(String s1) {
		
		Integer[] DominoArray = new Integer[48];
		String[] StringArray = new String[48];
		StringArray = s1.split(", ");
		
		for(int i = 0; i < DominoArray.length; i++) {
			DominoArray[i] = (Integer.parseInt(StringArray[i]));			
		}
		return DominoArray;
	}
	
	public static Domino[] arrangeTheDominos(String s1) {
		
		Domino[] DominoArray = new Domino[48];
		String[] StringArray = new String[48];
		StringArray = s1.split(", ");
		
		for(int i = 0; i < DominoArray.length; i++) {
			DominoArray[i] = KDController.getdominoByID(Integer.parseInt(StringArray[i]));
		}
		return DominoArray;
	}
	
	public static Domino[] removeDraftDominos(String s1, int int1) {
		Domino[] DominoArray = new Domino[48];
		String[] StringArray = new String[48];
		StringArray = s1.split(", ");
		int count = 0;
		for(int i = int1; i < DominoArray.length; i++) {
			DominoArray[count] = KDController.getdominoByID(Integer.parseInt(StringArray[i]));
			count++;
		}
		
		return DominoArray;
		
	}


	
	
	
	
	
	
	
	
	
	public static Player[] bubbleSort(Player[] scoreList) 
    { 
        int n = scoreList.length; 
        for (int i = 0; i < n-1; i++) 
            for (int j = 0; j < n-i-1; j++) 
                if (scoreList[j].getTotalScore() > scoreList[j+1].getTotalScore()) 
                { 
                    // swap arr[j+1] and arr[i] 
                    Player temp = scoreList[j]; 
                    scoreList[j] = scoreList[j+1]; 
                    scoreList[j+1] = temp; 
                } 
        System.out.println(scoreList.toString());
        return scoreList;
    } 
	
	public static PlayerColor retrieveColor(String s1) {
		PlayerColor p = null;
		if(s1.equals("blue")) {
			p = PlayerColor.Blue;
		} else if(s1.equals("green")) {
			p = PlayerColor.Green;
		} else if(s1.equals("pink")) {
			p = PlayerColor.Pink;
		} else if(s1.equals("yellow")) {
			p = PlayerColor.Yellow;
		}
		return p;
	}
	
	public static Player[] tieBreakProperty(Player[] p1) {
		
		if( p1[2].getTotalScore() == p1[3].getTotalScore()) {
			
			List<Property> propW1 = getPropertyByTerrain(p1[2], TerrainType.WheatField);
			List<Property> propF1 = getPropertyByTerrain(p1[2], TerrainType.Forest);
			List<Property> propG1 = getPropertyByTerrain(p1[2], TerrainType.Grass);
			List<Property> propL1 = getPropertyByTerrain(p1[2], TerrainType.Lake);
			List<Property> propM1 = getPropertyByTerrain(p1[2], TerrainType.Mountain);
			List<Property> propS1 = getPropertyByTerrain(p1[2], TerrainType.Swamp);
			List<Property> propW2 = getPropertyByTerrain(p1[3], TerrainType.WheatField);
			List<Property> propF2 = getPropertyByTerrain(p1[3], TerrainType.Forest);
			List<Property> propG2 = getPropertyByTerrain(p1[3], TerrainType.Grass);
			List<Property> propL2 = getPropertyByTerrain(p1[3], TerrainType.Lake);
			List<Property> propM2 = getPropertyByTerrain(p1[3], TerrainType.Mountain);
			List<Property> propS2 = getPropertyByTerrain(p1[3], TerrainType.Swamp);
			
			List<Integer> big = new ArrayList<>();
			big.add(propW1.size());
			big.add(propW2.size());
			big.add(propF1.size());
			big.add(propF2.size());
			big.add(propG1.size());
			big.add(propG2.size());
			big.add(propL1.size());
			big.add(propL2.size());
			big.add(propM1.size());
			big.add(propM2.size());
			big.add(propS1.size());
			big.add(propS2.size());
			
			Collections.sort(big);
			
			
			}
			
			
		}
		
		return p1;
	}
	
	public static List<PropertyAttribute> getAllPropertyAttributes(Player player) {
	List<Property> allProp=getAllProperty(player);
	List<PropertyAttribute> allAttributes=new ArrayList<PropertyAttribute>();
	
	for (Property p:allProp) {
		int propertySize=p.getSize();
		TerrainType t = p.getPropertyType();
		int crown = p.getCrowns();
		int score = p.getScore();
		PropertyAttribute pa = new PropertyAttribute(t,propertySize,crown,score);
		allAttributes.add(pa);
	}
	return allAttributes;
}

	public static List<Property> getPropertyByTerrain(Player player, TerrainType t){
		List<Property> allProp=getAllProperty(player);
		List<Property> filteredProp = new ArrayList<Property>();
		
		for (Property testProp:allProp) {
			if (testProp.getPropertyType().equals(t)) {
				filteredProp.add(testProp);
			}
		}
		return filteredProp;
	}
	
	public static List<Property> getAllProperty(Player player){
		return player.getKingdom().getProperties();
	}
	
	public static void calculatePlayerScore(Player player) {
		identifyAllProperty(player);
		List<Property> p = player.getKingdom().getProperties();
//		System.out.println(p);
		int score=0;
		for (Property each:p) {
//			System.out.println(each.getScore());
			score+=each.getScore();
		}
		score=score+player.getBonusScore();
		player.setPropertyScore(score);
		calculateBonusScore(player);
	}
	
	public static void calculateBonusScore(Player player) {
		Kingdomino kd = KingdominoApplication.getKingdomino();
		List<BonusOption> boList=kd.getBonusOptions();
		
		boolean useMiddle=false;
		boolean useHarmony=false;
		
		for (BonusOption bo:boList) {
			if (bo.getOptionName().equalsIgnoreCase("middlekingdom") || bo.getOptionName().equalsIgnoreCase("middle kingdom")) {
				useMiddle=true;
			}
			if (bo.getOptionName().equalsIgnoreCase("harmony")) {
				useHarmony=true;
			}
		}
		
		int bonus=0;
		if (useMiddle) {
			if (isMiddleKingdom(player)) {
				bonus=bonus+10;
			}
		}
		if (useHarmony) {
			if (isHarmony(player)) {
				bonus=bonus+5;
			}
		}
		
		player.setBonusScore(bonus);
		
		
	}
	
	public static boolean isMiddleKingdom(Player player) {
		int [][] coords = getPlayerTerritoryCoordinates(player);
		
		List<Integer> x1 = new ArrayList<Integer>();
		List<Integer> y1 = new ArrayList<Integer>();
		List<Integer> x2 = new ArrayList<Integer>();
		List<Integer> y2 = new ArrayList<Integer>();
		
		for (int i=0;i<coords[0].length;i++) {
			x1.add(coords[0][i]);
			y1.add(coords[1][i]);
			x2.add(coords[2][i]);
			y2.add(coords[3][i]);
		}
		
		Collections.sort(x1);
		Collections.sort(y1);
		Collections.sort(x2);
		Collections.sort(y2);
		
//		System.out.println(x1);
//		System.out.println(y1);
//		System.out.println(x2);		
//		System.out.println(y2);
		
		
		int minX=Math.min(x1.get(0), x2.get(0));
		int maxX=Math.max(x1.get(x1.size()-1), x2.get(x2.size()-1));
		int minY=Math.min(y1.get(0), y2.get(0));
		int maxY=Math.max(y1.get(y1.size()-1), y2.get(y2.size()-1));
		
		if ((Math.abs(minX)==maxX)&&(Math.abs(minY)==maxY)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public static boolean isHarmony(Player player) {
		
		int [][] coords = getPlayerTerritoryCoordinates(player);
		
		List<Integer> x1 = new ArrayList<Integer>();
		List<Integer> y1 = new ArrayList<Integer>();
		List<Integer> x2 = new ArrayList<Integer>();
		List<Integer> y2 = new ArrayList<Integer>();
		
		for (int i=0;i<coords[0].length;i++) {
			x1.add(coords[0][i]);
			y1.add(coords[1][i]);
			x2.add(coords[2][i]);
			y2.add(coords[3][i]);
		}
		
		x1.addAll(x2);
		y1.addAll(y2);
		
//		the extra -1 below because castle's position is double counted as both left and right tile
		if ((x1.size()-1)!=25){			
			return false;
		}
		
		Collections.sort(x1);
		Collections.sort(y1);

		if (((x1.get(x1.size()-1)-x1.get(0)+1)==5)&&((y1.get(y1.size()-1)-y1.get(0)+1)==5)) {
			return true;
		}
		else {
			return false;
		}

	}

	public static int[][] getPlayerTerritoryCoordinates(Player player) {
	
	List<KingdomTerritory> territories=getPlayerTerritory(player);
	
	int[][] dominoPos = new int[4][territories.size()];

	for (int i=0;i<territories.size();i++){
		if (territories.get(i) instanceof Castle) {
			dominoPos[0][i]=0;
			dominoPos[1][i]=0;
			dominoPos[2][i]=0;
			dominoPos[3][i]=0;
		}
		else if (territories.get(i) instanceof DominoInKingdom)
		{
			DominoInKingdom t = (DominoInKingdom) territories.get(i);
			int[] otherPos=KDController.calculateOtherPos(t);
			dominoPos[0][i]=t.getX();
			dominoPos[1][i]=t.getY();
			dominoPos[2][i]=otherPos[0];
			dominoPos[3][i]=otherPos[1];

		}
	}
	return dominoPos;
}

	public static List<KingdomTerritory> getPlayerTerritory(Player player){
		return player.getKingdom().getTerritories();		
	}
	
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
			newProp.setScore(propertySize.get(i)*propertyCrowns.get(i));
			List<Integer> ids = propertyIds.get(i);
			for (int j:ids) {
				Domino aDomino = getdominoByID(j);
				newProp.addIncludedDomino(aDomino);
			}
		}
		
	}
	
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

	public static int L2NormSquared(int x1, int y1, int x2, int y2) {
		int deltaX=x2-x1;
		int deltaY=y2-y1;
		
		int norm = deltaX*deltaX+deltaY*deltaY;
		
		return norm;
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
				"Error occured while trying to read alldominoes.dat: " + e.getMessage());}
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
	
	
	public static Domino getdominoByID(int id) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		for (Domino domino : game.getAllDominos()) {
			if (domino.getId() == id) {
				return domino;
			}
		}
		throw new java.lang.IllegalArgumentException("Domino with ID " + id + " not found.");
	}
	
	public static TerrainType retrieveTerrainType(String terrain) {
		switch (terrain) {
		case "wheat":
			return TerrainType.WheatField;
		case "forest":
			return TerrainType.Forest;
		case "mountain":
			return TerrainType.Mountain;
		case "grass":
			return TerrainType.Grass;
		case "swamp":
			return TerrainType.Swamp;
		case "lake":
			return TerrainType.Lake;
		default:
			throw new java.lang.IllegalArgumentException("Invalid terrain type: " + terrain);
		}
	}
	public static DirectionKind getDirection(String dir) {
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


	
	}
