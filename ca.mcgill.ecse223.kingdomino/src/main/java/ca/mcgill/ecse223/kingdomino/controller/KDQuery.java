package ca.mcgill.ecse223.kingdomino.controller;


import java.util.*;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.model.Domino.DominoStatus;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom.DirectionKind;
import ca.mcgill.ecse223.kingdomino.model.*;

public class KDQuery {
	
	public KDQuery() {}
	
	
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
	
	public static int getNumDraftsInGame() {
		Kingdomino kd = KingdominoApplication.getKingdomino();
		Game game = kd.getCurrentGame();
		return game.getAllDrafts().size();
	}
	
	public static boolean isDominoTaken(Domino domino) {
		return domino.hasDominoSelection();
	}
	
	public static boolean isCurrentPlayerTheLastInTurn(Player p) {
		List<Player> allPlayers=p.getGame().getPlayers();
		Player lastPlayer=allPlayers.get(allPlayers.size()-1);
		return p.equals(lastPlayer);
	}
	
	public static boolean hasAllPlayersChosen() {
		Kingdomino kd = KingdominoApplication.getKingdomino();
		Game game=kd.getCurrentGame();
		
		boolean allChosen=true;
		
		for (Player p:game.getPlayers()) {
			if (p.hasDominoSelection()==false) {
				allChosen=false;
				break;
			}
		}
		return allChosen;
	}
	
	public static boolean isThereAvailablePlacement(Player player, DominoInKingdom dInK) {
		List<List<Integer>> freeCoords=KDQuery.getValidFreeCoordinates(player);
		if (freeCoords.size()==0) {
//			System.out.println("no free space on board at all, return false");
			return false;
		}
		
		int prevX=dInK.getX();
		int prevY=dInK.getY();
		DominoInKingdom.DirectionKind prevD=dInK.getDirection();
		Domino.DominoStatus prevStatus=dInK.getDomino().getStatus();
		
		
		for (int j=0;j<freeCoords.size();j++) {
			
			List<Integer> coord=freeCoords.get(j);
			dInK.setX(coord.get(0));
			dInK.setY(coord.get(1));
			
			if (verifyDominoInKingdom(player,dInK)) {
//				System.out.println("initial pre-placement valid, return true");
				return true;
			}
			
			else {
				for (int i=0;i<4;i++) {
					KDController.rotateLatestDomino(player, "clockwise");
//					System.out.println("rotating clockwise...");
					if (verifyDominoInKingdom(player,dInK)) {
//						System.out.println("rotated placement valid, return true");
//						System.out.println("valid placement found at: ["+dInK.getX()+","+dInK.getY()+","+dInK.getDirection()+"]");
						dInK.setX(prevX);
						dInK.setY(prevY);
						dInK.setDirection(prevD);
						dInK.getDomino().setStatus(prevStatus);
						return true;
					}
//					System.out.println("invalid placement, rotate again");
				}
				
//				System.out.println("all orientations at this position invalid, remove and try again");
			}
			
		}
		
		dInK.setX(prevX);
		dInK.setY(prevY);
		dInK.setDirection(prevD);
		dInK.getDomino().setStatus(prevStatus);
		return false;
	}
	
	public static boolean verifyDominoInKingdom(Player player, DominoInKingdom dominoToPlace) {
		
		if (KDController.verifyGridSizeAllKingdom(player,dominoToPlace)) {
			if(KDController.verifyNoOverlapLastTerritory(player,dominoToPlace)) {
				if (KDController.verifyCastleAdjacency(player,dominoToPlace)||KDController.verifyNeighborAdjacencyLastTerritory(player,dominoToPlace)) {
					return true;
				}
				else {
//					System.out.println("\ninvalid: neither neighbor or castle adjacent");
					return false;
				}
			}
			else {
//				System.out.println("\ninvalid: overlap\n");
				return false;
			}
		}
		else {
//			System.out.println("\ninvalid: grid size exceeded\n");
			return false;
		}
	}
	
	public static boolean isDominoPileEmpty() {
		Kingdomino kd = KingdominoApplication.getKingdomino();
		Game game=kd.getCurrentGame();
		List<Domino> dominoPile=game.getAllDominos();
		
		int counter=0;
		for (Domino d:dominoPile) {
			if (d.getStatus().equals(DominoStatus.InPile)||d.getStatus().equals(DominoStatus.InCurrentDraft)) {
				counter+=1;
			}
		}
		
		if (counter==0) return true;
		else return false;
	}
	
	public static boolean hasAllPlayersPlayed() {
		Kingdomino kd = KingdominoApplication.getKingdomino();
		Game game=kd.getCurrentGame();
		List<Player> players=game.getPlayers();
		
		boolean allPlayed=true;
		
		for (Player p:players) {
			Domino lastDomino=p.getDominoSelection().getDomino();
			if (!(lastDomino.getStatus().equals(DominoStatus.PlacedInKingdom)||lastDomino.getStatus().equals(DominoStatus.Discarded))) {
				allPlayed=false;
				break;
			}
		}
		
		return allPlayed;
	}
	
	public static boolean isCurrentTurnTheLastInGame() {
		Kingdomino kd = KingdominoApplication.getKingdomino();
		Game game=kd.getCurrentGame();
		List<Domino> dominoPile=game.getAllDominos();
		
		int playerNum=game.getNumberOfPlayers();
		int index=dominoPile.size()-1;
		
		boolean lastTurn=true;
		
		if (playerNum==2||playerNum==4) {
			for (int i=0;i<4;i++) {
				Domino d=dominoPile.get(index);
				if (!d.getStatus().equals(DominoStatus.InCurrentDraft)) {
					lastTurn=false;
					break;
				}
				index--;
			}
		}
		return lastTurn;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static boolean isDraftLimitReached() {
		Kingdomino kd = KingdominoApplication.getKingdomino();
		Game game = kd.getCurrentGame();
		int draftNumLimit=0;
		
		if ((game.getNumberOfPlayers()==4)||(game.getNumberOfPlayers()==3)) draftNumLimit=12;
		if (game.getNumberOfPlayers()==2) draftNumLimit=6;
		
		return getNumDraftsInGame()==draftNumLimit;
	}
	

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
