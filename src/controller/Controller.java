package controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.swing.Timer;
import model.Models;
import model.animation.Animations;
import model.animation.IAnimation;
import shapes.AShape;
import shapes.Circle;
import shapes.Ellipse;
import shapes.Rectangle;
import shapes.Shapes;
import view.EditView;
import view.IView;
import view.MockView;
import view.SvgView;
import view.TextualView;
import view.VisualView;


/**
 * A Controller object that implements IController. It is constructed with a view that implements
 * IView and a model that implements Models. The purpose of this class is to pass model data to the
 * View and call methods in the view that display the final output. It also contains a PrintStream
 * that is passed to svgView.
 */
public class Controller implements IController, ActionListener {

  private Timer timer;
  private final Models model;
  private final IView view;
  private int ticksPerSec;
  private final PrintStream out;
  private int currentTick = 0;
  private LinkedHashMap<String, List<IAnimation>> animations;
  private boolean isLoop;
  private List<String> keyFrames;
  private int x = 0;
  private int y = 0;
  private int w = 0;
  private int h = 0;
  private int r = 0;
  private int g = 0;
  private int b = 0;
  private StringBuilder log;

  /**
   * A constructor for a new Controller. It takes in a model, view and a PrintStream.
   *
   * @param model    a model that extends Models, the data will be generated from this model
   * @param viewType a type of view that tells the controller which constructor to refer to.
   * @param out      a PrintStream to be used with svgView that is appended to the output.
   */
  public Controller(Models model, String viewType, int ticksPerSec, PrintStream out) {

    this.model = model;
    this.log = new StringBuilder();
    this.ticksPerSec = ticksPerSec;
    this.out = out;
    this.isLoop = false;

    if (viewType.equals("text")) {
      view = new TextualView();
    } else if (viewType.equals("svg")) {
      view = new SvgView();
    } else if (viewType.equals("visual")) {
      view = new VisualView(model.getX(), model.getY(), model.getBoardWidth(),
          model.getBoardHeight());
      animations = start();
      view.setOffset(model.getX(), model.getY());
    } else if (viewType.equals("edit")) {
      animations = start();
      view = new EditView(model.getX(), model.getY(), model.getBoardWidth(),
          model.getBoardHeight(), model.getShapes(), keyFrameList(animations));
      view.setOffset(model.getX(), model.getY());
    } else if (viewType.equals("Mock")) {
      view = new MockView(log);
      timer = new Timer(1, null);
    }
    else {
      view = null;
    }

  }

  @Override
  public void animate() {

    this.view.setButtonListener(this);

    if (model == null) {
      throw new IllegalArgumentException("The provided model can't be null");
    }

    if (view instanceof TextualView) {
      view.initialize(model.getX(), model.getY(), model.getBoardWidth(),
          model.getBoardHeight());
      createHelper(model.getShapes(), model.getMotions());
      view.result();
      view.makeVisible();
    } else if (view instanceof SvgView) {
      int max = model.maxKey();
      view.animate(model.getBoardWidth(), model.getBoardHeight(), max, ticksPerSec);
      svgAddShapeHelper(model.getShapes(), model.getMotions());
      view.sendToOutput(out);
    } else if (view instanceof VisualView) {
      timer = new Timer(1000 / ticksPerSec, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          currentTick++;
          List<Shapes> shapes = new ArrayList<>();
          Iterator<Entry<String, List<IAnimation>>> animationsIterator =
              animations.entrySet().iterator();
          while (animationsIterator.hasNext()) {
            List<IAnimation> currentAnimation = animationsIterator.next().getValue();
            for (int i = 0; i < currentAnimation.size(); i++) {
              if (currentTick >= currentAnimation.get(i).getStart() &&
                  currentTick <= currentAnimation.get(i).getEnd()) {
                currentAnimation.get(i).animate(currentTick);
                shapes.add(currentAnimation.get(i).getShape());
              }
            }
          }
          view.setShapes(shapes);
          view.refresh();
        }
      });
      timer.start();
      view.makeVisible();
    } else if (view instanceof EditView) {
      timer = new Timer(1000 / getSpeed(), new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          int lastFrame = model.maxKey();
          if (isLoop && (lastFrame - currentTick < 0.000001)) {
            currentTick = 0;

            timer.restart();
          } else if (lastFrame - currentTick < 0.000001) {
            timer.stop();
          }

          currentTick++;
          List<Shapes> shapes = new ArrayList<>();
          Iterator<Entry<String, List<IAnimation>>> animationsIterator =
              animations.entrySet().iterator();
          while (animationsIterator.hasNext()) {
            List<IAnimation> currentAnimation = animationsIterator.next().getValue();
            for (int i = 0; i < currentAnimation.size(); i++) {
              if (currentTick >= currentAnimation.get(i).getStart() &&
                  currentTick <= currentAnimation.get(i).getEnd()) {
                currentAnimation.get(i).animate(currentTick);
                shapes.add(currentAnimation.get(i).getShape());
              }
            }
          }
          view.setShapes(shapes);
          view.refresh();
        }
      });
      view.makeVisible();
    }
  }

  private List<String> keyFrameList(LinkedHashMap<String, List<IAnimation>> animations) {
    List<String> keyFrames = new ArrayList<>();

    for (String key: animations.keySet()) {
      keyFrames.add("Shape " + key + "'s keyFrames:");
      for (IAnimation animation: animations.get(key)) {
        keyFrames.add(animation.getStart() + "");
        keyFrames.add(animation.getEnd() + "");
      }
    }
    this.keyFrames = keyFrames;
    return this.keyFrames;
  }

  private LinkedHashMap<String, List<IAnimation>> start() {
    HashMap<Integer, List<AShape>> animations = model.getMotions();
    LinkedHashMap<String, AShape> shapes = model.getShapes();

    return visualAddShapeHelper(shapes, animations);
  }

  //Helper method that passes the motions hashmap from the model to svgView as well as
  //the type and id of the shape involved in the motion.
  private LinkedHashMap<String, List<IAnimation>> visualAddShapeHelper(LinkedHashMap<String, AShape>
      shapes, HashMap<Integer, List<AShape>> motion) {
    String type;
    String id;
    List<IAnimation> animationList;
    LinkedHashMap<String, List<IAnimation>> animations = new LinkedHashMap<>();
    Iterator<Entry<String, AShape>> shapesIterator = shapes.entrySet().iterator();
    while (shapesIterator.hasNext()) {
      Map.Entry<String, AShape> entry = shapesIterator.next();
      if (entry.getValue() instanceof Circle) {
        type = "circle";
        id = entry.getValue().getId();
        animationList = visualAnimate(motion, type, id);
        animations.put(id, animationList);
      } else if (entry.getValue() instanceof Rectangle) {
        type = "rect";
        id = entry.getValue().getId();
        animationList = visualAnimate(motion, type, id);
        animations.put(id, animationList);
      } else if (entry.getValue() instanceof Ellipse) {
        type = "ellipse";
        id = entry.getValue().getId();
        animationList = visualAnimate(motion, type, id);
        animations.put(id, animationList);
      }
    }
    return animations;
  }

  //Helper method that sets the variables of the shapes passed based on the motion hashmap, and
  //passes this information to a second helper that calls the methods in svgView that
  //append these motions to the file.
  private List<IAnimation> visualAnimate(HashMap<Integer, List<AShape>> motion, String type,
      String id) {
    int t1 = 0;
    double x1 = 0;
    double y1 = 0;
    double w1 = 0;
    double h1 = 0;
    int r1 = 0;
    int g1 = 0;
    int b1 = 0;
    boolean init = false;
    AShape start = null;
    int max = model.maxKey();
    List<IAnimation> animationList = new ArrayList<>();
    for (int i = 0; i <= max; i++) {
      if (motion.get(i) == null) {
        continue;
      } else {
        Iterator<AShape> itr = motion.get(i).iterator();
        while (itr.hasNext()) {
          AShape shape = itr.next();
          if (id.equals(shape.getId()) && !init) {
            start = shape;
            init = true;
            t1 = i;
            x1 = shape.getX();
            y1 = shape.getY();
            w1 = shape.getWidth();
            h1 = shape.getHeight();
            r1 = shape.getColor().getRed();
            g1 = shape.getColor().getGreen();
            b1 = shape.getColor().getBlue();
          } else if (id.equals(shape.getId()) && init) {
            init = false;
            if (i == t1) {
              continue;
            } else {
              IAnimation newAnimation = new Animations(start, t1, i, new Color(r1, g1, b1),
                  shape.getColor(), x1, y1, shape.getX(), shape.getY(), w1, h1, shape.getWidth(),
                  shape.getHeight());
              animationList.add(newAnimation);
            }
            itr.remove();
          }
        }
      }
    }
    return animationList;
  }

  //Helper method that passes the motions hashmap from the model to svgView as well as
  //the type and id of the shape involved in the motion.
  private void svgAddShapeHelper(LinkedHashMap<String, AShape> shapes,
      HashMap<Integer, List<AShape>> motion) {
    String type;
    String id;
    for (Map.Entry<String, AShape> entry : shapes.entrySet()) {
      if (entry.getValue() instanceof Circle) {
        type = "circle";
        id = entry.getValue().getId();
        svgAnimate(motion, type, id);
      } else if (entry.getValue() instanceof Rectangle) {
        type = "rect";
        id = entry.getValue().getId();
        svgAnimate(motion, type, id);
      } else if (entry.getValue() instanceof Ellipse) {
        type = "ellipse";
        id = entry.getValue().getId();
        svgAnimate(motion, type, id);
      }
    }
  }

  //Helper method that sets the variables of the shapes passed based on the motion hashmap, and
  //passes this information to a second helper that calls the methods in svgView that
  //append these motions to the file.
  private void svgAnimate(HashMap<Integer, List<AShape>> motion, String type, String id) {
    int t1 = 0;
    int x1 = 0;
    int y1 = 0;
    int w1 = 0;
    int h1 = 0;
    int r1 = 0;
    int g1 = 0;
    int b1 = 0;
    boolean init = false;
    boolean first = true;
    int max = model.maxKey();
    for (int i = 0; i <= max; i++) {
      if (motion.get(i) == null) {
        continue;
      } else {
        Iterator<AShape> itr = motion.get(i).iterator();
        while (itr.hasNext()) {
          AShape shape = itr.next();
          if (id.equals(shape.getId()) && !init) {
            init = true;
            t1 = i;
            x1 = (int) shape.getX();
            y1 = (int) shape.getY();
            w1 = (int) shape.getWidth();
            h1 = (int) shape.getHeight();
            r1 = shape.getColor().getRed();
            g1 = shape.getColor().getGreen();
            b1 = shape.getColor().getBlue();
          } else if (id.equals(shape.getId()) && init && first) {
            init = false;
            sameAsStartingValues(shape, true, i, t1, x1, y1, w1, h1, r1, g1, b1, id, type);
            first = false;
            itr.remove();
          } else if (id.equals(shape.getId()) && init && !first) {
            init = false;
            sameAsStartingValues(shape, false, i, t1, x1, y1, w1, h1, r1, g1, b1, id, type);
            itr.remove();
          }
        }
      }
      if (i == max) {
        view.closing(id, type);
      }
    }
  }

  //Checks if the input is the first set of information in the motion (pre-animation), and either
  //creates a new motion or passes the data to a third helper that appends to an existing motion.
  private void sameAsStartingValues(AShape shape, boolean first, int t2, int t1, int x1, int y1, int
      w1, int h1, int r1, int g1, int b1, String id, String type) {
    int duration = t2 - t1;
    if (first) {
      view.createShape(id, x1, y1,
          w1, h1, r1, g1, b1, type, model.getX(), model.getY());
    }
    checks(shape, duration, t1, x1, y1, w1, h1, r1, g1, b1, model.getX(), model.getY());

  }

  //Checks the type of motion and appends to svgView the correct motion.
  private void checks(AShape shape, int duration, int t1, int x1, int y1, int
      w1, int h1, int r1, int g1, int b1, int boardX, int boardY) {
    if (shape.getX() != x1) {
      view.svgShape(t1, duration, "x", x1 - boardX,
          ((int) shape.getX()) - boardX, "freeze", ticksPerSec);
    }
    if (shape.getY() != y1) {
      view.svgShape(t1, duration, "y", y1 - boardY,
          ((int) shape.getY()) - boardY, "freeze", ticksPerSec);
    }
    if (shape.getWidth() != w1) {
      view.svgShape(t1, duration, "w", w1, (int) shape.getWidth(), "freeze",
          ticksPerSec);
    }
    if (shape.getHeight() != h1) {
      view.svgShape(t1, duration, "h", h1, (int) shape.getHeight(), "freeze",
          ticksPerSec);
    }
    if (shape.getColor().getRed() != r1 || shape.getColor().getGreen() != g1 ||
        shape.getColor().getBlue() != b1) {
      view.svgColor(t1, duration, r1, g1, b1, shape, ticksPerSec);
    }
  }

  //Helper method to create a new shape in any type of view.
  private void createHelper(LinkedHashMap<String, AShape> shapes,
      HashMap<Integer, List<AShape>> motion) {
    String type;
    String id;
    for (Map.Entry<String, AShape> entry : shapes.entrySet()) {
      if (entry.getValue() instanceof Circle) {
        type = "circle";
        id = entry.getValue().getId();
        setValues(motion, id, type);
      } else if (entry.getValue() instanceof Rectangle) {
        type = "rectangle";
        id = entry.getValue().getId();
        setValues(motion, id, type);
      } else if (entry.getValue() instanceof Ellipse) {
        type = "ellipse";
        id = entry.getValue().getId();
        setValues(motion, id, type);
      }
    }
  }

  //Helper to set values of shapes to be displyed in textual view.
  private void setValues(HashMap<Integer, List<AShape>> motion, String id, String type) {
    int t1 = 0;
    int x1 = 0;
    int y1 = 0;
    int w1 = 0;
    int h1 = 0;
    int r1 = 0;
    int g1 = 0;
    int b1 = 0;
    boolean init = false;
    int max = model.maxKey();
    for (int i = 0; i <= max; i++) {
      if (motion.get(i) == null) {
        continue;
      } else {
        Iterator<AShape> itr = motion.get(i).iterator();
        while (itr.hasNext()) {
          AShape shape = itr.next();
          if (id.equals(shape.getId()) && !init) {
            init = true;
            t1 = i;
            x1 = (int) shape.getX();
            y1 = (int) shape.getY();
            w1 = (int) shape.getWidth();
            h1 = (int) shape.getHeight();
            r1 = shape.getColor().getRed();
            g1 = shape.getColor().getGreen();
            b1 = shape.getColor().getBlue();
          } else if (id.equals(shape.getId()) && init) {
            init = false;
            view.create(id, type, t1, x1, y1, w1, h1, r1, g1, b1, i,
                (int) shape.getX(), (int) shape.getY(), (int) shape.getWidth(),
                (int) shape.getHeight(), shape.getColor().getRed(),
                shape.getColor().getGreen(), shape.getColor().getBlue());
            itr.remove();
          }
        }
      }
    }
  }

  private int getSpeed() {
    return this.ticksPerSec;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    switch (e.getActionCommand()) {
      case "START":
        this.timer.start();
        view.setLabel("STARTED");
        break;
      case "RESUME":
        this.timer.start();
        view.setLabel("RESUMED");
        break;
      case "PAUSE":
        this.timer.stop();
        view.setLabel("PAUSED");
        break;
      case "RESTART":
        this.timer.restart();
        view.setLabel("RESTARTED");
        currentTick = 0;
        break;
      case "LOOP":
        isLoop = !isLoop;
        view.setIsLoop(isLoop);
        break;
      case "INCREASE SPEED":
        this.ticksPerSec += 2;
        this.timer.setDelay(1000 / ticksPerSec);
        view.setLabel("SPEED: " + ticksPerSec);
        break;
      case "DECREASE SPEED":
        if (ticksPerSec <= 2) {
          this.ticksPerSec = 1;
        } else {
          this.ticksPerSec -= 2;
        }
        this.timer.setDelay(1000 / ticksPerSec);
        view.setLabel("SPEED: " + ticksPerSec);
        break;
      case "Create":
        if (view.getName().equals("") || view.getShapeType().equals("")) {
          view.setText();
        } else {
          model.createShape(view.getName(), view.getShapeType());
          view.setText();
          view.createListOfShapes(model.getShapes(), this.keyFrames);
          animations = start();
          view.refresh();
        }
        break;
      case "Delete":
        if (view.getName().equals("")) {
          view.setText();
        } else {
          model.removeShape(view.getName());
          view.setText();
          view.createListOfShapes(model.getShapes(), this.keyFrames);
          animations = start();
          view.refresh();
        }
        break;
      case "Insert":
        if (view.getName().equals("") || view.getKeyFrameData().equals("")) {
          view.setText();
          view.setKeyFrame();
        } else {
          String name = view.getName();
          String time = view.getKeyFrameData();
          model.insertKeyFrame(name, Integer.parseInt(time), animations);
          view.setText();
          view.setKeyFrame();
          animations = start();
          view.createListOfShapes(model.getShapes(), keyFrameList(animations));
          view.refresh();
        }
        break;
      case "Remove":
        if (view.getName().equals("") || view.getKeyFrameData().equals("")) {
          view.setText();
          view.setKeyFrame();
        } else {
          model.removeKeyFrame(view.getName(), Integer.parseInt(view.getKeyFrameData()));
          view.setText();
          view.setKeyFrame();
          animations = start();
          view.createListOfShapes(model.getShapes(), keyFrameList(animations));
          view.refresh();
        }
        break;
      case "Edit":
        if (view.getName().equals("") || view.getKeyFrameData().equals("") ||
            (view.getKeyFramePosition().equals("") && view.getKeyFrameDimension().equals("") &&
            view.getKeyFrameColor().equals(""))) {
          view.setText();
          view.setKeyFrame();
        } else if (view.getKeyFramePosition().equals("") || view.getKeyFrameDimension().equals("")
            || view.getKeyFrameColor().equals("")) {
          //, , , , PC,
          if (view.getKeyFramePosition().equals("") && !view.getKeyFrameDimension().equals("") &&
              !view.getKeyFrameColor().equals("")) {
            setDimensions();
            setColor();
            model.editShape(view.getName(), Integer.parseInt(view.getKeyFrameData()), null,
                null, w, h, new Color(r, g, b));
          } else if (view.getKeyFramePosition().equals("") && view.getKeyFrameDimension().equals("")
              && !view.getKeyFrameColor().equals("")) {
            setColor();
            model.editShape(view.getName(), Integer.parseInt(view.getKeyFrameData()), null,
                null, -1, -1, new Color(r, g, b));
          } else if (view.getKeyFramePosition().equals("") && view.getKeyFrameColor().equals("") &&
              !view.getKeyFrameDimension().equals("")) {
            setDimensions();
            model.editShape(view.getName(), Integer.parseInt(view.getKeyFrameData()), null,
                null, w, h, null);
          } else if (!view.getKeyFramePosition().equals("") && view.getKeyFrameColor().equals("") &&
              view.getKeyFrameDimension().equals("")) {
            setPosition();
            model.editShape(view.getName(), Integer.parseInt(view.getKeyFrameData()), x, y, -1,
                -1, null);
          } else if (!view.getKeyFramePosition().equals("") && view.getKeyFrameColor().equals("") &&
              !view.getKeyFrameDimension().equals("")) {
            setPosition();
            setDimensions();
            model.editShape(view.getName(), Integer.parseInt(view.getKeyFrameData()), x, y, w, h,
                null);
          } else if (!view.getKeyFramePosition().equals("") && !view.getKeyFrameColor().equals("")
              && view.getKeyFrameDimension().equals("")) {
            setColor();
            setPosition();
            model.editShape(view.getName(), Integer.parseInt(view.getKeyFrameData()), x, y, -1,
                -1, new Color(r, g, b));
          }
          animations = start();
          view.setText();
          view.setKeyFrame();
          break;
        } else {
          String name = view.getName();
          int tick = Integer.parseInt(view.getKeyFrameData());
          setPosition();
          setDimensions();
          setColor();
          model.editShape(name, tick, x, y, w, h, new Color(r, g, b));
          animations = start();
          view.refresh();
        }
        view.setText();
        view.setKeyFrame();
        break;
      default:
        // do nothing
    }
  }

  private void setPosition() {
    int indexOfPos = view.getKeyFramePosition().indexOf(",");
    x = Integer.parseInt(view.getKeyFramePosition().substring(0, indexOfPos));
    y = Integer.parseInt(view.getKeyFramePosition().substring(indexOfPos + 1));
  }

  private void setDimensions() {
    int indexOfDim = view.getKeyFrameDimension().indexOf(",");
    w = Integer.parseInt(view.getKeyFrameDimension().substring(0, indexOfDim));
    h = Integer.parseInt(view.getKeyFrameDimension().substring(indexOfDim + 1));
  }

  private void setColor() {
    String[] rgb = view.getKeyFrameColor().split(",");
    r = Integer.parseInt(rgb[0]);
    g = Integer.parseInt(rgb[1]);
    b = Integer.parseInt(rgb[2]);
  }

  /**
   * Returns the log of the controller for testing purposes.
   * @return the log of the controller.
   */
  public StringBuilder getLog() {
    return this.log;
  }
}
