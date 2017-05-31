import java.util.Random;

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

    /**
    *   (Private) Constructor for World Class
    *   @param grid_width width of grid that holds all Entities
    *   @param grid_height height of grid that holds all Entities
    */
    private World(int grid_width, int grid_height){
    	WIDTH = grid_width;
    	HEIGHT = grid_height;
		
		int area = WIDTH*HEIGHT;
		NUMCARN = Math.max(area/40, area/120 + 2); //making sure there are never 0
		NUMHERB = Math.max(area/45, area/90 + 2);
		NUMPLAN = Math.max(area/30, area/60 + 1);
		PLANTSADDED = Math.max(area/75, area/100 + 2);

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

        //Herder in the middle of the grid
        x = WIDTH/2;
        y = HEIGHT/2;
        grid[x][y] = Herder.getInstance(x,y,1);


    }



    //-------------------------------------------
    //GET/SET

    /**
    *   Retrieve single World instance
    *   @return singleton World instance
    */
    public static World getInstance() {
        if(w == null)
            w = new World(30, 30);
        return w;
    }

    /**
    *   Retrieve single World instance
    *   @param width width of grid that holds all Entities if World is not yet instantiated
    *   @param height height of grid that holds all Entities if World is not yet instantiated
    *   @return singleton World instance
    */
    public static World getInstance(int width, int height) {
        if(w == null)
            w = new World(width, height);
        return w;
    }

    /**
    *   Check if a location in the grid is empty i.e. it does not have an Entity on int
    *   @param x X coordinate in grid (width)
    *   @param y Y coordinate in grid (height)
    *   @return boolean result of query
    */
    public boolean isEmpty(int x, int y) {
    	return (grid[x][y] == null);
    }

    /**
    *   Returns the entity at a specific location on the grid
    *   @param x X coordinate in grid (width)
    *   @param y Y coordinate in grid (height)
    *   @return Entity at (x,y) or null if no Entity at that location
    */
    public Entity getEntity(int x, int y) {
		if(isEmpty(x,y) == true)
			return null;
		return grid[x][y];
	}

    /**
    *   Returns the grid that holds all Entities
    *   @return grid of the World which is a 2-dimensional Entity array
    */
	public Entity[][] getGrid(){
    	return grid;
	}




    //-------------------------------------------
    //MOVING ANIMALS

    /**
    *   Tells every Carnivore on the grid to move
    */
    public void moveCarnivores() {
    	Carnivore a;
    	for (int x = 0; x < WIDTH; x++) {
    		for (int y = 0; y < HEIGHT; y++){
    			if (!isEmpty(x,y) && grid[x][y] instanceof Carnivore && !((Animal)grid[x][y]).isMoving()) {
    				a = (Carnivore)grid[x][y];
    				//make sure each animal can only move once
    				a.startMoving();
    				a.move(grid);
    			}
    		}
    	}
    	for (int x = 0; x < WIDTH; x++) {
    		for (int y = 0; y < HEIGHT; y++){
    			if (!isEmpty(x,y) && grid[x][y] instanceof Carnivore) {
    				//make sure each animal can only move once
    				((Carnivore)grid[x][y]).stopMoving();
    			}
    		}
    	}
    }

    /**
    *   Tells every Herbivore on the grid to move
    */
    public void moveHerbivores() {
    	Herbivore a;
    	for (int x = 0; x < WIDTH; x++) {
    		for (int y = 0; y < HEIGHT; y++){
    			if (!isEmpty(x,y) && grid[x][y] instanceof Herbivore && !((Animal)grid[x][y]).isMoving()) {
    				a = (Herbivore)grid[x][y];
    				//make sure each animal can only move once
    				a.startMoving();
    				a.move(grid);
    			}
    		}
    	}
    	for (int x = 0; x < WIDTH; x++) {
    		for (int y = 0; y < HEIGHT; y++){
    			if (!isEmpty(x,y) && grid[x][y] instanceof Herbivore) {
    				//make sure each animal can only move once
    				((Herbivore)grid[x][y]).stopMoving();
    			}
    		}
    	}
    }


    //-------------------------------------------
	//AGING/DYING

    /**
    *   Tells every Animal on the grid to grow older and possibly die
    */
	public void age() {
    	for (int x = 0; x < WIDTH; x++) {
    		for (int y = 0; y < HEIGHT; y++) {
    			if (!isEmpty(x,y)) {
    				Entity e = grid[x][y];
    				if (e instanceof Herder)
    					continue;
    				e.growOlder();
    				if (e.getAge() + 3*rand.nextGaussian() > e.getMaxAge()) {
    					//died from old age
    					grid[x][y] = null;
    				}
                    if (e instanceof Herbivore) {
                        ((Herbivore)e).age(grid);
                    }
    				if (e instanceof Carnivore) {
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

    /**
    *   Adds PLANTSADDED instances of Plant to the grid
    */
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

    /**
    *   Print the grid representing the World
    *   @return String representing the current state of the World
    */
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