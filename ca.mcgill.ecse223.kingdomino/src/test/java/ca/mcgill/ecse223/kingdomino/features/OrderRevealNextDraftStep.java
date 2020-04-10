package ca.mcgill.ecse223.kingdomino.features;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.*;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.KDController;
import ca.mcgill.ecse223.kingdomino.development.View;
import ca.mcgill.ecse223.kingdomino.model.Castle;
import ca.mcgill.ecse223.kingdomino.model.Domino;
import ca.mcgill.ecse223.kingdomino.model.Draft;
import ca.mcgill.ecse223.kingdomino.model.Game;
import ca.mcgill.ecse223.kingdomino.model.Kingdom;
import ca.mcgill.ecse223.kingdomino.model.Kingdomino;
import ca.mcgill.ecse223.kingdomino.model.Player;
import ca.mcgill.ecse223.kingdomino.model.TerrainType;
import ca.mcgill.ecse223.kingdomino.model.Player.PlayerColor;
import ca.mcgill.ecse223.kingdomino.model.Draft.DraftStatus;
import ca.mcgill.ecse223.kingdomino.model.DominoSelection;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class OrderRevealNextDraftStep {

	/**
	 * These methods checks if the next drafts are ordered 
	 * and revealed properly with the order and reveal features
	 * 
	 * @see OrderAndRevealNextDraft.feature
	 * @author Keon Olszewski 260927813
	 */


	 @Given("the game is initialized for order next draft of dominoes")
	 public void the_game_is_initialized_for_order_next_draft() {

			KDController.initiateEmptyGame();



	 }// game initialized for order next draft


	 @Given("the next draft is {string}")
	 public void the_next_draft(String aString) {

		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		String[] strArray = aString.split(",");
		int[] intArray = new int[strArray.length];

		for(int i = 0; i < strArray.length; i++) {

			intArray[i] = Integer.parseInt(strArray[i]);
		}


		List<Integer> firstEightIds=new ArrayList<Integer>();

		for (int i:intArray) {
			firstEightIds.add(i+1);
		}

		for (int i:intArray) {
			firstEightIds.add(i);
		}

		for (int i=0;i<firstEightIds.size();i++) {

			Domino dA=KDController.getdominoByID(firstEightIds.get(i));
			int indexA=game.getAllDominos().indexOf(dA);

			Domino dB=game.getAllDomino(i);
			int indexB=i;

			game.addOrMoveAllDominoAt(dA, indexB);
			game.addOrMoveAllDominoAt(dB, indexA);

		}
		game.setTopDominoInPile(game.getAllDomino(0));

		for (int i=0;i<game.getAllDominos().size()-1;i++) {
			game.getAllDomino(i).setNextDomino(game.getAllDomino(i+1));
		}

		// the desired dominos are in this turn's next draft
		KDController.createNextDraft();
		// but the sorting and revealing process only makes sense when the current turn ends
		// and the previous next draft becomes the now current draft
		KDController.createNextDraft();

	 }

	 @Given("the dominoes in next draft are facing down")
	 public void the_dominoes_in_next_draft_are_facing_down() {

		 Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		 game.getNextDraft().setDraftStatus(DraftStatus.FaceDown); 
	 }

	 @When("the ordering of the dominoes in the next draft is initiated")
	 public void the_ordering_of_next_draft_initiated() {

		 KDController.sortNextDraft();
	 }

	 @Then("the status of the next draft is sorted")
	 public void the_status_of_next_draft_sorted() {

		 Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		 DraftStatus expectedStatus = DraftStatus.Sorted;
		 DraftStatus actualStatus = game.getCurrentDraft().getDraftStatus();

		 assertEquals(expectedStatus, actualStatus);

	 }

	 @Then("the order of dominoes in the draft will be {string}")
	 public void then_the_order_of_dominoes_in_draft(String aString) {

		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		String[] strArray = aString.split(",");
		int[] intArray = new int[strArray.length];
		Draft nextDraft = game.getCurrentDraft();

		for(int i = 0; i < strArray.length; i++) {
			    intArray[i] = Integer.parseInt(strArray[i]);
			}

		for(int i = 0; i < intArray.length; i++) {

				int expectedID = intArray[i];
				int actualID = nextDraft.getIdSortedDomino(i).getId();

				assertEquals(expectedID, actualID);

			}

	 }


	 //reveal next draft

	 @Given("the game is initialized for reveal next draft of dominoes")
	 public  void the_game_is_initialized_for_reveal_next_draft_of_dominoes(){

			KDController.initiateEmptyGame();

	 }


	 @Given("the dominoes in next draft are sorted")
	 public void the_dominoes_in_next_draft_are_sorted() {

		 Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		 Draft nextDraft = game.getNextDraft();

		 nextDraft.setDraftStatus(DraftStatus.Sorted);

	 }

	 @When("the revealing of the dominoes in the next draft is initiated")
	 public void the_revealing_of_the_dominoes_in_the_next_draft_initiated() {

		 KDController.revealNextDraft();

	 }

	 @Then("the status of the next draft is face up")
	 public void the_status_of_the_next_draft_is_face_up() {

		 Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		 Draft nextDraft = game.getCurrentDraft();
		 DraftStatus expectedStatus = DraftStatus.FaceUp;
		 DraftStatus actualStatus = nextDraft.getDraftStatus();

		 assertEquals(expectedStatus, actualStatus);

	 }

	 /**
		 * These methods checks if the Choose Next Domino feature
		 * Appropriately adds the new selection to the draft 
		 * 
		 * @see OrderAndRevealNextDraft.feature
		 * @author Keon Olszewski 260927813
		 */


}