package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A data class that contains a GameWorld
 *
 * @author thomafe
 */
public class GameWorld {

  // TODO thomaf add comments

  private String indroduction = null;
  private Map<Place, String> endings = null;

  private Place startingPlace = null;
  private List<Place> places = null;

  /**
   * Create a new empty GameWorld with no introduction or starting place.
   * 
   */
  public GameWorld() {
    endings = new HashMap<>();
    places = new ArrayList<>();
  }

  /**
   * Create a new GameWorld with an introduction and a starting place.
   * 
   * @param introdution
   * @param startingPlace
   */
  public GameWorld(String introdution, Place startingPlace) {
    this();

    this.indroduction = introdution;
    this.startingPlace = startingPlace;
  }

  /**
   * Set the introduction to the game.
   * 
   * @param introduction
   */
  public void setIntroduction(String introduction) {
    this.indroduction = introduction;
  }

  /**
   * Get the introduction to the game.
   * 
   * @return
   */
  public String getIndroduction() {
    return indroduction;
  }

  /**
   * Set the starting place.
   * 
   * @param startingPlace
   */
  public void setStartingPlace(Place startingPlace) {
    this.startingPlace = startingPlace;
  }

  /**
   * Get the starting place.
   * 
   * @return
   */
  public Place getStartingPlace() {
    return startingPlace;
  }

  /**
   * Add a place to the GameWorld.
   * 
   * @param newPlace
   */
  public void addPlace(Place newPlace) {
    places.add(newPlace);
  }

  /**
   * Add a passage to a place.
   * 
   * @param passge
   * @param place
   */
  @Deprecated
  public void addPassageToPlace(Passage passge, Place place) {
    place.addPassage(passge);
  }

  /**
   * Add an ending place.
   * 
   * @param endingPlace
   * @param endingText
   */
  public void addEndingPlace(Place endingPlace, String endingText) {
    endings.put(endingPlace, endingText);
  }

  /**
   * Find out whether a place is an ending place.
   * 
   * @param place
   * @return
   */
  public boolean isEndingPlace(Place place) {
    return endings.containsKey(place);
  }

  /**
   * Get the ending for a place.
   * 
   * @param endingPlace the Edning or null if the place is not an ending place.
   * @return
   */
  public String getEndingForPlace(Place endingPlace) {
    String endingText = null;

    if (isEndingPlace(endingPlace)) {
      endingText = endings.get(endingPlace);
    }

    return endingText;
  }

}
