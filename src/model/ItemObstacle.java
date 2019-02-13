package model;



public class ItemObstacle extends Obstacle{
  protected Item requiredItem;
  protected Item additionalItem;
  protected boolean additionalItemResolved = false;
  boolean correctItemUsed = false;
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



      // Two items required
        resolve();
        consume(itemToTry);
        correctItemUsed = true;


      return correctItemUsed;
    }


  }



