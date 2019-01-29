package model;

// class mit name passage
public class Passage extends GameObject {



  // attribute
  private Place[] connectingRooms = new Place[2];

  private Obstacle obstacle = null;


  /**
   * Constructor.
   *
   * @param name String
   * @param description String
   * @param place1 Place
   * @param place2 Place
   */
  // constructor, kein rückgabewert, name so wie klasse, initialisiert classenattribute
  public Passage(String name, String description, Place place1, Place place2) {
    super(name, description);
    this.connectingRooms[0] = place1;
    this.connectingRooms[1] = place2;
    place1.addPassage(this);
    place2.addPassage(this);
  }

  /**
   * Returns next Place after accessing the passage.
   *
   *
   * @param comingFromPlace Place
   * @return Object
   */
  public Place usePassage(Place comingFromPlace) {
    // zurückgeben in welchem raum man ist
    if (comingFromPlace.equals(connectingRooms[0])) {
      return connectingRooms[1];
    } else if (comingFromPlace.equals(connectingRooms[1])) {
      return connectingRooms[0];
    } else {
      return null;
    }
  }

  // getter and setter for obstacle

  /**
   * Getter for obstacle.
   *
   * @return Object
   */
  public Obstacle getObstacle() {
    return obstacle;
  }

  /**
   * Setter for obstacle. Sets committed obstacle to
   *
   * @param obstacle Obstacle
   */
  public void setObstacle(Obstacle obstacle) {
    this.obstacle = obstacle;
  }

  /**
   * Getter for connectingRooms.
   *
   * @return Object Array
   */
  // getter for connecting rooms
  public Place[] getConnectingRooms() {
    return connectingRooms;
  }

  /**
   * if this has an Obstacle, return true, else return false.
   *
   * @return boolean
   */
  public boolean hasObstacle() {

    return obstacle != null;
  }

}
