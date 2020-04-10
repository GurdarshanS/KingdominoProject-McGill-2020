package ca.mcgill.ecse223.kingdomino.features;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.*;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.KDController;
import ca.mcgill.ecse223.kingdomino.controller.View;
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
import ca.mcgill.ecse223.kingdomino.model.*;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CalculateRankingStep {
	private static Kingdomino kd = KingdominoApplication.getKingdomino();


	/**
	 * 
	 * 	These methods retrieves the values from the table and places the two specified dominos in the 
	 * kingdoms of the players. If there are no tie breaks, the methods will calculate the rankings
	 * based on the points of each player and then provide the calculated rankings.
	 *  
	 *  @see CalculateRanking.feature
	 *  @author Gurdarshan Singh 260927466
	 *  
	 */
	
	@Given("the game is initialized for calculate ranking")
	public void the_game_is_initialized_for_calculate_ranking() {
	    KDController.initiateEmptyGame();
	    
	    kd.setStateMachine();
		kd.getStateMachine().setGamestatus("ConfirmingLastChoice"); 						
	}



	@Given("the players have no tiebreak")
	public void the_players_have_no_tiebreak() {
		
		for (Player p:KingdominoApplication.getKingdomino().getCurrentGame().getPlayers()) {
			KDController.calculateIndividualPlayerScore(p);
		}
		
		KDController.calculatePlayerRanking();
		
		List<Integer> rankings=new ArrayList<Integer>();
		
		for (Player p:KingdominoApplication.getKingdomino().getCurrentGame().getPlayers()) {
			rankings.add(p.getCurrentRanking());
		}
		
		Set<Integer> uniqueRanks = new HashSet<Integer>(rankings);
		
		
		assertEquals(KingdominoApplication.getKingdomino().getCurrentGame().getPlayers().size(),uniqueRanks.size());
	}


	
	@Then("player standings shall be the followings:")

	public void player_standings_shall_be_the_followings(io.cucumber.datatable.DataTable dataTable) {
		List<Map<String, String>> valueMaps = dataTable.asMaps();
		
		HashMap<String, Integer> expectedRankings = new HashMap<String, Integer>();
		
		boolean match=true;
		
		for (Map<String, String> map : valueMaps) {
			
			PlayerColor color = KDController.retrieveColor(map.get("player"));
			Integer ranking = (Integer.decode(map.get("standing"))).intValue();
			expectedRankings.put(color.toString().toLowerCase(), ranking);
			
		}
		
		for (Player p:KingdominoApplication.getKingdomino().getCurrentGame().getPlayers()) {
			Integer actualRanking=p.getCurrentRanking();
			Integer shouldBeRanking=expectedRankings.get(p.getColor().toString().toLowerCase());
			if (actualRanking.intValue()!=shouldBeRanking.intValue()) {
				match=false;
				break;
			}
		}
		
		assertEquals(true,match);
	}	
}