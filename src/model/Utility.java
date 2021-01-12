package model;

/**
 * A utility class used for getting the blend values for "tweening".
 */
public class Utility {
  /**
   * Checks to see if two doubles are the same.
   *
   * @param x1 First number to compare to
   * @param x2 Second number to compare to
   * @return true if doubles are the same, false if they are not
   */
  public static boolean checkDoubles(double x1, double x2) {
    return Math.abs(x1 - x2) <= 0.0000001;
  }

  /**
   * Returns the float representation of a field of a color. Number ranges from 0.0 to 1.0.
   *
   * @return the float representation of a color field
   */

  public static float getColorFloat(int x) {
    return (float) x / (float) 255;
  }
}
