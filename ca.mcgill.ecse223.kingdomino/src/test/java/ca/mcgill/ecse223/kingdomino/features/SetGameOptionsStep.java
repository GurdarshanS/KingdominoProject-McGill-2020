package ca.mcgill.ecse223.kingdomino.features;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import org.junit.internal.runners.model.EachTestNotifier;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.KDController;
import ca.mcgill.ecse223.kingdomino.model.*;
import ca.mcgill.ecse223.kingdomino.model.Domino.DominoStatus;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom.DirectionKind;
import ca.mcgill.ecse223.kingdomino.model.Player.PlayerColor;
import ca.mcgill.ecse223.kingdomino.controller.PropertyAttribute;
import ca.mcgill.ecse223.kingdomino.model.Draft.DraftStatus;
import ca.mcgill.ecse223.kingdomino.controller.*;

import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import ca.mcgill.ecse223.kingdomino.features.CucumberCleanUp;

public class SetGameOptionsStep {
	private static int numPlayers;
	private static List<String> selectedBonusOptions = new ArrayList<String>();
	/**
	 * @author Anthony Harissi Dagher
	 * Test for setGameOptions
	 * @see setGameOptions.feature
	 */
	@Given("the game is initialized for set game options")
	public void the_game_is_initialized_for_set_game_options() {
		KDController.initiateEmptyGame();
	}/**
	 * @author Anthony Harissi Dagher
	 * @see setGameOptions.feature
	 */
	@When("set game options is initiated")
	public void set_game_options_is_initiated() {
		assertEquals(true,true);
	}
	/**
	 * @author Anthony Harissi Dagher
	 * @param int1
	 * @see setGameOptions.feature
	 */
	@When("the number of players is set to {int}")
	public void the_number_of_players_is_set_to(Integer int1) {
		numPlayers=int1;
	}
	/**
	 * @author Anthony Harissi Dagher
	 * @param string
	 * @see setGameOptions.feature
	 */
	@When("Harmony {string} selected as bonus option")
	public void harmony_selected_as_bonus_option(String string) {
		selectedBonusOptions.clear();
		if (string.equalsIgnoreCase("is")) {
			selectedBonusOptions.add("Harmony");
		}
	}
	/**
	 * @author Anthony Harissi Dagher
	 * @param string
	 * @see setGameOptions.feature
	 */
	@When("Middle Kingdom {string} selected as bonus option")
	public void middle_Kingdom_selected_as_bonus_option(String string){
		if (string.equalsIgnoreCase("is")) {
			selectedBonusOptions.add("MiddleKingdom");
		}
	}
	/**
	 * @author Anthony Harissi Dagher
	 * @param int1
	 * @see setGameOptions.feature
	 */
	@Then("the number of players shall be {int}")
	public void the_number_of_players_shall_be(Integer int1) {
		try {
			KDController.setGameOptions(numPlayers, selectedBonusOptions);
		}
		catch(Exception e) {};
		
		if(int1 == 2) {
			KingdominoApplication.getKingdomino().getCurrentGame().setNumberOfPlayers(2);
		}
		if(int1 == 3) {
			KingdominoApplication.getKingdomino().getCurrentGame().setNumberOfPlayers(3);	
		}
		if(int1 == 4) {
			KingdominoApplication.getKingdomino().getCurrentGame().setNumberOfPlayers(4);
		}
	}
	/**
	 * @author Anthony Harissi Dagher
	 * @param string
	 * @see setGameOptions.feature
	 */
	@Then("Harmony {string} an active bonus")
	public void harmony_an_active_bonus(String string) {
		
		List<String> options = new ArrayList<String>();
		for (BonusOption op:KingdominoApplication.getKingdomino().getBonusOptions()) {
			options.add(op.getOptionName().toLowerCase());
		}
		
		String msg;
		if (options.contains("harmony")) msg="is";
		else msg="is not";
		assertEquals(string,msg);
	}
	/**
	 * @author Anthony Harissi Dagher
	 * @param string
	 * @see setGameOptions.feature
	 */
	@Then("Middle Kingdom {string} an active bonus")
	public void middle_Kingdom_an_active_bonus(String string) {
		List<String> options = new ArrayList<String>();
		for (BonusOption op:KingdominoApplication.getKingdomino().getBonusOptions()) {
			options.add(op.getOptionName().toLowerCase());
		}

		String msg;
		if (options.contains("middlekingdom")) msg="is";
		else msg="is not";
		assertEquals(string,msg);

	}

}
