package control;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;

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

      // parse all existing Places
      for (int placeCounter = 0; placeCounter < placeList.getLength(); placeCounter++) {
        Node placeNode = placeList.item(placeCounter);
        System.out.println("\nCurrent Element: " + placeNode.getNodeName());

        if (placeNode.getNodeType() == Node.ELEMENT_NODE) {
          Element placeElement = (Element) placeNode;

          System.out.println("- id: " + placeElement.getAttribute("id"));
          System.out.println("- Name: " + placeElement.getElementsByTagName("name").item(0).getTextContent());
          System.out.println("- Description: " + placeElement.getElementsByTagName("description").item(0).getTextContent());

          // parse all existing Place Items
          NodeList itemList = placeElement.getElementsByTagName("item");

          for (int itemCounter = 0; itemCounter < itemList.getLength(); itemCounter++) {
            Node itemNode = itemList.item(itemCounter);
            Element itemElement = (Element) itemNode;

            System.out.println("- Item" + itemCounter + ":");
            System.out.println("- - Name: " + itemElement.getElementsByTagName("name").item(0).getTextContent());
            System.out.println("- - Description: " + itemElement.getElementsByTagName("description").item(0).getTextContent());

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

          // parse all existing Passage Obstacles
          NodeList obstacleList = passageElement.getElementsByTagName("obstacle");

          for (int obstacleCounter = 0; obstacleCounter < obstacleList.getLength(); obstacleCounter++) {
            Node obstacleNode = obstacleList.item(obstacleCounter);
            Element obstacleElement = (Element) obstacleNode;

            System.out.println("- Obstacle" + obstacleCounter + ":");
            System.out.println("- - Description: " + obstacleElement.getElementsByTagName("description").item(0).getTextContent());
            System.out.println("- - Resolution: " + obstacleElement.getElementsByTagName("resolution").item(0).getTextContent());
            System.out.println("- - Required Item: " + obstacleElement.getElementsByTagName("requiredItem").item(0).getTextContent());

          }
        }
      }

    } catch (Exception e) {
      e.printStackTrace();
    }

  }
  // numberOfPlaces, numberOfPassages, numberOfItems, numberOfObstacles;
  public void setNumberOfPlaces(int num) {
    numberOfPlaces = num;
  }

  public int getNumberOfPlaces() {
    return numberOfPlaces;
  }
}
