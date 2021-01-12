package controller;

/**
 * Represents an interface for a controller that seperate implementations of controllers can
 * implement. The purpose of any class implementing IController is to pass model data to the
 * View and call methods in the view that display the final output.
 */
public interface IController {

  /**
   * A method that detects the type of IView that was passed to the controller, and calls the
   * appropriate methods in both the controller and view to pass the view enough model data to
   * construct a desired output.
   */
  void animate();
}
