package view;
import control.Control;
import model.GameObject;
import model.Item;
import model.Obstacle;
import model.Passage;
import model.Place;

public class Output {

    Control control = null;
    
//    private final static String[] AVAILABLE_ACTIONS = {"Look at", "Inventory", "Go through"};
    private final static String[] ACTIONS = {"Look at someting", "Look into Inventory", "Go through a passage", "List Actions"};
    
    // TODO replace "room" in all the strings with currentPlace.getName()
    /**
     * List all things the character can do.
     *  - Look at something
     *  - Look into Inventory
     *  - Go through passage
     *  - Look at room
     * 
     */
    public void listOptions() {
    	StringBuilder options = new StringBuilder();
    	
    	options.append("You can do these things:\n");
    	for(String action : ACTIONS) {
    		options.append(" - " + action + "\n");
    	}
    	
    	doOutput(options.toString());
    }
    
    /**
     * Lists all items in the room (on the floor).
     * 
     */
    public void listItemsInPlace() {
    	StringBuilder itemsInPlace = new StringBuilder();
    	
    	itemsInPlace.append("These items are in the room:\n");
    	for(Item item : control.getCharacter().getCurrentPlace().getItemsOnTheFloor()) {
    		itemsInPlace.append(" - " + item.getName() + "\n");
    	}
    	
    	doOutput(itemsInPlace.toString());
    }
    
    /**
     * Lists all items in characters inventory.
     * 
     */
    public void listInventory() {
    	StringBuilder itemsInInventory = new StringBuilder();
    	
    	itemsInInventory.append("These items are in your inventory:\n");
    	for(Item item : control.getCharacter().getItemsInInventory()) {
    		itemsInInventory.append(" - " + item.getName() + "\n");
    	}
    	
    	doOutput(itemsInInventory.toString());
    }
    
    /**
     * Lists all passages in the current room.
     * 
     */
    public void listPassages() {
    	StringBuilder passages = new StringBuilder();
    	
    	passages.append("These passages lead out of the room:\n");
    	for(Passage passage : control.getCharacter().getCurrentPlace().getPassages()) {
    		passages.append(" - " + passage.getName() + "\n");
    	}
    	
    	doOutput(passages.toString());
    	
    }
    
    /**
     * List the options to interact with an obstacle
     * 
     * @param obstacle
     */
    public void listOptionsObstacleInteraction(Obstacle obstacle) {
    	StringBuilder obstacleOptions = new StringBuilder();
    	
    	obstacleOptions.append(obstacle.getDescription() + "\n");
    	obstacleOptions.append("What item do you want to use? (Enter nothing to return to the previous place");
    	listInventory();
    	
    	doOutput(obstacleOptions.toString());
    }

    /**
     * Look at the currentPlace
     * 
     */
    public void lookAtCurrentPlace(){
        StringBuilder placeDescription = new StringBuilder();
        
        Place currentPlace = control.getCharacter().getCurrentPlace();
        
        placeDescription.append("You are in " + currentPlace.getName() + "\n");
        placeDescription.append(currentPlace.getDescription());
        
       doOutput(placeDescription.toString());
    }
    
    // TODO do we give this strings or does control resolve that?
//    public void lookAtGameObject()
    
    /**
     * 
     * @param number
     */
    public void lookAtGameObject(String objectName) {
    	StringBuilder gameObjectDescription = new StringBuilder();
    	
    	GameObject object = control.findGameObject(objectName);
    	
    	gameObjectDescription.append("You look at " + object.getName() + "\n");
    	gameObjectDescription.append(object.getDescription());
    	
    	doOutput(gameObjectDescription.toString());
    }
    
    public void doOutput(String message) {
    	System.out.println(message);
    }
}
