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

	private static int DomID;
	private static int DraftAmount;
	private static Domino[] DominoArray;
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
	

	
	public static void listDominos() {
		
		for(int i = 1; i < 49 ; i++) {
			Domino dom = KDController.getdominoByID(i);
			
		}
	}
	
	public static void printListDominos() {
		
		for(int i = 1; i < 49 ; i++) {
			Domino dom = KDController.getdominoByID(i);
			System.out.println("============================================================="+"\nID : "+ dom.getId() + "  Left Crown : " + dom.getLeftCrown() + "  Right Crown : "+ dom.getRightCrown()
			+ "  Left Tile : "+ dom.getLeftTile()+ "  Right Tile : " + dom.getRightTile() + "  Status : " + dom.getStatus() + "\n=============================================================");
		}
	}

	public static void takeInputID(int id1) {
		
		DomID = id1;
		System.out.println(DomID);
	}
	
	public static void getLeftTile(String string) {
		
		Domino dom = KDController.getdominoByID(DomID);
		if(dom.getLeftTile().toString().equalsIgnoreCase(string) || dom.getLeftTile().toString().equalsIgnoreCase("wheatfield") ) {
		System.out.println(dom.getLeftTile().toString());
		
		}
	}
	
	public static void getRightTile(String string) {
			
		Domino dom = KDController.getdominoByID(DomID);
		if(dom.getRightTile().toString().equalsIgnoreCase(string) || dom.getLeftTile().toString().equalsIgnoreCase("wheatfield")) {
		System.out.println(dom.getRightTile().toString());
			}
		}
	
	public static void getCrowns(int int1) {
		Domino dom = KDController.getdominoByID(DomID);
		if(dom.getLeftCrown()+dom.getRightCrown() == int1) {
		System.out.println(dom.getLeftCrown()+dom.getRightCrown());
		}
	}

	public static void filterbyTerrain (String t1) {
		
		Domino[] DominoArray = new Domino[48];
		int count = 0;
		
		for (int i = 1; i < DominoArray.length+1 ; i++) {
			Domino dom = KDController.getdominoByID(i);
			if ( dom.getLeftTile().toString().equalsIgnoreCase(t1) || dom.getRightTile().toString().equalsIgnoreCase(t1) ) {
				
				DominoArray[count] = dom;
				System.out.println(DominoArray[count].getId());
				count++;
			}			
		}	
		
		
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
	
	public static void shuffleDominos() {
		
			Random rand = new Random();
			int count = 0;
			DominoArray = new Domino[48];
			
			for (int i = 1; i < DominoArray.length+1 ; i++) {
				Domino dom = KDController.getdominoByID(i);
				DominoArray[count] = dom;
				count++;
			}
			
			for (int i = 0; i < DominoArray.length; i++) {
				int randomIndexToSwap = rand.nextInt(DominoArray.length);
				Domino temp = DominoArray[randomIndexToSwap];
				DominoArray[randomIndexToSwap] = DominoArray[i];
				DominoArray[i] = temp;
			}		
	
	}
	
	public static void firstDraft(int int2) {
		
		Domino[] firstDraft = new Domino[4];
		DraftAmount = int2;
		for(int i = 0; i<int2 ; i++) {
			firstDraft[i] = DominoArray[i];
		}
	}
	
	public static int amountLeft(int int3) {
		
		if(int3 == DomAmount - DraftAmount) {
			return int3;
		};
		return 0;
	}
	
	public static void arrangeDominos(String s1) {
		
		DominoArray = new Domino[48];
		String[] StringArray = new String[48];
		StringArray = s1.split(", ");
		
		for(int i = 0; i < DominoArray.length; i++) {
			DominoArray[i] = KDController.getdominoByID(Integer.parseInt(StringArray[i]));
			Domino dom = DominoArray[i];
			System.out.println("============================================================="+"\nID : "+ dom.getId() + "  Left Crown : " + dom.getLeftCrown() + "  Right Crown : "+ dom.getRightCrown()
			+ "  Left Tile : "+ dom.getLeftTile()+ "  Right Tile : " + dom.getRightTile() + "  Status : " + dom.getStatus() + "\n=============================================================");
			
		}
	}
	
	public static void removeFromArranged(String st1, int int1) {
		DominoArray = new Domino[48];
		String[] StringArray = new String[48];
		StringArray = st1.split(", ");
		
		for(int i = 0; i < DominoArray.length; i++) {
			DominoArray[i] = KDController.getdominoByID(Integer.parseInt(StringArray[i]));
			
		}
		Domino[] DomArray3 = new Domino[48-int1];
		int count = 0;
		for(int i=int1; i<DominoArray.length ; i++) {
			DomArray3[count] = DominoArray[i];
			Domino dom = DomArray3[count];
			System.out.println("============================================================="+"\nID : "+ dom.getId() + "  Left Crown : " + dom.getLeftCrown() + "  Right Crown : "+ dom.getRightCrown()
			+ "  Left Tile : "+ dom.getLeftTile()+ "  Right Tile : " + dom.getRightTile() + "  Status : " + dom.getStatus() + "\n=============================================================");
			count++;
			
		}
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
