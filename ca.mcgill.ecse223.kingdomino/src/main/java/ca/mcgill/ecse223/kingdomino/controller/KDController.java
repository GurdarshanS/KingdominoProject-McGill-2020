package ca.mcgill.ecse223.kingdomino.controller;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;


import ca.mcgill.ecse223.kingdomino.model.*;
import ca.mcgill.ecse223.kingdomino.model.Domino.DominoStatus;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom.DirectionKind;
import ca.mcgill.ecse223.kingdomino.model.Draft.DraftStatus;
import ca.mcgill.ecse223.kingdomino.model.Player.PlayerColor;
import ca.mcgill.ecse223.kingdomino.persistence.KDPersistence;

import java.awt.Component;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.Icon;
import javax.swing.JOptionPane;

public class KDController {
	/**
	 * @author Anthony Harissi Dagher
	 * Feature 1: This method sets the desired game options for the player.
	 * @param numPlayers : Number of players for the game.
	 * @param selectedBonusOptions: List of chosen bonus options.
	 * @throws IllegalArgumentException: Thrown when numPlayers is invalid.
	 */
	public static void setGameOptions(int numPlayers, List<BonusOption> selectedBonusOptions) 
										throws InvalidInputException{
		
		Kingdomino kingdomino = KingdominoApplication.getKingdomino();
		if(numPlayers < 1 || numPlayers > 4) {
			throw new InvalidInputException("Must between 2 and 4 players for a game.");
		}
		if(numPlayers == 2) {
			Game game = new Game(24, kingdomino);
			game.setNumberOfPlayers(2);
			for(int i=0; i<= selectedBonusOptions.size()-1; i++) {
				BonusOption bonusOption = selectedBonusOptions.get(i);
				game.addSelectedBonusOption(bonusOption);
			}
			kingdomino.setCurrentGame(game);
		}
		if(numPlayers == 3) {
			Game game = new Game(36, kingdomino);
			game.setNumberOfPlayers(3);
			for(int i=0; i<= selectedBonusOptions.size()-1; i++) {
				BonusOption bonusOption = selectedBonusOptions.get(i);
				game.addSelectedBonusOption(bonusOption);
			}
			kingdomino.setCurrentGame(game);
		}
		if(numPlayers == 4) {
			Game game = new Game(48, kingdomino);
			game.setNumberOfPlayers(4);
			for(int i=0; i<= selectedBonusOptions.size()-1; i++) {
				BonusOption bonusOption = selectedBonusOptions.get(i);
				game.addSelectedBonusOption(bonusOption);
			}
			kingdomino.setCurrentGame(game);
		}
	} 
	/**
	 * @author Anthony Harissi Dagher
	 * Feature 3: This method starts a new game for the player.
	 */
	public static void startANewGame() {
		
		Kingdomino kingdomino = KingdominoApplication.getKingdomino();
		Game game = kingdomino.getCurrentGame();
		int randomNum = ThreadLocalRandom.current().nextInt(0, game.numberOfPlayers());
		List<Domino> dominoesInGame;
		for (int i = 0; i < game.numberOfPlayers(); i++) {
			if(i==0) {
				game.getPlayer(i).setColor(Player.PlayerColor.Blue);
			}
			if(i==1) {
				game.getPlayer(i).setColor(Player.PlayerColor.Green);
			}
			if(i==2) {
				game.getPlayer(i).setColor(Player.PlayerColor.Pink);
			}
			if(i==3) {
				game.getPlayer(i).setColor(Player.PlayerColor.Yellow);
			}
		}
		game.setNextPlayer(game.getPlayer(randomNum));
		createAllDominoes(game);
		
		if(game.getNumberOfPlayers()==2) {
			
			int randDomino = ThreadLocalRandom.current().nextInt(0, 24);
			dominoesInGame = pickRandDomino(game.getAllDominos(), 24+1);
			for(int i=0; i<= 24; i++) {
				game.removeAllDomino(dominoesInGame.get(i));
			}
			game.setTopDominoInPile(dominoesInGame.get(randDomino));
		}
		if(game.getNumberOfPlayers()==3) {
			
			int randDomino = ThreadLocalRandom.current().nextInt(0, 36);
			dominoesInGame = pickRandDomino(game.getAllDominos(), 12+1);
			for(int i=0; i<= 12; i++) {
				game.removeAllDomino(dominoesInGame.get(i));
			}
			game.setTopDominoInPile(dominoesInGame.get(randDomino));
		}
		if(game.getNumberOfPlayers()==4) {
			
			int randDomino = ThreadLocalRandom.current().nextInt(0, 36);
			dominoesInGame = pickRandDomino(game.getAllDominos(), 48+1);
			game.setTopDominoInPile(dominoesInGame.get(randDomino));
		}
		createOneDraft();
	}
	/**
	 * @author Anthony Harissi Dagher
	 * Feature 6: This method loads a saved game for the player.
	 * @param file: The file inputed from the user.
	 * @return Method returns true if the game is loaded, false it cannot be.
	 * @throws InvalidInputException: Thrown if file cannot be loaded
	 */
	public static boolean loadGame(File file) throws InvalidInputException {
		
		boolean gameLoaded = false;
		String directory = "./src/test/resources/"+file.getName();	
		File fileSearch = new File(directory);
		if (fileSearch.isFile()) {
			if(fileSearch.canRead()) {
				try{
					KDPersistence.load();
					gameLoaded = true;
				}
				catch(RuntimeException r) {
					throw new InvalidInputException("Invalid file name.");
				}
			}
		}
		return gameLoaded;
	}
	/**
	 * @author Anthony Harissi Dagher
	 * Feature 7: This method saves the current game for the player.
	 * @param file: Name of the file saved by the user.
	 * @return Method returns true if file is saved, false if it cannot be.
	 * @throws InvalidInputException: Thrown if file cannot be saved
	 */
	public static boolean saveGame(File file, boolean overwrite) throws InvalidInputException {
		
		boolean gameSaved;
		Kingdomino kingdomino = KingdominoApplication.getKingdomino();
		String directory = "./src/test/resources/"+file.getName();
		File fileSearch = new File(directory);
		if(fileSearch.exists() && overwrite == false) {
			gameSaved = false;
		}
		else {
			try {
				KDPersistence.save(kingdomino); 
				gameSaved = true;
			}catch(RuntimeException r) {
				throw new InvalidInputException(r.getMessage());
			}
		}
		return gameSaved;
	}
	
	///////////////////////////////////////
	/// -----Private Helper Methods---- ///
	///////////////////////////////////////
	
	public static void revealNextDraft() {
		
		Kingdomino kingdomino = KingdominoApplication.getKingdomino();
		Game game = kingdomino.getCurrentGame();
		
		if(game.getNextDraft() == null) {
			
			Draft currentDraft = game.getCurrentDraft();
			currentDraft.setDraftStatus(DraftStatus.FaceUp);
		}
		
		else{
			
		Draft nextDraft = game.getNextDraft();
		nextDraft.setDraftStatus(DraftStatus.FaceUp);
		
		}
	}
	public static Draft createOneDraft() {
		Kingdomino kd = KingdominoApplication.getKingdomino();
		Game game = kd.getCurrentGame();
		
		int numPlayer=kd.getCurrentGame().getNumberOfPlayers();
		int dominoNum=0;
		
		if ((numPlayer==2)||(numPlayer==4)) dominoNum=4;
		if (numPlayer==3) dominoNum=3;
		
		Draft draft = new Draft(Draft.DraftStatus.FaceDown,game);
		
		for (int i=0;i<dominoNum;i++) {
			Domino dominoToAdd=game.getTopDominoInPile();
			draft.addIdSortedDomino(dominoToAdd);
		}
		
		return draft;
	}
	
	public static class InvalidInputException extends Exception {
		
		private static final long serialVersionUID = -5633915762703837868L;
		
		public InvalidInputException(String errorMessage) {
			super(errorMessage);
		}
	}
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
	public static Domino getDominoByID(int id) {
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
