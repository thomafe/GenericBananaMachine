package model;

import model.superclasses.GameObject;

/**
 * Obstacle can block passages, can be solved with one or more item or a riddle, can react when
 * character interacts with it or when an item is used
 *
 * @author Simone273
 */
public abstract class Obstacle extends GameObject {
  // TODO use a "tryToUse(Object)" method? More inheritance tricks!!

  private String resolution;
  protected boolean resolved = false;
  private boolean consumesItem = true;
  private int damagepoints;
  private String contactWithItem;
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
   * Getter for resolution which describes the resolution of the obstacle.
   *
   * @return String
   */
  public String getResolution() {
    return resolution;
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
   * Setter for Interactions with Obstacle
   */
  public void setConsumesItem(boolean consumesItem) {
    this.consumesItem = consumesItem;
  }


  public void setContactWithItem(String contactWithItem){
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
   * Method that will be run when Character gets in Contact with Item Obstacle reacts to contact
   * then
   */
  public String getReactionToContact() {
    return contactWithItem;
  }

  /**
   * Reaction of Obstacle when somebody used the false item
   */
  public String getReactionToFalseItem() {
    return usedFalseItem;
  }

  /**
   * Reaction of Obstacle when someone used the correct item
   */
  public String getReactionToCorrectItem() {
    return usedCorrectItem;
  }

  /**
   * Character walks away, Obstacle reacts
   */
  public String getWalkAwayReaction() {
    return walkingAway;
  }

  /**
   * Set the damage an Obstacle does and return the damagepoints done to the character
   */
  public void setDamagepoints(int damage) {
    damagepoints = damage;
  }

  public int getDamagepoints() {
    return damagepoints;
  }
}

