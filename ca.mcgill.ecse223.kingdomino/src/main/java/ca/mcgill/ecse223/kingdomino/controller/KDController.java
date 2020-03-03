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

	public static void discardDomino(Player aPlayer) throws java.lang.IllegalArgumentException{
		
		DominoInKingdom newlyPrePlacedDomino = (DominoInKingdom) aPlayer.getKingdom().getTerritory(aPlayer.getKingdom().getTerritories().size()-1);
	
		boolean castleAvailable= KDQuery.CastleNeighborhoodAvailable(aPlayer);
		String validity;
		boolean shouldDiscard = true;
		
		
		for(int i = -4; i<5; i++) {
			
			for(int j = -4; j<5; j++) {
				
				for(int z = 0; z<4; z++) {
					
					newlyPrePlacedDomino.setX(i);
					newlyPrePlacedDomino.setY(j);
					
					rotateCurrentDomino(aPlayer, newlyPrePlacedDomino, "Clockwise");
					
					newlyPrePlacedDomino.getDomino().setStatus(DominoStatus.CorrectlyPreplaced);
					
					if (castleAvailable) {			

						KDController.verifyCastleAdjacency(aPlayer);
						KDController.verifyGridSizeAllKingdom(aPlayer);

					}

					KDController.verifyGridSizeAllKingdom(aPlayer);
					KDController.verifyNoOverlapAllTerritories(aPlayer);
					KDController.verifyNeighborAdjacencyLastTerritory(aPlayer);

					validity = KDController.getKingdomVerificationResult(aPlayer);


					if (validity.equalsIgnoreCase("valid")){


						shouldDiscard = false;

					}
//					
//					if(verifyNeighbourAdjacency(aPlayer) && verifyNoOverLap(aPlayer) && verifyGridSizee(aPlayer)) {
//						
//						newlyPrePlacedDomino.getDomino().setStatus(DominoStatus.ErroneouslyPreplaced);
//						return;
//						
//					}
//					
//					else if(verifyCastleAdjacencyy(aPlayer) && verifyNoOverLap(aPlayer) && verifyGridSizee(aPlayer)) {
//						
//						newlyPrePlacedDomino.getDomino().setStatus(DominoStatus.ErroneouslyPreplaced);
//						return;
//						
//					}
//					
				}
				
			}
			
		}
		
		if (shouldDiscard) {

			newlyPrePlacedDomino.getDomino().setStatus(DominoStatus.ErroneouslyPreplaced);
			
		}

		else {

			newlyPrePlacedDomino.getDomino().setStatus(DominoStatus.Discarded);
			newlyPrePlacedDomino.delete();
			aPlayer.getDominoSelection().delete();

		}
		
//		newlyPrePlacedDomino.getDomino().setStatus(DominoStatus.Discarded);
//		newlyPrePlacedDomino.delete();
//		aPlayer.getDominoSelection().delete();

	}
	
	public static void rotateCurrentDomino(Player aPlayer, DominoInKingdom dInKingdom, String rotation) throws java.lang.IllegalArgumentException {
		
		if(dInKingdom.getDomino().getId() == 10) {
		
		Kingdom kingdom = aPlayer.getKingdom();
		
		for(int i = 1; i<kingdom.getTerritories().size(); i++) {
			
			DominoInKingdom dInK = (DominoInKingdom) kingdom.getTerritory(i);
			
			System.out.println(kingdom.getTerritory(i).getX() + "      " + kingdom.getTerritory(i).getY() + "    " + dInK.getDirection() + "    " + dInK.getDomino().getId() + "   " + dInK.getDomino().getLeftTile() + "     " + dInK.getDomino().getRightTile());
			
		}
		
		
		
		DirectionKind dominoDir = dInKingdom.getDirection();
		
		if(dominoDir.equals(DirectionKind.Up) && rotation.equalsIgnoreCase("Clockwise")) dInKingdom.setDirection(DirectionKind.Right);
		else if(dominoDir.equals(DirectionKind.Right) && rotation.equalsIgnoreCase("Clockwise")) dInKingdom.setDirection(DirectionKind.Down);
		else if(dominoDir.equals(DirectionKind.Down) && rotation.equalsIgnoreCase("Clockwise")) dInKingdom.setDirection(DirectionKind.Left);
		else if(dominoDir.equals(DirectionKind.Left) && rotation.equalsIgnoreCase("Clockwise")) dInKingdom.setDirection(DirectionKind.Up);
		
		if(dominoDir.equals(DirectionKind.Up) && rotation.equalsIgnoreCase("CounterClockwise")) dInKingdom.setDirection(DirectionKind.Left);
		else if(dominoDir.equals(DirectionKind.Left) && rotation.equalsIgnoreCase("CounterClockwise")) dInKingdom.setDirection(DirectionKind.Down);
		else if(dominoDir.equals(DirectionKind.Down) && rotation.equalsIgnoreCase("CounterClockwise")) dInKingdom.setDirection(DirectionKind.Right);
		else if(dominoDir.equals(DirectionKind.Right) && rotation.equalsIgnoreCase("CounterClockwise")) dInKingdom.setDirection(DirectionKind.Up);
		
		if(verifyNeighbourAdjacency(aPlayer) && verifyNoOverLap(aPlayer) && verifyGridSizee(aPlayer)) {
			
			System.out.println("Well Placed!" + "New Direction: " + dInKingdom.getDirection());
			dInKingdom.getDomino().setStatus(DominoStatus.CorrectlyPreplaced);
			return;
			
		}
		
		else if(verifyCastleAdjacencyy(aPlayer) && verifyNoOverLap(aPlayer) && verifyGridSizee(aPlayer)) {
			System.out.println("Well Placed!" + "New Direction: " + dInKingdom.getDirection());
			dInKingdom.getDomino().setStatus(DominoStatus.CorrectlyPreplaced);
			return;
			
		}
		System.out.println("Badly Placed!" + "New Direction: " + dInKingdom.getDirection());
		dInKingdom.getDomino().setStatus(DominoStatus.ErroneouslyPreplaced);
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	////////////////////////////////////////////////
	/*			My Controller Methods          */
    ////////////////////////////////////////////////
	
	
	public static boolean verifyNoOverLap(Player player) {
		
		List<KingdomTerritory> territories = player.getKingdom().getTerritories();
		
		KingdomTerritory newlyAddedTerritory = player.getKingdom().getTerritory(player.getKingdom().getTerritories().size()-1);
		
		if(territories.size() == 1) return true;
		
		else {
			
			for (int i = 0; i< territories.size(); i++) {

				if(!territories.get(i).equals(newlyAddedTerritory)) {
					
					if(checkOverlap(newlyAddedTerritory, territories.get(i))) {
						
						return false;
						
					}
					
				}

			}
			
			return true;
			
		}
		
	}
	
	public static boolean verifyGridSizee(Player player) {
		
		List<KingdomTerritory> territories = player.getKingdom().getTerritories();
		
		KingdomTerritory newlyAddedTerritory = player.getKingdom().getTerritory(player.getKingdom().getTerritories().size()-1);
		
		int largestXCoord = territories.get(0).getX();
		int largestYCoord = territories.get(0).getY();
		
		int smallestXCoord = territories.get(0).getX();
		int smallestYCoord = territories.get(0).getY();

		for (KingdomTerritory territory : territories) {
			
			if(territories.get(0).equals(territory)) continue;
			
			DominoInKingdom dInKingdom = (DominoInKingdom) territory;
			
			if(dInKingdom.getDirection().equals(DirectionKind.Up)) {
				
				if(territory.getY() + 1 > largestYCoord) 
					largestYCoord = territory.getY() + 1;
				
				if(territory.getY() < smallestYCoord) smallestYCoord = territory.getY();
				if(territory.getX() > largestXCoord) largestXCoord = territory.getX();
				if(territory.getX() < smallestXCoord) smallestXCoord = territory.getX();
				
			}
		
			if(dInKingdom.getDirection().equals(DirectionKind.Down)) {
				
				if(territory.getY() - 1 < smallestYCoord)
					smallestYCoord = territory.getY() - 1;
				
				if(territory.getY() > largestYCoord) largestYCoord = territory.getY();
				if(territory.getX() > largestXCoord) largestXCoord = territory.getX();
				if(territory.getX() < smallestXCoord) smallestXCoord = territory.getX();
				
			}
			
			if(dInKingdom.getDirection().equals(DirectionKind.Right)) {
				
				if(territory.getX() + 1 > largestXCoord)
					largestXCoord = territory.getX() + 1;
				
				if(territory.getX() < smallestXCoord) smallestXCoord = territory.getX();
				if(territory.getY() > largestYCoord) largestYCoord = territory.getY();
				if(territory.getY() < smallestYCoord) smallestYCoord = territory.getY();
				
			}
			
			if(dInKingdom.getDirection().equals(DirectionKind.Left)) {
				
				if(territory.getX() - 1 < smallestXCoord)
					smallestXCoord = territory.getX() - 1;
				
				if(territory.getX() > largestXCoord) largestXCoord = territory.getX();
				if(territory.getY() > largestYCoord) largestYCoord = territory.getY();
				if(territory.getY() < smallestYCoord) smallestYCoord = territory.getY();
				
			}

		}
		
		if(largestXCoord - smallestXCoord >= 5) {
			
			return false;
		}
		
		if(largestYCoord - smallestYCoord >= 5) {
			
			return false;
		}
	
		return true;
		
	}
	
	public static boolean verifyCastleAdjacencyy(Player player) {

		List <KingdomTerritory> territories = player.getKingdom().getTerritories();
		KingdomTerritory newlyAddedTerritory = player.getKingdom().getTerritory(player.getKingdom().getTerritories().size() - 1);

		if(territories.size() == 1) return true;
		
		int castleX = territories.get(0).getX();
		int castleY = territories.get(0).getY();
		
		DominoInKingdom dInKingdom = (DominoInKingdom) newlyAddedTerritory;
	
		if(dInKingdom.getDirection().equals(DirectionKind.Up)) {
			
			if(dInKingdom.getX() == castleX + 1 || dInKingdom.getX() == castleX - 1) {
				
				if(dInKingdom.getY() == castleY || dInKingdom.getY() + 1 == castleY) {
				
					return true;
				
				}
				
			}
			
			if(dInKingdom.getY() == castleY +1 || dInKingdom.getY() + 1 == castleY - 1) {
				
				if(dInKingdom.getX() == castleX) {
					
					return true;
				
				}
			}
			
		
		}
			
		if(dInKingdom.getDirection().equals(DirectionKind.Down)) {
			
			if(dInKingdom.getX() == castleX + 1 || dInKingdom.getX() == castleX - 1) {
				
				if(dInKingdom.getY() == castleY || dInKingdom.getY() - 1 == castleY) {
	
					return true;
				
				}
				
			}
			
			if(dInKingdom.getY() - 1 == castleY + 1 || dInKingdom.getY() == castleY - 1) {
				
				if(dInKingdom.getX() == castleX) {
	
					return true;
				
				}
			}
			
		}
		
		if(dInKingdom.getDirection().equals(DirectionKind.Right)) {
			
			if(dInKingdom.getX() == castleX + 1 || dInKingdom.getX() + 1 == castleX - 1) {
				
				if(dInKingdom.getY() == castleY) {
					
					return true;
				
				}
			}
			
			if(dInKingdom.getY() == castleY +1 || dInKingdom.getY() == castleY - 1) {
				
				if(dInKingdom.getX() == castleX || dInKingdom.getX() + 1 == castleX) {
					
					return true;
				
				}
			}
			
		}
		
		if(dInKingdom.getDirection().equals(DirectionKind.Left)) {
			
			if(dInKingdom.getX() - 1 == castleX + 1 || dInKingdom.getX() == castleX - 1) {
				
				if(dInKingdom.getY() == castleY) {

					return true;
				
				}
			}
			
			if(dInKingdom.getY() == castleY +1 || dInKingdom.getY() == castleY - 1) {
				
				if(dInKingdom.getX() == castleX || dInKingdom.getX() - 1 == castleX) {
			
					return true;
				
				}
			}
			
		}

		return false;
		
	}
	
	public static boolean verifyNeighbourAdjacency(Player player) {
		
		List <KingdomTerritory> territories = player.getKingdom().getTerritories();
		KingdomTerritory newlyAddedTerritory = player.getKingdom().getTerritory(player.getKingdom().getTerritories().size() - 1);
		
		for(KingdomTerritory territory : territories) {
			
			if(territories.get(0).equals(territory)) continue;
			if(territories.get(player.getKingdom().getTerritories().size()-1).equals(territory)) break;
			
			DominoInKingdom dInKingdomTerritory = (DominoInKingdom) territory;
			DominoInKingdom dInKingdomMine = (DominoInKingdom) newlyAddedTerritory;
			
			int tileAPosX = dInKingdomTerritory.getX();
			int tileAPosY = dInKingdomTerritory.getY();
			TerrainType tileAType = dInKingdomTerritory.getDomino().getLeftTile();
			
			int tileBPosX = 0;
			int tileBPosY = 0;
			TerrainType tileBType = dInKingdomTerritory.getDomino().getRightTile();
			
			int myTileAPosX = dInKingdomMine.getX();
			int myTileAPosY = dInKingdomMine.getY();
			TerrainType myTileAType = dInKingdomMine.getDomino().getLeftTile();
			
			int myTileBPosX = 0;
			int myTileBPosY = 0;
			TerrainType myTileBType = dInKingdomMine.getDomino().getRightTile();
			
			if(dInKingdomTerritory.getDirection().equals(DirectionKind.Up)){
				
				tileBPosX = tileAPosX;
				tileBPosY = tileAPosY + 1;
				
			}
			
			if(dInKingdomMine.getDirection().equals(DirectionKind.Up)) {
				
				myTileBPosX = myTileAPosX;
				myTileBPosY = myTileAPosY + 1;
				
			}
			
			if(dInKingdomTerritory.getDirection().equals(DirectionKind.Down)) {
				
				tileBPosX = tileAPosX;
				tileBPosY = tileAPosY - 1;
			
			}
			
			if(dInKingdomMine.getDirection().equals(DirectionKind.Down)) {
				
				myTileBPosX = myTileAPosX;
				myTileBPosY = myTileAPosY - 1;
				
			}
			
			if(dInKingdomTerritory.getDirection().equals(DirectionKind.Right)) {
	
				tileBPosX = tileAPosX + 1;
				tileBPosY = tileAPosY;
			
			}
			
			if(dInKingdomMine.getDirection().equals(DirectionKind.Right)) {
				
				myTileBPosX = myTileAPosX + 1;
				myTileBPosY = myTileAPosY;
				
			}
			
			
			if(dInKingdomTerritory.getDirection().equals(DirectionKind.Left)) {
	
				tileBPosX = tileAPosX - 1;
				tileBPosY = tileAPosY;
	
			}
			
			if(dInKingdomMine.getDirection().equals(DirectionKind.Left)) {
				
				myTileBPosX = myTileAPosX - 1;
				myTileBPosY = myTileAPosY;
				
			}
			
		
			
			// TILE A AND TILE A
		
			//left and right of tile A and tile A
			if(myTileAPosX -1 == tileAPosX || myTileAPosX + 1 == tileAPosX) 
				if(myTileAPosY == tileAPosY && tileAType.equals(myTileAType)) {
					
					return true;
		
				}
			
			//up and down of tile A and tile A
			if(myTileAPosY -1 == tileAPosY || myTileAPosY + 1 == tileAPosY) 
				if(myTileAPosX == tileAPosX && tileAType.equals(myTileAType)) {
				
					return true;
			
				}
			
			// TILE B AND TILE B
			
			//left and right of tile B and tile B
			if(myTileBPosX -1 == tileBPosX || myTileBPosX + 1 == tileBPosX) 
				if(myTileBPosY == tileBPosY && tileBType.equals(myTileBType)) {
				
					return true;
				}
			
			
			//up and down of tile B and tile B
			if(myTileBPosY -1 == tileBPosY || myTileBPosY + 1 == tileBPosY)
				if(myTileBPosX == tileBPosX && tileBType.equals(myTileBType)) {
					
					return true;
				}
			
			
			
			// TILE A AND TILE B
			
			//left and right of tile A & B
			if(myTileAPosX -1 == tileBPosX || myTileAPosX + 1 == tileBPosX)
				if(myTileAPosY == tileBPosY && tileBType.equals(myTileAType)) {
					
					return true;
				}
			
			
			//up and down of tile A & B
			if(myTileAPosY -1 == tileBPosY || myTileAPosY + 1 == tileBPosY) 
				if(myTileAPosX == tileBPosX && tileBType.equals(myTileAType)) {
					
					return true;
				}
				
				
				
				
			
			// TILE B AND TILE A
			
			//left and right of tile B & A
			if(myTileBPosX -1 == tileAPosX || myTileBPosX + 1 == tileAPosX) 
				if(myTileBPosY == tileAPosY && tileAType.equals(myTileBType)) {
			
					return true;
				}
				
				
			//up and down of tile B & A
			if(myTileBPosY -1 == tileAPosY || myTileBPosY + 1 == tileAPosY) 
				if(myTileBPosX == tileAPosX && tileAType.equals(myTileBType)) {
				
					return true;
				}
			
		}
	
		return false;
		
		
	}
	
	
	
	
	
	
	
	
	
	////////////////////////////////////////////////
	/*			Jay's Controller Methods          */
    ////////////////////////////////////////////////
	
	
	
	
	
	public static String getKingdomVerificationResult(Player player) {
		String validity="valid";
		
		List<DominoInKingdom> errorneouslyPlacedDominos=KDQuery.getErroneouslyPrePlacedDomino(player);
		if (!errorneouslyPlacedDominos.isEmpty()) {
			validity="invalid";
		}
		
		return validity;
	}
	
	public static boolean verifyGridSizeNewDominoInKingdom(Player player, DominoInKingdom dInK) {
		boolean valid=true;
		
		List <KingdomTerritory> t = player.getKingdom().getTerritories();
		List<Integer> xCoords = new ArrayList<Integer>();
		List<Integer> yCoords = new ArrayList<Integer>();
		
		for (KingdomTerritory each:t) {
			int [] otherPos=calculateOtherPos(each);
					
			int x1=each.getX();
			int y1=each.getY();
			int x2=otherPos[0];
			int y2=otherPos[1];
			
			xCoords.add(x1);
			xCoords.add(x1);
			yCoords.add(y1);
			yCoords.add(y2);
			
		}
		
		Collections.sort(xCoords);
		Collections.sort(yCoords);
		
		int xMin=xCoords.get(0);
		int xMax=xCoords.get(xCoords.size()-1);
		
		int yMin=yCoords.get(0);
		int yMax=yCoords.get(yCoords.size()-1);
		
		int xSize=xMax-xMin;
		int ySize=yMax-yMin;
		
		if (xSize>5 || ySize>5) {
			throw new IllegalArgumentException("Kingdom already have invalid sizse");
		}
		
		int [] otherTestPos=calculateOtherPos(dInK);

		int testX1=dInK.getX();
		int testY1=dInK.getY();
		int testX2=otherTestPos[0];
		int testY2=otherTestPos[1];
		
		if (testX1<xMin || testX2<xMin) {
			valid=false;
			dInK.getDomino().setStatus(DominoStatus.ErroneouslyPreplaced);
		}
		
		else if (testX1>xMax || testX2>xMax) {
			valid=false;
			dInK.getDomino().setStatus(DominoStatus.ErroneouslyPreplaced);
		}
		
		else if (testY1<yMin || testY2<yMin) {
			valid=false;
			dInK.getDomino().setStatus(DominoStatus.ErroneouslyPreplaced);
		}
		
		else if (testY1>yMax || testY2>yMax) {
			valid=false;
			dInK.getDomino().setStatus(DominoStatus.ErroneouslyPreplaced);
		}
		
		return valid;
	}
	
	public static void verifyGridSizeAllKingdom(Player player) {
		
		boolean valid=true;
		
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
					valid=false;
				}
			}
			
		}
//		if (valid==false) {
//			System.out.println("failed verifyGridSizeAllKingdom");
//		}
//		else {
//			System.out.println("passed verifyGrideSizeAllKingdom");
//		}
	}
	
	public static void verifyNoOverlapAllTerritories(Player player) {
		
//		verifies all territories in kingdom
		
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
	
	public static void verifyNoOverlapLastTerritory(Player player) {
		
//		only verifies the last preplaced domino
		
		List<KingdomTerritory> territories = player.getKingdom().getTerritories();
		
		if (territories.size()==1) {
			return;
		}
		
		else {
			
			KingdomTerritory tA;
			KingdomTerritory tB;
	
			tA=territories.get(territories.size()-1);
				
			for (int j=territories.size()-2;j>-1;j--) {
					
					tB=territories.get(j);

					if (checkOverlap(tA,tB)){
						((DominoInKingdom) tA).getDomino().setStatus(DominoStatus.ErroneouslyPreplaced);
						break;
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

		
		if ((norm1!=1)&&(norm2!=1)){
			if((testX1==0 && testY1==0)||(testX2==0 && testY2==0)) {
				((DominoInKingdom) testD).getDomino().setStatus(DominoStatus.ErroneouslyPreplaced);
			}
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
