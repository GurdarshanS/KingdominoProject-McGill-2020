package ca.mcgill.ecse223.kingdomino.development;

import java.util.List;
import ca.mcgill.ecse223.kingdomino.controller.KDController;
import ca.mcgill.ecse223.kingdomino.model.*;

public class viewKingdominoState {
	

	public static void main (String [] args) {
		
		Kingdomino kd = KDController.loadGame();

		printUsers(kd);
		printExistCurrentGame(kd);
		
		printDraft(kd);
				
		KDController.createNextDraft(); 		//3 drafts
		printDraft(kd);
		
		KDController.createNextDraft(); 		//4 drafts
		printDraft(kd);
		
		KDController.createNextDraft(); 		//5 drafts
		printDraft(kd);
		
		KDController.createNextDraft(); 		//6 drafts
		printDraft(kd);
		
		KDController.createNextDraft(); 		//7 drafts
		printDraft(kd);
		
		printTotalDraftNum(kd);
		printDominos(kd);
		printSortedDominos(kd);
		printPlayers(kd);
	}
	
	public static void printUsers(Kingdomino kd) {
		//		view all users of kingdomino (NOT players)
		System.out.println("================= users in kingdomino =============");
		for (User user:kd.getUsers()) {
			System.out.println(user.getName());
		}
	}
	
	public static void printExistCurrentGame(Kingdomino kd) {
		//		check existence of a current game
		System.out.println("=============== is there a current game ===========");
		System.out.println(kd.hasCurrentGame());
	}
	
	public static void printTotalDraftNum(Kingdomino kd) {
		//		check existence of a current game
		System.out.println("=============== total number of drafts ============");
		System.out.println(kd.getCurrentGame().getAllDrafts().size());
		System.out.println();
	}
	
	public static void printPlayers(Kingdomino kd) {
		//		view all players of the current game
		System.out.println("=============== players in current game ===========");
		for (Player p:kd.getCurrentGame().getPlayers()) {
			
			String row = String.format("%1$-7s ranking: %2$-2s score: %3$-3s username:  %4$-10s",
					p.getColor(),p.getCurrentRanking(),p.getTotalScore(),p.getUser().getName());
			
			System.out.println(row);
		}
	}
	
	public static void printDominos(Kingdomino kd) {
		//		view dominos in current game
		System.out.println("=============== dominos in current game ===========");
		List<Domino> allDominos=kd.getCurrentGame().getAllDominos();
		for (Domino d:allDominos) {
			String row = String.format("%1$-3s %2$-15s %3$-15s  %4$-10s",
					d.getId(),d.getLeftTile(),d.getRightTile(),d.getStatus());
			System.out.println(row);
		}
		System.out.println("-------------------------");
		System.out.println(allDominos.size()+" total dominos");	
	}
	
	public static void printSortedDominos(Kingdomino kd) {
		//		view dominos in current game
		System.out.println("=========== sorted dominos in current game ===========");
		List<Domino> allDominos=KDController.browseDominos();
		for (Domino d:allDominos) {
			String row = String.format("%1$-3s %2$-15s %3$-15s  %4$-10s",
					d.getId(),d.getLeftTile(),d.getRightTile(),d.getStatus());
			System.out.println(row);
		}
		System.out.println("-------------------------");
		System.out.println(allDominos.size()+" total dominos");	
	}
	
	public static void printDraft(Kingdomino kd) {
		//		view dominos in current game
		System.out.println("============= round #"+(kd.getCurrentGame().getAllDrafts().size()-1)+" in current game ============");
		
		System.out.println("current draft"+" - status: "+kd.getCurrentGame().getCurrentDraft().getDraftStatus());
		System.out.println("---------------------------------------------------");
		
		List<Domino> allDominos=kd.getCurrentGame().getCurrentDraft().getIdSortedDominos();
		for (Domino d:allDominos) {
			String row = String.format("%1$-3s %2$-15s %3$-15s  %4$-10s",
					d.getId(),d.getLeftTile(),d.getRightTile(),d.getStatus());
			System.out.println(row);
		}
		
		if (kd.getCurrentGame().getNextDraft()!=null) {
			System.out.println("\nnext draft"+" - status: "+kd.getCurrentGame().getNextDraft().getDraftStatus());
			System.out.println("---------------------------------------------------");
			
			List<Domino> allDominos2=kd.getCurrentGame().getNextDraft().getIdSortedDominos();
			for (Domino d:allDominos2) {
				String row = String.format("%1$-3s %2$-15s %3$-15s  %4$-10s",
						d.getId(),d.getLeftTile(),d.getRightTile(),d.getStatus());
				System.out.println(row);
			}
			System.out.println("---------------------------------------------------");
		}
		System.out.println("total drafts in game: "+kd.getCurrentGame().getAllDrafts().size());
		System.out.println();
	}
}
