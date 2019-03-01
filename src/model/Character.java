package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Character can be in a place, pick up items, move through passages and resolve obstacles.
 * He has a certain amount of lifepoints and can get more of them or loose them.
 * If no hitpoints are left, character is dead.
 *
 * @author Simone273
 */
public class Character {
  private Place currentPlace = null;
  private List<Item> itemsInInventory = new ArrayList<>();
  private int hitpoints;
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
  public Character(Place startingPlace, int hitpoints) {
    itemsInInventory = new ArrayList<>();
    currentPlace = startingPlace;
    this.hitpoints = hitpoints;
  }

  /**
   * Sets current Place to connected Place by passing a Passage.
   *
   * @param passage Passage
   */
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
   * @param itemToRemove - The item to be removed from the characters inventory.
   */
  public void removeItem(Item itemToRemove) {
    itemsInInventory.remove(itemToRemove);
  }

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

  /**
   * Damage the character.
   * 
   * @param damagepoints
   */
  public void takeDamage(int damagepoints) {
    hitpoints = hitpoints - damagepoints;
    if (hitpoints < 0) {
      hitpoints = 0;
    }
  }

  /**
   * Heal the character.
   * 
   * @param healingpoints
   */
  public void healDamage(int healingpoints) {
    hitpoints = hitpoints + healingpoints;
  }

  /**
   * Check if the character is dead.
   * 
   * @return
   */
  public boolean isDead() {
    if (hitpoints == 0) {
      dead = true;
    }
    return dead;
  }

  /**
   * Get the current lifepoints of the character.
   * 
   * @return
   */
  public int getHitpoints() {
    return hitpoints;
  }

  public List<String> getInventoryString(){
    List<String> items = new ArrayList<>();
    for (Item item : getItemsInInventory()) {
      items.add(item.getName());
    }

    return items;
  }
}

