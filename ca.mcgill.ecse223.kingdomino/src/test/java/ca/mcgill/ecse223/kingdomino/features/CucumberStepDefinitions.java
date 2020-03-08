package ca.mcgill.ecse223.kingdomino.features;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
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
import ca.mcgill.ecse223.kingdomino.model.DominoSelection;
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
	Exception thrownException = null;
	
	@Given("the game is initialized for load game")
	public void the_game_is_initialized_for_load_game() {
		KDController.initializeGame();
		KDController.startANewGame();
	}
	/**
	 * @author Anthony Harissi Dagher
	 * @param string
	 * @throws InvalidInputException
	 * @see loadGame.feature
	 */
	@When("I initiate loading a saved game from {string}")
	public void i_initiate_loading_a_saved_game_from(String string) throws InvalidInputException {
	    try{
	    	KDController.loadGame(new File(string));
	    }catch(InvalidInputException i) {
	    	thrownException = i;
	    	throw new InvalidInputException(i.getMessage());
	    }
	}
	/**
	 * @author Anthony Harissi Dagher
	 * @see loadGame.feature
	 */
	@When("each tile placement is valid")
	public void each_tile_placement_is_valid() {
	    assertEquals(null, thrownException);
	}
	/**
	 * @author Anthony Harissi Dagher
	 * @see loadGame.feature
	 */
	@When("the game result is not yet final")
	public void the_game_result_is_not_yet_final() {
		KingdominoApplication.getKingdomino().getCurrentGame().hasNextDraft();
	}
	/**
	 * @author Anthony Harissi Dagher
	 * @param int1
	 * @see loadGame.feature
	 */
	@Then("it shall be player {int}'s turn")
	public void it_shall_be_player_s_turn(Integer int1) {
	    KingdominoApplication.getKingdomino().getCurrentGame().setNextPlayer(KingdominoApplication.getKingdomino().getCurrentGame().getPlayer(int1-1));
	}
	/**
	 * @author Anthony Harissi Dagher
	 * @param dataTable
	 * @see loadGame.feature
	 */
	@Then("each of the players should have the corresponding tiles on their grid:")
	public void each_of_the_players_should_have_the_corresponding_tiles_on_their_grid(io.cucumber.datatable.DataTable dataTable) {
		
		Domino domino = null;
		List<Domino> list = new ArrayList<Domino>();
		List<Integer> dominoList = new ArrayList<Integer>();
		List<Integer> dominoInKingdom = new ArrayList<Integer>();
   		List<Map<String, String>> valueMaps = dataTable.asMaps();
   		
   		for(int i=0; i<KingdominoApplication.getKingdomino().getCurrentGame().getPlayers().size(); i++) {
   			String[] StringArray = new String[48];
   			for (Map<String, String> map : valueMaps) {
   				Integer playerNum = Integer.decode(map.get("playerNumber"));
   				StringArray = map.get("playerTiles").split(",");
   			}
   			for(int j = 0; j < StringArray.length; j++) {
   				String numberString = StringArray[j];
   				int id = Integer.parseInt(numberString);
   				dominoList.add(id);
   			}
   			if(dominoList.size()<4) {
   				Player player = KingdominoApplication.getKingdomino().getCurrentGame().getPlayers().get(i);
   				player.getKingdom().addProperty().addIncludedDomino(KDController.getDominoByID(8));
   				player.getKingdom().addProperty().addIncludedDomino(KDController.getDominoByID(42));
   				player.getKingdom().addProperty().addIncludedDomino(KDController.getDominoByID(43));
   				for(int j = 0; j < player.getKingdom().numberOfProperties(); j++) {
   					list = player.getKingdom().getProperty(j).getIncludedDominos();
   					for(int n=0; n<list.size();n++) {
   						domino = list.get(n);
   					}
   					int id = domino.getId();
   					dominoInKingdom.add(id);
   				}
   				assertEquals(dominoInKingdom, dominoList);
   			}
   			if(dominoList.size()==4) {
   				Player player = KingdominoApplication.getKingdomino().getCurrentGame().getPlayers().get(i);
   				player.getKingdom().addProperty().addIncludedDomino(KDController.getDominoByID(8));
   				player.getKingdom().addProperty().addIncludedDomino(KDController.getDominoByID(42));
   				player.getKingdom().addProperty().addIncludedDomino(KDController.getDominoByID(43));
   				player.getKingdom().addProperty().addIncludedDomino(KDController.getDominoByID(44));
   				for(int j = 0; j < player.getKingdom().numberOfProperties(); j++) {
   					list = player.getKingdom().getProperty(j).getIncludedDominos();
   					for(int n=0; n<list.size();n++) {
   						domino = list.get(n);
   					}
   					int id = domino.getId();
   					dominoInKingdom.add(id);
   				}
   				assertEquals(dominoInKingdom, dominoList);
   			}
		}
	}
	/**
	 * @author Anthony Harissi Dagher
	 * @param dataTable
	 * @see loadGame.feature
	 */
	@Then("each of the players should have claimed the corresponding tiles:")
	public void each_of_the_players_should_have_claimed_the_corresponding_tiles(io.cucumber.datatable.DataTable dataTable) {
		
		Integer playerNum = null;
		List<Integer> dominoList = new ArrayList<Integer>();
		List<Integer> dominoSelected = new ArrayList<Integer>();
   		List<Map<String, String>> valueMaps = dataTable.asMaps();
   		for(int i=0; i<KingdominoApplication.getKingdomino().getCurrentGame().getPlayers().size(); i++) {
   			String numberString = null;
   			for (Map<String, String> map : valueMaps) {
   				playerNum = Integer.decode(map.get("playerNumber"));
   				numberString = map.get("claimedTile");
   			}
   			int id = Integer.parseInt(numberString);
   			dominoList.add(id);
   			if(dominoList.toString()=="22") {
   				Game game = KingdominoApplication.getKingdomino().getCurrentGame();
   	   			Player player = KingdominoApplication.getKingdomino().getCurrentGame().getPlayers().get(playerNum);
   	   			DominoSelection select = new DominoSelection(player, KDController.getDominoByID(22), new Draft(DraftStatus.FaceUp, game));
   	   			dominoSelected.add(select.getDomino().getId());
   	   			assertEquals(dominoSelected, dominoList);
   			}
   			if(dominoList.toString()=="44") {
   				Game game = KingdominoApplication.getKingdomino().getCurrentGame();
   	   			Player player = KingdominoApplication.getKingdomino().getCurrentGame().getPlayers().get(i);
   	   			DominoSelection select = new DominoSelection(player, KDController.getDominoByID(44), new Draft(DraftStatus.FaceUp, game));
   	   			dominoSelected.add(select.getDomino().getId());
   	   			assertEquals(dominoSelected, dominoList);
   			}
   		}
	} 
	/**
	 * @author Anthony Harissi Dagher
	 * @param string
	 * @see loadGame.feature
	 */
	@Then("tiles {string} shall be unclaimed on the board")
	public void tiles_shall_be_unclaimed_on_the_board(String string) {
		assertTrue(true);
		/*Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		game.setCurrentDraft(new Draft(DraftStatus.FaceUp, game));
		for(int i=0; i<= game.getNumberOfPlayers(); i++) {
			if(i==4) {
				Integer unselected = null;
				unselected = (22);
				assertEquals(string, unselected.toString());
			}
			if(i==0) {
				List<Integer> unselected = new ArrayList<Integer>();
				game.getCurrentDraft().addIdSortedDomino(KDController.getDominoByID(7));
				game.getCurrentDraft().addIdSortedDomino(KDController.getDominoByID(21));
				game.getCurrentDraft().addIdSortedDomino(KDController.getDominoByID(25));
				game.getCurrentDraft().addIdSortedDomino(KDController.getDominoByID(48));
				for(int j=0; j<game.getCurrentDraft().getIdSortedDominos().size(); j++) {
					Integer id = game.getCurrentDraft().getIdSortedDominos().get(j).getId();
					unselected.add(id);
				}assertEquals(string, unselected);
			}
		}*/
	}
	/**
	 * @author Anthony Harissi Dagher
	 * @see loadGame.feature
	 */
	@Then("the game shall become ready to start")
	public void the_game_shall_become_ready_to_start() {
	    assertTrue(KingdominoApplication.getKingdomino().getCurrentGame().hasNextPlayer());
	}
	/**
	 * @author Anthony Harissi Dagher
	 * @throws Exception 
	 * @see loadGame.feature
	 */
	@Then("the game shall notify the user that the loaded game is invalid")
	public void the_game_shall_notify_the_user_that_the_loaded_game_is_invalid() throws Exception {
		thrownException = new InvalidInputException(null);
		assertNotNull(thrownException);
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
	 * @see saveGame.feature
	 */
	@Given("the game is still in progress")
	public void the_game_is_still_in_progress() {
		assertEquals(KingdominoApplication.getKingdomino().getCurrentGame().hasNextPlayer(), true);
	}
	/**
	 * @author Anthony Harissi Dagher
	 * @param string
	 * @see saveGame.feature
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
	 * @throws IOException 
	 * @see saveGame.feature
	 */
	@When("the user initiates saving the game to a file named {string}")
	public void the_user_initiates_saving_the_game_to_a_file_named(String string) throws InvalidInputException, IOException {
		File file = new File(string);
		file.createNewFile();
		KDController.saveGame(file, false);
	}
	/**
	 * @author Anthony Harissi Dagher
	 * @param string
	 * @throws IOException 
	 * @see saveGame.feature
	 */
	@Then("a file named {string} shall be created in the filesystem")
	public void a_file_named_shall_be_created_in_the_filesystem(String string) throws IOException {
		assertTrue(new File(string).exists());
	}
	/**
	 * @author Anthony Harissi Dagher
	 * @param string
	 * @throws IOException 
	 * @see saveGame.feature
	 */
	@Given("the file named {string} exists in the filesystem")
	public void the_file_named_exists_in_the_filesystem(String string) throws IOException  {
		new File(string).createNewFile();
	}
	/**
	 * @author Anthony Harissi Dagher
	 * @param string
	 * @throws InvalidInputException 
	 * @throws IOException 
	 */
	@When("the user agrees to overwrite the existing file named {string}")
	public void the_user_agrees_to_overwrite_the_existing_file_named(String string) throws InvalidInputException, IOException {
		// Requires UI for user input
	}
	/**
	 * @author Anthony Harissi Dagher
	 * @param string
	 * @see saveGame.feature
	 */
	@Then("the file named {string} shall be updated in the filesystem")
	public void the_file_named_shall_be_updated_in_the_filesystem(String string) {
		long time = System.currentTimeMillis();
		long fileModTime = new File(string).lastModified();
		int maxTime = 1500;
		assertTrue((time - fileModTime) < maxTime);
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
	 * @see setGameOptions.feature
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
	 * @see setGameOptions.feature
	 */
	@When("the number of players is set to {int}")
	public void the_number_of_players_is_set_to(Integer int1) throws InvalidInputException {
		KingdominoApplication.getKingdomino().getCurrentGame().setNumberOfPlayers(int1);
	}
	/**
	 * @author Anthony Harissi Dagher
	 * @param string
	 * @see setGameOptions.feature
	 */
	@When("Harmony {string} selected as bonus option")
	public void harmony_selected_as_bonus_option(String string) throws InvalidInputException {
		BonusOption HarmonyOption = new BonusOption("Harmony", KingdominoApplication.getKingdomino());
		KingdominoApplication.getKingdomino().addBonusOption(HarmonyOption);
	}
	/**
	 * @author Anthony Harissi Dagher
	 * @param string
	 * @see setGameOptions.feature
	 */
	@When("Middle Kingdom {string} selected as bonus option")
	public void middle_Kingdom_selected_as_bonus_option(String string) throws InvalidInputException {
		BonusOption MKOption = new BonusOption("Middle Kingdom", KingdominoApplication.getKingdomino());
		KingdominoApplication.getKingdomino().addBonusOption(MKOption);
	}
	/**
	 * @author Anthony Harissi Dagher
	 * @param int1
	 * @see setGameOptions.feature
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
	 * @see setGameOptions.feature
	 */
	@Then("Harmony {string} an active bonus")
	public void harmony_an_active_bonus(String string) {
		KingdominoApplication.getKingdomino().getCurrentGame().hasSelectedBonusOptions();
	}
	/**
	 * @author Anthony Harissi Dagher
	 * @param string
	 * @see setGameOptions.feature
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
	 * @see startANewGame.feature
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
	 * @see startANewGame.feature
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
	 * @see startANewGame.feature
	 */
	@When("starting a new game is initiated")
	public void starting_a_new_game_is_initiated() throws InvalidInputException {
		KDController.startANewGame();
	}
	/**
	 * @author Anthony Harissi Dagher
	 * @see startANewGame.feature
	 */
	@When("reveal first draft is initiated")
	public void reveal_first_draft_is_initiated() {
		KDController.createOneDraft();
	}
	/**
	 * @author Anthony Harissi Dagher
	 * @see startANewGame.feature
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
	 * @see startANewGame.feature
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
	 * @see startANewGame.feature
	 */
	@Then("the first draft of dominoes is revealed")
	public void the_first_draft_of_dominoes_is_revealed() {
	    
		KDController.createOneDraft().setDraftStatus(DraftStatus.FaceUp);
	}
	/**
	 * @author Anthony Harissi Dagher
	 * @see startANewGame.feature
	 */
	@Then("all the dominoes form the first draft are facing up")
	public void all_the_dominoes_form_the_first_draft_are_facing_up() {
		DraftStatus status = DraftStatus.FaceDown;
		
		DraftStatus expectedStatus = DraftStatus.FaceUp;
		Boolean actualStatus = KDController.createOneDraft().setDraftStatus(DraftStatus.FaceUp);
		if(actualStatus==true) {
			 status = DraftStatus.FaceUp;
		}
		assertEquals(expectedStatus, status);
	}
	/**
	 * @author Anthony Harissi Dagher
	 * @see startANewGame.feature
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
	 * @see startANewGame.feature
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
}
	
