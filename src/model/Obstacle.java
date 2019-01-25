package model;

public class Obstacle {
	
	public String description;
	public String resolution;
	public boolean resolved = false;
	
	private Item requiredItem;
	
	public Obstacle(String description, Item requiredItem) {
		this.description = description;
		this.requiredItem = requiredItem;
	}
	
	public boolean tryToUseItem(Item itemToTry) {
		// TODO check for correct Item, return correct value
		return false;
	}

}
