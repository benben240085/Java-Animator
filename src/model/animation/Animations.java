package model.animation;

import java.awt.Color;
import model.Utility;
import shapes.Shapes;

/**
 * Creates an animation which figures out the "tweening" values for each second.
 */
public class Animations implements IAnimation {

  private Shapes shape;
  private int start;
  private int end;
  private Color origin;
  private Color dest;
  private double originalX;
  private double originalY;
  private double newX;
  private double newY;
  private double originalW;
  private double originalH;
  private double newW;
  private double newH;

  /**
   * Constructs an Animations object which takes in starting values and ending values of a shape.
   * @param shape     the shape at the start of the interval
   * @param start     starting time
   * @param end       ending time
   * @param origin    starting color
   * @param dest      ending color
   * @param originalX initial x value
   * @param originalY initial y value
   * @param newX      final x value
   * @param newY      final y value
   * @param originalW initial width
   * @param originalH initial height
   * @param newW      final width
   * @param newH      final height
   */
  public Animations(Shapes shape, int start, int end, Color origin, Color dest, double originalX,
      double originalY, double newX, double newY, double originalW, double originalH, double newW,
      double newH) {
    if (start < 0 || end < 0 || newW < 0 || newH < 0) {
      throw new IllegalArgumentException("Can not be negative");
    }
    if (end < start) {
      throw new IllegalArgumentException("Disappear time can not be before appear time");
    }

    this.shape = shape;
    this.start = start;
    this.end = end;
    this.origin = origin;
    this.dest = dest;
    this.originalX = originalX;
    this.originalY = originalY;
    this.newX = newX;
    this.newY = newY;
    this.originalW = originalW;
    this.originalH = originalH;
    this.newW = newW;
    this.newH = newH;
  }

  @Override
  public void animate(double currentTime) {
    float currentRed = Utility.getColorFloat(this.origin.getRed());
    float currentGreen = Utility.getColorFloat(this.origin.getGreen());
    float currentBlue = Utility.getColorFloat(this.origin.getBlue());

    float destRed = Utility.getColorFloat(this.dest.getRed());
    float destGreen = Utility.getColorFloat(this.dest.getGreen());
    float destBlue = Utility.getColorFloat(this.dest.getBlue());

    float changeRed = destRed - currentRed;
    float changeGreen = destGreen - currentGreen;
    float changeBlue = destBlue - currentBlue;

    float changeInTime = (float) (currentTime - this.getStart())
        / (float) (this.getEnd() - this.getStart());

    double changeW = this.newW - this.originalW;
    double changeH = this.newH - this.originalH;

    double currentX = this.originalX;
    double currentY = this.originalY;

    double destX = this.newX;
    double destY = this.newY;

    double changeX = destX - currentX;
    double changeY = destY - currentY;

    if ((currentTime > this.getEnd()) || (currentTime < this.getStart())) {
      //do nothing here
    } else {
      float newRed = currentRed + (changeRed * changeInTime);
      float newGreen = currentGreen + (changeGreen * changeInTime);
      float newBlue = currentBlue + (changeBlue * changeInTime);

      Color newColor = new Color(newRed, newGreen, newBlue);
      double w = this.originalW + (changeInTime * changeW);
      double h = this.originalH + (changeInTime * changeH);
      double newX = currentX + (changeX * changeInTime);
      double newY = currentY + (changeY * changeInTime);

      this.getShape().setColor(newColor);
      this.getShape().setX(newX);
      this.getShape().setY(newY);
      this.getShape().setW(w);
      this.getShape().setH(h);
    }
  }

  @Override
  public Shapes getShape() {
    return this.shape;
  }

  @Override
  public int getStart() {
    return this.start;
  }

  @Override
  public int getEnd() {
    return this.end;
  }

  @Override
  public void setShape(Shapes s) {
    this.shape = s;
  }
}
