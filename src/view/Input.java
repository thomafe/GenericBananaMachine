package view;
import java.util.Scanner;
import view.Output;

public class Input {
    Scanner scan = new Scanner(System.in);
    String patternTakeKey = ".*[tT]ake.*[kK]ey.*";
    String patternUsePassage = ".*[uU]se.*[pP]assage.*";
    String patternLookAtPassage = ".*[lL]ook.*at.*[pP]assage.*";
    Output out = new Output();

    public String inputReader(){

        String userInput = scan.nextLine();

        if(userInput.matches(patternTakeKey)){
            return "takeKey";
        }else if (userInput.matches(patternUsePassage)){
            return "usePassage";
        }else if (userInput.matches(patternLookAtPassage)){
            out.descriptionPassage();
        }else{
            return "invalidInput";
        }
    }

}
