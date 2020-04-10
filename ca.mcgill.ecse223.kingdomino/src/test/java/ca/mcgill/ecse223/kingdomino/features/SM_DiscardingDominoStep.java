package ca.mcgill.ecse223.kingdomino.features;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.KDController;
import ca.mcgill.ecse223.kingdomino.controller.KDQuery;
import ca.mcgill.ecse223.kingdomino.development.View;
import ca.mcgill.ecse223.kingdomino.model.Castle;
import ca.mcgill.ecse223.kingdomino.model.Domino;
import ca.mcgill.ecse223.kingdomino.model.Domino.DominoStatus;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom.DirectionKind;
import ca.mcgill.ecse223.kingdomino.model.Game;
import ca.mcgill.ecse223.kingdomino.model.Gameplay;
import ca.mcgill.ecse223.kingdomino.model.Kingdom;
import ca.mcgill.ecse223.kingdomino.model.KingdomTerritory;
import ca.mcgill.ecse223.kingdomino.model.Kingdomino;
import ca.mcgill.ecse223.kingdomino.model.Player;
import ca.mcgill.ecse223.kingdomino.model.Player.PlayerColor;
import ca.mcgill.ecse223.kingdomino.model.TerrainType;
import ca.mcgill.ecse223.kingdomino.model.DominoSelection;
import ca.mcgill.ecse223.kingdomino.model.Draft;
import ca.mcgill.ecse223.kingdomino.model.Draft.DraftStatus;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.en.And;


public class SM_DiscardingDominoStep {
	private static Kingdomino kd = KingdominoApplication.getKingdomino();
	
	@Given("the game is initialized for discarding domino")
	public static void game_initialized_for_discarding() {
		

		//		to bring the game up to speed
		KDController.initiateEmptyGame();
		
		//this would be one of the superstate Playing.ManipulatingDomino
		kd.setStateMachine();
		kd.getStateMachine().setGamestatus("ManipulatingDomino"); 						
		System.out.println("state machine state: "+kd.getStateMachine().getGamestatusFullName());
		
		//we are supposed to be choosing from current draft, but we haven't made a draft yet
		//if we just choose a random domino, wouldn't work. See KDController.chooseNext
		//so let's just manually make a draft
		
		Draft manualDraft = new Draft(DraftStatus.FaceUp,kd.getCurrentGame());
		manualDraft.addIdSortedDomino(KDController.getdominoByID(33));
		manualDraft.addIdSortedDomino(KDController.getdominoByID(2));
		manualDraft.addIdSortedDomino(KDController.getdominoByID(13));
		manualDraft.addIdSortedDomino(KDController.getdominoByID(5));
		//these ids arent chosen by the test 
		
		kd.getCurrentGame().setCurrentDraft(manualDraft);
	}
}
