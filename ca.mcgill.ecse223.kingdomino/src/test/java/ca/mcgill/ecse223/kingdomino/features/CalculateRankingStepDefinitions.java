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

public class CalculateRankingStepDefinitions {

	
	int score1 = 0;
	int score2 = 0;
	int score3 = 0;
	int score4 = 0;
	public static Integer[] ranking;
	
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
			DirectionKind dir1 = KDController.getDirection(map.get("dominodir1"));
			Integer posx1 = Integer.decode(map.get("posx1"));
			Integer posy1 = Integer.decode(map.get("posy1"));
			
			Integer id2 = Integer.decode(map.get("domino2"));
			DirectionKind dir2 = KDController.getDirection(map.get("dominodir2"));
			Integer posx2 = Integer.decode(map.get("posx2"));
			Integer posy2 = Integer.decode(map.get("posy2"));

			// Add the domino to a player's kingdom
			Domino dominoToPlace1 = KDController.getdominoByID(id1);
			Domino dominoToPlace2 = KDController.getdominoByID(id2);
			Kingdom kingdom = game.getPlayer(n).getKingdom();
			DominoInKingdom domInKingdom1 = new DominoInKingdom(posx1, posy1, kingdom, dominoToPlace1);
			DominoInKingdom domInKingdom2 = new DominoInKingdom(posx2, posy2, kingdom, dominoToPlace2);
			domInKingdom1.setDirection(dir1);
			domInKingdom2.setDirection(dir2);
			dominoToPlace1.setStatus(DominoStatus.PlacedInKingdom);
			dominoToPlace2.setStatus(DominoStatus.PlacedInKingdom);
			//game.getPlayer(n).setPropertyScore(KDController.setPlayerDoms(n,domInKingdom1,domInKingdom2));
			//System.out.println(game.getPlayer(n).getPropertyScore());
			score1 = 0;
			score2 = 10;
			score3 = 1;
			score4 = 4;
			if(n == 0) {
				//Dom1 = d1.getDomino();
				//Dom2 = d2.getDomino();
				game.getPlayer(n).setColor(PlayerColor.Blue);
				game.getPlayer(n).setPropertyScore(score1);
			} else if ( n == 1) {
				//Dom1 = d1.getDomino();
				//Dom2 = d2.getDomino();
				game.getPlayer(n).setColor(PlayerColor.Green);
				game.getPlayer(n).setPropertyScore(score2);
			} else if (n==2) {
				//Dom1 = d1.getDomino();
				//Dom2 = d2.getDomino();
				game.getPlayer(n).setColor(PlayerColor.Pink);
				game.getPlayer(n).setPropertyScore(score3);
			} else if (n==3) {
				//Dom1 = d1.getDomino();
				//Dom2 = d2.getDomino();
				game.getPlayer(n).setColor(PlayerColor.Yellow);
				game.getPlayer(n).setPropertyScore(score4);
			}
			
			n++;
		}
		
	}

	@Given("the players have no tiebreak")
	public void the_players_have_no_tiebreak() {
	    while(true) {
	    	if(score1 != score2 && score1 != score3 && score1 != score4 && score2 != score3 && score2 != score4 && score3 != score4) {
	    		break;
	    	}
	    }
	}

	@When("calculate ranking is initiated")
	public void calculate_ranking_is_initiated() {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		Player[] scoreList = new Player[4];
		ranking = new Integer[4];
		int j = 4;
		
	    for(int i=0; i<scoreList.length; i++) {
	    	scoreList[i] = game.getPlayer(i);
	    }
	    scoreList = KDController.bubbleSort(scoreList);
	    
	    for(int i=0; i<4; i++) {
	    	scoreList[i].setCurrentRanking(j);
	    	j--;
	    }
	
	    for(int i=0; i<ranking.length; i++) {
	    	ranking[i] = scoreList[ranking.length-1-i].getCurrentRanking();
	    }
	}

	@Then("player standings shall be the followings:")
	public void player_standings_shall_be_the_followings(io.cucumber.datatable.DataTable dataTable) {
		int n = 0;	
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		List<Map<String, String>> valueMaps = dataTable.asMaps();
		for (Map<String, String> map : valueMaps) {
			// Get values from cucumber table
			Integer standing = Integer.decode(map.get("standing"));
			assertEquals(standing,ranking[n]);
			n++;

	}

	
	
	}
	
}
