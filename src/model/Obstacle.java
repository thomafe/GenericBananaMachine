package model;

/**
 * Obstacle can block passages, can be solved with one or more item or a riddle, can react when
 * character interacts with it or when an item is used
 *
 * @author Simone273
 */
public class Obstacle extends GameObject {

  // TODO use inheritance to make this a bunch cleaner
  private String resolution;
  protected boolean resolved = false;
  private boolean consumesItem = true;



  private int damagepoints;
  private String contactWithItem;
  private String usedFalseItem;
  private String usedCorrectItem;
  private String walkingAway;
  /**
   * Create a new obstacle that takes one item that will be consumed.
   *
   * @param name String
   * @param description String
   * @param requiredItem Item
   */
  public Obstacle(String name, String description, String resolution, Item requiredItem) {
    super(name, description);
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

    this.resolution = resolution;
  }





  /**
   * Getter for resolution which describes the resolution of the obstacle.
   *
   * @return String
   */
  public String getResolution() {
    return resolution;
  }

  /**
   * Returns the state of resolution. If obstacle is successfully resolved, return true, else false.
   *
   * @return boolean
   */
  public boolean isResolved() {
    return resolved;
  }

  /**
   * Set state of obstacle to true, when item has been used and needs to be consumed. Item can
   * either be consumed or not when resolving obstacle
   *
   * @param itemToResolve Item
   */
  protected void resolve() {
    this.resolved = true;
  }

  protected void consume(Item itemToResolve) {
    if (consumesItem) {
      itemToResolve.consume();
    }
  }

  /**
   * Set whether the item will be consumed upon resolving the obstacle.
   */
  public void setConsumesItem(boolean consumesItem) {
    this.consumesItem = consumesItem;
  }


  public void setContactWithItem(){
    this.contactWithItem = contactWithItem;
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
   * Method that will be run when Character gets in Contact with Item
   * Obstacle reacts to contact then
   *
   */
  public String getReactionToContact() {
   return contactWithItem;}
//   * Reaction of Obstacle when somebody uses the wrong item

    //
  public String getReactionToFalseItem(){
    return usedFalseItem;
  }

  /**
   * Reaction of Obstacle when someone used the correct item
   *
   */
  public String getReactionToCorrectItem() {
    return usedCorrectItem;
  }

  /**
   * Character walks away, Obstacle reacts
   *
   */
  public String getWalkAwayReaction() {
    return walkingAway;
  }

  /**
   * Set the damage an Obstacle does and return the damagepoints done to the character
   * @param damage
   */
  public void setDamagepoints(int damage){
    damagepoints =  damage;
  }

  public int getDamagepoints() {
    return damagepoints;
  }


}

