package view;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import control.GameControl;
import model.Furniture;
import model.GameObject;
import model.Item;
import model.Passage;
import view.Output.endingType;
import view.Output.errorType;
import view.Output.errorTypeInput;
import view.Output.successType;

/**
 * Processes input from the user.
 *
 * @author Niklas
 */
public class Input {

  private int boxings = 0;
  private Scanner scan = null;
  private Output out = null;
  private GameControl control = null;
  private Passage lastPassage = null;

  // List for all the patterns/commands
  private Pattern patternTakeItem = Pattern.compile("(?i)take\\s([\\w\\s]+)");
  private Pattern patternGotoPassage = Pattern.compile("(?i)(goto|use)\\s([\\w\\s]+)");
  private Pattern patternLookAtPlace = Pattern.compile("(?i)look\\s[\\w\\s]*around\\s*[\\w\\s]*");
  private Pattern patternLookAt = Pattern.compile("(?i)look\\s[\\w\\s]*at\\s([\\w\\s]*)");
  private Pattern patternInventory = Pattern.compile("(?i)inventory");
  private Pattern patternActions = Pattern.compile("(?i)actions");
  private Pattern patternExitGame = Pattern.compile("(?i)exit[\\w\\s]*");
  private Pattern patternGoBack = Pattern.compile("(?i)back");

  // Only in use while at obstacle
  private Pattern patternUseItemObstacle = Pattern.compile("(?i)(use)*\\s*([\\w\\s]*)");

  private Pattern[] possiblePatterns = {patternTakeItem, patternGotoPassage, patternLookAtPlace,
      patternLookAt, patternInventory, patternActions, patternExitGame, patternGoBack};


  /**
   * Constructor.
   *
   * @param output Output
   */
  public Input(Output output) {
    out = output;

    scan = new Scanner(System.in);
  }

  /**
   * Reads the user input and matches it with the patterns. Calls methods from Output or Control.
   */
  public void readInput() {
    String userInput = readInSingleLine();
    userInput = userInput.trim();

    // Packing the matcher inside an ArrayList.
    List<Matcher> matcher = new ArrayList<>();
    for (Pattern pattern : possiblePatterns) {
      matcher.add(pattern.matcher(userInput));
    }

    if (matcher.get(0).find()) {
      if (!testForBoxing(userInput, 1)) {
        matchTakeItem(matcher.get(0));
      }
    } else if (matcher.get(1).find()) {
      if (!testForBoxing(userInput, 2)) {
        matchGotoPassage(matcher.get(1));
      }
    } else if (matcher.get(2).find()) {
      if (!testForBoxing(userInput, 3)) {
        matchLookAtPlace();
      }
    } else if (matcher.get(3).find()) {
      if (!testForBoxing(userInput, 4)) {
        matchLookAt(matcher.get(3));
      }
    } else if (matcher.get(4).find()) {
      if (!testForBoxing(userInput, 5)) {
        matchInventory();
      }
    } else if (matcher.get(5).find()) {
      if (!testForBoxing(userInput, 6)) {
        matchActions();
      }
    } else if (matcher.get(6).find()) {
      if (!testForBoxing(userInput, 7)) {
        matchExitGame();
      }
    } else if (matcher.get(7).find()) {
      if (!testForBoxing(userInput, 8)) {
        if (lastPassage != null) {
          control.tryToMoveThroughPassage(lastPassage);
        } else {
          out.noSuccess(errorType.NO_PASSAGE);
        }
      }
    } else {
      noMatch();
    }
  }

  /**
   * Searches for "use <item>" and returns <item> in input else it searches for "leave" and returns
   * "leave" in input if nothing matches it returns null.
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
   * Reads a single raw line of user input.
   *
   * @return String
   */
  private String readInSingleLine() {
    out.beforeInput();
    return scan.nextLine();
  }

  /**
   * Waits until the user presses ENTER.
   */
  public void waitForEnter() {
    scan.nextLine();
  }

  private void matchTakeItem(Matcher match) {
    GameObject foundObject = control.findGameObject(match.group(1));

    if (foundObject instanceof Item) {
      control.pickUpItem((Item) foundObject);
      out.successfulInteraction(match.group(1), successType.PICK_UP);
    } else if (foundObject != null) {
      out.noSuccess(foundObject.getName(), errorTypeInput.NOT_AN_ITEM);
    } else {
      out.noSuccess(match.group(1), errorTypeInput.NO_ITEM);
    }
  }

  private void matchGotoPassage(Matcher match) {
    GameObject foundObject = control.findGameObject(match.group(2));

    if (foundObject instanceof Passage) {
      if (control.tryToMoveThroughPassage((Passage) foundObject)) {
        lastPassage = (Passage) foundObject;
      }
    } else if (foundObject instanceof Furniture) {
      control.interactWithFurniture((Furniture) foundObject);
    } else {
      out.noSuccess(match.group(2), errorTypeInput.THERE_IS_NONE);
    }
  }

  private void matchLookAtPlace() {
    out.lookAtGameObject(control.getCurrentPlace());
    out.listObjectsInPlace(control.getCurrentPlace());
    out.listPassages(control.getCurrentPlace());
  }

  private void matchLookAt(Matcher match) {
    GameObject foundObject = control.findGameObject(match.group(1));

    if (match.group(1).equalsIgnoreCase("yourself")) {
      out.lookAtSelf(control.getCharacter());
    } else if (foundObject == null) {
      out.noSuccess(match.group(1), errorTypeInput.THERE_IS_NONE);
    } else {
      out.lookAtGameObject(foundObject);
    }

  }

  private void matchInventory() {
    if (!control.getCharacter().getItemsInInventory().isEmpty()) {
      out.listOutput(control.getCharacter().getInventoryString());
    } else {
      out.noSuccess(errorType.EMPTY_INVENTORY);
    }
  }

  private void matchActions() {
    out.listOptions();
  }

  private void matchExitGame() {
    out.exitingTheGame(endingType.YOU_SURE);
    if (yesNo()) {
      out.exitingTheGame(endingType.YES);
      control.endGame(false);
    } else {
      out.exitingTheGame(endingType.NO);
    }
  }

  private void noMatch() {
    out.noSuccess(errorType.CANT_DO_THAT);
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
  private boolean testForBoxing(String userInput, int hierachy) {
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

  /**
   * The method to get a yes/no decision from the player.
   * 
   * @return boolean
   */
  public boolean yesNo() {
    while (true) {
      String answer = readInSingleLine();
      if (answer.matches("(?i)yes")) {
        return true;
      } else if (answer.matches("(?i)no")) {
        return false;
      } else {
        out.noSuccess(errorType.DECIDE);
      }
    }
  }


  public void setControl(GameControl control) {
    this.control = control;
  }

  public String getStartOpt(List<String> options) {
    String userInput = readInSingleLine();
    String chosenOpt = null;
    for (String option : options) {
      if (userInput.matches("(?i)" + option)) {
        chosenOpt = option;
        break;
      }
    }
    if (chosenOpt == null) {
      out.noSuccess(errorType.CANT_DO_THAT);
    }
    return chosenOpt;
  }
}
