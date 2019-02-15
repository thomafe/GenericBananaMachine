package control;

import view.Input;
import view.Output;

public class GameLauncher {


  /**
   * Main Method.
   * 
   * @param args
   */
  public static void main(String[] args) {
    GameControl gameControl = null;
    boolean doTest = false;
    String fileName = "level";
    int localGameNumber = -1;

    Output out = new Output();
    Input in = new Input(out);

    // args = new String[] {"-d"};

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

    // TODO Out and In probably should be created here, so we can do the play again stuff easily?

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
        gameControl.setInput(in);
        gameControl.setOutput(out);
      }
    } while (gameControl.runGame());
  }
}
