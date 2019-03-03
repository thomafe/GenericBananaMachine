package model;

import view.Output;

/**
 * Connects places, can be blocked by obstacles.
 * 
 * The name should describe the passage, not the obstacle. The description should hint at the
 * obstacle and maybe be funny.
 *
 * @author Simone273
 */
public class Passage extends GameObject {

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
  public Passage(String name, String description, Place place1, Place place2) {
    super(name, description);
    this.connectingRooms[0] = place1;
    this.connectingRooms[1] = place2;

    if (connectingRooms[0] == null || connectingRooms[1] == null) {
      Output.printError(
          "---[new Passage(" + name + ")]--- WARNING! None of the connecting places may be null)");
    } else {
      place1.addPassage(this);
      place2.addPassage(this);
    }
  }

  /**
   * Returns next Place after accessing the passage.
   *
   * @param comingFromPlace Place
   * @return Object
   */
  public Place usePassage(Place comingFromPlace) {
    if (comingFromPlace.equals(connectingRooms[0])) {
      return connectingRooms[1];
    } else if (comingFromPlace.equals(connectingRooms[1])) {
      return connectingRooms[0];
    } else {
      return null;
    }
  }

  /**
   * Getter for obstacle.
   *
   * @return Object
   */
  public Obstacle getObstacle() {
    return obstacle;
  }

  /**
   * Setter for obstacle. Sets committed obstacle too.
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
  public Place[] getConnectingRooms() {
    return connectingRooms;
  }

  /**
   * If this has an Obstacle, return true, else return false.
   *
   * @return boolean
   */
  public boolean hasObstacle() {
    return obstacle != null;
  }
}
