package shapes;

import java.awt.Color;

/**
 * An interface representing methods for shapes. A shape is an object that has an ID, a position
 * (represented by an x and y coordinate), a width and height, and a color (represented in rgb).
 * A shape can be modified to produce desired animations by methods in shape called by the model.
 */
public interface Shapes {

  /**
   * Makes a new shape with the specified fields.
   * @param id the id of the shape
   * @param x the x coordinate of the shape
   * @param y the y coordinate of the shape
   * @param w the width of the shape
   * @param h the height of the shape
   * @param c the color of the shape
   * @return a new AShape with the above values
   */
  AShape makeChangesWithoutBlend(String id, double x, double y, double w, double h, Color c);

  /**
   * Checks if the there was no "teleporting" from the end of the interval to the start of the
   * interval.
   * @param width the width of the shape at the start of the interval
   * @param height the height of the shape at the start of the interval
   * @param initialX the initialX of the shape at the start of the interval
   * @param initialY the initialY of the shape at the start of the interval
   * @param r1 the r1 of the shape at the start of the interval
   * @param g1 the g1 of the shape at the start of the interval
   * @param b1 the b1 of the shape at the start of the interval
   * @return true if the shape hasn't teleported
   */
  boolean checkTeleport(int width, int height, int initialX, int initialY, int r1, int g1, int b1);

  /**
   * Checks if the given ID is the same as this shape's ID.
   * @param id the id to be compared
   * @return true if the id's are the same
   */
  boolean checkID(String id);

  /**
   * Gets a copy of this shape's color.
   * @return a copy of this shape's color
   */
  Color getColor();

  /**
   * Creates a new shape with the same fields as this shape.
   * @return a new ashape
   */
  AShape newShape();

  /**
   * Gets the ID of this shape.
   * @return the ID of this shape.
   */
  String getId();

  /**
   * Gets the x coordinate of this shape.
   * @return the x coordinate of this shape
   */
  double getX();

  /**
   * Gets the y coordinate of this shape.
   * @return the y coordinate of this shape.
   */
  double getY();

  /**
   * Gets the width of this shape.
   * @return the width of this shape.
   */
  double getWidth();

  /**
   * Gets the height of this shape.
   * @return the height of this shape.
   */
  double getHeight();

  /**
   * Gets the render of this shape.
   * @return the render of this shape
   */
  boolean getRender();

  /**
   * Gets the shape.
   * @return the shape
   */
  AShape getShape();

  /**
   * Sets the color of the shape.
   * @param c new Color of the shape
   */
  void setColor(Color c);

  /**
   * Sets the width of the shape.
   * @param w the new width of the shape
   */
  void setW(double w);

  /**
   * Sets the height of the shape.
   * @param h the new height of the shape
   */
  void setH(double h);

  /**
   * Sets the x values of the shape.
   * @param x the new x value of the shape
   */
  void setX(double x);

  /**
   * Sets the y value of the shape.
   * @param y the new y value of the shape
   */
  void setY(double y);
}
