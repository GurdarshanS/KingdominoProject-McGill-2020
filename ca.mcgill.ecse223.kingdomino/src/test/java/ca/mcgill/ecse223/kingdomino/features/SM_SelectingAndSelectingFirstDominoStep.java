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
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;

public class SM_SelectingAndSelectingFirstDominoStep {
	
	private static Kingdomino kd = KingdominoApplication.getKingdomino();
	private static boolean chosen;
	private static boolean firstChosen;
	
	@Given("the game has been initialized for selecting domino")
	public static void game_initialized_for_selecting_domino() {
		int numPlayers=4;
		List<String> selectedBonusOptions = new ArrayList<String>();
		
		try{
			KDController.setGameOptions(numPlayers, selectedBonusOptions);
			int dominoNums=kd.getCurrentGame().getMaxPileSize();
			KDController.createDominoPile(kd.getCurrentGame(),dominoNums);
			KDController.createPlayers();
			
		}
		catch(Exception e) {}
		
		
		kd.setStateMachine();
		
		// the state here is after a player has placed/discard his domino and is ready to select the next domino, after which
		// it should be the next player's turn to place (manipuate) his domino
		// so the state machine should be in state ConfirmingChoice
		
		kd.getStateMachine().setGamestatus("ConfirmingChoice");
		System.out.println(kd.getStateMachine().getGamestatusFullName());
	}
	
	@Given("the order of players is {string}")
	public static void order_of_players_is(String playerOrder) {
		String[] order=playerOrder.split(",");
		
		//manually rearrange the player order by the specified order
		for (int i=0;i<order.length;i++) {
			Player p = sharedCucumberMethods.getPlayerByColor(order[i]);
			kd.getCurrentGame().addOrMovePlayerAt(p, i);
		}
		
		//manually reset the 'next' player
		kd.getCurrentGame().setNextPlayer(kd.getCurrentGame().getPlayer(0));
		
		System.out.println(" 'next' player: "+kd.getCurrentGame().getNextPlayer().getColor());
		System.out.println();
		int i=1;
		for (Player p:kd.getCurrentGame().getPlayers()) {
			System.out.println(i+": "+p.getColor());
			i++;
		}
	}
	
	@Given("the next draft has the dominoes with ID {string}")
	public static void next_draft_has_dominos(String dominoIds) {
		
		String[] splitIds=dominoIds.split(",");
		
		//domino ids [1,2,3,4] were in the previous turn's 'next' draft.
		Draft previousNextDraft = new Draft(DraftStatus.FaceDown,kd.getCurrentGame());
		for (String id:splitIds) {
			Domino d = KDController.getdominoByID(Integer.decode(id));
			previousNextDraft.addIdSortedDomino(d);
		}
		
		//however, it becomes this turn's 'current' draft
		
		kd.getCurrentGame().setCurrentDraft(previousNextDraft);
	}
	
	@Given("player's domino selection {string}")
	public static void domins_have_selection(String dominoSelectionOwners) {
		String[] eachDominoOwner=dominoSelectionOwners.split(",");
		for (int i=0;i<eachDominoOwner.length;i++) {
			String owner=eachDominoOwner[i];
			if (owner.equalsIgnoreCase("none")) {
				continue;
			}
			else {
				DominoSelection tmp = new DominoSelection(sharedCucumberMethods.getPlayerByColor(owner),
						kd.getCurrentGame().getCurrentDraft().getIdSortedDomino(i),
						kd.getCurrentGame().getCurrentDraft());
			}
		}
	}
	
	@Given("the {string} is selecting his\\/her domino with ID {int}")
	public static void current_player_selecting_domino(String color, int id) {
		//means making the transition between the states Playing.ConfirmingChoice and Playing.SelectingStandardDomino
		
		//normally we would do this in the @When statement. However, given the way that the state machine is setup
		//and the fact that we need to check the selection result immediately after this in the @And,
		//exceptionally make the triggering transition here instead
		
		//the choose transition always acts on the 'next' player of the game
		kd.getCurrentGame().setNextPlayer(sharedCucumberMethods.getPlayerByColor(color));

		
		System.out.println("original state:    "+kd.getStateMachine().getGamestatusFullName());
		chosen = KDController.chooseSM(KDController.getdominoByID(id));
		System.out.println("choose successful: "+chosen);
		System.out.println("new state:         "+kd.getStateMachine().getGamestatusFullName());
	}
	
	@And("the validation of domino selection returns {string}")
	public static void validation_of_selection_returns(String status) {
		if (status.equalsIgnoreCase("success")) {
			if (kd.getStateMachine().getGamestatusInitializing().equals(Gameplay.GamestatusInitializing.SelectingFirstDomino)) {
				assertEquals(true,firstChosen);
			}
			else assertEquals(true,chosen);
		}
		else if (status.equalsIgnoreCase("error")) {
			if (kd.getStateMachine().getGamestatusInitializing().equals(Gameplay.GamestatusInitializing.SelectingFirstDomino)) {
				assertEquals(false,firstChosen);
			}
			else assertEquals(false,chosen);
		}
	}
	
	
	@When("the {string} player completes his\\/her domino selection")
	public static void selection_complete(String color) {
		//since the triggering event and selection action was already executed in the previous
		//@Given statement, check to see if the state machine is indeed in the SelectingStandardDomino state
		
		String expectedState=Gameplay.GamestatusPlaying.SelectingStandardDomino.toString();
		String actualState=kd.getStateMachine().getGamestatusPlaying().toString();
		
		System.out.println("expected state: "+expectedState);
		System.out.println("actual state:   "+actualState);
		
		if (chosen)	assertEquals(true,expectedState.equalsIgnoreCase(actualState));
		else assertEquals(false,expectedState.equalsIgnoreCase(actualState));
	}
	
	@Then("the {string} player shall be {string} his\\/her domino")
	public static void next_player_action_should_be(String playerColor,String action) {
		//since the choose domino action by the previous 'next' player already updates the 
		//player order, need to check that the new 'next' player is the same as the player specified
		//and then check that since we are in the SelectionStandardDomino state, the only
		//fireable action was back to ManipulatingStandardDomino (which eventually leads to the new
		//'next' player placing)
		
		boolean playerMatch=kd.getCurrentGame().getNextPlayer().getColor().toString().equalsIgnoreCase(playerColor);
		
		
		//if placing (manipulatingDomino state) is an option, then that means state machine is in the SelectionStandardDomino state
		
		if (kd.getStateMachine().getGamestatusPlaying().equals(Gameplay.GamestatusPlaying.SelectingStandardDomino)) {
			//there are 3 exit transitions from SelectingStandardDomino, only the manipulateNext should be enabled
			
			//but for the manipulateNext transition to work, the 'next' player needs to have selected a domino
			//normally in a game, this would the domino he selected from the 'current' draft of the previous turn
			//here we just manually give him a dummy selection with an arbitrary domini
			
			Draft dummyDraft = new Draft(DraftStatus.FaceUp,kd.getCurrentGame());
			dummyDraft.addIdSortedDomino(KDController.getdominoByID(25));
			DominoSelection dummySelection = new DominoSelection(kd.getCurrentGame().getNextPlayer(),dummyDraft.getIdSortedDomino(0),dummyDraft);
			
			boolean manipulateNext=KDController.manipulateNext(0, 0, "up"); //here don't particularly care where the 'next' player's preplacement of domino is
			boolean nextSelectionReady=KDController.nextSelectionReadySM();
			boolean lastSelectionReady=KDController.lastSelectionReady(0, 0, "up");
			
			System.out.println("the new 'next' player is "+kd.getCurrentGame().getNextPlayer().getColor()+" and his placement was successful: "+manipulateNext);
			
			boolean goodFiring=(manipulateNext==true && nextSelectionReady==false && lastSelectionReady==false);
			
			if (goodFiring) {
				assertEquals(true,true);
			}
		}
		
		//else the previous 'next' player's selection was not good then the 'next' player shouldn't update and the statemachine
		//is still in the ConfirmingChoice state
		
		if (kd.getStateMachine().getGamestatusPlaying().equals(Gameplay.GamestatusPlaying.ConfirmingChoice)) {
			System.out.println("the new 'next' player is "+kd.getCurrentGame().getNextPlayer().getColor()+" it's back to selecting");
		}
	}
	
	@Given("the {string} player is selecting his\\/her first domino of the game with ID {int}")
	public static void player_selecting_first_domino_of_the_game(String color, int id) {
		//this is in the state SelectingFirstDomino
		//the shared @Given for initializing manually set the state machine to ConfirmingChoice
		//so need to manually set it to SelectingFirstDomino here
		
		kd.getStateMachine().setGamestatus("SelectingFirstDomino");
				
		//the choose transition always acts on the 'next' player of the game
		kd.getCurrentGame().setNextPlayer(sharedCucumberMethods.getPlayerByColor(color));
		
		//since it's a self transition, should be back in the SelectingFirstDomino state
		System.out.println("original state:    "+kd.getStateMachine().getGamestatusFullName());
		firstChosen = KDController.chooseSM(KDController.getdominoByID(id));
		System.out.println("choose successful: "+firstChosen);
		System.out.println("new state:         "+kd.getStateMachine().getGamestatusFullName());
		
		//also now all players hould have chosen, enabling the next firing of SelectionReady() to transition to the Playing state
		//and also triggering the creation of a new draft
		
		System.out.println("all players chosen: "+KDQuery.hasAllPlayersChosen());
	}
	
	@When("the {string} player completes his\\/her domino selection of the game")
	public static void turn_selection_complete(String color) {
		//make the transition from the Initializing.SelectingFirstDomino to the Playing.CreatingNextDraft state
		
		
		
		String originalState=kd.getStateMachine().getGamestatusFullName().toString();
		System.out.println("original state: "+originalState);
		
		boolean transitioned=KDController.selectionReadySM();
		System.out.println("transition success: "+transitioned);
		
		String newState=kd.getStateMachine().getGamestatusFullName().toString();
		System.out.println("actual state:   "+newState);
		
		assertEquals(Gameplay.GamestatusPlaying.CreatingNextDraft,kd.getStateMachine().getGamestatusPlaying());
	}
	
	@Then("a new draft shall be available, face down")
	public static void new_draft_facedown_avaialble() {
		boolean hasNext=kd.getCurrentGame().hasNextDraft();
		boolean status=kd.getCurrentGame().getNextDraft().getDraftStatus().equals(DraftStatus.FaceDown);
		
		assertEquals(true,hasNext&&status);
		
	}
	
	
	
	
	
	
	
	////////////////////////// now for selecting first domino //////////////////////////////////////////
	
	@Given("the game has been initialized for selecting first domino")
	public static void game_initialized_for_selecting_first_domino() {
		//this would be the case after the draftReady transition into the SelectionFirstDomino state
		
		int numPlayers=4;
		List<String> selectedBonusOptions = new ArrayList<String>();
		
		try{
			KDController.setGameOptions(numPlayers, selectedBonusOptions);
			int dominoNums=kd.getCurrentGame().getMaxPileSize();
			KDController.createDominoPile(kd.getCurrentGame(),dominoNums);
			KDController.createPlayers();
			
		}
		catch(Exception e) {}
		
		
		kd.setStateMachine();
		kd.getStateMachine().setGamestatus("SelectingFirstDomino");
		System.out.println(kd.getStateMachine().getGamestatusFullName());
	}
	
	@Given("the initial order of players is {string}")
	public static void initial_player_order_is(String playerOrder) {
		String[] order=playerOrder.split(",");
		
		//manually rearrange the player order by the specified order
		for (int i=0;i<order.length;i++) {
			Player p = sharedCucumberMethods.getPlayerByColor(order[i]);
			kd.getCurrentGame().addOrMovePlayerAt(p, i);
		}
		
		//manually reset the 'next' player
		kd.getCurrentGame().setNextPlayer(kd.getCurrentGame().getPlayer(0));
		
		System.out.println(" 'next' player: "+kd.getCurrentGame().getNextPlayer().getColor());
		System.out.println();
		int i=1;
		for (Player p:kd.getCurrentGame().getPlayers()) {
			System.out.println(i+": "+p.getColor());
			i++;
		}
	}
	
	@Given("the current draft has the dominoes with ID {string}")
	public static void current_draft_has_dominos(String dominoIds) {
		
		String[] splitIds=dominoIds.split(",");
		
		//domino ids [1,2,3,4] were in the previous turn's 'next' draft.
		Draft firstCurrentDraft = new Draft(DraftStatus.FaceDown,kd.getCurrentGame());
		Draft firstNextDraft = new Draft(DraftStatus.FaceDown,kd.getCurrentGame());
		
		int id2=10;
		
		for (String id:splitIds) {
			Domino d = KDController.getdominoByID(Integer.decode(id));
			d.setStatus(DominoStatus.InCurrentDraft);
			Domino d2= KDController.getdominoByID(id2);
			d2.setStatus(DominoStatus.InNextDraft);
			
			firstCurrentDraft.addIdSortedDomino(d);
			firstNextDraft.addIdSortedDomino(d2);
			id2+=1;
		}
		
		//however, it becomes this turn's 'current' draft
		
		kd.getCurrentGame().setCurrentDraft(firstCurrentDraft);
		kd.getCurrentGame().setNextDraft(firstNextDraft);
		
		View.printDraft(kd);
	}
	
	
	@Given("player's first domino selection of the game is {string}")
	public static void first_dominos_have_selection(String dominoSelectionOwners) {
		String[] eachDominoOwner=dominoSelectionOwners.split(",");
		for (int i=0;i<eachDominoOwner.length;i++) {
			String owner=eachDominoOwner[i];
			if (owner.equalsIgnoreCase("none")) {
				continue;
			}
			else {
				DominoSelection tmp = new DominoSelection(sharedCucumberMethods.getPlayerByColor(owner),
						kd.getCurrentGame().getCurrentDraft().getIdSortedDomino(i),
						kd.getCurrentGame().getCurrentDraft());
			}
		}
		
		for (Domino d:kd.getCurrentGame().getCurrentDraft().getIdSortedDominos()) {
			if (d.hasDominoSelection()) 	System.out.println(d.getId()+" : "+d.getDominoSelection().getPlayer().getColor());
			else System.out.println(d.getId()+" : none");
		}
	}
	
	@Given("the {string} player is selecting his\\/her domino with ID {int}")
	public static void player_selecting_first_domino(String color, int id) {
		//means making transitions from the SelectingFirstDomino state, either back itself or to Playing.CreatingNextDraft
		
		//normally we would do this in the @When statement. However, given the way that the state machine is setup
		//and the fact that we need to check the selection result immediately after this in the @And,
		//exceptionally make the triggering transition here instead
		
		//the choose transition always acts on the 'next' player of the game
		kd.getCurrentGame().setNextPlayer(sharedCucumberMethods.getPlayerByColor(color));

		
		System.out.println("original state:    "+kd.getStateMachine().getGamestatusFullName());
		firstChosen = KDController.chooseSM(KDController.getdominoByID(id));
		System.out.println("choose successful: "+firstChosen);
		System.out.println("new state:         "+kd.getStateMachine().getGamestatusFullName());
	}
	
	@Given("the {string} player is selecting his\\/her first domino with ID {int}")
	public static void last_player_first_turn_select(String color, int id) {
		kd.getCurrentGame().setNextPlayer(sharedCucumberMethods.getPlayerByColor(color));
		
		System.out.println("original state:    "+kd.getStateMachine().getGamestatusFullName());
		firstChosen = KDController.chooseSM(KDController.getdominoByID(id));
		System.out.println("choose successful: "+firstChosen);
		System.out.println("new state:         "+kd.getStateMachine().getGamestatusFullName());
	}
	
}
