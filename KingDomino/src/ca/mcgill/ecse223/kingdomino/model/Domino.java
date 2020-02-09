/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.kingdomino.model;
import java.util.*;

// line 60 "../../../../../KingDomino.ump"
public class Domino
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum LandscapeType { Wheatfield, Lake, Forest, Grass, Mountain, Swamp }
  public enum NumberOfCrowns { Zero, One, Two, Three }
  public enum PlayStatus { ToBePlayed, Played, Discarded }
  public enum DraftStatus { Drafted, Undrafted }

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static Map<Integer, Domino> dominosByRank = new HashMap<Integer, Domino>();

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Domino Attributes
  private int rank;
  private DraftStatus draftStatus;
  private PlayStatus playStatus;

  //Domino Associations
  private List<Tile> tiles;
  private Game game;
  private Player owner;
  private Draft draft;
  private Territory territory;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Domino(int aRank, DraftStatus aDraftStatus, PlayStatus aPlayStatus, Tile... allTiles)
  {
    // line 63 "../../../../../KingDomino.ump"
    if (aRank >= 48 || aRank<=1) {
    	  		throw new RuntimeException("domino rank must be between 1 and 48, inclusively");
    			}
    // END OF UMPLE BEFORE INJECTION
    draftStatus = aDraftStatus;
    playStatus = aPlayStatus;
    if (!setRank(aRank))
    {
      throw new RuntimeException("Cannot create due to duplicate rank");
    }
    tiles = new ArrayList<Tile>();
    boolean didAddTiles = setTiles(allTiles);
    if (!didAddTiles)
    {
      throw new RuntimeException("Unable to create Domino, must have 2 tiles");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setRank(int aRank)
  {
    boolean wasSet = false;
    // line 63 "../../../../../KingDomino.ump"
    if (aRank >= 48 || aRank<=1) {
    	  		throw new RuntimeException("domino rank must be between 1 and 48, inclusively");
    			}
    // END OF UMPLE BEFORE INJECTION
    Integer anOldRank = getRank();
    if (hasWithRank(aRank)) {
      return wasSet;
    }
    rank = aRank;
    wasSet = true;
    if (anOldRank != null) {
      dominosByRank.remove(anOldRank);
    }
    dominosByRank.put(aRank, this);
    return wasSet;
  }

  public boolean setDraftStatus(DraftStatus aDraftStatus)
  {
    boolean wasSet = false;
    draftStatus = aDraftStatus;
    wasSet = true;
    return wasSet;
  }

  public boolean setPlayStatus(PlayStatus aPlayStatus)
  {
    boolean wasSet = false;
    playStatus = aPlayStatus;
    wasSet = true;
    return wasSet;
  }

  public int getRank()
  {
    return rank;
  }
  /* Code from template attribute_GetUnique */
  public static Domino getWithRank(int aRank)
  {
    return dominosByRank.get(aRank);
  }
  /* Code from template attribute_HasUnique */
  public static boolean hasWithRank(int aRank)
  {
    return getWithRank(aRank) != null;
  }

  public DraftStatus getDraftStatus()
  {
    return draftStatus;
  }

  public PlayStatus getPlayStatus()
  {
    return playStatus;
  }
  /* Code from template association_GetMany */
  public Tile getTile(int index)
  {
    Tile aTile = tiles.get(index);
    return aTile;
  }

  /**
   * 0..1 -> 2 Tile tiles;
   */
  public List<Tile> getTiles()
  {
    List<Tile> newTiles = Collections.unmodifiableList(tiles);
    return newTiles;
  }

  public int numberOfTiles()
  {
    int number = tiles.size();
    return number;
  }

  public boolean hasTiles()
  {
    boolean has = tiles.size() > 0;
    return has;
  }

  public int indexOfTile(Tile aTile)
  {
    int index = tiles.indexOf(aTile);
    return index;
  }
  /* Code from template association_GetOne */
  public Game getGame()
  {
    return game;
  }

  public boolean hasGame()
  {
    boolean has = game != null;
    return has;
  }
  /* Code from template association_GetOne */
  public Player getOwner()
  {
    return owner;
  }

  public boolean hasOwner()
  {
    boolean has = owner != null;
    return has;
  }
  /* Code from template association_GetOne */
  public Draft getDraft()
  {
    return draft;
  }

  public boolean hasDraft()
  {
    boolean has = draft != null;
    return has;
  }
  /* Code from template association_GetOne */
  public Territory getTerritory()
  {
    return territory;
  }

  public boolean hasTerritory()
  {
    boolean has = territory != null;
    return has;
  }
  /* Code from template association_RequiredNumberOfMethod */
  public static int requiredNumberOfTiles()
  {
    return 2;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfTiles()
  {
    return 2;
  }
  /* Code from template association_MaximumNumberOfMethod */
  public static int maximumNumberOfTiles()
  {
    return 2;
  }
  /* Code from template association_SetNToOptionalOne */
  public boolean setTiles(Tile... newTiles)
  {
    boolean wasSet = false;
    ArrayList<Tile> checkNewTiles = new ArrayList<Tile>();
    for (Tile aTile : newTiles)
    {
      if (checkNewTiles.contains(aTile))
      {
        return wasSet;
      }
      else if (aTile.getDomino() != null && !this.equals(aTile.getDomino()))
      {
        return wasSet;
      }
      checkNewTiles.add(aTile);
    }

    if (checkNewTiles.size() != minimumNumberOfTiles())
    {
      return wasSet;
    }

    tiles.removeAll(checkNewTiles);
    
    for (Tile orphan : tiles)
    {
      setDomino(orphan, null);
    }
    tiles.clear();
    for (Tile aTile : newTiles)
    {
      setDomino(aTile, this);
      tiles.add(aTile);
    }
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_GetPrivate */
  private void setDomino(Tile aTile, Domino aDomino)
  {
    try
    {
      java.lang.reflect.Field mentorField = aTile.getClass().getDeclaredField("domino");
      mentorField.setAccessible(true);
      mentorField.set(aTile, aDomino);
    }
    catch (Exception e)
    {
      throw new RuntimeException("Issue internally setting aDomino to aTile", e);
    }
  }
  /* Code from template association_SetOptionalOneToOptionalN */
  public boolean setGame(Game aGame)
  {
    boolean wasSet = false;
    if (aGame != null && aGame.numberOfDominos() >= Game.maximumNumberOfDominos())
    {
      return wasSet;
    }

    Game existingGame = game;
    game = aGame;
    if (existingGame != null && !existingGame.equals(aGame))
    {
      existingGame.removeDomino(this);
    }
    if (aGame != null)
    {
      aGame.addDomino(this);
    }
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOptionalOneToMany */
  public boolean setOwner(Player aOwner)
  {
    boolean wasSet = false;
    Player existingOwner = owner;
    owner = aOwner;
    if (existingOwner != null && !existingOwner.equals(aOwner))
    {
      existingOwner.removeOwnedDomino(this);
    }
    if (aOwner != null)
    {
      aOwner.addOwnedDomino(this);
    }
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOptionalOneToOptionalN */
  public boolean setDraft(Draft aDraft)
  {
    boolean wasSet = false;
    if (aDraft != null && aDraft.numberOfDraftDominos() >= Draft.maximumNumberOfDraftDominos())
    {
      return wasSet;
    }

    Draft existingDraft = draft;
    draft = aDraft;
    if (existingDraft != null && !existingDraft.equals(aDraft))
    {
      existingDraft.removeDraftDomino(this);
    }
    if (aDraft != null)
    {
      aDraft.addDraftDomino(this);
    }
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOptionalOneToMany */
  public boolean setTerritory(Territory aTerritory)
  {
    boolean wasSet = false;
    Territory existingTerritory = territory;
    territory = aTerritory;
    if (existingTerritory != null && !existingTerritory.equals(aTerritory))
    {
      existingTerritory.removeTerritoryDomino(this);
    }
    if (aTerritory != null)
    {
      aTerritory.addTerritoryDomino(this);
    }
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    dominosByRank.remove(getRank());
    while (tiles.size() > 0)
    {
      Tile aTile = tiles.get(tiles.size() - 1);
      aTile.delete();
      tiles.remove(aTile);
    }
    
    if (game != null)
    {
      Game placeholderGame = game;
      this.game = null;
      placeholderGame.removeDomino(this);
    }
    if (owner != null)
    {
      Player placeholderOwner = owner;
      this.owner = null;
      placeholderOwner.removeOwnedDomino(this);
    }
    if (draft != null)
    {
      Draft placeholderDraft = draft;
      this.draft = null;
      placeholderDraft.removeDraftDomino(this);
    }
    if (territory != null)
    {
      Territory placeholderTerritory = territory;
      this.territory = null;
      placeholderTerritory.removeTerritoryDomino(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "rank" + ":" + getRank()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "draftStatus" + "=" + (getDraftStatus() != null ? !getDraftStatus().equals(this)  ? getDraftStatus().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "playStatus" + "=" + (getPlayStatus() != null ? !getPlayStatus().equals(this)  ? getPlayStatus().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "game = "+(getGame()!=null?Integer.toHexString(System.identityHashCode(getGame())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "owner = "+(getOwner()!=null?Integer.toHexString(System.identityHashCode(getOwner())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "draft = "+(getDraft()!=null?Integer.toHexString(System.identityHashCode(getDraft())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "territory = "+(getTerritory()!=null?Integer.toHexString(System.identityHashCode(getTerritory())):"null");
  }
}