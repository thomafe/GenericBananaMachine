package control;

import java.util.Collections;
import model.DoubleItemObstacle;
import model.Furniture;
import model.GameWorld;
import model.Item;
import model.ItemObstacle;
import model.Obstacle;
import model.Passage;
import model.Place;
import model.RiddleObstacle;

/**
 * Created playable games.
 * 
 * @author thomafe
 */
public class GameFactory {

  /**
   * There are no objects of this class.
   */
  private GameFactory() {

  }

  /**
   * Create a new test game.
   * 
   * @return GameControl
   */
  public static GameControl getTestGame() {
    return new GameControl(testWorld());
  }

  /**
   * Create a new game from an XML File.
   * 
   * @param filePath String
   * @return GameControl
   */
  public static GameControl getGameFromFile(String filePath) {
    GameControl newGame = null;

    XmlParser parser = new XmlParser();
    parser.initParser(filePath);

    if (parser.getStartingPlace() != null) {
      newGame = new GameControl(parser.getGameWorld());
    }

    return newGame;
  }


  /**
   * Create a test world.
   */
  private static GameWorld testWorld() {
    GameWorld testWorld = new GameWorld();

    testWorld.setIntroduction("This is the gobsmacking test Intro!");

    Place startingPlace = new Place("Entrance", "Starting Place");
    Place room1 = new Place("Room 1", "Test Room 1");
    Place room2 = new Place("Room 2", "Test Room 2");
    Place room3 = new Place("Room 3", "Test Room 3");
    Place room4 = new Place("Room 4", "Test Room 4");
    Place room5 = new Place("Room 5", "Test Room 5");
    Place endingPlace = new Place("Ending", "The end room");

    testWorld.addPlace(startingPlace);
    testWorld.addPlace(room1);
    testWorld.addPlace(room2);
    testWorld.addPlace(room3);
    testWorld.addPlace(room4);
    testWorld.addPlace(room5);
    testWorld.addPlace(endingPlace);
    testWorld.setStartingPlace(startingPlace);
    testWorld.addGoodEnding(endingPlace, "This is the end.");

    Item item1 = new Item("Required Item", "Required Item");
    Item item2 = new Item("Additional Item", "Additional Item");
    Item itemOnFloor = new Item("Rock", "A rock");
    Item itemInChest =
        new Item("Banana", "This is a powerful fruit which makes you feel like a monkey.");

    startingPlace.addObjectToPlace(item1);
    startingPlace.addObjectToPlace(item2);
    startingPlace.addObjectToPlace(itemOnFloor);

    Obstacle singleItemObstacle =
        new ItemObstacle("One Item Obstacle", "This obstalce takes one item", "It worked!", item1);
    Obstacle doulbeItemObstacle = new DoubleItemObstacle("Two Item Obstacle",
        "This obstalce takes one item, addtitional Item first!", "It worked!!!", item1, item2);
    doulbeItemObstacle.setUsedCorrectItem("Now use required item.");
    Obstacle riddleObstacle =
        new RiddleObstacle("Riddle Obstacle", "The answere is \"Shoe\"", "It worked!!", "Shoe");
    Obstacle dangerousObstacle = new ItemObstacle("Dangerous Obstacle", "This will kill you.",
        "You should never read this", new Item("Void Item", "This is nowhere"));
    dangerousObstacle.setDamagepoints(9001);
    dangerousObstacle.setUsedFalseItem("You dun goofed!");
    dangerousObstacle.setWalkingAway("I WILL get you.");


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
    (new Passage("Dangerous Passage", "Will kill you", startingPlace, room5))
        .setObstacle(dangerousObstacle);
    new Passage("Ending Passage", "Leads to the end", startingPlace, endingPlace);

    return testWorld;
  }

}
