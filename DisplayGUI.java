import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class DisplayGUI extends JFrame{
    private JLabel[][] labels;
    private JLabel cycle;
    private JPanel top, grid;

    /**
     * Constructor for DisplayGUI class
     * @param world Instance of World passed by reference
     */
    public DisplayGUI(World world)
    {
        super("GridWorld");
        labels = new JLabel[world.HEIGHT][world.WIDTH];
        cycle = new JLabel();
        top = new JPanel();
        grid = new JPanel();
        Container c = getContentPane();
        c.setLayout(new BorderLayout());

        top.add(cycle);
        grid.setLayout(new GridLayout(world.HEIGHT, world.WIDTH));
        for(int i=0; i<labels.length; i++)
        {
            for(int j=0; j<labels[i].length; j++)
            {
                labels[i][j] = new JLabel("", JLabel.CENTER);
                grid.add(labels[i][j]);
            }
        }

        c.add(top, BorderLayout.NORTH);
        c.add(grid, BorderLayout.CENTER);
        addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if(key == KeyEvent.VK_UP) {
                    Herder.getInstance().moveUp(world.getGrid());
                }
                else if(key == KeyEvent.VK_DOWN) {
                    Herder.getInstance().moveDown(world.getGrid());
                }
                else if(key == KeyEvent.VK_LEFT) {
                    Herder.getInstance().moveLeft(world.getGrid());
                }
                else if(key == KeyEvent.VK_RIGHT) {
                    Herder.getInstance().moveRight(world.getGrid());
                }
                setLabelsText(world);
            }
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    /**
     * Sets the text/icon of each JLabel in the GUI to the correct text/icon
     * @param world Instance of World passed by reference
     */
    public void setLabelsText(World world){
        //Resize each image once, set the icons of labels later
        int width = 1000/world.WIDTH;
        int height = 1000/world.HEIGHT;
        //Carnivore resize
        ImageIcon carnivore = new ImageIcon(getClass().getResource("/icons/Carnivore.png"));
        Image smallCarn = carnivore.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT);
        carnivore = new ImageIcon(smallCarn);
        //Herbivore resize
        ImageIcon herbivore = new ImageIcon(getClass().getResource("/icons/Herbivore.png"));
        Image smallHerb = herbivore.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT);
        herbivore = new ImageIcon(smallHerb);
        //Plant resize
        ImageIcon plant = new ImageIcon(getClass().getResource("/icons/Plant.png"));
        Image smallPlant = plant.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT);
        plant = new ImageIcon(smallPlant);
        //Herder resize
        ImageIcon herder = new ImageIcon(getClass().getResource("/icons/Herder.png"));
        Image smallHerder = herder.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT);
        herder = new ImageIcon(smallHerder);
        //Set images
        for(int i=0; i<labels.length; i++)
        {
            for(int j=0; j<labels[i].length; j++)
            {
                if(world.getEntity(i,j) instanceof Carnivore) {
                    labels[i][j].setIcon(carnivore);
                }
                else if(world.getEntity(i,j) instanceof Herbivore) {
                    labels[i][j].setIcon(herbivore);
                }
                else if(world.getEntity(i,j) instanceof Plant) {
                    labels[i][j].setIcon(plant);
                }
                else if(world.getEntity(i,j) instanceof Herder) {
                    labels[i][j].setIcon(herder);
                }
                else {
                    labels[i][j].setIcon(null);
                }
            }
        }
    }

    /**
     * Sets the text for the JLabel that represents the clock cycle
     * @param s The current clock cycle
     */
    public void setClockCycle(int s){
        cycle.setText("Clock cycle: " + s);
    }
}
