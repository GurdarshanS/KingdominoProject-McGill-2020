/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.kingdomino.model;
import java.io.Serializable;

// line 79 "../../../../../KingdominoPersistence.ump"
public class User implements Serializable
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public User()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 85 "../../../../../KingdominoPersistence.ump"
   public static  void reinitializeUsers(List<User> allUsers){
    usersByName = new HashMap<String, User>()
    for (User user : allUsers) {
      usersByName.put(user.getName(), user);
    }
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 82 "../../../../../KingdominoPersistence.ump"
  private static final long serialVersionUID = 14L ;

  
}