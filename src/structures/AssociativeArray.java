package structures;

import static java.lang.reflect.Array.newInstance;

/**
 * A basic implementation of Associative Arrays with keys of type K
 * and values of type V. Associative Arrays store key/value pairs
 * and permit you to look up values by key.
 *
 * @author Arsal Shaikh
 * @author Samuel A. Rebelsky
 */
public class AssociativeArray<K, V> {
  // +-----------+---------------------------------------------------
  // | Constants |
  // +-----------+

  /**
   * The default capacity of the initial array.
   */
  static final int DEFAULT_CAPACITY = 16;

  // +--------+------------------------------------------------------
  // | Fields |
  // +--------+

  /**
   * The size of the associative array (memory allocated to the array).
   */
  int capacity;


  /**
   * The size of the associative array (the number of key/value pairs).
   */
  int size;

  /**
   * The array of key/value pairs.
   */
  KVPair<K, V> pairs[];

  // +--------------+------------------------------------------------
  // | Constructors |
  // +--------------+

  /**
   * Create a new, empty associative array.
   */
  @SuppressWarnings({ "unchecked" })
  public AssociativeArray() {
    // Creating new arrays is sometimes a PITN.
    this.pairs = (KVPair<K, V>[]) newInstance((new KVPair<K, V>()).getClass(),
        DEFAULT_CAPACITY);
    this.size = 0;
  } // AssociativeArray()

  // +------------------+--------------------------------------------
  // | Standard Methods |
  // +------------------+

  /**
   * Create a copy of this AssociativeArray.
   */
  public AssociativeArray<K, V> clone() {
    AssociativeArray<K,V> copy = new AssociativeArray<K,V>();
    for (int i = 0; i < pairs.length; i++) {
      if (this.size > copy.capacity) {
        copy.expand();
      }
      copy.pairs[i] = this.pairs[i];
    } // for
    return copy;
  } // clone()

  /**
   * Convert the array to a string.
   */
  public String toString() {
    return "{}"; // STUB
  } // toString()

  // +----------------+----------------------------------------------
  // | Public Methods |
  // +----------------+

  /**
   * Set the value associated with key to value. Future calls to
   * get(key) will return value.
   */
  public void set(K key, V value) throws NullKeyException {
    // Catch null key
    if (key == null) {
      throw new NullKeyException();
    }

    int setIndex;
    // try key in array
    try {
      setIndex = this.find(key);
      this.pairs[setIndex].value = value;
    } catch (KeyNotFoundException e) {
      // if key dosent exist then set into a null slot
      try {
        setIndex = this.findNull();
        this.pairs[setIndex] = new KVPair<K,V>(key, value);
        this.size++;
      } catch (KeyNotFoundException f) {
        // Array is full so expand
        this.expand();
        capacity = capacity * 2;
        setIndex = this.size;
  
        // then set in first available space
        this.pairs[setIndex] = new KVPair<K,V>(key, value);
        this.size++;
      } // try catch
    } // try catch
  } // set(K,V)

  /**
   * Get the value associated with key.
   *
   * @throws KeyNotFoundException
   *                              when the key is null or does not 
   *                              appear in the associative array.
   */
  public V get(K key) throws KeyNotFoundException {
    // Catching null key
    if (key == null) {
      throw new KeyNotFoundException();
    } // if

    // Getting first instance of key found
    return this.pairs[this.find(key)].value;
  } // get(K)

  /**
   * Determine if key appears in the associative array. Should
   * return false for the null key.
   */
  public boolean hasKey(K key) {
    try {
      this.find(key);
      return true;
    } catch (KeyNotFoundException e) {
      return false;
    }
  } // hasKey(K)

  /**
   * Remove the key/value pair associated with a key. Future calls
   * to get(key) will throw an exception. If the key does not appear
   * in the associative array, does nothing.
   */
  public void remove(K key) {
    // Removing instance of key found
    try {
      int index = this.find(key);
      this.pairs[index] = null;
      this.size--;
    } catch (KeyNotFoundException e) {
      // if key not found then do nothing
      return;
    } // try to remove
  } // remove(K)

  /**
   * Determine how many key/value pairs are in the associative array.
   */
  public int size() {
    return this.size;
  } // size()

  // +-----------------+---------------------------------------------
  // | Private Methods |
  // +-----------------+

  /**
   * Expand the underlying array.
   */
  void expand() {
    this.pairs = java.util.Arrays.copyOf(this.pairs, this.pairs.length * 2);
  } // expand()

  /**
   * Find the index of the first entry in `pairs` that contains key.
   * If no such entry is found, throws an exception.
   */
  public int find(K key) throws KeyNotFoundException {
    // Getting first instance of key found
    for (int i = 0; i < this.pairs.length; i++) {
      // Skip null values
      if (this.pairs[i] == null) {
        continue;
      } // if

      if (this.pairs[i].key.equals(key)) {
        return i;
      } // if
    } // for
    
    // if not found
    throw new KeyNotFoundException();
  } // find(K)


  /**
   * Find the index of the first null key in `pairs`.
   * If no such entry is found, throws an exception.
   */
  public int findNull() throws KeyNotFoundException {
    // Getting first null value
    for (int i = 0; i < this.pairs.length; i++) {
      if (this.pairs[i] == null) {
        return i;
      } // if
    } // for
    
    // if not found
    throw new KeyNotFoundException();
  } // findNull()
} // class AssociativeArray
