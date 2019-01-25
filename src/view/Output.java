package view;
import control.Control;

public class Output {

    Control control = null;

    //Introduction for the player at the start of the game
    public void Greeting(){
        System.out.println("Hello fellow Player, welcome to your gobsmacking adventure!");
    }

    public void ordinaryEvent(){
        System.out.println("An event happend! Wow!");
    }

    public void describePlace(){
        System.out.println(control.lookAtCurrentPlace());
    }

    public void describePassage(int number){
        //System.out.println(control.lookAtPassage(number));
    }
}
