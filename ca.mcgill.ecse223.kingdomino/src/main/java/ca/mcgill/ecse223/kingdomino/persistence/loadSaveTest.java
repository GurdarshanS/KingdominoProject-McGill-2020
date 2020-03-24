package ca.mcgill.ecse223.kingdomino.persistence;
import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.*;
import ca.mcgill.ecse223.kingdomino.model.*;

public class loadSaveTest {
	public static void main (String [] args) {

//	String fileName="falseTrail.txt";
//	Kingdomino kd = KDController.loadGame(fileName);
//	System.out.println(kd);
		
		KDController.initiateEmptyGame();
		Kingdomino kd = KingdominoApplication.getKingdomino();
		
//		boolean overwrite=false;
//		boolean gameSaved=KDController.saveGame(overwrite);
//		System.out.println(gameSaved);
		
		boolean overwrite=true;
		String fileName="non_default_save.data";
		boolean gameSaved=KDController.saveGame(fileName,overwrite);
		System.out.println(gameSaved);
	
		
	}
}
