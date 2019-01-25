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
	
	public void move(Passage passage) {
		
	}
	
	public void takeItem(Item itemOnFloor) {
		// TODO check if that item is in this room
		// TODO remove from room, put into inventory
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
