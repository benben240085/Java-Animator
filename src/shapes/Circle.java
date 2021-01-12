package shapes;

import java.awt.Color;

/**
 * A class representing a shapes.Circle.
 */
public class Circle extends AShape {

  /**
   * Constructs a new shapes.Circle Object.
   * @param width the desired width of the circle
   * @param x the desired x coordinate of the circle
   * @param y the desired y coordinate of the circle
   * @param color the desired color of the circle
   * @param id the desired ID of the circle
   */
  public Circle(double width, double x, double y, Color color, String id) {
    super(width, x, y, color, id);
  }

  @Override
  AShape newShapes(double width, double height, double x, double y, Color color, String id) {
    return new Circle(width, x, y, color, id);
  }

}
