package ca.mcgill.ecse223.kingdomino.controller;
import ca.mcgill.ecse223.kingdomino.model.*;
import java.util.*;

class Neighborhood {
	
	/**
	 * 
	 * This class stores the neighborhood surrounding a tile
	 * 
	 * @see - no direct features, but associated with verifyNeighborAdjacencyLastTerritory of KDController
	 * @author Jing Han 260528152
	 * @param neighbors
	 * @param leftOrRight 
	 * @param neighborTerrain
	 * @param neighborCoord
	 */
	
	private List<KingdomTerritory> neighborTerritory;
	private List<String> leftOrRight;
	private List<TerrainType> neighborTerrain;
	private List<int[]> neighborCoord;
	
	public Neighborhood(List<KingdomTerritory> neighbors,List<String> leftOrRight,
						List<TerrainType> neighborTerrain,List<int[]> neighborCoord) {
		this.neighborTerritory=neighbors;
		this.leftOrRight=leftOrRight;
		this.neighborTerrain=neighborTerrain;
		this.neighborCoord=neighborCoord;
	}
	
	public List<KingdomTerritory> getNeighbors(){
		return this.neighborTerritory;
	}
	
	public List<String> getLeftOrRight(){
		return this.leftOrRight;
	}
	
	public List<TerrainType> getNeighborTerrainType(){
		return this.neighborTerrain;
	}
	
	public List<int[]> getNeighborCoord(){
		return this.neighborCoord;
	}
	
	public String toString() {
		
			int id;
			String orientation;
			int [] leftCoord = new int[2];
			String leftTerrain;
			int [] rightCoord = new int[2];
			String rightTerrain;
			int [] edgeCoord = new int[2];
			String edgeTerrain;
			
			String overall="";
			String s="";
			
			
		for (int i=0;i<this.neighborCoord.size();i++) {
			KingdomTerritory d = this.neighborTerritory.get(i);
			String leftRight=this.leftOrRight.get(i);
			
			edgeTerrain = this.neighborTerrain.get(i).name();
			edgeCoord = this.neighborCoord.get(i);
			
			if (d instanceof Castle) {
				id=0;
				orientation="castle";
				leftCoord[0]=d.getX();
				leftCoord[1]=d.getY();
				leftTerrain=edgeTerrain;
				rightCoord = KDQuery.calculateRightPos(d);
				rightTerrain = edgeTerrain;
				s = "id: "+id+" | orientation: "+orientation+" | left coord: "+Arrays.toString(leftCoord)+
						" | left terrain: "+leftTerrain+" | right coord: "+Arrays.toString(rightCoord)+" | right terrain: "+
						rightTerrain+" || "+"edge coord: "+Arrays.toString(edgeCoord)+" | edge terrain: "+edgeTerrain+"\n";
			}
			else if (d instanceof DominoInKingdom) {
				id=((DominoInKingdom) d).getDomino().getId();
				orientation=((DominoInKingdom) d).getDirection().name();
				leftCoord[0]=d.getX();
				leftCoord[1]=d.getY();
				leftTerrain=((DominoInKingdom) d).getDomino().getLeftTile().name();
				rightCoord= KDQuery.calculateRightPos(d);
				rightTerrain=((DominoInKingdom) d).getDomino().getRightTile().name();
				s = "id: "+id+" | orientation: "+orientation+" | left coord: "+Arrays.toString(leftCoord)+
						", left terrain: "+leftTerrain+" | right coord: "+Arrays.toString(rightCoord)+", right terrain: "+
						rightTerrain+" || "+"edge coord: "+Arrays.toString(edgeCoord)+" | edge terrain: "+edgeTerrain+"\n";
				
			}
			overall=overall+s;
		}
		return overall;
	}
}
