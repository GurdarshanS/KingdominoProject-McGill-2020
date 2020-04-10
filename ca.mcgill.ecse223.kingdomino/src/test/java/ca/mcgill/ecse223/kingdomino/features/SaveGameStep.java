package ca.mcgill.ecse223.kingdomino.features;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;
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
import ca.mcgill.ecse223.kingdomino.model.DominoSelection;
import ca.mcgill.ecse223.kingdomino.model.Draft;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import ca.mcgill.ecse223.kingdomino.controller.KDController.InvalidInputException;

public class SaveGameStep {
	
	private static long lastModified;
	
	/**
	 * these methods check for serializating capability
	 * @author Anthony Harissi Dagher 260924250
	 * Test for saveGame
	 * @see saveGame.feature
	 */
	@Given("the game is initialized for save game")
	public void the_game_is_initialized_for_save_game() {
		KDController.initiateEmptyGame();
	}
	
	@Given("the game is still in progress")
	public void the_game_is_still_in_progress() {
		assertEquals(KingdominoApplication.getKingdomino().getCurrentGame().hasNextPlayer(), true);
	}
	
	@Given("no file named {string} exists in the filesystem")
	public void no_file_named_exists_in_the_filesystem(String filename) {
		File file = new File(filename);
		if (file.exists()) {
			file.delete();
		}
		assertEquals(false,file.exists());
	}
	
	@When("the user initiates saving the game to a file named {string}")
	public void the_user_initiates_saving_the_game_to_a_file_named(String filename) throws InvalidInputException, IOException {
		boolean saved=KDController.saveGame(filename, true);
		assertEquals(true,saved);
		lastModified = new File(filename).lastModified();
	}
	
	@Then("a file named {string} shall be created in the filesystem")
	public void a_file_named_shall_be_created_in_the_filesystem(String filename) throws IOException {
		assertTrue(new File(filename).exists());
	}
	
	@Given("the file named {string} exists in the filesystem")
	public void the_file_named_exists_in_the_filesystem(String filename) throws IOException  {
		assertTrue(new File(filename).exists());
		
	}
	
	@When("the user agrees to overwrite the existing file named {string}")
	public void the_user_agrees_to_overwrite_the_existing_file_named(String newFilename) throws InvalidInputException, IOException {
		boolean overwrite=true;
		KDController.saveGame(newFilename, overwrite);
	}
	
	@Then("the file named {string} shall be updated in the filesystem")
	public void the_file_named_shall_be_updated_in_the_filesystem(String filename) {
		long newModified = new File(filename).lastModified();
		assertTrue(newModified!=lastModified);
	}

}