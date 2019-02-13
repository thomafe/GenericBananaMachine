package model;

import model.superclasses.GameObject;

/**
 * Obstacle can block passages, can be solved with one or more item or a riddle, can react when
 * character interacts with it or when an item is used
 *
 * @author Simone273
 */
public abstract class Obstacle extends GameObject {

  // TODO use inheritance to make this a bunch cleaner
  private String resolution;
  protected boolean resolved = false;
  private boolean consumesItem = true;

  private int damagepoints;
  private String contactWithItem;
  private String usedFalseItem;
  private String usedCorrectItem;
  private String walkingAway;

  public Obstacle(String name, String description, String resolution) {
    super(name, description);
    
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
      // One item required
      if (requiredItem.equals(itemToTry)) {
        consume(itemToTry);
        resolve();
        correctItemUsed = true;

      }


    } else {

      // Two items required
      if (additionalItem.equals(itemToTry)) {
        consume(itemToTry);
        additionalItemResolved = true;
        correctItemUsed = true;


      } else if (requiredItem.equals(itemToTry) && additionalItemResolved == true) {
        resolve();
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

    if (riddleAnswer != null && riddleAnswer.equalsIgnoreCase(answerForRiddle)) {
      resolve();
    }
    return resolved;
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

