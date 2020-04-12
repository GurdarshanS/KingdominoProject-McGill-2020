package ca.mcgill.ecse223.kingdomino.development;
import java.util.Scanner;

public class getConsoleInput {

	public static void main(String [] args) {
		Scanner in = new Scanner(System.in);
		String s="";
		while (true) {
			s=in.nextLine();
			System.out.println("echoing: "+s);
			if (s.equalsIgnoreCase("stop")) {
				System.out.println("goodbye...");
				break;
			}
		}
	}
}
