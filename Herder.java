// TODO: ADD THREE HERBIVORE LINKED LISTS AND ADD NEXT NODE TO 
// EACH HERBIVORE

public class Herder extends Entity {
    private Herbivore[] herbPack = new Herbivore[3];

	public Herder(int x, int y, int herbDirection){
		this.x = x;
        this.y = y;
        // herbDirection: 0 up, 1 down, 2 left, 3 right
        switch(herbDirection) {
            case 0:
                for(int i = 0; i < 3; i++)
                    herbPack[i] = new Herbivore(x-1, y-1+i);
            case 1:
                for(int i = 0; i < 3; i++)
                    herbPack[i] = new Herbivore(x+1, y-1+i);
            case 2:
                for(int i = 0; i < 3; i++)
                    herbPack[i] = new Herbivore(x-1+i, y-1);
            case 3:
                for(int i = 0; i < 3; i++)
                    herbPack[i] = new Herbivore(x-1+i, y+1);
        }
        
	}

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean checkValidMove(Entity[][] grid, int newX, int newY) {
    //check out of bounds
        if (newX < 0 || newY < 0 || newX > World.getInstance().WIDTH - 1 || newY > World.getInstance().HEIGHT - 1)
            return false;
        if (grid[newX][newY] != null)
            return false;
        return true;
	}
    
    public void moveUp(Entity[][] grid) {
        int X = this.getX();
        int Y = this.getY();
    	int newX = X-1;
    	int newY = Y;
    	if (checkValidMove(grid, newX, newY) == true) {
            // remove entity from grid
            grid[X][Y] = null;
	        this.setX(newX);
	        this.setY(newY);
            // add entity to grid
            grid[newX][newY] = this;
    	}
    }

    public void moveDown(Entity[][] grid) {
        int X = this.getX();
        int Y = this.getY();
    	int newX = X+1;
    	int newY = Y;
    	if (checkValidMove(grid, newX, newY) == true) {
            // remove entity from grid
            grid[X][Y] = null;
            this.setX(newX);
            this.setY(newY);
            // add entity to grid
            grid[newX][newY] = this;
        }
    }

    public void moveLeft(Entity[][] grid) {
        int X = this.getX();
        int Y = this.getY();
    	int newX = X;
    	int newY = Y-1;
    	if (checkValidMove(grid, newX, newY) == true) {
            // remove entity from grid
            grid[X][Y] = null;
            this.setX(newX);
            this.setY(newY);
            // add entity to grid
            grid[newX][newY] = this;
        }
    }

    public void moveRight(Entity[][] grid) {
        int X = this.getX();
        int Y = this.getY();
    	int newX = X;
    	int newY = Y+1;
    	if (checkValidMove(grid, newX, newY) == true) {
            // remove entity from grid
            grid[X][Y] = null;
            this.setX(newX);
            this.setY(newY);
            // add entity to grid
            grid[newX][newY] = this;
        }
    }

}