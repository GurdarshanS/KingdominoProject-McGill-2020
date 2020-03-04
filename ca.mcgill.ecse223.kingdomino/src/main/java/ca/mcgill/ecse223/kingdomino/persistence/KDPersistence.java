package ca.mcgill.ecse223.kingdomino.persistence;

import ca.mcgill.ecse223.kingdomino.persistence.PersistenceObjectStream;
import ca.mcgill.ecse223.kingdomino.model.*;

public class KDPersistence {
	
	private static String Persistancefilename = "block-data.ser";

	public static void save(Kingdomino kingdomino) {
		PersistenceObjectStream.serialize(kingdomino);
	}
	
	public static Kingdomino load() {
		PersistenceObjectStream.setFilename(Persistancefilename);
		
		Kingdomino kingdomino=null;
		try{
			 kingdomino = (Kingdomino) PersistenceObjectStream.deserialize();

		} catch (Exception e) {
			kingdomino = new Kingdomino();
			System.out.println("Serialization exception.");
		}
		if (kingdomino == null) {
			kingdomino = new Kingdomino();
		}
		return kingdomino;
	}
	
	public static void setFilename(String newFilename) {
		Persistancefilename = newFilename;
	}
	
	public static String getFilename() {
	  return Persistancefilename;
	}

}
