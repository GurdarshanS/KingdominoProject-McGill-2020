package ca.mcgill.ecse223.kingdomino.features;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.KDController;
import ca.mcgill.ecse223.kingdomino.model.BonusOption;
import ca.mcgill.ecse223.kingdomino.model.Game;
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
	    
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();			
		
		if(string.equalsIgnoreCase("Middle Castle")) {
				
			KDController.isCastleInMiddle(game.getNextPlayer());
		}
		
		if (string.equalsIgnoreCase("Harmony")) {
			
			KDController.isHarmony(game.getNextPlayer());
		}
	}

	@When("calculate player score is initiated")
	public void calculate_player_score_is_initiated() {
	    
		KDController.playerScore(KingdominoApplication.getKingdomino().getCurrentGame().getNextPlayer());
	}

	@Then("the total score should be {int}")
	public void the_total_score_should_be(Integer int1) {
	    
		assertEquals(int1, KingdominoApplication.getKingdomino().getCurrentGame().getNextPlayer().getTotalScore());
	}
}
