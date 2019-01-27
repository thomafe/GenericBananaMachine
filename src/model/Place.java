package model;

import java.util.ArrayList;
import java.util.List;

public class Place extends GameObject{

	// Attribute
	private List<Passage> passages;
	private List<Item> itemsOnTheFloor;

	/**
	 * COnstructor.
	 *
	 * @param name String
	 * @param description String
	 */
	public Place(String name, String description) {
		// noch ein constructor für place mit inhalt diesmal
		super(name, description);
		passages = new ArrayList<>();
		itemsOnTheFloor = new ArrayList<>();

		// TODO set passages
	}

	/**
	 * Getter for connected Passages to this Place.
	 *
	 * @return Passage List
	 */
	public List<Passage> getPassages() {
		// getter for passages
		return passages;
	}

	/**
	 * Add a single item to the item list.
	 *
	 * @param item Item
	 */
	public void addItemOnTheFloor (Item item) {
		itemsOnTheFloor.add(item);
	}

	/**
	 * Setter for including Item which can be found in this Place.
	 *
	 * @param itemsOnTheFloor Item List
	 */
	public void setItemsOnTheFloor(List<Item> itemsOnTheFloor) {
		this.itemsOnTheFloor = itemsOnTheFloor;
	}

	/**
	 * Removes Item from this Place.
	 *
	 * @param itemToRemove Item
	 */
	public void removeItemFromPlace(Item itemToRemove){
		this.itemsOnTheFloor.remove(itemToRemove);
	}

	/**
	 * Getter for Items on the floor
	 * 
	 * @return itemsOnTheFloor
	 */
	public List<Item> getItemsOnTheFloor() {
		return itemsOnTheFloor;
	}

	/**
	 * Connect a committed Passage to this Place.
	 *
	 * @param pPassage Passage
	 */
	public void addPassage(Passage pPassage){
		passages.add(pPassage);
	}
}
