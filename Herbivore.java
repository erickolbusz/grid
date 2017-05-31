import java.util.Random;
import javax.swing.JOptionPane;

public class Herbivore extends Animal {
	private Herbivore prevHerb, nextHerb; // following herbivore
	Random rand = new Random();
	public Herbivore(int x, int y) {
		super(x,y);
	}

	@Override
	public String toString() {
		return "&";
	}

	public void eat(int energy) {
		this.energy += energy;
	}

	public Herbivore getNextHerb() {
		return nextHerb;
	}

	public Herbivore getPrevHerb() {
		return prevHerb;
	}
	public void setNextHerb(Herbivore nextHerb) {
		this.nextHerb = nextHerb;
	}

	public void setPrevHerb(Herbivore prevHerb) {
		this.prevHerb = prevHerb;
	}

	public void age(Entity[][] grid) {
		int X = this.getX();
		int Y = this.getY();
		growOlder();
		if (getAge() + 3*rand.nextGaussian() > getMaxAge()) {
			//died from old age
			grid[X][Y] = null;
			// move nextHerbs to current location
			if(nextHerb != null) {
				if(prevHerb != null) {
					nextHerb.setPrevHerb(prevHerb);
					prevHerb.setNextHerb(nextHerb);
				}
				nextHerb.move(grid, X, Y);
			}
		}
		setEnergy(getEnergy()-1);
		if (getEnergy() < 1) {
			//starved
			grid[X][Y] = null;
			// move nextHerbs to current location
			if(nextHerb != null) {
				if(prevHerb != null) {
					nextHerb.setPrevHerb(prevHerb);
					prevHerb.setNextHerb(nextHerb);
				} else if(prevHerb == null) { // firstHerb
					Herder.getInstance().setFirstHerb(nextHerb);
				}
				nextHerb.move(grid, X, Y);
			} else if(nextHerb == null) { // no nextHerb
				if(prevHerb != null) {
					prevHerb.setNextHerb(null);
				} else if(prevHerb == null) { // no more herbs
					//Herder.getInstance().setFirstHerb(null);
					//JOptionPane.showMessageDialog(null, "All of your herbivores died. Game Over!", "GAME OVER!", JOptionPane.WARNING_MESSAGE);
				}
			}
		}
	}
    			
	public void move(Entity[][] grid, int newX, int newY) {
		// following what is before int
		int X = this.getX();
		int Y = this.getY();
		grid[X][Y] = null;
		grid[newX][newY] = this;
		this.setX(newX);
		this.setY(newY);
		if(nextHerb != null)
			nextHerb.move(grid, X, Y);

		//checks to see if the herbivore can give birth, else it moves
		boolean isBirthing = false;
		if (age >= 5 && age <= 12 && energy >= 9) {

			// check herbivore with no follower
			Herbivore temp = this;
			Herbivore baby;
			while(temp.getNextHerb() != null)
				temp = temp.getNextHerb();
			int endX = temp.getX();
			int endY = temp.getY();
			//needs an empty adjacent space to spawn a new herbivore
			for (int dx = -1; dx <= 1; dx++) {
				for (int dy = -1; dy <= 1; dy++) {
					try {
						if (grid[endX+dx][endY+dy] == null && !isBirthing) {// if (grid[x+dx][y+dy] == null && !isBirthing) {
							//give birth
							isBirthing = true;
							energy = energy-4;
							baby = new Herbivore(endX+dx,endY+dy);
							grid[endX+dx][endY+dy] = baby;
							temp.setNextHerb(baby);
							baby.setPrevHerb(temp);
						}
					} catch(ArrayIndexOutOfBoundsException e) {
						//off the grid
					}
				}
			}
		}
	}
}