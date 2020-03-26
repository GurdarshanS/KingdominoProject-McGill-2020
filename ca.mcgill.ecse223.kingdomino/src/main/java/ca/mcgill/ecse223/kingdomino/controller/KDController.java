package ca.mcgill.ecse223.kingdomino.controller;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.development.Box;
//import ca.mcgill.ecse223.kingdomino.model.Kingdomino;
import ca.mcgill.ecse223.kingdomino.model.*;
import ca.mcgill.ecse223.kingdomino.model.Domino.DominoStatus;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom.DirectionKind;
import ca.mcgill.ecse223.kingdomino.model.Draft.DraftStatus;
import ca.mcgill.ecse223.kingdomino.model.Player.PlayerColor;
import ca.mcgill.ecse223.kingdomino.persistence.KDPersistence;


import java.util.concurrent.ThreadLocalRandom;
import java.io.File;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class KDController {

	public KDController(){}
	
	/**
	 * 
	 * This method initiates an empty game.
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
	 * @author Anthony Harissi Dagher
	 * Feature 6: This method loads a saved game for the player.
	 * @param file
	 * @return boolean
	 * @throws java.lang.IllegalArgumentException	
	 *  */
	
	public static Kingdomino loadGame() {
		Kingdomino kd = KDPersistence.load();
		if (kd==null) {
			kd = new Kingdomino();
//			System.out.println("no kingdomino serialization found");
//			System.out.println("created new kingdomino instance");
//			System.out.println("serialized new kingdomino instance");
		}
		else {
//			System.out.println("loaded kingdomino serialization");	
		}
		
		KingdominoApplication.setKingdomino(kd);
		KDPersistence.save(kd);
		
		return kd;
	}
	
//	public static boolean loadGame(File file) throws InvalidInputException {
//		
//		boolean gameLoaded = false;
//		String directory = "./src/test/resources/"+file.getName();	
//		File fileSearch = new File(directory);
//		if (fileSearch.isFile()) {
//			if(fileSearch.canRead()) {
//				try{
//					KDPersistence.load();
//					gameLoaded = true;
//				}
//				catch(RuntimeException r) {
//					throw new InvalidInputException("Invalid file name.");
//				}
//			}
//		}
//		return gameLoaded;
//	}
	
	/**
	 * @author Anthony Harissi Dagher
	 * Feature 7: This method saves the current game for the player.
	 * @param file
	 * @return boolean
	 * @throws java.lang.IllegalArgumentException
	 */
	
	public static boolean saveGame() {
		Kingdomino kd = KingdominoApplication.getKingdomino();
		try{
			KDPersistence.save(kd);
			return true;
		}
		catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
//	public static boolean saveGame(File file, boolean overwrite) throws InvalidInputException {
//		
//		boolean gameSaved;
//		Kingdomino kingdomino = KingdominoApplication.getKingdomino();
//		String directory = "./src/test/resources/"+file.getName();
//		File fileSearch = new File(directory);
//		if(fileSearch.exists() && overwrite == false) {
//			gameSaved = false;
//		}
//		else {
//			try {
//				KDPersistence.save(kingdomino); 
//				gameSaved = true;
//			}catch(RuntimeException r) {
//				throw new InvalidInputException(r.getMessage());
//			}
//		}
//		return gameSaved;
//	}
	
	
	/**
	 * @author Anthony Harissi Dagher
	 * Feature 1: This method sets the desired game options for the player.
	 * @param numPlayers 
	 * @param selectedBonusOptions
	 * @throws java.lang.IllegalArgumentException	 
	 * */
	public static void setGameOptions(int numPlayers, List<BonusOption> selectedBonusOptions)throws InvalidInputException{
		
		Kingdomino kingdomino = KingdominoApplication.getKingdomino();
		if(numPlayers <= 1 || numPlayers > 4) {
			throw new InvalidInputException("Must between 2 and 4 players for a game.");
		}
		if(numPlayers == 2) {
			Game game = new Game(24, kingdomino);
			game.setNumberOfPlayers(2);
			for(int i=0; i< selectedBonusOptions.size(); i++) {
				BonusOption bonusOption = selectedBonusOptions.get(i);
				game.addSelectedBonusOption(bonusOption);
			}
			kingdomino.setCurrentGame(game);
		}
		else if(numPlayers == 3) {
			Game game = new Game(36, kingdomino);
			game.setNumberOfPlayers(3);
			for(int i=0; i<selectedBonusOptions.size(); i++) {
				BonusOption bonusOption = selectedBonusOptions.get(i);
				game.addSelectedBonusOption(bonusOption);
			}
			kingdomino.setCurrentGame(game);

		}
		else{
			Game game = new Game(48, kingdomino);
			game.setNumberOfPlayers(4);
			for(int i=0; i<selectedBonusOptions.size(); i++) {
				BonusOption bonusOption = selectedBonusOptions.get(i);
				game.addSelectedBonusOption(bonusOption);
			}
			kingdomino.setCurrentGame(game);
		}
		
//		KDPersistence.save(kingdomino);
	} 
	
	/**
	 * 
	 * This method checks if inputed user name
	 * is valid and then adds it to kingdomino
	 * 
	 * @see  - ProvideUserProfile.feature
	 * @author Keon Olszewski 260927813
	 * @param username
	 * @return void
	 * @throws java.lang.IllegalArgumentException
	 */

	public static void provideUserProfile(String username) throws IllegalArgumentException {

		Kingdomino kingdomino = KingdominoApplication.getKingdomino();
		List<User> userList = kingdomino.getUsers();
		
		for(int i = 0; i < userList.size(); i++) {
		
		if (userList.get(i).getName().equalsIgnoreCase(username)) {
			
			throw new IllegalArgumentException("User already exists");
			
			} 
		}
		
		if(!username.matches("[a-zA-Z0-9]+")){
			
			throw new IllegalArgumentException("must Contain only letters or Numbers");
		}
		
	
		
			
		kingdomino.addUser(username);
		
	}//ProvidetUserProfile
	
	/**
	
	/**
	 * @author Anthony Harissi Dagher
	 * Feature 3: This method starts a new game for the player.
	 */
	

	
	public static void startANewGame() {
		
		Kingdomino kd = KingdominoApplication.getKingdomino();
		Game game = kd.getCurrentGame();
		
		int dominoNums=game.getMaxPileSize();
		createShuffledDominoPile(game,dominoNums);
		
		
		int playerNums=game.getNumberOfPlayers();
		
		List<Integer> playerOrder = uniqueRandomSequence(playerNums,0,playerNums-1);
		PlayerColor[] availableColors= {PlayerColor.Blue,PlayerColor.Green,PlayerColor.Pink,PlayerColor.Yellow};
		
		int i=0;
		for (Integer order:playerOrder) {
			Player p = new Player(game);
			p.setColor(availableColors[order]);
			Kingdom kingdom = new Kingdom(p);
			new Castle(0, 0, kingdom, p);
			if (i==0) game.setNextPlayer(p);
			i++;
		}
		
		createNextDraft();
	}
	
	/**
	 * 
	 * This method lets a user browse domino pile before game starts
	 * displays previously shuffled dominos in ascending ranking,
	 * does not change underlying list
	 * 
	 * @see  - ProvideUserProfile.feature
	 * @author Jing Han	260528152
	 * @param username
	 * @return void
	 * @throws java.lang.IllegalArgumentException
	 */
	
	public static List<Domino> browseDominos(){
		
		Kingdomino kd = KingdominoApplication.getKingdomino();
		Game game = kd.getCurrentGame();
		List<Domino> allDominos = game.getAllDominos();
		List<Domino> sortedDominos = new ArrayList<Domino>();
		
		List<Integer> ignoreVal = new ArrayList<Integer>();
		
		for (int i=0;i<allDominos.size();i++) {
			int min=100;
			int minIndex=-1;
			for (int j=0;j<allDominos.size();j++) {
				Domino d = allDominos.get(j);
				if (!ignoreVal.contains(d.getId())) {
					if (d.getId()<min) {
						min=d.getId();
						minIndex=j;
					}
				}
			}
			sortedDominos.add(allDominos.get(minIndex));
			ignoreVal.add(min);
		}
		
		return sortedDominos;
		
	}
	
	/**
	 * 
	 * This method assigns a Player in the Current game to a User in kingdomino
	 * 
	 * @see  - ProvideUserProfile.feature
	 * @author Jing Han	260528152
	 * @param username
	 * @return void
	 * @throws java.lang.IllegalArgumentException
	 */
	
	public static boolean assignPlayerToUser(Player player, User user) {
		if (!player.hasUser()) {
			player.setUser(user);
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
	 * @author Keon Olszewski 260927813
	 * @param player
	 * @return void
	 */
	
	public static void createNextDraft() {
		Kingdomino kd = KingdominoApplication.getKingdomino();
		Game game = kd.getCurrentGame();
		int draftNumLimit=0;
		
		if ((game.getNumberOfPlayers()==4)||(game.getNumberOfPlayers()==3)) draftNumLimit=12;
		if (game.getNumberOfPlayers()==2) draftNumLimit=6;
		
		
		if (game.getAllDrafts().size()==0) {
			Draft currentDraft=createOneDraft();
			game.setCurrentDraft(currentDraft);
			changeDraftDominoStatus(currentDraft,Domino.DominoStatus.InCurrentDraft);

			Draft nextDraft = createOneDraft();
			nextDraft.setDraftStatus(Draft.DraftStatus.FaceDown);
			game.setNextDraft(nextDraft);
			changeDraftDominoStatus(nextDraft,Domino.DominoStatus.InNextDraft);	
			
			sortCurrentDraft();
			currentDraft.setDraftStatus(Draft.DraftStatus.Sorted);

		}
		else if (game.getAllDrafts().size()<draftNumLimit) {
			
//			changeDraftDominoStatus(game.getCurrentDraft(),DominoStatus.Excluded);
			
			changeDraftDominoStatus(game.getNextDraft(),DominoStatus.InCurrentDraft);
			game.setCurrentDraft(game.getNextDraft());
			
			Draft nextDraft=createOneDraft();
			changeDraftDominoStatus(nextDraft,DominoStatus.InNextDraft);
			game.setNextDraft(nextDraft);
			game.getNextDraft().setDraftStatus(Draft.DraftStatus.FaceDown);
			
			sortCurrentDraft();
			game.getCurrentDraft().setDraftStatus(Draft.DraftStatus.Sorted);

		}
		else if (kd.getCurrentGame().getAllDrafts().size()==draftNumLimit) {
			if (game.getNextDraft()!=null) {
//				changeDraftDominoStatus(game.getCurrentDraft(),DominoStatus.Excluded);
				game.setCurrentDraft(game.getNextDraft());
				game.setNextDraft(null);
			}
			
			game.getCurrentDraft().setDraftStatus(Draft.DraftStatus.Sorted);
			sortCurrentDraft();
		}
		
	}
	
	/**
	 * 
	 * This method returns the list of users
	 * in alphabetical order
	 * 
	 * @see  - ProvideUserProfile.feature
	 * @author Keon Olszewski 260927813
	 * @param void
	 * @return listToBrowse
	 */
	
	public static List<User> BrowseUserList() {
		
		Kingdomino kingdomino = KingdominoApplication.getKingdomino();
		
		List<User> userList = kingdomino.getUsers();
		List<User> listToBrowse = new ArrayList<User>();
		String userNames[] = new String[userList.size()];
		
		for(int i = 0; i < userNames.length; i ++) {
			
			String name = userList.get(i).getName();
			userNames[i] = name;
			
			}
		
		Arrays.sort(userNames);
		
		for(int i = 0; i < userNames.length; i ++) {
		listToBrowse.add(i, getUserByName(userNames[i]));
			}
		
		return listToBrowse;
		
	}
	
	
	/**
	 * 
	 * this method returns a given user
	 * for checking their statistics
	 * 
	 * @see  - ProvideUserProfile.feature
	 * @author Keon Olszewski 260927813
	 * @param username
	 * @return currentUser
	 */
	
	
	public static User queryUser(String username) {
		
		User currentUser = getUserByName(username);
		return currentUser;
	}
	
	
	
	
	
	
	
	/**
	 * 
	 * This method Adds a domino selection to the 
	 * next draft
	 * 
	 * @see  - ChooseNextDomino.feature
	 * @author Keon Olszewski 260927813
	 * @param aDomino
	 * @return void
	 */
	
	public static void ChoosNextDomino(Domino aDomino){
				
		Kingdomino kingdomino = KingdominoApplication.getKingdomino();
		Game game = kingdomino.getCurrentGame();
		Draft currentDraft = game.getCurrentDraft();
		Player currentPlayer = game.getNextPlayer();
		
		if (!currentDraft.getIdSortedDominos().contains(aDomino) || aDomino.hasDominoSelection()) {
			return;
		}
		
		List<Player> allPlayers = game.getPlayers();
		int currentPlayerIndex=-1;
		
		for (int i=0;i<allPlayers.size();i++) {
			Player testPlayer=allPlayers.get(i);
			if (testPlayer.getColor().equals(currentPlayer.getColor())) {
				currentPlayerIndex=i;
				break;
			}
		}
		
		DominoSelection currentSelection;
		if (!currentPlayer.hasDominoSelection()) {
			currentSelection = currentDraft.addSelection(currentPlayer, aDomino);
			}
		else {
			currentSelection=currentPlayer.getDominoSelection();
		}
		
		aDomino.setDominoSelection(currentSelection);      
        
        if (!(currentPlayerIndex==allPlayers.size()-1)) {
        	game.setNextPlayer(allPlayers.get(currentPlayerIndex+1));
        }
        
		boolean allChosen=true;
		for (Domino d:currentDraft.getIdSortedDominos()) {
			if (!d.hasDominoSelection()){
				allChosen=false;
				break;
			}
		}
		
		if (allChosen) {
			rearrangePlayerOrder();
		}
        
        
		
	}//ChoosNextDomino
	
	private static void rearrangePlayerOrder() {
		Kingdomino kingdomino = KingdominoApplication.getKingdomino();
		Game game = kingdomino.getCurrentGame();
		Draft currentDraft = game.getCurrentDraft();
		
		List<Domino> draftDominos = currentDraft.getIdSortedDominos();
		List<Player> newOrderPlayers = new ArrayList<Player>();
		
		for (Domino d:draftDominos) {
			newOrderPlayers.add(d.getDominoSelection().getPlayer());
		}
		
		for (int i=0;i<newOrderPlayers.size();i++) {
			game.addOrMovePlayerAt(newOrderPlayers.get(i), i);
		}
		
		game.setNextPlayer(game.getPlayer(0));
	}
	

//	public static void ChoosNextDomino(Domino aDomino){
//		
//		Kingdomino kingdomino = KingdominoApplication.getKingdomino();
//		Game game = kingdomino.getCurrentGame();
//		Draft nextDraft = game.getNextDraft();
//		Player currentPlayer = game.getNextPlayer();
//		
//		
//		if( aDomino.hasDominoSelection()) {
//			
//			return;
//			
//			}
//		else {
//			
//			DominoSelection currentSelection = nextDraft.addSelection(currentPlayer, aDomino);
//			aDomino.setDominoSelection(currentSelection);
//	        currentPlayer.setDominoSelection(currentSelection);
//	        
//		}
//	}//ChoosNextDomino
	
	
	/**
	 * 
	 * This method preplaces a DominoSelection into a playe's kingdom
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
	
	public static boolean verifyDominoInKingdom(Player player, DominoInKingdom dominoToPlace) {
		
		if (verifyGridSizeAllKingdom(player)) {
			if(verifyNoOverlapLastTerritory(player)) {
				if (verifyCastleAdjacency(player)||verifyNeighborAdjacencyLastTerritory(player)) {
					return true;
				}
				else {
					System.out.println("\ninvalid: neither neighbor or castle adjacent");
					return false;
				}
			}
			else {
				System.out.println("\ninvalid: overlap\n");
				return false;
			}
		}
		else {
			System.out.println("\ninvalid: grid size exceeded\n");
			return false;
		}
	}

	public static DominoInKingdom prePlaceDominoDominoSelection(Player player, int posx, int posy, String dir) {
		Domino dominoToPlace=player.getDominoSelection().getDomino();
		Kingdom kingdom=player.getKingdom();
		
		DominoInKingdom dInK = new DominoInKingdom(posx,posy,kingdom,dominoToPlace);
		dInK.setDirection(getDirection(dir));
		
		boolean valid=verifyDominoInKingdom(player,dInK);
		if (valid) {
			dominoToPlace.setStatus(DominoStatus.CorrectlyPreplaced);
		}
		else {
			dominoToPlace.setStatus(DominoStatus.ErroneouslyPreplaced);
		}
		
		return dInK;
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
	
	public static void moveLatestDomino(Player aPlayer, String movement){
		
		List<KingdomTerritory> territories = aPlayer.getKingdom().getTerritories();
		if (territories.size()==1) {
			return;
		}
		
		DominoInKingdom dInKingdom = (DominoInKingdom) territories.get(territories.size()-1);
		
		int xPosPrevious = dInKingdom.getX();
		int yPosPrevious = dInKingdom.getY();

		if(movement.equalsIgnoreCase("Right")) dInKingdom.setX(xPosPrevious + 1);
		else if(movement.equalsIgnoreCase("Left")) dInKingdom.setX(xPosPrevious - 1);
		else if(movement.equalsIgnoreCase("Up")) dInKingdom.setY(yPosPrevious + 1);
		else if(movement.equalsIgnoreCase("Down")) dInKingdom.setY(yPosPrevious - 1);
		
		if(!verifyGridLimit(dInKingdom)) {
	
			dInKingdom.setX(xPosPrevious);
			dInKingdom.setY(yPosPrevious);
			dInKingdom.getDomino().setStatus(DominoStatus.ErroneouslyPreplaced);
			System.out.println("invalid: grid size exceeded");

			return;
			
		}
		else {
			
			boolean valid=verifyDominoInKingdom(aPlayer,dInKingdom);
			
			if (valid) {
				dInKingdom.getDomino().setStatus(DominoStatus.CorrectlyPreplaced);
			}
			else {
				dInKingdom.getDomino().setStatus(DominoStatus.ErroneouslyPreplaced);
			}
			
		}
		
	}
	
//	public static void moveCurrentDomino(Player aPlayer, DominoInKingdom dInKingdom, String movement){
//	
//		int xPosPrevious = dInKingdom.getX();
//		int yPosPrevious = dInKingdom.getY();
//
//		if(movement.equalsIgnoreCase("Right")) dInKingdom.setX(xPosPrevious + 1);
//		else if(movement.equalsIgnoreCase("Left")) dInKingdom.setX(xPosPrevious - 1);
//		else if(movement.equalsIgnoreCase("Up")) dInKingdom.setY(yPosPrevious + 1);
//		else if(movement.equalsIgnoreCase("Down")) dInKingdom.setY(yPosPrevious - 1);
//		
//		if(!verifyGridLimit(dInKingdom)) {
//	
//			dInKingdom.setX(xPosPrevious);
//			dInKingdom.setY(yPosPrevious);
//			
//			return;
//			
//		}
//		
//		else {
//			
//			if(verifyNeighborAdjacencyLastTerritory(aPlayer) && verifyNoOverlapLastTerritory(aPlayer) && verifyGridSizeAllKingdom(aPlayer)) {
//				
//				dInKingdom.getDomino().setStatus(DominoStatus.CorrectlyPreplaced);
//				return;
//				
//			}
//				
//			else if(verifyCastleAdjacency(aPlayer) && verifyNoOverlapLastTerritory(aPlayer) && verifyGridSizeAllKingdom(aPlayer)) {
//					
//				
//				dInKingdom.getDomino().setStatus(DominoStatus.CorrectlyPreplaced);
//				return;
//				
//			}
//		
//			dInKingdom.getDomino().setStatus(DominoStatus.ErroneouslyPreplaced);
//			
//		}
//		
//	}
	
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
	
	public static void rotateLatestDomino(Player aPlayer, String rotation) { 

		List<KingdomTerritory> territories = aPlayer.getKingdom().getTerritories();
		if (territories.size()==1) {
			return;
		}
		
		DominoInKingdom dInKingdom = (DominoInKingdom) territories.get(territories.size()-1);

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
			System.out.println("invalid: grid size exceeded\n");
			return;
			
		}
		
		else {
			
			boolean valid=verifyDominoInKingdom(aPlayer,dInKingdom);
			
			if (valid) {
				dInKingdom.getDomino().setStatus(DominoStatus.CorrectlyPreplaced);
			}
			else {
				dInKingdom.getDomino().setStatus(DominoStatus.ErroneouslyPreplaced);
			}
			
		}
	
	}
	
//	public static void rotateCurrentDomino(Player aPlayer, DominoInKingdom dInKingdom, String rotation) { 
//
//		if(aPlayer == null || dInKingdom == null) throw new java.lang.IllegalArgumentException("Invalid input");
//		if(!(((DominoInKingdom)aPlayer.getKingdom().getTerritory(aPlayer.getKingdom().getTerritories().size() -1)).equals(dInKingdom))) throw new java.lang.IllegalArgumentException("This domino does not belong to this players kingdom");
//		if(dInKingdom.getDomino().getStatus().equals(DominoStatus.PlacedInKingdom)) throw new java.lang.IllegalArgumentException("This domino is already placed in the players kingdom");
//		
//		DirectionKind dominoDir = dInKingdom.getDirection();
//		
//		if(dominoDir.equals(DirectionKind.Up) && rotation.equalsIgnoreCase("Clockwise")) dInKingdom.setDirection(DirectionKind.Right);
//		else if(dominoDir.equals(DirectionKind.Right) && rotation.equalsIgnoreCase("Clockwise")) dInKingdom.setDirection(DirectionKind.Down);
//		else if(dominoDir.equals(DirectionKind.Down) && rotation.equalsIgnoreCase("Clockwise")) dInKingdom.setDirection(DirectionKind.Left);
//		else if(dominoDir.equals(DirectionKind.Left) && rotation.equalsIgnoreCase("Clockwise")) dInKingdom.setDirection(DirectionKind.Up);
//			
//		if(dominoDir.equals(DirectionKind.Up) && rotation.equalsIgnoreCase("CounterClockwise")) dInKingdom.setDirection(DirectionKind.Left);
//		else if(dominoDir.equals(DirectionKind.Left) && rotation.equalsIgnoreCase("CounterClockwise")) dInKingdom.setDirection(DirectionKind.Down);
//		else if(dominoDir.equals(DirectionKind.Down) && rotation.equalsIgnoreCase("CounterClockwise")) dInKingdom.setDirection(DirectionKind.Right);
//		else if(dominoDir.equals(DirectionKind.Right) && rotation.equalsIgnoreCase("CounterClockwise")) dInKingdom.setDirection(DirectionKind.Up);
//		
//		if(!verifyGridLimit(dInKingdom)) {
//	
//			dInKingdom.setDirection(dominoDir);
//			return;
//			
//		}
//		
//		else {
//			
//			if(verifyNeighborAdjacencyLastTerritory(aPlayer) && verifyNoOverlapLastTerritory(aPlayer) && verifyGridSizeAllKingdom(aPlayer)) {
//				
//				dInKingdom.getDomino().setStatus(DominoStatus.CorrectlyPreplaced);
//				return;
//				
//			}
//				
//			else if(verifyCastleAdjacency(aPlayer) && verifyNoOverlapLastTerritory(aPlayer) && verifyGridSizeAllKingdom(aPlayer)) {
//					
//				dInKingdom.getDomino().setStatus(DominoStatus.CorrectlyPreplaced);
//				return;
//				
//			}
//		
//			dInKingdom.getDomino().setStatus(DominoStatus.ErroneouslyPreplaced);
//			
//		}
//	
//	}
	
	
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
	
	public static boolean placeLatestDomino(Player aPlayer) { 
		
		List<KingdomTerritory> territories = aPlayer.getKingdom().getTerritories();
		if (territories.size()==1) {
			return false;
		}
		
		DominoInKingdom dInKingdom = (DominoInKingdom) territories.get(territories.size()-1);
		
		if (dInKingdom.getDomino().getStatus().equals(DominoStatus.CorrectlyPreplaced)) {
			dInKingdom.getDomino().setStatus(DominoStatus.PlacedInKingdom);
			return true;
		}
		else {
			return false;
		}
		
	}
//	public static void placeDomino(Player aPlayer, Domino dominoToPlace) throws java.lang.IllegalArgumentException { 
//		
//		if(aPlayer == null || dominoToPlace == null) throw new java.lang.IllegalArgumentException("Invalid input");
//		if(!(dominoToPlace.getDominoSelection().getPlayer().equals(aPlayer))) throw new java.lang.IllegalArgumentException("This domino does not belong to this player");
//		if(dominoToPlace.getStatus().equals(DominoStatus.PlacedInKingdom)) throw new java.lang.IllegalArgumentException("This domino is already placed in this players kingdom");
//		
//		if(verifyGridSizeAllKingdom(aPlayer) &&  verifyNoOverlapLastTerritory(aPlayer) && verifyNeighborAdjacencyLastTerritory(aPlayer) && dominoToPlace.getStatus().equals(DominoStatus.CorrectlyPreplaced)) {
//			
//			dominoToPlace.setStatus(DominoStatus.PlacedInKingdom);
//			aPlayer.getDominoSelection().delete();
//			
//		}
//		
//		else if(verifyGridSizeAllKingdom(aPlayer) && verifyNoOverlapLastTerritory(aPlayer) && verifyCastleAdjacency(aPlayer) && dominoToPlace.getStatus().equals(DominoStatus.CorrectlyPreplaced)) {
//			
//			dominoToPlace.setStatus(DominoStatus.PlacedInKingdom);	
//			aPlayer.getDominoSelection().delete();
//			
//		}
//		
//	}
	
	
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
	
	public static void discardLatestDomino(Player aPlayer){ 
		
		List<KingdomTerritory> territories = aPlayer.getKingdom().getTerritories();
		if (territories.size()==1) {
			return;
		}	
		DominoInKingdom dInKingdom = (DominoInKingdom) territories.get(territories.size()-1);
		
		
		
		int[] availableSpaceInKingdom = getAvailableSpaceInGrid(aPlayer);
		System.out.println(Arrays.toString(availableSpaceInKingdom));
	}
	
//	public static void discardDomino(Player aPlayer) throws java.lang.IllegalArgumentException{ 
//				
//		if(aPlayer == null) throw new java.lang.IllegalArgumentException("This player does not exist");
//		
//		DominoInKingdom newlyPrePlacedDomino = (DominoInKingdom) aPlayer.getKingdom().getTerritory(aPlayer.getKingdom().getTerritories().size()-1);
//		if(newlyPrePlacedDomino.getDomino().getStatus().equals(DominoStatus.PlacedInKingdom)) throw new java.lang.IllegalArgumentException("This domino is already placed in the players kingdom");
//
//		int[] availableSpaceInKingdom = getAvailableSpaceInGrid(aPlayer);
//		
//		for(int i = -4; i<5; i++) {
//			
//			for(int j = -4; j<5; j++) {
//				
//				for(int z = 0; z<4; z++) {
//					
//					if(i > availableSpaceInKingdom[0] && j > availableSpaceInKingdom[1] && i < availableSpaceInKingdom[2] && j < availableSpaceInKingdom[3]){
//				
//						newlyPrePlacedDomino.setX(i);
//						newlyPrePlacedDomino.setY(j);
//						
//						if(verifyGridSizeAllKingdom(aPlayer) && verifyNoOverlapLastTerritory(aPlayer) && verifyNeighborAdjacencyLastTerritory(aPlayer)) {
//						
//							newlyPrePlacedDomino.getDomino().setStatus(DominoStatus.ErroneouslyPreplaced);
//							return;
//							
//						}
//						
//						else if(verifyGridSizeAllKingdom(aPlayer) && verifyNoOverlapLastTerritory(aPlayer) && verifyCastleAdjacency(aPlayer)) {
//						
//							newlyPrePlacedDomino.getDomino().setStatus(DominoStatus.ErroneouslyPreplaced);
//							return;
//							
//						}
//					
//					}
//					
//					rotateCurrentDomino(aPlayer, newlyPrePlacedDomino, "Clockwise");
//				
//				}
//				
//			}
//		
//		}
//	
//		newlyPrePlacedDomino.getDomino().setStatus(DominoStatus.Discarded);
//		newlyPrePlacedDomino.delete();
//		aPlayer.getDominoSelection().delete();
//		
//	}
	
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
//			System.out.println(respectGrid);
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
//					((DominoInKingdom) each).getDomino().setStatus(DominoStatus.ErroneouslyPreplaced);
					badCount++;
				}
				else {
//					((DominoInKingdom) each).getDomino().setStatus(DominoStatus.CorrectlyPreplaced);
				}
			}
			
		}
		if (badCount>0) {
			respectGrid=false;
		}
		else {
			respectGrid=true;
		}
//		System.out.println(respectGrid);
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
	 * @return noOverlap
	 */
	
	public static boolean verifyNoOverlapAllTerritories(Player player) {
		
//		verifies all territories in kingdom
		int overlappedCount=0;
		boolean noOverlap=true;
		
		List<KingdomTerritory> territories = player.getKingdom().getTerritories();
		
		if (territories.size()==1) {
			noOverlap=true;
//			System.out.println(noOverlap);
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
//						((DominoInKingdom) tA).getDomino().setStatus(DominoStatus.ErroneouslyPreplaced);
						overlappedCount++;
					}
					else {
//						((DominoInKingdom) tA).getDomino().setStatus(DominoStatus.CorrectlyPreplaced);
					}
					
				}
			}
			if (overlappedCount>0) {
				noOverlap=false;
			}
			else {
				noOverlap=true;
			}
//			System.out.println(noOverlap);
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
	 * @return noOverlap
	 */
	
	public static boolean verifyNoOverlapLastTerritory(Player player) {
		
//		only verifies the last preplaced domino
		boolean noOverlap=true;
		
		List<KingdomTerritory> territories = player.getKingdom().getTerritories();
		
		if (territories.size()==1) {
			noOverlap=true;
//			System.out.println(noOverlap);
			return noOverlap;
		}
		
		else {
			
			KingdomTerritory tA;
			KingdomTerritory tB;
	
			tA=territories.get(territories.size()-1);
				
			for (int j=territories.size()-2;j>-1;j--) {
					
					tB=territories.get(j);

					if (checkOverlap(tA,tB)){
//						((DominoInKingdom) tA).getDomino().setStatus(DominoStatus.ErroneouslyPreplaced);
						noOverlap=false;
						break;
					}
					else {
//						((DominoInKingdom) tA).getDomino().setStatus(DominoStatus.CorrectlyPreplaced);
						noOverlap=true;
					}
					
				}
//			System.out.println(noOverlap);
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
	 * @return castleAdj
	 */
	
	public static boolean verifyCastleAdjacency(Player player) {
		
		boolean castleAdj=true;
		
		List <KingdomTerritory> t = player.getKingdom().getTerritories();
		
		if (t.size()==1) {
			castleAdj=true;
//			System.out.println(castleAdj);
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
//			((DominoInKingdom) testD).getDomino().setStatus(DominoStatus.CorrectlyPreplaced);
			castleAdj=true;
		}
		else if ((norm1>1)&&(norm2==1)) {
//			((DominoInKingdom) testD).getDomino().setStatus(DominoStatus.CorrectlyPreplaced);
			castleAdj=true;
		}
		else {
//			((DominoInKingdom) testD).getDomino().setStatus(DominoStatus.ErroneouslyPreplaced);
			castleAdj=false;
		}
		
//		System.out.println(castleAdj);
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
	 * @return neighborAdj
	 */
	
	public static boolean verifyNeighborAdjacencyLastTerritory(Player player) {
		
		boolean neighborAdj=true;
		
		List<KingdomTerritory> t =player.getKingdom().getTerritories();
		
		if (t.size()==1) {
			neighborAdj=true;
//			System.out.println(neighborAdj);
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
//				prePlacedDomino.getDomino().setStatus(DominoStatus.ErroneouslyPreplaced);
				neighborAdj=false;
			}
			else {
//				prePlacedDomino.getDomino().setStatus(DominoStatus.CorrectlyPreplaced);
				neighborAdj=true;
			}
		
//			System.out.println(neighborAdj);
			return neighborAdj;
		}
		
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
	 * This method Reveals next Draft by Setting
	 * Next Draft status to FaceUp 
	 * 
	 * @see  - OrderAndRevealNextDraft.feature
	 * @author Keon Olszewski 260927813
	 * @param void
	 * @return void
	 */
	public static void RevealNextDraft() {
		
		Kingdomino kingdomino = KingdominoApplication.getKingdomino();
		Game game = kingdomino.getCurrentGame();
		
		if(game.getNextDraft() == null) {
			
			Draft currentDraft =	game.getCurrentDraft();
			currentDraft.setDraftStatus(Draft.DraftStatus.FaceUp);
		}
		
		else{
			
		Draft nextDraft =	game.getNextDraft();
		nextDraft.setDraftStatus(Draft.DraftStatus.FaceUp);
		
		}
	}
	
	/**
	 * 
	 * This method sorts the dominos in the current draft
	 * by ID number
	 * 
	 * @see  - OrderAndRevealNextDraft.feature
	 * @author jing han 260528152
	 * @param void
	 * @return void
	 */
	
	public static void sortCurrentDraft() {
		
		Kingdomino kingdomino = KingdominoApplication.getKingdomino();
		Game game = kingdomino.getCurrentGame();
		Draft currentDraft =game.getCurrentDraft();
		
		List<Domino> originalDominos=currentDraft.getIdSortedDominos();
		List<Integer> originalIds= new ArrayList<Integer>();
		for (Domino d:originalDominos) originalIds.add(d.getId());
		

		
		while (currentDraft.getIdSortedDominos().size()>0) {
			currentDraft.removeIdSortedDomino(currentDraft.getIdSortedDomino(0));
		}
		
		Collections.sort(originalIds);
		
		for (int id:originalIds) {
			for (Domino d:game.getAllDominos()) {
				if (d.getId()==id) {
					currentDraft.addIdSortedDomino(d);
				}
			}
		}

	}// OrderNextDraft
	
	
	/**
	 * 
	 * This method orders the dominoes in the next draft
	 * by ID number
	 * 
	 * @see  - OrderAndRevealNextDraft.feature
	 * @author Keon Olszewski 260927813
	 * @param void
	 * @return void
	 */
	
	public static void OrderNextDraft() {
		
	Kingdomino kingdomino = KingdominoApplication.getKingdomino();
	Game game = kingdomino.getCurrentGame();
	Draft nextDraft =	game.getNextDraft();
	List<Domino> nextDraftList = nextDraft.getIdSortedDominos();
	int domIDs[] = new int[nextDraftList.size()];
	
	for(int i = 0; i < nextDraftList.size(); i ++) {
		
		int domID = nextDraftList.get(i).getId();
		domIDs[i] = domID;
		
		}
	
	Arrays.sort(domIDs);
	
	for(int i = 0; i < domIDs.length; i ++) {
		nextDraft.addOrMoveIdSortedDominoAt(getdominoByID(domIDs[i]), i);
	}

	nextDraft.setDraftStatus(Draft.DraftStatus.Sorted);

	}// OrderNextDraft
	
	
	
	
	
	
//	public static void startANewGame() {
//		
//		Kingdomino kingdomino = KingdominoApplication.getKingdomino();
//		Game game = kingdomino.getCurrentGame();
//		int randomNum = ThreadLocalRandom.current().nextInt(0, game.numberOfPlayers());
//		List<Domino> dominoesInGame;
//		for (int i = 0; i < game.numberOfPlayers(); i++) {
//			if(i==0) {
//				game.getPlayer(i).setColor(Player.PlayerColor.Blue);
//			}
//			if(i==1) {
//				game.getPlayer(i).setColor(Player.PlayerColor.Green);
//			}
//			if(i==2) {
//				game.getPlayer(i).setColor(Player.PlayerColor.Pink);
//			}
//			if(i==3) {
//				game.getPlayer(i).setColor(Player.PlayerColor.Yellow);
//			}
//		}
//		game.setNextPlayer(game.getPlayer(randomNum));
//		createAllDominoes(game);
//		
//		if(game.getNumberOfPlayers()==2) {
//			
//			int randDomino = ThreadLocalRandom.current().nextInt(0, 24);
//			dominoesInGame = pickRandDomino(game.getAllDominos(), 24+1);
//			for(int i=0; i<= 24; i++) {
//				game.removeAllDomino(dominoesInGame.get(i));
//			}
////			game.setTopDominoInPile(dominoesInGame.get(randDomino));
//		}
//		if(game.getNumberOfPlayers()==3) {
//			
//			int randDomino = ThreadLocalRandom.current().nextInt(0, 36);
//			dominoesInGame = pickRandDomino(game.getAllDominos(), 12+1);
//			for(int i=0; i<= 12; i++) {
//				game.removeAllDomino(dominoesInGame.get(i));
//			}
////			game.setTopDominoInPile(dominoesInGame.get(randDomino));
//		}
//		if(game.getNumberOfPlayers()==4) {
//			
//			int randDomino = ThreadLocalRandom.current().nextInt(0, 36);
//			dominoesInGame = pickRandDomino(game.getAllDominos(), 48+1);
////			game.setTopDominoInPile(dominoesInGame.get(randDomino));
//		}
//		
//		List<Domino> dominosInGame = game.getAllDominos();
//		
//		for (int i=0;i<dominosInGame.size()-1;i++) {
//			dominosInGame.get(i).setNextDomino(dominosInGame.get(i+1));
//		}
//		
//		Domino firstDomino=dominosInGame.get(0);
//		game.setTopDominoInPile(firstDomino);
//		
//		createOneDraft();
//	}
	
	/**
	 * 
	 * This method retrieves all the possible dominos that are a part
	 * of the game and stores them into an array.
	 * 
	 * @see BrowseDominoPile.feature
	 * @author Gurdarshan Singh 260927466
	 * @param null
	 * @return myDominos
	 */
	
	public static Domino[] listDominos() {
		Domino[] myDominos = new Domino[48];
		for(int i = 1; i < 49 ; i++) {
			Domino dom = KDController.getdominoByID(i);
			myDominos[i-1] = dom;
			
		}
		return myDominos;
	}
	
	/**
	 * 
	 * This method looks at all the dominos and separates them according to the
	 * terrain type that is provided whether it's the right tile or the left tile that contains it.
	 * 
	 * @see BrowseDominoPile.feature
	 * @author Gurdarshan Singh 260927466
	 * @param s1
	 * @return myDominos
	 */
	public static ArrayList<Integer> filterbyTerrain (String s1) {
		
		ArrayList<Integer> myDominos = new ArrayList<Integer>();
		TerrainType t1 = KDController.retrieveTerrainType(s1);
		for (int i = 1; i < 49 ; i++) {
			Domino dom = KDController.getdominoByID(i);
			
			if ( dom.getLeftTile().toString().equalsIgnoreCase(t1.toString()) || dom.getRightTile().toString().equalsIgnoreCase(t1.toString()) ) {
				
				myDominos.add(dom.getId());
			}			
		}	
		

		return myDominos;
	}
	
	/**
	 * 
	 * This method sets up a game according to the number of players specified.
	 * 
	 * @see ShuffleDominos.feature
	 * @author Gurdarshan Singh 260927466
	 * @param number
	 * @return void
	 */
	
	public static void numOfPlayers(int number) {
		
		Kingdomino kingdomino = KingdominoApplication.getKingdomino();
		int DomAmount = 0;
		if(number == 4) {
			DomAmount = 48;
		} else if (number == 3) {
			DomAmount = 36;
		} else if (number == 2) {
			DomAmount = 24;
		}
		Game game = new Game(DomAmount, kingdomino);
		game.setNumberOfPlayers(number);
		kingdomino.setCurrentGame(game);
		// Populate game
		addDefaultUsersAndPlayers(game);
		createAllDominoes(game);
		game.setNextPlayer(game.getPlayer(0));
		System.out.println(game.getNumberOfPlayers());
	}
	
	/**
	 * 
	 * This method takes all the dominos and shuffles them in a random order in order to start the game.
	 * The shuffling is done via the Collections method of ArrayLists.
	 * 
	 * @see ShuffleDominos.feature
	 * @author Gurdarshan Singh 260927466
	 * @param null
	 * @return dominoArray
	 */
	
	public static Domino[] shuffleDominos() {

			int count = 0;
			Domino[] DominoArray = new Domino[48];
			
			for (int i = 1; i < DominoArray.length+1 ; i++) {
				Domino dom = KDController.getdominoByID(i);
				DominoArray[count] = dom;
				count++;
			}
			
			ArrayList<Domino> Dominos = new ArrayList<Domino>();
			for (int i = 0; i < DominoArray.length; i++) {
				Dominos.add(DominoArray[i]);
			}		
			Collections.shuffle(Dominos);
			
			for(int i=0; i<DominoArray.length; i++) {
				DominoArray[i] = (Domino) Dominos.get(i);
			}
			return DominoArray;
	}
	
	
	
	/**
	 * 
	 * This method arranges the domino IDs in the order that is provided in the string accordingly.
	 * 
	 * @see ShuffleDomino.feature
	 * @author Gurdarshan Singh 260927466
	 * @param s1
	 * @return DominoArray
	 */
	
	public static Integer[] arrangeDominos(String s1) {
		
		Integer[] DominoArray = new Integer[48];
		String[] StringArray = new String[48];
		StringArray = s1.split(", ");
		
		for(int i = 0; i < DominoArray.length; i++) {
			DominoArray[i] = (Integer.parseInt(StringArray[i]));			
		}
		return DominoArray;
	}
	
	
	/**
	 * 
	 * This method arranges the domino in the order that is provided in the string accordingly.
	 * 
	 * @see ShuffleDomino.feature
	 * @author Gurdarshan Singh 260927466
	 * @param s1
	 * @return DominoArray
	 */
	
	public static Domino[] arrangeTheDominos(String s1) {
		
		Domino[] DominoArray = new Domino[48];
		String[] StringArray = new String[48];
		StringArray = s1.split(", ");
		
		for(int i = 0; i < DominoArray.length; i++) {
			DominoArray[i] = KDController.getdominoByID(Integer.parseInt(StringArray[i]));
		}
		return DominoArray;
	}
	
	/**
	 * 
	 * This method removes the amount of dominos specified from the list.
	 * 
	 * @see ShuffleDomino.feature
	 * @author Gurdarshan Singh 260927466
	 * @param s1
	 * @param int1
	 * @return DominoArray
	 */
	
	public static Domino[] removeDraftDominos(String s1, int int1) {
		Domino[] DominoArray = new Domino[48];
		String[] StringArray = new String[48];
		StringArray = s1.split(", ");
		int count = 0;
		for(int i = int1; i < DominoArray.length; i++) {
			DominoArray[count] = KDController.getdominoByID(Integer.parseInt(StringArray[i]));
			count++;
		}
		
		return DominoArray;
		
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
		List<Integer> allRanks=new ArrayList<Integer>();
		
		Player[] rankedPlayers = new Player[allPlayers.size()];

		for (Player p:allPlayers) {
			allRanks.add(p.getCurrentRanking());
		}
		
		int[][] rankAndDuplicity=argsort(allRanks);
		int [] rank=rankAndDuplicity[0];
		
		int i=0;
		for (int j=rank.length-1;j>-1;j--) {
			rankedPlayers[i]=allPlayers.get(rank[j]);
			i++;
		}
		
//		System.out.println(Arrays.toString(rankedPlayers));
		
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
	
	
	public static boolean existScoreTieBreak() {
		
		boolean exist=true;
		
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		
		List<Player> allPlayers = game.getPlayers();
		List<Integer> playerScores = new ArrayList<Integer>();

		for (Player each:allPlayers) {
			playerScores.add(each.getTotalScore());
		}
		
		Set<Integer> uniqueScores = new HashSet<Integer>(playerScores);
		
//		System.out.println(uniqueScores);
//		System.out.println(playerScores);

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
	
	private static int[] playerMaxPropSizeAndNumCrown(Player player) {
		int size=-1;
		int crown=0;
		
		for (Property prop:player.getKingdom().getProperties()) {
			if (prop.getSize()>size) size=prop.getSize();
			crown+=prop.getCrowns();
		}
		
		int[] sizeAndCrown= {size,crown};
		return sizeAndCrown;
	}

	private static void comparePlayers(List<Player> players) {
		Collections.sort(players, new Comparator<Player>() {
			public int compare(Player p1,Player p2) {
				
				int score1=p1.getTotalScore();
				int score2=p2.getTotalScore();
				
				int[] sizeCrown1=playerMaxPropSizeAndNumCrown(p1);
				int[] sizeCrown2=playerMaxPropSizeAndNumCrown(p2);
				
				int size1=sizeCrown1[0];
				int crown1=sizeCrown1[1];
				
				int size2=sizeCrown2[0];
				int crown2=sizeCrown2[1];
				
				if (score1>score2) return 1;
				else if (score1<score2) return -1;
				else {
					if (size1>size2) return 1;
					else if (size1<size2) return -1;
					else {
						if (crown1>crown2) return 1;
						else if (crown1<crown2) return -1;
						else return 0;
					}
				}
			}
		});
	}
	
	private static boolean comparePlayers(Player p1,Player p2) {				
		int score1=p1.getTotalScore();
		int score2=p2.getTotalScore();
		
		int[] sizeCrown1=playerMaxPropSizeAndNumCrown(p1);
		int[] sizeCrown2=playerMaxPropSizeAndNumCrown(p2);
		
		int size1=sizeCrown1[0];
		int crown1=sizeCrown1[1];
		
		int size2=sizeCrown2[0];
		int crown2=sizeCrown2[1];
		
		if ((score1==score2)&&(crown1==crown2)&&(size1==size2)) return true;
		else return false;
				
	}
	
//	private class playerInfo{
//		private 
//	}
	
	public static void calculatePlayerRanking() {
		
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		
		List<Player> allPlayers = game.getPlayers();
		List<Player> playerCopy = new ArrayList<Player>();
		for (Player p:allPlayers) {
			playerCopy.add(p);
		}
		
		
		
		comparePlayers(playerCopy);
		Collections.reverse(playerCopy);
		
		int rank=1;
		
		for (int i=0;i<playerCopy.size()-1;i++) {
			Player nowPlayer=playerCopy.get(i);
			Player nextPlayer=playerCopy.get(i+1);
			
			int[] sizeCrown1=playerMaxPropSizeAndNumCrown(nowPlayer);
			int[] sizeCrown2=playerMaxPropSizeAndNumCrown(nextPlayer);
			
			int size1=sizeCrown1[0];
			int crown1=sizeCrown1[1];
			
			int size2=sizeCrown2[0];
			int crown2=sizeCrown2[1];
			
			nowPlayer.setCurrentRanking(rank);
			if ((nowPlayer.getTotalScore()==nextPlayer.getTotalScore())
				&&(size1==size2)&&(crown1==crown2)) {}
			else {
				rank++;
			}
		}
		playerCopy.get(playerCopy.size()-1).setCurrentRanking(rank);
		

		
		List<Integer> ignoreIndex = new ArrayList<Integer>();
		
		for (int i=0;i<playerCopy.size();i++) {
			for (int j=0;j<playerCopy.size();j++) {
				if (!ignoreIndex.contains(j)) {
					if (comparePlayers(playerCopy.get(i),allPlayers.get(j))) {
						allPlayers.get(j).setCurrentRanking(playerCopy.get(i).getCurrentRanking());
					}
				}
			}
		}
		
		for (Player p:game.getPlayers()) {
			int[] sizeCrown=playerMaxPropSizeAndNumCrown(p);
			int size=sizeCrown[0];
			int crown=sizeCrown[1];
			String info=String.format("%1$-10s  score: %2$-5d max size: %3$-5d total crown: %4$-5d rank: %5$-5d",
					p.getColor(),p.getTotalScore(),size,crown,p.getCurrentRanking());
			System.out.println(info);
		}
		
		
	}
	
//	private static rankBySize(List<Player> allPlayers) {
//		
//	}
	
//	public static void calculatePlayerRanking() {
//		
//		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
//		
//		List<Player> allPlayers = game.getPlayers();
//		List<Integer> playerScores = new ArrayList<Integer>();
//
//		
////		System.out.println(" =========== unranked ============");
//		for (Player p:allPlayers) {
//			playerScores.add(p.getTotalScore());
////			System.out.println(p.getColor()+" ---- score: "+p.getTotalScore()+" ---- rank: "+p.getCurrentRanking());
//		}
//		
//		rankByValue(allPlayers,playerScores);
//		
//		if (!existScoreTieBreak()) {
////			System.out.println(" ============ no tiebreak ranked =============");
//			Player[] p2 = getRankedPlayers();
//			for (Player p:p2) {
////				System.out.println(p.getColor()+" ---- score: "+p.getTotalScore()+" ---- rank: "+p.getCurrentRanking());
//			}
//		}
//		else {
//			tieBreakByLargestProperty();
//		}
//	}
	
	/**
	 * 
	 * This helper method applies ranking to a list of players
	 * based on an arbitrary value list
	 * 
	 * @see CalculateRanking.feature
	 * @author Gurdarshan Singh 260927466, refactored by Jing Han
	 * @param void
	 * @return void
	 */
	public static void rankByValue(List<Player> allPlayers,List<Integer> value) {
		int[][] orderAndDuplicity = argsort(value);
		int[] order=orderAndDuplicity[0];
		int[] duplicity=orderAndDuplicity[1];
		int rank=1;
		
		for (int i=0;i<order.length;i++) {
			int index=order[i];
			int dupe=duplicity[i];
			
			allPlayers.get(index).setCurrentRanking(rank);
			
			if (dupe==1) {
				rank++;
			}
		}
	}
	
	/**
	 * 
	 * This method resolves tie break by property size
	 * or moves on to resolve by crown if property size equal
	 * of the game according to their total
	 * scores
	 * 
	 * @see CalculateRanking.feature
	 * @author Gurdarshan Singh 260927466, refactored by Jing Han
	 * @param void
	 * @return void
	 */
	
	public static void tieBreakByLargestProperty() {
			
		Player[] rankedPlayers = KDController.getRankedPlayers();
		List<Integer> sameRankIndex= new ArrayList<Integer>();
		List<Player> sameRankPlayers=new ArrayList<Player>();
		
		for (int i=0;i<rankedPlayers.length-1;i++) {
			Player nowPlayer = rankedPlayers[i];
			Player nextPlayer=rankedPlayers[i+1];
			if (nowPlayer.getCurrentRanking()==nextPlayer.getCurrentRanking()) {
				if (!(sameRankIndex.contains((i)))) {
					sameRankPlayers.add(nowPlayer);
					sameRankIndex.add(i);
				}
				if (!(sameRankIndex.contains((i+1)))) {
					sameRankPlayers.add(nextPlayer);
					sameRankIndex.add(i+1);
				}
			}
		}

		
		List<Integer> sameRankPropSize = new ArrayList<Integer>();
		for (Player p:sameRankPlayers) {
			List<Property> tempProp=p.getKingdom().getProperties();
			int maxPropSize=0;
			for (Property tempP:tempProp) {
				if (tempP.getSize()>maxPropSize) maxPropSize=tempP.getSize();
			}
			sameRankPropSize.add(maxPropSize);
		}
			
		Set<Integer> set = new HashSet<Integer>(sameRankPropSize);
		
		if (set.size()!=sameRankPropSize.size()) {
			tieBreakByCrown();
		}
		else {
			
			for (int i=0;i<rankedPlayers.length;i++) {
				if (!sameRankIndex.contains(i)) {
					(rankedPlayers[i]).setCurrentRanking(rankedPlayers[i].getCurrentRanking()+1);
				}
			}
			
			rankByValue(sameRankPlayers,sameRankPropSize);
			
//			System.out.println("\n\n================= tie broken by size ====================");
//			Player[] tieBrokenPlayers=getRankedPlayers();
//			for (Player p:tieBrokenPlayers) {
//				System.out.println(p.getColor()+" ---- score: "+p.getTotalScore()+" ---- rank: "+p.getCurrentRanking());
//			}
		}
		
		
	}
	
	/**
	 * 
	 * This method resolves tie break by crown number
	 * or moves on shared victory if crown number equal
	 * of the game according to their total
	 * scores
	 * 
	 * @see CalculateRanking.feature
	 * @author Gurdarshan Singh 260927466, refactored by Jing Han
	 * @param void
	 * @return void
	 */
	
	public static void tieBreakByCrown() {
		Player[] rankedPlayers = KDController.getRankedPlayers();
		List<Integer> sameRankIndex= new ArrayList<Integer>();
		List<Player> sameRankPlayers=new ArrayList<Player>();
		
		for (int i=0;i<rankedPlayers.length-1;i++) {
			Player nowPlayer = rankedPlayers[i];
			Player nextPlayer=rankedPlayers[i+1];
			if (nowPlayer.getCurrentRanking()==nextPlayer.getCurrentRanking()) {
				if (!(sameRankIndex.contains((i)))) {
					sameRankPlayers.add(nowPlayer);
					sameRankIndex.add(i);
				}
				if (!(sameRankIndex.contains((i+1)))) {
					sameRankPlayers.add(nextPlayer);
					sameRankIndex.add(i+1);
				}
			}
		}
		
		
		List<Integer> sameRankCrownSize = new ArrayList<Integer>();
		for (Player p:sameRankPlayers) {
			int crown=0;
			List<Property> tempProperty=p.getKingdom().getProperties();
			for (Property prop:tempProperty) {
				crown+=prop.getCrowns();
			}
			sameRankCrownSize.add(crown);
		}
			
		Set<Integer> set = new HashSet<Integer>(sameRankCrownSize);
		
		if (set.size()!=sameRankCrownSize.size()) {
//			System.out.println("================= shared victory=====================");
//			Player[] tieBrokenPlayers=getRankedPlayers();
//			for (Player p:tieBrokenPlayers) {
//				System.out.println(p.getColor()+" ---- score: "+p.getTotalScore()+" ---- rank: "+p.getCurrentRanking());
//			}
		}

		else {
			
			for (int i=0;i<rankedPlayers.length;i++) {
				if (!sameRankIndex.contains(i)) {
					(rankedPlayers[i]).setCurrentRanking(rankedPlayers[i].getCurrentRanking()+1);
				}
			}
			
			rankByValue(sameRankPlayers,sameRankCrownSize);
			
//			System.out.println("\n\n================= tie broken by crown ====================");
//			Player[] tieBrokenPlayers=getRankedPlayers();
//			for (Player p:tieBrokenPlayers) {
//				System.out.println(p.getColor()+" ---- score: "+p.getTotalScore()+" ---- rank: "+p.getCurrentRanking());
//			}
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
	 * This method calculates the score of a Player's kingdom
	 * @see  - CalculatePlayerScore.feature
	 * @author Jing Han 260528152
	 * @param player
	 * @return void
	 */
	
	public static void calculatePlayerScore(Player player) {
		
		List<Property> p = player.getKingdom().getProperties();
		identifyAllProperty(player);
		int score=0;
		for (Property each:p) {
			score+=each.getScore();
		}
		
		calculateBonusScore(player);
		player.setPropertyScore(score);
		
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
	 * This method changes the status of dominos in a game
	 * 
	 * @see  - CreateNextDraft.feature
	 * @author Keon Olszewski 260927813
	 * @param player
	 * @return void
	 */
	
	public static void changeDraftDominoStatus(Draft draft, Domino.DominoStatus status) {
		
		if(!((status.equals(Domino.DominoStatus.InCurrentDraft))||(status.equals(Domino.DominoStatus.InNextDraft))||(status.equals(Domino.DominoStatus.Excluded)))) {
			throw new IllegalArgumentException("status must be either InCurrentDraft or InNextDraft or Excluded");
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
	 * @author Keon Olszewski 260927813
	 * @param player
	 * @return void
	 */
	
	public static Draft createOneDraft() {
		Kingdomino kd = KingdominoApplication.getKingdomino();
		Game game = kd.getCurrentGame();
		
		int numPlayer=kd.getCurrentGame().getNumberOfPlayers();
//		System.out.println("number of players: "+numPlayer);
		int dominoNum=-1;
		
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
	
	/**
	 * 
	 * This method gets Dominos with status InPile
	 * 
	 * @see  - CreateNextDraft.feature
	 * @author Keon Olszewski 260927813
	 * @param player
	 * @return availableDominos
	 */
	
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
	
	/**
	 * 
	 * This method calculates the square of the L2 dist btwn two coordinates
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
	 * This helper method performs the equivalent
	 * of argsort in Python
	 * 
	 * @author Jing Han 260528152
	 * @param x
	 * @return sortedIndices
	 */
	
	public static int[][] argsort(List<Integer> x) {
		List<Integer> sortOrder = new ArrayList<Integer>();
		List<Integer> xCopy = new ArrayList<Integer>();
		int [] dupe = new int[x.size()];
		
		for (Integer val:x) {
			xCopy.add(val);
		}
		
		Collections.sort(xCopy);
		Collections.reverse(xCopy);
		
		for (int val:xCopy) {
			for (int index=0;index<x.size();index++) {
				if (!(sortOrder.contains(index))) {
					int ref=x.get(index);
					if (ref==val) {
						sortOrder.add(index);
						break;
					}
				}
			}
		}
		
		
		for (int i=0;i<sortOrder.size()-1;i++) {
			int val1=x.get(sortOrder.get(i));
			int val2=x.get(sortOrder.get(i+1));
			if (val1==val2) {
				dupe[i]=0;
			}
			else {
				dupe[i]=1;
			}
		}
		
		int [] finalOrder=new int[sortOrder.size()];
		
		for(int j=0;j<sortOrder.size();j++) {
			finalOrder[j]=sortOrder.get(j);
		}
		
		int[][] orderAndDuplicity= {finalOrder,dupe};
			
		
		
		return orderAndDuplicity;
	}

	/**
	 * 
	 * This class the Exception class
	 * @author Anthony Harissi Dagher
	 * @param errorMessage
	 */
	
	public static class InvalidInputException extends Exception {
		
		private static final long serialVersionUID = -5633915762703837868L;
		
		public InvalidInputException(String errorMessage) {
			super(errorMessage);
		}
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
	
	
	///////////////////////////////////////
	/// -----Private Helper Methods---- ///
	///////////////////////////////////////
	
	/**
	 * 
	 * This method gets gets a user by Name
	 * 
	 * @param userName
	 * @return userList
	 */
	
	public static User getUserByName(String username) {
		Kingdomino kingdomino = KingdominoApplication.getKingdomino();
		List<User> userList = kingdomino.getUsers();
		
		for(int i = 0; i< userList.size(); i++) {
			
			if(userList.get(i).getName().equals(username)) return userList.get(i);
		}
		
		return null;
	}
	

	
	/**
	 * 
	 * This method picks a random method
	 * @author Anthony Harissi Dagher
	 * @param lst
	 * @param n
	 * @return List<Domino>
	 */
	
	public static List<Domino> pickRandDomino(List<Domino> lst, int n) {
	    List<Domino> copy = new LinkedList<Domino>(lst);
	    Collections.shuffle(copy);
	    return copy.subList(0, n);
	}
	
	/**
	 * 
	 * This method initializes a game, specifically for 
	 * the StartNewGame.feature
	 * @author Anthony Harissi Dagher
	 * @param void
	 * @return void
	 */
	
	public static void initializeGame() {
		// Initialize empty game
		Kingdomino kingdomino = new Kingdomino();
		Game game = new Game(48, kingdomino);
		game.setNumberOfPlayers(4);
		kingdomino.setCurrentGame(game);
		// Populate game
		addDefaultUsersAndPlayers(game);
		createAllDominoes(game);
		game.setNextPlayer(game.getPlayer(0));
		KingdominoApplication.setKingdomino(kingdomino);
	}
	
	
	private static List<Integer> uniqueRandomSequence(int size,int min,int max){
		List<Integer> sequence = new ArrayList<Integer>();
		Random rand = new Random();
		
		while (sequence.size()<size){
			int num=rand.nextInt((max - min) + 1) + min;
			if (!sequence.contains(num)) {
				sequence.add(num);
			}
		}
		return sequence;
	}
	
	/**
	 * 
	 * This method returns the largest and smallest possible
	 * x and y values that a domino can be within in order
	 * to be considered for a valid position.
	 * 
	 * @author Massimo Vadacchino
	 * @param player
	 * @return int[]
	 */

	private static int[] getAvailableSpaceInGrid(Player player) {
		
		List<KingdomTerritory> territories = player.getKingdom().getTerritories(); 
	
		int largestXCoord = territories.get(0).getX();
		int largestYCoord = territories.get(0).getY();

		int smallestXCoord = territories.get(0).getX();
		int smallestYCoord = territories.get(0).getY();

		for (KingdomTerritory territory : territories) {
			
			if(territories.get(0).equals(territory)) continue;

			DominoInKingdom dInKingdom = (DominoInKingdom) territory;

			if(dInKingdom.getDirection().equals(DirectionKind.Up)) {
				
				if(territory.getY() + 1 > largestYCoord) largestYCoord = territory.getY() + 1;

				if(territory.getY() < smallestYCoord) smallestYCoord = territory.getY();

				if(territory.getX() > largestXCoord) largestXCoord = territory.getX();

				if(territory.getX() < smallestXCoord) smallestXCoord = territory.getX();

			}

			if(dInKingdom.getDirection().equals(DirectionKind.Down)) {

				if(territory.getY() - 1 < smallestYCoord) smallestYCoord = territory.getY() - 1;

				if(territory.getY() > largestYCoord) largestYCoord = territory.getY();

				if(territory.getX() > largestXCoord) largestXCoord = territory.getX();

				if(territory.getX() < smallestXCoord) smallestXCoord = territory.getX();


			}

			if(dInKingdom.getDirection().equals(DirectionKind.Right)) {

				if(territory.getX() + 1 > largestXCoord) largestXCoord = territory.getX() + 1;

				if(territory.getX() < smallestXCoord) smallestXCoord = territory.getX();

				if(territory.getY() > largestYCoord) largestYCoord = territory.getY();

				if(territory.getY() < smallestYCoord) smallestYCoord = territory.getY();

			}

			if(dInKingdom.getDirection().equals(DirectionKind.Left)) {

				if(territory.getX() - 1 < smallestXCoord) smallestXCoord = territory.getX() - 1;

				if(territory.getX() > largestXCoord) largestXCoord = territory.getX();

				if(territory.getY() > largestYCoord) largestYCoord = territory.getY();

				if(territory.getY() < smallestYCoord) smallestYCoord = territory.getY();
				
			}

		}
		
		int[] availableSpaceInGrid = new int[4];
	
		availableSpaceInGrid[0] = smallestXCoord - 2;
		availableSpaceInGrid[1] = smallestYCoord - 2;
		availableSpaceInGrid[2] = largestXCoord + 2;
		availableSpaceInGrid[3] = largestYCoord + 2;

		return availableSpaceInGrid;
		
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
	
	private static Neighborhood getDominoLeftNeighbors(List<KingdomTerritory> t, DominoInKingdom dInK) {
		
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

	private static Neighborhood getDominoRightNeighbors(List<KingdomTerritory> t, DominoInKingdom dInK) {
			
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
				if (((DominoInKingdom) each).getDomino().getStatus().equals(DominoStatus.PlacedInKingdom)) {
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
	
	/**
	 * 
	 * This method returns a terrain type depending on the input string.
	 * 
	 * @see BrowseDominoPile.feature
	 * @author Gurdarshan Singh 260927466
	 * @param String
	 * @return TerrainType
	 */
	
	public static TerrainType retrieveTerrainType(String terrain) {
		switch (terrain) {
		case "wheat":
			return TerrainType.WheatField;
		case "forest":
			return TerrainType.Forest;
		case "mountain":
			return TerrainType.Mountain;
		case "grass":
			return TerrainType.Grass;
		case "swamp":
			return TerrainType.Swamp;
		case "lake":
			return TerrainType.Lake;
		default:
			throw new java.lang.IllegalArgumentException("Invalid terrain type: " + terrain);
		}
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
	
	private static void createShuffledDominoPile(Game game, int pileSize) {
		class almostDomino{
			private Integer id;
			private TerrainType leftTerrain;
			private TerrainType rightTerrain;
			private Integer numCrown;
			
			private almostDomino(Integer id,TerrainType leftTerrain,TerrainType rightTerrain,Integer numCrown) {
				this.id=id;
				this.leftTerrain=leftTerrain;
				this.rightTerrain=rightTerrain;
				this.numCrown=numCrown;
			}
			public Integer getId() {return this.id;}
			public TerrainType getLeftTerrain() {return this.leftTerrain;}
			public TerrainType getRightTerrain() {return this.rightTerrain;}
			public Integer getNumCrown() {return this.numCrown;}
		}
		
		List<Integer> randomIds = uniqueRandomSequence(pileSize,1,48);
		List<almostDomino> potentialDominos = new ArrayList<almostDomino>();
				
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
				
				if (randomIds.contains(dominoId)) {
					almostDomino aD = new almostDomino(dominoId,leftTerrain,rightTerrain,numCrown);
					potentialDominos.add(aD);
				}
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw new java.lang.IllegalArgumentException(
					"Error occured while trying to read alldominoes.dat: " + e.getMessage());
		}
		
		Collections.shuffle(potentialDominos);
		
		for (almostDomino ad:potentialDominos) {
			new Domino(ad.getId(), ad.getLeftTerrain(), ad.getRightTerrain(), ad.getNumCrown(), game);
		}
		
		List<Domino> dominoInGame = game.getAllDominos();
		for (int i=0;i<dominoInGame.size()-1;i++) {
			dominoInGame.get(i).setNextDomino(dominoInGame.get(i+1));
		}
		
		game.setTopDominoInPile(game.getAllDomino(0));

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

}
