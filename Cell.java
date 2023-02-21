import java.awt.Color;
import java.util.List;
import java.util.Random;

/**
 * A class representing the shared characteristics of all forms of life
 *
 * @author David J. Barnes, Michael Kölling & Jeffery Raphael
 * @version 2022.01.06 (1)
 */

public abstract class Cell {
  // Whether the cell is alive or not.
  private boolean alive;

  // Whether the cell will be alive in the next generation.
  private boolean nextAlive;

  // The cell's field.
  private Field field;

  // The cell's position in the field.
  private Location location;

  // The cell's color
  private Color color = Color.white;

  public int age = 0;

  private boolean isDiseased = false;
  private int diseasedAge = 0;

  /**
   * Create a new cell at location in field.
   *
   * @param field    The field currently occupied.
   * @param location The location within the field.
   */
  public Cell(Field field, Location location, Color col) {
    alive = true;
    nextAlive = false;
    this.field = field;
    setLocation(location);
    setColor(col);
  }

  public static void main(String[] args) {
    randomize();
    System.out.println(randomize());

  }

  public void incrementAge() {
    age++;
  }

  public void resetAge() {
    age = 0;
  }

  public int getAge() {
    return age;
  }

  // randomize number from 0 to 10
  public static int randomize() {
    Random rand = new Random();
    int randomNum = rand.nextInt(10);
    return randomNum;
  }

  /**
   * Make this cell act - that is: the cell decides it's status in the
   * next generation.
   */
  public void act() {
    // if in the next round you are alive, increment age
    if (nextAlive) {
      // randomly get disease (for each life form)
      // get diseased in %30 of chance, if you are not already diseased
      if (randomize() > 7 && !isDiseased) {
        isDiseased = true;
        // get disease, die after 5 rounds of disease, ? (change of behaviour)
        setNextState(false);
        resetAge();
      }
      incrementAge();
    } else {
      resetAge();
    } 
  }

  /**
   * Check whether the cell is alive or not.
   * 
   * @return true if the cell is still alive.
   */
  protected boolean isAlive() {
    return alive;
  }

  /**
   * Indicate that the cell is no longer alive.
   */
  protected void setDead() {
    alive = false;
  }

  /**
   * Indicate that the cell will be alive or dead in the next generation.
   */
  public void setNextState(boolean value) {
    nextAlive = value;

  }

  public void handleAge(){
    if(nextAlive){
      incrementAge();
    }else{
      resetAge();
    }
  }

  /**
   * Changes the state of the cell and Age
   */
  public void updateState() {
    alive = nextAlive;
    handleAge();
  }

  /**
   * Changes the color of the cell
   */
  public void setColor(Color col) {
    color = col;
  }

  /**
   * Returns the cell's color
   */
  public Color getColor() {
    return color;
  }

  /**
   * Return the cell's location.
   * 
   * @return The cell's location.
   */
  protected Location getLocation() {
    return location;
  }

  /**
   * Place the cell at the new location in the given field.
   * 
   * @param location The cell's location.
   */
  protected void setLocation(Location location) {
    this.location = location;
    field.place(this, location);
  }

  /**
   * Return the cell's field.
   * 
   * @return The cell's field.
   */
  protected Field getField() {
    return field;
  }
}
