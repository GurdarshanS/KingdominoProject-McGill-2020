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

public class IdentifyPropertiesStep {
	
	/**
	 * These methods check the Property 
	 * identifications of a player's kingdom
	 * @see IdentifyProperties.feature
	 * @author Eric Guan 260930210
	 */
	
	@Given("the game is initialized for identify properties")
	public static void initialize_game_for_id_property() {
		KDController.initiateEmptyGame();
	}
	
	@When("the properties of the player are identified")
	public static void when_properties_are_identified() {
		Player player = KingdominoApplication.getKingdomino().getCurrentGame().getNextPlayer();
		KDController.identifyAllProperty(player);
	}

	@Then("the player shall have the following properties:")
	public static void the_kingdom_shall_have_following_properties(io.cucumber.datatable.DataTable dataTable) {
		
		boolean match=true;
		
		List<Map<String, String>> valueMaps = dataTable.asMaps();
		List<int[]> expectedIds = new ArrayList<int[]>();
		List<String> expectedTerrains = new ArrayList<String>();
		for (Map<String, String> map : valueMaps) {
			// Get values from cucumber table
			String propType = map.get("type");
			String propIds = map.get("dominoes");
			int [] numIds=sharedCucumberMethods.str2int(propIds);
			
			expectedIds.add(numIds);
			expectedTerrains.add(propType);
		}
		
		Player player=KingdominoApplication.getKingdomino().getCurrentGame().getNextPlayer();
		List<Property> allProperty = KDController.getAllProperty(player);
		
		if (allProperty.size()!=expectedTerrains.size()) {
			match=false;
		}
		else {	
			for (int i=0;i<expectedIds.size();i++) {
				match=true;
				int num_match=0;
				TerrainType testTerrain=sharedCucumberMethods.getTerrainTypeByFullString(expectedTerrains.get(i));
				int[] testIds=expectedIds.get(i);
				
				for (int j=0;j<allProperty.size();j++) {
					Property testProp=allProperty.get(j);
					if (KDController.checkPropertyMatch(testTerrain, testIds, testProp)) {
						num_match+=1;
					}
				}
				if (num_match!=1) {
					match=false;
					break;
				}
			}
		}
		
		assertEquals(true,match);
	}

}
