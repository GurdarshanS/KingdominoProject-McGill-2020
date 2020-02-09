/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.kingdomino.model;
import java.util.*;

// line 81 "../../../../../KingDomino.ump"
public class Territory
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum PlayStatus { ToBePlayed, Played, Discarded }
  public enum DraftStatus { Drafted, Undrafted }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Territory Attributes
  private int numConnected;
  private int numCrowns;
  private boolean isValid;

  //Territory Associations
  private Kingdom kingdom;
  private List<Domino> territoryDominos;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Territory(int aNumConnected, int aNumCrowns, boolean aIsValid)
  {
    numConnected = aNumConnected;
    numCrowns = aNumCrowns;
    isValid = aIsValid;
    territoryDominos = new ArrayList<Domino>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setNumConnected(int aNumConnected)
  {
    boolean wasSet = false;
    numConnected = aNumConnected;
    wasSet = true;
    return wasSet;
  }

  public boolean setNumCrowns(int aNumCrowns)
  {
    boolean wasSet = false;
    numCrowns = aNumCrowns;
    wasSet = true;
    return wasSet;
  }

  public boolean setIsValid(boolean aIsValid)
  {
    boolean wasSet = false;
    isValid = aIsValid;
    wasSet = true;
    return wasSet;
  }

  public int getNumConnected()
  {
    return numConnected;
  }

  public int getNumCrowns()
  {
    return numCrowns;
  }

  public int getTerritoryScore()
  {
    return numConnected*numCrowns;
  }

  public boolean getIsValid()
  {
    return isValid;
  }
  /* Code from template attribute_IsBoolean */
  public boolean isIsValid()
  {
    return isValid;
  }
  /* Code from template association_GetOne */
  public Kingdom getKingdom()
  {
    return kingdom;
  }

  public boolean hasKingdom()
  {
    boolean has = kingdom != null;
    return has;
  }
  /* Code from template association_GetMany */
  public Domino getTerritoryDomino(int index)
  {
    Domino aTerritoryDomino = territoryDominos.get(index);
    return aTerritoryDomino;
  }

  public List<Domino> getTerritoryDominos()
  {
    List<Domino> newTerritoryDominos = Collections.unmodifiableList(territoryDominos);
    return newTerritoryDominos;
  }

  public int numberOfTerritoryDominos()
  {
    int number = territoryDominos.size();
    return number;
  }

  public boolean hasTerritoryDominos()
  {
    boolean has = territoryDominos.size() > 0;
    return has;
  }

  public int indexOfTerritoryDomino(Domino aTerritoryDomino)
  {
    int index = territoryDominos.indexOf(aTerritoryDomino);
    return index;
  }
  /* Code from template association_SetOptionalOneToMany */
  public boolean setKingdom(Kingdom aKingdom)
  {
    boolean wasSet = false;
    Kingdom existingKingdom = kingdom;
    kingdom = aKingdom;
    if (existingKingdom != null && !existingKingdom.equals(aKingdom))
    {
      existingKingdom.removeTerritory(this);
    }
    if (aKingdom != null)
    {
      aKingdom.addTerritory(this);
    }
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfTerritoryDominos()
  {
    return 0;
  }
  /* Code from template association_AddManyToOptionalOne */
  public boolean addTerritoryDomino(Domino aTerritoryDomino)
  {
    boolean wasAdded = false;
    if (territoryDominos.contains(aTerritoryDomino)) { return false; }
    Territory existingTerritory = aTerritoryDomino.getTerritory();
    if (existingTerritory == null)
    {
      aTerritoryDomino.setTerritory(this);
    }
    else if (!this.equals(existingTerritory))
    {
      existingTerritory.removeTerritoryDomino(aTerritoryDomino);
      addTerritoryDomino(aTerritoryDomino);
    }
    else
    {
      territoryDominos.add(aTerritoryDomino);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeTerritoryDomino(Domino aTerritoryDomino)
  {
    boolean wasRemoved = false;
    if (territoryDominos.contains(aTerritoryDomino))
    {
      territoryDominos.remove(aTerritoryDomino);
      aTerritoryDomino.setTerritory(null);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addTerritoryDominoAt(Domino aTerritoryDomino, int index)
  {  
    boolean wasAdded = false;
    if(addTerritoryDomino(aTerritoryDomino))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfTerritoryDominos()) { index = numberOfTerritoryDominos() - 1; }
      territoryDominos.remove(aTerritoryDomino);
      territoryDominos.add(index, aTerritoryDomino);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveTerritoryDominoAt(Domino aTerritoryDomino, int index)
  {
    boolean wasAdded = false;
    if(territoryDominos.contains(aTerritoryDomino))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfTerritoryDominos()) { index = numberOfTerritoryDominos() - 1; }
      territoryDominos.remove(aTerritoryDomino);
      territoryDominos.add(index, aTerritoryDomino);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addTerritoryDominoAt(aTerritoryDomino, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    if (kingdom != null)
    {
      Kingdom placeholderKingdom = kingdom;
      this.kingdom = null;
      placeholderKingdom.removeTerritory(this);
    }
    while( !territoryDominos.isEmpty() )
    {
      territoryDominos.get(0).setTerritory(null);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "numConnected" + ":" + getNumConnected()+ "," +
            "numCrowns" + ":" + getNumCrowns()+ "," +
            "territoryScore" + ":" + getTerritoryScore()+ "," +
            "isValid" + ":" + getIsValid()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "kingdom = "+(getKingdom()!=null?Integer.toHexString(System.identityHashCode(getKingdom())):"null");
  }
}