public class Food {

	private int size = 1;
	private Point pos;
	
	private Food() {
		setPos(GeneticAlgorithm1.getRandomPos());
	}
	
	private void eat() {
		setPos(GeneticAlgorithm1.getRandomPos());
	}

	public Point getPos() {
		return pos;
	}

	public void setPos(Point pos) {
		this.pos = pos;
	}
	
}
