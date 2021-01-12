package cs3500.animator;

import controller.Controller;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import cs3500.animator.util.AnimationReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;
import model.Model.Builder;
import model.Models;
import view.IView;

/**
 * The class where the program starts and respective models and views are passed into the
 * controller.
 */
public final class Excellence {

  /**
   * A main method that takes in a string of arguments that define the type of view
   * and the qualities of the animation stored in a file that is also specified.
   * @param args the string of arguments
   * @throws FileNotFoundException if the file specified in the arguments is not available.
   */
  public static void main(String[] args) throws FileNotFoundException {
    Models model = null;
    IView view = null;
    String viewType = "";
    Readable rd;
    PrintStream out = System.out;
    System.setOut(out);
    String input;
    int ticks = 1;
    Scanner scan = new Scanner(toString(args));

    while (scan.hasNext()) {
      input = scan.next();
      switch (input) {
        case "-in":
          if (scan.hasNext()) {
            rd = new FileReader("resources/" + scan.next());
            AnimationReader ar = new AnimationReader();
            model = ar.parseFile(rd, new Builder());
          }
          break;
        case "-view":
          if (scan.hasNext()) {
            viewType = scan.next();
          }
          break;
        case "-out":
          if (scan.hasNext()) {
            try {
              String filePath = "resources/" + scan.next();
              out = new PrintStream(new FileOutputStream(filePath));
              System.setOut(out);
              return;
            } catch (IOException e) {
              out.append("An error occurred.");
            }
          }
          break;
        case "-speed":
          if (scan.hasNextInt()) {
            int check = scan.nextInt();
            if (check > 0) {
              ticks = check;
            }
          }
          break;
        default:
          break;
      }
    }

    new Controller(model, viewType, ticks, out).animate();
  }

  //Converts the String[] to a String.
  private static String toString(String[] arg) {
    String output = "";

    for (String str: arg) {
      output += str + " ";
    }
    return output;
  }
}
