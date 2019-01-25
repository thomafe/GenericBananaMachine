package view;
import view.Input;
import model.Passage;
import model.Place;
import model.Item;

public class Output {
    Input inp = new Input();

    //Introduction for the player at the start of the game
    public void Greeting(){
        System.out.println("Hello fellow Player, welcome to your gobsmacking adventure!");
    }

    public void ordinaryEvent(){
        System.out.println("An event happend! Wow!");
    }

    public void describePassage(Passage passage){
        System.out.println(passage.description);
    }

    public void describePlace(Place place){
        System.out.println(place.description);
    }

    public void describeitem(Item item){
        System.out.println(item.getDescription());
    }
}
