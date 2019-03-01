package control;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import view.Input;
import view.Output;
import view.Output.errorType;
import view.Output.options;

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
          startLevel(args, in, out);
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
   * @param args
   * @param in
   * @param out
   */
  private static void startLevel(String[] args, Input in, Output out) {
    GameControl gameControl = null;
    boolean doTest = false;
    String fileName = "game01.xml";
    int localGameNumber = -1;

    for (int i = 0; i < args.length; i++) {
      if (args[i].equalsIgnoreCase("-d")) {
        doTest = true;
        break;
      } else if (args[i].equalsIgnoreCase("-f") && ++i < args.length) {
        fileName = args[i];
      }
    }

    do {
      if (doTest) {
        gameControl = GameFactory.getTestGame();
        gameControl.setInputOutput(in, out);
      } else if (!fileName.isEmpty()) {
        gameControl = GameFactory.getGameFromFile(fileName);
        gameControl.setInputOutput(in, out);
      } else {
        System.err.println("No valid game found...");
        break;
      }
    } while (gameControl.runGame());
  }
}
