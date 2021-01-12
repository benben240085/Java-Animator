package view;

import java.awt.event.ActionListener;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import shapes.AShape;
import shapes.Shapes;

/**
 * A class that displays the provided data in the form of an SVG file that contains code that builds
 * the animation in a web browser. It implements IView. It has a stringbuilder that is appended to
 * over time and contains the code that will make up the svg file. It also contains a hashmap that
 * contains the reset values of each shape in the animation so that loops can be implemented.
 */
public class SvgView implements IView {

  StringBuilder output;
  HashMap<String, StringBuilder> resetValues;

  /**
   * Constructs a new svgView, with a blank stringbuilder and hashmap.
   */
  public SvgView() {
    this.output = new StringBuilder();
    this.resetValues = new HashMap<>();
  }

  @Override
  public void animate(int boardWidth, int boardHeight, int max, int ticks) {
    output.append("<svg width=\"" + boardWidth + "\" height=\"" + boardHeight +
        "\" version=\"1.1\"" + " xmlns=" + "\"http://www.w3.org/2000/svg\">\n");
    output.append("<rect> <animate id=\"base\" begin=\"0;base.end\" dur=\"" +
        (max * 1000) / (double)ticks + "ms\" attributeName=\"visibility\" from=\"hide\" "
        + "to=\"hide\" /> </rect>\n");
  }

  @Override
  public void createShape(String id, int x1, int y1, int w1, int h1, int r1, int g1,
      int b1, String type, int boardX, int boardY) {
    if (type.equals("rect")) {
      x1 -= boardX;
      y1 -= boardY;
      output.append("<" + type + " id=\"" + id + "\" x=\"" + x1 + "\" y=\"" + y1 + "\" width=\"" +
          w1 + "\" " + "height=\"" + h1 + "\" fill=\"rgb(" + r1 + "," + g1 + "," + b1 +
          ")\" visibility=" + "\"" + "visible\" " + ">\n");
      StringBuilder reset = new StringBuilder();
      reset.append("<animate attributeType=\"xml\" begin=\"base.end\" dur=\"1ms\" attributeName="
          + "\"x\" to=\"" + x1 + "\" fill=\"freeze\" />\n");
      reset.append("<animate attributeType=\"xml\" begin=\"base.end\" dur=\"1ms\" attributeName="
          + "\"y\" to=\"" + y1 + "\" fill=\"freeze\" />\n");
      reset.append("<animate attributeType=\"xml\" begin=\"base.end\" dur=\"1ms\" attributeName="
          + "\"w\" to=\"" + w1 + "\" fill=\"freeze\" />\n");
      reset.append("<animate attributeType=\"xml\" begin=\"base.end\" dur=\"1ms\" attributeName="
          + "\"h\" to=\"" + h1 + "\" fill=\"freeze\" />\n");
      reset.append("<animate attributeName=\"fill\" begin=\"base.end\" dur=\"1ms\" to=\"rgb(" + r1
          + "," + g1 + "," + b1 + ")\" fill=\"freeze\" />\n");
      resetValues.put(id, reset);
    } else if (type.equals("ellipse")) {
      output.append("<" + type + " id=\"" + id + "\" cx=\"" + x1 + "\" cy=\"" + y1 + "\" rx=\"" + w1
          + "\" " + "ry=\"" + h1 + "\" fill=\"rgb(" + r1 + "," + g1 + "," + b1 + ")\" visibility="
          + "\"" + "visible\" " + ">\n");
      StringBuilder reset = new StringBuilder();
      reset.append("<animate attributeType=\"xml\" begin=\"base.end\" dur=\"1ms\" attributeName="
          + "\"cx\" to=\"" + x1 + "\" fill=\"freeze\" />\n");
      reset.append("<animate attributeType=\"xml\" begin=\"base.end\" dur=\"1ms\" attributeName="
          + "\"cy\" to=\"" + y1 + "\" fill=\"freeze\" />\n");
      reset.append("<animate attributeType=\"xml\" begin=\"base.end\" dur=\"1ms\" attributeName="
          + "\"rx\" to=\"" + w1 + "\" fill=\"freeze\" />\n");
      reset.append("<animate attributeType=\"xml\" begin=\"base.end\" dur=\"1ms\" attributeName="
          + "\"ry\" to=\"" + h1 + "\" fill=\"freeze\" />\n");
      reset.append("<animate attributeName=\"fill\" begin=\"base.end\" dur=\"1ms\" to=\"rgb(" + r1
          + "," + g1 + "," + b1 + ")\" fill=\"freeze\" />\n");
      resetValues.put(id, reset);
    }
  }

  @Override
  public void svgShape(int t1, int duration, String attribute, int from, int to, String fill,
      int ticks) {
    output.append("<animate attributeType=\"xml\" begin=\"base.begin+" + (t1 * 1000) / (double)ticks
        + "ms\" dur=\"" + (duration * 1000) / (double)ticks + "ms\" attributeName=\"" + attribute +
        "\" from=\"" + from + "\" to=\"" + to + "\" fill=\"" + fill + "\" />\n");
  }

  @Override
  public void closing(String id, String type) {
    output.append(resetValues.get(id));
    output.append("</" + type + ">");
  }

  @Override
  public void setButtonListener(ActionListener actionEvent) {
    throw new UnsupportedOperationException("This view doesn't support this method");
  }

  @Override
  public void setIsLoop(boolean loop) {
    throw new UnsupportedOperationException("This view doesn't support this method");
  }

  @Override
  public void setLabel(String text) {
    throw new UnsupportedOperationException("This view doesn't support this method");
  }

  @Override
  public String getName() {
    throw new UnsupportedOperationException("This view doesn't support this method");
  }

  @Override
  public String getShapeType() {
    throw new UnsupportedOperationException("This view doesn't support this method");
  }

  @Override
  public void setText() {
    throw new UnsupportedOperationException("This view doesn't support this method");
  }

  @Override
  public void updateText(LinkedHashMap<String, AShape> text, List<String> keyFrames) {
    throw new UnsupportedOperationException("This view doesn't support this method");
  }

  @Override
  public void createListOfShapes(LinkedHashMap<String, AShape> text, List<String> keyFrames) {
    throw new UnsupportedOperationException("This view doesn't support this method");
  }

  @Override
  public String getKeyFrameData() {
    throw new UnsupportedOperationException("This view doesn't support this method");
  }

  @Override
  public void setKeyFrame() {
    throw new UnsupportedOperationException("This view doesn't support this method");
  }

  @Override
  public String getKeyFramePosition() {
    throw new UnsupportedOperationException("This view doesn't support this method");
  }

  @Override
  public String getKeyFrameDimension() {
    throw new UnsupportedOperationException("This view doesn't support this method");
  }

  @Override
  public String getKeyFrameColor() {
    throw new UnsupportedOperationException("This view doesn't support this method");
  }

  @Override
  public void svgColor(int t1, int duration, int r1, int g1, int b1, AShape shape, int ticks) {
    output.append("<animate attributeName=\"fill\" begin=\"base.begin+" + (t1 * 1000) /
        (double)ticks + "ms\" dur=\"" + (duration * 1000) / (double)ticks + "ms\" from=\"rgb(" +
        r1 + "," + g1 + "," + b1 + ")\" to=\"rgb(" + shape.getColor().getRed() + "," +
        shape.getColor().getGreen() + "," + shape.getColor().getBlue() +
        ")\" fill=\"freeze\" />\n");
  }

  @Override
  public void makeVisible() {
    output.append("</svg>");
  }

  @Override
  public StringBuilder initialize(int x, int y, int width, int height) {
    throw new UnsupportedOperationException("This view doesn't support this method");
  }

  @Override
  public void create(String name, String type, int t1, int x1, int y1, int w1, int h1, int r1,
      int g1, int b1, int t2, int x2, int y2, int w2, int h2, int r2, int g2, int b2) {
    throw new UnsupportedOperationException("This view doesn't support this method");
  }

  @Override
  public StringBuilder result() {
    throw new UnsupportedOperationException("This view doesn't support this method");
  }

  @Override
  public void setShapes(List<Shapes> shapes) {
    throw new UnsupportedOperationException("This view doesn't support this method");
  }

  @Override
  public void setOffset(int boardX, int boardY) {
    throw new UnsupportedOperationException("This view doesn't support this method");
  }

  @Override
  public void refresh() {
    throw new UnsupportedOperationException("This view doesn't support this method");
  }

  @Override
  public void sendToOutput(PrintStream out) {
    makeVisible();
    out.append(output);
  }
}
