package ca.mcgill.ecse223.kingdomino.controller;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;


import ca.mcgill.ecse223.kingdomino.model.*;
import ca.mcgill.ecse223.kingdomino.model.Domino.DominoStatus;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom.DirectionKind;
import ca.mcgill.ecse223.kingdomino.model.Draft.DraftStatus;
import ca.mcgill.ecse223.kingdomino.model.Player.PlayerColor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import java.util.Collection;
public class KDController {
	
	// 2, 8, 9, 10

//	a comments
	

	public static void ProvidetUserProfile(String username) throws IllegalArgumentException {

		Kingdomino kingdomino = KingdominoApplication.getKingdomino();
	
		
		if (User.hasWithName(username)) {
			throw new IllegalArgumentException("User already exists");
		} 
		
		else {
			
			
			kingdomino.addUser(username);
		//browse option??
			
			
		}

	}//ProvidetUserProfile
	

	/**
	 * 
	 * This method creates the current and next
	 * draft of the game. takes care of the beginning case
	 * when there is no current or next drafts, takes care
	 * of regular play by swapping current with next and 
	 * then generate a new next, and takes care of the
	 * end game when there is no more domino for  next 
	 * and it is set to null
	 * 
	 * @see  - CreateNextDraft.feature
	 * @author Keon Olsz, refactored by Jing Han
	 * @param player
	 * @return boolean
	 */
	
	public static void createNextDraft() {
		Kingdomino kd = KingdominoApplication.getKingdomino();
		Game game = kd.getCurrentGame();
		int draftNumLimit=0;
		
		if ((game.getNumberOfPlayers()==4)||(game.getNumberOfPlayers()==3)) draftNumLimit=12;
		if (game.getNumberOfPlayers()==2) draftNumLimit=6;
		
		
		if (game.getAllDrafts().size()==0) {
			Draft currentDraft=createOneDraft();
			currentDraft.setDraftStatus(Draft.DraftStatus.FaceUp);
			game.setCurrentDraft(currentDraft);
			changeDraftDominoStatus(currentDraft,Domino.DominoStatus.InCurrentDraft);

			Draft nextDraft = createOneDraft();
			nextDraft.setDraftStatus(Draft.DraftStatus.FaceDown);
			game.setNextDraft(nextDraft);
			changeDraftDominoStatus(nextDraft,Domino.DominoStatus.InNextDraft);
		}
		else if (game.getAllDrafts().size()<draftNumLimit) {
			changeDraftDominoStatus(game.getNextDraft(),DominoStatus.InCurrentDraft);
			game.setCurrentDraft(game.getNextDraft());
			game.getCurrentDraft().setDraftStatus(Draft.DraftStatus.FaceUp);
			
			Draft nextDraft=createOneDraft();
			changeDraftDominoStatus(nextDraft,DominoStatus.InNextDraft);
			game.setNextDraft(nextDraft);
			game.getNextDraft().setDraftStatus(Draft.DraftStatus.FaceDown);
		}
		else {
//			changeDraftDominoStatus(game.getNextDraft(),DominoStatus.InCurrentDraft);
			game.setCurrentDraft(game.getNextDraft());
			game.getCurrentDraft().setDraftStatus(Draft.DraftStatus.FaceUp);
			
			game.setNextDraft(null);
		}
	
	}
	
	/**
	 * 
	 * This method changes the status of dominos in a game
	 * 
	 * @see  - CreateNextDraft.feature
	 * @author Keon Olsz, refactored by Jing Han
	 * @param player
	 * @return boolean
	 */
	
	public static void changeDraftDominoStatus(Draft draft, Domino.DominoStatus status) {
		
		if(!((status.equals(Domino.DominoStatus.InCurrentDraft))||(status.equals(Domino.DominoStatus.InNextDraft)))) {
			throw new IllegalArgumentException("status must be either InCurrentDraft or InNextDraft");
		}
		
		List<Domino> draftDominos = draft.getIdSortedDominos();
		for (Domino each:draftDominos) {
			each.setStatus(status);
		}
	}
	
	/**
	 * 
	 * This method one draft based on the number
	 * of players in the game
	 * 
	 * @see  - CreateNextDraft.feature
	 * @author Keon Olsz, refactored by Jing Han
	 * @param player
	 * @return boolean
	 */
	
	public static Draft createOneDraft() {
		Kingdomino kd = KingdominoApplication.getKingdomino();
		Game game = kd.getCurrentGame();
		
		int numPlayer=kd.getCurrentGame().getNumberOfPlayers();
		int dominoNum=0;
		
		if ((numPlayer==2)||(numPlayer==4)) dominoNum=4;
		if (numPlayer==3) dominoNum=3;
		
		Draft draft = new Draft(Draft.DraftStatus.FaceDown,game);
		
		for (int i=0;i<dominoNum;i++) {
			Domino dominoToAdd=game.getTopDominoInPile();
			game.setTopDominoInPile(game.getTopDominoInPile().getNextDomino());
			draft.addIdSortedDomino(dominoToAdd);
		}
		
		return draft;
	}
	
	public static List<Domino> getAvailableDominoPile(){
		Kingdomino kd = KingdominoApplication.getKingdomino();
		Game game = kd.getCurrentGame();
		List<Domino> availableDomino=new ArrayList<Domino>();
		List<Domino> allDomino=game.getAllDominos();
		
		for (Domino test:allDomino) {
			if (test.getStatus().equals(Domino.DominoStatus.InPile)) {
				availableDomino.add(test);
			}
		}
		
		return availableDomino;
		
	}	
		

	
	public static void OrderNextDraft() {
		
	Kingdomino kingdomino = KingdominoApplication.getKingdomino();
	Game game = kingdomino.getCurrentGame();
	Draft nextDraft =	game.getNextDraft();
	List<Domino> nextDraftList = nextDraft.getIdSortedDominos();
	int domIDs[] = new int[nextDraftList.size()];
	
	for(int i = 0; i < nextDraftList.size(); i ++) {
		
		int domID = nextDraftList.get(i).getId();
		domIDs[i] = domID;
		
		}
	
	Arrays. sort(domIDs);
	
	for(int i = 0; i < domIDs.length; i ++) {
	nextDraft.addOrMoveIdSortedDominoAt(getdominoByID(domIDs[i]), i);
		}

	 

		nextDraft.setDraftStatus(Draft.DraftStatus.Sorted);

	}// OrderNextDraft
	
	
	public static void RevealNextDraft() {
		
		Kingdomino kingdomino = KingdominoApplication.getKingdomino();
		Game game = kingdomino.getCurrentGame();
		Draft nextDraft =	game.getNextDraft();
		
		nextDraft.setDraftStatus(DraftStatus.FaceUp);
		
	}
	
	
	
//	
//	public static void RevealNextDraft() {
//		Kingdomino kingdomino = KingdominoApplication.getKingdomino();
//		Game game = kingdomino.getCurrentGame();
//		//maybe if statements checking is its the right time
//		
//		Draft nextDraft = game.getNextDraft();
//		game.setCurrentDraft(nextDraft);
//		nextDraft.setDraftStatus(Draft.DraftStatus.FaceUp);
//		
//		
//		
//	}//RevealNextDraft
	
	public static void ChoosNextDomino(Player aPlayer, Domino aDomino)throws IllegalArgumentException {
		
		Kingdomino kingdomino = KingdominoApplication.getKingdomino();
		Game game = kingdomino.getCurrentGame();
		
		Draft currentDraft = game.getCurrentDraft();
		
		
		if(!currentDraft.getIdSortedDominos().contains(aDomino)) {
			
			throw new IllegalArgumentException("Domino Not In Draft");

		}
		
		else if( aDomino.hasDominoSelection()) {
			
			throw new IllegalArgumentException("Domino Already Chosen");
			
			}
		else {
			
			currentDraft.addSelection(aPlayer, aDomino);
			
			DominoSelection selection = currentDraft.addSelection(aPlayer, aDomino);
			aDomino.setDominoSelection(selection);
			aPlayer.setDominoSelection(selection);
		}
		
		
	}//ChoosNextDomino
	
	private static Domino getdominoByID(int id) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		for (Domino domino : game.getAllDominos()) {
			if (domino.getId() == id) {
				return domino;
			}
		}
		throw new java.lang.IllegalArgumentException("Domino with ID " + id + " not found.");
	}
	
	

	
}
