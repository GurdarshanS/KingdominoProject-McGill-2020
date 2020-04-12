package to_do;

import static org.junit.Assert.assertEquals;

import java.util.List;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.KDController;
import ca.mcgill.ecse223.kingdomino.model.Domino;
import ca.mcgill.ecse223.kingdomino.model.Draft;
import ca.mcgill.ecse223.kingdomino.model.Draft.DraftStatus;
import ca.mcgill.ecse223.kingdomino.model.Game;
import ca.mcgill.ecse223.kingdomino.model.Gameplay;
import ca.mcgill.ecse223.kingdomino.model.Player;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class SortingAndRevealingDraftStep {

	@Given("there is a next draft, face down")
	public void there_is_a_next_draft_face_down() {
	    
		KDController.initiateEmptyGame();
		Gameplay sm = KingdominoApplication.getStateMachine(); // Initializes State Machine
		
	}

	@Given("all dominoes in current draft are selected")
	public void all_dominoes_in_current_draft_are_selected() {
	   
		KDController.draftReadySM();
		
		for(Domino domino : KingdominoApplication.getKingdomino().getCurrentGame().getCurrentDraft().getIdSortedDominos()) {
			
			KDController.chooseSM(domino);
		
		}
		
	}

	@When("next draft is sorted")
	public void next_draft_is_sorted() {
	  
		KDController.selectionReadySM();
		
	}

	@When("next draft is revealed")
	public void next_draft_is_revealed() {
	   
		KDController.draftReadySM();
		
	}

	@Then("the next draft shall be sorted")
	public void the_next_draft_shall_be_sorted() {
	   
		Draft currentDraft = KingdominoApplication.getKingdomino().getCurrentGame().getCurrentDraft();
		List<Domino> currentDraftDominos = currentDraft.getIdSortedDominos();
		
		boolean isSorted = true;
		
		for(int i = 0; i< currentDraftDominos.size(); i++) {
			
			for(int j = i+1; j<currentDraftDominos.size(); j++) {
				
				if(currentDraftDominos.get(i).getId() > currentDraftDominos.get(j).getId()) isSorted = false;
				
			}
			
		}
	
		assertEquals(true, isSorted);
		
	}

	@Then("the next draft shall be facing up")
	public void the_next_draft_shall_be_facing_up() {
	  
		assertEquals(DraftStatus.FaceUp, KingdominoApplication.getKingdomino().getCurrentGame().getCurrentDraft().getDraftStatus());
		
	}

	@Then("it shall be the player's turn with the lowest domino ID selection")
	public void it_shall_be_the_player_s_turn_with_the_lowest_domino_ID_selection() {
	    
		Game currentGame = KingdominoApplication.getKingdomino().getCurrentGame();
		
		Player expectedPlayer = currentGame.getPlayer(0);
		Player actualPlayer = currentGame.getNextPlayer();
		
		for(Player player : currentGame.getPlayers()) {
			
			if(player.getDominoSelection().getDomino().getId() < expectedPlayer.getDominoSelection().getDomino().getId()) expectedPlayer = player;
			
		}
		
		assertEquals(expectedPlayer, actualPlayer);
		
	}

}