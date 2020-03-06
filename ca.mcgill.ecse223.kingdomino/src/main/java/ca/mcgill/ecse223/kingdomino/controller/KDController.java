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

	private static int DraftAmount;
	private static int DomAmount;

	
	public static void initiateEmptyGame() {
		// Intialize empty game
		Kingdomino kingdomino = KingdominoApplication.getKingdomino();
		Game game = new Game(48, kingdomino);
		game.setNumberOfPlayers(4);
		kingdomino.setCurrentGame(game);
		// Populate game
		addDefaultUsersAndPlayers(game);
		createAllDominoes(game);
		game.setNextPlayer(game.getPlayer(0));
	}
	
	public static Domino[] listDominos() {
		Domino[] myDominos = new Domino[48];
		for(int i = 1; i < 49 ; i++) {
			Domino dom = KDController.getdominoByID(i);
			myDominos[i-1] = dom;
			
		}
		return myDominos;
	}

	public static ArrayList filterbyTerrain (String s1) {
		
		ArrayList myDominos = new ArrayList();
		TerrainType t1 = KDController.retrieveTerrainType(s1);
		for (int i = 1; i < 49 ; i++) {
			Domino dom = KDController.getdominoByID(i);
			
			if ( dom.getLeftTile().toString().equalsIgnoreCase(t1.toString()) || dom.getRightTile().toString().equalsIgnoreCase(t1.toString()) ) {
				
				myDominos.add(dom.getId());
			}			
		}	
		

		return myDominos;
	}
	
	
	
	
	
	public static void numOfPlayers(int number) {
		
		Kingdomino kingdomino = KingdominoApplication.getKingdomino();
		DomAmount = 0;
		if(number == 4) {
			DomAmount = 48;
		} else if (number == 3) {
			DomAmount = 36;
		} else if (number == 2) {
			DomAmount = 24;
		}
		Game game = new Game(DomAmount, kingdomino);
		game.setNumberOfPlayers(number);
		kingdomino.setCurrentGame(game);
		// Populate game
		addDefaultUsersAndPlayers(game);
		createAllDominoes(game);
		game.setNextPlayer(game.getPlayer(0));
		System.out.println(game.getNumberOfPlayers());
	}
	
	public static Domino[] shuffleDominos() {

			int count = 0;
			Domino[] DominoArray = new Domino[48];
			
			for (int i = 1; i < DominoArray.length+1 ; i++) {
				Domino dom = KDController.getdominoByID(i);
				DominoArray[count] = dom;
				count++;
			}
			
			ArrayList Dominos = new ArrayList();
			for (int i = 0; i < DominoArray.length; i++) {
				Dominos.add(DominoArray[i]);
			}		
			Collections.shuffle(Dominos);
			
			for(int i=0; i<DominoArray.length; i++) {
				DominoArray[i] = (Domino) Dominos.get(i);
			}
			return DominoArray;
	}
	
	public static Integer[] arrangeDominos(String s1) {
		
		Integer[] DominoArray = new Integer[48];
		String[] StringArray = new String[48];
		StringArray = s1.split(", ");
		
		for(int i = 0; i < DominoArray.length; i++) {
			DominoArray[i] = (Integer.parseInt(StringArray[i]));			
		}
		return DominoArray;
	}
	
	public static Domino[] arrangeTheDominos(String s1) {
		
		Domino[] DominoArray = new Domino[48];
		String[] StringArray = new String[48];
		StringArray = s1.split(", ");
		
		for(int i = 0; i < DominoArray.length; i++) {
			DominoArray[i] = KDController.getdominoByID(Integer.parseInt(StringArray[i]));
		}
		return DominoArray;
	}
	
	public static Domino[] removeDraftDominos(String s1, int int1) {
		Domino[] DominoArray = new Domino[48];
		String[] StringArray = new String[48];
		StringArray = s1.split(", ");
		int count = 0;
		for(int i = int1; i < DominoArray.length; i++) {
			DominoArray[count] = KDController.getdominoByID(Integer.parseInt(StringArray[i]));
			System.out.println(DominoArray[count].getId());
			count++;
		}
		
		return DominoArray;
		
	}


	
	
	
	
	
	
	
	
	public static int setPlayerDoms(int n, DominoInKingdom d1, DominoInKingdom d2) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		Domino Dom1 = null;
		Domino Dom2 = null;
		int tiles1 = 0;
		int tiles2 = 0;
		int tiles3 = 0;
		int crowns1 = 0;
		int crowns2 = 0;
		int crowns3 = 0;
		int score = 0;
		if(n == 0) {
			Dom1 = d1.getDomino();
			Dom2 = d2.getDomino();
			game.getPlayer(n).setColor(PlayerColor.Blue);
		} else if ( n == 1) {
			Dom1 = d1.getDomino();
			Dom2 = d2.getDomino();
			game.getPlayer(n).setColor(PlayerColor.Green);
		} else if (n==2) {
			Dom1 = d1.getDomino();
			Dom2 = d2.getDomino();
			game.getPlayer(n).setColor(PlayerColor.Pink);
		} else if (n==3) {
			Dom1 = d1.getDomino();
			Dom2 = d2.getDomino();
			game.getPlayer(n).setColor(PlayerColor.Yellow);
		}
		
		if(Dom1.getLeftTile().equals(Dom1.getRightTile())) {
			tiles1++;
			crowns1 = Dom1.getLeftCrown() + Dom1.getRightCrown();
			if(Dom1.getRightTile().equals(Dom2.getRightTile())) {
				tiles1++;
				crowns1 = Dom1.getLeftCrown() + Dom1.getRightCrown()+ Dom2.getRightCrown();
				
				if(Dom2.getLeftTile().equals(Dom2.getRightTile())) {
					tiles1++;
					crowns1 = Dom1.getLeftCrown() + Dom1.getRightCrown()+ Dom2.getRightCrown() + Dom2.getLeftCrown();
				}
			}
		} else if(Dom1.getRightTile().equals(Dom2.getRightTile())) {
			tiles2++;
			crowns2 = Dom1.getRightCrown() + Dom2.getRightCrown();
			if(Dom2.getLeftTile().equals(Dom2.getRightTile())) {
				tiles2++;
				crowns2 = Dom1.getRightCrown() + Dom2.getRightCrown() + Dom2.getLeftCrown();
			}
		} else if (Dom2.getLeftTile().equals(Dom2.getRightTile())) {
			tiles3++;
			crowns3 = Dom2.getLeftCrown() + Dom2.getRightCrown();
		}
		
		
		
		return score = (tiles1*crowns1) + (tiles2*crowns2) + (tiles3*crowns3);
		
		
	}
	
	public static Player[] bubbleSort(Player[] scoreList) 
    { 
        int n = scoreList.length; 
        for (int i = 0; i < n-1; i++) 
            for (int j = 0; j < n-i-1; j++) 
                if (scoreList[j].getPropertyScore() > scoreList[j+1].getPropertyScore()) 
                { 
                    // swap arr[j+1] and arr[i] 
                    Player temp = scoreList[j]; 
                    scoreList[j] = scoreList[j+1]; 
                    scoreList[j+1] = temp; 
                } 
        System.out.println(scoreList.toString());
        return scoreList;
    } 
	
	
	
	
	
	private static void createAllDominoes(Game game) {
	try {
		BufferedReader br = new BufferedReader(new FileReader("src/main/resources/alldominoes.dat"));
		String line = "";
		String delimiters = "[:\\+()]";
		while ((line = br.readLine()) != null) {
			String[] dominoString = line.split(delimiters); // {id, leftTerrain, rightTerrain, crowns}
			int dominoId = Integer.decode(dominoString[0]);
			TerrainType leftTerrain = getTerrainType(dominoString[1]);
			TerrainType rightTerrain = getTerrainType(dominoString[2]);
			int numCrown = 0;
			if (dominoString.length > 3) {
				numCrown = Integer.decode(dominoString[3]);
			}
			new Domino(dominoId, leftTerrain, rightTerrain, numCrown, game);
		}
		br.close();
	} catch (IOException e) {
		e.printStackTrace();
		throw new java.lang.IllegalArgumentException(
				"Error occured while trying to read alldominoes.dat: " + e.getMessage());}
	}

	
	private static TerrainType getTerrainType(String terrain) {
		switch (terrain) {
		case "W":
			return TerrainType.WheatField;
		case "F":
			return TerrainType.Forest;
		case "M":
			return TerrainType.Mountain;
		case "G":
			return TerrainType.Grass;
		case "S":
			return TerrainType.Swamp;
		case "L":
			return TerrainType.Lake;
		default:
			throw new java.lang.IllegalArgumentException("Invalid terrain type: " + terrain);
		}
	}
	
	
	private static void addDefaultUsersAndPlayers(Game game) {
		String[] users = { "User1", "User2", "User3", "User4" };
		for (int i = 0; i < users.length; i++) {
			game.getKingdomino().addUser(users[i]);
			Player player = new Player(game);
			player.setColor(PlayerColor.values()[i]);
			Kingdom kingdom = new Kingdom(player);
			new Castle(0, 0, kingdom, player);
		}
	}
	
	
	public static Domino getdominoByID(int id) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		for (Domino domino : game.getAllDominos()) {
			if (domino.getId() == id) {
				return domino;
			}
		}
		throw new java.lang.IllegalArgumentException("Domino with ID " + id + " not found.");
	}
	
	public static TerrainType retrieveTerrainType(String terrain) {
		switch (terrain) {
		case "wheat":
			return TerrainType.WheatField;
		case "forest":
			return TerrainType.Forest;
		case "mountain":
			return TerrainType.Mountain;
		case "grass":
			return TerrainType.Grass;
		case "swamp":
			return TerrainType.Swamp;
		case "lake":
			return TerrainType.Lake;
		default:
			throw new java.lang.IllegalArgumentException("Invalid terrain type: " + terrain);
		}
	}
	public static DirectionKind getDirection(String dir) {
		switch (dir) {
		case "up":
			return DirectionKind.Up;
		case "down":
			return DirectionKind.Down;
		case "left":
			return DirectionKind.Left;
		case "right":
			return DirectionKind.Right;
		default:
			throw new java.lang.IllegalArgumentException("Invalid direction: " + dir);
		}
	}


	
	}
