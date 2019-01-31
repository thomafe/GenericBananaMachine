package model;

public class Obstacle extends GameObject {

  private String resolution;
  private boolean resolved = false;

  private Item requiredItem;

  /**
   * Constructor.
   *
   * @param name String
   * @param description String
   * @param requiredItem Item
   */
  public Obstacle(String name, String description, String resolution, Item requiredItem) {
    super(name, description);
    this.requiredItem = requiredItem;
    this.resolution = resolution;
  }

  /**
   * Receive item and checks if it's equal to the required item to solve the obstacle. Returns true
   * if the item matches the requirement, else false. If the correct item was tried, the obstacle gets resolved.
   *
   * @param itemToTry Item
   * @return boolean
   */
  public boolean tryToUseItem(Item itemToTry) {
    boolean obstacleResolved = false;

    if (requiredItem.equals(itemToTry)) {
      resolve(itemToTry);

      obstacleResolved = true;
    }

    return obstacleResolved;
  }

  /**
   * Getter for resolution which describes the resolution of the obstacle.
   *
   * @return String
   */
  public String getResolution() {
    // getter resolution
    return resolution;
  }

  /**
   * Returns the state of resolution. If obstacle is successfully resolved, return true, else false.
   *
   * @return boolean
   */
  public boolean isResolved() {
    // getter resolved
    return resolved;
  }

  /**
   * Returns the required item to resolve the obstacle.
   *
   * @return Object
   */
  public Item getRequiredItem() {
    // getter required
    return requiredItem;
  }

  /**
   * Sets state of obstacle to true.
   */
  private void resolve(Item itemToResolve) {
    this.resolved = true;

    itemToResolve.consume();
  }

}
