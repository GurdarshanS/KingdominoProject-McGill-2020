package ca.mcgill.ecse223.kingdomino.controller;

import java.util.List;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.model.Domino;
import ca.mcgill.ecse223.kingdomino.model.Domino.DominoStatus;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom;
import ca.mcgill.ecse223.kingdomino.model.KingdomTerritory;
import ca.mcgill.ecse223.kingdomino.model.Player;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom.DirectionKind;

public class DiscardTestDriver {
	
public static void main (String[] args) {
	
//	#################################################
//	#				Discard  Testing				#
//	#################################################
		
		boolean shouldDiscard=true;
		KDController.initiateEmptyGame();
		Player player=KingdominoApplication.getKingdomino().getCurrentGame().getNextPlayer();
		
		int testId=48;
		System.out.println("============================= Discard Test Domino "+testId+" ==================================");
		Domino dominoToPlace = KDController.getdominoByID(37);	//change id here according to table below
		
		//			dominos to test and status
	
		//		  | id | dstatus              |
		//	      | 48 | erroneouslyPreplaced |
		//	      | 45 | erroneouslyPreplaced |
		//	      | 37 | erroneouslyPreplaced |
		//	      | 33 | erroneouslyPreplaced |
		//	      |  8 | erroneouslyPreplaced |
		//	      | 41 | discarded            |
		//	      | 44 | discarded            |
		//	      | 27 | discarded            |
		//	      | 29 | discarded            |
		//	      |  2 | discarded            |


		int[] id= {7,20,22,23,24,30,31,32,38,40,43};
		String[] dir= {"left","up","up","right","left","up","right","down","right","left","right"};
		int[] x = {2,-1,3,0,2,3,-1,0,1,2,2};
		int[] y = {1,-1,-2,-3,-1,0,1,-1,-2,0,-3};
		
//						existing dominos
//	      | id | dominodir | posx | posy |
//	      |  7 | left      |    2 |    1 |
//	      | 20 | up        |   -1 |   -1 |
//	      | 22 | up        |    3 |   -2 |
//	      | 23 | right     |    0 |   -3 |
//	      | 24 | left      |    2 |   -1 |
//	      | 30 | up        |    3 |    0 |
//	      | 31 | right     |   -1 |    1 |
//	      | 32 | down      |    0 |   -1 |
//	      | 38 | right     |    1 |   -2 |
//	      | 40 | left      |    2 |    0 |
//	      | 43 | right     |    2 |   -3 |
		
		
		for (int i=0;i<id.length;i++) {
			Domino existingDominoToPlace = KDController.getdominoByID(id[i]); 
			KDController.prePlaceDomino(player, existingDominoToPlace, x[i], y[i], dir[i]);
		}
		
		boolean castleAvailable=KDQuery.CastleNeighborhoodAvailable(player);
		List<KingdomTerritory> t =player.getKingdom().getTerritories();
		System.out.println(t);



//		placing new test domino and start iterating through all possible positions and orientations
		KDController.prePlaceDomino(player, dominoToPlace, 0, 0, "left");
		KingdomTerritory territoryToChange=t.get(t.size()-1);
		
		int xPos=-4;
		DirectionKind[] possibleDirections= {DirectionKind.Up,DirectionKind.Down,DirectionKind.Left,DirectionKind.Right};
		
		String validity="valid";
		
		while (xPos<5) {
			int yPos=-4;
			
			while (yPos<5) {
				territoryToChange.setX(xPos);		//you can replace these two lines with your move method
				territoryToChange.setY(yPos);		//you can replace these two lines with your move method
				
				for (DirectionKind direction:possibleDirections) {
					((DominoInKingdom) territoryToChange).setDirection(direction);	//replace this with your rotate method
					//the line below should be taken care of your move and rotate methods, 
					//make sure to set DominoStatus to CorrectlyPreplaced after each move and/or rotation
					((DominoInKingdom) territoryToChange).getDomino().setStatus(DominoStatus.CorrectlyPreplaced);	

					if (castleAvailable) {			//only tests castle adjacency if castle surrounding is free
						KDController.verifyCastleAdjacency(player);
					}
					KDController.verifyGridSizeAllKingdom(player);
					KDController.verifyNoOverlapAllTerritories(player);
					KDController.verifyNeighborAdjacencyLastTerritory(player);
					validity=KDController.getKingdomVerificationResult(player);
//					
					int leftX=territoryToChange.getX();
					int leftY=territoryToChange.getY();
					int other[]=KDController.calculateOtherPos(territoryToChange);
					int rightX=other[0];
					int rightY=other[1];
							
					if (validity.equalsIgnoreCase("valid")){
						System.out.println("valid configuration --- left: ("+leftX+","+leftY+")"+" right: ("+rightX+","+rightY+") direction: "+direction.name()+" status: "+validity);
						shouldDiscard=false;
					}
				}
				yPos++;
			}
			xPos++;
		}
		
		if (shouldDiscard) {
			System.out.println("--> no possible valid moves available for this domino in the current board, discard");
		}
		else {
			System.out.println("--> exist possible valid move(s) available for this domino in the current board, DO NOT discard");
		}
	}
}
		
