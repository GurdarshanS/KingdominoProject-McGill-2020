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
//public class MoveCurrentDominoStep {
//	
//	/**
//	 * 
//	 *  These methods allow a player to move
//	 *  their selected domino, from the current
//	 *  draft, around (up, left, down, and right)
//	 *  their kingdom if and only if the move 
//	 *  respects the grid size. 
//	 * 
//	 * 	@see MoveCurrentDomino.feature
//	 *  @author Massimo Vadacchino 260928064
//	 * 
//	 */
//	
//	@When("{string} removes his king from the domino {int}")
//	public void removes_his_king_from_the_domino(String string, Integer int1) {
//	
//		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
//		
//		Player player = sharedCucumberMethods.getPlayerByColor(string);
//		Domino domino = KDController.getdominoByID(int1);
//		Kingdom currentPlayerKingdom = player.getKingdom();
//		
//		DominoInKingdom dInKingdom = new DominoInKingdom(0, 0, currentPlayerKingdom, domino);
//		domino.setStatus(DominoStatus.ErroneouslyPreplaced);
//		game.getCurrentDraft().removeIdSortedDomino(domino);
//		
//	}
//
//	@Then("domino {int} should be tentative placed at position {int}:{int} of {string}'s kingdom with ErroneouslyPreplaced status")
//	public void domino_should_be_tentative_placed_at_position_of_s_kingdom_with_ErroneouslyPreplaced_status(Integer int1, Integer int2, Integer int3, String string) {
//	   
//		Domino domino = KDController.getdominoByID(int1);
//		Player player = sharedCucumberMethods.getPlayerByColor(string);
//		
//		DominoInKingdom dInKingdom = (DominoInKingdom) player.getKingdom().getTerritory(player.getKingdom().getTerritories().size()-1);
//		Integer xPos = dInKingdom.getX();
//		Integer yPos = dInKingdom.getY();
//		
//		assertEquals(int2, xPos);
//		assertEquals(int3, yPos);
//		assertEquals(DominoStatus.ErroneouslyPreplaced, dInKingdom.getDomino().getStatus());
//		
//	}
//
//	@When("{string} requests to move the domino {string}")
//	public void requests_to_move_the_domino(String string, String string2) {
//	  
//		String rotation = string2;
//		Player player = sharedCucumberMethods.getPlayerByColor(string);
//		DominoInKingdom dInKingdom = (DominoInKingdom) player.getKingdom().getTerritory(player.getKingdom().getTerritories().size() -1);
//
//		try {
//			
//			KDController.moveCurrentDomino(player, dInKingdom, rotation);
//			
//		}
//		
//		catch(Exception e) {
//			
//			e.printStackTrace();
//			
//		}
//		
//	}
//
//	@Then("the domino {int} should be tentatively placed at position {int}:{int} with direction {string}")
//	public void the_domino_should_be_tentatively_placed_at_position_with_direction(Integer int1, Integer int2, Integer int3, String string) {
//	    
//		Domino domino = KDController.getdominoByID(int1);
//		
//		DominoInKingdom dInKingdom = (DominoInKingdom) domino.getDominoSelection().getPlayer().getKingdom().getTerritory(domino.getDominoSelection().getPlayer().getKingdom().getTerritories().size()-1);
//		Integer xPos = dInKingdom.getX();
//		Integer yPos = dInKingdom.getY();
//		DirectionKind dKind = dInKingdom.getDirection();
//		
//		assertEquals(int2, xPos);
//		assertEquals(int3, yPos);
//		
//	}
//
//	@Then("the new status of the domino is {string}")
//	public void the_new_status_of_the_domino_is(String string) {
//	   
//		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
//		Player player = game.getNextPlayer();
//		
//		DominoInKingdom dInKingdom = (DominoInKingdom) player.getKingdom().getTerritory(player.getKingdom().getTerritories().size()-1);
//		DominoStatus dStatus = dInKingdom.getDomino().getStatus();
//		
//		assertEquals(sharedCucumberMethods.getDominoStatusCapital(string), dStatus);
//		
//	}
//
//	@Given("{string}'s kingdom has following dominoes")
//	public void the_s_kingdom_has_following_dominoes(String string, io.cucumber.datatable.DataTable dataTable) {
//	   
//		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
//		List<Map<String, String>> valueMaps = dataTable.asMaps();
//		
//		for (Map<String, String> map : valueMaps) {
//			
//			// Get values from cucumber table
//			Integer id = Integer.decode(map.get("id"));
//			DirectionKind dir = sharedCucumberMethods.getDirection(map.get("dir"));
//			Integer posx = Integer.decode(map.get("x"));
//			Integer posy = Integer.decode(map.get("y"));
//
//			// Add the domino to a player's kingdom
//			Domino dominoToPlace = KDController.getdominoByID(id);
//			Kingdom kingdom = sharedCucumberMethods.getPlayerByColor(string).getKingdom();
//			DominoInKingdom domInKingdom = new DominoInKingdom(posx, posy, kingdom, dominoToPlace);
//			domInKingdom.setDirection(dir);
//			dominoToPlace.setStatus(DominoStatus.PlacedInKingdom);
//			
//		}
//	}
//
//	@Then("the domino {int} is still tentatively placed at position {int}:{int}")
//	public void the_domino_is_still_tentatively_placed_at_position(Integer int1, Integer int2, Integer int3) {
//	    
//		Domino domino = KDController.getdominoByID(int1);
//		
//		DominoInKingdom dInKingdom = (DominoInKingdom) domino.getDominoSelection().getPlayer().getKingdom().getTerritory(domino.getDominoSelection().getPlayer().getKingdom().getTerritories().size()-1);
//		Integer xPos = dInKingdom.getX();
//		Integer yPos = dInKingdom.getY();
//		
//		assertEquals(int2, int2);
//		assertEquals(int3, int3);
//		
//	}
//
//	@Then("the domino should still have status {string}")
//	public void the_domino_should_still_have_status(String string) {
//	 
//		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
//		Player player = game.getNextPlayer();
//		
//		Domino domino = player.getDominoSelection().getDomino();
//		
//		DominoInKingdom dInKingdom = (DominoInKingdom) domino.getDominoSelection().getPlayer().getKingdom().getTerritory(domino.getDominoSelection().getPlayer().getKingdom().getTerritories().size()-1);
//		DominoStatus dStatus = domino.getStatus();
//		
//		assertEquals(sharedCucumberMethods.getDominoStatusCapital(string), dStatus);
//		
//	}
//
//}
