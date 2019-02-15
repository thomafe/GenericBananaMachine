package model;

import java.util.Collections;
import java.util.List;
import model.superclasses.GameObject;

/**
 * Are in a place, can contain items, may be blcoked by obatacles
 *
 * @author thomafe
 */
public class Furniture extends GameObject {

  private Obstacle obstalce;
  private List<Item> itemsInside = null;

  /**
   * Constructor for Furniture.
   *
   * @param name String
   * @param description String
   */
  public Furniture(String name, String description) {
    super(name, description);
  }

  /**
   * Constructor for Furniture.
   *
   * @param name String
   * @param description String
   * @param itemsInside List<Item>
   */
  public Furniture(String name, String description, List<Item> itemsInside) {
    this(name, description);

    this.itemsInside = itemsInside;
  }

  /**
   * Constructor for Furniture.
   *
   * @param name String
   * @param description String
   * @param itemsInside List<Item>
   * @param obstacle Obstacle
   */
  public Furniture(String name, String description, List<Item> itemsInside, Obstacle obstacle) {
    this(name, description, itemsInside);

    this.obstalce = obstacle;
  }

  /**
   * Getter for Obstacle.
   *
   * @return Obstacle
   */
  public Obstacle getObstacle() {
    return obstalce;
  }

  /**
   * Get the items inside if there is no blocking obstacle. If there is an unresolved obstacle in
   * the way, there will be no items received.
   * 
   * @return Collections
   */
  public List<Item> receiveItemsInSide() {
    if (obstalce == null || obstalce.isResolved()) {
      return itemsInside;
    } else {
      return Collections.emptyList();
    }
  }

  /**
   * Clear collection of items.
   */
  public void emptyOutFurniture() {
    itemsInside = Collections.emptyList();
  }

}
