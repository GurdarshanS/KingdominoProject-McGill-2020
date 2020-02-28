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
			
			for(int i=0; i<= selectedBonusOptions.size(); i++) {
				
				game.getSelectedBonusOption(i);
			}
		}
		if(numPlayers == 3) {
			Game game = new Game(36, kingdomino);
		}
		if(numPlayers == 4) {
			Game game = new Game(48, kingdomino);
		}
	}
	/**
	 * @author Anthony Harissi Dagher
	 * Feature 3: This method starts a new game for the player.
	 */
	public static void startANewGame() {
		
		Kingdomino kingdomino = KingdominoApplication.getKingdomino();
		Game game = new Game(48, kingdomino);
		List<Player> players = game.getPlayers();
		
		for (int i = 0; i < game.getNumberOfPlayers(); i++) {
			
			game.getKingdomino().addUser(game.getPlayer(i).toString());
			Player player = new Player(game);
			player.setColor(PlayerColor.values()[i]);
			Kingdom kingdom = new Kingdom(player);
			new Castle(0, 0, kingdom, player);
		}
		kingdomino.setCurrentGame(game);
		createAllDominoes(game);
		game.setNextPlayer(game.getPlayer(0));
	}
	/**
	 * @author Anthony Harissi Dagher
	 * Feature 6: This method loads a saved game for the player.
	 * @param file: Name of the file input from the user.
	 * @throws FileNotFoundException: Thrown when the file is not identifiable.
	 */
	public static void loadGame(File file) throws FileNotFoundException {
		
		Kingdomino game = KingdominoApplication.getKingdomino();
		try {
			Scanner fileName = new Scanner(file);
		}
		catch(FileNotFoundException f) {
			
			game.getCurrentGame().delete();
			throw new FileNotFoundException("Sorry, this game does not exist");
		}
		if(game.hasCurrentGame() == true) {
			
			game.getCurrentGame().delete(); // Delete the current game, saved or not.
		}
		
	}
	/**
	 * @author Anthony Harissi Dagher
	 * Feature 7: This method saves the current game for the player.
	 * @param file: Name of the file saved by the user.
	 */
	public static void saveGame(File file){
		
		Kingdomino game = new Kingdomino();
		game.getCurrentGame();
	}
	
	///////////////////////////////////////
	/// -----Private Helper Methods---- ///
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
