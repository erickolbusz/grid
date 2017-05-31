import static java.lang.Math.sqrt;

public class Carnivore extends Animal {
	protected int visionRadius;
	//protected float visionScale; //animal can see perfectly within Radius then vision drops off with Scale
	protected boolean isCharging;
	protected final int chargeDist = 5;

	public Carnivore(int x, int y) {
		super(x,y);

		visionRadius = 3;
		//visionScale = 0.5;

	}

	@Override
	public String toString() {
		return "@";
	}

	public void eat(Herbivore h) {
		//eating a healty animal gives more energy
		energy += (7 + h.getEnergy()/2);
	}

	@Override
	public void growOlder() {
		age++;
		if (age <= 10) {
			visionRadius++;
			//visionScale *= 0.8;
		}
		else {
			visionRadius--;
			//visionScale *= 1.2; //old carnivores 
		}
	}

	@Override
	public void move(Entity[][] grid) {
		//checks to see if the carnivore can give birth, else it moves
		boolean gaveBirth = giveBirth(grid);
		if (!gaveBirth) {
			if (isHungry()) {
				//try to find a herbivore nearby to eat
				boolean hunting = hunt(grid);
				if (!hunting) {
					//didn't find prey nearby
					moveRandomly(grid);
				}
			}
			else {
				//not hungry
				moveRandomly(grid);
			}
		}
	}

	public boolean giveBirth(Entity[][] grid) {
		if (age >= 5 && age <= 12 && energy >= 9) {
			//needs an empty adjacent space to spawn a new carnivore
			for (int dx = -1; dx <= 1; dx++) {
				for (int dy = -1; dy <= 1; dy++) {
					try {
						if (grid[x+dx][y+dy] == null) {
							//give birth
							energy = energy-4;
							grid[x+dx][y+dy] = new Carnivore(x+dx,y+dy);
							//newborn cannot move this cycle
							((Animal)grid[x+dx][y+dy]).startMoving();
							return true;
						}
					} catch(ArrayIndexOutOfBoundsException e) {
						//off the grid
					}
				}
			}
		}
		return false;
	}

	public boolean hunt(Entity[][] grid) {
		int closestHerbivoreX, closestHerbivoreY;
		int minDistSq = -1;
		for (int dx = -1*visionRadius; dx <= visionRadius; dx++) {
			for (int dy = -1*visionRadius; dy <= visionRadius; dy++) {
				if (dx*dx + dy*dy <= visionRadius*visionRadius) {
					try {
						if (grid[x+dx][y+dy] instanceof Herbivore) {
							//found adjacent herbivore
							int distSq = dx*dx + dy*dy;
							if (distSq < minDistSq) {
								minDistSq = distSq;
							}
						}
					} catch(ArrayIndexOutOfBoundsException e) {
						//off the grid
					}
				}
			}
		}
		if (minDistSq != -1) {
			//nearby prey exists
			if (closestHerbivoreX >= -1 && closestHerbivoreX <= 1 && closestHerbivoreY >= -1 && closestHerbivoreY) {
				//adjacent prey
				Herbivore h = (Herbivore)grid[x+closestHerbivoreX][y+closestHerbivoreY];
				eat(h);
				grid[x][y] = null;
				grid[x+closestHerbivoreX][y+closestHerbivoreY] = this;
				x = x+closestHerbivoreX;
				y = y+closestHerbivoreX;

			}
			int dx = 0;
			int dy = 0; 
			if (closestHerbivoreX/Math.sqrt(minDistSq) >= 0.71) {
				dx = 1;
			}
			if (closestHerbivoreX/Math.sqrt(minDistSq) <= -0.71) {
				dx = -1;
			}
			if (closestHerbivoreY/Math.sqrt(minDistSq) >= 0.71) {
				dy = 1;
			}
			if (closestHerbivoreY/Math.sqrt(minDistSq) <= -0.71) {
				dy = -1;
			}
			grid[x][y] = null;
			grid[x+dx][y+dy] = this;
			return true;
		}
		return false;
	}

}