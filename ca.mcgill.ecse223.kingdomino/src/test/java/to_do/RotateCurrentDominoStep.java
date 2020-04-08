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
//public class RotateCurrentDominoStep {
//	
//	/**
//	 * 
//	 *  These methods allow a player to rotate
//	 *  their selected domino, from the current
//	 *  draft, counter-clockwise and clockwise in
//	 *  their kingdom if and only if the rotation 
//	 *  respects the grid size. 
//	 * 
//	 * 	@see RotateCurrentDomino.feature
//	 *  @author Massimo Vadacchino 260928064
//	 * 
//	 */
//	
//	@Given("the game is initialized for rotate current domino")
//	public void the_game_is_initialized_for_move_current_domino() {
//	   
//		// Intialize empty game
//		KDController.initiateEmptyGame();
//		
//	}
//	
//	@When("{string} requests to rotate the domino with {string}")
//	public void requests_to_rotate_the_domino_with(String string, String string2) {
//	    
//		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
//		Player playerToRotateDomino = sharedCucumberMethods.getPlayerByColor(string);
//		DominoInKingdom newlyPrePlacedDomino = (DominoInKingdom) playerToRotateDomino.getKingdom().getTerritory(playerToRotateDomino.getKingdom().getTerritories().size()-1);
//		
//		try {
//			
//			KDController.rotateCurrentDomino(playerToRotateDomino, newlyPrePlacedDomino, string2);
//			
//		} catch (Exception e) {
//			
//			e.printStackTrace();
//		}
//		
//	}
//	
//	@Then("the domino {int} is still tentatively placed at {int}:{int} but with new direction {string}")
//	public void the_domino_is_still_tentatively_placed_at_but_with_new_direction(Integer int1, Integer int2, Integer int3, String string) {
//	  
//		Domino domino = KDController.getdominoByID(int1);
//		DominoInKingdom dInKingdom = (DominoInKingdom) domino.getDominoSelection().getPlayer().getKingdom().getTerritory(domino.getDominoSelection().getPlayer().getKingdom().getTerritories().size()-1);
//		
//		DirectionKind domDirection = dInKingdom.getDirection();
//		Integer xPos = dInKingdom.getX();
//		Integer yPos = dInKingdom.getY();
//		
//		assertEquals(sharedCucumberMethods.getDirection(string), domDirection);
//		assertEquals(int2, xPos);
//		assertEquals(int3, yPos);
//		
//	}
//	
//	@Then("the domino {int} should have status {string}")
//	public void the_domino_should_have_status(Integer int1, String string) {
//	   
//		Domino domino = KDController.getdominoByID(int1);
//		DominoStatus domStatus = domino.getStatus();
//		
//		assertEquals(sharedCucumberMethods.getDominoStatusCapital(string), domStatus);
//		
//	}
//	
//	@Then("domino {int} is tentatively placed at the same position {int}:{int} with the same direction {string}")
//	public void domino_is_tentatively_placed_at_the_same_position_with_the_same_direction(Integer int1, Integer int2, Integer int3, String string) {
//	   
//		Domino domino = KDController.getdominoByID(int1);
//		DominoInKingdom dInKingdom = (DominoInKingdom) domino.getDominoSelection().getPlayer().getKingdom().getTerritory(domino.getDominoSelection().getPlayer().getKingdom().getTerritories().size()-1);
//		
//		DirectionKind domDirection = dInKingdom.getDirection();
//		Integer xPos = dInKingdom.getX();
//		Integer yPos = dInKingdom.getY();
//		
//		assertEquals(sharedCucumberMethods.getDirection(string), domDirection);
//		assertEquals(int2, xPos);
//		assertEquals(int3, yPos);
//		
//	}
//	
//	@Then("domino {int} should still have status {string}")
//	public void domino_should_still_have_status(Integer int1, String string) {
//	   
//		Domino domino = KDController.getdominoByID(int1);
//		DominoStatus domStatus = domino.getStatus();
//		
//		assertEquals(sharedCucumberMethods.getDominoStatusCapital(string), domStatus);
//		
//	}
//
//}
