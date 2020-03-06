package ca.mcgill.ecse223.kingdomino.controller;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;

import ca.mcgill.ecse223.kingdomino.model.*;
import ca.mcgill.ecse223.kingdomino.model.Domino.DominoStatus;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom.DirectionKind;
import ca.mcgill.ecse223.kingdomino.model.Player.PlayerColor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;


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
	
	public static void CreateNextDraft() {
		
		Kingdomino kingdomino = KingdominoApplication.getKingdomino();
		Game game = kingdomino.getCurrentGame();
		
		Draft nextDraft =  new Draft(Draft.DraftStatus.FaceDown ,game);
		
	
		
		
		if(game.getNumberOfPlayers() == 3) {
			
			
			if(game.getAllDrafts().size()-1 == 12) {
				
				
				Draft currentdDraft = game.getNextDraft();
				game.setCurrentDraft(currentdDraft);
				game.setNextDraft(null);
				game.setTopDominoInPile(null);
				
				return;
				
				
			}
			
			Domino currentDomino = game.getTopDominoInPile();
			nextDraft.addIdSortedDomino(currentDomino);
			currentDomino.setStatus(DominoStatus.InNextDraft);
			
			for(int i = 0; i < 3; i++) {
			
			nextDraft.addIdSortedDomino(currentDomino.getNextDomino());
			currentDomino.setStatus(DominoStatus.InNextDraft);
			currentDomino = currentDomino.getNextDomino();
			
			}
			
			game.setTopDominoInPile(currentDomino);
			
		}
		
		else {
			
			
			
			if(game.getAllDrafts().size()-1 == 12 && game.getNumberOfPlayers() == 4) {
				
				System.out.println("AAAAAAAAAWWWWWWWWWWWBBBB");
				Draft currentdDraft = game.getNextDraft();
				game.setCurrentDraft(currentdDraft);
				game.setNextDraft(null);
				game.setTopDominoInPile(null);
				
				
				
				return;
				
				
					}
			
			else if(game.getNumberOfPlayers() == 2 && game.getAllDrafts().size()-1 == 6) {
				
				
				Draft currentdDraft = game.getNextDraft();
				game.setCurrentDraft(currentdDraft);
				game.setNextDraft(null);
				game.setTopDominoInPile(null);
				
				return;
				
				
					}
			
			Domino currentDomino = game.getTopDominoInPile();
			nextDraft.addIdSortedDomino(currentDomino);
			currentDomino.setStatus(DominoStatus.InNextDraft);
			
			for(int i = 0; i < 4; i++) {
			
			nextDraft.addIdSortedDomino(currentDomino.getNextDomino());
			currentDomino.setStatus(DominoStatus.InNextDraft);
			currentDomino = currentDomino.getNextDomino();
			
			}
			
			game.setTopDominoInPile(currentDomino);
			
		}
		
		
		game.addAllDraft(nextDraft);
		Draft currentDraft = game.getNextDraft();
		
		game.setCurrentDraft(currentDraft);
		game.setNextDraft(nextDraft);
		
		
		for(int i = 0 ; i < currentDraft.getIdSortedDominos().size(); i++) {
			
			
			currentDraft.getIdSortedDomino(i).setStatus(DominoStatus.InCurrentDraft);
		}
		
		
	
		
		
	} // createNewDraft
		
		
		

	
//	public static void OrderNextDraft() {
//		Kingdomino kingdomino = KingdominoApplication.getKingdomino();
//		Game game = kingdomino.getCurrentGame();
//		
//	Draft nextDraft =	game.getNextDraft();
//	ArrayList<int> domIDs = new ArrayList<int>();
//	List<Domino> draftList = nextDraft.getIdSortedDominos();
//	
//	// sort Dominos by IDs
//	
//	nextDraft.setDraftStatus(Draft.DraftStatus.Sorted);
//
//
//		
//	}// OrderNextDraft
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
