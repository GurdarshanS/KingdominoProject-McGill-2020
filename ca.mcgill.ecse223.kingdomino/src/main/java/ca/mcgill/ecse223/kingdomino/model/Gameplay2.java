/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.kingdomino.model;

// line 3 "../../../../../Gameplay2.ump"
public class Gameplay2
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Gameplay2 State Machines
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

  public Gameplay2()
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
        // line 11 "../../../../../Gameplay2.ump"
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
        // line 15 "../../../../../Gameplay2.ump"
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
        // line 55 "../../../../../Gameplay2.ump"
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

  public boolean manipulateFirst(Player player,int posx,int posy,String dir)
  {
    boolean wasEventProcessed = false;
    
    GamestatusPlaying aGamestatusPlaying = gamestatusPlaying;
    switch (aGamestatusPlaying)
    {
      case RevealingNextDraft:
        exitGamestatusPlaying();
        // line 29 "../../../../../Gameplay2.ump"
        preplaceLatestDomino(player,posx,posy,dir);
        setGamestatusPlaying(GamestatusPlaying.ManipulatingDomino);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean discard(Player player)
  {
    boolean wasEventProcessed = false;
    
    GamestatusPlaying aGamestatusPlaying = gamestatusPlaying;
    GamestatusFinishing aGamestatusFinishing = gamestatusFinishing;
    switch (aGamestatusPlaying)
    {
      case ManipulatingDomino:
        if (!(isThereAvailablePlacement(player)))
        {
          exitGamestatusPlaying();
        // line 34 "../../../../../Gameplay2.ump"
          discardLatestDomino(player);
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
        if (!(isThereAvailablePlacement(player)))
        {
          exitGamestatusFinishing();
        // line 70 "../../../../../Gameplay2.ump"
          discardLatestDomino(player);
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

  public boolean place(Player player)
  {
    boolean wasEventProcessed = false;
    
    GamestatusPlaying aGamestatusPlaying = gamestatusPlaying;
    GamestatusFinishing aGamestatusFinishing = gamestatusFinishing;
    switch (aGamestatusPlaying)
    {
      case ManipulatingDomino:
        if (verifyDomino(player))
        {
          exitGamestatusPlaying();
        // line 35 "../../../../../Gameplay2.ump"
          placeLatestDomino(player);updatePlayerScore(player);
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
        if (verifyDomino(player))
        {
          exitGamestatusFinishing();
        // line 71 "../../../../../Gameplay2.ump"
          placeLatestDomino(player);updatePlayerScore(player);
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

  public boolean manipulateNext(Player player,int posx,int posy,String dir)
  {
    boolean wasEventProcessed = false;
    
    GamestatusPlaying aGamestatusPlaying = gamestatusPlaying;
    switch (aGamestatusPlaying)
    {
      case SelectingStandardDomino:
        if (!(hasAllPlayersChosen()))
        {
          exitGamestatusPlaying();
        // line 59 "../../../../../Gameplay2.ump"
          preplaceLatestDomino(player,posx,posy,dir);
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
        if (hasAllPlayersChosen()&&!(isDominoPileEmpty()))
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

  public boolean lastSelectionReady(Player player,int posx,int posy,String dir)
  {
    boolean wasEventProcessed = false;
    
    GamestatusPlaying aGamestatusPlaying = gamestatusPlaying;
    switch (aGamestatusPlaying)
    {
      case SelectingStandardDomino:
        if (hasAllPlayersChosen()&&isDominoPileEmpty())
        {
          exitGamestatus();
        // line 61 "../../../../../Gameplay2.ump"
          updatePlayerOrder();preplaceLatestDomino(player,posx,posy,dir);
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

  public boolean rotate(Player player,String dir)
  {
    boolean wasEventProcessed = false;
    
    GamestatusPlayingManipulatingDomino aGamestatusPlayingManipulatingDomino = gamestatusPlayingManipulatingDomino;
    GamestatusFinishingManipulatingLastDomino aGamestatusFinishingManipulatingLastDomino = gamestatusFinishingManipulatingLastDomino;
    switch (aGamestatusPlayingManipulatingDomino)
    {
      case IdlingDomino:
        exitGamestatusPlayingManipulatingDomino();
        // line 38 "../../../../../Gameplay2.ump"
        rotateLatestDomino(player,dir);
        setGamestatusPlayingManipulatingDomino(GamestatusPlayingManipulatingDomino.RotatingDomino);
        wasEventProcessed = true;
        break;
      case RotatingDomino:
        exitGamestatusPlayingManipulatingDomino();
        // line 43 "../../../../../Gameplay2.ump"
        rotateLatestDomino(player,dir);
        setGamestatusPlayingManipulatingDomino(GamestatusPlayingManipulatingDomino.RotatingDomino);
        wasEventProcessed = true;
        break;
      case MovingDomino:
        exitGamestatusPlayingManipulatingDomino();
        // line 48 "../../../../../Gameplay2.ump"
        rotateLatestDomino(player,dir);
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
        // line 74 "../../../../../Gameplay2.ump"
        rotateLatestDomino(player,dir);
        setGamestatusFinishingManipulatingLastDomino(GamestatusFinishingManipulatingLastDomino.RotatingLastDomino);
        wasEventProcessed = true;
        break;
      case RotatingLastDomino:
        exitGamestatusFinishingManipulatingLastDomino();
        // line 79 "../../../../../Gameplay2.ump"
        rotateLatestDomino(player,dir);
        setGamestatusFinishingManipulatingLastDomino(GamestatusFinishingManipulatingLastDomino.RotatingLastDomino);
        wasEventProcessed = true;
        break;
      case MovingLastDomino:
        exitGamestatusFinishingManipulatingLastDomino();
        // line 84 "../../../../../Gameplay2.ump"
        rotateLatestDomino(player,dir);
        setGamestatusFinishingManipulatingLastDomino(GamestatusFinishingManipulatingLastDomino.RotatingLastDomino);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean move(Player player,String movement)
  {
    boolean wasEventProcessed = false;
    
    GamestatusPlayingManipulatingDomino aGamestatusPlayingManipulatingDomino = gamestatusPlayingManipulatingDomino;
    GamestatusFinishingManipulatingLastDomino aGamestatusFinishingManipulatingLastDomino = gamestatusFinishingManipulatingLastDomino;
    switch (aGamestatusPlayingManipulatingDomino)
    {
      case IdlingDomino:
        exitGamestatusPlayingManipulatingDomino();
        // line 39 "../../../../../Gameplay2.ump"
        moveLatestDomino(player,movement);
        setGamestatusPlayingManipulatingDomino(GamestatusPlayingManipulatingDomino.MovingDomino);
        wasEventProcessed = true;
        break;
      case RotatingDomino:
        exitGamestatusPlayingManipulatingDomino();
        // line 44 "../../../../../Gameplay2.ump"
        moveLatestDomino(player,movement);
        setGamestatusPlayingManipulatingDomino(GamestatusPlayingManipulatingDomino.MovingDomino);
        wasEventProcessed = true;
        break;
      case MovingDomino:
        exitGamestatusPlayingManipulatingDomino();
        // line 49 "../../../../../Gameplay2.ump"
        moveLatestDomino(player,movement);
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
        // line 75 "../../../../../Gameplay2.ump"
        moveLatestDomino(player,movement);
        setGamestatusFinishingManipulatingLastDomino(GamestatusFinishingManipulatingLastDomino.MovingLastDomino);
        wasEventProcessed = true;
        break;
      case RotatingLastDomino:
        exitGamestatusFinishingManipulatingLastDomino();
        // line 80 "../../../../../Gameplay2.ump"
        moveLatestDomino(player,movement);
        setGamestatusFinishingManipulatingLastDomino(GamestatusFinishingManipulatingLastDomino.MovingLastDomino);
        wasEventProcessed = true;
        break;
      case MovingLastDomino:
        exitGamestatusFinishingManipulatingLastDomino();
        // line 85 "../../../../../Gameplay2.ump"
        moveLatestDomino(player,movement);
        setGamestatusFinishingManipulatingLastDomino(GamestatusFinishingManipulatingLastDomino.MovingLastDomino);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean manipulateLast(Player player,int posx,int posy,String dir)
  {
    boolean wasEventProcessed = false;
    
    GamestatusFinishing aGamestatusFinishing = gamestatusFinishing;
    switch (aGamestatusFinishing)
    {
      case ConfirmingLastChoice:
        if (!(hasAllPlayersPlayed()))
        {
          exitGamestatusFinishing();
        // line 90 "../../../../../Gameplay2.ump"
          setNextPlayer();preplaceLatestDomino(player,posx,posy,dir);
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
        // line 91 "../../../../../Gameplay2.ump"
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
        // line 10 "../../../../../Gameplay2.ump"
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
        // line 23 "../../../../../Gameplay2.ump"
        createNextDraft();orderNextDraft();
        break;
      case RevealingNextDraft:
        // line 28 "../../../../../Gameplay2.ump"
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
  // line 111 "../../../../../Gameplay2.ump"
   public void setGamestatus(String status){
    switch (status) {
          
       	case "CreatingFirstDraft":
          
       	    gamestatus = Gamestatus.Initializing;
       	    gamestatusInitializing = GamestatusInitializing.CreatingFirstDraft;
                  
               break;
          
       	case "SelectingFirstDomino":
          
                gamestatus = Gamestatus.Initializing;
                gamestatusInitializing = GamestatusInitializing.SelectingFirstDomino;
          
               break;
          
          case "CreatingNextDraft":
          
                gamestatus = Gamestatus.Playing;
                gamestatusPlaying = GamestatusPlaying.CreatingNextDraft;
          
                break;
          
          case "RevealingNextDraft":
          
                gamestatus = Gamestatus.Playing;
                gamestatusPlaying = GamestatusPlaying.RevealingNextDraft;
          
                break;
          
          case "IdlingDomino":
          
                gamestatus = Gamestatus.Playing;
                gamestatusPlaying = GamestatusPlaying.ManipulatingDomino;
                gamestatusPlayingManipulatingDomino = GamestatusPlayingManipulatingDomino.IdlingDomino;
          
                break;
          
          case "MovingDomino":
          
                gamestatus = Gamestatus.Playing;
                gamestatusPlaying = GamestatusPlaying.ManipulatingDomino;
                gamestatusPlayingManipulatingDomino = GamestatusPlayingManipulatingDomino.MovingDomino;
          
                break;
          
          case "RotatingDomino":
          
                gamestatus = Gamestatus.Playing;
                gamestatusPlaying = GamestatusPlaying.ManipulatingDomino;
                gamestatusPlayingManipulatingDomino = GamestatusPlayingManipulatingDomino.RotatingDomino;
          
                break;
          
          case "ConfirmingChoice":
          
                 gamestatus = Gamestatus.Playing;
                 gamestatusPlaying = GamestatusPlaying.ConfirmingChoice;
          
                break;
          
          case "SelectingStandardDomino":
          
                 gamestatus = Gamestatus.Playing;
                 gamestatusPlaying = GamestatusPlaying.SelectingStandardDomino;
          
                break;
                
          case "IdlingLastDomino":
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
          		gamestatus=Gamestatus.Finishing;
          		gamestatusFinishing=GamestatusFinishing.ManipulatingLastDomino;
          		gamestatusFinishingManipulatingLastDomino=GamestatusFinishingManipulatingLastDomino.RotatingLastDomino;
          		break;
          		
          case "ConfirmingLastChoice":
          		gamestatus=Gamestatus.Finishing;
          		gamestatusFinishing=GamestatusFinishing.ConfirmingLastChoice;
          		break;
          		
          case "EndingGame":
          
                gamestatus = Gamestatus.EndingGame;
          
                break;
          
       	default:
       	    throw new RuntimeException("Invalid gamestatus string was provided: " + status);
          
       	}
  }


  /**
   * Guards
   */
  // line 219 "../../../../../Gameplay2.ump"
   public boolean isCurrentPlayerTheLastInTurn(Player player){
    return true;
  }

  // line 220 "../../../../../Gameplay2.ump"
   public boolean isCurrentTurnTheLastInGame(){
    return true;
  }

  // line 221 "../../../../../Gameplay2.ump"
   public boolean isDominoTaken(Domino domino){
    return true;
  }

  // line 222 "../../../../../Gameplay2.ump"
   public boolean hasAllPlayersChosen(){
    return true;
  }

  // line 223 "../../../../../Gameplay2.ump"
   public boolean isThereAvailablePlacement(Player player){
    return true;
  }

  // line 224 "../../../../../Gameplay2.ump"
   public boolean verifyDomino(Player player){
    return true;
  }

  // line 225 "../../../../../Gameplay2.ump"
   public boolean isDominoPileEmpty(){
    return true;
  }

  // line 226 "../../../../../Gameplay2.ump"
   public boolean hasAllPlayersPlayed(){
    return true;
  }


  /**
   * You may need to add more guards here
   * Actions
   */
  // line 232 "../../../../../Gameplay2.ump"
   public void shuffleDominoPile(){
    
  }

  // line 233 "../../../../../Gameplay2.ump"
   public void createNextDraft(){
    
  }

  // line 234 "../../../../../Gameplay2.ump"
   public void orderNextDraft(){
    
  }

  // line 235 "../../../../../Gameplay2.ump"
   public void revealNextDraft(){
    
  }

  // line 236 "../../../../../Gameplay2.ump"
   public void generateInitialPlayerOrder(){
    
  }

  // line 237 "../../../../../Gameplay2.ump"
   public void chooseDomino(Domino domino){
    
  }

  // line 238 "../../../../../Gameplay2.ump"
   public void updatePlayerOrder(){
    
  }

  // line 239 "../../../../../Gameplay2.ump"
   public void updatePlayerScore(Player player){
    
  }

  // line 240 "../../../../../Gameplay2.ump"
   public void preplaceLatestDomino(Player player, int posx, int posy, String dir){
    
  }

  // line 241 "../../../../../Gameplay2.ump"
   public void rotateLatestDomino(Player player, String dir){
    
  }

  // line 242 "../../../../../Gameplay2.ump"
   public void moveLatestDomino(Player player, String movement){
    
  }

  // line 243 "../../../../../Gameplay2.ump"
   public void placeLatestDomino(Player player){
    
  }

  // line 244 "../../../../../Gameplay2.ump"
   public void discardLatestDomino(Player player){
    
  }

  // line 245 "../../../../../Gameplay2.ump"
   public void setNextPlayer(){
    
  }

  // line 246 "../../../../../Gameplay2.ump"
   public void calculateAllPlayerScores(){
    
  }

  // line 247 "../../../../../Gameplay2.ump"
   public void calculatePlayerRanking(){
    
  }

}