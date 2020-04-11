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
				
				case "next selection ready":
					System.out.println("processing SM event nextSelectionReady...");
					System.out.println("original state:  "+kd.getStateMachine().getGamestatusFullName());
					
					boolean nextSelectionReady=KDController.nextSelectionReadySM();

					System.out.println("event processed: "+nextSelectionReady);
					System.out.println("new state:       "+kd.getStateMachine().getGamestatusFullName());
					break;
				
				case "last selection ready":
					System.out.println("processing SM event lastSelectionReady, input preplacement posx, posy, and direction");			
					String data = in.nextLine();
					int posx=Integer.decode(data.split(",")[0]);
					int posy=Integer.decode(data.split(",")[1]);
					String dir=data.split(",")[2];
					String confirm = String.format("inputed data was posx: %1$-5d, posy: %2$-5d, direction: ", 
							posx,posy,dir);
					
					System.out.println("original state:  "+kd.getStateMachine().getGamestatusFullName());
					
					boolean lastSelectionReady=KDController.lastSelectionReadySM(posx, posy, dir);

					System.out.println("event processed: "+lastSelectionReady);
					System.out.println("new state:       "+kd.getStateMachine().getGamestatusFullName());
					break;
				
				case "manipulate first":
					System.out.println("processing SM event manipulateFirst, input preplacement posx, posy, and direction");
					data = in.nextLine();
					posx=Integer.decode(data.split(",")[0]);
					posy=Integer.decode(data.split(",")[1]);
					dir=data.split(",")[2];
					confirm = String.format("inputed data was posx: %1$-5d, posy: %2$-5d, direction: ", 
							posx,posy,dir);
					System.out.println(confirm);
					System.out.println("original state:  "+kd.getStateMachine().getGamestatusFullName());
					boolean manipulateFirst=KDController.manipulateFirstSM(posx, posy, dir);
					System.out.println("event processed: "+manipulateFirst);
					System.out.println("new state:       "+kd.getStateMachine().getGamestatusFullName());
					
					break;
				
				case "manipulate next":
					System.out.println("processing SM event manipulateNext, input preplacement posx, posy, and direction");
					data = in.nextLine();
					posx=Integer.decode(data.split(",")[0]);
					posy=Integer.decode(data.split(",")[1]);
					dir=data.split(",")[2];
					confirm = String.format("inputed data was posx: %1$-5d, posy: %2$-5d, direction: ", 
							posx,posy,dir);
					System.out.println(confirm);
					System.out.println("original state:  "+kd.getStateMachine().getGamestatusFullName());
					boolean manipulateNext=KDController.manipulateNextSM(posx, posy, dir);
					System.out.println("event processed: "+manipulateNext);
					System.out.println("new state:       "+kd.getStateMachine().getGamestatusFullName());
					
					break;
				
				case "manipulate last":
					System.out.println("processing SM event manipulateLast, input preplacement posx, posy, and direction");
					data = in.nextLine();
					posx=Integer.decode(data.split(",")[0]);
					posy=Integer.decode(data.split(",")[1]);
					dir=data.split(",")[2];
					confirm = String.format("inputed data was posx: %1$-5d, posy: %2$-5d, direction: ", 
							posx,posy,dir);
					System.out.println(confirm);
					System.out.println("original state:  "+kd.getStateMachine().getGamestatusFullName());
					boolean manipulateLast=KDController.manipulateLastSM(posx, posy, dir);
					System.out.println("event processed: "+manipulateLast);
					System.out.println("new state:       "+kd.getStateMachine().getGamestatusFullName());
					
					break;
				
				case "scoring":
					System.out.println("processing SM event scoring...");

					boolean scored=KDController.scoringSM();
					
					System.out.println("event processed: "+scored);
					System.out.println("new state:       "+kd.getStateMachine().getGamestatusFullName());
					break;
				
				case "see ranking":
					View.printRankings(kd);
					break;
					
				case "rotate":
					System.out.println("processing SM event rotate, input rotation direction ('clockwise','counterclockwise'):");
					String rotateDir = in.nextLine();
					System.out.println("original state:  "+kd.getStateMachine().getGamestatusFullName());
					
					boolean rotated=KDController.rotateSM(rotateDir);
					
					System.out.println("event processed: "+rotated);
					System.out.println("new state:       "+kd.getStateMachine().getGamestatusFullName());
					break;
				
				case "move":
					System.out.println("processing SM event move, input movement direction ('up','down','left','right'):");
					String movement = in.nextLine();
					System.out.println("original state:  "+kd.getStateMachine().getGamestatusFullName());
					
					boolean moved=KDController.moveSM(movement);
					
					System.out.println("event processed: "+moved);
					System.out.println("new state:       "+kd.getStateMachine().getGamestatusFullName());
					break;
				
				case "place":
					System.out.println("processing SM event place...");
					System.out.println("original state:  "+kd.getStateMachine().getGamestatusFullName());
					
					boolean placed=KDController.placeSM();

					System.out.println("event processed: "+placed);
					System.out.println("new state:       "+kd.getStateMachine().getGamestatusFullName());
					
					break;
				
				case "discard":
					System.out.println("processing SM event discard...");
					System.out.println("original state:  "+kd.getStateMachine().getGamestatusFullName());
					
					boolean discarded=KDController.discardSM();

					System.out.println("event processed: "+discarded);
					System.out.println("new state:       "+kd.getStateMachine().getGamestatusFullName());
					break;
				
				
				
				case "has all chosen":
					System.out.println("has all players chosen: "+KDQuery.hasAllPlayersChosen());
					break;
				
				case "has all played":
					System.out.println("has all players played: "+KDQuery.hasAllPlayersPlayed());
					break;
				
				case "is pile empty":
					System.out.println("is pile empty: "+KDQuery.isDominoPileEmpty());
					break;
				
				case "current player":
					System.out.println("current player: "+kd.getCurrentGame().getNextPlayer().getColor());
					break;
					
				case "is last in turn":
					System.out.println("is the last in turn: "+KDQuery.lastPlayerInTurn(kd.getCurrentGame().getNextPlayer()));
					break;
					
				case "is domino taken":
					System.out.println("enter domino id");
					int testId= Integer.decode(in.nextLine());
					Domino testDomino=KDController.getdominoByID(testId);
					System.out.println("is domino "+testId+" taken: "+KDQuery.isDominoTaken(testDomino));
					break;
				
				case "current state":
					System.out.println("current state: "+kd.getStateMachine().getGamestatusFullName());
					break;
					
			  	case "stop":
			  		run=false;
			  		break;
			}
				
				System.out.println("\ninput new command: ");
			}

		}
		
		
		
		
}


