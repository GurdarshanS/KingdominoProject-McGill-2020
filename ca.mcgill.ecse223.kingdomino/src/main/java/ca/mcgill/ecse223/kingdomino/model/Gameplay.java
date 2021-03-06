/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.kingdomino.model;
import java.io.Serializable;
import ca.mcgill.ecse223.kingdomino.controller.*;
import ca.mcgill.ecse223.kingdomino.model.*;
import ca.mcgill.ecse223.kingdomino.*;
import java.util.*;
// line 4 "../../../../../Gameplay.ump"
public class Gameplay implements Serializable
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Gameplay State Machines
  public enum Gamestatus { Initializing, Playing, Finishing, EndingGame }
  public enum GamestatusInitializing { Null, CreatingFirstDraft, SelectingFirstDomino }
  public enum GamestatusPlaying { Null, CreatingNextDraft, RevealingNextDraft, ManipulatingDomino, ConfirmingChoice, SelectingStandardDomino }
  public enum GamestatusPlayingManipulatingDomino { Null, IdlingDomino, RotatingDomino, MovingDomino }
  public enum GamestatusFinishing { Null, ManipulatingLastDomino, ConfirmingLastChoice }
  public enum GamestatusFinishingManipulatingLastDomino { Null, IdlingLastDomino, RotatingLastDomino, MovingLastDomino }
  private Gamestatus gamestatus;
  private GamestatusInitializing gamestatusInitializing;
  private GamestatusPlaying gamestatusPlaying;
  private GamestatusPlayingManipulatingDomino gamestatusPlayingManipulatingDomino;
  private GamestatusFinishing gamestatusFinishing;
  private GamestatusFinishingManipulatingLastDomino gamestatusFinishingManipulatingLastDomino;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Gameplay()
  {
    setGamestatusInitializing(GamestatusInitializing.Null);
    setGamestatusPlaying(GamestatusPlaying.Null);
    setGamestatusPlayingManipulatingDomino(GamestatusPlayingManipulatingDomino.Null);
    setGamestatusFinishing(GamestatusFinishing.Null);
    setGamestatusFinishingManipulatingLastDomino(GamestatusFinishingManipulatingLastDomino.Null);
    setGamestatus(Gamestatus.Initializing);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public String getGamestatusFullName()
  {
    String answer = gamestatus.toString();
    if (gamestatusInitializing != GamestatusInitializing.Null) { answer += "." + gamestatusInitializing.toString(); }
    if (gamestatusPlaying != GamestatusPlaying.Null) { answer += "." + gamestatusPlaying.toString(); }
    if (gamestatusPlayingManipulatingDomino != GamestatusPlayingManipulatingDomino.Null) { answer += "." + gamestatusPlayingManipulatingDomino.toString(); }
    if (gamestatusFinishing != GamestatusFinishing.Null) { answer += "." + gamestatusFinishing.toString(); }
    if (gamestatusFinishingManipulatingLastDomino != GamestatusFinishingManipulatingLastDomino.Null) { answer += "." + gamestatusFinishingManipulatingLastDomino.toString(); }
    return answer;
  }

  public Gamestatus getGamestatus()
  {
    return gamestatus;
  }

  public GamestatusInitializing getGamestatusInitializing()
  {
    return gamestatusInitializing;
  }

  public GamestatusPlaying getGamestatusPlaying()
  {
    return gamestatusPlaying;
  }

  public GamestatusPlayingManipulatingDomino getGamestatusPlayingManipulatingDomino()
  {
    return gamestatusPlayingManipulatingDomino;
  }

  public GamestatusFinishing getGamestatusFinishing()
  {
    return gamestatusFinishing;
  }

  public GamestatusFinishingManipulatingLastDomino getGamestatusFinishingManipulatingLastDomino()
  {
    return gamestatusFinishingManipulatingLastDomino;
  }

  public boolean draftReady()
  {
    boolean wasEventProcessed = false;
    
    GamestatusInitializing aGamestatusInitializing = gamestatusInitializing;
    GamestatusPlaying aGamestatusPlaying = gamestatusPlaying;
    switch (aGamestatusInitializing)
    {
      case CreatingFirstDraft:
        exitGamestatusInitializing();
        // line 15 "../../../../../Gameplay.ump"
        revealNextDraft(); generateInitialPlayerOrder();
        setGamestatusInitializing(GamestatusInitializing.SelectingFirstDomino);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    switch (aGamestatusPlaying)
    {
      case CreatingNextDraft:
        exitGamestatusPlaying();
        setGamestatusPlaying(GamestatusPlaying.RevealingNextDraft);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean choose(Domino domino)
  {
    boolean wasEventProcessed = false;
    
    GamestatusInitializing aGamestatusInitializing = gamestatusInitializing;
    GamestatusPlaying aGamestatusPlaying = gamestatusPlaying;
    switch (aGamestatusInitializing)
    {
      case SelectingFirstDomino:
        if (!(isDominoPileEmpty())&&!(isDominoTaken(domino))&&!(hasAllPlayersChosen()))
        {
          exitGamestatusInitializing();
        // line 19 "../../../../../Gameplay.ump"
          chooseDomino(domino);
          setGamestatusInitializing(GamestatusInitializing.SelectingFirstDomino);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    switch (aGamestatusPlaying)
    {
      case ConfirmingChoice:
        if (!(isDominoPileEmpty())&&!(isDominoTaken(domino))&&!(hasAllPlayersChosen()))
        {
          exitGamestatusPlaying();
        // line 59 "../../../../../Gameplay.ump"
          chooseDomino(domino);
          setGamestatusPlaying(GamestatusPlaying.SelectingStandardDomino);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean selectionReady()
  {
    boolean wasEventProcessed = false;
    
    GamestatusInitializing aGamestatusInitializing = gamestatusInitializing;
    switch (aGamestatusInitializing)
    {
      case SelectingFirstDomino:
        if (hasAllPlayersChosen())
        {
          exitGamestatus();
          setGamestatus(Gamestatus.Playing);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean manipulateFirst(int posx,int posy,String dir)
  {
    boolean wasEventProcessed = false;
    
    GamestatusPlaying aGamestatusPlaying = gamestatusPlaying;
    switch (aGamestatusPlaying)
    {
      case RevealingNextDraft:
        exitGamestatusPlaying();
        // line 33 "../../../../../Gameplay.ump"
        preplaceLatestDomino(posx,posy,dir);
        setGamestatusPlaying(GamestatusPlaying.ManipulatingDomino);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean discard()
  {
    boolean wasEventProcessed = false;
    
    GamestatusPlaying aGamestatusPlaying = gamestatusPlaying;
    GamestatusFinishing aGamestatusFinishing = gamestatusFinishing;
    switch (aGamestatusPlaying)
    {
      case ManipulatingDomino:
        if (!(isThereAvailablePlacement()))
        {
          exitGamestatusPlaying();
        // line 38 "../../../../../Gameplay.ump"
          discardLatestDomino();
          setGamestatusPlaying(GamestatusPlaying.ConfirmingChoice);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    switch (aGamestatusFinishing)
    {
      case ManipulatingLastDomino:
        if (!(isThereAvailablePlacement()))
        {
          exitGamestatusFinishing();
        // line 74 "../../../../../Gameplay.ump"
          discardLatestDomino();
          setGamestatusFinishing(GamestatusFinishing.ConfirmingLastChoice);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean place()
  {
    boolean wasEventProcessed = false;
    
    GamestatusPlaying aGamestatusPlaying = gamestatusPlaying;
    GamestatusFinishing aGamestatusFinishing = gamestatusFinishing;
    switch (aGamestatusPlaying)
    {
      case ManipulatingDomino:
        if (verifyDomino())
        {
          exitGamestatusPlaying();
        // line 39 "../../../../../Gameplay.ump"
          placeLatestDomino();updatePlayerScore();
          setGamestatusPlaying(GamestatusPlaying.ConfirmingChoice);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    switch (aGamestatusFinishing)
    {
      case ManipulatingLastDomino:
        if (verifyDomino())
        {
          exitGamestatusFinishing();
        // line 75 "../../../../../Gameplay.ump"
          placeLatestDomino();updatePlayerScore();
          setGamestatusFinishing(GamestatusFinishing.ConfirmingLastChoice);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean manipulateNext(int posx,int posy,String dir)
  {
    boolean wasEventProcessed = false;
    
    GamestatusPlaying aGamestatusPlaying = gamestatusPlaying;
    switch (aGamestatusPlaying)
    {
      case SelectingStandardDomino:
        if (!(lastPlayerInTurn()))
        {
          exitGamestatusPlaying();
        // line 63 "../../../../../Gameplay.ump"
          preplaceLatestDomino(posx,posy,dir);
          setGamestatusPlaying(GamestatusPlaying.ManipulatingDomino);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean nextSelectionReady()
  {
    boolean wasEventProcessed = false;
    
    GamestatusPlaying aGamestatusPlaying = gamestatusPlaying;
    switch (aGamestatusPlaying)
    {
      case SelectingStandardDomino:
        if (lastPlayerInTurn()&&!isDominoPileEmpty())
        {
          exitGamestatus();
          setGamestatus(Gamestatus.Playing);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean lastSelectionReady(int posx,int posy,String dir)
  {
    boolean wasEventProcessed = false;
    
    GamestatusPlaying aGamestatusPlaying = gamestatusPlaying;
    switch (aGamestatusPlaying)
    {
      case SelectingStandardDomino:
        if (isDominoPileEmpty()&&lastPlayerInTurn())
        {
          exitGamestatus();
        // line 65 "../../../../../Gameplay.ump"
          updatePlayerOrder();preplaceLatestDomino(posx,posy,dir);
          setGamestatus(Gamestatus.Finishing);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean rotate(String dir)
  {
    boolean wasEventProcessed = false;
    
    GamestatusPlayingManipulatingDomino aGamestatusPlayingManipulatingDomino = gamestatusPlayingManipulatingDomino;
    GamestatusFinishingManipulatingLastDomino aGamestatusFinishingManipulatingLastDomino = gamestatusFinishingManipulatingLastDomino;
    switch (aGamestatusPlayingManipulatingDomino)
    {
      case IdlingDomino:
        exitGamestatusPlayingManipulatingDomino();
        // line 42 "../../../../../Gameplay.ump"
        rotateLatestDomino(dir);
        setGamestatusPlayingManipulatingDomino(GamestatusPlayingManipulatingDomino.RotatingDomino);
        wasEventProcessed = true;
        break;
      case RotatingDomino:
        exitGamestatusPlayingManipulatingDomino();
        // line 47 "../../../../../Gameplay.ump"
        rotateLatestDomino(dir);
        setGamestatusPlayingManipulatingDomino(GamestatusPlayingManipulatingDomino.RotatingDomino);
        wasEventProcessed = true;
        break;
      case MovingDomino:
        exitGamestatusPlayingManipulatingDomino();
        // line 52 "../../../../../Gameplay.ump"
        rotateLatestDomino(dir);
        setGamestatusPlayingManipulatingDomino(GamestatusPlayingManipulatingDomino.RotatingDomino);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    switch (aGamestatusFinishingManipulatingLastDomino)
    {
      case IdlingLastDomino:
        exitGamestatusFinishingManipulatingLastDomino();
        // line 78 "../../../../../Gameplay.ump"
        rotateLatestDomino(dir);
        setGamestatusFinishingManipulatingLastDomino(GamestatusFinishingManipulatingLastDomino.RotatingLastDomino);
        wasEventProcessed = true;
        break;
      case RotatingLastDomino:
        exitGamestatusFinishingManipulatingLastDomino();
        // line 83 "../../../../../Gameplay.ump"
        rotateLatestDomino(dir);
        setGamestatusFinishingManipulatingLastDomino(GamestatusFinishingManipulatingLastDomino.RotatingLastDomino);
        wasEventProcessed = true;
        break;
      case MovingLastDomino:
        exitGamestatusFinishingManipulatingLastDomino();
        // line 88 "../../../../../Gameplay.ump"
        rotateLatestDomino(dir);
        setGamestatusFinishingManipulatingLastDomino(GamestatusFinishingManipulatingLastDomino.RotatingLastDomino);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean move(String movement)
  {
    boolean wasEventProcessed = false;
    
    GamestatusPlayingManipulatingDomino aGamestatusPlayingManipulatingDomino = gamestatusPlayingManipulatingDomino;
    GamestatusFinishingManipulatingLastDomino aGamestatusFinishingManipulatingLastDomino = gamestatusFinishingManipulatingLastDomino;
    switch (aGamestatusPlayingManipulatingDomino)
    {
      case IdlingDomino:
        exitGamestatusPlayingManipulatingDomino();
        // line 43 "../../../../../Gameplay.ump"
        moveLatestDomino(movement);
        setGamestatusPlayingManipulatingDomino(GamestatusPlayingManipulatingDomino.MovingDomino);
        wasEventProcessed = true;
        break;
      case RotatingDomino:
        exitGamestatusPlayingManipulatingDomino();
        // line 48 "../../../../../Gameplay.ump"
        moveLatestDomino(movement);
        setGamestatusPlayingManipulatingDomino(GamestatusPlayingManipulatingDomino.MovingDomino);
        wasEventProcessed = true;
        break;
      case MovingDomino:
        exitGamestatusPlayingManipulatingDomino();
        // line 53 "../../../../../Gameplay.ump"
        moveLatestDomino(movement);
        setGamestatusPlayingManipulatingDomino(GamestatusPlayingManipulatingDomino.MovingDomino);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    switch (aGamestatusFinishingManipulatingLastDomino)
    {
      case IdlingLastDomino:
        exitGamestatusFinishingManipulatingLastDomino();
        // line 79 "../../../../../Gameplay.ump"
        moveLatestDomino(movement);
        setGamestatusFinishingManipulatingLastDomino(GamestatusFinishingManipulatingLastDomino.MovingLastDomino);
        wasEventProcessed = true;
        break;
      case RotatingLastDomino:
        exitGamestatusFinishingManipulatingLastDomino();
        // line 84 "../../../../../Gameplay.ump"
        moveLatestDomino(movement);
        setGamestatusFinishingManipulatingLastDomino(GamestatusFinishingManipulatingLastDomino.MovingLastDomino);
        wasEventProcessed = true;
        break;
      case MovingLastDomino:
        exitGamestatusFinishingManipulatingLastDomino();
        // line 89 "../../../../../Gameplay.ump"
        moveLatestDomino(movement);
        setGamestatusFinishingManipulatingLastDomino(GamestatusFinishingManipulatingLastDomino.MovingLastDomino);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean manipulateLast(int posx,int posy,String dir)
  {
    boolean wasEventProcessed = false;
    
    GamestatusFinishing aGamestatusFinishing = gamestatusFinishing;
    switch (aGamestatusFinishing)
    {
      case ConfirmingLastChoice:
        if (!(hasAllPlayersPlayed()))
        {
          exitGamestatusFinishing();
        // line 94 "../../../../../Gameplay.ump"
          setNextPlayer();preplaceLatestDomino(posx,posy,dir);
          setGamestatusFinishing(GamestatusFinishing.ManipulatingLastDomino);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean scoring()
  {
    boolean wasEventProcessed = false;
    
    GamestatusFinishing aGamestatusFinishing = gamestatusFinishing;
    switch (aGamestatusFinishing)
    {
      case ConfirmingLastChoice:
        if (hasAllPlayersPlayed())
        {
          exitGamestatus();
        // line 95 "../../../../../Gameplay.ump"
          calculateAllPlayerScores();calculatePlayerRanking();
          setGamestatus(Gamestatus.EndingGame);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  private void exitGamestatus()
  {
    switch(gamestatus)
    {
      case Initializing:
        exitGamestatusInitializing();
        break;
      case Playing:
        exitGamestatusPlaying();
        break;
      case Finishing:
        exitGamestatusFinishing();
        break;
    }
  }

  private void setGamestatus(Gamestatus aGamestatus)
  {
    gamestatus = aGamestatus;

    // entry actions and do activities
    switch(gamestatus)
    {
      case Initializing:
        if (gamestatusInitializing == GamestatusInitializing.Null) { setGamestatusInitializing(GamestatusInitializing.CreatingFirstDraft); }
        break;
      case Playing:
        if (gamestatusPlaying == GamestatusPlaying.Null) { setGamestatusPlaying(GamestatusPlaying.CreatingNextDraft); }
        break;
      case Finishing:
        if (gamestatusFinishing == GamestatusFinishing.Null) { setGamestatusFinishing(GamestatusFinishing.ManipulatingLastDomino); }
        break;
      case EndingGame:
        delete();
        break;
    }
  }

  private void exitGamestatusInitializing()
  {
    switch(gamestatusInitializing)
    {
      case CreatingFirstDraft:
        setGamestatusInitializing(GamestatusInitializing.Null);
        break;
      case SelectingFirstDomino:
        setGamestatusInitializing(GamestatusInitializing.Null);
        break;
    }
  }

  private void setGamestatusInitializing(GamestatusInitializing aGamestatusInitializing)
  {
    gamestatusInitializing = aGamestatusInitializing;
    if (gamestatus != Gamestatus.Initializing && aGamestatusInitializing != GamestatusInitializing.Null) { setGamestatus(Gamestatus.Initializing); }

    // entry actions and do activities
    switch(gamestatusInitializing)
    {
      case CreatingFirstDraft:
        // line 14 "../../../../../Gameplay.ump"
        shuffleDominoPile(); createNextDraft(); orderNextDraft();
        break;
    }
  }

  private void exitGamestatusPlaying()
  {
    switch(gamestatusPlaying)
    {
      case CreatingNextDraft:
        setGamestatusPlaying(GamestatusPlaying.Null);
        break;
      case RevealingNextDraft:
        setGamestatusPlaying(GamestatusPlaying.Null);
        break;
      case ManipulatingDomino:
        exitGamestatusPlayingManipulatingDomino();
        setGamestatusPlaying(GamestatusPlaying.Null);
        break;
      case ConfirmingChoice:
        setGamestatusPlaying(GamestatusPlaying.Null);
        break;
      case SelectingStandardDomino:
        setGamestatusPlaying(GamestatusPlaying.Null);
        break;
    }
  }

  private void setGamestatusPlaying(GamestatusPlaying aGamestatusPlaying)
  {
    gamestatusPlaying = aGamestatusPlaying;
    if (gamestatus != Gamestatus.Playing && aGamestatusPlaying != GamestatusPlaying.Null) { setGamestatus(Gamestatus.Playing); }

    // entry actions and do activities
    switch(gamestatusPlaying)
    {
      case CreatingNextDraft:
        // line 27 "../../../../../Gameplay.ump"
        createNextDraft();orderNextDraft();
        break;
      case RevealingNextDraft:
        // line 32 "../../../../../Gameplay.ump"
        revealNextDraft();updatePlayerOrder();
        break;
      case ManipulatingDomino:
        if (gamestatusPlayingManipulatingDomino == GamestatusPlayingManipulatingDomino.Null) { setGamestatusPlayingManipulatingDomino(GamestatusPlayingManipulatingDomino.IdlingDomino); }
        break;
    }
  }

  private void exitGamestatusPlayingManipulatingDomino()
  {
    switch(gamestatusPlayingManipulatingDomino)
    {
      case IdlingDomino:
        setGamestatusPlayingManipulatingDomino(GamestatusPlayingManipulatingDomino.Null);
        break;
      case RotatingDomino:
        setGamestatusPlayingManipulatingDomino(GamestatusPlayingManipulatingDomino.Null);
        break;
      case MovingDomino:
        setGamestatusPlayingManipulatingDomino(GamestatusPlayingManipulatingDomino.Null);
        break;
    }
  }

  private void setGamestatusPlayingManipulatingDomino(GamestatusPlayingManipulatingDomino aGamestatusPlayingManipulatingDomino)
  {
    gamestatusPlayingManipulatingDomino = aGamestatusPlayingManipulatingDomino;
    if (gamestatusPlaying != GamestatusPlaying.ManipulatingDomino && aGamestatusPlayingManipulatingDomino != GamestatusPlayingManipulatingDomino.Null) { setGamestatusPlaying(GamestatusPlaying.ManipulatingDomino); }
  }

  private void exitGamestatusFinishing()
  {
    switch(gamestatusFinishing)
    {
      case ManipulatingLastDomino:
        exitGamestatusFinishingManipulatingLastDomino();
        setGamestatusFinishing(GamestatusFinishing.Null);
        break;
      case ConfirmingLastChoice:
        setGamestatusFinishing(GamestatusFinishing.Null);
        break;
    }
  }

  private void setGamestatusFinishing(GamestatusFinishing aGamestatusFinishing)
  {
    gamestatusFinishing = aGamestatusFinishing;
    if (gamestatus != Gamestatus.Finishing && aGamestatusFinishing != GamestatusFinishing.Null) { setGamestatus(Gamestatus.Finishing); }

    // entry actions and do activities
    switch(gamestatusFinishing)
    {
      case ManipulatingLastDomino:
        if (gamestatusFinishingManipulatingLastDomino == GamestatusFinishingManipulatingLastDomino.Null) { setGamestatusFinishingManipulatingLastDomino(GamestatusFinishingManipulatingLastDomino.IdlingLastDomino); }
        break;
    }
  }

  private void exitGamestatusFinishingManipulatingLastDomino()
  {
    switch(gamestatusFinishingManipulatingLastDomino)
    {
      case IdlingLastDomino:
        setGamestatusFinishingManipulatingLastDomino(GamestatusFinishingManipulatingLastDomino.Null);
        break;
      case RotatingLastDomino:
        setGamestatusFinishingManipulatingLastDomino(GamestatusFinishingManipulatingLastDomino.Null);
        break;
      case MovingLastDomino:
        setGamestatusFinishingManipulatingLastDomino(GamestatusFinishingManipulatingLastDomino.Null);
        break;
    }
  }

  private void setGamestatusFinishingManipulatingLastDomino(GamestatusFinishingManipulatingLastDomino aGamestatusFinishingManipulatingLastDomino)
  {
    gamestatusFinishingManipulatingLastDomino = aGamestatusFinishingManipulatingLastDomino;
    if (gamestatusFinishing != GamestatusFinishing.ManipulatingLastDomino && aGamestatusFinishingManipulatingLastDomino != GamestatusFinishingManipulatingLastDomino.Null) { setGamestatusFinishing(GamestatusFinishing.ManipulatingLastDomino); }
  }

  public void delete()
  {}


  /**
   * Setter for test setup
   */
  // line 115 "../../../../../Gameplay.ump"
   public void setGamestatus(String status){
    switch (status) {
          
       	case "CreatingFirstDraft":
          		
          		exitGamestatusInitializing();
          		exitGamestatusFinishing();
	       		exitGamestatusPlaying();
	       		exitGamestatusPlayingManipulatingDomino();
	       		exitGamestatusFinishing();
	       		exitGamestatusFinishingManipulatingLastDomino();
          		
	       	    gamestatus = Gamestatus.Initializing;
	       	    gamestatusInitializing = GamestatusInitializing.CreatingFirstDraft;
                  
               break;
          
       	case "SelectingFirstDomino":
          		exitGamestatusInitializing();
          		exitGamestatusFinishing();
	       		exitGamestatusPlaying();
	       		exitGamestatusPlayingManipulatingDomino();
	       		exitGamestatusFinishing();
	       		exitGamestatusFinishingManipulatingLastDomino();
          		
                gamestatus = Gamestatus.Initializing;
                gamestatusInitializing = GamestatusInitializing.SelectingFirstDomino;
          
               break;
          
          case "CreatingNextDraft":
          		exitGamestatusInitializing();
          		exitGamestatusFinishing();
	       		exitGamestatusPlaying();
	       		exitGamestatusPlayingManipulatingDomino();
	       		exitGamestatusFinishing();
	       		exitGamestatusFinishingManipulatingLastDomino();
          		          
                gamestatus = Gamestatus.Playing;
                gamestatusPlaying = GamestatusPlaying.CreatingNextDraft;
          
                break;
          
          case "RevealingNextDraft":
          		exitGamestatusInitializing();
          		exitGamestatusFinishing();
	       		exitGamestatusPlaying();
	       		exitGamestatusPlayingManipulatingDomino();
	       		exitGamestatusFinishing();
	       		exitGamestatusFinishingManipulatingLastDomino();
          		          
                gamestatus = Gamestatus.Playing;
                gamestatusPlaying = GamestatusPlaying.RevealingNextDraft;
          
                break;
          
          case "ManipulatingDomino":
          		exitGamestatusInitializing();
          		exitGamestatusFinishing();
	       		exitGamestatusPlaying();
	       		exitGamestatusPlayingManipulatingDomino();
	       		exitGamestatusFinishing();
	       		exitGamestatusFinishingManipulatingLastDomino();
          		          
          		gamestatus = Gamestatus.Playing;
                gamestatusPlaying = GamestatusPlaying.ManipulatingDomino;
                
                break;
          
          case "IdlingDomino":
          		exitGamestatusInitializing();
          		exitGamestatusFinishing();
	       		exitGamestatusPlaying();
	       		exitGamestatusPlayingManipulatingDomino();
	       		exitGamestatusFinishing();
	       		exitGamestatusFinishingManipulatingLastDomino();
          		          
                gamestatus = Gamestatus.Playing;
                gamestatusPlaying = GamestatusPlaying.ManipulatingDomino;
                gamestatusPlayingManipulatingDomino = GamestatusPlayingManipulatingDomino.IdlingDomino;
          
                break;
          
          case "MovingDomino":
          		exitGamestatusInitializing();
          		exitGamestatusFinishing();
	       		exitGamestatusPlaying();
	       		exitGamestatusPlayingManipulatingDomino();
	       		exitGamestatusFinishing();
	       		exitGamestatusFinishingManipulatingLastDomino();
          		          
                gamestatus = Gamestatus.Playing;
                gamestatusPlaying = GamestatusPlaying.ManipulatingDomino;
                gamestatusPlayingManipulatingDomino = GamestatusPlayingManipulatingDomino.MovingDomino;
          
                break;
          
          case "RotatingDomino":
          		exitGamestatusInitializing();
          		exitGamestatusFinishing();
	       		exitGamestatusPlaying();
	       		exitGamestatusPlayingManipulatingDomino();
	       		exitGamestatusFinishing();
	       		exitGamestatusFinishingManipulatingLastDomino();
          		          
                gamestatus = Gamestatus.Playing;
                gamestatusPlaying = GamestatusPlaying.ManipulatingDomino;
                gamestatusPlayingManipulatingDomino = GamestatusPlayingManipulatingDomino.RotatingDomino;
          
                break;
          
          case "ConfirmingChoice":
          		exitGamestatusInitializing();
          		exitGamestatusFinishing();
	       		exitGamestatusPlaying();
	       		exitGamestatusPlayingManipulatingDomino();
	       		exitGamestatusFinishing();
	       		exitGamestatusFinishingManipulatingLastDomino();
          		          
            	gamestatus = Gamestatus.Playing;
             	gamestatusPlaying = GamestatusPlaying.ConfirmingChoice;
      
                break;
          
          case "SelectingStandardDomino":
          		exitGamestatusInitializing();
          		exitGamestatusFinishing();
	       		exitGamestatusPlaying();
	       		exitGamestatusPlayingManipulatingDomino();
	       		exitGamestatusFinishing();
	       		exitGamestatusFinishingManipulatingLastDomino();
          		          
                gamestatus = Gamestatus.Playing;
                gamestatusPlaying = GamestatusPlaying.SelectingStandardDomino;
          
                break;
          
          case "ManipulatingLastDomino":
          		exitGamestatusInitializing();
          		exitGamestatusFinishing();
	       		exitGamestatusPlaying();
	       		exitGamestatusPlayingManipulatingDomino();
	       		exitGamestatusFinishing();
	       		exitGamestatusFinishingManipulatingLastDomino();
          		          
           		gamestatus=Gamestatus.Finishing;
          		gamestatusFinishing=GamestatusFinishing.ManipulatingLastDomino;
          		
          		break;         
          case "IdlingLastDomino":
          		exitGamestatusInitializing();
          		exitGamestatusFinishing();
	       		exitGamestatusPlaying();
	       		exitGamestatusPlayingManipulatingDomino();
	       		exitGamestatusFinishing();
	       		exitGamestatusFinishingManipulatingLastDomino();
          		          
          		gamestatus=Gamestatus.Finishing;
          		gamestatusFinishing=GamestatusFinishing.ManipulatingLastDomino;
          		gamestatusFinishingManipulatingLastDomino=GamestatusFinishingManipulatingLastDomino.IdlingLastDomino;
          		break;
          
          case "MovingLastDomino":
          		gamestatus=Gamestatus.Finishing;
          		gamestatusFinishing=GamestatusFinishing.ManipulatingLastDomino;
          		gamestatusFinishingManipulatingLastDomino=GamestatusFinishingManipulatingLastDomino.MovingLastDomino;
          		
          		break;
          		
          case "RotatingLastDomino":
          		exitGamestatusInitializing();
          		exitGamestatusFinishing();
	       		exitGamestatusPlaying();
	       		exitGamestatusPlayingManipulatingDomino();
	       		exitGamestatusFinishing();
	       		exitGamestatusFinishingManipulatingLastDomino();
          		          
          		gamestatus=Gamestatus.Finishing;
          		gamestatusFinishing=GamestatusFinishing.ManipulatingLastDomino;
          		gamestatusFinishingManipulatingLastDomino=GamestatusFinishingManipulatingLastDomino.RotatingLastDomino;
          		
          		break;
          		
          case "ConfirmingLastChoice":
          		exitGamestatusInitializing();
          		exitGamestatusFinishing();
	       		exitGamestatusPlaying();
	       		exitGamestatusPlayingManipulatingDomino();
	       		exitGamestatusFinishing();
	       		exitGamestatusFinishingManipulatingLastDomino();
          		          
          		gamestatus=Gamestatus.Finishing;
          		gamestatusFinishing=GamestatusFinishing.ConfirmingLastChoice;
          		
          		break;
          		
          case "EndingGame":
          		exitGamestatusInitializing();
          		exitGamestatusFinishing();
	       		exitGamestatusPlaying();
	       		exitGamestatusPlayingManipulatingDomino();
	       		exitGamestatusFinishing();
	       		exitGamestatusFinishingManipulatingLastDomino();
          		          
                gamestatus = Gamestatus.EndingGame;
          
                break;
          
       	default:
       	    throw new RuntimeException("Invalid gamestatus string was provided: " + status);
          
       	}
  }


   /**
    * Guards
    */
   
   public boolean lastPlayerInTurn(){
   	   Kingdomino kd = KingdominoApplication.getKingdomino();
   	   Game game=kd.getCurrentGame();
   	   Player player=game.getNextPlayer();
   	   return KDQuery.lastPlayerInTurn(player);
   }
   
   // line 219 "../../../../../Gameplay.ump"
    public boolean isCurrentPlayerTheLastInTurn(){
   	   Kingdomino kd = KingdominoApplication.getKingdomino();
   	   Game game=kd.getCurrentGame();
   	   Player player=game.getNextPlayer();
   	   return KDQuery.isCurrentPlayerTheLastInTurn(player);
   }

   // line 220 "../../../../../Gameplay.ump"
    public boolean isCurrentTurnTheLastInGame(){
   	   return KDQuery.isCurrentTurnTheLastInGame();
   }

   // line 221 "../../../../../Gameplay.ump"
    public boolean isDominoTaken(Domino domino){
   	   return KDQuery.isDominoTaken(domino);
   }

   // line 222 "../../../../../Gameplay.ump"
    public boolean hasAllPlayersChosen(){
   	   return KDQuery.hasAllPlayersChosen();
   }

   // line 223 "../../../../../Gameplay.ump"
    public boolean isThereAvailablePlacement(){
   	   Kingdomino kd = KingdominoApplication.getKingdomino();
   	   Game game=kd.getCurrentGame();
   	   Player player=game.getNextPlayer();
   	   List<KingdomTerritory> territories = player.getKingdom().getTerritories();
   	   DominoInKingdom dInK = (DominoInKingdom) territories.get(territories.size()-1);
   	   
   	   return KDQuery.isThereAvailablePlacement(player, dInK);
   }

   // line 224 "../../../../../Gameplay.ump"
    public boolean verifyDomino(){
   	   Kingdomino kd = KingdominoApplication.getKingdomino();
   	   Game game=kd.getCurrentGame();
   	   Player player=game.getNextPlayer();
   	   List<KingdomTerritory> territories = player.getKingdom().getTerritories();
   	   DominoInKingdom dInK = (DominoInKingdom) territories.get(territories.size()-1);
   	   
   	   return KDQuery.verifyDominoInKingdom(player, dInK);
   }

   // line 225 "../../../../../Gameplay.ump"
    public boolean isDominoPileEmpty(){
   	   return KDQuery.isDominoPileEmpty();
   }

   // line 226 "../../../../../Gameplay.ump"
    public boolean hasAllPlayersPlayed(){
   	   return KDQuery.hasAllPlayersPlayed();
   }


   /**
    * You may need to add more guards here
    * Actions
    */
   // line 232 "../../../../../Gameplay.ump"
    public void shuffleDominoPile(){
   	   KDController.shuffleDominoPile();
   }

   // line 233 "../../../../../Gameplay.ump"
    public void createNextDraft(){
   	   KDController.createNextDraft();
   }

   // line 234 "../../../../../Gameplay.ump"
    public void orderNextDraft(){
   	   KDController.sortNextDraft();
   }

   // line 235 "../../../../../Gameplay.ump"
    public void revealNextDraft(){
   	   KDController.revealNextDraft();
   }

   // line 236 "../../../../../Gameplay.ump"
    public void generateInitialPlayerOrder(){
   	   KDController.generateInitialPlayerOrder();
   }

   // line 237 "../../../../../Gameplay.ump"
    public void chooseDomino(Domino domino){
   	   KDController.chooseNextDomino(domino);
   }

   // line 238 "../../../../../Gameplay.ump"
    public void updatePlayerOrder(){
   	   KDController.updatePlayerOrder();
   }

   // line 239 "../../../../../Gameplay.ump"
    public void updatePlayerScore(){
   	   Kingdomino kd = KingdominoApplication.getKingdomino();
   	   Game game=kd.getCurrentGame();
   	   Player player=game.getNextPlayer();

   	   KDController.calculateIndividualPlayerScore(player);
   }

   // line 240 "../../../../../Gameplay.ump"
    public void preplaceLatestDomino(int posx, int posy, String dir){
     
   	   Kingdomino kd = KingdominoApplication.getKingdomino();
   	   Game game=kd.getCurrentGame();
   	   Player player=game.getNextPlayer();   
   	   KDController.preplaceLatestDomino(player, posx, posy, dir);
   }

   // line 241 "../../../../../Gameplay.ump"
    public void rotateLatestDomino(String dir){
   	   Kingdomino kd = KingdominoApplication.getKingdomino();
   	   Game game=kd.getCurrentGame();
   	   Player player=game.getNextPlayer();   
   	   KDController.rotateLatestDomino(player, dir);
   }

   // line 242 "../../../../../Gameplay.ump"
    public void moveLatestDomino(String movement){
   	   Kingdomino kd = KingdominoApplication.getKingdomino();
   	   Game game=kd.getCurrentGame();
   	   Player player=game.getNextPlayer();   
   	   KDController.moveLatestDomino(player, movement);
   }

   // line 243 "../../../../../Gameplay.ump"
    public void placeLatestDomino(){
   	   Kingdomino kd = KingdominoApplication.getKingdomino();
   	   Game game=kd.getCurrentGame();
   	   Player player=game.getNextPlayer();   
   	   KDController.placeLatestDomino(player);
   }

   // line 244 "../../../../../Gameplay.ump"
    public void discardLatestDomino(){
   	   Kingdomino kd = KingdominoApplication.getKingdomino();
   	   Game game=kd.getCurrentGame();
   	   Player player=game.getNextPlayer();   
   	   KDController.discardLatestDomino(player);
   }

   // line 245 "../../../../../Gameplay.ump"
    public void setNextPlayer(){
   	   Kingdomino kd = KingdominoApplication.getKingdomino();
   	   Game game=kd.getCurrentGame();
   	   Player player=game.getNextPlayer();
   	   KDController.updateNextPlayer(player);
   }

   // line 246 "../../../../../Gameplay.ump"
    public void calculateAllPlayerScores(){
   	   Kingdomino kd = KingdominoApplication.getKingdomino();
   	   Game game=kd.getCurrentGame();
   	   KDController.calculateAllPlayerScore(game);
   }

   // line 247 "../../../../../Gameplay.ump"
    public void calculatePlayerRanking(){
   	   KDController.calculatePlayerRanking();
   }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 7 "../../../../../Gameplay.ump"
  private static final long serialVersionUID = 30L ;

  
}