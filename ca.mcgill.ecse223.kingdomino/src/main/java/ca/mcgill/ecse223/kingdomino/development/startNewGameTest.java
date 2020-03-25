package ca.mcgill.ecse223.kingdomino.development;
import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.KDController;
import ca.mcgill.ecse223.kingdomino.controller.KDController.InvalidInputException;
import ca.mcgill.ecse223.kingdomino.model.*;
import java.util.*;

public class startNewGameTest {
	public static void main(String [] args) {
		
		Kingdomino kd = KDController.loadGame();
		
		List<BonusOption> selectedBonusOptions = new ArrayList<BonusOption>();

		int numPlayers=4;
		
		BonusOption mk = new BonusOption("Middle Kingdom",kd);
		BonusOption hm = new BonusOption("Harmony",kd);
		
		selectedBonusOptions.add(mk);
		selectedBonusOptions.add(hm);
		
		try{
			KDController.setGameOptions(numPlayers, selectedBonusOptions);
			KDController.startANewGame();
		}
		catch(KDController.InvalidInputException e) {
			e.printStackTrace();
		}
		
		KDController.assignPlayerToUser(kd.getCurrentGame().getPlayer(0), kd.getUser(0));
		KDController.assignPlayerToUser(kd.getCurrentGame().getPlayer(1), kd.getUser(1));
		KDController.assignPlayerToUser(kd.getCurrentGame().getPlayer(2), kd.getUser(2));
		KDController.assignPlayerToUser(kd.getCurrentGame().getPlayer(3), kd.getUser(3));
		
		KDController.saveGame();
		
		
			

	}
}
