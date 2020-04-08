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
//
//import io.cucumber.java.After;
//import io.cucumber.java.en.Given;
//import io.cucumber.java.en.Then;
//import io.cucumber.java.en.When;
//
//import ca.mcgill.ecse223.kingdomino.features.CucumberCleanUp;
//
//public class DiscardDominoStep {
//	
//	/**
//	 * 
//	 * 	These methods check if a player
//	 *  is allowed to discard the domino
//	 *  they had selected from the draft.
//	 *  If they are, the domino is discarded.
//	 *  If not, the dominos state is changed.
//	 *  
//	 *  @see DiscardDomino.feature
//	 *  @author Massimo Vadacchino 260928064
//	 *  
//	 */
//
//	
//	@Given("the game is initialized for discard domino")
//	public void the_game_is_initialized_for_discard_domino() {
//		// Intialize empty game
//		KDController.initiateEmptyGame();
//		
//	}
//
//	@Given("domino {int} is in the current draft")
//	public void domino_is_in_the_current_draft(Integer domID) {
//		
//		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
//		Domino dominoToAddtoDraft = KDController.getdominoByID(domID);
//	
//		Draft currentDraft = new Draft(DraftStatus.Sorted, game);
//		currentDraft.addIdSortedDomino(dominoToAddtoDraft);
//		game.addAllDraft(currentDraft);
//		game.setCurrentDraft(currentDraft);
//		
//		dominoToAddtoDraft.setStatus(DominoStatus.InCurrentDraft);
//			
//	}													
//
//	@Given("the current player has selected domino {int}")
//	public void the_current_player_has_selected_domino(Integer domID) { 
//		
//		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
//		
//		Player playerToSelectDomino = game.getNextPlayer();
//		Domino dominoToSelect = KDController.getdominoByID(domID);
//		Draft currentDraft = game.getCurrentDraft();
//	
//		DominoSelection dSelection = new DominoSelection(playerToSelectDomino, dominoToSelect, currentDraft);
//		
//		playerToSelectDomino.setDominoSelection(dSelection);
//		currentDraft.addSelection(dSelection);
//		
//	}									
//
//	@Given("the player preplaces domino {int} at its initial position")
//	public void the_player_preplaces_domino_at_its_initial_position(Integer domID) { 
//		
//		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
//		
//		Player playerToPrePlaceDomino = game.getNextPlayer();
//		Domino dominoToPrePlace = KDController.getdominoByID(domID);
//		Kingdom currentPlayerKingdom = playerToPrePlaceDomino.getKingdom();
//		
//		DominoInKingdom dInKingdom = new DominoInKingdom(0, 0, currentPlayerKingdom, dominoToPrePlace);
//		dominoToPrePlace.setStatus(DominoStatus.ErroneouslyPreplaced);
//	
//		game.getCurrentDraft().removeIdSortedDomino(dominoToPrePlace);
//		
//	}
//
//	@When("the player attempts to discard the selected domino")
//	public void the_player_attempts_to_discard_the_selected_domino() {
//		
//		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
//		Player playerToDiscardDomino = game.getNextPlayer();
//		
//		try {
//			
//			KDController.discardDomino(playerToDiscardDomino);
//			
//		} catch (Exception e) {
//			
//			e.printStackTrace();
//			
//		}
//		
//	}
//
//	@Then("domino {int} shall have status {string}")
//	public void domino_shall_have_status(Integer domID, String domStatus) {
//		DominoStatus actualStatus = KDController.getdominoByID(domID).getStatus();
//		DominoStatus expectedStatus = sharedCucumberMethods.getDominoStatus(domStatus);
//		assertEquals(expectedStatus, actualStatus);
//	}
//
//}
