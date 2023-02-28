import java.awt.Color;
import java.util.List;

/*
 * A simple model of a bacteria.
 * Bacteria age and act and die 
 * Acts in a non-deterministic way 
 * @author Ranim Ghebache, Hilal Sahin
 * @version 2023.02.28 (1)
 */
public class Bacteria extends Cell {
    /**
     * Constructor for creating a Bacteria object.
     * @param field The field where the bacteria is located.
     * @param location The location of the bacteria in the field.
     * @param col The color of the bacteria.
     */
    public Bacteria(Field field, Location location, Color col) {
        super(field, location, col);
        setOriginalColor(col);
    }
    /**
     * Method for determining the next state of the bacteria based on its neighbors.
     */
    public void act() {
        // combine the act of the super class
        super.act();

        List<Cell> neighbours = getField().getLivingNeighbours(getLocation());
        setNextState(false);

        // acts differently randomly
        if (randomize() < 5) {
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
            if (isAlive()) {
                if (neighbours.size() < 3) {
                    setNextState(false);
                } else {
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
