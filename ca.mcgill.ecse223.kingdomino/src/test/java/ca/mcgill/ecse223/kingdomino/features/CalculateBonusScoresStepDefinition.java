package ca.mcgill.ecse223.kingdomino.features;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.KDController;
import ca.mcgill.ecse223.kingdomino.model.BonusOption;
import ca.mcgill.ecse223.kingdomino.model.Domino;
import ca.mcgill.ecse223.kingdomino.model.Domino.DominoStatus;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom.DirectionKind;
import ca.mcgill.ecse223.kingdomino.model.Game;
import ca.mcgill.ecse223.kingdomino.model.Kingdom;
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
	
	@Given("the player's kingdom also includes the domino {int} at position {int}:{int} with the direction {string}")
	public void the_player_s_kingdom_also_includes_the_domino_at_position_with_the_direction(Integer int1, Integer int2, Integer int3, String string) {
	    
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		Player player = game.getNextPlayer();		
		Kingdom kingdom=player.getKingdom();
		
		Domino dominoToPlace = getdominoByID(int1);
		
		dominoToPlace.setStatus(DominoStatus.PlacedInKingdom);
		DominoInKingdom dominoInKingdom = new DominoInKingdom(int2, int3, kingdom, dominoToPlace);
		dominoInKingdom.setDirection(getDirectionKindByFullString(string));
	}
	
	@Given("Middle Kingdom is selected as bonus option")
	public void middle_Kingdom_is_selected_as_bonus_option() {
		
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
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		Player player = game.getNextPlayer();
		
		for (Map<String, String> map : dataMap) {
			
			Integer domId = Integer.decode(map.get("id"));
			Integer domX = Integer.decode(map.get("posx"));
			Integer domY = Integer.decode(map.get("posy"));
			String domDir = map.get("dominodir");
			
			Domino dominoToPlace = getdominoByID(domId);
			dominoToPlace.setStatus(DominoStatus.PlacedInKingdom);
			Kingdom kingdom=player.getKingdom();
			DominoInKingdom dInK = new DominoInKingdom(domX,domY,kingdom,dominoToPlace);
			dInK.setDirection(getDirectionKindByFullString(domDir));
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
	
	private Domino getdominoByID(int id) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		for (Domino domino : game.getAllDominos()) {
			if (domino.getId() == id) {
				return domino;
			}
		}
		throw new java.lang.IllegalArgumentException("Domino with ID " + id + " not found.");
	}
}
