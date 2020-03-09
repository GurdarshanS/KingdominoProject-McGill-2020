package ca.mcgill.ecse223.kingdomino.features;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
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
import ca.mcgill.ecse223.kingdomino.model.User;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ProvideUserProfileStep {
	
	private static String ProvideUserStatus = "succeed";

	
	/**
	 * These methods checks if the Kingdomino app maintains 
	 * User game statistics, that unique users are created, 
	 * and view users has the right order of users
	 * 
	 * @see ProvideUserProfile.feature
	 * @author Keon Olszewski 260927813
	 */
  		
  
    @Given("the program is started and ready for providing user profile")
	   public void program_is_started_and_ready_for_providing_user() {
		   
		   Kingdomino kingdomino = new Kingdomino();
		   KingdominoApplication.setKingdomino(kingdomino);
		   
	   }
    
    @Given("there are no users exist")
    public void no_users_exist() {
    	
    	//KingDomino is initialized without users
    	
    }
    
    @When("I provide my username {string} and initiate creating a new user")
    public void I_provide_my_username_and_initiate_creating_user_new_user(String aString) {
    	
    	ProvideUserStatus = "succeed";
    	try {
    	 KDController.ProvidetUserProfile(aString);
    	 
    	 
    	}
    	
    	catch(Exception e){
    		
    		ProvideUserStatus = "fail";
    	}
    	
    	
    }
    
    @Then("the user {string} shall be in the list of users")
    public void the_user_is_in_userList(String aString) {
    	
    	Kingdomino kingdomino = KingdominoApplication.getKingdomino();
    	String actualName = kingdomino.getUser(0).getName();
    	String expectedName = aString;
    	
    	assertEquals(expectedName, actualName);
    	
    }
    
    @Given("the following users exist:")
    public void the_following_users_exist(io.cucumber.datatable.DataTable dataTable) {
    	
    	Kingdomino kingdomino = KingdominoApplication.getKingdomino();
    	
    	List<Map<String, String>> valueMaps = dataTable.asMaps();
		for (Map<String, String> map : valueMaps) {
			
			String userName = (map.get("name"));
			
			kingdomino.addUser(userName);
		}
    	
    }
    
    @Then("the user creation shall {string}")
    public void the_user_creation_status_is(String aString) {
    	
    	String expectedStatus = aString;
    	String actualStatus = ProvideUserStatus;
    	
    	assertEquals(expectedStatus, actualStatus);
    		
    }
   
    @When("I initiate the browsing of all users")
    public void initiate_browsing_all_users() {
    	
    	KDController.BrowseUserList();
    }
    
    @Then("the users in the list shall be in the following alphabetical order:")
    public void the_users_in_list_alphabetical_order(io.cucumber.datatable.DataTable dataTable) {
    	
    	List<User> userList = KDController.BrowseUserList();
    	
    	List<Map<String, String>> valueMaps = dataTable.asMaps();
		for (Map<String, String> map : valueMaps) {
			
			String expectedName = (map.get("name"));
			Integer placeInlist = Integer.decode(map.get("placeinlist"));
			String actualName = userList.get(placeInlist-1).getName();
			
			assertEquals(expectedName, actualName);
		}
    	
    }
    
    @Given("the following users exist with their game statistics:")
    public void the_following_users_and_their_statistics_exist(io.cucumber.datatable.DataTable dataTable) {
    	
    	Kingdomino kingdomino = KingdominoApplication.getKingdomino();
    	
    	
    	List<Map<String, String>> valueMaps = dataTable.asMaps();
		for (Map<String, String> map : valueMaps) {
			
				String userName = (map.get("name"));
				User userToAdd = new User(userName,kingdomino);
				userToAdd.setPlayedGames(Integer.decode(map.get("playedGames")));
				userToAdd.setWonGames(Integer.decode(map.get("wonGames")));
				kingdomino.addUser(userToAdd);
			
		}
    }
    
    @When("I initiate querying the game statistics for a user {string}")
    public void I_initiate_querying_game_statistics_of_user(String userName) {
    	
    	KDController.queryUser(userName);
    	
    }
    
    @Then("the number of games played by {string} shall be {int}") 
    public void the_number_of_games_played_by_user_is(String userName, int gamesPlayed) {
    	
    	User currentUser = KDController.getUserByName(userName);
    	int actualGamesPlayed = currentUser.getPlayedGames();
    	int expectedGamesPlayed = gamesPlayed;
    	
    	assertEquals(expectedGamesPlayed, actualGamesPlayed);
    	
    }
    
    @Then("the number of games won by {string} shall be {int}")
	public void the_number_of_games_won_by_user_is(String userName, int gamesWon) {

			User currentUser = KDController.getUserByName(userName);
			int actualGamesWon = currentUser.getWonGames();
			int expectedGamesWon = gamesWon;

			assertEquals(expectedGamesWon, actualGamesWon);

	}

}
