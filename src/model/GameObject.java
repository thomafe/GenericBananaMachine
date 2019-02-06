
package model;

/**
 * 
 *
 * @author thomafe, lehmeti
 */
public abstract class GameObject {

  private String name;
  private String description;

  /**
   * Constructor
   *
   * @param name String
   * @param description String
   */
  public GameObject(String name, String description) {
    this.name = name;
    this.description = description;
  }

  /**
   * Getter for name of GameObjects.
   *
   * @return String
   */
  public String getName() {
    return name;
  }

  /**
   * Getter for description of GameObjects.
   *
   * @return String
   */
  public String getDescription() {
    return description;
  }

}
