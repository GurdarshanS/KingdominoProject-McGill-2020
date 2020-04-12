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
import ca.mcgill.ecse223.kingdomino.controller.View;
import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import ca.mcgill.ecse223.kingdomino.features.CucumberCleanUp;

public class VerifyCastleAdjacencyStep {
	private static boolean castleOK;
	/**
	 * These methods checks if current domino 
	 * placement is adjacent to the castle
	 * @see VerifyCastleAdjacency.feature
	 * @author Jing Han 260528152
	 */
	
	@Given("the game is initialized for castle adjacency")
	public static void initialize_game_for_castle_adjacency() {
		KDController.initiateEmptyGame();
	}
	
	@When("check castle adjacency is initiated")
	public static void initiate_castle_adjacency_check() {
		Player player=KingdominoApplication.getKingdomino().getCurrentGame().getNextPlayer();

		List<KingdomTerritory> territories = player.getKingdom().getTerritories();
		if (territories.size()==1) {
			return;
		}
		
		DominoInKingdom dInKingdom = (DominoInKingdom) territories.get(territories.size()-1);
		castleOK=KDController.verifyCastleAdjacency(player, dInKingdom);
	}

	@Then("the castle\\/domino adjacency is {string}")
	public void get_castle_adjacency_verification_result(String expectedValidity) {
		String validity;
		if (castleOK) validity="valid";
		else validity="invalid";
		assertEquals(expectedValidity,validity);
	}

}
