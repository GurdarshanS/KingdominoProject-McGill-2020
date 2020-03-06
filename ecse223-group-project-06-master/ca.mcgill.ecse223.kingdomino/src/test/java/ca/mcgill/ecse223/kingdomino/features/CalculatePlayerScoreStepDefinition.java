package ca.mcgill.ecse223.kingdomino.features;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
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
	    
		assertEquals(string,KDController.getBonusOptions().toString());
	}

	@When("calculate player score is initiated")
	public void calculate_player_score_is_initiated() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Then("the total score should be {int}")
	public void the_total_score_should_be(Integer int1) {
	    
		assertEquals(int1, KingdominoApplication.getKingdomino().getCurrentGame().getNextPlayer().getTotalScore());
	}
}
