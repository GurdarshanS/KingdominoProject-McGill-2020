//package ca.mcgill.ecse223.kingdomino.development;
//import java.util.List;
//
//import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
//import ca.mcgill.ecse223.kingdomino.controller.*;
//import ca.mcgill.ecse223.kingdomino.model.*;
//
//public class loadSaveTest {
//	public static void main (String [] args) {
//
////		test 1: no previously default kingdomino serialization exist
////		calling KDController.loadGame() should create a new default kingdomino serialization
//		Kingdomino kd = KDController.loadGame();
//		
//		
////		test 2: previously default kingdomino serialization exist
////		calling KDController.loadGame() should load default kingdomino serialization
////		KDController.loadGame();
//		
//		List<Domino> allDominos=kd.getCurrentGame().getAllDominos();
//		
//		for (Domino d:allDominos) {
//			System.out.println(d.getId()+"-"+d.getLeftTile()+"-"+d.getRightTile());
//		}
//		System.out.println("-------------------------");
//		System.out.println(allDominos.size()+" total dominos");
//		System.out.println("=========================");
//		
//		
//		List<Player> allPlayers=kd.getCurrentGame().getPlayers();
//		for (Player p:allPlayers) {
//			System.out.println(p.getColor()+"- ranking:"+p.getCurrentRanking()+"- "+p.getKingdom());
//		}
//		
//		
//	}
//}
