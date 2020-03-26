package ca.mcgill.ecse223.kingdomino.development;
import java.util.*;
public class comparatorTest {
		
	public static void main (String [] args) {
		
		Box boxA = new Box(2,4);
		Box boxB = new Box(2,3);
		Box boxC = new Box(1,3);
		
		List<Box> boxes = new ArrayList<Box>();
		boxes.add(boxA);
		boxes.add(boxB);
		boxes.add(boxC);
		
		System.out.println("unsorted");
		for (Box b:boxes) {
			System.out.println(b.getWidth()+"  "+b.getHeight());
		}
		order(boxes);
		Collections.reverse(boxes);
		
		System.out.println("\nsorted");
		for (Box b:boxes) {
			System.out.println(b.getWidth()+"  "+b.getHeight());
		}
		
	}
	
	private static void order(List<Box> boxes) {
		Collections.sort(boxes, new Comparator<Box>() {
			public int compare(Box o1,Box o2) {
				int w1=o1.getWidth();
				int w2=o2.getWidth();
				int h1=o1.getHeight();
				int h2=o2.getHeight();
				
				if (w1>w2) return 1;
				else if (w1<w2) return -1;
				else {
					if (h1>h2) return 1;
					else if (h1<h2) return -1;
					else return 0;
				}
			}
		});
	}
	
	

}
