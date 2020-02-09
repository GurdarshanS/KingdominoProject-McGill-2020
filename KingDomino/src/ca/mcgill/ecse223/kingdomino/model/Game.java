/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.kingdomino.model;
import java.util.*;

// line 20 "../../../../../KingDomino.ump"
public class Game
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum GameState { PreGame, InGame, PostGame }
  public enum PlayState { StartGame, PauseGame, EndGame }
  public enum PlayerNum { Two, Three, Four }
  public enum GameMode { RegularPlay, DynastyPlay }
  public enum DraftState { Shown, Hidden }
  public enum PlayStatus { ToBePlayed, Played, Discarded }
  public enum DraftStatus { Drafted, Undrafted }
  public enum TurnOrder { First, Second, Third, Fourth }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Game Attributes
  private PlayerNum numPlayers;
  private GameMode gameMode;
  private PlayState playState;
  private GameState gameState;

  //Game Associations
  private List<Draft> drafts;
  private List<Domino> dominos;
  private List<Player> allPlayers;
  private Player currentPlayer;
  private KingDomino kingDomino;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Game(PlayerNum aNumPlayers, GameMode aGameMode, PlayState aPlayState, GameState aGameState)
  {
    numPlayers = aNumPlayers;
    gameMode = aGameMode;
    playState = aPlayState;
    gameState = aGameState;
    drafts = new ArrayList<Draft>();
    dominos = new ArrayList<Domino>();
    allPlayers = new ArrayList<Player>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setNumPlayers(PlayerNum aNumPlayers)
  {
    boolean wasSet = false;
    numPlayers = aNumPlayers;
    wasSet = true;
    return wasSet;
  }

  public boolean setGameMode(GameMode aGameMode)
  {
    boolean wasSet = false;
    gameMode = aGameMode;
    wasSet = true;
    return wasSet;
  }

  public boolean setPlayState(PlayState aPlayState)
  {
    boolean wasSet = false;
    playState = aPlayState;
    wasSet = true;
    return wasSet;
  }

  public boolean setGameState(GameState aGameState)
  {
    boolean wasSet = false;
    gameState = aGameState;
    wasSet = true;
    return wasSet;
  }

  public PlayerNum getNumPlayers()
  {
    return numPlayers;
  }

  public GameMode getGameMode()
  {
    return gameMode;
  }

  public PlayState getPlayState()
  {
    return playState;
  }

  public GameState getGameState()
  {
    return gameState;
  }
  /* Code from template association_GetMany */
  public Draft getDraft(int index)
  {
    Draft aDraft = drafts.get(index);
    return aDraft;
  }

  public List<Draft> getDrafts()
  {
    List<Draft> newDrafts = Collections.unmodifiableList(drafts);
    return newDrafts;
  }

  public int numberOfDrafts()
  {
    int number = drafts.size();
    return number;
  }

  public boolean hasDrafts()
  {
    boolean has = drafts.size() > 0;
    return has;
  }

  public int indexOfDraft(Draft aDraft)
  {
    int index = drafts.indexOf(aDraft);
    return index;
  }
  /* Code from template association_GetMany */
  public Domino getDomino(int index)
  {
    Domino aDomino = dominos.get(index);
    return aDomino;
  }

  public List<Domino> getDominos()
  {
    List<Domino> newDominos = Collections.unmodifiableList(dominos);
    return newDominos;
  }

  public int numberOfDominos()
  {
    int number = dominos.size();
    return number;
  }

  public boolean hasDominos()
  {
    boolean has = dominos.size() > 0;
    return has;
  }

  public int indexOfDomino(Domino aDomino)
  {
    int index = dominos.indexOf(aDomino);
    return index;
  }
  /* Code from template association_GetMany */
  public Player getAllPlayer(int index)
  {
    Player aAllPlayer = allPlayers.get(index);
    return aAllPlayer;
  }

  public List<Player> getAllPlayers()
  {
    List<Player> newAllPlayers = Collections.unmodifiableList(allPlayers);
    return newAllPlayers;
  }

  public int numberOfAllPlayers()
  {
    int number = allPlayers.size();
    return number;
  }

  public boolean hasAllPlayers()
  {
    boolean has = allPlayers.size() > 0;
    return has;
  }

  public int indexOfAllPlayer(Player aAllPlayer)
  {
    int index = allPlayers.indexOf(aAllPlayer);
    return index;
  }
  /* Code from template association_GetOne */
  public Player getCurrentPlayer()
  {
    return currentPlayer;
  }

  public boolean hasCurrentPlayer()
  {
    boolean has = currentPlayer != null;
    return has;
  }
  /* Code from template association_GetOne */
  public KingDomino getKingDomino()
  {
    return kingDomino;
  }

  public boolean hasKingDomino()
  {
    boolean has = kingDomino != null;
    return has;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfDrafts()
  {
    return 0;
  }
  /* Code from template association_AddManyToOptionalOne */
  public boolean addDraft(Draft aDraft)
  {
    boolean wasAdded = false;
    if (drafts.contains(aDraft)) { return false; }
    Game existingGame = aDraft.getGame();
    if (existingGame == null)
    {
      aDraft.setGame(this);
    }
    else if (!this.equals(existingGame))
    {
      existingGame.removeDraft(aDraft);
      addDraft(aDraft);
    }
    else
    {
      drafts.add(aDraft);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeDraft(Draft aDraft)
  {
    boolean wasRemoved = false;
    if (drafts.contains(aDraft))
    {
      drafts.remove(aDraft);
      aDraft.setGame(null);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addDraftAt(Draft aDraft, int index)
  {  
    boolean wasAdded = false;
    if(addDraft(aDraft))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfDrafts()) { index = numberOfDrafts() - 1; }
      drafts.remove(aDraft);
      drafts.add(index, aDraft);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveDraftAt(Draft aDraft, int index)
  {
    boolean wasAdded = false;
    if(drafts.contains(aDraft))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfDrafts()) { index = numberOfDrafts() - 1; }
      drafts.remove(aDraft);
      drafts.add(index, aDraft);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addDraftAt(aDraft, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfDominos()
  {
    return 0;
  }
  /* Code from template association_MaximumNumberOfMethod */
  public static int maximumNumberOfDominos()
  {
    return 48;
  }
  /* Code from template association_AddOptionalNToOptionalOne */
  public boolean addDomino(Domino aDomino)
  {
    boolean wasAdded = false;
    if (dominos.contains(aDomino)) { return false; }
    if (numberOfDominos() >= maximumNumberOfDominos())
    {
      return wasAdded;
    }

    Game existingGame = aDomino.getGame();
    if (existingGame == null)
    {
      aDomino.setGame(this);
    }
    else if (!this.equals(existingGame))
    {
      existingGame.removeDomino(aDomino);
      addDomino(aDomino);
    }
    else
    {
      dominos.add(aDomino);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeDomino(Domino aDomino)
  {
    boolean wasRemoved = false;
    if (dominos.contains(aDomino))
    {
      dominos.remove(aDomino);
      aDomino.setGame(null);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addDominoAt(Domino aDomino, int index)
  {  
    boolean wasAdded = false;
    if(addDomino(aDomino))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfDominos()) { index = numberOfDominos() - 1; }
      dominos.remove(aDomino);
      dominos.add(index, aDomino);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveDominoAt(Domino aDomino, int index)
  {
    boolean wasAdded = false;
    if(dominos.contains(aDomino))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfDominos()) { index = numberOfDominos() - 1; }
      dominos.remove(aDomino);
      dominos.add(index, aDomino);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addDominoAt(aDomino, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfAllPlayers()
  {
    return 0;
  }
  /* Code from template association_MaximumNumberOfMethod */
  public static int maximumNumberOfAllPlayers()
  {
    return 4;
  }
  /* Code from template association_AddOptionalNToOptionalOne */
  public boolean addAllPlayer(Player aAllPlayer)
  {
    boolean wasAdded = false;
    if (allPlayers.contains(aAllPlayer)) { return false; }
    if (numberOfAllPlayers() >= maximumNumberOfAllPlayers())
    {
      return wasAdded;
    }

    Game existingGame = aAllPlayer.getGame();
    if (existingGame == null)
    {
      aAllPlayer.setGame(this);
    }
    else if (!this.equals(existingGame))
    {
      existingGame.removeAllPlayer(aAllPlayer);
      addAllPlayer(aAllPlayer);
    }
    else
    {
      allPlayers.add(aAllPlayer);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeAllPlayer(Player aAllPlayer)
  {
    boolean wasRemoved = false;
    if (allPlayers.contains(aAllPlayer))
    {
      allPlayers.remove(aAllPlayer);
      aAllPlayer.setGame(null);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addAllPlayerAt(Player aAllPlayer, int index)
  {  
    boolean wasAdded = false;
    if(addAllPlayer(aAllPlayer))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfAllPlayers()) { index = numberOfAllPlayers() - 1; }
      allPlayers.remove(aAllPlayer);
      allPlayers.add(index, aAllPlayer);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveAllPlayerAt(Player aAllPlayer, int index)
  {
    boolean wasAdded = false;
    if(allPlayers.contains(aAllPlayer))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfAllPlayers()) { index = numberOfAllPlayers() - 1; }
      allPlayers.remove(aAllPlayer);
      allPlayers.add(index, aAllPlayer);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addAllPlayerAt(aAllPlayer, index);
    }
    return wasAdded;
  }
  /* Code from template association_SetUnidirectionalOptionalOne */
  public boolean setCurrentPlayer(Player aNewCurrentPlayer)
  {
    boolean wasSet = false;
    currentPlayer = aNewCurrentPlayer;
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOptionalOneToMany */
  public boolean setKingDomino(KingDomino aKingDomino)
  {
    boolean wasSet = false;
    KingDomino existingKingDomino = kingDomino;
    kingDomino = aKingDomino;
    if (existingKingDomino != null && !existingKingDomino.equals(aKingDomino))
    {
      existingKingDomino.removeGame(this);
    }
    if (aKingDomino != null)
    {
      aKingDomino.addGame(this);
    }
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    while (drafts.size() > 0)
    {
      Draft aDraft = drafts.get(drafts.size() - 1);
      aDraft.delete();
      drafts.remove(aDraft);
    }
    
    while (dominos.size() > 0)
    {
      Domino aDomino = dominos.get(dominos.size() - 1);
      aDomino.delete();
      dominos.remove(aDomino);
    }
    
    while (allPlayers.size() > 0)
    {
      Player aAllPlayer = allPlayers.get(allPlayers.size() - 1);
      aAllPlayer.delete();
      allPlayers.remove(aAllPlayer);
    }
    
    currentPlayer = null;
    if (kingDomino != null)
    {
      KingDomino placeholderKingDomino = kingDomino;
      this.kingDomino = null;
      placeholderKingDomino.removeGame(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "numPlayers" + "=" + (getNumPlayers() != null ? !getNumPlayers().equals(this)  ? getNumPlayers().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "gameMode" + "=" + (getGameMode() != null ? !getGameMode().equals(this)  ? getGameMode().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "playState" + "=" + (getPlayState() != null ? !getPlayState().equals(this)  ? getPlayState().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "gameState" + "=" + (getGameState() != null ? !getGameState().equals(this)  ? getGameState().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "currentPlayer = "+(getCurrentPlayer()!=null?Integer.toHexString(System.identityHashCode(getCurrentPlayer())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "kingDomino = "+(getKingDomino()!=null?Integer.toHexString(System.identityHashCode(getKingDomino())):"null");
  }
}