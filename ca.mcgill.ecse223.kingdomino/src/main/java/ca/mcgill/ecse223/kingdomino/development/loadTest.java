package ca.mcgill.ecse223.kingdomino.development;

import java.util.List;

import ca.mcgill.ecse223.kingdomino.controller.KDController;

public class loadTest {
	public static void main(String[] args) {
		
		String s = System.getProperty("user.dir");
		System.out.println(s);
		List<String> fileName = KDController.fileSearch(s);
		System.out.println(fileName);
	}
}
