import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.awt.Color;

/**
 * A Life (Game of Life) simulator, first described by British mathematician
 * John Horton Conway in 1970.
 *
 * @author David J. Barnes, Michael Kölling & Jeffery Raphael
 * @version 2022.01.06 (1)
 */

public class Simulator {
  // The default width for the grid.
  private static final int DEFAULT_WIDTH = 100;

  // The default depth of the grid.
  private static final int DEFAULT_DEPTH = 100;

  // The probability that a Mycoplasma is alive
  private static final double MYCOPLASMA_ALIVE_PROB = 0.2;

  // The probability that a Salmonella is alive
  private static final double SALMONELLA_ALIVE_PROB = 0.3;

  // the probability that a bacteria is alive
  private static final double BACTERIA_ALIVE_PROB = 0.2;
  // List of cells in the field.
  private List<Cell> cells;

  // The current state of the field.
  private Field field;

  // The current generation of the simulation.
  private int generation;

  // A graphical view of the simulation.
  private SimulatorView view;

  /**
   * Execute simulation
   */
  public static void main(String[] args) {
    Simulator sim = new Simulator();
    sim.simulate(100);
    System.out.println("Simulation finished");

  }

  public void getCellsClassName() {
    for (Cell cell : cells) {
      System.out.println(cell.getClass().getName());
    }
  }

  // get the amount of Salmonella cells
  public void getSizeofSalmonellainCells() {
    int salmonellaCells = 0;
    for (Cell cell : cells) {
      if (cell.getClass().getName().equals("Salmonella")) {
        salmonellaCells++;
      }
    }
    System.out.println("Salmonella cells: " + salmonellaCells);
  }

  // get the amount of Mycoplasma cells
  public void getSizeofMycoplasmainCells() {
    int mycoplasmaCells = 0;
    for (Cell cell : cells) {
      if (cell.getClass().getName().equals("Mycoplasma")) {
        mycoplasmaCells++;
      }
    }
    System.out.println("Mycoplasma cells: " + mycoplasmaCells);
  }

  public void getDeadCellsSize() {
    int deadCells = 0;
    for (Cell cell : cells) {
      if (!cell.isAlive()) {
        deadCells++;
      }
    }
    System.out.println("Dead cells: " + deadCells);
  }

  /**
   * Construct a simulation field with default size.
   */
  public Simulator() {
    this(DEFAULT_DEPTH, DEFAULT_WIDTH);
  }

  /**
   * Create a simulation field with the given size.
   * 
   * @param depth Depth of the field. Must be greater than zero.
   * @param width Width of the field. Must be greater than zero.
   */
  public Simulator(int depth, int width) {
    if (width <= 0 || depth <= 0) {
      System.out.println("The dimensions must be greater than zero.");
      System.out.println("Using default values.");
      depth = DEFAULT_DEPTH;
      width = DEFAULT_WIDTH;
    }

    cells = new ArrayList<>();
    field = new Field(depth, width);

    // Create a view of the state of each location in the field.
    view = new SimulatorView(depth, width);

    // Setup a valid starting point.
    reset();
  }

  /**
   * Run the simulation from its current state for a reasonably long period,
   * (4000 generations).
   */
  public void runLongSimulation() {
    simulate(4000);

  }

  /**
   * Run the simulation from its current state for the given number of
   * generations. Stop before the given number of generations if the
   * simulation ceases to be viable.
   * 
   * @param numGenerations The number of generations to run for.
   */
  public void simulate(int numGenerations) {
    for (int gen = 1; gen <= numGenerations && view.isViable(field); gen++) {
      simOneGeneration();
      delay(500); // comment out to run simulation faster

      System.out.println("Generation: " + getGeneration());
      System.out.println("Number of cells: " + cells.size());
      System.out.println("Number of dead cells");
      getDeadCellsSize();
      System.out.println("Number of Mycoplasma cells");
      getSizeofMycoplasmainCells();
      System.out.println("Number of Salmonella cells");
      getSizeofSalmonellainCells();

    }

  }

  /**
   * Run the simulation from its current state for a single generation.
   * Iterate over the whole field updating the state of each life form.
   */
  public void simOneGeneration() {
    generation++;
    for (Iterator<Cell> it = cells.iterator(); it.hasNext();) {
      Cell cell = it.next();
      cell.act();
    }

    for (Cell cell : cells) {
      cell.updateState();
    }
    view.showStatus(generation, field);
  }

  /**
   * Reset the simulation to a starting position.
   */
  public void reset() {
    generation = 0;
    cells.clear();
    populate();

    // Show the starting state in the view.
    view.showStatus(generation, field);
  }

  /**
   * Randomly populate the field live/dead life forms
   */
  private void populate() {

    Random rand = Randomizer.getRandom();
    field.clear();

    for (int row = 0; row < field.getDepth(); row++) {
      for (int col = 0; col < field.getWidth(); col++) {
        // location is the every single spot inside the field
        Location location = new Location(row, col);
        System.out.println();

        // fill the field with myco if the random number is less than the probability
        if (rand.nextDouble() <= MYCOPLASMA_ALIVE_PROB) {
          Cell myco = new Mycoplasma(field, location, Color.BLUE);
          cells.add(myco);
        }

        // fill the field with sal if the random number is less than the probability
        else if (rand.nextDouble() <= SALMONELLA_ALIVE_PROB) {
          Cell sal = new Salmonella(field, location, Color.RED);
          cells.add(sal);
        }

        // fill the field with bac if the random number is less than the probability
        else if (rand.nextDouble() <= BACTERIA_ALIVE_PROB) {
          Cell bac = new Bacteria(field, location, Color.GREEN);
          cells.add(bac);

        }
        // fill the empty spots with dead cells so they can become alive later
        else {
          Salmonella sal = new Salmonella(field, location, Color.RED);
          cells.add(sal);
          sal.setDead();
        }
      }
    }

  }

  /**
   * Pause for a given time.
   * 
   * @param millisec The time to pause for, in milliseconds
   */
  private void delay(int millisec) {
    try {
      Thread.sleep(millisec);
    } catch (InterruptedException ie) {
      // wake up
    }
  }

  public int getGeneration() {
    return generation;
  }

}
