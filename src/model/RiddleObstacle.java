package model;

import control.GameLauncher;

/**
 *  Solves the Obstacle if correct answer to riddle is given.
 *
 * @author simone
 */
public class RiddleObstacle extends Obstacle {

  private String riddleAnswer;

  /**
   * Create a new obstacle that takes a riddle answer to be solved.
   *
   * @param name String
   * @param description String
   * @param riddleAnswere String
   */
  public RiddleObstacle(String name, String description, String resolution, String riddleAnswere) {
    super(name, description, resolution);
    this.riddleAnswer = riddleAnswere;
    
    if(riddleAnswere == null && GameLauncher.isDebugging()) {
      System.err.println("---[new RiddleObstacle(" + name + ")] WARNING! Riddle Answere may not be null!!!---");
    }
  }

  /**
   * Check if the answer for the riddle is correct. If yes, return 'true' fo obstacleResolved so that
   * Obstacle gets resolved. If no return 'false'.
   */
  @Override
  public boolean tryToSolve(Object answerForRiddle) {
    if (answerForRiddle instanceof String && riddleAnswer.equalsIgnoreCase((String)answerForRiddle)) {
      resolve();
    }
    return isResolved();
  }
}
