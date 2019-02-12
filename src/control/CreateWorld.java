package control;

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

    GameControl control = new GameControl(parser.getStartingPlace());

    control.runGame();

  }



}
