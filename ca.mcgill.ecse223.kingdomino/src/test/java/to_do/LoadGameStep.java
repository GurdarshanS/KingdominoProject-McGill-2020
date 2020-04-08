package to_do;
//package ca.mcgill.ecse223.kingdomino.features;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertTrue;
//import static org.junit.Assert.assertNotNull;
//
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileReader;
//import java.io.IOException;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
//import ca.mcgill.ecse223.kingdomino.controller.KDController;
//import ca.mcgill.ecse223.kingdomino.model.Castle;
//import ca.mcgill.ecse223.kingdomino.model.Domino;
//import ca.mcgill.ecse223.kingdomino.model.Domino.DominoStatus;
//import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom;
//import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom.DirectionKind;
//import ca.mcgill.ecse223.kingdomino.model.Game;
//import ca.mcgill.ecse223.kingdomino.model.Kingdom;
//import ca.mcgill.ecse223.kingdomino.model.Kingdomino;
//import ca.mcgill.ecse223.kingdomino.model.Player;
//import ca.mcgill.ecse223.kingdomino.model.Player.PlayerColor;
//import ca.mcgill.ecse223.kingdomino.model.TerrainType;
//import ca.mcgill.ecse223.kingdomino.model.DominoSelection;
//import ca.mcgill.ecse223.kingdomino.model.Draft;
//import io.cucumber.java.en.Given;
//import io.cucumber.java.en.Then;
//import io.cucumber.java.en.When;
//
//import ca.mcgill.ecse223.kingdomino.controller.KDController.InvalidInputException;
//
//
//
//public class LoadGameStep{
//		
//	/**
//		 * @author Anthony Harissi Dagher
//		 * Test for loadGame
//		 * @see loadGame.feature
//		 */
//		Exception thrownException = null;
//		
//		@Given("the game is initialized for load game")
//		public void the_game_is_initialized_for_load_game() {
//			KDController.initializeGame();
//			KDController.startANewGame();
//		}
//		/**
//		 * @author Anthony Harissi Dagher
//		 * @param string
//		 * @throws InvalidInputException
//		 * @see loadGame.feature
//		 */
//		@When("I initiate loading a saved game from {string}")
//		public void i_initiate_loading_a_saved_game_from(String string) throws InvalidInputException {
//		    try{
//		    	KDController.loadGame(new File(string));
//		    }catch(InvalidInputException i) {
//		    	thrownException = i;
//		    	throw new InvalidInputException(i.getMessage());
//		    }
//		}
//		/**
//		 * @author Anthony Harissi Dagher
//		 * @see loadGame.feature
//		 */
//		@When("each tile placement is valid")
//		public void each_tile_placement_is_valid() {
//		    assertEquals(null, thrownException);
//		}
//		/**
//		 * @author Anthony Harissi Dagher
//		 * @see loadGame.feature
//		 */
//		@When("the game result is not yet final")
//		public void the_game_result_is_not_yet_final() {
//			KingdominoApplication.getKingdomino().getCurrentGame().hasNextDraft();
//		}
//		/**
//		 * @author Anthony Harissi Dagher
//		 * @param int1
//		 * @see loadGame.feature
//		 */
//		@Then("it shall be player {int}'s turn")
//		public void it_shall_be_player_s_turn(Integer int1) {
//		    KingdominoApplication.getKingdomino().getCurrentGame().setNextPlayer(KingdominoApplication.getKingdomino().getCurrentGame().getPlayer(int1-1));
//		}
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
//}
