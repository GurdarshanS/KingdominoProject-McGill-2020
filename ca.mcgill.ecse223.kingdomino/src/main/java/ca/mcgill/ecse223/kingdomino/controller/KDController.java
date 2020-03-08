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
	
	/**
	 * 
	 * This method checks initiates an empty game.
	 * Useful for bring games up to a testable state
	 * 
	 * @see - no features associated
	 * @author Jing Han 260528152
	 * @param void
	 * @return void 
	 */

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
	
	/**
	 * 
	 * This method preplaces a domino into a playe's kingdom
	 * Useful for bring games up to a testable state in cucumber files
	 * 
	 * @see - no features associated
	 * @author Jing Han 260528152
	 * @param player
	 * @param dominoToPlace
	 * @param posx
	 * @param posy
	 * @param dir
	 * @return dInK
	 */

	public static DominoInKingdom prePlaceDomino(Player player, Domino dominoToPlace, int posx, int posy, String dir) {
		dominoToPlace.setStatus(DominoStatus.CorrectlyPreplaced);
		Kingdom kingdom=player.getKingdom();
		DominoInKingdom dInK = new DominoInKingdom(posx,posy,kingdom,dominoToPlace);
		dInK.setDirection(getDirection(dir));
		return dInK;
	}
	
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
	
	/**
	 * 
	 * This method checks a player's kingdom to make sure that
	 * all kingdom territories stay within a 5x5 grid (7x7 if 
	 * Mighty Kingdom) mode is enabled
	 * 
	 * @see VerifyGridSize.feature
	 * @author Jing Han 260528152
	 * @param player
	 * @return respectGrid
	 */
	
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
	
	
	
	/**
	 * 
	 * This method checks a player's kingdom to make sure that
	 * none of the kingdom territories overla each other
	 * 
	 * @see VerifyNoOverlapping.feature
	 * @author Jing Han 260528152
	 * @param player
	 * @return void
	 */
	
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
	
	/**
	 * 
	 * This method checks a player's kingdom to make sure that
	 * the last placed kingdom territory does not overlap any
	 * existing dominos
	 *  
	 * @see VerifyNoOverlapping.feature
	 * @author Jing Han 260528152
	 * @param player
	 * @return void
	 */
	
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
	
	/**
	 * 
	 * This method checks a player's kingdom to determine whether
	 * the last placed domino is adjacent to the kingdom castle
	 * 
	 * @see VerifyCastleAdjacency.fature
	 * @author Jing Han 260528152
	 * @param player
	 * @return void
	 */
	
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
	
	/**
	 * 
	 * This method checks a player's kingdom to determine whether
	 * the last placed domino is has at least 1 valid neighbor
	 * 
	 * @see VerifyNeighborAdjacency.fature
	 * @author Jing Han 260528152
	 * @param player
	 * @return void
	 */
	
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
	
	/**
	 * 
	 * This method returns the ranked players
	 * of the game according to their total
	 * scores
	 * 
	 * @see CalculateRanking.feature
	 * @author Gurdarshan Singh 260927466, refactored by Jing Han
	 * @param void
	 * @return rankedPlayers
	 */
	
	
	public static Player[] getRankedPlayers() {
		
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		
		List<Player> allPlayers = game.getPlayers();
		Player[] rankedPlayers = new Player[allPlayers.size()];
		
		for (Player player:allPlayers) {
			rankedPlayers[player.getCurrentRanking()-1]=player;
		}
		
		return rankedPlayers;
	
	}
	
	/**
	 * 
	 * This method determines whether a tiebreak exists
	 * between player scores. Returns true of exist, false
	 * if doesn't exist
	 * 
	 * @see CalculateRanking.feature
	 * @author Gurdarshan Singh 260927466, refactored by Jing Han
	 * @param void
	 * @return boolean
	 */
	
	
	public static boolean existTieBreak() {
		
		boolean exist=false;
		
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		
		List<Player> allPlayers = game.getPlayers();
		List<Integer> playerScores = new ArrayList<Integer>();

		for (Player each:allPlayers) {
			playerScores.add(each.getTotalScore());
		}
		
		Set<Integer> uniqueScores = new HashSet<Integer>(playerScores);

		if (uniqueScores.size()==playerScores.size()) {
			exist=false;
		}
		
		return exist;
	}
	
	/**
	 * 
	 * This method ranks the currrent players
	 * of the game according to their total
	 * scores
	 * 
	 * @see CalculateRanking.feature
	 * @author Gurdarshan Singh 260927466, refactored by Jing Han
	 * @param void
	 * @return void
	 */
	
	
	public static void calculatePlayerRanking() {
		

		
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		
		List<Player> allPlayers = game.getPlayers();
		List<Integer> playerScores = new ArrayList<Integer>();
		List<Integer> scoreCopy = new ArrayList<Integer>();
		List<Integer> ignoreIndex = new ArrayList<Integer>();
		
		System.out.println(" =========== unranked ============");
		for (Player p:allPlayers) {
			System.out.println(p.getColor()+" ---- score: "+p.getTotalScore()+" ---- rank: "+p.getCurrentRanking());
		}
		
		for (Player each:allPlayers) {
			playerScores.add(each.getTotalScore());
			scoreCopy.add(each.getTotalScore());
		}
		
		Collections.sort(scoreCopy);
		Collections.reverse(scoreCopy);
				
		System.out.println(" ============ ranked =============");
		
		for (int i=0;i<scoreCopy.size();i++) {
			int refVal=scoreCopy.get(i);
			for (int j=0;j<playerScores.size();j++) {
				if (!ignoreIndex.contains(j)) {
					int testVal=playerScores.get(j);
					if (testVal==refVal) {
						ignoreIndex.add(j);
						allPlayers.get(j).setCurrentRanking(i+1);					
					}
				}
			}
		}
		
		for (Player p:allPlayers) {
			System.out.println(p.getColor()+" ---- score: "+p.getTotalScore()+" ---- rank: "+p.getCurrentRanking());
		}
		
	}
	
	
	
	
	
	
	/**
	 * 
	 * This method identifies all the properties of a player's kingdom
	 * and creates the corresponding Property objects
	 * 
	 * @see IdentifyProperties.feature
	 * @author Jing Han 260528152
	 * @param player
	 * @return void
	 */
	
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
	
	/**
	 * 
	 * This method retrieves all the properties of a player's kingdom
	 * 
	 * @see  - no features explicitly
	 * @author Jing Han 260528152
	 * @param player
	 * @return List<Property>
	 */
	
	public static List<Property> getAllProperty(Player player){
		return player.getKingdom().getProperties();
	}
	
	/**
	 * 
	 * This method retrieves all Properties of a particular 
	 * TerrainType of a player's kingdom
	 * 
	 * @see  - CalculatePropertyAttributes.feature
	 * @author Jing Han 260528152
	 * @param player
	 * @param t
	 * @return filteredProp
	 */
	
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
	
	/**
	 * 
	 * This method checks to see if a Property matchs a given 
	 * TerrainType and a list of given Domino IDs
	 * 
	 * @see  - CalculatePropertyAttributes.feature
	 * @author Jing Han 260528152
	 * @param player
	 * @param testTerrain
	 * @param testIds
	 * @param property
	 * @return match
	 */
	
	public static boolean checkPropertyMatch(TerrainType testTerrain, int[] testIds, Property property) {
		
		boolean match=true;
		
		if (!(testTerrain.equals(property.getPropertyType()))) {
			match=false;
		}
		else {
			List<Domino> dInP=property.getIncludedDominos();
			List<Integer> ids = new ArrayList<Integer>();
			for (Domino d: dInP) {
				ids.add(d.getId());
			}
			
			if (ids.size()!=testIds.length) {
				match=false;
			}
			else {
				for (int i:testIds) {
					if (!(ids.contains(i))) {
						match=false;
						break;
					}
				}
			}
		}
		return match;
	}
	
	/**
	 * 
	 * This method returns a list of PropertyAttribute
	 * objects that captures the TerrainType,size,crown
	 * and score of each Property object of the Player's 
	 * Kingdom
	 * @see  - CalculatePropertyAttributes.feature
	 * @author Jing Han 260528152
	 * @param player
	 * @return allAttributes
	 */
	
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
	
	/**
	 * 
	 * This method the score of a Player's kingdom
	 * @see  - CalculatePlayerScore.feature
	 * @author Jing Han 260528152
	 * @param player
	 * @return void
	 */
	
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
	
	/**
	 * 
	 * This method sets the bonus score of a player
	 * according to the kingdom layout and the 
	 * bonus options set in KD
	 * @see  - CalculatePlayerScore.feature
	 * @author Jing Han 260528152
	 * @param player
	 * @return void
	 */
	
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
	
	/**
	 * 
	 * This method checks whether a player's kingdom
	 * is elgigible for the Middle Kingdom Bonus
	 * ie the castle is positioned at the center
	 * @see  - CalculatePlayerScore.feature
	 * @author Jing Han 260528152
	 * @param player
	 * @return boolean
	 */
	
	public static boolean isMiddleKingdom(Player player) {
		int [][] coords = KDQuery.getPlayerTerritoryCoordinates(player);
		
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
	
	/**
	 * 
	 * This method checks whether a player's kingdom
	 * is elgigible for the Harmony Bonus
	 * ie the kingdom is a 5x5 grid
	 * @see  - CalculatePlayerScore.feature
	 * @author Jing Han 260528152
	 * @param player
	 * @return boolean
	 */
	
	
	public static boolean isHarmony(Player player) {
		
		int [][] coords = KDQuery.getPlayerTerritoryCoordinates(player);
		
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
	
	/**
	 * 
	 * This method creates the current and next
	 * draft of the game. takes care of the beginning case
	 * when there is no current or next drafts, takes care
	 * of regular play by swapping current with next and 
	 * then generate a new next, and takes care of the
	 * end game when there is no more domino for  next 
	 * and it is set to null
	 * 
	 * @see  - CreateNextDraft.feature
	 * @author Keon Olsz, refactored by Jing Han
	 * @param player
	 * @return boolean
	 */
	
	public static void createNextDraft() {
		Kingdomino kd = KingdominoApplication.getKingdomino();
		Game game = kd.getCurrentGame();
		int draftNumLimit=0;
		
		if ((game.getNumberOfPlayers()==4)||(game.getNumberOfPlayers()==3)) draftNumLimit=12;
		if (game.getNumberOfPlayers()==2) draftNumLimit=6;
		
//		System.out.println("draft number limit: "+draftNumLimit);
		if (game.getAllDrafts().size()==0) {
			Draft currentDraft=createOneDraft();
			currentDraft.setDraftStatus(Draft.DraftStatus.FaceUp);
			game.setCurrentDraft(currentDraft);
			changeDraftDominoStatus(currentDraft,Domino.DominoStatus.InCurrentDraft);

			Draft nextDraft = createOneDraft();
			nextDraft.setDraftStatus(Draft.DraftStatus.FaceDown);
			game.setNextDraft(nextDraft);
			changeDraftDominoStatus(nextDraft,Domino.DominoStatus.InNextDraft);
		}
		else if (game.getAllDrafts().size()<draftNumLimit) {
			changeDraftDominoStatus(game.getNextDraft(),DominoStatus.InCurrentDraft);
			game.setCurrentDraft(game.getNextDraft());
			game.getCurrentDraft().setDraftStatus(Draft.DraftStatus.FaceUp);
			
			Draft nextDraft=createOneDraft();
			changeDraftDominoStatus(nextDraft,DominoStatus.InNextDraft);
			game.setNextDraft(nextDraft);
			game.getNextDraft().setDraftStatus(Draft.DraftStatus.FaceDown);
		}
		else {
//			changeDraftDominoStatus(game.getNextDraft(),DominoStatus.InCurrentDraft);
			game.setCurrentDraft(game.getNextDraft());
			game.getCurrentDraft().setDraftStatus(Draft.DraftStatus.FaceUp);
			
			game.setNextDraft(null);
		}
	
	}
	
	/**
	 * 
	 * This method changes the status of dominos in a game
	 * 
	 * @see  - CreateNextDraft.feature
	 * @author Keon Olsz, refactored by Jing Han
	 * @param player
	 * @return boolean
	 */
	
	public static void changeDraftDominoStatus(Draft draft, Domino.DominoStatus status) {
		
		if(!((status.equals(Domino.DominoStatus.InCurrentDraft))||(status.equals(Domino.DominoStatus.InNextDraft)))) {
			throw new IllegalArgumentException("status must be either InCurrentDraft or InNextDraft");
		}
		
		List<Domino> draftDominos = draft.getIdSortedDominos();
		for (Domino each:draftDominos) {
			each.setStatus(status);
		}
	}
	
	/**
	 * 
	 * This method one draft based on the number
	 * of players in the game
	 * 
	 * @see  - CreateNextDraft.feature
	 * @author Keon Olsz, refactored by Jing Han
	 * @param player
	 * @return boolean
	 */
	
	public static Draft createOneDraft() {
		Kingdomino kd = KingdominoApplication.getKingdomino();
		Game game = kd.getCurrentGame();
		
		int numPlayer=kd.getCurrentGame().getNumberOfPlayers();
//		System.out.println("number of players: "+numPlayer);
		int dominoNum=0;
		
		if ((numPlayer==2)||(numPlayer==4)) dominoNum=4;
		if (numPlayer==3) dominoNum=3;
		
		Draft draft = new Draft(Draft.DraftStatus.FaceDown,game);
		
		for (int i=0;i<dominoNum;i++) {
			Domino dominoToAdd=game.getTopDominoInPile();
			game.setTopDominoInPile(game.getTopDominoInPile().getNextDomino());
			draft.addIdSortedDomino(dominoToAdd);
		}
		
		return draft;
	}
	
	public static List<Domino> getAvailableDominoPile(){
		Kingdomino kd = KingdominoApplication.getKingdomino();
		Game game = kd.getCurrentGame();
		List<Domino> availableDomino=new ArrayList<Domino>();
		List<Domino> allDomino=game.getAllDominos();
		
		for (Domino test:allDomino) {
			if (test.getStatus().equals(Domino.DominoStatus.InPile)) {
				availableDomino.add(test);
			}
		}
		
		return availableDomino;
		
	}
	
	

	
	
	///////////////////////////////////////
	/// -----Private Helper Methods---- ///
	///////////////////////////////////////
	
	/**
	 * 
	 * This method checks which player has the largest property.
	 * 
	 * @see ResolveTiebreaker.feature
	 * @author Gurdarshan Singh 260927466
	 * @param List<Property>
	 * @return Property
	 */
	
	private static Property getBiggestProperty(List<Property> properties) {
		
		int biggest = properties.get(0).getSize();
		Property biggestProperty = properties.get(0);
		
		for(int i=1; i<properties.size(); i++) {
			
			if(properties.get(i).getSize() > biggest) {
				
				biggestProperty = properties.get(i);
				biggest = properties.get(i).getSize();
			}
		}
		
		return biggestProperty;
	}
	
	
	/**
	 * 
	 * This method takes in an array of all the players and adjusts the ranking to have a tiebreak.
	 * First it checks who has the biggest property, whoever does get's first place.
	 * If the property size is the same, the amount of crowns on those properties is compared.
	 * The greatest amount of crowns wins it. In the end, an updated array is returned depending on
	 * the new standings.
	 * 
	 * @see ResolveTiebreak.feature
	 * @author Gurdarshan Singh 260927466
	 * @param Player[]
	 * @return Player[]
	 */
	
	public static Player[] tieBreaker(Player[] p1) {
		int crowns1 = 0;
		int crowns2 = 0;
		if( p1[2].getTotalScore() == p1[3].getTotalScore()) {
			
			List<Property> propertiesPlayer1 = p1[2].getKingdom().getProperties();
			List<Property> propertiesPlayer2 = p1[3].getKingdom().getProperties();

			
			Property biggestPropertyPlayer1 = getBiggestProperty(propertiesPlayer1);
			Property biggestPropertyPlayer2 = getBiggestProperty(propertiesPlayer2);
			
			if(biggestPropertyPlayer1.getSize() > biggestPropertyPlayer2.getSize()) {
				p1[2].setPropertyScore(p1[2].getPropertyScore()+1);
				Player temp = p1[3];
				p1[3] = p1[2];
				p1[2] = temp;
				return p1;
			} else if (biggestPropertyPlayer1.getSize() < biggestPropertyPlayer2.getSize()) {
				p1[3].setPropertyScore(p1[3].getPropertyScore()+1);
				return p1;
			} else if (biggestPropertyPlayer1.getSize() == biggestPropertyPlayer2.getSize()) {
			
			for(int i=0; i<propertiesPlayer1.size(); i++) {
				crowns1 = crowns1 + propertiesPlayer1.get(i).getCrowns();
			}
			
			for(int i=0; i<propertiesPlayer2.size(); i++) {
				crowns2 = crowns2 + propertiesPlayer2.get(i).getCrowns();
			}
			
			
			if(crowns1 > crowns2) {
				p1[2].setPropertyScore(p1[2].getPropertyScore()+1);
				Player temp = p1[3];
				p1[3] = p1[2];
				p1[2] = temp;
				return p1;
			} else if(crowns1 < crowns2) {
				p1[3].setPropertyScore(p1[3].getPropertyScore()+1);
				return p1;
			} 
			
			}
		}
		
		return p1;
	}
	
	
	/**
	 * 
	 * This method sorts the player array so that the player with the lowest score is at the start 
	 * and the player with the highest score is at the end.
	 * 
	 * @see ShuffleDomino.feature
	 * @author Gurdarshan Singh 260927466
	 * @param Player[]
	 * @return Player[]
	 */
	
	public static Player[] bubbleSort(Player[] scoreList) 
    { 
        int n = scoreList.length; 
        for (int i = 0; i < n-1; i++) {
            for (int j = 0; j < n-i-1; j++) {
                if (scoreList[j].getTotalScore() > scoreList[j+1].getTotalScore()) 
                { 
                    // swap arr[j+1] and arr[i] 
                    Player temp = scoreList[j]; 
                    scoreList[j] = scoreList[j+1]; 
                    scoreList[j+1] = temp; 
                } 
            }
        }
        
        return scoreList;
    } 
	
	
	/**
	 * 
	 * This method returns the color of the player depending on the input string.
	 * 
	 * @see CalculateRanking.feature
	 * @author Gurdarshan Singh 260927466
	 * @param String
	 * @return PlayerColor
	 */
	
	public static PlayerColor retrieveColor(String s1) {
		PlayerColor p = null;
		if(s1.equals("blue")) {
			p = PlayerColor.Blue;
		} else if(s1.equals("green")) {
			p = PlayerColor.Green;
		} else if(s1.equals("pink")) {
			p = PlayerColor.Pink;
		} else if(s1.equals("yellow") || s1.equals("yelow")) {
			p = PlayerColor.Yellow;
		}
		return p;
	}
	
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
	

	/**
	 * 
	 * This helper method obtains the neighborhood of the leftTile
	 * of a DominoInKingdom's associated Domino
	 * 
	 * @see verifyNeighborAdjacencyLastTerritory
	 * @author Jing Han 260528152
	 * @param t
	 * @param dInK
	 * @return n
	 */
	
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
	
	/**
	 * 
	 * This helper method obtains the neighborhood of the rightTile
	 * of a DominoInKingdom's associated Domino
	 * 
	 * @see verifyNeighborAdjacencyLastTerritory
	 * @author Jing Han 260528152
	 * @param t
	 * @param dInK
	 * @return n
	 */

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
	
	/**
	 * 
	 * This helper method checks of two KingdomTerritories overlap
	 * 
	 * @see verifyNoOverlapAllTerritories, verifyNoOverlapLastTerritory
	 * @author Jing Han 260528152
	 * @param a
	 * @param b
	 * @return boolean
	 */

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
	
	/**
	 * 
	 * This helper method calculates the square of the L2 dist btwn two coordinates
	 * 
	 * @see all verification methods
	 * @author Jing Han 260528152
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2 
	 * @return norm
	 */
	
	public static int L2NormSquared(int x1, int y1, int x2, int y2) {
		int deltaX=x2-x1;
		int deltaY=y2-y1;
		
		int norm = deltaX*deltaX+deltaY*deltaY;
		
		return norm;
	}
	
	/**
	 * 
	 * This helper method calculates the coordinate of a Domino's rightTile
	 * based on the Domin's leftTile and orientation
	 * 
	 * @see all verification methods
	 * @author Jing Han 260528152
	 * @param d
	 * @return coord2
	 */
	

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
	
	/**
	 * 
	 * This method identifies one type of property by TerrainType
	 *  of a player's kingdom and creates the corresponding Property object
	 * 
	 * @see IdentifyProperties.feature
	 * @author Jing Han 260528152
	 * @param terrainGroup
	 * @param t
	 * @param player
	 * @return void
	 */
	
		
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
	
	/**
	 * 
	 * This method clusters the all the of a player's kingdom by
	 * their terrain group into the largest possible clusters
	 * 
	 * @see IdentifyProperties.feature
	 * @author Jing Han 260528152
	 * @param terrainGroup
	 * @return firstPass
	 */
	
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
	
	/**
	 * 
	 * This method merges two clusters of the same type if they 
	 * share an adjacent edge
	 * 
	 * @see IdentifyProperties.feature
	 * @author Jing Han 260528152
	 * @param initialClusters
	 * @return mergedClusters
	 */
	
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
	
	/**
	 * 
	 * This method merges two tiles of the same type if they 
	 * share an adjacent edge
	 * 
	 * @see IdentifyProperties.feature
	 * @author Jing Han 260528152
	 * @param initialTiles
	 * @return mergedList
	 */
	
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
	
	/**
	 * 
	 * This method checks whether two tiles
	 * share an adjacent edge
	 * 
	 * @see IdentifyProperties.feature
	 * @author Jing Han 260528152
	 * @param tileA
	 * @param tileB
	 * @return boolean
	 */
	
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
	
	/**
	 * 
	 * This method checks whether two clusters of adjacent tiles
	 * share an adjacent edge
	 * 
	 * @see IdentifyProperties.feature
	 * @author Jing Han 260528152
	 * @param clusterA
	 * @param clusterB
	 * @return boolean
	 */
	
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
	
	/**
	 * 
	 * This method sorts the tiles of a player's kingdom
	 * by their TerrainTypes
	 * 
	 * @see IdentifyProperties.feature
	 * @author Jing Han 260528152
	 * @param player
	 * @return allProperty
	 */
	
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
	
	/**
	 * 
	 * This method extracts the coordinates, id, and crown number
	 * of the tiles of a DominoInKingdom dInK of the player's kingdom
	 * 
	 * @see IdentifyProperties.feature
	 * @author Jing Han 260528152
	 * @param dInK
	 * @return tileInfo
	 */
	
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
	
	
	///////////////////////////////////////
	/// ----Given TA Helper Methods---- ///
	///////////////////////////////////////
	
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

}
