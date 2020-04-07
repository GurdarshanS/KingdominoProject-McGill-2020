/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.kingdomino.model;
import java.util.List;

import ca.mcgill.ecse223.kingdomino.controller.KDController;
import ca.mcgill.ecse223.kingdomino.controller.KDQuery;

// line 3 "../../../../../Gameplay.ump"
public class Gameplay
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Gameplay State Machines
	
  public enum Gamestatus { Initializing, Playing, Scoring }
  public enum GamestatusInitializing { Null, CreatingFirstDraft, SelectingFirstDomino }
  public enum GamestatusPlaying { Null, CreatingNextDraft, RevealingNextDraft, ManipulatingDomino, ConfirmingChoice, SelectingStandardDomino }
  public enum GamestatusPlayingManipulatingDomino { Null, Idle, RotatingDomino, MovingDomino }
  private Gamestatus gamestatus;
  private GamestatusInitializing gamestatusInitializing;
  private GamestatusPlaying gamestatusPlaying;
  private GamestatusPlayingManipulatingDomino gamestatusPlayingManipulatingDomino;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Gameplay()
  {
    setGamestatusInitializing(GamestatusInitializing.Null);
    setGamestatusPlaying(GamestatusPlaying.Null);
    setGamestatusPlayingManipulatingDomino(GamestatusPlayingManipulatingDomino.Null);
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

  public boolean scoring(Game game)
  {
    boolean wasEventProcessed = false;
    
    Gamestatus aGamestatus = gamestatus;
    switch (aGamestatus)
    {
      case Playing:
        if (isLastTurn(game)&&allPlayersPlayed(game))
        {
          exitGamestatus();
        // line 28 "../../../../../Gameplay.ump"
          calculateAllPlayerScores(game); calculatePlayerRanking(game);
          setGamestatus(Gamestatus.Scoring);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
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
        // line 13 "../../../../../Gameplay.ump"
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
    switch (aGamestatusInitializing)
    {
      case SelectingFirstDomino:
        if (!(isDominoTaken(domino)))
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

    return wasEventProcessed;
  }

  public boolean selectionReady(Player player)
  {
    boolean wasEventProcessed = false;
    
    GamestatusInitializing aGamestatusInitializing = gamestatusInitializing;
    GamestatusPlaying aGamestatusPlaying = gamestatusPlaying;
    switch (aGamestatusInitializing)
    {
      case SelectingFirstDomino:
        if (isLastPlayerInTurn(player)&&hasChosen(player))
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

    switch (aGamestatusPlaying)
    {
      case SelectingStandardDomino:
        if (isLastPlayerInTurn(player)&&hasChosen(player))
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

  public boolean manipulate(Player player,int posx,int posy,String dir)
  {
    boolean wasEventProcessed = false;
    
    GamestatusPlaying aGamestatusPlaying = gamestatusPlaying;
    switch (aGamestatusPlaying)
    {
      case RevealingNextDraft:
        exitGamestatusPlaying();
        // line 43 "../../../../../Gameplay.ump"
        prePlaceDomino(player, posx, posy, dir);
        setGamestatusPlaying(GamestatusPlaying.ManipulatingDomino);
        wasEventProcessed = true;
        break;
      case SelectingStandardDomino:
        exitGamestatusPlaying();
        // line 91 "../../../../../Gameplay.ump"
        prePlaceDomino(player, posx, posy, dir);
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
    switch (aGamestatusPlaying)
    {
      case ManipulatingDomino:
    	 
    	DominoInKingdom dInK = (DominoInKingdom) player.getKingdom().getTerritory(player.getKingdom().getTerritories().size()-1);
    	 
        if (!(isThereAvailablePlacement(player,dInK)))
        {
          exitGamestatusPlaying();
        // line 49 "../../../../../Gameplay.ump"
          discardLatestDomino(player);
          setGamestatusPlaying(GamestatusPlaying.ConfirmingChoice);
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
    switch (aGamestatusPlaying)
    {
      case ManipulatingDomino:
        if (verifyDomino(player))
        {
          exitGamestatusPlaying();
        // line 51 "../../../../../Gameplay.ump"
          placeLatestDomino(player); updatePlayerScore(player);
          setGamestatusPlaying(GamestatusPlaying.ConfirmingChoice);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean lastManipulate(Game game,Player player,int posx,int posy,String dir)
  {
    boolean wasEventProcessed = false;
    
    GamestatusPlaying aGamestatusPlaying = gamestatusPlaying;
    switch (aGamestatusPlaying)
    {
      case ConfirmingChoice:
        if (isLastTurn(game))
        {
          exitGamestatusPlaying();
        // line 81 "../../../../../Gameplay.ump"
          prePlaceDomino(player, posx, posy, dir);
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

  public boolean chooseStandardDomino(Domino domino,Game game)
  {
    boolean wasEventProcessed = false;
    
    GamestatusPlaying aGamestatusPlaying = gamestatusPlaying;
    switch (aGamestatusPlaying)
    {
      case ConfirmingChoice:
        if (!(isDominoTaken(domino))&&!(isLastTurn(game)))
        {
          exitGamestatusPlaying();
        // line 83 "../../../../../Gameplay.ump"
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

  public boolean rotate(Player player,String direction)
  {
    boolean wasEventProcessed = false;
    
    GamestatusPlayingManipulatingDomino aGamestatusPlayingManipulatingDomino = gamestatusPlayingManipulatingDomino;
    switch (aGamestatusPlayingManipulatingDomino)
    {
      case Idle:
        exitGamestatusPlayingManipulatingDomino();
        // line 55 "../../../../../Gameplay.ump"
        rotateLatestDomino(player, direction);
        setGamestatusPlayingManipulatingDomino(GamestatusPlayingManipulatingDomino.RotatingDomino);
        wasEventProcessed = true;
        break;
      case RotatingDomino:
        exitGamestatusPlayingManipulatingDomino();
        // line 63 "../../../../../Gameplay.ump"
        rotateLatestDomino(player, direction);
        setGamestatusPlayingManipulatingDomino(GamestatusPlayingManipulatingDomino.RotatingDomino);
        wasEventProcessed = true;
        break;
      case MovingDomino:
        exitGamestatusPlayingManipulatingDomino();
        // line 71 "../../../../../Gameplay.ump"
        rotateLatestDomino(player, direction);
        setGamestatusPlayingManipulatingDomino(GamestatusPlayingManipulatingDomino.RotatingDomino);
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
    switch (aGamestatusPlayingManipulatingDomino)
    {
      case Idle:
        exitGamestatusPlayingManipulatingDomino();
        // line 57 "../../../../../Gameplay.ump"
        moveLatestDomino(player, movement);
        setGamestatusPlayingManipulatingDomino(GamestatusPlayingManipulatingDomino.MovingDomino);
        wasEventProcessed = true;
        break;
      case RotatingDomino:
        exitGamestatusPlayingManipulatingDomino();
        // line 65 "../../../../../Gameplay.ump"
        moveLatestDomino(player, movement);
        setGamestatusPlayingManipulatingDomino(GamestatusPlayingManipulatingDomino.MovingDomino);
        wasEventProcessed = true;
        break;
      case MovingDomino:
        exitGamestatusPlayingManipulatingDomino();
        // line 73 "../../../../../Gameplay.ump"
        moveLatestDomino(player, movement);
        setGamestatusPlayingManipulatingDomino(GamestatusPlayingManipulatingDomino.MovingDomino);
        wasEventProcessed = true;
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
      case Scoring:
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
        // line 11 "../../../../../Gameplay.ump"
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
        // line 32 "../../../../../Gameplay.ump"
        createNextDraft(); orderNextDraft();
        break;
      case RevealingNextDraft:
        // line 41 "../../../../../Gameplay.ump"
        revealNextDraft(); updatePlayerOrder();
        break;
      case ManipulatingDomino:
        if (gamestatusPlayingManipulatingDomino == GamestatusPlayingManipulatingDomino.Null) { setGamestatusPlayingManipulatingDomino(GamestatusPlayingManipulatingDomino.Idle); }
        break;
    }
  }

  private void exitGamestatusPlayingManipulatingDomino()
  {
    switch(gamestatusPlayingManipulatingDomino)
    {
      case Idle:
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

  public void delete()
  {}


  /**
   * Setter for test setup
   */
  // line 111 "../../../../../Gameplay.ump"
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
          
          case "Idle":
          
                gamestatus = Gamestatus.Playing;
                gamestatusPlaying = GamestatusPlaying.ManipulatingDomino;
                gamestatusPlayingManipulatingDomino = GamestatusPlayingManipulatingDomino.Idle;
          
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
          
          case "Scoring":
          
                gamestatus = Gamestatus.Scoring;
          
                break;
          
       	default:
       	    throw new RuntimeException("Invalid gamestatus string was provided: " + status);
          
       	}
  }


  /**
   * Guards
   */
  // line 200 "../../../../../Gameplay.ump"
   public boolean isDominoTaken(Domino domino){
   
	   return KDQuery.isDominoTaken(domino);
	   
  }

  // line 208 "../../../../../Gameplay.ump"
   public boolean isLastPlayerInTurn(Player player){
   
	   return KDQuery.isCurrentPlayerTheLastInTurn(player);
  }

  // line 215 "../../../../../Gameplay.ump"
   public boolean isLastTurn(Game game){
    
	return game.getAllDrafts().get(game.getAllDrafts().size()-1).equals(game.getCurrentDraft());
	   
  }

  // line 224 "../../../../../Gameplay.ump"
   public boolean hasChosen(Player player){
	   
    return player.hasDominoSelection();
    
  }

  // line 231 "../../../../../Gameplay.ump"
   public boolean isThereAvailablePlacement(Player player, DominoInKingdom dInK){
    return true;
  }

  // line 238 "../../../../../Gameplay.ump"
   public boolean verifyDomino(Player player){
    return true;
  }

  // line 245 "../../../../../Gameplay.ump"
   public boolean allPlayersPlayed(Game game){
    
	   List<Player> players = game.getPlayers();
	   
	   boolean allPlayed = true;
	   
	   for(int i = 0; i<players.size(); i++) {
		   
		   if(players.get(i).hasDominoSelection()) allPlayed = false;
		   
	   }
	   
	   return allPlayed;
	   
  }


  /**
   * You may need to add more guards here
   * Actions
   */
  // line 256 "../../../../../Gameplay.ump"
   public void shuffleDominoPile(){
  
	   KDController.shuffleDominoPile();
	   
  }

  // line 260 "../../../../../Gameplay.ump"
   public void generateInitialPlayerOrder(){
    
	   KDController.generateInitialPlayerOrder();
	   
  }

  // line 264 "../../../../../Gameplay.ump"
   public void createNextDraft(){
    
	   KDController.createNextDraft();
	   
  }

  // line 268 "../../../../../Gameplay.ump"
   public void orderNextDraft(){
   
	   KDController.sortNextDraft();
	   
  }

  // line 272 "../../../../../Gameplay.ump"
   public void revealNextDraft(){
   
	   KDController.revealNextDraft();
	   
  }

  // line 278 "../../../../../Gameplay.ump"
   public void chooseDomino(Domino domino){
    
	   KDController.chooseNextDomino(domino);
	   
  }

  // line 283 "../../../../../Gameplay.ump"
   public void prePlaceDomino(Player player, int posx, int posy, String dir){
    
	   KDController.preplaceLatestDomino(player, posx, posy, dir);
	   
  }

  // line 290 "../../../../../Gameplay.ump"
   public void placeLatestDomino(Player player){
    
	   KDController.placeLatestDomino(player);
	   
  }

  // line 296 "../../../../../Gameplay.ump"
   public void discardLatestDomino(Player player){
   
	   KDController.discardLatestDomino(player);
	   
  }

  // line 302 "../../../../../Gameplay.ump"
   public void updatePlayerScore(Player player){
    
	   KDController.calculateIndividualPlayerScore(player);
	   
  }

  // line 308 "../../../../../Gameplay.ump"
   public void calculateAllPlayerScores(Game game){
	   
	   KDController.calculateAllPlayerScore(game);
	   
  }

  // line 315 "../../../../../Gameplay.ump"
   public void calculatePlayerRanking(Game game){
    
	   KDController.calculatePlayerRanking();
	   
  }

  // line 321 "../../../../../Gameplay.ump"
   public void rotateLatestDomino(Player player, String direction){
	   
	   KDController.rotateLatestDomino(player, direction);
    
  }

  // line 327 "../../../../../Gameplay.ump"
   public void moveLatestDomino(Player player, String movement){
    
	   KDController.moveLatestDomino(player, movement);
	   
  }

  // line 332 "../../../../../Gameplay.ump"
   public void updatePlayerOrder(){
    
	   KDController.updatePlayerOrder();
	   
  }

}