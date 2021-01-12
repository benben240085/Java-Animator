package shapes;

import java.awt.Color;

/**
 * A class representing a shapes.Ellipse.
 */
public class Ellipse extends AShape {
  /**
   * Constructs a new shapes.Ellipse Object.
   * @param width the desired width of the Ellipse
   * @param x the desired x coordinate of the Ellipse
   * @param y the desired y coordinate of the Ellipse
   * @param color the desired color of the Ellipse
   * @param id the desired ID of the Ellipse
   */
  public Ellipse(double width, double height, double x, double y, Color color, String id) {
    super(width, height, x, y, color, id);
  }

  @Override
  AShape newShapes(double width, double height, double x, double y, Color color, String id) {
    return new Ellipse(width, height, x, y, color, id);
  }
}
