import org.newdawn.slick.Graphics;

public class Food {

	public static int SIZE = 1;
	private Point pos;

	private Food() {
		setPos(GeneticAlgorithm1.getRandomPos());
	}

	public void eat() {
		setPos(GeneticAlgorithm1.getRandomPos());
	}

	public Point getPos() {
		return pos;
	}

	public void setPos(Point pos) {
		this.pos = pos;
	}

	public void draw(Graphics g) {
		g.drawOval(pos.getX() - SIZE / 2, pos.getY() - SIZE / 2, SIZE, SIZE);
	}

}
