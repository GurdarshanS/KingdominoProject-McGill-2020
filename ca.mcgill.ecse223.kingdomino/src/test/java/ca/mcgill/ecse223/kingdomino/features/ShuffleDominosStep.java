package ca.mcgill.ecse223.kingdomino.features;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.KDController;
import ca.mcgill.ecse223.kingdomino.development.View;
import ca.mcgill.ecse223.kingdomino.model.Castle;
import ca.mcgill.ecse223.kingdomino.model.Domino;
import ca.mcgill.ecse223.kingdomino.model.Domino.DominoStatus;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom.DirectionKind;
import ca.mcgill.ecse223.kingdomino.model.Draft;
import ca.mcgill.ecse223.kingdomino.model.Draft.DraftStatus;
import ca.mcgill.ecse223.kingdomino.model.Game;
import ca.mcgill.ecse223.kingdomino.model.Kingdom;
import ca.mcgill.ecse223.kingdomino.model.Kingdomino;
import ca.mcgill.ecse223.kingdomino.model.Player;
import ca.mcgill.ecse223.kingdomino.model.Player.PlayerColor;
import ca.mcgill.ecse223.kingdomino.model.TerrainType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ShuffleDominosStep {
	private static int numPlayer;
	private static Integer[] Dominos;
	private static Domino[] DomArray;
	private static int DomAmount;
	private static int DraftAmount;
	
	/**
	 * 
	 * 	These methods create a game depending on the amount of players provided.
	 * They then shuffle all the dominos randomly and make a first draft with
	 * an amount that varies depending on the amount of players. You then verify the
	 * amount of dominos drafted and the amount left. Then, they arrange the dominos
	 * in the order that is specified and perform a draft.
	 *  
	 *  @see ShuffleDomino.feature
	 *  @author Gurdarshan Singh 260927466
	 *  
	 */
	
	
	
	

	
	@Given("the game is initialized for shuffle dominoes")
	public void the_game_is_initialized_for_shuffle_dominoes() {
		int numPlayers=4;
		List<String> selectedBonusOptions = new ArrayList<String>();
		Kingdomino kd = KingdominoApplication.getKingdomino();
		try{
			
			KDController.setGameOptions(numPlayers, selectedBonusOptions);
			
			//		all these below can be replaced with KDController.startANewGame();
			Game game = kd.getCurrentGame();
			int dominoNums=game.getMaxPileSize();
			KDController.createDominoPile(game,dominoNums);
			KDController.generateInitialPlayerOrder();

		}
		catch(Exception e) {}
	}

	@Given("there are {int} players playing")
	public void there_are_players_playing(Integer int1) {
	
		numPlayer=int1;;
	   
	}

	@When("the shuffling of dominoes is initiated")
	public void the_shuffling_of_dominoes_is_initiated() {
		KDController.shuffleDominoPile();
	}

	@Then("the first draft shall exist")
	public void the_first_draft_shall_exist() {
		KDController.createNextDraft();
		Game game=KingdominoApplication.getKingdomino().getCurrentGame();
		for (Domino d:game.getNextDraft().getIdSortedDominos()) {
			d.setStatus(DominoStatus.InPile);
		}
	   assertEquals(true,KingdominoApplication.getKingdomino().getCurrentGame().hasCurrentDraft());
	}

	@Then("the first draft should have {int} dominoes on the board face down")
	public void the_first_draft_should_have_dominoes_on_the_board_face_down(Integer int1) {
	   Integer domamount = KingdominoApplication.getKingdomino().getCurrentGame().getCurrentDraft().getIdSortedDominos().size();
		assertEquals(int1,domamount);
	  
	}

	@Then("there should be {int} dominoes left in the draw pile")
	public void there_should_be_dominoes_left_in_the_draw_pile(Integer int1) {
		Game game=KingdominoApplication.getKingdomino().getCurrentGame();
		DomAmount=game.getAllDominos().size();
		DraftAmount=game.getCurrentDraft().getIdSortedDominos().size();
		Integer DomLeft = DomAmount - DraftAmount;
		assertEquals(int1,DomLeft);
	}
	
	
	@When("I initiate to arrange the domino in the fixed order {string}")
	public void i_initiate_to_arrange_the_domino_in_the_fixed_order(String string) {
		Kingdomino kd = KingdominoApplication.getKingdomino();
		Integer[] orderedIds=KDController.parseDominoIds(string);
	   for (int i=0;i<orderedIds.length;i++) {
		   kd.getCurrentGame().addOrMoveAllDominoAt(KDController.getdominoByID(orderedIds[i]), i);
	   }
	   
	   kd.getCurrentGame().setTopDominoInPile(kd.getCurrentGame().getAllDomino(0));
	   for (int i=0;i<kd.getCurrentGame().getAllDominos().size()-1;i++) {
		   kd.getCurrentGame().getAllDomino(i).setNextDomino(kd.getCurrentGame().getAllDomino(i+1));
	   }
	   
	}

	@Then("the draw pile should consist of everything in {string} except the first {int} dominoes with their order preserved")
	public void the_draw_pile_should_consist_of_everything_in_except_the_first_dominoes_with_their_order_preserved(String string, Integer int1) {
		
		Integer[] orderedIds=KDController.parseDominoIds(string);
		List<Integer> remainIds=new ArrayList<Integer>();
		Game game=KingdominoApplication.getKingdomino().getCurrentGame();
		
		for (Domino d:game.getAllDominos()) {
			if (d.getStatus().equals(DominoStatus.InPile)){
				remainIds.add(d.getId());
			}
		}
		
		
		boolean match=true;
		for (int i=0;i<remainIds.size();i++) {
			if (remainIds.get(i).intValue()!=orderedIds[i+int1]) {
				match=false;
				break;
			}
		}
		
		assertEquals(true,match);
		
	}
}