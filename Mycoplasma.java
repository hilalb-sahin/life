import java.awt.Color;
import java.util.List;
import java.util.Random;

/**
 * Simplest form of life.
 * Fun Fact: Mycoplasma are one of the simplest forms of life. A type of
 * bacteria, they only have 500-1000 genes! For comparison, fruit flies have
 * about 14,000 genes.
 *
 * @author David J. Barnes, Michael Kölling & Jeffery Raphael
 * @version 2022.01.06 (1)
 */

public class Mycoplasma extends Cell {

	/**
	 * Create a new Mycoplasma.
	 *
	 * @param field    The field currently occupied.
	 * @param location The location within the field.
	 */
	public Mycoplasma(Field field, Location location, Color col) {
		super(field, location, col);
	}

	/**
	 * Determines the next state of a Mycoplasma cell based on its neighbors.
	 * If alive, cell dies due to underpopulation (< 2 neighbors) or survives to
	 * next generation (2 or 3 neighbors).
	 * If dead, cell comes to life due to having exactly 3 living neighbors.
	 */
	public void act() {
		// call act method of super class
		super.act();

		// get the living neighbors of the Mycoplasma cell
		List<Cell> neighbours = getField().getLivingNeighbours(getLocation());

		// set the next state of the Mycoplasma cell to dead
		setNextState(false);

		if (isAlive()) {
			// check if any neighbouring cell is a Virus cell, if yes, then Mycoplasma cell
			// dies and age is reset (parasitic relationship)
			for (Cell cell : neighbours) {
				if (cell instanceof Virus) {
					setNextState(false);
					// exit loop once a virus is found as no other virus cell should matter for this
					// check
					break;
				}
			}
			// determine whether the Mycoplasma cell dies or survives based on the number of
			// living neighbors
			if (neighbours.size() < 2) {
				setNextState(false);
			} else if (neighbours.size() == 2 || neighbours.size() == 3) {
				setNextState(true);
			}
		} else {
			// reset the age of the Mycoplasma cell and determine whether it comes to life
			// based on the number of living neighbors
			resetAge();
			if (neighbours.size() == 3) {
				setNextState(true);
			}
		}
	}

}
