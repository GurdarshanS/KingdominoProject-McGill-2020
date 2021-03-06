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

public class SM_PlacingLastDominoStep {
	/**
	 * These methods checks for the state machine 
	 * transition for Placing last dominos
	 * 
	 * @see PlacingLastDomino.feature
	 * @author Keon Olszewski 260927813
	 */
	
	private static Kingdomino kd = KingdominoApplication.getKingdomino();
	private static Domino dominoToPlace;
	
	
	//this would be one of the superstate Playing.ManipulatingLastDomino
	@Given("the game has been initialized for placing last domino")		
	public static void  game_initialized_for_placing_last() {
//		to bring the game up to speed
		KDController.initiateEmptyGame();
		kd.setStateMachine();
		kd.getStateMachine().setGamestatus("ManipulatingLastDomino"); 						
		
	}
	
	@Given("it is the last turn of the game")
	public static void is_last_turn_in_game(){
		//basically this means that we need to have 12 drafts for the 3 and 4 player games and 6 for 2 player
		//here we just deal with the 4 player scenario, so create 12 drafts
		for (int i=0;i<12;i++) {
			KDController.createNextDraft();
		}
		

		//now the last 4 dominos are in the current draft
		//manually assign them to players so it simulates when they have all chosen their last regular dominos
		
		for (int i=0;i<4;i++) {
			DominoSelection tmp= new DominoSelection(kd.getCurrentGame().getPlayer(i),kd.getCurrentGame().getCurrentDraft().getIdSortedDomino(i),kd.getCurrentGame().getCurrentDraft());
		}
		
		for (int i=0;i<12;i++) {
			Draft dft=kd.getCurrentGame().getAllDraft(i);
			for (Domino d:dft.getIdSortedDominos()) {
				d.setStatus(DominoStatus.Excluded);
			}
		}
		
		boolean lastTurn=KDQuery.isCurrentTurnTheLastInGame();				//pretty much all of the guard conditions are in the KDQuery class
		
	}
	
	@Then("the next player shall be placing his\\/her domino")
	public static void next_player_shall_be_placing() {
		//first lets make sure that we are now in the ConfirmingLastChoice state
		
		//essentially here we check whether the firable action at ConfirmingLastChoice state is manipulateLast and not scoring
		//but we should also check that the player who manipulated again was infact the player after the current 'next' player
		List<Player.PlayerColor>colors=new ArrayList<Player.PlayerColor>();
		for (Player p:kd.getCurrentGame().getPlayers()) colors.add(p.getColor());
		
		int oldNextIndex=colors.indexOf(kd.getCurrentGame().getNextPlayer().getColor());
		Player newNextPlayer=kd.getCurrentGame().getPlayer(oldNextIndex+1);
		
		boolean manipulateAgain=KDController.manipulateLastSM(0, 0, "right"); 	//here we don't particularly care if it's a correct preplacement
		boolean score=KDController.scoringSM();
		
		assertEquals(true,manipulateAgain==true && score==false && newNextPlayer.equals(kd.getCurrentGame().getNextPlayer()));
	}

	@Then("the game shall be finished")
	public static void game_finished() {

		//check if we can enter the EndingGame state from the ConfirmingLastChoice state
		//needs to pass the hasAllPlayersPlayed guard, which checks to see if players still have domino selections
		//the placingSM() in the @When in SM_PlacingDomino places and deletes the domino selection for player 4
		//but it doesn't account for the other 3 players, so manually delete them here. In a real game, since the previous
		//3 players would have to either place or discard, their selections would be deleted automatically
		for (int i=0;i<3;i++) {
			Player p=kd.getCurrentGame().getPlayer(i);
			if (p.hasDominoSelection()) {
				p.getDominoSelection().delete();
			}
		}
		
		boolean scoring=KDController.scoringSM();
		assertEquals(Gameplay.Gamestatus.EndingGame.toString(),kd.getStateMachine().getGamestatusFullName());
	}
	
	@Then("the final results after successful placement shall be computed")
	public static void results_computed() {
		//kind of redundant, since by entering the EndingGame state we have already calculated player ranking and scores
		//guess here we will assert again that we are in the EndingGame state
		assertEquals(Gameplay.Gamestatus.EndingGame.toString(),kd.getStateMachine().getGamestatusFullName());
	}
	

}
