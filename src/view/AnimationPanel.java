package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import shapes.Ellipse;
import shapes.Rectangle;
import shapes.Shapes;

/**
 * Represents the panel on the JFrame and produces the animations with the given list of shapes
 * provided by the controller.
 */
public class AnimationPanel extends JPanel {

  private List<Shapes> shapes;
  private int boardX;
  private int boardY;

  /**
   * Constructs the panel that produces animations and actually draws the panel.
   */
  public AnimationPanel() {
    super();
    this.setBackground(Color.WHITE);
    this.shapes = new ArrayList<>();
    this.boardX = 0;
    this.boardY = 0;
  }

  @Override
  protected void paintComponent(Graphics g) {
    //never forget to call super.paintComponent!
    super.paintComponent(g);

    Graphics2D g2d = (Graphics2D) g;

    g2d.setColor(Color.BLACK);

    for (Shapes s : shapes) {
      if (s.getRender()) {
        int x = (int) s.getX();
        int y = (int) s.getY();
        int d1 = (int) s.getWidth();
        int d2 = (int) s.getHeight();
        Color c = s.getColor();
        if (s.getShape() instanceof Ellipse) {
          g2d.setColor(c);
          g2d.fillOval(x - boardX, y - boardY, d1, d2);
          g2d.drawOval(x - boardX, y - boardY, d1, d2);
        } else if (s.getShape() instanceof Rectangle) {
          g2d.setColor(c);
          g2d.fillRect(x - boardX, y - boardY, d1, d2);
          g2d.drawRect(x - boardX, y - boardY, d1, d2);
        }
      }
    }
  }

  /**
   * Sets the list of shapes of the panel to the given the list of shapes.
   * @param shapes list of shapes to be drawn at this tick
   */
  public void setShapes(List<Shapes> shapes) {
    this.shapes = shapes;
  }

  /**
   * Sets the offset of the board with the given offsets.
   *
   * @param boardX the offset of the x value of the board
   * @param boardY the offset of the y value of the board
   */
  public void setOffset(int boardX, int boardY) {
    this.boardX = boardX;
    this.boardY = boardY;
  }
}
