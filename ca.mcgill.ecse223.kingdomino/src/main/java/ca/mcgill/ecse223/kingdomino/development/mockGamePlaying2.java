package ca.mcgill.ecse223.kingdomino.development;

import java.util.ArrayList;
import java.util.List;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.KDController;
import ca.mcgill.ecse223.kingdomino.controller.KDQuery;
import ca.mcgill.ecse223.kingdomino.controller.View;
import ca.mcgill.ecse223.kingdomino.model.*;

public class mockGamePlaying2 {
	
		public static void main(String [] args)
		{	
			Kingdomino kd = KDController.loadGame(null);
			
			System.out.println("\n===============================================================================================");
			System.out.println("                      setting up game, state machine uninitialized");
			System.out.println("===============================================================================================");
			
			
			View.printUsers(kd);
			System.out.println("dominos before state machine initialization");
			View.printDominos(kd);	
			System.out.println("player order before draftReady transition");
			View.printPlayers(kd);
			
			System.out.println("\n===============================================================================================");
			
			kd.setStateMachine();				//initializes the state machine to Initializing.CreatingFirstDraft, 
												//state actions include ShuffleDominoPile,CreateNextDraft, and OrderNextDraft
			
			System.out.println("                              state machine initialized");
			System.out.println("===============================================================================================");
			System.out.println("current state: "+kd.getStateMachine().getGamestatusFullName());
			System.out.println("===============================================================================================");

			Gameplay sm = kd.getStateMachine();
			
			System.out.println("dominos after state machine initialization");
			View.printDominos(kd);
			View.printDraft(kd);
			
			System.out.println("\n===============================================================================================");
			System.out.println("original state: "+sm.getGamestatusFullName());
			
			KDController.draftReadySM();		// draftReady transition of SM from Initializing.CreatingFirstDraft to
												// Initializing.SelectingFirstDomino, actions include revealNextDraft()
												// and generateInitialPlayerOrder()
			System.out.println("transition:     draftReady()");
			System.out.println("new state:      "+sm.getGamestatusFullName());
			System.out.println("===============================================================================================");

			
			
			System.out.println("player order after draftReady transition");
			View.printPlayers(kd);
			View.printDraft(kd);
			System.out.println(kd.getStateMachine().getGamestatusFullName());
			
			
//			View.printDominos(kd);
//			View.printTotalDraftNum(kd);
//			View.printNextRoundPlayerOrder(kd);
//			
//			KDController.initializeSM();
//			Gameplay sm=KingdominoApplication.getStateMachine();
//			

//			
//			View.printDominos(kd);
//			View.printDraft(kd);
//			View.printNextRoundPlayerOrder(kd);
//
//			
//			System.out.println("\n===============================================================================================");
//			System.out.println("original state: "+sm.getGamestatusFullName());
//			System.out.println("transition:     draftReady()");
//			
//			KDController.draftReadySM();
//			
//			System.out.println("new state:      "+sm.getGamestatusFullName());
//			System.out.println("===============================================================================================");
//			
//			
//			
//			System.out.println("current draft - status: "+kd.getCurrentGame().getCurrentDraft().getDraftStatus());
//			View.printNextRoundPlayerOrder(kd);
//			
//			System.out.println("\n===============================================================================================");
//			System.out.println("original state: "+sm.getGamestatusFullName());
//			System.out.println("transition:     choose(Domino domino)");
//			System.out.println("new state:      "+sm.getGamestatusFullName());
//			System.out.println("===============================================================================================");
//			
//			boolean selected=KDController.chooseSM(kd.getCurrentGame().getCurrentDraft().getIdSortedDomino(0));
//			System.out.println("\nsuccessfully chosen:  "+selected);
//
//			View.printDraft(kd);
//			View.printNextRoundPlayerOrder(kd);
//			
//			boolean ready=KDController.selectionReadySM();
//			System.out.println("\nall players selected: "+ready);
//			
//			
//			
//			System.out.println("\n===============================================================================================");
//			System.out.println("original state: "+sm.getGamestatusFullName());
//			System.out.println("transition:     choose(Domino domino)");
//			System.out.println("new state:      "+sm.getGamestatusFullName());
//			System.out.println("===============================================================================================");
//			
//			selected=KDController.chooseSM(kd.getCurrentGame().getCurrentDraft().getIdSortedDomino(3));
//			System.out.println("\nsuccessfully chosen:  "+selected);
//			
//			View.printDraft(kd);
//			View.printNextRoundPlayerOrder(kd);
//			
//			ready=KDController.selectionReadySM();			
//			System.out.println("\nall players selected: "+ready);
//			
//			
//
//			System.out.println("\n===============================================================================================");
//			System.out.println("original state: "+sm.getGamestatusFullName());
//			System.out.println("transition:     choose(Domino domino)");
//			System.out.println("new state:      "+sm.getGamestatusFullName());
//			System.out.println("===============================================================================================");
//			
//			selected=KDController.chooseSM(kd.getCurrentGame().getCurrentDraft().getIdSortedDomino(2));
//			System.out.println("\nsuccessfully chosen:  "+selected);
//
//			View.printDraft(kd);
//			View.printNextRoundPlayerOrder(kd);
//			
//			ready=KDController.selectionReadySM();			
//			System.out.println("\nall players selected: "+ready);
//			
//			
//			
//			System.out.println("\n===============================================================================================");
//			System.out.println("original state: "+sm.getGamestatusFullName());
//			System.out.println("transition:     choose(Domino domino)");
//			System.out.println("new state:      "+sm.getGamestatusFullName());
//			System.out.println("===============================================================================================");
//			
//			selected=KDController.chooseSM(kd.getCurrentGame().getCurrentDraft().getIdSortedDomino(1));
//			System.out.println("\nsuccessfully chosen:  "+selected);
//
//			View.printDraft(kd);
//			View.printNextRoundPlayerOrder(kd);
//			
//			ready=KDController.selectionReadySM();	
//			System.out.println("\nall players selected: "+ready);
//			
//			System.out.println(sm.getGamestatusFullName());
//			
//			View.printDominos(kd);
//			View.printNextRoundPlayerOrder(kd);


		}
		
		
		
		
}


