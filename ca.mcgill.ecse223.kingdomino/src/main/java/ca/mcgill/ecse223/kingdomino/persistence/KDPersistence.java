package ca.mcgill.ecse223.kingdomino.persistence;

import ca.mcgill.ecse223.kingdomino.persistence.PersistenceObjectStream;

import java.io.File;

import ca.mcgill.ecse223.kingdomino.model.*;

public class KDPersistence {
	
	private static String fileName = "kingdomino.data";

	public static void save(Kingdomino kingdomino,boolean overwrite) {
		
		File file = new File(fileName);
		
		if (!file.exists()) {
			PersistenceObjectStream.setFilename(fileName);
			PersistenceObjectStream.serialize(kingdomino);
		}
		else {
			if (overwrite) {
				PersistenceObjectStream.setFilename(fileName);
				PersistenceObjectStream.serialize(kingdomino);
			}
		}

	}
	
	public static Kingdomino load() {
		PersistenceObjectStream.setFilename(fileName);
		Kingdomino kingdomino= (Kingdomino) PersistenceObjectStream.deserialize();
		
		if (kingdomino == null) {}
		else {
			kingdomino.reinitialize();
		}
		return kingdomino;
	}
	
	public static void setFilename(String newFilename) {
		fileName = newFilename;
	}
	
	public static String getFilename() {
	  return fileName;
	}

}