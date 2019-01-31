package view;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import control.Control;

public class Input {

  Scanner scan = new Scanner(System.in);

  // Pattern for TAKE ITEM
  Pattern patternTakeItem = Pattern.compile("[tT]ake\\s([a-zA-Z\\s]+)");
  // Pattern for USE PASSAGE NAME
  Pattern patternUsePassage = Pattern.compile("[uU]se\\s([a-zA-Z\\s]+)");
  // Pattern for LOOK AT PLACE
  Pattern patternLookAtPlace =
      Pattern.compile("[lL]ook\\s[a-zA-Z\\s]*[aA]round\\s*[a-zA-Z\\s]*");
  // Pattern for LOOK AT anything
  Pattern patternLookAt = Pattern.compile("[lL]ook\\s[a-zA-Z\\s]*[aT]t\\s([a-zA-Z\\s]*)");
  // Pattern for looking into INVENTORY
  Pattern patternInventory = Pattern.compile("[iI]nventory");
  // Pattern for getting a list of possible actions
  Pattern patternActions = Pattern.compile("[aA]ctions");
  // Pattern for using an item at an obstacle
  Pattern patternUseItemObstacle = Pattern.compile("[uU]se\\s([a-zA-Z\\s]*)");

  // Creating Output and Control object for referencing
  Output out;
  Control control = null;

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
    Matcher matcherUsePassage = patternUsePassage.matcher(userInput);
    Matcher matcherLookAtPlace = patternLookAtPlace.matcher(userInput);
    Matcher matcherLookAt = patternLookAt.matcher(userInput);
    Matcher matcherInventory = patternInventory.matcher(userInput);
    Matcher matcherActions = patternActions.matcher(userInput);

    // matches the user input with the patterns

    // matching with TAKE ITEM NAME
    if (matcherTakeItem.find()) {
      if(control.pickUpItem(matcherTakeItem.group(1))) {
        out.doOutput("You have successfully picked up " + matcherTakeItem.group(1));
      } else {
        // TODO make a method for this output - and others like it
        out.doOutput("There is no item called: " + matcherTakeItem.group(1));
      }


      // matching with USE PASSAGE NAME
    } else if (matcherUsePassage.find()) {
      control.tryToMoveThroughPassage(matcherUsePassage.group(1));
      out.lookAtCurrentPlace();

      // matching witch LOOK AT CURRENT PLACE
    } else if (matcherLookAtPlace.find()) {
      out.lookAtCurrentPlace();
      out.listItemsInPlace();
      out.listPassages();

      // matching with LOOK AT
    } else if (matcherLookAt.find()) {
      out.lookAtGameObject(matcherLookAt.group(1));

      // matching with INVENTORY
    } else if (matcherInventory.find()) {
      out.listInventory();

      // matching with ACTIONS
    } else if (matcherActions.find()) {
      out.listOptions();

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
    String input = this.readInSingleLine();
    Matcher matcherUseItemObstacle = patternUseItemObstacle.matcher((input));
    String decision = null;
    if (matcherUseItemObstacle.find()) {
      decision = matcherUseItemObstacle.group(1);
    } else if (input.matches("[lL]eave")){
      decision = "leave";
    }
    return decision;
  }
}
