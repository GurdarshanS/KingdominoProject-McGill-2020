package ca.mcgill.ecse223.kingdomino.development;
import ca.mcgill.ecse223.kingdomino.model.*;
import ca.mcgill.ecse223.kingdomino.persistence.*;
import ca.mcgill.ecse223.kingdomino.*;
import ca.mcgill.ecse223.kingdomino.controller.KDController;

public class provideUserProfileTest {
	
	public static void main (String [] args) {
		Kingdomino kd = KDController.loadGame();
		
		String usernameA="userA";
		String usernameB="userB";
		String usernameC="userC";
		String usernameD="userD";
		String usernameE="userE";
		String usernameF="userF";
		
		KDController.provideUserProfile(usernameA);
		KDController.provideUserProfile(usernameB);
		KDController.provideUserProfile(usernameC);
		KDController.provideUserProfile(usernameD);
		KDController.provideUserProfile(usernameE);
		KDController.provideUserProfile(usernameF);
		
		KDController.saveGame();
		
	}

}
