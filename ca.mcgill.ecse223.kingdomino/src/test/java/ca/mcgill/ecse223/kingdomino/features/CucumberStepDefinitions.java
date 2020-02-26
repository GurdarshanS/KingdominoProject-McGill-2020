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
		KDController.initiateEmptyGame(); 
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
	public void initiate_player_kingdom_grid_size_validation() {
		KDController.verifyGridSize(KingdominoApplication.getKingdomino().getCurrentGame().getNextPlayer());
	}
	
	@Then("the grid size of the player's kingdom shall be {string}")
	public void get_kingdom_gridsize_validation_result(String expectedValidity) {
		String validity=KDController.getKingdomVerificationResult(KingdominoApplication.getKingdomino().getCurrentGame().getNextPlayer());
		assertEquals(expectedValidity,validity);
	}
	
	@Given("the  player preplaces domino {int} to their kingdom at position {int}:{int} with direction {string}")
	public void the_player_preplaces_these_dominos_in_kingdom(Integer id, Integer posx, Integer posy, String dir) {
		Player player = KingdominoApplication.getKingdomino().getCurrentGame().getNextPlayer();
		Domino dominoToPlace = getdominoByID(id);
		KDController.prePlaceDomino(player, dominoToPlace, posx, posy, dir);
	}

	@After
	public void tearDown() {
		Kingdomino kingdomino = KingdominoApplication.getKingdomino();
		if (kingdomino != null) {
			kingdomino.delete();
		}
	}
	
	/**
	 * These methods checks if dominos overlap
	 * @see VerifyNoOverlapping.feature
	 * @author Jing Han 260528152
	 */
	
	@Given("the game is initialized to check domino overlapping")
	public void the_game_is_initialized_to_check_domino_overlapping() {
		KDController.initiateEmptyGame();
	}

	@Given("the following dominoes are present in a player's kingdom:")
	public void the_following_dominoes_are_present_in_a_player_kingdom(io.cucumber.datatable.DataTable dataTable) {
		Player player = KingdominoApplication.getKingdomino().getCurrentGame().getNextPlayer();
		
		List<Map<String, String>> valueMaps = dataTable.asMaps();
		for (Map<String, String> map : valueMaps) {
			// Get values from cucumber table
			Integer id = Integer.decode(map.get("domino"));
			String dir = map.get("dominodir");
			Integer posx = Integer.decode(map.get("posx"));
			Integer posy = Integer.decode(map.get("posy"));

			// Add the domino to a player's kingdom
			Domino dominoToPlace = getdominoByID(id); 
			KDController.prePlaceDomino(player, dominoToPlace, posx, posy, dir);
			dominoToPlace.setStatus(DominoStatus.PlacedInKingdom);
		}
	}

	@Given("the current player preplaced the domino with ID {int} at position {int}:{int} and direction {string}")
	public void the_current_player_preplaced_domino(Integer id, Integer posx, Integer posy, String dir) {
		Player player = KingdominoApplication.getKingdomino().getCurrentGame().getNextPlayer();
		Domino dominoToPlace = getdominoByID(id);
		KDController.prePlaceDomino(player, dominoToPlace, posx, posy, dir);
	}

	@When("check current preplaced domino overlapping is initiated")
	public void initiate_domino_overlap_verification() {
		KDController.verifyNoOverlap(KingdominoApplication.getKingdomino().getCurrentGame().getNextPlayer());
	}

	@Then("the current-domino\\/existing-domino overlapping is {string}")
	public void get_domino_overlap_verification_result(String expectedValidity) {
		String validity=KDController.getKingdomVerificationResult(KingdominoApplication.getKingdomino().getCurrentGame().getNextPlayer());
		assertEquals(expectedValidity,validity);
	}
	
	
	/**
	 * These methods checks if current domino 
	 * is adjacent to the caslte
	 * @see VerifyCastleAdjacency.feature
	 * @author Jing Han 260528152
	 */
	
	@Given("the game is initialized for castle adjacency")
	public static void initialize_game_for_castle_adjacency() {
		KDController.initiateEmptyGame();
	}
	
//	@Given("the current player preplaced the domino with ID 1 at position {int}:{int} and direction {string}")
//	public static void preplace_domino_for_castle_adjacency(Integer posx, Integer posy, String dir) {
//		Player player = KingdominoApplication.getKingdomino().getCurrentGame().getNextPlayer();
//		Domino dominoToPlace = getdominoByID(1);
//		KDController.prePlaceDomino(player, dominoToPlace, posx, posy, dir);
//	}
	
	@When("check castle adjacency is initiated")
	public static void initiate_castle_adjacency_check() {
		KDController.verifyCastleAdjacency(KingdominoApplication.getKingdomino().getCurrentGame().getNextPlayer());
	}

	@Then("the castle\\/domino adjacency is {string}")
	public void get_castle_adjacency_verification_result(String expectedValidity) {
		String validity=KDController.getKingdomVerificationResult(KingdominoApplication.getKingdomino().getCurrentGame().getNextPlayer());
		assertEquals(expectedValidity,validity);
	}
	///////////////////////////////////////
	/// -----Private Helper Methods---- ///
	///////////////////////////////////////
	
	private static Domino getdominoByID(int id) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		for (Domino domino : game.getAllDominos()) {
			if (domino.getId() == id) {
				return domino;
			}
		}
		throw new java.lang.IllegalArgumentException("Domino with ID " + id + " not found.");
	}
	
}
