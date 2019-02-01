package view;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import control.Control;

public class Input {

  int boxings = 0;
  Scanner scan = new Scanner(System.in);
  // List for all the patterns/commands

  // Pattern for TAKE ITEM
  Pattern patternTakeItem = Pattern.compile("(?i)take\\s([\\w\\s]+)");
  // Pattern for GOTO PASSAGE NAME
  Pattern patternGotoPassage = Pattern.compile("(?i)goto\\s([\\w\\s]+)");
  // Pattern for LOOK AT PLACE
  Pattern patternLookAtPlace =
      Pattern.compile("(?i)look\\s[\\w\\s]*around\\s*[\\w\\s]*");
  // Pattern for LOOK AT anything
  Pattern patternLookAt = Pattern.compile("(?i)look\\s[\\w\\s]*at\\s([\\w\\s]*)");
  // Pattern for looking into INVENTORY
  Pattern patternInventory = Pattern.compile("(?i)inventory");
  // Pattern for getting a list of possible actions
  Pattern patternActions = Pattern.compile("(?i)actions");
  // Pattern for using an item at an obstacle
  Pattern patternUseItemObstacle = Pattern.compile("(?i)use\\s([\\w\\s]*)");

  // Filling the array allPatterns with patterns
  private Pattern possiblePatterns[] = {patternTakeItem, patternGotoPassage, patternLookAtPlace, patternLookAt,
  patternInventory, patternActions, patternUseItemObstacle};

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
   *
   */
  public void readInput() {

    String userInput = readInSingleLine();

    //The order in which the input is being matched
    Matcher matcherTakeItem = patternTakeItem.matcher(userInput);
    Matcher matcherGotoPassage = patternGotoPassage.matcher(userInput);
    Matcher matcherLookAtPlace = patternLookAtPlace.matcher(userInput);
    Matcher matcherLookAt = patternLookAt.matcher(userInput);
    Matcher matcherInventory = patternInventory.matcher(userInput);
    Matcher matcherActions = patternActions.matcher(userInput);

    // matches the user input with the patterns

    // matching with TAKE ITEM NAME
    if (matcherTakeItem.find()) {
      // Checking for boxed commands
      if (!testForBoxing(userInput, 1)) {
        if (control.pickUpItem(matcherTakeItem.group(1))) {
          out.doOutput("You have successfully picked up " + matcherTakeItem.group(1));
        } else {
          out.doOutput("There is no item called: " + matcherTakeItem.group(1));
        }
      }


      // matching with GOTO PASSAGE NAME
    } else if (matcherGotoPassage.find()) {
      // Checking for boxed commands
      if (!testForBoxing(userInput, 2)){
        control.tryToMoveThroughPassage(matcherGotoPassage.group(1));
        out.lookAtCurrentPlace();
      }

      // matching witch LOOK AT CURRENT PLACE
    } else if (matcherLookAtPlace.find()) {
      // Checking for boxed commands
      if (!testForBoxing(userInput, 3)){
        out.lookAtCurrentPlace();
        out.listItemsInPlace();
        out.listPassages();
      }

      // matching with LOOK AT
    } else if (matcherLookAt.find()) {
      // Checking for boxed commands
      if (!testForBoxing(userInput, 4)){
        out.lookAtGameObject(matcherLookAt.group(1));
      }

      // matching with INVENTORY
    } else if (matcherInventory.find()) {
      // Checking for boxed commands
      if (!testForBoxing(userInput, 5)){
        out.listInventory();
      }

      // matching with ACTIONS
    } else if (matcherActions.find()) {
      // Checking for boxed commands
      if (!testForBoxing(userInput, 6)){
        out.listOptions();
      }

      // if NOTHING matches
    } else {
      out.doOutput("You can't do that!");
      out.listOptions();
    }
  }

  /**
   * Returns Scanner new Line method.
   * !Not recommended to use outside of this class!
   * @return Scanner
   */
  public String readInSingleLine() {
    return scan.nextLine();
  }

  /**
   * Reads nextLine and searches for an decision
   * searches for "use <item>" and returns <item>
   * else it searches for "leave" and returns "leave"
   * if nothing matches it returns null
   * @return String
   */
  public String readItemForObstacle(){
    String input = readInSingleLine();
    Matcher matcherUseItemObstacle = patternUseItemObstacle.matcher((input));
    String decision = null;
    if (matcherUseItemObstacle.find()) {
      decision = matcherUseItemObstacle.group(1);
    } else if (input.matches("[lL]eave")){
      decision = "leave";
    }
    return decision;
  }

  /**
   * Takes the userInput and the hierachy of the caller(command-matcher)
   * and searches the userInput for commands with a hierachy lower than the caller.
   * If any command matches with the input it return true, else it returns false.
   * @param userInput command from the user as string
   * @param hierachy the hierachy from the caller as integer
   * @return boolean
   */
  public boolean testForBoxing(String userInput, int hierachy){
    boolean boxed = false;

    for (int i=hierachy; i < possiblePatterns.length; i++){
      Matcher m = possiblePatterns[i].matcher(userInput);
      if (m.find()){
        boxed = true;
        break;
      }
    }
    if (boxed){
      boxings++;
      if (boxings == 3){
        out.doOutput("Are you stupid? I said: \"DONT MIX THE BLOODY COMMANDS!\"");
        boxings = 0;
      } else {
        out.doOutput("Don't mix the bloody commands!");
      }
    }
    return boxed;
  }
}
