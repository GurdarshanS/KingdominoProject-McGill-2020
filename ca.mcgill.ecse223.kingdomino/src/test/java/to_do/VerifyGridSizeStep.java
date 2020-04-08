package to_do;
//package ca.mcgill.ecse223.kingdomino.features;
//import static org.junit.Assert.assertEquals;
//
//import java.io.BufferedReader;
//import java.io.FileReader;
//import java.io.IOException;
//import java.util.List;
//import java.util.Map;
//import java.util.*;
//
//import org.junit.internal.runners.model.EachTestNotifier;
//
//import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
//import ca.mcgill.ecse223.kingdomino.controller.KDController;
//import ca.mcgill.ecse223.kingdomino.model.*;
//import ca.mcgill.ecse223.kingdomino.model.Domino.DominoStatus;
//import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom.DirectionKind;
//import ca.mcgill.ecse223.kingdomino.model.Player.PlayerColor;
//import ca.mcgill.ecse223.kingdomino.controller.PropertyAttribute;
//import io.cucumber.java.After;
//import io.cucumber.java.en.Given;
//import io.cucumber.java.en.Then;
//import io.cucumber.java.en.When;
//
//import ca.mcgill.ecse223.kingdomino.features.CucumberCleanUp;
//
//public class VerifyGridSizeStep {
//	
//	/**
//	 * These methods checks if a player's kingdom 
//	 * respects the maximum grid size of 5x5 
//	 * @see VerifyGridSize.feature
//	 * @author Jing Han 260528152
//	 */ 
//	
//	
//	@Given("the game is initialized for verify grid size")
//	public void the_game_is_initialized_for_verify_grid_size() {
//		KDController.initiateEmptyGame(); 
//	}
//	
//	@When("validation of the grid size is initiated")
//	public void initiate_player_kingdom_grid_size_validation() {
//		KDController.verifyGridSizeAllKingdom(KingdominoApplication.getKingdomino().getCurrentGame().getNextPlayer());
//	}
//	
//	@Then("the grid size of the player's kingdom shall be {string}")
//	public void get_kingdom_gridsize_validation_result(String expectedValidity) {
//		String validity=KDController.getKingdomVerificationResult(KingdominoApplication.getKingdomino().getCurrentGame().getNextPlayer());
//		assertEquals(expectedValidity,validity);
//	}
//	
//	@Given("the  player preplaces domino {int} to their kingdom at position {int}:{int} with direction {string}")
//	public void the_player_preplaces_these_dominos_in_kingdom(Integer id, Integer posx, Integer posy, String dir) {
//		Player player = KingdominoApplication.getKingdomino().getCurrentGame().getNextPlayer();
//		Domino dominoToPlace = KDController.getdominoByID(id);
//		KDController.prePlaceDomino(player, dominoToPlace, posx, posy, dir);
//	}
////
//
//}
