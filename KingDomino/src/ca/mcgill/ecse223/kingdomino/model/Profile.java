/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.kingdomino.model;
import java.util.*;

// line 103 "../../../../../KingDomino.ump"
public class Profile
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum TurnOrder { First, Second, Third, Fourth }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Profile Attributes
  private String name;
  private int numGamesPlayed;
  private int numGamesWon;
  private int numGamesLost;

  //Profile Associations
  private KingDomino kingDomino;
  private List<Player> profilePlayer;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Profile(String aName, int aNumGamesPlayed, int aNumGamesWon, int aNumGamesLost)
  {
    // line 106 "../../../../../KingDomino.ump"
    if (aName == null || aName.length() == 0) {
    	  		throw new RuntimeException("profile name cannot be empty");
    			}
    // END OF UMPLE BEFORE INJECTION
    name = aName;
    numGamesPlayed = aNumGamesPlayed;
    numGamesWon = aNumGamesWon;
    numGamesLost = aNumGamesLost;
    profilePlayer = new ArrayList<Player>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setName(String aName)
  {
    boolean wasSet = false;
    // line 106 "../../../../../KingDomino.ump"
    if (aName == null || aName.length() == 0) {
    	  		throw new RuntimeException("profile name cannot be empty");
    			}
    // END OF UMPLE BEFORE INJECTION
    name = aName;
    wasSet = true;
    return wasSet;
  }

  public boolean setNumGamesPlayed(int aNumGamesPlayed)
  {
    boolean wasSet = false;
    numGamesPlayed = aNumGamesPlayed;
    wasSet = true;
    return wasSet;
  }

  public boolean setNumGamesWon(int aNumGamesWon)
  {
    boolean wasSet = false;
    numGamesWon = aNumGamesWon;
    wasSet = true;
    return wasSet;
  }

  public boolean setNumGamesLost(int aNumGamesLost)
  {
    boolean wasSet = false;
    numGamesLost = aNumGamesLost;
    wasSet = true;
    return wasSet;
  }

  public String getName()
  {
    return name;
  }

  public int getNumGamesPlayed()
  {
    return numGamesPlayed;
  }

  public int getNumGamesWon()
  {
    return numGamesWon;
  }

  public int getNumGamesLost()
  {
    return numGamesLost;
  }

  public int getNumGamesTied()
  {
    return numGamesPlayed-numGamesWon-numGamesLost;
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
  /* Code from template association_GetMany */
  public Player getProfilePlayer(int index)
  {
    Player aProfilePlayer = profilePlayer.get(index);
    return aProfilePlayer;
  }

  public List<Player> getProfilePlayer()
  {
    List<Player> newProfilePlayer = Collections.unmodifiableList(profilePlayer);
    return newProfilePlayer;
  }

  public int numberOfProfilePlayer()
  {
    int number = profilePlayer.size();
    return number;
  }

  public boolean hasProfilePlayer()
  {
    boolean has = profilePlayer.size() > 0;
    return has;
  }

  public int indexOfProfilePlayer(Player aProfilePlayer)
  {
    int index = profilePlayer.indexOf(aProfilePlayer);
    return index;
  }
  /* Code from template association_SetOptionalOneToMany */
  public boolean setKingDomino(KingDomino aKingDomino)
  {
    boolean wasSet = false;
    KingDomino existingKingDomino = kingDomino;
    kingDomino = aKingDomino;
    if (existingKingDomino != null && !existingKingDomino.equals(aKingDomino))
    {
      existingKingDomino.removeProfile(this);
    }
    if (aKingDomino != null)
    {
      aKingDomino.addProfile(this);
    }
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfProfilePlayer()
  {
    return 0;
  }
  /* Code from template association_AddManyToOptionalOne */
  public boolean addProfilePlayer(Player aProfilePlayer)
  {
    boolean wasAdded = false;
    if (profilePlayer.contains(aProfilePlayer)) { return false; }
    Profile existingLoadedProfile = aProfilePlayer.getLoadedProfile();
    if (existingLoadedProfile == null)
    {
      aProfilePlayer.setLoadedProfile(this);
    }
    else if (!this.equals(existingLoadedProfile))
    {
      existingLoadedProfile.removeProfilePlayer(aProfilePlayer);
      addProfilePlayer(aProfilePlayer);
    }
    else
    {
      profilePlayer.add(aProfilePlayer);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeProfilePlayer(Player aProfilePlayer)
  {
    boolean wasRemoved = false;
    if (profilePlayer.contains(aProfilePlayer))
    {
      profilePlayer.remove(aProfilePlayer);
      aProfilePlayer.setLoadedProfile(null);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addProfilePlayerAt(Player aProfilePlayer, int index)
  {  
    boolean wasAdded = false;
    if(addProfilePlayer(aProfilePlayer))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfProfilePlayer()) { index = numberOfProfilePlayer() - 1; }
      profilePlayer.remove(aProfilePlayer);
      profilePlayer.add(index, aProfilePlayer);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveProfilePlayerAt(Player aProfilePlayer, int index)
  {
    boolean wasAdded = false;
    if(profilePlayer.contains(aProfilePlayer))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfProfilePlayer()) { index = numberOfProfilePlayer() - 1; }
      profilePlayer.remove(aProfilePlayer);
      profilePlayer.add(index, aProfilePlayer);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addProfilePlayerAt(aProfilePlayer, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    if (kingDomino != null)
    {
      KingDomino placeholderKingDomino = kingDomino;
      this.kingDomino = null;
      placeholderKingDomino.removeProfile(this);
    }
    while( !profilePlayer.isEmpty() )
    {
      profilePlayer.get(0).setLoadedProfile(null);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "name" + ":" + getName()+ "," +
            "numGamesPlayed" + ":" + getNumGamesPlayed()+ "," +
            "numGamesWon" + ":" + getNumGamesWon()+ "," +
            "numGamesLost" + ":" + getNumGamesLost()+ "," +
            "numGamesTied" + ":" + getNumGamesTied()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "kingDomino = "+(getKingDomino()!=null?Integer.toHexString(System.identityHashCode(getKingDomino())):"null");
  }
}