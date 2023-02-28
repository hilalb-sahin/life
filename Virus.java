import java.awt.Color;
import java.util.List;

/**
 * A virus that has a parasitic relationship with mycoplasma.
 * It extends the Cell class, which represents a single cell on a grid.
 * 
 * @author Ranim Ghebache, Hilal Sahin
 * @version 2023.02.28 (1)
 */
public class Virus extends Cell {
    
    /**
     * Create a new Virus with the specified field, location, and color.
     *
     * @param field The field currently occupied by the virus.
     * @param location The location within the field where the virus is created.
     * @param col The color of the virus.
     */
    public Virus(Field field, Location location, Color col) {
        // Call the constructor of the superclass (Cell) to initialize the object
        super(field, location, col);
    }
    
    /**
     * This is how the virus decides if it's alive or not and what to do.
     * It combines the act() method in the Cell class.
     */
    public void act() {
        // Call the act() method of the superclass (Cell) to update the state
        super.act();
        
        // Get the neighboring cells that are alive
        List<Cell> neighbours = getField().getLivingNeighbours(getLocation());
        
        // Assume that the virus will not survive until the next round
        setNextState(false);

        // Check if the virus is currently alive
        if (isAlive()) {
            // If the virus has less than 2 neighbors, it dies
            if (neighbours.size() < 2) {
                setNextState(false);
            }
            // If the virus has 2 or 3 neighbors, it survives to the next round
            else if (neighbours.size() == 2 || neighbours.size() == 3) {
                setNextState(true);
            }
        }
        // If the virus is currently dead
        else {
            // If there is a neighboring cell that is a mycoplasma, the virus becomes alive 
            for (Cell cell : neighbours) {
                if (cell instanceof Mycoplasma) {
                    setNextState(true);
                }
            }
            // If the virus has exactly 3 neighbors, it becomes alive 
            if (neighbours.size() == 3) {
                setNextState(true);
            }
        }
    }
}
