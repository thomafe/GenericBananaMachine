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
   * 
   * @param args
   */
  public static void main(String[] args) {
    GameControl gameControl = null;

    Output out = new Output();
    Input in = new Input(out);

    List<String> mainOptions = new ArrayList<>(Arrays.asList("Start Game", "Options",
        "Credits", "Exit Game"));
    String chosenOpt;
    do {
      chosenOpt = menuMain(out, in, mainOptions);

      switch (chosenOpt) {
        case "Exit Game":
          System.exit(0);
          break;
        case "Options":
          out.menuOptions(options.NOT_YET);
          break;
        case "Start Game":
          getLevel(args, gameControl, in, out);
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

  public static String menuMain(Output out, Input in, List<String> options){
    String chosenOpt = null;
    out.listOutput(options);
    chosenOpt = in.getStartOpt(options);
    return chosenOpt;
  }

  public static void getLevel(String[] args, GameControl gC, Input in, Output out){
    boolean doTest = false;
    String fileName = "game01.xml";
    int localGameNumber = -1;

    for (int i = 0; i < args.length; i++) {
      if (args[i].equalsIgnoreCase("-d")) {
        doTest = true;
        break;
      } else if (args[i].equalsIgnoreCase("-f") && ++i < args.length) {
        fileName = args[i];
      } else if (args[i].equalsIgnoreCase("-n") && ++i < args.length) {
        localGameNumber = Integer.parseInt(args[i]);
      }
    }

    setLevel(doTest, fileName, localGameNumber, gC, in, out);
  }

  public static void setLevel(boolean doTest, String fileName, int localGameNumber,
      GameControl gameControl, Input in, Output out){
    do {
      if (doTest) {
        gameControl = GameFactory.getTestGame();
      } else if (!fileName.isEmpty()) {
        gameControl = GameFactory.getGameFromFile(fileName);
      } else if (localGameNumber >= 0) {
        gameControl = GameFactory.getLocalGame(localGameNumber);
      }

      if (gameControl == null) {
        System.err.println("No valid game found...");
        break;
      } else {
        gameControl.setInputOutput(in, out);
      }
    } while (gameControl.runGame());
  }
}
