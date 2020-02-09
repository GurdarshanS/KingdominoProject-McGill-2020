/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.kingdomino.model;
import java.util.*;

// line 92 "../../../../../KingDomino.ump"
public class Kingdom
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum LandscapeType { Wheatfield, Lake, Forest, Grass, Mountain, Swamp }
  public enum NumberOfCrowns { Zero, One, Two, Three }

  //------------------------
  // STATIC VARIABLES
  //------------------------

  public static final int SIZELIM = 5;

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Kingdom Attributes
  private int kingdomScore;
  private int xSize;
  private int ySize;
  private boolean isValid;

  //Kingdom Associations
  private List<Tile> kingdomTiles;
  private List<Territory> territories;
  private Player player;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Kingdom(int aKingdomScore, int aXSize, int aYSize, boolean aIsValid)
  {
    kingdomScore = aKingdomScore;
    xSize = aXSize;
    ySize = aYSize;
    isValid = aIsValid;
    kingdomTiles = new ArrayList<Tile>();
    territories = new ArrayList<Territory>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setKingdomScore(int aKingdomScore)
  {
    boolean wasSet = false;
    kingdomScore = aKingdomScore;
    wasSet = true;
    return wasSet;
  }

  public boolean setXSize(int aXSize)
  {
    boolean wasSet = false;
    xSize = aXSize;
    wasSet = true;
    return wasSet;
  }

  public boolean setYSize(int aYSize)
  {
    boolean wasSet = false;
    ySize = aYSize;
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

  public int getKingdomScore()
  {
    return kingdomScore;
  }

  public int getXSize()
  {
    return xSize;
  }

  public int getYSize()
  {
    return ySize;
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
  /* Code from template association_GetMany */
  public Tile getKingdomTile(int index)
  {
    Tile aKingdomTile = kingdomTiles.get(index);
    return aKingdomTile;
  }

  public List<Tile> getKingdomTiles()
  {
    List<Tile> newKingdomTiles = Collections.unmodifiableList(kingdomTiles);
    return newKingdomTiles;
  }

  public int numberOfKingdomTiles()
  {
    int number = kingdomTiles.size();
    return number;
  }

  public boolean hasKingdomTiles()
  {
    boolean has = kingdomTiles.size() > 0;
    return has;
  }

  public int indexOfKingdomTile(Tile aKingdomTile)
  {
    int index = kingdomTiles.indexOf(aKingdomTile);
    return index;
  }
  /* Code from template association_GetMany */
  public Territory getTerritory(int index)
  {
    Territory aTerritory = territories.get(index);
    return aTerritory;
  }

  public List<Territory> getTerritories()
  {
    List<Territory> newTerritories = Collections.unmodifiableList(territories);
    return newTerritories;
  }

  public int numberOfTerritories()
  {
    int number = territories.size();
    return number;
  }

  public boolean hasTerritories()
  {
    boolean has = territories.size() > 0;
    return has;
  }

  public int indexOfTerritory(Territory aTerritory)
  {
    int index = territories.indexOf(aTerritory);
    return index;
  }
  /* Code from template association_GetOne */
  public Player getPlayer()
  {
    return player;
  }

  public boolean hasPlayer()
  {
    boolean has = player != null;
    return has;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfKingdomTiles()
  {
    return 0;
  }
  /* Code from template association_AddUnidirectionalMany */
  public boolean addKingdomTile(Tile aKingdomTile)
  {
    boolean wasAdded = false;
    if (kingdomTiles.contains(aKingdomTile)) { return false; }
    kingdomTiles.add(aKingdomTile);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeKingdomTile(Tile aKingdomTile)
  {
    boolean wasRemoved = false;
    if (kingdomTiles.contains(aKingdomTile))
    {
      kingdomTiles.remove(aKingdomTile);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addKingdomTileAt(Tile aKingdomTile, int index)
  {  
    boolean wasAdded = false;
    if(addKingdomTile(aKingdomTile))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfKingdomTiles()) { index = numberOfKingdomTiles() - 1; }
      kingdomTiles.remove(aKingdomTile);
      kingdomTiles.add(index, aKingdomTile);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveKingdomTileAt(Tile aKingdomTile, int index)
  {
    boolean wasAdded = false;
    if(kingdomTiles.contains(aKingdomTile))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfKingdomTiles()) { index = numberOfKingdomTiles() - 1; }
      kingdomTiles.remove(aKingdomTile);
      kingdomTiles.add(index, aKingdomTile);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addKingdomTileAt(aKingdomTile, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfTerritories()
  {
    return 0;
  }
  /* Code from template association_AddManyToOptionalOne */
  public boolean addTerritory(Territory aTerritory)
  {
    boolean wasAdded = false;
    if (territories.contains(aTerritory)) { return false; }
    Kingdom existingKingdom = aTerritory.getKingdom();
    if (existingKingdom == null)
    {
      aTerritory.setKingdom(this);
    }
    else if (!this.equals(existingKingdom))
    {
      existingKingdom.removeTerritory(aTerritory);
      addTerritory(aTerritory);
    }
    else
    {
      territories.add(aTerritory);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeTerritory(Territory aTerritory)
  {
    boolean wasRemoved = false;
    if (territories.contains(aTerritory))
    {
      territories.remove(aTerritory);
      aTerritory.setKingdom(null);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addTerritoryAt(Territory aTerritory, int index)
  {  
    boolean wasAdded = false;
    if(addTerritory(aTerritory))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfTerritories()) { index = numberOfTerritories() - 1; }
      territories.remove(aTerritory);
      territories.add(index, aTerritory);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveTerritoryAt(Territory aTerritory, int index)
  {
    boolean wasAdded = false;
    if(territories.contains(aTerritory))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfTerritories()) { index = numberOfTerritories() - 1; }
      territories.remove(aTerritory);
      territories.add(index, aTerritory);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addTerritoryAt(aTerritory, index);
    }
    return wasAdded;
  }
  /* Code from template association_SetOptionalOneToOne */
  public boolean setPlayer(Player aNewPlayer)
  {
    boolean wasSet = false;
    if (player != null && !player.equals(aNewPlayer) && equals(player.getKingdom()))
    {
      //Unable to setPlayer, as existing player would become an orphan
      return wasSet;
    }

    player = aNewPlayer;
    Kingdom anOldKingdom = aNewPlayer != null ? aNewPlayer.getKingdom() : null;

    if (!this.equals(anOldKingdom))
    {
      if (anOldKingdom != null)
      {
        anOldKingdom.player = null;
      }
      if (player != null)
      {
        player.setKingdom(this);
      }
    }
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    kingdomTiles.clear();
    while (territories.size() > 0)
    {
      Territory aTerritory = territories.get(territories.size() - 1);
      aTerritory.delete();
      territories.remove(aTerritory);
    }
    
    Player existingPlayer = player;
    player = null;
    if (existingPlayer != null)
    {
      existingPlayer.delete();
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "kingdomScore" + ":" + getKingdomScore()+ "," +
            "xSize" + ":" + getXSize()+ "," +
            "ySize" + ":" + getYSize()+ "," +
            "isValid" + ":" + getIsValid()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "player = "+(getPlayer()!=null?Integer.toHexString(System.identityHashCode(getPlayer())):"null");
  }
}