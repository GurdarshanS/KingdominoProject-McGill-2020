/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.kingdomino.model;
import java.util.*;

// line 32 "../../../../../KingDomino.ump"
public class Player
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum PlayStatus { ToBePlayed, Played, Discarded }
  public enum DraftStatus { Drafted, Undrafted }
  public enum TurnOrder { First, Second, Third, Fourth }

  //------------------------
  // STATIC VARIABLES
  //------------------------

  public static final int CASTLEX = 0;
  public static final int CASTLEY = 0;

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Player Attributes
  private boolean loadProfile;
  private TurnOrder turnOrder;
  private int currentScore;
  private String castleColor;

  //Player Associations
  private Domino currentDomino;
  private Kingdom kingdom;
  private Game game;
  private List<Domino> ownedDominos;
  private Profile loadedProfile;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Player(boolean aLoadProfile, TurnOrder aTurnOrder, int aCurrentScore, String aCastleColor, Kingdom aKingdom)
  {
    loadProfile = aLoadProfile;
    turnOrder = aTurnOrder;
    currentScore = aCurrentScore;
    castleColor = aCastleColor;
    boolean didAddKingdom = setKingdom(aKingdom);
    if (!didAddKingdom)
    {
      throw new RuntimeException("Unable to create player due to kingdom");
    }
    ownedDominos = new ArrayList<Domino>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setLoadProfile(boolean aLoadProfile)
  {
    boolean wasSet = false;
    loadProfile = aLoadProfile;
    wasSet = true;
    return wasSet;
  }

  public boolean setTurnOrder(TurnOrder aTurnOrder)
  {
    boolean wasSet = false;
    turnOrder = aTurnOrder;
    wasSet = true;
    return wasSet;
  }

  public boolean setCurrentScore(int aCurrentScore)
  {
    boolean wasSet = false;
    currentScore = aCurrentScore;
    wasSet = true;
    return wasSet;
  }

  public boolean setCastleColor(String aCastleColor)
  {
    boolean wasSet = false;
    castleColor = aCastleColor;
    wasSet = true;
    return wasSet;
  }

  public boolean getLoadProfile()
  {
    return loadProfile;
  }

  public TurnOrder getTurnOrder()
  {
    return turnOrder;
  }

  public int getCurrentScore()
  {
    return currentScore;
  }

  public String getCastleColor()
  {
    return castleColor;
  }
  /* Code from template attribute_IsBoolean */
  public boolean isLoadProfile()
  {
    return loadProfile;
  }
  /* Code from template association_GetOne */
  public Domino getCurrentDomino()
  {
    return currentDomino;
  }

  public boolean hasCurrentDomino()
  {
    boolean has = currentDomino != null;
    return has;
  }
  /* Code from template association_GetOne */
  public Kingdom getKingdom()
  {
    return kingdom;
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
  /* Code from template association_GetMany */
  public Domino getOwnedDomino(int index)
  {
    Domino aOwnedDomino = ownedDominos.get(index);
    return aOwnedDomino;
  }

  public List<Domino> getOwnedDominos()
  {
    List<Domino> newOwnedDominos = Collections.unmodifiableList(ownedDominos);
    return newOwnedDominos;
  }

  public int numberOfOwnedDominos()
  {
    int number = ownedDominos.size();
    return number;
  }

  public boolean hasOwnedDominos()
  {
    boolean has = ownedDominos.size() > 0;
    return has;
  }

  public int indexOfOwnedDomino(Domino aOwnedDomino)
  {
    int index = ownedDominos.indexOf(aOwnedDomino);
    return index;
  }
  /* Code from template association_GetOne */
  public Profile getLoadedProfile()
  {
    return loadedProfile;
  }

  public boolean hasLoadedProfile()
  {
    boolean has = loadedProfile != null;
    return has;
  }
  /* Code from template association_SetUnidirectionalOptionalOne */
  public boolean setCurrentDomino(Domino aNewCurrentDomino)
  {
    boolean wasSet = false;
    currentDomino = aNewCurrentDomino;
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToOptionalOne */
  public boolean setKingdom(Kingdom aNewKingdom)
  {
    boolean wasSet = false;
    if (aNewKingdom == null)
    {
      //Unable to setKingdom to null, as player must always be associated to a kingdom
      return wasSet;
    }
    
    Player existingPlayer = aNewKingdom.getPlayer();
    if (existingPlayer != null && !equals(existingPlayer))
    {
      //Unable to setKingdom, the current kingdom already has a player, which would be orphaned if it were re-assigned
      return wasSet;
    }
    
    Kingdom anOldKingdom = kingdom;
    kingdom = aNewKingdom;
    kingdom.setPlayer(this);

    if (anOldKingdom != null)
    {
      anOldKingdom.setPlayer(null);
    }
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOptionalOneToOptionalN */
  public boolean setGame(Game aGame)
  {
    boolean wasSet = false;
    if (aGame != null && aGame.numberOfAllPlayers() >= Game.maximumNumberOfAllPlayers())
    {
      return wasSet;
    }

    Game existingGame = game;
    game = aGame;
    if (existingGame != null && !existingGame.equals(aGame))
    {
      existingGame.removeAllPlayer(this);
    }
    if (aGame != null)
    {
      aGame.addAllPlayer(this);
    }
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfOwnedDominos()
  {
    return 0;
  }
  /* Code from template association_AddManyToOptionalOne */
  public boolean addOwnedDomino(Domino aOwnedDomino)
  {
    boolean wasAdded = false;
    if (ownedDominos.contains(aOwnedDomino)) { return false; }
    Player existingOwner = aOwnedDomino.getOwner();
    if (existingOwner == null)
    {
      aOwnedDomino.setOwner(this);
    }
    else if (!this.equals(existingOwner))
    {
      existingOwner.removeOwnedDomino(aOwnedDomino);
      addOwnedDomino(aOwnedDomino);
    }
    else
    {
      ownedDominos.add(aOwnedDomino);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeOwnedDomino(Domino aOwnedDomino)
  {
    boolean wasRemoved = false;
    if (ownedDominos.contains(aOwnedDomino))
    {
      ownedDominos.remove(aOwnedDomino);
      aOwnedDomino.setOwner(null);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addOwnedDominoAt(Domino aOwnedDomino, int index)
  {  
    boolean wasAdded = false;
    if(addOwnedDomino(aOwnedDomino))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfOwnedDominos()) { index = numberOfOwnedDominos() - 1; }
      ownedDominos.remove(aOwnedDomino);
      ownedDominos.add(index, aOwnedDomino);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveOwnedDominoAt(Domino aOwnedDomino, int index)
  {
    boolean wasAdded = false;
    if(ownedDominos.contains(aOwnedDomino))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfOwnedDominos()) { index = numberOfOwnedDominos() - 1; }
      ownedDominos.remove(aOwnedDomino);
      ownedDominos.add(index, aOwnedDomino);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addOwnedDominoAt(aOwnedDomino, index);
    }
    return wasAdded;
  }
  /* Code from template association_SetOptionalOneToMany */
  public boolean setLoadedProfile(Profile aLoadedProfile)
  {
    boolean wasSet = false;
    Profile existingLoadedProfile = loadedProfile;
    loadedProfile = aLoadedProfile;
    if (existingLoadedProfile != null && !existingLoadedProfile.equals(aLoadedProfile))
    {
      existingLoadedProfile.removeProfilePlayer(this);
    }
    if (aLoadedProfile != null)
    {
      aLoadedProfile.addProfilePlayer(this);
    }
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    currentDomino = null;
    Kingdom existingKingdom = kingdom;
    kingdom = null;
    if (existingKingdom != null)
    {
      existingKingdom.delete();
    }
    if (game != null)
    {
      Game placeholderGame = game;
      this.game = null;
      placeholderGame.removeAllPlayer(this);
    }
    while( !ownedDominos.isEmpty() )
    {
      ownedDominos.get(0).setOwner(null);
    }
    if (loadedProfile != null)
    {
      Profile placeholderLoadedProfile = loadedProfile;
      this.loadedProfile = null;
      placeholderLoadedProfile.removeProfilePlayer(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "loadProfile" + ":" + getLoadProfile()+ "," +
            "currentScore" + ":" + getCurrentScore()+ "," +
            "castleColor" + ":" + getCastleColor()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "turnOrder" + "=" + (getTurnOrder() != null ? !getTurnOrder().equals(this)  ? getTurnOrder().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "currentDomino = "+(getCurrentDomino()!=null?Integer.toHexString(System.identityHashCode(getCurrentDomino())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "kingdom = "+(getKingdom()!=null?Integer.toHexString(System.identityHashCode(getKingdom())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "game = "+(getGame()!=null?Integer.toHexString(System.identityHashCode(getGame())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "loadedProfile = "+(getLoadedProfile()!=null?Integer.toHexString(System.identityHashCode(getLoadedProfile())):"null");
  }
}