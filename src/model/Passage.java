package model;

// class mit name passage
public class Passage {



	// attribute
	public String description;



	public Place[] connectingRooms = new Place[2];

	public Obstacle obstacle = null;

	// constructor, kein rückgabewert, name so wie klasse, initialisiert classenattribute
	public Passage(Place place1, Place place2, String description) {
		this.description = description;
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

	// getter für discription
	public String getDescription() {
		return description;
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
}
