//package ca.mcgill.ecse223.kingdomino.development;
//import java.util.*;
//import ca.mcgill.ecse223.kingdomino.model.*;
//import ca.mcgill.ecse223.kingdomino.controller.KDController;
//
//public class browseDominoTest {
//	
//	public static void main (String [] args) {
//		Kingdomino kd =KDController.loadGame();
//		Game game = kd.getCurrentGame();
//		List<Domino> allDominos = game.getAllDominos();
//		List<Domino> sortedDominos = new ArrayList<Domino>();
//		
//		List<Integer> ignoreVal = new ArrayList<Integer>();
//		
//		for (int i=0;i<allDominos.size();i++) {
//			int min=100;
//			int minIndex=-1;
//			for (int j=0;j<allDominos.size();j++) {
//				Domino d = allDominos.get(j);
//				if (!ignoreVal.contains(d.getId())) {
//					if (d.getId()<min) {
//						min=d.getId();
//						minIndex=j;
//					}
//				}
//			}
//			sortedDominos.add(allDominos.get(minIndex));
//			ignoreVal.add(min);
//		}
//		
//		for (Domino d:sortedDominos) {
//			System.out.println(d.getId()+"_"+d.getLeftTile()+"_"+d.getRightTile());
//		}
//		
//		
//		
//		
//
//		
//		
//	}
//
//}
