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

public class SM_PlacingDominoStep {
	
	private static Kingdomino kd = KingdominoApplication.getKingdomino();
	private static Domino dominoToPlace;
	
	//this would be one of the superstate Playing.ManipulatingDomino
	@Given("the game has been initialized for placing domino")		
	public static void  game_initialized_for_placing() {
//		to bring the game up to speed
		KDController.initiateEmptyGame();
		kd.setStateMachine();
		kd.getStateMachine().setGamestatus("ManipulatingDomino"); 						
		System.out.println("state machine state: "+kd.getStateMachine().getGamestatusFullName());
		
		//we are supposed to be choosing from current draft, but we haven't made a draft yet
		//if we just choose a random domino, wouldn't work. See KDController.chooseNext
		//so let's just manually make a draft
		
		Draft manualDraft = new Draft(DraftStatus.FaceUp,kd.getCurrentGame());
		manualDraft.addIdSortedDomino(KDController.getdominoByID(33));
		manualDraft.addIdSortedDomino(KDController.getdominoByID(2));
		manualDraft.addIdSortedDomino(KDController.getdominoByID(13));
		manualDraft.addIdSortedDomino(KDController.getdominoByID(5));
		//these ids arent chosen by the test 
		
		kd.getCurrentGame().setCurrentDraft(manualDraft);
	}
	
	@Given("it is not the last turn of the game")
	public static void not_last_turn_in_game(){
		boolean lastTurn=KDQuery.isCurrentTurnTheLastInGame();				//pretty much all of the guard conditions are in the KDQuery class
		System.out.println("is last turn: "+lastTurn);
	}
	
	@Given("the current player is not the last player in the turn")
	public static void not_last_player_in_turn() {
		//work with the 'next' player of this turn
		boolean lastPlayer=KDQuery.isCurrentPlayerTheLastInTurn(kd.getCurrentGame().getNextPlayer());
		System.out.println("is last player: "+lastPlayer);
		
	}
	
	@Given("the current player is preplacing his\\/her domino with ID {int} at location {int}:{int} with direction {string}")
	public static void preplacing(int id, int posx, int posy, String dir) {
		
		// this is a bit tricky, but the "given player's kingdom has following dominos" is actually a shared scenario across many features
		// so it is factored out into the sharedCucumberMethod class, that's why it's not in this step def
		// but the method in sharedCucumberMethod only preplaces it, it doesn't place in kingdom, so technically the kingdom has no
		// dominos right now. So, need to here, manually change the domino stattus of those preplaced dominos to PlacedInKingdom
		// this rests on the premise that in this scenario, those preplaced dominos are in valid positions, as they must be for 
		// the test case
		
		for (KingdomTerritory t:kd.getCurrentGame().getNextPlayer().getKingdom().getTerritories()) {
			if (t instanceof DominoInKingdom) {
				((DominoInKingdom) t).getDomino().setStatus(DominoStatus.PlacedInKingdom);
			}
		}
		
		View.printPlayerKingdom(kd.getCurrentGame().getNextPlayer());	// view to see we actually have kingdom with these dominos
		
		
		// use KDController.preplaceArbitraryDomino, the default KDController.preplaceLatestDomino always preplaces the domino that was
		// most recently added to player.getKingdom.getKingdomTerritories(). 
		// However, both preplace methods, as well as rotate, move, place, discard methods; will all set the domino 
		// status accordingly by themselves. 
		dominoToPlace=KDController.getdominoByID(id);
		KDController.preplaceArbitraryDomino(kd.getCurrentGame().getNextPlayer(), dominoToPlace, posx, posy, dir);	
	}
	
	@And("the preplaced domino has the status {string}")
	public static void and_status_correctly_preplace(String status) {
		assertEquals(true,status.equalsIgnoreCase(dominoToPlace.getStatus().name()));
	}
	
	@When("the current player places his\\/her domino")
	public static void place_domin() {
		
		// in case of last player of the last turn, also needs to manually enable the hasAllPlayersPlayed() guard
		// so can proceed to the EndingGame state. During normal play, this would be taken care by SM
		// how KDQuery checks if all players have played is to see if all player's selection dominos are either PlacedInKingdom or Discarded
		if (KDQuery.isCurrentPlayerTheLastInTurn(kd.getCurrentGame().getNextPlayer())&&KDQuery.isCurrentTurnTheLastInGame()) {
			for (Player p:kd.getCurrentGame().getPlayers()) {
				p.getDominoSelection().getDomino().setStatus(DominoStatus.PlacedInKingdom);
			}
			
			System.out.println("all played: "+KDQuery.hasAllPlayersPlayed());
		}
		
		// call the place transition of the state machine
		// no guards to satisfy. However, if there were, we would have to manually satisfy them by calling controller/sm methods 
		// see CreateNextDraftStep for idea
		
		boolean placed=KDController.placeSM();		//note that the placeSM takes no player as parameter, that's because it always acts on the 'next' player of the game
		System.out.println("domino placed: "+placed);
		View.printPlayerKingdom(kd.getCurrentGame().getNextPlayer());

	}
	
	
	@Then("this player now shall be making his\\/her domino selection")
	public static void now_make_selection(){
		//first check if the transition to the ConfirimingChoice stat has been made
		System.out.println("new state: "+kd.getStateMachine().getGamestatusFullName());
		

		Domino randomDomino=kd.getCurrentGame().getCurrentDraft().getIdSortedDomino(0);		//33 not in the list of ids used in this test
		System.out.println("domino to choose: "+randomDomino.getId());
		//remember that the choose transition in the state machine automatically updates the 'next' player
		//so to check out previous 'next' player has selected, we need to make a reference to it before the udpate
		Player original=kd.getCurrentGame().getNextPlayer();
		System.out.println("player "+kd.getCurrentGame().getNextPlayer().getColor()+" choose?");
		boolean chosen = KDController.chooseSM(randomDomino);	
		System.out.println("player "+original.getColor()+" now has selection: "+(chosen&&original.hasDominoSelection()));
		System.out.println("player "+original.getColor()+"'s new selection has domino: "+original.getDominoSelection().getDomino().getId());
		assertEquals(true,original.getDominoSelection().getDomino().getId()==randomDomino.getId());

	}
	
	@Given("the current player is the last player in the turn")
	public static void last_player_in_turn() {
		
		//call the updatePlayerOrder 3 times to set the 'next' player to last player
		//remember this scenario is unrelataed to the first one, so the upates that we made to the player order there
		//doesn't carry thru to this scenario. otherwise, only update 2 times
		
		System.out.println("========= player order ==========");
		for (Player p:kd.getCurrentGame().getPlayers()) {
			System.out.println(p.getColor());
		}
		
		System.out.println("current 'next' plyaer: "+kd.getCurrentGame().getNextPlayer().getColor());
		KDController.updateNextPlayer(kd.getCurrentGame().getNextPlayer());
		System.out.println("current 'next' plyaer: "+kd.getCurrentGame().getNextPlayer().getColor());
		KDController.updateNextPlayer(kd.getCurrentGame().getNextPlayer());
		System.out.println("current 'next' plyaer: "+kd.getCurrentGame().getNextPlayer().getColor());
		KDController.updateNextPlayer(kd.getCurrentGame().getNextPlayer());
		System.out.println("current 'next' plyaer: "+kd.getCurrentGame().getNextPlayer().getColor());
		
		System.out.println("is last player: "+KDQuery.isCurrentPlayerTheLastInTurn(kd.getCurrentGame().getNextPlayer()));
		
		
		
		
	}
	
	

}
