package model;
/**
 * character moves through the world, picks up items, moves through passages and resolves obstacles
 * has lifepoints, can get more or use them, dies if no lifepoints left
 *
 * @author Simone273
 */

import java.util.ArrayList;
import java.util.List;

public class Character {

  private Place currentPlace = null;
  private List<Item> itemsInInventory = new ArrayList<>();
  private int lifepoints;
  private boolean dead = false;

  /**
   * Constructor for games where the character won't take damage.
   *
   * @param startingPlace Place
   */
  public Character(Place startingPlace) {
    this(startingPlace, 1);
  }

  /**
   * Constructor for games where the character may take damage.
   *
   * @param startingPlace Place
   */
  public Character(Place startingPlace, int lifepoints) {
    itemsInInventory = new ArrayList<>();
    currentPlace = startingPlace;
    this.lifepoints = lifepoints;
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

  public void takeDamage(int damagepoints) {
    lifepoints = lifepoints - damagepoints;
    if (lifepoints < 0) {
      lifepoints = 0;
    }
  }

  public void healDamage(int healingpoints) {
    lifepoints = lifepoints + healingpoints;
  }

  public boolean isDead() {
    if (lifepoints == 0) {
      dead = true;
    }
    return dead;

  }

  public int getLifepoints() {
    return lifepoints;
  }


}

