package model;

public class RiddleObstacle extends Obstacle{

  public RiddleObstacle(){};

  private String riddleAnswer;
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
