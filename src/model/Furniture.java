package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Furniture extends GameObject {
  
  private Obstacle obstalce;
  private List<Item> itemsInside;

  public Furniture(String name, String description) {
    super(name, description);
    
    itemsInside = new ArrayList<>();
  }
  
  public Obstacle getObstacle() {
    return obstalce;
  }
  
  /**
   * Get the items inside if there is no blocking obstacle. If there is an unresolved obstacle in the way, there will be no items received.
   * 
   * @return
   */
  public List<Item> receiveItemsInSide() {
    if(obstalce == null || obstalce.isResolved()) {
      // TODO are the items removed from here then??
      return itemsInside;
    } else {
      return Collections.emptyList();
    }
  }
  
}
