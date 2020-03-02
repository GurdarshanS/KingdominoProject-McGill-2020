package ca.mcgill.ecse223.kingdomino.controller;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;

import ca.mcgill.ecse223.kingdomino.model.*;
import ca.mcgill.ecse223.kingdomino.model.Domino.DominoStatus;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom.DirectionKind;
import ca.mcgill.ecse223.kingdomino.model.Player.PlayerColor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class KDController {

	/**
	 * @author Anthony Harissi Dagher
	 * Feature 1: This method sets the desired game options for the player.
	 * @param numPlayers : Number of players for the game.
	 * @param selectedBonusOptions: The chosen bonus options.
	 * @throws IllegalArgumentException: Thrown when numPlayers is invalid.
	 */
	public static void setGameOptions(int numPlayers, List<BonusOption> selectedBonusOptions) 
										throws IllegalArgumentException{
		
		Kingdomino kingdomino = KingdominoApplication.getKingdomino();
		if(numPlayers < 1 || numPlayers > 4) {
			throw new IllegalArgumentException("Invalid amount of players. Try between 2 and 4.");
		}
		if(numPlayers == 2) {
			Game game = new Game(24, kingdomino);
			game.setNumberOfPlayers(2);
			kingdomino.setCurrentGame(game);
			for(int i=0; i<= selectedBonusOptions.size(); i++) {
				BonusOption bonusOption = selectedBonusOptions.get(i);
				game.addSelectedBonusOption(bonusOption);
			}
		}
		if(numPlayers == 3) {
			Game game = new Game(36, kingdomino);
			game.setNumberOfPlayers(3);
			kingdomino.setCurrentGame(game);
			for(int i=0; i<= selectedBonusOptions.size(); i++) {
				BonusOption bonusOption = selectedBonusOptions.get(i);
				game.addSelectedBonusOption(bonusOption);
			}
		}
		if(numPlayers == 4) {
			Game game = new Game(48, kingdomino);
			game.setNumberOfPlayers(4);
			kingdomino.setCurrentGame(game);
			for(int i=0; i<= selectedBonusOptions.size(); i++) {
				BonusOption bonusOption = selectedBonusOptions.get(i);
				game.addSelectedBonusOption(bonusOption);
			}
		}
	} 
	
	/**
	 * @author Anthony Harissi Dagher
	 * Feature 3: This method starts a new game for the player.
	 */
	public static void startANewGame() {
		
		Kingdomino kingdomino = KingdominoApplication.getKingdomino();
		Game game = kingdomino.getCurrentGame();
		int randomNum = ThreadLocalRandom.current().nextInt(0, game.getNumberOfPlayers()-1);
		int randID = ThreadLocalRandom.current().nextInt(0, 24);
		List<Domino> dominoesInGame;
		for (int i = 0; i < game.getNumberOfPlayers(); i++) {
			
			game.getKingdomino().addUser(game.getPlayer(i).toString());
			Player player = new Player(game);
			player.setColor(PlayerColor.values()[i]);
			Kingdom kingdom = new Kingdom(player);
			new Castle(0, 0, kingdom, player);
		}
		game.setNextPlayer(game.getPlayer(randomNum));
		createAllDominoes(game);
		
		if(game.getNumberOfPlayers()==2) {
			
			dominoesInGame = pickRandDomino(game.getAllDominos(), 24);
			game.setTopDominoInPile(aNewTopDominoInPile)
		}
		if(game.getNumberOfPlayers()==3) {
			dominoesInGame = pickRandDomino(game.getAllDominos(), 36);
			game.setTopDominoInPile(aNewTopDominoInPile)
		}
		if(game.getNumberOfPlayers()==4) {
			dominoesInGame = pickRandDomino(game.getAllDominos(), 48);
			game.setTopDominoInPile(aNewTopDominoInPile)
		}
	}
	
	/**
	 * @author Anthony Harissi Dagher
	 * Feature 6: This method loads a saved game for the player.
	 * @param file:  The file inputed from the user.
	 * @return Method returns true if the game is loaded, false it cannot be.
	 */
	public static boolean loadGame(String file) {
		
		boolean gameLoaded = false;
		Kingdomino game = KingdominoApplication.getKingdomino();
		String directory = "./src/main/resources/savedGames/"+file;	
	
		File fileSearch = new File(directory);
		if (fileSearch.isFile()) {
			if(fileSearch.canRead()) {
				gameLoaded = loadGameFile(directory);
			}
		}
		return gameLoaded;
	}
	
	/**
	 * @author Anthony Harissi Dagher
	 * Feature 7: This method saves the current game for the player.
	 * @param file: Name of the file saved by the user.
	 * @return Method returns true if file is saved, false if it cannot be.
	 */
	public static boolean saveGame(String file){
		
		boolean gameSaved = false;
		Kingdomino kingdomino = KingdominoApplication.getKingdomino();
		Game game = kingdomino.getCurrentGame();
		String directory = "./src/main/resources/savedGames/"+file;
		File fileSearch = new File(directory);
		
		if(fileSearch.exists()== true) {
			gameSaved = overwriteSave(directory); //If the file exists, overwrite it.(basically just create new save but under same name)
		}
		else {
			gameSaved = newSave(directory); //If the file does not exist, new save.
		}
		return gameSaved;
	}
	
	///////////////////////////////////////
	/// -----Private Helper Methods---- ///
	///////////////////////////////////////
	
	public static void initializeGame() {
		// Intialize empty game
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
	public static List<Domino> pickRandDomino(List<Domino> lst, int n) {
	    List<Domino> copy = new LinkedList<Domino>(lst);
	    Collections.shuffle(copy);
	    return copy.subList(0, n);
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
}
