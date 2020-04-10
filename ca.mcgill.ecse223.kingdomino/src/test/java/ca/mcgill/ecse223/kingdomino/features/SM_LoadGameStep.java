package ca.mcgill.ecse223.kingdomino.features;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import java.util.Scanner;
import java.util.Set;

import com.google.common.collect.Sets;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.KDController;
import ca.mcgill.ecse223.kingdomino.model.Castle;
import ca.mcgill.ecse223.kingdomino.model.Domino;
import ca.mcgill.ecse223.kingdomino.model.Domino.DominoStatus;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom.DirectionKind;
import ca.mcgill.ecse223.kingdomino.model.Draft.DraftStatus;
import ca.mcgill.ecse223.kingdomino.model.Game;
import ca.mcgill.ecse223.kingdomino.model.Kingdom;
import ca.mcgill.ecse223.kingdomino.model.KingdomTerritory;
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
import ca.mcgill.ecse223.kingdomino.controller.KDQuery;
import ca.mcgill.ecse223.kingdomino.controller.View;



public class SM_LoadGameStep{
	
	/**
	 * These methods test the loading 
	 * of a kingdomino game
	 * @see LoadGame.feature
	 * @author Anthony Harissi Dagher 260924250
	 */
	
	private static Kingdomino kd = KingdominoApplication.getKingdomino();
	private static File file;
	private static List<String> data=new ArrayList<String>();
	private static String claims;
	private static String unclaims;
	private static String p1Data;
	private static String p2Data;
	private static String p3Data;
	private static String p4Data;
	private static Player p1;
	private static Player p2;
	private static Player p3;
	private static Player p4;
	private static boolean valid;

		/**
		 * @author Anthony Harissi Dagher
		 * Test for loadGame
		 * @see loadGame.feature
		 */
		Exception thrownException = null;
		
		@Given("the game is initialized for load game")
		public void the_game_is_initialized_for_load_game() {
			KDController.initiateEmptyGame();
			p1=kd.getCurrentGame().getPlayer(0);
			p2=kd.getCurrentGame().getPlayer(1);
			p3=kd.getCurrentGame().getPlayer(2);
			p4=kd.getCurrentGame().getPlayer(3);
		}
		
		
		/**
		 * @author Anthony Harissi Dagher
		 * @param string
		 * @throws InvalidInputException
		 * @see loadGame.feature
		 */
		@When("I initiate loading a saved game from {string}")
		public void i_initiate_loading_a_saved_game_from(String string) throws InvalidInputException {
			data.clear();
			try {
				file = new File(string);
				Scanner reader = new Scanner(file);
				while (reader.hasNextLine()){
					String line=reader.nextLine();
					data.add(line);
				}
			}
			catch(Exception e) {}
			
			claims=data.get(0);
			unclaims=data.get(1);
			p1Data=data.get(2);
			p2Data=data.get(3);
			p3Data=data.get(4);
			p4Data=data.get(5);	
			
			
			try {
				parseAndPlace(p1,p1Data);
				parseAndPlace(p2,p2Data);
				parseAndPlace(p3,p3Data);
				parseAndPlace(p4,p4Data);
			}
			catch (Exception e){
				valid=false;
				return;
			}
				


			
		}
		/**
		 * @author Anthony Harissi Dagher
		 * @see loadGame.feature
		 */
		@When("each tile placement is valid")
		public void each_tile_placement_is_valid() {
//			View.printAllKingdoms(kd);
			boolean valid1=verifyAllPlacements(p1);
			boolean valid2=verifyAllPlacements(p2);
			boolean valid3=verifyAllPlacements(p3);
			boolean valid4=verifyAllPlacements(p4);
			
			valid=valid1&&valid2&&valid3&&valid4;
			assertEquals(true,valid);
		   
		}
		/**
		 * @author Anthony Harissi Dagher
		 * @see loadGame.feature
		 */
		@When("the game result is not yet final")
		public void the_game_result_is_not_yet_final() {
			boolean finished=true;
			
			for (Domino d:kd.getCurrentGame().getAllDominos()) {
				if (!(d.getStatus().equals(DominoStatus.PlacedInKingdom)||d.getStatus().equals(DominoStatus.Discarded))) {
					finished=false;
					break;
				}
			}
			assertEquals(false,finished);
		}
		/**
		 * @author Anthony Harissi Dagher
		 * @param int1
		 * @see loadGame.feature
		 */
		@Then("it shall be player {int}'s turn")
		public void it_shall_be_player_s_turn(Integer int1) {
			kd.getCurrentGame().setNextPlayer(kd.getCurrentGame().getPlayer(int1-1));
		}
//		/**
//		 * @author Anthony Harissi Dagher
//		 * @param dataTable
//		 * @see loadGame.feature
//		 */
		@Then("each of the players should have the corresponding tiles on their grid:")
		public void each_of_the_players_should_have_the_corresponding_tiles_on_their_grid(io.cucumber.datatable.DataTable dataTable) {
			
			List<Integer> expectedIds = new ArrayList<Integer>();
			List<Integer> hasIds=new ArrayList<Integer>();
	   		List<Map<String, String>> valueMaps = dataTable.asMaps();
	   		
	   		boolean allmatch=true;
	   		
	   		int i=0;
	   		for (Map<String,String> map:valueMaps) {
	   			hasIds.clear();
	   			expectedIds.clear();
	   			
	   			String[] idStrings=map.get("playerTiles").split(",");
	   			for (String s:idStrings) expectedIds.add(Integer.decode(s));
	   			
	   			Player p=kd.getCurrentGame().getPlayer(i);
	   			for (KingdomTerritory t:p.getKingdom().getTerritories()) {
	   				if (t instanceof DominoInKingdom) {
	   					hasIds.add(((DominoInKingdom) t).getDomino().getId());
	   				}
	   			}
	   			i+=1;
	   			
	   			if (!(expectedIds.equals(hasIds))) {
	   				allmatch=false;
	   				break;
	   			}
			}
	   		assertEquals(true,allmatch);
		}
		/**
		 * @author Anthony Harissi Dagher
		 * @param dataTable
		 * @see loadGame.feature
		 */
		@Then("each of the players should have claimed the corresponding tiles:")
		public void each_of_the_players_should_have_claimed_the_corresponding_tiles(io.cucumber.datatable.DataTable dataTable) {
			
			List<Integer> expectedIds = new ArrayList<Integer>();
			List<Integer> hasIds=new ArrayList<Integer>();
	   		List<Map<String, String>> valueMaps = dataTable.asMaps();
	   		
	   		boolean allmatch=true;
	   		Draft firstDraft = new Draft(DraftStatus.FaceUp,kd.getCurrentGame());
	   		
	   		int i=0;
	   		for (Map<String,String> map:valueMaps) {
	   			hasIds.clear();
	   			expectedIds.clear();
	   			
	   			String[] idStrings=map.get("claimedTile").split(",");
	   			for (String s:idStrings) expectedIds.add(Integer.decode(s));
	   			
	   			Player p=kd.getCurrentGame().getPlayer(i);
	   			DominoSelection sel=new DominoSelection(p,KDController.getdominoByID(expectedIds.get(0)),firstDraft);
	   			i+=1;
	   		}
		} 
		/**
		 * @author Anthony Harissi Dagher
		 * @param string
		 * @see loadGame.feature
		 */
		@Then("tiles {string} shall be unclaimed on the board")
		public void tiles_shall_be_unclaimed_on_the_board(String expectedId) {
			List<Integer> hasIds=new ArrayList<Integer>();
			Game game = KingdominoApplication.getKingdomino().getCurrentGame();
			game.setCurrentDraft(new Draft(DraftStatus.FaceUp, game));
			String [] ids = expectedId.split(", ");
			for (String s: ids) hasIds.add(Integer.decode(s));
			
			for (int i:hasIds) {
				kd.getCurrentGame().getCurrentDraft().addIdSortedDomino(KDController.getdominoByID(i));
			}
			
			
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
			try {
				boolean valid1=verifyAllPlacements(p1);
				boolean valid2=verifyAllPlacements(p2);
				boolean valid3=verifyAllPlacements(p3);
				boolean valid4=verifyAllPlacements(p4);
				valid=valid1&&valid2&&valid3&&valid4;
			}
			catch(Exception e) {
				valid=false;
			}
			
			
			List<Integer> claimedIds=parseClaim(claims);
			Set<Integer> claimSet=new HashSet<Integer>(claimedIds);
			
			if (valid) {
				for (Player p:kd.getCurrentGame().getPlayers()) {
					
					List<Integer> playedIds=new ArrayList<Integer>();
					for (KingdomTerritory t:p.getKingdom().getTerritories()) {
						if (t instanceof DominoInKingdom) {
							playedIds.add(((DominoInKingdom) t).getDomino().getId());
						}
					}
					
					Set<Integer> existSet=new HashSet<Integer>(playedIds);
					if (!(Sets.intersection(claimSet, existSet)).isEmpty()) {
						valid=false;
						break;
					}
				}
			}
			
			
			assertEquals(false,valid);
		}
		
		private static boolean verifyAllPlacements(Player p) {
			boolean valid=true;
			for (KingdomTerritory t:p.getKingdom().getTerritories()) {
				if (t instanceof DominoInKingdom) {
					 if (KDQuery.verifyDominoInKingdom(p, (DominoInKingdom) t)) {
						((DominoInKingdom) t).getDomino().setStatus(DominoStatus.PlacedInKingdom); 
					 }
					 else {
						 valid=false;
						 break;
					 }
				}
			}
			return valid;
		}
		
		private static void parseAndPlace(Player player, String playerData) {
			int id=-1;
			int posx=-1;
			int posy=-1;
			String dir="none";
			String pos = playerData.split(":")[1];
			String[] pieces=pos.split(", ");
			for (String s:pieces) {
				s=s.replaceAll("\\s+", "");
				id = Integer.decode(s.split("@")[0]);
				String info = s.split("@")[1];
				info=info.substring(info.indexOf("(") + 1, info.indexOf(")"));
				posx=Integer.decode(info.split(",")[0]);
				posy=Integer.decode(info.split(",")[1]);
				if (info.split(",")[2].equalsIgnoreCase("r")) dir="right";
				else if (info.split(",")[2].equalsIgnoreCase("l")) dir="left";
				else if (info.split(",")[2].equalsIgnoreCase("d")) dir="down";
				else if (info.split(",")[2].equalsIgnoreCase("u")) dir="up";
				KDController.preplaceArbitraryDomino(player, KDController.getdominoByID(id), posx, posy, dir);
				KDController.getdominoByID(id).setStatus(DominoStatus.ErroneouslyPreplaced);
			}
			KDController.updateNextPlayer(kd.getCurrentGame().getNextPlayer());
			
		}
		
		private static List<Integer> parseClaim(String claim) {
//			C: 34, 37, 29, 44
			String chunk=claim.split(": ")[1];
			String [] idstr= chunk.split(", ");
			List<Integer> id=new ArrayList<Integer>();
			for (String s:idstr) {
				id.add(Integer.decode(s));
			}
			return id;
		}
		
		
}
