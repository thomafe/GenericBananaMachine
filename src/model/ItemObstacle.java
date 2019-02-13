package model;


public class ItemObstacle extends Obstacle {

  private Item requiredItem;

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
   * Check if you only have to use one item or if you have to use an 'additional item' first. If you
   * don't have to use an additional item, check if the item you wanted to use to resolve the
   * obstacle is correct If it is correct, resolve obstacle by returning true for obstacleResolved,
   * if not return false. If u have to use an additional item , check if the additional item is
   * correct and safe that in additional item Obstacle resolved= true. When you run the method again
   * with the correct first item, obstacle resolved will be returned.
   *
   * @param itemToTry Item
   * @return boolean
   */

  public boolean tryToUseItem(Item itemToTry) {
    boolean correctItemUsed = false;
    
    if (requiredItem.equals(itemToTry) ) {

      consume(itemToTry);
      correctItemUsed = true;
    }
    resolve();
    return correctItemUsed;

  }


}



