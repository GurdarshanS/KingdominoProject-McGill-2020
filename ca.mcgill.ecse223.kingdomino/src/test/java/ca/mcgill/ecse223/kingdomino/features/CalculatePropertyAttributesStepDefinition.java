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
	
	@Given("the following dominoes are present in a player's kingdom:")
	public void the_following_dominoes_are_present_in_a_player_s_kingdom(io.cucumber.datatable.DataTable dataTable) {
	    // Write code here that turns the phrase above into concrete actions
	    // For automatic transformation, change DataTable to one of
	    // E, List<E>, List<List<E>>, List<Map<K,V>>, Map<K,V> or
	    // Map<K, List<V>>. E,K,V must be a String, Integer, Float,
	    // Double, Byte, Short, Long, BigInteger or BigDecimal.
	    //
	    // For other transformations you can register a DataTableType.
	    throw new cucumber.api.PendingException();
	}

	@When("calculate property attributes is initiated")
	public void calculate_property_attributes_is_initiated() {
		
	    Player player = KingdominoApplication.getKingdomino().getCurrentGame().getNextPlayer();
	    
	    for(int index = 0; index < player.getKingdom().getProperties().size(); index++) {
	    	
	    	KDController.propertySize(player, index);
	    	KDController.getPropertyCrown(player, index);
	    	
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
		List<int[]> mapPropSize = new ArrayList<int[]>();
		List<int[]> mapPropCrowns = new ArrayList<int[]>();
		List<String> mapPropType = new ArrayList<String>();
		
		for (Map<String, String> map : dataMap) {
			
			String propSize = map.get("size");
			String propCrowns = map.get("crowns");
			String propType = map.get("type");
			int[] size = str2int(propSize);
			int[] crowns = str2int(propCrowns);
			
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
			if (prop.getScore() != mapPropSize.get(i)[0]) {
				
				same = false;
			}
			
			if (prop.getCrowns() != mapPropCrowns.get(i)[0]) {
				
				same = false;
			}
		}
		assertEquals(true,same);
	}
	private static int[] str2int(String str) {
		String splits []= str.split(",");
		int [] num = new int[splits.length];
		for (int i=0;i<splits.length;i++) {
			num[i]=Integer.parseInt(splits[i]);
		}
		return num;
	}
}
