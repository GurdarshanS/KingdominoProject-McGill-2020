package ca.mcgill.ecse223.kingdomino.controller;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.model.*;
import ca.mcgill.ecse223.kingdomino.model.Domino.DominoStatus;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom.DirectionKind;
import ca.mcgill.ecse223.kingdomino.model.Draft.DraftStatus;
import ca.mcgill.ecse223.kingdomino.model.Player.PlayerColor;
import ca.mcgill.ecse223.kingdomino.persistence.KDPersistence;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.common.collect.Sets;

public class KDController {
	
	public KDController(){}
	
	/**
	 * 
	 * wrapper method for initializing a state machine on
	 * 
	 * @author Jing Han 260528152
	 * @param none
	 * @return void
	 * 
	 */
	
	public static void initializeSM() {
		KingdominoApplication.setStatemachine();
		Gameplay sm = KingdominoApplication.getStateMachine();
		sm.setGamestatus("CreatingFirstDraft");
	}
	
	/**
	 * 
	 * wrapper method for triggering the 
	 * rotate transition of state machine (SM)
	 * 
	 * @author Jing Han 260528152
	 * @param none
	 * @return boolean
	 * 
	 */
	
	public static boolean rotateSM(String dir) {
		Gameplay sm = KingdominoApplication.getStateMachine();
		return sm.rotate(dir);
	}
	
	/**
	 * 
	 * wrapper method for triggering the 
	 * move transition of state machine (SM)
	 * 
	 * @author Massimo Vadacchino 260928064
	 * @param none
	 * @return boolean
	 * 
	 */
	
	public static boolean moveSM(String movement) {
		Gameplay sm = KingdominoApplication.getStateMachine();
		return sm.move(movement);
	}
	
	/**
	 * 
	 * wrapper method for triggering the 
	 * place transition of state machine (SM)
	 * 
	 * @author Jing Han 260528152
	 * @param none
	 * @return boolean
	 * 
	 */
	
	public static boolean placeSM() {
		Gameplay sm = KingdominoApplication.getStateMachine();
		return sm.place();
	}
	
	/**
	 * 
	 * wrapper method for triggering the 
	 * discard transition of SM
	 * 
	 * @author Massimo Vadacchino 260928064
	 * @param none
	 * @return boolean
	 * 
	 */
	
	public static boolean discardSM() {
		Gameplay sm = KingdominoApplication.getStateMachine();
		return sm.discard();
	}
	
	/**
	 * 
	 * wrapper method for triggering the 
	 * scoring transition of SM
	 * 
	 * @author Massimo Vadacchino 260928064
	 * @param none
	 * @return boolean
	 * 
	 */
	
	public static boolean scoringSM() {
		Gameplay sm = KingdominoApplication.getStateMachine();
		return sm.scoring();
	}
	
	/**
	 * 
	 * wrapper method for triggering the 
	 * manipulateFirst transition of SM
	 * 
	 * @author Jing	Han 260528152
	 * @param posx
	 * @param posy
	 * @param dir
	 * @return boolean
	 * 
	 */
	
	public static boolean manipulateFirstSM(int posx, int posy, String dir) {
		Gameplay sm = KingdominoApplication.getStateMachine();
		return sm.manipulateFirst(posx, posy, dir);
	}
	
	/**
	 * 
	 * wrapper method for triggering the 
	 * manipulateNext transition of SM
	 * 
	 * @author Eric Guan 260930210
	 * @param posx
	 * @param posy
	 * @param dir
	 * @return boolean
	 * 
	 */
	
	public static boolean manipulateNextSM(int posx, int posy, String dir) {
		Gameplay sm = KingdominoApplication.getStateMachine();
		return sm.manipulateNext(posx, posy, dir);
	}
	
	/**
	 * 
	 * wrapper method for triggering the 
	 * manipulateLast transition of SM
	 * 
	 * @author Eric Guan 260930210
	 * @param posx
	 * @param posy
	 * @param dir
	 * @return boolean
	 * 
	 */
	
	public static boolean manipulateLastSM(int posx, int posy, String dir) {
		Gameplay sm = KingdominoApplication.getStateMachine();
		return sm.manipulateLast(posx, posy, dir);
	}
	
	
	/**
	 * 
	 * wrapper method for triggering the 
	 * draftReady transition of SM
	 * 
	 * @author Anthony Harissi Dagher 260924250
	 * @param none
	 * @return boolean
	 * 
	 */
		
	public static boolean draftReadySM() {
		Gameplay sm = KingdominoApplication.getStateMachine();
		boolean complete=sm.draftReady();
		return complete;
	}
	
	/**
	 * 
	 * wrapper method for triggering the 
	 * choose transition of SM
	 * 
	 * @author Anthony Harissi Dagher 260924250
	 * @param domino
	 * @return boolean
	 * 
	 */
	public static boolean chooseSM(Domino domino) {
		Gameplay sm = KingdominoApplication.getStateMachine();
		return sm.choose(domino);
	}
	
	/**
	 * 
	 * wrapper method for triggering the 
	 * selectionReady transition of SM
	 * 
	 * @author Keon Olszewski 260927813
	 * @param none
	 * @return boolean
	 * 
	 */
	
	public static boolean selectionReadySM() {
		Gameplay sm = KingdominoApplication.getStateMachine();
		return sm.selectionReady();
	}
	
	/**
	 * 
	 * wrapper method for triggering the 
	 * nextSelectionReady transition of SM
	 * 
	 * @author Keon Olszewski 260927813
	 * @param none
	 * @return boolean
	 * 
	 */
	public static boolean nextSelectionReadySM() {
		Gameplay sm = KingdominoApplication.getStateMachine();
		return sm.nextSelectionReady();
	}
	
	/**
	 * 
	 * wrapper method for triggering the 
	 * lastSelectionReady transition of SM
	 * 
	 * @author Gurdarshan Singh 260927466
	 * @param none
	 * @return boolean
	 * 
	 */
	public static boolean lastSelectionReadySM(int posx, int posy, String dir) {
		Gameplay sm = KingdominoApplication.getStateMachine();
		return sm.lastSelectionReady(posx, posy, dir);
	}
	
	/**
	 * 
	 * loads a saved Kingdomino object from memory if exists
	 * otherwise creates a new Kingdomino object and saves it
	 * to memory for loading the next time it is called
	 * 
	 * 
	 * @see  - LoadGame.feature
	 * @author Anthony Harissi Dagher 260924250
	 * @param none
	 * @return kd
	 * 
	 */
	
	public static Kingdomino loadGame(String newFilename) {
		if (newFilename!=null) {
			KDPersistence.setFilename(newFilename);
		}
		Kingdomino kd = KDPersistence.load();
		if (kd==null) kd = new Kingdomino();		
		KingdominoApplication.setKingdomino(kd);
		KDPersistence.save(kd,true);
		return kd;
	}
	
	/**
	 * 
	 * saves the current Kingdomino object to memory
	 * by default overwrites existing object in memory
	 * if present
	 * 
	 * 
	 * @see  - SaveGame.feature
	 * @author Anthony Harissi Dagher 260924250
	 * @param none
	 * @return boolean
	 * 
	 */
	
	public static boolean saveGame(String newFilename,boolean overwrite) {
		Kingdomino kd = KingdominoApplication.getKingdomino();
		try{
			if (newFilename!=null) {
				KDPersistence.setFilename(newFilename);
			}
			KDPersistence.save(kd,overwrite);
			return true;
		}
		catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
	/**
	 * 
	 * This method initiates an empty game.
	 * Useful for bring games up to a testable state
	 * should NOT be used in actual game
	 * 
	 * @see - no features associated
	 * @author Jing Han 260528152
	 * @param void
	 * @return void 
	 */

	public static void initiateEmptyGame() {
		Kingdomino kingdomino = KingdominoApplication.getKingdomino();
		Game game = new Game(48, kingdomino);
		game.setNumberOfPlayers(4);
		kingdomino.setCurrentGame(game);
		addDefaultUsersAndPlayers(game);
		createAllDominoes(game);
		game.setNextPlayer(game.getPlayer(0));
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
		
		if(!username.matches("[a-zA-Z0-9]+")) throw new IllegalArgumentException("must Contain only letters or Numbers");
		kingdomino.addUser(username);
	}
	
	/**
	 * 
	 * This method assigns a Player in the Current game to a User in kingdomino
	 * 
	 * @see  - ProvideUserProfile.feature
	 * @author Jing Han	260528152
	 * @param username
	 * @return boolean
	 * @throws java.lang.IllegalArgumentException
	 */
	
	public static boolean assignPlayerToUser(Player player, User user) throws IllegalArgumentException {
		
		Kingdomino kd = KingdominoApplication.getKingdomino();
		
		Set<Player> playersInThisGame=new HashSet<Player> (kd.getCurrentGame().getPlayers());
		Set<Player> playersOfThisUser = new HashSet<Player> (user.getPlayerInGames());
		Set<Player> playersOfThisUserInThisGame=Sets.intersection(playersInThisGame, playersOfThisUser);
		
		if (!playersOfThisUserInThisGame.isEmpty()) {
			String assignedColors="";
			for (Player p:playersOfThisUserInThisGame) {
				assignedColors+=(" "+p.getColor().toString());
			}
			throw new IllegalArgumentException("user "+user.getName()+" is already assigned to player"+assignedColors+" in this game");
		}
		
		if (!player.hasUser()) {
			player.setUser(user);
			return true;
		}
		else {
			throw new IllegalArgumentException("player "+player.getColor().toString()+" is already assigned to user "+user.getName()+" in this game");
		}
	}

	
	/**
	 * 
	 * sets the game options for current game
	 * 
	 * 
	 * @see  - SetGameOptions.feature
	 * @author Anthony Harissi Dagher 260924250
	 * @param numPlayers
	 * @param selectedBonusOptions
	 * @return none
	 * @throws java.lang.InvalidInputException
	 * 
	 */
	public static void setGameOptions(int numPlayers, List<String> selectedBonusOptions)throws InvalidInputException{
		
		Kingdomino kingdomino = KingdominoApplication.getKingdomino();
		if(numPlayers <= 1 || numPlayers > 4) {
			throw new InvalidInputException("Must between 2 and 4 players for a game.");
		}
		if(numPlayers == 2) {
			Game game = new Game(24, kingdomino);
			game.setNumberOfPlayers(2);
			for(String option:selectedBonusOptions) {
				kingdomino.addBonusOption(option);
			}
			kingdomino.setCurrentGame(game);
		}
		else if(numPlayers == 3) {
			Game game = new Game(36, kingdomino);
			game.setNumberOfPlayers(3);
			for(String option:selectedBonusOptions) {
				kingdomino.addBonusOption(option);
				}
			kingdomino.setCurrentGame(game);

		}
		else{
			Game game = new Game(48, kingdomino);
			game.setNumberOfPlayers(4);
			for(String option:selectedBonusOptions) {
				kingdomino.addBonusOption(option);			
				}
			kingdomino.setCurrentGame(game);
		}
		
		
	} 
	
	/**
	 * 
	 * creates the full domino pile from the given
	 * .dat file. arranges in ascending order
	 * 
	 * 
	 * @see  - BrowseDominoPile.feature
	 * @author Gurdarshan Singh 260927466
	 * @param game
	 * @param pileSize
	 * @return none
	 * @throws java.lang.IllegalArgumentException
	 * 
	 */
	
	public static void createDominoPile(Game game, int pileSize) {
		
		List<Integer> randomIds = uniqueRandomSequence(pileSize,1,48);				
		try {
			BufferedReader br = new BufferedReader(new FileReader("src/main/resources/alldominoes.dat"));
			String line = "";
			String delimiters = "[:\\+()]";
			while ((line = br.readLine()) != null) {
				String[] dominoString = line.split(delimiters); 
				int dominoId = Integer.decode(dominoString[0]);
				TerrainType leftTerrain = getTerrainType(dominoString[1]);
				TerrainType rightTerrain = getTerrainType(dominoString[2]);
				int numCrown = 0;
				if (dominoString.length > 3) {
					numCrown = Integer.decode(dominoString[3]);
				}
				
				if (randomIds.contains(dominoId)) {
					new Domino (dominoId,leftTerrain,rightTerrain,numCrown,game);
				}
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw new java.lang.IllegalArgumentException(
					"Error occured while trying to read alldominoes.dat: " + e.getMessage());
		}
		
		for (int i=0;i<game.getAllDominos().size()-1;i++) {
			game.getAllDomino(i).setNextDomino(game.getAllDomino(i+1));
		}
		
		game.setTopDominoInPile(game.getAllDomino(0));
		
	}
	
	/**
	 * 
	 * shuffles the existing domino pile from 
	 * by arranging the dominos in a random order
	 * 
	 * 
	 * @see  - ShuffleDominoPile.feature
	 * @author Gurdarshan Singh 260927466
	 * @param none
	 * @return none
	 * 
	 */
	
	public static void shuffleDominoPile() {
		Kingdomino kd = KingdominoApplication.getKingdomino();
		Game game = kd.getCurrentGame();
		int dominoNums=game.getMaxPileSize();
		
		List<Integer> shuffleIndex=KDController.uniqueRandomSequence(dominoNums, 0, dominoNums-1);
		
		for (int i=0;i<shuffleIndex.size();i++) {
			game.addOrMoveAllDominoAt(game.getAllDomino(i), shuffleIndex.get(i));
		}
		
		for (int i=0;i<game.getAllDominos().size()-1;i++) {
			game.getAllDomino(i).setNextDomino(game.getAllDomino(i+1));
		}
		
		game.setTopDominoInPile(game.getAllDomino(0));
	}
	
	/**
	 * 
	 *  creates the 4 players and arrange them
	 *  in a default order. actual play order
	 *  to be determined by the generateInitialPlayerOrder
	 *  method
	 * 
	 * 
	 * @see  - StartANewGame.feature
	 * @author Anthony Harissi Dagher 260924250
	 * @param none
	 * @return none
	 * 
	 */
	public static void createPlayers() {
		Kingdomino kd = KingdominoApplication.getKingdomino();
		Game game = kd.getCurrentGame();
		
		int playerNums=game.getNumberOfPlayers();
		PlayerColor[] availableColors= {PlayerColor.Blue,PlayerColor.Green,PlayerColor.Pink,PlayerColor.Yellow};
		
		for (int i=0;i<playerNums;i++) {
			Player p = new Player(game);
			p.setColor(availableColors[i]);
			Kingdom kingdom = new Kingdom(p);
			new Castle(0, 0, kingdom, p);
			if (i==0) game.setNextPlayer(p);
		}
	}
	
	/**
	 * 
	 * randomly assigns playing order to the 
	 * default generated players
	 * 
	 * 
	 * @see  - InitializingGame.feature
	 * @author Anthony Harissi Dagher 260924250
	 * @param none
	 * @return none
	 * 
	 */
	
	public static void generateInitialPlayerOrder() {
		Kingdomino kd = KingdominoApplication.getKingdomino();
		Game game = kd.getCurrentGame();
		
		int playerNums=game.getNumberOfPlayers();
		List<Integer> playerOrder = uniqueRandomSequence(playerNums,0,playerNums-1);
		List<Player> tmpPlayers=new ArrayList<Player>();
		for (int i:playerOrder) tmpPlayers.add(game.getPlayer(i));
		
		for (int i=0;i<tmpPlayers.size();i++) {
			boolean moved=game.addOrMovePlayerAt(tmpPlayers.get(i),i);
			
		}
		
		game.setNextPlayer(game.getPlayer(0));
		
	}
	
	/**
	 * 
	 * updates player order based on their previous
	 * domino selections
	 * 
	 * 
	 * @see  - CreateNextDraft.feature
	 * @author Jing Han 260528152
	 * @param none
	 * @return none
	 * 
	 */
	
	public static void updatePlayerOrder() {
		Kingdomino kd = KingdominoApplication.getKingdomino();
		HashMap<Integer,Player> idPlayerPair = new HashMap<Integer,Player>();
		List<Integer> ids = new ArrayList<Integer>();
		
		for (Player p:kd.getCurrentGame().getPlayers()) {
			int key = p.getDominoSelection().getDomino().getId();
			idPlayerPair.put(key, p);
			ids.add(key);
		}
		
		Collections.sort(ids);
		
		for (int i=0;i<ids.size();i++) {
			int index=i;
			int key=ids.get(i);
			Player p = idPlayerPair.get(key);
			kd.getCurrentGame().addOrMovePlayerAt(p, index);
		}
		
		kd.getCurrentGame().setNextPlayer(kd.getCurrentGame().getPlayer(0));
	}
	
	
	
	/**
	 * 
	 * starts the current game by drawing the number of dominos required
	 * for the number of players, shuffles the dominos, and creates the
	 * initial current draft, which is sorted and facing up, and the initial
	 * next draft, which is facing down and unsorted
	 * 
	 * 
	 * @see  - LoadGame.feature
	 * @author Anthony Harissi Dagher 260924250
	 * @param none
	 * @return none
	 * 
	 */
	public static void startANewGame() {
		
		Kingdomino kd = KingdominoApplication.getKingdomino();
		KDController.createPlayers();
		
		Game game = kd.getCurrentGame();
		int dominoNums=game.getMaxPileSize();
		createDominoPile(game,dominoNums);
		generateInitialPlayerOrder();
	}
	
	/**
	 * 
	 * This method lets a user browse domino pile before game starts
	 * displays previously shuffled dominos in ascending ranking,
	 * does not change underlying list
	 * 
	 * @see  - ProvideUserProfile.feature
	 * @author Gurdarshan Singh 260927466
	 * @param none
	 * @return sortedDominos
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
	 * @param none
	 * @return void
	 */

	
	public static void createNextDraft() {
		Kingdomino kd = KingdominoApplication.getKingdomino();
		Game game = kd.getCurrentGame();		
		
		if (game.getAllDrafts().size()==0) {
			Draft currentDraft=createOneDraft();
			currentDraft.setDraftStatus(DraftStatus.FaceDown);
			game.setCurrentDraft(currentDraft);
			changeDraftDominoStatus(currentDraft,Domino.DominoStatus.InCurrentDraft);

			Draft nextDraft = createOneDraft();
			nextDraft.setDraftStatus(Draft.DraftStatus.FaceDown);
			game.setNextDraft(nextDraft);
			changeDraftDominoStatus(nextDraft,Domino.DominoStatus.InNextDraft);	

		}
		else if (!KDQuery.isDraftLimitReached()) {
			
			changeDraftDominoStatus(game.getNextDraft(),DominoStatus.InCurrentDraft);
			game.setCurrentDraft(game.getNextDraft());
			
			Draft nextDraft=createOneDraft();
			changeDraftDominoStatus(nextDraft,DominoStatus.InNextDraft);
			game.setNextDraft(nextDraft);
			game.getNextDraft().setDraftStatus(Draft.DraftStatus.FaceDown);

		}
		else if (KDQuery.isDraftLimitReached()) {
			if (game.getNextDraft()!=null) {
				changeDraftDominoStatus(game.getNextDraft(),DominoStatus.InCurrentDraft);

				game.setCurrentDraft(game.getNextDraft());
				game.setNextDraft(null);
			}
		}
		
	}
	
	
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
	
	public static void sortNextDraft() {
		
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
		
		currentDraft.setDraftStatus(DraftStatus.Sorted);
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
	
	public static void revealNextDraft() {
		
		Kingdomino kingdomino = KingdominoApplication.getKingdomino();
		Game game = kingdomino.getCurrentGame();
		Draft currentDraft =game.getCurrentDraft();
		currentDraft.setDraftStatus(DraftStatus.FaceUp);
	}
	

	
	/**
	 * 
	 * This method lets the next player of current game to choose a domino
	 * from the sorted current Draft
	 * 
	 * 
	 * @see  - ChooseNextDomino.feature
	 * @author Keon Olszewski 260927813
	 * @param aDomino
	 * @return void
	 */
	
	public static void chooseNextDomino(Domino aDomino){
		
		Kingdomino kingdomino = KingdominoApplication.getKingdomino();
		Game game = kingdomino.getCurrentGame();
		Draft currentDraft = game.getCurrentDraft();
		Player currentPlayer = game.getNextPlayer();
		
		if (!currentDraft.getIdSortedDominos().contains(aDomino) || aDomino.hasDominoSelection()) {
			return;
		}
		
		aDomino.setStatus(DominoStatus.Excluded);
		@SuppressWarnings("unused")
		DominoSelection currentSelection = new DominoSelection(currentPlayer,aDomino,currentDraft);
		
		updateNextPlayer(currentPlayer);
     
	}
	
	/**
	 * 
	 * This method lets updates the next 'next' player of the current game
	 * to the player positioned 1 index after the current 'next' player in
	 * the game.getPlayers() list.
	 * in the case of the current 'next' player being the last in list,
	 * sets the next 'next' to the first player in the list
	 * 
	 * 
	 * @see  - SelectingDomino.feature
	 * @author Jing Han 260528152
	 * @param currentPlayer
	 * @return void
	 */
	
	public static void updateNextPlayer(Player currentPlayer) {
		Game game=currentPlayer.getGame();
		if (!KDQuery.isCurrentPlayerTheLastInTurn(currentPlayer)) {
			
			List<Player.PlayerColor> currentColorOrder = new ArrayList<Player.PlayerColor>();			
			for (Player p:game.getPlayers()) {
				currentColorOrder.add(p.getColor());
			}
			int currentPlayerIndex=currentColorOrder.indexOf(currentPlayer.getColor());
			game.setNextPlayer(game.getPlayers().get(currentPlayerIndex+1));
		}
		else {
			game.setNextPlayer(game.getPlayer(0));
		}
	}
	

	
	/**
	 * 
	 * This method allows a player to preplace an arbitrary domino
	 * on the gameboard and set its orientation. useful for testing.
	 * NOT used for actual game.
	 * verification checks are performed on grid size, 
	 * overlapping, castle adjacency, and neighbor adjacency.
	 * Corresponding DominoStatus of CorrectlyPreplaced or
	 * ErroneouslyPreplaced are set accordingly. 
	 * 
	 * 
	 * @author Jing Han 260528152
	 * @param player
	 * @param posx
	 * @param posy
	 * @param dir
	 * @return dInK
	 * 
	 */
	
	public static DominoInKingdom preplaceArbitraryDomino(Player player, Domino dominoToPlace, int posx, int posy, String dir) {
		Kingdom kingdom=player.getKingdom();
		
		DominoInKingdom dInK = new DominoInKingdom(posx,posy,kingdom,dominoToPlace);
		dInK.setDirection(getDirection(dir));
		
		boolean valid=KDQuery.verifyDominoInKingdom(player,dInK);
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
	 * This method allows a player to preplace the domino in his/her 
	 * latest selection to a position on the gameboard and set its orientation.
	 * verification checks are performed on grid size, 
	 * overlapping, castle adjacency, and neighbor adjacency.
	 * Corresponding DominoStatus of CorrectlyPreplaced or
	 * ErroneouslyPreplaced are set accordingly. 
	 * 
	 * 
	 * @author Jing Han 260528152
	 * @param player
	 * @param posx
	 * @param posy
	 * @param dir
	 * @return dInK
	 * 
	 */
	
	public static DominoInKingdom preplaceLatestDomino(Player player, int posx, int posy, String dir) {
		Domino dominoToPlace=player.getDominoSelection().getDomino();
		Kingdom kingdom=player.getKingdom();
		
		DominoInKingdom dInK = new DominoInKingdom(posx,posy,kingdom,dominoToPlace);
		dInK.setDirection(getDirection(dir));
		
		boolean valid=KDQuery.verifyDominoInKingdom(player,dInK);
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
	 * @param movement
	 * @return void
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
	
		DominoStatus prevStatus=dInKingdom.getDomino().getStatus();

		if(movement.equalsIgnoreCase("Right")) dInKingdom.setX(xPosPrevious + 1);
		else if(movement.equalsIgnoreCase("Left")) dInKingdom.setX(xPosPrevious - 1);
		else if(movement.equalsIgnoreCase("Up")) dInKingdom.setY(yPosPrevious + 1);
		else if(movement.equalsIgnoreCase("Down")) dInKingdom.setY(yPosPrevious - 1);
		
		if(!verifyGridLimit(dInKingdom)) {
	
			dInKingdom.setX(xPosPrevious);
			dInKingdom.setY(yPosPrevious);
			dInKingdom.getDomino().setStatus(prevStatus);

			return;
			
		}
		else {
			
			boolean valid=KDQuery.verifyDominoInKingdom(aPlayer,dInKingdom);
			
			if (valid) {
				dInKingdom.getDomino().setStatus(DominoStatus.CorrectlyPreplaced);
			}
			else {
				dInKingdom.getDomino().setStatus(DominoStatus.ErroneouslyPreplaced);
			}
			
		}
		
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
	 * @param rotation
	 * @return void
	 * 
	 */
	
	public static void rotateLatestDomino(Player aPlayer, String rotation) { 

		List<KingdomTerritory> territories = aPlayer.getKingdom().getTerritories();
		if (territories.size()==1) {
			return;
		}
		
		DominoInKingdom dInKingdom = (DominoInKingdom) territories.get(territories.size()-1);
		
		int xPosPrevious = dInKingdom.getX();
		int yPosPrevious = dInKingdom.getY();
		DominoStatus prevStatus=dInKingdom.getDomino().getStatus();

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
			dInKingdom.setX(xPosPrevious);
			dInKingdom.setY(yPosPrevious);
			dInKingdom.getDomino().setStatus(prevStatus);
			dInKingdom.setDirection(dominoDir);
			return;
			
		}
		
		else {
			
			boolean valid=KDQuery.verifyDominoInKingdom(aPlayer,dInKingdom);
			
			if (valid) {
				dInKingdom.getDomino().setStatus(DominoStatus.CorrectlyPreplaced);
			}
			else {
				dInKingdom.getDomino().setStatus(DominoStatus.ErroneouslyPreplaced);
			}
			
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
	 * 
	 * @see PlaceDomino.feature
	 * @author Massimo Vadacchino 260928064
	 * @param aPlayer
	 * @return boolean
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
			if (aPlayer.hasDominoSelection()) aPlayer.getDominoSelection().delete();
			return true; 
		}
		else {
			return false;
		}
		
	}
	
	public static boolean discardLatestDomino(Player aPlayer) {
		
		List<KingdomTerritory> territories = aPlayer.getKingdom().getTerritories();
		if (territories.size()==1) {
			return false;
		}
		
		DominoInKingdom dInKingdom = (DominoInKingdom) territories.get(territories.size()-1);
		
		if (KDQuery.isThereAvailablePlacement(aPlayer,dInKingdom)) {
			return false;
		}
		else {
			dInKingdom.getDomino().setStatus(DominoStatus.Discarded);
			if (aPlayer.hasDominoSelection()) aPlayer.getDominoSelection().delete();
			return true;
		}
	}
	
	
	
	
	/**
	 * 
	 * This method checks a player's kingdom to make sure that
	 * all kingdom territories stay within a 5x5 grid (7x7 if 
	 * Mighty Kingdom mode is enabled) 
	 * 
	 * @see VerifyGridSize.feature
	 * @author Jing Han 260528152
	 * @param player
	 * @param testDomino
	 * @return boolean
	 */
	
	public static boolean verifyGridSizeAllKingdom(Player player,DominoInKingdom testDomino) {
				
		List<Integer> allX=new ArrayList<Integer>();
		List<Integer> allY=new ArrayList<Integer>();
				
		for (KingdomTerritory territory:player.getKingdom().getTerritories()) {
			if (territory instanceof Castle) {
				allX.add(0);
				allY.add(0);
			}
			else if (territory instanceof DominoInKingdom) {
				if (((DominoInKingdom) territory).getDomino().getStatus().equals(DominoStatus.PlacedInKingdom)) {
					int [] rightCoord=KDQuery.calculateRightPos(territory);
					allX.add(territory.getX());
					allY.add(territory.getY());
					allX.add(rightCoord[0]);
					allY.add(rightCoord[1]);
				}
			}
		}
		
		int[] testRightCoord=KDQuery.calculateRightPos(testDomino);
		allX.add(testDomino.getX());
		allY.add(testDomino.getY());
		allX.add(testRightCoord[0]);
		allY.add(testRightCoord[1]);
		
		Collections.sort(allX);
		Collections.sort(allY);
		
		if ((allX.get(allX.size()-1)-allX.get(0)+1)>5) {
			return false;
		}
		else if ((allY.get(allY.size()-1)-allY.get(0)+1)>5) {
			return false;
		}
		else {
			return true;
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
	 * @param testDomino
	 * @return boolean
	 */
	
	public static boolean verifyNoOverlapLastTerritory(Player player,DominoInKingdom testDomino) {
		
		
		List<List<Integer>> existingCoords = new ArrayList<List<Integer>>();
		
		for (KingdomTerritory territory:player.getKingdom().getTerritories()) {
			
			if (territory instanceof Castle) {
				
				List<Integer> tmp = new ArrayList<Integer>(Arrays.asList(0,0));
				existingCoords.add(tmp);
				
			}
			else if (territory instanceof DominoInKingdom) {
				
				if (((DominoInKingdom) territory).getDomino().getStatus().equals(DominoStatus.PlacedInKingdom)) {
					
					int [] rightCoord=KDQuery.calculateRightPos(territory);
					List<Integer> tmp1 = new ArrayList<Integer>(Arrays.asList(territory.getX(),territory.getY()));
					List<Integer> tmp2 = new ArrayList<Integer>(Arrays.asList(rightCoord[0],rightCoord[1]));
					
					existingCoords.add(tmp1);
					existingCoords.add(tmp2);
				}
			}
		}
		
		int[] testRightCoord=KDQuery.calculateRightPos(testDomino);
		List<Integer> testCoord1=new ArrayList<Integer>(Arrays.asList(testDomino.getX(),testDomino.getY()));
		List<Integer> testCoord2=new ArrayList<Integer>(Arrays.asList(testRightCoord[0],testRightCoord[1]));
		
		if (existingCoords.contains(testCoord1)) {
			return false;
		}
		else if (existingCoords.contains(testCoord2)) {
			return false;
		}
		else {
			return true;
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
	 * @param testDomino
	 * @return boolean
	 */
	
	public static boolean verifyCastleAdjacency(Player player,DominoInKingdom testDomino) {
		
		List<List<Integer>> castleNeighborhood = new ArrayList<List<Integer>>();
		
		List<Integer> right = new ArrayList<Integer>(Arrays.asList(1,0));
		List<Integer> top = new ArrayList<Integer>(Arrays.asList(0,1));
		List<Integer> left = new ArrayList<Integer>(Arrays.asList(-1,0));
		List<Integer> down = new ArrayList<Integer>(Arrays.asList(0,-1));
		
		castleNeighborhood.add(right);
		castleNeighborhood.add(top);
		castleNeighborhood.add(left);
		castleNeighborhood.add(down);
		
		int[] rightCoord=KDQuery.calculateRightPos(testDomino);
		List<Integer> testCoord1=new ArrayList<Integer>(Arrays.asList(testDomino.getX(),testDomino.getY()));
		List<Integer> testCoord2=new ArrayList<Integer>(Arrays.asList(rightCoord[0],rightCoord[1]));
		
		if (testCoord1.get(0)==0 && testCoord1.get(1)==0) return false;
		if (testCoord2.get(0)==0 && testCoord2.get(1)==0) return false;
		
		if (castleNeighborhood.contains(testCoord1)) {
			return true;
		}
		else if (castleNeighborhood.contains(testCoord2)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * 
	 * This method checks a player's kingdom to determine whether
	 * the last placed domino is has at least 1 valid neighbor
	 * 
	 * @see VerifyNeighborAdjacency.fature
	 * @author Jing Han 260528152
	 * @param player
	 * @param testDomino
	 * @return boolean
	 */
	
	
	public static boolean verifyNeighborAdjacencyLastTerritory(Player player,DominoInKingdom testDomino) {
		
		List<List<Integer>> existingCoords = new ArrayList<List<Integer>>();
		List<TerrainType> existingTerrains = new ArrayList<TerrainType>();
		
		for (KingdomTerritory territory:player.getKingdom().getTerritories()) {
			
			 if (territory instanceof DominoInKingdom) {
				
				if (((DominoInKingdom) territory).getDomino().getStatus().equals(DominoStatus.PlacedInKingdom)) {
					
					int [] rightCoord=KDQuery.calculateRightPos(territory);
					List<Integer> tmp1 = new ArrayList<Integer>(Arrays.asList(territory.getX(),territory.getY()));
					List<Integer> tmp2 = new ArrayList<Integer>(Arrays.asList(rightCoord[0],rightCoord[1]));
					
					existingCoords.add(tmp1);
					existingTerrains.add(((DominoInKingdom) territory).getDomino().getLeftTile());
					
					existingCoords.add(tmp2);
					existingTerrains.add(((DominoInKingdom) territory).getDomino().getRightTile());
				}
			}
		}
		
		List<List<Integer>> leftNeighbors=leftNeighborCoords(testDomino);
		List<List<Integer>> rightNeighbors=rightNeighborCoords(testDomino);
		
		for (List<Integer> coord:leftNeighbors) {
			int possibleIndex=existingCoords.indexOf(coord);
			if (possibleIndex!=-1) {
				TerrainType possibleTerrain=existingTerrains.get(possibleIndex);
				if (possibleTerrain.equals(testDomino.getDomino().getLeftTile())) {
					return true;
				}
			}
		}
		
		for (List<Integer> coord:rightNeighbors) {
			int possibleIndex=existingCoords.indexOf(coord);
			if (possibleIndex!=-1) {
				TerrainType possibleTerrain=existingTerrains.get(possibleIndex);
				if (possibleTerrain.equals(testDomino.getDomino().getRightTile())) {
					return true;
				}
			}
		}
		
		return false;

	}
	
	
	/**
	 * 
	 * This method calculates the score of a Player's kingdom
	 * @see  - CalculatePlayerScore.feature
	 * @author Jing Han 260528152
	 * @param player
	 * @return void
	 */
	
	public static void calculateIndividualPlayerScore(Player player) {
		
		List<Property> p = player.getKingdom().getProperties();
		
		while (p.size()>0) {
			p.get(0).delete();
		}
		
		identifyAllProperty(player);
		int score=0;
		for (Property each:p) {
			score+=each.getScore();
		}
		
		calculateBonusScore(player);
		player.setPropertyScore(score);
		
	}
	
	public static void calculateAllPlayerScore(Game game) {
		   for (Player player:game.getPlayers()) {
			   calculateIndividualPlayerScore(player);
		   }
	}

	/**
	 * 
	 * This method calculates the ranking of players in kingdom
	 * @see  - CalculatePlayerRanking.feature
	 * @author Jing Han 260528152
	 * @param none
	 * @return void
	 */
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
			
			nowPlayer.setCurrentRanking(rank);
			if (comparePlayers(nowPlayer,nextPlayer)) {}
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
		
		//UPDATE PLAYER SCORES
	
		for(Player player : allPlayers) {
			
				if(player.hasUser()) {
					
					if(player.getCurrentRanking() == 1) {
						
						player.getUser().setWonGames(player.getUser().getWonGames()+1);
						
					}
					
					player.getUser().setPlayedGames(player.getUser().getPlayedGames()+1);
				
				}
		}
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
	
	
	////////////////////////////////////////
	/// ---- Gherkin Helper Methods ---- ///
	////////////////////////////////////////
	

	/**
	 * 
	 * This method returns the color of the player depending on the input string.
	 * 
	 * @see CalculateRanking.feature
	 * @author Gurdarshan Singh 260927466
	 * @param s1
	 * @return p
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
	 * This method returns a terrain type depending on the input string.
	 * 
	 * @see BrowseDominoPile.feature
	 * @author Gurdarshan Singh 260927466
	 * @param terrain
	 * @return TerrainType
	 * @throws java.lang.IllegalArgumentException
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
		TerrainType t1 = retrieveTerrainType(s1);
		for (int i = 1; i < 49 ; i++) {
			Domino dom = getdominoByID(i);
			
			if ( dom.getLeftTile().toString().equalsIgnoreCase(t1.toString()) || dom.getRightTile().toString().equalsIgnoreCase(t1.toString()) ) {
				
				myDominos.add(dom.getId());
			}			
		}	
		

		return myDominos;
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
	 * This method arranges the domino IDs in the order that is provided in the string accordingly.
	 * 
	 * @see ShuffleDomino.feature
	 * @author Gurdarshan Singh 260927466
	 * @param s1
	 * @return DominoArray
	 */
	
	public static Integer[] parseDominoIds(String s1) {
		
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
			DominoArray[i] = getdominoByID(Integer.parseInt(StringArray[i]));
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
			DominoArray[count] = getdominoByID(Integer.parseInt(StringArray[i]));
			count++;
		}
		
		return DominoArray;
		
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
	 * This method gets gets a user by Name
	 * @author Keon Olszewski 260927813
	 * @param userName
	 * @return userList
	 * @throws java.lang.IllegalArgumentException
	 */
	
	public static User getUserByName(String username) throws IllegalArgumentException {
		Kingdomino kingdomino = KingdominoApplication.getKingdomino();
		List<User> userList = kingdomino.getUsers();
		
		for(int i = 0; i< userList.size(); i++) {
			
			if(userList.get(i).getName().equalsIgnoreCase(username)) return userList.get(i);
		}
		
		throw new IllegalArgumentException("username provided does not exist");
	}
	
	/**
	 * 
	 * This method gets gets a domino by its id
	 * @author Massimo Vadacchino 260928064
	 * @param userName
	 * @return userList
	 */
	
	public static Domino getdominoByID(int id) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		for (Domino domino : game.getAllDominos()) {
			if (domino.getId() == id) {
				return domino;
			}
		}
		throw new java.lang.IllegalArgumentException("Domino with ID " + id + " not found.");
	}
	public static ArrayList<String> fileSearch(String s){
		
		Set<String> str = listFilesUsingJavaIO(s);
		ArrayList<String> list = new ArrayList<String>();
		for(String stri: str) {
			if(stri.contains(".data")){
				list.add(stri);
			}
		}
		
		return list;
		
	}
	
	////////////////////////////////////////
	/// ---- Private Helper Methods ---- ///
	////////////////////////////////////////
	/**
	 * 
	 * This method returns all the file name inside a directory
	 * Retrieved on Baeldung : https://www.baeldung.com/java-list-directory-files?fbclid=IwAR2peMbOXSEzbUPNn9Coe8XK9WHyimpyqI--L-9BcrFKJBF_jHsSPOqYiXM
	 * @param dir
	 * @return Set<String>
	 */
	private static Set<String> listFilesUsingJavaIO(String dir) {
	    return Stream.of(new File(dir).listFiles())
	      .filter(file -> !file.isDirectory())
	      .map(File::getName)
	      .collect(Collectors.toSet());
	}

	
	private static List<List<Integer>> leftNeighborCoords (DominoInKingdom testDomino){
		
		int[] rightCoords=KDQuery.calculateRightPos(testDomino);
		
		List<Integer> rightCen=new ArrayList<Integer>(Arrays.asList(rightCoords[0],rightCoords[1]));
		
		List<List<Integer>> leftNeighbors = new ArrayList<List<Integer>>();
		List<Integer> leftNeighborRight = new ArrayList<Integer>(Arrays.asList(testDomino.getX()+1,testDomino.getY()));
		List<Integer> leftNeighborLeft = new ArrayList<Integer>(Arrays.asList(testDomino.getX()-1,testDomino.getY()));
		List<Integer> leftNeighborTop = new ArrayList<Integer>(Arrays.asList(testDomino.getX(),testDomino.getY()+1));
		List<Integer> leftNeighborBottom = new ArrayList<Integer>(Arrays.asList(testDomino.getX(),testDomino.getY()-1));
		
		leftNeighbors.add(leftNeighborBottom);
		leftNeighbors.add(leftNeighborTop);
		leftNeighbors.add(leftNeighborLeft);
		leftNeighbors.add(leftNeighborRight);
		leftNeighbors.remove(rightCen);		
		
		return leftNeighbors;
	}
	
	private static List<List<Integer>> rightNeighborCoords (DominoInKingdom testDomino){
		
		int[] rightCoords=KDQuery.calculateRightPos(testDomino);
		List<Integer> leftCen = new ArrayList<Integer>(Arrays.asList(testDomino.getX(),testDomino.getY()));
		
		List<List<Integer>> rightNeighbors = new ArrayList<List<Integer>>();
		List<Integer> rightNeighborRight = new ArrayList<Integer>(Arrays.asList(rightCoords[0]+1,rightCoords[1]));
		List<Integer> rightNeighborLeft = new ArrayList<Integer>(Arrays.asList(rightCoords[0]-1,rightCoords[1]));
		List<Integer> rightNeighborUp = new ArrayList<Integer>(Arrays.asList(rightCoords[0],rightCoords[1]+1));
		List<Integer> rightNeighborDown = new ArrayList<Integer>(Arrays.asList(rightCoords[0],rightCoords[1]-1));
		
		rightNeighbors.add(rightNeighborDown);
		rightNeighbors.add(rightNeighborUp);
		rightNeighbors.add(rightNeighborLeft);
		rightNeighbors.add(rightNeighborRight);
		rightNeighbors.remove(leftCen);
		
		
		return rightNeighbors;
	}
	
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
	
	private static void calculateBonusScore(Player player) {
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
	
	public static boolean isHarmony(Player player) {
		
		
		int numPlaced = 0;
		
		for (KingdomTerritory t:player.getKingdom().getTerritories()) {
			if (t instanceof DominoInKingdom) {
				if (((DominoInKingdom) t).getDomino().getStatus().equals(DominoStatus.PlacedInKingdom)) {
					numPlaced+=1;
				}
			}
		}
		
		if (numPlaced!=12) {
			return false;
		}
		
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
	
	private static List<List<int[]>> clusterTerrain(List<int[]> terrainGroup) {
		int delta=1;
		List<List<int[]>> firstPass = mergeTile(terrainGroup);
		while (delta>0) {
			
			List<List<int[]>> secondPass=mergeCluster(firstPass);
			delta = firstPass.size()-secondPass.size();
			firstPass=secondPass;
		}		
		return firstPass;
	}
	
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
	
	private static int[] calculateOtherPos(KingdomTerritory d) {
		
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
	
	
	
	private static int L2NormSquared(int x1, int y1, int x2, int y2) {
		int deltaX=x2-x1;
		int deltaY=y2-y1;
		
		int norm = deltaX*deltaX+deltaY*deltaY;
		
		return norm;
	}
	
	
	
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
	
	private static void changeDraftDominoStatus(Draft draft, Domino.DominoStatus status) {
		
		if(!((status.equals(Domino.DominoStatus.InCurrentDraft))||(status.equals(Domino.DominoStatus.InNextDraft))||(status.equals(Domino.DominoStatus.Excluded)))) {
			throw new IllegalArgumentException("status must be either InCurrentDraft or InNextDraft or Excluded");
		}
		
		List<Domino> draftDominos = draft.getIdSortedDominos();
		for (Domino each:draftDominos) {
			each.setStatus(status);
		}
	}
	
	private static void sortCurrentDraft() {
		
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

	}
	
	private static Draft createOneDraft() {
		Kingdomino kd = KingdominoApplication.getKingdomino();
		Game game = kd.getCurrentGame();
		
		int numPlayer=kd.getCurrentGame().getNumberOfPlayers();
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
	
	private static List<Integer> uniqueRandomSequence(int size,int min,int max){
		List<Integer> sequence = new ArrayList<Integer>();
		Random rand = new Random();			//seeded to get consistent random numbers to help development
												//remove seed before deployment
		
		while (sequence.size()<size){
			int num=rand.nextInt((max - min) + 1) + min;
			if (!sequence.contains(num)) {
				sequence.add(num);
			}
		}
		return sequence;
	}
	
	
	
	
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