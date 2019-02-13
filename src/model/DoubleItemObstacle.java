package model;

public class DoubleItemObstacle extends ItemObstacle{

  public boolean tryToUseItem(Item itemToTry) {
  if (additionalItem.equals(itemToTry)) {
    consume(itemToTry);
    resolve();
    correctItemUsed = true;
    super.tryToUseItem(itemToTry);
     }

  return correctItemUsed;
  }

}
