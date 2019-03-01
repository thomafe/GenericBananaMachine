package control;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import view.Input;
import view.Output;
import view.Output.errorType;
import view.Output.options;

/**
 * Launches the GBM and displays the main menu. By passing the argument "-d" it is possible to
 * access a debug mode.
 * 
 * @author fthoma, Niklas
 *
 */
public class GameLauncher {

  /**
   * There are no objects of this class.
   */
  private GameLauncher() {}

  /**
   * Main Method.
   */
  public static void main(String[] args) {

    for (String arg : args) {
      if (arg.equalsIgnoreCase("-d")) {
        startLevel("Debug");
        break;
      }
    }

    Output out = new Output();
    Input in = new Input(out);

    List<String> mainOptions =
        new ArrayList<>(Arrays.asList("Start Game", "Options", "Credits", "Exit Game"));
    String chosenOpt;

    do {
      do {
        out.listOutput(mainOptions);
        chosenOpt = in.getStartOpt(mainOptions);
      } while (chosenOpt == null);

      switch (chosenOpt) {
        case "Exit Game":
          System.exit(0);
          break;
        case "Options":
          out.menuOptions(options.NOT_YET);
          break;
        case "Start Game":
          String level = chooseLevel();
          startLevel(level);
          break;
        case "Credits":
          out.credits();
          break;
        default:
          out.noSuccess(errorType.CANT_DO_THAT);
          break;
      }
    } while (true);
  }

  private static String chooseLevel() {

    Output out = new Output();
    Input in = new Input(out);
    Map<String, String> allLevels;
    List<String> levelList = new ArrayList<>();
    allLevels = listAllLevels();

    out.menuOptions(options.WHICH_LEVEL);

    for (Map.Entry<String, String> entry : allLevels.entrySet()) {
      levelList.add(entry.getKey());
    }
    Collections.sort(levelList);
    out.listOutput(levelList);

    return allLevels.get(in.getStartOpt(levelList));
  }

  /**
   * Starts a Level depending on the program-arguments.
   * 
   * @param level
   */
  private static void startLevel(String level) {
    GameControl gameControl = null;
    Output out = new Output();
    Input in = new Input(out);

    do {
      if (level.equals("Debug")) {
        gameControl = GameFactory.getTestGame();
        gameControl.setInputOutput(in, out);
      } else {
        gameControl = GameFactory.getGameFromFile(level);
        gameControl.setInputOutput(in, out);
      }
    } while (gameControl.runGame());
  }

  private static Map<String, String> listAllLevels() {
    XmlParser parser = new XmlParser();
    Map<String, String> allLevels = new HashMap<>();
  
    File levels = new File("./levels");
    for (File level : levels.listFiles()) {
      if (level.isFile()) {
        allLevels.put(parser.getStoryName(level.getAbsolutePath()), level.getName());
      }
    }
    return allLevels;
  }
}
