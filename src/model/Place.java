package model;

import java.util.ArrayList;
import java.util.List;

public class Place {
	
	public String description;
	public List<Passage> passages;
	private List<Item> itemsOnTheFloor;
	
	public Place() {
		passages = new ArrayList<>();
		itemsOnTheFloor = new ArrayList<>();
	}
	
	public Place(String description) {
		this();
		// TODO set description and passages
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
