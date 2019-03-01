package model;

/**
 * Obstacle that can be resolved with using the correct item. When resolved, passage is not blocked
 * anymore.
 *
 * @author Simone
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
  @Override
  public boolean tryToSolve(Object itemToTry) {
    boolean correctItemUsed = false;

    if (itemToTry instanceof Item && requiredItem.equals(itemToTry)) {
      consume((Item)itemToTry);
      correctItemUsed = true;
      resolve();
    }
    return correctItemUsed;
  }

  /**
   * Consume the correct item. Not all obstacles do consume the item(s). 
   * 
   * @param itemToResolve
   */
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



