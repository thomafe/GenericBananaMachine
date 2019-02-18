package model;

/**
 * 
 * @author simone
 *
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
  }

  /**
   * Check if the answer for the riddle is correct if yes return true fo obstacleResolved so that
   * Obstacle gets resolved if no return false
   */
  public boolean tryToAnswerRiddle(String answerForRiddle) {
    if (riddleAnswer.equalsIgnoreCase(answerForRiddle)) {
      resolve();
    }
    return isResolved();
  }
}
