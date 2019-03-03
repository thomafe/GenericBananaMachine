package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A data class that contains the GameWorld.
 *
 * @author thomafe
 */
public class GameWorld {

  private String introduction = null;
  private String levelName = null;
  private String levelVersion = null;
  private Map<Place, String> badEndings = null;
  private Map<Place, String> goodEnding = null;

  private Place startingPlace = null;
  private List<Place> places = null;

  /**
   * Create a new empty GameWorld with no introduction or starting place.
   * 
   */
  public GameWorld() {
    badEndings = new HashMap<>();
    goodEnding = new HashMap<>();
    places = new ArrayList<>();
  }

  /**
   * Create a new GameWorld with an introduction and a starting place.
   * 
   * @param introduction String
   * @param startingPlace Place
   */
  public GameWorld(String introduction, Place startingPlace) {
    this();

    this.introduction = introduction;
    this.startingPlace = startingPlace;
  }

  /**
   * Set the introduction to the game.
   * 
   * @param introduction String
   */
  public void setIntroduction(String introduction) {
    this.introduction = introduction;
  }

  /**
   * Get the introduction to the game.
   * 
   * @return String
   */
  public String getIntroduction() {
    return introduction;
  }

  /**
   * Set the starting place.
   * 
   * @param startingPlace Place
   */
  public void setStartingPlace(Place startingPlace) {
    this.startingPlace = startingPlace;
  }

  /**
   * Get the starting place.
   * 
   * @return Place
   */
  public Place getStartingPlace() {
    return startingPlace;
  }

  /**
   * Setter for levelName.
   *
   * @param levelName String
   */
  public void setLevelName(String levelName) {
    this.levelName = levelName;
  }

  /**
   * Getter for levelName.
   *
   * @return String
   */
  public String getlevelName() {
    return levelName;
  }

  /**
   * Setter for levelVersion.
   *
   * @param version String
   */
  public void setLevelVersion(String version) {
    this.levelVersion = version;
  }

  /**
   * Getter for levelVersion.
   *
   * @return String
   */
  public String getLevelVersion() {
    return levelVersion;
  }

  /**
   * Add a place to the GameWorld.
   * 
   * @param newPlace Place
   */
  public void addPlace(Place newPlace) {
    places.add(newPlace);
  }

  /**
   * Add a bad ending place.
   * 
   * @param endingPlace Place
   * @param endingText String
   */
  public void addBadEnding(Place endingPlace, String endingText) {
    badEndings.put(endingPlace, endingText);
  }

  /**
   * Set the good ending place.
   * 
   * @param endingPlace
   * @param endingText
   */
  public void setGoodEnding(Place endingPlace, String endingText) {
    goodEnding.clear();
    goodEnding.put(endingPlace, endingText);
  }

  /**
   * Find out whether a place is a bad ending place.
   * 
   * @param place Place
   * @return Place
   */
  public boolean isBadEnding(Place place) {
    return badEndings.containsKey(place);
  }

  /**
   * Find out whether a place is the good ending place.
   * 
   * @param place
   * @return
   */
  public boolean isGoodEnding(Place place) {
    return goodEnding.containsKey(place);
  }

  /**
   * Get the ending for a place.
   * 
   * @param endingPlace the Ending or null if the place is not an ending place.
   * @return String
   */
  public String getEndingForPlace(Place endingPlace) {
    String endingText = null;

    if (isBadEnding(endingPlace)) {
      endingText = badEndings.get(endingPlace);
    } else if (isGoodEnding(endingPlace)) {
      endingText = goodEnding.get(endingPlace);
    }

    return endingText;
  }

}
