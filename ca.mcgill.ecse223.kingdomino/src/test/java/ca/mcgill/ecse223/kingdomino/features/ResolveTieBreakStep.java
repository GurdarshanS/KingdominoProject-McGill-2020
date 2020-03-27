//package ca.mcgill.ecse223.kingdomino.features;
//
//import static org.junit.Assert.assertEquals;
//
//import java.io.BufferedReader;
//import java.io.FileReader;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.List;
//import java.util.Map;
//
//import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
//import ca.mcgill.ecse223.kingdomino.controller.KDController;
//import ca.mcgill.ecse223.kingdomino.model.Castle;
//import ca.mcgill.ecse223.kingdomino.model.Domino;
//import ca.mcgill.ecse223.kingdomino.model.Domino.DominoStatus;
//import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom;
//import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom.DirectionKind;
//import ca.mcgill.ecse223.kingdomino.model.Game;
//import ca.mcgill.ecse223.kingdomino.model.Kingdom;
//import ca.mcgill.ecse223.kingdomino.model.Kingdomino;
//import ca.mcgill.ecse223.kingdomino.model.Player;
//import ca.mcgill.ecse223.kingdomino.model.Player.PlayerColor;
//import ca.mcgill.ecse223.kingdomino.model.TerrainType;
//import ca.mcgill.ecse223.kingdomino.model.*;
//import io.cucumber.java.en.Given;
//import io.cucumber.java.en.Then;
//import io.cucumber.java.en.When;
//
//public class ResolveTieBreakStep {
//	
//	/**
//	 * 
//	  * 	These methods retrieves the values from the table and places the two specified dominos in the 
//	 * kingdoms of the players.The methods will calculate the rankings based on the points of each player 
//	 * If there is a tie break, the given tie break will be solved using the biggest property check, the most
//	 * amount of crowns or they will both achieve the same ranking. The rankings that were calculated
//	 * will then be provided
//	 *  
//	 *  @see ResolveTiebreak.feature
//	 *  @author Gurdarshan Singh 260927466, refactored by Jing Han
//	 *  
//	 */
//	
//	@Given("the game is initialized for resolve tiebreak")
//	public static void inititialize_game_for_tiebreak() {
//		KDController.initiateEmptyGame();
//	}
//	
//	@Then("player standings should be the followings:")
//	public void player_standings_should_be_the_followings(io.cucumber.datatable.DataTable dataTable) {
//		List<Map<String, String>> valueMaps = dataTable.asMaps();
//		Player[] rankedPlayers = KDController.getRankedPlayers();
//		
//		boolean match=true;
//
//		int counter=0;
//		for (Map<String, String> map : valueMaps) {
//			
//			// Get values from cucumber table
//			PlayerColor expectedColor = KDController.retrieveColor(map.get("player"));
//			Integer expectedRanking = (Integer.decode(map.get("standing"))).intValue();
//			
//			Player actualPlayer=rankedPlayers[counter];
//			PlayerColor actualColor=actualPlayer.getColor();
//			int actualRanking=actualPlayer.getCurrentRanking();
//			
//			counter++;
//			
//			if (!((actualColor.equals(expectedColor))&&(actualRanking==expectedRanking))) {
//				match=false;
//				break;
//			}
//		}
//		
//		assertEquals(true,match);
//	}	
//
//}
