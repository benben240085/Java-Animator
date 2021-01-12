package view;

import java.awt.event.ActionListener;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import shapes.AShape;
import shapes.Shapes;

/**
 * A class that displays the provided data in the form of a textual grid. It Implements IView.
 * It has a StringBuilder that is appended to over time that will eventually be returned. It also
 * contains a hashmap of motions that relates shapes to their motions described as text.
 */
public class TextualView extends JFrame implements IView {

  StringBuilder output;
  HashMap<String, StringBuilder> motions;
  JLabel jl;
  JPanel drawingPanel;
  JScrollPane scrollPane;

  /**
   * Constructs a new textualView with a panel to draw on, a scroll bar if the text exceeds the
   * bounding box set by the user, and an empty hashmap.
   */
  public TextualView() {
    this.output = new StringBuilder();
    this.motions = new HashMap<>();
    jl = new JLabel();
    drawingPanel = new JPanel();
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setResizable(false);
    scrollPane = new JScrollPane(drawingPanel);
    this.add(scrollPane);
  }

  @Override
  public StringBuilder initialize(int x, int y, int width, int height) {
    output.append(String.format("<html>canvas %d %d %d %d<br>", x, y, width, height));
    setSize(width, height);
    setLocation(x, y);
    return output;
  }

  @Override
  public void create(String name, String type, int t1, int x1, int y1, int w1, int h1, int r1, int
      g1, int b1, int t2, int x2, int y2, int w2, int h2, int r2, int g2, int b2) {
    StringBuilder result = new StringBuilder();
    result.append("shape " + name + " " + type + "<br>");
    String format = String.format("motion %s %-3d %-3d %-3d %-3d %-3d %-3d %-3d "
            + "%-3d    %-3d %-3d %-3d %-3d %-3d %-3d %-3d %-3d<br>", name, t1, x1, y1, w1, h1, r1,
        g1, b1, t2, x2, y2, w2, h2, r2, g2, b2);
    if (motions.get(name) == null) {
      motions.put(name, result.append(format));
    } else {
      motions.get(name).append(format);
    }
  }

  @Override
  public StringBuilder result() {
    for (String motion: motions.keySet()) {
      output.append(motions.get(motion) + "\n");
    }
    output.append("</html>");
    jl.setText(output.toString());
    drawingPanel.add(jl);
    return output;
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

  @Override
  public void makeVisible() {
    this.setVisible(true);
  }
}
