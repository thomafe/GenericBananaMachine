package model;

public class Passage {
	
	public String description;
	public Place[] connectingRooms = new Place[2];
	public Obstacle obstacle = null;
	
	public Passage(Place place1, Place place2) {
		
	}
	
	public Place usePassage(Place comingFromPlace) {
		// TODO check for correct place, return target place
		return null;
	}

	/**
	 * if this has an Obstacle, return true, else return false.
	 *
	 * @return boolean
	 */
	public boolean hasObstacle () {

		if(obstacle != null) {
			return true;
		} else {
			return false;
		}
	}
	
}
