package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Are in a place, can contain items, may be blocked by obstacles.
 * 
 * The name should describe the furniture.
 * The obstacle follows the normal rules, but the resolution should mention the items being dropped into the room.
 *
 * @author thomafe
 */
public class Furniture extends GameObject {

  private Obstacle obstalce;
  private List<Item> itemsInside = new ArrayList<>();
  private String linkedPlace;

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
   * THis one is needed for Parser / Level Design!
   *
   * @param name String
   * @param description String
   */
  public Furniture(String name, String description, String linkedPlace) {
    super(name, description);
    this.linkedPlace = linkedPlace;
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
   * Constructor for Furniture without ItemList.
   * Currently Used in XML Parser.
   *
   * @param name String
   * @param description String
   * @param obstacle Obstacle
   */
  public Furniture(String name, String description, Obstacle obstacle) {
    this(name, description);

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
   * Clear the contents of the furniture.
   */
  public void emptyOutFurniture() {
    itemsInside = Collections.emptyList();
  }

  /**
   * Add another item to the item list.
   *
   * @param item Item
   */
  public void addItem(Item item) {
    this.itemsInside.add(item);
  }

  /**
   * Setter for obstacle.
   *
   * @param obstacle Obstacle
   */
  public void setObstalce(Obstacle obstacle) {
    this.obstalce = obstacle;
  }

  /**
   * Setter for linkedPlace String which is needed for Parser / Level Design.
   *
   * @param linkedPlace String
   */
  public void setLinkedPlace(String linkedPlace) {
    this.linkedPlace = linkedPlace;
  }

  /**
   * Getter for linkedPlace which is needed for Parser / Level Design.
   *
   * @return Place
   */
  public String getLinkedPlace() {
    return linkedPlace;
  }

}
