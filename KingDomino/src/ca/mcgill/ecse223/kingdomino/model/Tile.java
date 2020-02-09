/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.kingdomino.model;
import java.util.*;

// line 53 "../../../../../KingDomino.ump"
public class Tile
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum LandscapeType { Wheatfield, Lake, Forest, Grass, Mountain, Swamp }
  public enum NumberOfCrowns { Zero, One, Two, Three }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Tile Attributes
  private LandscapeType landscape;
  private NumberOfCrowns numCrowns;

  //Tile Associations
  private Coord coord;
  private List<Tile> closestNeighbors;
  private Domino domino;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Tile(LandscapeType aLandscape, NumberOfCrowns aNumCrowns, Coord aCoord)
  {
    landscape = aLandscape;
    numCrowns = aNumCrowns;
    boolean didAddCoord = setCoord(aCoord);
    if (!didAddCoord)
    {
      throw new RuntimeException("Unable to create tile due to coord");
    }
    closestNeighbors = new ArrayList<Tile>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setLandscape(LandscapeType aLandscape)
  {
    boolean wasSet = false;
    landscape = aLandscape;
    wasSet = true;
    return wasSet;
  }

  public boolean setNumCrowns(NumberOfCrowns aNumCrowns)
  {
    boolean wasSet = false;
    numCrowns = aNumCrowns;
    wasSet = true;
    return wasSet;
  }

  public LandscapeType getLandscape()
  {
    return landscape;
  }

  public NumberOfCrowns getNumCrowns()
  {
    return numCrowns;
  }
  /* Code from template association_GetOne */
  public Coord getCoord()
  {
    return coord;
  }
  /* Code from template association_GetMany */
  public Tile getClosestNeighbor(int index)
  {
    Tile aClosestNeighbor = closestNeighbors.get(index);
    return aClosestNeighbor;
  }

  public List<Tile> getClosestNeighbors()
  {
    List<Tile> newClosestNeighbors = Collections.unmodifiableList(closestNeighbors);
    return newClosestNeighbors;
  }

  public int numberOfClosestNeighbors()
  {
    int number = closestNeighbors.size();
    return number;
  }

  public boolean hasClosestNeighbors()
  {
    boolean has = closestNeighbors.size() > 0;
    return has;
  }

  public int indexOfClosestNeighbor(Tile aClosestNeighbor)
  {
    int index = closestNeighbors.indexOf(aClosestNeighbor);
    return index;
  }
  /* Code from template association_GetOne */
  public Domino getDomino()
  {
    return domino;
  }

  public boolean hasDomino()
  {
    boolean has = domino != null;
    return has;
  }
  /* Code from template association_SetOneToOptionalOne */
  public boolean setCoord(Coord aNewCoord)
  {
    boolean wasSet = false;
    if (aNewCoord == null)
    {
      //Unable to setCoord to null, as tile must always be associated to a coord
      return wasSet;
    }
    
    Tile existingTile = aNewCoord.getTile();
    if (existingTile != null && !equals(existingTile))
    {
      //Unable to setCoord, the current coord already has a tile, which would be orphaned if it were re-assigned
      return wasSet;
    }
    
    Coord anOldCoord = coord;
    coord = aNewCoord;
    coord.setTile(this);

    if (anOldCoord != null)
    {
      anOldCoord.setTile(null);
    }
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfClosestNeighbors()
  {
    return 0;
  }
  /* Code from template association_MaximumNumberOfMethod */
  public static int maximumNumberOfClosestNeighbors()
  {
    return 3;
  }
  /* Code from template association_AddUnidirectionalOptionalN */
  public boolean addClosestNeighbor(Tile aClosestNeighbor)
  {
    boolean wasAdded = false;
    if (closestNeighbors.contains(aClosestNeighbor)) { return false; }
    if (numberOfClosestNeighbors() < maximumNumberOfClosestNeighbors())
    {
      closestNeighbors.add(aClosestNeighbor);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean removeClosestNeighbor(Tile aClosestNeighbor)
  {
    boolean wasRemoved = false;
    if (closestNeighbors.contains(aClosestNeighbor))
    {
      closestNeighbors.remove(aClosestNeighbor);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_SetUnidirectionalOptionalN */
  public boolean setClosestNeighbors(Tile... newClosestNeighbors)
  {
    boolean wasSet = false;
    ArrayList<Tile> verifiedClosestNeighbors = new ArrayList<Tile>();
    for (Tile aClosestNeighbor : newClosestNeighbors)
    {
      if (verifiedClosestNeighbors.contains(aClosestNeighbor))
      {
        continue;
      }
      verifiedClosestNeighbors.add(aClosestNeighbor);
    }

    if (verifiedClosestNeighbors.size() != newClosestNeighbors.length || verifiedClosestNeighbors.size() > maximumNumberOfClosestNeighbors())
    {
      return wasSet;
    }

    closestNeighbors.clear();
    closestNeighbors.addAll(verifiedClosestNeighbors);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addClosestNeighborAt(Tile aClosestNeighbor, int index)
  {  
    boolean wasAdded = false;
    if(addClosestNeighbor(aClosestNeighbor))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfClosestNeighbors()) { index = numberOfClosestNeighbors() - 1; }
      closestNeighbors.remove(aClosestNeighbor);
      closestNeighbors.add(index, aClosestNeighbor);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveClosestNeighborAt(Tile aClosestNeighbor, int index)
  {
    boolean wasAdded = false;
    if(closestNeighbors.contains(aClosestNeighbor))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfClosestNeighbors()) { index = numberOfClosestNeighbors() - 1; }
      closestNeighbors.remove(aClosestNeighbor);
      closestNeighbors.add(index, aClosestNeighbor);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addClosestNeighborAt(aClosestNeighbor, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    Coord existingCoord = coord;
    coord = null;
    if (existingCoord != null)
    {
      existingCoord.delete();
    }
    closestNeighbors.clear();
    if (domino != null)
    {
        Domino placeholderDomino = domino;
        this.domino = null;
        placeholderDomino.delete();
    }
  }


  public String toString()
  {
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "landscape" + "=" + (getLandscape() != null ? !getLandscape().equals(this)  ? getLandscape().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "numCrowns" + "=" + (getNumCrowns() != null ? !getNumCrowns().equals(this)  ? getNumCrowns().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "coord = "+(getCoord()!=null?Integer.toHexString(System.identityHashCode(getCoord())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "domino = "+(getDomino()!=null?Integer.toHexString(System.identityHashCode(getDomino())):"null");
  }
}