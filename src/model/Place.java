package model;

import java.util.ArrayList;
import java.util.List;

public class Place {

	/**
	 * Attributes
	 */
	private String description;
	private List<Passage> passages;
	private List<Item> itemsOnTheFloor;

	/**
	 * Construct
	 */
	public Place() {
		passages = new ArrayList<>();
		itemsOnTheFloor = new ArrayList<>();
	}

	/**
	 * Construct
	 *
	 * @param description
	 */
	public Place(String description) {

		this.description = description;

		// TODO set passages
	}

	/**
	 * Getter for description.
	 *
	 * @return String
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Getter for Passages.
	 *
	 * @return passages
	 */
	public List<Passage> getPassages() {
		return passages;
	}

	/**
	 * Getter for Items available in the current Place.
	 * @return items
	 */
	public List<Item> getItemsOnTheFloor() {

		return itemsOnTheFloor;
	}

	/**
	 * Setter for items in the current Place.
	 * 
	 * @param itemsOnTheFloor
	 */
	public void setItemsOnTheFloor(List<Item> itemsOnTheFloor) {

		this.itemsOnTheFloor = itemsOnTheFloor;
	}

}
