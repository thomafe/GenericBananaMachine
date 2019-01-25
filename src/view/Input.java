package view;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import control.Control;

public class Input {

    Scanner scan = new Scanner(System.in);

    //Pattern for TAKE ITEM
    Pattern patternTakeItem = Pattern.compile("[a-z\\s]*take\\s([a-z\\s]+)");
    //Pattern for USE PASSAGE NAME
    Pattern patternUsePassage = Pattern.compile("[a-z\\s]*use\\s([a-z]+)");
    //Pattern for LOOK AT PLACE
    String patternLookAtPlace = "[a-z\\s]*look\\s[a-z\\s]*around\\s[a-z\\s]*";
    //Pattern for LOOK AT anything
    Pattern patternLookAt = Pattern.compile("[a-z\\s]*look\\s[a-z\\s]*at\\s([a-z]*)");

    //Creating Output and Control object for referencing
    Output out = null;
    Control control = null;


    /**
     * reads the user input and matches it with the patterns
     * calls methods from Output or Control
     *
     */
    public void inputReader(){

        String userInput = scan.nextLine();

        Matcher matcherTakeItem = patternTakeItem.matcher(userInput);
        Matcher matcherUsePassage = patternUsePassage.matcher(userInput);
        Matcher matcherLookAt = patternLookAt.matcher(userInput);

        /**
         * matches the user input with the patterns
         *
         */
        //matching with TAKE ITEM NAME
        if(matcherTakeItem.find()){
            control.pickUpItem(matcherTakeItem.group(1));

        //matching with USE PASSAGE NAME
        }else if (matcherUsePassage.find()){
            control.canMoveCharacter(matcherUsePassage.group(1));

        //matching witch LOOK AT CURRENT PLACE
        }else if (userInput.matches(patternLookAtPlace)) {
            out.lookAtCurrentPlace();

        //matching with LOOK AT
        }else if (matcherLookAt.find()){
            out.lookAtGameObject(matcherLookAt.group(1));

        //if NOTHING matches
        }else{
            System.out.println("You can't do that!");
        }
    }

}
