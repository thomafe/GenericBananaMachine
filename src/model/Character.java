package model;

import java.util.ArrayList;
import java.util.List;

public class Character {
	
	private Place currentPlace = null;
	private List<Item> itemsInInventory = null;
	
	public Character() {
		itemsInInventory = new ArrayList<>();
	}
	
	public Character(Place startingPlace) {
		this();
	}

	// bei gang durch passage wird current place zum neuen place
	public void move(Passage passage) {
		currentPlace= passage.usePassage(currentPlace);

	}
	
	public void takeItem(Item itemToPickUp) {
		// check if item is in the room and remove from inventory

		List<Item> itemsOnTheFloor = currentPlace.getItemsOnTheFloor();

	for (int i=0; i< itemsOnTheFloor.size(); i++){

		itemsOnTheFloor.get(i);
		if (itemToPickUp.equals(itemsOnTheFloor.get(i))){
			itemsInInventory.add(itemToPickUp);
			currentPlace.removeItemFromPlace(itemsOnTheFloor.get(i));

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
