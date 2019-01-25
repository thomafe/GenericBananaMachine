package model;

import java.util.ArrayList;
import java.util.List;

public class Character {
	
	private Place currentPlace = null;
	private List<Item> itemsInInventory = new ArrayList<>();
	
	public Character(Place startingPlace) {
		itemsInInventory = new ArrayList<>();
		currentPlace = startingPlace;
	}

	// bei gang durch passage wird current place zum neuen place
	public void move(Passage passage) {
		currentPlace= passage.usePassage(currentPlace);

	}

	/**
	 * Check if item is on the floor, if yes, take item and add to the item list. Picked up Item on the floor will be
	 * removed from the floor.
	 *
	 * @param itemToPickUp
	 */
	public void takeItem(Item itemToPickUp) {
		// check if item is in the room and remove from inventory

		List<Item> itemsOnTheFloor = currentPlace.getItemsOnTheFloor();

		for (int i=0; i< itemsOnTheFloor.size(); i++){

			itemsOnTheFloor.get(i);

			if (itemToPickUp.equals(itemsOnTheFloor.get(i))){

				itemsInInventory.add(itemToPickUp);
				currentPlace.removeItemFromPlace(itemsOnTheFloor.get(i));

			} else {
				// TODO: Output that no item is on the floor
			}
		}
	}
	
	public void useItem(Item item) {
	}

	/**
	 * Getter for current Place.
	 *
	 * @return Place
	 */
	public Place getCurrentPlace () {

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
