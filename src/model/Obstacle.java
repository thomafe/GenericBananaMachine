package model;

public class Obstacle extends GameObject{
	
	private String resolution;
	private boolean resolved = false;
	
	private Item requiredItem;

	/**
	 * Constructor.
	 *
	 * @param name String
	 * @param description String
	 * @param requiredItem Item
	 */
	public Obstacle(String name, String description, Item requiredItem) {
		super(name, description);
		this.requiredItem = requiredItem;
		this.resolution = resolution;
	}

	/**
	 * Receive item and checks if it's equal to the required item to solve the obstacle.
	 * Returns true if the item matches the requirement, else false.
	 *
	 * @param itemToTry Item
	 * @return boolean
	 */
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

	/**
	 * Getter for resolution which describes the resolution of the obstacle.
	 *
	 * @return String
	 */
	public String getResolution() {
		//getter resolution
		return resolution;
	}

	/**
	 * Returns the state of resolution.
	 * If obstacle is successfully resolved, return true, else false.
	 *
	 * @return boolean
	 */
	public boolean isResolved() {
		// getter resolved
		return resolved;
	}

	/**
	 * Returns the required item to resolve the obstacle.
	 *
	 * @return Object
	 */
	public Item getRequiredItem() {
		// getter required
		return requiredItem;
	}

	/**
	 * Sets state of obstacle to true.
	 */
	public void resolved() {
		this.resolved = true;
	}
}
