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


public class InitializingGameStep {
	
	/**
	 * These methods test the initialization
	 * of a kingdomino game using state machine
	 * @see InitializingGame.feature
	 * @author Jing Han 260528152
	 */
	
	
	private static Kingdomino kd = KingdominoApplication.getKingdomino();
	
	@Given("the game has not been started")
	public static void game_has_not_started() {

		int numPlayers=4;
		List<String> selectedBonusOptions = new ArrayList<String>();
		
		try{
			KDController.setGameOptions(numPlayers, selectedBonusOptions);
			int dominoNums=kd.getCurrentGame().getMaxPileSize();
			KDController.createDominoPile(kd.getCurrentGame(),dominoNums);
			KDController.createPlayers();
		}
		catch(Exception e) {}
	}
	
	@When("start of the game is initiated")
	public static void game_start_initiated() {	
		KDController.initializeSM();
		KDController.draftReadySM();
	}
	
	@Then("the pile shall be shuffled")
	public static void then_pile_shall_be_shuffled() {
		List<Domino> allDominos=kd.getCurrentGame().getAllDominos();
		
		int numInOrder=0;
				
		for (int i=0;i<allDominos.size()-1;i++) {
			int previousId=allDominos.get(i).getId();
			int nextId=allDominos.get(i+1).getId();
			if((nextId-previousId)==1) {
				numInOrder+=1;
			}
		}
		
		boolean shuffled;
		
		if (numInOrder==(allDominos.size()-1)) {
			shuffled=false;
		}
		else {
			shuffled=true;
		}
		
		assertEquals(true,shuffled);
	}
	
	@Then("the first draft shall be on the table")
	public static void then_first_draft_shall_be_on_table() {
		assertEquals(true,kd.getCurrentGame().hasCurrentDraft());
	}
	
	@Then("the first draft shall be revealed")
	public static void then_the_first_draft_shall_be_revealed() {
		assertEquals(DraftStatus.FaceUp,kd.getCurrentGame().getCurrentDraft().getDraftStatus());
	}
	
	@Then("the initial order of players shall be determined")
	public static void then_the_initial_order_of_players_determined() {
		
		boolean hasColor=true;
		boolean diffColor=true;
		
		for (int i=0;i<kd.getCurrentGame().getPlayers().size()-1;i++) {
			Player p1=kd.getCurrentGame().getPlayer(i);
			Player p2=kd.getCurrentGame().getPlayer(i+1);
			
			if (p1.getColor()==null || p2.getColor()==null) {
				hasColor=false;
				break;
			}
			
			if (p1.getColor().equals(p2.getColor())) {
				diffColor=false;
				break;
			}
			
		}
		
		assertEquals(true,(hasColor&&diffColor));
	}
	
	@Then("the first player shall be selecting his\\/her first domino of the game")
	public static void first_player_shall_be_selecting_firrst() {
		Player p1=kd.getCurrentGame().getPlayer(0);
		List<KingdomTerritory> t = p1.getKingdom().getTerritories();
		int dominoCount=0;
		for (KingdomTerritory test:t) {
			if (test instanceof DominoInKingdom) {
				dominoCount+=1;
			}
		}
		
		assertEquals(0,dominoCount);
	}
	
	@Then("the second draft shall be on the table, face down")
	public static void main_second_draft_on_table_facedown() {
		boolean hasNextDraft=kd.getCurrentGame().hasNextDraft();
		boolean isFacedown=kd.getCurrentGame().getNextDraft().getDraftStatus().equals(DraftStatus.FaceDown);
		assertEquals(true,(hasNextDraft&&isFacedown));
		
		kd.removeStateMachine();
	}
	
	

}
