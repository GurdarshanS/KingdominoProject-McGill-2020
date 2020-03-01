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

	public static void discardDomino(Game aGame, Domino aSelectedDomino, Player aPlayer) throws java.lang.IllegalArgumentException{

//		if(aGame == null) throw new java.lang.IllegalArgumentException("Game does not exist");
//		
//		if(!aSelectedDomino.getDominoSelection().getPlayer().equals(aPlayer)) throw new java.lang.IllegalArgumentException("This domino does not belong to this player");
//		
//		if(!doesPlayerBelong(aGame, aPlayer)) throw new java.lang.IllegalArgumentException("This player does not belong to this game");
//		
//		DominoInKingdom dInKingdom = new DominoInKingdom(0, 0, aPlayer.getKingdom(), aSelectedDomino);
//	
//		for(int i = -4; i< 5; i++) {
//			
//			for(int j = -4; j<5; j++) {
//				
//				dInKingdom = new DominoInKingdom(i, j, aPlayer.getKingdom(), aSelectedDomino);
//				
//				for(int z = 0; z<4; z++) {
//					
//					aSelectedDomino.setStatus(DominoStatus.CorrectlyPreplaced);
//					
//					rotateCurrentDomino(dInKingdom, "Clockwise");
//				
//					System.out.println(dInKingdom.getX());
//					System.out.println(dInKingdom.getY());
//					System.out.println("Before: " + dInKingdom.getDomino().getStatus());
//					System.out.println(dInKingdom.getDirection());
//					
//					verifyCastleAdjacency(aPlayer);
//					verifyGridSize(aPlayer);
//					verifyNeighborAdjacencyLastTerritory(aPlayer);
//					verifyNoOverlapAllTerritories(aPlayer);
//					
//					System.out.println("After: " + dInKingdom.getDomino().getStatus());
//					
//					if(aSelectedDomino.getStatus().equals(DominoStatus.CorrectlyPreplaced)) break;
//					
//				}
//				
//				if(aSelectedDomino.getStatus().equals(DominoStatus.CorrectlyPreplaced)) break;
//				
//			}
//			
//			if(aSelectedDomino.getStatus().equals(DominoStatus.CorrectlyPreplaced)) break;
//			
//		}
//		
//		if(aSelectedDomino.getStatus().equals(DominoStatus.CorrectlyPreplaced)) {
//			
//			aSelectedDomino.setStatus(DominoStatus.ErroneouslyPreplaced);
//			dInKingdom = new DominoInKingdom(0, 0, aPlayer.getKingdom(), aSelectedDomino);
//			
//		}
//	
//		else {
//			
//			aSelectedDomino.setStatus(DominoStatus.Discarded);
//			dInKingdom.delete();
//			aPlayer.getDominoSelection().delete();
//			
//		}
		
		if(aSelectedDomino.getId() == 48) {
			
//		System.out.println(aSelectedDomino.getId() + "    " + aSelectedDomino.getRightTile() + "    " + aSelectedDomino.getLeftTile());
//		
//		for(int i=0; i<aPlayer.getKingdom().getTerritories().size(); i++) {
//			
//			System.out.println(aPlayer.getKingdom().getTerritories().get(i).getX() + "    " + aPlayer.getKingdom().getTerritories().get(i).getY() );
//			
//		}
		
		DominoInKingdom kind = (DominoInKingdom) aPlayer.getKingdom().getTerritory(aPlayer.getKingdom().getTerritories().size()-1);
		
		kind.setDirection(DirectionKind.Down);
	
		System.out.println(kind.getDomino().getId());
		System.out.println(kind.getDomino().getStatus());
		
		verifyCastleAdjacency(aPlayer);
		verifyGridSize(aPlayer);
		verifyNeighborAdjacencyLastTerritory(aPlayer);
		verifyNoOverlapAllTerritories(aPlayer);
		
			if(aPlayer.getDominoSelection().getDomino().getStatus().equals(DominoStatus.CorrectlyPreplaced)) {
				
				System.out.println("Booooom");
				
			}
			
			else {
				
				System.out.println(kind.getDomino().getStatus());
				
			}
		}
	}
	
	public static void rotateCurrentDomino(DominoInKingdom DInKingdom, String rotation) throws java.lang.IllegalArgumentException {
		
		DirectionKind dominoDir = DInKingdom.getDirection();
		
		if(dominoDir.equals(DirectionKind.Up) && rotation.equalsIgnoreCase("Clockwise")) DInKingdom.setDirection(DirectionKind.Right);
		else if(dominoDir.equals(DirectionKind.Right) && rotation.equalsIgnoreCase("Clockwise")) DInKingdom.setDirection(DirectionKind.Down);
		else if(dominoDir.equals(DirectionKind.Down) && rotation.equalsIgnoreCase("Clockwise")) DInKingdom.setDirection(DirectionKind.Left);
		else if(dominoDir.equals(DirectionKind.Left) && rotation.equalsIgnoreCase("Clockwise")) DInKingdom.setDirection(DirectionKind.Up);
		
		if(dominoDir.equals(DirectionKind.Up) && rotation.equalsIgnoreCase("CounterClockwise")) DInKingdom.setDirection(DirectionKind.Left);
		else if(dominoDir.equals(DirectionKind.Left) && rotation.equalsIgnoreCase("CounterClockwise")) DInKingdom.setDirection(DirectionKind.Down);
		else if(dominoDir.equals(DirectionKind.Down) && rotation.equalsIgnoreCase("CounterClockwise")) DInKingdom.setDirection(DirectionKind.Right);
		else if(dominoDir.equals(DirectionKind.Right) && rotation.equalsIgnoreCase("CounterClockwise")) DInKingdom.setDirection(DirectionKind.Up);
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	////////////////////////////////////////////////
	/*			Jay's Controller Methods          */
    ////////////////////////////////////////////////
	
	public static void verifyGridSize(Player player) {


		List<KingdomTerritory> t = player.getKingdom().getTerritories();

		List<Integer> xCoords = new ArrayList<Integer>();

		List<Integer> yCoords = new ArrayList<Integer>();


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

				}
				
			}


		}

	}

	public static void verifyNoOverlapAllTerritories(Player player) {

		List<KingdomTerritory> territories = player.getKingdom().getTerritories();

		
		if (territories.size()==1) {

			return;

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

					}
					
				}

			}

		
		}
	

	}


	public static void verifyCastleAdjacency(Player player) {

		List <KingdomTerritory> t = player.getKingdom().getTerritories();

		

		if (t.size()==1) {

			return;

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

		

		if (((norm1!=1)&&(norm2!=1))||(testX1==0 && testY1==0)||(testX2==0 && testY2==0)) {

			((DominoInKingdom) testD).getDomino().setStatus(DominoStatus.ErroneouslyPreplaced);

		}
	}


	public static void verifyNeighborAdjacencyLastTerritory(Player player) {

		
		List<KingdomTerritory> t =player.getKingdom().getTerritories();

		

		if (t.size()==1) {

			return;

		}

		else {


			int validNeighborCount=0;

			
			DominoInKingdom prePlacedDomino = (DominoInKingdom) t.get(t.size()-1);


			Neighborhood leftNeighborhood = getDominoLeftNeighbors(t,prePlacedDomino);

			Neighborhood rightNeighborhood =getDominoRightNeighbors(t,prePlacedDomino);


			List<TerrainType> leftTileNeighborTerrains = leftNeighborhood.getNeighborTerrainType();

			List<TerrainType> rightTileNeighborTerrains = rightNeighborhood.getNeighborTerrainType();


			if (leftTileNeighborTerrains.isEmpty() && rightTileNeighborTerrains.isEmpty()) {

				//System.out.println("no neighbors at all");

			}

			

			if (!leftTileNeighborTerrains.isEmpty()) {

				for (TerrainType testTerrain:leftTileNeighborTerrains) {

					if (testTerrain.name().equalsIgnoreCase(prePlacedDomino.getDomino().getLeftTile().name())){

						validNeighborCount++;

					}

				}

			}

			

			if (!rightTileNeighborTerrains.isEmpty()) {

				for (TerrainType testTerrain:rightTileNeighborTerrains) {

					if (testTerrain.name().equalsIgnoreCase(prePlacedDomino.getDomino().getRightTile().name())){

						validNeighborCount++;

					}

				}

			}

			

			if (validNeighborCount==0) {

				prePlacedDomino.getDomino().setStatus(DominoStatus.ErroneouslyPreplaced);

			}
			
		}

	}
	
	
	////////////////////////////////////////////////
	/*		  Jay's Private helper Methods        */
    ////////////////////////////////////////////////
	
	
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


		int bx1=b.getX();

		int by1=b.getY();

		int bx2=otherB[0];

		int by2=otherB[1];


		if (ax1==bx1 && ay1==by1) {

			return true;

		}

		else if (ax1==bx2 && ay2==by2) {

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
	
	
	////////////////////////////////////////////////
	/*	    Massimo's Private helper Methods      */
    ////////////////////////////////////////////////
	

	private static boolean doesPlayerBelong(Game aGame, Player aPlayer) {
		
		for(int i = 0; i< aGame.getPlayers().size(); i++) {
			
			if(aGame.getPlayer(i).equals(aPlayer)) return true;
			
		}
	
		
		return false;
		
	}
	
}
