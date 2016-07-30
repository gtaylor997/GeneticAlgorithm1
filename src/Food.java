import java.awt.Graphics;

public class Food {

	public static int SIZE = 1;
	private Point pos;

	private Food() {
		setPos(Simulator.getRandomPos());
	}

	public void eat() {
		setPos(Simulator.getRandomPos());
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
