package model;

public class Obstacle {


	private String description;


	private String resolution;


	private boolean resolved = false;


	private Item requiredItem;

	// constructor
	public Obstacle(String description, Item requiredItem, String resolution) {
		this.description = description;
		this.requiredItem = requiredItem;
		this.resolution = resolution;
	}
	
	public boolean tryToUseItem(Item itemToTry) {
		// TODO check for correct Item, return correct value
		// return true if correct item
		if (requiredItem.equals(itemToTry)){
			return true;
		}
		else{
			return false;
		}

	}

   // getter description
	public String getDescription() {
		return description;
	}

	//getter resolution
	public String getResolution() {
		return resolution;
	}

	// getter resolved
	public boolean isResolved() {
		return resolved;
	}

   // getter required
	public Item getRequiredItem() {
		return requiredItem;
	}

	public void resolved (){
		this.resolved= true;
	}
}
