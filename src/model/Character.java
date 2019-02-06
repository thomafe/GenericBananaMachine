package model;

import java.util.ArrayList;
import java.util.List;

public class Character {

  private Place currentPlace = null;
  private List<Item> itemsInInventory = new ArrayList<>();

  /**
   * Constructor.
   *
   * @param startingPlace Place
   */
  public Character(Place startingPlace) {
    itemsInInventory = new ArrayList<>();
    currentPlace = startingPlace;
  }

  /**
   * Sets current Place to connected Place by passing a Passage.
   *
   * @param passage Passage
   */
  // bei gang durch passage wird current place zum neuen place
  public void move(Passage passage) {
    currentPlace = passage.usePassage(currentPlace);
  }

  /**
   * Puts an item into the players inventory.
   *
   * @param itemToPickUp Item
   */
  public void takeItem(Item itemToPickUp) {
    itemsInInventory.add(itemToPickUp);
  }

  /**
   * Removes an item from the characters inventory. If that item isn't in the players inventory,
   * nothing happens.
   * 
   * @param itemToRemove - The item to be removed from the characters inventory
   */
  public void removeItem(Item itemToRemove) {
    itemsInInventory.remove(itemToRemove);
  }

  /**
   * Use Item to solve Obstocle.
   *
   * @param item
   */
  public void useItem(Item item) {}

  /**
   * Getter for current Place.
   *
   * @return Place
   */
  public Place getCurrentPlace() {

    return currentPlace;
  }

  /**
   * Getter for items.
   * 
   * @return itemsInInventory
   */
  public List<Item> getItemsInInventory() {
    return itemsInInventory;
  }

}
