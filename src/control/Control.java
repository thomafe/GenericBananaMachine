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

    private Character character = null;
    private Place activePlace = character.getCurrentPlace();

    /**
     * Construct for Control
     */
    public Control () {

    }

    /**
     * check if passage is successable. If an Obstacle is included, return false.
     * If Passage does not include an Obstacle, Character can move to connected Room -> return true and move Character.
     *
     * @param destinationPassage
     * @return boolean
     */
    public boolean canMoveCharacter (Passage destinationPassage) {
    	boolean passageClear = true;
    	
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
     * @param destinationPassage
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
    	boolean continueInteraction = true;
    	Item itemToTry = null;
    	
    	while(continueInteraction) {
    		// TODO tell out to promt the user to try any item or quit
    		// Item = out.whatItemToUseOnObstacle()
    		if(currentObstacle.tryToUseItem(itemToTry)) {
    			itemToTry.consume();
    			// resolute obstacle
    			obstacleResolved = true;
    			break;
    		}
    	}
    	
    	return obstacleResolved;
    }

    public boolean pickUpItem (String itemName) {
    	character.getCurrentPlace().getItemsOnTheFloor();
    	
        return true;
    }

    /**
     * Return the currents Place's description.
     *
     * @return String description
     */
    public String lookAtCurrentPlace () {

        return character.getCurrentPlace().getDescription();
    }

    /**
     * Return the chosen item's description.
     *
     * @param gameObject
     * @return String description
     */
    public String lookAtGameObject (GameObject gameObject) {

        return gameObject.getDescription();
    }

}
