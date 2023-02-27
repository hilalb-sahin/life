import java.awt.Color;
import java.util.List;

/*
 * A simple model of a bacteria.
 * Bacteria age and act and die 
 * Acts in a non-deterministic way 
 */ 
public class Bacteria extends Cell {
    public Bacteria(Field field, Location location, Color col) {
        super(field, location, col);
        setOriginalColor(col);
    }

    public void act() {
        //combine the act of the super class
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
