package ca.mcgill.ecse223.kingdomino.persistence;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class PersistenceObjectStream {
	
	private static String fileName = "kingdomino.txt";

	public static void serialize(Object object) {
		 FileOutputStream fileOutput;
	        try {
	            fileOutput = new FileOutputStream(fileName);
	            ObjectOutputStream output = new ObjectOutputStream(fileOutput);
	            output.writeObject(object);
	            output.close();
	            fileOutput.close();
	        } catch (Exception e) {
	            throw new RuntimeException("Couldn't save '" + fileName + "'.");
	        }
	    }

	public static Object deserialize() {
      File file = new File(fileName);
      if(file.exists()){
      } else {
         try {
           file.createNewFile();
         } catch (IOException e) {
         }
      }  
		Object o = null;
		try (
            FileInputStream fileIn = new FileInputStream(file);
            ObjectInputStream in = new ObjectInputStream(fileIn);
		) {
		 	try {
        o = in.readObject();
      } catch (IOException e) {
        e.printStackTrace();
      }
		  
		} catch (EOFException e1) {
		} catch (ClassNotFoundException e2) {
		  throw new RuntimeException("Could not load data from file '" + fileName + "'.");
        } catch (IOException e3) {
      e3.printStackTrace();
    } 
		
		if (o == null) {
		}
		return o;
	}
	public static void setFilename(String newFilename) {
		fileName = newFilename;
	}
	
}