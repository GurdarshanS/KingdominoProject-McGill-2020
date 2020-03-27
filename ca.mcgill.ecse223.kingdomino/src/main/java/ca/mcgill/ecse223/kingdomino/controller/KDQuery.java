package ca.mcgill.ecse223.kingdomino.controller;


import java.util.*;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.model.Castle;
import ca.mcgill.ecse223.kingdomino.model.Domino;
import ca.mcgill.ecse223.kingdomino.model.Domino.DominoStatus;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom;
import ca.mcgill.ecse223.kingdomino.model.Game;
import ca.mcgill.ecse223.kingdomino.model.Kingdom;
import ca.mcgill.ecse223.kingdomino.model.KingdomTerritory;
import ca.mcgill.ecse223.kingdomino.model.Player;
import ca.mcgill.ecse223.kingdomino.model.Property;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom.DirectionKind;

public class KDQuery {
	
	public KDQuery() {}
	
	
	/**
	 * 
	 * This method checks a player's kingdom for any territory that
	 * violates one of more of the verification methods that checks
	 * kingdom grid size, castle adjacency (when applicable), 
	 * neighbor adjacency, and overlap. When violations occur, return
	 * an "invalid" string, "valid" otherwise.
	 * 
	 * @see - no features associated, but used in many When calls of cucumber features
	 * @author Jing Han 260528152
	 * @param player
	 * @return validity
	 */
	
	public static String getKingdomVerificationResult(Player player) {
		String validity="valid";
		
		List<DominoInKingdom> errorneouslyPlacedDominos=KDQuery.getErroneouslyPrePlacedDomino(player);
		if (!errorneouslyPlacedDominos.isEmpty()) {
			validity="invalid";
		}
		
		return validity;
	}
	
	public static int[] playerMaxPropSizeAndNumCrown(Player player) {
		int size=-1;
		int crown=0;
		
		for (Property prop:player.getKingdom().getProperties()) {
			if (prop.getSize()>size) size=prop.getSize();
			crown+=prop.getCrowns();
		}
		
		int[] sizeAndCrown= {size,crown};
		return sizeAndCrown;
	}
	
	/**
	 * 
	 * This method checks to see if the coordinates around
	 * the castle are available. This forms the premise for 
	 * whether to initiate the VerifyCastleAdjacency checks
	 * 
	 * @see - no direct features, but associated with VerifyCastleAdjacency
	 * @author Jing Han 260528152
	 * @param player
	 * @return boolean
	 */
	
	public static boolean CastleNeighborhoodAvailable(Player player) {
		List<KingdomTerritory> t =player.getKingdom().getTerritories();
		int [][] existingCoords = KDQuery.getPlayerTerritoryCoordinates(player);
		int [] existingX1=existingCoords[0];
		int [] existingY1=existingCoords[1];
		int [] existingX2=existingCoords[2];
		int [] existingY2=existingCoords[3];
		
		boolean leftOccupied=false;
		boolean rightOccupied=false;
		boolean topOccupied=false;
		boolean bottomOccupied=false;
		
		for (int k=0;k<existingX1.length;k++) {
			int x1=existingX1[k];
			int y1=existingY1[k];
			int x2=existingX2[k];
			int y2=existingY2[k];
			
			if ((x1==-1 && y1==0)||(x2==-1 && y2==0)) {
				leftOccupied=true;
			}
			
			if ((x1==1 && y1==0)||(x2==1 && y2==0)) {
				rightOccupied=true;
			}
			
			if ((x1==0 && y1==1)||(x2==0 && y2==1)) {
				topOccupied=true;
			}
			if ((x1==0 && y1==-1)||(x2==0 && y2==-1)) {
				bottomOccupied=true;
			}
		}
		
		if (leftOccupied && rightOccupied && topOccupied && bottomOccupied) {
			return false;
		}
		else {
			return true;
		}
	}
	
	/**
	 * 
	 * This method checks the kingdom and collects all DominoInKingdom whose
	 * status is ErroneouslyPrePlaced
	 * 
	 * @see - no direct features, but associated with all the verification methods
	 * @author Jing Han 260528152
	 * @param player
	 * @return errorDominos
	 */
	
	public static List<DominoInKingdom> getErroneouslyPrePlacedDomino(Player player) {
		List<DominoInKingdom> errorDominos = new ArrayList<DominoInKingdom>();
		List<KingdomTerritory> t = getPlayerTerritory(player);
		for (KingdomTerritory each:t) {
			if (each instanceof DominoInKingdom) {
				if (((DominoInKingdom) each).getDomino().getStatus().equals(DominoStatus.ErroneouslyPreplaced)) {
					errorDominos.add((DominoInKingdom) each);	
				}
			}
		}
		return errorDominos;
	}
	
	/**
	 * 
	 * wrapper for returning player territories
	 * 
	 * @author Jing Han 260528152
	 * @param player
	 * @return List<KingdomTerritory>
	 */
	
	public static List<KingdomTerritory> getPlayerTerritory(Player player){
		return player.getKingdom().getTerritories();		
	}
	
	public static int getPlayerKingdomSize(Player player) {
		
		int[][] dominoPos=getPlayerTerritoryCoordinates(player);

		int maxSize=0;
		for (int[] each:dominoPos) {
			int[] minmax=minMaxArray(each);
			int newSize=minmax[1]-minmax[0]+1;
			if (newSize>maxSize) {
				maxSize=newSize;
			}
		}
		return maxSize;
	}
	
	
	
	/**
	 * 
	 * This method finds the coordinates of all the KingdomTerritory objects of player
	 * 
	 * @see - no direct features, but associated with all the verification methods
	 * @author Jing Han 260528152
	 * @param player
	 * @return dominoPos
	 */
	
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
				int[] otherPos=calculateRightPos(t);
				dominoPos[0][i]=t.getX();
				dominoPos[1][i]=t.getY();
				dominoPos[2][i]=otherPos[0];
				dominoPos[3][i]=otherPos[1];

			}
		}
		return dominoPos;
	}
	
//	private helper methods
	
	public static int[] calculateRightPos(KingdomTerritory d) {
		
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
	
	
	public static List<List<Integer>> getValidFreeCoordinates(Player player) {
		List<List<Integer>> allPossibleCoords = new ArrayList<List<Integer>>();
		List<List<Integer>> combinedOccupiedCoords = new ArrayList<List<Integer>>();
		
		List<Integer> allX = new ArrayList<Integer>();
		List<Integer> allY = new ArrayList<Integer>();
		
		int[][] occupiedCoords = KDQuery.getPlayerTerritoryCoordinates(player);
				
		for (int i=0;i<occupiedCoords[0].length;i++) {
			int[] leftCoord= {occupiedCoords[0][i],occupiedCoords[1][i]};
			int[] rightCoord= {occupiedCoords[2][i],occupiedCoords[3][i]};
			
			if ((leftCoord[0]==rightCoord[0])&&(leftCoord[1]==rightCoord[1])) {
				List<Integer> tmp=new ArrayList<Integer>(Arrays.asList(occupiedCoords[0][i],occupiedCoords[1][i]));
				combinedOccupiedCoords.add(tmp);
			}
			else {
				List<Integer> tmp1=new ArrayList<Integer>(Arrays.asList(occupiedCoords[0][i],occupiedCoords[1][i]));
				List<Integer> tmp2=new ArrayList<Integer>(Arrays.asList(occupiedCoords[2][i],occupiedCoords[3][i]));
				combinedOccupiedCoords.add(tmp1);
				combinedOccupiedCoords.add(tmp2);
			}
			allX.add(occupiedCoords[0][i]);
			allY.add(occupiedCoords[1][i]);
			allX.add(occupiedCoords[2][i]);
			allY.add(occupiedCoords[3][i]);	
		}
		
		int minX=Collections.min(allX);
		int maxX=Collections.max(allX);
		int minY=Collections.min(allY);
		int maxY=Collections.max(allY);


		for (int x=minX-1;x<=maxX+1;x++) {
			for (int y=minY-1;y<=maxY+1;y++) {
				List<Integer> tmp=new ArrayList<Integer>(Arrays.asList(x,y));
				allPossibleCoords.add(tmp);
			}
		}

		allPossibleCoords.removeAll(combinedOccupiedCoords);
		
		return allPossibleCoords;

	}
	
	
	
	
	/**
	 * 
	 * This helper method returns the min and max of an array
	 * @author Jing Han 260528152
	 * @param x
	 * @return minmax
	 */
	private static int[] minMaxArray(int[] x) {
		int[] xTemp=x.clone();
		Arrays.sort(xTemp);
		int[] minmax= {xTemp[0],xTemp[xTemp.length-1]};
		return minmax;
	}
	
	/**
	 * 
	 * Duplicate of the TA's helper method to faciliate call here
	 * 
	 * @param dir
	 * @return DirectionKind
	 */
	
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

}
