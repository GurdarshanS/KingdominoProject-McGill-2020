package ca.mcgill.ecse223.kingdomino.controller;
import ca.mcgill.ecse223.kingdomino.model.TerrainType;

public class PropertyAttribute {
	
	/**
	 * 
	 * This class stores the relevant attributes of a property
	 * 
	 * @see - no direct features, but associated with identifyProperties
	 * @author Jing Han 260528152
	 * @param terrain
	 * @param size 
	 * @param crownNum
	 * @param score
	 */
	
	private TerrainType terrain;
	private int size;
	private int crownNum;
	private int score;
	
	public PropertyAttribute(TerrainType terrain,int size,int crownNum, int score) {
		this.terrain=terrain;
		this.size=size;
		this.crownNum=crownNum;
		this.score=score;
	}
	
	public TerrainType getTerrain() {
		return this.terrain;
	}
	public int getSize() {
		return this.size;
	}
	public int getCrown() {
		return this.crownNum;
	}
	public int getScore() {
		return this.score;
	}
	public String toString() {
		return this.terrain.name()+" ---- size: "+this.size+" --- crowns: "+this.crownNum+" --- score: "+this.score;
	}
}
