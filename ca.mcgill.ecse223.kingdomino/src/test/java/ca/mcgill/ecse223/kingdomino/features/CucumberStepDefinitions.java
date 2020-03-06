package ca.mcgill.ecse223.kingdomino.features;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.*;

import org.junit.internal.runners.model.EachTestNotifier;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.KDController;
import ca.mcgill.ecse223.kingdomino.model.*;
import ca.mcgill.ecse223.kingdomino.model.Domino.DominoStatus;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom.DirectionKind;
import ca.mcgill.ecse223.kingdomino.model.Player.PlayerColor;
import ca.mcgill.ecse223.kingdomino.controller.PropertyAttribute;
import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import ca.mcgill.ecse223.kingdomino.features.CucumberCleanUp;

public class CucumberStepDefinitions {

	
 
	/**
	 * These methods checks if a player's kingdom respects the maximum 
	 * grid size of 5x5 (regular mode) or 7x7 (mighty kingdom)
	 * @see VerifyGridSize.feature
	 * @author Jing Han 260528152
	 */
	
	
	@Given("the game is initialized for verify grid size")
	public void the_game_is_initialized_for_verify_grid_size() {
		KDController.initiateEmptyGame(); 
	}
	
	@Given("the player's kingdom has the following dominoes:")
	public void the_player_s_kingdom_has_the_following_dominoes(io.cucumber.datatable.DataTable dataTable) {
		Player player = KingdominoApplication.getKingdomino().getCurrentGame().getNextPlayer();
		
		List<Map<String, String>> valueMaps = dataTable.asMaps();
		for (Map<String, String> map : valueMaps) {
			// Get values from cucumber table
			Integer id = Integer.decode(map.get("id"));
			String dir = map.get("dominodir");
			Integer posx = Integer.decode(map.get("posx"));
			Integer posy = Integer.decode(map.get("posy"));

			// Add the domino to a player's kingdom
			Domino dominoToPlace = getdominoByID(id); 
			KDController.prePlaceDomino(player, dominoToPlace, posx, posy, dir);
		}
	}
	
	@When("validation of the grid size is initiated")
	public void initiate_player_kingdom_grid_size_validation() {
		KDController.verifyGridSizeAllKingdom(KingdominoApplication.getKingdomino().getCurrentGame().getNextPlayer());
	}
	
	@Then("the grid size of the player's kingdom shall be {string}")
	public void get_kingdom_gridsize_validation_result(String expectedValidity) {
		String validity=KDController.getKingdomVerificationResult(KingdominoApplication.getKingdomino().getCurrentGame().getNextPlayer());
		assertEquals(expectedValidity,validity);
	}
	
	@Given("the  player preplaces domino {int} to their kingdom at position {int}:{int} with direction {string}")
	public void the_player_preplaces_these_dominos_in_kingdom(Integer id, Integer posx, Integer posy, String dir) {
		Player player = KingdominoApplication.getKingdomino().getCurrentGame().getNextPlayer();
		Domino dominoToPlace = getdominoByID(id);
		KDController.prePlaceDomino(player, dominoToPlace, posx, posy, dir);
	}

	@After
	public void tearDown() {
		Kingdomino kingdomino = KingdominoApplication.getKingdomino();
		if (kingdomino != null) {
			kingdomino.delete();
		}
	}
	
	/**
	 * These methods checks if dominos overlap
	 * @see VerifyNoOverlapping.feature
	 * @author Jing Han 260528152
	 */
	
	@Given("the game is initialized to check domino overlapping")
	public void the_game_is_initialized_to_check_domino_overlapping() {
		KDController.initiateEmptyGame();
	}

	@Given("the following dominoes are present in a player's kingdom:")
	public void the_following_dominoes_are_present_in_a_player_kingdom(io.cucumber.datatable.DataTable dataTable) {
		Player player = KingdominoApplication.getKingdomino().getCurrentGame().getNextPlayer();
		
		List<Map<String, String>> valueMaps = dataTable.asMaps();
		for (Map<String, String> map : valueMaps) {
			// Get values from cucumber table
			Integer id = Integer.decode(map.get("id"));
			String dir = map.get("dominodir");
			Integer posx = Integer.decode(map.get("posx"));
			Integer posy = Integer.decode(map.get("posy"));

			// Add the domino to a player's kingdom
			Domino dominoToPlace = getdominoByID(id); 
			KDController.prePlaceDomino(player, dominoToPlace, posx, posy, dir);
			dominoToPlace.setStatus(DominoStatus.PlacedInKingdom);
		}
	}

	@Given("the current player preplaced the domino with ID {int} at position {int}:{int} and direction {string}")
	public void the_current_player_preplaced_domino(Integer id, Integer posx, Integer posy, String dir) {
		Player player = KingdominoApplication.getKingdomino().getCurrentGame().getNextPlayer();
		Domino dominoToPlace = getdominoByID(id);
		KDController.prePlaceDomino(player, dominoToPlace, posx, posy, dir);
	}

	@When("check current preplaced domino overlapping is initiated")
	public void initiate_domino_overlap_verification() {
		KDController.verifyNoOverlapLastTerritory(KingdominoApplication.getKingdomino().getCurrentGame().getNextPlayer());
	}

	@Then("the current-domino\\/existing-domino overlapping is {string}")
	public void get_domino_overlap_verification_result(String expectedValidity) {
		String validity=KDController.getKingdomVerificationResult(KingdominoApplication.getKingdomino().getCurrentGame().getNextPlayer());
		assertEquals(expectedValidity,validity);
	}
	
	
	/**
	 * These methods checks if current domino 
	 * is adjacent to the caslte
	 * @see VerifyCastleAdjacency.feature
	 * @author Jing Han 260528152
	 */
	
	@Given("the game is initialized for castle adjacency")
	public static void initialize_game_for_castle_adjacency() {
		KDController.initiateEmptyGame();
	}
	
	@When("check castle adjacency is initiated")
	public static void initiate_castle_adjacency_check() {
		KDController.verifyCastleAdjacency(KingdominoApplication.getKingdomino().getCurrentGame().getNextPlayer());
	}

	@Then("the castle\\/domino adjacency is {string}")
	public void get_castle_adjacency_verification_result(String expectedValidity) {
		String validity=KDController.getKingdomVerificationResult(KingdominoApplication.getKingdomino().getCurrentGame().getNextPlayer());
		assertEquals(expectedValidity,validity);
	}
	
	
	/**
	 * These methods checks if current domino 
	 * has at least one adjacent tile with same terrain type
	 * @see VerifyNeighboAdjacency.feature
	 * @author Jing Han 260528152
	 */
	
	@When("the game is initialized for neighbor adjacency")
	public static void initialize_game_for_neighbor_adjacency() {
		KDController.initiateEmptyGame();
	}
	
	@When("check current preplaced domino adjacency is initiated")
	public static void initialize_neighbor_adjacency_verification() {
		KDController.verifyNeighborAdjacencyLastTerritory(KingdominoApplication.getKingdomino().getCurrentGame().getNextPlayer());
	}
	
	@Then("the current-domino\\/existing-domino adjacency is {string}")
	public static void get_neighbor_adjacency_verification_result(String expectedValidity) {
		String validity=KDController.getKingdomVerificationResult(KingdominoApplication.getKingdomino().getCurrentGame().getNextPlayer());
		assertEquals(expectedValidity,validity);
	}
	
	/**
	 * These methods identifies the properties of a 
	 * player's kingdom
	 * @see IdentifyProperties.feature
	 * @author Jing Han 260528152
	 */
	
	@Given("the game is initialized for identify properties")
	public static void initialize_game_for_id_property() {
		KDController.initiateEmptyGame();
	}
	
	@When("the properties of the player are identified")
	public static void when_properties_are_identified() {
		Player player = KingdominoApplication.getKingdomino().getCurrentGame().getNextPlayer();
		KDController.identifyAllProperty(player);
	}

	@Then("the player shall have the following properties:")
	public static void the_kingdom_shall_have_following_properties(io.cucumber.datatable.DataTable dataTable) {
		
		boolean match=true;
		
		List<Map<String, String>> valueMaps = dataTable.asMaps();
		List<int[]> expectedIds = new ArrayList<int[]>();
		List<String> expectedTerrains = new ArrayList<String>();
		for (Map<String, String> map : valueMaps) {
			// Get values from cucumber table
			String propType = map.get("type");
			String propIds = map.get("dominoes");
			int [] numIds=str2int(propIds);
			
			expectedIds.add(numIds);
			expectedTerrains.add(propType);
		}
		
		Player player=KingdominoApplication.getKingdomino().getCurrentGame().getNextPlayer();
		List<Property> allProperty = KDController.getAllProperty(player);
		
		if (allProperty.size()!=expectedTerrains.size()) {
			match=false;
		}
		else {	
			for (int i=0;i<expectedIds.size();i++) {
				match=true;
				int num_match=0;
				TerrainType testTerrain=getTerrainTypeByFullString(expectedTerrains.get(i));
				int[] testIds=expectedIds.get(i);
				
				for (int j=0;j<allProperty.size();j++) {
					Property testProp=allProperty.get(j);
					if (KDController.checkPropertyMatch(testTerrain, testIds, testProp)) {
						num_match+=1;
					}
				}
				if (num_match!=1) {
					match=false;
					break;
				}
			}
		}
		
		assertEquals(true,match);
		
	}
	
	
//	Player player = KingdominoApplication.getKingdomino().getCurrentGame().getNextPlayer();
	/**
	 * These methods calculates Propety Attributes
	 * @see CalculatePropertyAttributes.feature
	 * @author Jing Han 260528152
	 */
	@Given("the game is initialized for calculate property attributes")
	public static void game_initialized_for_calculate_property_attributes() {
		KDController.initiateEmptyGame();
	}
	
	@When("calculate property attributes is initiated")
	public static void initiate_calculate_property() {
		Player player = KingdominoApplication.getKingdomino().getCurrentGame().getNextPlayer();
		KDController.identifyAllProperty(player);
	}
	
	@Then("the player shall have a total of {int} properties")
	public static void the_player_shall_have_x_number_properties(Integer expectedPropertyNum) {
		Player player = KingdominoApplication.getKingdomino().getCurrentGame().getNextPlayer();
		List<Property> p = player.getKingdom().getProperties();
		int actualNum=p.size();
		assertEquals(expectedPropertyNum.intValue(),actualNum);
	}
	
	@Then("the player shall have properties with the following attributes:")
	public static void player_has_properties_with_following_attributes(io.cucumber.datatable.DataTable dataTable) {
		
		Player player = KingdominoApplication.getKingdomino().getCurrentGame().getNextPlayer();
		List<PropertyAttribute> pa = KDController.getAllPropertyAttributes(player);
		
//		for (PropertyAttribute each:pa) {
//			System.out.println(each);
//		}
//		
		List<Map<String, String>> valueMaps = dataTable.asMaps();
		
		List<Integer> expectedSize = new ArrayList<Integer>();
		List<Integer> expectedCrown = new ArrayList<Integer>();
		List<String> expectedTerrains = new ArrayList<String>();
	
		
		for (Map<String, String> map : valueMaps) {
			// Get values from cucumber table
			String propType = map.get("type");
			int propSize = Integer.parseInt(map.get("size"));
			int propCrown= Integer.parseInt(map.get("crowns"));
			
			expectedTerrains.add(propType);
			expectedCrown.add(propCrown);
			expectedSize.add(propSize);
		}
		
//		System.out.println(expectedTerrains);
//		System.out.println(expectedSize);
//		System.out.println(expectedCrown);
		
		if (expectedSize.size()!=pa.size()) {
			assertEquals(true,false);
		}
		
		else {
			
			List<Integer> ignoreIndex=new ArrayList<Integer>();
			for (int i=0;i<expectedSize.size();i++) {
				
					int currentSize=expectedSize.get(i);
					int currentCrown=expectedCrown.get(i);
					String currentTerrain=expectedTerrains.get(i);
					
					int num_match=0;
					
					for (int j=0;j<pa.size();j++) {
						if (!ignoreIndex.contains(j)) {
							int testSize=pa.get(j).getSize();
							int testCrown=pa.get(j).getCrown();
							String testTerrain=getStringByTerrainType(pa.get(j).getTerrain());
							System.out.println("expected index: "+ i+" --- terrain: "+currentTerrain+" --- size: "+currentSize+" --- crown: "+currentCrown);
							System.out.println("test     index: "+j+" --- terrain: "+testTerrain+" --- size: "+testSize+" --- crown: "+testCrown);
							if (currentTerrain.equalsIgnoreCase(testTerrain) && testSize==currentSize && testCrown==currentCrown) {
								num_match++;
//								ignoreIndex.add(i);
								ignoreIndex.add(j);
								System.out.println("match!");
								break;
							}
						}
					}
					if (num_match!=1) assertEquals(true,false);
					
					System.out.println("===========================================");
							
			}
			assertEquals(true,true);

		}
	}
			
			
			
			
//		assertEquals(1,1);
//		
		
	
	
	///////////////////////////////////////
	/// -----Private Helper Methods---- ///
	///////////////////////////////////////
	
	private static int[] str2int(String str) {
		String splits []= str.split(",");
		int [] num = new int[splits.length];
		for (int i=0;i<splits.length;i++) {
			num[i]=Integer.parseInt(splits[i]);
		}
		return num;
	}
	
	private static Domino getdominoByID(int id) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		for (Domino domino : game.getAllDominos()) {
			if (domino.getId() == id) {
				return domino;
			}
		}
		throw new java.lang.IllegalArgumentException("Domino with ID " + id + " not found.");
	}
	
	private static TerrainType getTerrainTypeByFullString(String terrain) {
		
		if (terrain.equalsIgnoreCase("wheat")) return TerrainType.WheatField;
		if (terrain.equalsIgnoreCase("grass")) return TerrainType.Grass;
		if (terrain.equalsIgnoreCase("mountain")) return TerrainType.Mountain;
		if (terrain.equalsIgnoreCase("lake")) return TerrainType.Lake;
		if (terrain.equalsIgnoreCase("swamp")) return TerrainType.Swamp;
		if (terrain.equalsIgnoreCase("forest")) return TerrainType.Forest;
		else throw new IllegalArgumentException();

		}
	
	private static String getStringByTerrainType(TerrainType terrain) {
		
		if (terrain.equals(TerrainType.WheatField)) return "wheat";
		if (terrain.equals(TerrainType.Swamp)) return "swamp";
		if (terrain.equals(TerrainType.Forest)) return "forest";
		if (terrain.equals(TerrainType.Grass)) return "grass";
		if (terrain.equals(TerrainType.Mountain)) return "mountain";
		if (terrain.equals(TerrainType.Lake)) return "lake";
		
		else throw new IllegalArgumentException();

		}
	}
	

