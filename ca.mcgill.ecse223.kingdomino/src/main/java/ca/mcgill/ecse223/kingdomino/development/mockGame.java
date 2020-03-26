package ca.mcgill.ecse223.kingdomino.development;
import java.util.ArrayList;
import java.util.List;

import ca.mcgill.ecse223.kingdomino.controller.*;
import ca.mcgill.ecse223.kingdomino.model.*;
import ca.mcgill.ecse223.kingdomino.persistence.*;

public class mockGame {
	
	public static void main(String [] args) {
		
//		load up an existing kingdomino serialization, if not found, creates a new serialization
		Kingdomino kd = KDController.loadGame();
		
//		provides users to kd
		String usernameA="userA";
		String usernameB="userB";
		String usernameC="userC";
		String usernameD="userD";
		String usernameE="userE";
		String usernameF="userF";
		
		KDController.provideUserProfile(usernameA);
		KDController.provideUserProfile(usernameB);
		KDController.provideUserProfile(usernameC);
		KDController.provideUserProfile(usernameD);
		KDController.provideUserProfile(usernameE);
		KDController.provideUserProfile(usernameF);
		
//		starts a new game
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
		
//		System.out.println(kd.getCurrentGame().getTopDominoInPile().getId());
//		System.out.println("--------");
//		for (Domino d:kd.getCurrentGame().getAllDominos()) System.out.println(d.getId());
		
//		optionally assigns the players in new game to existing users in kd
		KDController.assignPlayerToUser(kd.getCurrentGame().getPlayer(0), kd.getUser(0));
		KDController.assignPlayerToUser(kd.getCurrentGame().getPlayer(1), kd.getUser(1));
		KDController.assignPlayerToUser(kd.getCurrentGame().getPlayer(2), kd.getUser(2));
		KDController.assignPlayerToUser(kd.getCurrentGame().getPlayer(3), kd.getUser(3));
	
		
		
		
//		serializes kd
		KDController.saveGame();
	}

}
