import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class GeneticAlgorithm1 extends BasicGame {

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

	public GeneticAlgorithm1(String title) {
		super(title);

		Utils.rand = new Random();
		creatures = new ArrayList<Creature>();
		initCreatures();

	}

	public static void main(String[] args) {
		try {
			AppGameContainer app = new AppGameContainer(new GeneticAlgorithm1("Genetic Algorithm 1"));
			app.setDisplayMode(WIDTH, HEIGHT, false);
			app.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	private void initCreatures() {
		for (int i = 0; i < (MIN_CREATURES + MAX_CREATURES) / 2; i++) {
			creatures.add(new Creature());
		}
	}

	public void render(GameContainer arg0, Graphics g) throws SlickException {

		g.setBackground(Color.black);

		for (Creature c : creatures) {
			c.draw(g);
		}
		for (Food f : food) {
			f.draw(g);
		}

	}

	public void init(GameContainer arg0) throws SlickException {
		// TODO Auto-generated method stub

	}

	public void update(GameContainer arg0, int arg1) throws SlickException {

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

	public static Point getRandomPos() {
		return new Point((int) Math.floor(Utils.rand.nextDouble() * WIDTH),
				(int) Math.floor(Utils.rand.nextDouble() * HEIGHT));
	}

	public static double mutationFunction() {
		double r = (STRETCH_MUTATION_RATE * timeStep) + MID_MUTATION_RATE;
		r = Math.pow(Math.E, r) + 1;
		return 1 / r;
	}

}
