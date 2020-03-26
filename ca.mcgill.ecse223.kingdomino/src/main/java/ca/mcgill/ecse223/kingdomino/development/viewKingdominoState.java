package ca.mcgill.ecse223.kingdomino.development;

import java.util.List;
import ca.mcgill.ecse223.kingdomino.controller.KDController;
import ca.mcgill.ecse223.kingdomino.model.*;

public class viewKingdominoState {
	

	public static void main (String [] args) {
		
		Kingdomino kd = KDController.loadGame();
		
		printUsers(kd);
		printPlayers(kd);
		
		//===================== round 1 ============================		
		
		//		first player actions
		
		Player player1=kd.getCurrentGame().getPlayer(0);
		KDController.ChoosNextDomino(kd.getCurrentGame().getCurrentDraft().getIdSortedDomino(2));
		KDController.prePlaceDominoDominoSelection(player1, 1, 0, "right");
		KDController.placeLatestDomino(player1);
		
		//		second player actions
		
		Player player2=kd.getCurrentGame().getPlayer(1);
		KDController.ChoosNextDomino(kd.getCurrentGame().getCurrentDraft().getIdSortedDomino(1));
		KDController.prePlaceDominoDominoSelection(player2, 1, 0, "right");
		KDController.placeLatestDomino(player2);
				
		//		third player actions
		
		Player player3=kd.getCurrentGame().getPlayer(2);
		KDController.ChoosNextDomino(kd.getCurrentGame().getCurrentDraft().getIdSortedDomino(0));
		KDController.prePlaceDominoDominoSelection(player3, 1, 0, "right");
		KDController.placeLatestDomino(player3);
		
		//		fourth player actions
		
		Player player4=kd.getCurrentGame().getPlayer(3);
		KDController.ChoosNextDomino(kd.getCurrentGame().getCurrentDraft().getIdSortedDomino(3));
		KDController.prePlaceDominoDominoSelection(player4, 1, 0, "right");
		KDController.placeLatestDomino(player4);

		printDraft(kd);
		printPlayers(kd);
		
		
		//===================== round 2 ============================		
		KDController.createNextDraft();
		
		//		first player actions
		player1=kd.getCurrentGame().getPlayer(0);
		KDController.ChoosNextDomino(kd.getCurrentGame().getCurrentDraft().getIdSortedDomino(2));
		KDController.prePlaceDominoDominoSelection(player1, -1, 0, "left");
		KDController.placeLatestDomino(player1);
		
		//		second player actions
		player2=kd.getCurrentGame().getPlayer(1);
		KDController.ChoosNextDomino(kd.getCurrentGame().getCurrentDraft().getIdSortedDomino(1));
		KDController.prePlaceDominoDominoSelection(player2, -1, 0, "left");
		KDController.placeLatestDomino(player2);
				
		//		third player actions
		player3=kd.getCurrentGame().getPlayer(2);
		KDController.ChoosNextDomino(kd.getCurrentGame().getCurrentDraft().getIdSortedDomino(0));
		KDController.prePlaceDominoDominoSelection(player3, -1, 0, "left");
		KDController.placeLatestDomino(player3);
		
		//		fourth player actions
		player4=kd.getCurrentGame().getPlayer(3);
		KDController.ChoosNextDomino(kd.getCurrentGame().getCurrentDraft().getIdSortedDomino(3));
		KDController.prePlaceDominoDominoSelection(player4, -1, 0, "left");
		KDController.placeLatestDomino(player4);	
		
		//===================== round 3 ============================		
		KDController.createNextDraft();
		
		//		first player actions
		player1=kd.getCurrentGame().getPlayer(0);
		KDController.ChoosNextDomino(kd.getCurrentGame().getCurrentDraft().getIdSortedDomino(2));
		KDController.prePlaceDominoDominoSelection(player1, 0, 1, "left");
		KDController.placeLatestDomino(player1);
		
		//		second player actions
		player2=kd.getCurrentGame().getPlayer(1);
		KDController.ChoosNextDomino(kd.getCurrentGame().getCurrentDraft().getIdSortedDomino(1));
		KDController.prePlaceDominoDominoSelection(player2, 0, 1, "left");
		KDController.placeLatestDomino(player2);
				
		//		third player actions
		player3=kd.getCurrentGame().getPlayer(2);
		KDController.ChoosNextDomino(kd.getCurrentGame().getCurrentDraft().getIdSortedDomino(0));
		KDController.prePlaceDominoDominoSelection(player3, 0, 1, "left");
		KDController.placeLatestDomino(player3);
		
		//		fourth player actions
		player4=kd.getCurrentGame().getPlayer(3);
		KDController.ChoosNextDomino(kd.getCurrentGame().getCurrentDraft().getIdSortedDomino(3));
		KDController.prePlaceDominoDominoSelection(player4, 0, 1, "left");
		KDController.placeLatestDomino(player4);
		
		
		//===================== round 4 ============================		
		KDController.createNextDraft();
		
		//		first player actions
		player1=kd.getCurrentGame().getPlayer(0);
		KDController.ChoosNextDomino(kd.getCurrentGame().getCurrentDraft().getIdSortedDomino(2));
		KDController.prePlaceDominoDominoSelection(player1, 0, -1, "left");
		KDController.placeLatestDomino(player1);
		
		//		second player actions
		player2=kd.getCurrentGame().getPlayer(1);
		KDController.ChoosNextDomino(kd.getCurrentGame().getCurrentDraft().getIdSortedDomino(1));
		KDController.prePlaceDominoDominoSelection(player2, 0, -1, "left");
		KDController.placeLatestDomino(player2);
				
		//		third player actions
		player3=kd.getCurrentGame().getPlayer(2);
		KDController.ChoosNextDomino(kd.getCurrentGame().getCurrentDraft().getIdSortedDomino(0));
		KDController.prePlaceDominoDominoSelection(player3, 0, -1, "left");
		KDController.placeLatestDomino(player3);
		
		//		fourth player actions
		player4=kd.getCurrentGame().getPlayer(3);
		KDController.ChoosNextDomino(kd.getCurrentGame().getCurrentDraft().getIdSortedDomino(3));
		KDController.prePlaceDominoDominoSelection(player4, 0, -1, "left");
		KDController.placeLatestDomino(player4);		

		
		
		//====================== end game =========================	
		KDController.calculatePlayerScore(player1);
		KDController.calculatePlayerScore(player2);
		KDController.calculatePlayerScore(player3);
		KDController.calculatePlayerScore(player4);
		
		KDController.calculatePlayerRanking();
		
		printDraft(kd);
		printDominos(kd);
//		printPlayers(kd);


//	

		
	}
	
	public static void printProperties(Player player) {
		System.out.println("properties:");
		List<Property> playerProperties=player.getKingdom().getProperties();
		for (Property p:playerProperties) {
			String row=String.format("%1$-20s size: %2$-5d crowns: %3$-5d score: %4$-5d",
					p.getPropertyType(),p.getSize(),p.getCrowns(),p.getScore());
			System.out.println(row);
		}
	}
	
	public static void printPlayerKingdom(Player p) {
//		System.out.println("================================== "+p.getColor()+" player kingdom =====================================");
			System.out.println("\n"+KDController.getKingdomVerificationResult(p)+" kingdom\n");
			for (KingdomTerritory territory:p.getKingdom().getTerritories()) {
				String row="none";
				if (territory instanceof Castle) {
					row=String.format("   NoID   %1$-20s  left x: %2$-5d left y: %3$-5d right x: %4$-5d right y: %5$-5d %6$-10s status: %7$-2s",
							"Castle",territory.getX(),territory.getY(),0,0,"none","none");
				}
				else if (territory instanceof DominoInKingdom){
					int[] otherPos=KDController.calculateOtherPos(territory);
					row=String.format("   %1$-5d  %2$-20s  left x: %3$-5d left y: %4$-5d right x: %5$-5d right y: %6$-5d %7$-10s status: %8$-2s",
							((DominoInKingdom) territory).getDomino().getId(),"DominoInKingdom",territory.getX(),territory.getY(),otherPos[0],otherPos[1],((DominoInKingdom) territory).getDirection(),
							((DominoInKingdom) territory).getDomino().getStatus());
				}
				System.out.println(row);
			}
			
			System.out.println("------------------------------------------------------------------------------------------------------------------");
	}
	
	public static void printAllKingdoms(Kingdomino kd) {
		System.out.println("====================================== player kingdoms ====================================");
		for (Player p:kd.getCurrentGame().getPlayers()) {
			System.out.println("\n"+p.getColor());
			System.out.println(KDController.getKingdomVerificationResult(p)+" kingdom\n");
			for (KingdomTerritory territory:p.getKingdom().getTerritories()) {
				String row="none";
				if (territory instanceof Castle) {
					row=String.format("   NoID   %1$-20s  left x: %2$-5d left y: %3$-5d right x: %4$-5d right y: %5$-5d %6$-10s status: %7$-2s",
							"Castle",territory.getX(),territory.getY(),0,0,"none","none");
				}
				else if (territory instanceof DominoInKingdom){
					int[] otherPos=KDController.calculateOtherPos(territory);
					row=String.format("   %1$-5d  %2$-20s  left x: %3$-5d left y: %4$-5d right x: %5$-5d right y: %6$-5d %7$-10s status: %8$-2s\"",
							((DominoInKingdom) territory).getDomino().getId(),"DominoInKingdom",territory.getX(),territory.getY(),otherPos[0],otherPos[1],((DominoInKingdom) territory).getDirection(),
							((DominoInKingdom) territory).getDomino().getStatus());
				}
				System.out.println(row);
			}
		}
	}
	
	public static void printUsers(Kingdomino kd) {
		//		view all users of kingdomino (NOT players)
		System.out.println("==================================== users in kingdomino ==================================");
		for (User user:kd.getUsers()) {
			System.out.println(user.getName());
		}
		System.out.println();
	}
	
	public static void printExistCurrentGame(Kingdomino kd) {
		//		check existence of a current game
		System.out.println("=============== is there a current game ===========");
		System.out.println(kd.hasCurrentGame());
		System.out.println();
	}
	
	public static void printTotalDraftNum(Kingdomino kd) {
		//		check existence of a current game
		System.out.println("=============== total number of drafts ============");
		System.out.println(kd.getCurrentGame().getAllDrafts().size());
		System.out.println();
	}
	
	public static void printPlayers(Kingdomino kd) {
		//		view all players of the current game
		System.out.println("================================ player order and selections ==============================\n\n");
		for (Player p:kd.getCurrentGame().getPlayers()) {
			String row;
			if (p.hasDominoSelection()) {
				row = String.format("%1$-7s ranking: %2$-2s score: %3$-3s username:  %4$-10s selected domino: %5$-10s",
						p.getColor(),p.getCurrentRanking(),p.getTotalScore(),p.getUser().getName(),p.getDominoSelection().getDomino().getId());
			}
			else {
				row = String.format("%1$-7s ranking: %2$-2s score: %3$-3s username:  %4$-10s",
						p.getColor(),p.getCurrentRanking(),p.getTotalScore(),p.getUser().getName());	
			}
			System.out.println(row);
			printProperties(p);
			printPlayerKingdom(p);
		}
		System.out.println();
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
		System.out.println("================================== round #"+(kd.getCurrentGame().getAllDrafts().size()-1)+
				" results =======================================\n");
		
		System.out.println("current draft"+" - status: "+kd.getCurrentGame().getCurrentDraft().getDraftStatus());
		System.out.println("---------------------------------------------------");
		
		List<Domino> allDominos=kd.getCurrentGame().getCurrentDraft().getIdSortedDominos();
		
		String row;
		for (Domino d:allDominos) {
			if (d.getDominoSelection()!=null)
			{
				row = String.format("%1$-3s %2$-15s %3$-15s  %4$-15s  selected: %5$-2s  player: %6$-2s" ,
						d.getId(),d.getLeftTile(),d.getRightTile(),d.getStatus(),d.hasDominoSelection(),d.getDominoSelection().getPlayer().getColor());
			}
			else {
				row = String.format("%1$-3s %2$-15s %3$-15s  %4$-15s  selected: %5$-2s" ,
						d.getId(),d.getLeftTile(),d.getRightTile(),d.getStatus(),d.hasDominoSelection());
			}
			System.out.println(row);
		}
		
		if (kd.getCurrentGame().getNextDraft()!=null) {
			System.out.println("\nnext draft"+" - status: "+kd.getCurrentGame().getNextDraft().getDraftStatus());
			System.out.println("---------------------------------------------------");
			
			List<Domino> allDominos2=kd.getCurrentGame().getNextDraft().getIdSortedDominos();
			for (Domino d:allDominos2) {
				if (d.getDominoSelection()!=null)
				{
					row = String.format("%1$-3s %2$-15s %3$-15s  %4$-15s  selected: %5$-2s  player: %6$-2s" ,
							d.getId(),d.getLeftTile(),d.getRightTile(),d.getStatus(),d.hasDominoSelection(),d.getDominoSelection().getPlayer().getColor());
				}
				else {
					row = String.format("%1$-3s %2$-15s %3$-15s  %4$-15s  selected: %5$-2s" ,
							d.getId(),d.getLeftTile(),d.getRightTile(),d.getStatus(),d.hasDominoSelection());
				}
				System.out.println(row);
			}
			System.out.println("---------------------------------------------------");
		}
		System.out.println("total drafts in game: "+kd.getCurrentGame().getAllDrafts().size());
		System.out.println();
	}
}
