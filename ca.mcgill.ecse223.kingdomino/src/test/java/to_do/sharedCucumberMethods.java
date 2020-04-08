package to_do;
//package ca.mcgill.ecse223.kingdomino.features;
//
//import static org.junit.Assert.assertEquals;
//
//import java.io.BufferedReader;
//import java.io.FileReader;
//import java.io.IOException;
//import java.util.List;
//import java.util.Map;
//import java.util.*;
//
//import org.junit.internal.runners.model.EachTestNotifier;
//
//import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
//import ca.mcgill.ecse223.kingdomino.controller.KDController;
//import ca.mcgill.ecse223.kingdomino.model.*;
//import ca.mcgill.ecse223.kingdomino.model.Domino.DominoStatus;
//import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom.DirectionKind;
//import ca.mcgill.ecse223.kingdomino.model.Player.PlayerColor;
//import ca.mcgill.ecse223.kingdomino.model.Draft.DraftStatus;
//
//import ca.mcgill.ecse223.kingdomino.controller.PropertyAttribute;
//import io.cucumber.java.After;
//import io.cucumber.java.en.Given;
//import io.cucumber.java.en.Then;
//import io.cucumber.java.en.When;
//
//import ca.mcgill.ecse223.kingdomino.features.CucumberCleanUp;
//
//public class sharedCucumberMethods {
//	
//	/**
//	 * These Cucumber methods are shared across
//	 * multiple features and step definitions 
//	 * @author Jing Han 260528152
//	 */
//	
//	@Given("the current player preplaced the domino with ID {int} at position {int}:{int} and direction {string}")
//	public void the_current_player_preplaced_domino(Integer id, Integer posx, Integer posy, String dir) {
//		Player player = KingdominoApplication.getKingdomino().getCurrentGame().getNextPlayer();
//		Domino dominoToPlace = KDController.getdominoByID(id);
//		KDController.prePlaceDomino(player, dominoToPlace, posx, posy, dir);
//	}
//	
//	@Given("the following dominoes are present in a player's kingdom:")
//	public void the_following_dominoes_are_present_in_a_player_kingdom(io.cucumber.datatable.DataTable dataTable) {
//		Player player = KingdominoApplication.getKingdomino().getCurrentGame().getNextPlayer();
//		
//		List<Map<String, String>> valueMaps = dataTable.asMaps();
//		for (Map<String, String> map : valueMaps) {
//			// Get values from cucumber table
//			Integer id = Integer.decode(map.get("id"));
//			String dir = map.get("dominodir");
//			Integer posx = Integer.decode(map.get("posx"));
//			Integer posy = Integer.decode(map.get("posy"));
//
//			// Add the domino to a player's kingdom
//			Domino dominoToPlace = KDController.getdominoByID(id); 
//			KDController.prePlaceDomino(player, dominoToPlace, posx, posy, dir);
//			dominoToPlace.setStatus(DominoStatus.PlacedInKingdom);
//		}
//	}
//	
//	@Given("the player's kingdom has the following dominoes:")
//	public void the_player_s_kingdom_has_the_following_dominoes(io.cucumber.datatable.DataTable dataTable) {
//		Player player = KingdominoApplication.getKingdomino().getCurrentGame().getNextPlayer();
//		
//		List<Map<String, String>> valueMaps = dataTable.asMaps();
//		for (Map<String, String> map : valueMaps) {
//			// Get values from cucumber table
//			Integer id = Integer.decode(map.get("id"));
//			String dir = map.get("dominodir");
//			Integer posx = Integer.decode(map.get("posx"));
//			Integer posy = Integer.decode(map.get("posy"));
//
//			// Add the domino to a player's kingdom
//			Domino dominoToPlace = KDController.getdominoByID(id); 
//			KDController.prePlaceDomino(player, dominoToPlace, posx, posy, dir);
//		}
//	}
//	
//	/**
//	 * 
//	 *  These methods are shared by the rotate, move
//	 *  and place domino features of a player. 
//	 * 
//	 * 	@see RotateCurrentDomino.feature, MoveCurrentDomino.feature, PlaceDomino.feature
//	 *  @author Massimo Vadacchino 260928064
//	 * 
//	 */
//	
//	@Given("the game is initialized for move current domino")
//	public void the_game_is_initialized_for_move_current_domino() {
//	   
//		// Intialize empty game
//		KDController.initiateEmptyGame();
//		
//	}
//
//	@Given("it is {string}'s turn")
//	public void it_is_s_turn(String string) {
//
//		Player player = getPlayerByColor(string);
//		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
//		
//		game.setNextPlayer(player);
//		
//	}
//
//	@Given("{string} has selected domino {int}")
//	public void has_selected_domino(String string, Integer int1) {
//
//		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
//	
//		Domino dominoToSelect = KDController.getdominoByID(int1);
//		Player playerToSelectDomino = getPlayerByColor(string);
//		
//		game.setNextPlayer(playerToSelectDomino);
//		
//		Draft currentDraft = new Draft(DraftStatus.Sorted, game);
//		currentDraft.addIdSortedDomino(dominoToSelect);
//		game.addAllDraft(currentDraft);
//		game.setCurrentDraft(currentDraft);
//		
//		dominoToSelect.setStatus(DominoStatus.InCurrentDraft);
//		
//	
//		DominoSelection dSelection = new DominoSelection(playerToSelectDomino, dominoToSelect, currentDraft);
//		
//		playerToSelectDomino.setDominoSelection(dSelection);
//		currentDraft.addSelection(dSelection);
//		
//	}
//	
//	@Given("{string}'s kingdom has following dominoes:")
//	public void s_kingdom_has_following_dominoes(String string, io.cucumber.datatable.DataTable dataTable) {
//	
//		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
//		List<Map<String, String>> valueMaps = dataTable.asMaps();
//		
//		for (Map<String, String> map : valueMaps) {
//			
//			// Get values from cucumber table
//			Integer id = Integer.decode(map.get("id"));
//			DirectionKind dir = getDirection(map.get("dir"));
//			Integer posx = Integer.decode(map.get("posx"));
//			Integer posy = Integer.decode(map.get("posy"));
//
//			// Add the domino to a player's kingdom
//			Domino dominoToPlace = KDController.getdominoByID(id);
//			Kingdom kingdom = getPlayerByColor(string).getKingdom();
//			DominoInKingdom domInKingdom = new DominoInKingdom(posx, posy, kingdom, dominoToPlace);
//			domInKingdom.setDirection(dir);
//			dominoToPlace.setStatus(DominoStatus.PlacedInKingdom);
//			
//		}
//		
//	}
//	
//	@Given("domino {int} is tentatively placed at position {int}:{int} with direction {string}")
//	public void domino_is_tentatively_placed_at_position_with_direction(Integer int1, Integer int2, Integer int3, String string) {
//	   
//		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
//		
//		Domino dominoToBeTentativelyPlaced = KDController.getdominoByID(int1);
//		Kingdom playerKingdom = game.getNextPlayer().getKingdom();
//		
//		DominoInKingdom dInKingdom = new DominoInKingdom(int2, int3, playerKingdom, dominoToBeTentativelyPlaced);
//		dInKingdom.setDirection(getDirection(string));
//		
//		game.getCurrentDraft().removeIdSortedDomino(dominoToBeTentativelyPlaced);
//		dominoToBeTentativelyPlaced.setStatus(DominoStatus.ErroneouslyPreplaced);
//		
//	}
//	
//	@Given("domino {int} has status {string}")
//	public void domino_has_status(Integer int1, String string) {
//	 
//		Domino domino = KDController.getdominoByID(int1);
//		domino.setStatus(getDominoStatusCapital(string));
//		
//	}
//	
//	
//	
//		/////////////////////////////
//		// shared helper methods   //
//		// across multiple steps   //
//		////////////////////////////
//	
//	
//	public static DominoStatus getDominoStatus(String status) {
//		switch (status) {
//		case "inPile":
//			return DominoStatus.InPile;
//		case "excluded":
//			return DominoStatus.Excluded;
//		case "inCurrentDraft":
//			return DominoStatus.InCurrentDraft;
//		case "inNextDraft":
//			return DominoStatus.InNextDraft;
//		case "erroneouslyPreplaced":
//			return DominoStatus.ErroneouslyPreplaced;
//		case "correctlyPreplaced":
//			return DominoStatus.CorrectlyPreplaced;
//		case "placedInKingdom":
//			return DominoStatus.PlacedInKingdom;
//		case "discarded":
//			return DominoStatus.Discarded;
//		default:
//			throw new java.lang.IllegalArgumentException("Invalid domino status: " + status);
//		}
//	}
//	
//	public static DirectionKind getDirection(String dir) {
//		switch (dir) {
//		case "up":
//			return DirectionKind.Up;
//		case "down":
//			return DirectionKind.Down;
//		case "left":
//			return DirectionKind.Left;
//		case "right":
//			return DirectionKind.Right;
//		default:
//			throw new java.lang.IllegalArgumentException("Invalid direction: " + dir);
//		}
//	}
//	
//	public static int[] str2int(String str) {
//		String splits []= str.split(",");
//		int [] num = new int[splits.length];
//		for (int i=0;i<splits.length;i++) {
//			num[i]=Integer.parseInt(splits[i]);
//		}
//		return num;
//	}
//	
//
//	public static TerrainType getTerrainTypeByFullString(String terrain) {
//	
//		if (terrain.equalsIgnoreCase("wheat")) return TerrainType.WheatField;
//		if (terrain.equalsIgnoreCase("grass")) return TerrainType.Grass;
//		if (terrain.equalsIgnoreCase("mountain")) return TerrainType.Mountain;
//		if (terrain.equalsIgnoreCase("lake")) return TerrainType.Lake;
//		if (terrain.equalsIgnoreCase("swamp")) return TerrainType.Swamp;
//		if (terrain.equalsIgnoreCase("forest")) return TerrainType.Forest;
//		else throw new IllegalArgumentException();
//
//	}
//
//	public static String getStringByTerrainType(TerrainType terrain) {
//		
//		if (terrain.equals(TerrainType.WheatField)) return "wheat";
//		if (terrain.equals(TerrainType.Swamp)) return "swamp";
//		if (terrain.equals(TerrainType.Forest)) return "forest";
//		if (terrain.equals(TerrainType.Grass)) return "grass";
//		if (terrain.equals(TerrainType.Mountain)) return "mountain";
//		if (terrain.equals(TerrainType.Lake)) return "lake";
//		
//		else throw new IllegalArgumentException();
//	
//	}
//	
//	public static DominoStatus getDominoStatusCapital(String status) {
//		switch (status) {
//		case "InPile":
//			return DominoStatus.InPile;
//		case "Excluded":
//			return DominoStatus.Excluded;
//		case "InCurrentDraft":
//			return DominoStatus.InCurrentDraft;
//		case "InNextDraft":
//			return DominoStatus.InNextDraft;
//		case "ErroneouslyPreplaced":
//			return DominoStatus.ErroneouslyPreplaced;
//		case "CorrectlyPreplaced":
//			return DominoStatus.CorrectlyPreplaced;
//		case "PlacedInKingdom":
//			return DominoStatus.PlacedInKingdom;
//		case "Discarded":
//			return DominoStatus.Discarded;
//		default:
//			throw new java.lang.IllegalArgumentException("Invalid domino status: " + status);
//		}
//	}
//	
//	public static Player getPlayerByColor(String color) {
//		
//		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
//		
//		for(Player player : game.getPlayers()) {
//			
//			if(player.getColor().equals(getColor(color))) return player;
//			
//		}
//		
//		throw new java.lang.IllegalArgumentException("Player with color" + color + " not found.");
//		
//	}
//	
//	public static PlayerColor getColor(String color) {
//		
//		if(color.equalsIgnoreCase("Blue")) return PlayerColor.Blue;
//		if(color.equalsIgnoreCase("Green")) return PlayerColor.Green;
//		if(color.equalsIgnoreCase("Yellow")) return PlayerColor.Yellow;
//		if(color.equalsIgnoreCase("Pink")) return PlayerColor.Pink;
//		
//		throw new java.lang.IllegalArgumentException("No player of this color exists");
//		
//	}
//
//}
