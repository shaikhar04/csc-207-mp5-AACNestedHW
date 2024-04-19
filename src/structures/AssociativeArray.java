package structures;

import static java.lang.reflect.Array.newInstance;
import java.util.NoSuchElementException;

/**
 * A basic implementation of Associative Arrays with keys of type K
 * and values of type V. Associative Arrays store key/value pairs
 * and permit you to look up values by key.
 *
 * @author Arsal Shaikh
 * @author Samuel A. Rebelsky
 */
public class AssociativeArray<K, V> implements Iterable<KVPair<K,V>> {
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
    this.capacity = DEFAULT_CAPACITY;
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
    for (int i = 0; i < this.size; i++) {
      if (this.size > copy.capacity) {
        copy.expand();
      } // if
      copy.pairs[i] = new KVPair<K,V>(this.pairs[i].key, this.pairs[i].value);
    } // for
    copy.size = this.size;
    return copy;
  } // clone()

  /**
   * Convert the array to a string.
   */
  public String toString() {
    // Case: array is empty
    if (this.size == 0) {
      return "{}";
    } // if

    String output = String.format("{ %s: %s", this.pairs[0].key, this.pairs[0].value);
    for (int i = 1; i < this.size; i++) {
      output += String.format(", %s: %s", this.pairs[i].key, this.pairs[i].value);
    } // for
    output += " }";

    return output;
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
    } // if

    int setIndex;
    // try key in array
    try {
      setIndex = this.find(key);
      this.pairs[setIndex].value = value;
    } catch (KeyNotFoundException e) {
      
      // if Array is full then expand
      if (this.size >= this.capacity) {
        this.expand();
        capacity = capacity * 2;    
      } // if

      // then set in first available space
      setIndex = this.size;
      this.pairs[setIndex] = new KVPair<K,V>(key, value);
      this.size++;
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
      this.pairs[index] = this.pairs[this.size - 1];
      this.pairs[this.size] = new KVPair<>();
      this.size--;
    } catch (KeyNotFoundException e) {
      // if key not found then do nothing
      return;
    } // try catch
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
    for (int i = 0; i < this.size; i++) {
      // Fail on null values
      if (this.pairs[i].key == null) {
        throw new KeyNotFoundException();
      } // if

      if (this.pairs[i].key.equals(key)) {
        return i;
      } // if
    } // for
    
    // if not found
    throw new KeyNotFoundException();
  } // find(K)

  /**
   * Implements iterator for AssociativeArray using an anonymous class. 
   */
  @Override
  public java.util.Iterator<KVPair<K, V>> iterator() {
    return new java.util.Iterator<KVPair<K,V>>() {
      int index = 0;

      public boolean hasNext() {
        return (this.index < AssociativeArray.this.size );
      } // hasNext()

      public KVPair<K,V> next() {
        if(!hasNext()) {
          throw new NoSuchElementException();
        } // if
        return AssociativeArray.this.pairs[this.index++];
      } // next()
    }; // anon class iterator()
  } // iterator() 
} // class AssociativeArray
