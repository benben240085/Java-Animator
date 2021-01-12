import org.junit.Test;

import java.awt.event.ActionEvent;
import java.io.PrintStream;

import controller.Controller;
import model.Model;

import static junit.framework.TestCase.assertEquals;

/**
 * A test class that checks that all the buttons on the editView call methods in the view.
 */
public class ControllerTest {

  StringBuilder log = new StringBuilder();

  Model testModel = new Model();
  Controller testController = new Controller(testModel, "Mock", 1,
          new PrintStream(System.out));

  ActionEvent start = new ActionEvent(this, 1, "START");
  ActionEvent resume = new ActionEvent(this, 1, "RESUME");
  ActionEvent pause = new ActionEvent(this, 1, "PAUSE");
  ActionEvent restart = new ActionEvent(this, 1, "RESTART");
  ActionEvent loop = new ActionEvent(this, 1, "LOOP");
  ActionEvent speedup = new ActionEvent(this, 1, "INCREASE SPEED");
  ActionEvent speeddown = new ActionEvent(this, 1, "DECREASE SPEED");
  ActionEvent create = new ActionEvent(this, 1, "Create");
  ActionEvent delete = new ActionEvent(this, 1, "Delete");
  ActionEvent insert = new ActionEvent(this, 1, "Insert");
  ActionEvent remove = new ActionEvent(this, 1, "Remove");
  ActionEvent edit = new ActionEvent(this, 1, "Edit");

  @Test
  public void testStartButtons() {

    assertEquals("", testController.getLog().toString());

    testController.actionPerformed(start);

    assertEquals("STARTED\n", testController.getLog().toString());
  }

  @Test
  public void testResumeButtons() {

    assertEquals("", testController.getLog().toString());

    testController.actionPerformed(resume);

    assertEquals("RESUMED\n", testController.getLog().toString());
  }

  @Test
  public void testPauseButtons() {

    assertEquals("", testController.getLog().toString());

    testController.actionPerformed(pause);

    assertEquals("PAUSED\n", testController.getLog().toString());
  }

  @Test
  public void testRestartButtons() {

    assertEquals("", testController.getLog().toString());

    testController.actionPerformed(restart);

    assertEquals("RESTARTED\n", testController.getLog().toString());
  }

  @Test
  public void testLoopButtons() {

    assertEquals("", testController.getLog().toString());

    testController.actionPerformed(loop);

    assertEquals("LOOP\n", testController.getLog().toString());
  }

  @Test
  public void testSpeedButtons() {

    assertEquals("", testController.getLog().toString());

    testController.actionPerformed(speedup);

    testController.actionPerformed(speedup);

    assertEquals("SPEED: 3\n" +
            "SPEED: 5\n", testController.getLog().toString());
  }

  @Test
  public void testCreateButton() {

    assertEquals("", testController.getLog().toString());

    testController.actionPerformed(create);

    assertEquals("action complete\n", testController.getLog().toString());
  }

  @Test
  public void testDeleteButton() {

    assertEquals("", testController.getLog().toString());

    testController.actionPerformed(delete);

    assertEquals("action complete\n", testController.getLog().toString());
  }

  @Test
  public void testInsertButton() {

    assertEquals("", testController.getLog().toString());

    testController.actionPerformed(insert);

    assertEquals("action complete\n", testController.getLog().toString());
  }

  @Test
  public void testRemoveButton() {

    assertEquals("", testController.getLog().toString());

    testController.actionPerformed(remove);

    assertEquals("action complete\n", testController.getLog().toString());
  }

  @Test
  public void testEditButton() {

    assertEquals("", testController.getLog().toString());

    testController.actionPerformed(edit);

    assertEquals("action complete\n" +
            "action complete\n", testController.getLog().toString());
  }
}
