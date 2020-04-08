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

public class CreateNextDraftStep {
	/**
	 * These methods test the creation
	 * of next draft using state machine
	 * @see InitializingGame.feature
	 * @author Jing Han 260528152
	 */
	
	private static Kingdomino kd = KingdominoApplication.getKingdomino();
	private static List<Integer> prevNextIds=new ArrayList<Integer>();
	

	@Given("the game is initialized to create next draft")
	public static void game_initialized_to_create_next_draft() {
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
		
//		since entering the state machine means shuffling the pile, the rest of this method is to ensure that the 
//		dominos 1-13 are arranged in ascending order and placed at the top of the pile. Then the createNextDraft()
//		controller method is called manually again to create the two first drafts from this artificially rearranged,
//		semi-random pile
		
		for (Domino d:kd.getCurrentGame().getCurrentDraft().getIdSortedDominos()) d.setStatus(DominoStatus.InPile);
		for (Domino d:kd.getCurrentGame().getNextDraft().getIdSortedDominos()) d.setStatus(DominoStatus.InPile);
		
		kd.getCurrentGame().getCurrentDraft().delete();
		kd.getCurrentGame().getNextDraft().delete();
		
		List<Integer>ids=new ArrayList<Integer>();
		
		for (Domino d:kd.getCurrentGame().getAllDominos()) ids.add(d.getId());
		
		for (int i=0;i<13;i++) {
			int oldIndex=ids.indexOf(i+1);
			Domino swapA=kd.getCurrentGame().getAllDomino(oldIndex);
			Domino swapB=kd.getCurrentGame().getAllDomino(i);
			kd.getCurrentGame().addOrMoveAllDominoAt(swapA, i);
			kd.getCurrentGame().addOrMoveAllDominoAt(swapB, oldIndex);
		}
		
		kd.getCurrentGame().setTopDominoInPile(kd.getCurrentGame().getAllDomino(0));
		
		for (int i=0;i<kd.getCurrentGame().getAllDominos().size()-1;i++) {
			kd.getCurrentGame().getAllDomino(i).setNextDomino(kd.getCurrentGame().getAllDomino(i+1));
		}
		
		KDController.createNextDraft();
		
		
		for (Domino d:kd.getCurrentGame().getNextDraft().getIdSortedDominos()) prevNextIds.add(d.getId());
		
	}
	
	@Given("there has been {int} drafts created")
	public static void there_has_been_x_drafts(Integer x) {
		int draftNum=kd.getCurrentGame().getAllDrafts().size();
		assertEquals(x.intValue(),draftNum);
	}
	
	@Given("there is a current draft")
	public static void there_is_a_current_draft() {
		assertEquals(true,kd.getCurrentGame().hasCurrentDraft());
	}
	
	@Given("there is a next draft")
	public static void there_is_a_next_draft() {
		assertEquals(true,kd.getCurrentGame().hasNextDraft());
	}
	
	@Given("the top {int} dominoes in my pile have the IDs {string}")
	public static void top_x_dominos_have_id(Integer numDominos, String idStrings) {
		int[] dominoIds=sharedCucumberMethods.str2int(idStrings);
		
		boolean equals=true;
		Domino d=kd.getCurrentGame().getTopDominoInPile();
		for (int i=0;i<dominoIds.length;i++) {
			
			if (dominoIds[i]!=d.getId()) {
				equals=false;
				break;
			}
			d=d.getNextDomino();			
		}
		assertEquals(true,equals);
	}
	
	@When("create next draft is initiated")
	public static void create_next_draft_initiated() {
		KDController.draftReadySM();
		
//		these lines below manually satisfies the guard hasAllPlayersChosen() is satisfied
		KDController.chooseSM(kd.getCurrentGame().getCurrentDraft().getIdSortedDomino(0));
		KDController.chooseSM(kd.getCurrentGame().getCurrentDraft().getIdSortedDomino(1));
		KDController.chooseSM(kd.getCurrentGame().getCurrentDraft().getIdSortedDomino(2));
		KDController.chooseSM(kd.getCurrentGame().getCurrentDraft().getIdSortedDomino(3));
		
		KDController.selectionReadySM();
	}
	
	@Then("a new draft is created from dominoes {string}")
	public static void new_draft_created_from_dominos(String idStrings) {
		
		int[] dominoIds=sharedCucumberMethods.str2int(idStrings);
		List<Integer> searchIds=new ArrayList<Integer>();
		for (int i:dominoIds) searchIds.add(i);
		
		boolean newDraftCreated=(kd.getCurrentGame().getAllDrafts().size()==3);
		
		Draft latestDraft=kd.getCurrentGame().getAllDrafts().get(kd.getCurrentGame().getAllDrafts().size()-1);
		boolean sameNumberOfDominos = latestDraft.getIdSortedDominos().size()==dominoIds.length;
		
		boolean idsMatch=true;
		
		for (Domino d:latestDraft.getIdSortedDominos()) {
			int testId=d.getId();
			if (searchIds.contains(testId)) {}
			else {
				idsMatch=false;
				break;
			}
		}
	
		assertEquals(true,(newDraftCreated&&sameNumberOfDominos&&idsMatch));
	}
	
	@Then("the next draft now has the dominoes {string}")
	public static void next_draft_has_domins(String idStrings) {
		int[] dominoIds=sharedCucumberMethods.str2int(idStrings);
		List<Integer> searchIds=new ArrayList<Integer>();
		for (int i:dominoIds) searchIds.add(i);
		
		
		boolean sameNumberOfDominos = kd.getCurrentGame().getNextDraft().getIdSortedDominos().size()==dominoIds.length;
		
		boolean idsMatch=true;
		
		for (Domino d:kd.getCurrentGame().getNextDraft().getIdSortedDominos()) {
			int testId=d.getId();
			if (searchIds.contains(testId)) {}
			else {
				idsMatch=false;
				break;
			}
		}
	
		assertEquals(true,(sameNumberOfDominos&&idsMatch));
	}
	
	@Then("the dominoes in the next draft are face down")
	public static void dominos_in_next_draft_facedown() {
		assertEquals(DraftStatus.FaceDown,kd.getCurrentGame().getNextDraft().getDraftStatus());
	}

	
	@Then("the top domino of the pile is ID {int}")
	public static void top_domino_of_pile_is_now(int x) {
		assertEquals(x,kd.getCurrentGame().getTopDominoInPile().getId());
	}
	
	@Then("the former next draft is now the current draft")
	public static void former_next_is_now_current() {
		List<Integer> nowCurrentIds=new ArrayList<Integer>();
		for (Domino d:kd.getCurrentGame().getCurrentDraft().getIdSortedDominos()) nowCurrentIds.add(d.getId());
		assertEquals(prevNextIds,nowCurrentIds);
	}

	

}
