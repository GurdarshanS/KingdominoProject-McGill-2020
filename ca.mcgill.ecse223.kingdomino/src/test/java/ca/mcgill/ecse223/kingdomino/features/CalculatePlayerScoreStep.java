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

public class CalculatePlayerScoreStep {
	
	 /**
	 * These methods calculates Player totalScore
	 * based on the Kingdom layout and bonus options
	 * @see CalculatePlayerScore.feature
	 * @author Eric Guan 260930210
	 */
	
	@Given("the game is initialized for calculate player score")
	public static  void initialize_game_for_calculate_player_score() {
		 KDController.initiateEmptyGame();
	}
	
	@Given("the game has {string} bonus option")
	public static void game_has_x_bonus_option(String option) {
		 Kingdomino kd = KingdominoApplication.getKingdomino();
		 kd.addBonusOption(option);
	}
	
	@When("calculate player score is initiated")
	public static void initiate_player_score_calculation() {
		 Player player = KingdominoApplication.getKingdomino().getCurrentGame().getNextPlayer();
		 KDController.calculateIndividualPlayerScore(player);
	}
	
	@Then("the total score should be {int}")
	
	public static void check_resulting_score(Integer expectedScore) {
		 Player player = KingdominoApplication.getKingdomino().getCurrentGame().getNextPlayer();
		 int totalScore=player.getTotalScore();
		 assertEquals(expectedScore.intValue(),totalScore);
	}

}
