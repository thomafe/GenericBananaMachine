package view;

import java.util.List;
import control.Control;
import model.GameObject;
import model.Item;
import model.Obstacle;
import model.Passage;
import model.Place;

public class Output {
  
  // TODO define Strings for common use! "You can't do that" etc...

  private Control control;

  private static final String[] ACTIONS = {"Look at <something>", "Look around",
      "Goto <Passage Name>", "Take <Item Name>", "Inventory", "Actions"};

  /**
   * Constructor.
   *
   * @param contoller Control
   */
  public Output(Control contoller) {
    control = contoller;
  }

  /**
   * Introduction for the player at the start of the game.
   */
  public void greeting() {
    printString("Hello fellow player!\n"
        + " In this glorious adventure game you can prove your bravery and smartness\n by passing the many obstacles that will come in your way\n"
        + "Look for things along the way that might help you and you may stand a chance");

  }

  /**
   * Ending sequence when the game is done, either because of succeed or because of death
   */
  public void goodEnding() {
    printString(
        "Congraltulations, you've made it\n You reached the end of the game \n passing many obstacles you fought your way through the world \nconsider yourself a hero now");
  }

  public void badEnding() {
    printString("You failed\n This is the end of the game \n This place brought death to you");
    // TODO implement play again
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
  public void listObjectsInPlace() {
    List<GameObject> objectsInPlace = control.getCharacter().getCurrentPlace().getObjectsInPlace();

    if (itemsInPlace.isEmpty()) {
      printString("There are no items here.");
    } else {
      StringBuilder thingsOutput = new StringBuilder();

      itemsOutput.append(
          "These items are in " + control.getCharacter().getCurrentPlace().getName() + ": \n");
      for (Item item : itemsInPlace) {
        itemsOutput.append(" - " + item.getName() + "\n");
      }

      printString(itemsOutput.toString());
    }
  }

  /**
   * Lists all items in characters inventory.
   */
  public void listInventory() {
    if (!control.getCharacter().getItemsInInventory().isEmpty()) {
      StringBuilder itemsInInventory = new StringBuilder();

      itemsInInventory.append("These items are in your inventory:\n");
      for (Item item : control.getCharacter().getItemsInInventory()) {
        itemsInInventory.append(" - " + item.getName() + "\n");
      }

      printString(itemsInInventory.toString());
    } else {
      printString("You have nothing in your inventory!");
    }
  }

  /**
   * Lists all passages in the current room.
   */
  public void listPassages() {
    StringBuilder passages = new StringBuilder();

    passages.append(
        "These passages lead out of " + control.getCharacter().getCurrentPlace().getName() + ":\n");
    for (Passage passage : control.getCharacter().getCurrentPlace().getPassages()) {
      passages.append(" - " + passage.getName() + "\n");
    }

    printString(passages.toString());

  }

  /**
   * List the options to interact with an obstacle
   */
  public void listOptionsObstacleInteraction(Obstacle obstacle) {
    StringBuilder obstacleOptions = new StringBuilder();

    obstacleOptions.append(obstacle.getDescription() + "\n");
    obstacleOptions.append("Do you want to \"use\" an item or do you want to \"leave\"?");
    listInventory();

    printString(obstacleOptions.toString());
  }

  /**
   * Look at the currentPlace.
   */
  public void lookAtCurrentPlace() {
    Place currentPlace = control.getCharacter().getCurrentPlace();

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
  public void lookAtGameObject(String objectName) {
    GameObject object = control.findGameObject(objectName);

    if (object == null) {
      printString("There is no " + objectName + " here.");
    } else {
      StringBuilder gameObjectDescription = new StringBuilder();

      gameObjectDescription.append("You look at " + object.getName() + "\n");
      gameObjectDescription.append(object.getDescription());

      printString(gameObjectDescription.toString());
    }
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

  /**
   * Print a string to the console.
   * 
   * @param message
   */
  private void printString(String message) {
    System.out.println(message);
  }
}
