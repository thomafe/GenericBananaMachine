package view;
import java.util.Scanner;

public class Input {
    Scanner scan = new Scanner(System.in);

    public String inputReader(String input){

        String patternTakeKey = ".*take.*[kK]ey.*";

        String userInput = scan.nextLine();

        if(userInput.matches(patternTakeKey)){
            return "takeKey";
        }

        return "";
    }
}
