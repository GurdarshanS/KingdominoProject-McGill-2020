/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.kingdomino.model;
import java.io.Serializable;

// line 4 "../../../../../KingdominoPersistence.ump"
public class Kingdomino implements Serializable
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Kingdomino()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 9 "../../../../../KingdominoPersistence.ump"
   public void reinitialize(){
    User.reinitializeUsers(this.getUsers());
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 7 "../../../../../KingdominoPersistence.ump"
  private static final long serialVersionUID = 1L ;

  
}