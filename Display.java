import java.util.Random;
import java.util.Scanner;
import java.util.InputMismatchException;

public class Display {
	public static void main(String[] args) {
		Random rand = new Random();
		Scanner s = new Scanner(System.in);
		int width = 30;
		int height = 30;
		int maxClock = 0;

		System.out.print("Please enter the simulation width: ");
		boolean received = false;
		while (!received) {
			try {
				width = s.nextInt();
				if (width <= 0) {
					System.out.println("Please enter a positive integer.");
				}
				else {
					received = true;
				}
			} catch (InputMismatchException e) {
				System.out.println("Please enter a positive integer.");
			}
		}

		System.out.print("Please enter the simulation height: ");
		received = false;
		while (!received) {
			try {
				height = s.nextInt();
				if (height <= 0) {
					System.out.println("Please enter a positive integer.");
				}
				else {
					received = true;
				}
			} catch (InputMismatchException e) {
				System.out.println("Please enter a positive integer.");
			}
		}

		System.out.print("Please enter the max number of clock cycles, or 0 for infinite: ");
		received = false;
		while (!received) {
			try {
				maxClock = s.nextInt();
				if (maxClock < 0) {
					System.out.println("Please enter zero (for infinite) or higher.");
				}
				else {
					received = true;
				}
			} catch (InputMismatchException e) {
				System.out.println("Please enter an integer >= 0.");
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


			if (rand.nextInt(10) < 9) {
				//carnivores move 90% of the time
				w.moveCarnivores();
			}
			if (rand.nextInt(2) == 0) {
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

			d.setLabelsText(w);

			//wait 400ms
			try {
    			Thread.sleep(400);
			} catch(InterruptedException e) {
    			Thread.currentThread().interrupt();
			}
		}
	}
}