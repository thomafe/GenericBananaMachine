package control;

import java.io.File;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import model.DoubleItemObstacle;
import model.Furniture;
import model.GameWorld;
import model.Item;
import model.ItemObstacle;
import model.Passage;
import model.Place;
import model.RiddleObstacle;

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

  /**
   * Initialize Parser, check if file exists and runs parsing if true.
   *
   * @param file String
   */
  public void initParser(String file) {
    if(checkFileExists(file)){
      parseXml(file);
    }
  }

  /**
   * Parses XML file and returns the Story name.
   *
   * @param path String
   * @return String
   */
  public String getStoryName(String path) {
    try {
      File fXmlFile = new File(path);
      DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
      Document doc = dBuilder.parse(fXmlFile);

      doc.getDocumentElement().normalize();

      NodeList storyList = doc.getElementsByTagName("story");

      // parse story text
      Node storyNode = storyList.item(0);
      Element storyElement = (Element) storyNode;
      debug("Intro: " + storyElement.getElementsByTagName("introduction").item(0).getTextContent());
      debug("Level: " + storyElement.getElementsByTagName("name").item(0).getTextContent());
      debug("Version: " + storyElement.getElementsByTagName("version").item(0).getTextContent());
      // add story elements to world
      storyName = storyElement.getElementsByTagName("name").item(0).getTextContent();

    } catch (Exception e) {
      e.printStackTrace();
    }
    return storyName;
  }

  /**
   * Parse given XML file in /levels/ directory, generate objects, connections and GameWorld.
   * NOTE: Duplicate Code is needed for debugging.
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
      NodeList furnitureList = doc.getElementsByTagName("furniture");
      NodeList storyList = doc.getElementsByTagName("story");

      // Create object arrays
      ArrayList<Place> places = new ArrayList<>();
      ArrayList<Item> items = new ArrayList<>();
      ArrayList<Passage> passages = new ArrayList<>();
      ArrayList<Furniture> furnitures = new ArrayList<Furniture>();

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
        }
      }

      // parse all furniture
      for (int furnitureCounter = 0; furnitureCounter < furnitureList.getLength(); furnitureCounter++) {
        Node furnitureNode = furnitureList.item(furnitureCounter);

        Element furnitureElement = (Element) furnitureNode;

        debug("\nCurrent Element: " + furnitureNode.getNodeName());

        debug("- Furniture" + furnitureCounter);
        debug("- - In Place: " + furnitureElement.getElementsByTagName("in-place").item(0).getTextContent());
        debug("- - Name: " + furnitureElement.getElementsByTagName("name").item(0).getTextContent());
        debug("- - Description: " + furnitureElement.getElementsByTagName("description").item(0).getTextContent());

        // Create furniture objects
        furnitures.add(
            new Furniture(
                furnitureElement.getElementsByTagName("name").item(0).getTextContent(),   // name
                furnitureElement.getElementsByTagName("description").item(0).getTextContent(),    // description
                furnitureElement.getElementsByTagName("in-place").item(0).getTextContent()
            )
        );

        NodeList furnitureItemList = furnitureElement.getElementsByTagName("content-item");

        // parse all Furniture Items
        for (int furnitureItemCounter = 0; furnitureItemCounter < furnitureItemList.getLength(); furnitureItemCounter++) {
          Node furnitureItemNode = furnitureItemList.item(furnitureItemCounter);

          Element furnitureItemElement = (Element) furnitureItemNode;

          debug("- - Content Items:");
          debug("- - - Name: " + furnitureItemElement.getElementsByTagName("name").item(0).getTextContent());
          debug("- - - Description: " + furnitureItemElement.getElementsByTagName("description").item(0).getTextContent());

          addItems(furnitureItemElement, furnitures, items, furnitureCounter);

        }

        NodeList furnitureObstacleList = furnitureElement.getElementsByTagName("obstacle");

        // parse all Furniture Obstacles
        for (int furnitureObstacleCounter = 0; furnitureObstacleCounter < furnitureObstacleList.getLength(); furnitureObstacleCounter++) {
          Node furnitureObstacleNode = furnitureObstacleList.item(furnitureObstacleCounter);

          Element furnitureObstacleElement = (Element) furnitureObstacleNode;

          debug("- - Obstacle:");
          debug("- - - Description: " + furnitureObstacleElement.getElementsByTagName("description").item(0).getTextContent());
          debug("- - - Resolution: " + furnitureObstacleElement.getElementsByTagName("resolution").item(0).getTextContent());
          //debug("- - - Requirement: " + furnitureObstacleElement.getElementsByTagName("requiredItem").item(0).getTextContent());

          // create furniture obstacle
          if(furnitureObstacleElement.getAttribute("type").equals("double")){
            // double Item obstacle
            furnitures.get(furnitureCounter).setObstalce(
              new DoubleItemObstacle(
                "",
                furnitureObstacleElement.getElementsByTagName("description").item(0).getTextContent(),
                furnitureObstacleElement.getElementsByTagName("resolution").item(0).getTextContent(),
                getRequiredItem(items, furnitureObstacleElement.getElementsByTagName("requiredItem").item(0).getTextContent()),
                getRequiredItem(items, furnitureObstacleElement.getElementsByTagName("additionalItem").item(0).getTextContent())
              )
            );

          } else if(furnitureObstacleElement.getAttribute("type").equals("riddle")) {
            // riddle Obstacle
            furnitures.get(furnitureCounter).setObstalce(
              new RiddleObstacle(
                "",
                furnitureObstacleElement.getElementsByTagName("description").item(0).getTextContent(),
                furnitureObstacleElement.getElementsByTagName("resolution").item(0).getTextContent(),
                furnitureObstacleElement.getElementsByTagName("requiredAnswer").item(0).getTextContent()
              )
            );

          } else {
            // normal Obstacle
            furnitures.get(furnitureCounter).setObstalce(
              new ItemObstacle(
                "",
                furnitureObstacleElement.getElementsByTagName("description").item(0).getTextContent(),
                furnitureObstacleElement.getElementsByTagName("resolution").item(0).getTextContent(),
                getRequiredItem(items,
                  furnitureObstacleElement.getElementsByTagName("requiredItem").item(0).getTextContent())
              )
            );
          }
          // add damage points to obstacle
          if(!furnitureObstacleElement.getAttribute("damage").equals("")) {
            passages.get(furnitureCounter).getObstacle().setDamagepoints(Integer.parseInt(furnitureObstacleElement.getAttribute("damage")));
          }

        }

      }

      // Add current furniture to its containing Place
      setFurnitureInPlace(furnitures, places);

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

            debug("- Obstacle" + passageCounter + ":");
            debug("- - Description: " + obstacleElement.getElementsByTagName("description").item(0).getTextContent());
            debug("- - Resolution: " + obstacleElement.getElementsByTagName("resolution").item(0).getTextContent());
            //debug("- - Required Item: " + obstacleElement.getElementsByTagName("requiredItem").item(0).getTextContent());

            // Create the obstacle for each passage.
            if(obstacleElement.getAttribute("type").equals("double")){
              // double Item obstacle
              passages.get(passageCounter).setObstacle(new DoubleItemObstacle("",
                  obstacleElement.getElementsByTagName("description").item(0).getTextContent(),
                  obstacleElement.getElementsByTagName("resolution").item(0).getTextContent(),
                  getRequiredItem(items, obstacleElement.getElementsByTagName("requiredItem").item(0).getTextContent()),
                  getRequiredItem(items, obstacleElement.getElementsByTagName("additionalItem").item(0).getTextContent())
              ));

            } else if(obstacleElement.getAttribute("type").equals("riddle")) {
              // riddle Obstacle
              passages.get(passageCounter).setObstacle(new RiddleObstacle("",
                  obstacleElement.getElementsByTagName("description").item(0).getTextContent(),
                  obstacleElement.getElementsByTagName("resolution").item(0).getTextContent(),
                  obstacleElement.getElementsByTagName("requiredAnswer").item(0).getTextContent()
              ));

            } else {
              // normal Obstacle
              passages.get(passageCounter).setObstacle(new ItemObstacle("",
                  obstacleElement.getElementsByTagName("description").item(0).getTextContent(),
                  obstacleElement.getElementsByTagName("resolution").item(0).getTextContent(),
                  getRequiredItem(items, obstacleElement.getElementsByTagName("requiredItem").item(0).getTextContent())
              ));
            }

            // add damage points to obstacle
            if(!obstacleElement.getAttribute("damage").equals("")) {
              passages.get(passageCounter).getObstacle().setDamagepoints(Integer.parseInt(obstacleElement.getAttribute("damage")));
            }


          }
        }
      }

      startingPlace = places.get(0);

      // Add Places to GameWorld
      addPlacesToWorld(places, world);

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

  /**
   * Check which Furniture belongs to which Place and add specific Furniture to chosen Place.
   *
   * @param furnitures furniture
   * @param places place
   */
  private void setFurnitureInPlace(ArrayList<Furniture> furnitures, ArrayList<Place> places) {
    for(int i = 0; i < furnitures.size(); i++) {
      for(int j = 0; j < places.size(); j++) {
        if(places.get(j).getName().equals(furnitures.get(i).getLinkedPlace())) {
          // is linked Place
          places.get(j).addObjectToPlace(furnitures.get(i));
        }
      }
    }
  }

  /**
   * Add all parsed Places to World Object.
   *
   * @param places ArrayList
   * @param world GameWorld
   */
  private void addPlacesToWorld(ArrayList<Place> places, GameWorld world) {
    for(int i = 0; i < places.size(); i++) {
      world.addPlace(places.get(i));
    }
  }

  /**
   * Add parsed items to furniture and itemlist.
   *
   * @param furnitureItemElement Element
   * @param furnitures ArrayList
   * @param items ArrayList
   * @param counter int
   */
  private void addItems(Element furnitureItemElement, ArrayList<Furniture> furnitures, ArrayList<Item> items, int counter) {
    Item tmpItem = new Item(
        furnitureItemElement.getElementsByTagName("name").item(0).getTextContent(),
        furnitureItemElement.getElementsByTagName("description").item(0).getTextContent()
    );

    // add current Item to furniture
    furnitures.get(counter).addItem(tmpItem);

    // add items to items list
    items.add(tmpItem);
  }
  
  public GameWorld getGameWorld() {
    return world;
  }

}
