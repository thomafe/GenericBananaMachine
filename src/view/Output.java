package view;
import control.Control;
import model.Item;
import model.Obstacle;
import model.Passage;

public class Output {

    Control control = null;
    
//    private final static String[] AVAILABLE_ACTIONS = {"Look at", "Inventory", "Go through"};
    private final static String[] ACTIONS = {"Look at someting", "Look into Inventory", "Go through a passage"};

    //Introduction for the player at the start of the game
    public void Greeting(){
        System.out.println("Hello fellow Player, welcome to your gobsmacking adventure!");
    }

    public void ordinaryEvent(){
        System.out.println("An event happend! Wow!");
    }
    
    
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
    	
    	System.out.println(options.toString());
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
    	
    	System.out.println(itemsInPlace.toString());
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
    	
    	System.out.println(itemsInInventory.toString());
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
    	
    	System.out.println(passages.toString());
    	
    }
    
    public void listOptionsObstacleInteraction(Obstacle obstacle) {
    	StringBuilder obstacleOptions = new StringBuilder();
    	
    	obstacleOptions.append(obstacle.getDescription() + "\n");
    	obstacleOptions.append("What item do you want to use? (Enter nothing to return to the previous place");
    	listInventory();
    }

    public void describePlace(){
        System.out.println(control.lookAtCurrentPlace());
    }

    public void describePassage(int number){
        //System.out.println(control.lookAtPassage(number));
    }
}
