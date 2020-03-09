package ca.mcgill.ecse223.kingdomino.features;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.*;

import org.junit.internal.runners.model.EachTestNotifier;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.KDController;
import ca.mcgill.ecse223.kingdomino.model.*;
import ca.mcgill.ecse223.kingdomino.model.Domino.DominoStatus;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom.DirectionKind;
import ca.mcgill.ecse223.kingdomino.model.Player.PlayerColor;
import ca.mcgill.ecse223.kingdomino.controller.PropertyAttribute;
import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import ca.mcgill.ecse223.kingdomino.features.CucumberCleanUp;

public class CalculateBonusScoresStep {
	
	 /**
	 * These methods calculates Player BonusScore
	 * based on the Kingdom layout and bonus options
	 * @see CalculateBonusScores.feature
	 * @author Eric Guan 260930210, refactored by Jing Han 260528152
	 */
	
	@Given("the game is initialized for calculate bonus scores")
	public static void initiate_game_for_calculate_bonus_score() {
		 KDController.initiateEmptyGame();
	}
	@Given("Middle Kingdom is selected as bonus option")
	public static void middle_kingdom_is_selected() {
		 Kingdomino kd = KingdominoApplication.getKingdomino();
		 kd.addBonusOption("MiddleKingdom");
	}
	
	@Given("Harmony is selected as bonus option")
	public static void harmony_is_selected() {
		 Kingdomino kd = KingdominoApplication.getKingdomino();
		 kd.addBonusOption("Harmony");
	}
	
	@When ("calculate bonus score is initiated")
	public static void initiate_bonus_score() {
		 Player player = KingdominoApplication.getKingdomino().getCurrentGame().getNextPlayer();
		 KDController.calculateBonusScore(player);
	}
	
	@Then("the bonus score should be {int}")
	public static void bonus_score_should_be(Integer expectedScore) {
		 Player player = KingdominoApplication.getKingdomino().getCurrentGame().getNextPlayer();
		 int actualScore=player.getBonusScore();
		 assertEquals(expectedScore.intValue(),actualScore);
	}

}
