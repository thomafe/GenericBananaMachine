package model;

import java.util.ArrayList;
import java.util.List;

public class Place {
	
	public String description;
	public List<Passage> passages;
	public List<Item> itemsOnTheFloor;
	
	public Place() {
		passages = new ArrayList<>();
		itemsOnTheFloor = new ArrayList<>();
	}
	
	public Place(String description) {
		this();
		// TODO set description and passages
	}

	/**
	 * Getter for description.
	 *
	 * @return String
	 */
	public String getDescription() {

		return description;
	}

}
