/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.kingdomino.model;

// line 48 "../../../../../KingDomino.ump"
public class Coord
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Coord Attributes
  private int x;
  private int y;

  //Coord Associations
  private Tile tile;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Coord(int aX, int aY)
  {
    x = aX;
    y = aY;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setX(int aX)
  {
    boolean wasSet = false;
    x = aX;
    wasSet = true;
    return wasSet;
  }

  public boolean setY(int aY)
  {
    boolean wasSet = false;
    y = aY;
    wasSet = true;
    return wasSet;
  }

  public int getX()
  {
    return x;
  }

  public int getY()
  {
    return y;
  }
  /* Code from template association_GetOne */
  public Tile getTile()
  {
    return tile;
  }

  public boolean hasTile()
  {
    boolean has = tile != null;
    return has;
  }
  /* Code from template association_SetOptionalOneToOne */
  public boolean setTile(Tile aNewTile)
  {
    boolean wasSet = false;
    if (tile != null && !tile.equals(aNewTile) && equals(tile.getCoord()))
    {
      //Unable to setTile, as existing tile would become an orphan
      return wasSet;
    }

    tile = aNewTile;
    Coord anOldCoord = aNewTile != null ? aNewTile.getCoord() : null;

    if (!this.equals(anOldCoord))
    {
      if (anOldCoord != null)
      {
        anOldCoord.tile = null;
      }
      if (tile != null)
      {
        tile.setCoord(this);
      }
    }
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    Tile existingTile = tile;
    tile = null;
    if (existingTile != null)
    {
      existingTile.delete();
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "x" + ":" + getX()+ "," +
            "y" + ":" + getY()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "tile = "+(getTile()!=null?Integer.toHexString(System.identityHashCode(getTile())):"null");
  }
}