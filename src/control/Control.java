package control;

import model.Character;
import model.Item;
import model.Obstacle;
import model.Place;
import view.Input;
import view.Output;
import model.Passage;
import model.GameObject;

public class Control {
	
	private Input in;
	private Output out;

  public static void main (String[] args) {

    // initialize basic game settings
    initGame();
  }

  private static void initGame() {

    Place entrance = new Place("Entrance", "This is your starting area.");
    Place secondRoom = new Place("Hall of Doom",
        "This is the final Boss Room...not. It just sounds cool.");
    Place thirdRoom = new Place("Lighthouse",
        "You can't see anything in here because the light is blinding.");

    Character character = new Character(entrance);

    Passage pas1 = new Passage("Door of Doom", "This Door seems to be very heavy and doomed",
        entrance, secondRoom);
    Passage pas2 = new Passage("snake pit", "You are greeted by the lovely sound of zzzzzzzzz",
        secondRoom,
        thirdRoom);

    Item item1 = new Item("Lightsaber", "This is a powerful jedi melee weapon.");
    Item item2 = new Item("Banana", "This is a powerful fruit which makes you feel like a monkey.");

    Obstacle obstacle = new Obstacle("Blastdoor", "A thick blast door that blocks the way",
        "You melt through the door with your lightsaber!", item1);

    pas1.setObstacle(obstacle);

    entrance.addItemOnTheFloor(item1);
    secondRoom.addItemOnTheFloor(item2);

    Control ctrl = new Control(character);
    ctrl.gameStart();

    do {

      ctrl.getInput().readInput();

    } while (true);

  }

      private Character character = null;

    /**
     * Construct for Control
     */
    public Control (Character pCharacter) {

      character = pCharacter;
      Output output = new Output(this);
      Input input = new Input(output, this);

      this.setOutput(output);
      this.setInput(input);

    }
    
    /**
     * check if passage is successable. If an Obstacle is included, return false.
     * If Passage does not include an Obstacle, Character can move to connected Room -> return true and move Character.
     *
     * @param passageName String
     * @return boolean
     */
    public boolean canMoveCharacter (String passageName) {
    	boolean passageClear = true;
    	Passage destinationPassage = findPassage(passageName);
    	
    	if(destinationPassage == null) {
    		return false;
    	}
    	
        if(this.checkForObstacle(destinationPassage)) {
            passageClear = interactWithObstacle(destinationPassage.getObstacle());
        }
        
        if(passageClear) {
        	character.move(destinationPassage);
        	return true;
        } else {
        	return false;
        }

    }

    /**
     * Check if destinated Passage has Obstacle in it.
     * If yes, return true, else false.
     *
     * @param destinationPassage Passage
     * @return boolean
     */
    public boolean checkForObstacle (Passage destinationPassage) {
        return destinationPassage.hasObstacle();
    }

    /**
     * Allows the character to interact with an obstacle. Loops as long as the character tries to use items on the obstacle.
     * If the obstacle gets resolved the item is consumed and the character moves through the passage.
     * If the character stops trying items the interaction ends and the character stays in that room.
     * 
     * @return whether the character resolved the obstacle or not
     */
    public boolean interactWithObstacle (Obstacle currentObstacle) {
    	boolean obstacleResolved = false;
    	boolean continueTrying = true;
    	
    	Item choosenItem = null;
    	
    	while(continueTrying) {
    		out.listOptionsObstacleInteraction(currentObstacle);

    		choosenItem = findItemInInventory(in.readInSingleLine());
    		
    		if(choosenItem == null) {
    			out.doOutput("You go back");
    			break;
    		}
    		
    		if(currentObstacle.tryToUseItem(choosenItem)) {
    			((Item)choosenItem).consume();
    			// resolute obstacle
    			obstacleResolved = true;
    			continueTrying = false;
    		} else {
    			out.doOutput("That doesn't work");
    		}
    	}
    	
    	return obstacleResolved;
    }

	/**
	 * Tells the character to pick up an item
	 *
	 * @param itemName String
	 * @return boolean
	 */
	public boolean pickUpItem (String itemName) {
		boolean success = false;
		
		Item itemToPickUp = findItemOnTheFloor(itemName);
		
		if(itemToPickUp != null) {
			character.takeItem((Item)itemToPickUp);

			success = true;
		}
			
        return success;
    }

    /**
     * Looks for a object with the given name in the players inventory or in the current place.
     *
     * @param objectName String
     * @return String description
     */
    public GameObject findGameObject(String objectName) {
    	for (Passage passage : character.getCurrentPlace().getPassages()) {
			if(passage.getName().equals(objectName)) {
				// TODO refactor to its own passage
				return passage;
			}
		}
    	
    	for (Item item : character.getCurrentPlace().getItemsOnTheFloor()) {
    		if(item.getName().equals(objectName)) {
    			return item;
    		}
		}
    	
    	for (Item item2 : character.getItemsInInventory()) {
			if(item2.getName().equals(objectName)) {
				return item2;
			}
		}
    	
    	return null;
    }
    
    /**
     * Look for an item on the floor of the current room.
     * 
     * @param itemName
     * @return
     */
    private Item findItemOnTheFloor(String itemName) {
		Item foundItem = null;
    	
    	for (Item item : character.getCurrentPlace().getItemsOnTheFloor()) {
			if(item.getName().equalsIgnoreCase(itemName)) {
				foundItem = item;
			}
		}
    	
    	return foundItem;
    }
    
    /**
     * Look for item in characters inventory.
     * 
     * @param itemName
     * @return
     */
    private Item findItemInInventory(String itemName) {
		Item foundItem = null;
    	
    	for (Item item : character.getItemsInInventory()) {
			if(item.getName().equalsIgnoreCase(itemName)) {
				foundItem = item;
			}
		}
    	
    	return foundItem;
    }

	/**
	 * Returns found Passage after searching it in the current Place where the Character currently is inside.
	 *
	 * @param passageName String
	 * @return Passage
	 */
	private Passage findPassage(String passageName) {
	  Passage foundPassage = null;
    	
	  for (Passage passage : character.getCurrentPlace().getPassages()) {
	    if(passage.getName().equalsIgnoreCase(passageName)) {
	      foundPassage = passage;
				break;
			}
		}
    	return foundPassage;
    }

  public void gameStart(){
    this.getOutput().greeting();
    this.getOutput().listOptions();
    this.getOutput().lookAtCurrentPlace();

  }

	/**
	 * Getter for Character.
	 *
	 * @return Character
	 */
	public Character getCharacter() {
    	return character;
    }
	
	/**
	 * Setter for Output.
	 * @param out
	 */
	public void setOutput(Output out) {
		this.out = out;
	}

  /**
   * Getter for Output
   * @return out
   */
	public Output getOutput(){
	  return out;
  }
	
	/**
	 * Setter for Input.
	 * @param in
	 */
	public void setInput(Input in) {
		this.in = in;
	}

  /**
   * Getter for Input
   * @return in
   */
	public Input getInput(){
	  return in;
  }

}
