public class Plant extends Entity {

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