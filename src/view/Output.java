package view;
import model.GameObject;

public class Output {

    //Introduction for the player at the start of the game
    public void Greeting(){
        System.out.println("Hello fellow Player, welcome to your gobsmacking adventure!");
    }

    public void ordinaryEvent(){
        System.out.println("An event happend! Wow!");
    }

    public void describe(GameObject object){
        System.out.println(object.getDescription());
    }
}
