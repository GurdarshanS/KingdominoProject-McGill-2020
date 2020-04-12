package ca.mcgill.ecse223.kingdomino;

import ca.mcgill.ecse223.kingdomino.model.Kingdomino;
import ca.mcgill.ecse223.kingdomino.controller.*;
import ca.mcgill.ecse223.kingdomino.model.Gameplay;
import ca.mcgill.ecse223.kingdomino.persistence.*;

public class KingdominoApplication {

	private static Kingdomino kingdomino;
	private static Gameplay sm;

	public static void main(String[] args) {
		System.out.println("Hello World!");
	}

	public static Kingdomino getKingdomino() {
		if (kingdomino == null) {
			kingdomino = new Kingdomino();
		}
		else {
		}		
		return kingdomino;
	}
	
	public static Gameplay getStateMachine() {
		if (!kingdomino.hasStateMachine()) {
			kingdomino.setStateMachine();
		}
		return kingdomino.getStateMachine();
	}
	
	public static void setStatemachine() {
		kingdomino.setStateMachine();
	}
	
	public static void setKingdomino(Kingdomino kd) {
		kingdomino = kd;
	}
}
