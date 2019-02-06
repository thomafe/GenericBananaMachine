package model;

/**
 * Obstacle can block passages, can be solved with one or more item or a riddle, can react when character
 * interacts with it or when an item is used
 *
 * @author Simone273
 */
public class Obstacle extends GameObject {

  private String resolution;
  private boolean resolved = false;
  private boolean consumesItem = true;
  private Item requiredItem;
  private Item additionalItem;
  private String riddleAnswer;
  private boolean additionalItemResolved = false;



  /**
   * Create a new obstacle that takes one item that will be consumed.
   *
   * @param name String
   * @param description String
   * @param requiredItem Item
   */
  public Obstacle(String name, String description, String resolution, Item requiredItem) {
    super(name, description);
    this.requiredItem = requiredItem;
    this.resolution = resolution;
  }

  /**
   * Create a new obstacle that takes two items that will be consumed. Careful! Additional item is
   * reqq
   *
   * @param name String
   * @param description String
   * @param requiredItem Item
   * @param additionalItem Item
   */
  public Obstacle(String name, String description, String resolution, Item requiredItem,
      Item additionalItem) {
    super(name, description);
    this.requiredItem = requiredItem;
    this.additionalItem = additionalItem;
    this.resolution = resolution;
  }

  /**
   * Create a new obstacle that takes a riddle answer to be solved.
   *
   * @param name String
   * @param description String
   * @param riddleAnswere String
   */
  public Obstacle(String name, String description, String resolution, String riddleAnswere) {
    super(name, description);
    this.riddleAnswer = riddleAnswere;
    this.resolution = resolution;
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

    if (additionalItem == null) {
      // Only one item
      if (requiredItem.equals(itemToTry)) {
        resolve(itemToTry);
        consume(itemToTry);
        correctItemUsed = true;

      }
    } else {

      // Two item required
      if (additionalItem.equals(itemToTry)) {
        consume(itemToTry);
        additionalItemResolved = true;
        correctItemUsed= true;


      } else if (requiredItem.equals(itemToTry) && additionalItemResolved == true) {
        resolve(itemToTry);
        consume(itemToTry);
        correctItemUsed = true;

      }
    }

    return correctItemUsed;
  }

  /**
   * Check if the answer for the riddle is correct if yes return true fo obstacleResolved so that
   * Obstacle gets resolved if no return false
   */
  public boolean tryToAnswerRiddle(String answerForRiddle) {
    boolean obstacleResolved = false;
    if (riddleAnswer.equals(answerForRiddle)) {
      obstacleResolved = true;
    }
    return obstacleResolved;
  }

  /**
   * Getter for resolution which describes the resolution of the obstacle.
   *
   * @return String
   */
  public String getResolution() {
    // getter resolution
    return resolution;
  }

  /**
   * Returns the state of resolution. If obstacle is successfully resolved, return true, else
   * false.
   *
   * @return boolean
   */
  public boolean isResolved() {
    // getter resolved
    return resolved;
  }

  /**
   * Set state of obstacle to true, when item has been used and needs to be consumed. Item can
   * either be consumed or not when resolving obstacle
   *
   * @param itemToResolve Item
   */
  private void resolve(Item itemToResolve) {
    this.resolved = true;


  }

  private void consume(Item itemToResolve) {
    if (consumesItem == true) {
      itemToResolve.consume();
    }
  }

  /**
   * Set whether the item will be consumed upon resolving the obstacle.
   */
  public void setConsumesItem(boolean consumesItem) {
    this.consumesItem = consumesItem;
  }

  public void reactsToContact() {
    System.out.println("The Obstacle is slightliy buzzing, it seems to notice your presence");
  }

  public void reactToFalseItem() {
    System.out.println("Obstacle is shaking, seems you've used the wrong item ");
  }

  public void reactsToCorrectItem() {
    System.out.println("Obstacle is gleaming, seems you've used the right item");
  }

  public void walkAway() {
    System.out.println("You can run from the obstacle but not from your own failure");
  }
}

