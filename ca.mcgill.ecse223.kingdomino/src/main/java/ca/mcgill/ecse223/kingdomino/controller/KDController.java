package ca.mcgill.ecse223.kingdomino.controller;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
//import ca.mcgill.ecse223.kingdomino.model.Kingdomino;
import ca.mcgill.ecse223.kingdomino.model.*;
import ca.mcgill.ecse223.kingdomino.model.Domino.DominoStatus;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom.DirectionKind;
import ca.mcgill.ecse223.kingdomino.model.Player.PlayerColor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class KDController {

	public KDController(){}
	

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

	public static String getKingdomSizeVerificationResult(Player player) {
		String validity="valid";
		
		Kingdom kingdom=player.getKingdom();
		List<DominoInKingdom> errorneouslyPlacedDominos=KDQuery.getErroneouslyPrePlacedDomino(player);
//		System.out.println(errorneouslyPlacedDominos.size());
		if (!errorneouslyPlacedDominos.isEmpty()) {
			validity="invalid";
		}
		
		return validity;
		
	}
	
	public static void prePlaceDomino(Player player, Domino dominoToPlace, int posx, int posy, String dir) {
		dominoToPlace.setStatus(DominoStatus.ErroneouslyPreplaced);
		Kingdom kingdom=player.getKingdom();
		DominoInKingdom dInK = new DominoInKingdom(posx,posy,kingdom,dominoToPlace);
		dInK.setDirection(getDirection(dir));
	}
	
	public static void verifyKingdom(Player player) {
		
		List<KingdomTerritory> t = player.getKingdom().getTerritories();
		List<Integer> xCoords = new ArrayList<Integer>();
		List<Integer> yCoords = new ArrayList<Integer>();

		
		for (KingdomTerritory each:t) {
			
			int x1=each.getX();
			int y1=each.getY();
			
			xCoords.add(x1);
			yCoords.add(y1);
			
			int x2;
			int y2;
			
			if (each instanceof DominoInKingdom) {
				((DominoInKingdom) each).getDomino().setStatus(DominoStatus.PlacedInKingdom);
				int[] otherPos=calculateOtherPos((DominoInKingdom) each);
				x2=otherPos[0];
				y2=otherPos[1];
			}
			else {
				x2=0;
				y2=0;
			}
			
			xCoords.add(x2);
			yCoords.add(y2);
			
			Collections.sort(xCoords);
			Collections.sort(yCoords);
			
			int xSize=xCoords.get(xCoords.size()-1)-xCoords.get(0)+1;
			int ySize=yCoords.get(yCoords.size()-1)-yCoords.get(0)+1;
			
			if (xSize>5 || ySize>5) {
				if (each instanceof DominoInKingdom) {
					((DominoInKingdom) each).getDomino().setStatus(DominoStatus.ErroneouslyPreplaced);
				}
			}
			
		}
	}
	
	///////////////////////////////////////
	/// -----Private Helper Methods---- ///
	///////////////////////////////////////
	
	private static int[] minMaxArray(int[] x) {
		int[] xTemp=x.clone();
		Arrays.sort(xTemp);
		int[] minmax= {xTemp[0],xTemp[xTemp.length-1]};
		return minmax;
	}

	public static int[] calculateOtherPos(DominoInKingdom d) {
		int x2;
		int y2;
		
		if (d.getDirection().equals(DirectionKind.Right)) {
			x2=d.getX()+1;
			y2=d.getY();
		}
		else if (d.getDirection().equals(DirectionKind.Up)) {
			x2=d.getX();
			y2=d.getY()+1;
		}
		else if (d.getDirection().equals(DirectionKind.Left)) {
			x2=d.getX()-1;
			y2=d.getY();
		}
		else {
			x2=d.getX();
			y2=d.getY()-1;
		}
		
		int [] coord2= {x2,y2};
		return coord2;
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
					"Error occured while trying to read alldominoes.dat: " + e.getMessage());
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

	private static DirectionKind getDirection(String dir) {
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

	private static DominoStatus getDominoStatus(String status) {
		switch (status) {
		case "inPile":
			return DominoStatus.InPile;
		case "excluded":
			return DominoStatus.Excluded;
		case "inCurrentDraft":
			return DominoStatus.InCurrentDraft;
		case "inNextDraft":
			return DominoStatus.InNextDraft;
		case "erroneouslyPreplaced":
			return DominoStatus.ErroneouslyPreplaced;
		case "correctlyPreplaced":
			return DominoStatus.CorrectlyPreplaced;
		case "placedInKingdom":
			return DominoStatus.PlacedInKingdom;
		case "discarded":
			return DominoStatus.Discarded;
		default:
			throw new java.lang.IllegalArgumentException("Invalid domino status: " + status);
		}
	}
	

	private int argmax (int[] x) {
		int max=0;
		int index=-1;
		
		for (int i=0;i<x.length;i++) {
			if (x[i]>max) {
				max=x[i];
				index=i;
			}
		}
		return index;
	}
	
	private int argmin (int[] x) {
		int min=0;
		int index=-1;
		
		for (int i=0;i<x.length;i++) {
			if (x[i]<min) {
				min=x[i];
				index=i;
			}
		}
		return index;
	}

}
