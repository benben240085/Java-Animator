import java.awt.Color;
import model.Model;
import model.Models;
import org.junit.Test;
import shapes.AShape;
import shapes.Circle;
import shapes.Rectangle;

import static org.junit.Assert.assertEquals;

/**
 * Tests the model to see if these classes do what they're supposed to be doing.
 */
public class ModelTest {
  Models model1 = new Model();
  Models model2 = new Model(300, 300);
  Models model3 = new Model(Color.black);
  Models model4 = new Model(200, 700, Color.green);
  AShape circle = new Circle(5, 20, 40, Color.green, "C");
  AShape rectangle = new Rectangle(20, 10, 100, 100, Color.magenta, "R");
  String result = "                       start                            end\n"
      + "         -------------------------------    -------------------------------\n"
      + "         t   x   y   w   h   r   g   b      t   x   y   w   h   r   g   b  \n"
      + "shape C shapes.Circle\n"
      + "motion C 1   31  51  5   0   0   0   255    5   100 100 25  0   0   0   255\n"
      + "motion C 5   100 100 25  0   0   0   255    10  31  51  7   0   0   255 0  \n\n";
  String result1 = "                       start                            end\n"
      + "         -------------------------------    -------------------------------\n"
      + "         t   x   y   w   h   r   g   b      t   x   y   w   h   r   g   b  \n"
      + "shape R shapes.Rectangle\n"
      + "motion R 1   31  51  5   0   0   0   255    5   100 100 25  0   0   0   255\n"
      + "motion R 5   100 100 25  0   0   0   255    10  31  51  7   0   0   255 0  \n\n";
  String init = "                       start                            end\n"
      + "         -------------------------------    -------------------------------\n"
      + "         t   x   y   w   h   r   g   b      t   x   y   w   h   r   g   b  \n";
  String createdShape = init + "shape C shapes.Circle\n\n";
  String result2 = "                       start                            end\n"
      + "         -------------------------------    -------------------------------\n"
      + "         t   x   y   w   h   r   g   b      t   x   y   w   h   r   g   b  \n"
      + "shape R shapes.Rectangle\n"
      + "motion R 1   31  51  5   0   0   0   255    5   100 100 25  0   0   0   255\n\n";
  String result3 = "                       start                            end\n"
      + "         -------------------------------    -------------------------------\n"
      + "         t   x   y   w   h   r   g   b      t   x   y   w   h   r   g   b  \n"
      + "shape R shapes.Rectangle\n"
      + "motion R 2   45  67  20  20  128 128 128    7   12  14  40  12  128 128 128\n"
      + "\n"
      + "shape C shapes.Circle\n"
      + "motion C 1   31  51  5   0   0   0   255    5   100 100 25  0   0   0   255\n"
      + "motion C 5   100 100 25  0   0   0   255    10  31  51  7   0   0   255 0  \n\n";
  String result4 = "                       start                            end\n"
      + "         -------------------------------    -------------------------------\n"
      + "         t   x   y   w   h   r   g   b      t   x   y   w   h   r   g   b  \n"
      + "shape C shapes.Circle\n"
      + "motion C 1   20  20  5   0   0   0   255    5   20  20  5   0   0   0   255\n\n";

  @Test
  public void testAShapeDiameterConstructor() {
    assertEquals(new Circle(5, 20, 40, Color.green, "C"), this.circle);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testInvalidAShapeDiameterConstructor() {
    AShape circle = new Circle(-5, 20, 20, Color.blue, "H");
  }

  @Test
  public void testAShapeWidthHeightConstructor() {
    assertEquals(new Rectangle(20, 10, 100, 100, Color.magenta, "R"),
        this.rectangle);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testInvalidConstructorWithWidth() {
    AShape rectangle = new Rectangle(-5, 20, 20, 20, Color.blue, "R");
  }

  @Test (expected = IllegalArgumentException.class)
  public void testInvalidConstructorWithHeight() {
    AShape rectangle = new Rectangle(20, -5, 20, 20, Color.blue, "R");
  }

  @Test
  public void testModelConstructor() {
    assertEquals(new Model(), this.model1);
  }

  @Test
  public void testModelConstructorWithWidthHeight() {
    assertEquals(new Model(300, 300), this.model2);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testConstructorWithInvalidWidth() {
    Models model = new Model(-5, 100);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testConstructorWithInvalidHeight() {
    Models model = new Model(100, -5);
  }

  @Test
  public void testModelConstructorWithColor() {
    assertEquals(new Model(Color.black), this.model3);
  }

  @Test
  public void testModelConstructorWithWidthHeightColor() {
    assertEquals(new Model(200, 700, Color.green), this.model4);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testInvalidConstructorWithWidthHeightColor() {
    Models model = new Model(-35, 400, Color.green);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testInvalidConstructorWithWidthHeightColor2() {
    Models model = new Model(400, -35, Color.green);
  }
}
