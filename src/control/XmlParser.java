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

      for (int temp = 0; temp < placeList.getLength(); temp++) {
        Node placeNode = placeList.item(temp);
        System.out.println("\nCurrent Element: " + placeNode.getNodeName());

        if (placeNode.getNodeType() == Node.ELEMENT_NODE) {
          Element eElement = (Element) placeNode;

          System.out.println("Place id: " + eElement.getAttribute("id"));
          System.out.println("AAA: " + eElement.getElementsByTagName("name").item(0).getTextContent());
          System.out.println("BBB: " + eElement.getElementsByTagName("description").item(0).getTextContent());
          System.out.println("CCC: " + eElement.getElementsByTagName("items").item(0).getTextContent());
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
