package model;

import java.util.ArrayList;
import java.util.List;

public class Character {
	
	private Place currentPlace = null;
	private List<Item> itemsInInventory = null;
	
	private Character() {
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

}
