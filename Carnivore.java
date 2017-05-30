public class Carnivore extends Animal {

	public Carnivore(int x, int y) {
		super(x,y);
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
	public void move(Entity[][] grid) {
		//checks to see if the carnivore can give birth, else it moves
		boolean isBirthing = false;
		if (age >= 5 && age <= 12 && energy >= 9) {
			//needs an empty adjacent space to spawn a new carnivore
			for (int dx = -1; dx <= 1; dx++) {
				for (int dy = -1; dy <= 1; dy++) {
					try {
						if (grid[x+dx][y+dy] == null && !isBirthing) {
							//give birth
							isBirthing = true;
							energy = energy-4;
							grid[x+dx][y+dy] = new Carnivore(x+dx,y+dy);
							//newborn cannot move this cycle
							((Animal)grid[x+dx][y+dy]).startMoving();
						}
					} catch(ArrayIndexOutOfBoundsException e) {
						//off the grid
					}
				}
			}
		}

		if (!isBirthing) {
			//if hungry and not giving birth, try to find a herbivore nearby to eat
			boolean isHunting = false;
			if (isHungry()) {
				for (int dx = -1; dx <= 1; dx++) {
					for (int dy = -1; dy <= 1; dy++) {
						try {
							if (grid[x+dx][y+dy] instanceof Herbivore && !isHunting) {
								//found adjacent herbivore
								isHunting = true;
								Herbivore h = (Herbivore)grid[x+dx][y+dy];

								eat(h);
								grid[x][y] = null;
								grid[x+dx][y+dy] = this;
								x = x+dx;
								y = y+dy;
							}
						} catch(ArrayIndexOutOfBoundsException e) {
							//off the grid
						}
					}
				}
			}
			if (!isHunting) {
				//didn't find prey nearby
				moveRandomly(grid);
			}
		}
	}
}