import java.lang.Math;
import java.lang.Integer;
import java.util.Arrays;
import java.util.Comparator;

public class Carnivore extends Animal {
	protected int visionRadius;
	//protected float visionScale; //animal can see perfectly within Radius then vision drops off with Scale
	protected boolean isCharging;
	protected final int chargeDist = 3;

	public Carnivore(int x, int y) {
		super(x,y);
		isCharging = false;
		visionRadius = 8;
		//visionScale = 0.5;
		maxEnergy = 18;
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
			visionRadius += 2;
			//visionScale *= 0.8;
		}
		else {
			visionRadius--;
			//visionScale *= 1.2; //old carnivores 
		}
	}

	@Override
	public void move(Entity[][] grid) {
		//charging takes precendence
		if (isCharging && energy >= 5) {
			energy--; //charging makes you tired!
			hunt(grid);
		}
		else {
			//possibly too tired to keep charging
			isCharging = false;
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
		int closestHerbivoreX = 0;
		int closestHerbivoreY = 0;
		int minDistSq = Integer.MAX_VALUE;
		for (int dx = -1*visionRadius; dx <= visionRadius; dx++) {
			for (int dy = -1*visionRadius; dy <= visionRadius; dy++) {
				if (Math.pow(dx,2) + Math.pow(dy,2) <= Math.pow(visionRadius,2)) {
					try {
						if (grid[x+dx][y+dy] instanceof Herbivore) {
							//found nearby herbivore
							int distSq = dx*dx + dy*dy;
							if (distSq < minDistSq) {
								minDistSq = distSq;
								closestHerbivoreX = dx;
								closestHerbivoreY = dy;
							}
						}
					} catch(ArrayIndexOutOfBoundsException e) {
						//off the grid
					}
				}
			}
		}
		if (closestHerbivoreX != 0 || closestHerbivoreY != 0) {
			//nearby prey exists
			if (closestHerbivoreX >= -1 && closestHerbivoreX <= 1 && closestHerbivoreY >= -1 && closestHerbivoreY <= 1) {
				//adjacent prey
				Herbivore h = (Herbivore)grid[x+closestHerbivoreX][y+closestHerbivoreY];
				eat(h);
				grid[x][y] = null;
				grid[x+closestHerbivoreX][y+closestHerbivoreY] = this;
				x = x+closestHerbivoreX;
				y = y+closestHerbivoreY;
				return true;
			}

			if (minDistSq <= chargeDist*chargeDist) {
				//prey is close enough to chase
				isCharging = true;
			}
			else {
				isCharging = false;
			}

			//try all 8 moves prioritizing the path to the prey
			//can possibly trample any plants blocking the path
			int[][] moves = new int[8][2];
			int i = 0;
			for (int dx = -1; dx <= 1; dx++) {
				for (int dy = -1; dy <= 1; dy++) {
					if (dx != 0 || dy != 0) {
						moves[i][0] = dx;
						moves[i][1] = dy;
						i++;
					}
				}
			}

			Arrays.sort(moves, new Comparator<int[]>() {
			    @Override
			    public int compare(int[] move1, int[] move2) {
					double d1 = Math.pow(x-move1[0],2) + Math.pow(y-move1[1],2);
					double d2 = Math.pow(x-move2[0],2) + Math.pow(y-move2[1],2);
					if (d1 < d2) {
						return -1;
					}
					if (d1 > d2) {
						return 1;
					}
					return 0;
				}
			});

			for(int j=0; j<9; j++) {
				int dx = moves[j][0];
				int dy = moves[j][1];
				try {
					if (grid[x+dx][y+dy] == null || (isCharging && grid[x+dx][y+dy] instanceof Plant)) {
						//empty space OR trampled plant
						grid[x][y] = null;
						grid[x+dx][y+dy] = this;
						x = x+dx;
						y = y+dy;
						return true;
					}
				} catch(ArrayIndexOutOfBoundsException e) {
					//off the grid
				}
			}
		}
		return false;
	}

}