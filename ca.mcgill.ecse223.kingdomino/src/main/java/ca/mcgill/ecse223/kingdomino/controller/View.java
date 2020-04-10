package ca.mcgill.ecse223.kingdomino.controller;

import java.util.List;

import ca.mcgill.ecse223.kingdomino.model.Castle;
import ca.mcgill.ecse223.kingdomino.model.Domino;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom;
import ca.mcgill.ecse223.kingdomino.model.KingdomTerritory;
import ca.mcgill.ecse223.kingdomino.model.Kingdomino;
import ca.mcgill.ecse223.kingdomino.model.Player;
import ca.mcgill.ecse223.kingdomino.model.Property;
import ca.mcgill.ecse223.kingdomino.model.User;

public class View {
	/**
	 * 
	 * Simple wrapper class to facilitate console printout of information for debugging and textual simulating
	 * 
	 * @see - no direct features, but associated with identifyProperties
	 * @author Jing Han 260528152
	 */
	
	public static void printNextRoundPlayerOrder(Kingdomino kd) {
		System.out.println("======== next round player ordering =========");
		if (!kd.getCurrentGame().getPlayers().isEmpty())
		{
			for (Player p:kd.getCurrentGame().getPlayers()) {
				System.out.println(p.getColor());
			}
		}
		else {
			System.out.println("player order not set");
		}
	}
	
	public static void printRankings(Kingdomino kd) {
		System.out.println("================================================ current rankings ====================================================");
			for (Player p:kd.getCurrentGame().getPlayers()) {
				int[] sizeCrown=KDQuery.playerMaxPropSizeAndNumCrown(p);
				String row=String.format("%1$-10s  rank: %2$-5d score: %3$-5d size: %4$-5d crown: %5$-5d",
							p.getColor(),p.getCurrentRanking(),p.getTotalScore(),sizeCrown[0],sizeCrown[1]);
				System.out.println(row);
			}
			
			System.out.println("------------------------------------------------------------------------------------------------------------------");
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
			System.out.println("\n"+KDQuery.getKingdomVerificationResult(p)+" kingdom\n");
			for (KingdomTerritory territory:p.getKingdom().getTerritories()) {
				String row="none";
				if (territory instanceof Castle) {
					row=String.format("   NoID   %1$-20s  left x: %2$-5d left y: %3$-5d right x: %4$-5d right y: %5$-5d %6$-10s status: %7$-2s",
							"Castle",territory.getX(),territory.getY(),0,0,"none","none");
				}
				else if (territory instanceof DominoInKingdom){
					int[] otherPos=KDQuery.calculateRightPos(territory);
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
			System.out.println(KDQuery.getKingdomVerificationResult(p)+" kingdom\n");
			for (KingdomTerritory territory:p.getKingdom().getTerritories()) {
				String row="none";
				if (territory instanceof Castle) {
					row=String.format("   NoID   %1$-20s  left x: %2$-5d left y: %3$-5d right x: %4$-5d right y: %5$-5d %6$-10s status: %7$-2s",
							"Castle",territory.getX(),territory.getY(),0,0,"none","none");
				}
				else if (territory instanceof DominoInKingdom){
					int[] otherPos=KDQuery.calculateRightPos(territory);
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
				if (p.hasUser()) {
					row = String.format("%1$-7s ranking: %2$-2s score: %3$-3s username:  %4$-10s selected domino: %5$-10s",
						p.getColor(),p.getCurrentRanking(),p.getTotalScore(),p.getUser().getName(),p.getDominoSelection().getDomino().getId());
				}
				else {
					row = String.format("%1$-7s ranking: %2$-2s score: %3$-3s username:  %4$-10s selected domino: %5$-10s",
							p.getColor(),p.getCurrentRanking(),p.getTotalScore(),"not assigned",p.getDominoSelection().getDomino().getId());
				}
			}
			else {
				if (p.hasUser())
				{
					row = String.format("%1$-7s ranking: %2$-2s score: %3$-3s username:  %4$-10s",
							p.getColor(),p.getCurrentRanking(),p.getTotalScore(),p.getUser().getName());
				}
				else {
					row = String.format("%1$-7s ranking: %2$-2s score: %3$-3s username:  %4$-10s",
							p.getColor(),p.getCurrentRanking(),p.getTotalScore(),"no user assigned");
				}
			}
			System.out.println(row);
//			printProperties(p);
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
		System.out.println("\n================== round #"+(kd.getCurrentGame().getAllDrafts().size()-1)+
				" results ================\n");
		
		System.out.println("current draft"+" - status: "+kd.getCurrentGame().getCurrentDraft().getDraftStatus());
		System.out.println("---------------------------------------------------");
		
		List<Domino> allDominos=kd.getCurrentGame().getCurrentDraft().getIdSortedDominos();
		
		String row;
		for (Domino d:allDominos) {
			if (d.getDominoSelection()!=null)
			{
				row = String.format("%1$-3s %2$-15s %3$-15s  %4$-25s  selected: %5$-2s  player: %6$-2s" ,
						d.getId(),d.getLeftTile(),d.getRightTile(),d.getStatus(),d.hasDominoSelection(),d.getDominoSelection().getPlayer().getColor());
			}
			else {
				row = String.format("%1$-3s %2$-15s %3$-15s  %4$-25s  selected: %5$-2s" ,
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
					row = String.format("%1$-3s %2$-15s %3$-15s  %4$-25s  selected: %5$-2s  player: %6$-2s" ,
							d.getId(),d.getLeftTile(),d.getRightTile(),d.getStatus(),d.hasDominoSelection(),d.getDominoSelection().getPlayer().getColor());
				}
				else {
					row = String.format("%1$-3s %2$-15s %3$-15s  %4$-25s  selected: %5$-2s" ,
							d.getId(),d.getLeftTile(),d.getRightTile(),d.getStatus(),d.hasDominoSelection());
				}
				System.out.println(row);
			}
			System.out.println("---------------------------------------------------");
		}
		System.out.println("---------------------------------------------------");
		System.out.println("total drafts in game: "+kd.getCurrentGame().getAllDrafts().size());
		System.out.println();
	}

}
