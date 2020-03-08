package ca.mcgill.ecse223.kingdomino.features;

import static org.junit.Assert.assertEquals;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.KDController;
import ca.mcgill.ecse223.kingdomino.model.Castle;
import ca.mcgill.ecse223.kingdomino.model.Domino;
import ca.mcgill.ecse223.kingdomino.model.Domino.DominoStatus;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom;
import ca.mcgill.ecse223.kingdomino.model.DominoSelection;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom.DirectionKind;
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

public class CucumberStepDefinition {
	
	private static String ProvideUserStatus = "succeed";
	
	
	/**
	 * These methods checks if the next drafts take
	 * the top 5 dominos off the pile and that the
	 * pile is empty after the  number of rounds 
	 * 
	 * @see CreateNextDraft.feature
	 * @author Keon Olszewski 260927813
	 */
	
	
	@Given ("the game is initialized to create next draft")
	public void the_game_is_initialized_for_next_draft() {
		// Intialize empty game
		Kingdomino kingdomino = new Kingdomino();
		Game game = new Game(48, kingdomino);
		game.setNumberOfPlayers(4);
		kingdomino.setCurrentGame(game);
		// Populate game
		addDefaultUsersAndPlayers(game);
		createAllDominoes(game);
		game.setNextPlayer(game.getPlayer(0));
		KingdominoApplication.setKingdomino(kingdomino);
	}

	
	
	@Given("there has been {int} drafts created")
	public static void there_has_been_NumDrafts(int numDrafts) {

		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		
		for(int i = 0; i < numDrafts; i++ ) {
			
			
			Draft aDraft = new Draft(DraftStatus.FaceDown, game);
			game.addAllDraft(aDraft);
	
			
		}		
		game.setNextDraft(game.getAllDraft(game.getAllDrafts().size()-1));
	}
	
	@Given("there is a current draft")
	public static void there_is_currentDraft() {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		
		game.setCurrentDraft(game.getAllDraft(0));
	}
	
	@Given("there is a next draft")
	public static void there_is_nextDraft() {
		
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		game.setNextDraft(game.getAllDraft(1));
		
		}
	
	@Given("the top 5 dominoes in my pile have the IDs {string}")
	public static void the_top_5_dominoes_in_pile(String aString) {
		
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		
		String[] strArray = aString.split(",");
		int[] intArray = new int[strArray.length];
		
		for(int i = 0; i < strArray.length; i++) {
		    intArray[i] = Integer.parseInt(strArray[i]);
			}
		
		for(int i = 0; i < intArray.length; i++) {
		   Domino aDomino = getdominoByID(intArray[i]);
		   
		  game.addOrMoveAllDominoAt(aDomino, i);
			}
		
		for(int i = 1; i < intArray.length-1; i++) {
			
			Domino aDomino = getdominoByID(intArray[i]);
			   
			aDomino.setNextDomino(getdominoByID(intArray[i+1]));
			aDomino.setPrevDomino(getdominoByID(intArray[i-1]));
			
			}
		
		game.setTopDominoInPile(getdominoByID(intArray[0]));
		
		getdominoByID(intArray[0]).setNextDomino(getdominoByID(intArray[1]));
		getdominoByID(intArray[intArray.length-1]).setPrevDomino(getdominoByID(intArray[intArray.length-2]));
		
		//./gradlew test --info
	} //the_top_5_dominoes_in_pile
	
	@When("create next draft is initiated")
	public static void create_next_draft_initiatied() {
			
		KDController.createNextDraft();
		
	}
	
	@Then("a new draft is created from dominoes {string}")
	public static void then_new_draft_is_created_from_dominoes(String aString) {
		
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		String[] strArray = aString.split(",");
		int[] intArray = new int[strArray.length];
		DominoStatus expectedStatus = DominoStatus.InNextDraft;
		
		for(int i = 0; i < strArray.length; i++) {
		   
			intArray[i] = Integer.parseInt(strArray[i]);
				
		}
		
		for(int i = 0; i < intArray.length; i++) {
			   
			DominoStatus actualStatus = getdominoByID(intArray[i]).getStatus();
			assertEquals(expectedStatus, actualStatus);
		}
		
	}
	
	@Then("the next draft now has the dominoes {string}")
	public static void then_new_draft_now_has_dominoes(String aString) {
		
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		String[] strArray = aString.split(",");
		int[] intArray = new int[strArray.length];
		Draft nextDraft = game.getNextDraft();
		
		for(int i = 0; i < strArray.length; i++) {
		   
			intArray[i] = Integer.parseInt(strArray[i]);
				
		}
		
		for(int i = 0; i < intArray.length; i++) {
			   
			Domino expectedDomino = getdominoByID(intArray[i]);
			Domino actualDomino = nextDraft.getIdSortedDomino(i);
			assertEquals(expectedDomino, actualDomino);
		}
	}
	
	@Then("the dominoes in the next draft are face down")
	public static void the_dominoes_in_next_draft_are_facedown() {
		
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		Draft nextDraft = game.getNextDraft();
		DraftStatus expectedStatus = DraftStatus.FaceDown;
		DraftStatus actualStatus = nextDraft.getDraftStatus();
		
		assertEquals(expectedStatus, actualStatus);
	}
	
	@Then("the top domino of the pile is ID {int}")
	public static void the_top_domino_of_pile_is(int anInt) {
		
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		Domino actualDomino = game.getTopDominoInPile();
		Domino expectedDomino = getdominoByID(anInt);
		
		assertEquals(expectedDomino, actualDomino);
		
	}
	   
	
	@Then("the former next draft is now the current draft")
	public static void the_former_nextdraft_is_currentdraft() {
		
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		Draft nextDraft = game.getNextDraft();
		
		if(nextDraft == null) {
			Draft expectedDraft = game.getAllDraft(game.getAllDrafts().size()-1);
			Draft actualDraft = game.getCurrentDraft();
			assertEquals(expectedDraft, actualDraft);	
			
		}
		
		else {
		
		Draft expectedDraft = game.getAllDraft(game.getAllDrafts().size()-2);
		Draft actualDraft = game.getCurrentDraft();
		assertEquals(expectedDraft, actualDraft);
		}
		
	}
	
	
	
	@Given("this is a {int} player game")
	public static void this_is_a_numPlayer_game(int aInt) {
		
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		game.setNumberOfPlayers(aInt);
	}
	
	
	
	
	@Then("the pile is empty")
	public static void the_pile_is_empty(){
		
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		Domino expectedDomino = null;
		Domino actualDomino = game.getTopDominoInPile();
		
		assertEquals(expectedDomino, actualDomino);
		
	}
	
	
	@Then("there is no next draft")
	public static void there_is_no_nextDraft(){
		
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		Draft expectedDraft = null;
		Draft actualDraft = game.getNextDraft();
		
		assertEquals(expectedDraft, actualDraft);
		
	}
	
	/**
	 * These methods checks if the next drafts are ordered 
	 * and revealed properly with the order and reveal features
	 * 
	 * @see OrderAndRevealNextDraft.feature
	 * @author Keon Olszewski 260927813
	 */
	
	
	 @Given("the game is initialized for order next draft of dominoes")
	 public void the_game_is_initialized_for_order_next_draft() {
		 
			Kingdomino kingdomino = new Kingdomino();
			Game game = new Game(48, kingdomino);
			game.setNumberOfPlayers(4);
			kingdomino.setCurrentGame(game);
			// Populate game
			addDefaultUsersAndPlayers(game);
			createAllDominoes(game);
			game.setNextPlayer(game.getPlayer(0));
			KingdominoApplication.setKingdomino(kingdomino);
		 
		 
	 }// game initialized for order next draft
	 
	 
	 @Given("the next draft is {string}")
	 public void the_next_draft(String aString) {
		 
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		String[] strArray = aString.split(",");
		int[] intArray = new int[strArray.length];
		Draft nextDraft = new Draft(DraftStatus.FaceDown, game);
			
		for(int i = 0; i < strArray.length; i++) {
		
			intArray[i] = Integer.parseInt(strArray[i]);
		}
		
		for(int i = 0; i < intArray.length; i++) {
			   
			nextDraft.addIdSortedDomino(getdominoByID(intArray[i]));
			   
		}
			
			
		game.setNextDraft(nextDraft);
		 
	 }
	 
	 @Given("the dominoes in next draft are facing down")
	 public void the_dominoes_in_next_draft_are_facing_down() {
		 
		 Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		 game.getNextDraft().setDraftStatus(DraftStatus.FaceDown); 
	 }
	 
	 @When("the ordering of the dominoes in the next draft is initiated")
	 public void the_ordering_of_next_draft_initiated() {
		 
		 KDController.OrderNextDraft();
	 }
	 
	 @Then("the status of the next draft is sorted")
	 public void the_status_of_next_draft_sorted() {
		 
		 Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		 DraftStatus expectedStatus = DraftStatus.Sorted;
		 DraftStatus actualStatus = game.getNextDraft().getDraftStatus();
		 
		 assertEquals(expectedStatus, actualStatus);
		 
	 }
	 
	 @Then("the order of dominoes in the draft will be {string}")
	 public void then_the_order_of_dominoes_in_draft(String aString) {
		 
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		String[] strArray = aString.split(",");
		int[] intArray = new int[strArray.length];
		Draft nextDraft = game.getNextDraft();
			
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
		 
			// Intialize empty game
			Kingdomino kingdomino = new Kingdomino();
			Game game = new Game(48, kingdomino);
			game.setNumberOfPlayers(4);
			kingdomino.setCurrentGame(game);
			// Populate game
			addDefaultUsersAndPlayers(game);
			createAllDominoes(game);
			game.setNextPlayer(game.getPlayer(0));
			KingdominoApplication.setKingdomino(kingdomino);
			
	 }
	 
	 
	 @Given("the dominoes in next draft are sorted")
	 public void the_dominoes_in_next_draft_are_sorted() {
		 
		 Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		 Draft nextDraft = game.getNextDraft();
		 
		 nextDraft.setDraftStatus(DraftStatus.Sorted);
		 
	 }
	 
	 @When("the revealing of the dominoes in the next draft is initiated")
	 public void the_revealing_of_the_dominoes_in_the_next_draft_initiated() {
		 
		 KDController.RevealNextDraft();
		 
	 }
	 
	 @Then("the status of the next draft is face up")
	 public void the_status_of_the_next_draft_is_face_up() {
		 
		 Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		 Draft nextDraft = game.getNextDraft();
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
	 
	 @Given("the game is initialized for choose next domino")
	 public void the_game_is_initialized_for_choose_next_domino() {
		 
		 	Kingdomino kingdomino = new Kingdomino();
			Game game = new Game(48, kingdomino);
			game.setNumberOfPlayers(4);
			kingdomino.setCurrentGame(game);
			// Populate game
			addDefaultUsersAndPlayers(game);
			createAllDominoes(game);
			game.setNextPlayer(game.getPlayer(0));
			KingdominoApplication.setKingdomino(kingdomino);
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
			   
				nextDraft.addIdSortedDomino(getdominoByID(intArray[i]));
			   
				}
		
		game.setNextDraft(nextDraft);
		nextDraft.setDraftStatus(DraftStatus.Sorted);
		 
	 }
	 
	 
	 @Given("player's domino selection {string}")
	 public void players_domino_selection(String aString) {
		
		 Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		 String[] strArray = aString.split(",");
		 Draft nextDraft = game.getNextDraft();
			
			
			for(int i = 0; i < strArray.length; i++) {
				
				if(!strArray[i].equalsIgnoreCase("none")) {
					
					nextDraft.addSelection(getPlayerByColor(strArray[i]), nextDraft.getIdSortedDomino(i));
					
					}
			    }
		 
	 }
	 
	 
	 @Given("the current player is {string}")
	 public void the_current_player_is(String aColor) {
		
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		Player currentPlayer = getPlayerByColor(aColor);
		game.setNextPlayer(currentPlayer);
		 
	 }
	 
	  @When("current player chooses to place king on {int}")
	  public void current_player_chooses_to_place_king_on(int anInt) {
		  
		  KDController.ChoosNextDomino(getdominoByID(anInt));
		 
	  }
	  
	  @Then("current player king now is on {string}")
	  public void current_player_is_on(String idString) {
		  
		  Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		  
		  int expectedID = Integer.parseInt(idString);
		  int actualID = game.getNextPlayer().getDominoSelection().getDomino().getId();
				  
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
	    	
	    	User currentUser = getUserByName(userName);
	    	int actualGamesPlayed = currentUser.getPlayedGames();
	    	int expectedGamesPlayed = gamesPlayed;
	    	
	    	assertEquals(expectedGamesPlayed, actualGamesPlayed);
	    	
	    }
	    
	    @Then("the number of games won by {string} shall be {int}")
		public void the_number_of_games_won_by_user_is(String userName, int gamesWon) {
	
				User currentUser = getUserByName(userName);
				int actualGamesWon = currentUser.getWonGames();
				int expectedGamesWon = gamesWon;
	
				assertEquals(expectedGamesWon, actualGamesWon);
	
		}
///////////////////////////////////////
/// -----Keon's Helper Methods  ---- ///
///////////////////////////////////////
	 
	public Player getPlayerByColor(String aColor) { 
		 
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		 List<Player> playerList = game.getPlayers();
		 for(int i = 0; i < playerList.size(); i++) {
			 
			 if(playerList.get(i).getColor().toString().equalsIgnoreCase(aColor)) {
				 
				 return playerList.get(i);
			 }
		 
		 }
		 
		 return null;
	}
	
	public User getUserByName(String username) {
		Kingdomino kingdomino = KingdominoApplication.getKingdomino();
		List<User> userList = kingdomino.getUsers();
		
		for(int i = 0; i< userList.size(); i++) {
			
			if(userList.get(i).getName().equals(username)) return userList.get(i);
		}
		
		return null;
	}  
	 
	 
	///////////////////////////////////////
	/// -----Private Helper Methods---- ///
	///////////////////////////////////////

	private void addDefaultUsersAndPlayers(Game game) {
		String[] userNames = { "User1", "User2", "User3", "User4" };
		for (int i = 0; i < userNames.length; i++) {
			User user = game.getKingdomino().addUser(userNames[i]);
			Player player = new Player(game);
			player.setUser(user);
			player.setColor(PlayerColor.values()[i]);
			Kingdom kingdom = new Kingdom(player);
			new Castle(0, 0, kingdom, player);
		}
	}

	private void createAllDominoes(Game game) {
		try {
			BufferedReader br = new BufferedReader(new FileReader("src/main/resources/alldominoes.dat"));
			String line = "";
			String delimiters = "[:\\+()]";
			while ((line = br.readLine()) != null) {
				String[] dominoString = line.split(delimiters); // {id, leftTerrain, rightTerrain, crowns}
				int dominoId = Integer.decode(dominoString[0]);
				TerrainType leftTerrain = getTerrainType(dominoString[1]);
				TerrainType rightTerrain = getTerrainType(dominoString[2]);
				int numCrown = 0;
				if (dominoString.length > 3) {
					numCrown = Integer.decode(dominoString[3]);
				}
				new Domino(dominoId, leftTerrain, rightTerrain, numCrown, game);
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw new java.lang.IllegalArgumentException(
					"Error occured while trying to read alldominoes.dat: " + e.getMessage());
		}
	}

	private static Domino getdominoByID(int id) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		for (Domino domino : game.getAllDominos()) {
			if (domino.getId() == id) {
				return domino;
			}
		}
		throw new java.lang.IllegalArgumentException("Domino with ID " + id + " not found.");
	}

	private TerrainType getTerrainType(String terrain) {
		switch (terrain) {
		case "W":
			return TerrainType.WheatField;
		case "F":
			return TerrainType.Forest;
		case "M":
			return TerrainType.Mountain;
		case "G":
			return TerrainType.Grass;
		case "S":
			return TerrainType.Swamp;
		case "L":
			return TerrainType.Lake;
		default:
			throw new java.lang.IllegalArgumentException("Invalid terrain type: " + terrain);
		}
	}

	}