import java.util.List;
import java.util.Random;
import java.awt.Color;

public class Ndplasma extends Cell {
    public Ndplasma(Field field, Location location, Color col) {
        super(field, location, col);
    }

    // randomize number from 0 to 10
    public int randomize() {
        Random rand = new Random();
        int randomNum = rand.nextInt(10);
        return randomNum;
    }

    public void act() {
        List<Cell> neighbours = getField().getLivingNeighbours(getLocation());
        // it dies next raund by default
        setNextState(false);

        if (randomize() > 5) {
            if (isAlive()) {

                if (neighbours.size() < 2) {
                    setNextState(false);

                }
            } else {
                resetAge();
                if (neighbours.size() == 1) {
                    setNextState(true);
                }

            }
        } else {
            if (isAlive()) {

                if (neighbours.size() < 1) {
                    setNextState(false);
                }
            } else {
                resetAge();
                if (neighbours.size() == 2) {
                    setNextState(true);
                }

            }

        }
    }

}
