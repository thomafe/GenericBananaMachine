package model;

public class Item extends GameObject {



	// Attribute
	private boolean consumed = false;

	// constructor (zum objekte erstellen)
	public Item(String name, String description) {
		super(name, description);

	}

	public boolean isConsumed() {
		return consumed;
	}

	// methode, wenn item consumed dann true
	public void consume() {
		this.consumed = true;
	}

}
