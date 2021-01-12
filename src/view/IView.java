package view;

import java.awt.event.ActionListener;
import java.io.PrintStream;
import java.util.LinkedHashMap;
import java.util.List;
import shapes.AShape;
import shapes.Shapes;

/**
 * An interface that represents a view. The purpose of any class implementing IView is to display
 * the data passed to it by the controller. The method provided in this interface makes the view
 * visible.
 */
public interface IView {
  /**
   * Make the view visible. This is usually called after the view is constructed.
   */
  void makeVisible();

  /**
   * Initializes the Header of the textualView. Only works in the textualView.
   *
   * @return a StringBuilder that contains a new header
   */
  StringBuilder initialize(int x, int y, int width, int height);

  /**
   * Appends a new motion of the specified shape to the shape's log in the motions hashmap. If the
   * shape does not yet exist it creates a new key in the map. Only works in the textualView.
   * @param name The ID of the shape
   * @param type the type of the shape
   * @param t1 the first tick of the motion
   * @param x1 the initial x coordinate of the shape
   * @param y1 the initial y coordinate of the shape
   * @param w1 the initial width of the shape
   * @param h1 the initial height of the shape
   * @param r1 the initial r value of the color of the shape
   * @param g1 the initial green value of the color of the shape
   * @param b1 the initial blue value of the color of the shape
   * @param t2 the final tick of the motion
   * @param x2 the final x coordinate of the motion
   * @param y2 the final y coordinate of the motion
   * @param w2 the final width of the motion
   * @param h2 the final height of the motion
   * @param r2 the final red value of the color of the motion
   * @param g2 the final green value of the color of the motion
   * @param b2 the final blue value of the color of the motion
   */
  void create(String name, String type, int t1, int x1, int y1, int w1, int h1, int r1, int
      g1, int b1, int t2, int x2, int y2, int w2, int h2, int r2, int g2, int b2);

  /**
   * Appends the StringBuilder of each individual shape to the final output. Only works in the
   * textualView.
   *
   * @return the final output
   */
  StringBuilder result();

  /**
   * Passes in the given list of shapes which should be drawn at that tick to AnimationPanel.
   * Only supported with VisualView.
   * @param shapes the given list of shapes to be drawn
   */
  void setShapes(List<Shapes> shapes);

  /**
   * Sets the offset of the board with the given offsets. Only supported with VisualView.
   * @param boardX the offset of the x value of the board
   * @param boardY the offset of the y value of the board
   */
  void setOffset(int boardX, int boardY);

  /**
   * Repaints the view. Only supported for the visual view.
   */
  void refresh();

  /**
   * Writes the file and calls the final closing tag for the svg. Only supported with SvgView.
   * @param out the printstream to be written to.
   */
  void sendToOutput(PrintStream out);

  /**
   * Appends a line to output that animates the color attribute of the specified shape. Only
   * supported with SvgView.
   * @param t1 the start time of the animation
   * @param duration the duration of the animation
   * @param r1 the initial red value of the shape's color
   * @param g1 the initial green value of the shape's color
   * @param b1 the initial blue value of the shape's color
   * @param shape the shape to be animated
   * @param ticks the number of ticks in the interval
   */
  void svgColor(int t1, int duration, int r1, int g1, int b1, AShape shape, int ticks);

  /**
   * Appends lines that define the canvas width and height for the svg, as well as a rectangle that
   * will serve as a marker for future shape movements. Only supported with SvgView.
   * @param boardWidth the desired board width
   * @param boardHeight the desired board height
   * @param max the last tick that contains a motion
   * @param ticks the number of ticks in the motion
   */
  void animate(int boardWidth, int boardHeight, int max, int ticks);

  /**
   * Appends a line to output that defines a new shape in the animation. Only
   * supported with SvgView.
   * @param id the id of the shape
   * @param x1 the x value of the shape
   * @param y1 the y value of the shape
   * @param w1 the width of the shape
   * @param h1 the height of the shape
   * @param r1 the red value of the color of the shape
   * @param g1 the green value of the color of the shape
   * @param b1 the blue value of the color of the shape
   * @param type the type of the shape
   * @param boardX the board x bound
   * @param boardY the board y bound
   */
  void createShape(String id, int x1, int y1, int w1, int h1, int r1, int g1,
      int b1, String type, int boardX, int boardY);

  /**
   * Appends a line to output that defines a new animation of the given shape's specified attribute
   * over the specified time interval. Only supported with SvgView.
   * @param t1 the start interval
   * @param duration the duration of time that the transition should take
   * @param attribute the attribute that is being animated over the interval
   * @param from the initial attribute value
   * @param to the final attribute value
   * @param fill a tag that defines the "fill" attribute in the svg line
   * @param ticks the number of ticks in the interval
   */
  void svgShape(int t1, int duration, String attribute, int from, int to, String fill,
      int ticks);

  /**
   * Appends a line to output that closes the shape. Called once all animations for that shape
   * have been added. Only supported with SvgView.
   * @param id the ID of the shape
   * @param type the type of the shape
   */
  void closing(String id, String type);

  /**
   * Provide the editView with an actionListener for the buttons in the view. Only supported for
   * the editView.
   *
   * @param actionEvent The actionEvent for the button
   * @throws UnsupportedOperationException if the view does not need the functionality
   */
  void setButtonListener(ActionListener actionEvent);

  /**
   * Sets the boolean isLoop in the view.
   *
   * @param loop boolean to set isLoop to
   * @throws UnsupportedOperationException if the view does not need the functionality
   */
  void setIsLoop(boolean loop);

  /**
   * Sets the text of the label to the given text.
   * @param text the text for the label
   */
  void setLabel(String text);

  /**
   * Gets the name from the name text field.
   * @return the text from the name text field
   */
  String getName();

  /**
   * Gets the type from the type text field.
   * @return the text from the type text field
   */
  String getShapeType();

  /**
   * Sets the text of the text fields.
   */
  void setText();

  /**
   * Updates the list of shapes present in the animation.
   * @param text the list of shapes present in the animation
   */
  void updateText(LinkedHashMap<String, AShape> text, List<String> keyFrames);

  /**
   * Updates the panel that displays the list of shapes present and the keyFrames for each shape.
   */
  void createListOfShapes(LinkedHashMap<String, AShape> text, List<String> keyFrames);

  /**
   * Gets all the data for the keyFrame.
   * @return the data for the keyFrame
   */
  String getKeyFrameData();

  /**
   * Resets the text for the keyFrame.
   */
  void setKeyFrame();

  /**
   * Gets the x and y values of the key frame.
   * @return the x and y values of the key frame
   */
  String getKeyFramePosition();

  /**
   * Gets the width and height values of the key frame.
   * @return the width and height values of the key frame
   */
  String getKeyFrameDimension();

  /**
   * Gets the rgb values of the key frame.
   * @return the rgb values of the key frame
   */
  String getKeyFrameColor();
}
