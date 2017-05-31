import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class DisplayGUI extends JFrame{
    private JLabel[][] labels;
    private JLabel cycle;
    private JPanel top, grid;
    private final Font font = new Font("Calibri", Font.PLAIN, 24);

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

    public void setLabelsText(World world){
        for(int i=0; i<labels.length; i++)
        {
            for(int j=0; j<labels[i].length; j++)
            {
                if(world.getEntity(i,j) instanceof Carnivore)
                    labels[i][j].setText("@");
                else if(world.getEntity(i,j) instanceof Herbivore)
                    labels[i][j].setText("&");
                else if(world.getEntity(i,j) instanceof Plant)
                    labels[i][j].setText("*");
                else if(world.getEntity(i,j) instanceof Herder)
                    labels[i][j].setText("O");
                else
                    labels[i][j].setText(".");
                labels[i][j].setFont(font);
            }
        }
    }

    public void setClockCycle(int s){
        cycle.setText("Clock cycle: " + s);
    }
}
