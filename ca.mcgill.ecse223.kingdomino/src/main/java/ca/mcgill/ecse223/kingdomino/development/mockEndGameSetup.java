package ca.mcgill.ecse223.kingdomino.development;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.*;
import ca.mcgill.ecse223.kingdomino.model.*;
import ca.mcgill.ecse223.kingdomino.persistence.*;

public class mockEndGameSetup {
	
	public static void main(String [] args) {
		
		System.out.println("\n===================================================================================================");
		System.out.println("                      			Kingdomino Setup");
		System.out.println("===================================================================================================");

		
		
		// load up an existing kingdomino serialization, if not found, creates a new serialization
		Kingdomino kd = KDController.loadGame("endgame.data");
		int numPlayers=4;
		List<String> bonus = new ArrayList<String>();
		
		try {
		KDController.setGameOptions(numPlayers, bonus);
		KDController.startANewGame();
		}
		catch(Exception e) {}
		
		KDController.provideUserProfile("jay");
		KDController.provideUserProfile("keon");
		
		KDController.assignPlayerToUser(KDQuery.getPlayerByColor("blue"), KDController.getUserByName("jay"));
		
		View.printPlayers(kd);
		
		System.out.println(KDQuery.getAvailableUserNamesThisGame());

		

	}
}
