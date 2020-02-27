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
	 * This method sets the desired game options for the player
	 */
	public static void setGameOptions(Player player) {
		
	
	}
	/**
	 * @author Anthony Harissi Dagher
	 * This method starts a new game for the player
	 */
	public static void startANewGame(List<Player> players) {
		
		Kingdomino kingdomino = KingdominoApplication.getKingdomino();
		Game game = new Game(48, kingdomino);
		game.setNumberOfPlayers(4);
		kingdomino.setCurrentGame(game);
		addDefaultUsersAndPlayers(game);
		createAllDominoes(game);
		game.setNextPlayer(game.getPlayer(0));
	}
	/**
	 * @author Anthony Harissi Dagher
	 * This method loads a saved game for the player
	 */
	public static void loadGame(File file) throws FileNotFoundException {
		
		try {
			Scanner fileName = new Scanner(file);
		}
		catch(FileNotFoundException f) {
			
			KingdominoApplication.getKingdomino().getCurrentGame().delete();
			throw new FileNotFoundException("Sorry, this game does not exist");
		}
		if(KingdominoApplication.getKingdomino().hasCurrentGame() == true) {
			
		}
		
	}
	/**
	 * @author Anthony Harissi Dagher
	 * This method saves the game for the player
	 */
	public static void saveGame(File file){
		
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
