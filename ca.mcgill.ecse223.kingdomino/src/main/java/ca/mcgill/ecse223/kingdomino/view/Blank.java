package ca.mcgill.ecse223.kingdomino.view;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.KDController;
import ca.mcgill.ecse223.kingdomino.controller.View;

public class Blank {

	public static void main(String[] shit){
		
		KDController.loadGame("almostEndGame.data");
		View.printPlayers(KingdominoApplication.getKingdomino());
		View.printRankings(KingdominoApplication.getKingdomino());
		
	}
}
