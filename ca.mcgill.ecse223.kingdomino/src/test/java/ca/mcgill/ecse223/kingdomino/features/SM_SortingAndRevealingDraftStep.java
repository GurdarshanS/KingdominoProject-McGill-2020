package ca.mcgill.ecse223.kingdomino.features;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.KDController;
import ca.mcgill.ecse223.kingdomino.model.Domino;
import ca.mcgill.ecse223.kingdomino.model.Draft;
import ca.mcgill.ecse223.kingdomino.model.Draft.DraftStatus;
import ca.mcgill.ecse223.kingdomino.model.Game;
import ca.mcgill.ecse223.kingdomino.model.Gameplay;
import ca.mcgill.ecse223.kingdomino.model.Kingdomino;
import ca.mcgill.ecse223.kingdomino.model.Player;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.en.And;

public class SM_SortingAndRevealingDraftStep {
	private static Kingdomino kd = KingdominoApplication.getKingdomino();

	@Given("there is a next draft, face down")
	public void there_is_a_next_draft_face_down() {
	    
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
		kd.getStateMachine().setGamestatus("CreatingFirstDraft"); 	
		
	}

	@And("all dominoes in current draft are selected")
	public void all_dominoes_in_current_draft_are_selected() {
	   
		KDController.draftReadySM();
		
		for(Domino domino : KingdominoApplication.getKingdomino().getCurrentGame().getCurrentDraft().getIdSortedDominos()) {
			
			boolean chosen=KDController.chooseSM(domino);
			System.out.println("chosen: "+chosen);
			System.out.println("d has selection: "+domino.hasDominoSelection());
		
		}
		
	}

	@When("next draft is sorted")
	public void next_draft_is_sorted() {
	  
		KDController.selectionReadySM();
		
	}

	@When("next draft is revealed")
	public void next_draft_is_revealed() {
		
		System.out.println(kd.getStateMachine().getGamestatusFullName());
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
