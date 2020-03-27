//package ca.mcgill.ecse223.kingdomino.features;
//
//import static org.junit.Assert.assertEquals;
//
//import java.io.BufferedReader;
//import java.io.FileReader;
//import java.io.IOException;
//import java.util.List;
//import java.util.*;
//
//import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
//import ca.mcgill.ecse223.kingdomino.controller.KDController;
//import ca.mcgill.ecse223.kingdomino.model.Castle;
//import ca.mcgill.ecse223.kingdomino.model.Domino;
//import ca.mcgill.ecse223.kingdomino.model.Draft;
//import ca.mcgill.ecse223.kingdomino.model.Game;
//import ca.mcgill.ecse223.kingdomino.model.Kingdom;
//import ca.mcgill.ecse223.kingdomino.model.Kingdomino;
//import ca.mcgill.ecse223.kingdomino.model.Player;
//import ca.mcgill.ecse223.kingdomino.model.TerrainType;
//import ca.mcgill.ecse223.kingdomino.model.Player.PlayerColor;
//import io.cucumber.java.en.Given;
//import io.cucumber.java.en.Then;
//import io.cucumber.java.en.When;
//
//public class CreateNextDraftStep {
//	
//	private static Draft nextDraftRecord = null;
//	
//	/**
//	 * These methods checks if the next drafts take
//	 * the top 5 dominos off the pile and that the
//	 * pile is empty after the  number of rounds 
//	 * 
//	 * @see CreateNextDraft.feature
//	 * @author Keon Olszewski 260927813
//	 */
//	
//	 
//	 @Given("the game is initialized to create next draft")
//	 public static void initialize_game_for_create_next_draft() {
//		 Kingdomino kingdomino = KingdominoApplication.getKingdomino();
//			Game game = new Game(48, kingdomino);
//			kingdomino.setCurrentGame(game);
//			game.setNumberOfPlayers(4);
//			
//			String[] users = { "User1", "User2","User3","User4"};
//			for (int i = 0; i < users.length; i++) {
//				game.getKingdomino().addUser(users[i]);
//				Player player = new Player(game);
//				player.setColor(PlayerColor.values()[i]);
//				Kingdom kingdom = new Kingdom(player);
//				new Castle(0, 0, kingdom, player);
//			}
//			
//			try {
//				BufferedReader br = new BufferedReader(new FileReader("src/main/resources/alldominoes.dat"));
//				String line = "";
//				String delimiters = "[:\\+()]";
//				while ((line = br.readLine()) != null) {
//					String[] dominoString = line.split(delimiters); // {id, leftTerrain, rightTerrain, crowns}
//					int dominoId = Integer.decode(dominoString[0]);
//					TerrainType leftTerrain = getTerrainType(dominoString[1]);
//					TerrainType rightTerrain = getTerrainType(dominoString[2]);
//					int numCrown = 0;
//					if (dominoString.length > 3) {
//						numCrown = Integer.decode(dominoString[3]);
//					}
//					new Domino(dominoId, leftTerrain, rightTerrain, numCrown, game);
//				}
//				br.close();
//			} catch (IOException e) {
//				e.printStackTrace();
//				throw new java.lang.IllegalArgumentException(
//						"Error occured while trying to read alldominoes.dat: " + e.getMessage());
//			}
//		
//		List<Domino> dominosInGame = game.getAllDominos();
//		
//		for (int i=0;i<dominosInGame.size()-1;i++) {
//			dominosInGame.get(i).setNextDomino(dominosInGame.get(i+1));
//		}
//		
//		Domino firstDomino=dominosInGame.get(0);
//		game.setTopDominoInPile(firstDomino);
//	 }
//	 
//	 @Given("there has been {int} drafts created")
//	 public static void there_has_been_x_drafts_created(Integer draftNum) {
//		Kingdomino kd = KingdominoApplication.getKingdomino();
//		Game game = kd.getCurrentGame();
//		
//		for (int i=0;i<draftNum-1;i++) {
//			KDController.createNextDraft();
//			nextDraftRecord=game.getNextDraft();
//		}
//		assertEquals(game.getAllDrafts().size(),draftNum.intValue());
//	 }
//	 
//	 @Given("there is a current draft")
//	 public static void given_exist_current_draft() {
//		 Kingdomino kd = KingdominoApplication.getKingdomino();
//		 Game game = kd.getCurrentGame();
//		 assertEquals(true,game.hasCurrentDraft());
//	 }
//	 
//	 @Given("there is a next draft")
//	 public static void given_exist_next_draft() {
//		 Kingdomino kd = KingdominoApplication.getKingdomino();
//		 Game game = kd.getCurrentGame();
//		 
//		 assertEquals(true,game.hasNextDraft());
//	 }
//	 
//	 @Given("the top 5 dominoes in my pile have the IDs {string}")
//	 public static void given_top_5_ids_are(String expectIds) {
//		 Kingdomino kd = KingdominoApplication.getKingdomino();
//		 Game game = kd.getCurrentGame();
//		 
//		 int [] ids=str2int(expectIds);
//		 boolean match=true;
//		 Domino top = game.getTopDominoInPile();
//		 if (top.getId()!=ids[0]) match=false;
//		 if (top.getNextDomino().getId()!=ids[1]) match=false;
//		 if (top.getNextDomino().getNextDomino().getId()!=ids[2]) match=false;
//		 if (top.getNextDomino().getNextDomino().getNextDomino().getId()!=ids[3]) match=false;
//		 if (top.getNextDomino().getNextDomino().getNextDomino().getNextDomino().getId()!=ids[4]) match=false;
//		 
//		 assertEquals(true,match);	 
//	 }
//	 
//	 @When("create next draft is initiated")
//	 public static void initiate_create_next(){
//		 KDController.createNextDraft();
//	 }
//	 
//	 @Then("a new draft is created from dominoes {string}")
//	 public static void check_draft_ids(String expectIds) {
//		 Kingdomino kd = KingdominoApplication.getKingdomino();
//		 Game game = kd.getCurrentGame();
//		 
//		 int [] ids=str2int(expectIds);
//		 boolean match=true;
//		 
//		 Draft newDraft=game.getNextDraft();
//		 List<Domino> dominos=newDraft.getIdSortedDominos();
//		 
//		 for (int i=0;i<dominos.size();i++) {
//			 if (dominos.get(i).getId()!=ids[i]) {
//				 match=false;
//				 break;
//			 }
//		 }
//		 
//		 assertEquals(true,match);	 
//	 }
//	 
//	 @Then("the next draft now has the dominoes {string}")
//	 public static void check_next_draft_ids(String expectIds) {
//		 Kingdomino kd = KingdominoApplication.getKingdomino();
//		 Game game = kd.getCurrentGame();
//		 
//		 int [] ids=str2int(expectIds);
//		 boolean match=true;
//		 
//		 Draft newDraft=game.getNextDraft();
//		 List<Domino> dominos=newDraft.getIdSortedDominos();
//		 
//		 for (int i=0;i<dominos.size();i++) {
//			 if (dominos.get(i).getId()!=ids[i]) {
//				 match=false;
//				 break;
//			 }
//		 }
//		 
//		 assertEquals(true,match);	 
//	 }
//	 
//	 @Then("the dominoes in the next draft are face down")
//	 public static void check_next_draft_status() {
//		 Kingdomino kd = KingdominoApplication.getKingdomino();
//		 Game game = kd.getCurrentGame();
//		 Draft newDraft=game.getNextDraft();
//		 if (newDraft.getDraftStatus().equals(Draft.DraftStatus.FaceDown)) {
//			 assertEquals(true,true);	 
//		 }
//		 else {
//			 assertEquals(true,false);
//		 }
//		 
//	 }
//	 
//	 @Then("the top domino of the pile is ID {int}")
//	 public static void check_top_id(Integer expectId) {
//		 Kingdomino kd = KingdominoApplication.getKingdomino();
//		 Game game = kd.getCurrentGame();
//		 Domino top = game.getTopDominoInPile();
//		 assertEquals(expectId.intValue(),top.getId());
//	 }
//	 
//	 @Then("the former next draft is now the current draft")
//	 public static void former_draft_is_now_current() {
//		 Kingdomino kd = KingdominoApplication.getKingdomino();
//		 Game game = kd.getCurrentGame();
//		 Draft currentDraft = game.getCurrentDraft();
//		assertEquals(true,currentDraft.equals(nextDraftRecord));
//	 }
//	 
//	 
//	 @Given("this is a {int} player game")
//	 public static void this_is_a_x_player_game(Integer playerNum) {
//	    Kingdomino kingdomino = KingdominoApplication.getKingdomino();
//	    kingdomino.removeAllGame(kingdomino.getCurrentGame());
//		
//		List<String> users= new ArrayList<String>();
//		int domNum=0;
//		
//		if (playerNum==4) {
//			users.add("usera");
//			users.add("userb");
//			users.add("userc");
//			users.add("userd");
//			domNum=48;
//
//		}
//		if (playerNum==3) {
//			users.add("usera");
//			users.add("userb");
//			users.add("userc");
//			domNum=36;
//		}
//		if (playerNum==2) {
//			users.add("usera");
//			users.add("userb");
//			domNum=24;
//		}
//		
//		   
//		Game game = new Game(domNum, kingdomino);
//		kingdomino.setCurrentGame(game);
//		game.setNumberOfPlayers(playerNum);
//		
//		for (int i = 0; i < users.size(); i++) {
//			game.getKingdomino().addUser(users.get(i));
//			Player player = new Player(game);
//			player.setColor(PlayerColor.values()[i]);
//			Kingdom kingdom = new Kingdom(player);
//			new Castle(0, 0, kingdom, player);
//		}
//	 
//		try {
//			BufferedReader br = new BufferedReader(new FileReader("src/main/resources/alldominoes.dat"));
//			String line = "";
//			String delimiters = "[:\\+()]";
//
//			int counter=0;
//			while (((line = br.readLine()) != null)&&counter<domNum) {
//				String[] dominoString = line.split(delimiters); // {id, leftTerrain, rightTerrain, crowns}
//				int dominoId = Integer.decode(dominoString[0]);
//				TerrainType leftTerrain = getTerrainType(dominoString[1]);
//				TerrainType rightTerrain = getTerrainType(dominoString[2]);
//				int numCrown = 0;
//				if (dominoString.length > 3) {
//					numCrown = Integer.decode(dominoString[3]);
//				}
//				new Domino(dominoId, leftTerrain, rightTerrain, numCrown, game);
//				counter++;
//			}
//			br.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//			throw new java.lang.IllegalArgumentException(
//					"Error occured while trying to read alldominoes.dat: " + e.getMessage());
//		}
//		
//		List<Domino> dominosInGame = game.getAllDominos();
//		System.out.println("total dominos in game: "+dominosInGame.size());
//		
//		for (int i=0;i<dominosInGame.size()-1;i++) {
//			dominosInGame.get(i).setNextDomino(dominosInGame.get(i+1));
//		}
//		
//		Domino firstDomino=dominosInGame.get(0);
//		game.setTopDominoInPile(firstDomino);
//	 }
//	 
//	 
//	 @Then("the pile is empty")
//	 public static void pile_is_empty() {
//		 List<Domino> p =KDController.getAvailableDominoPile();
//		 assertEquals(0,p.size());
//	 }
//	 
//	 @Then("there is no next draft")
//	 public static void no_next_draft() {
//		 Kingdomino kingdomino = KingdominoApplication.getKingdomino();
//		Game game = new Game(48, kingdomino);
//		assertEquals(true,!game.hasNextDraft());
//	 }
//		///////////////////////////////////////
//		/// -----Private Helper Methods---- ///
//		///////////////////////////////////////
//		
//		private static int[] str2int(String str) {
//			String splits []= str.split(",");
//			int [] num = new int[splits.length];
//			for (int i=0;i<splits.length;i++) {
//				num[i]=Integer.parseInt(splits[i]);
//			}
//			return num;
//		}
//		
//		private static TerrainType getTerrainType(String terrain) {
//			switch (terrain) {
//			case "W":
//				return TerrainType.WheatField;
//			case "F":
//				return TerrainType.Forest;
//			case "M":
//				return TerrainType.Mountain;
//			case "G":
//				return TerrainType.Grass;
//			case "S":
//				return TerrainType.Swamp;
//			case "L":
//				return TerrainType.Lake;
//			default:
//				throw new java.lang.IllegalArgumentException("Invalid terrain type: " + terrain);
//			}
//		}
//
//}
