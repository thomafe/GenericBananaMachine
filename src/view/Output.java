package view;

import java.util.List;
import model.Character;
import model.Item;
import model.Obstacle;
import model.Passage;
import model.Place;
import model.superclasses.GameObject;

/**
 * reacts to input from user with output
 *
 * @author Niklas
 */
public class Output {

  public enum errorType {
    CANT_DO_THAT, DOES_NOT_WORK, DONT_HAVE_ITEM, GO_BACK, DONT_MIX, DONT_MIX_MAD, YOU_DEAD,
    DECIDE, NO_PASSAGE
  }

  public enum errorTypeInput {
    NO_ITEM, NO_PASSAGE, THERE_IS_NONE
  }

  public enum successType {
    PICK_UP, OBSTACLE_RESOLUTION, OBSTACLE_REACTION
  }

  public enum endingType {
    YOU_SURE, NO, YES, TRY_AGAIN
  }

  private Character character = null;

  private static final String[] ACTIONS = {"Look at <something>", "Look around",
      "Goto/Use <Passage/Furniture Name>", "Go back to the last passage", "Take <Item Name>",
      "Inventory", "Actions" , "Exit"};

  /**
   * Introduction for the player at the start of the game.
   */
  public void greeting() {
    printString("Hello fellow player!\n"
        + "In this glorious adventure game you can prove your bravery and smartness\n"
        + "by passing the many obstacles that will come in your way\n"
        + "Look for things along the way that might help you and you may stand a chance");

  }

  /**
   * Ending sequence when the game is done, either because of succeed or because of death
   */
  public void goodEnding() {
    printString(
        "Congraltulations, you've made it\n"
            + "You reached the end of the game \n"
            + "passing many obstacles you fought your way through the world \n"
            + "consider yourself a hero now\n");
    youDidItASCI();
  }

  public void badEnding() {
    printString("You failed\n This is the end of the game \n"
        + "This place brought death to you");
    youDidItNotASCI();
  }

  /**
   * List all things the character can do. - Look at something - Look into Inventory - Go through
   * passage - Look at room
   */
  public void listOptions() {
    StringBuilder options = new StringBuilder();

    options.append("You can do these things:\n");
    for (int i = 0; i < ACTIONS.length; i++) {
      options.append(ACTIONS[i]);
      if (i != ACTIONS.length - 1) {
        options.append(" | ");
      }
    }

    printString(options.toString());
  }

  /**
   * Lists all items in the room (on the floor).
   */
  public void listObjectsInPlace(Place currentPlace) {
    if (currentPlace.getObjectsInPlace().isEmpty()) {
      printString("There are no things here.");
    } else {
      StringBuilder thingsOutput = new StringBuilder();

      thingsOutput.append(
          "These things are in " + currentPlace.getName() + ": \n");
      for (GameObject object : currentPlace.getObjectsInPlace()) {
        thingsOutput.append(" - " + object.getName() + "\n");
      }

      printString(thingsOutput.toString());
    }
  }

  /**
   * Lists all items in characters inventory.
   */
  public void listInventory(List<Item> itemsInInventory) {
    if (!itemsInInventory.isEmpty()) {
      StringBuilder itemList = new StringBuilder();

      itemList.append("These items are in your inventory:\n");
      for (Item item : itemsInInventory) {
        itemList.append(" - " + item.getName() + "\n");
      }

      printString(itemList.toString());
    } else {
      printString("You have nothing in your inventory!");
    }
  }

  /**
   * Lists all passages in the current room.
   */
  public void listPassages(Place currentPlace) {
    StringBuilder passageList = new StringBuilder();

    passageList.append(
        "These passages lead out of " + currentPlace.getName() + ":\n");
    for (Passage passage : currentPlace.getPassages()) {
      passageList.append(" - " + passage.getName() + "\n");
    }

    printString(passageList.toString());

  }

  /**
   * List the options to interact with an obstacle
   */
  public void listOptionsObstacleInteraction(Obstacle obstacle) {
    StringBuilder obstacleOptions = new StringBuilder();

    obstacleOptions.append(obstacle.getDescription() + "\n");
    obstacleOptions.append("Do you want to use an item or do you want to \"leave\"?");

    printString(obstacleOptions.toString());
  }

  /**
   * Look at the currentPlace.
   */
  public void lookAtCurrentPlace(Place currentPlace) {
    StringBuilder placeDescription = new StringBuilder();

    placeDescription.append("You are in " + currentPlace.getName() + "\n");
    placeDescription.append(currentPlace.getDescription());

    printString(placeDescription.toString());
  }

  /**
   * Shows GameObject's name / description in console or gives User an Exception if no such item
   * exists.
   *
   * @param objectName String
   */
  public void lookAtGameObject(GameObject object) {
      StringBuilder gameObjectDescription = new StringBuilder();

      gameObjectDescription.append("You look at " + object.getName() + "\n");
      gameObjectDescription.append(object.getDescription());

      printString(gameObjectDescription.toString());
  }

  /**
   * Standard output for unsuccessful operations
   */
  public void noSuccess(errorType type) {
    switch (type) {
      case CANT_DO_THAT:
        printString("You can't do that!");
        listOptions();
        break;
      case DOES_NOT_WORK:
        printString("That doesn't work");
        break;
      case DONT_HAVE_ITEM:
        printString("You don't have this item!");
        break;
      case GO_BACK:
        printString("You decided to go back to " + character.getCurrentPlace().getName());
        break;
      case DONT_MIX:
        printString("Don't mix the bloody commands!");
        break;
      case DONT_MIX_MAD:
        printString("Are you stupid? I said: \"DONT MIX THE BLOODY COMMANDS!\"");
        break;
      case YOU_DEAD:
        printString("You are dieded :(");
        break;
      case DECIDE:
        printString("You can't do both!");
        break;
      case NO_PASSAGE:
        printString("You first have to go through a passage!");
        break;
      default:
        printString("Quite impossible");
    }
  }

  /**
   * Standard output for unsuccessful operations
   */
  public void noSuccess(String userInput, errorTypeInput type) {
    switch (type) {
      case NO_ITEM:
        printString("There is no item called: " + userInput);
        break;
      case NO_PASSAGE:
        printString("There is no passage called: " + userInput);
        break;
      case THERE_IS_NONE:
        printString("There is no " + userInput + " here.");
        break;
      default:
        printString("That's not here!");
    }
  }

  /**
   * Standard output for successful operations
   * @param userInput
   * @param type
   */
  public void success(String userInput, successType type) {
    switch (type) {
      case PICK_UP:
        printString("You have successfully picked up " + userInput);
        break;
      default:
        printString("Yeah, you did it!");
    }
  }

  /**
   * Standard output for obstacle interactions
   * @param obstacle
   * @param type
   */
  public void obstacleOut(Obstacle obstacle, successType type) {
    switch (type) {
      case OBSTACLE_RESOLUTION:
        printString(obstacle.getResolution());
        break;
      case OBSTACLE_REACTION:
//        printString(obstacle.getReactionToFalseItem(););
        obstacle.getReactionToFalseItem();
        break;
      default:
        printString("Yeah, you did it!");
    }
  }

  /**
   * Standard output for game endings
   * @param type    
   */
  public void exitingTheGame(endingType type){
    switch (type){
      case YOU_SURE:
        printString("Are you sure you want to exit the game?[YES/NO]");
        break;
      case NO:
        printString("Glad you're still here!");
        break;
      case YES:
        printString("Sad to see you leaving :(");
        break;
      case TRY_AGAIN:
        printString("Do you want to play again?");
        break;
    }
  }

  public void youDidItASCI(){
    printString(" __     __               _ _     _   _ _     __  \n"
        + " \\ \\   / /              | (_)   | | (_) |    \\ \\ \n"
        + "  \\ \\_/ /__  _   _    __| |_  __| |  _| |_  (_) |\n"
        + "   \\   / _ \\| | | |  / _` | |/ _` | | | __|   | |\n"
        + "    | | (_) | |_| | | (_| | | (_| | | | |_   _| |\n"
        + "    |_|\\___/ \\__,_|  \\__,_|_|\\__,_| |_|\\__| (_) |\n"
        + "                                             /_/ \n");
  }

  public void youDidItNotASCI(){
    printString(" __     __               _ _     _    _ _                 _        __\n"
        + " \\ \\   / /              | (_)   | |  (_) |               | |    _ / /\n"
        + "  \\ \\_/ /__  _   _    __| |_  __| |   _| |_   _ __   ___ | |_  (_) | \n"
        + "   \\   / _ \\| | | |  / _` | |/ _` |  | | __| | '_ \\ / _ \\| __|   | | \n"
        + "    | | (_) | |_| | | (_| | | (_| |  | | |_  | | | | (_) | |_   _| | \n"
        + "    |_|\\___/ \\__,_|  \\__,_|_|\\__,_|  |_|\\__| |_| |_|\\___/ \\__| (_) | \n"
        + "                                                                  \\_\\\n");
  }

  /**
   * Output a committed message in console. Deprecated! There should be a method for what you want
   * to do.
   *
   * @param message String
   */
  @Deprecated
  public void doOutput(String message) {
    printString(message);
  }

  public void inputLine(){
    System.out.print(">");
  }

  /**
   * Print a string to the console.
   */
  private void printString(String message) {
    System.out.println(message);
    System.out.println("-------------------------------");
  }
}
