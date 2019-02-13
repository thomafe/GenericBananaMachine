package model;

public class DoubleItemObstacle extends ItemObstacle{

  public boolean tryToUseItem(Item itemToTry) {
  if (requiredItem.equals(itemToTry)) {
    consume(itemToTry);
    resolve();
    correctItemUsed = true;
    super.tryToUseItem(itemToTry);
     }
  return correctItemUsed;
  }

}
