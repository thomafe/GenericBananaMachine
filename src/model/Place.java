package model;

import java.util.ArrayList;
import java.util.List;

public class Place extends GameObject{



	// Attribute
	public List<Passage> passages;
	private List<Item> itemsOnTheFloor;

	// noch ein constructor für place mit inhalt diesmal
	public Place(String name, String description) {
		super(name, description);
		passages = new ArrayList<>();
		itemsOnTheFloor = new ArrayList<>();

		// TODO set passages
	}

	// getter für passages
	public List<Passage> getPassages() {
		return passages;
	}

	/**
	 * Add a single item to the item list.
	 *
	 * @param item
	 */
	public void addItemOnTheFloor (Item item) {
		itemsOnTheFloor.add(item);
	}

	public void setItemsOnTheFloor(List<Item> itemsOnTheFloor) {
		this.itemsOnTheFloor = itemsOnTheFloor;
	}

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
}
