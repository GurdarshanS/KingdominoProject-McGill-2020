package ca.mcgill.ecse223.kingdomino.features;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.KDController;
import ca.mcgill.ecse223.kingdomino.model.Player;
import ca.mcgill.ecse223.kingdomino.model.Property;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CalculatePropertyAttributesStepDefinition {
	
	@Given("the game is initialized for calculate property attributes")
	public void the_game_is_initialized_for_calculate_property_attributes() {

		KDController.initializeGame();
	}

	@When("calculate property attributes is initiated")
	public void calculate_property_attributes_is_initiated() {
		
	    Player player = KingdominoApplication.getKingdomino().getCurrentGame().getNextPlayer();
	    KDController.getAllProperty(player);
	    
	    for(int index = 0; index < player.getKingdom().getProperties().size(); index++) {
	    	
	    	Property property = KDController.getAllProperty(player).get(index);
	    	KDController.propertySize(player, property);
	    	KDController.getPropertyCrown(player, property);
	    	
	    }
	    
		KDController.getNumberOfProperties(player);
		KDController.getPropertyAttributes(player);
	}

	@Then("the player shall have a total of {int} properties")
	public void the_player_shall_have_a_total_of_properties(Integer int1) {
	    
		Player player = KingdominoApplication.getKingdomino().getCurrentGame().getNextPlayer();
		assertEquals(int1, KDController.getNumberOfProperties(player));
	}

	@Then("the player shall have properties with the following attributes:")
	public void the_player_shall_have_properties_with_the_following_attributes(io.cucumber.datatable.DataTable dataTable) {
		
		List<Map<String, String>> dataMap = dataTable.asMaps();
		List<Integer> mapPropSize = new ArrayList<Integer>();
		List<Integer> mapPropCrowns = new ArrayList<Integer>();
		List<String> mapPropType = new ArrayList<String>();
		
		for (Map<String, String> map : dataMap) {
			
			String propSize = map.get("size");
			String propCrowns = map.get("crowns");
			String propType = map.get("type");
			int size = Integer.parseInt(propSize);
			int crowns = Integer.parseInt(propCrowns);
			
			mapPropType.add(propType);
			mapPropSize.add(size);
			mapPropCrowns.add(crowns);
		}
		
		boolean same = false;
		
		Player player = KingdominoApplication.getKingdomino().getCurrentGame().getNextPlayer();
		
		if (KDController.getAllProperty(player).size()==mapPropType.size()) {
			
			same = true;
		}
		
		for (int i = 0; i < KDController.getAllProperty(player).size(); i++) {
			
			Property prop = KDController.getAllProperty(player).get(i);
			
			if (prop.getScore() != mapPropSize.get(i)) {
				
				same = false;
			}
			
			if (prop.getCrowns() != mapPropCrowns.get(i)) {
				
				same = false;
			}
		}
		assertEquals(true,same);
	}
}
