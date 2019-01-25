package model;

import java.util.ArrayList;
import java.util.List;

public class Place extends GameObject{



	// Attribute
	private List<Passage> passages;
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


	public void setItemsOnTheFloor(List<Item> itemsOnTheFloor) {
		this.itemsOnTheFloor = itemsOnTheFloor;
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
