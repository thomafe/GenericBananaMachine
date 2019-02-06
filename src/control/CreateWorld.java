package control;

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
 * Take information from XmlParser and use it to create several objects and relations between.
 */
public class CreateWorld {
  // TODO: create objects similar to current Control main method. GET content from XmlParser.

  /**
   * Main method.
   *
   * @param args String
   */
  public static void main(String args[]) {
    XmlParser parser = new XmlParser();
    parser.parseXml();

    int numPlaces = parser.getNumberOfPlaces();
    Place[] places = new Place[numPlaces];

  }





}
