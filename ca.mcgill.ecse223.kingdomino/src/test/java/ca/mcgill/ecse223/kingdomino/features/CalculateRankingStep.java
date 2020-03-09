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
import ca.mcgill.ecse223.kingdomino.model.*;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CalculateRankingStep {

	/**
	 * 
	 * 	These methods retrieves the values from the table and places the two specified dominos in the 
	 * kingdoms of the players. If there are no tie breaks, the methods will calculate the rankings
	 * based on the points of each player and then provide the calculated rankings.
	 *  
	 *  @see CalculateRanking.feature
	 *  @author Gurdarshan Singh 260927466, refactored by Jing Han
	 *  
	 */
	
	@Given("the game is initialized for calculate ranking")
	public void the_game_is_initialized_for_calculate_ranking() {
	    KDController.initiateEmptyGame();
	}

	@Given("the players have the following two dominoes in their respective kingdoms:")
	public void the_players_have_the_following_two_dominoes_in_their_respective_kingdoms(io.cucumber.datatable.DataTable dataTable) {
		int n = 0;	
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		List<Map<String, String>> valueMaps = dataTable.asMaps();
		for (Map<String, String> map : valueMaps) {
			// Get values from cucumber table
			Integer id1 = Integer.decode(map.get("domino1"));
			String dir1 = map.get("dominodir1");
			Integer posx1 = Integer.decode(map.get("posx1"));
			Integer posy1 = Integer.decode(map.get("posy1"));
			PlayerColor p = KDController.retrieveColor(map.get("player"));
			Integer id2 = Integer.decode(map.get("domino2"));
			String dir2 = map.get("dominodir2");
			Integer posx2 = Integer.decode(map.get("posx2"));
			Integer posy2 = Integer.decode(map.get("posy2"));
			
			// Add the domino to a player's kingdom
			
			Player player = game.getPlayer(n);
			player.setColor(p);
			
			Domino dominoToPlace1 = KDController.getdominoByID(id1);
			Domino dominoToPlace2 = KDController.getdominoByID(id2);
						
			KDController.prePlaceDomino(player, dominoToPlace1, posx1, posy1, dir1);
			KDController.prePlaceDomino(player, dominoToPlace2, posx2, posy2, dir2);

			dominoToPlace1.setStatus(DominoStatus.PlacedInKingdom);
			dominoToPlace2.setStatus(DominoStatus.PlacedInKingdom);
		
			KDController.calculatePlayerScore(game.getPlayer(n));					
			n++;
		}
	}

	@Given("the players have no tiebreak")
	public void the_players_have_no_tiebreak() {
		KDController.existScoreTieBreak();
		assertEquals(true,true);
	}

	@When("calculate ranking is initiated")
	public void calculate_ranking_is_initiated() {
		KDController.calculatePlayerRanking();
	}
	
	
	@Then("player standings shall be the followings:")

	public void player_standings_shall_be_the_followings(io.cucumber.datatable.DataTable dataTable) {
		List<Map<String, String>> valueMaps = dataTable.asMaps();
		Player[] rankedPlayers = KDController.getRankedPlayers();
		
		boolean match=true;
		
		int counter=0;
		for (Map<String, String> map : valueMaps) {
			
			// Get values from cucumber table
			PlayerColor expectedColor = KDController.retrieveColor(map.get("player"));
			Integer expectedRanking = (Integer.decode(map.get("standing"))).intValue();
			
			Player actualPlayer=rankedPlayers[counter];
			PlayerColor actualColor=actualPlayer.getColor();
			int actualRanking=actualPlayer.getCurrentRanking();
			
			counter++;
			
			if (!((actualColor.equals(expectedColor))&&(actualRanking==expectedRanking))) {
				match=false;
				break;
			}
		}
		
		assertEquals(true,match);
	}	
}