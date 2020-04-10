package ca.mcgill.ecse223.kingdomino.development;

import java.util.ArrayList;
import java.util.List;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.KDController;
import ca.mcgill.ecse223.kingdomino.controller.KDQuery;
import ca.mcgill.ecse223.kingdomino.controller.View;
import ca.mcgill.ecse223.kingdomino.model.*;

public class stateMachineTest {
	
		public static void main(String [] args)
		{	
			
			
			Kingdomino kd = KingdominoApplication.getKingdomino();

			int numPlayers=4;
			List<String> selectedBonusOptions = new ArrayList<String>();
			
			try{
				KDController.setGameOptions(numPlayers, selectedBonusOptions);
				System.out.println(kd.getCurrentGame().getNumberOfPlayers());
				KDController.startANewGame();
			}
			catch(Exception e) {}
			
			System.out.println("\n===============================================================================================");
			System.out.println("                      setting up game, state machine uninitialized");
			System.out.println("===============================================================================================");
			
			View.printDominos(kd);
			View.printTotalDraftNum(kd);
			View.printNextRoundPlayerOrder(kd);
			
			KDController.initializeSM();
			Gameplay sm=KingdominoApplication.getStateMachine();
			
			System.out.println("\n===============================================================================================");
			System.out.println("                              state machine initialized");
			System.out.println("===============================================================================================");
			System.out.println("current state: "+sm.getGamestatusFullName());
			System.out.println("===============================================================================================");

			
			View.printDominos(kd);
			View.printDraft(kd);
			View.printNextRoundPlayerOrder(kd);

			
			System.out.println("\n===============================================================================================");
			System.out.println("original state: "+sm.getGamestatusFullName());
			System.out.println("transition:     draftReady()");
			
			KDController.draftReadySM();
			
			System.out.println("new state:      "+sm.getGamestatusFullName());
			System.out.println("===============================================================================================");
			
			
			
			System.out.println("current draft - status: "+kd.getCurrentGame().getCurrentDraft().getDraftStatus());
			View.printNextRoundPlayerOrder(kd);
			
			System.out.println("\n===============================================================================================");
			System.out.println("original state: "+sm.getGamestatusFullName());
			System.out.println("transition:     choose(Domino domino)");
			System.out.println("new state:      "+sm.getGamestatusFullName());
			System.out.println("===============================================================================================");
			
			boolean selected=KDController.chooseSM(kd.getCurrentGame().getCurrentDraft().getIdSortedDomino(0));
			System.out.println("\nsuccessfully chosen:  "+selected);

			View.printDraft(kd);
			View.printNextRoundPlayerOrder(kd);
			
			boolean ready=KDController.selectionReadySM();
			System.out.println("\nall players selected: "+ready);
			
			
			
			System.out.println("\n===============================================================================================");
			System.out.println("original state: "+sm.getGamestatusFullName());
			System.out.println("transition:     choose(Domino domino)");
			System.out.println("new state:      "+sm.getGamestatusFullName());
			System.out.println("===============================================================================================");
			
			selected=KDController.chooseSM(kd.getCurrentGame().getCurrentDraft().getIdSortedDomino(3));
			System.out.println("\nsuccessfully chosen:  "+selected);
			
			View.printDraft(kd);
			View.printNextRoundPlayerOrder(kd);
			
			ready=KDController.selectionReadySM();			
			System.out.println("\nall players selected: "+ready);
			
			

			System.out.println("\n===============================================================================================");
			System.out.println("original state: "+sm.getGamestatusFullName());
			System.out.println("transition:     choose(Domino domino)");
			System.out.println("new state:      "+sm.getGamestatusFullName());
			System.out.println("===============================================================================================");
			
			selected=KDController.chooseSM(kd.getCurrentGame().getCurrentDraft().getIdSortedDomino(2));
			System.out.println("\nsuccessfully chosen:  "+selected);

			View.printDraft(kd);
			View.printNextRoundPlayerOrder(kd);
			
			ready=KDController.selectionReadySM();			
			System.out.println("\nall players selected: "+ready);
			
			
			
			System.out.println("\n===============================================================================================");
			System.out.println("original state: "+sm.getGamestatusFullName());
			System.out.println("transition:     choose(Domino domino)");
			System.out.println("new state:      "+sm.getGamestatusFullName());
			System.out.println("===============================================================================================");
			
			selected=KDController.chooseSM(kd.getCurrentGame().getCurrentDraft().getIdSortedDomino(1));
			System.out.println("\nsuccessfully chosen:  "+selected);

			View.printDraft(kd);
			View.printNextRoundPlayerOrder(kd);
			
			ready=KDController.selectionReadySM();	
			System.out.println("\nall players selected: "+ready);
			
			System.out.println(sm.getGamestatusFullName());
			
			View.printDominos(kd);
			View.printNextRoundPlayerOrder(kd);


		}
		
		
		
		
}

///**
// * Guards
// */
//// line 219 "../../../../../Gameplay.ump"
// public boolean isCurrentPlayerTheLastInTurn(){
//	   Kingdomino kd = KingdominoApplication.getKingdomino();
//	   Game game=kd.getCurrentGame();
//	   Player player=game.getNextPlayer();
//	   return KDQuery.isCurrentPlayerTheLastInTurn(player);
//}
//
//// line 220 "../../../../../Gameplay.ump"
// public boolean isCurrentTurnTheLastInGame(){
//	   return KDQuery.isCurrentTurnTheLastInGame();
//}
//
//// line 221 "../../../../../Gameplay.ump"
// public boolean isDominoTaken(Domino domino){
//	   return KDQuery.isDominoTaken(domino);
//}
//
//// line 222 "../../../../../Gameplay.ump"
// public boolean hasAllPlayersChosen(){
//	   return KDQuery.hasAllPlayersChosen();
//}
//
//// line 223 "../../../../../Gameplay.ump"
// public boolean isThereAvailablePlacement(){
//	   Kingdomino kd = KingdominoApplication.getKingdomino();
//	   Game game=kd.getCurrentGame();
//	   Player player=game.getNextPlayer();
//	   List<KingdomTerritory> territories = player.getKingdom().getTerritories();
//	   DominoInKingdom dInK = (DominoInKingdom) territories.get(territories.size()-1);
//	   
//	   return KDQuery.isThereAvailablePlacement(player, dInK);
//}
//
//// line 224 "../../../../../Gameplay.ump"
// public boolean verifyDomino(){
//	   Kingdomino kd = KingdominoApplication.getKingdomino();
//	   Game game=kd.getCurrentGame();
//	   Player player=game.getNextPlayer();
//	   List<KingdomTerritory> territories = player.getKingdom().getTerritories();
//	   DominoInKingdom dInK = (DominoInKingdom) territories.get(territories.size()-1);
//	   
//	   return KDQuery.verifyDominoInKingdom(player, dInK);
//}
//
//// line 225 "../../../../../Gameplay.ump"
// public boolean isDominoPileEmpty(){
//	   return KDQuery.isDominoPileEmpty();
//}
//
//// line 226 "../../../../../Gameplay.ump"
// public boolean hasAllPlayersPlayed(){
//	   return KDQuery.hasAllPlayersPlayed();
//}
//
//
///**
// * You may need to add more guards here
// * Actions
// */
//// line 232 "../../../../../Gameplay.ump"
// public void shuffleDominoPile(){
//	   KDController.shuffleDominoPile();
//}
//
//// line 233 "../../../../../Gameplay.ump"
// public void createNextDraft(){
//	   KDController.createNextDraft();
//}
//
//// line 234 "../../../../../Gameplay.ump"
// public void orderNextDraft(){
//	   KDController.sortNextDraft();
//}
//
//// line 235 "../../../../../Gameplay.ump"
// public void revealNextDraft(){
//	   KDController.revealNextDraft();
//}
//
//// line 236 "../../../../../Gameplay.ump"
// public void generateInitialPlayerOrder(){
//	   KDController.generateInitialPlayerOrder();
//}
//
//// line 237 "../../../../../Gameplay.ump"
// public void chooseDomino(Domino domino){
//	   KDController.chooseNextDomino(domino);
//}
//
//// line 238 "../../../../../Gameplay.ump"
// public void updatePlayerOrder(){
//	   KDController.updatePlayerOrder();
//}
//
//// line 239 "../../../../../Gameplay.ump"
// public void updatePlayerScore(){
//	   Kingdomino kd = KingdominoApplication.getKingdomino();
//	   Game game=kd.getCurrentGame();
//	   Player player=game.getNextPlayer();
//
//	   KDController.calculateIndividualPlayerScore(player);
//}
//
//// line 240 "../../../../../Gameplay.ump"
// public void preplaceLatestDomino(int posx, int posy, String dir){
//  
//	   Kingdomino kd = KingdominoApplication.getKingdomino();
//	   Game game=kd.getCurrentGame();
//	   Player player=game.getNextPlayer();   
//	   KDController.preplaceLatestDomino(player, posx, posy, dir);
//}
//
//// line 241 "../../../../../Gameplay.ump"
// public void rotateLatestDomino(String dir){
//	   Kingdomino kd = KingdominoApplication.getKingdomino();
//	   Game game=kd.getCurrentGame();
//	   Player player=game.getNextPlayer();   
//	   KDController.rotateLatestDomino(player, dir);
//}
//
//// line 242 "../../../../../Gameplay.ump"
// public void moveLatestDomino(String movement){
//	   Kingdomino kd = KingdominoApplication.getKingdomino();
//	   Game game=kd.getCurrentGame();
//	   Player player=game.getNextPlayer();   
//	   KDController.moveLatestDomino(player, movement);
//}
//
//// line 243 "../../../../../Gameplay.ump"
// public void placeLatestDomino(){
//	   Kingdomino kd = KingdominoApplication.getKingdomino();
//	   Game game=kd.getCurrentGame();
//	   Player player=game.getNextPlayer();   
//	   KDController.placeLatestDomino(player);
//}
//
//// line 244 "../../../../../Gameplay.ump"
// public void discardLatestDomino(){
//	   Kingdomino kd = KingdominoApplication.getKingdomino();
//	   Game game=kd.getCurrentGame();
//	   Player player=game.getNextPlayer();   
//	   KDController.discardLatestDomino(player);
//}
//
//// line 245 "../../../../../Gameplay.ump"
// public void setNextPlayer(){
//	   Kingdomino kd = KingdominoApplication.getKingdomino();
//	   Game game=kd.getCurrentGame();
//	   Player player=game.getNextPlayer();
//	   KDController.updateNextPlayer(player);
//}
//
//// line 246 "../../../../../Gameplay.ump"
// public void calculateAllPlayerScores(){
//	   Kingdomino kd = KingdominoApplication.getKingdomino();
//	   Game game=kd.getCurrentGame();
//	   KDController.calculateAllPlayerScore(game);
//}
//
//// line 247 "../../../../../Gameplay.ump"
// public void calculatePlayerRanking(){
//	   KDController.calculatePlayerRanking();
//}


