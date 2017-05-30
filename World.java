import java.util.Random;
import java.util.ArrayList;

public class World {
	private static World w;
	private Random rand;
	private Entity[][] grid;

	public int WIDTH;
	public int HEIGHT;
	public int NUMCARN;
	public int NUMHERB;
	public int NUMPLAN;
	public int PLANTSADDED;

    private World(int grid_width, int grid_height){
    	WIDTH = grid_width;
    	HEIGHT = grid_height;
		
		int area = WIDTH*HEIGHT;
		NUMCARN = Math.max(area/60, area/120 + 2); //making sure there are never 0
		NUMHERB = Math.max(area/45, area/90 + 2);
		NUMPLAN = Math.max(area/10, area/20 + 1);
		PLANTSADDED = Math.max(area/50, area/100 + 2);

        grid = new Entity[WIDTH][HEIGHT];
        rand = new Random();

        int x, y;
        //add NUMCARN carnivores to grid
        for (int iCarn = 0; iCarn < NUMCARN; iCarn++) {
        	do {
        		x = rand.nextInt(WIDTH);
        		y = rand.nextInt(HEIGHT);
        	}
        	while (!isEmpty(x,y));
        	grid[x][y] = new Carnivore(x,y);
        }
        //add NUMHERB herbivores
        for (int iHerb = 0; iHerb < NUMHERB; iHerb++) {
        	do {
        		x = rand.nextInt(WIDTH);
        		y = rand.nextInt(HEIGHT);
        	}
        	while (!isEmpty(x,y));
        	grid[x][y] = new Herbivore(x,y);
        }
        //add NUMPLAN plants
        for (int iPlant = 0; iPlant < NUMPLAN; iPlant++) {
        	do {
        		x = rand.nextInt(WIDTH);
        		y = rand.nextInt(HEIGHT);
        	}
        	while (!isEmpty(x,y));
        	grid[x][y] = new Plant(x,y);
        }


    }



    //-------------------------------------------
    //GET/SET
    public static World getInstance() {
        if(w == null)
            w = new World(30, 30);
        return w;
    }

    public static World getInstance(int width, int height) {
        if(w == null)
            w = new World(width, height);
        return w;
    }

    public boolean isEmpty(int x, int y) {
    	return (grid[x][y] == null);
    }



    //-------------------------------------------
    //MOVING ANIMALS
    public void moveCarnivores() {
    	Animal a;
    	for (int x = 0; x < WIDTH; x++) {
    		for (int y = 0; y < HEIGHT; y++){
    			if (!isEmpty(x,y) && grid[x][y] instanceof Carnivore && !((Animal)grid[x][y]).isMoving()) {
    				a = (Animal)grid[x][y];
    				//make sure each animal can only move once
    				a.move();

					//checks to see if the carnivore can give birth, else it moves
					boolean isBirthing = false;
					if (a.getAge() >= 5 && a.getAge() <= 12 && a.getEnergy() >= 9) {
						//needs an empty adjacent space to spawn a new carnivore
						for (int dx = -1; dx <= 1; dx++) {
							for (int dy = -1; dy <= 1; dy++) {
								try {
									if (isEmpty(x+dx,y+dy) && !isBirthing) {
										//give birth
										isBirthing = true;
										a.setEnergy(a.getEnergy()-4);
										grid[x+dx][y+dy] = new Carnivore(x+dx,y+dy);
										//newborn cannot move this cycle
										((Animal)grid[x+dx][y+dy]).move();
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
						if (a.isHungry()) {
							for (int dx = -1; dx <= 1; dx++) {
								for (int dy = -1; dy <= 1; dy++) {
									try {
										if (grid[x+dx][y+dy] instanceof Herbivore && !isHunting) {
											//found adjacent herbivore
											isHunting = true;
											Herbivore h = (Herbivore)grid[x+dx][y+dy];

											((Carnivore)a).eat(h);
											grid[x][y] = null;
											grid[x+dx][y+dy] = a;
											a.setX(x+dx);
											a.setY(y+dy);
										}
									} catch(ArrayIndexOutOfBoundsException e) {
										//off the grid
									}
								}
							}
						}
						if (!isHunting) {
							//didn't find prey nearby
	    					moveRandomly(a);
						}
					}
    			}
    		}
    	}
    	for (int x = 0; x < WIDTH; x++) {
    		for (int y = 0; y < HEIGHT; y++){
    			if (!isEmpty(x,y) && grid[x][y] instanceof Carnivore) {
    				//make sure each animal can only move once
    				((Animal)grid[x][y]).stopMoving();
    			}
    		}
    	}
    }

    public void moveHerbivores() {
    	Animal a;
    	for (int x = 0; x < WIDTH; x++) {
    		for (int y = 0; y < HEIGHT; y++){
    			if (!isEmpty(x,y) && grid[x][y] instanceof Herbivore && !((Animal)grid[x][y]).isMoving()) {
    				a = (Animal)grid[x][y];
    				//make sure each animal can only move once
    				a.move();

					//checks to see if the herbivore can give birth, else it moves
					boolean isBirthing = false;
					if (a.getAge() >= 5 && a.getAge() <= 12 && a.getEnergy() >= 9) {
						//needs an empty adjacent space to spawn a new herbivore
						for (int dx = -1; dx <= 1; dx++) {
							for (int dy = -1; dy <= 1; dy++) {
								try {
									if (isEmpty(x+dx,y+dy) && !isBirthing) {
										//give birth
										isBirthing = true;
										a.setEnergy(a.getEnergy()-4);
										grid[x+dx][y+dy] = new Herbivore(x+dx,y+dy);
										//newborn cannot move this cycle
										((Animal)grid[x+dx][y+dy]).move();
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
						if (a.isHungry()) {
							for (int dx = -1; dx <= 1; dx++) {
								for (int dy = -1; dy <= 1; dy++) {
									try {
										if (grid[x+dx][y+dy] instanceof Plant && !isHunting) {
											//found adjacent plant
											isHunting = true;
											Plant p = (Plant)grid[x+dx][y+dy];

											((Herbivore)a).eat(p);
											grid[x][y] = null;
											grid[x+dx][y+dy] = a;
											a.setX(x+dx);
											a.setY(y+dy);
										}
									} catch(ArrayIndexOutOfBoundsException e) {
										//off the grid
									}
								}
							}
						}
						if (!isHunting) {
							//didn't find food nearby
	    					moveRandomly(a);
						}
					}
    			}
    		}
    	}
    	for (int x = 0; x < WIDTH; x++) {
    		for (int y = 0; y < HEIGHT; y++){
    			if (!isEmpty(x,y) && grid[x][y] instanceof Herbivore) {
    				//make sure each animal can only move once
    				((Animal)grid[x][y]).stopMoving();
    			}
    		}
    	}
    }

	public void moveRandomly(Animal a) {
		//moves:
		//0 1 2
		//3 4 5
		//6 7 8
		//move[4] is the current location and will be false
		//since the location is taken by this animal
		int i = 0;
		int x = a.getX();
		int y = a.getY();
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
			grid[x+(chosenMove/3)-1][y+(chosenMove%3)-1] = a;
			a.setX(x+(chosenMove/3)-1);
			a.setY(y+(chosenMove%3)-1);
		}
	}



    //-------------------------------------------
	//AGING/DYING
	public void age() {
    	for (int x = 0; x < WIDTH; x++) {
    		for (int y = 0; y < HEIGHT; y++) {
    			if (!isEmpty(x,y)) {
    				Entity e = grid[x][y];
    				e.growOlder();
    				if (e.getAge() + 3*rand.nextGaussian() > e.getMaxAge()) {
    					//died from old age
    					grid[x][y] = null;
    				}
    				if (e instanceof Animal) {
    					((Animal)e).setEnergy(((Animal)e).getEnergy()-1);
    					if (((Animal)e).getEnergy() < 1) {
    						//starved
    						grid[x][y] = null;
    					}
    				}
    			}
    		}
    	}
	}



    //-------------------------------------------
    //ADD PLANTS
    public void addPlants() {
        //add PLANTSADDED plants
        int x, y;
        for (int iPlant = 0; iPlant < PLANTSADDED; iPlant++) {
        	do {
        		x = rand.nextInt(WIDTH);
        		y = rand.nextInt(HEIGHT);
        	}
        	while (!isEmpty(x,y));
        	grid[x][y] = new Plant(x,y);
        }
    }


    
    //-------------------------------------------
    //TOSTRING
    @Override
    public String toString() {
    	String s = "";
    	for (int x = 0; x < WIDTH; x++) {
    		for (int y = 0; y < HEIGHT; y++) {
    			if (isEmpty(x,y)) {
    				s += ".";
    			}
    			else {
    				s += grid[x][y].toString();
    			}

    			if (y != HEIGHT-1) {
    				s += " ";
    			}
    		}
    		s += "\n";
    	}
    	return s;
    }
}