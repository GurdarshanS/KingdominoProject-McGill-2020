package ca.mcgill.ecse223.kingdomino.controller;

public class scratch {
	public static void main(String[] args) {
		int [] a = {1,2,3,4,5};
		
		for (int i=a.length-1;i>0;i--) {
			System.out.println(a[i]);
			System.out.println("------");
			for (int j=i-1;j>-1;j--) {
				System.out.println(a[j]);
			}
			System.out.println("======");
		}
	}
}
