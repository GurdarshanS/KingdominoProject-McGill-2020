package ca.mcgill.ecse223.kingdomino.features;

import java.util.List;

import ca.mcgill.ecse223.kingdomino.controller.KDController;
import ca.mcgill.ecse223.kingdomino.model.BonusOption;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CalculatePlayerScoreStepDefinition {
	
	@Given("the game is initialized for calculate player score")
	public void the_game_is_initialized_for_calculate_player_score() {

		KDController.initializeGame();
	}

	@Given("the game has {string} bonus option")
	public void the_game_has_bonus_option(String string) {
	    
		int total = 0;
		List<BonusOption> bonus = KDController.getBonusOptions();
		bonus.toArray();
		
		for (int i = 0; i < bonus.toArray().length; i++) {
			
			if (bonus.toArray()[i] != null) {
				total++;
			}
		}
		System.out.println("The Game has "+total+" bonus option(s).");
	}

	@When("calculate player score is initiated")
	public void calculate_player_score_is_initiated() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Then("the total score should be {int}")
	public void the_total_score_should_be(Integer int1) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}
}
