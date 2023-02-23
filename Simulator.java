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
  private static final int DEFAULT_WIDTH = 25;

  // The default depth of the grid.
  private static final int DEFAULT_DEPTH = 25;

  private static final double MYCOPLASMA_ALIVE_PROB = 0.2;
  private static final double SALMONELLA_ALIVE_PROB = 0.1;
  private static final double BACTERIA_ALIVE_PROB = 0.8;
  private static final double VIRUS_ALIVE_PROB = 0.4;
  private static final double NDP_ALIVE_PROB = 0.3;

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
    Simulator sim = new Simulator(25, 25);
    sim.simulate(100);
    System.out.println("Simulation finished");

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
    for (int gen = 1; gen <= numGenerations && view.isViable(field) && getContinueSimulation(); gen++) {
      simOneGeneration();
      delay(500);
      System.out.println(view.getPopulationDetails(field));
      while (!getContinueSimulation()) {
          // if the loop should be paused, wait until continueSimulation is true again
          delay(50000);
      }
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
          // purple
          Cell myco = new Mycoplasma(field, location, new Color(229, 204, 255));
          cells.add(myco);
        } else if (rand.nextDouble() <= SALMONELLA_ALIVE_PROB) {
          Cell sal = new Salmonella(field, location, Color.gray);
          cells.add(sal);
        }
        // fill the field with virus if the random number is less than the probability
        else if (rand.nextDouble() <= VIRUS_ALIVE_PROB) {
          Cell virus = new Virus(field, location, Color.pink);
          cells.add(virus);
        }
        // fill the field with npd if random number is less than the probability
        else if (rand.nextDouble() <= NDP_ALIVE_PROB) {
          Cell ndp = new Ndplasma(field, location, Color.orange);
          cells.add(ndp);
        }
        // fill with bacteria
        else if (rand.nextDouble() <= BACTERIA_ALIVE_PROB) {
          Cell bac = new Bacteria(field, location, Color.green);
          cells.add(bac);
        }
        // fill the empty spots with dead cells so they can become alive later
        else {
          Cell bac = new Bacteria(field, location, Color.green);
          cells.add(bac);
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

  // get generation
  public int getGeneration() {
    return generation;
  }

  //return boolean continueSimulation
  public boolean getContinueSimulation(){
    return view.getContinueSimulation();
  }

}
