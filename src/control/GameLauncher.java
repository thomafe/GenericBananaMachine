package control;

public class GameLauncher {


  /**
   * Main Method.
   * 
   * @param args
   */
  public static void main(String[] args) {
    boolean doTest = true;

    // TODO Out and In probably should be created here, so we can do the play again stuff easily?
    // TODO File/Path or parameter input. Via args?
    
    GameControl control = new GameControl(null);

    control.runGame();
  }

}
