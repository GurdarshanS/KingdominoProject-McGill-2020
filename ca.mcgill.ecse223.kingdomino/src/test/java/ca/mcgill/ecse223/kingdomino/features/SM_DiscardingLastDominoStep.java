package ca.mcgill.ecse223.kingdomino.features;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

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
import io.cucumber.java.en.And;


public class SM_DiscardingLastDominoStep {
	
	/**
	 * These methods checks for the state machine 
	 * transition for discarding last dominos
	 * 
	 * @see DiscardingLastDomino.feature
	 * @author Eric Guan 260930210
	 */
	
	private static Kingdomino kd = KingdominoApplication.getKingdomino();
	
	@Given("the game is initialized for discarding last domino")
	public static void game_initialized_for_discarding() {
		

		///		to bring the game up to speed
		KDController.initiateEmptyGame();
		kd.setStateMachine();
		kd.getStateMachine().setGamestatus("ManipulatingLastDomino"); 						
	}
	
	@Then("the final results after discard shall be computed")
	public static void final_results_after_discard_computed() {
		//kind of redundant, since by entering the EndingGame state we have already calculated player ranking and scores
		//guess here we will assert again that we are in the EndingGame state
		assertEquals(Gameplay.Gamestatus.EndingGame.toString(),kd.getStateMachine().getGamestatusFullName());
	}
}
