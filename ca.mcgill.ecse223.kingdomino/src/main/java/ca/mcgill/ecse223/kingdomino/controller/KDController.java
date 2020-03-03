package ca.mcgill.ecse223.kingdomino.controller;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
//import ca.mcgill.ecse223.kingdomino.model.Kingdomino;
import ca.mcgill.ecse223.kingdomino.model.*;
import ca.mcgill.ecse223.kingdomino.model.Domino.DominoStatus;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom.DirectionKind;
import ca.mcgill.ecse223.kingdomino.model.Player.PlayerColor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class KDController {

	public KDController(){}

	public static void initiateEmptyGame() {
		//start empty game
		Kingdomino kingdomino = KingdominoApplication.getKingdomino();
		Game game = new Game(48, kingdomino);
		game.setNumberOfPlayers(4);
		kingdomino.setCurrentGame(game);
		// Populate game
		addDefaultUsersAndPlayers(game);
		createAllDominoes(game);
		game.setNextPlayer(game.getPlayer(0));
	}

	public static DominoInKingdom prePlaceDomino(Player player, Domino dominoToPlace, int posx, int posy, String dir) {
		dominoToPlace.setStatus(DominoStatus.CorrectlyPreplaced);
		Kingdom kingdom=player.getKingdom();
		DominoInKingdom dInK = new DominoInKingdom(posx,posy,kingdom,dominoToPlace);
		dInK.setDirection(getDirection(dir));
		return dInK;
	}
	
	public static String getKingdomVerificationResult(Player player) {
		String validity="valid";
		
		List<DominoInKingdom> errorneouslyPlacedDominos=KDQuery.getErroneouslyPrePlacedDomino(player);
		if (!errorneouslyPlacedDominos.isEmpty()) {
			validity="invalid";
		}
		
		return validity;
	}
	
	public static boolean verifyGridSizeAllKingdom(Player player) {
		
		int badCount=0;
		boolean respectGrid=true;
				
		List<KingdomTerritory> t = player.getKingdom().getTerritories();
		List<Integer> xCoords = new ArrayList<Integer>();
		List<Integer> yCoords = new ArrayList<Integer>();
		
		if (t.size()==1) {
			respectGrid=true;
			System.out.println(respectGrid);
			return true;
		}

		
		for (KingdomTerritory each:t) {
			
			int x1=each.getX();
			int y1=each.getY();
			
			xCoords.add(x1);
			yCoords.add(y1);
			
			int x2;
			int y2;
			
			int[] otherPos=calculateOtherPos(each);
			x2=otherPos[0];
			y2=otherPos[1];
			
			xCoords.add(x2);
			yCoords.add(y2);
			
			Collections.sort(xCoords);
			Collections.sort(yCoords);
			
			int xSize=xCoords.get(xCoords.size()-1)-xCoords.get(0)+1;
			int ySize=yCoords.get(yCoords.size()-1)-yCoords.get(0)+1;
			
			if (each instanceof DominoInKingdom) {
				if (xSize>5 || ySize>5) {
					((DominoInKingdom) each).getDomino().setStatus(DominoStatus.ErroneouslyPreplaced);
					badCount++;
				}
				else {
					((DominoInKingdom) each).getDomino().setStatus(DominoStatus.CorrectlyPreplaced);
				}
			}
			
		}
		if (badCount>0) {
			respectGrid=false;
		}
		else {
			respectGrid=true;
		}
		System.out.println(respectGrid);
		return respectGrid;
	}
	
public static boolean verifyNoOverlapAllTerritories(Player player) {
		
//		verifies all territories in kingdom
		int overlappedCount=0;
		boolean noOverlap=true;
		
		List<KingdomTerritory> territories = player.getKingdom().getTerritories();
		
		if (territories.size()==1) {
			noOverlap=true;
			System.out.println(noOverlap);
			return noOverlap;
		}
		
		else {
			
			KingdomTerritory tA;
			KingdomTerritory tB;
			for (int i=territories.size()-1;i>0;i--) {
				
				tA=territories.get(i);
				
				for (int j=i-1;j>-1;j--) {
					
					tB=territories.get(j);

					if (checkOverlap(tA,tB)){
						((DominoInKingdom) tA).getDomino().setStatus(DominoStatus.ErroneouslyPreplaced);
						overlappedCount++;
					}
					else {
						((DominoInKingdom) tA).getDomino().setStatus(DominoStatus.CorrectlyPreplaced);
					}
					
				}
			}
			if (overlappedCount>0) {
				noOverlap=false;
			}
			else {
				noOverlap=true;
			}
			System.out.println(noOverlap);
			return noOverlap;
		}
		
	}
	
	public static boolean verifyNoOverlapLastTerritory(Player player) {
		
//		only verifies the last preplaced domino
		boolean noOverlap=true;
		
		List<KingdomTerritory> territories = player.getKingdom().getTerritories();
		
		if (territories.size()==1) {
			noOverlap=true;
			System.out.println(noOverlap);
			return noOverlap;
		}
		
		else {
			
			KingdomTerritory tA;
			KingdomTerritory tB;
	
			tA=territories.get(territories.size()-1);
				
			for (int j=territories.size()-2;j>-1;j--) {
					
					tB=territories.get(j);

					if (checkOverlap(tA,tB)){
						((DominoInKingdom) tA).getDomino().setStatus(DominoStatus.ErroneouslyPreplaced);
						noOverlap=false;
						break;
					}
					else {
						((DominoInKingdom) tA).getDomino().setStatus(DominoStatus.CorrectlyPreplaced);
						noOverlap=true;
					}
					
				}
			System.out.println(noOverlap);
			return noOverlap;
			}
			
	}
	
	public static boolean verifyCastleAdjacency(Player player) {
		
		boolean castleAdj=true;
		
		List <KingdomTerritory> t = player.getKingdom().getTerritories();
		
		if (t.size()==1) {
			castleAdj=true;
			System.out.println(castleAdj);
			return castleAdj;
		}
		
		int castleX=t.get(0).getX();
		int castleY=t.get(0).getY();

		
		KingdomTerritory testD = t.get(t.size()-1);
		int [] testOtherPos=calculateOtherPos(testD);
		
		int testX1=testD.getX();
		int testY1=testD.getY();
		int testX2=testOtherPos[0];
		int testY2=testOtherPos[1];
		
		
		int norm1=L2NormSquared(testX1,testY1,castleX,castleY);
		int norm2=L2NormSquared(testX2,testY2,castleX,castleY);

		if ((norm1==1)&&(norm2>1)){
			((DominoInKingdom) testD).getDomino().setStatus(DominoStatus.CorrectlyPreplaced);
			castleAdj=true;
		}
		else if ((norm1>1)&&(norm2==1)) {
			((DominoInKingdom) testD).getDomino().setStatus(DominoStatus.CorrectlyPreplaced);
			castleAdj=true;
		}
		else {
			((DominoInKingdom) testD).getDomino().setStatus(DominoStatus.ErroneouslyPreplaced);
			castleAdj=false;
		}
		
		System.out.println(castleAdj);
		return castleAdj;
		
	}
	
	public static boolean verifyNeighborAdjacencyLastTerritory(Player player) {
		
		boolean neighborAdj=true;
		
		List<KingdomTerritory> t =player.getKingdom().getTerritories();
		
		if (t.size()==1) {
			neighborAdj=true;
			System.out.println(neighborAdj);
			return neighborAdj;
		}
		else {
			
			int validNeighborCount=0;
			
			DominoInKingdom prePlacedDomino = (DominoInKingdom) t.get(t.size()-1);
			
			Neighborhood leftNeighborhood = getDominoLeftNeighbors(t,prePlacedDomino);
			Neighborhood rightNeighborhood =getDominoRightNeighbors(t,prePlacedDomino);
			
//			System.out.println(leftNeighborhood);	
//			System.out.println("---------------------------------------------------------------------------------------------");
//			System.out.println(rightNeighborhood);	

			List<TerrainType> leftTileNeighborTerrains = leftNeighborhood.getNeighborTerrainType();
//			System.out.println(leftTileNeighborTerrains.size());
			
			List<TerrainType> rightTileNeighborTerrains = rightNeighborhood.getNeighborTerrainType();
//			System.out.println(rightTileNeighborTerrains.size());
			
			if (leftTileNeighborTerrains.isEmpty() && rightTileNeighborTerrains.isEmpty()) {
//				System.out.println("no neighbors at all");
			}
			
			if (!leftTileNeighborTerrains.isEmpty()) {
				String leftTerrain=prePlacedDomino.getDomino().getLeftTile().name();
				for (TerrainType testTerrain:leftTileNeighborTerrains) {
					String testTerrainName=testTerrain.name();
					if (testTerrainName.equalsIgnoreCase(leftTerrain)){
//						System.out.println("found left match!");
						validNeighborCount++;
					}
				}
			}
			
			if (!rightTileNeighborTerrains.isEmpty()) {
				for (TerrainType testTerrain:rightTileNeighborTerrains) {
					if (testTerrain.name().equalsIgnoreCase(prePlacedDomino.getDomino().getRightTile().name())){
//						System.out.println("found right match!");
						validNeighborCount++;
					}
				}
			}
			
			if (validNeighborCount==0) {
				prePlacedDomino.getDomino().setStatus(DominoStatus.ErroneouslyPreplaced);
				neighborAdj=false;
			}
			else {
				prePlacedDomino.getDomino().setStatus(DominoStatus.CorrectlyPreplaced);
				neighborAdj=true;
			}
		
			System.out.println(neighborAdj);
			return neighborAdj;
		}
		
	}
	
//	public static void discardDomino(Player player) {
//		int x=-4;
//		int y=-4;
//		
//		while (x<5) {
//			while (y<5) {
//				
//			}
//		}
//		
//	}
	
//	public static void discardDomino(Player player)
	
	
	///////////////////////////////////////
	/// -----Private Helper Methods---- ///
	///////////////////////////////////////
	
	public static Neighborhood getDominoLeftNeighbors(List<KingdomTerritory> t, DominoInKingdom dInK) {
		
		List <KingdomTerritory> neighborTerritory=new ArrayList<KingdomTerritory>();
		List <String> neighborTileType=new ArrayList<String>();
		List <TerrainType> neighborTerrain = new ArrayList<TerrainType>();
		List <int []> neighborCoord = new ArrayList<int[]>();
	
		int searchX=dInK.getX();
		int searchY=dInK.getY();
		
		for (KingdomTerritory each:t) {
			
			int [] testRightCoord=calculateOtherPos(each);
			
			int testLeftX=each.getX();
			int testLeftY=each.getY();
			int testRightX=testRightCoord[0];
			int testRightY=testRightCoord[1];
			
			int [] testLeftCoord = {testLeftX,testLeftY};
			
			int norm1=L2NormSquared(searchX,searchY,testLeftX,testLeftY);
			int norm2=L2NormSquared(searchX,searchY,testRightX,testRightY);
			
			if (norm1==1 || norm2==1) {
				if (each instanceof Castle) {
					neighborTerritory.add(each);
					neighborTileType.add("castle");
					neighborTerrain.add(dInK.getDomino().getLeftTile());
					neighborCoord.add(testLeftCoord);
				}
				else if (each instanceof DominoInKingdom) {
					if (((DominoInKingdom) each).getDomino().getId()!=dInK.getDomino().getId()) {
						neighborTerritory.add(each);
						if (norm1==1) {
							neighborTileType.add("left");
							neighborTerrain.add(((DominoInKingdom) each).getDomino().getLeftTile());
							neighborCoord.add(testLeftCoord);
						}
						else if (norm2==1) {
							neighborTileType.add("right");
							neighborTerrain.add(((DominoInKingdom) each).getDomino().getRightTile());
							neighborCoord.add(testRightCoord);
						}
					}
				}
		}
	}
	
	Neighborhood n = new Neighborhood(neighborTerritory,neighborTileType,neighborTerrain,neighborCoord);		
	return n;
}	

	public static Neighborhood getDominoRightNeighbors(List<KingdomTerritory> t, DominoInKingdom dInK) {
			
			List <KingdomTerritory> neighborTerritory=new ArrayList<KingdomTerritory>();
			List <String> neighborTileType=new ArrayList<String>();
			List <TerrainType> neighborTerrain = new ArrayList<TerrainType>();
			List <int []> neighborCoord = new ArrayList<int[]>();
		
			int [] otherXY=calculateOtherPos(dInK);
			int searchX=otherXY[0];
			int searchY=otherXY[1];
			
			for (KingdomTerritory each:t) {
				
				int [] testRightCoord=calculateOtherPos(each);
				
				int testLeftX=each.getX();
				int testLeftY=each.getY();
				int testRightX=testRightCoord[0];
				int testRightY=testRightCoord[1];
				
				int [] testLeftCoord = {testLeftX,testLeftY};
				
				int norm1=L2NormSquared(searchX,searchY,testLeftX,testLeftY);
				int norm2=L2NormSquared(searchX,searchY,testRightX,testRightY);
	
				if (norm1==1 || norm2==1) {
					if (each instanceof Castle) {
						neighborTerritory.add(each);
						neighborTileType.add("castle");
						neighborTerrain.add(dInK.getDomino().getRightTile());
						neighborCoord.add(testLeftCoord);
					}
					else if (each instanceof DominoInKingdom) {
						if (((DominoInKingdom) each).getDomino().getId()!=dInK.getDomino().getId()) {
							neighborTerritory.add(each);
							if (norm1==1) {
								neighborTileType.add("left");
								neighborTerrain.add(((DominoInKingdom) each).getDomino().getLeftTile());
								neighborCoord.add(testLeftCoord);
							}
							else if (norm2==1) {
								neighborTileType.add("right");
								neighborTerrain.add(((DominoInKingdom) each).getDomino().getRightTile());
								neighborCoord.add(testRightCoord);
							}
						}
					}
			}
		}
			
		Neighborhood n = new Neighborhood(neighborTerritory,neighborTileType,neighborTerrain,neighborCoord);		
		return n;
	}	

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
	
	private static int L2NormSquared(int x1, int y1, int x2, int y2) {
		int deltaX=x2-x1;
		int deltaY=y2-y1;
		
		int norm = deltaX*deltaX+deltaY*deltaY;
		
		return norm;
	}
	
	private static int[] minMaxArray(int[] x) {
		int[] xTemp=x.clone();
		Arrays.sort(xTemp);
		int[] minmax= {xTemp[0],xTemp[xTemp.length-1]};
		return minmax;
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
	
	private int argmax (int[] x) {
		int max=0;
		int index=-1;
		
		for (int i=0;i<x.length;i++) {
			if (x[i]>max) {
				max=x[i];
				index=i;
			}
		}
		return index;
	}
	
	private int argmin (int[] x) {
		int min=0;
		int index=-1;
		
		for (int i=0;i<x.length;i++) {
			if (x[i]<min) {
				min=x[i];
				index=i;
			}
		}
		return index;
	}

}
