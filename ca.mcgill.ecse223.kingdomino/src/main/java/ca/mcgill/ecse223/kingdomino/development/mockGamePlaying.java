package ca.mcgill.ecse223.kingdomino.development;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.KDController;
import ca.mcgill.ecse223.kingdomino.controller.KDQuery;
import ca.mcgill.ecse223.kingdomino.controller.View;
import ca.mcgill.ecse223.kingdomino.model.*;

public class mockGamePlaying {
	
		public static void main(String [] args)
		{	
			Scanner in = new Scanner(System.in);
			Kingdomino kd = KDController.loadGame(null);
			
			System.out.println("\n===================================================================================================");
			System.out.println("                      			Kingdomino Gameplay");
			System.out.println("===================================================================================================");
			
			System.out.println("input command: ");
			boolean run=true;
			while (run) {
				String cmd = in.nextLine();
				
				switch(cmd) {
				
				case "view players":
					View.printPlayers(kd);
					break;
				    
				case "view next order":
					View.printNextRoundPlayerOrder(kd);
					break;
					
			  	case "browse sorted":
			  		System.out.println("event: browse sorted domino pile");
			  		View.printSortedDominos(kd);
			  		break;
			  	
			  	case "browse unsorted":
			  		System.out.println("event: browse unsorted domino pile");
			  		View.printDominos(kd);
			  		break;
				
			  	case "start":
			  		System.out.println("event: starting game and state machine");
			  		kd.setStateMachine();
					System.out.println("initial state:  "+kd.getStateMachine().getGamestatusFullName());
			  		break;
			  		
			  	case "view draft":
					System.out.println("event: view draft");
					View.printDraft(kd);
					break;
					
				case "draft ready":
					System.out.println("processing SM event draftReady...");
					System.out.println("original state:  "+kd.getStateMachine().getGamestatusFullName());
					
					boolean draftReady=KDController.draftReadySM();

					System.out.println("event processed: "+draftReady);
					System.out.println("new state:       "+kd.getStateMachine().getGamestatusFullName());
					break;
				
				case "choose":
					System.out.println("processing SM event choose, input chosen domino ID:");
					String id = in.nextLine();
					int dominoID=Integer.decode(id);
					System.out.println("original state:  "+kd.getStateMachine().getGamestatusFullName());
					
					boolean choose=KDController.chooseSM(KDController.getdominoByID(dominoID));
					
					System.out.println("event processed: "+choose);
					System.out.println("new state:       "+kd.getStateMachine().getGamestatusFullName());
					break;
				
				case "selection ready":
					System.out.println("processing SM event selectionReady...");
					System.out.println("original state:  "+kd.getStateMachine().getGamestatusFullName());
					
					boolean selectionReady=KDController.selectionReadySM();

					System.out.println("event processed: "+selectionReady);
					System.out.println("new state:       "+kd.getStateMachine().getGamestatusFullName());
					break;
				
				case "manipulate first":
					System.out.println("processing SM event manipulateFirst, input preplacement posx, posy, and direction");
					String data = in.nextLine();
					int posx=Integer.decode(data.split(",")[0]);
					int posy=Integer.decode(data.split(",")[1]);
					String dir=data.split(",")[2];
					String confirm = String.format("inputed data was posx: %1$-5d, posy: %2$-5d, direction: ", 
							posx,posy,dir);
					System.out.println(confirm);
					System.out.println("original state:  "+kd.getStateMachine().getGamestatusFullName());
					boolean manipulateFirst=KDController.manipulateFirstSM(posx, posy, dir);
					System.out.println("event processed: "+manipulateFirst);
					System.out.println("new state:       "+kd.getStateMachine().getGamestatusFullName());

					
			  	case "stop":
			  		run=false;
			  		break;
			}
				
				System.out.println("\ninput new command: ");
			}

		}
		
		
		
		
}


