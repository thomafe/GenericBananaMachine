package control;

import model.Character;
import model.Place;
import model.Passage;

public class Control {

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

        if(this.checkForObstacle(destinationPassage)) {
            // Passage has Obstacle

            return false;
        } else {
            // Passage has no Obstacle
            character.move(destinationPassage);

            return true;
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

        if(destinationPassage.hasObstacle()) {
            // Passage has Obstacle
            return true;
        } else {
            // Passage has no Obstacle
            return false;
        }
    }

    public void interactWithObstacle () {

    }

    public boolean pickUpItem (String itemName) {
    	
    	character.getCurrentPlace().getItemsOnTheFloor();
    	
        return true;
    }

    /**
     * Return the currents Place's description.
     */
    public String lookAtCurrentPlace () {

        return character.getCurrentPlace().getDescription();
    }

    public void lookAtItem () {

    }

}
