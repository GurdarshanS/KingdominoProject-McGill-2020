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
	
	public static void CreateAndOrderNextDraft() {
		Kingdomino kingdomino = KingdominoApplication.getKingdomino();
		Game game = kingdomino.getCurrentGame();
		

		Draft nextDraft =  new Draft(Draft.DraftStatus.FaceDown ,game);
		
		Domino currentDomino = game.getTopDominoInPile();
		
		int[] domIDs = new int[nextDraft.maximumNumberOfIdSortedDominos()];
		
		domIDs[0] = currentDomino.getId();
		
		
		for( int i = 1; i < nextDraft.maximumNumberOfIdSortedDominos(); i++) {
			
			domIDs[i] = currentDomino.getNextDomino().getId();
			
			
		}
	
		
		game.setTopDominoInPile(currentDomino.getNextDomino());
		game.setNextDraft(nextDraft);
		
	}//CreateNextDraft
	
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

	
}
