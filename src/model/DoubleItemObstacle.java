package model;

public class DoubleItemObstacle extends ItemObstacle {

  private Item additionalItem;
  private boolean additionalItemResolved = false;

  /**
   * Create a new obstacle that takes two items that will be consumed. Careful! Additional item is
   * required.
   *
   * @param name String
   * @param description String
   * @param requiredItem Item
   * @param additionalItem Item
   */
  public DoubleItemObstacle(String name, String description, String resolution, Item requiredItem,
      Item additionalItem) {
    super(name, description, resolution, requiredItem);

    this.additionalItem = additionalItem;
  }

  @Override
  public boolean tryToUseItem(Item itemToTry) {
    boolean correctItemUsed = false;
    if (additionalItem.equals(itemToTry)) {
      consume(itemToTry);

      correctItemUsed = true;
      // additionalItemResolved= true;
      super.tryToUseItem(itemToTry);

    }

    return correctItemUsed;
  }

}
