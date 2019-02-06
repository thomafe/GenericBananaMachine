package control;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;

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

      setNumberOfPlaces(placeList.getLength());

      // TODO: reference to (1)
      // Create object arrays.
      Place[] places = new Place[getNumberOfPlaces()];
      Item[] items = new Item[getNumberOfItems()];
      Passage[] passages = new Passage[getNumberOfPassages()];
      Obstacle[] obstacles = new Obstacle[getNumberOfObstacles()];

      // parse all existing Places
      for (int placeCounter = 0; placeCounter < placeList.getLength(); placeCounter++) {
        Node placeNode = placeList.item(placeCounter);
        System.out.println("\nCurrent Element: " + placeNode.getNodeName());

        if (placeNode.getNodeType() == Node.ELEMENT_NODE) {
          Element placeElement = (Element) placeNode;

          System.out.println("- id: " + placeElement.getAttribute("id"));
          System.out.println("- Name: " + placeElement.getElementsByTagName("name").item(0).getTextContent());
          System.out.println("- Description: " + placeElement.getElementsByTagName("description").item(0).getTextContent());

          // TODO: reference to (1)
          // Create Places.
          places[placeCounter] = new Place(placeElement.getElementsByTagName("name").item(0).getTextContent(),
              placeElement.getElementsByTagName("description").item(0).getTextContent());

          // parse all existing Place Items
          NodeList itemList = placeElement.getElementsByTagName("item");

          for (int itemCounter = 0; itemCounter < itemList.getLength(); itemCounter++) {
            Node itemNode = itemList.item(itemCounter);
            Element itemElement = (Element) itemNode;

            System.out.println("- Item" + itemCounter + ":");
            System.out.println("- - Name: " + itemElement.getElementsByTagName("name").item(0).getTextContent());
            System.out.println("- - Description: " + itemElement.getElementsByTagName("description").item(0).getTextContent());

            // TODO: reference to (1)
            // Create items.
            items[itemCounter] = new Item(itemElement.getElementsByTagName("name").item(0).getTextContent(),
                itemElement.getElementsByTagName("description").item(0).getTextContent());

          }
        }
      }

      // parse all existing passages
      for (int passageCounter = 0; passageCounter < passageList.getLength(); passageCounter++) {
        Node passageNode = passageList.item(passageCounter);
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
          passages[passageCounter] = new Passage(passageElement.getElementsByTagName("name").item(0).getTextContent(),
              passageElement.getElementsByTagName("description").item(0).getTextContent(),
              places[0],    // from this Place
              places[1]);   // to that Place

          // parse all existing Passage Obstacles
          NodeList obstacleList = passageElement.getElementsByTagName("obstacle");

          for (int obstacleCounter = 0; obstacleCounter < obstacleList.getLength(); obstacleCounter++) {
            Node obstacleNode = obstacleList.item(obstacleCounter);
            Element obstacleElement = (Element) obstacleNode;

            System.out.println("- Obstacle" + obstacleCounter + ":");
            System.out.println("- - Description: " + obstacleElement.getElementsByTagName("description").item(0).getTextContent());
            System.out.println("- - Resolution: " + obstacleElement.getElementsByTagName("resolution").item(0).getTextContent());
            System.out.println("- - Required Item: " + obstacleElement.getElementsByTagName("requiredItem").item(0).getTextContent());

            // TODO: reference to (1)
            // Create Obstacles.
            obstacles[obstacleCounter] = new Obstacle(
                "Obstacle" + obstacleCounter,       // obstacle name
                obstacleElement.getElementsByTagName("description").item(0).getTextContent(),
                obstacleElement.getElementsByTagName("resolution").item(0).getTextContent(),
                items[0]);      // required item
          }
        }
      }

    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  /**
   * Setter for Number of Places.
   *
   * @param num int
   */
  public void setNumberOfPlaces(int num) {
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
}
