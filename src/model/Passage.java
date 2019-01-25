package model;

// class mit name passage
public class Passage extends GameObject{



	// attribute
	public Place[] connectingRooms = new Place[2];

	public Obstacle obstacle = null;

	// constructor, kein rückgabewert, name so wie klasse, initialisiert classenattribute
	public Passage(String name, String description, Place place1, Place place2) {
		super(name, description);
		this.connectingRooms[0] = place1;
		this.connectingRooms[1]= place2;

	}

	// methode
	public Place usePassage(Place comingFromPlace) {

		 // zurückgeben in welchem raum man ist
           if (comingFromPlace.equals(connectingRooms[0])) {
		   	return connectingRooms[0];
		   }
           else if (comingFromPlace.equals(connectingRooms[1])) {
		   	return connectingRooms[1];
		   }
		   else{
			   return null;
		   }

	}


	// getter and setter for obstacle
	public Obstacle getObstacle() {
		return obstacle;
	}
	public void setObstacle(Obstacle obstacle) {
		this.obstacle = obstacle;
	}

	// getter for connecting rooms
	public Place[] getConnectingRooms() {
		return connectingRooms;
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
