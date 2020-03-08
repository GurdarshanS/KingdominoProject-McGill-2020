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
	 
	 //ChooseNextDomino
	 
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
	  		
	  
	
	  
	 
///////////////////////////////////////
/// -----Keon's Helper Method  ---- ///
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
	 
	 
	/*
	 * Note that these step definitions and helper methods just serve as a guide to help
	 * you get started. You may change the code if required.
	 */

	@Given("the game is initialized for discard domino")
	public void the_game_is_initialized_for_discard_domino() {
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

	@Given("the player's kingdom has the following dominoes:")
	public void the_player_s_kingdom_has_the_following_dominoes(io.cucumber.datatable.DataTable dataTable) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		List<Map<String, String>> valueMaps = dataTable.asMaps();
		for (Map<String, String> map : valueMaps) {
			// Get values from cucumber table
			Integer id = Integer.decode(map.get("id"));
			DirectionKind dir = getDirection(map.get("dominodir"));
			Integer posx = Integer.decode(map.get("posx"));
			Integer posy = Integer.decode(map.get("posy"));

			// Add the domino to a player's kingdom
			Domino dominoToPlace = getdominoByID(id);
			 Kingdom kingdom = game.getPlayer(0).getKingdom();
			DominoInKingdom domInKingdom = new DominoInKingdom(posx, posy, kingdom, dominoToPlace);
			domInKingdom.setDirection(dir);
			dominoToPlace.setStatus(DominoStatus.PlacedInKingdom);
		}
	}

	@Given("domino {int} is in the current draft")
	public void domino_is_in_the_current_draft(Integer domID) {
		// TODO: Write code here that turns the phrase above into concrete actions
	}

	@Given("the current player has selected domino {int}")
	public void the_current_player_has_selected_domino(Integer domID) {
		// TODO: Write code here that turns the phrase above into concrete actions
	}

	@Given("the player preplaces domino {int} at its initial position")
	public void the_player_preplaces_domino_at_its_initial_position(Integer domID) {
		// TODO: Write code here that turns the phrase above into concrete actions
	}

	@When("the player attempts to discard the selected domino")
	public void the_player_attempts_to_discard_the_selected_domino() {
		// TODO: Call your Controller method here.
		throw new cucumber.api.PendingException(); // Remove this line once your controller method is implemented
	}

	@Then("domino {int} shall have status {string}")
	public void domino_shall_have_status(Integer domID, String domStatus) {
		DominoStatus actualStatus = getdominoByID(domID).getStatus();
		DominoStatus expectedStatus = getDominoStatus(domStatus);
		assertEquals(expectedStatus, actualStatus);
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

	private DirectionKind getDirection(String dir) {
		switch (dir) {
		case "up":
			return DirectionKind.Up;
		case "down":
			return DirectionKind.Down;
		case "left":
			return DirectionKind.Left;
		case "right":
			return DirectionKind.Right;
		default:
			throw new java.lang.IllegalArgumentException("Invalid direction: " + dir);
		}
	}

	private DominoStatus getDominoStatus(String status) {
		switch (status) {
		case "inPile":
			return DominoStatus.InPile;
		case "excluded":
			return DominoStatus.Excluded;
		case "inCurrentDraft":
			return DominoStatus.InCurrentDraft;
		case "inNextDraft":
			return DominoStatus.InNextDraft;
		case "erroneouslyPreplaced":
			return DominoStatus.ErroneouslyPreplaced;
		case "correctlyPreplaced":
			return DominoStatus.CorrectlyPreplaced;
		case "placedInKingdom":
			return DominoStatus.PlacedInKingdom;
		case "discarded":
			return DominoStatus.Discarded;
		default:
			throw new java.lang.IllegalArgumentException("Invalid domino status: " + status);
		}
	}
}