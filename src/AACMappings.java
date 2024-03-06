/**
 * AACMappings
 * TODO DESCRIPTION
 *
 * @author Arsal Shaikh
 */

/**
 * Fields
*/
AssociativeArray<String, AACCategory> mappings;
AACCategory defaultCat;
AACCategory currentCategory;

/**
 * Constructor
 * TODO
 * @param filename
 */
public class AACMappings {
  public AACMappings(String filename) {
    this.mappings = new AssociativeArray<String, AACCategory>;
  } // AACMappings(String)

  /**
   * Method
   * Adds the mapping to the current category (or the default category if that is the current category).
   * @param imageLoc
   * @param text
   */
  public void add(String imageLoc, String text) {
    // STUB
  } // add(String, String)

  /**
   * Method
   * Gets the current category
   * @return
   */
  public String getCurrentCategory() {
    return this.mappings.getCategory(this.currentCategory).getCategory();
  } // getCurrentCategory()

  /**
   * Method
   * Provides an array of all the images in the current category
   * @return
   */
  public String[] getImageLocs() {
    return this.mappings.getCategory(this.currentCategory).getImages();
  } // getImageLocs()

  /**
   * Method
   * Given the image location selected, it determines the associated text with the image.
   * @param imageLoc
   * @return
   */
  public String getText(String imageLoc) {
    return this.mappings.getCategory(this.currentCategory).getText(imageLoc);
  } // getText(String)

  /**
   * Method
   * Determines if the image represents a category or text to speak
   * @return
   */
  public boolean isCategory() {
    if (this.mappings.get(this.currentCategory).getClass() == ) {
      
    }
  } // isCategory()

  /**
   * Method
   * Resets the current category of the AAC back to the default category
   */
  public void reset() {
    this.currentCategory = this.defaultCat;
  } // reset()

  /**
   * Method
   * Writes the ACC mappings stored to a file.
   * @param filename
   */
  public void writeToFile(String filename) {
    // STUB
  } // writeToFile(String)
} // class AACMappings
