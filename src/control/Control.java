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
    public Control () {}

    public boolean moveCharacter (String direction) {

        return true;
    }

    public boolean checkForObstacle (Passage currentPassage) {

        return true;
    }

    public void interactWithObstacle () {

    }

    public boolean pickUpItem () {

        return true;
    }

    public void lookAtCurrentPlace () {

    }

    public void lookAtItem () {

    }

}
