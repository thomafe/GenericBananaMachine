package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Character can be in place, can move from place to place via passages an item can be in a place.
 *
 * The name of the place should lead with "the" if needed and be short.
 * The description of the place shouldn't be to long and set the feeling of the level.
 *
 * @author Simone273
 */
public class Place extends GameObject {
  private List<Passage> passages;
  private List<GameObject> thingsInTheRoom;

  /**
   * Default constructor. Creates a simple place with nothing in it.
   *
   * @param name String
   * @param description String
   */
  public Place(String name, String description) {
    super(name, description);
    passages = new ArrayList<>();
    thingsInTheRoom = new ArrayList<>();
  }

  /**
   * Getter for connected Passages to this Place.
   *
   * @return Passage List
   */
  public List<Passage> getPassages() {
    return passages;
  }

  /**
   * Adds a new thing to to place.
   * 
   * @param object GameObject
   */
  public void addObjectToPlace(GameObject object) {
    if (!thingsInTheRoom.contains(object)) {
      thingsInTheRoom.add(object);
    }
  }

  /**
   * Removes Item from this Place.
   *
   * @param itemToRemove Item
   */
  public void removeItemFromPlace(Item itemToRemove) {
    this.thingsInTheRoom.remove(itemToRemove);
  }

  /**
   * Getter for Items on the floor
   * 
   * @return itemsOnTheFloor
   */
  public List<GameObject> getObjectsInPlace() {
    return thingsInTheRoom;
  }

  /**
   * Connect a committed Passage to this Place.
   *
   * @param newPassage Passage
   */
  public void addPassage(Passage newPassage) {
    if (!passages.contains(newPassage)) {
      passages.add(newPassage);
    }
  }

  public List<String> getObjectsString() {
    List<String> objects = new ArrayList<>();
    for (GameObject gameObject : getObjectsInPlace()) {
      objects.add(gameObject.getName());
    }

    return objects;
  }
  
  public void removePassage(Passage passage) {
    passages.remove(passage);
  }
}
