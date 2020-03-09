package ca.mcgill.ecse223.kingdomino.features;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.*;

import org.junit.internal.runners.model.EachTestNotifier;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.KDController;
import ca.mcgill.ecse223.kingdomino.model.*;
import ca.mcgill.ecse223.kingdomino.model.Domino.DominoStatus;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom.DirectionKind;
import ca.mcgill.ecse223.kingdomino.model.Player.PlayerColor;
import ca.mcgill.ecse223.kingdomino.controller.PropertyAttribute;
import ca.mcgill.ecse223.kingdomino.model.Draft.DraftStatus;

import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import ca.mcgill.ecse223.kingdomino.features.CucumberCleanUp;

public class PlaceDominoStep {
	
	/**
	 * 
	 *  These methods allow a player to place
	 *  their selected domino into their kingdom
	 *  if and only if it respects the kingdom size,
	 *  has a neighbour/next to the castle, does
	 *  not overlap with other dominoes, and has the
	 *  status "CorrectlyPreplaced". 
	 *  
	 *  @see PlaceDomino.feature
	 *  @author Massimo Vadacchino 260928064
	 * 
	 */
	
	@Given("the {string}'s kingdom has the following dominoes:")
	public void the_s_kingdom_has_the_following_dominoes(String string, io.cucumber.datatable.DataTable dataTable) {
		
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		List<Map<String, String>> valueMaps = dataTable.asMaps();
		
		for (Map<String, String> map : valueMaps) {
			
			// Get values from cucumber table
			Integer id = Integer.decode(map.get("domino"));
			DirectionKind dir = sharedCucumberMethods.getDirection(map.get("dominodir"));
			Integer posx = Integer.decode(map.get("posx"));
			Integer posy = Integer.decode(map.get("posy"));

			// Add the domino to a player's kingdom
			Domino dominoToPlace = KDController.getdominoByID(id);
			Kingdom kingdom = sharedCucumberMethods.getPlayerByColor(string).getKingdom();
			DominoInKingdom domInKingdom = new DominoInKingdom(posx, posy, kingdom, dominoToPlace);
			domInKingdom.setDirection(dir);
			dominoToPlace.setStatus(DominoStatus.PlacedInKingdom);
			
		}
	}
	
	@Given("domino {int} is in {string} status")
	public void domino_is_in_status(Integer int1, String string) {
	   
		Domino domino = KDController.getdominoByID(int1);
		DominoStatus domStatus = sharedCucumberMethods.getDominoStatusCapital(string);
		
		domino.setStatus(domStatus);
		
	}
	
	@When("{string} requests to place the selected domino {int}")
	public void requests_to_place_the_selected_domino(String string, Integer int1) {
	  
		Domino domino = KDController.getdominoByID(int1);
		Player player = sharedCucumberMethods.getPlayerByColor(string);
		
		try {
			
			KDController.placeDomino(player, domino);
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
	}
	
	@Then("{string}'s kingdom should now have domino {int} at position {int}:{int} with direction {string}")
	public void s_kingdom_should_now_have_domino_at_position_with_direction(String string, Integer int1, Integer int2, Integer int3, String string2) {
	   
		Player player = sharedCucumberMethods.getPlayerByColor(string);
		Domino domino = KDController.getdominoByID(int1);

		DominoInKingdom dInKingdom = (DominoInKingdom) player.getKingdom().getTerritory(player.getKingdom().getTerritories().size()-1);
		Integer xPos = dInKingdom.getX();
		Integer yPos = dInKingdom.getY();
		DirectionKind directionKind = dInKingdom.getDirection();
		
		assertEquals(int2, xPos);
		assertEquals(int3, yPos);
		assertEquals(sharedCucumberMethods.getDirection(string2), directionKind);
		
	}

}
