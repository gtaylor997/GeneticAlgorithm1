import java.awt.Canvas;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

public class Simulator extends Canvas {

	public static int WIDTH;
	public static int HEIGHT;
	public static int MID_MUTATION_RATE = 1;
	public static double STRETCH_MUTATION_RATE = 0.00007;
	public static int DEATH_PROB = 2000;
	public static int MIN_CREATURES = 8;
	public static int MAX_CREATURES = 30;

	private ArrayList<Creature> creatures;
	public static ArrayList<Food> food;

	private static int timeStep = 0;

	public Simulator() {
		Utils.rand = new Random();
		creatures = new ArrayList<Creature>();
		initCreatures();
	}

	private void initCreatures() {
		for (int i = 0; i < (MIN_CREATURES + MAX_CREATURES) / 2; i++) {
			creatures.add(new Creature());
		}
	}

	public static Point getRandomPos() {
		return new Point((int) Math.floor(Utils.rand.nextDouble() * WIDTH),
				(int) Math.floor(Utils.rand.nextDouble() * HEIGHT));
	}

	public static double mutationFunction() {
		double r = (STRETCH_MUTATION_RATE * timeStep) + MID_MUTATION_RATE;
		r = Math.pow(Math.E, r) + 1;
		return 1 / r;
	}

	public void update() {

		// Make every creature act
		for (Creature c : creatures) {
			c.act();
		}

		// Ensure that the number of creatures does not exceed the maximum by
		// killing the creatures with the least energy
		while (creatures.size() > MAX_CREATURES) {
			Creature min = creatures.get(0);
			double energy = min.getEnergy();
			for (Creature c : creatures) {
				if (c.getEnergy() < energy) {
					min = c;
					energy = c.getEnergy();
				}
			}
			creatures.remove(min);
		}

		// If there are less creatures than the minimum then create 3 more
		// creatures
		while (creatures.size() < MIN_CREATURES) {
			creatures.add(new Creature());
		}

		int eatDistance = Creature.SIZE / 2 + Food.SIZE / 2;
		for (Creature c : creatures) {
			for (Food f : food) {
				if (Utils.getDistance(c.getPos(), f.getPos()) < eatDistance) {
					f.eat();
					c.eat();
				}
			}
		}
	}

	public void paint(Graphics g) {

		g.fillRect(0, 0, WIDTH, HEIGHT);

		for (Creature c : creatures) {
			c.draw(g);
		}
		for (Food f : food) {
			f.draw(g);
		}
	}

	public void start() {
		// TODO Auto-generated method stub

	}

}
