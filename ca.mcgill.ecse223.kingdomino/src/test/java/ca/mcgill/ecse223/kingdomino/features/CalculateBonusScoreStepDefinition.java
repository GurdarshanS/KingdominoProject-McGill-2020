package ca.mcgill.ecse223.kingdomino.features;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.KDController;
import ca.mcgill.ecse223.kingdomino.model.BonusOption;
import ca.mcgill.ecse223.kingdomino.model.Game;
import ca.mcgill.ecse223.kingdomino.model.Kingdomino;
import ca.mcgill.ecse223.kingdomino.model.Player;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CalculateBonusScoreStepDefinition {
	
	@Given("the game is initialized for calculate bonus scores")
	public void the_game_is_initialized_for_calculate_bonus_scores() {

		KDController.initializeGame();
	}
	
	@Given("the game has {string} bonus option")
	public void the_game_has_bonus_option(String string) {
	    
		
		assertEquals(string,KDController.getBonusOptions().toString());
	}

	@Given("Middle Kingdom is selected as bonus option")
	public void middle_Kingdom_is_selected_as_bonus_option() {
		
		List<BonusOption> bonus;
		bonus = KDController.getBonusOptions();
		
		for (BonusOption bonusSelected: bonus) {
			
			if (bonusSelected.getOptionName().equals("Middle Kingdom")) {
				
				KDController.isCastleInMiddle(KingdominoApplication.getKingdomino().getCurrentGame().getNextPlayer());	
			}
		}
	}

	@When("calculate bonus score is initiated")
	public void calculate_bonus_score_is_initiated() {
			
		KDController.bonusScore(KingdominoApplication.getKingdomino().getCurrentGame().getNextPlayer());
	}

	@Then("the bonus score should be {int}")
	public void the_bonus_score_should_be(Integer int1) {
	    
		assertEquals(int1, KingdominoApplication.getKingdomino().getCurrentGame().getNextPlayer().getBonusScore());
	}

	@Given("Harmony is selected as bonus option")
	public void harmony_is_selected_as_bonus_option() {
	   
		List<BonusOption> bonus;
		bonus = KDController.getBonusOptions();
		
		for (BonusOption bonusSelected: bonus) {
			
			if (bonusSelected.getOptionName().equals("Harmony")) {
				
				KDController.isHarmony(KingdominoApplication.getKingdomino().getCurrentGame().getNextPlayer());	
			}
		}
	}
}
