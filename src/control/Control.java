package control;

import java.util.Collections;
import model.Character;
import model.Furniture;
import model.Item;
import model.Obstacle;
import model.Place;
import view.Input;
import view.Output;
import model.Passage;
import model.GameObject;
import view.Output.errorType;
import view.Output.successType;

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
   * Creates a test game if the flag is set or a regular game if not.
   */
  public Control(boolean doTest) {
    if (doTest) {
      testWorld();
    } else {
      initGame();
    }
  }

  private void initCharacter(Place startingPlace) {
    character = new Character(startingPlace);

    out = new Output(this);
    in = new Input(out, this);
  }

  /**
   * Initializes the game world and all other required objects.
   */
  private void initGame() {

    // Scenario: "Shipwrecked"
    Place room0 = new Place("Beach",
        "You look around and see a bunch of equipment stuff laying on the ground.");
    Place room1 = new Place("Coast", "You enter a barren Coast which borders on a deep Jungle.");
    Place room2 = new Place("Jungle",
        "You got lost in the downs of the deep Jungle. You can't orientate yourself.");
    Place room3 = new Place("Cave",
        "You enter a dark cave. You can hear a drumming noise from the deeper inside.");
    Place room4 = new Place("Outback",
        "After you followed the signed path, you enter the jungle's outback, which is less deep as the upper lands.");
    Place room5 = new Place("Beach at the other side",
        "You found a way out of the jungle and enter the beach at the other side of the island. In the distance you can see the silhouette of a ship.");
    Place room6 = new Place("Ship of Coastguard",
        "You are saved by the coastguard. Just in the moment you feel saved and rescued, you realize that it is not the coastguard but pirates who seem to use you as their new slave.");
    Place room7 = new Place("Bad Ending",
        "The wood is old and rotten. It instantly breaks after you tried to walk on it and you fall to death.");
    Place room8 = new Place("Another Bad Ending",
        "After you fired your Signal Rocket, you are hit by arrows. An unfriendly civilization of Indians found another human meal.");

    Passage pass1 = new Passage("High Cliff",
        "This is a very sharp and high cliff. You need something to climb it.", room0, room1);
    Passage pass2 = new Passage("Underwood",
        "The vegetation is blocking your way through. You need something to cut a pass through.",
        room1, room2);
    Passage pass3 = new Passage("Cave Entrance",
        "You stand in front of an impressive cave entrance. You seem to be attracted by the darkness which comes from inside. You need something to light up your way.",
        room2, room3);
    Passage pass4 = new Passage("Dirty Signpost",
        "You look at a very dirty and old signpost. You can't read it because it's so dirty. You need something to clean it.",
        room2, room4);
    Passage pass5 = new Passage("Hole in the Ground",
        "You stand in front of a small but unbreachable hole. You need something to get over it.",
        room3, room7);
    Passage pass6 = new Passage("Tunnel Entrance",
        "You light inside but can't see anything. It seems that something inside is attracting you.",
        room3, room3);
    Passage pass7 = new Passage("Path to voices",
        "You can hear human voices in the distance. You think about calling someones attention.",
        room4, room8);
    Passage pass8 = new Passage("Path away",
        "You can hear human voices in the distance. You think about not to call attention and follow the other path.",
        room4, room5);
    Passage pass9 = new Passage("Shore",
        "You enter the shore, the broken waves plash on your legs. You need something to call attention.",
        room5, room6);

    Item item1 =
        new Item("Machete", "This is a sharp meele weapon which can also be used to cut plants.");
    Item item2 = new Item("Flashlight",
        "The battery is on low charge but a small light is better than no light.");
    Item item3 = new Item("Rope",
        "This is a long rope which has an iron hook at the end which can be used to climb.");
    Item item4 = new Item("Cloth",
        "This is a part of a cloth which can be used as a bandage or to clean yourself or something else.");
    Item item5 = new Item("Plank", "This is a wooden plank which lied in the water at the beach.");
    Item item6 = new Item("Stone", "This is a sharp stone.");
    Item item7 = new Item("Compass",
        "The needle always sign to north. Now you can orientate yourself in the jungle.");
    Item item8 = new Item("Signal Rocket",
        "There is one missile left which can be used to call someones attention.");

    Obstacle obs1 = new Obstacle("Way upstairs", "You need to find a way to climb the cliff.",
        "You used the rope to climb the cliff.", item3);
    Obstacle obs2 = new Obstacle("Blocking vegetation",
        "You need to cut a way through the vegetation which blocks you.",
        "You used the machete to cut through.", item1);
    Obstacle obs3 = new Obstacle("Darkness in the cave",
        "You can't see anything. You need to light up your way.",
        "You used the flashlight to light up your way.", item2);
    Obstacle obs4 = new Obstacle("Dirt", "You need to remove the dirt from the signpost.",
        "You used the cloth to clean the signpost. You can hear human voices in the distance.",
        item4);
    Obstacle obs5 = new Obstacle("Hole", "You need to pass the hole.",
        "You used the wooden plank to build a custom bridge.", item5);
    Obstacle obs6 = new Obstacle("Darkness in the tunnel",
        "Your flashlight is too low for this darkness. You need something to check if there is anything.",
        "You threw the stone in the tunnel but you are instantly hit by the same stone. Is this a portal back to the entrance?",
        item6);
    Obstacle obs7 = new Obstacle("Attention", "You need to call attention of the humans you hear.",
        "You fired your missile of signal rockets to call attention.", item8);
    Obstacle obs8 = new Obstacle("Path away from voices",
        "You need something to orientate not to get lost on the path.",
        "You used the compass to find the way to south which guides you away from the human voices.",
        item7);
    Obstacle obs9 = new Obstacle("Rescue", "You need something to call attention for rescue.",
        "You used the missile of the sign rocket to call attention of the ship. It answered and took you on board.",
        item8);

    pass1.setObstacle(obs1); // High cliff -> way upstairs
    pass2.setObstacle(obs2); // Underwood -> blocking vegetation
    pass3.setObstacle(obs3); // Cave entrance -> darkness in cave
    pass4.setObstacle(obs4); // dirty signpost -> dirt
    pass5.setObstacle(obs5); // hole in the ground -> hole
    pass6.setObstacle(obs6); // tunnel entrance -> darkness in tunnel
    pass7.setObstacle(obs7); // Path to voices -> Attention
    pass8.setObstacle(obs8); // Path away -> away
    pass9.setObstacle(obs9); // Shore -> Rescue

    room0.addItemOnTheFloor(item1); // beach -> machete
    room0.addItemOnTheFloor(item2); // beach -> flashlight
    room0.addItemOnTheFloor(item3); // beach -> rope
    room1.addItemOnTheFloor(item4); // coast -> cloth
    room2.addItemOnTheFloor(item5); // Jungle -> Wooden Plank
    room2.addItemOnTheFloor(item6); // Jungle -> Stone
    room3.addItemOnTheFloor(item7); // Cave -> Compass
    room4.addItemOnTheFloor(item8); // Outback -> signal rockets

    // Other objects
    initCharacter(room0);

  }

  /**
   * Create a test world. Character already has items in his inventory.
   */
  private void testWorld() {
    Place startingPlace = new Place("Entrance", "Starting Place");
    Place room1 = new Place("Room 1", "Test Room 1");
    Place room2 = new Place("Room 2", "Test Room 2");
    Place room3 = new Place("Room 3", "Test Room 3");
    Place room4 = new Place("Room 4", "Test Room 4");

    Item item1 = new Item("Required Item", "Required Item");
    Item item2 = new Item("Additional Item", "Additional Item");
    Item itemOnFloor = new Item("Shoe", "A shoe");
    Item itemInChest =
        new Item("Banana", "This is a powerful fruit which makes you feel like a monkey.");

    startingPlace.addObjectToPlace(itemOnFloor);

    Obstacle singleItemObstacle =
        new Obstacle("One Item Obstacle", "This obstalce takes one item", "It worked!", item1);
    Obstacle doulbeItemObstacle = new Obstacle("Two Item Obstacle",
        "This obstalce takes one item, addtitional Item first!", "It worked!", item1, item2);
    Obstacle riddleObstacle =
        new Obstacle("Riddle Obstacle", "The answere is \"Shoe\"", "It worked!", "Shoe");

    Furniture chest = new Furniture("Chest", "A dirty old chest",
        Collections.singletonList(itemInChest), singleItemObstacle);

    startingPlace.addObjectToPlace(chest);

    new Passage("Free Passage", "Has no obstacles", startingPlace, room1);
    (new Passage("Simple Passage", "Has simple Obstacle", startingPlace, room2))
        .setObstacle(singleItemObstacle);
    (new Passage("Double Passage", "Has double Obstacle", startingPlace, room3))
        .setObstacle(doulbeItemObstacle);
    (new Passage("Riddle Passage", "Has riddle Obstacle", startingPlace, room4))
        .setObstacle(riddleObstacle);

    initCharacter(startingPlace);

    character.takeItem(item1);
    character.takeItem(item2);
  }

  @Deprecated
  public void oldTestWorld() {
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

    Item lightsaber = new Item("Lightsaber", "This is a powerful jedi melee weapon.");
    Item item2 = new Item("Banana", "This is a powerful fruit which makes you feel like a monkey.");
    Item overcharger = new Item("Overcharger", "This thing just makes all gadgets go Uhweeeeee");

    Obstacle obstacle = new Obstacle("Blastdoor", "A thick blast door that blocks the way",
        "You melt through the door with your lightsaber!", lightsaber, overcharger);

    pas1.setObstacle(obstacle);

    entrance.addItemOnTheFloor(lightsaber);
    entrance.addItemOnTheFloor(overcharger);
    secondRoom.addItemOnTheFloor(item2);

    initCharacter(entrance);
  }

  /**
   * Contains the main game loop Every time check if game might end!
   */
  private void runGame() {
    gameIntroduction();

    // Game Loop
    do {

      checkForBadEnding();
      checkForGoodEnding();
      in.readInput();

    } while (true);

  }

  /**
   * Checks if the passage passageName exists at this place
   *
   * @return boolean
   */
  public boolean checkPassage(String passageName) {
    if (findPassage(passageName) != null) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * Tries to move the character through a passage. If there is an obstacle in the way the character
   * first interacts with that obstacle. If there is no obstacle or the obstacle gets resolved the
   * character moves to the next room.
   *
   * @param passageName String
   * @return whether the character moved or not
   */

  public boolean tryToMoveThroughPassage(String passageName) {
    boolean characterMoved = false;

    Passage destinationPassage = findPassage(passageName);

    Obstacle obstacleInPassage = destinationPassage.getObstacle();

    if (obstacleInPassage == null || obstacleInPassage.isResolved()
        || interactWithObstacle(obstacleInPassage)) {
      character.move(destinationPassage);
      characterMoved = true;
    }

    return characterMoved;
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

    while (true) {
      out.listOptionsObstacleInteraction(currentObstacle);
      answerString = in.readItemForObstacle();
      chosenItem = findItemInInventory(answerString);

      if (answerString == null) {
        out.noSuccess(errorType.DONT_HAVE_ITEM);
      } else if (answerString.equals("leave")) {
        out.noSuccess(errorType.GO_BACK);
        break;
      } else if (currentObstacle.tryToUseItem(chosenItem)) {
        // TODO tell the player about the outcome of trying to use the item
        // out.obstacleOut(currentObstacle, successType.OBSTACLE_REACTION);
        // TODO only consumed items get removed??
        character.removeItem(chosenItem);
        if (currentObstacle.isResolved()) {
          out.obstacleOut(currentObstacle, successType.OBSTACLE_RESOLUTION);
          return true;
        }
      } else {
        out.noSuccess(errorType.DOES_NOT_WORK);
      }
    }
    return false;
  }

  /**
   * Checks if the GameObject exists
   * 
   * @param objectName
   * @return boolean
   */
  public boolean checkLookAtGameObject(String objectName) {
    if (findGameObject(objectName) != null) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * Checks if the item exists in this place
   *
   * @return boolean
   */
  public boolean checkPickUpItem(String itemName) {
    if (findItemOnTheFloor((itemName)) != null) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * Tells the character to pick up an item
   *
   * @param itemName String
   */
  public void pickUpItem(String itemName) {
    Item itemToPickUp = findItemOnTheFloor(itemName);
    character.takeItem(itemToPickUp);
    character.getCurrentPlace().removeItemFromPlace(itemToPickUp);
  }

  /**
   * Looks for a object with the given name in the players inventory or in the current place.
   *
   * @param objectName String
   * @return String description
   */
  public GameObject findGameObject(String objectName) {
    GameObject foundObject = null;

    // TODO thomaf rework all of these
    foundObject = findPassage(objectName);

    if (foundObject == null) {
      foundObject = findItemOnTheFloor(objectName);
    }

    if (foundObject == null) {
      foundObject = findItemInInventory(objectName);
    }

    if (foundObject == null) {
      foundObject = findFurniture(objectName);
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
   * @return the found item or null if there was no such item
   */
  private Item findItemOnTheFloor(String itemName) {
    Item foundItem = null;

    for (GameObject objectInPlace : character.getCurrentPlace().getObjectsInPlace()) {
      if (objectInPlace instanceof Item && objectInPlace.getName().equalsIgnoreCase(itemName)) {
        foundItem = (Item) objectInPlace;
        break;
      }
    }

    return foundItem;
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
   * Looks for furniture in the current room.
   * 
   * @param furnitureName
   * @return
   */
  private Furniture findFurniture(String furnitureName) {
    Furniture foundFurniture = null;

    for (GameObject gameObject : character.getCurrentPlace().getObjectsInPlace()) {
      if (gameObject instanceof Furniture && gameObject.getName().equalsIgnoreCase(furnitureName)) {
        foundFurniture = (Furniture) gameObject;
        break;
      }
    }

    return foundFurniture;
  }

  /**
   * Is run once at game start to introduce the player to the game.
   */
  private void gameIntroduction() {
    out.greeting();
    out.lookAtCurrentPlace();
    out.listOptions();
  }

  /**
   * Checks if a good ending was entered
   */
  private void checkForGoodEnding() {

    if (character.getCurrentPlace().getName().equals("Ship of Coastguard")) {
      out.goodEnding();

      System.exit(0);
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

      System.exit(0);
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
   * Main Method.
   * 
   * @param args
   */
  public static void main(String[] args) {
    boolean doTest = true;

    Control control = new Control(doTest);

    control.runGame();
  }


}
