import java.awt.Color;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * The Salmonella class represents a type of cell that can be present in the simulation.
 * It extends the Cell class and overrides its act() method to provide a specific behavior for Salmonella.
 * It also provides a constructor that takes in a field, location, and color for the cell.
 *
 * @author Ranim Ghebache, Hilal Sahin
 * @version 2023.02.28 (1)
 */
public class Salmonella extends Cell {

  /**
   * Creates a new Salmonella cell with the given field, location, and color.
   *
   * @param field    The field currently occupied by the cell.
   * @param location The location within the field.
   * @param col      The color of the cell.
   */
  public Salmonella(Field field, Location location, Color col) {
    super(field, location, col);
    setColor(new Color(255, 102, 102));
  }

  /**
   * Combines the act() method of the Cell class to provide specific behavior for Salmonella.
   * If the cell's age is less than 5, it may die or reproduce based on the number of living neighbors.
   * If the cell's age is greater than or equal to 5, it becomes black and can only reproduce if it has exactly one neighbor.
   */
  public void act() {
    super.act();
    List<Cell> neighbours = getField().getLivingNeighbours(getLocation());
    setNextState(false);
    if (getAge() < 5) {
      setColor(Color.gray);
      if (isAlive()) {
        if (neighbours.size() < 2) {
          setNextState(false);
        } else if (neighbours.size() == 2 || neighbours.size() == 3) {
          setNextState(true);
        }
      } else {
        if (neighbours.size() == 3) {
          setNextState(true);
        }
      }
    } else {
      setColor(Color.black);
      if (isAlive()) {
        if (neighbours.size() < 3) {
          setNextState(true);
        }
      } else {
        if (neighbours.size() == 1) {
          setNextState(true);
        }
      }
    }
  }

}
