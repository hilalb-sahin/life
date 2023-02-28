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

  private Color originalColor;

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
    setOriginalColor(col);
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

  //randomize double from 0 to 10
  public double randomizeDouble(){
    Random rand = new Random();
    double randomNum = rand.nextDouble(10);
    return randomNum;
  }

  /**
   * Make this cell act - that is: the cell decides it's status in the
   * next generation.
   */
  public void act() {
    if (isAlive()) {
        // by a small chance, the cell will be diseased
        if (randomizeDouble() > 7.5) {
            setIsDiseased(true);
            setColor(Color.RED);
            diseasedAge = 0;
            while (isDiseased && isAlive()) {
                diseasedAge++;
                //if diseasedAge is more than 4 infect the neighbors
                if (diseasedAge > 4) {
                    // set neighbor cells to be diseased
                    List<Cell> neighbours = getField().getLivingNeighbours(getLocation());
                    for (Cell cell : neighbours) {
                        if (!cell.isDiseased()) {
                            cell.setIsDiseased(true);
                        }
                    }
                    // set the cell to be dead
                    setNextState(false);
                    setIsDiseased(false);
                    diseasedAge = 0;
                }
            }
        } else {
            setColor(originalColor);
            setIsDiseased(false);
            diseasedAge = 0;
        }
    }
}

  

 /*
  * Set the original color of the cell
  */
  public void setOriginalColor(Color col) {
    originalColor= col;
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

  /*
   * Handle the age of the cell based on the next state
   */
  public void handleAge() {
    if (nextAlive) {
      incrementAge();
    } else {
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

  /*
   *  Set the cell to diseased or not
   */
  public void setIsDiseased(boolean value) {
    isDiseased = value;
  }

  /*
   * Returns whether the cell is diseased or not
   */
  public boolean isDiseased() {
    return isDiseased;
  }
}
