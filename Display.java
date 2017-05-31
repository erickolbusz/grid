import java.util.Random;
import javax.swing.JOptionPane;

public class Display {
	public static void main(String[] args) {
		Random rand = new Random();
		int width = 30;
		int height = 30;
		int maxClock = 0;

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

			if (rand.nextInt(10) < 9) {
				//carnivores move 90% of the time
				w.moveCarnivores();
			}
			// if (rand.nextInt(2) == 0) {
			// 	//herbivores move 50% of the time
			// 	w.moveHerbivores();
			// }
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