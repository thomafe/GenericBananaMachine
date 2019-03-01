package model;

/**
 * Obstacles that require two items to be resolved, the second. Must be resolved so that they don't block
 * passages anymore.
 * 
 * The name is still not relevant.
 * The second object is needed first, the description should hint at that. (TODO thomaf Output for "first item correct)
 * 
 * @author Simone
 */
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

  /**
   * Checks if additional item is correct, if yes, run 'tryToUseItem' in ItemObstacle.
   */
  @Override
  public boolean tryToSolve(Object itemToTry) {
    boolean correctItemUsed = false;

    if (itemToTry instanceof Item && additionalItem.equals(itemToTry)) {
      consume((Item) itemToTry);
      correctItemUsed = true;
      additionalItemResolved = true;
    } else if (additionalItemResolved) {
      correctItemUsed = super.tryToSolve(itemToTry);
    }
    return correctItemUsed;
  }
}
