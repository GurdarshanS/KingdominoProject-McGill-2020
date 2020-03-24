package ca.mcgill.ecse223.kingdomino.persistence;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class PersistenceObjectStream {
	
	private static String fileName = "default_saved_game.data";

	public static void serialize(Object object) {
		 FileOutputStream fileOutput;
	        try {
	            fileOutput = new FileOutputStream(fileName);
	            ObjectOutputStream output = new ObjectOutputStream(fileOutput);
	            output.writeObject(object);
	            output.close();
	            fileOutput.close();
	            System.out.println("something got saved");
	        } catch (Exception e) {
	            throw new RuntimeException("Couldn't save '" + fileName + "'.");
	        }
	    }

	public static Object deserialize() {
      File file = new File(fileName); 
      Object o = null;
      
      
      try (
            FileInputStream fileIn = new FileInputStream(file);
            ObjectInputStream in = new ObjectInputStream(fileIn);
		) {
		 	try {
		 		o = in.readObject();
		 	} 
		 	catch (IOException e) {
		 		e.printStackTrace();
		 	}
		 	fileIn.close();
		 	in.close();
      } 
      catch (EOFException e1) {e1.printStackTrace();} 
      catch (ClassNotFoundException e2) {e2.printStackTrace();}
      catch (IOException e3) {
    	  System.out.println("saved game file not found, make sure such file exists");
    	  e3.printStackTrace();
      	} 
      
      return o;
	}
	
	public static void setFilename(String newFilename) {
		fileName = newFilename;
	}
	
}