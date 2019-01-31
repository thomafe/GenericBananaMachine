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

  private Character character = null;
  private Input in = null;
  private Output out = null;

  /**
   * Default constructor. Initializes the game.
   */
  public Control() {
    initGame();
  }

  /**
   * Initializes the game world and all other required objects.
   * 
   */
  private void initGame() {
    // Game World
    Place entrance = new Place("Entrance", "This is your starting area.");
    Place secondRoom =
        new Place("Hall of Doom", "This is the final Boss Room...not. It just sounds cool.");
    Place thirdRoom =
        new Place("Lighthouse", "You can't see anything in here because the light is blinding.");

    Passage pas1 = new Passage("Door of Doom", "This Door seems to be very heavy and doomed",
        entrance, secondRoom);
    new Passage("snake pit", "You are greeted by the lovely sound of zzzzzzzzz", secondRoom,
        thirdRoom);

    Item item1 = new Item("Lightsaber", "This is a powerful jedi melee weapon.");
    Item item2 = new Item("Banana", "This is a powerful fruit which makes you feel like a monkey.");

    Obstacle obstacle = new Obstacle("Blastdoor", "A thick blast door that blocks the way",
        "You melt through the door with your lightsaber!", item1);

    pas1.setObstacle(obstacle);

    entrance.addItemOnTheFloor(item1);
    secondRoom.addItemOnTheFloor(item2);
    
    // Other objects
    character = new Character(entrance);

    out = new Output(this);
    in = new Input(out, this);

  }

  /**
   * Contains the main game loop
   */
  private void runGame() {
    gameIntroduction();

    // Game Loop
    do {
      in.readInput();

    } while (true);

  }

  /**
   * Tries to move the character through a passage. If there is an obstacle in the way the character
   * first interacts with that obstacle. If there is no obstacle or the obstacle gets resolved the
   * character moves to the next room.
   * 
   * @param destinationPassage
   * @return whether the character moved r not
   */
  public boolean tryToMoveThroughPassage(String passageName) {
    boolean passageClear = false;

    Passage destinationPassage = findPassage(passageName);
    
    if(destinationPassage == null) {
      out.doOutput("There is no passage called " + passageName);
      return passageClear;
    }

    if (this.checkForObstacle(destinationPassage)) {
      passageClear = interactWithObstacle(destinationPassage.getObstacle());
    }

    if (passageClear) {
      character.move(destinationPassage);
      return true;
    } else {
      return false;
    }

  }

  /**
   * Allows the character to interact with an obstacle. Loops as long as the character tries to use
   * items on the obstacle. If the obstacle gets resolved the item is consumed and the character
   * moves through the passage. If the character stops trying items the interaction ends and the
   * character stays in that room.
   * 
   * @return whether the character resolved the obstacle or not
   */
  public boolean interactWithObstacle(Obstacle currentObstacle) {
    boolean obstacleResolved = false;
    boolean continueTrying = true;

    Item choosenItem = null;

    if (currentObstacle.isResolved()) {
      obstacleResolved = true;
    } else {

      while (continueTrying) {
        out.listOptionsObstacleInteraction(currentObstacle);

        choosenItem = findItemInInventory(in.readInSingleLine());

        if (choosenItem == null) {
          out.doOutput("You go back to " + character.getCurrentPlace().getName());
          break;
        }

        if (currentObstacle.tryToUseItem(choosenItem)) {
          out.doOutput(currentObstacle.getResolution());
          character.removeItem(choosenItem);
          
          obstacleResolved = true;
          continueTrying = false;
        } else {
          out.doOutput("That doesn't work");
        }
      }
    }

    return obstacleResolved;
  }

  /**
   * Tells the character to pick up an item
   *
   * @param itemName String
   * @return boolean
   */
  public boolean pickUpItem(String itemName) {
    boolean success = false;

    Item itemToPickUp = findItemOnTheFloor(itemName);

    if (itemToPickUp != null) {
      character.takeItem((Item) itemToPickUp);

      success = true;
    }

    return success;
  }

  /**
   * Looks for a object with the given name in the players inventory or in the current place.
   *
   * @param objectName String
   * @return String description
   */
  public GameObject findGameObject(String objectName) {
    GameObject foundObject = null;

    foundObject = findPassage(objectName);

    if (foundObject == null) {
      foundObject = findItemOnTheFloor(objectName);
    }

    if (foundObject == null) {
      foundObject = findItemInInventory(objectName);
    }

    return foundObject;
  }

  /**
   * Returns found Passage after searching it in the current Place where the Character currently is
   * inside.
   *
   * @param passageName String
   * @return Passage
   */
  private Passage findPassage(String passageName) {
    Passage foundPassage = null;

    for (Passage passage : character.getCurrentPlace().getPassages()) {
      if (passage.getName().equalsIgnoreCase(passageName)) {
        foundPassage = passage;
        break;
      }
    }
    return foundPassage;
  }

  /**
   * Looks for an item on the floor of the current room.
   * 
   * @param itemName
   * @return the found item or null if there was no such item
   */
  private Item findItemOnTheFloor(String itemName) {
    Item foundItem = null;

    for (Item item : character.getCurrentPlace().getItemsOnTheFloor()) {
      if (item.getName().equalsIgnoreCase(itemName)) {
        foundItem = item;
        break;
      }
    }

    return foundItem;
  }

  /**
   * Looks for an item in the characters inventory.
   * 
   * @param itemName
   * @return the found item or null if there was no such item
   */
  private Item findItemInInventory(String itemName) {
    Item foundItem = null;

    for (Item item : character.getItemsInInventory()) {
      if (item.getName().equalsIgnoreCase(itemName) && !item.isConsumed()) {
        foundItem = item;
        break;
      }
    }

    return foundItem;
  }
  
  /**
   * Check if destinated Passage has Obstacle in it. If yes, return true, else false.
   *
   * @param destinationPassage Passage
   * @return boolean
   */
  private boolean checkForObstacle(Passage destinationPassage) {
    return destinationPassage.hasObstacle();
  }

  /**
   * Is run once at game start to introduce the player to the game.
   *
   */
  private void gameIntroduction() {
    out.greeting();
    out.listOptions();
    out.lookAtCurrentPlace();
  }

  /**
   * Getter for Character.
   *
   * @return Character
   */
  public Character getCharacter() {
    return character;
  }

  // Main Method

  public static void main(String[] args) {

    Control control = new Control();

    control.runGame();
  }


}
