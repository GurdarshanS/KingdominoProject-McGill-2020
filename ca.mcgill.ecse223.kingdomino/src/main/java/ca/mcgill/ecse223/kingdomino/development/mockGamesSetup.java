package ca.mcgill.ecse223.kingdomino.development;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.*;
import ca.mcgill.ecse223.kingdomino.model.*;
import ca.mcgill.ecse223.kingdomino.persistence.*;

public class mockGamesSetup {
	
	public static void main(String [] args) {
		
		System.out.println("\n===================================================================================================");
		System.out.println("                      			Kingdomino Setup");
		System.out.println("===================================================================================================");

		
		Scanner in = new Scanner(System.in);
		
		// load up an existing kingdomino serialization, if not found, creates a new serialization
		Kingdomino kd = KDController.loadGame(null);
		
		//addes users to the loaded kingdomino object
		System.out.println("enter names of new users to add to Kingodmino, input 'done' to finish or skip (defaults to no user)");
		System.out.println("---------------------------------------------------------------------------------------------------");

		while (true) {
			String name = in.nextLine();
			if (name.equalsIgnoreCase("done")) {
				System.out.println("user creation complete, total users:");
				int i=1;
				for (User u:kd.getUsers()) {
					System.out.println(i+". "+u.getName());
					i+=1;
				}
				break;
			}
			
			try {
				KDController.provideUserProfile(name);
				String msg = String.format("user %1$-20s successfully created\n", name);
				System.out.println(msg);
			}
			catch(IllegalArgumentException e) {
				String msg= String.format("user %1$-20s not created due to "
						+ e.getMessage()+"\n", name);
				System.out.println(msg);
			}
		}
	
		System.out.println("---------------------------------------------------------------------------------------------------");
		System.out.println("enter number of players for current game, input 'done' to finish or skip (defaults to 4)");
		
		in.reset();
		String numInput=in.nextLine();
		int numPlayers;
		
		if (numInput.equalsIgnoreCase("done")) {
			numPlayers=4;
		}
		else{
			numPlayers=Integer.decode(numInput);
		}
		
		System.out.println("---------------------------------------------------------------------------------------------------");
		List<String> selectedBonusOptions = new ArrayList<String>();
		
		System.out.println("enter bonus options for current game, or input 'done' to finish or skip (defaults no bonus)");
		while (true) {
			
			String option=in.nextLine();
			
			if (option.equalsIgnoreCase("done")) {
				if (selectedBonusOptions.isEmpty()) {
					System.out.println("confirmed. no options chosen");
				}
				else {
					System.out.println("confirmed. options chosen: ");
					for (String s:selectedBonusOptions) System.out.println(s);
				}
				break;
			}
			if (option.equalsIgnoreCase("none")) {
				System.out.println("confirmed. no options chosen");
				break;
			}
			
			if (!selectedBonusOptions.isEmpty()) {
				if (!selectedBonusOptions.contains(option)) {
					System.out.println("option: "+option+" selected. Input another option or 'done' to terminate\n");
					selectedBonusOptions.add(option);
				}
				else {
					System.out.println("option: "+option+" already selected\n");
				}
			}
			else {
				System.out.println("option: "+option+" selected. Input another option or 'done' to terminate\n");
				selectedBonusOptions.add(option);
			}
		}
		
		
		System.out.println("---------------------------------------------------------------------------------------------------");
		System.out.println("new game setup: assign players to users, or input 'done' to finish or skip (defaults to no user associated with players");
		
		try{
			
			KDController.setGameOptions(numPlayers, selectedBonusOptions);
			KDController.startANewGame();
			
			
			while (true) {
				if (KDQuery.hasAllPlayersAUser()){
					System.out.println("all available players assigned a user, exiting player-user assignment");
					break;
				}
				System.out.println("assign pair (playercolor,username):");
				String input=in.nextLine();
				
				if (input.equalsIgnoreCase("done")) {
					System.out.println("player - user assignment complete");
					for (Player p:kd.getCurrentGame().getPlayers()) {
						String row = String.format("user: %1$-10s player: %2$-10s",p.getColor(),p.getUser().getName());
						System.out.println(row);
					}
						
					break;
				}
				
				try {
					String color = input.split(",")[0];
					String name = input.split(",")[1];
					Player p = KDQuery.getPlayerByColor(color);
					User u = KDController.getUserByName(name);
					KDController.assignPlayerToUser(p, u);
					System.out.println("succeed: player "+p.getColor()+" assigned to user "+u.getName()+"\n");
				}
				catch (Exception e) {
					System.out.println("failed: "+e.getMessage()+"\n");
				}
				
				
			}
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			}



		KDController.saveGame(null,true);
		System.out.println("---------------------------------------------------------------------------------------------------");
		System.out.println("game setup complete, serializing data to default disk location: "+KDPersistence.getFilename());
		System.out.println("game ready");


	}
}
