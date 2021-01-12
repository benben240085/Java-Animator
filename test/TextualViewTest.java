import org.junit.Test;

import view.TextualView;
import static org.junit.Assert.assertEquals;

/**
 * Tests the visual view class. 
 */
public class TextualViewTest {
  private TextualView textView1 = new TextualView();

  @Test
  public void testTextualviewInitialize() {
    assertEquals("<html>canvas 0 1 2 3<br>",
            textView1.initialize(0,1,2,3).toString());
  }

  @Test (expected = IllegalArgumentException.class)
  public void testNegativeInitializeX() {
    textView1.initialize(0,0,-2,3);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testNegativeInitializeY() {
    textView1.initialize(1,0,2,-3);
  }

  @Test
  public void testBlankResult() {
    assertEquals("</html>",textView1.result().toString());
  }

  @Test
  public void testInitializeOnly() {
    textView1.initialize(100,100,200,200);
    assertEquals("<html>canvas 100 100 200 200<br></html>",textView1.result().toString());
  }

  @Test
  public void testCreate() {
    textView1.initialize(100,100,100,100);
    textView1.create("S1", "Rectangle", 1, 1, 1, 1,1, 1,
            1, 1, 2, 2, 2, 2, 2, 2, 2, 2);
    textView1.create("S1", "Rectangle", 2, 2, 2, 2,2, 2,
            2, 2, 3, 3, 3, 3, 3, 3, 3, 3);
    textView1.create("S1", "Rectangle", 1, 1, 1, 1,1, 1,
            1, 1, 2, 2, 2, 2, 2, 2, 2, 2);
    textView1.create("S2", "Rectangle", 2, 2, 2, 2,2, 2,
            2, 2, 3, 3, 3, 3, 3, 3, 3, 3);

    assertEquals("<html>canvas 100 100 100 100<br>shape S1 Rectangle" +
            "<br>motion S1 1   1   1   1   1   1   1   1      2   2   2   2   2   2   2   2  " +
            "<br>motion S1 2   2   2   2   2   2   2   2      3   3   3   3   3   3   3   3  " +
            "<br>motion S1 1   1   1   1   1   1   1   1      2   2   2   2   2   2   2   2  " +
            "<br>\n" + "shape S2 Rectangle" +
            "<br>motion S2 2   2   2   2   2   2   2   2      3   3   3   3   3   3   3   3  " +
            "<br>\n" +
            "</html>",textView1.result().toString());
  }

}
