package view;

import java.util.List;
import model.Character;
import model.GameObject;
import model.Obstacle;
import model.Place;

/**
 * Reacts to input from user with output.
 *
 * @author Niklas
 */
public class Output {

  public enum errorType {
    CANT_DO_THAT, DOES_NOT_WORK, DONT_HAVE_ITEM, GO_BACK, DONT_MIX, DONT_MIX_MAD, YOU_DEAD, DECIDE, NO_PASSAGE, EMPTY, EMPTY_INVENTORY
  }

  public enum errorTypeInput {
    NO_ITEM, NO_PASSAGE, THERE_IS_NONE, NOT_AN_ITEM
  }

  public enum successType {
    PICK_UP, MOVE_THROUGH, AT_PLACE, OBSTACLE_RESOLUTION, OBSTACLE_REACT_FALSE, OBSTACLE_REACT_RIGHT, OBSTACLE_WALK_AWAY
  }

  public enum endingType {
    YOU_SURE, NO, YES, TRY_AGAIN
  }

  public enum options {
    NOT_YET, WHICH_LEVEL, PRESS_ENTER
  }

  private static final String[] ACTIONS =
      {"Look at <something>", "Look around", "Goto/Use <Passage/Furniture Name>",
          "Go back to the last passage", "Take <Item Name>", "Inventory", "Actions", "Exit"};

  /**
   * Introduction for the player at the start of the game.
   */
  public void greeting(String introduction) {
    if (introduction != null && !introduction.isEmpty()) {
      printString(introduction);
    } else {
      printString("Hello fellow player!\n"
          + "In this glorious adventure game you can prove your bravery and smartness\n"
          + "by passing the many obstacles that will come in your way\n"
          + "Look for things along the way that might help you and you may stand a chance");
    }
  }

  /**
   * Ending sequence when the game is done, either because of succeed or because of death.
   */
  public void goodEnding(String ending) {
    if (ending != null && !ending.isEmpty()) {
      printString(ending);
    } else {
      printString("Congraltulations, you've made it\n" + "You reached the end of the game \n"
          + "passing many obstacles you fought your way through the world \n"
          + "consider yourself a hero now\n");
    }
  }

  public void badEnding(String ending) {
    if (ending != null && !ending.isEmpty()) {
      printString(ending);
    } else {
      printString(
          "You failed\n This is the end of the game \n" + "This place brought death to you");
    }
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

      thingsOutput.append("These things are in " + currentPlace.getName() + ":");

      printString(thingsOutput.toString());

      listOutput(currentPlace.getObjectsString());
    }
  }

  /**
   * Lists all passages in the current room.
   */
  public void listPassages(Place currentPlace) {
    StringBuilder passageList = new StringBuilder();

    passageList.append("These passages lead out of " + currentPlace.getName() + ":\n");
    for (int i = 0; i < currentPlace.getPassages().size(); i++) {
      passageList.append(currentPlace.getPassages().get(i));
      if (i != currentPlace.getPassages().size() - 1) {
        passageList.append(" | ");
      }
    }


    printString(passageList.toString());

  }

  /**
   * List the options to interact with an obstacle.
   */
  public void listOptionsObstacleInteraction(Obstacle obstacle) {
    StringBuilder obstacleOptions = new StringBuilder();

    obstacleOptions.append(obstacle.getDescription() + "\n");
    obstacleOptions.append("Do you want to use an item or do you want to \"leave\"?");

    printString(obstacleOptions.toString());
  }

  /**
   * Look at the currentPlace. Use <code>lookAtGameObject()</code> instead.
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
   * @param object GameObject
   */
  public void lookAtGameObject(GameObject object) {
    printString(object.getDescription());
  }

  /**
   * Look at the character itself.
   * 
   * @param character
   */
  public void lookAtSelf(Character character) {
    printString("You look at yourself. You have " + character.getHitpoints() + " hitpoints left.");
  }

  /**
   * Standard output for unsuccessful operations.
   */
  public void noSuccess(errorType type) {
    switch (type) {
      case CANT_DO_THAT:
        printString("You can't do that!");
        break;
      case DOES_NOT_WORK:
        printString("That doesn't work");
        break;
      case DONT_HAVE_ITEM:
        printString("You don't have this item!");
        break;
      case GO_BACK:
        printString("You decided to go back");
        break;
      case DONT_MIX:
        printString("Don't mix the bloody commands!");
        break;
      case DONT_MIX_MAD:
        printString("Are you stupid? I said: \"DONT MIX THE BLOODY COMMANDS!\"");
        break;
      case YOU_DEAD:
        printString("You are dead :(");
        break;
      case DECIDE:
        printString("You can't do both!");
        break;
      case NO_PASSAGE:
        printString("You first have to go through a passage!");
        break;
      case EMPTY:
        printString("There is nothing here.");
        break;
      case EMPTY_INVENTORY:
        printString("You have nothing in your inventory!");
        break;
      default:
        printString("Quite impossible");
    }
  }

  /**
   * Standard output for unsuccessful operations.
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
      case NOT_AN_ITEM:
        printString(userInput + " is not an item!");
        break;
      default:
        printString("That's not here!");
    }
  }

  /**
   * Standard output for successful operations.
   * 
   * @param interactedObject
   * @param type
   */
  public void successfulInteraction(String interactedObject, successType type) {
    switch (type) {
      case PICK_UP:
        printString("You have successfully picked up " + interactedObject);
        break;
      case MOVE_THROUGH:
        printString("You go through " + interactedObject);
        break;
      case AT_PLACE:
        printString("You are in " + interactedObject);
        break;
      default:
        printString("Yeah, you did it!");
    }
  }

  /**
   * Standard output for obstacle interactions.
   * 
   * @param obstacle
   * @param type
   */
  public void obstacleOut(Obstacle obstacle, successType type) {
    switch (type) {
      case OBSTACLE_RESOLUTION:
        printString(obstacle.getResolution());
        break;
      case OBSTACLE_REACT_RIGHT:
        if (!isEmpty(obstacle.getReactionToCorrectItem())) {
          printString(obstacle.getReactionToCorrectItem());
        } else {
          printString("That was the correct first item!");
        }
        break;
      case OBSTACLE_REACT_FALSE:
        if (!isEmpty(obstacle.getReactionToFalseItem())) {
          printString(obstacle.getReactionToFalseItem());
        } else {
          noSuccess(errorType.DOES_NOT_WORK);
        }
        if(obstacle.getDamagepoints() > 0) {
          printString("You take " + obstacle.getDamagepoints() + " points of damage.");
        }
        break;
      case OBSTACLE_WALK_AWAY:
        printString(obstacle.getWalkAwayReaction());
        break;
      default:
        printString("Yeah, you did it!");
    }
  }

  /**
   * Standard output for game endings.
   * 
   * @param type
   */
  public void exitingTheGame(endingType type) {
    switch (type) {
      case YOU_SURE:
        printString("Are you sure you want to exit the game?");
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

  public void menuOptions(options opt) {
    switch (opt) {
      case NOT_YET:
        printString("There is nothing you can change yet!");
        break;
      case WHICH_LEVEL:
        printString("Which level do you want to play?");
        break;
      case PRESS_ENTER:
        printString("Press Enter to continue");
        break;
    }
  }

  public void youDidItASCI() {
    printString(" __     __               _ _     _   _ _     __  \n"
        + " \\ \\   / /              | (_)   | | (_) |    \\ \\ \n"
        + "  \\ \\_/ /__  _   _    __| |_  __| |  _| |_  (_) |\n"
        + "   \\   / _ \\| | | |  / _` | |/ _` | | | __|   | |\n"
        + "    | | (_) | |_| | | (_| | | (_| | | | |_   _| |\n"
        + "    |_|\\___/ \\__,_|  \\__,_|_|\\__,_| |_|\\__| (_) |\n"
        + "                                             /_/ \n");
  }

  public void youDidItNotASCI() {
    printString(" __     __               _ _     _    _ _                 _        __\n"
        + " \\ \\   / /              | (_)   | |  (_) |               | |    _ / /\n"
        + "  \\ \\_/ /__  _   _    __| |_  __| |   _| |_   _ __   ___ | |_  (_) | \n"
        + "   \\   / _ \\| | | |  / _` | |/ _` |  | | __| | '_ \\ / _ \\| __|   | | \n"
        + "    | | (_) | |_| | | (_| | | (_| |  | | |_  | | | | (_) | |_   _| | \n"
        + "    |_|\\___/ \\__,_|  \\__,_|_|\\__,_|  |_|\\__| |_| |_|\\___/ \\__| (_) | \n"
        + "                                                                  \\_\\\n");
  }

  public void beforeInput() {
    System.out.print("-----------------------------------------\n> ");
  }

  /**
   * Print a string to the console.
   */
  private void printString(String message) {
    if (!isEmpty(message)) {
      System.out.println(message);
    }
  }
  
  /**
   * Static printout for errors.
   * 
   * @param error
   */
  public static void printError(String error) {
    System.err.println(error);
  }

  /**
   * Standard output method for lists.
   * 
   * @param gameObjects a string list of GameObjects.
   */
  public void listOutput(List<String> gameObjects) {
    StringBuilder gameObjectList = new StringBuilder();

    gameObjectList.append(gameObjects.get(0));

    if (gameObjects.size() > 1) {
      for (int i = 1; i < gameObjects.size(); i++) {
        gameObjectList.append(" | ");
        gameObjectList.append(gameObjects.get(i));
      }
    }

    printString(gameObjectList.toString());
  }

  public void credits() {
    printString("Project Manager: Felix Jan Thoma\n");
    printString("Creative Writer: Simone Maag\n");
    printString("Level Designer: Tim Hendrik Lehmeier\n");
    printString("Developer: Niklas Grethler");
  }

  public void introduction() {
    printString("This is a text adventure game.\nYou are taking the role of a character.\n"
        + "The character can move from place to place. To do so, you have to use passages, for example doors etc.\n"
        + "The passages can be blocked by Obstacles. To resolve those, you have to use one or more item or answer a question correctly.\n"
        + "You can find items in a place if you look around. It's always a good idea to take them with you.\n"
        + "There can also be furniture in a place, which you might find helpful, if you go to it and look at it more closely.\n"
        + "If you don't know what to do, it's always a good idea to enter 'actions' to get your options,to look around or to look at something more closely. You can also look at \"yourself\"\n"
        + "Make wise decisions, or your character will die.\nGood luck!!\n");
  }

  private boolean isEmpty(String string) {
    return string == null || string.isEmpty();
  }

}
