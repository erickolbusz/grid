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

        //Herder in the middle of the grid
        x = WIDTH/2;
        y = HEIGHT/2;
        grid[x][y] = Herder.getInstance(x,y,1);


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

    public Entity getEntity(int x, int y) {
		if(isEmpty(x,y) == true)
			return null;
		return grid[x][y];
	}

	public Entity[][] getGrid(){
    	return grid;
	}




    //-------------------------------------------
    //MOVING ANIMALS
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
                        e.age(grid);
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