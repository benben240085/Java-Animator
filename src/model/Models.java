package model;

import cs3500.animator.util.AnimationBuilder;
import java.awt.Color;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import model.animation.IAnimation;
import shapes.AShape;

/**
 * Represents an interface of all the different types of models one can have for the simple
 * animator. A model that implements Models will utilize the methods in this interface to create
 * data that represents an animation. The purpose of any model implementation is to provide data to
 * the controller when prompted.
 */
public interface Models {
  /**
   * Creates a new shape of the desired type and adds it to the list of shapes and the log.
   * @param id desired id of the shape
   * @param type desired type of the shape
   * @throws IllegalArgumentException if the type is not a supported shape
   */
  void createShape(String id, String type);

  /**
   * Master method that modifies the shape in motion.
   * @param id id of the shape
   * @param initialX initial X of the shape at the start of the interval
   * @param initialY initial y of the shape at the start of the interval
   * @param finalX final x of the shape
   * @param finalY final y of the shape
   * @param width initial width of the shape at the start of the interval
   * @param height initial height of the shape at the start of the interval
   * @param finalWidth final width of the shape
   * @param finalHeight final height of the shape
   * @param r1 the red color aspect of the shape at the start of the interval
   * @param g1 the green color aspect of the shape at the start of the interval
   * @param b1 the blue color aspect of the shape at the start of the interval
   * @param r2 the red color aspect of the shape at the end of the interval
   * @param g2 the green color aspect of the shape at the end of the interval
   * @param b2 the blue color aspect of the shape at the end of the interval
   * @param startTick the first tick in the interval
   * @param endTick the last tick of the interval
   * @throws IllegalArgumentException when there is any instantaneous movement or changes
   */
  void detailChanges(String id, int initialX, int initialY, int finalX, int finalY, int width
      , int height, int finalWidth, int finalHeight, int r1, int g1, int b1, int r2, int g2, int b2,
      int startTick, int endTick);

  /**
   * Specify the bounding box to be used for the animation.
   * @param x The leftmost x value
   * @param y The topmost y value
   * @param width The width of the bounding box
   * @param height The height of the bounding box
   */
  void setBounds(int x, int y, int width, int height);

  /**
   * Adds an individual keyframe to the growing document.
   * @param name The name of the shape (added with {@link AnimationBuilder#declareShape})
   * @param t    The time for this keyframe
   * @param x    The x-position of the shape
   * @param y    The y-position of the shape
   * @param w    The width of the shape
   * @param h    The height of the shape
   * @param r    The red color-value of the shape
   * @param g    The green color-value of the shape
   * @param b    The blue color-value of the shape
   */
  void addKeyFrame(String name, int t, double x, double y, double w, double h, int r, int g, int b);

  /**
   * Retrieves a hashmap of all the shapes currently created in the model.
   * @return a LinkedHashMap containing all the shapes that are currently in the model.
   */
  LinkedHashMap<String, AShape> getShapes();

  /**
   * Retrieves a hashmap of all the motions that have currently been saved to the model.
   * @return A HashMap of all the motions and the ticks when they occur in the model.
   */
  HashMap<Integer, List<AShape>> getMotions();

  /**
   * Gets the board width of the model.
   * @return the model's board width.
   */
  int getBoardWidth();

  /**
   * Gets the board height of the model.
   * @return the model's board height.
   */
  int getBoardHeight();

  /**
   * Gets the x-bound of the model.
   * @return the model's x-bound.
   */
  int getX();

  /**
   * Gets the y-bound of the model.
   * @return the model's y-bound.
   */
  int getY();

  /**
   * Gets the color of the board's background in the model.
   * @return the color of the board's background.
   */
  Color getBoardColor();

  /**
   * Removes a shape from the list of shapes.
   * @param name the name of the shape to be removed.
   */
  void removeShape(String name);

  /**
   * Removes a key frame from the list of motions.
   * @param name the name of the shape to be removed
   * @param tick the tick of the motion
   */
  void removeKeyFrame(String name, int tick);

  /**
   * Gets the max key in a hashmap.
   * @return returns the maxKey
   */
  Integer maxKey();

  /**
   * Finds the min key in a list of animations of a specific shape.
   * @param animations the list of animations for a specific shape
   * @param max the max key of that shape
   * @return the min key for the specific shape
   */
  Integer minKey(List<IAnimation> animations, int max);

  /**
   * Finds the max key in a list of animations of a specific shape.
   * @param animations the list of animations for a specific shape
   * @return the max key for the specific shape
   */
  Integer maxKeys(List<IAnimation> animations);

  /**
   * Inserts a key frame but doesn't change any of the values of the keyFrame at that time.
   * @param name the name of the shape you want to add a keyFrame for
   * @param time the time that you want to add a keyFrame
   * @param animations a list of all animations for every shape
   */
  void insertKeyFrame(String name, int time, LinkedHashMap<String, List<IAnimation>> animations);

  /**
   * Edits a shape's features based on the given parameters from the user.
   * @param name the name of the shape
   * @param time the time the shape's parameters should be changed
   * @param x the new x value
   * @param y the new y value
   * @param w the new width
   * @param h the new height
   * @param color the new color
   */
  void editShape(String name, int time, Integer x, Integer y, int w, int h, Color color);
}
