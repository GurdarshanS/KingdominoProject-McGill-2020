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
import ca.mcgill.ecse223.kingdomino.model.Gameplay;
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


public class InitializingGameStep {
	
	/**
	 * These methods test the initialization
	 * of a kingdomino game using state machine
	 * @see InitializingGame.feature
	 * @author Jing Han 260528152
	 */
	
	@Given("the game has not been started")
	public static void game_has_not_started() {
		Kingdomino kd = KingdominoApplication.getKingdomino();
		Game game=kd.getCurrentGame();

		int numPlayers=4;
		List<String> selectedBonusOptions = new ArrayList<String>();
		
		try{
			KDController.setGameOptions(numPlayers, selectedBonusOptions);
			int dominoNums=game.getMaxPileSize();
			KDController.createDominoPile(game,dominoNums);
			KDController.createPlayers();
		}
		catch(Exception e) {}
	}
	
	@When("start of the game is initiated")
	public static void game_start_initiated() {
		KDController.initializeSM();
		KDController.draftReadySM();
	}

}
