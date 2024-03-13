package structures;

/**
 * Exceptions that indicate that an element is not in an AACCategory object.
 *
 * @author Arsal Shaikh
 */
public class ElementNotFoundException extends Exception {
  // +--------------+------------------------------------------------
  // | Constructors |
  // +--------------+

  /**
   * Create a new exception.
   */
  public ElementNotFoundException() {
    super("element not found");
  } // ElementNotFoundException()

  /**
   * Create a new exception with a particular message.
   */
  public ElementNotFoundException(String message) {
    super(message);
  } // ElementNotFoundException(String)
} // class ElementNotFoundException
