package control;

import model.*;
import view.*;

import model.Character;
import java.util.Scanner;

public class GameController {

    public static void main (String[] args) {

        // initialize basic game settings
        initGame();

    }

//    private static void initGame () {
//
//        Scanner scanner = new Scanner(System.in);
//
//
//        Place entrance = new Place("Entrance", "This is your starting area.");
//        Place secondRoom = new Place("Hall of Doom", "This is the final Boss Room...not. It just sounds cool.");
//        Place thirdRoom = new Place("Lighthouse", "You can't see anything in here because the light is blinding.");
//
//        Character character = new Character(entrance);
//
//        Passage pas1 = new Passage("Door of Doom", "This Door seems to be very heavy and doomed", entrance, secondRoom);
//        Passage pas2 = new Passage("snake pit", "You are greeted by the lovely sound of zzzzzzzzz", secondRoom, thirdRoom);
//        Item item1 = new Item("Lightsaber", "This is a powerful jedi melee weapon.");
//        Item item2 = new Item("Banana", "This is a powerful fruit which makes you feel like a monkey.");
//        entrance.addItemOnTheFloor(item1);
//        secondRoom.addItemOnTheFloor(item2);
//
//        entrance.addPassage(pas1);
//        secondRoom.addPassage(pas2);
//
//		// initialize basic game settings
//		initGame();
//
//	}

	private static void initGame() {

		Scanner scanner = new Scanner(System.in);

		Place entrance = new Place("Entrance", "This is your starting area.");
		Place secondRoom = new Place("Hall of Doom", "This is the final Boss Room...not. It just sounds cool.");
		Place thirdRoom = new Place("Lighthouse", "You can't see anything in here because the light is blinding.");

		Character character = new Character(entrance);

		Passage pas1 = new Passage("Door of Doom", "This Door seems to be very heavy and doomed", entrance, secondRoom);
		Passage pas2 = new Passage("snake pit", "You are greeted by the lovely sound of zzzzzzzzz", secondRoom,
				thirdRoom);
		Item item1 = new Item("Lightsaber", "This is a powerful jedi melee weapon.");
		Item item2 = new Item("Banana", "This is a powerful fruit which makes you feel like a monkey.");
		entrance.addItemOnTheFloor(item1);
		secondRoom.addItemOnTheFloor(item2);

		entrance.addPassage(pas1);
		secondRoom.addPassage(pas2);

		Control ctrl = new Control(character);


		Output output = new Output(ctrl);
		Input input = new Input(output, ctrl);

		output.greeting();
        output.listOptions();
        output.lookAtCurrentPlace();

		do {


			input.readInput();

		} while (true);

		/*
		 * 
		 * // character tries to take item from floor character.takeItem(item1);
		 * 
		 * // character calls List of Inventory for(Item item:
		 * character.getItemsInInventory()) {
		 * 
		 * System.out.println(item.getName()); }
		 */

		// character tries to move
		
	}

}
