package to_do;
import java.io.File;
import java.util.*;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.KDController;
import ca.mcgill.ecse223.kingdomino.development.View;
import ca.mcgill.ecse223.kingdomino.model.Domino.DominoStatus;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom;
import ca.mcgill.ecse223.kingdomino.model.Kingdomino;
import ca.mcgill.ecse223.kingdomino.model.Player;


public class parseData {
	private static Kingdomino kd = KingdominoApplication.getKingdomino();
	private static File file;
	private static List<String> data=new ArrayList<String>();
	private static String claims;
	private static String unclaims;
	private static String p1Data;
	private static String p2Data;
	private static String p3Data;
	private static String p4Data;
	private static Player p1;
	private static Player p2;
	private static Player p3;
	private static Player p4;
	
	public static void main(String [] args) {
		KDController.initiateEmptyGame();
		p1=kd.getCurrentGame().getPlayer(0);
		p2=kd.getCurrentGame().getPlayer(1);
		p3=kd.getCurrentGame().getPlayer(2);
		p4=kd.getCurrentGame().getPlayer(3);
			
		String string = "src/test/resources/kingdomino_test_game_1.mov";
		readFile(string);
		parseAndPlace(p1,p1Data);
		View.printPlayerKingdom(p1);
	}
	
	public static void readFile(String string) {
		data.clear();
		try {
			file = new File(string);
			Scanner reader = new Scanner(file);
			while (reader.hasNextLine()){
				String line=reader.nextLine();
				data.add(line);
			}
		}
		catch(Exception e) {}
		for (int i=0;i<data.size();i++) {
			System.out.println(i+"-----"+data.get(i));
		}
		
		claims=data.get(0);
		unclaims=data.get(1);
		p1Data=data.get(2);
		p2Data=data.get(3);
		p3Data=data.get(4);
		p4Data=data.get(5);
	}
	
	public static void parseAndPlace(Player player, String playerData) {
		int id=-1;
		int posx=-1;
		int posy=-1;
		String dir="none";
		String pos = playerData.split(":")[1];
		String[] pieces=pos.split(", ");
		for (String s:pieces) {
			s=s.replaceAll("\\s+", "");
			id = Integer.decode(s.split("@")[0]);
			String info = s.split("@")[1];
			info=info.substring(info.indexOf("(") + 1, info.indexOf(")"));
			posx=Integer.decode(info.split(",")[0]);
			posy=Integer.decode(info.split(",")[1]);
			if (info.split(",")[2].equalsIgnoreCase("r")) dir="right";
			else if (info.split(",")[2].equalsIgnoreCase("l")) dir="left";
			else if (info.split(",")[2].equalsIgnoreCase("d")) dir="down";
			else if (info.split(",")[2].equalsIgnoreCase("u")) dir="up";
			KDController.preplaceArbitraryDomino(player, KDController.getdominoByID(id), posx, posy, dir);
			KDController.getdominoByID(id).setStatus(DominoStatus.PlacedInKingdom);
		}
		
		System.out.println(id+" "+posx+" "+posy+" "+dir);
		
	}

}
