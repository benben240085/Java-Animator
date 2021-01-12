package shapes;

import java.awt.Color;

/**
 * A class representing a shapes.Rectangle.
 */
public class Rectangle extends AShape {

  /**
   * Constructs a new shapes.Rectangle Object.
   * @param width the desired width of the rectangle
   * @param height the desired height of the rectangle
   * @param x the desired x coordinate of the rectangle
   * @param y the desired y coordinate of the rectangle
   * @param color the desired color of the rectangle
   * @param id the desired id of the rectangle
   */
  public Rectangle(double width, double height, double x, double y, Color color, String id) {
    super(width, height, x, y, color, id);
  }

  @Override
  AShape newShapes(double width, double height, double x, double y, Color color, String id) {
    return new Rectangle(width, height, x, y, color, id);
  }

}