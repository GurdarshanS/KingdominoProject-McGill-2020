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


public class VerifyNeighborAdjacencyStep {
	public static boolean neighbors;
	
	/**
	 * These methods checks if current domino 
	 * has at least one adjacent tile with same terrain type
	 * @see VerifyNeighborAdjacency.feature
	 * @author Jing Han 260528152
	 */
		
	@When("the game is initialized for neighbor adjacency")
	public static void initialize_game_for_neighbor_adjacency() {
		KDController.initiateEmptyGame();
	}
	
	@When("check current preplaced domino adjacency is initiated")
	public static void initialize_neighbor_adjacency_verification() {
		Player player=KingdominoApplication.getKingdomino().getCurrentGame().getNextPlayer();
		View.printPlayerKingdom(player);

		List<KingdomTerritory> territories = player.getKingdom().getTerritories();
		if (territories.size()==1) {
			return;
		}
		
		DominoInKingdom dInKingdom = (DominoInKingdom) territories.get(territories.size()-1);
		neighbors=KDController.verifyNeighborAdjacencyLastTerritory(player, dInKingdom);
	}
	
	@Then("the current-domino\\/existing-domino adjacency is {string}")
	public static void get_neighbor_adjacency_verification_result(String expectedValidity) {
		String validity;
		if (neighbors) validity="valid";
		else validity="invalid";
		assertEquals(expectedValidity,validity);
	}

}
