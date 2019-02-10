package control;

public class GameLauncher {

  
  /**
   * Main Method.
   * 
   * @param args
   */
  public static void main(String[] args) {
    boolean doTest = true;

    Control control = new Control(doTest);

    control.runGame();
  }

}
