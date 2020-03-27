package ca.mcgill.ecse223.kingdomino.development;
import ca.mcgill.ecse223.kingdomino.controller.*;
import ca.mcgill.ecse223.kingdomino.*;
import ca.mcgill.ecse223.kingdomino.model.*;
import ca.mcgill.ecse223.kingdomino.model.Domino.DominoStatus;
import ca.mcgill.ecse223.kingdomino.model.Draft.DraftStatus;

import java.util.*;

public class discardTest {
	
	public static void main(String [] args) {
		List<String> bonusOptions = new ArrayList<String>();
		try {KDController.setGameOptions(4, bonusOptions);}
		catch(Exception e) {}
		KDController.startANewGame();
		
		Kingdomino kd=KingdominoApplication.getKingdomino();
		Game game = kd.getCurrentGame();
		
		
		Player player1 = kd.getCurrentGame().getNextPlayer();
		
		KDController.preplaceArbitraryDomino(player1, KDController.getdominoByID(6),  1,  0, "right");
		KDController.getdominoByID(6).setStatus(DominoStatus.PlacedInKingdom);
		
		KDController.preplaceArbitraryDomino(player1, KDController.getdominoByID(23), -1, 0, "left");
		KDController.getdominoByID(23).setStatus(DominoStatus.PlacedInKingdom);
		
		KDController.preplaceArbitraryDomino(player1, KDController.getdominoByID(13), 0,  1, "left");
		KDController.getdominoByID(13).setStatus(DominoStatus.PlacedInKingdom);
		
		KDController.preplaceArbitraryDomino(player1, KDController.getdominoByID(4),  0,  -1, "left");
		KDController.getdominoByID(4).setStatus(DominoStatus.PlacedInKingdom);
		
		KDController.preplaceArbitraryDomino(player1, KDController.getdominoByID(21), 0,  3, "down");
		KDController.getdominoByID(21).setStatus(DominoStatus.PlacedInKingdom);
		
		KDController.preplaceArbitraryDomino(player1, KDController.getdominoByID(7),  1,  1, "right");
		KDController.getdominoByID(7).setStatus(DominoStatus.PlacedInKingdom);
		
		KDController.preplaceArbitraryDomino(player1, KDController.getdominoByID(5),  -1, 2, "left");
		KDController.getdominoByID(5).setStatus(DominoStatus.PlacedInKingdom);
		
		KDController.preplaceArbitraryDomino(player1, KDController.getdominoByID(35), 2,  3, "down");
		KDController.getdominoByID(35).setStatus(DominoStatus.PlacedInKingdom);

		printPlayerKingdom(player1);
		
		Draft draft = new Draft(DraftStatus.Sorted,kd.getCurrentGame());
		draft.addIdSortedDomino(KDController.getdominoByID(47));
		kd.getCurrentGame().setCurrentDraft(draft);
		
		Domino testDomino=game.getCurrentDraft().getIdSortedDomino(0);
		
		KDController.ChoosNextDomino(testDomino);
		KDController.preplaceLatestDomino(player1, 0, 0, "left");
		
		boolean discarded=KDController.discardLatestDomino(player1);
		System.out.println(discarded);
		
		printPlayerKingdom(player1);

		
		
		
		
	} 
		
	
	


		
		
		
	
	public static void printPlayerKingdom(Player player) {
		System.out.println("\n======================== player kingdom =====================");
		for (KingdomTerritory territory:player.getKingdom().getTerritories()) {
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
	}

	
}
