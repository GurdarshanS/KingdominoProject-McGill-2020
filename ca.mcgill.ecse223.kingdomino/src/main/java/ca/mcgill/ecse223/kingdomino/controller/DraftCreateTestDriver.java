package ca.mcgill.ecse223.kingdomino.controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.model.Castle;
import ca.mcgill.ecse223.kingdomino.model.Domino;
import ca.mcgill.ecse223.kingdomino.model.Game;
import ca.mcgill.ecse223.kingdomino.model.Kingdom;
import ca.mcgill.ecse223.kingdomino.model.Kingdomino;
import ca.mcgill.ecse223.kingdomino.model.Player;
import ca.mcgill.ecse223.kingdomino.model.TerrainType;
import ca.mcgill.ecse223.kingdomino.model.User;
import ca.mcgill.ecse223.kingdomino.model.Domino.DominoStatus;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom.DirectionKind;
import ca.mcgill.ecse223.kingdomino.model.Player.PlayerColor;

public class DraftCreateTestDriver {
	
public static void main (String [] args) {
	Kingdomino kingdomino = new Kingdomino();
	Game game = new Game(48, kingdomino);
	game.setNumberOfPlayers(4);
	kingdomino.setCurrentGame(game);
	// Populate game
	addDefaultUsersAndPlayers(game);
	createAllDominoes(game);
	game.setNextPlayer(game.getPlayer(0));
	KingdominoApplication.setKingdomino(kingdomino);
	
	System.out.println(game.getAllDrafts());
	
	KDController.createNextDraft();

	
	System.out.println(game.getAllDrafts());
	
	System.out.println(game.getCurrentDraft());
	System.out.println(game.getNextDraft());
}






private static void addDefaultUsersAndPlayers(Game game) {
	String[] userNames = { "User1", "User2", "User3", "User4" };
	for (int i = 0; i < userNames.length; i++) {
		User user = game.getKingdomino().addUser(userNames[i]);
		Player player = new Player(game);
		player.setUser(user);
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

private Domino getdominoByID(int id) {
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

private DirectionKind getDirection(String dir) {
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

private DominoStatus getDominoStatus(String status) {
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
