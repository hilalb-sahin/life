import java.awt.Color;
import java.util.List;
import java.util.Random;

/**
 * Has parasitic relationship with mycoplasma
 *
 * @author David J. Barnes, Michael Kölling & Jeffery Raphael
 * @version 2022.01.06 (1)
 */

public class Virus extends Cell {

  /**
   * Create a new Mycoplasma.
   *
   * @param field    The field currently occupied.
   * @param location The location within the field.
   */
  public Virus(Field field, Location location, Color col) {
    super(field, location, col);
  }

  /**
   * This is how the Mycoplasma decides if it's alive or not
   */
  public void act() {
    List<Cell> neighbours = getField().getLivingNeighbours(getLocation());
    setNextState(false);

    if (isAlive()) {
      
      if (neighbours.size() < 2) {
        setNextState(false);
        resetAge();

      } else if (neighbours.size() == 2 || neighbours.size() == 3) {
        setNextState(true);
        incrementAge();

      }
    } else {
      //if neighbouring cell is mycoplasma then become alive (positive effect on virus)
      for (Cell cell : neighbours) {
        if (cell instanceof Mycoplasma) {
          setNextState(true);
          incrementAge();
        }
      }
      resetAge();
      if (neighbours.size() == 3)
        setNextState(true);
        incrementAge();
    }
  }

}