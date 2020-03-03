package ca.mcgill.ecse223.kingdomino;

import ca.mcgill.ecse223.kingdomino.controller.KDController;
import ca.mcgill.ecse223.kingdomino.model.Kingdomino;

public class KingdominoApplication {

	KDController kdc;
	private static Kingdomino kingdomino;

	public void main(String[] args) {
		System.out.println("Hello World!");
		KDController.initiateEmptyGame();
		
		kdc.listDominos();
	}

	public static Kingdomino getKingdomino() {
		if (kingdomino == null) {
			kingdomino = new Kingdomino();
		}
		return kingdomino;
	}

	public static void setKingdomino(Kingdomino kd) {
		kingdomino = kd;
	}
}
