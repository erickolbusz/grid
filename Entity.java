public class Entity {
	protected int x, y, age, maxAge;
	protected boolean alive;
	/**
	*	Retrieve x coordinate
	*	@return x coordinate for Entity
	*/
	public int getX() {
		return x;
	}
	/**
	*	Retrieve y coordinate
	*	@return y coordinate for Entity
	*/
	public int getY() {
		return y;
	}
	/**
	*	Retrieve age of Entity
	*	@return age for Entity
	*/
	public int getAge() {
		return age;
	}
	/**
	*	Retrieve max age of Entity
	*	@return max age for Entity
	*/
	public int getMaxAge() {
		return maxAge;
	}
	/**
	*	Increase age
	*/
	public void growOlder() {
		age++;
	}
}