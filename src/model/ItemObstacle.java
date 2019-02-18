package model;

/**
 * Obstacle that can be resolved with using the correct item. When resolved, passage is not blocked
 * anymore.
 *
 * @author
 */
public class ItemObstacle extends Obstacle {

  private Item requiredItem;
  private boolean consumesItem = true;

  /**
   * Create a new obstacle that takes one item that will be consumed.
   *
   * @param name String
   * @param description String
   * @param requiredItem Item
   */
  public ItemObstacle(String name, String description, String resolution, Item requiredItem) {
    super(name, description, resolution);
    this.requiredItem = requiredItem;
  }

  /**
   * Checks if required item is correct, if yes resolves Obstacle
   *
   * @param itemToTry Item
   * @return boolean
   */
  public boolean tryToUseItem(Item itemToTry) {
    boolean correctItemUsed = false;

    if (requiredItem.equals(itemToTry)) {
      consume(itemToTry);
      correctItemUsed = true;
      resolve();
    }
    return correctItemUsed;
  }

  protected void consume(Item itemToResolve) {
    if (consumesItem) {
      itemToResolve.consume();
    }
  }

  /**
   * Setter for Interactions with Obstacle
   */
  public void setConsumesItem(boolean consumesItem) {
    this.consumesItem = consumesItem;
  }

}



