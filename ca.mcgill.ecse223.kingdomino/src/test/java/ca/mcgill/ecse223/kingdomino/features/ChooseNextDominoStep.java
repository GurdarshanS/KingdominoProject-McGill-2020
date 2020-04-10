package ca.mcgill.ecse223.kingdomino.features;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.KDController;
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


public class ChooseNextDominoStep {
	private static Kingdomino kd = KingdominoApplication.getKingdomino();
	private static String playerColor;

	 /**
		 * These methods checks if the Choose Next Domino feature
		 * Appropriately adds the new selection to the draft 
		 * 
		 * @see OrderAndRevealNextDraft.feature
		 * @author Keon Olszewski 260927813
		 */
	
	 @Given("the game is initialized for choose next domino")
	 public void the_game_is_initialized_for_choose_next_domino() {
		 
		 	KDController.initiateEmptyGame();
		 	kd.setStateMachine();
			
			// the state here is after a player has placed/discard his domino and is ready to select the next domino, after which
			// it should be the next player's turn to place (manipuate) his domino
			// so the state machine should be in state ConfirmingChoice
			
			kd.getStateMachine().setGamestatus("ConfirmingChoice");
		}
	 
	 @Given("the next draft is sorted with dominoes {string}")
	 public void the_next_draft_is_sorted_with_dominoes(String aString) {
		 
		 Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		 String[] strArray = aString.split(",");
		 int[] intArray = new int[strArray.length];
		 Draft nextDraft = new Draft(DraftStatus.FaceDown, game);
			
		for(int i = 0; i < strArray.length; i++) {
			    intArray[i] = Integer.parseInt(strArray[i]);
				
				}
			
		for(int i = 0; i < intArray.length; i++) {
			   
				nextDraft.addIdSortedDomino(KDController.getdominoByID(intArray[i]));
			   
				}
		
		game.setNextDraft(nextDraft);
		nextDraft.setDraftStatus(DraftStatus.Sorted);
		 
	 }

	 
	 
	 @Given("the current player is {string}")
	 public void the_current_player_is(String aColor) {
		
		playerColor=aColor;
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		Player currentPlayer = sharedCucumberMethods.getPlayerByColor(aColor);
		game.setNextPlayer(currentPlayer);
		 
	 }
	 
	  @When("current player chooses to place king on {int}")
	  public void current_player_chooses_to_place_king_on(int anInt) {
		  Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		  
		  //need to put this domino in a current draft, according to new state machine rules
		  //dummy draft
		  
		  Draft dft=new Draft(DraftStatus.FaceUp,game);
		  dft.addIdSortedDomino(KDController.getdominoByID(anInt));
		  game.setCurrentDraft(dft);
		 
		  KDController.chooseNextDomino(KDController.getdominoByID(anInt));
		 
	  }
	  
	  @Then("current player king now is on {string}")
	  public void current_player_is_on(String idString) {
		  
		  Player p=sharedCucumberMethods.getPlayerByColor(playerColor);
		  
		  int expectedID = Integer.parseInt(idString);
		  int actualID = sharedCucumberMethods.getPlayerByColor(playerColor).getDominoSelection().getDomino().getId();
				  
		  assertEquals(expectedID, actualID); 
		  
	  }
	  
	  
	  @Then("the selection for next draft is now equal to {string}")
	  public void the_selection_for_next_draft_is_now(String aString) {
		  
		  Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		  Draft nextDraft = game.getNextDraft();
		  String[] strArray = aString.split(",");
		  String expectedColor;
		  String actualColor;
		  ArrayList<String> colorArray = new  ArrayList<String>();
		  List<DominoSelection> selectionList = nextDraft.getSelections();
	
		   for(int i = 0; i < strArray.length; i++) {
			  
			  if(!strArray[i].equals("none")) {
				  
				  colorArray.add(strArray[i]);
				
			  }  
		  }
		  
		  for(int i = 0; i < selectionList.size(); i++) {
			  
			  expectedColor = colorArray.get(i).toLowerCase();
			  actualColor = selectionList.get(i).getPlayer().getColor().toString().toLowerCase();
			  
			  assertEquals(expectedColor, actualColor);
		  }
		  
	}
	  
	  
	  
	  
	    @Then("the selection for the next draft selection is still {string}")
	    public void the_selection_for_the_next_draft_is_still(String aString) {
	    	
	  	  Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		  Draft nextDraft = game.getNextDraft();
		  String[] strArray = aString.split(",");
		  String expectedColor;
		  String actualColor;
		  ArrayList<String> colorArray = new  ArrayList<String>();
		  List<DominoSelection> selectionList = nextDraft.getSelections();
	
		   for(int i = 0; i < strArray.length; i++) {
			  
			  if(!strArray[i].equals("none")) {
				  
				  colorArray.add(strArray[i]);
				
			  }  
		  }
		  
		  for(int i = 0; i < selectionList.size(); i++) {
			  
			  expectedColor = colorArray.get(i).toLowerCase();
			  actualColor = selectionList.get(i).getPlayer().getColor().toString().toLowerCase();
			  
			  assertEquals(expectedColor, actualColor);
		  }
	    	
	    }
	

}
