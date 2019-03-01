package control;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import view.Input;
import view.Output;
import view.Output.errorType;
import view.Output.options;
import control.XmlParser;

public class GameLauncher {

  /**
   * There are no objects of this class.
   */
  private GameLauncher() {
  }

  /**
   * Main Method.
   */
  public static void main(String[] args) {

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
          out.menuOptions(options.WHICH_LEVEL);
          out.listOutput(listAllLevels());
          String level = in.getStartOpt(listAllLevels());
          startLevel(level, in, out);
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

  /**
   * Starts a Level depending on the program-arguments.
   * @param level
   * @param in
   * @param out
   */
  private static void startLevel(String level, Input in, Output out) {
    GameControl gameControl = null;

    //setting the game
//    for (int i = 0; i < args.length; i++) {
//      if (args[i].equalsIgnoreCase("-d")) {
//        doTest = true;
//        break;
//      } else if (args[i].equalsIgnoreCase("-f") && ++i < args.length) {
//        fileName = args[i];
//      }
//    }

    //running the game
    do {
      if (level.equals("Debug")) {
        gameControl = GameFactory.getTestGame();
        gameControl.setInputOutput(in, out);
      } else {
        gameControl = GameFactory.getGameFromFile(level + "xml");
        gameControl.setInputOutput(in, out);
      }
    } while (gameControl.runGame());
  }

  private static List<String> listAllLevels(){
    XmlParser parser = new XmlParser();
    List<String> allLevels = new ArrayList<>();
    File levels = new File("./levels");
    for (File level : levels.listFiles()) {
      if (level.isFile()) {
        allLevels.add(parser.getStoryName(level.getName()));
      }
    }
    return allLevels;
  }

}
