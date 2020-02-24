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
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom.DirectionKind;

public class KDQuery {
	
	public KDQuery() {}
	
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
	
	
	public static void prePlaceNextPlayerDomino(Domino dominoToPlace, String dir, int posx, int posy) {

		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		Kingdom kingdom = game.getNextPlayer().getKingdom();
		
		DominoInKingdom domInKingdom = new DominoInKingdom(posx, posy, kingdom, dominoToPlace);
		domInKingdom.setDirection(getDirection(dir));
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
	
//	private helper methods
	
	private static int[] minMaxArray(int[] x) {
		int[] xTemp=x.clone();
		Arrays.sort(xTemp);
		int[] minmax= {xTemp[0],xTemp[xTemp.length-1]};
		return minmax;
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

}
