package view;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import control.Control;

public class Input {

  private int boxings = 0;
  Scanner scan = new Scanner(System.in);
  // List for all the patterns/commands

  Pattern patternTakeItem = Pattern.compile("(?i)take\\s([\\w\\s]+)");
  Pattern patternGotoPassage = Pattern.compile("(?i)goto\\s([\\w\\s]+)");
  Pattern patternLookAtPlace = Pattern.compile("(?i)look\\s[\\w\\s]*around\\s*[\\w\\s]*");
  Pattern patternLookAt = Pattern.compile("(?i)look\\s[\\w\\s]*at\\s([\\w\\s]*)");
  Pattern patternInventory = Pattern.compile("(?i)inventory");
  Pattern patternActions = Pattern.compile("(?i)actions");
  Pattern patternUseItemObstacle = Pattern.compile("(?i)(use)*\\s*([\\w\\s]*)");

  private Pattern[] possiblePatterns = {patternTakeItem, patternGotoPassage, patternLookAtPlace,
      patternLookAt, patternInventory, patternActions};

  // Creating Output and Control object for referencing
  Output out;
  Control control;

  /**
   * Constructor.
   * 
   * @param output Output
   */
  public Input(Output output, Control control) {
    out = output;
    this.control = control;
  }

  /**
   * Reads the user input and matches it with the patterns. Calls methods from Output or Control.
   */
  public void readInput() {

    String userInput = readInSingleLine();

    // The order in which the input is being matched
    Matcher matcherTakeItem = patternTakeItem.matcher(userInput);
    Matcher matcherGotoPassage = patternGotoPassage.matcher(userInput);
    Matcher matcherLookAtPlace = patternLookAtPlace.matcher(userInput);
    Matcher matcherLookAt = patternLookAt.matcher(userInput);
    Matcher matcherInventory = patternInventory.matcher(userInput);
    Matcher matcherActions = patternActions.matcher(userInput);

    if (matcherTakeItem.find()) {
      matchTakeItem(matcherTakeItem, userInput);
    } else if (matcherGotoPassage.find()) {
      matchGotoPassage(matcherGotoPassage, userInput);
    } else if (matcherLookAtPlace.find()) {
      matchLookAtPlace(matcherLookAtPlace, userInput);
    } else if (matcherLookAt.find()) {
      matchLookAt(matcherLookAt, userInput);
    } else if (matcherInventory.find()) {
      matchInventory(matcherInventory, userInput);
    } else if (matcherActions.find()) {
      matchActions(matcherActions, userInput);
    } else {
      noMatch();
    }

  }

  public boolean matchTakeItem(Matcher match, String userInput) {
    if (!testForBoxing(userInput, 1)) {
      if (control.tryPickUpItem(match.group(1))) {
        control.pickUpItem(match.group(1));
        out.doOutput("You have successfully picked up " + match.group(1));
        return true;
      } else {
        out.doOutput("There is no item called: " + match.group(1));
      }
    }
    return false;
  }

  public boolean matchGotoPassage(Matcher match, String userInput) {
    if (!testForBoxing(userInput, 2)) {
      control.tryToMoveThroughPassage(match.group(1));
      out.lookAtCurrentPlace();
      return true;
    } else {
      return false;
    }
  }

  public boolean matchLookAtPlace(Matcher match, String userInput) {
    if (!testForBoxing(userInput, 3)) {
      out.lookAtCurrentPlace();
      out.listItemsInPlace();
      out.listPassages();
      return true;
    } else {
      return false;
    }
  }

  public boolean matchLookAt(Matcher match, String userInput) {
    if (!testForBoxing(userInput, 4)) {
      out.lookAtGameObject(match.group(1));
      return true;
    } else {
      return false;
    }
  }

  public boolean matchInventory(Matcher match, String userInput) {
    if (!testForBoxing(userInput, 5)) {
      out.listInventory();
      return true;
    } else {
      return false;
    }
  }

  public boolean matchActions(Matcher match, String userInput) {
    if (!testForBoxing(userInput, 6)) {
      out.listOptions();
      return true;
    }
    {
      return false;
    }
  }

  public void noMatch() {
    out.doOutput("You can't do that!");
    out.listOptions();
  }

  /**
   * !Not recommended to use outside of this class!
   * @return String
   */
  public String readInSingleLine() {
    return scan.nextLine();
  }

  /**
   * Reads nextLine and searches for an decision searches for "use <item>" and returns <item> else
   * it searches for "leave" and returns "leave" if nothing matches it returns null
   * 
   * @return String
   */
  public String readItemForObstacle() {
    String input = readInSingleLine();
    Matcher matcherUseItemObstacle = patternUseItemObstacle.matcher((input));
    String decision = null;
    if (input.matches("[lL]eave")) {
      decision = "leave";
    } else if (matcherUseItemObstacle.find()) {
      decision = matcherUseItemObstacle.group(2);
    }
    return decision;
  }

  /**
   * Takes the userInput and the hierachy of the caller(command-matcher) and searches the userInput
   * for commands with a hierachy lower than the caller. If any command matches with the input it
   * return true, else it returns false.
   * 
   * @param userInput command from the user as string
   * @param hierachy the hierachy from the caller as integer
   * @return boolean
   */
  public boolean testForBoxing(String userInput, int hierachy) {
    boolean boxed = false;

    for (int i = hierachy; i < possiblePatterns.length; i++) {
      Matcher m = possiblePatterns[i].matcher(userInput);
      if (m.find()) {
        boxed = true;
        break;
      }
    }

    if (boxed) {
      boxings++;
      if (boxings == 3) {
        out.doOutput("Are you stupid? I said: \"DONT MIX THE BLOODY COMMANDS!\"");
        boxings = 0;
      } else {
        out.doOutput("Don't mix the bloody commands!");
      }
    }
    return boxed;
  }
}
