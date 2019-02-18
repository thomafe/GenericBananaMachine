package control;

import java.io.File;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import model.Item;
import model.ItemObstacle;
import model.Passage;
import model.Place;
import model.GameWorld;

/**
 * Parse given XML file in a specific path.
 * 
 * @author Lehmeti
 */
public class XmlParser {

  private Place startingPlace = null;
  private GameWorld world = new GameWorld();

  private boolean enableDebug = false;
  private String storyName;

  public void initParser(String file) {
    if(checkFileExists(file)){
      parseXml(file);
    }
  }

  /**
   * Parse given XML file in /levels/ directory, generate objects, connections and GameWorld.
   *
   * @param file String
   */
  public void parseXml(String file) {

    try {

      debug("\n\nIn the beginning God created the heaven and the world...\n\n");

      File fXmlFile = new File("./levels/" + file);
      DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
      Document doc = dBuilder.parse(fXmlFile);

      doc.getDocumentElement().normalize();
      debug("Chosen entity: " + doc.getDocumentElement().getNodeName());

      NodeList placeList = doc.getElementsByTagName("place");
      NodeList passageList = doc.getElementsByTagName("passage");
      NodeList storyList = doc.getElementsByTagName("story");

      // Create object arrays.
      ArrayList<Place> places = new ArrayList<>();
      ArrayList<Item> items = new ArrayList<>();
      ArrayList<Passage> passages = new ArrayList<>();

      // parse story text
      Node storyNode = storyList.item(0);
      Element storyElement = (Element) storyNode;
      debug("Intro: " + storyElement.getElementsByTagName("introduction").item(0).getTextContent());
      debug("Level: " + storyElement.getElementsByTagName("name").item(0).getTextContent());
      debug("Version: " + storyElement.getElementsByTagName("version").item(0).getTextContent());
      // add story elements to world
      world.setIntroduction(storyElement.getElementsByTagName("introduction").item(0).getTextContent());
      world.setLevelName(storyElement.getElementsByTagName("name").item(0).getTextContent());
      world.setLevelVersion(storyElement.getElementsByTagName("version").item(0).getTextContent());

      // parse all existing Places
      for (int placeCounter = 0; placeCounter < placeList.getLength(); placeCounter++) {
        Node placeNode = placeList.item(placeCounter);

        debug("\nCurrent Element: " + placeNode.getNodeName());

        if (placeNode.getNodeType() == Node.ELEMENT_NODE) {
          Element placeElement = (Element) placeNode;

          debug("- Ending: " + placeElement.getAttribute("end"));
          debug("- id: " + placeElement.getAttribute("id"));
          debug("- Name: " + placeElement.getElementsByTagName("name").item(0).getTextContent());
          debug("- Description: " + placeElement.getElementsByTagName("description").item(0).getTextContent());

          // Create Places.
          places.add(
              new Place(
                  placeElement.getElementsByTagName("name").item(0).getTextContent(),
                  placeElement.getElementsByTagName("description").item(0).getTextContent()
              )
          );

          // add EndingPlace to World, set endingText to Places' Description
          if(placeElement.getAttribute("end").equals("bad")) {
            world.addEndingPlace(places.get(placeCounter), places.get(placeCounter).getDescription());
          }

          // Add current Place to GameWorld
          world.addPlace(places.get(placeCounter));

          // parse all existing Place Items
          NodeList itemList = placeElement.getElementsByTagName("item");

          for (int itemCounter = 0; itemCounter < itemList.getLength(); itemCounter++) {
            Node itemNode = itemList.item(itemCounter);

            Element itemElement = (Element) itemNode;

            debug("- Item" + itemCounter + ":");
            debug("- - Name: " + itemElement.getElementsByTagName("name").item(0).getTextContent());
            debug("- - Description: " + itemElement.getElementsByTagName("description").item(0).getTextContent());

            // Create items.
            items.add(
                new Item(
                    itemElement.getElementsByTagName("name").item(0).getTextContent(),
                    itemElement.getElementsByTagName("description").item(0).getTextContent()
                )
            );
            // Set items in current place.
            places.get(placeCounter).addObjectToPlace(getIncludedItem(itemElement, items));

          }

          // parse all Place furniture
          NodeList furnitureList = placeElement.getElementsByTagName("furniture");

          for (int furnitureCounter = 0; furnitureCounter < furnitureList.getLength(); furnitureCounter++) {
            Node furnitureNode = furnitureList.item(furnitureCounter);

            Element furnitureElement = (Element) furnitureNode;

            debug("- Furniture" + furnitureCounter);
            debug("- - Name: " + furnitureElement.getElementsByTagName("name").item(0).getTextContent());
            debug("- - Description: " + furnitureElement.getElementsByTagName("description").item(0).getTextContent());
            debug("- - Conaining Item: " + furnitureElement.getElementsByTagName("content-item").item(0).getTextContent());
            //TODO: Furiture Obstacles aso.
          }

        }
      }

      // parse all existing passages
      for (int passageCounter = 0; passageCounter < passageList.getLength(); passageCounter++) {
        Node passageNode = passageList.item(passageCounter);

        debug("\nCurrent Element: " + passageNode.getNodeName());

        if (passageNode.getNodeType() == Node.ELEMENT_NODE) {
          Element passageElement = (Element) passageNode;

          debug("- id: " + passageElement.getAttribute("id"));
          debug("- Name: " + passageElement.getElementsByTagName("name").item(0).getTextContent());
          debug("- Description: " + passageElement.getElementsByTagName("description").item(0).getTextContent());
          debug("- Comes from: " + passageElement.getElementsByTagName("comeFrom").item(0).getTextContent());
          debug("- Connects to: " + passageElement.getElementsByTagName("connectTo").item(0).getTextContent());

          // Create Passage with connected Places.
          passages.add(new Passage(passageElement.getElementsByTagName("name").item(0).getTextContent(),
              passageElement.getElementsByTagName("description").item(0).getTextContent(),
              getFromPlace(passageElement, places),    // from this Place
              getFollowPlace(passageElement, places))    // to that Place
          );

          // parse all existing Passage Obstacles
          NodeList obstacleList = passageElement.getElementsByTagName("obstacle");

          for (int obstacleCounter = 0; obstacleCounter < obstacleList.getLength(); obstacleCounter++) {
            Node obstacleNode = obstacleList.item(obstacleCounter);

            Element obstacleElement = (Element) obstacleNode;

            debug("- Obstacle" + obstacleCounter + ":");
            debug("- - Description: " + obstacleElement.getElementsByTagName("description").item(0).getTextContent());
            debug("- - Resolution: " + obstacleElement.getElementsByTagName("resolution").item(0).getTextContent());
            debug("- - Required Item: " + obstacleElement.getElementsByTagName("requiredItem").item(0).getTextContent());

            // Create the obstacle for each passage.
            passages.get(passageCounter).setObstacle(new ItemObstacle("",
                obstacleElement.getElementsByTagName("description").item(0).getTextContent(),
                obstacleElement.getElementsByTagName("resolution").item(0).getTextContent(),
                getRequiredItem(items, obstacleElement.getElementsByTagName("requiredItem").item(0).getTextContent())
            ));

          }
        }
      }

      startingPlace = places.get(0);

      // set starting Place in GameWorld
      if(world.getStartingPlace() == null) {
        world.setStartingPlace(startingPlace);
      }

      debug("\n\n");
      debug("World has successfully been created! God saw all that he had made, and it was good.");
      debug("\n\n\n\n");

    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  /**
   * Getter for required item for obstacle.
   * Gets all items and returns the item which has the equal name as the needed one.
   *
   * @param items Item
   */
  private Item getRequiredItem(ArrayList<Item> items, String requiredItemName) {
    Item requirement = null;

    for(int i=0; i <= items.size()-1; i++) {
      if(items.get(i).getName().equals(requiredItemName)) {
        requirement = items.get(i);
      }
    }
    return requirement;
  }

  /**
   * Getter for the Starting Place which must be given to the new Character.
   *
   * @return Place
   */
  public Place getStartingPlace() {
    return startingPlace;
  }

  /**
   * Checks which Place is the Place before the current Passage and
   * returns the Place the Character comes from.
   *
   * @param passageElement Element
   * @param places ArrayList
   * @return Place
   */
  private Place getFromPlace (Element passageElement, ArrayList<Place> places) {
    Place start = null;
    for(int i = 0; i <= places.size()-1; i++) {
      if(passageElement.getElementsByTagName("comeFrom").item(0).getTextContent().equals(places.get(i).getName())){
        start = places.get(i);
      }
    }
    return start;
  }

  /**
   * Checks which Place is the Place after the current Passage and
   * returns the Place the Character can enter.
   *
   * @param passageElement Element
   * @param places ArrayList
   * @return Place
   */
  private Place getFollowPlace (Element passageElement, ArrayList<Place> places) {
    Place follow = null;
    for(int i = 0; i <= places.size()-1; i++) {
      if(passageElement.getElementsByTagName("connectTo").item(0).getTextContent().equals(places.get(i).getName())){
        follow = places.get(i);
      }
    }
    return follow;
  }

  /**
   * Checks which Item is included in the current Place and
   * returns the included item.
   *
   * @param itemElement Element
   * @param items ArrayList
   * @return Item
   */
  private Item getIncludedItem (Element itemElement, ArrayList<Item> items) {
    Item include = null;
    for(int i = 0; i <= items.size()-1; i++) {
      if(items.get(i).getName().equals(itemElement.getElementsByTagName("name").item(0).getTextContent())) {
        include = items.get(i);
      }
    }
    return include;
  }

  /**
   * If enableDebug = true, debug alerts will be printed in console.
   *
   * @param post String
   */
  private void debug(String post) {
    if(enableDebug) {
      System.out.println(post);
    }
  }

  /**
   * Setter for storyName.
   */
  public void setStoryName(String name) {
    storyName = name;
  }

  /**
   * Getter for storyName.
   *
   * @return String
   */
  private String getStoryName() {
    return storyName;
  }

  /**
   * Check if file in path exists and returns true if it does, false if not.
   * @param file String
   * @return boolean
   */
  private boolean checkFileExists(String file) {
    boolean exist = false;
    File tmpFile = new File("./levels/" + file);
    if(tmpFile.exists()) {
      //file exists
      exist = true;
    }
    return exist;
  }

}
