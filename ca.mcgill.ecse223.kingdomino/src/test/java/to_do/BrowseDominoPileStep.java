package to_do;
//package ca.mcgill.ecse223.kingdomino.features;
//
//import static org.junit.Assert.assertEquals;
//
//import java.io.BufferedReader;
//import java.io.FileReader;
//import java.io.IOException;
//import java.util.ArrayList;
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
//public class BrowseDominoPileStep {
//
//	/**
//	 * 
//	 * 	These methods retrieve all of the dominos present in the game and lists them in increasing order.
//	 * Afterwards, depending on the given ID, they give either the terrain or the amount of crowns of a 
//	 * certain tile. They also filter the dominos in order to return the dominos of the specified
//	 * terrain type.
//	 *  
//	 *  @see BrowseDominoPile.feature
//	 *  @author Gurdarshan Singh 260927466
//	 *  
//	 */
//	
//	
//	
//	Domino dom;
//	ArrayList myDominoes;
//	Domino[] Dominos;
//	
//	@Given("the program is started and ready for browsing dominoes")
//	public void the_program_is_started_and_ready_for_browsing_dominoes() {
//		KDController.initiateEmptyGame();
//	}
//	
//	@When("I initiate the browsing of all dominoes")
//	public void I_initiate_the_browsing_of_all_dominoes() {
//		Dominos = KDController.listDominos();
//	}
//	
//	@Then("all the dominoes are listed in increasing order of identifiers")
//	public void all_the_dominoes_are_listed_in_increasing_order_of_identifiers() {
//		boolean check = true;
//		boolean recheck = false;
//		for(int i = 1; i < 48; i++) {
//			if(Dominos[i-1].getId()<Dominos[i].getId()) {
//				recheck = true;
//			}
//		}
//		assertEquals(check,recheck);
//	}
//	
//	@When("I provide a domino ID {int}")
//	public void i_provide_a_domino_ID(Integer int1) {
//	  dom = KDController.getdominoByID(int1);
//	}
//
//	@Then("the listed domino has {string} left terrain")
//	public void the_listed_domino_has_left_terrain(String string) {
//		TerrainType t1 = dom.getLeftTile();
//		TerrainType t2 = KDController.retrieveTerrainType(string);
//		assertEquals(t2, t1);
//	 
//	    
//	}
//
//	@Then("the listed domino has {string} right terrain")
//	public void the_listed_domino_has_right_terrain(String string) {
//		TerrainType t1 = dom.getRightTile();
//		TerrainType t2 = KDController.retrieveTerrainType(string);
//		assertEquals(t2, t1);
//	 
//	}
//
//	@Then("the listed domino has {int} crowns")
//	public void the_listed_domino_has_crowns(Integer int1) {
//	   Integer crowns = dom.getLeftCrown()+dom.getRightCrown();
//	   assertEquals(int1,crowns);
//
//	}
//
//	@When("I initiate the browsing of all dominoes of {string} terrain type")
//	public void i_initiate_the_browsing_of_all_dominoes_of_terrain_type(String string) {
//	    
//		myDominoes = KDController.filterbyTerrain(string);
//		
//	}
//
//	@Then("list of dominoes with IDs {string} should be shown")
//	public void list_of_dominoes_with_IDs_should_be_shown(String string) {
//		Integer[] IntArray = new Integer[48];
//		String[] StringArray = string.split(",");
//		
//		for(int i = 0; i < myDominoes.size(); i++) {
//			IntArray[i] = (Integer.parseInt(StringArray[i]));
//			assertEquals(IntArray[i],myDominoes.get(i));
//			
//		}
//	
//	}
//	
//	
//	
//	
//	
//}