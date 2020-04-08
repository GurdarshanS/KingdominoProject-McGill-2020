package to_do;
//package ca.mcgill.ecse223.kingdomino.features;
//
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
//import ca.mcgill.ecse223.kingdomino.model.Draft.DraftStatus;
//import ca.mcgill.ecse223.kingdomino.controller.*;
//
//import io.cucumber.java.After;
//import io.cucumber.java.en.Given;
//import io.cucumber.java.en.Then;
//import io.cucumber.java.en.When;
//
//import ca.mcgill.ecse223.kingdomino.features.CucumberCleanUp;
//public class StartANewGameStep {
//	
//	/**
//	 * @author Anthony Harissi Dagher
//	 * Test for startANewGame
//	 * @see startANewGame.feature
//	 */
//	@Given("the program is started and ready for starting a new game")
//	public void the_program_is_started_and_ready_for_starting_a_new_game() {
//		KDController.initializeGame();
//	}
//	/**
//	 * @author Anthony Harissi Dagher
//	 * @throws InvalidInputException
//	 * @see startANewGame.feature
//	 */
//	@Given("there are four selected players")
//	public void there_are_four_selected_players() throws KDController.InvalidInputException {
//		while(KingdominoApplication.getKingdomino().getCurrentGame().numberOfPlayers()!=4) {
//			KingdominoApplication.getKingdomino().getCurrentGame().addPlayer();
//		}
//	}
//	/**
//	 * @author Anthony Harissi Dagher
//	 * @throws InvalidInputException
//	 * @see startANewGame.feature
//	 */
//	@Given("bonus options Harmony and MiddleKingdom are selected")
//	public void bonus_options_Harmony_and_MiddleKingdom_are_selected() throws KDController.InvalidInputException {
//		Kingdomino kingdomino = KingdominoApplication.getKingdomino();
//		kingdomino.addBonusOption("Harmony");
//		kingdomino.addBonusOption("Middle Kingdom");
//	}
//	/**
//	 * @author Anthony Harissi Dagher
//	 * @throws InvalidInputException 
//	 * @see startANewGame.feature
//	 */
//	@When("starting a new game is initiated")
//	public void starting_a_new_game_is_initiated() throws KDController.InvalidInputException {
//		KDController.startANewGame();
//	}
//	/**
//	 * @author Anthony Harissi Dagher
//	 * @see startANewGame.feature
//	 */
//	@When("reveal first draft is initiated")
//	public void reveal_first_draft_is_initiated() {
//		KDController.createOneDraft();
//	}
//	/**
//	 * @author Anthony Harissi Dagher
//	 * @see startANewGame.feature
//	 */
//	@Then("all kingdoms shall be initialized with a single castle")
//	public void all_kingdoms_shall_be_initialized_with_a_single_castle() {
//		Kingdomino kingdomino = KingdominoApplication.getKingdomino();
//		Game game = kingdomino.getCurrentGame();
//		for (int i = 0; i < game.getNumberOfPlayers(); i++) {
//			Player player = game.getPlayer(i);
//			Kingdom kingdom = player.getKingdom();
//			kingdom.setPlayer(player);
//			new Castle(0,0, kingdom, player);
//		}
//	}
//	/**
//	 * @author Anthony Harissi Dagher
//	 * @param int1
//	 * @param int2
//	 * @see startANewGame.feature
//	 */
//	@Then("all castle are placed at {int}:{int} in their respective kingdoms")
//	public void all_castle_are_placed_at_in_their_respective_kingdoms(Integer int1, Integer int2) {
//		Kingdomino kingdomino = KingdominoApplication.getKingdomino();
//		Game game = kingdomino.getCurrentGame();
//		for (int i = 0; i < game.getNumberOfPlayers(); i++) {
//			Player player = game.getPlayer(i);
//			Kingdom kingdom = player.getKingdom();
//			KingdomTerritory castle = kingdom.getTerritory(0);
//			castle.setX(int1);
//			castle.setY(int2);
//		}
//	}
//	/**
//	 * @author Anthony Harissi Dagher
//	 * @see startANewGame.feature
//	 */
//	@Then("the first draft of dominoes is revealed")
//	public void the_first_draft_of_dominoes_is_revealed() {
//	    
//		KDController.createOneDraft().setDraftStatus(DraftStatus.FaceUp);
//	}
//	/**
//	 * @author Anthony Harissi Dagher
//	 * @see startANewGame.feature
//	 */
//	@Then("all the dominoes form the first draft are facing up")
//	public void all_the_dominoes_form_the_first_draft_are_facing_up() {
//		DraftStatus status = DraftStatus.FaceDown;
//		
//		DraftStatus expectedStatus = DraftStatus.FaceUp;
//		Boolean actualStatus = KDController.createOneDraft().setDraftStatus(DraftStatus.FaceUp);
//		if(actualStatus==true) {
//			 status = DraftStatus.FaceUp;
//		}
//		assertEquals(expectedStatus, status);
//	}
//	/**
//	 * @author Anthony Harissi Dagher
//	 * @see startANewGame.feature
//	 */
//	@Then("all the players have no properties")
//	public void all_the_players_have_no_properties() {
//		Kingdomino kingdomino = KingdominoApplication.getKingdomino();
//		Game game = kingdomino.getCurrentGame();
//		for (int i = 0; i < game.getNumberOfPlayers(); i++) {
//			Player player = game.getPlayer(i);
//			Kingdom kingdom = player.getKingdom();
//			 while (kingdom.hasProperties()) {
//			      Property aProperty =	kingdom.getProperties().get(kingdom.getProperties().size()- 1);
//			      aProperty.delete();
//			      kingdom.getProperties().remove(aProperty);
//			 }
//		}
//	}
//	/**
//	 * @author Anthony Harissi Dagher
//	 * @see startANewGame.feature
//	 */
//	@Then("all player scores are initialized to zero")
//	public void all_player_scores_are_initialized_to_zero() {
//		Kingdomino kingdomino = KingdominoApplication.getKingdomino();
//		Game game = kingdomino.getCurrentGame();
//		for (int i = 0; i < game.getNumberOfPlayers(); i++) {
//			Player player = game.getPlayer(i);
//			player.setPropertyScore(0);
//		}
//	}
//
//}
