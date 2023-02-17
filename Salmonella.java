import java.awt.Color;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * Simplest form of life.
 * Fun Fact: Mycoplasma are one of the simplest forms of life. A type of
 * bacteria, they only have 500-1000 genes! For comparison, fruit flies have
 * about 14,000 genes.
 * 
 * 
 *
 * @author David J. Barnes, Michael Kölling & Jeffery Raphael
 * @version 2022.01.06 (1)
 */

public class Salmonella extends Cell {


  /**
   * Create a new Mycoplasma.
   *
   * @param field    The field currently occupied.
   * @param location The location within the field.
   */

  public Salmonella(Field field, Location location, Color col) {
    super(field, location, col);
  }

  /**
   * This is how the Salmonella decides if it's alive or not
   * displays different way of action depending on cell age
   */
  public void act() {
    List<Cell> neighbours = getField().getLivingNeighbours(getLocation());
    setNextState(false);
    if (getAge() < 5) {
      setColor(Color.red);
      if (isAlive()) {
        if (neighbours.size() < 2) {
          setNextState(false);
          resetAge();
        } else if (neighbours.size() == 2 || neighbours.size() == 3) {
          setNextState(true);
          incrementAge();
        }
      } else {
        if (neighbours.size() == 3) {
          setNextState(true);
          incrementAge();
        }
      }
    } // if its age is more than 5, then it becomes black and behaviour changes
    else {
      
      setNextState(false);
      setColor(Color.black);

      if (isAlive()) {
        if (neighbours.size() < 2) {
          setNextState(false);
          resetAge();
        }
      } else {
        // become alive if you have 2 neighbours
        if (neighbours.size() == 3) {
          setNextState(true);
          incrementAge();
        }
      }
    }
  }

  public void test() {
    // all salmonella dies after 5 seconds
    CompletableFuture.delayedExecutor(5, TimeUnit.SECONDS).execute(() -> {
      // Your code here executes after 5 seconds!
      setNextState(false);
      System.out.println("Salmonella died");
    });
    System.out.println("test2");
  }

}
