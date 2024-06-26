import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import structures.AssociativeArray;
import structures.KVPair;
import structures.KeyNotFoundException;
import structures.NullKeyException;


public class AACMappings {
  /**
   * AACMappings
   * Stores the mapping of the images on the home page to the AACCategories.
   *
   * @author Arsal Shaikh
   * @author Catie Baker
   * @author Samuel A. Rebelsky
   */
  
  /**
   * Fields
  */
  AssociativeArray<String, AACCategory> categoryMappings; // maps category images to category objects
  AACCategory homepage; // maps category images too category text/name
  AACCategory currentCategory; // object tracking the current category being displayed
  File pathFile;


  /**
   * Constructor 
   * @param filename contains the categories and the image file paths within them.
   */
  public AACMappings(String filename) {
    this.categoryMappings = new AssociativeArray<String, AACCategory>();
    this.homepage = new AACCategory("");
    this.currentCategory = this.homepage;
    
    this.pathFile = new File(filename);
    try {
      String categoryImage = null;
      String categoryText = null;
      String text = null;
      String imagePath;
      Scanner reader = new Scanner(new FileReader(this.pathFile));
      String currentLine;
      String[] currentLineTokens;
      while (reader.hasNextLine()) {
        currentLine = reader.nextLine();
        currentLineTokens = currentLine.split(" ", 2);
        
        // if new image
        if (currentLine.startsWith(">")) {
          // Get text and image path of new image
          imagePath = currentLineTokens[0].substring(1);
          text = currentLineTokens[1];

          // Add new entry in the category
          try {
            this.categoryMappings.get(categoryImage).addItem(imagePath, text);
          } catch (KeyNotFoundException knfe) {
            knfe.printStackTrace();
          } // try catch

        } else {
          // if new category
          
          // Get image path and text of the new category
          imagePath = currentLineTokens[0];
          categoryText = currentLineTokens[1];
          
          // Add new category to ACCMappings
          try {
            this.categoryMappings.set(imagePath, new AACCategory(imagePath));
          } catch (NullKeyException nke) {
            nke.printStackTrace();
          }

          // Add new category to the homepage
          this.homepage.addItem(imagePath, categoryText);
          
          // Keep track of the category we are adding to
          categoryImage = imagePath;
        } // if else
      } // while

      reader.close();
    } catch (FileNotFoundException e) {
      System.err.println("File not Found");
    }
  } // AACMappings(String)

  /**
   * Method
   * Adds the mapping to the current category (or the default category if that is the current category).
   * @param imageLoc
   * @param text
   */
  public void add(String imageLoc, String text) {
    // if (On Homepage)
    if (this.currentCategory.equals(this.homepage)) {
      // Create new category
      AACCategory newCategory = new AACCategory(text);
      
      // Adds mapping to associative array
      try {this.categoryMappings.set(imageLoc, newCategory);} catch (NullKeyException nke) {}
      
      // Adds mapping to homepage
      this.homepage.addItem(imageLoc, text);

      // Selects new category as current page
      this.currentCategory = newCategory;
    } else {
      // Add to currently selected category
      this.currentCategory.addItem(imageLoc, text);
    } // if else
  } // add(String, String)

  /**
   * Method
   * Gets the current category
   * @return
   */
  public String getCurrentCategory() {
    return this.currentCategory.getCategory();
  } // getCurrentCategory()

  /**
   * Method
   * Provides an array of all the images in the current category
   * @return
   */
  public String[] getImageLocs() {
    return this.currentCategory.getImages();
  } // getImageLocs()

  /**
   * Method
   * Given the image location selected, it determines the associated text with the image.
   * @param imageLoc
   * @return name of the category or text associated with given image
   */
  public String getText(String imageLoc) throws Exception {
    String result = this.currentCategory.getText(imageLoc);
    if (result.equals("Not Found")) {
      throw new Exception();
    } // if (image not found)

    if (this.currentCategory.equals(this.homepage)) {
      try {
        this.currentCategory = this.categoryMappings.get(imageLoc);
      } catch (KeyNotFoundException knfe) {
        throw new Exception();
      }
    } // if (on homepage)
    return result;
  } // getText(String)

  /**
   * Method
   * Determines if the image represents a category or text to speak
   * @param imageLoc
   * @return true if imageLoc associated with category
   */
  public boolean isCategory(String imageLoc) {
    return this.homepage.hasImage(imageLoc);
  } // isCategory()

  /**
   * Method
   * Resets the current category of the AAC back to the default category
   */
  public void reset() {
    this.currentCategory = this.homepage;
  } // reset()

  /**
   * Method
   * Writes the ACC mappings stored to a file.
   * @param filename
   */
  public void writeToFile(String filename) {
    try {
      FileWriter writer = new FileWriter(filename, true);
      AACCategory currentCategory;

      for (KVPair<String, AACCategory> categoryName : this.categoryMappings) {
        writer.write(categoryName.getKey() + " " + homepage.getText(categoryName.getKey()) + "\n"); // CategoryName + CatImage
        currentCategory = categoryName.getValue();
        for (KVPair<String, String> catImagepath : currentCategory.mappings) {
          writer.write(">" + catImagepath.getKey() + " " + catImagepath.getValue() + "\n");
        } // for - loops through images within a category
      } // for - loops through categories
  
      writer.close();
    } catch (IOException ioe) {
      System.err.println("IO Error");
    } // try catch
  } // writeToFile(String)
} // class AACMappings
