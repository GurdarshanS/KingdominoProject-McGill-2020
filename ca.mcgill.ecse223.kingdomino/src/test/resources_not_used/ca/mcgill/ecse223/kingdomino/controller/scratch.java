package ca.mcgill.ecse223.kingdomino.controller;
import java.util.*;
public class scratch {
	public static void main(String[] args) {

		List<String> playerNames=new ArrayList<String>();
		List<Integer> playerScores = new ArrayList<Integer>();
		List<Integer> scoreCopy = new ArrayList<Integer>();
		List<Integer> ignoreIndex = new ArrayList<Integer>();
		
		playerNames.add("blue");
		playerNames.add("red");
		playerNames.add("yellow");
		playerNames.add("green");
		
		playerScores.add(31);
		playerScores.add(10);
		playerScores.add(101);
		playerScores.add(2);
		
		for (int i=0;i<playerScores.size();i++) {
			System.out.println(playerNames.get(i)+" - "+playerScores.get(i));
			scoreCopy.add(playerScores.get(i));
		}
		
		Collections.sort(scoreCopy);
		Collections.reverse(scoreCopy);
		
		System.out.println(scoreCopy);
		
		for (int i=0;i<scoreCopy.size();i++) {
			int refVal=scoreCopy.get(i);
			for (int j=0;j<playerScores.size();j++) {
				if (!ignoreIndex.contains(j)) {
					int testVal=playerScores.get(j);
					if (testVal==refVal) {
						ignoreIndex.add(j);
						
					}
				}
			}
		}
		

	}
	
}
