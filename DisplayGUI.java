import javax.swing.*;
import java.awt.*;

public class DisplayGUI extends JFrame{
    private JLabel[][] labels;
    private final Font font = new Font("Calibri", Font.PLAIN, 24);

    public DisplayGUI(World world)
    {
        super("GridWorld");
        Container c = getContentPane();
        c.setLayout(new GridLayout(world.HEIGHT, world.WIDTH));
        labels = new JLabel[world.HEIGHT][world.WIDTH];

        for(int i=0; i<labels.length; i++)
        {
            for(int j=0; j<labels[i].length; j++)
            {
                labels[i][j] = new JLabel("", JLabel.CENTER);
                c.add(labels[i][j]);
            }
        }
        setSize(500,500);
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
                else
                    labels[i][j].setText(".");
                labels[i][j].setFont(font);
            }
        }
    }
}
