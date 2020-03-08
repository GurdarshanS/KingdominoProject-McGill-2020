package ca.mcgill.ecse223.kingdomino.features;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.KDController;
import ca.mcgill.ecse223.kingdomino.model.Player;
import ca.mcgill.ecse223.kingdomino.model.Property;
import ca.mcgill.ecse223.kingdomino.model.TerrainType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class IdentifyPropertiesStepDefinition {
	
	@Given("the game is initialized for identify properties")
	public void the_game_is_initialized_for_identify_properties() {
	    
		KDController.initializeGame();
	}

	@When("the properties of the player are identified")
	public static void when_properties_are_identified() {
		
		KDController.identifyAllProperty(KingdominoApplication.getKingdomino().getCurrentGame().getNextPlayer());
	}

	@Then("the player shall have the following properties:")
	public void the_kingdom_shall_have_following_properties(io.cucumber.datatable.DataTable dataTable) {
		
		boolean match=true;
		
		List<Map<String, String>> valueMaps = dataTable.asMaps();
		List<int[]> expectedIds = new ArrayList<int[]>();
		List<String> expectedTerrains = new ArrayList<String>();
		for (Map<String, String> map : valueMaps) {
			// Get values from cucumber table
			String propType = map.get("type");
			String propIds = map.get("dominoes");
			int [] numIds = str2int(propIds);
			
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
				TerrainType testTerrain=getTerrainTypeByFullString(expectedTerrains.get(i));
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
	
	private static int[] str2int(String str) {
		String splits []= str.split(",");
		int [] num = new int[splits.length];
		for (int i=0;i<splits.length;i++) {
			num[i]=Integer.parseInt(splits[i]);
		}
		return num;
	}
	
private static TerrainType getTerrainTypeByFullString(String terrain) {
		
		if (terrain.equalsIgnoreCase("wheat")) return TerrainType.WheatField;
		if (terrain.equalsIgnoreCase("grass")) return TerrainType.Grass;
		if (terrain.equalsIgnoreCase("mountain")) return TerrainType.Mountain;
		if (terrain.equalsIgnoreCase("lake")) return TerrainType.Lake;
		if (terrain.equalsIgnoreCase("swamp")) return TerrainType.Swamp;
		if (terrain.equalsIgnoreCase("forest")) return TerrainType.Forest;
		else throw new IllegalArgumentException();

		}
}
