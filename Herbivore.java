public class Herbivore extends Animal {

	public Herbivore(int x, int y) {
		super(x,y);
	}

	@Override
	public String toString() {
		return "&";
	}

	public void eat(Plant p) {
		energy += 5;
	}

	@Override
	public void move(Entity[][] grid) {
		//checks to see if the herbivore can give birth, else it moves
		boolean isBirthing = false;
		if (age >= 5 && age <= 12 && energy >= 9) {
			//needs an empty adjacent space to spawn a new herbivore
			for (int dx = -1; dx <= 1; dx++) {
				for (int dy = -1; dy <= 1; dy++) {
					try {
						if (grid[x+dx][y+dy] == null && !isBirthing) {
							//give birth
							isBirthing = true;
							energy = energy-4;
							grid[x+dx][y+dy] = new Herbivore(x+dx,y+dy);
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
			//if hungry and not giving birth, try to find a plant nearby to eat
			boolean isHunting = false;
			if (isHungry()) {
				for (int dx = -1; dx <= 1; dx++) {
					for (int dy = -1; dy <= 1; dy++) {
						try {
							if (grid[x+dx][y+dy] instanceof Plant && !isHunting) {
								//found adjacent plant
								isHunting = true;
								Plant p = (Plant)grid[x+dx][y+dy];

								eat(p);
								grid[x][y] = null;
								grid[x+dx][y+dy] = this;
								x = x+dx;
								x = y+dy;
							}
						} catch(ArrayIndexOutOfBoundsException e) {
							//off the grid
						}
					}
				}
			}
			if (!isHunting) {
				//didn't find food nearby
				moveRandomly(grid);
			}
		}
	}
}