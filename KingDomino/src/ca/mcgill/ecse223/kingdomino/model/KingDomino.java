/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.kingdomino.model;
import java.util.*;

// line 15 "../../../../../KingDomino.ump"
public class KingDomino
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum GameState { PreGame, InGame, PostGame }
  public enum PlayState { StartGame, PauseGame, EndGame }
  public enum PlayerNum { Two, Three, Four }
  public enum GameMode { RegularPlay, DynastyPlay }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //KingDomino Associations
  private List<Profile> profiles;
  private List<Game> games;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public KingDomino()
  {
    profiles = new ArrayList<Profile>();
    games = new ArrayList<Game>();
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetMany */
  public Profile getProfile(int index)
  {
    Profile aProfile = profiles.get(index);
    return aProfile;
  }

  public List<Profile> getProfiles()
  {
    List<Profile> newProfiles = Collections.unmodifiableList(profiles);
    return newProfiles;
  }

  public int numberOfProfiles()
  {
    int number = profiles.size();
    return number;
  }

  public boolean hasProfiles()
  {
    boolean has = profiles.size() > 0;
    return has;
  }

  public int indexOfProfile(Profile aProfile)
  {
    int index = profiles.indexOf(aProfile);
    return index;
  }
  /* Code from template association_GetMany */
  public Game getGame(int index)
  {
    Game aGame = games.get(index);
    return aGame;
  }

  public List<Game> getGames()
  {
    List<Game> newGames = Collections.unmodifiableList(games);
    return newGames;
  }

  public int numberOfGames()
  {
    int number = games.size();
    return number;
  }

  public boolean hasGames()
  {
    boolean has = games.size() > 0;
    return has;
  }

  public int indexOfGame(Game aGame)
  {
    int index = games.indexOf(aGame);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfProfiles()
  {
    return 0;
  }
  /* Code from template association_AddManyToOptionalOne */
  public boolean addProfile(Profile aProfile)
  {
    boolean wasAdded = false;
    if (profiles.contains(aProfile)) { return false; }
    KingDomino existingKingDomino = aProfile.getKingDomino();
    if (existingKingDomino == null)
    {
      aProfile.setKingDomino(this);
    }
    else if (!this.equals(existingKingDomino))
    {
      existingKingDomino.removeProfile(aProfile);
      addProfile(aProfile);
    }
    else
    {
      profiles.add(aProfile);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeProfile(Profile aProfile)
  {
    boolean wasRemoved = false;
    if (profiles.contains(aProfile))
    {
      profiles.remove(aProfile);
      aProfile.setKingDomino(null);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addProfileAt(Profile aProfile, int index)
  {  
    boolean wasAdded = false;
    if(addProfile(aProfile))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfProfiles()) { index = numberOfProfiles() - 1; }
      profiles.remove(aProfile);
      profiles.add(index, aProfile);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveProfileAt(Profile aProfile, int index)
  {
    boolean wasAdded = false;
    if(profiles.contains(aProfile))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfProfiles()) { index = numberOfProfiles() - 1; }
      profiles.remove(aProfile);
      profiles.add(index, aProfile);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addProfileAt(aProfile, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfGames()
  {
    return 0;
  }
  /* Code from template association_AddManyToOptionalOne */
  public boolean addGame(Game aGame)
  {
    boolean wasAdded = false;
    if (games.contains(aGame)) { return false; }
    KingDomino existingKingDomino = aGame.getKingDomino();
    if (existingKingDomino == null)
    {
      aGame.setKingDomino(this);
    }
    else if (!this.equals(existingKingDomino))
    {
      existingKingDomino.removeGame(aGame);
      addGame(aGame);
    }
    else
    {
      games.add(aGame);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeGame(Game aGame)
  {
    boolean wasRemoved = false;
    if (games.contains(aGame))
    {
      games.remove(aGame);
      aGame.setKingDomino(null);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addGameAt(Game aGame, int index)
  {  
    boolean wasAdded = false;
    if(addGame(aGame))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfGames()) { index = numberOfGames() - 1; }
      games.remove(aGame);
      games.add(index, aGame);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveGameAt(Game aGame, int index)
  {
    boolean wasAdded = false;
    if(games.contains(aGame))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfGames()) { index = numberOfGames() - 1; }
      games.remove(aGame);
      games.add(index, aGame);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addGameAt(aGame, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    while (profiles.size() > 0)
    {
      Profile aProfile = profiles.get(profiles.size() - 1);
      aProfile.delete();
      profiles.remove(aProfile);
    }
    
    while (games.size() > 0)
    {
      Game aGame = games.get(games.size() - 1);
      aGame.delete();
      games.remove(aGame);
    }
    
  }

}