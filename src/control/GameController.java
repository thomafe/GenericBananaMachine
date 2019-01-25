package control;

import model.*;

public class GameController {

    public static void main (String[] args) {

        // initialize basic game settings
        initGame();
    }

    private static void initGame () {

        Control ctrl = new Control();

        Place entrance = new Place("Entrance", "This is your starting area.");
        Place secondRoom = new Place("Hall of Doom", "This is the final Boss Room...not. It just sounds cool.");
        model.Character character = new model.Character(entrance);

        Passage pas1 = new Passage("Door of Doom", "This Door seems to be very heavy and doomed", entrance, secondRoom);

        Item item1 = new Item("Lightsaber", "This is a powerful jedi melee weapon.");
        Item item2 = new Item("Banana", "This is a powerful fruit which makes you feel like a monkey.");
        entrance.addItemOnTheFloor(item1);
        secondRoom.addItemOnTheFloor(item2);

        // character tries to take item from floor
        character.takeItem(item1);

        // character calls List of Inventory
        for(Item item: character.getItemsInInventory()) {

            System.out.println(item.getName());
        }

        // character tries to move
        // TODO: character tries to move and if success he moves to the next place.




        // TODO: create further objects and connections between Places and Obstacles
    }

}
