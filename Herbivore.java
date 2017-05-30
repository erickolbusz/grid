public class Herbivore extends Animal {

	public Herbivore(int x, int y) {
		super(x,y);
	}

	@Override
	public String toString() {
		return "&";
	}

	public void eat(Plant p) {
		energy += 5;
	}
}