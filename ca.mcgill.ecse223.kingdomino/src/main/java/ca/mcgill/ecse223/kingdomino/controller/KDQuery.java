package ca.mcgill.ecse223.kingdomino.controller;


import java.util.*;

import com.google.common.collect.Sets;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.model.Domino.DominoStatus;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom.DirectionKind;
import ca.mcgill.ecse223.kingdomino.model.*;

public class KDQuery {
	
	public KDQuery() {}
	
	/**
	 * 
	 * This method checks returns a
	 * all the users of the current
	 * Kingdomino object
	 * 
	 * @author Jing Han 260528152
	 * @param player
	 * @return users
	 */
	
	public static List<User> getUsers(){
		Kingdomino kd = KingdominoApplication.getKingdomino();
		List<User> users = kd.getUsers();
		return users;
	}
	
	/**
	 * 
	 * This method checks returns a
	 * 'valid' or 'invalid' status
	 * for a player's kingdom based
	 * on the verification methods
	 * of the controller
	 * 
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
	 * This method returns the size of the
	 * largest territory in a player's kingdom
	 * and the player's total number of crowns
	 * 
	 * @author Jing Han 260528152
	 * @param player
	 * @return sizeAndCrown
	 */
	
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
	 * This method returns a list of Dominos
	 * in a player's kingdom that violates
	 * the verification methods
	 * 
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
	 * determines if all players in game has been assigned user
	 * 
	 * @author Jing Han 260528152
	 * @param player
	 * @return p
	 * @throws java.lang.IllegalArgumentException
	 */
	
	public static boolean hasAllPlayersAUser() {
		Kingdomino kd = KingdominoApplication.getKingdomino();
		boolean allAssigned=true;
		for (Player p:kd.getCurrentGame().getPlayers()) {
			if (!p.hasUser()) {
				allAssigned=false;
				break;
			}
		}
		return allAssigned;
	}
	
	/**
	 * 
	 * simple wrapper method to retreive users that
	 * can be assigned to a player in the current game
	 * 
	 * @author Jing Han 260528152
	 * @param player
	 * @return p
	 * @throws java.lang.IllegalArgumentException
	 */
	
	public static List<User> getAvailableUserThisGame() {
		Kingdomino kd = KingdominoApplication.getKingdomino();
		List<User> availableUsers = new ArrayList<User>();
		for (User user:kd.getUsers()) {
			if (!user.hasPlayerInGames()) {
				availableUsers.add(user);
			}
			else {
				boolean noPlayersInThisGame=true;
				for (Player p:user.getPlayerInGames()) {
					if (p.getGame().equals(kd.getCurrentGame())) {
						noPlayersInThisGame=false;
						break;
					}
				}
				if (noPlayersInThisGame) {
					availableUsers.add(user);
				}
			}
		}
		return availableUsers;
		
	}
	
	public static List<String> getAvailableUserNamesThisGame() {
		List<User> availableUsers=getAvailableUserThisGame();
		List<String> names= new ArrayList<String>();
		for (User u:availableUsers) names.add(u.getName());
		return names;
	}
	
	/**
	 * 
	 * simple wrapper method for retrieving a player by color,
	 * applicable for 3-4 player game, further work needed for
	 * 2 player game
	 * 
	 * @author Jing Han 260528152
	 * @param player
	 * @return p
	 * @throws java.lang.IllegalArgumentException
	 */
	
	public static Player getPlayerByColor(String color) {
		Kingdomino kd = KingdominoApplication.getKingdomino();
		
		for (Player p:kd.getCurrentGame().getPlayers()) {
			if (p.getColor().name().equalsIgnoreCase(color)) return p;
		}
		
		throw new IllegalArgumentException("specified player color does not exist in current game");
	}
	
	/**
	 * 
	 * simple wrapper method for getting the territories of a player
	 * 
	 * @author Jing Han 260528152
	 * @param player
	 * @return List<KingdomTerritory>
	 */
	
	public static List<KingdomTerritory> getPlayerTerritory(Player player){
		return player.getKingdom().getTerritories();		
	}
	
	/**
	 * 
	 * simple wrapper method for returning the coordinates 
	 * of all tiles in a player's kingdom
	 * 
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
				if (t.getDomino().getStatus().equals(DominoStatus.PlacedInKingdom)) {
					int[] otherPos=calculateRightPos(t);
					dominoPos[0][i]=t.getX();
					dominoPos[1][i]=t.getY();
					dominoPos[2][i]=otherPos[0];
					dominoPos[3][i]=otherPos[1];
				}

			}
		}
		return dominoPos;
	}
	
	/**
	 * 
	 * simple wrapper method for calculating the 
	 * position of a domino's right tile based on 
	 * left tile and direction
	 * 
	 * @author Jing Han 260528152
	 * @param d
	 * @return coord2
	 */
	
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
	
	/**
	 * 
	 * determines valid coordinates for next domino placement
	 * in a player's kingdom. useful for discarding
	 * 
	 * @see DiscardDomino.feature 
	 * @author Jing Han 260528152
	 * @param player
	 * @return allPossibleCoords
	 */
	
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
	 * simple wrapper method for returning the 
	 * number of drafts created so far in game
	 * 
	 * @author Jing Han 260528152
	 * @param none
	 * @return int
	 */
	public static int getNumDraftsInGame() {
		Kingdomino kd = KingdominoApplication.getKingdomino();
		Game game = kd.getCurrentGame();
		return game.getAllDrafts().size();
	}
	
	/**
	 * 
	 * determines whether a domino has already been 
	 * chosen based on .hasDominoSelection
	 * 
	 * @author Jing Han 260528152
	 * @param domino
	 * @return boolean
	 */
	public static boolean isDominoTaken(Domino domino) {
		return domino.hasDominoSelection();
	}
	
	/**
	 * 
	 * determines whether the current player is the last
	 * in the current turn
	 *
	 * @see guard [isCurrentPlayerTheLastInTurn] of state machine 
	 * @author Jing Han 260528152
	 * @param p
	 * @return boolean
	 */
	
	public static boolean isCurrentPlayerTheLastInTurn(Player p) {
		List<Player> allPlayers=p.getGame().getPlayers();
		Player lastPlayer=allPlayers.get(allPlayers.size()-1);
		return p.equals(lastPlayer);
	}
	
	public static boolean lastPlayerInTurn(Player p) {
		List<Player> allPlayers=p.getGame().getPlayers();
		Player lastPlayer=allPlayers.get(0);
		return p.equals(lastPlayer);
	}
	
	/**
	 * 
	 * determines whether all the players have chosen in the 
	 * current round after placing/discarding their dominos
	 *
	 * @see guard [hasAllPlayersChosen] of state machine 
	 * @author Jing Han 260528152
	 * @param none
	 * @return boolean
	 */
	
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
	
	/**
	 * whether a domino has a valid placement given the player's
	 * current kingdom
	 *
	 * @see [isThereAvailablePlacement] guard of SM
	 * @author Jing Han 260528152
	 * @param player
	 * @param dInK
	 * @return boolean
	 */
	
	public static boolean isThereAvailablePlacement(Player player, DominoInKingdom dInK) {
		if (dInK.getDomino().getStatus().equals(DominoStatus.CorrectlyPreplaced)) {
			return true;
		}
		
		List<List<Integer>> freeCoords=KDQuery.getValidFreeCoordinates(player);
		if (freeCoords.size()==0) {
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
				dInK.setX(prevX);
				dInK.setY(prevY);
				dInK.setDirection(prevD);
				dInK.getDomino().setStatus(prevStatus);
				return true;
			}
			
			else {
				for (int i=0;i<4;i++) {
					KDController.rotateLatestDomino(player, "clockwise");
					if (verifyDominoInKingdom(player,dInK)) {
						dInK.setX(prevX);
						dInK.setY(prevY);
						dInK.setDirection(prevD);
						dInK.getDomino().setStatus(prevStatus);
						return true;
					}
				}
				
			}
			
		}
		
		dInK.setX(prevX);
		dInK.setY(prevY);
		dInK.setDirection(prevD);
		dInK.getDomino().setStatus(prevStatus);
		return false;
	}
	

	/**
	 * wrapper method to execute all the 
	 * verification methods in the controller
	 * simultaneously to determine the validity
	 * of a domino's position
	 *
	 * @see [verifyDomino] guard in SM
	 * @author Jing Han 260528152
	 * @param player
	 * @param dominoToPlace
	 * @return boolean
	 */
	
	public static boolean verifyDominoInKingdom(Player player, DominoInKingdom dominoToPlace) {
		
		if (KDController.verifyGridSizeAllKingdom(player,dominoToPlace)) {
			if(KDController.verifyNoOverlapLastTerritory(player,dominoToPlace)) {
				if (KDController.verifyCastleAdjacency(player,dominoToPlace)||KDController.verifyNeighborAdjacencyLastTerritory(player,dominoToPlace)) {
					return true;
				}
				else {
					return false;
				}
			}
			else {
				return false;
			}
		}
		else {
			return false;
		}
	}
	
	/**
	 * returns whether if the game's domino pile no longer has any dominos 
	 * of the status InPile or inNextDraft. Signlas when to proceed to 
	 * the Finishing state of the State Machine
	 * 
	 * @see [isDominoPileEmpty] guard of state machine
	 * @author Jing Han 260528152
	 * @param none
	 * @return boolean
	 */
	
	public static boolean isDominoPileEmpty() {
		Kingdomino kd = KingdominoApplication.getKingdomino();
		Game game=kd.getCurrentGame();
		List<Domino> dominoPile=game.getAllDominos();
		
		int counter=0;
		for (Domino d:dominoPile) {
			if (d.getStatus().equals(DominoStatus.PlacedInKingdom)||d.getStatus().equals(DominoStatus.Discarded)||d.getStatus().equals(DominoStatus.Excluded)) {
				counter+=1;
			}
		}
		
		if (counter==dominoPile.size()) return true;
		else return false;
	}
	
//	public static boolean isDominoPileEmpty() {
//		Kingdomino kd = KingdominoApplication.getKingdomino();
//		Game game=kd.getCurrentGame();
//		List<Domino> dominoPile=game.getAllDominos();
//		
//		int counter=0;
//		for (Domino d:dominoPile) {
//			if (d.getStatus().equals(DominoStatus.InPile)||d.getStatus().equals(DominoStatus.InNextDraft)) {
//				counter+=1;
//			}
//		}
//		
//		if (counter==0) return true;
//		else return false;
//	}
	
	/**
	 * determines whether all the players have played in this turn by either discarding or 
	 * placing their dominos
	 * 
	 * @see [hasAllPlayersPlayed] guard of state machine
	 * @author Jing Han 260528152
	 * @param none
	 * @return boolean
	 */
	
	public static boolean hasAllPlayersPlayed() {
		Kingdomino kd = KingdominoApplication.getKingdomino();
		Game game=kd.getCurrentGame();
		List<Player> players=game.getPlayers();
		
		boolean allPlayed=true;
		
		for (Player p:players) {
//			Domino lastDomino=p.getDominoSelection().getDomino();
//			if (!(lastDomino.getStatus().equals(DominoStatus.PlacedInKingdom)||lastDomino.getStatus().equals(DominoStatus.Discarded))) {
//				allPlayed=false;
//				break;
//			}
			if (p.hasDominoSelection()) {
				allPlayed=false;
				break;
			}
		}
		
		return allPlayed;
	}
	
	/**
	 * determines whether the current turn is the last turn of the game
	 * 
	 * @see [isCurrentTurnTheLastInGame] guard of state machine
	 * @author Jing Han 260528152
	 * @param none
	 * @return boolean
	 */
	
	public static boolean isCurrentTurnTheLastInGame() {
		
		return isDraftLimitReached();
	}
	
	/**
	 * determines whether the maximum number of drafts have been 
	 * reached for the given number of players
	 * 
	 * @author Jing Han 260528152
	 * @param none
	 * @return boolean
	 */

	public static boolean isDraftLimitReached() {
		Kingdomino kd = KingdominoApplication.getKingdomino();
		Game game = kd.getCurrentGame();
		int draftNumLimit=0;
		
		if ((game.getNumberOfPlayers()==4)||(game.getNumberOfPlayers()==3)) draftNumLimit=12;
		if (game.getNumberOfPlayers()==2) draftNumLimit=6;
		
		return getNumDraftsInGame()==draftNumLimit;
	}
	
	
	///////////////////////////////////////////////
	//				helper methods				 //
	///////////////////////////////////////////////

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