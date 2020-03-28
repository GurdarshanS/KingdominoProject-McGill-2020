package ca.mcgill.ecse223.kingdomino.development;
import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.*;
import ca.mcgill.ecse223.kingdomino.model.*;
import ca.mcgill.ecse223.kingdomino.persistence.*;
import java.util.*;

public class refactoringTestBench {
	public static void main(String[] args) {
		
		Kingdomino kd = KingdominoApplication.getKingdomino();
		
		
		int numPlayers=4;
		List<String> selectedBonusOptions = new ArrayList<String>();
		
		try {
			KDController.setGameOptions(numPlayers, selectedBonusOptions);
		}
		catch(Exception e) {}
		

		//		all these below can be replaced with KDController.startANewGame();
		Game game = kd.getCurrentGame();
		int dominoNums=game.getMaxPileSize();
		KDController.createDominoPile(game,dominoNums);
		KDController.shuffleDominoPile();
		KDController.generateInitialPlayerOrder();
		View.printPlayers(kd);

		
		//		round 1
		KDController.createNextDraft();
		KDController.sortNextDraft();
		KDController.revealNextDraft();		
		
		KDController.ChoosNextDomino(game.getCurrentDraft().getIdSortedDomino(3));
		KDController.preplaceLatestDomino(game.getPlayer(0), 0, 0, "left");
		
		KDController.ChoosNextDomino(game.getCurrentDraft().getIdSortedDomino(1));
		KDController.preplaceLatestDomino(game.getPlayer(1), 0, 0, "left");
		
		KDController.ChoosNextDomino(game.getCurrentDraft().getIdSortedDomino(2));
		KDController.preplaceLatestDomino(game.getPlayer(2), 0, 0, "left");
		
		KDController.ChoosNextDomino(game.getCurrentDraft().getIdSortedDomino(0));
		KDController.preplaceLatestDomino(game.getPlayer(3), 0, 0, "left");
		
		View.printDraft(kd);
		KDController.updatePlayerOrder();
		View.printPlayers(kd);
		
		
		//		round 2
		KDController.createNextDraft();
		KDController.sortNextDraft();
		KDController.revealNextDraft();
		
		KDController.ChoosNextDomino(game.getCurrentDraft().getIdSortedDomino(1));
		KDController.preplaceLatestDomino(game.getPlayer(0), 0, 0, "left");
		
		KDController.ChoosNextDomino(game.getCurrentDraft().getIdSortedDomino(3));
		KDController.preplaceLatestDomino(game.getPlayer(1), 0, 0, "left");
		
		KDController.ChoosNextDomino(game.getCurrentDraft().getIdSortedDomino(2));
		KDController.preplaceLatestDomino(game.getPlayer(2), 0, 0, "left");
		
		KDController.ChoosNextDomino(game.getCurrentDraft().getIdSortedDomino(0));
		KDController.preplaceLatestDomino(game.getPlayer(3), 0, 0, "left");
		KDController.updatePlayerOrder();
		
		View.printDraft(kd);
		KDController.updatePlayerOrder();
		View.printPlayers(kd);

		//		round 3
		KDController.createNextDraft();
		KDController.sortNextDraft();
		KDController.revealNextDraft();
		
		KDController.ChoosNextDomino(game.getCurrentDraft().getIdSortedDomino(0));
		KDController.preplaceLatestDomino(game.getPlayer(0), 0, 0, "left");
		
		KDController.ChoosNextDomino(game.getCurrentDraft().getIdSortedDomino(2));
		KDController.preplaceLatestDomino(game.getPlayer(1), 0, 0, "left");
		
		KDController.ChoosNextDomino(game.getCurrentDraft().getIdSortedDomino(3));
		KDController.preplaceLatestDomino(game.getPlayer(2), 0, 0, "left");
		
		KDController.ChoosNextDomino(game.getCurrentDraft().getIdSortedDomino(1));
		KDController.preplaceLatestDomino(game.getPlayer(3), 0, 0, "left");
		KDController.updatePlayerOrder();
		
		View.printDraft(kd);
		KDController.updatePlayerOrder();
		View.printPlayers(kd);

		

		
	}
}
