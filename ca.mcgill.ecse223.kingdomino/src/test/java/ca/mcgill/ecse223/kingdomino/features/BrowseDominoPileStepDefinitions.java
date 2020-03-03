package ca.mcgill.ecse223.kingdomino.features;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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
import ca.mcgill.ecse223.kingdomino.model.Kingdom;
import ca.mcgill.ecse223.kingdomino.model.Kingdomino;
import ca.mcgill.ecse223.kingdomino.model.Player;
import ca.mcgill.ecse223.kingdomino.model.Player.PlayerColor;
import ca.mcgill.ecse223.kingdomino.model.TerrainType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class BrowseDominoPileStepDefinitions {

	
	@Given("the program is started and ready for browsing dominoes")
	public void the_program_is_started_and_ready_for_browsing_dominoes() {
		KDController.initiateEmptyGame();
	}
	
	@When("I initiate the browsing of all dominoes")
	public void I_initiate_the_browsing_of_all_dominoes() {
		KDController.listDominos();
	}
	
	@Then("all the dominoes are listed in increasing order of identifiers")
	public void all_the_dominoes_are_listed_in_increasing_order_of_identifiers() {
		KDController.printListDominos();
	}
	
	@When("I provide a domino ID {int}")
	public void i_provide_a_domino_ID(Integer int1) {
	   KDController.takeInputID(int1);
	}

	@Then("the listed domino has {string} left terrain")
	public void the_listed_domino_has_left_terrain(String string) {
		
	    KDController.getLeftTile(string);
	    
	}

	@Then("the listed domino has {string} right terrain")
	public void the_listed_domino_has_right_terrain(String string) {
	   KDController.getRightTile(string);
	 
	}

	@Then("the listed domino has {int} crowns")
	public void the_listed_domino_has_crowns(Integer int1) {
	    KDController.getCrowns(int1);

	}

	@When("I initiate the browsing of all dominoes of {string} terrain type")
	public void i_initiate_the_browsing_of_all_dominoes_of_terrain_type(String string) {
	    
		
		
	}

	@Then("list of dominoes with IDs {string} should be shown")
	public void list_of_dominoes_with_IDs_should_be_shown(String string) {
		
		KDController.filterbyTerrain(string);
	
	}
}
