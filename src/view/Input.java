package view;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import control.Control;

public class Input {
    Scanner scan = new Scanner(System.in);

    Pattern patternLookAtPassage = Pattern.compile("[a-z\\s]*look\\s[a-z\\s]*at\\s[a-z\\s]*passage\\s[a-z\\s]*(\\d)");
    Pattern patternTakeItem = Pattern.compile("[a-z\\s]*take\\s([a-z\\s]*)");
    Pattern patternUsePassage = Pattern.compile("[a-z\\s]*use\\s[a-z\\s]*passage\\s[a-z\\s]*(\\d)");
    String patternLookAtPlace = "[a-z\\s]*look\\s[a-z\\s]*around\\s[a-z\\s]*";
    Pattern patternLookAtItem = Pattern.compile("[a-z\\s]*look\\s[a-z\\s]*at\\s([a-z]*)");

    Output out = null;
    Control control = null;

    public void inputReader(){

        String userInput = scan.nextLine();

        Matcher matcherLookAtPassage = patternLookAtPassage.matcher(userInput);
        Matcher matcherTakeItem = patternTakeItem.matcher(userInput);
        Matcher matcherUsePassage = patternUsePassage.matcher(userInput);
        Matcher matcherLookAtItem = patternLookAtItem.matcher(userInput);

        if(matcherTakeItem.find()){
            control.pickUpItem(matcherTakeItem.group(1));
        }else if (matcherUsePassage.find()){
            control.canMoveCharacter(matcherUsePassage.group(1));
        }else if (matcherLookAtPassage.find()) {
            out.describePassage(Integer.parseInt(matcherLookAtPassage.group(1)));
        }else if (userInput.matches(patternLookAtPlace)) {
            out.describePlace();
        }else if (matcherLookAtItem.find()){
            //TODO
        }else{
            //TODO Method for invalid input
        }
    }

}
