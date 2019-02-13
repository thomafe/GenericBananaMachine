package model;

public class DoubleItemObstacle extends ItemObstacle{

  public boolean tryToUseItem(Item itemToTry) {
  if (additionalItem.equals(itemToTry)) {
    consume(itemToTry);

    correctItemUsed = true;
    // additionalItemResolved= true;
    super.tryToUseItem(itemToTry);

     }

  return correctItemUsed;
  }

}
