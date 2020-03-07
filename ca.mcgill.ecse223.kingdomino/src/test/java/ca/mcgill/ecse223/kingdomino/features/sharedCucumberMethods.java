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

public class sharedCucumberMethods {
	
	@Given("the current player preplaced the domino with ID {int} at position {int}:{int} and direction {string}")
	public void the_current_player_preplaced_domino(Integer id, Integer posx, Integer posy, String dir) {
		Player player = KingdominoApplication.getKingdomino().getCurrentGame().getNextPlayer();
		Domino dominoToPlace = KDController.getdominoByID(id);
		KDController.prePlaceDomino(player, dominoToPlace, posx, posy, dir);
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
			Domino dominoToPlace = KDController.getdominoByID(id); 
			KDController.prePlaceDomino(player, dominoToPlace, posx, posy, dir);
			dominoToPlace.setStatus(DominoStatus.PlacedInKingdom);
		}
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
			Domino dominoToPlace = KDController.getdominoByID(id); 
			KDController.prePlaceDomino(player, dominoToPlace, posx, posy, dir);
		}
	}

	
	
	
		/////////////////////////////
		// shared helper methods   //
		// across multiple steps   //
		////////////////////////////
	
	
	
	
	public static int[] str2int(String str) {
		String splits []= str.split(",");
		int [] num = new int[splits.length];
		for (int i=0;i<splits.length;i++) {
			num[i]=Integer.parseInt(splits[i]);
		}
		return num;
	}
	

	public static TerrainType getTerrainTypeByFullString(String terrain) {
	
		if (terrain.equalsIgnoreCase("wheat")) return TerrainType.WheatField;
		if (terrain.equalsIgnoreCase("grass")) return TerrainType.Grass;
		if (terrain.equalsIgnoreCase("mountain")) return TerrainType.Mountain;
		if (terrain.equalsIgnoreCase("lake")) return TerrainType.Lake;
		if (terrain.equalsIgnoreCase("swamp")) return TerrainType.Swamp;
		if (terrain.equalsIgnoreCase("forest")) return TerrainType.Forest;
		else throw new IllegalArgumentException();

	}

	public static String getStringByTerrainType(TerrainType terrain) {
		
		if (terrain.equals(TerrainType.WheatField)) return "wheat";
		if (terrain.equals(TerrainType.Swamp)) return "swamp";
		if (terrain.equals(TerrainType.Forest)) return "forest";
		if (terrain.equals(TerrainType.Grass)) return "grass";
		if (terrain.equals(TerrainType.Mountain)) return "mountain";
		if (terrain.equals(TerrainType.Lake)) return "lake";
		
		else throw new IllegalArgumentException();
	
	}
}
