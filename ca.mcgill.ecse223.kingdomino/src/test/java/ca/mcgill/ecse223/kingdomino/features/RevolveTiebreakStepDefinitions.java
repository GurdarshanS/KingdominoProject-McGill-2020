package ca.mcgill.ecse223.kingdomino.features;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.KDController;
import ca.mcgill.ecse223.kingdomino.model.Castle;
import ca.mcgill.ecse223.kingdomino.model.Domino;
import ca.mcgill.ecse223.kingdomino.model.Domino.DominoStatus;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom.DirectionKind;
import ca.mcgill.ecse223.kingdomino.model.Game;
import ca.mcgill.ecse223.kingdomino.model.Kingdom;
import ca.mcgill.ecse223.kingdomino.model.Kingdomino;
import ca.mcgill.ecse223.kingdomino.model.Player;
import ca.mcgill.ecse223.kingdomino.model.Player.PlayerColor;
import ca.mcgill.ecse223.kingdomino.model.TerrainType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class RevolveTiebreakStepDefinitions {
	
	/**
	 * 
	  * 	These methods retrieves the values from the table and places the two specified dominos in the 
	 * kingdoms of the players.The methods will calculate the rankings based on the points of each player 
	 * If there is a tie break, the given tie break will be solved using the biggest property check, the most
	 * amount of crowns or they will both achieve the same ranking. The rankings that were calculated
	 * will then be provided
	 *  
	 *  @see ResolveTiebreak.feature
	 *  @author Gurdarshan Singh 260927466
	 *  
	 */
	
	@Given("the game is initialized for resolve tiebreak")
	public void the_game_is_initialized_for_resolve_tiebreak() {
		KDController.initiateEmptyGame();
	}
	
	@Then("player standings should be the followings:")
	public void player_standings_should_be_the_followings(io.cucumber.datatable.DataTable dataTable) {
		int n = 0;
		int j = 3;
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		List<Map<String, String>> valueMaps = dataTable.asMaps();
		for (Map<String, String> map : valueMaps) {
			// Get values from cucumber table
			PlayerColor p = KDController.retrieveColor(map.get("player"));
			Integer standing = Integer.decode(map.get("standing"));
			assertEquals(standing,CalculateRankingStepDefinitions.ranking[n]);
			assertEquals(p,CalculateRankingStepDefinitions.colors[j]);
			n++;
			j--;

	}
	}
}
