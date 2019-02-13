package model;

import java.util.Collections;
import java.util.List;
import model.superclasses.Approachable;
import model.superclasses.GameObject;

/**
 *Are in a place, can contain items, may be blcoked by obatacles
 *
 * @author thomafe
 */
public class Furniture extends GameObject implements Approachable {
  
  private Obstacle obstalce;
  private List<Item> itemsInside = null;

  public Furniture(String name, String description) {
    super(name, description);
  }
  
  public Furniture(String name, String description, List<Item> itemsInside) {
    this(name, description);
    
    this.itemsInside = itemsInside;
  }
  
  public Furniture(String name, String description, List<Item> itemsInside, Obstacle obstacle) {
    this(name, description, itemsInside);
    
    this.obstalce = obstacle;
  }
  
  public Obstacle getObstacle() {
    return obstalce;
  }
  
  /**
   * Get the items inside if there is no blocking obstacle. If there is an unresolved obstacle in the way, there will be no items received.
   * 
   * @return
   */
  public List<Item> receiveItemsInSide(boolean takeOutItems) {
    if(obstalce == null || obstalce.isResolved()) {
      if(takeOutItems) {
        itemsInside = Collections.emptyList();
      }
      return itemsInside;
    } else {
      return Collections.emptyList();
    }
  }
  
}
