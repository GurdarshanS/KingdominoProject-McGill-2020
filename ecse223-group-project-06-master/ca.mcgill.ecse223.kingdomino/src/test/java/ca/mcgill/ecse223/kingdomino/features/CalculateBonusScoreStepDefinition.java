package ca.mcgill.ecse223.kingdomino.features;

import java.util.List;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.KDController;
import ca.mcgill.ecse223.kingdomino.model.BonusOption;
import ca.mcgill.ecse223.kingdomino.model.Game;
import ca.mcgill.ecse223.kingdomino.model.Kingdomino;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CalculateBonusScoreStepDefinition {
	@Given("the game is initialized for calculate bonus scores")
	public void the_game_is_initialized_for_calculate_bonus_scores() {

		KDController.initializeGame();
	}

	@Given("Middle Kingdom is selected as bonus option")
	public void middle_Kingdom_is_selected_as_bonus_option() {
		
		List<BonusOption> bonus;
		bonus = KDController.getBonusOptions();
		bonus.toArray();
		
		for (int i = 0; i < bonus.toArray().length; i++) {
			
			if (bonus.toArray()[i].toString().equals("Middle Kingdom")) {
				
				Game game = KingdominoApplication.getKingdomino().getCurrentGame();
				
			}
		}
	}

	@Given("the player's kingdom also includes the domino {int} at position {int}:{int} with the direction {string}")
	public void the_player_s_kingdom_also_includes_the_domino_at_position_with_the_direction(Integer int1, Integer int2, Integer int3, String string) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@When("calculate bonus score is initiated")
	public void calculate_bonus_score_is_initiated() {
	    
	}

	@Then("the bonus score should be {int}")
	public void the_bonus_score_should_be(Integer int1) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Given("Harmony is selected as bonus option")
	public void harmony_is_selected_as_bonus_option() {
	   
		boolean harmony = false;
		List<BonusOption> bonus;
		bonus = KDController.getBonusOptions();
		bonus.toArray();
		
		for (int i = 0; i < bonus.toArray().length; i++) {
			
			if (bonus.toArray()[i].toString().equals("Harmony")) {
				
				harmony = true;
			}
		}
	}
}
