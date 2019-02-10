package control;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.util.*;

import model.Character;
import model.Item;
import model.Obstacle;
import model.Place;
import view.Input;
import view.Output;
import model.Passage;
import model.GameObject;
import control.XmlParser;

/**
 * Parse given XML file in a specific path
 */
public class XmlParser {

  public Place startingPlace = null;

  // TODO: (1) create getters of the variables which can be called in CreateWorld instead of pushing them to it.
  private int numberOfPlaces, numberOfPassages, numberOfItems, numberOfObstacles;

  public void parseXml() {

    CreateWorld creator = new CreateWorld();

    try {

      File fXmlFile = new File("./levels/game01.xml");
      DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
      Document doc = dBuilder.parse(fXmlFile);

      doc.getDocumentElement().normalize();
      System.out.println("Chosen entity: " + doc.getDocumentElement().getNodeName());

      NodeList placeList = doc.getElementsByTagName("place");
      NodeList passageList = doc.getElementsByTagName("passage");

      // TODO: reference to (1)
      // Create object arrays.
      ArrayList<Place> places = new ArrayList<Place>();
      ArrayList<Item> items = new ArrayList<Item>();
      ArrayList<Passage> passages = new ArrayList<Passage>();
      ArrayList<Obstacle> obstacles = new ArrayList<Obstacle>();

      // parse all existing Places
      for (int placeCounter = 0; placeCounter < placeList.getLength(); placeCounter++) {
        Node placeNode = placeList.item(placeCounter);

        // set number of places.
        setNumberOfPlaces(placeList.getLength());

        System.out.println("\nCurrent Element: " + placeNode.getNodeName());

        if (placeNode.getNodeType() == Node.ELEMENT_NODE) {
          Element placeElement = (Element) placeNode;

          System.out.println("- id: " + placeElement.getAttribute("id"));
          System.out.println("- Name: " + placeElement.getElementsByTagName("name").item(0).getTextContent());
          System.out.println("- Description: " + placeElement.getElementsByTagName("description").item(0).getTextContent());

          // TODO: reference to (1)
          // Create Places.
          places.add(
              new Place(
                  placeElement.getElementsByTagName("name").item(0).getTextContent(),
                  placeElement.getElementsByTagName("description").item(0).getTextContent()
              )
          );

          // parse all existing Place Items
          NodeList itemList = placeElement.getElementsByTagName("item");

          for (int itemCounter = 0; itemCounter < itemList.getLength(); itemCounter++) {
            Node itemNode = itemList.item(itemCounter);

            // set number of items.
            setNumberOfItems(itemList.getLength());

            Element itemElement = (Element) itemNode;

            System.out.println("- Item" + itemCounter + ":");
            System.out.println("- - Name: " + itemElement.getElementsByTagName("name").item(0).getTextContent());
            System.out.println("- - Description: " + itemElement.getElementsByTagName("description").item(0).getTextContent());

            // TODO: reference to (1)
            // Create items.

            items.add(
                new Item(
                    itemElement.getElementsByTagName("name").item(0).getTextContent(),
                    itemElement.getElementsByTagName("description").item(0).getTextContent()
                )
            );
            // Set items in current place.
            places.get(placeCounter).addItemOnTheFloor(getIncludedItem(itemElement, items));

            /*
            places.get(placeCounter).addItemOnTheFloor(new Item(
                itemElement.getElementsByTagName("name").item(0).getTextContent(),
                itemElement.getElementsByTagName("description").item(0).getTextContent()
            ));
             */
          }
        }
      }

      // parse all existing passages
      for (int passageCounter = 0; passageCounter < passageList.getLength(); passageCounter++) {
        Node passageNode = passageList.item(passageCounter);

        // set number of passages.
        setNumberOfPassages(passageList.getLength());

        System.out.println("\nCurrent Element: " + passageNode.getNodeName());

        if (passageNode.getNodeType() == Node.ELEMENT_NODE) {
          Element passageElement = (Element) passageNode;

          System.out.println("- id: " + passageElement.getAttribute("id"));
          System.out.println("- Name: " + passageElement.getElementsByTagName("name").item(0).getTextContent());
          System.out.println("- Description: " + passageElement.getElementsByTagName("description").item(0).getTextContent());
          System.out.println("- Comes from: " + passageElement.getElementsByTagName("comeFrom").item(0).getTextContent());
          System.out.println("- Connects to: " + passageElement.getElementsByTagName("connectTo").item(0).getTextContent());

          // TODO: reference to (1)
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

            // set number of obstacles.
            setNumberOfObstacles(obstacleList.getLength());

            Element obstacleElement = (Element) obstacleNode;

            System.out.println("- Obstacle" + obstacleCounter + ":");
            System.out.println("- - Description: " + obstacleElement.getElementsByTagName("description").item(0).getTextContent());
            System.out.println("- - Resolution: " + obstacleElement.getElementsByTagName("resolution").item(0).getTextContent());
            System.out.println("- - Required Item: " + obstacleElement.getElementsByTagName("requiredItem").item(0).getTextContent());

            // Create the obstacle for each passage.
            passages.get(passageCounter).setObstacle(new Obstacle("",
                obstacleElement.getElementsByTagName("description").item(0).getTextContent(),
                obstacleElement.getElementsByTagName("resolution").item(0).getTextContent(),
                getRequiredItem(items, obstacleElement.getElementsByTagName("requiredItem").item(0).getTextContent())
            ));

          }
        }
      }

      startingPlace = places.get(0);

      System.out.println("\n\n");
      System.out.println("World has successfully been created! God saw all that he had made, and it was good.");
      System.out.println("\n\n\n\n");

    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  /**
   * Setter for Number of Places.
   *
   * @param num int
   */
  private void setNumberOfPlaces(int num) {
    numberOfPlaces = num;
  }

  /**
   * Getter for Number of Places.
   *
   * @return int
   */
  public int getNumberOfPlaces() {
    return numberOfPlaces;
  }

  /**
   * Setter for Number of Items.
   *
   * @param num int
   */
  public void setNumberOfItems(int num) {
    numberOfItems = num;
  }

  /**
   * Getter for Number of Items.
   *
   * @return int
   */
  public int getNumberOfItems() {
    return numberOfItems;
  }

  /**
   * Setter for Number of Passages.
   *
   * @param num int
   */
  public void setNumberOfPassages(int num) {
    numberOfPassages = num;
  }

  /**
   * Getter for Number of Passages.
   *
   * @return int
   */
  public int getNumberOfPassages() {
    return numberOfPassages;
  }

  /**
   * Setter for Number of Obstacles.
   *
   * @param num int
   */
  public void setNumberOfObstacles(int num) {
    numberOfObstacles = num;
  }

  /**
   * Getter for Number of Obstacles.
   *
   * @return int
   */
  public int getNumberOfObstacles() {
    return numberOfObstacles;
  }

  /**
   * Getter for required item for obstacle.
   * Gets all items and returns the item which has the equal name as the needed one.
   *
   * @param items Item
   */
  public Item getRequiredItem(ArrayList<Item> items, String requiredItemName) {
    Item requirement = null;

    for(int i=0; i <= items.size()-1; i++) {
      if(items.get(i).getName().equals(requiredItemName)) {
        requirement = items.get(i);
      }
    }
    return requirement;
  }

  public Place getStartingPlace() {
    return startingPlace;
  }

  public Place getFromPlace (Element passageElement, ArrayList<Place> places) {
    Place start = null;
    for(int i = 0; i <= places.size()-1; i++) {
      if(passageElement.getElementsByTagName("comeFrom").item(0).getTextContent().equals(places.get(i).getName())){
        start = places.get(i);
      }
    }
    return start;
  }

  public Place getFollowPlace (Element passageElement, ArrayList<Place> places) {
    Place follow = null;
    for(int i = 0; i <= places.size()-1; i++) {
      if(passageElement.getElementsByTagName("connectTo").item(0).getTextContent().equals(places.get(i).getName())){
        follow = places.get(i);
      }
    }
    return follow;
  }

  public Item getIncludedItem (Element itemElement, ArrayList<Item> items) {
    Item include = null;
    for(int i = 0; i <= items.size()-1; i++) {
      if(items.get(i).getName().equals(itemElement.getElementsByTagName("name").item(0).getTextContent())) {
        include = items.get(i);
      }
    }
    return include;
  }

}
