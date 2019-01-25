package model;

public class Obstacle extends GameObject{
	
	public String resolution;
	public boolean resolved = false;
	
	private Item requiredItem;
	
	public Obstacle(String name, String description, Item requiredItem) {
		super(name, description);
		this.requiredItem = requiredItem;
	}
	
	public boolean tryToUseItem(Item itemToTry) {
		// TODO check for correct Item, return correct value
		return false;
	}

}
