package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.io.PrintStream;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import shapes.AShape;
import shapes.Shapes;

/**
 * A class that extends JFrame and constructs the size and location of the frame based on the input
 * in the file. Has an instance of a visualView in order to use the decorator pattern to implement
 * some of the methods in this class. This class represents a VisualView that also has the
 * functionality to edit things through the use of the buttons and textFields that are added as
 * components onto the JFrame using layout managers.
 **/
public class EditView extends JFrame implements IView {

  private final VisualView baseView;
  private JButton startButton;
  private JButton pauseButton;
  private JButton resumeButton;
  private JButton restartButton;
  private JButton increaseSpeedButton;
  private JButton decreaseSpeedButton;
  private JButton createShapeButton;
  private JButton deleteShapeButton;
  private JButton deleteFrameButton;
  private JButton insertFrameButton;
  private JButton editFrameButton;
  private JTextArea shapeName;
  private JTextArea shapeType;
  private JTextArea keyFrame;
  private JTextArea keyFramePosition;
  private JTextArea keyFrameDimension;
  private JTextArea keyFrameColor;
  private JCheckBox loopButton;
  private JLabel visualCues;
  private JScrollPane shapeListScrollPane;
  private JLabel shapes;
  private boolean isLoop;

  /**
   * A constructor for a new EditView. It takes all the data that a visual view would require as
   * well as two linked hashmaps that contain animation data.
   *
   * @param x           the x cordinate of the bounding box
   * @param y           the y coordinate of the bounding box
   * @param boardWidth  the board width
   * @param boardHeight the board height
   * @param text        a linked hashmap that contains animation data for each shape
   * @param keyFrames   a linked hashmap that contains keyframe data for the animation
   */
  public EditView(int x, int y, int boardWidth, int boardHeight, LinkedHashMap<String, AShape>
          text, List<String> keyFrames) {
    baseView = new VisualView(x, y, boardWidth, boardHeight);

    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setSize(boardWidth, boardHeight);
    this.setLocation(x, y);
    this.setResizable(false);

    JScrollPane scrollPane = new JScrollPane(baseView.getMotionPanel());
    scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    this.add(scrollPane);

    this.add(baseView.getMotionPanel());
    JPanel buttonPanel;
    JPanel insertOrDeletePanel;
    JPanel editPanel;
    buttonPanel = new JPanel();
    insertOrDeletePanel = new JPanel();
    editPanel = new JPanel();
    buttonPanel.setLayout(new FlowLayout());
    insertOrDeletePanel.setLayout(new BoxLayout(insertOrDeletePanel, BoxLayout.X_AXIS));
    editPanel.setLayout(new BoxLayout(editPanel, BoxLayout.Y_AXIS));
    this.add(buttonPanel, BorderLayout.SOUTH);
    this.add(insertOrDeletePanel, BorderLayout.NORTH);
    this.add(editPanel, BorderLayout.WEST);

    startButton = new JButton("START");
    buttonPanel.add(startButton);

    resumeButton = new JButton("RESUME");
    buttonPanel.add(resumeButton);

    pauseButton = new JButton("PAUSE");
    buttonPanel.add(pauseButton);
    restartButton = new JButton("RESTART");
    buttonPanel.add(restartButton);

    loopButton = new JCheckBox("LOOP");
    buttonPanel.add(loopButton);

    increaseSpeedButton = new JButton("INCREASE SPEED");
    buttonPanel.add(increaseSpeedButton);

    decreaseSpeedButton = new JButton("DECREASE SPEED");
    buttonPanel.add(decreaseSpeedButton);

    shapeName = new JTextArea(1, 3);
    shapeName.setBorder(BorderFactory.createTitledBorder("Shape Name"));
    insertOrDeletePanel.add(shapeName);

    shapeType = new JTextArea(1, 3);
    shapeType.setBorder(BorderFactory.createTitledBorder("Shape Type"));
    insertOrDeletePanel.add(shapeType);

    createShapeButton = new JButton("Create");
    insertOrDeletePanel.add(createShapeButton);

    deleteShapeButton = new JButton("Delete");
    insertOrDeletePanel.add(deleteShapeButton);

    keyFrame = new JTextArea(1, 1);
    keyFrame.setBorder(BorderFactory.createTitledBorder("KeyFrame(t)"));
    insertOrDeletePanel.add(keyFrame);

    insertFrameButton = new JButton("Insert");
    insertOrDeletePanel.add(insertFrameButton);

    deleteFrameButton = new JButton("Remove");
    insertOrDeletePanel.add(deleteFrameButton);

    visualCues = new JLabel("");
    insertOrDeletePanel.add(visualCues);

    keyFramePosition = new JTextArea(1, 6);
    keyFramePosition.setBorder(BorderFactory.createTitledBorder("KeyFrame(x,y)"));
    editPanel.add(keyFramePosition);

    keyFrameDimension = new JTextArea(1, 5);
    keyFrameDimension.setBorder(BorderFactory.createTitledBorder("KeyFrame(w,h)"));
    editPanel.add(keyFrameDimension);

    keyFrameColor = new JTextArea(1, 5);
    keyFrameColor.setBorder(BorderFactory.createTitledBorder("KeyFrame(r,g,b)"));
    editPanel.add(keyFrameColor);

    editFrameButton = new JButton("Edit");
    editPanel.add(editFrameButton);

    this.isLoop = false;
    createListOfShapes(text, keyFrames);
    this.pack();
  }

  @Override
  public void createListOfShapes(LinkedHashMap<String, AShape> text, List<String> keyFrames) {
    if (shapeListScrollPane != null) {
      this.remove(shapeListScrollPane);
    }
    JPanel shapeListPanel = new JPanel();
    shapeListPanel.setLayout(new BoxLayout(shapeListPanel, BoxLayout.Y_AXIS));
    JLabel shapeListLabel = new JLabel("");
    shapeListPanel.add(shapeListLabel);

    this.updateText(text, keyFrames);

    Iterator<Entry<String, AShape>> shapesIterator = text.entrySet().iterator();
    StringBuilder shape = new StringBuilder();
    shape.append(String.format("<html>Shapes present in <br>this animation<br><br>"));
    while (shapesIterator.hasNext()) {
      shape.append(String.format("shape %s<br>", shapesIterator.next().getKey()));
    }

    for (int i = 0; i < keyFrames.size(); i++) {
      shape.append(String.format("%s<br>", keyFrames.get(i)));
    }
    shape.append(String.format("</html>"));
    shapeListLabel.setText(shape.toString());
    shapeListScrollPane = new JScrollPane(shapeListPanel);
    shapeListScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    shapeListScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    shapeListScrollPane.setBounds(50, 30, 50, 500);
    shapeListScrollPane.setPreferredSize(new Dimension(140, 200));
    this.add(shapeListScrollPane, BorderLayout.EAST);
    this.shapeListScrollPane.repaint();
  }

  @Override
  public void updateText(LinkedHashMap<String, AShape> text, List<String> keyFrames) {
    this.text = text;
    this.keyFrames = keyFrames;
  }

  @Override
  public void setLabel(String text) {
    visualCues.setText(text);
  }

  @Override
  public void makeVisible() {
    this.setVisible(true);
  }

  @Override
  public String getName() {
    return shapeName.getText();
  }

  @Override
  public String getShapeType() {
    return shapeType.getText();
  }

  @Override
  public String getKeyFrameData() {
    return keyFrame.getText();
  }

  @Override
  public String getKeyFramePosition() {
    return keyFramePosition.getText();
  }

  @Override
  public String getKeyFrameDimension() {
    return keyFrameDimension.getText();
  }

  @Override
  public String getKeyFrameColor() {
    return keyFrameColor.getText();
  }

  @Override
  public void setKeyFrame() {
    keyFrame.setText("");
    keyFramePosition.setText("");
    keyFrameDimension.setText("");
    keyFrameColor.setText("");
  }

  @Override
  public void setText() {
    shapeName.setText("");
    shapeType.setText("");
  }

  @Override
  public void setButtonListener(ActionListener actionEvent) {
    startButton.addActionListener(actionEvent);
    resumeButton.addActionListener(actionEvent);
    pauseButton.addActionListener(actionEvent);
    restartButton.addActionListener(actionEvent);
    increaseSpeedButton.addActionListener(actionEvent);
    decreaseSpeedButton.addActionListener(actionEvent);
    loopButton.addActionListener(actionEvent);
    createShapeButton.addActionListener(actionEvent);
    deleteShapeButton.addActionListener(actionEvent);
    insertFrameButton.addActionListener(actionEvent);
    deleteFrameButton.addActionListener(actionEvent);
    editFrameButton.addActionListener(actionEvent);
  }

  @Override
  public void setIsLoop(boolean loop) {
    this.isLoop = loop;
  }

  @Override
  public StringBuilder initialize(int x, int y, int width, int height) {
    throw new UnsupportedOperationException("This method isn't supported for this view");
  }

  @Override
  public void create(String name, String type, int t1, int x1, int y1, int w1, int h1, int r1,
                     int g1, int b1, int t2, int x2, int y2, int w2, int h2, int r2, int g2,
      int b2) {
    throw new UnsupportedOperationException("This method isn't supported for this view");
  }

  @Override
  public StringBuilder result() {
    throw new UnsupportedOperationException("This method isn't supported for this view");
  }

  @Override
  public void setShapes(List<Shapes> shapes) {
    baseView.setShapes(shapes);
  }

  @Override
  public void setOffset(int boardX, int boardY) {
    this.baseView.setOffset(boardX, boardY);
  }

  @Override
  public void refresh() {
    this.repaint();
  }

  @Override
  public void sendToOutput(PrintStream out) {
    throw new UnsupportedOperationException("This method isn't supported for this view");
  }

  @Override
  public void svgColor(int t1, int duration, int r1, int g1, int b1, AShape shape, int ticks) {
    throw new UnsupportedOperationException("This method isn't supported for this view");
  }

  @Override
  public void animate(int boardWidth, int boardHeight, int max, int ticks) {
    throw new UnsupportedOperationException("This method isn't supported for this view");
  }

  @Override
  public void createShape(String id, int x1, int y1, int w1, int h1, int r1, int g1, int b1,
                          String type, int boardX, int boardY) {
    throw new UnsupportedOperationException("This method isn't supported for this view");
  }

  @Override
  public void svgShape(int t1, int duration, String attribute, int from, int to, String fill,
                       int ticks) {
    throw new UnsupportedOperationException("This method isn't supported for this view");
  }

  @Override
  public void closing(String id, String type) {
    throw new UnsupportedOperationException("This method isn't supported for this view");
  }
}
