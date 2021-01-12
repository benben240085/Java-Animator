Our Main Model Implementation model has:
  - Integer fields that determine the bounds of the board by setting the width and the visible bounds
  - A hashmap that contains all Ashapes in the animation and their (string)ID's as keys
  - A hashmap that contains all a list of shapes that are to be animated at each tick. The (int)tick in this map serves as the key to a list of Ashape that contains the shapes to be moved on that tick
  - A color field that describes the background color of the board

We also have a class Excellence that starts the program and hands in data to the controller. It also reads in arguments that describe the contents of the animation.

Interfaces:
  -Models:
  Represents an interface of all the different types of models one can have for the simple animator. A model that implements Models will utilize the methods in this interface to create data that represents an animation. The purpose of any model implementation is to provide data to the controller when prompted.

  -Shapes:
  An interface representing methods for shapes. A shape is an object that has an ID, a position (represented by an x and y coordinate), a width and height, and a color (represented in rgb). A shape can be modified to produce desired animations by methods in shape called by the model.

  -Icontroller:
  Represents an interface for a controller that seperate implementations of controllers can implement. The purpose of any class implementing IController is to pass model data to the View and call methods in the view that display the final output.

  -IView:
  An interface that represents a view. The purpose of any class implementing IView is to display the data passed to it by the controller. The method provided in this interface makes the view visible.
  
  -IAnimation
  An interface that represents an animation. The purpose of any class implementing IAnimation is to construct animation objects which in turn can calculate the "tweening" values for a shape given the current time. 

Classes:

 -Animations
 Represents an animation which contains starting values of a shape and ending values of a shape. The Animations class has a getShape method so we can get the shape and then set the shape with the new "tween" values. 
 
 -Utility
 Represents a utility belt which has certain "tools" you can use when "tweening". 

  -Model:
  Represents a collection of shapes and a board to be animated. The Model class has a width and height field as well as an x and y field that determine the bounds of the board. It also has a Color field that determines the background color of the board.

  To track the shapes and motions of the animaton, the Model contains one hashmap and one linkedHashMap. The linkedHashMap holds all the shapes and their ID's and therefore we can keep track of insertion order when drawing things in our visual view, and the hashMap holds all the motions and the shapes at the start and end of each motion.

  -Ashape:
  An Abstract Class that represents a shape. It implements Shapes. This class implements shapes and provides abstract methods that can be used by all shapes that extend Ashape. It also provides two super constructors, one that uses width and height to build a shape, and another that uses diameter.

  -Circle:
  A class representing a circle shape. It extends Ashape.

  -Ellipse:
  A class representing an ellipse shape. It extends Ashape.

  -Rectangle:
  A class representing a rectangle shape. It extends Ashape.

  -Controller:
  A Controller object that implements IController. It is constructed with a view that implements IView and a model that implements Models. The purpose of this class is to pass model data to the View and call methods in the view that display the final output. It also contains a PrintStream that is passed to SvgView. 

  -TextualView:
  A class that displays the provided data in the form of a textual grid. It Implements IView. It has a StringBuilder that is appended to over time that will eventually be returned. It also contains a hashmap of motions that relates shapes to their motions described as text.

  -SvgView:
  A class that displays the provided data in the form of an SVG file that contains code that builds the animation in a web browser. It implements IView. It has a stringbuilder that is appended to over time and contains the code that will make up the svg file. It also contains a hashmap that contains the reset values of each shape in the animation so that loops can be implemented.

  -AnimationPanel: 
  A class that uses the Graphics class to draw the objects at every tick onto a panel that is used in the VisualView. 

  -VisualView: 
  A class that extends JFrame and constructs the size and location of the frame based on the input in the file. Has an instance of the AnimationPanel class and adds it as a component onto the JFrame in order to display the animations and shapes. 
  
  -EditView:
  A class that extends JFrame and constructs the size and location of the frame based on the input in the file. Has an instance of a visualView in order to use the decorator pattern to implement some of the methods in this class. This class represents a VisualView that also has the functionality to edit things through the use of the buttons and textFields that are added as components onto the JFrame using layout managers. 

Design of the model:

  - The constructors of the model do not allow a model to have a board with negative area, becauase this makes no sense in the context of displaying an animation.

  - The model also disallows instantaneous changes to any shape, so any motion following another motion must have the same starting details as the ending details of the previous motion.

  - We save copies of the shape data in motions, rather than the original shape object. This helps us avoid destructive mutation that would cause bugs.

  - The model works with Ashapes, which allows new shapes to be added to our program without the need to modify any code in the model as long as they extend Ashape.
  
  - The model also now contains getters that can now pass data to the controller in a safe way where we only return copies of our hashMaps and linkedHashMaps. 


Design of the controller:

  - The controller takes any model or view that implements Models or IView respectively.

  - The controller contains helper methods that are specific to each view implementation to simply code in the views and make method purposes more specific and clear.
  
  - The controller uses the model and the view to collect and send information from and to the model and view. Therefore, we didn't have any information leaks. 
  
Design of the views:

  - The IView's purpose is to represent a class that is passed data by the controller and somehow visualizes that data. The makeVisible method is always the last method to be called in all of our view implementations, and must be called after the view is constructed.

  - The SVGView implements the IView. Its main task is to render the animation into an SVG that can be displayed in a web browser. The view writes the output to a local stringbuilder, then appends this stringbuilder to a PrintStream passed in by the controller.

  - The TextualView implements the IView. Its main task is to render the animation into formatted text that summarizes the animation. The view writes the output to a local appendable and displays this appendabe as output in a JPanel.

  - The VisualView implements the IView. Its main task is to render the animations onto a JFrame for the user to watch. The view uses the AnimationPanel class to draw all the objects that are being passed into it through controller,
  
  - The EditView implements the IView. Its main task is to render the animations onto a JFrame for the user to watch and enable the user to edit the animation in many different ways. The view creates a buttonListener and implements the listener in the controller which uses methods from the model and eventually tells the view what to do once the methods have been executed.
 
 
 
 
 
 