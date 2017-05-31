public class Plant extends Entity {
	/**
	*	Constructor for Plant Class
	*	@param x x coordinate for Plant Object
	*	@param y y coordinate for Plant Object
	*	@return whether animal moved or not already
	*/
	public Plant(int x, int y) {
		this.x = x;
		this.y = y;

		age = 0;
		maxAge = 15;
	}

	@Override
	public String toString() {
		return "*";
	}

}