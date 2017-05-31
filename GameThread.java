import java.util.Random;
import java.util.Scanner;
import java.util.InputMismatchException;

class GridThread extends Thread {
	private Thread t;
	private String name;

	GridThread(String name) {
		this.name = name;
	}

	public void run() {
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
			d.setClockCycle(clock);
			d.setLabelsText(w);

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

			//wait 400ms
			try {
				Thread.sleep(400);
			} catch(InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
	}

	public void start() {
		if(t == null) {
			t = new Thread(this, name);
			System.out.println("GridThread Started");
			t.start();
		}
	}
}

class InputThread extends Thread{
	private Thread t;
	private String name;

	InputThread(String name) {
		this.name = name;
	}

	public void run() {
		/* REPLACE THIS IMPLEMENTATION WITH KEYBOARD INPUTS */
		/* ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ */
		/* ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ */
		/* ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ */
		/* ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ */
		/* ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ */
		/* ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ */
		Scanner scanner = new Scanner(System.in);
		String input = "";
		
		System.out.println("InputThread Started");
		while(!(input.equals("exit"))) {
			System.out.println("InputThread: ");
			input = scanner.nextLine();
		}
		System.out.println("InputThread Finished");
	}

	public void start() {
		if(t == null){
			t = new Thread(this, name);
			System.out.println("InputThread Started");
			t.start();
		}
	}
}

public class GameThread {
	public static void main(String[] args) {
		GridThread game = new GridThread("GridThread");
		InputThread input = new InputThread("InputThread");
		game.start();
		input.start();	
	}
}