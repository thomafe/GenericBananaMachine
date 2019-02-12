package model;

/**
 * item can be picked up by the character, added to inventory, used to resolve obstacles, and be consumed
 *
 * @author Simone273
 */
public class Item extends GameObject {

  // Attribute
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
