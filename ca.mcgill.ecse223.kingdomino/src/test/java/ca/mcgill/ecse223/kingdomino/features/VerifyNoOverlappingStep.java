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
import ca.mcgill.ecse223.kingdomino.development.View;
import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import ca.mcgill.ecse223.kingdomino.features.CucumberCleanUp;

public class VerifyNoOverlappingStep {
	public static boolean overlap;
	/**
	 * These methods checks if dominos overlap
	 * @see VerifyNoOverlapping.feature
	 * @author Jing Han 260528152
	 */
	
	@Given("the game is initialized to check domino overlapping")
	public void the_game_is_initialized_to_check_domino_overlapping() {
		KDController.initiateEmptyGame();
	}

	@When("check current preplaced domino overlapping is initiated")
	public void initiate_domino_overlap_verification() {
		Player player=KingdominoApplication.getKingdomino().getCurrentGame().getNextPlayer();
		View.printPlayerKingdom(player);

		List<KingdomTerritory> territories = player.getKingdom().getTerritories();
		if (territories.size()==1) {
			return;
		}
		
		DominoInKingdom dInKingdom = (DominoInKingdom) territories.get(territories.size()-1);
		overlap=KDController.verifyNoOverlapLastTerritory(player,dInKingdom);
	}

	@Then("the current-domino\\/existing-domino overlapping is {string}")
	public void get_domino_overlap_verification_result(String expectedValidity) {
		String validity;
		if (overlap) validity="valid";
		else validity="invalid";
		assertEquals(expectedValidity,validity);
	}

}
