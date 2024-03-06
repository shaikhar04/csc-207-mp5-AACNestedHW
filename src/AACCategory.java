import structures.AssociativeArray;
import structures.NullKeyException;

/**
 * AACCategory
 * TODO DESCRIPTION
 *
 * @author Arsal Shaikh
 */

public class AACCategory {
  
  /**
   * Fields
   */
  String name;
  AssociativeArray<String, String> mappings;
   
  /**
   * Constructor
   * Creates a new empty category with the given name.
   * @param name
   */
  public AACCategory(String name) {
    this.name = name;
    this.mappings = new AssociativeArray<String, String>();
    // STUB
  } // AACCategory(String)

  /**
   * Method
   * Adds the mapping of the imageLoc to the text to the category.
   * @param imageLoc
   * @param text
   */
  public void addItem(String imageLoc, String text) {
    try {
      this.mappings.set(imageLoc, text);
    } catch (NullKeyException nke) {
      // TODO: handle exception
    }
  } // class addItem(String, String)

  /**
   * Method
   * Returns the name of the category.
   * @return this.name
   */
  public String getCategory() {
    return this.name;
  } // getCategory()

  /**
   * Method
   * Returns an array of all the images in the category.
   * @return
   */
  public String[] getImages() {
    return new String[] {}; // STUB
  } // getImages()

  /**
   * Method
   * Returns the text associated with the given image loc in this category.
   * @param imageLoc
   * @return 
   */
  public String getText(String imageLoc) {
    try {
      return this.mappings.get(imageLoc);
    } catch (NullKeyException nke) {
      return "Not Found."
    }
  } // getText(String)

  /**
   * Method
   * Determines if the provided images is stored in the category.
   * @param imageLoc
   * @return
   */
  public boolean hasImage(String imageLoc) {
    // STUB
  } // class hasImage(String)
} // class AACCategory
