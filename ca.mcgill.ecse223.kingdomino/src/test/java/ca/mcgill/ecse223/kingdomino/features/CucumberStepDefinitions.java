package ca.mcgill.ecse223.kingdomino.features;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
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
	@When("I initiate loading a saved game from {string}")
	public void i_initiate_loading_a_saved_game_from(String string) throws InvalidInputException {
	    KDController.loadGame(string);
	}
	@When("each tile placement is valid")
	public void each_tile_placement_is_valid() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}
	@When("the game result is not yet final")
	public void the_game_result_is_not_yet_final() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}
	@Then("it shall be player {int}'s turn")
	public void it_shall_be_player_s_turn(Integer int1) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}
	@Then("each of the players should have the corresponding tiles on their grid:")
	public void each_of_the_players_should_have_the_corresponding_tiles_on_their_grid(io.cucumber.datatable.DataTable dataTable) {
	    // Write code here that turns the phrase above into concrete actions
	    // For automatic transformation, change DataTable to one of
	    // E, List<E>, List<List<E>>, List<Map<K,V>>, Map<K,V> or
	    // Map<K, List<V>>. E,K,V must be a String, Integer, Float,
	    // Double, Byte, Short, Long, BigInteger or BigDecimal.
	    //
	    // For other transformations you can register a DataTableType.
	    throw new cucumber.api.PendingException();
	}
	@Then("each of the players should have claimed the corresponding tiles:")
	public void each_of_the_players_should_have_claimed_the_corresponding_tiles(io.cucumber.datatable.DataTable dataTable) {
	    // Write code here that turns the phrase above into concrete actions
	    // For automatic transformation, change DataTable to one of
	    // E, List<E>, List<List<E>>, List<Map<K,V>>, Map<K,V> or
	    // Map<K, List<V>>. E,K,V must be a String, Integer, Float,
	    // Double, Byte, Short, Long, BigInteger or BigDecimal.
	    //
	    // For other transformations you can register a DataTableType.
	    throw new cucumber.api.PendingException();
	}
	@Then("tiles {string} shall be unclaimed on the board")
	public void tiles_shall_be_unclaimed_on_the_board(String string) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}
	@Then("the game shall become ready to start")
	public void the_game_shall_become_ready_to_start() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}
	@Then("the game shall notify the user that the game file is invalid")
	public void the_game_shall_notify_the_user_that_the_game_file_is_invalid() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	/**
	 * @author Anthony Harissi Dagher
	 * Test for saveGame
	 * @see saveGame.feature
	 */
	@Given("the game is initialized for save game")
	public void the_game_is_initialized_for_save_game() {
		KDController.initializeGame();
	}
	@Given("the game is still in progress")
	public void the_game_is_still_in_progress() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}
	@Given("no file named {string} exists in the filesystem")
	public void no_file_named_exists_in_the_filesystem(String string) {
		String directory = "./src/test/resources/savedGames/"+string;
		File fileSearch = new File(directory);
		fileSearch.exists() = false;
	}
	@When("the user initiates saving the game to a file named {string}")
	public void the_user_initiates_saving_the_game_to_a_file_named(String string) throws InvalidInputException {
	    KDController.saveGame(string);
	}
	@Then("a file named {string} shall be created in the filesystem")
	public void a_file_named_shall_be_created_in_the_filesystem(String string) {
		Kingdomino kingdomino = KingdominoApplication.getKingdomino();
		KDPersistence.save(kingdomino);
	}
	@Given("the file named {string} exists in the filesystem")
	public void the_file_named_exists_in_the_filesystem(String string) {
		String directory = "./src/test/resources/savedGames/"+string;
		File fileSearch = new File(directory);
		fileSearch.exists();
		ovewriteSave(string);
	}
	@When("the user agrees to overwrite the existing file")
	public void the_user_agrees_to_overwrite_the_existing_file() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}
	@Then("the file named {string} shall be updated in the filesystem")
	public void the_file_named_shall_be_updated_in_the_filesystem(String string) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	/**
	 * @author Anthony Harissi Dagher
	 * Test for setGameOptions
	 * @see setGameOptions.feature
	 */
	@Given("the game is initialized for set game options")
	public void the_game_is_initialized_for_set_game_options() {
		KDController.initializeGame();
	}
	@When("set game options is initiated")
	public void set_game_options_is_initiated() throws InvalidInputException {
		int numPlayers=4;
		List<BonusOption> selectedBonusOptions = null;
		try {	
			KDController.setGameOptions(numPlayers, selectedBonusOptions);
		}
		catch(InvalidInputException i) {
			throw new InvalidInputException(i.getMessage());
		}
	}
	@When("the number of players is set to {int}")
	public void the_number_of_players_is_set_to(Integer int1) throws InvalidInputException {
		List<BonusOption> selectedBonusOptions = null;
		KDController.setGameOptions(int1, selectedBonusOptions);
	}
	@When("Harmony {string} selected as bonus option")
	public void harmony_selected_as_bonus_option(String string) throws InvalidInputException {
		Kingdomino kingdomino = KingdominoApplication.getKingdomino();
		Game game = new Game(48, kingdomino);
		BonusOption HarmonyOption = new BonusOption("Harmony", kingdomino);
		game.addSelectedBonusOption(HarmonyOption);
		List<BonusOption> selectedBonusOptions = game.getSelectedBonusOptions();
		KDController.setGameOptions(4, selectedBonusOptions);
	}
	@When("Middle Kingdom {string} selected as bonus option")
	public void middle_Kingdom_selected_as_bonus_option(String string) throws InvalidInputException {
		Kingdomino kingdomino = KingdominoApplication.getKingdomino();
		Game game = new Game(48, kingdomino);
		BonusOption MKOption = new BonusOption("Middle Kingdom", kingdomino);
		game.addSelectedBonusOption(MKOption);
		List<BonusOption> selectedBonusOptions = game.getSelectedBonusOptions();
		KDController.setGameOptions(4, selectedBonusOptions);
	}
	@Then("the number of players shall be {int}")
	public void the_number_of_players_shall_be(Integer int1) {
		Kingdomino kingdomino = KingdominoApplication.getKingdomino();
		if(int1 == 2) {
			Game game = new Game(24, kingdomino);
			game.setNumberOfPlayers(2);
			game.getNumberOfPlayers();
		}
		if(int1 == 3) {
			Game game = new Game(36, kingdomino);
			game.setNumberOfPlayers(3);
			game.getNumberOfPlayers();

		}
		if(int1 == 4) {
			Game game = new Game(48, kingdomino);
			game.setNumberOfPlayers(4);
			game.getNumberOfPlayers();
		}
	}
	@Then("Harmony {string} an active bonus")
	public void harmony_an_active_bonus(String string) {
	
	}
	@Then("Middle Kingdom {string} an active bonus")
	public void middle_Kingdom_an_active_bonus(String string) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	
	/**
	 * @author Anthony Harissi Dagher
	 * Test for startANewGame
	 * @see startANewGame.feature
	 */
	@Given("the program is started and ready for starting a new game")
	public void the_program_is_started_and_ready_for_starting_a_new_game() {
		Kingdomino program = KingdominoApplication.getKingdomino();
	}
	@Given("there are four selected players")
	public void there_are_four_selected_players() throws InvalidInputException {
		Kingdomino program = KingdominoApplication.getKingdomino();
		Game game = program.getCurrentGame();
		game.setNumberOfPlayers(4);
	}
	@Given("bonus options Harmony and MiddleKingdom are selected")
	public void bonus_options_Harmony_and_MiddleKingdom_are_selected() throws InvalidInputException {
		Kingdomino kingdomino = KingdominoApplication.getKingdomino();
		Game game = kingdomino.getCurrentGame();
		game.setNumberOfPlayers(4);
		BonusOption MKOption = new BonusOption("Middle Kingdom", kingdomino);
		BonusOption HarmonyOption = new BonusOption("Harmony", kingdomino);
		game.addSelectedBonusOption(MKOption);
		game.addSelectedBonusOption(HarmonyOption);
		KDController.setGameOptions(game.getNumberOfPlayers(), game.getSelectedBonusOptions());
	}
	@When("starting a new game is initiated")
	public void starting_a_new_game_is_initiated() {
	    KDController.startANewGame();
	}
	@When("reveal first draft is initiated")
	public void reveal_first_draft_is_initiated() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}
	@Then("all kingdoms shall be initialized with a single castle")
	public void all_kingdoms_shall_be_initialized_with_a_single_castle() {
		Kingdomino kingdomino = KingdominoApplication.getKingdomino();
		Game game = kingdomino.getCurrentGame();
		for (int i = 0; i < game.getNumberOfPlayers(); i++) {
			Player player = game.getPlayer(i);
			Kingdom kingdom = new Kingdom(player);
			Castle castle = null;
			kingdom.addTerritory(castle);
		}
	}
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
	@Then("the first draft of dominoes is revealed")
	public void the_first_draft_of_dominoes_is_revealed() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}
	@Then("all the dominoes form the first draft are facing up")
	public void all_the_dominoes_form_the_first_draft_are_facing_up() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}
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
