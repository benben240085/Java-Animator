package view;

import java.awt.event.ActionListener;
import java.io.PrintStream;
import java.util.LinkedHashMap;
import java.util.List;

import shapes.AShape;
import shapes.Shapes;

/**
 * A Mock view class that is used to test button calls from the controller. It has no real
 * functionality other than testing the button calls.
 */
public class MockView implements IView {

  StringBuilder log;

  public MockView(StringBuilder log) {
    this.log = log;
  }


  @Override
  public void makeVisible() {
    //This method is not needed by the mock to test the controller.
  }

  @Override
  public StringBuilder initialize(int x, int y, int width, int height) {
    return null;
  }

  @Override
  public void create(String name, String type, int t1, int x1, int y1, int w1, int h1, int r1,
                     int g1, int b1, int t2, int x2, int y2, int w2, int h2, int r2,
                     int g2, int b2) {
    //This method is not needed by the mock to test the controller.

  }

  @Override
  public StringBuilder result() {
    return null;
  }

  @Override
  public void setShapes(List<Shapes> shapes) {
    //This method is not needed by the mock to test the controller.

  }

  @Override
  public void setOffset(int boardX, int boardY) {
    //This method is not needed by the mock to test the controller.

  }

  @Override
  public void refresh() {
    //This method is not needed by the mock to test the controller.

  }

  @Override
  public void sendToOutput(PrintStream out) {
    //This method is not needed by the mock to test the controller.

  }

  @Override
  public void svgColor(int t1, int duration, int r1, int g1, int b1, AShape shape, int ticks) {
    //This method is not needed by the mock to test the controller.

  }

  @Override
  public void animate(int boardWidth, int boardHeight, int max, int ticks) {
    //This method is not needed by the mock to test the controller.

  }

  @Override
  public void createShape(String id, int x1, int y1, int w1, int h1, int r1, int g1,
                          int b1, String type, int boardX, int boardY) {
    //This method is not needed by the mock to test the controller.

  }

  @Override
  public void svgShape(int t1, int duration, String attribute, int from, int to, String fill, int
      ticks) {
    //This method is not needed by the mock to test the controller.

  }

  @Override
  public void closing(String id, String type) {
    //This method is not needed by the mock to test the controller.

  }

  @Override
  public void setButtonListener(ActionListener actionEvent) {
    //This method is not needed by the mock to test the controller.

  }

  @Override
  public void setIsLoop(boolean loop) {
    log.append("LOOP\n");
  }

  @Override
  public void setLabel(String text) {
    log.append(text + "\n");

  }

  @Override
  public String getName() {
    return "";
  }

  @Override
  public String getShapeType() {
    return "Circle";
  }

  @Override
  public void setText() {
    log.append("action complete\n");
  }

  @Override
  public void updateText(LinkedHashMap<String, AShape> text, List<String> keyFrames) {
    //This method is not needed by the mock to test the controller.

  }

  @Override
  public void createListOfShapes(LinkedHashMap<String, AShape> text, List<String> keyFrames) {
    //This method is not needed by the mock to test the controller.

  }

  @Override
  public String getKeyFrameData() {
    return null;
  }

  @Override
  public void setKeyFrame() {
    //This method is not needed by the mock to test the controller.
  }

  @Override
  public String getKeyFramePosition() {
    return null;
  }

  @Override
  public String getKeyFrameDimension() {
    return null;
  }

  @Override
  public String getKeyFrameColor() {
    return null;
  }
}
