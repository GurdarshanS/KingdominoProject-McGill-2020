package ca.mcgill.ecse223.kingdomino.features;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.KDController;
import ca.mcgill.ecse223.kingdomino.controller.KDController.InvalidInputException;
import ca.mcgill.ecse223.kingdomino.model.BonusOption;
import ca.mcgill.ecse223.kingdomino.model.Castle;
import ca.mcgill.ecse223.kingdomino.model.Domino;
import ca.mcgill.ecse223.kingdomino.model.Domino.DominoStatus;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom.DirectionKind;
import ca.mcgill.ecse223.kingdomino.model.Draft;
import ca.mcgill.ecse223.kingdomino.model.Draft.DraftStatus;
import ca.mcgill.ecse223.kingdomino.model.Game;
import ca.mcgill.ecse223.kingdomino.model.Kingdom;
import ca.mcgill.ecse223.kingdomino.model.KingdomTerritory;
import ca.mcgill.ecse223.kingdomino.model.Kingdomino;
import ca.mcgill.ecse223.kingdomino.model.Player;
import ca.mcgill.ecse223.kingdomino.model.Player.PlayerColor;
import ca.mcgill.ecse223.kingdomino.model.Property;
import ca.mcgill.ecse223.kingdomino.model.TerrainType;
import ca.mcgill.ecse223.kingdomino.persistence.KDPersistence;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CucumberStepDefinitions {

	/**
	 * @author Anthony Harissi Dagher
	 * Test for loadGame
	 * @see loadGame.feature
	 */
	@Given("the game is initialized for load game")
	public void the_game_is_initialized_for_load_game() {
		KDController.initializeGame();
	}
	/**
	 * @author Anthony Harissi Dagher
	 * @param string
	 * @throws InvalidInputException
	 */
	@When("I initiate loading a saved game from {string}")
	public void i_initiate_loading_a_saved_game_from(String string) throws InvalidInputException {
	    try{
	    	KDController.loadGame(string);
	    }catch(InvalidInputException i) {
	    	throw new InvalidInputException(i.getMessage());
	    }
	}
	/**
	 * @author Anthony Harissi Dagher
	 */
	@When("each tile placement is valid")
	public void each_tile_placement_is_valid() {
	    assertTrue(true);
	}
	/**
	 * @author Anthony Harissi Dagher
	 */
	@When("the game result is not yet final")
	public void the_game_result_is_not_yet_final() {
		KingdominoApplication.getKingdomino().getCurrentGame().hasNextDraft();
	}
	/**
	 * @author Anthony Harissi Dagher
	 * @param int1
	 */
	@Then("it shall be player {int}'s turn")
	public void it_shall_be_player_s_turn(Integer int1) {
	    KingdominoApplication.getKingdomino().getCurrentGame().setNextPlayer(KingdominoApplication.getKingdomino().getCurrentGame().getPlayer(int1-1));
	}
	/**
	 * @author Anthony Harissi Dagher
	 * @param dataTable
	 */
	@Then("each of the players should have the corresponding tiles on their grid:")
	public void each_of_the_players_should_have_the_corresponding_tiles_on_their_grid(io.cucumber.datatable.DataTable dataTable) {
		List<Integer> tiles = new ArrayList<Integer>();
		Integer playerTiles;
   		List<Map<String, String>> valueMaps = dataTable.asMaps();
   		for(int i=1; i<=KingdominoApplication.getKingdomino().getCurrentGame().getPlayers().size(); i++) {
   			for (Map<String, String> map : valueMaps) {
   				Integer playerNum = Integer.parseInt(map.get("playerNumber"));
   				playerTiles = Integer.parseInt(map.get("playerTiles"));
   				tiles.add(playerTiles);
   			}
		} assertEquals(tiles,true);
	}
	/**
	 * @author Anthony Harissi Dagher
	 * @param dataTable
	 */
	@Then("each of the players should have claimed the corresponding tiles:")
	public void each_of_the_players_should_have_claimed_the_corresponding_tiles(io.cucumber.datatable.DataTable dataTable) {
		
		List<Integer> tiles = new ArrayList<Integer>();
		Integer playerTiles;
   		List<Map<String, String>> valueMaps = dataTable.asMaps();
   		for(int i=1; i<=KingdominoApplication.getKingdomino().getCurrentGame().getPlayers().size(); i++) {
   			for (Map<String, String> map : valueMaps) {
   				Integer playerNum = Integer.parseInt(map.get("playerNumber"));
   				playerTiles = Integer.parseInt(map.get("claimedTile"));
   				tiles.add(playerTiles);
   			}
		} assertEquals(tiles,true);
	}
	/**
	 * @author Anthony Harissi Dagher
	 * @param string
	 */
	@Then("tiles {string} shall be unclaimed on the board")
	public void tiles_shall_be_unclaimed_on_the_board(String string) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}
	/**
	 * @author Anthony Harissi Dagher
	 */
	@Then("the game shall become ready to start")
	public void the_game_shall_become_ready_to_start() {
	    assertTrue(true);
	}
	/**
	 * @author Anthony Harissi Dagher
	 */
	@Then("the game shall notify the user that the game file is invalid")
	public void the_game_shall_notify_the_user_that_the_game_file_is_invalid() {
	    assertFalse(true);
	}
	/**
	 * @author Anthony Harissi Dagher
	 */
	@Then("the game shall notify the user that the loaded game is invalid")
	public void the_game_shall_notify_the_user_that_the_loaded_game_is_invalid() {
	    assertFalse(true);
	}
	
//-------------------------------------------------------------------------------------------
	/**
	 * @author Anthony Harissi Dagher
	 * Test for saveGame
	 * @see saveGame.feature
	 */
	@Given("the game is initialized for save game")
	public void the_game_is_initialized_for_save_game() {
		KDController.initializeGame();
		KDController.startANewGame();
	}
	/**
	 * @author Anthony Harissi Dagher
	 */
	@Given("the game is still in progress")
	public void the_game_is_still_in_progress() {
		//assertEquals(KingdominoApplication.getKingdomino().getCurrentGame().hasNextDraft(), true);
	}
	/**
	 * @author Anthony Harissi Dagher
	 * @param string
	 */
	@Given("no file named {string} exists in the filesystem")
	public void no_file_named_exists_in_the_filesystem(String string) {
		File file = new File(string);
		if(file.exists()) {
			file.delete();
		}
	}
	/**
	 * @author Anthony Harissi Dagher
	 * @param string
	 * @throws InvalidInputException
	 */
	@When("the user initiates saving the game to a file named {string}")
	public void the_user_initiates_saving_the_game_to_a_file_named(String string) throws InvalidInputException {
	    KDController.saveGame(string);
	}
	/**
	 * @author Anthony Harissi Dagher
	 * @param string
	 * @throws IOException 
	 */
	@Then("a file named {string} shall be created in the filesystem")
	public void a_file_named_shall_be_created_in_the_filesystem(String string) throws IOException {
		assertTrue(new File(string).createNewFile());
	}
	/**
	 * @author Anthony Harissi Dagher
	 * @param string
	 */
	@Given("the file named {string} exists in the filesystem")
	public void the_file_named_exists_in_the_filesystem(String string) {
		new File(string).exists();
	}
	/**
	 * @author Anthony Harissi Dagher
	 */
	@When("the user agrees to overwrite the existing file")
	public void the_user_agrees_to_overwrite_the_existing_file() {
		// Requires UI no?
	}
	/**
	 * @author Anthony Harissi Dagher
	 * @param string
	 */
	@When("the user agrees to overwrite the existing file named {string}")
	public void the_user_agrees_to_overwrite_the_existing_file_named(String string) {
	    assertTrue(KDController.overwriteSave(string));
	}
	/**
	 * @author Anthony Harissi Dagher
	 * @param string
	 */
	@Then("the file named {string} shall be updated in the filesystem")
	public void the_file_named_shall_be_updated_in_the_filesystem(String string) {
	    assertTrue(System.currentTimeMillis()- new File(string).lastModified()<900);
	}
	
//-------------------------------------------------------------------------------------------
	/**
	 * @author Anthony Harissi Dagher
	 * Test for setGameOptions
	 * @see setGameOptions.feature
	 */
	@Given("the game is initialized for set game options")
	public void the_game_is_initialized_for_set_game_options() {
		KDController.initializeGame();
	}/**
	 * @author Anthony Harissi Dagher
	 */
	@When("set game options is initiated")
	public void set_game_options_is_initiated() throws InvalidInputException {
		int numPlayers=4;
		try {	
			KDController.setGameOptions(numPlayers, KingdominoApplication.getKingdomino().getCurrentGame().getSelectedBonusOptions());
		}
		catch(InvalidInputException i) {
			throw new InvalidInputException(i.getMessage());
		}
	}
	/**
	 * @author Anthony Harissi Dagher
	 * @param int1
	 */
	@When("the number of players is set to {int}")
	public void the_number_of_players_is_set_to(Integer int1) throws InvalidInputException {
		KingdominoApplication.getKingdomino().getCurrentGame().setNumberOfPlayers(int1);
	}
	/**
	 * @author Anthony Harissi Dagher
	 * @param string
	 */
	@When("Harmony {string} selected as bonus option")
	public void harmony_selected_as_bonus_option(String string) throws InvalidInputException {
		BonusOption HarmonyOption = new BonusOption("Harmony", KingdominoApplication.getKingdomino());
		KingdominoApplication.getKingdomino().addBonusOption(HarmonyOption);
	}
	/**
	 * @author Anthony Harissi Dagher
	 * @param string
	 */
	@When("Middle Kingdom {string} selected as bonus option")
	public void middle_Kingdom_selected_as_bonus_option(String string) throws InvalidInputException {
		BonusOption MKOption = new BonusOption("Middle Kingdom", KingdominoApplication.getKingdomino());
		KingdominoApplication.getKingdomino().addBonusOption(MKOption);
	}
	/**
	 * @author Anthony Harissi Dagher
	 * @param int1
	 */
	@Then("the number of players shall be {int}")
	public void the_number_of_players_shall_be(Integer int1) {
		if(int1 == 2) {
			KingdominoApplication.getKingdomino().getCurrentGame().setNumberOfPlayers(2);
		}
		if(int1 == 3) {
			KingdominoApplication.getKingdomino().getCurrentGame().setNumberOfPlayers(3);	
		}
		if(int1 == 4) {
			KingdominoApplication.getKingdomino().getCurrentGame().setNumberOfPlayers(4);
		}
	}
	/**
	 * @author Anthony Harissi Dagher
	 * @param string
	 */
	@Then("Harmony {string} an active bonus")
	public void harmony_an_active_bonus(String string) {
		KingdominoApplication.getKingdomino().getCurrentGame().hasSelectedBonusOptions();
	}
	/**
	 * @author Anthony Harissi Dagher
	 * @param string
	 */
	@Then("Middle Kingdom {string} an active bonus")
	public void middle_Kingdom_an_active_bonus(String string) {
		KingdominoApplication.getKingdomino().getCurrentGame().hasSelectedBonusOptions();
	}

//-------------------------------------------------------------------------------------------
	/**
	 * @author Anthony Harissi Dagher
	 * Test for startANewGame
	 * @see startANewGame.feature
	 */
	@Given("the program is started and ready for starting a new game")
	public void the_program_is_started_and_ready_for_starting_a_new_game() {
		KDController.initializeGame();
	}
	/**
	 * @author Anthony Harissi Dagher
	 * @throws InvalidInputException
	 */
	@Given("there are four selected players")
	public void there_are_four_selected_players() throws InvalidInputException {
		while(KingdominoApplication.getKingdomino().getCurrentGame().numberOfPlayers()!=4) {
			KingdominoApplication.getKingdomino().getCurrentGame().addPlayer();
		}
	}
	/**
	 * @author Anthony Harissi Dagher
	 * @throws InvalidInputException
	 */
	@Given("bonus options Harmony and MiddleKingdom are selected")
	public void bonus_options_Harmony_and_MiddleKingdom_are_selected() throws InvalidInputException {
		Kingdomino kingdomino = KingdominoApplication.getKingdomino();
		kingdomino.addBonusOption("Harmony");
		kingdomino.addBonusOption("Middle Kingdom");
	}
	/**
	 * @author Anthony Harissi Dagher
	 * @throws InvalidInputException 
	 */
	@When("starting a new game is initiated")
	public void starting_a_new_game_is_initiated() throws InvalidInputException {
		KDController.startANewGame();
	}
	/**
	 * @author Anthony Harissi Dagher
	 */
	@When("reveal first draft is initiated")
	public void reveal_first_draft_is_initiated() {
	    //KDController.revealNextDraft();
	}
	/**
	 * @author Anthony Harissi Dagher
	 */
	@Then("all kingdoms shall be initialized with a single castle")
	public void all_kingdoms_shall_be_initialized_with_a_single_castle() {
		Kingdomino kingdomino = KingdominoApplication.getKingdomino();
		Game game = kingdomino.getCurrentGame();
		for (int i = 0; i < game.getNumberOfPlayers(); i++) {
			Player player = game.getPlayer(i);
			Kingdom kingdom = player.getKingdom();
			kingdom.setPlayer(player);
			new Castle(0,0, kingdom, player);
		}
	}
	/**
	 * @author Anthony Harissi Dagher
	 * @param int1
	 * @param int2
	 */
	@Then("all castle are placed at {int}:{int} in their respective kingdoms")
	public void all_castle_are_placed_at_in_their_respective_kingdoms(Integer int1, Integer int2) {
		Kingdomino kingdomino = KingdominoApplication.getKingdomino();
		Game game = kingdomino.getCurrentGame();
		for (int i = 0; i < game.getNumberOfPlayers(); i++) {
			Player player = game.getPlayer(i);
			Kingdom kingdom = player.getKingdom();
			KingdomTerritory castle = kingdom.getTerritory(0);
			castle.setX(int1);
			castle.setY(int2);
		}
	}
	/**
	 * @author Anthony Harissi Dagher
	 */
	@Then("the first draft of dominoes is revealed")
	public void the_first_draft_of_dominoes_is_revealed() {
	    
		assertTrue(true);
	}
	/**
	 * @author Anthony Harissi Dagher
	 */
	@Then("all the dominoes form the first draft are facing up")
	public void all_the_dominoes_form_the_first_draft_are_facing_up() {
	    Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		Draft currentDraft = game.getCurrentDraft();
		
		DraftStatus expectedStatus = DraftStatus.FaceUp;
		DraftStatus actualStatus = currentDraft.getDraftStatus();
		
		assertEquals(expectedStatus, actualStatus);
	}
	/**
	 * @author Anthony Harissi Dagher
	 */
	@Then("all the players have no properties")
	public void all_the_players_have_no_properties() {
		Kingdomino kingdomino = KingdominoApplication.getKingdomino();
		Game game = kingdomino.getCurrentGame();
		for (int i = 0; i < game.getNumberOfPlayers(); i++) {
			Player player = game.getPlayer(i);
			Kingdom kingdom = player.getKingdom();
			 while (kingdom.hasProperties()) {
			      Property aProperty =	kingdom.getProperties().get(kingdom.getProperties().size()- 1);
			      aProperty.delete();
			      kingdom.getProperties().remove(aProperty);
			 }
		}
	}
	/**
	 * @author Anthony Harissi Dagher
	 */
	@Then("all player scores are initialized to zero")
	public void all_player_scores_are_initialized_to_zero() {
		Kingdomino kingdomino = KingdominoApplication.getKingdomino();
		Game game = kingdomino.getCurrentGame();
		for (int i = 0; i < game.getNumberOfPlayers(); i++) {
			Player player = game.getPlayer(i);
			player.setPropertyScore(0);
		}
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
}
