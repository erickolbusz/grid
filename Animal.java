import java.util.Random;
import java.util.ArrayList;

public class Animal extends Entity {
	protected int energy;
	protected boolean moving;
	protected Random rand;

	public Animal(int x, int y) {
		this.x = x;
		this.y = y;
		maxAge = 15;

		age = 0;
		energy = 5;
		moving = false;

		//for random movement
		rand = new Random();
	}

	public void eat(Entity food) {
	}

	public int getEnergy() {
		return energy;
	}
	public void setEnergy(int energy) {
		this.energy = energy;
	}
	public boolean isHungry() {
		return (energy < 10);
	}

	public void setX(int x) {
		this.x = x;
	}
	public void setY(int y) {
		this.y = y;
	}

	public void move(Entity[][] grid) {
		moveRandomly(grid);
	}
	public void startMoving() {
		moving = true;
	}
	public boolean isMoving() {
		return moving;
	}
	public void stopMoving() {
		moving = false;
	}

	public void moveRandomly(Entity[][] grid) {
		//moves:
		//0 1 2
		//3 4 5
		//6 7 8
		//move[4] is the current location and will be false
		//since the location is taken by this animal
		int i = 0;
		ArrayList<Integer> moves = new ArrayList<Integer>();
		//append all possible moves to arraylist
		for (int dx = -1; dx <= 1; dx++) {
			for (int dy = -1; dy <= 1; dy++) {
				try {
					if (grid[x+dx][y+dy] == null) {
						moves.add(i);
					}
				} catch(ArrayIndexOutOfBoundsException e) {
					//off the grid
				}
				i++;
			}
		}
		//choose random move and follow it
		if (moves.size() != 0) {
			int chosenMove = moves.get(rand.nextInt(moves.size()));
			grid[x][y] = null;
			grid[x+(chosenMove/3)-1][y+(chosenMove%3)-1] = this;
			x = x+(chosenMove/3)-1;
			y = y+(chosenMove%3)-1;
		}
	}
}