package control;

public class GameLauncher {


  /**
   * Main Method.
   * 
   * @param args
   */
  public static void main(String[] args) {
    GameControl gameControl = null;

    boolean doTest = false;

    // TODO Out and In probably should be created here, so we can do the play again stuff easily?
    // TODO File/Path or parameter input. Via args?

    if(doTest) {
      gameControl = GameFactory.getTestGame();
    } else {
      gameControl = GameFactory.getGameFromFile(null);
    }

    if (gameControl != null) {
      gameControl.runGame();
    }
  }

}
