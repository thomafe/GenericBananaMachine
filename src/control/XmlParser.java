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

  public static void main(String args[]) {

    try {

      File fXmlFile = new File("./levels/game01.xml");
      DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
      Document doc = dBuilder.parse(fXmlFile);

      doc.getDocumentElement().normalize();
      System.out.println("Chosen entity: " + doc.getDocumentElement().getNodeName());
      NodeList placeList = doc.getElementsByTagName("place");

      // parse all existing Places
      for (int placeCounter = 0; placeCounter < placeList.getLength(); placeCounter++) {
        Node placeNode = placeList.item(placeCounter);
        System.out.println("\nCurrent Element: " + placeNode.getNodeName());

        if (placeNode.getNodeType() == Node.ELEMENT_NODE) {
          Element placeElement = (Element) placeNode;

          System.out.println("Place id: " + placeElement.getAttribute("id"));
          System.out.println("Place Name: " + placeElement.getElementsByTagName("name").item(0).getTextContent());
          System.out.println("Place Description: " + placeElement.getElementsByTagName("description").item(0).getTextContent());

          // parse all existing Place Items
          NodeList placeItemList = doc.getElementsByTagName("items");

          for(int placeItemCounter = 0; placeItemCounter < placeItemList.getLength(); placeItemCounter++) {
            Node placeItemNode = placeItemList.item(placeItemCounter);

            if (placeItemNode.getNodeType() == Node.ELEMENT_NODE) {
              Element placeItemElement = (Element) placeNode;

              System.out.println("Place Item" + placeItemCounter +": " + placeItemElement.getElementsByTagName("item").item(0).getTextContent());
            }

          }

        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
