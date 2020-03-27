//package ca.mcgill.ecse223.kingdomino.controller;
//
//import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
//import ca.mcgill.ecse223.kingdomino.model.*;
//import ca.mcgill.ecse223.kingdomino.model.Player.PlayerColor;
//
//import java.io.BufferedReader;
//import java.io.FileReader;
//import java.io.IOException;
//import java.util.*;
//
//public class HowToUseCreateDraftMethods {
//	
//	public static void main (String [] args) {
//		
//		int playerNum=4;
//		
//		Kingdomino kingdomino = KingdominoApplication.getKingdomino();
////	    kingdomino.removeAllGame(kingdomino.getCurrentGame());
//	    
//		Game game = new Game(48, kingdomino);
//		kingdomino.setCurrentGame(game);
//		game.setNumberOfPlayers(playerNum);
//		
//		List<String> users= new ArrayList<String>();
//		
//		
//		
//		if (playerNum==4) {
//			users.add("usera");
//			users.add("userb");
//			users.add("userc");
//			users.add("userd");
//
//		}
//		if (playerNum==3) {
//			users.add("usera");
//			users.add("userb");
//			users.add("userc");
//		}
//		if (playerNum==2) {
//			users.add("usera");
//			users.add("userb");
//		}
//		for (int i = 0; i < users.size(); i++) {
//			game.getKingdomino().addUser(users.get(i));
//			Player player = new Player(game);
//			player.setColor(PlayerColor.values()[i]);
//			Kingdom kingdom = new Kingdom(player);
//			new Castle(0, 0, kingdom, player);
//		}
//		
//		try {
//			BufferedReader br = new BufferedReader(new FileReader("src/main/resources/alldominoes.dat"));
//			String line = "";
//			String delimiters = "[:\\+()]";
//			while ((line = br.readLine()) != null) {
//				String[] dominoString = line.split(delimiters); // {id, leftTerrain, rightTerrain, crowns}
//				int dominoId = Integer.decode(dominoString[0]);
//				TerrainType leftTerrain = getTerrainType(dominoString[1]);
//				TerrainType rightTerrain = getTerrainType(dominoString[2]);
//				int numCrown = 0;
//				if (dominoString.length > 3) {
//					numCrown = Integer.decode(dominoString[3]);
//				}
//				new Domino(dominoId, leftTerrain, rightTerrain, numCrown, game);
//			}
//			br.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//			throw new java.lang.IllegalArgumentException(
//					"Error occured while trying to read alldominoes.dat: " + e.getMessage());
//		}
//		
//		Kingdomino kd = KingdominoApplication.getKingdomino();
//		game = kd.getCurrentGame();
//		
//		List<Domino> dominosInGame = game.getAllDominos();
//		
//		for (int i=0;i<dominosInGame.size()-1;i++) {
//			dominosInGame.get(i).setNextDomino(dominosInGame.get(i+1));
//		}
//		
//		Domino firstDomino=dominosInGame.get(0);
//		game.setTopDominoInPile(firstDomino);
//		
//		System.out.println("==================== Deck ====================");
//		while (firstDomino.getNextDomino()!=null) {
//			System.out.println(firstDomino.getId()+" - "+firstDomino.getLeftTile()+" - "+firstDomino.getRightTile());
//			firstDomino=firstDomino.getNextDomino();
//		}
//		System.out.println(firstDomino.getId()+" - "+firstDomino.getLeftTile()+" - "+firstDomino.getRightTile());
//
//		
//		System.out.println("\n==================== initiate game ====================");
//		System.out.println("current number of drafts: "+game.getAllDrafts().size());
//		
//		
//		Draft currentDraft = game.getCurrentDraft();
//		Draft nextDraft = game.getNextDraft();
//		
//		int numDominosInPile=KDController.getAvailableDominoPile().size();
//		
//		System.out.println("number of dominos in pile: "+numDominosInPile);
//		System.out.print("current draft: ");
//		System.out.print(currentDraft);
//		System.out.println();
//		System.out.print("next draft: ");
//		System.out.print(nextDraft);
//		System.out.println();
//		
//		
//		for (int round=0;round<12;round++)
//		{
//			System.out.println("\n====================  round #" + round+" ====================");
//
//			numDominosInPile=KDController.getAvailableDominoPile().size();
//			System.out.println("number of dominos before draft: "+numDominosInPile);
//			
//			KDController.createNextDraft();
//			currentDraft = game.getCurrentDraft();
//			nextDraft = game.getNextDraft();
//			
//			System.out.println("\ncurrent draft");
//			System.out.println("---------------------------------");
//			for (int i=0;i<currentDraft.getIdSortedDominos().size();i++) {
//				Domino each = currentDraft.getIdSortedDominos().get(i);
//				System.out.println(each.getId()+" - "+each.getLeftTile()+" - "+each.getRightTile()+" - "+each.getStatus());
//			}
//			
//			if (!game.hasNextDraft()) break;
//
//			
//			System.out.println("\nnext draft");
//			System.out.println("---------------------------------");
//			for (int i=0;i<nextDraft.getIdSortedDominos().size();i++) {
//				Domino each = nextDraft.getIdSortedDominos().get(i);
//				System.out.println(each.getId()+" - "+each.getLeftTile()+" - "+each.getRightTile()+" - "+each.getStatus());
//			}
//			
//			numDominosInPile=KDController.getAvailableDominoPile().size();
//			System.out.println("\nnumber of dominos after current and next drafts: "+numDominosInPile);
//			System.out.println("total drafts in game: "+game.getAllDrafts().size());
//		}
//		
//		System.out.println("\n\n===================== all dominos drafted ================================");
//		System.out.println("last current draft of the game: \n");
//		for (int i=0;i<currentDraft.getIdSortedDominos().size();i++) {
//			Domino each = currentDraft.getIdSortedDominos().get(i);
//			System.out.println(each.getId()+" - "+each.getLeftTile()+" - "+each.getRightTile()+" - "+each.getStatus());
//		}
//		System.out.println("=====================================================");
//		System.out.println("last next draft of the game should be null: ");
//		System.out.println(game.getNextDraft());
//		
//	}
//	
//	private static TerrainType getTerrainType(String terrain) {
//		switch (terrain) {
//		case "W":
//			return TerrainType.WheatField;
//		case "F":
//			return TerrainType.Forest;
//		case "M":
//			return TerrainType.Mountain;
//		case "G":
//			return TerrainType.Grass;
//		case "S":
//			return TerrainType.Swamp;
//		case "L":
//			return TerrainType.Lake;
//		default:
//			throw new java.lang.IllegalArgumentException("Invalid terrain type: " + terrain);
//		}
//	}
//
//}
