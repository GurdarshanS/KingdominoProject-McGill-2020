package ca.mcgill.ecse223.kingdomino.development;
import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.KDController;
import ca.mcgill.ecse223.kingdomino.model.*;
import java.util.*;

public class optionsTest {
	public static void main (String [] args) {
		Kingdomino kd = new Kingdomino();
		KingdominoApplication.setKingdomino(kd);
		
		List<String> options=new ArrayList<String>(Arrays.asList("Harmony","MiddleKingdom"));
		
		try{
			KDController.setGameOptions(4, options);
		}
		catch(Exception e) {}
		
		System.out.println(kd.getBonusOptions());
		
	}
}
