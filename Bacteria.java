import java.awt.Color;
import java.util.List;


public class Bacteria extends Cell{
    public Bacteria(Field field, Location location, Color col) {
        super(field, location, col);
    }

    public void act() {
        List<Cell> neighbours = getField().getLivingNeighbours(getLocation());
        setNextState(false);
        if (isAlive()) {
            if (neighbours.size() < 2) {
                setNextState(false);
            } else if (neighbours.size() == 2 || neighbours.size() == 3) {
                setNextState(true);
            }
        } else {
            if (neighbours.size() == 3)
                setNextState(true);
        }
    }
    
}
