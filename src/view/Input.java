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
    //Pattern for looking into INVENTORY
    Pattern patternInventory = Pattern.compile("[a-z\\s]*inventory\\s*[a-z]*");
    //Pattern for getting a list of possible actions
    Pattern patternActions = Pattern.compile("[a-z\\s]*actions\\s*[a-z\\s]*");

    //Creating Output and Control object for referencing
    Output out;
    Control control = null;

    public Input (Output output) {
        out = output;
    }

    /**
     * reads the user input and matches it with the patterns
     * calls methods from Output or Control
     *
     */
    public void readInput(){

        String userInput = scan.nextLine();

        Matcher matcherTakeItem = patternTakeItem.matcher(userInput);
        Matcher matcherUsePassage = patternUsePassage.matcher(userInput);
        Matcher matcherLookAt = patternLookAt.matcher(userInput);
        Matcher matcherInventory = patternInventory.matcher(userInput);
        Matcher matcherActions = patternActions.matcher(userInput);

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
            out.listItemsInPlace();
            out.listPassages();

        //matching with LOOK AT
        }else if (matcherLookAt.find()) {
            out.lookAtGameObject(matcherLookAt.group(1));

        //matching with INVENTORY
        }else if (matcherInventory.find()) {
            out.listInventory();

        //matching with ACTIONS
        }else if (matcherActions.find()) {
            out.listOptions();

        //if NOTHING matches
        }else{
            out.doOutput("You can't do that!");
        }
    }
    public String doInput(){
        return scan.nextLine();
    }
}
