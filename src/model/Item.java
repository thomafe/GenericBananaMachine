package model;

public class Item {



	// Attribute
	private String description;




	private boolean consumed = false;

	// constructor (zum objekte erstellen)
	public Item(String description) {

		this.description = description;


	}
	// getter, public methode gibt attribut zurück
	public String getDescription() {
		return description;
	}

	public boolean isConsumed() {
		return consumed;
	}

	// methode, wenn item consumed dann true
	public void consume() {
		this.consumed = true;
	}

}
