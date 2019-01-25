package view;

public class Output {

    //Introduction for the player at the start of the game
    public void Greeting(){
        System.out.println("Hello fellow Player, welcome to your gobsmacking adventure!");
    }

    public void ordinaryEvent(){
        System.out.println("An event happend! Wow!");
    }

    public void tellPlayer(String command){
        if(command == "something"){
            System.out.println("something");
        }else if(command == "another something"){
            System.out.println("another something");
        }
    }
}
