package ca.mcgill.ecse223.kingdomino.controller;

import java.util.List; 
import java.util.*;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.model.Domino;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom;
import ca.mcgill.ecse223.kingdomino.model.KingdomTerritory;
import ca.mcgill.ecse223.kingdomino.model.Player;
import ca.mcgill.ecse223.kingdomino.model.Property;
import ca.mcgill.ecse223.kingdomino.model.TerrainType;

public class HowToUsePropertyIDMethods {
	public static void main (String [] args) {
		KDController.initiateEmptyGame();

//		Populate board with random dominos, beyond the 5x5 just to test all possibilities
		int[] id= {31,20,40,30,24,22,38,23,43,13,1,25,19,36};
		String[] dir= {"right","up","left","up","left","up","right","right","right","down","down","right","right","left"};
		int[] x= {-1,-1,2,3,2,3,1,0,2,-1,-2,-2,-4,-3};
		int[] y= {1,-1,0,0,-1,-2,-2,-3,-3,-2,-2,-4,-4,-2};
		
		Player player=KingdominoApplication.getKingdomino().getCurrentGame().getNextPlayer();

		for (int i=0;i<id.length;i++) {
			Domino dominoToPlace = KDController.getdominoByID(id[i]); 
			KDController.prePlaceDomino(player, dominoToPlace, x[i], y[i], dir[i]);
		}
		
//		call the controller method to identify the properties of this player
		KDController.identifyAllProperty(player);
		
		
		
		
//		call the controller to see all the properties of this player
		
		List<Property> allProp = KDController.getAllProperty(player);
		System.out.println("========= all properties of this player ==========\n");
		for (Property p:allProp) {
			System.out.println(p.getPropertyType());
			List<Domino> dominoInProp = p.getIncludedDominos();
			for (Domino d:dominoInProp) {
				System.out.println(d.getId());
			}
			System.out.println("----------------------------");
		}
		System.out.println("================================================\n");
		
//		call the controller method to sort player properties by terrain type
		List<Property> properties = KDController.getPropertyByTerrain(player, TerrainType.WheatField);
		
//
		System.out.println("========= wheat properties of this player ==========\n");
		for (Property p:properties) {
			System.out.println(p.getPropertyType());
			List<Domino> dominoInProp = p.getIncludedDominos();
			for (Domino d:dominoInProp) {
				System.out.println(d.getId());
			}
			System.out.println("----------------------------");
		}
		System.out.println("================================================\n");
//		
////		call the controller method to compare a property against a TerrainType and expected domino IDs in property
//		
		Property testP = properties.get(0);
		
		System.out.println("========= characteristics of this test property ==========\n");
		System.out.println(testP.getPropertyType());
		List<Domino> dominoInProp = testP.getIncludedDominos();
		for (Domino d:dominoInProp) {
			System.out.println(d.getId());
			}
		System.out.println("================= test against ===============================\n");
		
		TerrainType testTerrain=TerrainType.WheatField;
		int [] testIds= {43,38,22};
		boolean match = KDController.checkPropertyMatch(testTerrain,testIds,testP);
		System.out.println(testTerrain.name());
		System.out.println(Arrays.toString(testIds));
		
		System.out.println("================ test resetult ===========================");
		System.out.println(match);

		
		System.out.println(testP.getCrowns());
		
		System.out.println("\n\n================ using the PropertyAttribute class ===========================");
		List<PropertyAttribute> pa = KDController.getAllPropertyAttributes(player);
		for (PropertyAttribute each:pa) {
			System.out.println(each);
		}
	}
}
