package model;



public class ItemObstacle extends Obstacle{
  private Item requiredItem;
  private Item additionalItem;
  private boolean additionalItemResolved = false;
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

    if (additionalItem == null) {
      // One item required
      if (requiredItem.equals(itemToTry)) {
        consume(itemToTry);
        resolve();
        correctItemUsed = true;

      }


    } else {

      // Two items required
      if (additionalItem.equals(itemToTry)) {
        consume(itemToTry);
        additionalItemResolved = true;
        correctItemUsed = true;


      } else if (requiredItem.equals(itemToTry) && additionalItemResolved == true) {
        resolve();
        consume(itemToTry);
        correctItemUsed = true;

      }
    }

    return correctItemUsed;
  }


}
