import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class Creature {

	public static int SIZE = 10;
	public static double SPEED = 3.0f;
	public static double ROTATION_SPEED = 1.0f;
	public static double VIEW_RANGE = 20;
	public static int STATE_COUNT = 32;
	public static double ENERGY_LOSS_RATE = 0.05;
	public static int PERCEPT_COUNT = 3;
	public static double FOOD_ENERGY = 10;

	public static int FOOD_LEFT = 0;
	public static int FOOD_RIGHT = 1;
	public static int FOOD_NONE = 2;

	private double energy;
	private double angle;
	private Point pos;
	private int state = 0;

	private int[][] genes = new int[STATE_COUNT][PERCEPT_COUNT];

	/**
	 * The default constructor for a new creature
	 */
	public Creature() {
		setPos(GeneticAlgorithm1.getRandomPos());

		for (int i = 0; i < STATE_COUNT; i++) {
			for (int j = 0; j < PERCEPT_COUNT; j++) {
				genes[i][j] = Utils.rand.nextInt((STATE_COUNT * 3) + 2);
			}
		}
	}

	/**
	 * Move the creature
	 */
	private void move() {

		getPos().setX((int) (getPos().getX() + (SPEED * Math.cos(angle))));
		getPos().setY((int) (getPos().getY() + (SPEED * Math.sin(angle))));

	}

	/**
	 * Turn the creature
	 * 
	 * @param dir
	 *            True = Left, False = Right
	 */
	private void turn(boolean dir) {
		angle += dir ? ROTATION_SPEED : -ROTATION_SPEED;
	}

	/**
	 * Perform a single action for the creature
	 */
	public void act() {
		setEnergy(getEnergy() - ENERGY_LOSS_RATE);
		int percept = getPercept();
		int gene = genes[state][percept];
		state = Math.floorDiv(gene, 3);
		int action = Math.floorMod(gene, 3);
		switch (action) {
		case 0:
			move();
			break;
		case 1:
			turn(true);
			break;
		case 2:
			turn(false);
			break;
		}
	}

	/**
	 * Get the value for the current percept
	 * 
	 * @return The value of the percept
	 */
	private int getPercept() {
		Food closest = GeneticAlgorithm1.food.get(0);
		double distance = Utils.getDistance(closest.getPos(), getPos());
		for (Food f : GeneticAlgorithm1.food) {
			double c = Utils.getDistance(f.getPos(), getPos());
			if (c < distance) {
				distance = c;
				closest = f;
			}
		}
		if (distance > VIEW_RANGE) {
			return FOOD_NONE;
		}

		// TODO: Food angle

		return 0;
	}

	/**
	 * Mutate the genes of the creature
	 */
	public void mutate() {
		int max = (STATE_COUNT * 3) + 2;
		for (int i = 0; i < STATE_COUNT; i++) {
			for (int j = 0; j < PERCEPT_COUNT; j++) {
				int mod = (int) (modificationFunction(max) * GeneticAlgorithm1.mutationFunction());
				genes[i][j] += mod;
			}
		}

	}

	/**
	 * Function to scale the value of the mutation closer to 0
	 * 
	 * @return The scaled value
	 */
	public double modificationFunction(double max) {
		double r = Utils.rand.nextDouble() * 2 - 1;
		r = Math.pow(r, 19);
		r *= max;
		return r;
	}

	public double getEnergy() {
		return energy;
	}

	public void setEnergy(double energy) {
		this.energy = energy;
	}

	public void draw(Graphics g) {
		g.setColor(Color.white);
		g.drawOval(getPos().getX() - SIZE / 2, getPos().getY() - SIZE / 2, SIZE, SIZE);
		int dx = (int) ((SIZE / 2 * Math.cos(Math.toRadians(angle))) + getPos().getX());
		int dy = (int) ((SIZE / 2 * Math.sin(Math.toRadians(angle))) + getPos().getY());
		g.drawLine(getPos().getX(), getPos().getY(), dx, dy);
	}

	public Point getPos() {
		return pos;
	}

	public void setPos(Point pos) {
		this.pos = pos;
	}

	public void eat() {
		energy += FOOD_ENERGY;
	}

}
