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
      if (!testForBoxing(userInput, 1)) {
        matchTakeItem(matcherTakeItem);
      }
    } else if (matcherGotoPassage.find()) {
      if (!testForBoxing(userInput, 2)) {
        matchGotoPassage(matcherGotoPassage);
      }
    } else if (matcherLookAtPlace.find()) {
      if (!testForBoxing(userInput, 3)) {
        matchLookAtPlace();
      }
    } else if (matcherLookAt.find()) {
      if (!testForBoxing(userInput, 4)) {
        matchLookAt(matcherLookAt);
      }
    } else if (matcherInventory.find()) {
      if (!testForBoxing(userInput, 5)) {
        matchInventory();
      }
    } else if (matcherActions.find()) {
      if (!testForBoxing(userInput, 6)) {
        matchActions();
      }
    } else {
      noMatch();
    }

  }

  public void matchTakeItem(Matcher match) {
      if (control.checkPickUpItem(match.group(1))) {
        control.pickUpItem(match.group(1));
        out.success(match.group(1), 1);
      } else {
        out.noSuccess(match.group(1), 1);
      }
  }

  public void matchGotoPassage(Matcher match) {
      if (control.checkPassage(match.group(1))) {
        control.tryToMoveThroughPassage(match.group(1));
        out.lookAtCurrentPlace();
      } else {
        out.noSuccess(match.group(1), 2);
      }
  }

  public void matchLookAtPlace() {
      out.lookAtCurrentPlace();
  }

  public void matchLookAt(Matcher match) {
    if (control.checkLookAtGameObject(match.group(1))) {
      out.lookAtGameObject(match.group(1));
    } else {
      out.noSuccess(match.group(1), 3);
    }
  }

  public void matchInventory() {
      out.listInventory();
  }

  public void matchActions() {
      out.listOptions();
  }

  public void noMatch() {
    out.noSuccess(1);
  }

  /**
   * !Not recommended to use outside of this class!
   * @return String
   */
  public String readInSingleLine() {
    return scan.nextLine();
  }

  /**
   * Searches for "use <item>" and returns <item> in input else
   * it searches for "leave" and returns "leave" in input
   * if nothing matches it returns null
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
        out.noSuccess(6);
        boxings = 0;
      } else {
        out.noSuccess(5);
      }
    }
    return boxed;
  }
}
