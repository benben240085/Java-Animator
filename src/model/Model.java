package model;

import cs3500.animator.util.AnimationBuilder;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import model.animation.IAnimation;
import shapes.AShape;
import shapes.Circle;
import shapes.Ellipse;
import shapes.Rectangle;
import shapes.Shapes;

/**
 * Represents a collection of shapes and a board to be animated. The Model class has a width and
 * height field as well as an x and y field that determine the bounds of the board. It also has a
 * Color field that determines the background color of the board. To track the shapes and motions of
 * the animation, the Model contains two hashmaps, one that holds all the shapes and their ID's, and
 * another that holds all the motions and the shapes at the start and end of each motion.
 */
public final class Model implements Models {

  /**
   * Represents a builder class that builds our animation from the ground up when the reader is
   * called.
   */
  public static final class Builder implements AnimationBuilder<Models> {

    private Models model;

    public Builder() {
      this.model = new Model();
    }

    @Override
    public Models build() {
      return this.model;
    }

    @Override
    public AnimationBuilder<Models> setBounds(int x, int y, int width, int height) {
      model.setBounds(x, y, width, height);
      return this;
    }

    @Override
    public AnimationBuilder<Models> declareShape(String name, String type) {
      model.createShape(name, type);
      return this;
    }

    @Override
    public AnimationBuilder<Models> addMotion(String name, int t1, int x1, int y1, int w1, int h1,
        int r1, int g1, int b1, int t2, int x2, int y2, int w2, int h2, int r2, int g2, int b2) {
      model.detailChanges(name, x1, y1, x2, y2, w1, h1, w2, h2, r1, g1, b1, r2, g2, b2, t1,
          t2);
      return this;
    }

    @Override
    public AnimationBuilder<Models> addKeyframe(String name, int t, int x, int y, int w, int h,
        int r, int g, int b) {
      model.addKeyFrame(name, t, x, y, w, h, r, g, b);
      return this;
    }
  }

  private final LinkedHashMap<String, AShape> shapes;
  private final HashMap<Integer, List<AShape>> motions;
  private int boardWidth;
  private int boardHeight;
  private int x;
  private int y;
  private final Color boardColor;

  /**
   * Constructs a new model.Model with default parameters.
   */
  public Model() {
    this(500, 500, Color.white);
  }

  /**
   * Constructs a new model.Model with a custom board dimension.
   *
   * @param boardWidth  the desired board width
   * @param boardHeight the desired board height
   */
  public Model(int boardWidth, int boardHeight) {
    this(boardWidth, boardHeight, Color.white);
  }

  /**
   * Constructs a new model with a custom background color.
   *
   * @param color the desired background color.
   */
  public Model(Color color) {
    this(500, 500, color);
  }

  /**
   * Constructs a new model with custom dimemsions and color.
   *
   * @param boardWidth  the desired board width
   * @param boardHeight the desired board height
   * @param boardColor  the desired board color
   * @throws IllegalArgumentException when either of the dimensions of the board are less than zero
   */
  public Model(int boardWidth, int boardHeight, Color boardColor) {
    if (boardWidth < 0 || boardHeight < 0) {
      throw new IllegalArgumentException("Dimensions of the board must be greater than 0");
    }
    this.shapes = new LinkedHashMap<>();
    this.motions = new HashMap<>();
    this.boardWidth = boardWidth;
    this.boardHeight = boardHeight;
    this.x = 0;
    this.y = 0;
    this.boardColor = boardColor;
  }

  @Override
  public void createShape(String id, String type) {
    switch (type) {
      case "circle":
        AShape c = new Circle(0, 0, 0, Color.white, id);
        shapes.put(id, c);
        break;
      case "rectangle":
        AShape r = new Rectangle(0, 0, 0, 0, Color.white, id);
        shapes.put(id, r);
        break;
      case "ellipse":
        AShape e = new Ellipse(0, 0, 0, 0, Color.white, id);
        shapes.put(id, e);
        break;
      default:
        break;
    }
  }

  @Override
  public void detailChanges(String id, int initialX, int initialY, int finalX, int finalY, int width
      , int height, int finalWidth, int finalHeight, int r1, int g1, int b1, int r2, int g2, int b2,
      int startTick, int endTick)
      throws IllegalArgumentException {

    if (startTick == endTick) {
      if (initialX != finalX || initialY != finalY || width != finalWidth || height != finalHeight
          || r1 != r2 || g1 != g2 || b1 != b2) {
        throw new IllegalArgumentException("No instant changes");
      }
    }

    try {
      if (motions.get(startTick) != null) {
        for (int i = 0; i < motions.get(startTick).size(); i++) {
          if (motions.get(startTick).get(i).getId().equals(id)) {
            motions.get(startTick).get(i).checkTeleport(width, height, initialX, initialY, r1, g1,
                b1);
          }
        }
      }
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("Can't instantly make changes");
    }

    addMotionHelper(startTick, id, initialX, initialY, width, height, r1, g1, b1);
    addMotionHelper(endTick, id, finalX, finalY, finalWidth, finalHeight, r2, g2, b2);
  }

  //Helper that adds a new motion to the motion list if the tick does not exist in the hashmap,
  //otherwise adds to the existing motion at that tick.
  private void addMotionHelper(int tick, String id, int x, int y, int width, int height, int r,
      int g, int b) {
    if (motions.get(tick) == null) {
      List<AShape> list = new ArrayList<>();
      list.add(shapes.get(id).makeChangesWithoutBlend(id, x, y, width, height, new Color(r, g, b)));
      motions.put(tick, list);
    } else {
      motions.get(tick).add(shapes.get(id).makeChangesWithoutBlend(id, x, y, width, height,
          new Color(r, g, b)));
    }
  }

  /**
   * Checks if the given id is currently in the list of shapes.
   *
   * @param id   the ID to check
   * @param list the list to check
   * @return true if the ID is inside the given list
   */
  private boolean containsKey(String id, List<AShape> list) {
    boolean contains = false;
    for (int i = 0; i < list.size(); i++) {
      if (list.get(i).checkID(id)) {
        contains = true;
        break;
      }
    }
    return contains;
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof Models)) {
      return false;
    } else {
      Model that = (Model) obj;

      return this.boardWidth == that.boardWidth && this.boardHeight == that.boardHeight && this.x ==
          that.x && this.y == that.y && this.boardColor == that.boardColor;
    }
  }

  @Override
  public int hashCode() {
    return Objects.hash(shapes, motions, boardWidth, boardHeight, x, y, boardColor);
  }

  @Override
  public void setBounds(int x, int y, int width, int height) {
    this.boardWidth = width;
    this.boardHeight = height;
    this.x = x;
    this.y = y;
  }

  @Override
  public void addKeyFrame(String name, int t, double x, double y, double w, double h, int r, int g,
      int b) {
    addKeyFrameHelper(name, 0, x, y, w, h, r, g, b);
    addKeyFrameHelper(name, t, x, y, w, h, r, g, b);
  }

  private void addKeyFrameHelper(String name, int t, double x, double y, double w, double h, int r,
      int g, int b) {
    if (motions.get(t) == null) {
      List<AShape> newList = new ArrayList<AShape>();
      AShape newShape = shapes.get(name).makeChangesWithoutBlend(name, x, y, w, h, new Color(r, g,
          b));
      newList.add(newShape);
      motions.put(t, newList);
    } else {
      AShape newShape = shapes.get(name).makeChangesWithoutBlend(name, x, y, w, h, new Color(r, g,
          b));
      motions.get(t).add(newShape);
    }
  }

  @Override
  public LinkedHashMap<String, AShape> getShapes() {
    Iterator<Entry<String, AShape>> shapesIterator = this.shapes.entrySet().iterator();
    LinkedHashMap<String, AShape> newShapes = new LinkedHashMap<>();
    while (shapesIterator.hasNext()) {
      Map.Entry<String, AShape> entry = shapesIterator.next();
      newShapes.put(entry.getKey(), entry.getValue());
    }
    return newShapes;
  }

  @Override
  public HashMap<Integer, List<AShape>> getMotions() {
    HashMap<Integer, List<AShape>> newMotions = new HashMap<>();
    for (Integer key : motions.keySet()) {
      List<AShape> newList = new ArrayList<>();
      for (int i = 0; i < motions.get(key).size(); i++) {
        AShape newShape = motions.get(key).get(i).newShape();
        newList.add(newShape);
      }
      newMotions.put(key, newList);
    }
    return newMotions;
  }

  @Override
  public void editShape(String name, int time, Integer x, Integer y, int w, int h, Color color) {
    for (int i = 0; i < motions.get(time).size();
        i++) {
      if (motions.get(time).get(i).getId().equals(name)) {
        if (x != null) {
          motions.get(time).get(i).setX(x);
        }
        if (y != null) {
          motions.get(time).get(i).setY(y);
        }
        if (w != -1) {
          motions.get(time).get(i).setW(w);
        }
        if (h != -1) {
          motions.get(time).get(i).setH(h);
        }
        if (color != null) {
          motions.get(time).get(i).setColor(color);
        }
      }
    }
  }

  @Override
  public int getBoardWidth() {
    return this.boardWidth;
  }

  @Override
  public int getBoardHeight() {
    return this.boardHeight;
  }

  @Override
  public int getX() {
    return this.x;
  }

  @Override
  public int getY() {
    return this.y;
  }

  @Override
  public Color getBoardColor() {
    return new Color(boardColor.getRed(), boardColor.getGreen(), boardColor.getBlue());
  }

  @Override
  public void removeShape(String name) {
    Objects.requireNonNull(name);
    shapes.remove(name);
  }

  public boolean hasShape(int tick) {
    return this.motions.containsKey(tick);
  }

  @Override
  public void removeKeyFrame(String name, int tick) {
    for (int i = 0; i < motions.get(tick).size(); i++) {
      if (motions.get(tick).get(i).getId().equals(name)) {
        motions.get(tick).remove(motions.get(tick).get(i));
        motions.get(tick).remove(motions.get(tick).get(i));
      }
    }
  }

  @Override
  public Integer maxKey() {
    Integer maxK = 0;
    for (Integer key : motions.keySet()) {
      if (key > maxK) {
        maxK = key;
      }
    }
    return maxK;
  }

  @Override
  public Integer maxKeys(List<IAnimation> animations) {
    Integer maxK = 0;
    for (IAnimation key : animations) {
      if (key.getEnd() > maxK) {
        maxK = key.getEnd();
      }
    }
    return maxK;
  }

  @Override
  public Integer minKey(List<IAnimation> animations, int max) {
    int minK = max;
    for (IAnimation key : animations) {
      if (key.getStart() < minK) {
        minK = key.getStart();
      }
    }
    return minK;
  }

  @Override
  public void insertKeyFrame(String name, int time, LinkedHashMap<String,
      List<IAnimation>> animations) {
    for (String key : animations.keySet()) {
      if (animations.get(key).size() == 0) {
        Shapes shape = shapes.get(key);
        addKeyFrame(key, time, shape.getX(), shape.getY(), shape.getWidth(), shape.getHeight(),
            shape.getColor().getRed(), shape.getColor().getGreen(), shape.getColor().getBlue());
      } else {
        int max = maxKey();
        int min = minKey(animations.get(key), max);
        if (key.equals(name)) {
          if (time < min) {
            Shapes shape = animations.get(key).get(0).getShape();
            addKeyFrameHelper(name, time, 0, 0, 0, 0, 255, 255, 255);
            addKeyFrameHelper(name, min, shape.getX(), shape.getY(), shape.getWidth(),
                shape.getHeight(), shape.getColor().getRed(), shape.getColor().getGreen(),
                shape.getColor().getBlue());
          } else if (time > max) {
            int size = animations.get(key).size();
            Shapes shape = animations.get(key).get(size - 1).getShape();
            addKeyFrameHelper(name, time, 0, 0, 0, 0, 255, 255, 255);
            addKeyFrameHelper(name, max, shape.getX(), shape.getY(), shape.getWidth(),
                shape.getHeight(), shape.getColor().getRed(), shape.getColor().getGreen(),
                shape.getColor().getBlue());
          }
          for (IAnimation current : animations.get(key)) {
            if (time < current.getEnd() && time > current.getStart()) {
              insertKeyFrameHelper(current, name, time);
            }
          }
        }
      }
    }
  }

  private void insertKeyFrameHelper(IAnimation current, String name, int time) {
    double originalX = current.getShape().getX();
    double originalY = current.getShape().getY();
    double originalW = current.getShape().getWidth();
    double originalH = current.getShape().getHeight();
    Color originalColor = current.getShape().getColor();

    current.animate(time);
    double newX = current.getShape().getX();
    double newY = current.getShape().getY();
    double newW = current.getShape().getWidth();
    double newH = current.getShape().getHeight();
    Color newColor = current.getShape().getColor();
    int newR = current.getShape().getColor().getRed();
    int newG = current.getShape().getColor().getGreen();
    int newB = current.getShape().getColor().getBlue();
    addKeyFrameHelper(name, time, newX, newY, newW, newH, newR, newG, newB);
    addKeyFrameHelper(name, time, newX, newY, newW, newH, newR, newG, newB);
    current.getShape().setX(originalX);
    current.getShape().setY(originalY);
    current.getShape().setW(originalW);
    current.getShape().setH(originalH);
    current.getShape().setColor(originalColor);
  }
}
