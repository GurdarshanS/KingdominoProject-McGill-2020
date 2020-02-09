/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.kingdomino.model;
import java.util.*;

// line 73 "../../../../../KingDomino.ump"
public class Draft
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum DraftState { Shown, Hidden }
  public enum PlayStatus { ToBePlayed, Played, Discarded }
  public enum DraftStatus { Drafted, Undrafted }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Draft Attributes
  private DraftState state;

  //Draft Associations
  private Game game;
  private List<Domino> draftDominos;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Draft(DraftState aState)
  {
    state = aState;
    draftDominos = new ArrayList<Domino>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setState(DraftState aState)
  {
    boolean wasSet = false;
    state = aState;
    wasSet = true;
    return wasSet;
  }

  public DraftState getState()
  {
    return state;
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
  public Domino getDraftDomino(int index)
  {
    Domino aDraftDomino = draftDominos.get(index);
    return aDraftDomino;
  }

  public List<Domino> getDraftDominos()
  {
    List<Domino> newDraftDominos = Collections.unmodifiableList(draftDominos);
    return newDraftDominos;
  }

  public int numberOfDraftDominos()
  {
    int number = draftDominos.size();
    return number;
  }

  public boolean hasDraftDominos()
  {
    boolean has = draftDominos.size() > 0;
    return has;
  }

  public int indexOfDraftDomino(Domino aDraftDomino)
  {
    int index = draftDominos.indexOf(aDraftDomino);
    return index;
  }
  /* Code from template association_SetOptionalOneToMany */
  public boolean setGame(Game aGame)
  {
    boolean wasSet = false;
    Game existingGame = game;
    game = aGame;
    if (existingGame != null && !existingGame.equals(aGame))
    {
      existingGame.removeDraft(this);
    }
    if (aGame != null)
    {
      aGame.addDraft(this);
    }
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfDraftDominos()
  {
    return 0;
  }
  /* Code from template association_MaximumNumberOfMethod */
  public static int maximumNumberOfDraftDominos()
  {
    return 4;
  }
  /* Code from template association_AddOptionalNToOptionalOne */
  public boolean addDraftDomino(Domino aDraftDomino)
  {
    boolean wasAdded = false;
    if (draftDominos.contains(aDraftDomino)) { return false; }
    if (numberOfDraftDominos() >= maximumNumberOfDraftDominos())
    {
      return wasAdded;
    }

    Draft existingDraft = aDraftDomino.getDraft();
    if (existingDraft == null)
    {
      aDraftDomino.setDraft(this);
    }
    else if (!this.equals(existingDraft))
    {
      existingDraft.removeDraftDomino(aDraftDomino);
      addDraftDomino(aDraftDomino);
    }
    else
    {
      draftDominos.add(aDraftDomino);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeDraftDomino(Domino aDraftDomino)
  {
    boolean wasRemoved = false;
    if (draftDominos.contains(aDraftDomino))
    {
      draftDominos.remove(aDraftDomino);
      aDraftDomino.setDraft(null);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addDraftDominoAt(Domino aDraftDomino, int index)
  {  
    boolean wasAdded = false;
    if(addDraftDomino(aDraftDomino))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfDraftDominos()) { index = numberOfDraftDominos() - 1; }
      draftDominos.remove(aDraftDomino);
      draftDominos.add(index, aDraftDomino);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveDraftDominoAt(Domino aDraftDomino, int index)
  {
    boolean wasAdded = false;
    if(draftDominos.contains(aDraftDomino))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfDraftDominos()) { index = numberOfDraftDominos() - 1; }
      draftDominos.remove(aDraftDomino);
      draftDominos.add(index, aDraftDomino);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addDraftDominoAt(aDraftDomino, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    if (game != null)
    {
      Game placeholderGame = game;
      this.game = null;
      placeholderGame.removeDraft(this);
    }
    while( !draftDominos.isEmpty() )
    {
      draftDominos.get(0).setDraft(null);
    }
  }


  public String toString()
  {
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "state" + "=" + (getState() != null ? !getState().equals(this)  ? getState().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "game = "+(getGame()!=null?Integer.toHexString(System.identityHashCode(getGame())):"null");
  }
}