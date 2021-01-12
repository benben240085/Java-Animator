package shapes;

import java.awt.Color;
import java.util.Objects;

/**
 * An Abstract Class that represents a shape. This class implements shapes and provides abstract
 * methods that can be used by all shapes that extend Ashape. It also provides super constructors,
 * one that uses width and height, and another that uses diameter.
 */
public abstract class AShape implements Shapes {
  private double width;
  private double height;
  private double x;
  private double y;
  private Color color;
  private final String id;
  private boolean render;

  /**
   * Constructs a new shapes.AShape.
   * @param width the desired width of the Shape
   * @param height the desired height of the Shape
   * @param x the desired x coordinate of the shape
   * @param y the desired y coordinate of the shape
   * @param color the desired color of the shape
   * @param id the desired ID of the shape
   * @throws IllegalArgumentException if the width or height are negative
   */
  public AShape(double width, double height, double x, double y, Color color, String id) {
    if (width < 0 || height < 0) {
      throw new IllegalArgumentException("The area of a shape must be positive");
    }
    this.width = width;
    this.height = height;
    this.x = x;
    this.y = y;
    this.color = color;
    this.id = id;
    this.render = true;
  }

  /**
   * Constructs a new shapes.AShape using diameter rather than width and height.
   * @param diameter the desired diameter of the shape
   * @param x the desired x coordinate of the shape
   * @param y the desired y coordinate of the shape
   * @param color the desired color of the shape
   * @param id the desired ID of the shape
   * @throws IllegalArgumentException if the diameter is negative
   */
  public AShape(double diameter, double x, double y, Color color, String id) {
    if (diameter < 0) {
      throw new IllegalArgumentException("Diameter must be positive");
    }
    this.width = diameter;
    this.height = 0;
    this.x = x;
    this.y = y;
    this.color = color;
    this.id = id;
  }

  @Override
  public AShape makeChangesWithoutBlend(String id, double x, double y, double w, double h,
      Color c) {
    return this.newShapes(w, h, x, y, c, id);
  }

  @Override
  public boolean checkTeleport(int width, int height, int initialX, int initialY, int r1, int g1,
      int b1) {
    return this.width == width && this.height == height && this.x == initialX && this.y == initialY
        && this.color.getRed() == r1 && this.color.getGreen() == g1 && this.color.getBlue() == b1;
  }

  @Override
  public boolean checkID(String id) {
    return this.id.equals(id);
  }

  @Override
  public Color getColor() {
    return this.color;
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof AShape)) {
      return false;
    } else {
      AShape that = (AShape) obj;

      return this.width == that.width && this.height == that.height && this.id.equals(that.id)
          && this.x == that.x && this.y == that.y && this.color.getRed() == that.color.getRed()
          && this.color.getGreen() == that.color.getGreen() && this.color.getBlue() ==
          that.color.getBlue();
    }
  }

  @Override
  public int hashCode() {
    return Objects.hash(width, height, x, y, id, color);
  }

  @Override
  public String getId() {
    return this.id;
  }

  @Override
  public double getX() {
    return this.x;
  }

  @Override
  public double getY() {
    return this.y;
  }

  @Override
  public double getWidth() {
    return this.width;
  }

  @Override
  public double getHeight() {
    return this.height;
  }

  @Override
  public boolean getRender() {
    return this.render;
  }

  @Override
  public AShape getShape() {
    return this;
  }

  @Override
  public AShape newShape() {
    return newShapes(this.getWidth(), this.getHeight(), this.getX(), this.getY(), this.getColor(),
        this.getId());
  }

  @Override
  public void setColor(Color c) {
    this.color = c;
  }

  @Override
  public void setW(double w) {
    this.width = w;
  }

  @Override
  public void setH(double h) {
    this.height = h;
  }

  @Override
  public void setX(double x) {
    this.x = x;
  }

  @Override
  public void setY(double y) {
    this.y = y;
  }

  /**
   * Creates a new Shape of the desired type.
   * @param width the desired width of the shape
   * @param height the desired height of the shape
   * @param x the desired x coordinate of the shape
   * @param y the desired y coordinate of the shape
   * @param color the desired color of the shape
   * @param id the desired ID of the shape
   * @return a new shape.
   */
  abstract AShape newShapes(double width, double height, double x, double y, Color color,
      String id);

}
