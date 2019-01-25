package model;

import java.util.ArrayList;
import java.util.List;

public class Place {



	// Attribute
	public String description;



	public List<Passage> passages;


	public List<Item> itemsOnTheFloor;

	// constructor
	public Place() {
		passages = new ArrayList<>();
		itemsOnTheFloor = new ArrayList<>();
	}

	// noch ein constructor für place mit inhalt diesmal
	public Place(String description) {
		this();
		this.description= description;

		// TODO set passages
	}

	// getter description
	public String getDescription() {
		return description;
	}

	// getter für passages
	public List<Passage> getPassages() {
		return passages;
	}

	// getter and setter für items on the floor
	public List<Item> getItemsOnTheFloor() {
		return itemsOnTheFloor;
	}

	public void setItemsOnTheFloor(List<Item> itemsOnTheFloor) {
		this.itemsOnTheFloor = itemsOnTheFloor;
	}

}
