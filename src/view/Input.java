package view;
import java.util.Scanner;
import view.Output;

public class Input {
    Scanner scan = new Scanner(System.in);
    String patternTakeKey = ".*[tT]ake.*[kK]ey.*";
    String patternUsePassage = ".*[uU]se.*[pP]assage.*";
    String patternLookAtPassage = ".*[lL]ook.*at.*[pP]assage.*";
    String patternLookAtPlace = ".*[lL]ook.*around.*";
    String patternLookAtItem = ".*[lL]ook.*at.*";
    Output out = null;

    public String inputReader(){

        String userInput = scan.nextLine();

        if(userInput.matches(patternTakeKey)){
            return "takeKey";
        }else if (userInput.matches(patternUsePassage)){
            return "usePassage";
        }else if (userInput.matches(patternLookAtPassage)||userInput.matches(patternLookAtPlace)) {
            //out.describe();
        }else{
            return "invalidInput";
        }
    }

}
