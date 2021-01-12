package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.io.PrintStream;
import java.util.LinkedHashMap;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import shapes.AShape;
import shapes.Shapes;

/**
 * Represents the visual view which sets the bounding box and the window for the panel and the
 * graphics and animations to be displayed.
 */
public class VisualView extends JFrame implements IView {

  private AnimationPanel motionPanel;

  /**
   * Constructs a visual view object.
   * @param x topmost x value of the board
   * @param y leftmost y value of the board
   * @param boardWidth the width of the board
   * @param boardHeight the height of the board
   */
  public VisualView(int x, int y, int boardWidth, int boardHeight) {
    super();
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setSize(boardWidth, boardHeight);
    this.setLocation(x, y);
    this.setResizable(false);
    motionPanel = new AnimationPanel();
    motionPanel.setMinimumSize(new Dimension(boardWidth, boardHeight));
    motionPanel.setPreferredSize(new Dimension(boardWidth, boardHeight));
    this.motionPanel.setBackground(Color.WHITE);
    this.add(motionPanel);
    JScrollPane scrollPane = new JScrollPane(motionPanel);
    scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    this.add(scrollPane);
  }

  public AnimationPanel getMotionPanel() {
    return this.motionPanel;
  }

  @Override
  public void setShapes(List<Shapes> shapes) {
    motionPanel.setShapes(shapes);
  }

  @Override
  public void setOffset(int boardX, int boardY) {
    motionPanel.setOffset(boardX, boardY);
  }

  @Override
  public void makeVisible() {
    this.setVisible(true);
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
  public void refresh() {
    repaint();
  }

  @Override
  public void sendToOutput(PrintStream out) {
    throw new UnsupportedOperationException("This view doesn't support this method");
  }

  @Override
  public void svgColor(int t1, int duration, int r1, int g1, int b1, AShape shape, int ticks) {
    throw new UnsupportedOperationException("This view doesn't support this method");
  }

  @Override
  public void animate(int boardWidth, int boardHeight, int max, int ticks) {
    throw new UnsupportedOperationException("This view doesn't support this method");
  }

  @Override
  public void createShape(String id, int x1, int y1, int w1, int h1, int r1, int g1, int b1,
      String type, int boardX, int boardY) {
    throw new UnsupportedOperationException("This view doesn't support this method");
  }

  @Override
  public void svgShape(int t1, int duration, String attribute, int from, int to, String fill,
      int ticks) {
    throw new UnsupportedOperationException("This view doesn't support this method");
  }

  @Override
  public void closing(String id, String type) {
    throw new UnsupportedOperationException("This view doesn't support this method");
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
  public String getShapeType() {
    throw new UnsupportedOperationException("This view doesn't support this method");
  }

  @Override
  public String getName() {
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
}
