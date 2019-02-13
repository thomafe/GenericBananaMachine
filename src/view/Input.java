package view;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import control.GameControl;
import control.GameControl.decision_type;
import model.Furniture;
import model.Item;
import model.Passage;
import model.superclasses.GameObject;
import view.Output.endingType;
import view.Output.errorType;
import view.Output.errorTypeInput;
import view.Output.successType;

/**
 * processes input from user
 *
 * @author Niklas
 */
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
  Pattern patternExitGame = Pattern.compile("(?i)exit[\\w\\s]*");
  Pattern patternYesNo = Pattern.compile("(?i)(yes)[\\w\\s]*(no)");

  private Pattern[] possiblePatterns = {patternTakeItem, patternGotoPassage, patternLookAtPlace,
      patternLookAt, patternInventory, patternActions, patternExitGame};

  // Creating Output and Control object for referencing
  Output out;
  GameControl control;

  /**
   * Constructor.
   * 
   * @param output Output
   */
  public Input(Output output, GameControl control) {
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
    Matcher matcherExitGame = patternExitGame.matcher(userInput);

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
    } else if (matcherExitGame.find()) {
      if (!testForBoxing(userInput, 7)) {
        matchExitGame();
      }
    } else {
      noMatch();
    }

  }

  public void matchTakeItem(Matcher match) {
    GameObject foundObject = control.findGameObject(match.group(1));

    if (foundObject instanceof Item) {
      control.pickUpItem((Item) foundObject);
      out.success(match.group(1), successType.PICK_UP);
    } else {
      out.noSuccess(match.group(1), errorTypeInput.NO_ITEM);
    }
  }

  public void matchGotoPassage(Matcher match) {
    GameObject foundObject = control.findGameObject(match.group(1));

    if (foundObject instanceof Passage) {
      control.tryToMoveThroughPassage((Passage) foundObject);
    } else if (foundObject instanceof Furniture) {
      control.interactWithFurniture((Furniture) foundObject);
    } else {
      out.noSuccess(match.group(1), errorTypeInput.THERE_IS_NONE);
    }
  }

  public void matchLookAtPlace() {
    out.lookAtCurrentPlace(control.getCurrentPlace());
    out.listObjectsInPlace(control.getCurrentPlace());
    out.listPassages(control.getCurrentPlace());
  }

  public void matchLookAt(Matcher match) {
    GameObject foundObject = control.findGameObject(match.group(1));

    if (foundObject != null) {
      out.lookAtGameObject(foundObject);
    } else {
      out.noSuccess(match.group(1), errorTypeInput.THERE_IS_NONE);
    }

  }

  public void matchInventory() {
    out.listInventory(control.getCharacter().getItemsInInventory());
  }

  public void matchActions() {
    out.listOptions();
  }

  public void matchExitGame() {
    control.endGame();
  }

  public void noMatch() {
    out.noSuccess(errorType.CANT_DO_THAT);
  }

  /**
   * !Not recommended to use outside of this class!
   * 
   * @return String
   */
  private String readInSingleLine() {
    return scan.nextLine();
  }

  /**
   * Searches for "use <item>" and returns <item> in input else it searches for "leave" and returns
   * "leave" in input if nothing matches it returns null
   * 
   * @return String
   */
  public String readItemForObstacle() {
    String decision = readInSingleLine();
    Matcher matcherUseItemObstacle = patternUseItemObstacle.matcher(decision);

    if (decision.matches("(?i)leave")) {
      decision = "leave";
    } else if (matcherUseItemObstacle.find()) {
      decision = matcherUseItemObstacle.group(2);
    }
    return decision;
  }

  /**
   * The method to get a yes/no decision from the player
   * @return boolean
   */
  public boolean yesNo(){
    while (true) {
      String answer = readInSingleLine();
      if (answer.matches("(?i)yes")){
        return true;
      } else if (answer.matches("(?i)no")){
        return false;
      } else {
        out.noSuccess(errorType.DECIDE);
      }
    }
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
        out.noSuccess(errorType.DONT_MIX_MAD);
        boxings = 0;
      } else {
        out.noSuccess(errorType.DONT_MIX);
      }
    }
    return boxed;
  }
}
