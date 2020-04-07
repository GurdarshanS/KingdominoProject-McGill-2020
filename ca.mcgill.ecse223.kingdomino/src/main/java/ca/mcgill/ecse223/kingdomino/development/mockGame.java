package ca.mcgill.ecse223.kingdomino.development;
import java.util.ArrayList;
import java.util.List;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
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
		
		int numPlayers=4;
		List<String> selectedBonusOptions = new ArrayList<String>();
		
		try{
			
			KDController.setGameOptions(numPlayers, selectedBonusOptions);
			
			//		all these below can be replaced with KDController.startANewGame();
			Game game = kd.getCurrentGame();
			int dominoNums=game.getMaxPileSize();
			KDController.createDominoPile(game,dominoNums);
			KDController.shuffleDominoPile();
			KDController.generateInitialPlayerOrder();

		}
		catch(Exception e) {}
		
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













//
///**
// * Guards
// */
//// line 219 "../../../../../Gameplay2.ump"
// public boolean isCurrentPlayerTheLastInTurn(Player player){
//	   return KDQuery.isCurrentPlayerTheLastInTurn(player);
//}
//
//// line 220 "../../../../../Gameplay2.ump"
// public boolean isCurrentTurnTheLastInGame(){
//  return KDQuery.isCurrentTurnTheLastInGame();
//}
//
//// line 221 "../../../../../Gameplay2.ump"
// public boolean isDominoTaken(Domino domino){
//  return KDQuery.isDominoTaken(domino);
//}
//
//// line 222 "../../../../../Gameplay2.ump"
// public boolean hasAllPlayersChosen(){
//  return KDQuery.hasAllPlayersChosen();
//}
//
//// line 223 "../../../../../Gameplay2.ump"
// public boolean isThereAvailablePlacement(Player player){
//	   List<KingdomTerritory> territories = player.getKingdom().getTerritories();
//	   DominoInKingdom dInK = (DominoInKingdom) territories.get(territories.size()-1);
//	   return KDQuery.isThereAvailablePlacement(player, dInK);
//}
//
//// line 224 "../../../../../Gameplay2.ump"
// public boolean verifyDomino(Player player){
//	   List<KingdomTerritory> territories = player.getKingdom().getTerritories();
//	   DominoInKingdom dInK = (DominoInKingdom) territories.get(territories.size()-1);
//	   return KDQuery.verifyDominoInKingdom(player,dInK);
//}
//
//// line 225 "../../../../../Gameplay2.ump"
// public boolean isDominoPileEmpty(){
//	   return KDQuery.isDominoPileEmpty();
//}
//
//// line 226 "../../../../../Gameplay2.ump"
// public boolean hasAllPlayersPlayed(){
//	   return KDQuery.hasAllPlayersPlayed();
//}
//
//
///**
// * You may need to add more guards here
// * Actions
// */
//// line 232 "../../../../../Gameplay2.ump"
// public void shuffleDominoPile(){
//	   KDController.shuffleDominoPile();
//}
//
//// line 233 "../../../../../Gameplay2.ump"
// public void createNextDraft(){
//	   KDController.createNextDraft();
//}
//
//// line 234 "../../../../../Gameplay2.ump"
// public void orderNextDraft(){
//	   KDController.sortNextDraft();
//}
//
//// line 235 "../../../../../Gameplay2.ump"
// public void revealNextDraft(){
//	   KDController.revealNextDraft();
//}
//
//// line 236 "../../../../../Gameplay2.ump"
// public void generateInitialPlayerOrder(){
//	   KDController.generateInitialPlayerOrder();
//}
//
//// line 237 "../../../../../Gameplay2.ump"
// public void chooseDomino(Domino domino){
//	   KDController.chooseNextDomino(domino);
//}
//
//// line 238 "../../../../../Gameplay2.ump"
// public void updatePlayerOrder(){
//	   KDController.updatePlayerOrder();
//}
//
//// line 239 "../../../../../Gameplay2.ump"
// public void updatePlayerScore(Player player){
//	   KDController.calculateIndividualPlayerScore(player);
//}
//
//// line 240 "../../../../../Gameplay2.ump"
// public void preplaceLatestDomino(Player player, int posx, int posy, String dir){
//	   KDController.preplaceLatestDomino(player, posx, posy, dir)
//}
//
//// line 241 "../../../../../Gameplay2.ump"
// public void rotateLatestDomino(Player player, String dir){
//  
//}
//
//// line 242 "../../../../../Gameplay2.ump"
// public void moveLatestDomino(Player player, String movement){
//  
//}
//
//// line 243 "../../../../../Gameplay2.ump"
// public void placeLatestDomino(Player player){
//  
//}
//
//// line 244 "../../../../../Gameplay2.ump"
// public void discardLatestDomino(Player player){
//  
//}
//
//// line 245 "../../../../../Gameplay2.ump"
// public void setNextPlayer(){
//  
//}
//
//// line 246 "../../../../../Gameplay2.ump"
// public void calculateAllPlayerScores(){
//  
//}
//
//// line 247 "../../../../../Gameplay2.ump"
// public void calculatePlayerRanking(){
//  
//}