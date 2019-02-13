package control;


import model.Character;
import model.Furniture;
import model.GameWorld;
import model.Item;
import model.Obstacle;
import model.Passage;
import model.Place;
import model.superclasses.GameObject;
import view.Input;
import view.Output;
import view.Output.endingType;
import view.Output.errorType;
import view.Output.successType;

/**
 * Controls all interactions and logic of the game. Calls the input to take user input. Then
 * executes the command and loops back to the input. Longer interaction routines can be happen from
 * within the normal game loop but follow the same semantic.
 *
 * @author Lehmeti, thomafe
 */
public class GameControl {

  private Character character = null;
  private Input in = null;
  private Output out = null;

  private GameWorld gameWorld = null;

  private boolean gameIsRunning = false;

  public enum decision_type {
    CANT_DECIDE, YES, NO, NO_MATCH
  }

  /**
   * Create a new controller with local Input and output.
   * 
   * @param startingPlace
   */
  public GameControl(Place startingPlace) {
    character = new Character(startingPlace);

    out = new Output();
    in = new Input(out, this);
  }

  /**
   * Create new controller and use provided input and output. Currently not usable as Input needs a
   * reference to control.
   * 
   * @param out
   * @param in
   * @param startingPlace
   */
  public GameControl(Output out, Input in, Place startingPlace) {
    character = new Character(startingPlace);

    this.out = out;
    this.in = in;
  }

  /**
   * Contains the main game loop Every time check if game might end!
   */
  public void runGame() {
    gameIntroduction();

    gameIsRunning = true;

    // Game Loop
    while (gameIsRunning) {

      checkForBadEnding();
      checkForGoodEnding();
      out.lookAtCurrentPlace(getCurrentPlace());
      in.readInput();

    }

  }

  /**
   * Asks the player if he wants to leave the game.
   * Takes an yes/no answer from input.
   */
  public void endGame() {
    out.exitingTheGame(endingType.YOU_SURE);
    if (in.yesNo()) {
      out.exitingTheGame(endingType.YES);
      gameIsRunning = false;
    } else {
        out.exitingTheGame(endingType.NO);
    }
    // TODO take an "Ending" as parameter? Maybe just a string?
  }

  /**
   * Tries to move the character through a passage. If there is an obstacle in the way the character
   * first interacts with that obstacle. If there is no obstacle or the obstacle gets resolved the
   * character moves to the next room.
   *
   * @param passageName String
   * @return whether the character moved or not
   */

  public boolean tryToMoveThroughPassage(Passage destinationPassage) {
    boolean characterMoved = false;

    Obstacle obstacleInPassage = destinationPassage.getObstacle();

    if (obstacleInPassage == null || obstacleInPassage.isResolved()
        || interactWithObstacle(obstacleInPassage)) {
      character.move(destinationPassage);
      characterMoved = true;
    }

    return characterMoved;
  }

  /**
   * Tries to receive items from a furniture. If there is an obstacle on the furniture the character
   * interacts with it. If not or it gets resolved all items from within the furniture are put into
   * the room.
   * 
   * @param furniture
   */
  public void interactWithFurniture(Furniture furniture) {
    Obstacle obstacleOnFurniture = furniture.getObstacle();

    if (obstacleOnFurniture == null || obstacleOnFurniture.isResolved()
        || interactWithObstacle(obstacleOnFurniture)) {
      furniture.receiveItemsInSide(true).forEach(character.getCurrentPlace()::addObjectToPlace);
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
    String answerString = null;
    Item chosenItem = null;
    boolean obstacleResolved = false;

    while (!obstacleResolved && gameIsRunning) {
      out.listOptionsObstacleInteraction(currentObstacle);
      out.listInventory(character.getItemsInInventory());
      answerString = in.readItemForObstacle();

      if (answerString.equalsIgnoreCase("leave")) {
        break;
      }

      chosenItem = findItemInInventory(answerString);

      if (chosenItem != null) {
        if (currentObstacle.tryToUseItem(chosenItem) && chosenItem.isConsumed()) {
          character.removeItem(chosenItem);
        }
      } else {
        currentObstacle.tryToAnswerRiddle(answerString);
        // TODO when we can tell riddle and item obstacles apart, rework this
      }

      if (currentObstacle.isResolved()) {
        out.obstacleOut(currentObstacle, successType.OBSTACLE_RESOLUTION);
        obstacleResolved = true;
      } else {
        distributeDamage(currentObstacle.getDamagepoints());
      }
    }

    return obstacleResolved;
  }

  /**
   * Tells the character to pick up an item
   *
   * @param itemName String
   */
  public void pickUpItem(Item itemToPickUp) {
    character.takeItem(itemToPickUp);
    character.getCurrentPlace().removeItemFromPlace(itemToPickUp);
  }

  /**
   * Deals damage to the character, then checks for death.
   * 
   * @param damage
   */
  public void distributeDamage(int damage) {
    character.takeDamage(damage);

    checkIfCharacterDead();
  }

  /**
   * Looks for a object with the given name in the players inventory or in the current place.
   *
   * @param objectName String
   * @return String description
   */
  public GameObject findGameObject(String objectName) {
    GameObject foundObject = findThingInPlace(objectName);

    if (foundObject == null) {
      foundObject = findPassageInPlace(objectName);
    }

    if (foundObject == null) {
      foundObject = findItemInInventory(objectName);
    }

    return foundObject;
  }

  /**
   * Look for a thing in the current place
   * 
   * @param objectName
   * @return
   */
  private GameObject findThingInPlace(String objectName) {
    GameObject foundObject = null;

    for (GameObject gameObject : character.getCurrentPlace().getObjectsInPlace()) {
      if (gameObject.getName().equalsIgnoreCase(objectName)) {
        foundObject = gameObject;
        break;
      }
    }

    return foundObject;
  }

  /**
   * Look for a thing in the current place
   * 
   * @param objectName
   * @return
   */
  private Passage findPassageInPlace(String passageName) {
    Passage foundPassage = null;

    // TODO thomaf add passages to the things in a room and rework?
    for (Passage gameObject : character.getCurrentPlace().getPassages()) {
      if (gameObject.getName().equalsIgnoreCase(passageName)) {
        foundPassage = gameObject;
        break;
      }
    }

    return foundPassage;
  }

  /**
   * Looks for an item in the characters inventory.
   *
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
   * Is run once at game start to introduce the player to the game.
   */
  private void gameIntroduction() {
    out.greeting();
    // TODO use the introduction from the level
    out.lookAtCurrentPlace(getCurrentPlace());
    out.listOptions();
  }

  /**
   * Checks if a good ending was entered
   */
  private void checkForGoodEnding() {

    if (character.getCurrentPlace().getName().equals("Ship of Coastguard")) {
      out.goodEnding();

      gameIsRunning = false;
    }
  }

  /**
   * Checks if a bad ending was entered.
   * 
   */
  private void checkForBadEnding() {
    if (character.getCurrentPlace().getName().equals("Bad Ending")
        || character.getCurrentPlace().getName().equals("Another Bad Ending")) {
      out.badEnding();

      // out.doOutput("Your Character unfortunately died. Wanna play again? Please enter YES or
      // NO");
      // Replay question
      /*
       * if (in.readInSingleLine().equals("YES")) { Control control = new Control();
       * control.runGame(); } else { out.doOutput("Thanks for playing! See you later."); }
       */

      gameIsRunning = false;
    }
  }

  /**
   * Checks if Character is dead
   *
   */
  private void checkIfCharacterDead() {
    if (character.isDead()) {
      out.noSuccess(errorType.YOU_DEAD);
      gameIsRunning = false;
    }
  }

  /**
   * Getter for Character.
   *
   * @return Character
   */
  public Character getCharacter() {
    return character;
  }

  /**
   * Getter for the place the character is currently in.
   * 
   * @return
   */
  public Place getCurrentPlace() {
    return character.getCurrentPlace();
  }

}
