import java.util.Random;

public class Animal extends Entity {
	protected int energy;
	protected boolean moving;

	public Animal(int x, int y) {
		this.x = x;
		this.y = y;
		maxAge = 15;

		age = 0;
		energy = 5;
		moving = false;
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

	public void move() {
		moving = true;
	}
	public boolean isMoving() {
		return moving;
	}
	public void stopMoving() {
		moving = false;
	}
}