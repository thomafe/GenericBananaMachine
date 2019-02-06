package model;

public class Obstacle extends GameObject {

  private String resolution;
  private boolean resolved = false;
  private boolean consumesItem = false;
  private Item requiredItem;
  private Item secondItem;
  private String riddleAnswer;
  private boolean secondItemResolved= false;
  // TODO Item merken das schon da war, Methode muss nochmal aufgerufen werden für zweites Item
  // versuchen

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
   * Create a new obstacle that takes two items that will be consumed.
   *
   * @param name String
   * @param description String
   * @param firstItem Item
   * @param secondItem Item
   */
  public Obstacle(String name, String description, String resolution, Item firstItem,
      Item secondItem) {
    super(name, description);
    this.requiredItem = firstItem;
    this.secondItem = secondItem;
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
   * Check if you only have to use one item or if you have to use a 'second item' first. If you
   * don't have to use a second item, check if the item you wanted to use to resolve the obstacle is
   * correct If it is correct, resolve obstacle by returning true for obstacleResolved, if not
   * return false. If u have to use a second, check if the second item is correct and safe that in second item Obstacle
   * resolved= true. When you run the method again with the correct first item, obstacle resolved will be returned.
   *
   * @param itemToTry Item
   * @return boolean
   */
  public boolean tryToUseItem(Item itemToTry) {
    boolean obstacleResolved = false;

    if (secondItem == null) {
      if (requiredItem.equals(itemToTry)) {
        resolve(itemToTry);

        obstacleResolved = true;
      }
    } else {

      if (secondItem.equals(itemToTry)) {
       secondItemResolved= true;

      }

      }

    if (requiredItem.equals(itemToTry)&& secondItemResolved ==  true) {
      resolve(itemToTry);
      obstacleResolved = true;}

    return obstacleResolved;
  }


  /**
   * Check if the answer for the riddle is correct if yes return true fo obstacleResolved so that
   * Obstacle gets resolved if no return false
   * 
   * @param answerForRiddle
   * @return
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
   * Returns the state of resolution. If obstacle is successfully resolved, return true, else false.
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

    if (consumesItem == true) {
      itemToResolve.consume();
    }
  }
  
  /**
   * Set whether the item will be consumed upon resolving the obstacle.
   * 
   * @param consumesItem
   */
  public void setConsumesItem(boolean consumesItem) {
    this.consumesItem = consumesItem;
  }

}
