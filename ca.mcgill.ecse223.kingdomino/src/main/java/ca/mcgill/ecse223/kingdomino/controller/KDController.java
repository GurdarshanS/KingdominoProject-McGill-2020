package ca.mcgill.ecse223.kingdomino.controller;

import ca.mcgill.ecse223.kingdomino.model.*;
import ca.mcgill.ecse223.kingdomino.model.Domino.DominoStatus;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom.DirectionKind;
import java.util.*;

public class KDController {

	/**
	 * 
	 * This method checks if a player is allowed to
	 * discard the domino they have selected and
	 * prePlaced in their kingdom. If they are allowed
	 * to do so, the domino is discarded and their
	 * dominoSelected is deleted. If not, the dominos
	 * status is changed to ErroneouslyPrePlaced.
	 * 
	 * @see DiscardDomino.feature
	 * @author Massimo Vadacchino 260928064
	 * @param aPlayer
	 * @return void
	 * @throws java.lang.IllegalArgumentException
	 */
	
	public static void discardDomino(Player aPlayer) throws java.lang.IllegalArgumentException{ 
				
		if(aPlayer == null) throw new java.lang.IllegalArgumentException("This player does not exist");
		
		DominoInKingdom newlyPrePlacedDomino = (DominoInKingdom) aPlayer.getKingdom().getTerritory(aPlayer.getKingdom().getTerritories().size()-1);
		
		if(newlyPrePlacedDomino.getDomino().getStatus().equals(DominoStatus.PlacedInKingdom)) throw new java.lang.IllegalArgumentException("This domino is already placed in the players kingdom");

		for(int i = -4; i<5; i++) {
			
			for(int j = -4; j<5; j++) {
				
				for(int z = 0; z<4; z++) {
					
					newlyPrePlacedDomino.setX(i);
					newlyPrePlacedDomino.setY(j);
					
					if(verifyGridSizeAllKingdom(aPlayer) && verifyNoOverlapLastTerritory(aPlayer) && verifyNeighborAdjacencyLastTerritory(aPlayer)) {
						
						newlyPrePlacedDomino.getDomino().setStatus(DominoStatus.ErroneouslyPreplaced);
						return;
						
					}
					
					else if(verifyGridSizeAllKingdom(aPlayer) && verifyNoOverlapLastTerritory(aPlayer) && verifyCastleAdjacency(aPlayer)) {
						
						newlyPrePlacedDomino.getDomino().setStatus(DominoStatus.ErroneouslyPreplaced);
						return;
						
					}	
					
					rotateCurrentDomino(aPlayer, newlyPrePlacedDomino, "Clockwise");
				
				}
				
			}
		
		}
		
		newlyPrePlacedDomino.getDomino().setStatus(DominoStatus.Discarded);
		newlyPrePlacedDomino.delete();
		aPlayer.getDominoSelection().delete();
		
	}
	
	/**
	 * 
	 * This method allows a player to rotate 
	 * the domino they have selected and 
	 * prePlaced in their kingdom in 2 directions,
	 * ClockWise or Counter-ClockWise. This may only
	 * be possible if the rotation does stay within
	 * the 9x9 grid size. The dominoes status and direction
	 * is updated accordingly.
	 * 
	 * 
	 * @see RotateCurrentDomino.feature
	 * @author Massimo Vadacchino 260928064
	 * @param aPlayer
	 * @param dInKingdom
	 * @param rotation
	 * @return void
	 * @throws java.lang.IllegalArgumentException
	 * 
	 */
	
	public static void rotateCurrentDomino(Player aPlayer, DominoInKingdom dInKingdom, String rotation) throws java.lang.IllegalArgumentException { 

		if(aPlayer == null || dInKingdom == null) throw new java.lang.IllegalArgumentException("Invalid input");
		if(!(((DominoInKingdom)aPlayer.getKingdom().getTerritory(aPlayer.getKingdom().getTerritories().size() -1)).equals(dInKingdom))) throw new java.lang.IllegalArgumentException("This domino does not belong to this players kingdom");
		if(dInKingdom.getDomino().getStatus().equals(DominoStatus.PlacedInKingdom)) throw new java.lang.IllegalArgumentException("This domino is already placed in the players kingdom");
		
		DirectionKind dominoDir = dInKingdom.getDirection();
		
		if(dominoDir.equals(DirectionKind.Up) && rotation.equalsIgnoreCase("Clockwise")) dInKingdom.setDirection(DirectionKind.Right);
		else if(dominoDir.equals(DirectionKind.Right) && rotation.equalsIgnoreCase("Clockwise")) dInKingdom.setDirection(DirectionKind.Down);
		else if(dominoDir.equals(DirectionKind.Down) && rotation.equalsIgnoreCase("Clockwise")) dInKingdom.setDirection(DirectionKind.Left);
		else if(dominoDir.equals(DirectionKind.Left) && rotation.equalsIgnoreCase("Clockwise")) dInKingdom.setDirection(DirectionKind.Up);
			
		if(dominoDir.equals(DirectionKind.Up) && rotation.equalsIgnoreCase("CounterClockwise")) dInKingdom.setDirection(DirectionKind.Left);
		else if(dominoDir.equals(DirectionKind.Left) && rotation.equalsIgnoreCase("CounterClockwise")) dInKingdom.setDirection(DirectionKind.Down);
		else if(dominoDir.equals(DirectionKind.Down) && rotation.equalsIgnoreCase("CounterClockwise")) dInKingdom.setDirection(DirectionKind.Right);
		else if(dominoDir.equals(DirectionKind.Right) && rotation.equalsIgnoreCase("CounterClockwise")) dInKingdom.setDirection(DirectionKind.Up);
		
		if(!verifyGridLimit(dInKingdom)) {
	
			dInKingdom.setDirection(dominoDir);
			return;
			
		}
		
		else {
			
			if(verifyNeighborAdjacencyLastTerritory(aPlayer) && verifyNoOverlapLastTerritory(aPlayer) && verifyGridSizeAllKingdom(aPlayer)) {
				
				dInKingdom.getDomino().setStatus(DominoStatus.CorrectlyPreplaced);
				return;
				
			}
				
			else if(verifyCastleAdjacency(aPlayer) && verifyNoOverlapLastTerritory(aPlayer) && verifyGridSizeAllKingdom(aPlayer)) {
					
				dInKingdom.getDomino().setStatus(DominoStatus.CorrectlyPreplaced);
				return;
				
			}
		
			dInKingdom.getDomino().setStatus(DominoStatus.ErroneouslyPreplaced);
			
		}
	
	}
	
	/**
	 * 
	 * This method allows a player to place their
	 * selected domino into their kingdom if and
	 * only if their domino passes the verifications
	 * (no overlapping, within kingdom size, has a neighbour,
	 * and adjacent to the castle) and has a status
	 * of "CorrectlyPrePlaced". If not, the domino will 
	 * have the same attributes as before. 
	 * 
	 * @see PlaceDomino.feature
	 * @author Massimo Vadacchino 260928064
	 * @param aPlayer
	 * @param dominoToPlace
	 * @return void
	 * @throws java.lang.IllegalArgumentException
	 * 
	 */
	
	public static void placeDomino(Player aPlayer, Domino dominoToPlace) throws java.lang.IllegalArgumentException { 
		
		if(aPlayer == null || dominoToPlace == null) throw new java.lang.IllegalArgumentException("Invalid input");
		if(!(dominoToPlace.getDominoSelection().getPlayer().equals(aPlayer))) throw new java.lang.IllegalArgumentException("This domino does not belong to this player");
		if(dominoToPlace.getStatus().equals(DominoStatus.PlacedInKingdom)) throw new java.lang.IllegalArgumentException("This domino is already placed in this players kingdom");
		
		if(verifyGridSizeAllKingdom(aPlayer) &&  verifyNoOverlapLastTerritory(aPlayer) && verifyNeighborAdjacencyLastTerritory(aPlayer) && dominoToPlace.getStatus().equals(DominoStatus.CorrectlyPreplaced)) {
			
			dominoToPlace.setStatus(DominoStatus.PlacedInKingdom);
			aPlayer.getDominoSelection().delete();
			
		}
		
		else if(verifyGridSizeAllKingdom(aPlayer) && verifyNoOverlapLastTerritory(aPlayer) && verifyCastleAdjacency(aPlayer) && dominoToPlace.getStatus().equals(DominoStatus.CorrectlyPreplaced)) {
			
			dominoToPlace.setStatus(DominoStatus.PlacedInKingdom);	
			aPlayer.getDominoSelection().delete();
			
		}
		
	}
	
	/**
	 * 
	 * This method allows a player to move 
	 * the domino they have selected and 
	 * prePlaced in their kingdom in 4 directions,
	 * up, down, left, and right. This may only
	 * be possible if the movement does stay within
	 * the 9x9 grid size. The dominoes status and position
	 * is updated accordingly.
	 * 
	 * 
	 * @see MoveCurrentDomino.feature
	 * @author Massimo Vadacchino 260928064
	 * @param aPlayer
	 * @param dInKingdom
	 * @param movement
	 * @return void
	 * @throws java.lang.IllegalArgumentException
	 * 
	 */
	
	public static void moveCurrentDomino(Player aPlayer, DominoInKingdom dInKingdom, String movement) throws java.lang.IllegalArgumentException{
	
		if(aPlayer == null || dInKingdom == null) throw new java.lang.IllegalArgumentException("Invalid input");
		if(!(((DominoInKingdom)aPlayer.getKingdom().getTerritory(aPlayer.getKingdom().getTerritories().size() -1)).equals(dInKingdom))) throw new java.lang.IllegalArgumentException("This domino does not belong to this players kingdom");
		if(dInKingdom.getDomino().getStatus().equals(DominoStatus.PlacedInKingdom)) throw new java.lang.IllegalArgumentException("This domino is already placed in the players kingdom");
		
		int xPosPrevious = dInKingdom.getX();
		int yPosPrevious = dInKingdom.getY();

		if(movement.equalsIgnoreCase("Right")) dInKingdom.setX(xPosPrevious + 1);
		else if(movement.equalsIgnoreCase("Left")) dInKingdom.setX(xPosPrevious - 1);
		else if(movement.equalsIgnoreCase("Up")) dInKingdom.setY(yPosPrevious + 1);
		else if(movement.equalsIgnoreCase("Down")) dInKingdom.setY(yPosPrevious - 1);
		
		if(!verifyGridLimit(dInKingdom)) {
	
			dInKingdom.setX(xPosPrevious);
			dInKingdom.setY(yPosPrevious);
			
			return;
			
		}
		
		else {
			
			if(verifyNeighborAdjacencyLastTerritory(aPlayer) && verifyNoOverlapLastTerritory(aPlayer) && verifyGridSizeAllKingdom(aPlayer)) {
				
				dInKingdom.getDomino().setStatus(DominoStatus.CorrectlyPreplaced);
				return;
				
			}
				
			else if(verifyCastleAdjacency(aPlayer) && verifyNoOverlapLastTerritory(aPlayer) && verifyGridSizeAllKingdom(aPlayer)) {
					
				
				dInKingdom.getDomino().setStatus(DominoStatus.CorrectlyPreplaced);
				return;
				
			}
		
			dInKingdom.getDomino().setStatus(DominoStatus.ErroneouslyPreplaced);
			
		}
		
	}
	
		////////////////////////////////////////////////
		/*			   My Helper Methods              */
		////////////////////////////////////////////////
	
	
	/**
	 * 
	 * This method verifies if the current prePlaced
	 * dominos position and direction is respecting
	 * the grid size limit of 9x9. If it does, then the
	 * method returns true. If not, then it returns
	 * false.
	 * 
	 * 
	 * @author Massimo Vadacchino 260928064
	 * @param dInKingdom
	 * @return boolean
	 * 
	 */
	
	private static boolean verifyGridLimit(DominoInKingdom dInKingdom) {
		
		DirectionKind dKind = dInKingdom.getDirection();
		
		int largestXPos = dInKingdom.getX();
		int largestYPos = dInKingdom.getY();
		int smallestXPos = dInKingdom.getX();
		int smallestYPos = dInKingdom.getY();
		
		if(dKind.equals(DirectionKind.Right)) largestXPos += 1;
		if(dKind.equals(DirectionKind.Up)) largestYPos += 1;
		if(dKind.equals(DirectionKind.Left)) smallestXPos -= 1;
		if(dKind.equals(DirectionKind.Down)) smallestYPos -= 1;
		
		if(largestXPos <= 4 && smallestXPos >= -4 && largestYPos <= 4 && smallestYPos >= -4) return true;
		
		return false;
		
	}


	////////////////////////////////////////////////
	/*			Jay's Controller Methods          */
    ////////////////////////////////////////////////
	
	public static boolean verifyGridSizeAllKingdom(Player player) {
		
		int badCount=0;
		boolean respectGrid=true;
				
		List<KingdomTerritory> t = player.getKingdom().getTerritories();
		List<Integer> xCoords = new ArrayList<Integer>();
		List<Integer> yCoords = new ArrayList<Integer>();
		
		if (t.size()==1) {
			respectGrid=true;
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
		
		return respectGrid;
	}
	
	public static boolean verifyNoOverlapLastTerritory(Player player) {
		
		boolean noOverlap=true;
		
		List<KingdomTerritory> territories = player.getKingdom().getTerritories();
		
		if (territories.size()==1) {
			noOverlap=true;
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
	
			return noOverlap;
			}
			
	}
	
	public static boolean verifyCastleAdjacency(Player player) {
		
		boolean castleAdj=true;
		
		List <KingdomTerritory> t = player.getKingdom().getTerritories();
		
		if (t.size()==1) {
			castleAdj=true;
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
		
		return castleAdj;
		
	}
	
	public static boolean verifyNeighborAdjacencyLastTerritory(Player player) {
		
		boolean neighborAdj=true;
		
		List<KingdomTerritory> t =player.getKingdom().getTerritories();
		
		if (t.size()==1) {
			neighborAdj=true;
			return neighborAdj;
		}
		else {
			
			int validNeighborCount=0;
			
			DominoInKingdom prePlacedDomino = (DominoInKingdom) t.get(t.size()-1);
			
			Neighborhood leftNeighborhood = getDominoLeftNeighbors(t,prePlacedDomino);
			Neighborhood rightNeighborhood =getDominoRightNeighbors(t,prePlacedDomino);

			List<TerrainType> leftTileNeighborTerrains = leftNeighborhood.getNeighborTerrainType();

			List<TerrainType> rightTileNeighborTerrains = rightNeighborhood.getNeighborTerrainType();

			if (!leftTileNeighborTerrains.isEmpty()) {
				String leftTerrain=prePlacedDomino.getDomino().getLeftTile().name();
				for (TerrainType testTerrain:leftTileNeighborTerrains) {
					String testTerrainName=testTerrain.name();
					if (testTerrainName.equalsIgnoreCase(leftTerrain)){
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
				neighborAdj=false;
			}
			else {
				prePlacedDomino.getDomino().setStatus(DominoStatus.CorrectlyPreplaced);
				neighborAdj=true;
			}
	
			return neighborAdj;
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

}
