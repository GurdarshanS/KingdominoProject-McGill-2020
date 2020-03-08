package ca.mcgill.ecse223.kingdomino.features;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.KDController;
import ca.mcgill.ecse223.kingdomino.model.BonusOption;
import ca.mcgill.ecse223.kingdomino.model.Domino;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom.DirectionKind;
import ca.mcgill.ecse223.kingdomino.model.Game;
import ca.mcgill.ecse223.kingdomino.model.KingdomTerritory;
import ca.mcgill.ecse223.kingdomino.model.Kingdomino;
import ca.mcgill.ecse223.kingdomino.model.Player;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CalculateBonusScoresStepDefinition {
	
	@Given("the game is initialized for calculate bonus scores")
	public void the_game_is_initialized_for_calculate_bonus_scores() {

		KDController.initializeGame();
	}
	
	@Given("the player's kingdom also includes the following dominoes:")
	public void the_player_s_kingdom_also_includes_the_following_dominoes(io.cucumber.datatable.DataTable dataTable) {
		
		List<Map<String, String>> dataMap = dataTable.asMaps();
		List<Integer> dominoId = new ArrayList<Integer>();
		List<Integer> dominoX = new ArrayList<Integer>();
		List<Integer> dominoY = new ArrayList<Integer>();
		List<DirectionKind> dominoDir = new ArrayList<DirectionKind>();
		
		for (Map<String, String> map : dataMap) {
			
			String id = map.get("domino");
			String direction = map.get("dominodir");
			String x = map.get("posx");
			String y = map.get("posy");
			
			int domId = Integer.parseInt(id);
			int domX = Integer.parseInt(x);
			int domY = Integer.parseInt(y);
			DirectionKind domDir = getDirectionKindByFullString(direction);
			
			dominoX.add(domX);
			dominoId.add(domId);
			dominoY.add(domY);
			dominoDir.add(domDir);
		}
	}
	
	@Given("the following dominoes are present in a player's kingdom:")
	public void the_following_dominoes_are_present_in_a_player_s_kingdom(io.cucumber.datatable.DataTable dataTable) {
	   
		List<Map<String, String>> dataMap = dataTable.asMaps();
		List<Integer> dominoId = new ArrayList<Integer>();
		List<Integer> dominoX = new ArrayList<Integer>();
		List<Integer> dominoY = new ArrayList<Integer>();
		List<DirectionKind> dominoDir = new ArrayList<DirectionKind>();
		
		for (Map<String, String> map : dataMap) {
			
			String id = map.get("domino");
			String direction = map.get("dominodir");
			String x = map.get("posx");
			String y = map.get("posy");
			
			int domId = Integer.parseInt(id);
			int domX = Integer.parseInt(x);
			int domY = Integer.parseInt(y);
			DirectionKind domDir = getDirectionKindByFullString(direction);
			
			dominoX.add(domX);
			dominoId.add(domId);
			dominoY.add(domY);
			dominoDir.add(domDir);
		}
	}
	
	@Given("the game has {string} bonus option")
	public void the_game_has_bonus_option(String string) {
	    
		List<BonusOption> bonus;
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		bonus = KDController.getBonusOptions(game);
		
		for (int index = 0; index < bonus.size(); index++) {
			
			BonusOption bonusSelected = bonus.get(index);
			
			if(string.equals(bonusSelected.getOptionName())) {
				
				assertEquals(string,bonusSelected.toString());
			}
		}
	}
	
	@Given("the player's kingdom also includes the domino {int} at position {int}:{int} with the direction {string}")
	public void the_player_s_kingdom_also_includes_the_domino_at_position_with_the_direction(Integer int1, Integer int2, Integer int3, String string) {
	    
		List<Integer> dominoId = new ArrayList<Integer>();
		List<Integer> dominoX = new ArrayList<Integer>();
		List<Integer> dominoY = new ArrayList<Integer>();
		List<DirectionKind> dominoDir = new ArrayList<DirectionKind>();
		
		DirectionKind domDir = getDirectionKindByFullString(string);
		
		dominoId.add(int1);
		dominoX.add(int2);
		dominoY.add(int3);
		dominoDir.add(domDir);
		
	}
	
	@Given("Middle Kingdom is selected as bonus option")
	public void middle_Kingdom_is_selected_as_bonus_option() {
		
		List<BonusOption> bonus = KingdominoApplication.getKingdomino().getCurrentGame().getSelectedBonusOptions();
		//bonus.add(Middle Castle)
		KDController.isCastleInMiddle(KingdominoApplication.getKingdomino().getCurrentGame().getNextPlayer());
	}

	@When("calculate bonus score is initiated")
	public void calculate_bonus_score_is_initiated() {
			
		KDController.bonusScore(KingdominoApplication.getKingdomino().getCurrentGame().getNextPlayer());
	}

	@Then("the bonus score should be {int}")
	public void the_bonus_score_should_be(Integer int1) {
	    
		assertEquals(int1, KingdominoApplication.getKingdomino().getCurrentGame().getNextPlayer().getBonusScore());
	}
	
	@Given("the player's kingdom has the following dominoes:")
	public void the_player_s_kingdom_has_the_following_dominoes(io.cucumber.datatable.DataTable dataTable) {
		
		List<Map<String, String>> dataMap = dataTable.asMaps();
		List<Integer> dominoId = new ArrayList<Integer>();
		List<Integer> dominoX = new ArrayList<Integer>();
		List<Integer> dominoY = new ArrayList<Integer>();
		List<DirectionKind> dominoDir = new ArrayList<DirectionKind>();
		
		Player player = KingdominoApplication.getKingdomino().getCurrentGame().getNextPlayer();
		
		for (Map<String, String> map : dataMap) {
			
			String id = map.get("domino");
			String direction = map.get("dominodir");
			String x = map.get("posx");
			String y = map.get("posy");
			
			int domId = Integer.parseInt(id);
			int domX = Integer.parseInt(x);
			int domY = Integer.parseInt(y);
			DirectionKind domDir = getDirectionKindByFullString(direction);
			
			dominoX.add(domX);
			dominoId.add(domId);
			dominoY.add(domY);
			dominoDir.add(domDir);
	
		}
	}
	
	@Given("Harmony is selected as bonus option")
	public void harmony_is_selected_as_bonus_option() {
	   
		KDController.isHarmony(KingdominoApplication.getKingdomino().getCurrentGame().getNextPlayer());
	}
	
	private static DirectionKind getDirectionKindByFullString(String direction) {
		
		if (direction.equalsIgnoreCase("right")) return DirectionKind.Right;
		if (direction.equalsIgnoreCase("down")) return DirectionKind.Down;
		if (direction.equalsIgnoreCase("left")) return DirectionKind.Left;
		if (direction.equalsIgnoreCase("up")) return DirectionKind.Up;
		else throw new IllegalArgumentException();

	}
}
