package view;
import view.Input;
import model.

public class Output {
    Input inp = new Input();

    //Introduction for the player at the start of the game
    public void Greeting(){
        System.out.println("Hello fellow Player, welcome to your gobsmacking adventure!");
    }

    public void ordinaryEvent(){
        System.out.println("An event happend! Wow!");
    }

    public void describe(){
        System.out.println("");
    }
}
