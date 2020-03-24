package ca.mcgill.ecse223.kingdomino.controller;
import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.model.*;
import java.util.*;

public class startNewGameTest {
	public static void main(String [] args) {
		Kingdomino kd = KingdominoApplication.getKingdomino();
		List<BonusOption> selectedBonusOptions = new ArrayList<BonusOption>();

		int numPlayers=3;
		
		BonusOption mk = new BonusOption("Middle Kingdom",kd);
		BonusOption hm = new BonusOption("Harmony",kd);
		
		selectedBonusOptions.add(mk);
		selectedBonusOptions.add(hm);
		
		try{
			KDController.setGameOptions(numPlayers, selectedBonusOptions);
		}
		catch(KDController.InvalidInputException e) {
			e.printStackTrace();
		}
		
		KDController.startANewGame();
		

	}
}
