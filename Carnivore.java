public class Carnivore extends Animal {

	public Carnivore(int x, int y) {
		super(x,y);
	}

	@Override
	public String toString() {
		return "@";
	}

	public void eat(Herbivore h) {
		//eating a healty animal gives more energy
		energy += (7 + h.getEnergy()/2);
	}
}