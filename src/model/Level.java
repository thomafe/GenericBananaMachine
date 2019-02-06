package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Level {
  
  // TODO thomaf add comments

  private String indroduction = null;
  private Map<Place, String> endings = null;

  private Place startingPlace = null;
  private List<Place> places = null;

  public Level() {
    endings = new HashMap<>();
    places = new ArrayList<>();
  }

  public Level(String introdution, Place startingPlace) {
    this();

    this.indroduction = introdution;
    this.startingPlace = startingPlace;
  }

  public void setIntroduction(String introduction) {
    this.indroduction = introduction;
  }

  public String getIndroduction() {
    return indroduction;
  }

  public void setStartingPlace(Place startingPlace) {
    this.startingPlace = startingPlace;
  }

  public void addPlace(Place newPlace) {
    places.add(newPlace);
  }

  public void addPassageToPlace(Passage passge, Place place) {
    place.addPassage(passge);
  }

  public void addEndingPlace(Place endingPlace, String endingText) {
    endings.put(endingPlace, endingText);
  }

  public boolean isEndingPlace(Place place) {
    return endings.containsKey(place);
  }

  public String getEndingForPlace(Place endingPlace) {
    String endingText = null;

    if (isEndingPlace(endingPlace)) {
      endingText = endings.get(endingPlace);
    }

    return endingText;
  }

}
