package view;
import java.util.Scanner;

public class Input {
    Scanner scan = new Scanner(System.in);
    String patternTakeKey = ".*[tT]ake.*[kK]ey.*";
    String patternUseDoor = ".*[uU]se.*[dD]oor.*";
    String patternUsePassage = ".*[uU]se.*[pP]assage.*";

    public String inputReader(String input){

        String userInput = scan.nextLine();

        if(userInput.matches(patternTakeKey)) {
            return "takeKey";
        }else if (userInput.matches(patternUseDoor)) {
            return "useDoor";
        }else if (userInput.matches(patternUsePassage)){
            return "usePassage";
        }else{
            return "invalidInput";
        }
    }

}
