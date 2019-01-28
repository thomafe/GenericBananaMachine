package view;

import java.util.List;

import control.Control;
import model.GameObject;
import model.Item;
import model.Obstacle;
import model.Passage;
import model.Place;

public class Output {

  private Control control;

  // private final static String[] AVAILABLE_ACTIONS = {"Look at", "Inventory",
  // "Go through"};
  private static final String[] ACTIONS = {"Look at <something>", "Look around",
      "Use <Passage Name>",
      "Take <Item Name>", "Inventory", "Actions"};

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
    doOutput("Hello fellow Player, welcome to your gobsmacking adventure!");
  }

  // TODO replace "room" in all the strings with currentPlace.getName()

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

    doOutput(options.toString());
  }

  /**
   * Lists all items in the room (on the floor).
   */
  public void listItemsInPlace() {
    List<Item> itemsInPlace = control.getCharacter().getCurrentPlace().getItemsOnTheFloor();

    if (itemsInPlace.isEmpty()) {
      doOutput("There are no items here.");
    } else {
      StringBuilder itemsOutput = new StringBuilder();

      itemsOutput.append("These items are in the room:\n");
      for (Item item : itemsInPlace) {
        itemsOutput.append(" - " + item.getName() + "\n");
      }

      doOutput(itemsOutput.toString());
    }
  }

  /**
   * Lists all items in characters inventory.
   */
  public void listInventory() {
    StringBuilder itemsInInventory = new StringBuilder();

    itemsInInventory.append("These items are in your inventory:\n");
    for (Item item : control.getCharacter().getItemsInInventory()) {
      itemsInInventory.append(" - " + item.getName() + "\n");
    }

    doOutput(itemsInInventory.toString());
  }

  /**
   * Lists all passages in the current room.
   */
  public void listPassages() {
    StringBuilder passages = new StringBuilder();

    passages.append("These passages lead out of the room:\n");
    for (Passage passage : control.getCharacter().getCurrentPlace().getPassages()) {
      passages.append(" - " + passage.getName() + "\n");
    }

    doOutput(passages.toString());

  }

  /**
   * List the options to interact with an obstacle
   */
  public void listOptionsObstacleInteraction(Obstacle obstacle) {
    StringBuilder obstacleOptions = new StringBuilder();

    obstacleOptions.append(obstacle.getDescription() + "\n");
    obstacleOptions
        .append("What item do you want to use? (Enter nothing to return to the previous place");
    listInventory();

    doOutput(obstacleOptions.toString());
  }

  /**
   * Look at the currentPlace.
   */
  public void lookAtCurrentPlace() {
    Place currentPlace = control.getCharacter().getCurrentPlace();

    StringBuilder placeDescription = new StringBuilder();

    placeDescription.append("You are in " + currentPlace.getName() + "\n");
    placeDescription.append(currentPlace.getDescription());

    doOutput(placeDescription.toString());
  }

  // TODO do we give this strings or does control resolve that?
  // public void lookAtGameObject()

  /**
   * Shows GameObject's name / description in console or gives User an Exception if no such item
   * exists.
   *
   * @param objectName String
   */
  public void lookAtGameObject(String objectName) {
    GameObject object = control.findGameObject(objectName);

    if (object == null) {
      doOutput("There is no " + objectName + "here.");
    } else {
      StringBuilder gameObjectDescription = new StringBuilder();

      gameObjectDescription.append("You look at " + object.getName() + "\n");
      gameObjectDescription.append(object.getDescription());

      doOutput(gameObjectDescription.toString());
    }
  }

  /**
   * Output a committed message in console.
   *
   * @param message String
   */
  public void doOutput(String message) {
    System.out.println(message);
  }
}
