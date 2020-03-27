//package ca.mcgill.ecse223.kingdomino.features;
//
//import static org.junit.Assert.assertEquals;
//
//import java.io.BufferedReader;
//import java.io.FileReader;
//import java.io.IOException;
//import java.util.List;
//import java.util.Map;
//
//import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
//import ca.mcgill.ecse223.kingdomino.controller.KDController;
//import ca.mcgill.ecse223.kingdomino.model.Castle;
//import ca.mcgill.ecse223.kingdomino.model.Domino;
//import ca.mcgill.ecse223.kingdomino.model.Domino.DominoStatus;
//import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom;
//import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom.DirectionKind;
//import ca.mcgill.ecse223.kingdomino.model.Draft;
//import ca.mcgill.ecse223.kingdomino.model.Draft.DraftStatus;
//import ca.mcgill.ecse223.kingdomino.model.Game;
//import ca.mcgill.ecse223.kingdomino.model.Kingdom;
//import ca.mcgill.ecse223.kingdomino.model.Kingdomino;
//import ca.mcgill.ecse223.kingdomino.model.Player;
//import ca.mcgill.ecse223.kingdomino.model.Player.PlayerColor;
//import ca.mcgill.ecse223.kingdomino.model.TerrainType;
//import io.cucumber.java.en.Given;
//import io.cucumber.java.en.Then;
//import io.cucumber.java.en.When;
//
//public class ShuffleDominosStep {
//
//	
//	/**
//	 * 
//	 * 	These methods create a game depending on the amount of players provided.
//	 * They then shuffle all the dominos randomly and make a first draft with
//	 * an amount that varies depending on the amount of players. You then verify the
//	 * amount of dominos drafted and the amount left. Then, they arrange the dominos
//	 * in the order that is specified and perform a draft.
//	 *  
//	 *  @see ShuffleDomino.feature
//	 *  @author Gurdarshan Singh 260927466
//	 *  
//	 */
//	
//	
//	
//	
//	Draft firstDraft ;
//	Domino[] DomArray;
//	Integer DraftAmount;
//	Integer DomAmount;
//	Integer[] Dominos;
//	
//	@Given("the game is initialized for shuffle dominoes")
//	public void the_game_is_initialized_for_shuffle_dominoes() {
//		
//	}
//
//	@Given("there are {int} players playing")
//	public void there_are_players_playing(Integer int1) {
//		if(int1 == 4) {
//			DomAmount = 48;
//			DraftAmount = 4;
//		} else if (int1 == 3) {
//			DomAmount = 36;
//			DraftAmount = 3;
//		} else if (int1 == 2) {
//			DomAmount = 24;
//			DraftAmount = 4;
//		}
//		
//		
//		KDController.numOfPlayers(int1);
//	   
//	}
//
//	@When("the shuffling of dominoes is initiated")
//	public void the_shuffling_of_dominoes_is_initiated() {
//	   DomArray = KDController.shuffleDominos();
//	}
//
//	@Then("the first draft shall exist")
//	public void the_first_draft_shall_exist() {
//		boolean check = true;
//		boolean recheck = false;
//		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
//	   firstDraft = new Draft(DraftStatus.FaceDown,game);
//	   for(int i=0; i<DraftAmount; i++) {
//		   recheck = firstDraft.addIdSortedDomino(DomArray[i]);
//	   }
//	   assertEquals(check,recheck);
//	}
//
//	@Then("the first draft should have {int} dominoes on the board face down")
//	public void the_first_draft_should_have_dominoes_on_the_board_face_down(Integer int1) {
//	   Integer domamount = firstDraft.getIdSortedDominos().size();
//		assertEquals(int1,domamount);
//	  
//	}
//
//	@Then("there should be {int} dominoes left in the draw pile")
//	public void there_should_be_dominoes_left_in_the_draw_pile(Integer int1) {
//		Integer DomLeft = DomAmount - DraftAmount;
//		assertEquals(int1,DomLeft);
//	}
//	
//	
//	@When("I initiate to arrange the domino in the fixed order {string}")
//	public void i_initiate_to_arrange_the_domino_in_the_fixed_order(String string) {
//	    Dominos = KDController.arrangeDominos(string);
//	    DomArray = KDController.arrangeTheDominos(string);
//	}
//
//	@Then("the draw pile should consist of everything in {string} except the first {int} dominoes with their order preserved")
//	public void the_draw_pile_should_consist_of_everything_in_except_the_first_dominoes_with_their_order_preserved(String string, Integer int1) {
//		Integer[] IntArray = new Integer[48];
//		String[] StringArray = string.split(", ");
//		
//		for(int i = 0; i < StringArray.length; i++) {
//			IntArray[i] = (Integer.parseInt(StringArray[i]));	
//		}
//		
//		for(int i=0; i<IntArray.length; i++) {
//			assertEquals(Dominos[i],IntArray[i]);
//		}
//		
//		assertEquals(int1,DraftAmount);
//		DomArray = KDController.removeDraftDominos(string,int1);
//		
//		
//	}
//}