package model;

/**
 * Obstacle can block passages. They can be solved with one or more item or a riddle. They can react when
 * character interacts with it or when an item is used.
 * 
 * The name of the obstacle is not very important, the player never currently never sees it.
 * The description should tell the player that he is blocked from progressing and give some clues how to solve it.
 * The resolution should tell the player how he uses the item to clear the obstacle.
 * The amount of damagepoints tells how much the character takes when failing to solve the obstacle.
 * The reactions may contain some text that will be printed when the corrosponding action is taken (WIP).
 *
 * @author Simone273
 */
public abstract class Obstacle extends GameObject {
  // TODO use a "tryToUse(Object)" method? More inheritance tricks!!

  private String resolution;
  protected boolean resolved = false;
  private int damagepoints;
  private String usedFalseItem;
  private String usedCorrectItem;
  private String walkingAway;

  /**
   * Constructor
   */
  public Obstacle(String name, String description, String resolution) {
    super(name, description);
    this.resolution = resolution;
  }

  /**
   *
   * @param object
   * @return
   */
  public abstract boolean tryToSolve(Object object);

  /**
   * Getter for resolution, which describes the resolution of the obstacle.
   *
   * @return String
   */
  public String getResolution() {
    return resolution;
  }
  
  /**
   * Set state of obstacle to true, when item has been used and needs to be consumed. Item can
   * either be consumed or not when resolving obstacle.
   *
   * @param itemToResolve Item
   */
  protected void resolve() {
    this.resolved = true;
  }

  /**
   * Returns the state of resolution. If obstacle is successfully resolved, return true, else
   * false.
   *
   * @return boolean
   */
  public boolean isResolved() {
    return resolved;
  }

  public void setUsedFalseItem(String usedFalseItem) {
    this.usedFalseItem = usedFalseItem;
  }

  public void setUsedCorrectItem(String usedCorrectItem) {
    this.usedCorrectItem = usedCorrectItem;
  }

  public void setWalkingAway(String walkingAway) {
    this.walkingAway = walkingAway;
  }

  public void setResolution(String resolution) {
    this.resolution = resolution;
  }

  /**
   * Reaction of Obstacle when somebody used the false item.
   */
  public String getReactionToFalseItem() {
    return usedFalseItem;
  }

  /**
   * Reaction of Obstacle when someone used the correct item.
   */
  public String getReactionToCorrectItem() {
    return usedCorrectItem;
  }

  /**
   * Character walks away, Obstacle reacts.
   */
  public String getWalkAwayReaction() {
    return walkingAway;
  }

  /**
   * Set the damage an Obstacle does and return the damagepoints done to the character.
   */
  public void setDamagepoints(int damage) {
    damagepoints = damage;
  }

  public int getDamagepoints() {
    return damagepoints;
  }
}

