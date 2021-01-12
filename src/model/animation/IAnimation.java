package model.animation;

import shapes.Shapes;

/**
 * Represents an animation which has a start and end for x and y values, width and height, and
 * color. When passed into the visual view it is given new shapes each second.
 */
public interface IAnimation {

  /**
   * Calculates the "tween" for each second in between the start and end times.
   * @param currentTime the currentTime or tick
   */
  void animate(double currentTime);

  /**
   * Gets the shape at the start of the interval.
   * @return the shape at the start of the interval
   */
  Shapes getShape();

  /**
   * Gets the starting interval of the motion.
   * @return the starting interval of the motion
   */
  int getStart();

  /**
   * Gets the ending interval of the motion.
   * @return the ending interval of the motion
   */
  int getEnd();

  /**
   * Sets the shape to the given shape.
   * @param s the given shape
   */
  void setShape(Shapes s);
}
