public class Entity {
	protected int x, y, age, maxAge;
	protected boolean alive;

	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public int getAge() {
		return age;
	}
	public int getMaxAge() {
		return maxAge;
	}

	public void growOlder() {
		age++;
	}
}