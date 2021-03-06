package ca.mcgill.ecse223.kingdomino.features;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.KDController;
import ca.mcgill.ecse223.kingdomino.controller.KDQuery;
import ca.mcgill.ecse223.kingdomino.controller.View;
import ca.mcgill.ecse223.kingdomino.model.Castle;
import ca.mcgill.ecse223.kingdomino.model.Domino;
import ca.mcgill.ecse223.kingdomino.model.Domino.DominoStatus;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom.DirectionKind;
import ca.mcgill.ecse223.kingdomino.model.Game;
import ca.mcgill.ecse223.kingdomino.model.Gameplay;
import ca.mcgill.ecse223.kingdomino.model.Kingdom;
import ca.mcgill.ecse223.kingdomino.model.KingdomTerritory;
import ca.mcgill.ecse223.kingdomino.model.Kingdomino;
import ca.mcgill.ecse223.kingdomino.model.Player;
import ca.mcgill.ecse223.kingdomino.model.Player.PlayerColor;
import ca.mcgill.ecse223.kingdomino.model.TerrainType;
import ca.mcgill.ecse223.kingdomino.model.DominoSelection;
import ca.mcgill.ecse223.kingdomino.model.Draft;
import ca.mcgill.ecse223.kingdomino.model.Draft.DraftStatus;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;


public class SM_ResolveTieBreakStep {
	/**
	 * These methods checks for the state machine 
	 * transition for resolving tiebreak
	 * 
	 * @see ResolveTieBreak.feature
	 * @author Jing Han	260528152
	 */

		private static Kingdomino kd = KingdominoApplication.getKingdomino();
		
		@Given("the game is initialized for resolve tiebreak")
		public static void game_initialized_for_tiebreak() {
//			to bring the game up to speed
			KDController.initiateEmptyGame();
			
			
//			to resolve tie break, ie scoring, needs to be in the ConfirmingLastChoice state, 
			kd.setStateMachine();
			kd.getStateMachine().setGamestatus("ConfirmingLastChoice"); 						
		}
		
		@Given("the players have the following two dominoes in their respective kingdoms:")
		public static void players_have_following_dominoes_in_respectivte_kingdoms(io.cucumber.datatable.DataTable dataTable) {
			
			List<Map<String, String>> valueMaps = dataTable.asMaps();
			
			for (Map<String, String> map : valueMaps) {
				
				// Get values from cucumber table
				String playerColor=map.get("player");
				Integer dominoID1=Integer.decode(map.get("domino1"));
				Integer posx1=Integer.decode(map.get("posx1"));
				Integer posy1=Integer.decode(map.get("posy1"));
				String dir1=map.get("dominodir1");
				
				Integer dominoID2=Integer.decode(map.get("domino2"));
				Integer posx2=Integer.decode(map.get("posx2"));
				Integer posy2=Integer.decode(map.get("posy2"));
				String dir2=map.get("dominodir2");
				
				// first find the respective players by color
				Player foundPlayer=sharedCucumberMethods.getPlayerByColor(playerColor);
				
				// then preplace dominos 1
				Domino dominoToPlace1 = KDController.getdominoByID(dominoID1); 
				KDController.preplaceArbitraryDomino(foundPlayer, dominoToPlace1, posx1, posy1, dir1);
				
				// then manually 'place' it in kingdom by change the domino status to CorrectlyPlacedInKingdom
				// in an actual game, this step would also be taken care of by the state machine
				dominoToPlace1.setStatus(DominoStatus.PlacedInKingdom);
				
				
				// domino2 is trickier because we have to think of passing the hasAllPlayersPlayed() guard later when making the
				//scoring transition from ConfirmingLastChoice to EndingGame, triggering the calculatePlayerScores() and 
				//calculatePlayerRanking() actions.
				//note that the hasAllPlayersPlayed() checks if all the players of the game has the domino in their selections
				//set to either PlacedInKingdom or Discarded.
				//so here not only do we have to place domino2 in the player's kingdom, we need to also assign it to his selection
				//through a dummy draft and dummy selection
				
				Domino dominoToPlace2 = KDController.getdominoByID(dominoID2); 
				KDController.preplaceArbitraryDomino(foundPlayer, dominoToPlace2, posx2, posy2, dir2);
				dominoToPlace2.setStatus(DominoStatus.PlacedInKingdom);

				Draft tmpDraft = new Draft(DraftStatus.FaceUp,kd.getCurrentGame());
				DominoSelection tmpSelect = new DominoSelection (foundPlayer,dominoToPlace2,tmpDraft);
				
			}
				
		}
		
	@When("calculate ranking is initiated")
	public static void calculate_ranking_initiated() {
		//essentially meaning that we are triggering the scoring transition between Finishing.ConfirmingLastChoice and EndingGame
		//so check that we are in the correct original state
		
		//but needs to pass the hasAllPlayersPlayed guard, which checks to see if players still have domino selections
		//In a real game, since getting the last player to the original Finishing.ConfirmingLastChoice means that all the
		//players have either discared or placed, which would have deleted their selections automatically so the guard
		//would be satisified. Here, however, since we weren't calling the placing and discarding transitions, need
		//to manually satisfy that guard condition
		for (Player p:kd.getCurrentGame().getPlayers()) p.getDominoSelection().delete();

		
		boolean scored=KDController.scoringSM();
		System.out.println("scoring transition: "+scored);
	}
	
	@Then("player standings should be the followings:")
	public static void standings_should_be_followings(io.cucumber.datatable.DataTable dataTable) {
		
		HashMap<String, Integer> expectedPlayerRankings = new HashMap<String, Integer>();
		List<Map<String, String>> valueMaps = dataTable.asMaps();
		
		for (Map<String, String> map : valueMaps) {
			
			// Get values from cucumber table
			String playerColor=map.get("player");
			Integer standing=Integer.decode(map.get("standing"));
			 expectedPlayerRankings.put(playerColor, standing);
		}
		
		boolean rankingCorrect=true;
		
		for (Player p:kd.getCurrentGame().getPlayers()) {
			int actualRanking=p.getCurrentRanking();
			int expectedRanking=expectedPlayerRankings.get(p.getColor().name().toLowerCase());
			
			if (actualRanking!=expectedRanking) {
				rankingCorrect=false;
				break;
			}
		}
		
		assertEquals(true,rankingCorrect);
		
	}
}
