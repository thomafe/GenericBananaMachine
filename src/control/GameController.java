package control;

import model.*;

public class GameController {

    public static void main (String[] args) {

        // initialize basic game settings
        initGame();
    }

    private static void initGame () {

        Place p1 = new Place("Entrance", "This is your starting area.");
        model.Character character = new model.Character(p1);

        // TODO: create further objects and connections between Places and Obstacles
    }

}
