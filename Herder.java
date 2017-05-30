// TODO: ADD THREE HERBIVORE LINKED LISTS AND ADD NEXT NODE TO 
// EACH HERBIVORE

public class Herder extends Entity {
    private Herbivore[] herbPack = new Herbivore[3];

	public Herder(int x, int y){
		super(x, y);
        // for(int i = 0; i < 3; i++)
        //     herbPack[i] = new Herbivore()

	}

    public void setX(int x) {
        this.x = x;
    }
    
    public void setY(int y) {
        this.y = y;
    }

    public boolean checkValidMove(Grid g, int newX, int newY) {
    //check out of bounds
        if (newX < 0 || newY < 0 || newX > g.GRID_NUM_ROWS - 1 || newY > g.GRID_NUM_COLS - 1)
            return false;
        if (g.getEntity(newX, newY) != null)
            return false;
        return true;
	}
    
    public void moveUp(Grid g) {
    	int newX = this.getX()-1;
    	int newY = this.getY();
    	if (checkValidMove(g, newX, newY) == true) {
	    	g.removeEntity(this.getX(), this.getY());
	        this.setX(newX);
	        this.setY(newY);
	        g.addEntity(this);
    	}
    }

    public void moveDown(Grid g) {
    	int newX = this.getX()+1;
    	int newY = this.getY();
    	if (checkValidMove(g, newX, newY) == true) {
	    	g.removeEntity(this.getX(), this.getY());
	        this.setX(newX);
	        this.setY(newY);
	        g.addEntity(this);
    	}
    }

    public void moveLeft(Grid g) {
    	int newX = this.getX();
    	int newY = this.getY()-1;
    	if (checkValidMove(g, newX, newY) == true) {
	    	g.removeEntity(this.getX(), this.getY());
	        this.setX(newX);
	        this.setY(newY);
	        g.addEntity(this);
    	}
    }

    public void moveRight(Grid g) {
    	int newX = this.getX();
    	int newY = this.getY()+1;
    	if (checkValidMove(g, newX, newY) == true) {
	    	g.removeEntity(this.getX(), this.getY());
	        this.setX(newX);
	        this.setY(newY);
	        g.addEntity(this);
    	}
    }

}