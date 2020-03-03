package ca.mcgill.ecse223.kingdomino.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.model.Castle;
import ca.mcgill.ecse223.kingdomino.model.Domino;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom;
import ca.mcgill.ecse223.kingdomino.model.Game;
import ca.mcgill.ecse223.kingdomino.model.Kingdom;
import ca.mcgill.ecse223.kingdomino.model.KingdomTerritory;
import ca.mcgill.ecse223.kingdomino.model.Player;
import ca.mcgill.ecse223.kingdomino.model.Domino.DominoStatus;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom.DirectionKind;

public class KDQuery {

	//////////////////////////////////
	/* 	   Jay's Query Methods      */
	/////////////////////////////////

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



	public static boolean CastleNeighborhoodAvailable(Player aPlayer) {
		
		List<KingdomTerritory> t = aPlayer.getKingdom().getTerritories();
		int [][] existingCoords = KDQuery.getPlayerTerritoryCoordinates(aPlayer);
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
