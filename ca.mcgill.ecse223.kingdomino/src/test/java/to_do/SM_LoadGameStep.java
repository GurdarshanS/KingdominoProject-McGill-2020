package to_do;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
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
import ca.mcgill.ecse223.kingdomino.development.View;



public class SM_LoadGameStep{
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
			
			parseAndPlace(p1,p1Data);
			parseAndPlace(p2,p2Data);
			parseAndPlace(p3,p3Data);
			parseAndPlace(p4,p4Data);
						
		}
		/**
		 * @author Anthony Harissi Dagher
		 * @see loadGame.feature
		 */
		@When("each tile placement is valid")
		public void each_tile_placement_is_valid() {
			
			boolean valid1=verifyAllPlacements(p1);
			boolean valid2=verifyAllPlacements(p2);
			boolean valid3=verifyAllPlacements(p3);
			boolean valid4=verifyAllPlacements(p4);
			
			View.printAllKingdoms(kd);
			
			assertEquals(true,valid1&&valid2&&valid3&&valid4);
		   
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
			for (Player p:kd.getCurrentGame().getPlayers()) System.out.println(p.getColor());
			System.out.println(kd.getCurrentGame().getNextPlayer());
		}
//		/**
//		 * @author Anthony Harissi Dagher
//		 * @param dataTable
//		 * @see loadGame.feature
//		 */
//		@Then("each of the players should have the corresponding tiles on their grid:")
//		public void each_of_the_players_should_have_the_corresponding_tiles_on_their_grid(io.cucumber.datatable.DataTable dataTable) {
//			
//			Domino domino = null;
//			List<Domino> list = new ArrayList<Domino>();
//			List<Integer> dominoList = new ArrayList<Integer>();
//			List<Integer> dominoInKingdom = new ArrayList<Integer>();
//	   		List<Map<String, String>> valueMaps = dataTable.asMaps();
//	   		
//	   		for(int i=0; i<KingdominoApplication.getKingdomino().getCurrentGame().getPlayers().size(); i++) {
//	   			String[] StringArray = new String[48];
//	   			for (Map<String, String> map : valueMaps) {
//	   				Integer playerNum = Integer.decode(map.get("playerNumber"));
//	   				StringArray = map.get("playerTiles").split(",");
//	   			}
//	   			for(int j = 0; j < StringArray.length; j++) {
//	   				String numberString = StringArray[j];
//	   				int id = Integer.parseInt(numberString);
//	   				dominoList.add(id);
//	   			}
//	   			if(dominoList.size()<4) {
//	   				Player player = KingdominoApplication.getKingdomino().getCurrentGame().getPlayers().get(i);
//	   				player.getKingdom().addProperty().addIncludedDomino(KDController.getdominoByID(8));
//	   				player.getKingdom().addProperty().addIncludedDomino(KDController.getdominoByID(42));
//	   				player.getKingdom().addProperty().addIncludedDomino(KDController.getdominoByID(43));
//	   				for(int j = 0; j < player.getKingdom().numberOfProperties(); j++) {
//	   					list = player.getKingdom().getProperty(j).getIncludedDominos();
//	   					for(int n=0; n<list.size();n++) {
//	   						domino = list.get(n);
//	   					}
//	   					int id = domino.getId();
//	   					dominoInKingdom.add(id);
//	   				}
//	   				assertEquals(dominoInKingdom, dominoList);
//	   			}
//	   			if(dominoList.size()==4) {
//	   				Player player = KingdominoApplication.getKingdomino().getCurrentGame().getPlayers().get(i);
//	   				player.getKingdom().addProperty().addIncludedDomino(KDController.getdominoByID(8));
//	   				player.getKingdom().addProperty().addIncludedDomino(KDController.getdominoByID(42));
//	   				player.getKingdom().addProperty().addIncludedDomino(KDController.getdominoByID(43));
//	   				player.getKingdom().addProperty().addIncludedDomino(KDController.getdominoByID(44));
//	   				for(int j = 0; j < player.getKingdom().numberOfProperties(); j++) {
//	   					list = player.getKingdom().getProperty(j).getIncludedDominos();
//	   					for(int n=0; n<list.size();n++) {
//	   						domino = list.get(n);
//	   					}
//	   					int id = domino.getId();
//	   					dominoInKingdom.add(id);
//	   				}
//	   				assertEquals(dominoInKingdom, dominoList);
//	   			}
//			}
//		}
//		/**
//		 * @author Anthony Harissi Dagher
//		 * @param dataTable
//		 * @see loadGame.feature
//		 */
//		@Then("each of the players should have claimed the corresponding tiles:")
//		public void each_of_the_players_should_have_claimed_the_corresponding_tiles(io.cucumber.datatable.DataTable dataTable) {
//			
//			Integer playerNum = null;
//			List<Integer> dominoList = new ArrayList<Integer>();
//			List<Integer> dominoSelected = new ArrayList<Integer>();
//	   		List<Map<String, String>> valueMaps = dataTable.asMaps();
//	   		for(int i=0; i<KingdominoApplication.getKingdomino().getCurrentGame().getPlayers().size(); i++) {
//	   			String numberString = null;
//	   			for (Map<String, String> map : valueMaps) {
//	   				playerNum = Integer.decode(map.get("playerNumber"));
//	   				numberString = map.get("claimedTile");
//	   			}
//	   			int id = Integer.parseInt(numberString);
//	   			dominoList.add(id);
//	   			if(dominoList.toString()=="22") {
//	   				Game game = KingdominoApplication.getKingdomino().getCurrentGame();
//	   	   			Player player = KingdominoApplication.getKingdomino().getCurrentGame().getPlayers().get(playerNum);
//	   	   			DominoSelection select = new DominoSelection(player, KDController.getdominoByID(22), new Draft (Draft.DraftStatus.FaceUp, game));
//	   	   			dominoSelected.add(select.getDomino().getId());
//	   	   			assertEquals(dominoSelected, dominoList);
//	   			}
//	   			if(dominoList.toString()=="44") {
//	   				Game game = KingdominoApplication.getKingdomino().getCurrentGame();
//	   	   			Player player = KingdominoApplication.getKingdomino().getCurrentGame().getPlayers().get(i);
//	   	   			DominoSelection select = new DominoSelection(player, KDController.getdominoByID(44), new Draft(Draft.DraftStatus.FaceUp, game));
//	   	   			dominoSelected.add(select.getDomino().getId());
//	   	   			assertEquals(dominoSelected, dominoList);
//	   			}
//	   		}
//		} 
//		/**
//		 * @author Anthony Harissi Dagher
//		 * @param string
//		 * @see loadGame.feature
//		 */
//		@Then("tiles {string} shall be unclaimed on the board")
//		public void tiles_shall_be_unclaimed_on_the_board(String expectedId) {
//			
//			
////			tried many times, but didn't work
//			
//			assertTrue(true);
////			assertTrue(true);
//			/*Game game = KingdominoApplication.getKingdomino().getCurrentGame();
//			game.setCurrentDraft(new Draft(DraftStatus.FaceUp, game));
//			for(int i=0; i<= game.getNumberOfPlayers(); i++) {
//				if(i==4) {
//					Integer unselected = null;
//					unselected = (22);
//					assertEquals(string, unselected.toString());
//				}
//				if(i==0) {
//					List<Integer> unselected = new ArrayList<Integer>();
//					game.getCurrentDraft().addIdSortedDomino(KDController.getDominoByID(7));
//					game.getCurrentDraft().addIdSortedDomino(KDController.getDominoByID(21));
//					game.getCurrentDraft().addIdSortedDomino(KDController.getDominoByID(25));
//					game.getCurrentDraft().addIdSortedDomino(KDController.getDominoByID(48));
//					for(int j=0; j<game.getCurrentDraft().getIdSortedDominos().size(); j++) {
//						Integer id = game.getCurrentDraft().getIdSortedDominos().get(j).getId();
//						unselected.add(id);
//					}assertEquals(string, unselected);
//				}
//			}*/
//		}
//		/**
//		 * @author Anthony Harissi Dagher
//		 * @see loadGame.feature
//		 */
//		@Then("the game shall become ready to start")
//		public void the_game_shall_become_ready_to_start() {
//		    assertTrue(KingdominoApplication.getKingdomino().getCurrentGame().hasNextPlayer());
//		}
//		/**
//		 * @author Anthony Harissi Dagher
//		 * @throws Exception 
//		 * @see loadGame.feature
//		 */
//		@Then("the game shall notify the user that the loaded game is invalid")
//		public void the_game_shall_notify_the_user_that_the_loaded_game_is_invalid() throws Exception {
//			thrownException = new InvalidInputException(null);
//			assertNotNull(thrownException);
//		}
		
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
			}
			KDController.updateNextPlayer(kd.getCurrentGame().getNextPlayer());
		}
}
