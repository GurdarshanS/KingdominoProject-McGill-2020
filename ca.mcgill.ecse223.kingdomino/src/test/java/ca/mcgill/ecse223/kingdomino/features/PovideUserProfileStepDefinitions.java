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
import ca.mcgill.ecse223.kingdomino.model.DominoSelection;
import ca.mcgill.ecse223.kingdomino.model.Draft;
import ca.mcgill.ecse223.kingdomino.model.Draft.DraftStatus;
import ca.mcgill.ecse223.kingdomino.model.Game;
import ca.mcgill.ecse223.kingdomino.model.Kingdom;
import ca.mcgill.ecse223.kingdomino.model.Kingdomino;
import ca.mcgill.ecse223.kingdomino.model.Player;
import ca.mcgill.ecse223.kingdomino.model.Player.PlayerColor;
import ca.mcgill.ecse223.kingdomino.model.TerrainType;
import ca.mcgill.ecse223.kingdomino.model.User;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;


public class PovideUserProfileStepDefinitions {
	
	@Given("the program is started and ready for providing user profile")
	public void the_program_is_started_and_ready_for_providing_user_profile(){
		
		Kingdomino kingdomino = new Kingdomino();
		
		
	}
	
	@Given("there are no users exist")
	public void no_users_exist() {
		
		Kingdomino kingdomino =	KingdominoApplication.getKingdomino();
		
		//set List of Users = null??
		// create a new list of users?
		
	}
	
	@When("I provide my username {String} and initiate creating a new user")
	public void povide_user_name(String userString) {
		
		Kingdomino kingdomino =	KingdominoApplication.getKingdomino();
		
		kingdomino.addUser(userString);
	}

}
