import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.LinkedHashMap;
import java.util.Map;
import java.awt.Color;

/**
 * A graphical view of the simulation grid. The view displays a rectangle for
 * each location. Colors for each type of life form can be defined using the
 * setColor method.
 *
 * @author David J. Barnes, Michael Kölling & Jeffery Raphael
 * @version 2022.01.06 (1)
 */

public class SimulatorView extends JFrame {
    // Colors used for empty locations.
    private static final Color EMPTY_COLOR = Color.WHITE;

    // Color used for objects that have no defined color.
    private static final Color UNKNOWN_COLOR = Color.GRAY;

    // Text for generation GUI label
    private final String GENERATION_PREFIX = "Generation: ";

    // Text for population GUI label
    private final String POPULATION_PREFIX = "Population: ";

    //buttons for the simulation
    private JButton pause, resume, stop;


    // GUI labels
    private JLabel genLabel, population, infoLabel;

    // Extends the multi-line plain text view to be suitable for a single-line
    // editor view. (part of Swing)
    private FieldView fieldView;

    // A statistics object computing and storing simulation information
    private FieldStats stats;

    /**
     * Create a view of the given width and height.
     * @param height The simulation's height.
     * @param width  The simulation's width.
     */
    public SimulatorView(int height, int width) {
        stats = new FieldStats();
    
        setTitle("Life Simulation");
        genLabel = new JLabel(GENERATION_PREFIX, JLabel.CENTER);
        infoLabel = new JLabel("  ", JLabel.CENTER);
        population = new JLabel(POPULATION_PREFIX, JLabel.CENTER);
        pause = new JButton("Pause");
        resume = new JButton("Resume");
        stop = new JButton("Stop");
    
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
        setLocation(100, 50);
    
        fieldView = new FieldView(height, width);
    
        JPanel buttonPanel = new JPanel(new GridLayout(1, 3));
   
        buttonPanel.add(pause);
        buttonPanel.add(resume);
        buttonPanel.add(stop);
    
        Container contents = getContentPane();
        contents.setPreferredSize(new Dimension(400, 300));
        contents.setLayout(new BorderLayout());
    
        // Create a panel that contains generation
        JPanel infoPane = new JPanel(new BorderLayout());
        infoPane.add(genLabel, BorderLayout.WEST);
        infoPane.add(infoLabel, BorderLayout.CENTER);

        // Create the south panel that contains the things in the south
        JPanel southPanel = new JPanel(new GridLayout(2, 1));
        southPanel.add(buttonPanel);
        southPanel.add(population);
        contents.add(southPanel, BorderLayout.SOUTH);

        // Create the north panel that contains the things in the north
        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.add(infoPane, BorderLayout.CENTER);
        contents.add(northPanel, BorderLayout.NORTH);
    
        contents.add(fieldView, BorderLayout.CENTER);

    
        pack();
        setVisible(true);
    }
    

    /**
     * Display a short information label at the top of the window.
     */
    public void setInfoText(String text) {
        infoLabel.setText(text);
    }


 
    /**
     * Show the current status of the field.
     * @param generation The current generation.
     * @param field The field whose status is to be displayed.
     */
    public void showStatus(int generation, Field field) {
      if (!isVisible()) {
        setVisible(true);
      }

      genLabel.setText(GENERATION_PREFIX + generation);
      stats.reset();
      fieldView.preparePaint();

      for (int row = 0; row < field.getDepth(); row++) {
        for (int col = 0; col < field.getWidth(); col++) {
          Cell cell = field.getObjectAt(row, col);

          if (cell != null && cell.isAlive()) {
            stats.incrementCount(cell.getClass());
            fieldView.drawMark(col, row, cell.getColor(), cell.getAge());
          }
          else {
            fieldView.drawMark(col, row, EMPTY_COLOR, 0);
          }
        }
      }

      stats.countFinished();
      population.setText(POPULATION_PREFIX + stats.getPopulationDetails(field));
      fieldView.repaint();
    }

    //get population details
    public String getPopulationDetails(Field field) {
      return stats.getPopulationDetails(field);
    }

    /**
     * Determine whether the simulation should continue to run.
     * @return true If there is more than one species alive.
     */
    public boolean isViable(Field field) {
        return stats.isViable(field);
    }

    /**
     * Provide a graphical view of a rectangular field. This is
     * a nested class (a class defined inside a class) which
     * defines a custom component for the user interface. This
     * component displays the field.
     * This is rather advanced GUI stuff - you can ignore this
     * for your project if you like.
     */
    private class FieldView extends JPanel {
        private final int GRID_VIEW_SCALING_FACTOR = 6;
        private int gridWidth, gridHeight;
        private int xScale, yScale;
        Dimension size;
        private Graphics g;
        private Image fieldImage;

        /**
         * Create a new FieldView component.
         */
        public FieldView(int height, int width) {
            gridHeight = height;
            gridWidth = width;
            size = new Dimension(0, 0);
        }

        /**
         * Tell the GUI manager how big we would like to be.
         */
        public Dimension getPreferredSize() {
            return new Dimension(gridWidth * GRID_VIEW_SCALING_FACTOR,
                                 gridHeight * GRID_VIEW_SCALING_FACTOR);
        }

        /**
         * Prepare for a new round of painting. Since the component
         * may be resized, compute the scaling factor again.
         */
        public void preparePaint() {
            if (!size.equals(getSize())) {  // if the size has changed...
                size = getSize();
                fieldImage = fieldView.createImage(size.width, size.height);
                g = fieldImage.getGraphics();

                xScale = size.width / gridWidth;
                if (xScale < 1) {
                    xScale = GRID_VIEW_SCALING_FACTOR;
                }
                yScale = size.height / gridHeight;
                if (yScale < 1) {
                    yScale = GRID_VIEW_SCALING_FACTOR;
                }
            }
        }

        /**
         * Paint on grid location on this field in a given color.
         */
        public void drawMark(int x, int y, Color color, int age) {
            g.setColor(color);
            g.fillRect(x * xScale, y * yScale, xScale-1, yScale-1);
            if (age >= 0) {
                g.setColor(Color.WHITE);
                g.drawString(Integer.toString(age), x * xScale + 2, y * yScale + yScale - 4);
            }
        }
    

        /**
         * The field view component needs to be redisplayed. Copy the
         * internal image to screen.
         */
        public void paintComponent(Graphics g) {
            if (fieldImage != null) {
                Dimension currentSize = getSize();
                if (size.equals(currentSize)) {
                    g.drawImage(fieldImage, 0, 0, null);
                }
                else {
                    // Rescale the previous image.
                    g.drawImage(fieldImage, 0, 0, currentSize.width, currentSize.height, null);
                }
            }
        }
    }
}
