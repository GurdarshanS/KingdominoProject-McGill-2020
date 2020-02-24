package ca.mcgill.ecse223.kingdomino.features;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.junit.internal.runners.model.EachTestNotifier;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.KDController;
import ca.mcgill.ecse223.kingdomino.model.*;
import ca.mcgill.ecse223.kingdomino.model.Domino.DominoStatus;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom.DirectionKind;
import ca.mcgill.ecse223.kingdomino.model.Player.PlayerColor;
import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import ca.mcgill.ecse223.kingdomino.features.CucumberCleanUp;

public class CucumberStepDefinitions {

	

	/**
	 * These methods checks if a player's kingdom respects the maximum 
	 * grid size of 5x5 (regular mode) or 7x7 (mighty kingdom)
	 * @see VerifyGridSize.feature
	 * @author Jing Han 260528152
	 */
	
	
	@Given("the game is initialized for verify grid size")
	public void the_game_is_initialized_for_verify_grid_size() {
		initiateEmptyGame();
	}
	
	@Given("the player's kingdom has the following dominoes:")
	public void the_player_s_kingdom_has_the_following_dominoes(io.cucumber.datatable.DataTable dataTable) {
		Player player = KingdominoApplication.getKingdomino().getCurrentGame().getNextPlayer();
		
		List<Map<String, String>> valueMaps = dataTable.asMaps();
		for (Map<String, String> map : valueMaps) {
			// Get values from cucumber table
			Integer id = Integer.decode(map.get("id"));
			String dir = map.get("dominodir");
			Integer posx = Integer.decode(map.get("posx"));
			Integer posy = Integer.decode(map.get("posy"));

			// Add the domino to a player's kingdom
			Domino dominoToPlace = getdominoByID(id);
			KDController.prePlaceDomino(player, dominoToPlace, posx, posy, dir);
		}
	}
	
	@When("validation of the grid size is initiated")
	public void initiate_player_kingdom_validation() {
		KDController.verifyKingdom(KingdominoApplication.getKingdomino().getCurrentGame().getNextPlayer());
	}
	
	@Then("the grid size of the player's kingdom shall be {string}")
	public void get_kingdom_gridsize_validation_result(String expectedValidity) {
		String validity=KDController.getKingdomSizeVerificationResult(KingdominoApplication.getKingdomino().getCurrentGame().getNextPlayer());
		assertEquals(expectedValidity,validity);
	}
	
	@After
	public void tearDown() {
		Kingdomino kingdomino = KingdominoApplication.getKingdomino();
		if (kingdomino != null) {
			kingdomino.delete();
		}
	}
	
	@Given("the  player preplaces domino {int} to their kingdom at position {int}:{int} with direction {string}")
	public void the_player_preplaces_these_dominos_in_kingdom(Integer id, Integer posx, Integer posy, String dir) {
		Player player = KingdominoApplication.getKingdomino().getCurrentGame().getNextPlayer();
		Domino dominoToPlace = getdominoByID(id);
		KDController.prePlaceDomino(player, dominoToPlace, posx, posy, dir);
	}


	
	




//	@Given("domino {int} is in the current draft")
//	public void domino__in_the_current_draft(Integer domID) {
//		// TODO: Write code here that turns the phrase above into concrete actions
//	}
	
	/*
	 * Note that these step definitions and helper methods just serve as a guide to help
	 * you get started. You may change the code if required.
	 */

//	@Given("the game is initialized for discard domino")
//	public void the_game_is_initialized_for_discard_domino() {
//		// Intialize empty game
//		Kingdomino kingdomino = new Kingdomino();
//		Game game = new Game(48, kingdomino);
//		game.setNumberOfPlayers(4);
//		kingdomino.setCurrentGame(game);
//		// Populate game
//		addDefaultUsersAndPlayers(game);
//		createAllDominoes(game);
//		game.setNextPlayer(game.getPlayer(0));
//		KingdominoApplication.setKingdomino(kingdomino);
//	}
//
//	@Given("the player's kingdom has the following dominoes:")
//	public void the_player_s_kingdom_has_the_following_dominoes(io.cucumber.datatable.DataTable dataTable) {
//		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
//		List<Map<String, String>> valueMaps = dataTable.asMaps();
//		for (Map<String, String> map : valueMaps) {
//			// Get values from cucumber table
//			Integer id = Integer.decode(map.get("id"));
//			DirectionKind dir = getDirection(map.get("dominodir"));
//			Integer posx = Integer.decode(map.get("posx"));
//			Integer posy = Integer.decode(map.get("posy"));
//
//			// Add the domino to a player's kingdom
//			Domino dominoToPlace = getdominoByID(id);
//			Kingdom kingdom = game.getPlayer(0).getKingdom();
//			DominoInKingdom domInKingdom = new DominoInKingdom(posx, posy, kingdom, dominoToPlace);
//			domInKingdom.setDirection(dir);
//			dominoToPlace.setStatus(DominoStatus.PlacedInKingdom);
//		}
//	}
//
//	@Given("domino {int} is in the current draft")
//	public void domino_is_in_the_current_draft(Integer domID) {
//		// TODO: Write code here that turns the phrase above into concrete actions
//	}
//
//	@Given("the current player has selected domino {int}")
//	public void the_current_player_has_selected_domino(Integer domID) {
//		// TODO: Write code here that turns the phrase above into concrete actions
//	}
//
//	@Given("the player preplaces domino {int} at its initial position")
//	public void the_player_preplaces_domino_at_its_initial_position(Integer domID) {
//		// TODO: Write code here that turns the phrase above into concrete actions
//	}
//
//	@When("the player attempts to discard the selected domino")
//	public void the_player_attempts_to_discard_the_selected_domino() {
//		// TODO: Call your Controller method here.
//		throw new cucumber.api.PendingException(); // Remove this line once your controller method is implemented
//	}
//
//	@Then("domino {int} shall have status {string}")
//	public void domino_shall_have_status(Integer domID, String domStatus) {
//		DominoStatus actualStatus = getdominoByID(domID).getStatus();
//		DominoStatus expectedStatus = getDominoStatus(domStatus);
//		assertEquals(expectedStatus, actualStatus);
//	}

	///////////////////////////////////////
	/// -----Private Helper Methods---- ///
	///////////////////////////////////////
	
private void initiateEmptyGame() {
		// Intialize empty game
		Kingdomino kingdomino = KingdominoApplication.getKingdomino();
		Game game = new Game(48, kingdomino);
		game.setNumberOfPlayers(4);
		kingdomino.setCurrentGame(game);
		// Populate game
		addDefaultUsersAndPlayers(game);
		createAllDominoes(game);
		game.setNextPlayer(game.getPlayer(0));
	}

private void populateNextPlayerKingdom(int dominoId, String dir, int posx, int posy,Kingdom kingdom) {
	Domino dominoToPlace = getdominoByID(dominoId);	
	DominoInKingdom domInKingdom = new DominoInKingdom(posx, posy, kingdom, dominoToPlace);
	domInKingdom.setDirection(getDirection(dir));
	dominoToPlace.setStatus(DominoStatus.PlacedInKingdom);
}

	private void addDefaultUsersAndPlayers(Game game) {
		String[] users = { "User1", "User2", "User3", "User4" };
		for (int i = 0; i < users.length; i++) {
			game.getKingdomino().addUser(users[i]);
			Player player = new Player(game);
			player.setColor(PlayerColor.values()[i]);
			Kingdom kingdom = new Kingdom(player);
			new Castle(0, 0, kingdom, player);
		}
	}

	private void createAllDominoes(Game game) {
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

	private TerrainType getTerrainType(String terrain) {
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
