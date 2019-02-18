package model;

import model.superclasses.GameObject;

/**
 * Items can be picked up by the character, added to inventory, used to resolve obstacles, and be
 * consumed.
 * 
 * An items name should describe it.
 * An items description should give some clues about where to use it or at least be funny.
 *
 * @author Simone273
 */
public class Item extends GameObject {

  private boolean consumed = false;

  /**
   * Constructor.
   *
   * @param name String
   * @param description String
   */
  public Item(String name, String description) {
    super(name, description);
  }

  /**
   * Gives state of consume of an item. Returns true if item is already used (consumed), else false.
   *
   * @return boolean
   */
  public boolean isConsumed() {
    return consumed;
  }

  /**
   * Sets consume state on true if item has been used (consumed).
   */
  public void consume() {
    this.consumed = true;
  }
}
