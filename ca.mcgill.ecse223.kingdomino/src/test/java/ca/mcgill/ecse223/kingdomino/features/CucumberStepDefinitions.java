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
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CucumberStepDefinitions {

	/**
	 * 
	 * 		DiscardDomino Feature
	 *  
	 *  @see DiscardDomino.feature
	 *  @author Massimo Vadacchino
	 *  
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
		
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		Domino dominoToAddtoDraft = getdominoByID(domID);
	
		Draft currentDraft = new Draft(DraftStatus.Sorted, game);
		currentDraft.addIdSortedDomino(dominoToAddtoDraft);
		game.addAllDraft(currentDraft);
		game.setCurrentDraft(currentDraft);
		
		dominoToAddtoDraft.setStatus(DominoStatus.InCurrentDraft);
			
	}													

	@Given("the current player has selected domino {int}")
	public void the_current_player_has_selected_domino(Integer domID) { 
		
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		
		Player playerToSelectDomino = game.getPlayer(0);
		Domino dominoToSelect = getdominoByID(domID);
		Draft currentDraft = game.getCurrentDraft();
	
		DominoSelection dSelection = new DominoSelection(playerToSelectDomino, dominoToSelect, currentDraft);
		
		playerToSelectDomino.setDominoSelection(dSelection);
		currentDraft.addSelection(dSelection);
		
	}									

	@Given("the player preplaces domino {int} at its initial position")
	public void the_player_preplaces_domino_at_its_initial_position(Integer domID) { 
		
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		
		Player playerToPrePlaceDomino = game.getPlayer(0);
		Domino dominoToPrePlace = getdominoByID(domID);
		Kingdom currentPlayerKingdom = playerToPrePlaceDomino.getKingdom();
		
		DominoInKingdom dInKingdom = new DominoInKingdom(0, 0, currentPlayerKingdom, dominoToPrePlace);
		dominoToPrePlace.setStatus(DominoStatus.ErroneouslyPreplaced);
	
		game.getCurrentDraft().removeIdSortedDomino(dominoToPrePlace);
		
	}

	@When("the player attempts to discard the selected domino")
	public void the_player_attempts_to_discard_the_selected_domino() {
		
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		Player playerToDiscardDomino = game.getPlayer(0);
		
		try {
			
			KDController.discardDomino(playerToDiscardDomino);
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
	}

	@Then("domino {int} shall have status {string}")
	public void domino_shall_have_status(Integer domID, String domStatus) {
		DominoStatus actualStatus = getdominoByID(domID).getStatus();
		DominoStatus expectedStatus = getDominoStatus(domStatus);
		assertEquals(expectedStatus, actualStatus);
	}

	
	/**
	 * 
	 *     MoveCurrentDomino Feature 
	 * 
	 * 	@see MoveCurrentDomino.feature
	 *  @author Massimo Vadacchino
	 * 
	 */
	

	
	/**
	 * 
	 *  	PlaceDomino Feature 
	 *  
	 *  @see PlaceDomino.feature
	 *  @author Massimo Vadacchino
	 * 
	 */
	
	
	
	/**
	 * 
	 * 		RotateCurrentDomino Feature
	 * 
	 * 	@see RotateCurrentDomino.feature
	 * 	@author Massimo Vadacchino
	 * 
	 */
	
	@Given("the game is initialized for move current domino")
	public void the_game_is_initialized_for_move_current_domino() {
	   
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

	@Given("it is {string}'s turn")
	public void it_is_s_turn(String string) {

		Player player = getPlayerByColor(string);
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		
		game.setNextPlayer(player);
		
	}

	@Given("{string}'s kingdown has following dominoes:")
	public void s_kingdown_has_following_dominoes(String string, io.cucumber.datatable.DataTable dataTable) {
		
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		List<Map<String, String>> valueMaps = dataTable.asMaps();
		
		for (Map<String, String> map : valueMaps) {
			
			// Get values from cucumber table
			Integer id = Integer.decode(map.get("id"));
			DirectionKind dir = getDirection(map.get("dir"));
			Integer posx = Integer.decode(map.get("posx"));
			Integer posy = Integer.decode(map.get("posy"));

			// Add the domino to a player's kingdom
			Domino dominoToPlace = getdominoByID(id);
			Kingdom kingdom = getPlayerByColor(string).getKingdom();
			DominoInKingdom domInKingdom = new DominoInKingdom(posx, posy, kingdom, dominoToPlace);
			domInKingdom.setDirection(dir);
			dominoToPlace.setStatus(DominoStatus.PlacedInKingdom);
			
		}
		
	}

	@Given("{string} has selected domino {int}")
	public void has_selected_domino(String string, Integer int1) {

		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
	
		Domino dominoToSelect = getdominoByID(int1);
		Player playerToSelectDomino = getPlayerByColor(string);
		
		Draft currentDraft = new Draft(DraftStatus.Sorted, game);
		currentDraft.addIdSortedDomino(dominoToSelect);
		game.addAllDraft(currentDraft);
		game.setCurrentDraft(currentDraft);
		
		dominoToSelect.setStatus(DominoStatus.InCurrentDraft);
		
	
		DominoSelection dSelection = new DominoSelection(playerToSelectDomino, dominoToSelect, currentDraft);
		
		playerToSelectDomino.setDominoSelection(dSelection);
		currentDraft.addSelection(dSelection);
		
	}

	@Given("domino {int} is tentatively placed at position {int}:{int} with direction {string}")
	public void domino_is_tentatively_placed_at_position_with_direction(Integer int1, Integer int2, Integer int3, String string) {
	   
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		
		Domino dominoToBeTentativelyPlaced = getdominoByID(int1);
		Kingdom playerKingdom = game.getNextPlayer().getKingdom();
		
		DominoInKingdom dInKingdom = new DominoInKingdom(int2, int3, playerKingdom, dominoToBeTentativelyPlaced);
		dInKingdom.setDirection(getDirection(string));
		
		game.getCurrentDraft().removeIdSortedDomino(dominoToBeTentativelyPlaced);
		dominoToBeTentativelyPlaced.setStatus(DominoStatus.ErroneouslyPreplaced);
		
	}

	@When("{string} requests to rotate the domino with {string}")
	public void requests_to_rotate_the_domino_with(String string, String string2) {
	    
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		Player playerToRotateDomino = getPlayerByColor(string);
		DominoInKingdom newlyPrePlacedDomino = (DominoInKingdom) playerToRotateDomino.getKingdom().getTerritory(playerToRotateDomino.getKingdom().getTerritories().size()-1);
		
		try {
			
			KDController.rotateCurrentDomino(playerToRotateDomino, newlyPrePlacedDomino, string2);
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
	}

	@Then("the domino {int} is still tentatively placed at {int}:{int} but with new direction {string}")
	public void the_domino_is_still_tentatively_placed_at_but_with_new_direction(Integer int1, Integer int2, Integer int3, String string) {
	  
		Domino domino = getdominoByID(int1);
		DominoInKingdom dInKingdom = (DominoInKingdom) domino.getDominoSelection().getPlayer().getKingdom().getTerritory(domino.getDominoSelection().getPlayer().getKingdom().getTerritories().size()-1);
		
		DirectionKind domDirection = dInKingdom.getDirection();
		Integer xPos = dInKingdom.getX();
		Integer yPos = dInKingdom.getY();
		
		assertEquals(domDirection, getDirection(string));
		assertEquals(xPos, int2);
		assertEquals(yPos, int3);
		
	}

	@Then("the domino {int} should have status {string}")
	public void the_domino_should_have_status(Integer int1, String string) {
	   
		Domino domino = getdominoByID(int1);
		DominoStatus domStatus = domino.getStatus();
		
		assertEquals(domStatus, getDominoStatusCapital(string));
		
	}

	@Given("{string}'s kingdom has following dominoes:")
	public void s_kingdom_has_following_dominoes(String string, io.cucumber.datatable.DataTable dataTable) {
	
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		List<Map<String, String>> valueMaps = dataTable.asMaps();
		
		for (Map<String, String> map : valueMaps) {
			
			// Get values from cucumber table
			Integer id = Integer.decode(map.get("id"));
			DirectionKind dir = getDirection(map.get("dir"));
			Integer posx = Integer.decode(map.get("posx"));
			Integer posy = Integer.decode(map.get("posy"));

			// Add the domino to a player's kingdom
			Domino dominoToPlace = getdominoByID(id);
			Kingdom kingdom = getPlayerByColor(string).getKingdom();
			DominoInKingdom domInKingdom = new DominoInKingdom(posx, posy, kingdom, dominoToPlace);
			domInKingdom.setDirection(dir);
			dominoToPlace.setStatus(DominoStatus.PlacedInKingdom);
			
		}
		
	}

	@Given("domino {int} has status {string}")
	public void domino_has_status(Integer int1, String string) {
	 
		Domino domino = getdominoByID(int1);
		domino.setStatus(getDominoStatusCapital(string));
		
	}

	@Then("domino {int} is tentatively placed at the same position {int}:{int} with the same direction {string}")
	public void domino_is_tentatively_placed_at_the_same_position_with_the_same_direction(Integer int1, Integer int2, Integer int3, String string) {
	   
		Domino domino = getdominoByID(int1);
		DominoInKingdom dInKingdom = (DominoInKingdom) domino.getDominoSelection().getPlayer().getKingdom().getTerritory(domino.getDominoSelection().getPlayer().getKingdom().getTerritories().size()-1);
		
		DirectionKind domDirection = dInKingdom.getDirection();
		Integer xPos = dInKingdom.getX();
		Integer yPos = dInKingdom.getY();
		
		assertEquals(domDirection, getDirection(string));
		assertEquals(xPos, int2);
		assertEquals(yPos, int3);
		
	}

	@Then("domino {int} should still have status {string}")
	public void domino_should_still_have_status(Integer int1, String string) {
	   
		Domino domino = getdominoByID(int1);
		DominoStatus domStatus = domino.getStatus();
		
		assertEquals(domStatus, getDominoStatusCapital(string));
		
	}
	
	///////////////////////////////////////
	/// -----Private Helper Methods---- ///
	///////////////////////////////////////

	private void addDefaultUsersAndPlayers(Game game) {
		String[] users = { "User1", "User2", "User3", "User4" };
		for (int i = 0; i < users.length; i++) {
			game.getKingdomino().addUser(users[i]);
			Player player = new Player(game);
			player.setColor(PlayerColor.values()[i]);
			Kingdom kingdom = new Kingdom(player);
			new Castle(0, 0, kingdom, player);
		}
	}
	
	private Player getPlayerByColor(String color) {
		
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		
		for(Player player : game.getPlayers()) {
			
			if(player.getColor().equals(getColor(color))) return player;
			
		}
		
		throw new java.lang.IllegalArgumentException("Player with color" + color + " not found.");
		
	}
	
	private PlayerColor getColor(String color) {
		
		if(color.equalsIgnoreCase("Blue")) return PlayerColor.Blue;
		if(color.equalsIgnoreCase("Green")) return PlayerColor.Green;
		if(color.equalsIgnoreCase("Yellow")) return PlayerColor.Yellow;
		if(color.equalsIgnoreCase("Pink")) return PlayerColor.Pink;
		
		throw new java.lang.IllegalArgumentException("No player of this color exists");
		
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

	private Domino getdominoByID(int id) {
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
	
	private DominoStatus getDominoStatusCapital(String status) {
		switch (status) {
		case "InPile":
			return DominoStatus.InPile;
		case "Excluded":
			return DominoStatus.Excluded;
		case "InCurrentDraft":
			return DominoStatus.InCurrentDraft;
		case "InNextDraft":
			return DominoStatus.InNextDraft;
		case "ErroneouslyPreplaced":
			return DominoStatus.ErroneouslyPreplaced;
		case "CorrectlyPreplaced":
			return DominoStatus.CorrectlyPreplaced;
		case "PlacedInKingdom":
			return DominoStatus.PlacedInKingdom;
		case "Discarded":
			return DominoStatus.Discarded;
		default:
			throw new java.lang.IllegalArgumentException("Invalid domino status: " + status);
		}
	}
}
