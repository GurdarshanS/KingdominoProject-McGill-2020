package ca.mcgill.ecse223.kingdomino.controller;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.model.*;
import ca.mcgill.ecse223.kingdomino.model.Domino.DominoStatus;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom.DirectionKind;
import ca.mcgill.ecse223.kingdomino.model.Player.PlayerColor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class KDController {

	public static void discardDomino(Domino aSelectedDomino, Player aPlayer) {
		
		//Finish later
		
		aPlayer.getDominoSelection().delete();
		aSelectedDomino.setStatus(DominoStatus.Discarded);
		
	}
	
	public static void moveCurrentDomino() {
		
		
		
	}
	
	public static void placeCurrentDomino() {
		
		
		
		
	}
	
	public static void rotateCurrentDomino() {
		
		
		
		
	}

}