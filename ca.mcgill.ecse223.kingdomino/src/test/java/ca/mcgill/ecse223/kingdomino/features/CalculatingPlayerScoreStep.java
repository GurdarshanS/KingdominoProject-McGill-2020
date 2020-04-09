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
import ca.mcgill.ecse223.kingdomino.development.View;
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
import io.cucumber.java.en.And;

public class CalculatingPlayerScoreStep {
	
	private static Kingdomino kd = KingdominoApplication.getKingdomino();
	private static Player player;
	
	@Given("the game is initialized for calculating player score")
	public static void game_initialized_for_calculating_player_score() {
		// means we are in the ConfirmingLastChoice state of the state machine and
		// that the guards hasAllPlayersPlayed is satisfied, i.e. the domino
		// in each player's DominoSelection has status PlacedInKingdom or Discarded
		
		KDController.initiateEmptyGame();
		
		//make the 'player' in this series of tests the player from kd.getCurrentGame.getPlayers().get(0)
		player=kd.getCurrentGame().getPlayer(0);
		
		kd.setStateMachine();
		kd.getStateMachine().setGamestatus("ManipulatingLastDomino"); 						
		System.out.println("state machine state: "+kd.getStateMachine().getGamestatusFullName());
		
		
	}
	
	@Given("the current player has no dominoes in his\\/her kingdom yet")
	public static void player_kingdom_empty() {
		
		
		boolean hasDomino=false;
		
		for (KingdomTerritory t:player.getKingdom().getTerritories()) {
			if (t instanceof DominoInKingdom) {
				hasDomino=true;
				break;
			}
		}
		assertEquals(false,hasDomino);
	}
	
	@Given("the score of the current player is {int}")
	public static void score_of_current_player_is(int expectedScore) {
//		View.printPlayerKingdom(player);
		System.out.println(player.hasKingdom());
		KDController.calculateIndividualPlayerScore(player);
		int actualScore=player.getTotalScore();
		assertEquals(expectedScore,actualScore);
	}
	
	@Then("the score of the current player shall be {int}")
	public static void score_of_current_player_shall_be(int expectedScore) {
		KDController.calculateIndividualPlayerScore(player);
		int actualScore=player.getTotalScore();
		assertEquals(expectedScore,actualScore);
	}
	
	@Given("the game has no bonus options selected")
	public static void no_bonus_selected() {
		assertEquals(0,kd.getBonusOptions().size());
	}
	
	@Given("the current player is placing his\\/her domino with ID {int}")
	public static void placing_to_discard(int id) {
		KDController.preplaceArbitraryDomino(player, KDController.getdominoByID(id), 0, 0, "up");
	}
	
	@And("it is impossible to place the current domino in his\\/her kingdom")
	public static void impossible_to_place() {
		List<KingdomTerritory> allT=player.getKingdom().getTerritories();
		DominoInKingdom latestPreplacedDomino=(DominoInKingdom) allT.get(allT.size()-1);
		boolean canPlace=KDQuery.isThereAvailablePlacement(player, latestPreplacedDomino);
		System.out.println(canPlace);
	}
	
	@When("the current player discards his\\/her domino")
	public static void discard_impossible_placement() {
		//first see if we are still in the ManipulatingLastDomino state
		System.out.println("state machine state: "+kd.getStateMachine().getGamestatusFullName());
		
		//if yes, can then make the transition to ConfirmingLastChoice with discard event
		//but discard only works on the 'next' player
		//so manually set the player we've been working with to the 'next' player of the game
		
		kd.getCurrentGame().setNextPlayer(player);
		boolean discarded=KDController.discardSM();
		System.out.println("successful discard: "+discarded);
		
		assertEquals(true,discarded);
	}
	
	

}
