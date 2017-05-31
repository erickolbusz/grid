import java.util.Random;
import java.util.ArrayList;

public class Animal extends Entity {
	protected int energy, maxEnergy;
	protected boolean moving;
	protected Random rand;
	/**
	*	Constructor for Animal Class
	*	@param x X coordinate for Animal object
	*	@param y Y Coordinate for Animal object
	*/
	public Animal(int x, int y) {
		this.x = x;
		this.y = y;
		maxAge = 15;
		maxEnergy = 10;

		age = 0;
		energy = 5;
		moving = false;

		//for random movement
		rand = new Random();
	}

	public void eat(Entity food) {
	}
	/**
	* 	Retrieve energy of Animal
	*	@return energy variable
	*/
	public int getEnergy() {
		return energy;
	}
	/**
	*	Set energy of Animal
	*	@param energy amount of energy added
	*/
	public void setEnergy(int energy) {
		this.energy = energy;
	}
	/**
	*	Check if Animal is hungry
	*	@return whether energy is not at capacity
	*/
	public boolean isHungry() {
		return (energy < maxEnergy);
	}
	/**
	*	Set x coordinate of Animal
	*	@param x X coordinate
	*/
	public void setX(int x) {
		this.x = x;
	}
	/**
	*	Set y coordinate of Animal
	*	@param y Y coordinate
	*/
	public void setY(int y) {
		this.y = y;
	}
	/**
	*	Move Animal
	*	@param grid grid that holds all entities
	*/
	public void move(Entity[][] grid) {
		moveRandomly(grid);
	}
	/**
	*	Set animal to "finished moving"
	*/
	public void startMoving() {
		moving = true;
	}
	/**
	*	Check if Animal has moved already
	*	@param x X coordinate for Animal object
	*	@return whether animal moved or not already
	*/
	public boolean isMoving() {
		return moving;
	}
	/**
	*	Reset animal to "has not moved"
	*/
	public void stopMoving() {
		moving = false;
	}
	/**
	*	Moves animal randomly if there are no priority moves like eating food
	*	@param grid grid that holds all Entities
	*/
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