import java.awt.*;
import java.util.Random;
import javax.swing.*;

public class DisplayDriver {
	public static void main(String[] args) {
		Random rand = new Random();
		int width = 30;
		int height = 30;
		int maxClock = 0;

		JOptionPane.showMessageDialog(null,
								"You are the herder. Your job is to lead your herbivores to plants.\n" +
										"More herbivores will give birth if they are fed and are old enough.\n" +
										"The objective of the game is to avoid the carnivores until the final cycle.\n" +
										"Click OK to see the legend and setup the dimensions of your grid.",
									"Instructions", JOptionPane.INFORMATION_MESSAGE);

		JLabel[] icons = new JLabel[4];
		icons[0] = new JLabel("-->Herder", new ImageIcon(DisplayDriver.class.getResource("icons/Herder.png")), JLabel.LEFT);
		icons[1] = new JLabel("-->Herbivore", new ImageIcon(DisplayDriver.class.getResource("icons/Herbivore.png")), JLabel.LEFT);
		icons[2] = new JLabel("-->Carnivores", new ImageIcon(DisplayDriver.class.getResource("icons/Carnivore.png")), JLabel.LEFT);
		icons[3] = new JLabel("-->Plants", new ImageIcon(DisplayDriver.class.getResource("icons/Plant.png")), JLabel.LEFT);
		JPanel panel = new JPanel();
		for(int i=0; i<icons.length; i++)
			panel.add(icons[i]);
		panel.setSize(500,500);
		JOptionPane.showMessageDialog(null, panel, "Legend", JOptionPane.INFORMATION_MESSAGE);

		boolean received = false;
		while (!received) {
			String input = JOptionPane.showInputDialog("Please enter the simulation width.");
			try {
				width = Integer.parseInt(input);
				if (width <= 0) {
					JOptionPane.showMessageDialog(null, "Please enter a positive integer.", "Error!", JOptionPane.ERROR_MESSAGE);
				}
				else {
					received = true;
				}
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(null, "Please enter a positive integer.", "Error!", JOptionPane.ERROR_MESSAGE);
			}
		}

		received = false;
		while (!received) {
			String input = JOptionPane.showInputDialog("Please enter the simulation height.");
			try {
				height = Integer.parseInt(input);
				if (height <= 0) {
					JOptionPane.showMessageDialog(null, "Please enter a positive integer.", "Error!", JOptionPane.ERROR_MESSAGE);
				}
				else {
					received = true;
				}
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(null, "Please enter a positive integer.", "Error!", JOptionPane.ERROR_MESSAGE);
			}
		}

		received = false;
		while (!received) {
			String input = JOptionPane.showInputDialog("Please enter the max number of clock cycles, or 0 for infinite.");
			try {
				maxClock = Integer.parseInt(input);
				if (maxClock < 0) {
					JOptionPane.showMessageDialog(null, "Please enter zero (for infinite) or higher.", "Error!", JOptionPane.ERROR_MESSAGE);
				}
				else {
					received = true;
				}
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(null, "Please enter an integer >= 0.", "Error!", JOptionPane.ERROR_MESSAGE);
			}
		}

		int clock = 1;
		World w = World.getInstance(width, height);

		DisplayGUI d = new DisplayGUI(w);

		while (true) {
			//print the world
			System.out.print("\033[H\033[2J");
			System.out.flush();
			System.out.print(w);
			System.out.print("\nClock cycle: "+clock+"\n");
			d.setClockCycle(clock);
			d.setLabelsText(w);

			w.moveCarnivores();

			if (clock%2 == 0) {
				//herbivores move 50% of the time
				w.moveHerbivores();
			}
			if (clock%3 == 0) {
				//age every 3 cycles
				w.age();
			}
			if (clock%4 == 0) {
				//add plants every 4 cycles
				w.addPlants();
			}


			if (clock == maxClock) {
				break;
			}
			clock++;

			//wait 400ms
			try {
    			Thread.sleep(400);
			} catch(InterruptedException e) {
    			Thread.currentThread().interrupt();
			}
		}
	}
}