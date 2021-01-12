import java.awt.Color;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import shapes.Ellipse;
import view.SvgView;

import static org.junit.Assert.assertEquals;

/**
 * A class that cointains tests for svgView.
 */
public class SvgViewTest {

  private SvgView view1 = new SvgView();

  @Test
  public void testLegalAnimate() {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PrintStream ps = new PrintStream(baos);
    PrintStream old = System.out;
    view1.animate(100, 100, 100, 50);
    view1.sendToOutput(ps);
    System.out.flush();
    System.setOut(old);
    assertEquals("<svg width=\"100\" height=\"100\" version=\"1.1\" "
            + "xmlns=\"http://www.w3.org/2000/svg\">\n" + "<rect> <animate id=\"base\" begin=\"0;"
            + "base."
            + "end\" dur=\"2000.0ms\" attributeName=\"visibility\" from=\"hide\" to=\"hide\" /> "
            + "</rect>\n" + "</svg>", baos.toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIllegalAnimateWidth() {
    view1.animate(-1, 100, 100,
            50);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIllegalAnimateHeight() {
    view1.animate(100, -1, 100,
            50);
  }

  @Test
  public void testAnimatingRectangle() {
    view1.animate(100, 100, 100, 100);
    view1.createShape("R1", 1, 1, 1, 1, 1, 1, 1,
            "rect", 0, 0);
    view1.svgShape(1, 5, "x", 1, 2, "fill",
            4);
    view1.closing("R1", "rect");
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PrintStream ps = new PrintStream(baos);
    PrintStream old = System.out;
    view1.sendToOutput(ps);
    System.out.flush();
    System.setOut(old);
    assertEquals("" + "<svg width=\"100\" height=\"100\" version=\"1.1\" xmlns=\"http://ww"
            + "w"
            + ".w3.org/2000/svg\">\n" + "<rect> <animate id=\"base\" begin=\"0;base.end\" "
            + "dur=\"1000.0m"
            + "s\" attributeName=\"visibility\" from=\"hide\" to=\"hide\" /> </rect>\n" +
            "<rect id=\"R1\" x=\"1\" y=\"1\" width=\"1\" height=\"1\" fill=\"rgb(1,1,1)\" "
            + "visibility=\""
            + "visible\" >\n" +
            "<animate attributeType=\"xml\" begin=\"base.begin+250.0ms\" dur=\"1250.0ms\" "
            + "attributeName"
            + "=\"x\" from=\"1\" to=\"2\" fill=\"fill\" />\n" +
            "<animate attributeType=\"xml\" begin=\"base.end\" dur=\"1ms\" attributeName=\"x\" "
            + "to=\"1\""
            + " "
            + "fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"base.end\" dur=\"1ms\" attributeName=\"y\" "
            + "to=\"1\""
            + " fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"base.end\" dur=\"1ms\" attributeName=\"w\" "
            + "to=\"1\""
            + " fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"base.end\" dur=\"1ms\" attributeName=\"h\" "
            + "to=\"1\""
            + " fill=\"freeze\" />\n" +
            "<animate attributeName=\"fill\" begin=\"base.end\" dur=\"1ms\" to=\"rgb(1,1,1)\" "
            + "fill=\""
            + "freeze\" />\n" + "</rect></svg>", baos.toString());
  }

  @Test
  public void testAnimateEllipse() {
    view1.animate(100, 100, 100, 100);
    view1.createShape("E1", 1, 1, 1, 1, 1, 1, 1,
            "ellipse", 0, 0);
    view1.svgShape(1, 5, "x", 1, 2, "fill",
            4);
    view1.closing("E1", "ellipse");
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PrintStream ps = new PrintStream(baos);
    PrintStream old = System.out;
    view1.sendToOutput(ps);
    System.out.flush();
    System.setOut(old);
    assertEquals("" +
            "<svg width=\"100\" height=\"100\" version=\"1.1\" xmlns=\"http://www.w3.org/2000/"
            + "svg\">\n"
            +
            "<rect> <animate id=\"base\" begin=\"0;base.end\" dur=\"1000.0ms\" attributeName=\""
            + "visibil"
            + "ity\" from=\"hide\" to=\"hide\" /> </rect>\n" +
            "<ellipse id=\"E1\" cx=\"1\" cy=\"1\" rx=\"1\" ry=\"1\" fill=\"rgb(1,1,1)\" "
            + "visibility=\""
            + "visible\" >\n" +
            "<animate attributeType=\"xml\" begin=\"base.begin+250.0ms\" dur=\"1250.0ms\" "
            + "attributeName"
            + "=\"x\" from=\"1\" to=\"2\" fill=\"fill\" />\n" +
            "<animate attributeType=\"xml\" begin=\"base.end\" dur=\"1ms\" attributeName=\"cx\" "
            + "to=\""
            + "1\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"base.end\" dur=\"1ms\" attributeName=\"cy\" "
            + "to=\""
            + "1\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"base.end\" dur=\"1ms\" attributeName=\"rx\" "
            + "to=\""
            + "1\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"base.end\" dur=\"1ms\" attributeName=\"ry\" "
            + "to=\""
            + "1\" fill=\"freeze\" />\n" +
            "<animate attributeName=\"fill\" begin=\"base.end\" dur=\"1ms\" to=\"rgb(1,1,1)\" "
            + "fill=\""
            + "freeze\" />\n" +
            "</ellipse></svg>", baos.toString());
  }

  @Test
  public void testAnimateColor() {
    Ellipse e1 = new Ellipse(1, 1, 1, 1, new Color(1, 1, 1), "E1");
    view1.animate(100, 100, 100, 100);
    view1.createShape("E1", 1, 1, 1, 1, 1, 1, 1,
            "ellipse", 0, 0);
    view1.svgColor(1, 5, 10, 20, 30, e1, 4);
    view1.closing("E1", "ellipse");
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PrintStream ps = new PrintStream(baos);
    PrintStream old = System.out;
    view1.sendToOutput(ps);
    System.out.flush();
    System.setOut(old);
    assertEquals("" + "<svg width=\"100\" height=\"100\" version=\"1.1\" xmlns=\"http://ww"
            + "w.w3.org/2000/svg\">\n" +
            "<rect> <animate id=\"base\" begin=\"0;base.end\" dur=\"1000.0ms\" attributeName=\""
            + "visibili"
            + "ty\" from=\"hide\" to=\"hide\" /> </rect>\n" +
            "<ellipse id=\"E1\" cx=\"1\" cy=\"1\" rx=\"1\" ry=\"1\" fill=\"rgb(1,1,1)\" visibility"
            + "=\"v"
            + "isible\" >\n" +
            "<animate attributeName=\"fill\" begin=\"base.begin+250.0ms\" dur=\"1250.0ms\" from=\""
            + "rg"
            + "b(10,20,30)\" to=\"rgb(1,1,1)\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"base.end\" dur=\"1ms\" attributeName=\"cx\" "
            + "to=\""
            + "1\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"base.end\" dur=\"1ms\" attributeName=\"cy\" "
            + "to=\""
            + "1\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"base.end\" dur=\"1ms\" attributeName=\"rx\" "
            + "to=\""
            + "1\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"base.end\" dur=\"1ms\" attributeName=\"ry\" "
            + "to=\""
            + "1\" fill=\"freeze\" />\n" +
            "<animate attributeName=\"fill\" begin=\"base.end\" dur=\"1ms\" to=\"rgb(1,1,1)\" "
            + "fill=\""
            + "freeze\" />\n" +
            "</ellipse></svg>", baos.toString());
  }

}
