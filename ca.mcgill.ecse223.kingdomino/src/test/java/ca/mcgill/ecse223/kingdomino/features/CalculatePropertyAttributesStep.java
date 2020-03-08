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
import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import ca.mcgill.ecse223.kingdomino.features.CucumberCleanUp;


public class CalculatePropertyAttributesStep {
	/**
	 * These methods calculates Propety Attributes
	 * @see CalculatePropertyAttributes.feature
	 * @author Jing Han 260528152
	 */
	@Given("the game is initialized for calculate property attributes")
	public static void game_initialized_for_calculate_property_attributes() {
		KDController.initiateEmptyGame();
	}
	
	@When("calculate property attributes is initiated")
	public static void initiate_calculate_property() {
		Player player = KingdominoApplication.getKingdomino().getCurrentGame().getNextPlayer();
		KDController.identifyAllProperty(player);
	}
	
	@Then("the player shall have a total of {int} properties")
	public static void the_player_shall_have_x_number_properties(Integer expectedPropertyNum) {
		Player player = KingdominoApplication.getKingdomino().getCurrentGame().getNextPlayer();
		List<Property> p = player.getKingdom().getProperties();
		int actualNum=p.size();
		assertEquals(expectedPropertyNum.intValue(),actualNum);
	}
	
	@Then("the player shall have properties with the following attributes:")
	public static void player_has_properties_with_following_attributes(io.cucumber.datatable.DataTable dataTable) {
		
		Player player = KingdominoApplication.getKingdomino().getCurrentGame().getNextPlayer();
		List<PropertyAttribute> pa = KDController.getAllPropertyAttributes(player);
		
		List<Map<String, String>> valueMaps = dataTable.asMaps();
		
		List<Integer> expectedSize = new ArrayList<Integer>();
		List<Integer> expectedCrown = new ArrayList<Integer>();
		List<String> expectedTerrains = new ArrayList<String>();
	
		
		for (Map<String, String> map : valueMaps) {
			String propType = map.get("type");
			int propSize = Integer.parseInt(map.get("size"));
			int propCrown= Integer.parseInt(map.get("crowns"));
			
			expectedTerrains.add(propType);
			expectedCrown.add(propCrown);
			expectedSize.add(propSize);
		}
		
		if (expectedSize.size()!=pa.size()) {
			assertEquals(true,false);
		}
		
		else {
			
			List<Integer> ignoreIndex=new ArrayList<Integer>();
			for (int i=0;i<expectedSize.size();i++) {
				
					int currentSize=expectedSize.get(i);
					int currentCrown=expectedCrown.get(i);
					String currentTerrain=expectedTerrains.get(i);
					
					int num_match=0;
					
					for (int j=0;j<pa.size();j++) {
						if (!ignoreIndex.contains(j)) {
							int testSize=pa.get(j).getSize();
							int testCrown=pa.get(j).getCrown();
							String testTerrain=sharedCucumberMethods.getStringByTerrainType(pa.get(j).getTerrain());
							System.out.println("expected index: "+ i+" --- terrain: "+currentTerrain+" --- size: "+currentSize+" --- crown: "+currentCrown);
							System.out.println("test     index: "+j+" --- terrain: "+testTerrain+" --- size: "+testSize+" --- crown: "+testCrown);
							if (currentTerrain.equalsIgnoreCase(testTerrain) && testSize==currentSize && testCrown==currentCrown) {
								num_match++;
								ignoreIndex.add(j);
								System.out.println("match!");
								break;
							}
						}
					}
					if (num_match!=1) assertEquals(true,false);
					
					System.out.println("===========================================");		
			}

		}
	}
	
	 @Given("the player's kingdom also includes the domino {int} at position {int}:{int} with the direction {string}")
	 public static void player_kingdom_also_includes(Integer id, Integer posX, Integer posY, String direction) {
		Player player = KingdominoApplication.getKingdomino().getCurrentGame().getNextPlayer();
		Domino dominoToPlace = KDController.getdominoByID(id);
		KDController.prePlaceDomino(player, dominoToPlace, posX, posY, direction);
	 }

}
